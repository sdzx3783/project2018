package com.hotent.makshi.controller.doc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.api.org.model.ISysUser;
import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.page.PageBean;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.makshi.model.doc.Doc;
import com.hotent.makshi.model.doc.DocCollection;
import com.hotent.makshi.model.doc.DocFile;
import com.hotent.makshi.model.doc.DocLogs;
import com.hotent.makshi.service.doc.DocCollectionService;
import com.hotent.makshi.service.doc.DocFileService;
import com.hotent.makshi.service.doc.DocLogsService;
import com.hotent.makshi.service.doc.DocService;
import com.hotent.makshi.utils.IPUtils;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.model.system.SysRole;
import com.hotent.platform.service.system.SysOrgService;
import com.hotent.platform.service.system.SysRoleService;

/**
 * 对象功能:合同基本信息 控制器类
 */
@Controller
@RequestMapping("/makshi/doc/docinfo/")
public class DocController extends BaseController {
	private final Logger log = Logger.getLogger(this.getClass());
	@Resource
	private DocService docService;
	@Resource
	private DocFileService docFileService;
	@Resource
	private SysOrgService sysOrgService;
	@Resource
	private DocCollectionService collectionService;
	@Resource
	private DocLogsService docLogsService;
	@Resource
	private SysRoleService sysRoleService;

	/**
	 * 添加或更新文档目录信息。
	 * 
	 * @param request
	 * @param response
	 * @param doc 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description = "添加或更新文档目录信息")
	public void save(HttpServletRequest request, HttpServletResponse response, Doc doc) throws Exception {
		String resultMsg = null;
		try {
			ISysUser currentUser = ContextUtil.getCurrentUser();
			if (doc.getDocid() == null) {
				long id = UniqueIdUtil.genId();
				doc.setDocid(id);
				Doc tempDoc = docService.getById(doc.getDocsupid());
				if (tempDoc == null) {
					doc.setPath(doc.getDocsupid() + "." + id + ".");
					doc.setPathname("/" + doc.getDocname());
				} else {
					doc.setPath(tempDoc.getPath() + doc.getDocid() + ".");
					doc.setPathname(tempDoc.getPathname() + "/" + doc.getDocname());
				}
				doc.setCreatorid(currentUser.getUserId());
				doc.setCreatetime(new Date());
				doc.setIsdelete(0);
				docService.add(doc);
				recordLogs(doc, 0, request);
				resultMsg = getText("添加", "文档目录信息");

			} else {
				// 更新文档目录
				Doc temp = docService.getById(doc.getDocid());
				if (temp == null) {
					resultMsg = getText("更新", "该文件目录已经不存在了！");
					writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Fail);
					return;
				}
				if (doc.getDocname() == null || doc.getDocname().length() == 0) {
					resultMsg = getText("更新", "目录名称不能为空！");
					writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Fail);
					return;
				}
				String recordTempName = temp.getDocname();
				temp.setDocname(doc.getDocname());

				String tpname = temp.getPathname();
				temp.setPathname(tpname.substring(0, tpname.lastIndexOf("/") + 1) + doc.getDocname());
				temp.setReadorgs(doc.getReadorgs());
				temp.setReadorgsID(doc.getReadorgsID());
				temp.setReaduser(doc.getReaduser());
				temp.setReaduserID(doc.getReaduserID());
				temp.setWriteorgsID(doc.getWriteorgsID());
				temp.setWriteorgs(doc.getWriteorgs());
				temp.setWriteuser(doc.getWriteuser());
				temp.setWriteuserID(doc.getWriteuserID());

				temp.setUpdateid(currentUser.getUserId());
				temp.setUpdatetime(new Date());
				docService.update(temp);
				// 判断文档目录名称是否修改
				if (!recordTempName.equals(doc.getDocname())) {
					// 更新下级目录的路径名称
					updatePathName(temp);
				}

				recordLogs(temp, 1, request);
				resultMsg = getText("更新", "文档目录信息");
			}
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
		}
	}

	/**
	 * 根据指定文档目录递归更新下级目录的路径名称
	 * 
	 * @param temp
	 */
	private void updatePathName(Doc temp) {
		List<Doc> docBySupId = docService.getDocBySupId(temp.getDocid());
		if (docBySupId == null || docBySupId.size() == 0) {
			return;
		}
		for (Doc doc : docBySupId) {
			doc.setPathname(temp.getPathname() + "/" + doc.getDocname());
			docService.update(doc);
			updatePathName(doc);
		}

	}

