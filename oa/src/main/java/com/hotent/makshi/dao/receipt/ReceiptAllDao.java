
package com.hotent.makshi.dao.receipt;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.WfBaseDao;

import com.hotent.makshi.model.receipt.ReceiptAll;

@Repository
public class ReceiptAllDao extends WfBaseDao<ReceiptAll>
{
	@Override
	public Class<?> getEntityClass()
	{
		return ReceiptAll.class;
	}

}