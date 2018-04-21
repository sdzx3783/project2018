

package com.hotent.makshi.controller.worksheet;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.makshi.model.worksheet.WorkSheet;
import com.hotent.makshi.service.worksheet.WorkCommonService;
import com.hotent.makshi.service.worksheet.WorkSheetService;
import com.hotent.makshi.utils.DateUtils;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.model.system.SysRole;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.system.SysOrgService;
import com.hotent.platform.service.system.SysRoleService;
/**
 * 对象功能:考勤记录 控制器类
 */
@Controller
@RequestMapping("/makshi/worksheet/workSheet/")
public class WorkSheetController extends BaseController
{
	@Resource
	private WorkSheetService workSheetService;
	
	@Resource
	private SysOrgService sysOrgService;
	
	@Resource
	private SysRoleService roleService;
	
	@Autowired
	private WorkCommonService workCommonService;
	
	@RequestMapping("dakaCk")
	@Action(description="检查是否可以打卡")
	public void isNeedClock(HttpServletRequest request, HttpServletResponse response){
		SysUser sysUser = (SysUser) ContextUtil.getCurrentUser();
		QueryFilter query=new QueryFilter(request,"workSheetItem");
		String clockDate=DateUtils.getFormatCurrentTime("yyyy-MM-dd");
		
		//非工作日不能签到
		if(!workCommonService.isWorkDay(new Date())){
			try {
				writeResultMessage(response.getWriter(),"no",ResultMessage.Fail);
			} catch (IOException e) {}
			return;
		}
		
		//当前已签到不能再签到
		query.addFilter("fullnameID", sysUser.getUserId());
		query.addFilter("clock_time", clockDate);
		List<WorkSheet> list=workSheetService.getAll(query);
		if(null==list || list.size()==0){
			try {
				writeResultMessage(response.getWriter(),"ok",ResultMessage.Success);
			} catch (IOException e) {}
		}else{
			try {
				writeResultMessage(response.getWriter(),"no",ResultMessage.Fail);
			} catch (IOException e) {}
		}
	}
	