	/**
	 * 获取目录
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description = "获取目录")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		long orgId = RequestUtil.getLong(request, "orgId", 0);
		long supId = RequestUtil.getLong(request, "supId", 0);
		int isFirst = RequestUtil.getInt(request, "isFirst", 0);
		List<Doc> docListTemp = null;
		if (isFirst == 1) {
			SysOrg containCurrentOrg = getContainCurrentOrg();
			if (containCurrentOrg == null || containCurrentOrg.getOrgName().equals("深水咨询")) {
				docListTemp = docService.getDocsByOrg(orgId, supId);
			} else {
				orgId = containCurrentOrg.getOrgId();
				docListTemp = docService.getDocsByOrg(orgId, supId);
			}
		} else {
			docListTemp = docService.getDocsByOrg(orgId, supId);
		}
		List<Doc> docList = new ArrayList<Doc>();
		Long currentUserId = ContextUtil.getCurrentUserId();
		List<SysOrg> userorgs = getOrgIdByUserId();
		// boolean isWrite = false;//写权限控制
		boolean canCreate = false;// 新建分类权限
		long lastSupId = 0;// 上级目录，用于返回上一页
		if (supId != 0) {
			Doc lastDoc = docService.getById(supId);
			if (lastDoc != null) {
				lastSupId = lastDoc.getDocsupid();
				// 写权限
				// isWrite = docFileService.isWriteDoc(lastDoc.getDocid(),
				// currentUserId, currentOrgId);
			}
		}

		if (!isOrgDocAdmin(orgId)) {
			for (Doc doc : docListTemp) {
				// 读权限控制
				boolean isView = docFileService.isViewDoc(doc.getDocid(), currentUserId, userorgs);
				boolean isRead = docFileService.isReadDoc(doc.getDocid(), currentUserId, userorgs);
				if (isView) {
					doc.setIsRead(isRead);
					docList.add(doc);
				}
			}
		} else {// 超管或文档管理员
			docList = docListTemp;
			// isWrite= true;
			canCreate = true;
			for (Doc doc : docList) {
				doc.setIsRead(true);
			}
		}

		List<SysOrg> orgs = sysOrgService.getOrgsAll();
		for (SysOrg org : orgs) {
			if (org.getOrgName().equals("深水咨询")) {
				org.setOrgId(0l);
				break;
			}

		}

		ModelAndView mv = this.getAutoView().addObject("docList", docList).addObject("orgs", orgs).addObject("orgId", orgId).addObject("supId", supId)
				.addObject("lastSupId", lastSupId).addObject("canCreate", canCreate);

		return mv;
	}

	/**
	 * 获取下拉列表包含的当前组织
	 * 
	 * @param currentOrgId
	 * @return
	 */
	private SysOrg getContainCurrentOrg() {
		List<SysOrg> orgs = getOrgIdByUserId();
		if (orgs == null || orgs.size() == 0) {
			return null;
		}
		List<String> ids = new ArrayList<>();
		for (SysOrg org : orgs) {
			ids.add(org.getOrgId() + "");
			String path = org.getPath();
			if (StringUtils.isNotEmpty(path)) {
				String[] split = path.split("\\.");
				if (split.length >= 3) {
					ids.add(split[2]);// 获取二级部门id
				}
			}
		}
		List<SysOrg> sysorgs = sysOrgService.getOrgsAll();
		for (SysOrg org : sysorgs) {
			if (ids.contains(org.getOrgId() + "")) {
				return org;
			}
		}
		return null;
	}

