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
import com.hotent.makshi.model.hr.DepartmentTransfer;
import com.hotent.makshi.dao.hr.DepartmentTransferDao;
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
public class DepartmentTransferService extends WfBaseService<DepartmentTransfer>
{
	@Resource
	private DepartmentTransferDao dao;
	
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmBusLinkService bpmBusLinkService;
	@Resource
	private BpmService bpmService;
	public DepartmentTransferService()
	{
	}
	
	@Override
	protected IEntityDao<DepartmentTransfer,Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 重写getAll方法绑定流程runId
	 * @param queryFilter
	 */
	public List<DepartmentTransfer> getAll(QueryFilter queryFilter){
		List<DepartmentTransfer> departmentTransferList=super.getAll(queryFilter);
		List<DepartmentTransfer> departmentTransfers=new ArrayList<DepartmentTransfer>();
		for(DepartmentTransfer departmentTransfer:departmentTransferList){
			ProcessRun processRun=processRunService.getByBusinessKey(departmentTransfer.getId().toString());
			List<ProcessTask> tasks = null;
			if(BeanUtils.isNotEmpty(processRun)){
				if(processRun.getActInstId()==null) continue;
				tasks = bpmService.getTasks(processRun.getActInstId());
				departmentTransfer.setRunId(processRun.getRunId());
				departmentTransfer.setCreator(processRun.getCreator());
				departmentTransfer.setCreateTime(processRun.getCreatetime());
				departmentTransfer.setGlobalFlowNo(processRun.getGlobalFlowNo());
			}
			departmentTransfer.setProcessStatus("已结束");
			if(tasks!=null && tasks.size()>0){
				departmentTransfer.setProcessStatus(tasks.get(0).getName());
			}
			departmentTransfers.add(departmentTransfer);
		}
		return departmentTransfers;
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
			DepartmentTransfer departmentTransfer=getDepartmentTransfer(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				departmentTransfer.setId(genId);
				this.add(departmentTransfer);
			}else{
				departmentTransfer.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(departmentTransfer);
			}
			cmd.setBusinessKey(departmentTransfer.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取DepartmentTransfer对象
	 * @param json
	 * @return
	 */
	public DepartmentTransfer getDepartmentTransfer(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		DepartmentTransfer departmentTransfer = (DepartmentTransfer)JSONObject.toBean(obj, DepartmentTransfer.class);
		return departmentTransfer;
	}
	/**
	 * 保存 部门调动表 信息
	 * @param departmentTransfer
	 */

	@WorkFlow(flowKey="department_tranfer",tableName="department_transfer")
	public void save(DepartmentTransfer departmentTransfer) throws Exception{
		Long id=departmentTransfer.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			departmentTransfer.setId(id);
		    this.add(departmentTransfer);
		    //添加到业务关联数据表
		    bpmBusLinkService.add(departmentTransfer.getId().toString(), null , true,  "department_transfer");
		}
		else{
		    this.update(departmentTransfer);
		}
		BpmResult result=new BpmResult();
		//添加流程变量
//		result.addVariable("", "");
		result.setBusinessKey(id.toString());
		BpmAspectUtil.setBpmResult(result);
	}
}
