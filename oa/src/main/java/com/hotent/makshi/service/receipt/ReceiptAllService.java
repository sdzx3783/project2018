package com.hotent.makshi.service.receipt;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import org.codehaus.stax2.ri.typed.ValueDecoderFactory.LongArrayDecoder;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.util.BeanUtils;
import com.hotent.makshi.model.receipt.ReceiptAll;
import com.hotent.makshi.dao.receipt.ReceiptAllDao;
import com.hotent.core.util.UniqueIdUtil;

import net.sf.json.util.JSONUtils;
import net.sf.ezmorph.object.DateMorpher;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.bpm.model.ProcessTask;
import com.hotent.core.util.StringUtil;
import net.sf.json.JSONObject;


import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.model.bpm.BpmDefinition;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.model.bpm.TaskRead;
import com.hotent.platform.service.bpm.BpmDefinitionService;
import com.hotent.platform.service.bpm.BpmService;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.core.service.WfBaseService;
import com.hotent.core.annotion.WorkFlow;
import com.hotent.core.api.util.ContextUtil;
import com.hotent.platform.service.bpm.util.BpmAspectUtil;
import com.hotent.core.bpm.BpmResult;
import com.hotent.platform.service.bpm.BpmBusLinkService;
import com.hotent.platform.service.form.TaskReadService;


@Service
public class ReceiptAllService extends WfBaseService<ReceiptAll>
{
	@Resource
	private ReceiptAllDao dao;
	
	@Resource
	private BpmService bpmService;
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmBusLinkService bpmBusLinkService;
	@Resource
	private TaskReadService taskReadService;
	@Resource
	private BpmDefinitionService bpmDefinitionService;
	public ReceiptAllService()
	{
	}
	
	@Override
	protected IEntityDao<ReceiptAll,Long> getEntityDao() 
	{
		return dao;
	}
	/**
	 * 根据businessKey获取记录
	 * @param valueOf
	 * @return
	 */
	public ReceiptAll getBygetBusinessKey(Long businessKey) {
		return dao.getById(businessKey);
	}
	/**
	 * 重写getAll方法绑定流程runId
	 * @param queryFilter
	 */
	public List<ReceiptAll> getAll(QueryFilter queryFilter){
		//获取我发起的收文
		queryFilter.addFilter("processName", "收文");
		queryFilter.addFilter("creatorId", ContextUtil.getCurrentUserId());
		List<ProcessRun> lists = processRunService.getAll(queryFilter);
		//设置收文文集合
		/*if (BeanUtils.isNotEmpty(lists)) {
			for (ProcessRun processRun : lists) {
				processRun.setIsRead(false);
				//获取getBusinessKey，通过getBusinessKey获取申请数据
				String businessKey = processRun.getBusinessKey();
				ReceiptAll rec = dao.getById(Long.valueOf(businessKey));
				processRun.setProcessName(rec.get)
				String actInstId = processRun.getActInstId();//流程实例id
				List<TaskRead> taskReadByInstId = taskReadService.getTaskReadByInstId(Long.parseLong(actInstId==null?"1":actInstId));
				if(taskReadByInstId!=null && taskReadByInstId.size()>0){
					processRun.setIsRead(true);//已读，不能追回
				}
				BpmDefinition bpmDefinition = bpmDefinitionService.getByActDefId(processRun.getActDefId());
				if (bpmDefinition.getIsPrintForm() == 1) {
					processRun.setIsPrintForm(bpmDefinition.getIsPrintForm());
				}
			}
		}*/

		
		String IdList = "(";
		for(ProcessRun pro:lists){
			IdList = IdList+pro.getBusinessKey()+",";
		};
		IdList = IdList.substring(0, IdList.length()-1);
		IdList = IdList + ")";
		List<ReceiptAll> receiptAlls=new ArrayList<ReceiptAll>();
		if(IdList.length()>2){
			queryFilter.addFilter("ids", IdList);
			//queryFilter.addFilter("state", 1);
			List<ProcessTask> tasks = null;
			List<ReceiptAll> receiptAllList=super.getAll(queryFilter);
			for(ReceiptAll receiptAll:receiptAllList){
				ProcessRun processRun=processRunService.getByBusinessKey(receiptAll.getId().toString());
				if(BeanUtils.isNotEmpty(processRun)){
					if(null!=processRun.getActInstId()){
						tasks = bpmService.getTasks(processRun.getActInstId());
					};
					if(null!=processRun.getRunId()){
						receiptAll.setRunId(processRun.getRunId());
					};	
					if(tasks!=null && tasks.size()>0){
						receiptAll.setState(tasks.get(0).getName());
						receiptAll.setTaskId(Long.valueOf(tasks.get(0).getId()));
					}
				}
				receiptAlls.add(receiptAll);
			}
			return receiptAlls;
		}
		return receiptAlls;	
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
			ReceiptAll receiptAll=getReceiptAll(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				receiptAll.setId(genId);
				this.add(receiptAll);
			}else{
				receiptAll.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(receiptAll);
			}
			cmd.setBusinessKey(receiptAll.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取ReceiptAll对象
	 * @param json
	 * @return
	 */
	public ReceiptAll getReceiptAll(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		ReceiptAll receiptAll = (ReceiptAll)JSONObject.toBean(obj, ReceiptAll.class);
		return receiptAll;
	}
	/**
	 * 保存 收文汇总表 信息
	 * @param receiptAll
	 */

	@WorkFlow(flowKey="receipt",tableName="receipt_all")
	public void save(ReceiptAll receiptAll) throws Exception{
		Long id=receiptAll.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			receiptAll.setId(id);
		    this.add(receiptAll);
		    //添加到业务关联数据表
		    bpmBusLinkService.add(receiptAll.getId().toString(), null , true,  "receipt_all");
		}
		else{
		    this.update(receiptAll);
		}
		BpmResult result=new BpmResult();
		//添加流程变量
//		result.addVariable("", "");
		result.setBusinessKey(id.toString());
		BpmAspectUtil.setBpmResult(result);
	}

	public List<ReceiptAll> getCheckList(QueryFilter queryFilter) {
		queryFilter.addFilter("checkState", 0);
		return getAll(queryFilter);
		
	}

	public List<ReceiptAll> getSendList(QueryFilter queryFilter) {
		queryFilter.addFilter("checkState", 1);
		return getAll(queryFilter);
	}
	
	public int updateReadState(Long id){
		return dao.update("updateReadState", id);
	}


}
