/*
 * 版权所有 (C) 2014-2017 深圳掌趣互动科技有限公司。保留所有权利。
 * 版本：
 * 修改记录：
 *		1、2017-12-11，cp创建。 
 */
package com.hotent.makshi.service.selfcheck;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.makshi.dao.selfcheck.WorkSelfcheckMonthMainDao;
import com.hotent.makshi.model.selfcheck.WorkSelfcheckMonthMain;

@Service
public class WorkSelfcheckMonthMainService extends BaseService<WorkSelfcheckMonthMain> {

	@Resource
	private WorkSelfcheckMonthMainDao workSelfcheckMonthMainDao;

	@Override
	protected IEntityDao<WorkSelfcheckMonthMain, Long> getEntityDao() {
		return workSelfcheckMonthMainDao;
	}

	private Map<String, Object> getQueryParams(String userId, String submitAt, Integer status) {
		Map<String, Object> param = new HashMap<>();
		param.put("userId", userId);
		param.put("status", status);
		param.put("submitAt", submitAt);
		return param;
	}

	public int count(String userId, String submitAt, Integer status) {
		return (int) workSelfcheckMonthMainDao.getOne("count", getQueryParams(userId, submitAt, status));
	}

	public List<WorkSelfcheckMonthMain> page(String userId, String submitAt, Integer status, Integer page, Integer pageSize) {
		Map<String, Object> param = getQueryParams(userId, submitAt, status);
		if (page <= 0)
			page = 1;
		param.put("begNum", (page - 1) * pageSize);
		param.put("pageSize", pageSize);
		return workSelfcheckMonthMainDao.getBySqlKey("page", param);
	}

	public Long getLastId(Long userId) {
		Map<String, Object> param = new HashMap<>();
		param.put("userId", userId);
		return (Long) workSelfcheckMonthMainDao.getOne("getLastId", param);
	}

	public void submit(Long id) {
		Map<String, Object> param = new HashMap<>();
		param.put("id", id);
		workSelfcheckMonthMainDao.update("submit", param);
	}

	public int getBySubmitAtNotExculetId(Long id, Long userId, String submitAt) {
		Map<String, Object> param = new HashMap<>();
		param.put("id", id);
		param.put("userId", userId);
		param.put("submitAt", submitAt);
		return (int) workSelfcheckMonthMainDao.getOne("getBySubmitAtNotExculetId", param);
	}

}
