

package com.hotent.makshi.controller.project;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.util.ExceptionUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.makshi.model.project.ClassifyLibrary;
import com.hotent.makshi.service.project.ClassifyLibraryService;
import com.hotent.makshi.service.project.ClassifyStageService;
import com.hotent.makshi.service.project.ClassifyStageTaskService;
import com.hotent.makshi.service.project.ProjectService;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.annotion.ActionExecOrder;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.service.bpm.thread.MessageUtil;
import com.hotent.platform.service.system.SysOrgService;
/**
 * 对象功能:项目分类类别 控制器类
 */
@Controller
@RequestMapping("/makshi/project/classifylib/")
public class ClassifycategoryController extends BaseController
{
	
	@Autowired
	private ClassifyLibraryService classifyLibService;
	@Resource
	private SysOrgService sysOrgService;
	@Autowired
	private ClassifyStageService classifyStageService;
	@Autowired
	private ClassifyStageTaskService classifyStageTaskService;
	@Autowired
	private ProjectService projectService;
	/**
	 * 添加或更新项目分类类别。
	 * @param request
	 * @param response
	 * @param classifycategory 添加或更新的实体
	 * @return
	 * @throws Exception
	 *//*
	@RequestMapping("save")
	@Action(description="添加或更新项目分类类别")
	public void save(HttpServletRequest request, HttpServletResponse response,Classifycategory classifycategory) throws Exception
	{
		String resultMsg=null;
		PrintWriter out = response.getWriter();
		try{
			if(classifycategory.getId()==null){
				classifycategoryService.save(classifycategory);
				//resultMsg=getText("添加","项目分类类别");
				String result="{result:1,id:"+classifycategory.getId()+",operate:'add'}";
				out.print(result);
			}else{
				Classifycategory temp = classifycategoryService.getById(classifycategory.getId());
				if (temp == null) {
					resultMsg= getText("更新","该文件目录已经不存在了！");
					writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Fail);
					return;
				}
				if(classifycategory.getName()==null || classifycategory.getName().length()==0){
					resultMsg= getText("更新","目录名称不能为空！");
					writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Fail);
					return;
				}
				String recordTempName=temp.getName();
				String tpname = temp.getPathname();
				classifycategory.setPathname(tpname.substring(0, tpname.lastIndexOf("/")+1)+classifycategory.getName());
				classifycategoryService.save(classifycategory);
				//判断目录名称是否修改
				if(!recordTempName.equals(classifycategory.getName())){
					//更新下级目录的路径名称
					updatePathName(classifycategory);
				}
				//resultMsg=getText("更新","项目分类类别");
			    String result="{result:1,operate:'edit'}";
				out.print(result);
			}
			//writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}*/
	
	/**
	 * 根据指定分类类别递归更新下级目录的路径名称
	 * @param temp
	 *//*
	private void updatePathName(Classifycategory temp) {
		List<Classifycategory> cateBySupId = classifycategoryService.getCateBySupId(temp.getId());
		if(cateBySupId==null || cateBySupId.size()==0){
			return ;
		}
		for (Classifycategory cat : cateBySupId) {
			cat.setPathname(temp.getPathname()+"/"+cat.getName());
			classifycategoryService.update(cat);
			updatePathName(cat);
		}
	}*/
	
	/**
	 * 	编辑项目分类类别
	 * @param request
	 * @param response
	 * @throws Exception
	 *//*
	@RequestMapping("edit")
	@Action(description="编辑项目分类类别")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		String parentId = RequestUtil.getString(request,"parentId");
		String returnUrl=RequestUtil.getPrePage(request);
		Classifycategory classifycategory=classifycategoryService.getById(id);
		if(classifycategory==null){
			classifycategory=new Classifycategory();
			if(StringUtil.isNotEmpty(parentId)){
				classifycategory.setSubid(Long.valueOf(parentId));
			}
			Date ctime = new Date();
			classifycategory.setCtime(ctime);
			classifycategory.setUtime(ctime);
			ISysUser currentUser = ContextUtil.getCurrentUser();
			classifycategory.setCreator(currentUser.getFullname());
			classifycategory.setCreatorID(currentUser.getUserId()+"");
		}
		return getAutoView().addObject("classifycategory",classifycategory)
							.addObject("returnUrl",returnUrl);
	}*/

