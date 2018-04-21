
package com.hotent.makshi.controller.dispatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.apache.commons.httpclient.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.api.util.PropertyUtil;
import com.hotent.core.engine.GroovyScriptEngine;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.makshi.model.dispatch.Dic;
import com.hotent.makshi.model.dispatch.Dispatch;
import com.hotent.makshi.model.dispatch.DocDic;
import com.hotent.makshi.model.doc.Doc;
import com.hotent.makshi.model.receipt.ReceiptAll;
import com.hotent.makshi.service.dispatch.DicService;
import com.hotent.makshi.service.dispatch.DispatchService;
import com.hotent.makshi.service.dispatch.DocDicService;
import com.hotent.makshi.service.doc.DocService;
import com.hotent.makshi.service.receipt.ReceiptAllService;
import com.hotent.makshi.utils.DateUtils;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.model.bpm.BpmDefinition;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.model.bpm.TaskOpinion;
import com.hotent.platform.model.bpm.TaskRead;
import com.hotent.platform.model.system.Dictionary;
import com.hotent.platform.model.system.SysProperty;
import com.hotent.platform.model.system.SysRole;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.model.util.WarningSetting;
import com.hotent.platform.service.bpm.BpmDefinitionService;
import com.hotent.platform.service.bpm.BpmService;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.platform.service.bpm.TaskOpinionService;
import com.hotent.platform.service.bpm.TaskReminderService;
import com.hotent.platform.service.form.TaskReadService;
import com.hotent.platform.service.system.DictionaryService;
import com.hotent.platform.service.system.MessageReceiverService;
import com.hotent.platform.service.system.SysRoleService;

/**
 * 对象功能:发文总表 控制器类
 */
@Controller
@RequestMapping("/makshi/dispatch/dispatch/")
public class DispatchController extends BaseController {
	@Resource
	private DispatchService dispatchService;
	@Resource
	private GroovyScriptEngine engine;
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private TaskReadService taskReadService;
	@Resource
	private BpmDefinitionService bpmDefinitionService;
	@Resource
	private BpmService bpmService;
	@Resource
	private TaskReminderService reminderService;
	@Resource
	TaskOpinionService taskOpinionService;
	@Resource
	private MessageReceiverService receiverService;
	@Resource
	private ReceiptAllService receiptAllService;
	private Integer myDraft = 4;// 我的草稿类型
	@Resource
	private DocService docService;
	@Resource
	private DictionaryService dictionaryService;
	@Resource
	private DocDicService docDicService;
	@Resource
	private SysRoleService sysRoleService;
	@Resource
	private DicService dicService;
	
	/**
	 * 添加或更新发文总表。
	 * 
	 * @param request
	 * @param response
	 * @param dispatch 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description = "添加或更新发文总表")
	public void save(HttpServletRequest request, HttpServletResponse response, Dispatch dispatch) throws Exception {
		String resultMsg = null;
		try {
			if (dispatch.getId() == null) {
				dispatchService.save(dispatch);
				resultMsg = getText("添加", "发文总表");
			} else {
				String typeId = request.getParameter("mdispatchtype_id");
				dispatch.setType(typeId);
				dispatchService.save(dispatch);
				resultMsg = getText("更新", "发文总表");
			}
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
		}
	}

	/**
	 * 保存次级目录
	 * @param request
	 * @param response
	 * @param dispatch
	 * @throws Exception
	 */
	@RequestMapping("saveDic")
	@Action(description = "添加或更新发文总表")
	public void saveDic(HttpServletRequest request, HttpServletResponse response, Dic dic) throws Exception {
		String resultMsg = null;
		try {
			if (dic.getDicId() == null) {
				dicService.save(dic);
				resultMsg = getText("添加", "");
			} else {
				dicService.save(dic);
				resultMsg = getText("更新", "");
			}
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得发文总表分页列表
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description = "查看发文总表分页列表")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		long orgId = 7;
		long supId = 7;
		List<Doc> docListTemp = docService.getDocsByOrg(orgId, supId);
		//判断是否是公文管理员
		boolean editor = isEditor();
		ModelAndView mv = this.getAutoView().addObject("docList", docListTemp).addObject("orgId", orgId).addObject("supId", supId).addObject("canCreate",editor);
		return mv;

	}
	
