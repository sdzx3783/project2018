

package com.hotent.makshi.controller.waterprotectpark;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.api.org.model.ISysOrg;
import com.hotent.core.api.org.model.ISysUser;
import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.makshi.model.waterprotectpark.EquipmentMaintenanceApplication;
import com.hotent.makshi.service.waterprotectpark.EquipmentMaintenanceApplicationService;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.model.system.SysRole;
import com.hotent.platform.service.system.SysRoleService;
/**
 * 对象功能:设施设备维修申请(水保园流程) 控制器类
 */
@Controller
@RequestMapping("/makshi/waterprotectpark/equipmentMaintenanceApplication/")
public class EquipmentMaintenanceApplicationController extends BaseController
{
	@Resource
	private EquipmentMaintenanceApplicationService equipmentMaintenanceApplicationService;
	@Resource
	private SysRoleService sysRoleService;
	/**
	 * 添加或更新设施设备维修申请(水保园流程)。
	 * @param request
	 * @param response
	 * @param equipmentMaintenanceApplication 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新设施设备维修申请(水保园流程)")
	public void save(HttpServletRequest request, HttpServletResponse response,EquipmentMaintenanceApplication equipmentMaintenanceApplication) throws Exception
	{
		String resultMsg=null;		
		try{
			if(equipmentMaintenanceApplication.getId()==null){
				equipmentMaintenanceApplication.setFrom(1);
				equipmentMaintenanceApplicationService.save(equipmentMaintenanceApplication);
				resultMsg=getText("添加","设施设备维修申请(水保园流程)");
			}else{
			    equipmentMaintenanceApplicationService.save(equipmentMaintenanceApplication);
				resultMsg=getText("更新","设施设备维修申请(水保园流程)");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得设施设备维修申请(水保园流程)分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看设施设备维修申请(水保园流程)分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<EquipmentMaintenanceApplication> list=equipmentMaintenanceApplicationService.getAll(new QueryFilter(request,"equipmentMaintenanceApplicationItem"));
		ModelAndView mv=this.getAutoView().addObject("equipmentMaintenanceApplicationList",list);
		
		Long currentUserId = ContextUtil.getCurrentUserId();
		Boolean hasAccess=false;
		if(currentUserId==1l){
			hasAccess=true;
		}else{
			List<SysRole> roles = sysRoleService.getByUserId(currentUserId);
			for (SysRole sysRole : roles) {
				if((sysRole.getRoleId()+"").equals("10000013290007")){//设备管理员——水保部
					hasAccess=true;
					break;
				}
			}
		}
		return mv.addObject("hasAccess", hasAccess);
	}
	
	/**
	 * 删除设施设备维修申请(水保园流程)
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除设施设备维修申请(水保园流程)")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"id");
			equipmentMaintenanceApplicationService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除设施设备维修申请(水保园流程)成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑设施设备维修申请(水保园流程)
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑设施设备维修申请(水保园流程)")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		
		String returnUrl=RequestUtil.getPrePage(request);
		EquipmentMaintenanceApplication equipmentMaintenanceApplication=equipmentMaintenanceApplicationService.getById(id);
		ISysUser currentUser = ContextUtil.getCurrentUser();
		if(currentUser!=null){
			if(equipmentMaintenanceApplication==null){
				equipmentMaintenanceApplication=new EquipmentMaintenanceApplication();
			}
			equipmentMaintenanceApplication.setApplicant(currentUser.getFullname());
			equipmentMaintenanceApplication.setApplicantID(currentUser.getUserId()+"");
			equipmentMaintenanceApplication.setApplicationTime(new Date());
			ISysOrg currentOrg = ContextUtil.getCurrentOrg();
			if(currentOrg!=null){
				equipmentMaintenanceApplication.setOrg(currentOrg.getOrgName());
				equipmentMaintenanceApplication.setOrgID(currentOrg.getOrgId()+"");
			}
		}
		return getAutoView().addObject("equipmentMaintenanceApplication",equipmentMaintenanceApplication)
							.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得设施设备维修申请(水保园流程)明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看设施设备维修申请(水保园流程)明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		EquipmentMaintenanceApplication equipmentMaintenanceApplication=equipmentMaintenanceApplicationService.getById(id);
		return getAutoView().addObject("equipmentMaintenanceApplication", equipmentMaintenanceApplication);
	}
	
}