package com.hotent.makshi.service.hr;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.GenericService;
import com.hotent.core.util.BeanUtils;
import com.hotent.makshi.model.hr.Transferposition;
import com.hotent.makshi.dao.hr.TransferpositionDao;
import com.hotent.core.util.UniqueIdUtil;

import net.sf.json.util.JSONUtils;
import net.sf.ezmorph.object.DateMorpher;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.bpm.model.ProcessTask;
import com.hotent.core.util.StringUtil;
import net.sf.json.JSONObject;


import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.service.bpm.BpmService;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.core.service.WfBaseService;
import com.hotent.core.annotion.WorkFlow;
import com.hotent.platform.service.bpm.util.BpmAspectUtil;
import com.hotent.core.bpm.BpmResult;
import com.hotent.platform.service.bpm.BpmBusLinkService;


@Service
public class TransferpositionService extends WfBaseService<Transferposition>
{
	@Resource
	private TransferpositionDao dao;
	
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmBusLinkService bpmBusLinkService;
	@Resource
	private BpmService bpmService;
	public TransferpositionService()
	{
	}
	
	@Override
	protected IEntityDao<Transferposition,Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 重写getAll方法绑定流程runId
	 * @param queryFilter
	 */
	public List<Transferposition> getAll(QueryFilter queryFilter){
		List<Transferposition> transferpositionList=super.getAll(queryFilter);
		List<Transferposition> transferpositions=new ArrayList<Transferposition>();
		for(Transferposition transferposition:transferpositionList){
			ProcessRun processRun=processRunService.getByBusinessKey(transferposition.getId().toString());
			List<ProcessTask> tasks = null;
			if(BeanUtils.isNotEmpty(processRun)){
				if(processRun.getActInstId()==null) continue;
				tasks = bpmService.getTasks(processRun.getActInstId());
				transferposition.setRunId(processRun.getRunId());
				transferposition.setCreator(processRun.getCreator());
				transferposition.setCreateTime(processRun.getCreatetime());
				transferposition.setGlobalFlowNo(processRun.getGlobalFlowNo());
			}
			transferposition.setProcessStatus("已结束");
			if(tasks!=null && tasks.size()>0){
				transferposition.setProcessStatus(tasks.get(0).getName());
			}
			transferpositions.add(transferposition);
		}
		return transferpositions;
	}
	

		
	
	/**
	 * 流程处理器方法 用于处理业务数据
	 * @param cmd
	 * @throws Exception
	 */
	public void processHandler(ProcessCmd cmd)throws Exception{
		Map data=cmd.getFormDataMap();
		if(BeanUtils.isNotEmpty(data)){
			String json=data.get("json").toString();
			Transferposition transferposition=getTransferposition(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				transferposition.setId(genId);
				this.add(transferposition);
			}else{
				transferposition.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(transferposition);
			}
			cmd.setBusinessKey(transferposition.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取Transferposition对象
	 * @param json
	 * @return
	 */
	public Transferposition getTransferposition(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		Transferposition transferposition = (Transferposition)JSONObject.toBean(obj, Transferposition.class);
		return transferposition;
	}
	/**
	 * 保存 部门内调岗 信息
	 * @param transferposition
	 */

	@WorkFlow(flowKey="transferPosition",tableName="transferposition")
	public void save(Transferposition transferposition) throws Exception{
		Long id=transferposition.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			transferposition.setId(id);
		    this.add(transferposition);
		    //添加到业务关联数据表
		    bpmBusLinkService.add(transferposition.getId().toString(), null , true,  "transferposition");
		}
		else{
		    this.update(transferposition);
		}
		BpmResult result=new BpmResult();
		//添加流程变量
//		result.addVariable("", "");
		result.setBusinessKey(id.toString());
		BpmAspectUtil.setBpmResult(result);
	}
	
	public List<Transferposition> getFinishedList(QueryFilter filter){
		return dao.getBySqlKey("getFinishedList", filter);
	}
}