	/**
	 * 添加或更新考勤记录。
	 * @param request
	 * @param response
	 * @param workSheet 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新考勤记录")
	public void save(HttpServletRequest request, HttpServletResponse response,WorkSheet workSheet) throws Exception
	{
		SysUser sysUser = (SysUser) ContextUtil.getCurrentUser();
		String daka=request.getParameter("daka");
		//设置入库时间
		workSheet.setCreate_time(new Date());
		if(null==daka || !daka.equals("daka")){
			Date date=workSheet.getClock_time();
			if(!workCommonService.isWorkDay(date)){//非工作日不能补录
				writeResultMessage(response.getWriter(),"非工作日不能补录",ResultMessage.Fail);
				return;
			}
			
			workSheet.setWeek(DateUtils.getWeekDay(date));//设置星期
			
			//当前用户包含组织
			List<SysOrg> sysOrgs = sysOrgService.getOrgsByUserId(Long.parseLong(workSheet.getFullnameID()));
			
			//设置角色
			List<SysRole> roles=roleService.getByUserId(Long.parseLong(workSheet.getFullnameID()));
			if(null!=roles && roles.size()>0){
				boolean isRoleHr=false;//是否是人事专员
				for(SysRole role:roles){
					if(role.getAlias().equals("bpm_hr_manager")){//人事管理角色
						isRoleHr=true;
						workSheet.setRoleID(role.getRoleId().toString());
						workSheet.setRolename(role.getRoleName());
					}
				}
				if(!isRoleHr){
					workSheet.setRoleID(roles.get(0).getRoleId().toString());
					workSheet.setRolename(roles.get(0).getRoleName());
				}
			}
			//设置部门
			if(sysOrgs!=null && sysOrgs.size()>0){
				workSheet.setOrgID(sysOrgs.get(0).getOrgId().toString());
				workSheet.setOrg(sysOrgs.get(0).getOrgName());
			}
			workSheet.setType("补录");
			//修改或入库
			String resultMsg=null;		
			try{
				if(workSheet.getId()==null){
					workSheetService.save(workSheet);
					resultMsg=getText("添加","考勤补录");
				}else{
					workSheetService.save(workSheet);
					resultMsg=getText("更新","考勤补录");
				}
				writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
			}catch(Exception e){
				writeResultMessage(response.getWriter(),"已补录过"+DateUtils.formatDateS(workSheet.getClock_time())+"的缺勤记录",ResultMessage.Fail);
			}
		}else{
			if(null!=sysUser && null!=sysUser.getUserId()){
				Date date=new Date();
				if(!workCommonService.isWorkDay(date)){//非工作日不能签到
					writeResultMessage(response.getWriter(),"非工作日不能签到",ResultMessage.Fail);
					return;
				}
				workSheet.setFullname(sysUser.getFullname());
				workSheet.setFullnameID(sysUser.getUserId().toString());
				
				//当前用户组织
				SysOrg curSysOrg = (SysOrg) ContextUtil.getCurrentOrg();
				if(curSysOrg!=null){
					workSheet.setOrg(curSysOrg.getOrgName());
					workSheet.setOrgID(curSysOrg.getOrgId()==null?null:curSysOrg.getOrgId().toString());
				}
				workSheet.setClock_time(date);
				
				//设置角色
				List<SysRole> roles=roleService.getByUserId(sysUser.getUserId());
				if(null!=roles && roles.size()>0){
					boolean isRoleHr=false;//是否是人事专员
					for(SysRole role:roles){
						if(role.getAlias().equals("bpm_hr_manager")){//人事管理角色
							isRoleHr=true;
							workSheet.setRoleID(role.getRoleId().toString());
							workSheet.setRolename(role.getRoleName());
						}
					}
					if(!isRoleHr){
						workSheet.setRoleID(roles.get(0).getRoleId().toString());
						workSheet.setRolename(roles.get(0).getRoleName());
					}
				}
				//设置星期
				workSheet.setWeek(DateUtils.getWeekDay(new Date()));
				
				workSheet.setType("签到");
				String resultMsg=null;		
				try{
					if(workSheet.getId()==null){
						workSheetService.save(workSheet);
						resultMsg=getText("添加","考勤记录");
					}else{
						workSheetService.save(workSheet);
						resultMsg=getText("更新","考勤记录");
					}
					writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
				}catch(Exception e){
					writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
				}
			}
		}
	}
	
	
	/**
	 * 取得考勤记录分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看考勤记录分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		String type=request.getParameter("ty");
		SysUser sysUser = (SysUser) ContextUtil.getCurrentUser();
		//设置角色
		List<SysRole> roles=roleService.getByUserId(sysUser.getUserId());
		String alias=null;
		if(null!=roles && roles.size()>0){
			for(SysRole role:roles){
				if(role.getAlias().equals("bpm_hr_manager")){
					alias=role.getAlias();
				}
			}
			if(null==alias){
				alias=roles.get(0).getAlias();
			}
		}
		
		QueryFilter query=new QueryFilter(request,"workSheetItem");
		if((null!=type && type.equals("kq")) || (alias!=null && !alias.equals("bpm_hr_manager"))){
			query.addFilter("fullnameID", sysUser.getUserId());
		}
		//创建时间结束日期的处理
		if(query!=null && query.getFilters().containsKey("endcreate_time")){
			Date endCreateTime=(Date)query.getFilters().get("endcreate_time");
			String endCreateTimeStr=DateUtils.formatDateS(endCreateTime);
			endCreateTimeStr+=" 23:59:59";
			endCreateTime=DateUtils.parseDateL(endCreateTimeStr);
			query.getFilters().put("endcreate_time", endCreateTime);
		}
		
		List<WorkSheet> list=workSheetService.getAll(query);
		ModelAndView mv=this.getAutoView().addObject("workSheetList",list)
				.addObject("alias",alias==null?null:alias).addObject("ty", type);
		return mv;
	}
	
	/**
	 * 删除考勤记录
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除考勤记录")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"id");
			workSheetService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除考勤记录成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑考勤记录
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑考勤记录")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		
		String returnUrl=RequestUtil.getPrePage(request);
		WorkSheet workSheet=workSheetService.getById(id);
		
		return getAutoView().addObject("workSheet",workSheet)
							.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得考勤记录明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看考勤记录明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		WorkSheet workSheet=workSheetService.getById(id);
		return getAutoView().addObject("workSheet", workSheet);
	}
	
}