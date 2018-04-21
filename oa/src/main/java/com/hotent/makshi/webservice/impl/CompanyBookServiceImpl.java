package com.hotent.makshi.webservice.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.apache.log4j.Logger;

import com.hotent.core.web.query.QueryFilter;
import com.hotent.makshi.model.addrBook.CompanyBook;
import com.hotent.makshi.service.addrBook.CompanyBookService;
import com.hotent.makshi.webservice.api.CompanyBookWebService;
import com.hotent.platform.model.system.Demension;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.service.system.DemensionService;
import com.hotent.platform.service.system.SysOrgService;
import com.hotent.platform.webservice.exception.ApiExcetpion;
import com.hotent.platform.webservice.impl.BaseRuelt;
import com.hotent.platform.webservice.util.ReturnCode;

import net.sf.json.JSONObject;

public class CompanyBookServiceImpl implements CompanyBookWebService {
	private final Logger log = Logger.getLogger(this.getClass());

	@Resource
	private SysOrgService sysOrgService;
	@Context
	private HttpServletRequest request;
	@Resource
	private CompanyBookService companyBookService;
	@Resource
	private DemensionService demensionService;

	@Override
	public String list(String orgId) {
		/*
		 * BaseRuelt result = new BaseRuelt(); Long orgid = RequestUtil.getLong(request, "orgId"); QueryFilter queryFilter = new QueryFilter(request, "orgId"); SysOrg sysOrg =
		 * sysOrgService.getById(orgid); if(sysOrg!=null && "深水咨询".equals(sysOrg.getOrgName())){ queryFilter.getFilters().remove("orgId"); } List<CompanyBook> list =
		 * companyBookService.getAll(queryFilter); for (CompanyBook ls :list) { if(!ls.getOrgName().equals("深水咨询")){ ls.setOrgName(ls.getOrgPathName().replace("/深水咨询/", "")); }else{
		 * ls.setOrgName("公司领导"); } } Map<String, Object> map = new HashMap<String, Object>(); // map.put("list2",getOrgFullTree()); map.put("list", list); result.setResultMap(map,
		 * ReturnCode.SUCCESS);
		 */
		return getOrgFullTree();
	}

	public String getOrgFullTree() {
		BaseRuelt result = new BaseRuelt();
		try {
			List<Demension> demens = demensionService.getAll();
			List<Object> list = new ArrayList<>();
			List<Object> resultList = new ArrayList<>();
			if (demens.size() > 0) {
				for (Demension dement : demens) {
					resultList.addAll(getChilderOrg(dement.getDemId()));
				}
			}
			for (Object res : resultList) {
				Map<String, Object> resmap = (Map<String, Object>) res;
				Map<String, Object> maptemp = new HashMap<>();
				maptemp.put("orgName", resmap.get("orgName"));
				maptemp.put("orgId", resmap.get("orgId"));
				maptemp.put("orgList", new ArrayList<Object>());
				maptemp.put("userList", (List<Object>) resmap.get("userList"));
				list.add(maptemp);
				list.addAll((List<Object>) resmap.get("orgList"));
			}
			Map<String, Object> map = new HashMap<>();
			map.put("list", list);
			result.setResultMap(map, ReturnCode.SUCCESS);
			return result.getResultMap().toString();
		} catch (Exception e) {
			log.error("错误信息", e);
			throw new ApiExcetpion(ReturnCode.SYSTEM_ERROR);
		}
	}

	public List<Object> getChilderOrg(Long demId) {
		List<SysOrg> childList = sysOrgService.getOrgByOrgSupId(demId);
		List<Object> listtemp = new ArrayList<>();
		for (SysOrg sysOrg : childList) {
			List<Map<String, Object>> companyBook = getCompanyByOrgid(sysOrg.getOrgId());
			Map<String, Object> map = new HashMap<>();
			map.put("orgName", sysOrg.getOrgName().equals("深水咨询") ? "公司领导" : sysOrg.getOrgName());
			map.put("orgId", sysOrg.getOrgId());
			List<Object> MoreList = getChilderOrg(sysOrg.getOrgId());
			map.put("orgList", MoreList);
			map.put("userList", companyBook);
			listtemp.add(map);
		}
		return listtemp;
	}

	public List<Map<String, Object>> getCompanyByOrgid(Long orgid) {
		JSONObject object = new JSONObject();
		SysOrg sysOrg = sysOrgService.getById(orgid);
		/*
		 * if(sysOrg!=null && "深水咨询".equals(sysOrg.getOrgName())){ // queryFilter.getFilters().remove("orgId"); }
		 */
		object.accumulate("orgId", orgid);
		QueryFilter queryFilter = new QueryFilter(object);
		List<Map<String, Object>> companyBook = new ArrayList<Map<String, Object>>();
		List<CompanyBook> list = companyBookService.getAll(queryFilter);
		for (CompanyBook ls : list) {
			Map<String, Object> map = new HashMap<>();
			map.put("userName", ls.getUserName());
			map.put("account", ls.getAccount());
			map.put("sex", ls.getSex() == 0 ? "女" : "男");
			if (!ls.getOrgName().equals("深水咨询")) {
				map.put("orgName", ls.getOrgPathName().replace("/深水咨询/", ""));
			} else {
				map.put("orgName", "公司领导");
			}
			map.put("posName", ls.getPosName());
			map.put("phone", ls.getPhone());
			map.put("mobile", ls.getMobile());
			map.put("sjdh", ls.getSjdh());
			companyBook.add(map);
		}
		return companyBook;
	}
}
