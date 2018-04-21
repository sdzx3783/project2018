package com.hotent.makshi.service.dispatch;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.TaskService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hotent.core.annotion.WorkFlow;
import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.WfBaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.makshi.dao.dispatch.DispatchDao;
import com.hotent.makshi.model.contract.ContractSealApplication;
import com.hotent.makshi.model.dispatch.Dispatch;
import com.hotent.makshi.model.dispatch.DocDic;
import com.hotent.platform.dao.bpm.ProcessRunDao;
import com.hotent.platform.model.bpm.BpmDefinition;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.model.bpm.TaskRead;
import com.hotent.platform.model.system.MessageSend;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.bpm.BpmBusLinkService;
import com.hotent.platform.service.bpm.BpmDefinitionService;
import com.hotent.platform.service.bpm.BpmService;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.platform.service.form.TaskReadService;
import com.hotent.platform.service.system.MessageSendService;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

@Service
public class DispatchService extends WfBaseService<Dispatch> {
	private final Logger log = Logger.getLogger(this.getClass());
	@Resource
	private DispatchDao dao;
	@Resource
	private DocDicService docDicService;
	@Resource
	private BpmService bpmService;
	@Resource
	private TaskService taskService;
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmBusLinkService bpmBusLinkService;
	@Resource
	private TaskReadService taskReadService;
	@Resource
	private BpmDefinitionService bpmDefinitionService;
	@Resource
	private MessageSendService msgSendService;
	
	@Resource
	private ProcessRunDao processRunDao;
	public DispatchService() {
	}

	@Override
	protected IEntityDao<Dispatch, Long> getEntityDao() {
		return dao;
	}

	public Dispatch getBygetBusinessKey(Long businessKey) {
		return dao.getById(businessKey);
	}

	/**
	 * 重写getAll方法绑定流程runId
	 * 
	 * @param queryFilter
	 */
	public List<Dispatch> getAll(QueryFilter queryFilter) {
		queryFilter.addFilter("processName", "发文");
		queryFilter.addFilter("creatorId", ContextUtil.getCurrentUserId());
		List<ProcessRun> lists = processRunService.getAll(queryFilter);
		// 设置发文文集合
		if (BeanUtils.isNotEmpty(lists)) {
			for (ProcessRun processRun : lists) {
				processRun.setIsRead(false);
				// 获取businessKey，通过businessKey获取申请数据
				String businessKey = processRun.getBusinessKey();
				Dispatch dis = dao.getById(Long.valueOf(businessKey));
				processRun.setProcessName(dis.getDispatch_id());
				String actInstId = processRun.getActInstId();// 流程实例id
				List<TaskRead> taskReadByInstId = taskReadService.getTaskReadByInstId(Long.parseLong(actInstId == null ? "1" : actInstId));
				if (taskReadByInstId != null && taskReadByInstId.size() > 0) {
					processRun.setIsRead(true);// 已读，不能追回
				}
				BpmDefinition bpmDefinition = bpmDefinitionService.getByActDefId(processRun.getActDefId());
				if (bpmDefinition.getIsPrintForm() == 1) {
					processRun.setIsPrintForm(bpmDefinition.getIsPrintForm());
				}
			}
		}
		return null;
		/*
		 * String IdList = "("; for(ProcessRun pro:lists){ IdList = IdList+pro.getBusinessKey()+","; }; IdList = IdList.substring(0, IdList.length()-1); IdList = IdList + ")";
		 * queryFilter.addFilter("ids", IdList); List<Dispatch> dispatchs=new ArrayList<Dispatch>(); if(IdList.length()>2){ queryFilter.addFilter("ids", IdList); List<ProcessTask> tasks = null;
		 * List<Dispatch> dispatchList=super.getAll(queryFilter); for(Dispatch dispatch:dispatchList){ ProcessRun processRun=processRunService.getByBusinessKey(dispatch.getId().toString());
		 * if(BeanUtils.isNotEmpty(processRun)){ dispatch.setRunId(processRun.getRunId()); tasks = bpmService.getTasks(processRun.getActInstId()); if(tasks!=null && tasks.size()>0){
		 * dispatch.setState(tasks.get(0).getName()); dispatch.setTaskId(Long.valueOf(tasks.get(0).getId())); } } dispatchs.add(dispatch); } return dispatchs; }else{ return dispatchs; }
		 */
	}

