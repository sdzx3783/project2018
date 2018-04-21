package com.hotent.makshi.service.seal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.GenericService;
import com.hotent.core.util.BeanUtils;
import com.hotent.makshi.model.seal.SealBorrow;
import com.hotent.makshi.dao.seal.SealBorrowDao;
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
public class SealBorrowService extends WfBaseService<SealBorrow>
{
	@Resource
	private SealBorrowDao dao;
	
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmBusLinkService bpmBusLinkService;
	@Resource
	private BpmService bpmService;
	public SealBorrowService()
	{
	}
	
	@Override
	protected IEntityDao<SealBorrow,Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 重写getAll方法绑定流程runId
	 * @param queryFilter
	 */
	public List<SealBorrow> getAll(QueryFilter queryFilter){
		List<SealBorrow> sealBorrowList=super.getAll(queryFilter);
		List<SealBorrow> sealBorrows=new ArrayList<SealBorrow>();
		for(SealBorrow sealBorrow:sealBorrowList){
			ProcessRun processRun=processRunService.getByBusinessKey(sealBorrow.getId().toString());
			List<ProcessTask> tasks = null;
			if(BeanUtils.isNotEmpty(processRun)){
				if(null!= processRun.getRunId()){
					sealBorrow.setRunId(processRun.getRunId());
				}
				if(null!= processRun.getActInstId()){
					tasks = bpmService.getTasks(processRun.getActInstId());
				}
				if(tasks!=null && tasks.size()>0){
					sealBorrow.setStatus(tasks.get(0).getName());
				}else{
					sealBorrow.setStatus("流程已结束");
				}
				sealBorrow.setGlobalNo(processRun.getGlobalFlowNo());
				sealBorrow.setCreateTime(processRun.getCreatetime());
			}
			sealBorrows.add(sealBorrow);
		}
		return sealBorrows;
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
			SealBorrow sealBorrow=getSealBorrow(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				sealBorrow.setId(genId);
				this.add(sealBorrow);
			}else{
				sealBorrow.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(sealBorrow);
			}
			cmd.setBusinessKey(sealBorrow.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取SealBorrow对象
	 * @param json
	 * @return
	 */
	public SealBorrow getSealBorrow(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		SealBorrow sealBorrow = (SealBorrow)JSONObject.toBean(obj, SealBorrow.class);
		return sealBorrow;
	}
	/**
	 * 保存 公章外借申请表 信息
	 * @param sealBorrow
	 */

	@WorkFlow(flowKey="seal_borrow",tableName="seal_borrow")
	public void save(SealBorrow sealBorrow) throws Exception{
		Long id=sealBorrow.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			sealBorrow.setId(id);
		    this.add(sealBorrow);
		    //添加到业务关联数据表
		    bpmBusLinkService.add(sealBorrow.getId().toString(), null , true,  "seal_borrow");
		}
		else{
		    this.update(sealBorrow);
		}
		BpmResult result=new BpmResult();
		//添加流程变量
//		result.addVariable("", "");
		result.setBusinessKey(id.toString());
		BpmAspectUtil.setBpmResult(result);
	}
}