	/**
	 * 编辑文档目录
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description = "编辑文档目录")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		Long id = RequestUtil.getLong(request, "id");
		long orgId = RequestUtil.getLong(request, "orgId", 0);
		long supId = RequestUtil.getLong(request, "supId", 0);
		String returnUrl = RequestUtil.getPrePage(request);
		Doc doc = docService.getById(id);
		return getAutoView().addObject("docinfo", doc).addObject("returnUrl", returnUrl).addObject("orgId", orgId).addObject("supId", supId);
	}

	/**
	 * 获取目录文档
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("filelist")
	@Action(description = "获取目录文档")
	public ModelAndView filelist(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String from = RequestUtil.getString(request, "from", "");
		long docId = RequestUtil.getLong(request, "docId", 0);
		long orgId = 0;
		long supId = 0;

		Long currentUserId = ContextUtil.getCurrentUserId();
		// Long currentOrgId =
		// ContextUtil.getCurrentOrgId()==0l?-1l:ContextUtil.getCurrentOrgId();//主岗位组织id
		List<SysOrg> userorgs = getOrgIdByUserId();
		boolean isWrite = false;// 写权限控制

		Doc doc = docService.getById(docId);
		if (doc != null) {
			orgId = doc.getOrgid();
			supId = doc.getDocsupid();
			// 写权限
			isWrite = docFileService.isWriteDoc(docId, currentUserId, userorgs);
		}
		if (isOrgDocAdmin(doc.getOrgid())) {
			isWrite = true;
		}
		QueryFilter queryFilter = new QueryFilter(request, "docFile");
		queryFilter.getFilters().put("showdraft", currentUserId);
		List<DocFile> docFileList = docFileService.getByDocId(docId, queryFilter);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/makshi/doc/docinfoFileList.jsp");
		mv.addObject("docFileList", docFileList).addObject("docId", docId).addObject("orgId", orgId).addObject("supId", supId)
				.addObject("pageBeandocFile", queryFilter.getPageBean())// 此两项用于分页标签
				.addObject("requestURIdocFile", request.getRequestURI()).addObject("isWrite", isWrite).addObject("from", from);

		return mv;
	}

	/**
	 * 编辑文档
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("fileedit")
	@Action(description = "编辑文档")
	public ModelAndView fileedit(HttpServletRequest request) throws Exception {
		Long id = RequestUtil.getLong(request, "id");
		long docId = RequestUtil.getLong(request, "docId", 0);
		String returnUrl = RequestUtil.getPrePage(request);
		String lastReturnUrl = RequestUtil.getString(request, "lastReturnUrl", "");// 记录返回文档详情页所需要的返回url
		if (returnUrl.indexOf("&lastReturnUrl") < 0) {
			if (returnUrl.indexOf("?") != -1) {
				returnUrl = returnUrl + "&lastReturnUrl=" + lastReturnUrl;
			} else {
				returnUrl = returnUrl + "?lastReturnUrl=" + lastReturnUrl;
			}
		}
		Doc doc = docService.getById(docId);
		String docName = "";
		String orgName = "";
		if (doc != null) {
			docName = doc.getPathname();
			Long orgid = doc.getOrgid();
			if (orgid == 0) {
				orgName = "深水咨询";
			} else {
				SysOrg org = sysOrgService.getById(orgid);
				if (org != null) {
					orgName = org.getOrgPathname();
				}
			}

		}
		DocFile docFile = docFileService.getById(id);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/makshi/doc/docinfoFileEdit.jsp");
		mv.addObject("docFile", docFile).addObject("returnUrl", returnUrl).addObject("docId", docId).addObject("docName", docName).addObject("orgName", orgName);
		return mv;
	}

	/**
	 * 文档详情
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("filedetail")
	@Action(description = "文档详情")
	public ModelAndView filedetail(HttpServletRequest request) throws Exception {
		Long id = RequestUtil.getLong(request, "id");

		// boolean isWrite = RequestUtil.getBoolean(request, "isWrite", false);
		DocFile docFile = docFileService.getById(id);
		String returnUrl = RequestUtil.getPrePage(request);
		String lastReturnUrl = RequestUtil.getString(request, "lastReturnUrl", "");
		if (lastReturnUrl != null && lastReturnUrl.length() > 0) {
			if (lastReturnUrl.indexOf("/platform/console/home.ht") < 0) {
				returnUrl = lastReturnUrl;
			}
		}
		ModelAndView mv = getAutoView();
		Long docorgId = null;
		if (docFile != null) {
			long docId = docFile.getDocid();
			Doc doc = docService.getById(docId);
			String docName = "";
			String orgName = "";
			if (doc != null) {
				docorgId = doc.getOrgid();
				docName = doc.getPathname();
				Long orgid = doc.getOrgid();
				if (orgid == 0) {
					orgName = "深水咨询";
				} else {
					SysOrg org = sysOrgService.getById(orgid);
					if (org != null) {
						orgName = org.getOrgPathname();
					}
				}
			}

			// 判断权限
			Long currentUserId = ContextUtil.getCurrentUserId();
			// Long currentOrgId =
			// ContextUtil.getCurrentOrgId()==0l?-1l:ContextUtil.getCurrentOrgId();//主岗位组织id
			List<SysOrg> userorgs = getOrgIdByUserId();
			boolean isWrite = true;
			if (isOrgDocAdmin(docorgId)) {
				isWrite = true;
			} else {
				isWrite = docFileService.isWriteFile(id, currentUserId, userorgs);
			}
			boolean isCollection = collectionService.isCollection(ContextUtil.getCurrentUser().getUserId(), id);

			mv.addObject("docFile", docFile).addObject("returnUrl", returnUrl).addObject("docId", docId).addObject("docName", docName).addObject("orgName", orgName)
					.addObject("isCollection", isCollection).addObject("isWrite", isWrite);
		} else {
			mv.addObject("docFile", null).addObject("returnUrl", returnUrl);
		}
		return mv;
	}

	/**
	 * 添加或更新文档信息。
	 * 
	 * @param request
	 * @param response
	 * @param doc 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("filesave")
	@Action(description = "添加或更新文档目录信息")
	public void filesave(HttpServletRequest request, HttpServletResponse response, DocFile docFile) throws Exception {
		String resultMsg = null;
		String type = RequestUtil.getString(request, "type");
		if ("preview".equals(type)) {
			request.setAttribute("docFile", docFile);
			request.getRequestDispatcher("/WEB-INF/view/makshi/doc/docinfopreview.jsp").forward(request, response);
			return;
		}
		try {
			ISysUser currentUser = ContextUtil.getCurrentUser();
			if (docFile.getDfid() == null) {
				docFile.setIsdelete(0);
				docFile.setCreatetime(new Date());
				docFile.setCreator(currentUser.getFullname());
				docFile.setCreatorid(currentUser.getUserId() + "");
				docFile.setVersion(1);
				docFileService.save(docFile);
				recordLogs(docFile, 0, request);
				resultMsg = getText("添加", "文档信息");

			} else {
				DocFile tempFile = docFileService.getById(docFile.getDfid());
				if (tempFile.getVersion() > docFile.getVersion()) {
					resultMsg = getText("更新", "修改文档失败，该版本已被修改");
					writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Fail);
					return;
				} else {
					docFile.setUpdateor(currentUser.getFullname());
					docFile.setUpdateorid(currentUser.getUserId() + "");
					docFile.setUpdatetime(new Date());
					docFile.setVersion(docFile.getVersion() + 1);
					docFileService.update(docFile);
					recordLogs(docFile, 1, request);
					resultMsg = getText("更新", "文档信息");
				}
			}
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
		}
	}

	/**
	 * 删除文档
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description = "删除文档")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
			docFileService.deleteDocFile(lAryId);
			List<DocFile> list = docFileService.getDocFilesByIds(lAryId);
			if (list != null) {
				for (DocFile docFile : list) {
					recordLogs(docFile, 2, request);
				}
			}
			message = new ResultMessage(ResultMessage.Success, "删除文档成功!");
		} catch (Exception ex) {
			message = new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	/**
	 * 删除文档
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("backDel")
	@Action(description = "还原删除文档")
	public void backDel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		try {
			Long id = RequestUtil.getLong(request, "id");
			docFileService.backDelById(id);
			DocFile docFile = docFileService.getById(id);
			if (docFile != null) {
				recordLogs(docFile, 3, request);
			}
			message = new ResultMessage(ResultMessage.Success, "还原文档成功!");
		} catch (Exception ex) {
			message = new ResultMessage(ResultMessage.Fail, "还原文档失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	/**
	 * 删除文档目录
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("delDoc")
	@Action(description = "删除文档目录")
	public void delDoc(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		try {
			Long docId = RequestUtil.getLong(request, "docid");
			Doc doc = docService.getById(docId);
			if (doc == null) {
				message = new ResultMessage(ResultMessage.Fail, "该目录不存在或者已经被删除！");
				writeResultMessage(response.getWriter(), message);
				return;
			}
			// 级联删除子目录及其文档
			delDocAndRealationDocs(doc, request);

			message = new ResultMessage(ResultMessage.Success, "删除目录及其所有子目录成功");
		} catch (Exception e) {
			message = new ResultMessage(ResultMessage.Fail, "删除目录及其所有子目录失败" + e.getMessage());
		}
		writeResultMessage(response.getWriter(), message);
	}

	private void delDocAndRealationDocs(Doc doc, HttpServletRequest request) {
		if (doc.getDoctype() == 1) {// 文档目录
			List<DocFile> list3 = docFileService.getByDocId(doc.getDocid(), -1, -1);
			for (DocFile docFile : list3) {
				docFileService.deleteDocFile(new Long[] { docFile.getDfid() });
				recordLogs(docFile, 2, request);
			}
		} else {
			List<Doc> list = docService.getDocBySupId(doc.getDocid());
			if (list != null && list.size() > 0) {
				for (Doc doc2 : list) {
					if (doc2.getDoctype() == 1) {// 文档目录
						List<DocFile> list2 = docFileService.getByDocId(doc2.getDocid(), -1, -1);
						for (DocFile docFile : list2) {
							docFileService.deleteDocFile(new Long[] { docFile.getDfid() });
							recordLogs(docFile, 2, request);
						}
						docService.delById(doc2.getDocid());
						recordLogs(doc2, 2, request);
					} else {
						delDocAndRealationDocs(doc2, request);
					}
				}
			}
		}
		// 删除本目录
		docService.delById(doc.getDocid());
		recordLogs(doc, 2, request);
	}

	/**
	 * 删除文档
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("collection")
	@Action(description = "收藏文档")
	public void collection(HttpServletRequest request, HttpServletResponse response) throws Exception {
		long id = RequestUtil.getLong(request, "id");
		DocFile docFile = docFileService.getById(id);
		Long userId = ContextUtil.getCurrentUser().getUserId();
		if (docFile != null) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("id", id);
			params.put("userid", userId);
			DocCollection collection = collectionService.getByIdAndUserid(params);
			if (null == collection) {
				collection = new DocCollection();
				collection.setCtime(new Date());
				collection.setDocid(id);
				collection.setUserid(userId);
				collection.setState(1);
				collection.setType(1);
				collectionService.save(collection);
				response.getWriter().print("ok");
				return;
			} else {
				Integer state = collection.getState();
				if (state == 1) {
					collection.setState(0);
					collectionService.save(collection);
					response.getWriter().print("cancel");
				} else {
					collection.setState(1);
					collectionService.save(collection);
					response.getWriter().print("ok");
				}
				return;
			}
		} else {
			response.getWriter().print("文件已经不存在了！");
		}
	}

	@RequestMapping("getDocTree")
	@Action(description = "文档树")
	public @ResponseBody
	List<Doc> getDocTree(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Doc> docs = new ArrayList<>();
		Long orgId = RequestUtil.getLong(request, "orgId");
		Long supid = RequestUtil.getLong(request, "docid");
		String docname = RequestUtil.getString(request, "docname");

		if (supid == 0 && !"文档目录".equals(docname)) {
			// 根节点
			Doc doc = new Doc();
			doc.setDocid(0L);
			doc.setDocname("文档目录");
			doc.setIsRoot((short) 1);
			doc.setDocsupid(-1L);
			doc.setPath("0.");
			docs.add(doc);
		}
		String type = RequestUtil.getString(request, "type");
		if ("getAll".equals(type) && supid == 0) {
			supid = null;// 一次查询出部门下所有目录节点
		}
		List<Doc> docsByOrg = docService.getDocsByOrg(orgId, supid);
		if (docsByOrg != null && docsByOrg.size() > 0) {
			docs.addAll(docsByOrg);
		}

		return docs;
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
		Long currentUserId = ContextUtil.getCurrentUserId();
		if (currentUserId != 1l) {
			Iterator<SysOrg> iterator = orgs.iterator();
			while (iterator.hasNext()) {
				if (!isOrgDocAdmin(iterator.next().getOrgId())) {
					iterator.remove();
				}
			}
		}

		return getAutoView().addObject("orgs", orgs);

	}

	@RequestMapping("toSetUpdate")
	@Action(description = "去修改目录设置页面")
	public ModelAndView toSetUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long id = RequestUtil.getLong(request, "id");
		Doc doc = docService.getById(id);
		return getAutoView().addObject("docinfo", doc);
	}

	@RequestMapping("getEmpty")
	@Action(description = "空页面")
	public ModelAndView getEmpty(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return getAutoView();
	}

	/**
	 * 获取首页展示的通知公告和最新文档
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("getfilelistindex")
	@Action(description = "获取文档列表")
	public void getFileListIndex(HttpServletRequest request, HttpServletResponse response) throws Exception { // type
																												// 3：最新文档
																												// ,10:通知公告
		int type = RequestUtil.getInt(request, "type");
		QueryFilter queryFilter = new QueryFilter(request);
		List<DocFile> list = null;
		Map<String, Object> params = new HashMap<>();
		Map<String, Object> filters = queryFilter.getFilters();
		String orderField = (String) filters.get("orderField");
		String orderSeq = (String) filters.get("orderSeq");
		Long userId = ContextUtil.getCurrentUser().getUserId();
		if (!StringUtils.isEmpty(orderField) && !StringUtils.isEmpty(orderSeq)) {
			params.put("orderField", orderField);
			params.put("orderSeq", orderSeq);
		}
		PageBean pageBean = new PageBean();
		pageBean.setCurrentPage(1);
		pageBean.setPagesize(3);
		queryFilter.setPageBean(pageBean);
		params.put("status", "1");
		if (type == 3) {// 最新文档
			params.put("userid", userId);
			params.put("isdelete", 0);
			if (!params.containsKey("orderField") && !params.containsKey("orderSeq")) {
				params.put("orderField", "wf.`CREATETIME`");
				params.put("orderSeq", "desc");
			}
			list = docFileService.getDocFileList(params, queryFilter);
		} else if (type == 100) {// 获取公司通知文档
			/*
			 * params.put("docName", "公司通知"); params.put("isdelete", 0);
			 * if(!params.containsKey("orderField") &&
			 * !params.containsKey("orderSeq")){ params.put("orderField",
			 * "wf.`CREATETIME`"); params.put("orderSeq", "desc"); }
			 */
			Doc doc = docService.getCommonDocByName("公司通知");
			if (doc != null) {
				list = docFileService.getByDocId(doc.getDocid(), queryFilter);
			}
		}
		int count = queryFilter.getPageBean().getTotalCount();
		JSONArray json = JSONArray.fromObject(list);
		String resultMsg = json.toString();
		String rstJson = "{\"total\":" + count + ",\"data\":" + resultMsg + "}";
		writeResultMessage(response.getWriter(), rstJson, ResultMessage.Success);
	}

	@RequestMapping("getfilelist")
	@Action(description = "获取文档列表")
	public ModelAndView getFileList(HttpServletRequest request, HttpServletResponse response) throws Exception { // type
																													// 0:我的文档
																													// 1：回收站
																													// 2：我的收藏
																													// 3：我的最新文档
																													// 4：公司最新文档
		int type = RequestUtil.getInt(request, "type");
		QueryFilter queryFilter = new QueryFilter(request, "docFile");
		List<DocFile> list = new ArrayList<>();
		Map<String, Object> params = new HashMap<>();
		Map<String, Object> filters = queryFilter.getFilters();
		String orderField = (String) filters.get("orderField");
		String orderSeq = (String) filters.get("orderSeq");
		Long userId = ContextUtil.getCurrentUser().getUserId();
		if (!StringUtils.isEmpty(orderField) && !StringUtils.isEmpty(orderSeq)) {
			params.put("orderField", orderField);
			params.put("orderSeq", orderSeq);
		}
		if (type == 0) {
			params.put("userid", userId);
			params.put("isdelete", 0);
			list = docFileService.getDocFileList(params, queryFilter);
		} else if (type == 1) {
			params.put("isdelete", 1);
			params.put("userid", userId);
			list = docFileService.getDocFileList(params, queryFilter);
		} else if (type == 2) {
			PageBean pageBean = queryFilter.getPageBean();
			params.putAll(queryFilter.getFilters());
			params.put("userid", userId);
			List<DocFile> tempList = docFileService.getCollectedDocFiles(params);
			// 判断权限
			Long currentUserId = ContextUtil.getCurrentUserId();
			// Long currentOrgId =
			// ContextUtil.getCurrentOrgId()==0l?-1l:ContextUtil.getCurrentOrgId();//主岗位组织id
			List<SysOrg> userorgs = getOrgIdByUserId();
			Iterator<DocFile> iterator1 = tempList.iterator();
			while (iterator1.hasNext()) {
				DocFile temp = iterator1.next();
				Doc byId = docService.getById(temp.getDocid());
				if (byId == null) {
					iterator1.remove();
				} else {
					if (!isOrgDocAdmin(byId.getOrgid())) {
						if (!docFileService.isReadFile(temp.getDfid(), currentUserId, userorgs)) {
							iterator1.remove();
						}
					}
				}
			}
			// 过滤出状态为正常的文件
			Iterator<DocFile> iterator = tempList.iterator();
			while (iterator.hasNext()) {
				DocFile temp = iterator.next();
				if (temp.getStatus() == 1 || (currentUserId + "").equals(temp.getCreatorid())) {

				} else {
					iterator.remove();
				}
			}
			// 矫正分页参数 物理分页
			pageBean.setTotalCount(tempList.size());
			int startIndex = (pageBean.getCurrentPage() - 1) * pageBean.getPageSize();
			int endIndex = startIndex + pageBean.getPageSize() - 1;
			if (startIndex < tempList.size()) {
				for (int i = startIndex; i < tempList.size(); i++) {
					if (i >= startIndex && i <= endIndex) {
						list.add(tempList.get(i));
					}
				}
			}
		} else if (type == 3) {
			params.put("userid", userId);
			params.put("isdelete", 0);
			if (!params.containsKey("orderField") && !params.containsKey("orderSeq")) {
				params.put("orderField", "wf.`CREATETIME`");
				params.put("orderSeq", "desc");
			}
			list = docFileService.getDocFileList(params, queryFilter);
		} else if (type == 100) {// 获取公司通知文档
			/*
			 * params.put("docName", "公司通知"); params.put("isdelete", 0);
			 * if(!params.containsKey("orderField") &&
			 * !params.containsKey("orderSeq")){ params.put("orderField",
			 * "wf.`CREATETIME`"); params.put("orderSeq", "desc"); } list =
			 * docFileService.getDocFileList(params, queryFilter);
			 */
			Doc doc = docService.getCommonDocByName("公司通知");
			if (doc != null) {
				list = docFileService.getByDocId(doc.getDocid(), queryFilter);
			}
		} else if (type == 4) {
			// 公司最新文档
			PageBean pageBean = queryFilter.getPageBean();
			params.put("isdelete", 0);
			if (!params.containsKey("orderField") && !params.containsKey("orderSeq")) {
				params.put("orderField", "wf.`CREATETIME`");
				params.put("orderSeq", "desc");
			}
			List<DocFile> tempList = docFileService.getDocFileList(params);
			// 判断权限
			// Long currentOrgId =
			// ContextUtil.getCurrentOrgId()==0l?-1l:ContextUtil.getCurrentOrgId();//主岗位组织id
			List<SysOrg> userorgs = getOrgIdByUserId();
			Iterator<DocFile> iterator1 = tempList.iterator();
			while (iterator1.hasNext()) {
				DocFile next = iterator1.next();
				Doc byId = docService.getById(next.getDocid());
				if (byId == null) {
					iterator1.remove();
				} else {
					if (!isOrgDocAdmin(byId.getOrgid())) {
						if (!docFileService.isReadFile(next.getDfid(), userId, userorgs)) {
							iterator1.remove();
						}
					}
				}
			}
			// 过滤出状态为正常的文件
			Iterator<DocFile> iterator = tempList.iterator();
			while (iterator.hasNext()) {
				DocFile temp = iterator.next();
				if (temp.getStatus() == 1) {

				} else {
					iterator.remove();
				}
			}
			// 矫正分页参数 物理分页
			pageBean.setTotalCount(tempList.size());
			int startIndex = (pageBean.getCurrentPage() - 1) * pageBean.getPageSize();
			int endIndex = startIndex + pageBean.getPageSize() - 1;
			if (startIndex < tempList.size()) {
				for (int i = startIndex; i < tempList.size(); i++) {
					if (i >= startIndex && i <= endIndex) {
						list.add(tempList.get(i));
					}
				}
			}
		}

		return getAutoView().addObject("docFileList", list).addObject("type", type).addObject("pageBeandocFile", queryFilter.getPageBean())// 此两项用于分页标签
				.addObject("requestURIdocFile", request.getRequestURI());
	}

	/**
	 * 记录文档目录及文档操作日志
	 * 
	 * @param file 文档目录或者文档对象
	 * @param operType 操作类型 0：新增 1：修改 2：删除 3：还原
	 */
	private void recordLogs(Object file, int operType, HttpServletRequest request) {
		if (file == null) {
			return;
		}
		DocLogs log = new DocLogs();
		log.setId(UniqueIdUtil.genId());
		String ipAddr = IPUtils.getIpAddr(request);
		log.setIp(ipAddr);
		log.setOperType(operType);
		log.setUserName(ContextUtil.getCurrentUser().getFullname());
		log.setUserId(ContextUtil.getCurrentUser().getUserId());
		log.setCtime(new Date());
		if (file instanceof DocFile) {
			DocFile temp = (DocFile) file;
			log.setRefid(temp.getDfid());
			log.setTitle(temp.getTitle());
			log.setType(1);
		} else if (file instanceof Doc) {
			Doc temp = (Doc) file;
			log.setRefid(temp.getDocid());
			log.setTitle(temp.getDocname());
			log.setPathname(temp.getPathname());
			log.setType(0);
		} else {
			return;
		}
		docLogsService.add(log);
	}

	/**
	 * 获取目录文档
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("docloglist")
	@Action(description = "获取目录日志")
	public ModelAndView docLoglist(HttpServletRequest request, HttpServletResponse response) throws Exception {

		long refid = RequestUtil.getLong(request, "refid", 0);
		long type = RequestUtil.getInt(request, "type", 0);
		List<DocLogs> docLogs = null;
		Map<String, Object> params = new HashMap<>();
		QueryFilter queryFilter = new QueryFilter(request, "docLog");
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/makshi/doc/docinfoLogs.jsp");
		if (type == 0) {
			// 目录日志
			params.put("refid", refid);
			params.put("type", 0);
			docLogs = docLogsService.getDocLogs(params, queryFilter);
		} else if (type == 1) {
			// 文档日志
			params.put("refid", refid);
			params.put("type", 1);
			docLogs = docLogsService.getDocLogs(params, queryFilter);
		}

		mv.addObject("docLogList", docLogs).addObject("type", type).addObject("pageBeandocLog", queryFilter.getPageBean())// 此两项用于分页标签
				.addObject("requestURIdocLog", request.getRequestURI());
		return mv;
	}

	/**
	 * 判断当前用户是否为指定部门的文档管理员或者超级管理员
	 * 
	 * @return
	 */
	public boolean isOrgDocAdmin(Long orgId) {
		Long currentUserId = ContextUtil.getCurrentUserId();
		if (currentUserId == 1l) {// 超级管理员
			return true;
		}
		List<SysRole> byUserId = sysRoleService.getByUserId(currentUserId);
		if (byUserId == null || byUserId.size() == 0) {
			return false;
		}
		if (orgId == 0) {// 深水咨询
			for (SysRole sysRole : byUserId) {
				if (("深水咨询-文档管理员").equals(sysRole.getRoleName())) {
					return true;
				}
			}
			return false;
		}
		SysOrg byId = sysOrgService.getById(orgId);
		if (byId == null) {
			return false;
		} else {
			String orgName = byId.getOrgName();
			boolean flag = false;
			for (SysRole sysRole : byUserId) {
				if ((orgName + "-文档管理员").equals(sysRole.getRoleName())) {
					flag = true;
					break;
				}
			}
			return flag;
		}
	}

	/**
	 * 根据当前用户id获取所属组织
	 * 
	 * @return
	 */
	public List<SysOrg> getOrgIdByUserId() {
		Long currentUserId = ContextUtil.getCurrentUserId();
		List<SysOrg> orgsByUserId = sysOrgService.getOrgsByUserId(currentUserId);
		return orgsByUserId;
	}

	@RequestMapping("getDocFilesByNum")
	public ModelAndView getDocFilesByNum(@RequestParam(required = false, value = "filenum") String filenum) {
		return new ModelAndView("makshi/help/flow/flow-help.jsp").addObject("docfile", docFileService.getDocFilesByNum(filenum));
	}

	@RequestMapping("isExistByNum")
	public void isExistByNum(@RequestParam(required = false, value = "filenum") String filenum, HttpServletRequest request, HttpServletResponse response) {
		try {
			DocFile file = docFileService.getDocFilesByNum(filenum);
			if (null != file) {
				writeResultMessage(response.getWriter(), "success", 1);
				return;
			}
			writeResultMessage(response.getWriter(), "failed", 0);
		} catch (IOException e) {
			log.error("错误信息",e);
		}
	}
}