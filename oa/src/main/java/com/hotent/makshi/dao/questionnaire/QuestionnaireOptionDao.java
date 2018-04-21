
package com.hotent.makshi.dao.questionnaire;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.WfBaseDao;
import com.hotent.makshi.model.questionnaire.QuestionnaireOption;

@Repository
public class QuestionnaireOptionDao extends WfBaseDao<QuestionnaireOption>
{
	@Override
	public Class<?> getEntityClass()
	{
		return QuestionnaireOption.class;
	}
	public void delByQuestionnaireId(Long id){
		String statement=this.getIbatisMapperNamespace() + ".delByQuestionnaireId";
		this.getSqlSessionTemplate().delete(statement, id);
	}
}