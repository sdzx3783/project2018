/*
 * 版权所有 (C) 2014-2017 深圳掌趣互动科技有限公司。保留所有权利。
 * 版本：
 * 修改记录：
 *		1、2017-12-11，cp创建。 
 */
package com.hotent.makshi.dao.selfcheck;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.makshi.model.selfcheck.WorkSelfcheckMonthItem;

@Repository
public class WorkSelfcheckMonthItemDao extends BaseDao<WorkSelfcheckMonthItem> {
	@Override
	public Class<?> getEntityClass() {
		return WorkSelfcheckMonthItem.class;
	}

}
