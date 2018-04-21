
package com.hotent.makshi.dao.questionnaire;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.WfBaseDao;
import com.hotent.makshi.model.questionnaire.QuestionnaireReceiver;

@Repository
public class QuestionnaireReceiverDao extends WfBaseDao<QuestionnaireReceiver>
{
	@Override
	public Class<?> getEntityClass()
	{
		return QuestionnaireReceiver.class;
	}
	public void delByQuestionnaireId(Long id){
		String statement=this.getIbatisMapperNamespace() + ".delByQuestionnaireId";
		this.getSqlSessionTemplate().delete(statement, id);
	}
}