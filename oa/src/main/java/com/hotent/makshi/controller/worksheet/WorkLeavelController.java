

package com.hotent.makshi.controller.worksheet;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.bpmn20.entity.activiti.In;
import com.hotent.core.util.DateUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.makshi.model.worksheet.WorkLeave;
import com.hotent.makshi.service.worksheet.WorkCommonService;
import com.hotent.makshi.service.worksheet.WorkLeaveService;
import com.hotent.makshi.utils.Constants;
import com.hotent.makshi.utils.DateUtils;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.model.system.SysRole;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.system.SysOrgService;
import com.hotent.platform.service.system.SysRoleService;

/**
 * 对象功能:缺勤表 控制器类
 */
@Controller
@RequestMapping("/makshi/worksheet/workLeave/")
public class WorkLeavelController extends BaseController
{
	@Resource
	private WorkLeaveService workLeaveService;
	
	@Resource
	private SysOrgService sysOrgService;
	
	@Resource
	private SysRoleService roleService;
	
	@Autowired
	private WorkCommonService workCommonService;
	
	@RequestMapping("exist_vacation")
	@Action(description="是否已重复请过的假期")
	public void existVacation(HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		SysUser sysUser = (SysUser) ContextUtil.getCurrentUser();
		//查询考勤统计是否已重复请过假期
		String leave_start=request.getParameter("leave_start");
		String leave_end=request.getParameter("leave_end");
		Date leaveStartDate=DateUtils.parseDateS(leave_start);
		Date leaveEndDate=DateUtils.parseDateS(leave_end);
		Integer levea_start_select=Integer.parseInt(request.getParameter("levea_start_select"));
		Integer levea_end_select=Integer.parseInt(request.getParameter("levea_end_select"));
		
		QueryFilter queryFilter=new QueryFilter(request);
		queryFilter.getFilters().clear();
		queryFilter.addFilter("fullnameID", sysUser.getUserId());
		queryFilter.addFilter("beginleave_time", leave_start);
		queryFilter.addFilter("endleave_time", leave_end);
		List<WorkLeave> leaveList=workLeaveService.getAll(queryFilter);
		
		
		List<String> dateList= getDurDateList(leaveStartDate,leaveEndDate);
		
		//查看缺勤表
		Boolean isExist=false;
		if(leaveList!=null && leaveList.size()>0){
			for(WorkLeave leave:leaveList){
				String leaveTime=DateUtils.formatDateS(leave.getLeave_time());
				if(leave_start.equals(leave_end)){//只请一天假
					if(!(leaveTime.equals(leave_start) && levea_start_select==levea_end_select && 
							levea_start_select!=leave.getLeave_range() &&
							leave.getLeave_range() != Constants.LEAVERANGEALLDAY)){
						isExist=true;
						break;
					}
					
				}else{
					if(dateList.get(0).equals(leaveTime) ){
						if(!(levea_start_select==levea_end_select && levea_start_select==Constants.LEAVEMORNING
								&& leave.getLeave_range()!=levea_start_select && leave.getLeave_range() != Constants.LEAVERANGEALLDAY)){//非缺勤当天只缺勤下午，请假时间只请假中午
							isExist=true;
							break;
						}
					}else if(dateList.get(dateList.size()-1).equals(leaveTime)){
						if(!(levea_start_select==levea_end_select && levea_start_select==Constants.LEAVEAFTERNOON
								&& leave.getLeave_range()!=levea_start_select && leave.getLeave_range() != Constants.LEAVERANGEALLDAY)){//非请假时间只请到中午，缺勤只缺下午
							isExist=true;
							break;
						}
					}else{
						isExist=true;
						break;
					}
				}
				
			}
			if(isExist){//存在记录数据
				writeResultMessage(response.getWriter(),"",ResultMessage.Fail);
				return;
			}
		}
		//查看请假流程表
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("durJudge", true);
		params.put("fullnameID", sysUser.getUserId());
		params.put("startTimeBegin", leave_start);
		params.put("endTimeEnd", leave_end);
		List<Map<String,Object>> processDataList=workLeaveService.geProcesstVacation(params);
		if(processDataList!=null && processDataList.size()>0){
			for(Map<String,Object> dataMap:processDataList){
				String startTime=dataMap.get("startTime").toString();
				String endTime=dataMap.get("endTime").toString();
				String leaveStartSolt=dataMap.get("leaveStartRange").toString();
				String leaveEndSolt=dataMap.get("leaveEndRange").toString();
				
			/*	if(leave_start.equals(leave_end)){//只请一天假
					if(!((leave_end.equals(startTime) && levea_end_select!=Integer.parseInt(leaveStartSolt))
							|| (endTime.equals(startTime) && levea_end_select!=Integer.parseInt(leaveStartSolt)))){
						isExist=true;
						break;
					}
					
				}else{*/
						if(!(leave_end.equals(startTime) && levea_end_select!=Integer.parseInt(leaveStartSolt)
								&& levea_end_select==Constants.LEAVEAFTERNOON)){//当前请假的数据在已存在请假数据的前面
							isExist=true;
							break;
						}
						
						if(!(endTime.equals(leave_start) && Integer.parseInt(leaveEndSolt)!=levea_start_select 
								&& levea_start_select==Constants.LEAVEAFTERNOON)){//当前请假的数据在已存在请假数据的后面
							isExist=true;
							break;
						}
//				}
			}
			
			if(isExist){//存在记录数据
				writeResultMessage(response.getWriter(),"",ResultMessage.Fail);
				return;
			}
		}
		
		writeResultMessage(response.getWriter(),"",ResultMessage.Success);
	}
	
