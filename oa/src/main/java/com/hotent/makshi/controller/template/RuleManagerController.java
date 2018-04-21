

package com.hotent.makshi.controller.template;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.api.org.model.ISysUser;
import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.makshi.model.template.RuleBookmark;
import com.hotent.makshi.model.template.RuleManager;
import com.hotent.makshi.service.template.RuleManagerService;
import com.hotent.platform.annotion.Action;
/**
 * 对象功能:规则管理 控制器类
 */
@Controller
@RequestMapping("/makshi/doc/rule/")
public class RuleManagerController extends BaseController
{
	@Resource
	private RuleManagerService ruleManagerService;
	
	/**
	 * 添加或更新规则管理。
	 * @param request
	 * @param response
	 * @param ruleManager 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新规则管理")
	public void save(HttpServletRequest request, HttpServletResponse response,RuleManager ruleManager) throws Exception
	{
		String resultMsg=null;		
		try{
			if(ruleManager.getId()==null){
				ISysUser currentUser = ContextUtil.getCurrentUser();
				ruleManager.setCtime(new Date());
				ruleManager.setCreator(currentUser.getFullname());
				ruleManager.setCreatorID(currentUser.getUserId());
				ruleManagerService.save(ruleManager);			
				resultMsg=getText("添加","规则管理");
			}else{
			    ruleManagerService.save(ruleManager);
				resultMsg=getText("更新","规则管理");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得规则管理分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看规则管理分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<RuleManager> list=ruleManagerService.getAll(new QueryFilter(request,"ruleManagerItem"));
		ModelAndView mv=this.getAutoView().addObject("ruleManagerList",list);
		return mv;
	}
	
	/**
	 * 删除规则管理
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除规则管理")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"id");
			ruleManagerService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success,"删除规则管理及其从表成功!");
			ruleManagerService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除规则管理成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑规则管理
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑规则管理")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		
		String returnUrl=RequestUtil.getPrePage(request);
		RuleManager ruleManager=ruleManagerService.getById(id);
		List<RuleBookmark> ruleBookmarkList=ruleManagerService.getRuleBookmarkList(id);
		
		return getAutoView().addObject("ruleManager",ruleManager)
							.addObject("ruleBookmarkList",ruleBookmarkList)
							.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得规则管理明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看规则管理明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		RuleManager ruleManager=ruleManagerService.getById(id);
		List<RuleBookmark> ruleBookmarkList=ruleManagerService.getRuleBookmarkList(id);
		return getAutoView().addObject("ruleManager",ruleManager)
							.addObject("ruleBookmarkList",ruleBookmarkList);
	}
	
}