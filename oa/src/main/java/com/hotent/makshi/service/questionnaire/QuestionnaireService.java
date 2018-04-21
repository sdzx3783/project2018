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
import com.hotent.makshi.dao.questionnaire.QuestionnaireDao;
import com.hotent.makshi.model.questionnaire.Questionnaire;
import com.hotent.platform.service.bpm.BpmBusLinkService;
import com.hotent.platform.service.bpm.BpmService;
import com.hotent.platform.service.bpm.ProcessRunService;


@Service
public class QuestionnaireService extends WfBaseService<Questionnaire>
{
	@Resource
	private QuestionnaireDao dao;
	
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmBusLinkService bpmBusLinkService;
	@Resource
	private BpmService bpmService;
	public QuestionnaireService()
	{
	}
	
	@Override
	protected IEntityDao<Questionnaire,Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 重写getAll方法绑定流程runId
	 * @param queryFilter
	 */
	public List<Questionnaire> getAll(QueryFilter queryFilter){
		List<Questionnaire> QuestionnaireList=super.getAll(queryFilter);
		
		return QuestionnaireList;
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
			Questionnaire Questionnaire=getQuestionnaire(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				Questionnaire.setId(genId);
				this.add(Questionnaire);
			}else{
				Questionnaire.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(Questionnaire);
			}
			cmd.setBusinessKey(Questionnaire.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取Questionnaire对象
	 * @param json
	 * @return
	 */
	public Questionnaire getQuestionnaire(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		Questionnaire Questionnaire = (Questionnaire)JSONObject.toBean(obj, Questionnaire.class);
		return Questionnaire;
	}
	/**
	 * 保存 问卷调查 信息
	 * @param Questionnaire
	 */

	public void save(Questionnaire Questionnaire) throws Exception{
		Long id=Questionnaire.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			Questionnaire.setId(id);
		    this.add(Questionnaire);
		}
		else{
		    this.update(Questionnaire);
		}
	}
	
	
	public List<Questionnaire> getByUid(Long uid){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("uid", uid);
		List<Questionnaire> list = dao.getBySqlKey("getByUid", params);
		return list;
	}
	
}
