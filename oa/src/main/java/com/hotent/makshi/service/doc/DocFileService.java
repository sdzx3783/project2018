package com.hotent.makshi.service.doc;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.makshi.dao.doc.DocFileDao;
import com.hotent.makshi.model.doc.Doc;
import com.hotent.makshi.model.doc.DocFile;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.service.system.SysOrgService;

@Service
public class DocFileService extends BaseService<DocFile> {
	@Resource
	private DocFileDao dao;
	@Resource
	private DocFileService docFileService;
	@Resource
	private DocService docService;
	@Resource
	private SysOrgService sysOrgService;

	public DocFileService() {

	}

	@Override
	protected IEntityDao<DocFile, Long> getEntityDao() {
		return dao;
	}

	/**
	 * 保存 文档目录
	 * 
	 * @param partyFiles
	 */

	public void save(DocFile docFile) throws Exception {
		Long id = docFile.getDfid();
		if (id == null || id == 0) {
			id = UniqueIdUtil.genId();
			docFile.setDfid(id);
			this.add(docFile);
		} else {
			this.update(docFile);
		}
	}

	public void deleteDocFile(Long[] lAryId) {
		if (lAryId == null || lAryId.length == 0) {
			return;
		}
		Long userId = ContextUtil.getCurrentUser().getUserId();
		String fullname = ContextUtil.getCurrentUser().getFullname();
		Date now = new Date();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userid", userId);
		params.put("fullname", fullname);
		params.put("dfids", lAryId);
		params.put("updatetime", now);
		dao.getBySqlKey("delfileByIds", params);
	}

	public List<DocFile> getDocFilesByIds(Long[] lAryId) {
		if (lAryId.length <= 0) {
			return null;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("dfids", lAryId);
		return dao.getBySqlKey("getDocFilesByIds", params);
	}

	public DocFile getDocFilesByNum(String filenum) {
		if (StringUtils.isEmpty(filenum)) {
			return null;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("filenum", filenum);
		return (DocFile) dao.getOne("getDocFilesByNum", params);
	}

	public List<DocFile> getByDocId(long docId, QueryFilter queryFilter) {
		Map<String, Object> params = new HashMap<String, Object>();
		Object obj = queryFilter.getFilters().get("showdraft");
		Object creator = queryFilter.getFilters().get("creator");
		Object begincreatetime = queryFilter.getFilters().get("begincreatetime");
		Object endcreatetime = queryFilter.getFilters().get("endcreatetime");
		Object title = queryFilter.getFilters().get("title");
		params.put("status", 1);
		if (obj != null) {
			// 文档目录入口里的文件列表 不过滤自己的草稿文件
			params.put("showdraft", (Long) obj);
		}
		if (creator != null) {
			params.put("creator", (String) creator);
		}
		if (begincreatetime != null) {
			params.put("begincreatetime", (Date) begincreatetime);
		}
		if (endcreatetime != null) {
			params.put("endcreatetime", (Date) endcreatetime);
		}
		if (title != null) {
			params.put("title", (String) title);
		}
		params.put("docId", docId);

		List<DocFile> docFileList = dao.getBySqlKey("getByDocId", params, queryFilter.getPageBean());
		return docFileList;
	}

	public List<DocFile> getByDocId(long docId, int pageStart, int pageSize) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("docId", docId);
		params.put("pageStart", pageStart);
		params.put("pageSize", pageSize);
		params.put("orderField", "createtime");
		params.put("orderSeq", "desc");
		List<DocFile> docFileList = dao.getBySqlKey("getByDocIdWithPage", params);
		return docFileList;
	}

	public List<DocFile> getByDocIdForPortal(long docId, int pageStart, int pageSize) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("docId", docId);
		params.put("status", 1);// 门户里只取正常状态的文档
		params.put("pageStart", pageStart);
		params.put("pageSize", pageSize);
		params.put("orderField", "createtime");
		params.put("orderSeq", "desc");
		List<DocFile> docFileList = dao.getBySqlKey("getByDocIdWithPage", params);
		return docFileList;
	}

