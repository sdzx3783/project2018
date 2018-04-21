

package com.hotent.makshi.controller.inhouseregistration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.makshi.model.common.WChangeHistory;
import com.hotent.makshi.model.inhouseregistration.InHouseRegistration;
import com.hotent.makshi.model.inuserinfo.UserInfo;
import com.hotent.makshi.service.common.ChangeHistoryService;
import com.hotent.makshi.service.inhouseregistration.InHouseRegistrationService;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.annotion.ActionExecOrder;
/**
 * 对象功能:租房入住人员登记表 控制器类
 */
@Controller
@RequestMapping("/makshi/inhouseregistration/inHouseRegistration/")
public class InHouseRegistrationController extends BaseController
{
	@Resource
	private InHouseRegistrationService inHouseRegistrationService;
	@Resource
	private ChangeHistoryService changeHistoryService;
	
	/**
	 * 添加或更新租房入住人员登记表。
	 * @param request
	 * @param response
	 * @param inHouseRegistration 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新租房入住人员登记表")
	public void save(HttpServletRequest request, HttpServletResponse response,InHouseRegistration inHouseRegistration) throws Exception
	{
		String resultMsg=null;		
		try{
			if(inHouseRegistration.getId()==null){
				inHouseRegistrationService.save(inHouseRegistration);			
				resultMsg=getText("添加","租房入住人员登记表");
			}else{
			    inHouseRegistrationService.save(inHouseRegistration);
				resultMsg=getText("更新","租房入住人员登记表");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得租房入住人员登记表分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看租房入住人员登记表分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		HashMap<String,Object> param = new HashMap<String,Object>();
		String begininDate = request.getParameter("begininDate");
		String endleftDate = request.getParameter("endleftDate");
		String houseId = request.getParameter("houseId");
		String userName = request.getParameter("userName");
		if(null!=houseId&&!("".equals(houseId))){
			param.put("houseId", houseId);
		}
		if(null!=userName&&!("".equals(userName))){
			param.put("userName", userName);
		}
		if(null!=begininDate&&!("".equals(begininDate))){
			param.put("begininDate", begininDate);
		}
		if(null!=endleftDate&&!("".equals(endleftDate))){
			param.put("endleftDate", endleftDate);
		}
		if(param.size()>0){
			List<UserInfo> userInfoList = inHouseRegistrationService.getUserInfoListByParam(param);
			ModelAndView mv=this.getAutoView().addObject("inHouseRegistrationList",userInfoList);
			return mv;
		}
		List<InHouseRegistration> list=inHouseRegistrationService.getAll(new QueryFilter(request,"inHouseRegistrationItem"));
		List<UserInfo> userInfoList  = new ArrayList<UserInfo>();
		if(CollectionUtils.isNotEmpty(list)){
			for(InHouseRegistration inHouseRegistration:list){
				String house_id = inHouseRegistration.getHouse_id();
				List<UserInfo> userList = inHouseRegistrationService.getUserInfoList(inHouseRegistration.getId());
				if(userList.size()>0){
					for(UserInfo userInfo:userList){
						userInfo.sethouse_id(house_id);
						userInfoList.add(userInfo);
					}
				}
				//inHouseRegistration.setUserInfoList(userList);
			}
		}
		ModelAndView mv=this.getAutoView().addObject("inHouseRegistrationList",userInfoList);
		return mv;
	}
	
	/**
	 * 删除租房入住人员登记表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除租房入住人员登记表")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"id");
			inHouseRegistrationService.delAll(lAryId);
			message=new ResultMessage(ResultMessage.Success,"删除租房入住人员登记表成功!");
			/*inHouseRegistrationService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除租房入住人员登记表成功!");*/
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑租房入住人员登记表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑租房入住人员登记表")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		
		String returnUrl=RequestUtil.getPrePage(request);
		InHouseRegistration inHouseRegistration=inHouseRegistrationService.getById(id);
		List<UserInfo> userInfoList=inHouseRegistrationService.getUserInfoList(id);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("UserInfo", userInfoList);
		map.put("queryType", "UserInfo");
		map.put("queryCondition", id+"");
		request.getSession().setAttribute("historyData", map);
		
		
		return getAutoView().addObject("inHouseRegistration",inHouseRegistration)
							.addObject("userInfoList",userInfoList)
							.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得租房入住人员登记表明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看租房入住人员登记表明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		InHouseRegistration inHouseRegistration=inHouseRegistrationService.getById(id);
		List<UserInfo> userInfoList=inHouseRegistrationService.getUserInfoList(id);
		return getAutoView().addObject("inHouseRegistration",inHouseRegistration)
							.addObject("userInfoList",userInfoList);
	}
	
	/**
	 * 取得以入住租房编号
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("houseIdList")
	@ResponseBody
	public String getHouseIdList(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String houseId = request.getParameter("houseId");
		QueryFilter filter = new QueryFilter(request);
		filter.addFilter("house_id", houseId);
		List<InHouseRegistration> list = inHouseRegistrationService.getAll(filter);
		if (CollectionUtils.isEmpty(list)){
			return "ok";
		}
		for (InHouseRegistration inHouseRegistration : list) {
			List<UserInfo> userList = inHouseRegistrationService.getUserInfoList(inHouseRegistration.getId());
			if (CollectionUtils.isEmpty(userList))
				return "ok";
		}
		return "no";
							
	}
	/**
	 * 取得租房入住人员登记表变更历史
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("historyList")
	public ModelAndView getChangeHisByUserId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("/makshi/renthouseregistration/renthouseRegistrationHistoryList.jsp");
		mv = getChangeHisView(request, response, mv, 1);
		return mv;
	}
	@Action(description = "用户变更历史", execOrder = ActionExecOrder.AFTER, detail = "用户变更历史", exectype = "管理日志")
	public ModelAndView getChangeHisView(HttpServletRequest request, HttpServletResponse response, ModelAndView mv, int isOtherLink) throws Exception {
		Long id=RequestUtil.getLong(request,"id");
		long canReturn = RequestUtil.getLong(request, "canReturn", 0);
		//查看的类型
		String openType = RequestUtil.getString(request, "openType", "");
		//获取基本信息
		InHouseRegistration inHouseRegistration=inHouseRegistrationService.getById(id);
		List<WChangeHistory> changeHisList=null;
		if (inHouseRegistration != null) {
			changeHisList = changeHistoryService.getListByType("UserInfo", id+"");
		}
		
		String returnUrl = RequestUtil.getPrePage(request);
		return mv.addObject("assetRegistration", inHouseRegistration).addObject("canReturn", canReturn).addObject("returnUrl", returnUrl).addObject("isOtherLink", isOtherLink).addObject("openType", openType).addObject("changeHisList", changeHisList);
	}

}