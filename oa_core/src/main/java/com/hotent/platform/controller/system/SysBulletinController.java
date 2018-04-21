package com.hotent.platform.controller.system;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.api.util.PropertyUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.model.system.SysBulletin;
import com.hotent.platform.model.system.SysBulletinColumn;
import com.hotent.platform.model.system.SysBulletinTemplate;
import com.hotent.platform.model.system.SysPropertyConstants;
import com.hotent.platform.model.system.SysReadRecode;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.system.SysBulletinColumnService;
import com.hotent.platform.service.system.SysBulletinService;
import com.hotent.platform.service.system.SysBulletinTemplateService;
import com.hotent.platform.service.system.SysReadRecodeService;

/**
 * 对象功能:公告表 控制器类
 */
@Controller
@RequestMapping("/platform/system/sysBulletin/")
public class SysBulletinController extends BaseController {
	@Resource
	private SysBulletinService sysBulletinService;
	@Resource
	private SysBulletinColumnService sysBulletinColumnService;
	@Resource
	private SysBulletinTemplateService sysBulletinTemplateService;
	@Resource
	private SysReadRecodeService sysReadRecodeService;

	

	/**
	 * 列表数据
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description = "查看公告表分页列表")
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long companyId = ContextUtil.getCurrentCompanyId();
		Boolean isSuperAdmin = ContextUtil.isSuperAdmin();
		
		QueryFilter filter = new QueryFilter(request, "sysBulletinItem");
		filter.addFilter("companyId", companyId);
		filter.addFilter("isSuperAdmin", isSuperAdmin);
		List<SysBulletin> list = sysBulletinService.getAll(filter);
		// 有权限的栏目
		List<SysBulletinColumn> columnList = sysBulletinColumnService
				.getColumn(companyId, isSuperAdmin);
		Long test = ContextUtil.getCurrentUserId();
		ModelAndView mv = this.getAutoView().addObject("sysBulletinList", list)
				.addObject("columnList", columnList)
				.addObject("isSuperAdmin", isSuperAdmin)
				.addObject("currentUserId", ContextUtil.getCurrentUserId());
		return mv;
	}
	
	@RequestMapping("getMylist")
	@Action(description = "查看我的公告表分页列表")
	public ModelAndView getMylist(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long companyId = ContextUtil.getCurrentCompanyId();
		Boolean isSuperAdmin = ContextUtil.isSuperAdmin();
		
		QueryFilter filter = new QueryFilter(request, "sysBulletinItem");
		filter.addFilter("status", 1);
		filter.addFilter("companyId", companyId);
		filter.addFilter("isSuperAdmin", isSuperAdmin);
		List<SysBulletin> list = sysBulletinService.getAll(filter);
		// 有权限的栏目
		List<SysBulletinColumn> columnList = sysBulletinColumnService
				.getColumn(companyId, isSuperAdmin);
		ModelAndView mv = new ModelAndView("/platform/system/sysBulletinList.jsp").addObject("sysBulletinList", list)
				.addObject("columnList", columnList);
		return mv;
	} 

	/**
	 * 删除公告表
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description = "删除公告表")
	public void del(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
			sysBulletinService.delByIds(lAryId);
			message = new ResultMessage(ResultMessage.Success, "删除公告表成功!");
		} catch (Exception ex) {
			message = new ResultMessage(ResultMessage.Fail, "删除失败"
					+ ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	/**
	 * 编辑公告
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description = "编辑公告")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
	
		boolean isAddByFlow = RequestUtil.getBoolean(request, "addByFlow",false);
		boolean isSupportWeixin = PropertyUtil.getBooleanByAlias(SysPropertyConstants.WX_IS_SUPPORT,false);
		String returnUrl = RequestUtil.getPrePage(request);
		Long id = RequestUtil.getLong(request, "id");
		SysBulletin sysBulletin = sysBulletinService.getById(id);
		Long companyId = ContextUtil.getCurrentCompanyId();
		//公告模板
		List<SysBulletinTemplate> templateList  = sysBulletinTemplateService.getAll();
		// 有权限的栏目
		List<SysBulletinColumn> columnList = sysBulletinColumnService
				.getColumn(companyId, ContextUtil.isSuperAdmin());
		
		boolean canSelect=companyId==0 || ContextUtil.isSuperAdmin();
		
		return getAutoView().addObject("sysBulletin", sysBulletin)
				.addObject("returnUrl", returnUrl)
				.addObject("canSelect", canSelect)
				.addObject("columnList", columnList)
				.addObject("columnList", columnList)
				.addObject("isAddByFlow", isAddByFlow)
		        .addObject("templateList", templateList)
		        .addObject("isSupportWeixin", isSupportWeixin);
	}

	/**
	 * 取得公告表明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description = "查看公告表明细")
	public ModelAndView get(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long id = RequestUtil.getLong(request, "id");
		SysBulletin sysBulletin = sysBulletinService.getById(id);
		
		if(sysBulletin != null){
			long userId = ContextUtil.getCurrentUserId();
			if(!sysReadRecodeService.hasRead(id,userId)){
				sysReadRecodeService.add(new SysReadRecode(id,userId,"Bulletin",sysBulletin.getColumnid()));
			}
		}
		return getAutoView().addObject("sysBulletin", sysBulletin);
	}
	
	/**
	 * 在首页上面点击更多按钮的时候跳转到这里
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("more")
	public ModelAndView more(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String alias = RequestUtil.getString(request, "alias");
		QueryFilter filter = new QueryFilter(request, "sysBulletinItem");
		filter.addFilter("alias", alias);
		filter.addFilter("userId", ContextUtil.getCurrentUserId());
		List<SysBulletin> list = sysBulletinService.getAllByAlias(filter);
		return this.getAutoView().addObject("alias", alias).addObject("sysBulletinList", list);
	}
	
}