package com.hotent.weixin.api;

import java.util.List;

import com.hotent.core.api.org.model.ISysOrg;
import com.hotent.platform.model.system.SysOrg;

public interface IOrgService {
	/**
	 * 新增组织
	 * @param org
	 */
	public void create(SysOrg org);
	/**
	 * 更新组织
	 * @param org
	 */
	public void update(SysOrg org);
	
	/**
	 * 删除组织
	 * @param orgId 用户账户
	 */
	public void delete(String orgId);
	/**
	 * 批量删除
	 * @param orgIds
	 */
	public void deleteAll(String orgIds);
	/**
	 * 批量添加组织
	 * @param sysOrgList
	 */
	void addAll(List<SysOrg> sysOrgList);
}