	/**
	 * 流程处理器方法 用于处理业务数据
	 * 
	 * @param cmd
	 * @throws Exception
	 */
	public void processHandler(ProcessCmd cmd) throws Exception {
		Map data = cmd.getFormDataMap();
		if (BeanUtils.isNotEmpty(data)) {
			String json = data.get("json").toString();
			Dispatch dispatch = getDispatch(json);
			if (StringUtil.isEmpty(cmd.getBusinessKey())) {
				Long genId = UniqueIdUtil.genId();
				dispatch.setId(genId);
				this.add(dispatch);
			} else {
				dispatch.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(dispatch);
			}
			cmd.setBusinessKey(dispatch.getId().toString());
		}
	}

	/**
	 * 根据json字符串获取Dispatch对象
	 * 
	 * @param json
	 * @return
	 */
	public Dispatch getDispatch(String json) {
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if (StringUtil.isEmpty(json))
			return null;
		JSONObject obj = JSONObject.fromObject(json);
		Dispatch dispatch = (Dispatch) JSONObject.toBean(obj, Dispatch.class);
		return dispatch;
	}

	/**
	 * 保存 发文总表 信息
	 * 
	 * @param dispatch
	 */

	@WorkFlow(flowKey = "dispatch", tableName = "dispatch")
	public void save(Dispatch dispatch) throws Exception {
		Long id = dispatch.getId();
		dispatch.setState("1");
		if (id == null || id == 0) {
			id = UniqueIdUtil.genId();
			dispatch.setId(id);
			this.add(dispatch);
		} else {
			this.update(dispatch);
		}
		DocDic docDic = this.docDicService.getByDisId(id);
		if(null==docDic){
			docDic = new DocDic();
		}
		docDic.setDicId(dispatch.getDicId());
		docDic.setDisId(id);
		docDic.setDocId(dispatch.getDocId());
		this.docDicService.save(docDic);
	}

	public void sendDispatch(String receiverId, String receiverName, SysUser curUser, Long runId, String title, String type) {
		MessageSend send = new MessageSend();
		send.setSubject("发文分发");
		if ("1".equals(type)) {
			send.setSubject("收文传阅");
		}
		send.setContent("<a href=/platform/bpm/processRun/info.ht?runId=" + runId + " target='_blank'>" + title + "</a>");
		send.setContentText(title);
		send.setMessageType("1");
		try {
			msgSendService.addMessageSend(send, curUser, receiverId, receiverName, "", "");
		} catch (Exception e) {
			log.error("错误信息", e);
		}
	}

	public List<Dispatch> getAllList(Map params) {
		List<Dispatch> list = dao.getBySqlKey("getByYearAndType",params );
		return list;
	}

	public void updateDis(String runId) {
		//通过runId获取合同编号
		ProcessRun processRun = processRunDao.getById(Long.valueOf(runId));
		if(processRun.getStatus()==4){
			return;
		}
		Dispatch dispatch = dao.getById(Long.parseLong(processRun.getBusinessKey()));
	}

	/*
	 * public List<Dispatch> getRedChromatographyList(QueryFilter queryFilter) { return getAll(queryFilter); }
	 * 
	 * public List<Dispatch> checkList(QueryFilter queryFilter) { queryFilter.addFilter("checkState", 1); return getAll(queryFilter); }
	 * 
	 * public List<Dispatch> examineList(QueryFilter queryFilter) { queryFilter.addFilter("examineState", 1); return getAll(queryFilter); }
	 * 
	 * public List<Dispatch> sendList(QueryFilter queryFilter) { queryFilter.addFilter("sendState", "1"); return getAll(queryFilter); }
	 */
}
