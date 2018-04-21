

package com.hotent.makshi.controller.questionnaire;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.log.SysAuditThreadLocalHolder;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.CookieUitl;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.makshi.model.questionnaire.Questionnaire;
import com.hotent.makshi.model.questionnaire.QuestionnaireOption;
import com.hotent.makshi.model.questionnaire.QuestionnaireQuestion;
import com.hotent.makshi.model.questionnaire.QuestionnaireReceiver;
import com.hotent.makshi.model.questionnaire.QuestionnaireResult;
import com.hotent.makshi.service.questionnaire.QuestionnaireOptionService;
import com.hotent.makshi.service.questionnaire.QuestionnaireQuestionService;
import com.hotent.makshi.service.questionnaire.QuestionnaireReceiverService;
import com.hotent.makshi.service.questionnaire.QuestionnaireResultService;
import com.hotent.makshi.service.questionnaire.QuestionnaireService;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.model.system.MessageSend;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.model.system.UserPosition;
import com.hotent.platform.service.system.MessageSendService;
import com.hotent.platform.service.system.SysUserService;
import com.hotent.platform.service.system.UserPositionService;
/**
 * 对象功能:问卷调查 控制器类
 */
@Controller
@RequestMapping("/makshi/questionnaire/questionnaire/")
public class QuestionnaireController extends BaseController
{
	@Resource
	private QuestionnaireService questionnaireService;
	@Resource
	private QuestionnaireOptionService questionnaireOptionService;
	@Resource
	private QuestionnaireQuestionService questionnaireQuestionService;
	@Resource
	private QuestionnaireResultService questionnaireResultService;
	@Resource
	private MessageSendService messageSendService;
	@Resource
	private QuestionnaireReceiverService questionnaireReceiverService;
	@Resource
	private UserPositionService userPositionService;
	@Resource
	private SysUserService sysUserService;
	/**
	 * 添加或更新问卷调查。
	 * @param request
	 * @param response
	 * @param questionnaire 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新问卷调查")
	public void save(HttpServletRequest request, HttpServletResponse response,Questionnaire questionnaire) throws Exception
	{
		String resultMsg=null;	
		boolean del = false;
		try{
			if(questionnaire.getId()==null){
				resultMsg=getText("添加","问卷调查");
			}else{
				del = true;
				resultMsg=getText("更新","问卷调查");
			}
			SysUser curUser = (SysUser) ContextUtil.getCurrentUser();
			Long userId=curUser.getUserId();
			questionnaire.setCreater(curUser.getFullname());
			questionnaire.setCreater_id(userId+"");
			questionnaireService.save(questionnaire);
			String str = request.getParameter("detailList");
			if(del){
				questionnaireQuestionService.delByQuestionnaireId(questionnaire.getId());
				questionnaireOptionService.delByQuestionnaireId(questionnaire.getId());
				questionnaireReceiverService.delByQuestionnaireId(questionnaire.getId());
			}
			//[{"desc":["q","w","e"],"title":"1"},{"desc":["a","s","d"],"title":"2"},{"desc":["","",""],"title":""}]
			JSONArray array = JSON.parseArray(str);
			for (Object object : array) {
				JSONObject obj = JSON.parseObject(object.toString());
				if(obj.get("title")!=null && !"".equals(obj.get("title"))){
					//添加问题
					QuestionnaireQuestion question = new QuestionnaireQuestion();
					question.setTitle(obj.get("title").toString());
					question.setQuestionnaire_id(questionnaire.getId());
					if(obj.get("checkbox")!=null && !"".equals(obj.get("checkbox"))){
						question.setCheckbox(Boolean.valueOf(obj.get("checkbox")+""));
					}
					if(obj.get("max_choice")!=null && !"".equals(obj.get("max_choice"))){
						question.setMax_choice(Integer.valueOf(obj.get("max_choice")+""));
					}
					if(obj.get("required")!=null && !"".equals(obj.get("required"))){
						question.setRequired(Boolean.valueOf(obj.get("required")+""));
					}
					if(obj.get("type")!=null && !"".equals(obj.get("type"))){
						question.setType(Integer.valueOf(obj.get("type")+""));
					}
					if(!question.getCheckbox()){
						question.setRequired(true);
					}
					questionnaireQuestionService.save(question);
					if(question.getType()==0){
						JSONArray array1 = JSON.parseArray(obj.get("desc").toString());
						for (Object object2 : array1) {
							if(object2!=null && !"".equals(object2.toString().trim())){
								QuestionnaireOption option = new QuestionnaireOption();
								option.setQuestionnaire_id(questionnaire.getId());
								option.setQuestionnaire_ques_id(question.getId());
								option.setDesc(object2.toString());
								questionnaireOptionService.save(option);
							}
						}
					}
					
				}
			}
			//发送消息
			String receiverId=RequestUtil.getString(request, "receiverId");
			String receiverName=RequestUtil.getString(request, "receiverName");
			String receiverOrgId=RequestUtil.getString(request, "receiverOrgId");
			String receiverOrgName=RequestUtil.getString(request, "receiverOrgName");
			questionnaireReceiverService.addQuestionnaireReceiver(questionnaire, curUser,
					receiverId, receiverName, receiverOrgId, receiverOrgName);
			
			
			//所属组织
			List<UserPosition> userPosList = userPositionService.getOrgListByUserId(userId);
			QueryFilter query=new QueryFilter(request,"questionnaireReceiverItem");
			List<Long> ids = new ArrayList<Long>();
			ids.add(userId);
			for (UserPosition up : userPosList) {
				ids.add(up.getOrgId());
			}
			query.getFilters().put("user_ids",ids );
			query.getFilters().put("questionnaire_id",questionnaire.getId() );
			List<QuestionnaireReceiver> ls = questionnaireReceiverService.getAll(query);
			if(ls!=null && ls.size()>0){
				questionnaire.setCreater_partin(true);
				questionnaireService.update(questionnaire);
			}
			MessageSend messageSend = new MessageSend();
			messageSend.setSubject("请参与名称为:"+questionnaire.getTitle()+"问卷调查");
			messageSend.setContent(questionnaire.getDesc());
			messageSend.setMessageType("1");
			messageSendService.addMessageSend(messageSend, curUser,
					receiverId, receiverName, receiverOrgId, receiverOrgName);
			SysAuditThreadLocalHolder.putParamerter("isAdd", true);
			SysAuditThreadLocalHolder.putParamerter("mesendId", messageSend.getId().toString());
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得问卷调查分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看问卷调查分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		SysUser curUser = (SysUser) ContextUtil.getCurrentUser();
		Long userId=curUser.getUserId();
		List<Questionnaire> list = new ArrayList<Questionnaire>();
		//所属组织
		List<UserPosition> userPosList = userPositionService.getOrgListByUserId(userId);
		QueryFilter query=new QueryFilter(request,"questionnaireReceiverItem");
		List<Long> ids = new ArrayList<Long>();
		ids.add(userId);
		for (UserPosition up : userPosList) {
			ids.add(up.getOrgId());
		}
		query.getFilters().put("user_ids",ids );
		List<QuestionnaireReceiver> ls = questionnaireReceiverService.getAll(query);
		query=new QueryFilter(request,"questionnaireItem");
		if(ls!=null && ls.size()>0){
			ids = new ArrayList<Long>();
			for (QuestionnaireReceiver questionnaireReceiver : ls) {
				ids.add(questionnaireReceiver.getQuestionnaire_id());
			}
			query.getFilters().put("ids", ids);
		}
		
		query.getFilters().put("filters", "filters");
		query.getFilters().put("creater_id_or", userId);
		list=questionnaireService.getAll(query);
		List<Questionnaire> resultList = new ArrayList<Questionnaire>();
		Date date = new Date();
		for (Questionnaire q : list) {
			Integer status = null;
			if(q.getBegin_date().after(date)){
				status = 0;
			}else if(q.getEnd_date().before(date)){
				continue;
			}
			else if(q.getBegin_date().before(date) && q.getEnd_date().after(date)){
				status = 1;
			}
			q.setStatus(status);//0：未开始 1：投票中 2：本人已投 3：已结束
			resultList.add(q);
		}
		
		ModelAndView mv=this.getAutoView().addObject("questionnaireList",resultList);
		return mv;
	}
	
	
	
	/**
	 * 取得问卷调查分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("resultList")
	@Action(description="查看问卷调查结果分页列表")
	public ModelAndView resultList(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		SysUser curUser = (SysUser) ContextUtil.getCurrentUser();
		Long userId=curUser.getUserId();
		List<Questionnaire> list = new ArrayList<Questionnaire>();
		//所属组织
		List<UserPosition> userPosList = userPositionService.getOrgListByUserId(userId);
		QueryFilter query=new QueryFilter(request,"questionnaireReceiverItem");
		List<Long> ids = new ArrayList<Long>();
		ids.add(userId);
		for (UserPosition up : userPosList) {
			ids.add(up.getOrgId());
		}
		query.getFilters().put("user_ids",ids );
		List<QuestionnaireReceiver> ls = questionnaireReceiverService.getAll(query);
		query=new QueryFilter(request,"questionnaireItem");
		if(ls!=null && ls.size()>0){
			ids = new ArrayList<Long>();
			for (QuestionnaireReceiver questionnaireReceiver : ls) {
				ids.add(questionnaireReceiver.getQuestionnaire_id());
			}
			query.getFilters().put("ids", ids);
		}
		
		query.getFilters().put("filters", "filters");
		query.getFilters().put("creater_id_or", userId);
		list=questionnaireService.getAll(query);
		List<Questionnaire> resultList = new ArrayList<Questionnaire>();
		Date date = new Date();
		for (Questionnaire q : list) {
			Integer status = null;
			if(q.getEnd_date().before(date)){
				status = 3;
				q.setStatus(status);//0：未开始 1：投票中 2：本人已投 3：已结束
				resultList.add(q);
			}
			
		}
		ModelAndView mv= new ModelAndView();
		mv.setViewName("/makshi/questionnaire/questionnaireResultList.jsp");
		mv.addObject("questionnaireList",resultList);
		return mv;
	}
	
	
	private Long getDistanceHours(Date from,Date to){
		long f = from.getTime();  
		long t = to.getTime();  
		long hours = (t - f)/(1000 * 60 * 60);  
		return hours;
	}
	
	
	/**
	 * 	问卷调查投票
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("vote")
	@Action(description="问卷调查投票")
	public void vote(HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		String resultMsg=getText("投票","问卷调查");
		
		Questionnaire que = questionnaireService.getById(id);
		if(que.getLimit_cookie() !=null &&  que.getLimit_cookie() && CookieUitl.isExistByName("hasvote"+id, request)){
			resultMsg = getText("限制COOKIE","问卷调查");
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Fail);
			return;
		}
		if(que.getLimit_ip() !=null && que.getLimit_ip()){
			QueryFilter query=new QueryFilter(request,"questionnaireQuestionItem");
			query.getFilters().put("ip", request.getRemoteAddr());
			query.getFilters().put("questionnaire_id", id);
			List<QuestionnaireResult> ls = questionnaireResultService.getAll(query);
			if(ls!=null && ls.size()>0){
				resultMsg = getText("限制IP","问卷调查");
				writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Fail);
				return;
			}
		}
	
		String str = request.getParameter("detailList");
		SysUser curUser = (SysUser) ContextUtil.getCurrentUser();
		Long userId=curUser.getUserId();
		JSONArray array = JSON.parseArray(str);
		for (Object object : array) {
			JSONObject obj = JSON.parseObject(object.toString());
		
			JSONArray array1 = JSON.parseArray(obj.get("result").toString());
			for (Object object2 : array1) {
				if(object2!=null && !"".equals(object2.toString().trim())){
					QuestionnaireResult result = new QuestionnaireResult();
					result.setQuestionnaire_id(Long.valueOf(obj.get("questionnaire_id").toString()));
					result.setQuestionnaire_ques_id(Long.valueOf(obj.get("questionnaire_ques_id").toString()));
					result.setUser_id(userId+"");
					result.setUser(curUser.getFullname());
					result.setResult(object2.toString().trim());
					questionnaireResultService.save(result);
				}
			}
		}
		CookieUitl.addCookie("hasvote"+id, "hasvote", request, response);
		writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
	}
	
	/**
	 * 删除问卷调查
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除问卷调查")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"id");
			questionnaireService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除问卷调查成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑问卷调查
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑问卷调查")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
	
		String returnUrl=RequestUtil.getPrePage(request);
		Questionnaire questionnaire=questionnaireService.getById(id);
		if(questionnaire!=null){
			QueryFilter query=new QueryFilter(request,"questionnaireQuestionItem");
			query.getFilters().put("questionnaire_id", questionnaire.getId());
			List<QuestionnaireQuestion> list=questionnaireQuestionService.getAll(query);
		
			for (QuestionnaireQuestion questionnaireQuestion : list) {
				query=new QueryFilter(request,"questionnaireOptionItem");
				query.getFilters().put("questionnaire_id", questionnaire.getId());
				query.getFilters().put("questionnaire_ques_id", questionnaireQuestion.getId());
				List<QuestionnaireOption> ls=questionnaireOptionService.getAll(query);
				questionnaireQuestion.setQuestionnaireOptionList(ls);
			}
			questionnaire.setQuestionnaireQuestionList(list);
			
			query=new QueryFilter(request,"questionnaireReceiverItem");
			query.getFilters().put("questionnaire_id", questionnaire.getId());
			List<QuestionnaireReceiver> receiverLs = questionnaireReceiverService.getAll(query);
			StringBuffer perId = new StringBuffer();
			StringBuffer perName = new StringBuffer();
			StringBuffer orgId = new StringBuffer();
			StringBuffer orgName = new StringBuffer();
			for (QuestionnaireReceiver qr : receiverLs) {
				if(qr.getReceiver_type()==0){
					perId.append(qr.getUser_id()+",");
					perName.append(qr.getUser()+",");
				}else{
					orgId.append(qr.getUser_id()+",");
					orgName.append(qr.getUser()+",");
				}
			}
			if(perId.length()>0)
			questionnaire.setQuestionnaireReceiverPer(perId.toString().substring(0, perId.toString().length()-1));
			if(perName.length()>0)
			questionnaire.setQuestionnaireReceiverPerName(perName.toString().substring(0, perName.toString().length()-1));
			if(orgId.length()>0)
			questionnaire.setQuestionnaireReceiverOrg(orgId.toString().substring(0, orgId.toString().length()-1));
			if(orgName.length()>0)
			questionnaire.setQuestionnaireReceiverOrgName(orgName.toString().substring(0, orgName.toString().length()-1));
		}
		
		return getAutoView().addObject("questionnaire",questionnaire)
							.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得问卷调查明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看问卷调查明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		
		Long id=RequestUtil.getLong(request,"id");
		Questionnaire questionnaire=questionnaireService.getById(id);
		QueryFilter query=new QueryFilter(request,"questionnaireQuestionItem");
		query.getFilters().put("questionnaire_id", questionnaire.getId());
		List<QuestionnaireQuestion> list=questionnaireQuestionService.getAll(query);
//		
//		for (QuestionnaireQuestion questionnaireQuestion : list) {
//			query=new QueryFilter(request,"questionnaireOptionItem");
//			query.getFilters().put("questionnaire_id", questionnaire.getId());
//			query.getFilters().put("questionnaire_ques_id", questionnaireQuestion.getId());
//			List<QuestionnaireOption> ls=questionnaireOptionService.getAll(query);
//			questionnaireQuestion.setQuestionnaireOptionList(ls);
//		}
		NumberFormat numberFormat = NumberFormat.getInstance();  
		// 设置精确到小数点后2位  
	    numberFormat.setMaximumFractionDigits(2); 
		Boolean hasVote =false;
		for (QuestionnaireQuestion questionnaireQuestion : list) {
			if(questionnaireQuestion.getType()==0){
				query=new QueryFilter(request,"questionnaireOptionItem");
				query.getFilters().put("questionnaire_id", questionnaire.getId());
				query.getFilters().put("questionnaire_ques_id", questionnaireQuestion.getId());
				List<QuestionnaireOption> ls=questionnaireOptionService.getAll(query);
				//此问题参与投票人数
				query=new QueryFilter(request,"questionnaireResultItem");
				query.getFilters().put("questionnaire_id", questionnaire.getId());
				query.getFilters().put("questionnaire_ques_id", questionnaireQuestion.getId());
				List<QuestionnaireResult> resultList = questionnaireResultService.getAll(query);
				for (QuestionnaireOption questionnaireOption : ls) {
					if(resultList==null || resultList.size()<1){
						questionnaireOption.setCount(0);
						questionnaireOption.setPercentum(0.0);
						continue;
					}
					//此选项参与投票人数
					query=new QueryFilter(request,"questionnaireResultItem");
					query.getFilters().put("questionnaire_id", questionnaire.getId());
					query.getFilters().put("questionnaire_ques_id", questionnaireQuestion.getId());
					query.getFilters().put("result_like", questionnaireOption.getId());
					List<QuestionnaireResult> resultLs = questionnaireResultService.getAll(query);
					questionnaireOption.setCount(resultLs==null?0:resultLs.size());
					String result = numberFormat.format((float) questionnaireOption.getCount() / (float) resultList.size() * 100);  
					questionnaireOption.setPercentum(Double.valueOf(result));
				}
				questionnaireQuestion.setQuestionnaireOptionList(ls);
			}else{//问答题
				query=new QueryFilter(request,"questionnaireResultItem");
				query.getFilters().put("questionnaire_id", questionnaire.getId());
				query.getFilters().put("questionnaire_ques_id", questionnaireQuestion.getId());
				List<QuestionnaireResult> resultLs = questionnaireResultService.getAll(query);
				questionnaireQuestion.setResultList(resultLs);
				
			}
			
		}
		questionnaire.setQuestionnaireQuestionList(list);
		return getAutoView().addObject("questionnaire", questionnaire);
	}
	
	/**
	 * 问卷调查投票
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("ballot")
	@Action(description="问卷调查投票")
	public ModelAndView ballot(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		
		Long id=RequestUtil.getLong(request,"id");
		Questionnaire questionnaire=questionnaireService.getById(id);
		QueryFilter query=new QueryFilter(request,"questionnaireQuestionItem");
		query.getFilters().put("questionnaire_id", questionnaire.getId());
		List<QuestionnaireQuestion> list=questionnaireQuestionService.getAll(query);
		NumberFormat numberFormat = NumberFormat.getInstance();  
		// 设置精确到小数点后2位  
	    numberFormat.setMaximumFractionDigits(2); 
		Boolean hasVote =false;
		for (QuestionnaireQuestion questionnaireQuestion : list) {
			if(questionnaireQuestion.getType()==0){
				query=new QueryFilter(request,"questionnaireOptionItem");
				query.getFilters().put("questionnaire_id", questionnaire.getId());
				query.getFilters().put("questionnaire_ques_id", questionnaireQuestion.getId());
				List<QuestionnaireOption> ls=questionnaireOptionService.getAll(query);
				//此问题参与投票人数
				query=new QueryFilter(request,"questionnaireResultItem");
				query.getFilters().put("questionnaire_id", questionnaire.getId());
				query.getFilters().put("questionnaire_ques_id", questionnaireQuestion.getId());
				List<QuestionnaireResult> resultList = questionnaireResultService.getAll(query);
				for (QuestionnaireOption questionnaireOption : ls) {
					if(resultList==null || resultList.size()<1){
						questionnaireOption.setCount(0);
						questionnaireOption.setPercentum(0.0);
						continue;
					}
					//此选项参与投票人数
					query=new QueryFilter(request,"questionnaireResultItem");
					query.getFilters().put("questionnaire_id", questionnaire.getId());
					query.getFilters().put("questionnaire_ques_id", questionnaireQuestion.getId());
					query.getFilters().put("result_like", questionnaireOption.getId());
					List<QuestionnaireResult> resultLs = questionnaireResultService.getAll(query);
					questionnaireOption.setCount(resultLs==null?0:resultLs.size());
					String result = numberFormat.format((float) questionnaireOption.getCount() / (float) resultList.size() * 100);  
					questionnaireOption.setPercentum(Double.valueOf(result));
				}
				questionnaireQuestion.setQuestionnaireOptionList(ls);
			}else{//问答题
				query=new QueryFilter(request,"questionnaireResultItem");
				query.getFilters().put("questionnaire_id", questionnaire.getId());
				query.getFilters().put("user_id", ContextUtil.getCurrentUserId());
				query.getFilters().put("questionnaire_ques_id", questionnaireQuestion.getId());
				List<QuestionnaireResult> resultLs = questionnaireResultService.getAll(query);
				questionnaireQuestion.setResultList(resultLs);
				
			}
			
		}
		questionnaire.setQuestionnaireQuestionList(list);
		Date date = new Date();
		SysUser curUser = (SysUser) ContextUtil.getCurrentUser();
		Long userId=curUser.getUserId();
		Integer status = null;
		if(questionnaire.getBegin_date().after(date)){
			status = 0;
		}else if(questionnaire.getEnd_date().before(date)){
			status = 3;
		}else if(questionnaire.getBegin_date().before(date) 
				&& questionnaire.getEnd_date().after(date)){
			status = 1;
			query=new QueryFilter(request,"questionnaireResultItem");
			query.getFilters().put("questionnaire_id", questionnaire.getId());
			query.getFilters().put("user_id", userId);
			query.getFilters().put("orderField", "F_ctime");
			query.getFilters().put("orderSeq", "desc");
			List<QuestionnaireResult> ls = questionnaireResultService.getAll(query);
			if(ls!=null && ls.size()>0){//有投过票
				hasVote = true;
				status = 2;
//				Long hours = getDistanceHours(ls.get(0).getCtime(), date);
//				if(questionnaire.getRepeat_time()==null){//禁止重复投票
//					status = 2;
//				}else if(questionnaire.getRepeat_time()>0
//						&&  hours < questionnaire.getRepeat_time()){ //已投票时间还没到下一次允许再次投票时间
//					status = 2;
//				}
			}
		}
		questionnaire.setStatus(status);//0：未开始 1：投票中 2：本人已投 3：已结束
		return getAutoView().addObject("questionnaire", questionnaire).addObject("hasVote", hasVote);
	}
   
}