

package com.hotent.makshi.controller.assetregistration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.makshi.model.assetregistration.AssetRegistration;
import com.hotent.makshi.model.common.WChangeHistory;
import com.hotent.makshi.service.assetapplicationall.AssetApplicationAllService;
import com.hotent.makshi.service.assetregistration.AssetRegistrationService;
import com.hotent.makshi.service.common.ChangeHistoryService;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.annotion.ActionExecOrder;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.system.SysOrgService;
import com.hotent.platform.service.system.SysUserService;
/**
 * 对象功能:资产登记表 控制器类
 */
@Controller
@RequestMapping("/makshi/assetregistration/assetRegistration/")
public class AssetRegistrationController extends BaseController
{
	@Resource
	private AssetRegistrationService assetRegistrationService;
	
	@Resource
	private ChangeHistoryService changeHistoryService;
	
	@Resource
	private AssetApplicationAllService assetApplicationAllService;
	
	@Resource
	private SysOrgService sysOrgService;
	
	@Resource
	private SysUserService sysUserService;
	
	/**
	 * 添加或更新资产登记表。
	 * @param request
	 * @param response
	 * @param assetRegistration 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新资产登记表")
	public void save(HttpServletRequest request, HttpServletResponse response,AssetRegistration assetRegistration) throws Exception
	{
		String resultMsg=null;		
		try{
			String type = assetRegistration.getAsset_type().substring(1,5);
			if(null==type||("").equals(type)){
				type = assetRegistration.getAsset_id().substring(0,4);
			}
			assetRegistration.setAsset_type(type);
			String asset_code =  assetApplicationAllService.getTypeName(type).get(0).getAssetType();
			assetRegistration.setAsset_code(asset_code);
			if(assetRegistration.getId()==null){
				assetRegistrationService.save(assetRegistration);
				resultMsg=getText("添加","资产登记表");
			}else{
			    assetRegistrationService.save(assetRegistration);
				resultMsg=getText("更新","资产登记表");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 手工报废
	 */
	@RequestMapping("abandomentRecord")
	@Action(description="添加或更新资产登记表")
	public void abandoment(HttpServletRequest request, HttpServletResponse response,AssetRegistration assetRegistration) throws Exception
	{
		String resultMsg=null;		
		try{
		//	    assetRegistrationService.addAbandoment(assetRegistration);
				resultMsg=getText("更新","资产登记表");
				writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	/**
	 * 取得资产登记表分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看资产登记表分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		QueryFilter fiter = new QueryFilter(request,"assetRegistrationItem");
		Long orgId = (Long) request.getSession().getAttribute("chooseOrgId");
		HttpSession session=request.getSession(); 
		boolean isEditor = false;
		if(orgId!=null&&orgId!=-1L){
			SysOrg sysOrg = sysOrgService.getById(orgId);
		if(null!=sysOrg){
			Long orgType = sysOrg.getOrgType();
			//添加限制条件,只能看当前组织的记录
			if(orgType!=null){
				String orgName = sysOrg.getOrgName();
				session.setAttribute("orgName",orgName);
				fiter.addFilter("use_department", orgName);
			}
		}
		}else{
			isEditor = true;
			session.removeAttribute("orgName");
			session.removeAttribute("chooseOrgId");
		}
		List<AssetRegistration> list=assetRegistrationService.getAll(fiter);
		ModelAndView mv=this.getAutoView().addObject("assetRegistrationList",list).addObject("isEditor",isEditor);
		return mv;
	}
	
	/**
	 * 查询个人名下保管资产
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
/*	public ModelAndView myAssetlist(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		Long id=RequestUtil.getLong(request,"id");
	//	List<AssetRegistration> list=assetRegistrationService.getMyAssetList(id);
	//	ModelAndView mv=this.getAutoView().addObject("assetRegistrationList",list);
		return mv;
	}*/
	
	/**
	 * 生成资产编号
	 */
	@RequestMapping("getAssetId")
	@ResponseBody
	@Action(description="生成资产编号")
	public String getAssetId(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		String assetType = request.getParameter("assetType");
		String lastAssetId =null;
		String assetId = null;
		//判断是否拥有该类型
		List<AssetRegistration> list = assetRegistrationService.getAllAssetType();
		List<String> typeList = new ArrayList<String>();
		for(AssetRegistration assetRegistration : list){
			if(null!=assetRegistration&&null!=assetRegistration.getAsset_type())
			typeList.add(assetRegistration.getAsset_type());
		}
		if(typeList.contains(assetType)){
			lastAssetId = assetRegistrationService.getLastAssetId(assetType);
			Integer temId = Integer.valueOf(lastAssetId)+1;
			assetId = "0"+String.valueOf(temId);
		}
		if(lastAssetId==null){
			assetId = assetType+"0000";
		}
		return assetId;
	}
/*	@RequestMapping("historyList")
	//@Action(description="查看资产登记表分页列表")
	public ModelAndView historyList(HttpServletRequest request) throws Exception
	{	
		Long id=RequestUtil.getLong(request,"id");
		List<AssetRegistration> list=assetRegistrationService.getHistoryList(id);
		ModelAndView mv=this.getAutoView().addObject("assetRegistrationList",list);
		return mv;
	}*/
	
	/**
	 * 删除资产登记表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除资产")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"id");
			assetRegistrationService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除资产成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑资产登记表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑资产登记表")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		Map<String, Object> map = new HashMap<String, Object>();

		String returnUrl=RequestUtil.getPrePage(request);
		AssetRegistration assetRegistration=assetRegistrationService.getById(id);
/*		AssetRegistration assetRegistrationHis=assetRegistrationService.getById(id);
		String state = assetRegistration.getState();
		if(("0").equals(state)){
			assetRegistrationHis.setState("在用");
		}
		if(("1").equals(state)){
			assetRegistrationHis.setState("报废");
		}*/
		map.put("assetRegistration", assetRegistration);
		map.put("queryType", "AssetRegistration");
		map.put("queryCondition", id+"");
		request.getSession().setAttribute("historyData", map);
		
		return getAutoView().addObject("assetRegistration",assetRegistration)
							.addObject("returnUrl",returnUrl);
	}
	
	/**
	 * 取得资产变更历史
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("historyList")
	public ModelAndView getChangeHisByUserId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("/makshi/assetregistration/assetRegistrationHistoryList.jsp");
		mv = getChangeHisView(request, response, mv, 1);
		return mv;
	}
	/**
	 * 取得资产变更历史
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
		AssetRegistration assetRegistration=assetRegistrationService.getById(id);
		List<WChangeHistory> changeHisList=null;
		if (assetRegistration != null) {
			changeHisList = changeHistoryService.getListByType("AssetRegistration", id+"");
		}
		
		String returnUrl = RequestUtil.getPrePage(request);
		return mv.addObject("assetRegistration", assetRegistration).addObject("canReturn", canReturn).addObject("returnUrl", returnUrl).addObject("isOtherLink", isOtherLink).addObject("openType", openType).addObject("changeHisList", changeHisList);
	}

	
	
	@RequestMapping("transfer")
	@Action(description="编辑资产登记表")
	public ModelAndView transfer(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		
		String returnUrl=RequestUtil.getPrePage(request);
		AssetRegistration assetRegistration=assetRegistrationService.getById(id);
		
		return getAutoView().addObject("assetRegistration",assetRegistration)
							.addObject("returnUrl",returnUrl);
	}
	
	
	@RequestMapping("abandoment")
	@Action(description="编辑资产登记表")
	public ModelAndView abandoment(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		
		String returnUrl=RequestUtil.getPrePage(request);
		AssetRegistration assetRegistration=assetRegistrationService.getById(id);
		
		return getAutoView().addObject("assetRegistration",assetRegistration)
							.addObject("returnUrl",returnUrl);
	}
	
	/**
	 * 取得资产登记表明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看资产登记表明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		AssetRegistration assetRegistration=assetRegistrationService.getById(id);
		return getAutoView().addObject("assetRegistration", assetRegistration);
	}
	
	@RequestMapping("testInfo")
	public void testInfo(HttpServletRequest request, HttpServletResponse response)
	{
		List<AssetRegistration> list=assetRegistrationService.getAllInfo();
		for(AssetRegistration assetRegistration:list){
			String carepersonId = assetRegistration.getCare_personID();
			if(StringUtil.isEmpty(carepersonId)){
				String careperson = assetRegistration.getCare_person();
				if(careperson.equals("刘波")||careperson.equals("张伟")){
					continue;
				}
				List<SysUser> userList = sysUserService.getByUserName(careperson);
				String userId  = null;
				if(userList.size()>1){
					for(SysUser user : userList){
						if(!("离职").equals(user.getUserStatus())){
							userId = user.getUserId().toString();
							 assetRegistration.setCare_personID(userId);
							 assetRegistrationService.update(assetRegistration);

						}
					}
				}else
				if(userList.size()==1){
					 userId = userList.get(0).getUserId().toString();
					 assetRegistration.setCare_personID(userId);
					 assetRegistrationService.update(assetRegistration);
				}
				
			}
		}
		
	/*	
		
		Set<String> nameSet = new HashSet<String>();
		Set<String> userIdSet = new HashSet<String>();
		for(AssetRegistration assetRegistration:list){
			String department = assetRegistration.getUse_department();
			String userName = assetRegistration.getCare_person();
			if(userName!=null&&userName.length()>0){
				if(userName.equals("刘波")||userName.equals("张伟")){
					continue;
				}
				userIdSet.add(userName);
			}
			if(department!=null){
				int a = department.indexOf("]");
				if(-1==a){
					a=0;
				}
				int b = department.length();
				if(0!=a){
					String newdepartment = department.substring(a+1,b);
					assetRegistrationService.setInfo(newdepartment,department);
					nameSet.add(newdepartment);
				}
				nameSet.add(department);
			}
		}
		for(String orgName:nameSet){
			
			 List<SysOrg> sysOrgList = sysOrgService.getByOrgName(orgName);
			 if(sysOrgList.size()>0){
			 assetRegistrationService.complete(orgName,sysOrgList.get(0));
			 }
		}
		for(String userId:userIdSet){
			 SysUser user = sysUserService.getByUserName(userId);
			 if(user!=null){
			 assetRegistrationService.completeUserInfo(userId,user.getUserId());
			 }
		}*/
	}
	
	/**
	 * 获取当前用户保管的资产
	 * @return
	 */
	@RequestMapping("getAssetByUserId")
	@ResponseBody
	public boolean test(){
		List<AssetRegistration> assetRegistrationList = assetRegistrationService.getMyAssetList(ContextUtil.getCurrentUserId().toString());
		if(assetRegistrationList.size()>0){
			return true;
		}
		return false;
	}
	
	
	@RequestMapping("addInfo")
	public void addInfo(HttpServletRequest request, HttpServletResponse response)
	{
		List<AssetRegistration> list=assetRegistrationService.getAllInfo();
		Map<String,Object> params = new HashMap<String,Object>();
		for(AssetRegistration assetRegistration:list){
			String departmentId = assetRegistration.getUse_departmentID();
			String userName = assetRegistration.getCare_person();
			if(departmentId!=null && userName!=null && departmentId.length()>0 && userName.length()>0){
				params.put("userName", userName);
				params.put("orgId", departmentId);
				assetRegistrationService.addInfo(params);
			}
		}
	}
	
}