	@RequestMapping("addCate")
	@Action(description = "新建分类库")
	public ModelAndView addCate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = getAutoView();
		Long id=RequestUtil.getLong(request, "id", 0l);
		String orgid=RequestUtil.getString(request, "orgid");
		Long parentId=RequestUtil.getLong(request, "parentId", 0l);
		ClassifyLibrary byId = classifyLibService.getById(id);
		if(byId!=null){
			long stagenum = classifyStageService.getCountByClassifyid(byId.getId());
			byId.setStagenum(stagenum);
			long tasknum = classifyStageTaskService.getTaskCountByClassifyid(byId.getId());
			byId.setTasknum(tasknum);
			long pronum = projectService.getCountByClassifyLibId(byId.getId());
			byId.setPronum(pronum);
		}
		if(StringUtils.isNotEmpty(orgid)){
			SysOrg sysOrg = sysOrgService.getById(Long.valueOf(orgid));
			if(sysOrg!=null){
				if(byId==null){
					byId=new ClassifyLibrary();
					byId.setSupid(parentId);
					byId.setOrgID(sysOrg.getOrgId());
					byId.setOrg(sysOrg.getOrgName());
				}
			}
		}
		return mv.addObject("classifyLib", byId);
	}
	@RequestMapping("toSetView")
	@Action(description = "去修改页面")
	public ModelAndView toSetView(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<SysOrg> orgs = sysOrgService.getOrgsAll();
		for (SysOrg org : orgs) {
			if (org.getOrgName().equals("深水咨询")) {
				org.setOrgId(0l);
				break;
			}
		}
		return getAutoView().addObject("orgs", orgs);
	}
	
	@RequestMapping("getCategoryTree")
	@Action(description = "项目分类类别")
	public @ResponseBody List<ClassifyLibrary> getCategoryTree(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<ClassifyLibrary> cats = new ArrayList<>();
		Long orgId = RequestUtil.getLong(request, "orgId");
		Long supid = RequestUtil.getLong(request, "id");
		String docname = RequestUtil.getString(request, "name");

		if (supid == 0 && !"项目分类类别".equals(docname)) {
			// 根节点
			ClassifyLibrary cat = new ClassifyLibrary();
			cat.setId(0L);
			cat.setName("项目分类类别");
			cat.setIsRoot((short) 1);
			cat.setSupid(-1L);
			cat.setPath("0.");
			cats.add(cat);
		}
		String type = RequestUtil.getString(request, "type");
		if ("getAll".equals(type) && supid == 0) {
			supid = null;// 一次查询出部门下所有目录节点
		}
		if(0l==orgId){
			//深水咨询
			orgId=null;
		}
		List<ClassifyLibrary> catesByOrg = classifyLibService.getCatesByOrg(orgId, supid);
		if (catesByOrg != null && catesByOrg.size() > 0) {
			for (ClassifyLibrary classifyLibrary : catesByOrg) {
				long stagenum = classifyStageService.getCountByClassifyid(classifyLibrary.getId());
				classifyLibrary.setStagenum(stagenum);
				long tasknum = classifyStageTaskService.getTaskCountByClassifyid(classifyLibrary.getId());
				classifyLibrary.setTasknum(tasknum);
				long pronum = projectService.getCountByClassifyLibId(classifyLibrary.getId());
				classifyLibrary.setPronum(pronum);
			}
			cats.addAll(catesByOrg);
		}

		return cats;
	}
	/**
	 * 移动分类数据。
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("move")
	@Action(description = "转移分类", execOrder = ActionExecOrder.AFTER, detail = "转移分类", exectype = "管理日志")
	public void move(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultMessage resultMessage = null;
		PrintWriter out = response.getWriter();
		long targetId = RequestUtil.getLong(request, "targetId", 0);
		long dragId = RequestUtil.getLong(request, "dragId", 0);
		String moveType = RequestUtil.getString(request, "moveType");
		try {
			classifyLibService.move(targetId, dragId, moveType);

			resultMessage = new ResultMessage(ResultMessage.Success, "转移分类完成");
		} catch (Exception ex) {
			String str = MessageUtil.getMessage();
			if (StringUtil.isNotEmpty(str)) {
				resultMessage = new ResultMessage(ResultMessage.Fail, "转移分类失败:" + str);
				response.getWriter().print(resultMessage);
			} else {
				String message = ExceptionUtil.getExceptionMessage(ex);
				resultMessage = new ResultMessage(ResultMessage.Fail, message);
				response.getWriter().print(resultMessage);
			}
		}
		out.print(resultMessage);
	}
}