	public List<DocFile> getDocFileList(Map<String, Object> params, QueryFilter queryFilter) {
		params.putAll(queryFilter.getFilters());
		List<DocFile> list = dao.getBySqlKey("getDocFileList", params, queryFilter.getPageBean());
		return list;
	}

	public List<DocFile> getDocFileList(Map<String, Object> params) {
		List<DocFile> list = dao.getBySqlKey("getDocFileList", params);
		return list;
	}

	public List<DocFile> getDocFileList(Map<String, Object> params, int pageStart, int pageSize) {
		params.put("pageStart", pageStart);
		params.put("pageSize", pageSize);
		List<DocFile> list = dao.getBySqlKey("getDocFileListWithPage", params);
		return list;
	}

	public List<DocFile> getCollectedDocFiles(Map<String, Object> params, QueryFilter queryFilter) {
		List<DocFile> list = dao.getBySqlKey("getCollectedDocFiles", params, queryFilter.getPageBean());
		return list;
	}

	public List<DocFile> getCollectedDocFiles(Map<String, Object> params) {
		List<DocFile> list = dao.getBySqlKey("getCollectedDocFiles", params);
		return list;
	}

	/**
	 * 判断文件有无读权限
	 * 
	 * @param docFileId
	 * @param userId
	 * @param orgId
	 * @return
	 */
	public boolean isReadFile(long docFileId, long userId, List<SysOrg> orgs) {
		DocFile docFile = getById(docFileId);

		if (docFile == null)
			return false;
		Long docId = docFile.getDocid();
		return isReadDoc(docId, userId, orgs);

	}

	/**
	 * 判断用户对目录有无读权限
	 * 
	 * @param docFileId
	 * @param userId
	 * @param orgId
	 * @return
	 */
	public boolean isReadDoc(long docId, long userId, List<SysOrg> orgs) {

		Doc doc = docService.getById(docId);
		if (doc == null)
			return false;
		if (isHadReadPermission(doc)) {
			return isReadHadPermissionDoc(docId, userId, orgs);
		} else {
			return isReadDoc(doc.getDocsupid(), userId, orgs);
		}
	}

	/**
	 * 判断目录是否已经配置了读权限
	 * 
	 * @param doc
	 * @return
	 */
	public boolean isHadReadPermission(Doc doc) {
		if (StringUtils.isEmpty(doc.getReadorgs()) && StringUtils.isEmpty(doc.getReadorgsID()) && StringUtils.isEmpty(doc.getReaduser())
				&& StringUtils.isEmpty(doc.getReaduserID())) {
			return false;
		}
		return true;
	}

	/**
	 * 判断目录是否已经配置了写权限
	 * 
	 * @param doc
	 * @return
	 */
	public boolean isHadWritePermission(Doc doc) {
		if (StringUtils.isEmpty(doc.getWriteorgs()) && StringUtils.isEmpty(doc.getWriteorgsID()) && StringUtils.isEmpty(doc.getWriteuser())
				&& StringUtils.isEmpty(doc.getWriteuserID())) {
			return false;
		}
		return true;
	}

	/**
	 * 判断用户对已配置有权限的文件目录有无读权限
	 * 
	 * @param docFileId
	 * @param userId
	 * @param orgId
	 * @return
	 */
	public boolean isReadHadPermissionDoc(long docId, long userId, List<SysOrg> orgs) {
		boolean isRead = false;
		Doc doc = docService.getById(docId);
		if (doc == null)
			return false;

		if (doc.getCreatorid().longValue() == userId) {
			return true;
		}
		if (doc.getReaduserID().contains(userId + "")) {// 人员
			isRead = true;
		} else {// 部门
			if (orgs == null || orgs.size() == 0) {
				return false;
			}
			for (SysOrg _sysOrg : orgs) {

				Long tempOrgId = _sysOrg.getOrgId();
				String lastOrgIds = "";
				while (tempOrgId != -1l && tempOrgId != 1l) {// 递归获取上一级部门
					SysOrg org = sysOrgService.getById(tempOrgId);
					if (org != null) {
						lastOrgIds = lastOrgIds + tempOrgId + ",";
						tempOrgId = org.getOrgSupId() == 0l ? -1l : org.getOrgSupId();
					}

				}
				String[] idArray = lastOrgIds.split(",");
				for (int i = 0; i < idArray.length; i++) {// 部门
					String id = idArray[i].trim();
					if (!StringUtils.isEmpty(id)) {
						if (doc.getReadorgsID().contains(id)) {
							isRead = true;
							break;
						}
					}
				}
			}
		}
		return isRead;
	}

