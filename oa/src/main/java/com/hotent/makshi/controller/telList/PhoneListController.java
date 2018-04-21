

package com.hotent.makshi.controller.telList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.makshi.model.common.WChangeHistory;
import com.hotent.makshi.model.telList.PhoneList;
import com.hotent.makshi.service.common.ChangeHistoryService;
import com.hotent.makshi.service.telList.PhoneListService;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.annotion.ActionExecOrder;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.system.SysOrgService;
import com.hotent.platform.service.system.SysUserService;
/**
 * 对象功能:手机号码列表 控制器类
 */
@Controller
@RequestMapping("/makshi/telList/phoneList/")
public class PhoneListController extends BaseController
{
	@Resource
	private PhoneListService phoneListService;
	@Resource
	private ChangeHistoryService changeHistoryService;
	
	@Resource
	private SysOrgService sysOrgService;
	
	@Resource
	private SysUserService sysUserService;
	/**
	 * 添加或更新手机号码列表。
	 * @param request
	 * @param response
	 * @param phoneList 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新手机号码列表")
	public void save(HttpServletRequest request, HttpServletResponse response,PhoneList phoneList) throws Exception
	{
		String resultMsg=null;		
		try{
			if(phoneList.getId()==null){
				phoneListService.save(phoneList);
				resultMsg=getText("添加","手机号码列表");
			}else{
			    phoneListService.save(phoneList);
				resultMsg=getText("更新","手机号码列表");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得手机号码列表分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看手机号码列表分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<PhoneList> list=phoneListService.getAll(new QueryFilter(request,"phoneListItem"));
		ModelAndView mv=this.getAutoView().addObject("phoneListList",list);
		return mv;
	}
	
	@RequestMapping("rzlc_phone")
	@Action(description="查看手机号码列表分页列表")
	public void saveRzlcPhone(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		QueryFilter filter=new QueryFilter(request);
		filter.addFilter("userIdIsNull", true);
		List<PhoneList> list=phoneListService.getAll(filter);
		String resultMsg="";
		if(null!=list && list.size()>0){
			JSONObject json = JSONObject.fromObject(list.get(0));
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("PhoneList", list.get(0));
			map.put("queryType", "PhoneList");
			map.put("queryCondition", list.get(0).getId()+"");
			request.getSession().setAttribute("historyData", map);
			resultMsg=json.toString();
		}
		writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
	}
	
	
	@RequestMapping("rzlc_phone_save")
	@Action(description="查看手机号码列表分页列表")
	public void listJson(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		Long id=RequestUtil.getLong(request,"id");
		String account=request.getParameter("account").toString();
		if(id==0){
			writeResultMessage(response.getWriter(),"",ResultMessage.Success);
		}else{
			PhoneList phoneList=phoneListService.getById(id);
			if(phoneList!=null && phoneList.getUser_id()!=null 
					&& !phoneList.getUser_id().equals("")){
				writeResultMessage(response.getWriter(),"",ResultMessage.Fail);
			}else{
				//手机表插入对应用户
				SysUser sysUser = sysUserService.getByAccount(account);
				List<SysOrg> sysOrgs = sysOrgService.getOrgsByUserId(sysUser.getUserId());//获取组织
				phoneList.setUser_id(sysUser.getUserId()+"");
				phoneList.setUser_name(sysUser.getFullname());
				//设置部门
				if(sysOrgs!=null && sysOrgs.size()>0){
					phoneList.setDepartmentID(sysOrgs.get(0).getOrgId().toString());
					phoneList.setDepartment(sysOrgs.get(0).getOrgName());
				}
				Integer state=0;
				if(sysUser.getUserStatus()!=null && sysUser.getUserStatus().equals("离职")){
					state=1;
				}
				phoneList.setState(state+"");
				phoneListService.save(phoneList);
				
				writeResultMessage(response.getWriter(),"",ResultMessage.Success);
			}
		}
	}
	
	
	/**
	 * 删除手机号码列表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除手机号码列表")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"id");
			phoneListService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除手机号码列表成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑手机号码列表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑手机号码列表")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		
		String returnUrl=RequestUtil.getPrePage(request);
		PhoneList phoneList=phoneListService.getById(id);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("PhoneList", phoneList);
		map.put("queryType", "PhoneList");
		map.put("queryCondition", id+"");
		request.getSession().setAttribute("historyData", map);
		
		return getAutoView().addObject("phoneList",phoneList)
							.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得变更历史
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("historyList")
	public ModelAndView getChangeHisByUserId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("/makshi/telList/telListHistoryList.jsp");
		mv = getChangeHisView(request, response, mv, 1);
		return mv;
	}
	/**
	 * 取得变更历史
	 * 
	 * @param request
	 * @param response
	 * @param isOtherLink
	 * @return
	 * @throws Exception
	 */
	@Action(description = "用户变更历史", execOrder = ActionExecOrder.AFTER, detail = "用户变更历史", exectype = "管理日志")
	public ModelAndView getChangeHisView(HttpServletRequest request, HttpServletResponse response, ModelAndView mv, int isOtherLink) throws Exception {
		Long id=RequestUtil.getLong(request,"id");
		long canReturn = RequestUtil.getLong(request, "canReturn", 0);
		//查看的类型
		String openType = RequestUtil.getString(request, "openType", "");
		//获取基本信息
		PhoneList phoneList=phoneListService.getById(id);
		List<WChangeHistory> changeHisList=null;
		if (phoneList != null) {
			changeHisList = changeHistoryService.getListByType("PhoneList", id+"");
		}
		
		String returnUrl = RequestUtil.getPrePage(request);
		return mv.addObject("phoneList", phoneList).addObject("canReturn", canReturn).addObject("returnUrl", returnUrl).addObject("isOtherLink", isOtherLink).addObject("openType", openType).addObject("changeHisList", changeHisList);
	}

	
	
	/**
	 * 取得手机号码列表明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看手机号码列表明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		PhoneList phoneList=phoneListService.getById(id);
		return getAutoView().addObject("phoneList", phoneList);
	}
	
}