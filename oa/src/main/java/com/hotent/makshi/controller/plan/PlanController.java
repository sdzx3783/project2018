package com.hotent.makshi.controller.plan;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.api.org.model.ISysUser;
import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.makshi.model.plan.Plan;
import com.hotent.makshi.model.plan.PlanDetail;
import com.hotent.makshi.model.plan.PlanReply;
import com.hotent.makshi.service.plan.PlanDetailService;
import com.hotent.makshi.service.plan.PlanReplyService;
import com.hotent.makshi.service.plan.PlanServices;
import com.hotent.platform.annotion.Action;

@Controller
@RequestMapping("/makshi/plan/plan/")
public class PlanController extends BaseController{
	
	@Autowired
	private PlanServices planServices;
	@Autowired
	private PlanReplyService planReplyService;
	@Autowired
	private PlanDetailService planDetailService;
	
	@RequestMapping("list")
	@Action(description = "我的计划")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ISysUser currentUser = ContextUtil.getCurrentUser();
		ModelAndView mv = getAutoView();
		QueryFilter queryFilter=new QueryFilter(request,"planItem");
		queryFilter.addFilter("cuser", currentUser.getUserId());
		List<Plan> list = planServices.getAll(queryFilter);
		for(Plan plan :list){
			List<PlanReply> replyList = planReplyService.getReplyByPId(plan.getId());
			if(replyList!=null && replyList.size()>0){
				plan.setIsReply(true);
			}else{
				plan.setIsReply(false);
			}
		}
		mv.addObject("list", list).addObject("username", currentUser.getFullname());
		return mv;
	}
	
	
	@RequestMapping("toAdd")
	@Action(description = "跳转创建计划页面")
	public ModelAndView toAdd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String returnUrl=RequestUtil.getPrePage(request);
		ModelAndView mv = new ModelAndView("/makshi/plan/planAdd.jsp");
		return mv.addObject("returnUrl", returnUrl);
	}
	
	
	@RequestMapping("save")
	@Action(description = "新建计划")
	public void save(HttpServletRequest request, HttpServletResponse response,Plan plan) throws Exception {
		String resultMsg = null;
		try {
			if(plan.getId()==null){
				Long currentUserId = ContextUtil.getCurrentUserId();
				plan.setCuser(currentUserId+"");
				plan.setCtime(new Date());
				plan.setIsdelete(0);
				planServices.add(plan);
				planDetailService.addSubList(plan);
				resultMsg = getText("添加", "计划");
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
			}else{
				plan.setUtime(new Date());
				plan.setUuser(ContextUtil.getCurrentUserId()+"");
				planServices.update(plan);
				planDetailService.updateSubList(plan);
				resultMsg = getText("更新", "计划");
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
			}
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
		}
	}
	
	@RequestMapping("edit")
	@Action(description = "获取计划")
	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String returnUrl=RequestUtil.getPrePage(request);
		Long id = RequestUtil.getLong(request, "id");
		ModelAndView mv = new ModelAndView("/makshi/plan/planEdit.jsp");
		Plan plan = planServices.getById(id);
		
		if(plan!=null){
			List<PlanDetail> detailList = planDetailService.getDetailByPId(id.intValue());
			plan.setPlanDetailList(detailList);
			List<PlanReply> replyList = planReplyService.getReplyByPId(plan.getId());
			plan.setReplyList(replyList);
		}
		return mv.addObject("plan", plan).addObject("returnUrl", returnUrl);
	}
	
	
	@RequestMapping("detail")
	@Action(description = "计划详情")
	public ModelAndView detail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String returnUrl=RequestUtil.getPrePage(request);
		Long id = RequestUtil.getLong(request, "id");
		Long type = RequestUtil.getLong(request, "type");//1为详情，2为评价
		ModelAndView mv =null;
		if(type==1){
			mv=new ModelAndView("/makshi/plan/planDetail.jsp");
		}else{
			mv=new ModelAndView("/makshi/plan/planReply.jsp");
		}
		 
		Plan plan = planServices.getById(id);
		if(plan!=null){
			List<PlanReply> replyList = planReplyService.getReplyByPId(plan.getId());
			plan.setReplyList(replyList);
			
			List<PlanDetail> detailList = planDetailService.getDetailByPId(id.intValue());
			plan.setPlanDetailList(detailList);
		}
		return mv.addObject("plan", plan).addObject("returnUrl", returnUrl).addObject("type", type);
	}
	
	
	
	@RequestMapping("delPlan")
	@Action(description = "删除计划")
	public void delPlan(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resultMsg = null;
		Long id = RequestUtil.getLong(request, "id");
		Plan plan = planServices.getById(id);
		if(plan!=null){
			plan.setIsdelete(1);
			planServices.update(plan);
		}
		resultMsg = getText("删除", "计划");
		writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
	}
	
	
	@RequestMapping("underList")
	@Action(description = "下属的计划")
	public ModelAndView underList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long currentUserId = ContextUtil.getCurrentUserId();
		ModelAndView mv = new ModelAndView("/makshi/plan/underPlanList.jsp");
		QueryFilter queryFilter=new QueryFilter(request,"planItem");
		queryFilter.addFilter("userId", currentUserId);
		List<Plan> list = planServices.getUnderPlan(queryFilter);
		for(Plan plan :list){
			List<PlanReply> replyList = planReplyService.getReplyByPId(plan.getId());
			if(replyList!=null && replyList.size()>0){
				plan.setIsReply(true);
			}else{
				plan.setIsReply(false);
			}
		}
		mv.addObject("list", list).addObject("pageBeanplanItem", queryFilter.getPageBean())// 此两项用于分页标签
		.addObject("requestURIplanItem", request.getRequestURI());
		return mv;
	}
	
	@RequestMapping("reply")
	@Action(description = "评论")
	public void reply(HttpServletRequest request, HttpServletResponse response,Plan plan) throws Exception {
		String resultMsg = null;
		int id = RequestUtil.getInt(request, "id");
		String reply = RequestUtil.getString(request, "reply");
		Long currentUserId = ContextUtil.getCurrentUserId();
		PlanReply planReply = new PlanReply(id, reply, currentUserId+"", new Date(), 0);
		planReplyService.add(planReply);
		resultMsg = getText("添加", "评论");
		writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
	}
}
