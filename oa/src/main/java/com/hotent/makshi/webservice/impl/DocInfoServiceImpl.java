package com.hotent.makshi.webservice.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hotent.core.page.PageBean;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.makshi.model.doc.Doc;
import com.hotent.makshi.model.doc.DocFile;
import com.hotent.makshi.service.doc.DocFileService;
import com.hotent.makshi.service.doc.DocService;
import com.hotent.makshi.utils.DateUtils;
import com.hotent.makshi.webservice.api.DocInfoWebService;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.service.system.SysOrgService;
import com.hotent.platform.webservice.exception.ApiExcetpion;
import com.hotent.platform.webservice.impl.BaseRuelt;
import com.hotent.platform.webservice.util.PropUtils;
import com.hotent.platform.webservice.util.ReturnCode;

public class DocInfoServiceImpl implements DocInfoWebService {
	private final Logger log = Logger.getLogger(this.getClass());

	@Resource
	private DocFileService docFileService;
	@Resource
	private DocService docService;
	@Resource
	private SysOrgService sysOrgService;
	@Context
	private HttpServletRequest request;

	@Override
	public String getlist(String docId) {
		BaseRuelt result = new BaseRuelt();
		if (StringUtils.isBlank(docId)) {
			throw new ApiExcetpion(ReturnCode.PRAMS_IS_NULL, "docId 为空！");
		}
		try {
			Long docid = Long.valueOf(docId);
			QueryFilter queryFilter = new QueryFilter(new net.sf.json.JSONObject());
			int pageNo = RequestUtil.getInt(request, "pageNo", 1);
			int pageSize = RequestUtil.getInt(request, "pageSize", 15);
			PageBean pageBean = new PageBean(pageNo, pageSize);
			queryFilter.setPageBean(pageBean);
			List<DocFile> docFileList = docFileService.getByDocId(docid, queryFilter);
			Map<String, Object> listMap = new HashMap<>();
			List<Map<String, Object>> listtemp = new ArrayList<>();
			for (DocFile list : docFileList) {
				Map<String, Object> listmap = new HashMap<>();
				listmap.put("id", list.getDfid());
				listmap.put("title", list.getTitle());
				listmap.put("createTime", list.getCreatetime() == null ? "" : DateUtils.formatDateS(list.getCreatetime()));
				listmap.put("updateTime", list.getUpdatetime() == null ? "" : DateUtils.formatDateS(list.getUpdatetime()));
				listtemp.add(listmap);
			}
			listMap.put("list", listtemp);
			result.setResultMap(listMap, ReturnCode.SUCCESS);
			return result.getResultMap().toString();
		} catch (Exception e) {
			log.error("错误信息", e);
			throw new ApiExcetpion(ReturnCode.SYSTEM_ERROR);
		}
	}

	@Override
	public String getDetail(String Id) {
		BaseRuelt result = new BaseRuelt();
		Long id = Long.valueOf(Id);
		DocFile docFile = docFileService.getById(id);
		Map<String, Object> restMap = new HashMap<>();
		if (docFile != null) {
			long docId = docFile.getDocid();
			Doc doc = docService.getById(docId);
			String docPathName = "";
			String orgName = "";
			if (doc != null) {
				docPathName = doc.getPathname();
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
			String file = docFile.getFile();
			List<Map<String, Object>> fileList = new ArrayList<>();
			if (StringUtils.isNotBlank(file)) {
				try {
					JSONArray jsonArray = new JSONArray(file);
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject maptemp = (JSONObject) jsonArray.get(i);
						Map<String, Object> map = new HashMap<>();
						map.put("fileId", maptemp.get("id"));
						map.put("fileName", maptemp.get("name"));
						map.put("fileUrl", PropUtils.getPropertyByDirKey("app_file_download_url", "api")+maptemp.get("id"));
						fileList.add(map);
					}
				} catch (JSONException e) {
					log.error("错误信息", e);
				}

			}
			restMap.put("fileList", fileList);
			restMap.put("content",
					StringUtils.isBlank(docFile.getContent()) ? "" : "<!DOCTYPE html><html><head><meta charset=\"UTF-8\"><title>启动流程</title></head><body>" + docFile.getContent() + "</body></html>");
			restMap.put("creator", docFile.getCreator());
			restMap.put("keyword", docFile.getKeyword());
			restMap.put("orgName", orgName);
			restMap.put("docPathName", docPathName);
		}
		result.setResultMap(restMap, ReturnCode.SUCCESS);
		return result.getResultMap().toString();
	}
}
