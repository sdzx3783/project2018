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
import com.hotent.makshi.dao.selfcheck.WorkSelfcheckMonthItemDao;
import com.hotent.makshi.model.selfcheck.WorkSelfcheckMonthItem;

@Service
public class WorkSelfcheckMonthItemService extends BaseService<WorkSelfcheckMonthItem> {

	@Resource
	private WorkSelfcheckMonthItemDao workSelfcheckMonthItemDao;

	@Override
	protected IEntityDao<WorkSelfcheckMonthItem, Long> getEntityDao() {
		return workSelfcheckMonthItemDao;
	}

	public void delAllByMainId(Long mainId) {
		Map<String, Object> param = new HashMap<>();
		param.put("mainId", mainId);
		workSelfcheckMonthItemDao.update("delAllByMainId", param);
	}

	public List<WorkSelfcheckMonthItem> getAllByMainId(Long mainId) {
		Map<String, Object> param = new HashMap<>();
		param.put("mainId", mainId);
		return workSelfcheckMonthItemDao.getBySqlKey("getAllByMainId", param);

	}

}