	@RequestMapping("docList")
	@Action(description = "查看发文总表分页列表")
	public ModelAndView docList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		long docId = RequestUtil.getLong(request, "docId");
		String returnUrl = RequestUtil.getPrePage(request);
		boolean editor = isEditor();
		//获取发文分类
		List<Dic> list = dicService.getByDocId(docId);
		return getAutoView().addObject("dicList", list).addObject("returnUrl", returnUrl).addObject("canCreate",editor).addObject("docId",docId);
	}
	
	@RequestMapping("getListByYear")
	@Action(description = "查看发文总表分页列表")
	public ModelAndView getListByYear(HttpServletRequest request, HttpServletResponse response) throws Exception {
	//	QueryFilter queryFilter = new QueryFilter(request, "dispatchItem");
		String docId = RequestUtil.getString(request, "docId");
		String dicId = RequestUtil.getString(request, "dicId");
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("docId", docId);
		params.put("dicId", dicId);
		List<Dispatch> dispatchList = dispatchService.getAllList(params);
		//判断是否是公文管理员
		boolean editor = isEditor();
		ModelAndView mv = this.getAutoView().addObject("dispatchList", dispatchList).addObject("params", params).addObject("canCreate",editor);
				//.addObject("docId", docId);
		return mv;

	}

	private boolean isEditor() {
		String alias = "bpm_doc_manager";
		Long userId = ContextUtil.getCurrentUserId();
		if(userId==1L){
			return true;
		}
		List<SysRole> roleList = sysRoleService.getByUserId(userId);
		for (SysRole role : roleList) {
			if (role.getAlias().equals(alias)) {
				return true;
			}
		}
		return false;
	}
	

	/**
	 * 编辑文档目录
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("editDoc")
	@Action(description = "编辑文档目录")
	public ModelAndView editDoc(HttpServletRequest request) throws Exception {
		Long id = RequestUtil.getLong(request, "id");
		String year = RequestUtil.getString(request, "year");
		String returnUrl = RequestUtil.getPrePage(request);
		//获取发文分类
		List<Doc> docList = new ArrayList<Doc>();
		List<Dictionary> list = dictionaryService.getByTypeId(10000006140011L, true);
		for(Dictionary dictionary:list){
			Doc doc = new Doc();
			doc.setDocname(dictionary.getItemName());
			doc.setDocid(dictionary.getDicId());
			docList.add(doc);
		}
		//获取现有文档
		
	//	if(StringUtil.isNotEmpty(year)){
	//		docList = docService.getDisDoc(Integer.valueOf(year));
	//	}
	    //doc = docService.getById(id);
		return getAutoView().addObject("docList", docList).addObject("returnUrl", returnUrl).addObject("cutYear", DateUtils.getDefinedYear(0, "yyyy"));
	}
	
	/**
	 * 编辑文档目录
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("editDic")
	@Action(description = "编辑文档目录")
	public ModelAndView addDic(HttpServletRequest request) throws Exception {
		Long id = RequestUtil.getLong(request, "docId");
		String returnUrl = RequestUtil.getPrePage(request);
		List<Dictionary> list = dictionaryService.getByTypeId(10000006140011L, false);
		return getAutoView().addObject("returnUrl", returnUrl).addObject("docId", id).addObject("dicList", list);
	}
	
	/**
	 * 发文待办
	 */
	@RequestMapping("pendingMattersList")
	public ModelAndView pendingMattersList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryFilter filter = new QueryFilter(request, "taskItem");
		filter.addFilter("processName", "发文");
		String nodePath = RequestUtil.getString(request, "nodePath");
		if (StringUtils.isNotEmpty(nodePath))
			filter.getFilters().put("nodePath", nodePath + "%");
		List<TaskEntity> list = bpmService.getMyTasks(filter);
		Map<Integer, WarningSetting> warningSetMap = reminderService.getWaringSetMap();
		// 是否有全局流水号
		boolean hasGlobalFlowNo = PropertyUtil.getBooleanByAlias(SysProperty.GlobalFlowNo);
		return getAutoView().addObject("taskList", list).addObject("warningSet", warningSetMap).addObject("hasGlobalFlowNo", hasGlobalFlowNo);

	}

	/**
	 * 发文经办
	 */
	@RequestMapping("alreadyMattersList")
	@Action(description = "查看已办事宜流程列表")
	public ModelAndView alreadyMattersList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryFilter filter = new QueryFilter(request, "processRunItem");
		filter.addFilter("processName", "发文");
		String nodePath = RequestUtil.getString(request, "nodePath");// 右侧流程分类
		if (StringUtils.isNotEmpty(nodePath))
			filter.getFilters().put("nodePath", nodePath + "%");
		filter.addFilter("assignee", ContextUtil.getCurrentUserId().toString());// 用户id
		List<ProcessRun> list = processRunService.getAlreadyMattersList(filter);
		for (ProcessRun processRun : list) {
			if (processRun.getStatus().shortValue() != ProcessRun.STATUS_FINISH.shortValue()) {
				// 1.查找当前用户是否有最新审批的任务
				TaskOpinion taskOpinion = taskOpinionService.getLatestUserOpinion(processRun.getActInstId(), ContextUtil.getCurrentUserId());
				if (BeanUtils.isNotEmpty(taskOpinion))
					processRun.setRecover(ProcessRun.STATUS_RECOVER);
			}
		}
		// 是否有全局流水号
		boolean hasGlobalFlowNo = PropertyUtil.getBooleanByAlias(SysProperty.GlobalFlowNo);
		return this.getAutoView().addObject("processRunList", list).addObject("hasGlobalFlowNo", hasGlobalFlowNo);
	}

	/**
	 * 获取发文列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("myList")
	@Action(description = "查看发文总表分页列表")
	public ModelAndView mylist(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 获取所有我的发文
		QueryFilter filter = new QueryFilter(request, "processRunItem");
		filter.addFilter("processName", "发文");
		filter.addFilter("exceptStatus", myDraft);
		filter.addFilter("creatorId", ContextUtil.getCurrentUserId());
		List<ProcessRun> list = processRunService.getAll(filter);
		// 编辑ProcessRun对象
		if (BeanUtils.isNotEmpty(list)) {
			for (ProcessRun processRun : list) {
				processRun.setIsRead(false);
				// 获取businessKey，通过businessKey获取申请数据
				String businessKey = processRun.getBusinessKey();
				Dispatch dis = dispatchService.getBygetBusinessKey(Long.valueOf(businessKey));
				// 设置流程定义名称为发文字号
				if (null != dis && !("").equals(dis)) {
					processRun.setProcessName(dis.getDispatch_id());
					processRun.setSubject(dis.getTitle());
				}
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

		ModelAndView mv = this.getAutoView().addObject("processRunList", list);

		return mv;
	}


	/**
	 * 删除发文总表
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description = "删除发文总表")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
			//processRunService.delByIds(lAryId);
		    dispatchService.delByIds(lAryId);
			message = new ResultMessage(ResultMessage.Success, "删除成功!");
		} catch (Exception ex) {
			message = new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 删除文件夹
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("delDocList")
	public ModelAndView delDocList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		long orgId = 7;
		long supId = 7;
		List<Doc> docListTemp = docService.getDocsByOrg(orgId, supId);
		ModelAndView mv = this.getAutoView().addObject("docList", docListTemp);
		return mv;
	}
	@RequestMapping("delDoc")
	@ResponseBody
	public Map<String, String> delDoc(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long id = RequestUtil.getLong(request, "id");
		Map<String,String> result = new HashMap<String,String>();
		try {
			docService.delById(id);
			result.put("success", "true");
			result.put("message", "删除成功!");
		} catch (Exception ex) {
			result.put("success", "false");
			result.put("message", "删除失败!");
		}
		return result;
	}
	@RequestMapping("delDicList")
	public ModelAndView delDicList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long docId = RequestUtil.getLong(request, "docId");
		List<Dic> list = dicService.getByDocId(docId);
		ModelAndView mv = this.getAutoView().addObject("dicList", list);
		return mv;
	}
	@RequestMapping("delDic")
	@ResponseBody
	public Map<String, String> delDic(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long id = RequestUtil.getLong(request, "id");
		Map<String,String> result = new HashMap<String,String>();
		try {
			dicService.delById(id);
			result.put("success", "true");
			result.put("message", "删除成功!");
		} catch (Exception ex) {
			result.put("success", "false");
			result.put("message", "删除失败!");
		}
		return result;
	}
	/**
	 * 编辑发文总表
	 * 
	 * @param request0
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description = "编辑发文总表")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		Long id = RequestUtil.getLong(request, "id");
		Long docId = RequestUtil.getLong(request, "docId");
		Long dicId = RequestUtil.getLong(request, "dicId");
		String returnUrl = RequestUtil.getPrePage(request);
		Dispatch dispatch = dispatchService.getById(id);
		DocDic docDic = docDicService.getByDisId(id);
		Dic dic = dicService.getById(dicId);
		if(null==docDic){
			docDic = new DocDic();
			docDic.setDicId(dicId);
			docDic.setDocId(docId);
		}
		return getAutoView().addObject("dispatch", dispatch).addObject("returnUrl", returnUrl).addObject("docDic", docDic).addObject("dic", dic);
	}

	/**
	 * 跟新数据库
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("updateMysqlDis")
	@Action(description = "编辑发文总表")
	public void updateMysqlDis(HttpServletRequest request) throws Exception {
		List<Dispatch> disList = dispatchService.getAll();
		Map<String,Object> param = new HashMap<String,Object>();
		for(Dispatch dispatch : disList){
			Doc doc = docService.getbyDocName(DateUtil.formatDate(dispatch.getTime(),"yyyy"));
			if(null==doc){
				throw new RuntimeException("未找到文件夹"+ DateUtil.formatDate(dispatch.getTime(),"yyyy"));
			}
			param.put("docId", doc.getDocid());
			param.put("dicName", dispatch.getType());
			List<Dic> dicList = dicService.getByDocIdAndDicName(param);
			if(dicList.size()==0){
				throw new RuntimeException(doc.getDocname()+"目录未找到"+ dispatch.getType()+"子目录");
			}
			DocDic docDic = new DocDic();
			docDic.setDicId(dicList.get(0).getDicId());
			docDic.setDocId(doc.getDocid());
			docDic.setDisId(dispatch.getId());
			docDicService.save(docDic);
		}
	}

	
	@RequestMapping("sendEdit")
	@Action(description = "编辑发文总表")
	public ModelAndView sendEdit(HttpServletRequest request) throws Exception {
		Long id = RequestUtil.getLong(request, "id");

		Long runId = 0L;
		ProcessRun processRun = processRunService.getByBusinessKey(id.toString());
		if (BeanUtils.isNotEmpty(processRun)) {
			runId = processRun.getRunId();
		}
		String returnUrl = RequestUtil.getPrePage(request);
		Dispatch dispatch = dispatchService.getById(id);
		return getAutoView().addObject("dispatch", dispatch).addObject("runId", runId).addObject("returnUrl", returnUrl);
	}

	/**
	 * 取得发文总表明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description = "查看发文总表明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long id = RequestUtil.getLong(request, "id");
		Dispatch dispatch = dispatchService.getById(id);
		return getAutoView().addObject("dispatch", dispatch);
	}

	/**
	 * 流程url表单 绑定的表单明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("detail")
	@Action(description = "表单明细")
	public ModelAndView detail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long id = RequestUtil.getLong(request, "id");
		Dispatch dispatch = dispatchService.getById(id);
		return getAutoView().addObject("dispatch", dispatch);
	}

	/**
	 * 流程url表单 绑定的表单编辑页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("modify")
	public ModelAndView modify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long id = RequestUtil.getLong(request, "id");
		Dispatch dispatch = dispatchService.getById(id);
		return getAutoView().addObject("dispatch", dispatch);
	}


	@RequestMapping("getMyTodoTask")
	@Action(description = "查看发文总表任务分页列表")
	public ModelAndView getMyTodoTask(HttpServletRequest request, HttpServletResponse response) throws Exception {
		SysUser sysUser = (SysUser) ContextUtil.getCurrentUser();
		Long userId = sysUser.getUserId();
		List<Dispatch> list = dispatchService.getMyTodoTask(userId, new QueryFilter(request, "dispatchItem"));
		ModelAndView mv = this.getAutoView().addObject("dispatchList", list);
		return mv;
	}

	@RequestMapping("getMyDraft")
	@Action(description = "查看发文总表草稿")
	public ModelAndView getMyDraft(HttpServletRequest request, HttpServletResponse response) throws Exception {
		SysUser sysUser = (SysUser) ContextUtil.getCurrentUser();
		Long userId = sysUser.getUserId();
		List<Dispatch> list = dispatchService.getMyDraft(userId, new QueryFilter(request, "dispatchItem"));
		ModelAndView mv = this.getAutoView().addObject("dispatchList", list);
		return mv;
	}

	@RequestMapping("getMyEnd")
	@Action(description = "查看我结束的发文总表实例")
	public ModelAndView getMyEnd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		SysUser sysUser = (SysUser) ContextUtil.getCurrentUser();
		Long userId = sysUser.getUserId();
		List<Dispatch> list = dispatchService.getMyEnd(userId, new QueryFilter(request, "dispatchItem"));
		ModelAndView mv = this.getAutoView().addObject("dispatchList", list);
		return mv;
	}

	@RequestMapping("setSendPerson")
	public ModelAndView setSendPerson(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long runId = RequestUtil.getLong(request, "runId");
		String type = RequestUtil.getString(request, "type");
		Long businessKey = RequestUtil.getLong(request, "businessKey");
		String title = "";
		String sendType = "";
		if ("dispatch".equals(type)) {
			Dispatch dispatch = dispatchService.getById(businessKey);
			title = dispatch.getTitle();
		}
		if ("receipt".equals(type)) {
			ReceiptAll receipt = receiptAllService.getById(businessKey);
			title = receipt.getTitle();
			sendType = "1";
		}
		ModelAndView mv = this.getAutoView().addObject("runId", runId).addObject("title", title).addObject("sendType", sendType);
		return mv;
	}

	@RequestMapping("sendToPerson")
	public void SendToPerson(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resultMsg = null;
		String receiverId = request.getParameter("receiptPersonID");
		String receiverName = request.getParameter("receiptPerson");
		String runId = request.getParameter("runId");
		String title = request.getParameter("title");
		String type = request.getParameter("type");
		SysUser curUser = (SysUser) ContextUtil.getCurrentUser();
		try {
			dispatchService.sendDispatch(receiverId, receiverName, curUser, Long.valueOf(runId), title, type);
			resultMsg = getText("发送", "发文总表");
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
		}
	}
}