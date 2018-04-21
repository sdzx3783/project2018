package com.hotent.platform.controller.console;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.api.util.PropertyUtil;
import com.hotent.core.engine.FreemarkEngine;
import com.hotent.core.engine.GroovyScriptEngine;
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.makshi.model.doc.Doc;
import com.hotent.makshi.model.project.ClassifyLibrary;
import com.hotent.makshi.service.doc.DocService;
import com.hotent.makshi.service.project.ClassifyLibraryService;
import com.hotent.makshi.utils.OrgResUtils;
import com.hotent.platform.model.system.MessageSend;
import com.hotent.platform.model.system.Position;
import com.hotent.platform.model.system.Resources;
import com.hotent.platform.model.system.SubSystem;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.model.system.SysRole;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.model.system.UserPosition;
import com.hotent.platform.service.system.MessageSendService;
import com.hotent.platform.service.system.PositionService;
import com.hotent.platform.service.system.ResourcesService;
import com.hotent.platform.service.system.SubSystemService;
import com.hotent.platform.service.system.SubSystemUtil;
import com.hotent.platform.service.system.SysIndexMyLayoutService;
import com.hotent.platform.service.system.SysOrgService;
import com.hotent.platform.service.system.SysRoleService;
import com.hotent.platform.service.system.UserPositionService;

import java.util.Arrays;