	/**
	 * 判断文件目录有无显示权限
	 * 
	 * @param docFileId
	 * @param userId
	 * @param orgId
	 * @return
	 */
	public boolean isViewDoc(long docId, long userId, List<SysOrg> orgs) {
		boolean isView = false;
		Doc doc = docService.getById(docId);
		if (doc == null) {
			return false;
		}
		Long docsupid = doc.getDocsupid();
		if (docsupid == 0) {
			return isReadDoc(docId, userId, orgs);
		}
		while (docsupid != 0) {
			Doc temp = docService.getById(docsupid);
			if (temp == null) {
				return false;
			}
			if (StringUtils.isEmpty(temp.getReadorgs()) && StringUtils.isEmpty(temp.getReadorgs())) {
				docsupid = temp.getDocsupid();
				continue;
			} else {
				return isReadDoc(docsupid, userId, orgs);
			}
		}

		return isView;
	}

	/**
	 * 判断文件有无写权限
	 * 
	 * @param docFileId
	 * @param userId
	 * @param orgId
	 * @return
	 */
	public boolean isWriteFile(long docFileId, long userId, List<SysOrg> orgs) {
		DocFile docFile = getById(docFileId);

		if (docFile == null)
			return false;
		Long docId = docFile.getDocid();
		return isWriteDoc(docId, userId, orgs);

	}

	/**
	 * 判断用户对目录有无读权限
	 * 
	 * @param docFileId
	 * @param userId
	 * @param orgId
	 * @return
	 */
	public boolean isWriteDoc(long docId, long userId, List<SysOrg> orgs) {

		Doc doc = docService.getById(docId);
		if (doc == null)
			return false;
		if (isHadWritePermission(doc)) {
			return isWriteHadPermissionDoc(docId, userId, orgs);
		} else {
			return isWriteDoc(doc.getDocsupid(), userId, orgs);
		}
	}

	/**
	 * 判断用户对已配置有写权限的文件目录有无写权限
	 * 
	 * @param docFileId
	 * @param userId
	 * @param orgId
	 * @return
	 */
	public boolean isWriteHadPermissionDoc(long docId, long userId, List<SysOrg> orgs) {
		boolean isWrite = false;
		Doc doc = docService.getById(docId);
		if (doc == null)
			return false;
		if (doc.getCreatorid().longValue() == userId) {
			return true;
		}

		if (doc.getWriteuserID().contains(userId + "")) {// 人员
			isWrite = true;
		} else {// 部门
			if (orgs == null || orgs.size() == 0) {
				return false;
			}
			for (SysOrg _sysOrg : orgs) {
				Long tempOrgId = _sysOrg.getOrgId();
				String lastOrgIds = "";
				while (tempOrgId != -1l && tempOrgId != 1l) {// 递归获取上一级部门
					SysOrg org = sysOrgService.getById(tempOrgId);
					if (org != null) {
						lastOrgIds = lastOrgIds + tempOrgId + ",";
						tempOrgId = org.getOrgSupId() == 0l ? -1l : org.getOrgSupId();
					}

				}
				String[] idArray = lastOrgIds.split(",");
				for (int i = 0; i < idArray.length; i++) {// 部门
					String id = idArray[i].trim();
					if (!StringUtils.isEmpty(id)) {
						if (doc.getWriteorgsID().contains(id)) {
							isWrite = true;
							break;
						}
					}
				}
			}

		}

		return isWrite;
	}

	public void backDelById(Long id) {
		DocFile docFile = new DocFile();
		docFile.setDfid(id);
		docFile.setIsdelete(0);
		dao.update(docFile);
	}
}
