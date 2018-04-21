
package com.hotent.makshi.controller.template;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.api.org.model.ISysUser;
import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.makshi.model.doc.DocFile;
import com.hotent.makshi.model.template.DocTemplate;
import com.hotent.makshi.model.template.RuleBookmark;
import com.hotent.makshi.model.template.RuleManager;
import com.hotent.makshi.service.template.DocTemplateService;
import com.hotent.makshi.service.template.RuleManagerService;
import com.hotent.platform.annotion.Action;

/**
 * 对象功能:合同基本信息 控制器类
 */
@Controller
@RequestMapping("/makshi/doc/template/")
public class DocTemplateController extends BaseController {
	@Resource
	private DocTemplateService docTemplateService;
	@Resource
	private RuleManagerService ruleManagerService;
	/**
	 * 添加或更新文档目录信息。
	 * 
	 * @param request
	 * @param response
	 * @param doc
	 *            添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description = "添加或更新文档目录信息")
	public void save(HttpServletRequest request, HttpServletResponse response, DocTemplate docTemplate) throws Exception {
		String resultMsg = null;
		try {
			ISysUser currentUser = ContextUtil.getCurrentUser();
			if (docTemplate.getId() == null) {
				docTemplate.setId(UniqueIdUtil.genId());
				docTemplate.setCreatorID(currentUser.getUserId());
				docTemplate.setCreator(currentUser.getFullname());
				docTemplate.setIsdelete(0);
				docTemplate.setCtime(new Date());
				docTemplateService.add(docTemplate);
				resultMsg = getText("添加", "文档模板信息");

			} else {
				// 更新文档目录
				docTemplateService.update(docTemplate);
				resultMsg = getText("更新", "文档模板信息");
			}
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
		}
	}

	@RequestMapping("list")
	@Action(description = "获取模板列表")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<DocTemplate> list = docTemplateService.getAll(new QueryFilter(request, "templateItem"));
		ModelAndView mv=getAutoView();
		return mv.addObject("templatelist", list);
	}
	
	@RequestMapping("edit")
	@Action(description = "获取模板列表")
	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long id = RequestUtil.getLong(request, "id");
		DocTemplate docTemplate = docTemplateService.getById(id);
		
		ModelAndView mv=getAutoView();
		return mv.addObject("docTemplate", docTemplate);
	}
	
	/*@RequestMapping("ruleList")
	@Action(description = "获取模板列表")
	public ModelAndView ruleList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<DocTemplate> list = docTemplateService.getAll();
		ModelAndView mv=getAutoView();
		return mv.addObject("list", list);
	}*/
	
	@RequestMapping("detail")
	@Action(description = "获取阶段库")
	public ModelAndView detail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = getAutoView();
		Long id=RequestUtil.getLong(request,"id");
		Long ruleId=RequestUtil.getLong(request,"ruleId");
		DocTemplate docTemplate = docTemplateService.getById(id);
		List<RuleBookmark> bookmarks = ruleManagerService.getRuleBookmarkList(ruleId);
		List<RuleManager> ruleList = ruleManagerService.getAll();
		return mv.addObject("docTemplate", docTemplate).addObject("bookmarks", bookmarks)
				.addObject("ruleList", ruleList).addObject("ruleId", ruleId);
	}
	
	@RequestMapping("del")
	@Action(description = "删除文档模板")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
			docTemplateService.delByIds(lAryId);
			message = new ResultMessage(ResultMessage.Success, "删除文档模板成功!");
		} catch (Exception ex) {
			message = new ResultMessage(ResultMessage.Fail, "删除文档模板失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
}