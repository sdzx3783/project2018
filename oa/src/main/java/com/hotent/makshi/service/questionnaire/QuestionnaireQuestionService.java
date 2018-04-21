package com.hotent.makshi.service.questionnaire;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.springframework.stereotype.Service;

import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.WfBaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.makshi.dao.questionnaire.QuestionnaireQuestionDao;
import com.hotent.makshi.model.questionnaire.QuestionnaireQuestion;
import com.hotent.platform.service.bpm.BpmBusLinkService;
import com.hotent.platform.service.bpm.BpmService;
import com.hotent.platform.service.bpm.ProcessRunService;


@Service
public class QuestionnaireQuestionService extends WfBaseService<QuestionnaireQuestion>
{
	@Resource
	private QuestionnaireQuestionDao dao;
	
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmBusLinkService bpmBusLinkService;
	@Resource
	private BpmService bpmService;
	public QuestionnaireQuestionService()
	{
	}
	
	@Override
	protected IEntityDao<QuestionnaireQuestion,Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 重写getAll方法绑定流程runId
	 * @param queryFilter
	 */
	public List<QuestionnaireQuestion> getAll(QueryFilter queryFilter){
		List<QuestionnaireQuestion> QuestionnaireQuestionList=super.getAll(queryFilter);
		
		return QuestionnaireQuestionList;
	}
	
	public void delByQuestionnaireId(Long id){
		dao.delByQuestionnaireId(id);
	}
		
	
	/**
	 * 流程处理器方法 用于处理业务数据
	 * @param cmd
	 * @throws Exception
	 */
	public void processHandler(ProcessCmd cmd)throws Exception{
		Map data=cmd.getFormDataMap();
		if(BeanUtils.isNotEmpty(data)){
			String json=data.get("json").toString();
			QuestionnaireQuestion QuestionnaireQuestion=getQuestionnaireQuestion(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				QuestionnaireQuestion.setId(genId);
				this.add(QuestionnaireQuestion);
			}else{
				QuestionnaireQuestion.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(QuestionnaireQuestion);
			}
			cmd.setBusinessKey(QuestionnaireQuestion.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取QuestionnaireQuestion对象
	 * @param json
	 * @return
	 */
	public QuestionnaireQuestion getQuestionnaireQuestion(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		QuestionnaireQuestion QuestionnaireQuestion = (QuestionnaireQuestion)JSONObject.toBean(obj, QuestionnaireQuestion.class);
		return QuestionnaireQuestion;
	}
	/**
	 * 保存 问卷调查 信息
	 * @param QuestionnaireQuestion
	 */

	public void save(QuestionnaireQuestion QuestionnaireQuestion) throws Exception{
		Long id=QuestionnaireQuestion.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			QuestionnaireQuestion.setId(id);
		    this.add(QuestionnaireQuestion);
		}
		else{
		    this.update(QuestionnaireQuestion);
		}
	}
	
	
	public List<QuestionnaireQuestion> getByUid(Long uid){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("uid", uid);
		List<QuestionnaireQuestion> list = dao.getBySqlKey("getByUid", params);
		return list;
	}
	
}
