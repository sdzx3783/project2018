
package com.hotent.makshi.dao.questionnaire;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.WfBaseDao;
import com.hotent.makshi.model.questionnaire.Questionnaire;

@Repository
public class QuestionnaireDao extends WfBaseDao<Questionnaire>
{
	@Override
	public Class<?> getEntityClass()
	{
		return Questionnaire.class;
	}

}