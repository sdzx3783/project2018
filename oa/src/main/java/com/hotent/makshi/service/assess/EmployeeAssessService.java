/*
 * 版权所有 (C) 2014-2017 深圳掌趣互动科技有限公司。保留所有权利。
 * 版本：
 * 修改记录：
 *		1、2017-12-13，cp创建。 
 */
package com.hotent.makshi.service.assess;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.makshi.dao.assess.EmployeeAssessDao;
import com.hotent.makshi.model.assess.EmployeeAssess;

@Service
@SuppressWarnings({ "unchecked", "rawtypes" })
public class EmployeeAssessService extends BaseService<EmployeeAssess> {

	@Resource
	private EmployeeAssessDao employeeAssessDao;

	@Override
	protected IEntityDao getEntityDao() {
		return employeeAssessDao;
	}

	private Map<String, Object> getQueryParams(String userId, String submitAt, Integer status) {
		Map<String, Object> param = new HashMap<>();
		param.put("userId", userId);
		param.put("status", status);
		if (StringUtils.isNotEmpty(submitAt))
			param.put("submitAt", submitAt.concat("-").concat("01 00:00:00"));
		return param;
	}

	public int count(String userId, String submitAt, Integer status) {
		return (int) employeeAssessDao.getOne("count", getQueryParams(userId, submitAt, status));
	}

	public List<EmployeeAssess> page(String userId, String submitAt, Integer status, Integer page, Integer pageSize) {
		Map<String, Object> param = getQueryParams(userId, submitAt, status);
		if (page <= 0)
			page = 1;
		param.put("begNum", (page - 1) * pageSize);
		param.put("pageSize", pageSize);
		return employeeAssessDao.getBySqlKey("page", param);
	}

	public void submit(Long id) {
		Map<String, Object> param = new HashMap<>();
		param.put("id", id);
		employeeAssessDao.update("submit", param);
	}

	public int getBySubmitAtNotExculetId(Long id, Long userId, Date submitAt) {
		Map<String, Object> param = new HashMap<>();
		param.put("id", id);
		param.put("userId", userId);
		param.put("submitAt", submitAt);
		return (int) employeeAssessDao.getOne("getBySubmitAtNotExculetId", param);
	}
}