	/**
	 * 获取请假范围内的日期list
	 * @param leaveStartDate
	 * @param leaveEndDate
	 * @return
	 */
	public List<String> getDurDateList(Date leaveStartDate,Date leaveEndDate){
		int day=DateUtils.getDiffDaysByDay(leaveStartDate, leaveEndDate);
		List<String> list=new ArrayList<String>();
		String startDateStr=DateUtils.formatDateS(leaveStartDate);
		for(int i=0 ;i<=day;i++){
			String curDate=DateUtils.getDateAddDay(startDateStr.replaceAll("-", ""), i);
			curDate=DateUtils.transDate(curDate);
			list.add(curDate);
		}
		return list;
	}
	
	/**
	 * 添加或更新缺勤表。
	 * @param request
	 * @param response
	 * @param workLevel 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新缺勤表")
	public void save(HttpServletRequest request, HttpServletResponse response,WorkLeave workLeave) throws Exception
	{
		String resultMsg=null;	
		String leave_start=request.getParameter("leave_start");
		String leave_end=request.getParameter("leave_end");
		workLeave.setCreate_time(new Date());
		
		//当前用户包含组织
		List<SysOrg> sysOrgs = sysOrgService.getOrgsByUserId(Long.parseLong(workLeave.getFullnameID()));
		
		List<SysRole> roles=roleService.getByUserId(Long.parseLong(workLeave.getFullnameID()));
		//设置角色
		if(null!=roles && roles.size()>0){
			boolean isRoleHr=false;//是否是人事专员
			for(SysRole role:roles){
				if(role.getAlias().equals("bpm_hr_manager")){//人事管理角色
					isRoleHr=true;
					workLeave.setRoleID(role.getRoleId().toString());
					workLeave.setRolename(role.getRoleName());
				}
			}
			if(!isRoleHr){
				workLeave.setRoleID(roles.get(0).getRoleId().toString());
				workLeave.setRolename(roles.get(0).getRoleName());
			}
		}
		//设置部门
		if(sysOrgs!=null && sysOrgs.size()>0){
			workLeave.setOrgID(sysOrgs.get(0).getOrgId().toString());
			workLeave.setOrg(sysOrgs.get(0).getOrgName());
		}
		
		if(leave_start.equals(leave_end)){
			Date leaveDate=DateUtil.parseDate(leave_start);
			if(!workCommonService.isWorkDay(leaveDate)){//不能添加非工作日的缺勤记录
				writeResultMessage(response.getWriter(),leave_start+"为非工作日，不能添加缺勤记录",ResultMessage.Fail);
				return;
			}
			workLeave.setLeave_time(leaveDate);
			Integer levea_start_select=Integer.parseInt(request.getParameter("levea_start_select"));
			Integer levea_end_select=Integer.parseInt(request.getParameter("levea_end_select"));
			if(levea_start_select==Constants.LEAVERANGEARRArr[0]
					&& levea_end_select==Constants.LEAVERANGEARRArr[0]){//缺勤上午
				workLeave.setLeave_range(Constants.LEAVEMORNING);
			}else if(levea_start_select==Constants.LEAVERANGEARRArr[1]
					&& levea_end_select==Constants.LEAVERANGEARRArr[1]){//缺勤下午
				workLeave.setLeave_range(Constants.LEAVEAFTERNOON);
			}else if(levea_start_select==Constants.LEAVERANGEARRArr[0]
					&& levea_end_select==Constants.LEAVERANGEARRArr[1]){//缺勤一天
				workLeave.setLeave_range(Constants.LEAVERANGEALLDAY);
			}
			
			try{
				
				workLeaveService.save(workLeave);
				resultMsg=getText("添加","缺勤表");
				writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
			}catch(Exception e){
				writeResultMessage(response.getWriter(),"已添加过"+leave_start+"的缺勤记录",ResultMessage.Fail);
			}
		}else{//添加的缺勤范围
			Date leaveStartDate=DateUtils.parseDateS(leave_start);
			Date leaveEndDate=DateUtils.parseDateS(leave_end);
			//判断两个日期相差的时间
			int day=DateUtils.getDiffDaysByDay(leaveStartDate, leaveEndDate);
			boolean isError=false;
			String dateAddRptMsg="已添加过缺勤记录的日期:";
			boolean isAdd=false;//是否有添加数据
			Integer levea_start_select=Integer.parseInt(request.getParameter("levea_start_select"));
			Integer levea_end_select=Integer.parseInt(request.getParameter("levea_end_select"));
			StringBuffer notWorkDay=new StringBuffer("非工作日:");
			for(int i=0;i<=day;i++){
				if(i==0){
					if(levea_start_select==Constants.LEAVERANGEARRArr[0]){//上午就开始请假，则算为请假一天
						workLeave.setLeave_range(Constants.LEAVERANGEALLDAY);
					}else if(levea_start_select==Constants.LEAVERANGEARRArr[1]){//下午开始请假，请假下午
						workLeave.setLeave_range(Constants.LEAVEAFTERNOON);
					}
				}else if(i==day){
					if(levea_end_select==Constants.LEAVERANGEARRArr[0]){//结束上午请假，则只请到上午的半天假期
						workLeave.setLeave_range(Constants.LEAVEMORNING);
					}else if(levea_end_select==Constants.LEAVERANGEARRArr[1]){//下午才结束请假，则请假全天
						workLeave.setLeave_range(Constants.LEAVERANGEALLDAY);
					}
				}else{
					workLeave.setLeave_range(Constants.LEAVERANGEALLDAY);//设置默认缺勤范围
				}
				Date curDate=DateUtils.getDateAddDay(leaveStartDate, i);
				//判断是否是工作日
				if(!workCommonService.isWorkDay(curDate)){
					notWorkDay.append(DateUtils.formatDateS(curDate)+",");
					continue;
				}
				
				workLeave.setId(null);
				workLeave.setLeave_time(curDate);
				try{
					workLeaveService.save(workLeave);
					isAdd=true;
					resultMsg=getText("添加","缺勤表");
				}catch(Exception e){
					dateAddRptMsg+=DateUtils.formatDateS(curDate)+",";
					isError=true;
				}
			}
			if(isError){
				StringBuffer errorMsg=new StringBuffer();
				if(StringUtils.isNotEmpty(notWorkDay.toString())){
					errorMsg.append(notWorkDay.toString().substring(0,notWorkDay.length()-1)).append(";");
				}
				if(StringUtils.isNotEmpty(dateAddRptMsg)){
					errorMsg.append(dateAddRptMsg.toString());
				}
				if(!errorMsg.equals("")){
					errorMsg.append("不能添加缺勤记录");
				}
				if(isAdd){
					errorMsg.append(",其他日期添加缺勤记录成功");
				}
				writeResultMessage(response.getWriter(),errorMsg.toString(),ResultMessage.Fail);
			}else{
				writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
			}
		}
	}
	
	
	/**
	 * 取得缺勤表分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看缺勤表分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
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
		
		QueryFilter query=new QueryFilter(request,"workLeaveItem");
		if(alias!=null && !alias.equals("bpm_hr_manager")){
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
		List<WorkLeave> list=workLeaveService.getAll(query);
		ModelAndView mv=this.getAutoView().addObject("workLeaveList",list).addObject("alias",alias);
		return mv;
	}
	
	/**
	 * 删除缺勤表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除缺勤表")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"id");
			workLeaveService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除缺勤表成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑缺勤表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑缺勤表")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		
		String returnUrl=RequestUtil.getPrePage(request);
		WorkLeave workLevel=workLeaveService.getById(id);
		
		return getAutoView().addObject("workLevel",workLevel)
							.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得缺勤表明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看缺勤表明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		WorkLeave workLevel=workLeaveService.getById(id);
		return getAutoView().addObject("workLevel", workLevel);
	}
	
}