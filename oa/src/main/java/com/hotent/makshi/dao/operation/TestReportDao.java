
package com.hotent.makshi.dao.operation;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.BaseDao;

import com.hotent.makshi.model.operation.TestReport;

@Repository
public class TestReportDao extends BaseDao<TestReport>
{
	@Override
	public Class<?> getEntityClass()
	{
		return TestReport.class;
	}

}