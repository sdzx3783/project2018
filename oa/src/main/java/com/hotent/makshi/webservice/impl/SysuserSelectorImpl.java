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
import com.hotent.makshi.webservice.api.SysuserSelectorWebService;
import com.hotent.platform.model.system.Demension;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.model.system.UserPosition;
import com.hotent.platform.service.system.DemensionService;
import com.hotent.platform.service.system.SysOrgService;
import com.hotent.platform.service.system.SysUserService;
import com.hotent.platform.service.system.UserPositionService;
import com.hotent.platform.webservice.exception.ApiExcetpion;
import com.hotent.platform.webservice.impl.BaseRuelt;
import com.hotent.platform.webservice.util.ReturnCode;

import net.sf.json.JSONObject;

public class SysuserSelectorImpl implements SysuserSelectorWebService {
	private final Logger log = Logger.getLogger(this.getClass());

	@Resource
	private SysOrgService sysOrgService;
	@Context
	private HttpServletRequest request;
	@Resource
	private SysUserService sysUserService;
	@Resource
	private DemensionService demensionService;
	@Resource
	private UserPositionService userPositionService;

	@Override
	public String getlist(String account) {
		SysUser sysUser = sysUserService.getByAccount(account);
		if (sysUser == null) {
			throw new ApiExcetpion(ReturnCode.NO_DATA_FOUND, "用户不存在 account：" + account);
		}
		UserPosition userPos = userPositionService.getPrimaryUserPositionByUserId(sysUser.getUserId());
		BaseRuelt result = new BaseRuelt();
		Map<String, Object> map = new HashMap<>();
		map.put("list", getOrgFullTree());
		map.put("userOrgId", userPos.getOrgId());
		result.setResultMap(map, ReturnCode.SUCCESS);
		return result.getResultMap().toString();
	}

	public List<Object> getOrgFullTree() {
		List<Object> list = new ArrayList<>();
		try {
			List<Demension> demens = demensionService.getAll();
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
			return list;
		} catch (Exception e) {
			log.error("错误信息", e);
			throw new ApiExcetpion(ReturnCode.SYSTEM_ERROR);
		}
	}

	public List<Object> getChilderOrg(Long demId) {
		List<SysOrg> childList = sysOrgService.getOrgByOrgSupId(demId);
		List<Object> listtemp = new ArrayList<>();
		for (SysOrg sysOrg : childList) {
			List<Map<String, Object>> userlist = getCompanyByOrgid(sysOrg.getOrgId());
			Map<String, Object> map = new HashMap<>();
			map.put("orgName", sysOrg.getOrgName().equals("深水咨询") ? "公司领导" : sysOrg.getOrgName());
			map.put("orgId", sysOrg.getOrgId());
			List<Object> MoreList = getChilderOrg(sysOrg.getOrgId());
			map.put("orgList", MoreList);
			map.put("userList", userlist);
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
		List<SysUser> list = sysUserService.getDistinctUserByOrgId(queryFilter);
		List<Map<String, Object>> userlist = new ArrayList<Map<String, Object>>();
		// List<CompanyBook> list = companyBookService.getAll(queryFilter);
		String orgPathname = "";
		if (!sysOrg.getOrgName().equals("深水咨询")) {
			orgPathname = sysOrg.getOrgPathname().replace("/深水咨询/", "");
		} else {
			orgPathname = "公司领导";
		}
		for (SysUser ls : list) {
			Map<String, Object> map = new HashMap<>();
			map.put("orgName", orgPathname);
			map.put("userId", ls.getUserId());
			map.put("userName", ls.getFullname());
			map.put("account", ls.getAccount());
			// map.put("posName", ls.getPosName());
			// map.put("phone", ls.getPhone());
			// map.put("mobile", ls.getMobile());
			// map.put("sjdh", ls.getSjdh());
			userlist.add(map);
		}
		return userlist;
	}
}
