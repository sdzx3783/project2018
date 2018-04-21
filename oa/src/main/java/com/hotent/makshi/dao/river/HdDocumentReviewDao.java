
package com.hotent.makshi.dao.river;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;

import com.hotent.core.db.WfBaseDao;

import com.hotent.makshi.model.river.HdDocumentReview;

@Repository
public class HdDocumentReviewDao extends WfBaseDao<HdDocumentReview>
{
	@Override
	public Class<?> getEntityClass()
	{
		return HdDocumentReview.class;
	}

}