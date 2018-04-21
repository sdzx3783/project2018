
package com.hotent.makshi.dao.questionnaire;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.WfBaseDao;
import com.hotent.makshi.model.questionnaire.QuestionnaireQuestion;

@Repository
public class QuestionnaireQuestionDao extends WfBaseDao<QuestionnaireQuestion>
{
	@Override
	public Class<?> getEntityClass()
	{
		return QuestionnaireQuestion.class;
	}
	
	public void delByQuestionnaireId(Long id){
		String statement=this.getIbatisMapperNamespace() + ".delByQuestionnaireId";
		this.getSqlSessionTemplate().delete(statement, id);
	}

}