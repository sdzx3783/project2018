package com.hotent.platform.controller.system;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.log.SysAuditThreadLocalHolder;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.annotion.ActionExecOrder;
import com.hotent.platform.model.system.OrgAuth;
import com.hotent.platform.model.system.Position;
import com.hotent.platform.model.system.SysAuditModelType;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.model.system.SysUserOrg;
import com.hotent.platform.model.system.SysUserOrgPos;
import com.hotent.platform.model.system.UserPosition;
import com.hotent.platform.service.system.OrgAuthService;
import com.hotent.platform.service.system.PositionService;
import com.hotent.platform.service.system.SysOrgService;
import com.hotent.platform.service.system.SysUserOrgService;
import com.hotent.platform.service.system.UserPositionService;

/**
 * 对象功能:用户所属组织或部门 控制器类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:pkq
 * 创建时间:2011-12-07 18:23:24
 */
@Controller
@RequestMapping("/platform/system/sysUserOrg/")
@Action(ownermodel=SysAuditModelType.USER_MANAGEMENT)
public class SysUserOrgController extends BaseController
{
	@Resource
	private SysUserOrgService sysUserOrgService;
	@Resource
	private SysOrgService sysOrgService;
	@Resource
	private UserPositionService userPositionService;
	@Resource
	private OrgAuthService orgAuthService;
	/**
	 * 取得用户所属组织或部门分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看用户所属组织或部门分页列表",detail="查看用户所属组织或部门分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<SysUserOrg> list=new ArrayList<SysUserOrg>();
		List<UserPosition> upList=sysUserOrgService.getAll(new QueryFilter(request,"sysUserOrgItem"));
		for(UserPosition up:upList){
			list.add(up);
		}
		ModelAndView mv=this.getAutoView().addObject("sysUserOrgList",list);
		return mv;
	}
	
	/**
	 * 删除用户所属组织或部门
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除用户所属组织或部门")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		Long[] lAryId =RequestUtil.getLongAryByStr(request, "userPosId");
		sysUserOrgService.delByIds(lAryId);
		
		response.sendRedirect(preUrl);
	}

	@RequestMapping("edit")
	@Action(description="编辑用户所属组织或部门")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long userOrgId=RequestUtil.getLong(request,"userOrgId");
		String returnUrl=RequestUtil.getPrePage(request);
		SysUserOrg sysUserOrg=null;
		if(userOrgId!=null){
			 sysUserOrg= sysUserOrgService.getById(userOrgId);
		}else{
			sysUserOrg=new SysUserOrg();
		}
		return getAutoView().addObject("sysUserOrg",sysUserOrg).addObject("returnUrl", returnUrl);
	}

	/**
	 * 取得用户所属组织或部门明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看用户所属组织或部门明细",detail="查看用户所属组织或部门明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long id=RequestUtil.getLong(request,"userOrgId");
		SysUserOrg sysUserOrg = sysUserOrgService.getById(id);		
		return getAutoView().addObject("sysUserOrg", sysUserOrg);
	}
	
	/**
	 * 取得组织下的所有用户
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("userList")
	@Action(description="取得用户列表",detail="取得用户列表")
	public ModelAndView userList(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv=this.getAutoView();
		mv.addObject("action", "global");
		return getUserByOrgId(request,mv);
	}
	/**
	 * 
	 * @author hjx
	 * @version 创建时间：2013-12-3  下午2:11:49
	 * @param request
	 * @param mv
	 * @return
	 */
	private ModelAndView getUserByOrgId(HttpServletRequest request,ModelAndView mv) {
		Long orgId = RequestUtil.getLong(request, "orgId");
		SysOrg sysOrg = sysOrgService.getById(orgId);

		List<UserPosition> list = userPositionService.getAll(new QueryFilter(request, "userPositionItem"));

		mv.addObject("userPositionList", list)
		  .addObject("orgId", orgId)
		  .addObject("sysOrg", sysOrg);
		return mv;
	}


	
	@Deprecated
	private String getPositionNames(List<Position> list){
		String positionNames = "";
		for(Position pos : list){
			if(pos.getIsPrimary()!=null&&pos.getIsPrimary()==1){
				positionNames = pos.getPosName() + "(<B>主</B>)," + positionNames;
			}
			else {
				positionNames = positionNames + pos.getPosName() +",";
			}
		}
		positionNames = positionNames.substring(0, positionNames.length()-1);
		return positionNames;
	}
	
	
	
	@RequestMapping("getUserListByOrgId")
	@ResponseBody
	@Action(description="根据组织ID取得用户List",detail="根据组织ID取得用户List")
	public List<SysUserOrg> getUserListByOrgId(HttpServletRequest request){
		String orgId=RequestUtil.getString(request, "orgId");
		return sysUserOrgService.getUserByOrgIds(orgId);
	}
	
	@RequestMapping("userGradeList")
	@Action(description="取得用户列表")
	public ModelAndView userGradeList(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv=new ModelAndView();
		Long authId = RequestUtil.getLong(request, "authId", 0);
		OrgAuth orgAuth = orgAuthService.getById(authId);
		//获取组织ID
		mv.addObject("orgAuth", orgAuth);
		mv.addObject("action", "grade");
		mv.setViewName("/platform/system/sysUserOrgUserList.jsp");
		return getUserByOrgId(request,mv); 
	}
	

	
	
	/**
	 * 设置是否主岗位。
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("setIsPrimary")
	@Action(description="设置主岗位",execOrder=ActionExecOrder.AFTER,
	detail="<#assign entity=SysAuditLinkService.getByUserPosId(Long.valueOf(userPosId))/>" +
			"设置人员：【${entity.userName}】的主岗位为组织【${entity.orgName}】 <#if isSuccess>成功<#else>失败</#if>")
	public void setIsPrimary(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		Long userPosId=RequestUtil.getLong(request, "userPosId",0);
		ResultMessage message=null;
		String preUrl= RequestUtil.getPrePage(request);
		boolean issuccess=true;
		try{
			sysUserOrgService.setIsPrimary(userPosId);
			message=new ResultMessage(ResultMessage.Success, "设置组织成功");
		}
		catch(Exception ex){
			issuccess=false;
			message=new ResultMessage(ResultMessage.Fail, "设置组织失败");
		}
		SysAuditThreadLocalHolder.putParamerter("isSuccess", issuccess);
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 设置是主管。
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("setIsCharge")
	@Action(description="设置是主管",execOrder=ActionExecOrder.AFTER,
	detail="<#assign entity=SysAuditLinkService.getByUserPosId(Long.valueOf(userPosId))/>" +
			"设置人员：【${entity.userName}】,为组织【${entity.orgName}】的主管  <#if isSuccess>成功<#else>失败</#if>")
	public void setIsCharge(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String preUrl= RequestUtil.getPrePage(request);
		Long userPosId=RequestUtil.getLong(request, "userPosId",0);
		ResultMessage message=null;
		boolean issuccess=true;
		try{
			sysUserOrgService.setIsCharge(userPosId);
			message=new ResultMessage(ResultMessage.Success, "设置主管成功");
		}
		catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "设置主管失败");
			issuccess=false;
		}
		SysAuditThreadLocalHolder.putParamerter("isSuccess", issuccess);
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	


}
