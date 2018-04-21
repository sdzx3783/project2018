
package com.hotent.makshi.dao.questionnaire;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.WfBaseDao;
import com.hotent.makshi.model.questionnaire.QuestionnaireResult;

@Repository
public class QuestionnaireResultDao extends WfBaseDao<QuestionnaireResult>
{
	@Override
	public Class<?> getEntityClass()
	{
		return QuestionnaireResult.class;
	}

}