@Controller
@RequestMapping("/platform/console")
public class MainController extends BaseController {
	@Resource
	private SubSystemService subSystemService;
	@Resource
	private ResourcesService resourcesService;
	@Resource
	private MessageSendService msgSendService;
	@Resource
	private FreemarkEngine freemarkEngine;
	@Resource
	private SysOrgService sysOrgService;
	@Resource
	private UserPositionService userPositionService;
	@Resource
	private PositionService positionService;
	@Resource
	private SysIndexMyLayoutService sysIndexMylayoutService;
	@Resource
	private DocService docService;
	@Resource
	private ClassifyLibraryService classifyLibraryService;
	@Resource
	private SysRoleService sysRoleService;
	
	
	/**
	 * 切换组织
	 * 
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	@RequestMapping("turnToMain")
	public ModelAndView turnToMain(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		logger.debug("....................................跳转....................................");
		return this.getView("console", "turnToMain");
	}

	/**
	 * 控制台页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping("main")
	public ModelAndView main(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//当前用户
		SysUser curUser = (SysUser) ContextUtil.getCurrentUser();
		Long curUserId = curUser.getUserId();
		//当前有权限的子系统
		List<SubSystem> subSystemList = subSystemService.getByUser(curUser);
		SubSystem currentSystem = SubSystemUtil.getCurrentSystem(request);

		// 得到个人未读信息
		List<MessageSend> list = msgSendService.getNotReadMsg(curUserId);

		//当前用户包含组织
		List<SysOrg> sysOrgs = sysOrgService.getOrgsByUserId(curUserId);
		//当前用户组织
		SysOrg curSysOrg = (SysOrg) ContextUtil.getCurrentOrg();
		
		//当前用户包含岗位
		List<Position> positions = positionService.getByUserId(curUserId);
		//当前用户岗位
		Position curPosition = (Position) ContextUtil.getCurrentPos();
		

		// 取个性话设置参数并设置logo路径
		String skinStyle = ContextUtil.getCurrentUserSkin(request);
		String uiStyle=PropertyUtil.getByAlias("UI_STYLE", "default");
		// 添加内容
		String mainViewName = "extendIndex".equals(uiStyle)?"mainExtendIndex":"main";
		//前系统有系统
		if(currentSystem!=null ){
			//以前有选择系统,但是现在即不再拥用该系统的权限,重新选择系统
			if(!subSystemList.contains(currentSystem))
				mainViewName = "selectCurrSys";	

		}else{		
			//只有一个系统有权限
			if(subSystemList!=null&&subSystemList.size()==1){
				currentSystem = subSystemList.get(0);
				subSystemService.setCurrentSystem(currentSystem.getSystemId(),request, response);
			}else
				//以前没有选择系统
				mainViewName = "selectCurrSys";
		}
		if(currentSystem != null){
			String logo = StringUtil.formatParamMsg(currentSystem.getLogo(), skinStyle);
			
			currentSystem.setLogo(logo);
		}
		
		return this.getView("console",mainViewName)
			.addObject("skinStyle",skinStyle)
			.addObject("currentSystem", currentSystem)
			.addObject("currentSystemId", currentSystem == null?null:currentSystem.getSystemId())
			.addObject("subSystemList",subSystemList)
			.addObject("readMsg", list.size())
			.addObject("userId",curUserId)
			.addObject("sysOrgs",sysOrgs)
			.addObject("positions",positions)
			.addObject("curSysOrg",curSysOrg)
			.addObject("curPosition",curPosition);

	}
	
	/**
	 * 控制台页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping("index")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//当前用户
		SysUser curUser = (SysUser) ContextUtil.getCurrentUser();
		Long curUserId = curUser.getUserId();
		//当前有权限的子系统
		List<SubSystem> subSystemList = subSystemService.getByUser(curUser);
		SubSystem currentSystem = SubSystemUtil.getCurrentSystem(request);

		// 得到个人未读信息
		List<MessageSend> list = msgSendService.getNotReadMsg(curUserId);

		//当前用户包含组织
		List<SysOrg> sysOrgs = sysOrgService.getOrgsByUserId(curUserId);
		//当前用户组织
		SysOrg curSysOrg = (SysOrg) ContextUtil.getCurrentOrg();
		
		//当前用户包含岗位
		List<Position> positions = positionService.getByUserId(curUserId);
		//当前用户岗位
		Position curPosition = (Position) ContextUtil.getCurrentPos();
		

		// 取个性话设置参数并设置logo路径
		String skinStyle = ContextUtil.getCurrentUserSkin(request);
		String uiStyle=PropertyUtil.getByAlias("UI_STYLE", "default");
		// 添加内容
		String mainViewName = "extendIndex".equals(uiStyle)?"mainExtendIndex":"main";
		//前系统有系统
		if(currentSystem!=null ){
			//以前有选择系统,但是现在即不再拥用该系统的权限,重新选择系统
			if(!subSystemList.contains(currentSystem))
				mainViewName = "selectCurrSys";	

		}else{		
			//只有一个系统有权限
			if(subSystemList!=null&&subSystemList.size()==1){
				currentSystem = subSystemList.get(0);
				subSystemService.setCurrentSystem(currentSystem.getSystemId(),request, response);
			}else
				//以前没有选择系统
				mainViewName = "selectCurrSys";
		}
		if(currentSystem != null){
			String logo = StringUtil.formatParamMsg(currentSystem.getLogo(), skinStyle);
			
			currentSystem.setLogo(logo);
		}
		
		return this.getView("console",mainViewName)
			.addObject("skinStyle",skinStyle)
			.addObject("currentSystem", currentSystem)
			.addObject("currentSystemId", currentSystem == null?null:currentSystem.getSystemId())
			.addObject("subSystemList",subSystemList)
			.addObject("readMsg", list.size())
			.addObject("userId",curUserId)
			.addObject("sysOrgs",sysOrgs)
			.addObject("positions",positions)
			.addObject("curSysOrg",curSysOrg)
			.addObject("curPosition",curPosition);

	}

	/**
	 * 控制台页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("home")
	public ModelAndView home(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long userId = ContextUtil.getCurrentUserId();
		String html = sysIndexMylayoutService.obtainMyIndexData(userId);

		return this.getView("console", "home").addObject("html",html);
	}

	/**
	 * 获取桌面栏目对应模块的更多信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getResourceNode")
	@ResponseBody
	public Resources getResourceNode(HttpServletRequest request,
			HttpServletResponse response) {
		Resources resource = null;
		try {
			String columnUrl = RequestUtil.getString(request, "columnUrl");
			resource = resourcesService.getByUrl(columnUrl);
		} catch (Exception e) {
			return null;
		}
		return resource;
	}

	/**
	 * 设置默认系统
	 * 
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	@RequestMapping("saveCurrSys")
	public void saveCurrSys(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Long systemId = RequestUtil.getLong(request, "systemId");
		subSystemService.setCurrentSystem(systemId, request, response);
		response.sendRedirect(request.getContextPath()
				+ "/platform/console/main.ht");
	}

	/**
	 * 切换岗位
	 * 
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	@RequestMapping("switchCurrentOrg")
	public void switchCurrentPos(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Long posId = RequestUtil.getLong(request, "posId");
		ContextUtil.setCurrentPos(posId);
		response.sendRedirect(request.getContextPath()
				+ "/platform/console/main.ht");
	}

	/**
	 * 切换语言
	 * @return
	 * @throws IOException 
	 * @throws Exception
	 */
	@RequestMapping("switchSysLanguage")
	public void switchSysLanguage(HttpServletRequest request,HttpServletResponse response) throws IOException {	
		String language=RequestUtil.getString(request, "language");
		String[] lan = language.split("_");
		Locale local = new Locale(lan[0],lan[1]);
		ContextUtil.setLocale(local);
        SessionLocaleResolver sessionLocaleResolver = (SessionLocaleResolver) AppUtil.getBean(SessionLocaleResolver.class);
        sessionLocaleResolver.setLocale(request, response, local);
        response.sendRedirect(request.getContextPath()+ "/platform/console/main.ht");
	}

	/**
	 * 取得总分类表用于显示树层次结构的分类可以分页列表
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 *             -------------
	 */
	@RequestMapping("getSysRolResTreeData")
	// @Action(description="取得总分类表树形数据")
	@ResponseBody
	public List<Resources> getSysRolResTreeData(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		long resOrgId = RequestUtil.getLong(request, "id", 10000005010038l);//默认公司
		Long chooseOrgId = sysOrgService.changeResIdToOrgId(resOrgId+"");//将部门资源Id转化为组织部门Id,-1为公司
		request.getSession().setAttribute("chooseOrgId", chooseOrgId);//将值存入session
		
		
		SubSystem currentSystem=SubSystemUtil.getCurrentSystem(request);
		SysUser currentUser =(SysUser) ContextUtil.getCurrentUser();
		List<Resources> resourcesList=resourcesService.getSysMenu(currentSystem,currentUser,resOrgId);
		//加载文件管理新闻目录下的资源数据
		List<Doc> docForPortal = docService.getDocForPortal(new HashMap<String,Object>());

		List<Resources> resourcesListTemp=new ArrayList<Resources>();//需过滤资源
		List<Resources> resourcesListContain=new ArrayList<Resources>();//需过滤资源
		HashSet<String> orgNames=new HashSet<>();//记录一个用户属于多个部门的情况
		//获取深水咨询资源下一级名称为公司的资源节点	
		for (Resources res : resourcesList) {
			if(("公司".equals(res.getResName()) || "深水咨询".equals(res.getResName())) && res.getParentId().intValue()==0){
				if(docForPortal!=null && docForPortal.size()>0){
					for (Doc doc : docForPortal) {
						Resources rs=new Resources();
						rs.setResId(new Date().getTime()+Long.valueOf(new Random().nextInt(9999)));
						rs.setParentId(res.getResId());
						rs.setResName(doc.getDocname());
						rs.setIsNewOpen((short) 0);
						rs.setDefaultUrl("/makshi/doc/docinfo/filelist.ht?docId="+doc.getDocid()+"&from=portal");
						rs.setIcon("/images/menu_icon/menu_62.png");
						resourcesListContain.add(rs);
					}
					//break;
				}
			}
			if("项目".equals(res.getResName()) && res.getParentId().intValue()==0){
				String resOrgIds = res.getResOrgIds();
				if(StringUtil.isEmpty(resOrgIds)){
					res.setIsDisplayInMenu((short)0);
				}
			}
			if(resOrgId!=-1l){
				if(res.getParentId()==resOrgId&&(Arrays.asList(OrgResUtils.secondToFirstMenu).contains(res.getResName()))){
					res.setParentId(0l);//二级菜单变一级菜单
				}
			}
			//是否是超级管理员
			if(!ContextUtil.isSuperAdmin()){
				Long userId = currentUser.getUserId();
				List<UserPosition> userPosList = userPositionService.getByUserId(userId);//查询当前用户部门
				
				for(UserPosition pos :userPosList){
					Long orgId = pos.getOrgId();
					SysOrg org = sysOrgService.getById(orgId);
					String orgPathname = org.getOrgPathname();
					String[] orgArr = orgPathname.split("/");
					String orgName ="";
					if(orgArr.length>2){
						orgName=orgArr[2];//获得一级部门
						orgNames.add(orgName);
					}
					if((orgArr.length==2 && "深水咨询".equals(orgArr[1])) || isOrgAdmin()){
						//公司领导 可见所有部门
					}else{
						Map map = OrgResUtils.getOrgRes();
						if(map.containsKey(orgName)){
							List resList =(List) map.get(orgName);
							if(resList.contains(res.getResName())){
								resourcesListTemp.add(res);
							}
						}else{
							if(Arrays.asList(OrgResUtils.all).contains(res.getResName()))
								resourcesListTemp.add(res);
						}
					}
				}
			}
			res.setIcon(request.getContextPath()+ res.getIcon());
			
		}
		for (String orgName : orgNames) {
			Iterator<Resources> iterator = resourcesListTemp.iterator();
			while(iterator.hasNext()){
				Resources next = iterator.next();
				if(next.getResName().equals(orgName)){
					iterator.remove();
				}
			}
		}
		resourcesList.removeAll(resourcesListTemp);
		resourcesList.addAll(resourcesListContain);
	/*	if(resOrgId!=-1l && resOrgId!=10000005010038l){
			String rolealias = currentUser.getRoles();
			if(rolealias.contains("bpm_bmrszy")){
				Resources resources = resourcesService.getById(10000002740017L);
				resources.setParentId(0L);
				resourcesList.add(resources);
			}
		}*/
		
		QueryFilter queryFilter =new QueryFilter(request);
		queryFilter.setPageBean(null);
		//queryFilter.addFilter("isUsed", 1);
		if(chooseOrgId!=-1l){
			queryFilter.addFilter("orgID", chooseOrgId);
		}
		/*List<ClassifyLibrary> calssifyList = classifyLibraryService.getAll(queryFilter);
		if(calssifyList!=null && calssifyList.size()>0){
			for (Resources res : resourcesList) {
				if("项目分类".equals(res.getResName()) && res.getParentId().longValue()==10000005830017l){
					for (ClassifyLibrary calssify : calssifyList) {//项目菜单
						Resources rs=new Resources();
						rs.setResId(new Date().getTime()+Long.valueOf(new Random().nextInt(9999)));
						rs.setParentId(res.getResId());
						rs.setResName(calssify.getName());
						rs.setIsNewOpen((short) 0);
						rs.setDefaultUrl("/makshi/project/project/list.ht?classifylibraryid="+calssify.getId()+"&from=portal");
						rs.setIcon("/images/menu_icon/menu_62.png");
						resourcesList.add(rs);
					}
					break;
				}
			}
			
		}*/
		//采用分类库类别分类
		Resources proCateParentres=null;
		for (Resources res : resourcesList) {
			if("项目分类".equals(res.getResName()) && res.getParentId().longValue()==10000005830017l){
				proCateParentres=res;
			}
		}
		List<ClassifyLibrary> calssifyList = classifyLibraryService.getAll(queryFilter);
		if(calssifyList!=null && calssifyList.size()>0 && proCateParentres!=null){
			//key-value:id-obj
			Map<String,ClassifyLibrary> data=new HashMap<>();
			//key-value:subid-List<obj>
			Map<String,List<ClassifyLibrary>> subdataList=new HashMap<>();
			List<ClassifyLibrary> topCategory=new ArrayList<>();
			for(int i=0;i<calssifyList.size();i++){
				ClassifyLibrary classifyLibrary = calssifyList.get(i);
				data.put(classifyLibrary.getId()+"", classifyLibrary);
				List<ClassifyLibrary> subList = subdataList.get(classifyLibrary.getSupid()+"");
				if(subList==null){
					subList=new ArrayList<>();
					subList.add(classifyLibrary);
					subdataList.put(classifyLibrary.getSupid()+"", subList);
				}else{
					subList.add(classifyLibrary);
				}
				if("0".equals(classifyLibrary.getSupid()+"")){
					topCategory.add(classifyLibrary);
				}
				
			}
			for (ClassifyLibrary classifyLibrary : topCategory) {
				addProClassifyCateRes(proCateParentres,classifyLibrary,data,subdataList,resourcesList,0);
			}
			
		}
		
		
		//ResourcesService.addIconCtxPath(resourcesList, request.getContextPath());
		return resourcesList;
	}
	//递归添加分类类别资源
	private void addProClassifyCateRes(Resources proCateParentres, ClassifyLibrary classifyLibrary, Map<String, ClassifyLibrary> data,Map<String, List<ClassifyLibrary>> subdataList, List<Resources> resourcesList, int deep){
		
		Resources rs=new Resources();
		rs.setResId(new Date().getTime()+Long.valueOf(new Random().nextInt(9999)));
		rs.setParentId(proCateParentres.getResId());
		rs.setResName(classifyLibrary.getName());
		rs.setIsNewOpen((short) 0);
		String isParent = classifyLibrary.getIsParent();
		rs.setIsFolder((short) 1);
		rs.setIcon("/images/menu_icon/menu_62.png");
		//rs.setIcon("/images/menu_icon/menu_67.png");
		if(1==classifyLibrary.getIsused()){
			rs.setDefaultUrl("/makshi/project/project/list.ht?classifylibraryid="+classifyLibrary.getId()+"&from=portal");
		}
		if(!("true").equals(isParent)){
			//分类类别叶子节点
			rs.setIsFolder((short) 0);
			/*List<ClassifyLibrary> classifyLibList = classifyLibraryService.getClassifyByCateId(classifycategory.getId());*/
			resourcesList.add(rs);
			return ;
		}else {
			List<ClassifyLibrary> list = subdataList.get(classifyLibrary.getId()+"");
			
			if(list!=null && list.size()>0){
				resourcesList.add(rs);
				for (ClassifyLibrary classifyLibrary2 : list) {
					addProClassifyCateRes(rs,classifyLibrary2, data, subdataList,resourcesList,++deep);
				}
			}else{
				rs.setIsFolder((short) 0);
				resourcesList.add(rs);
			}
		}
	}
	public boolean isOrgAdmin(){
		Long currentUserId = ContextUtil.getCurrentUserId();
		if(currentUserId==1l){//超级管理员
			return true;
		}
		List<SysRole> byUserId = sysRoleService.getByUserId(currentUserId);
		if(byUserId==null || byUserId.size()==0){
			return false;
		}
		for (SysRole sysRole : byUserId) {
			if(("深水咨询部门管理员").equals(sysRole.getRoleName())){
				return true;
			}
		}
		return false;
	}
	/**
	 * 验证expression中的脚本
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getValidateResult")
	@ResponseBody
	public Object getValidateResult(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String script = RequestUtil.getString(request, "script");
		GroovyScriptEngine engine = (GroovyScriptEngine) AppUtil
				.getBean(GroovyScriptEngine.class);

		Map<String, Object> reObj = new HashMap<String, Object>();
		try {
			Object result = engine.executeObject(script, null);
			reObj.put("hasError", false);
			reObj.put("errorMsg", "");

			if (result != null) {
				reObj.put("result", result);
				reObj.put("resultType", result.getClass().getName());
			}
		} catch (Exception ex) {
			reObj.put("hasError", true);
			reObj.put("errorMsg", ex.getMessage());
		}
		return reObj;
	}
}
