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
import com.hotent.makshi.dao.questionnaire.QuestionnaireResultDao;
import com.hotent.makshi.model.questionnaire.QuestionnaireResult;
import com.hotent.platform.service.bpm.BpmBusLinkService;
import com.hotent.platform.service.bpm.BpmService;
import com.hotent.platform.service.bpm.ProcessRunService;


@Service
public class QuestionnaireResultService extends WfBaseService<QuestionnaireResult>
{
	@Resource
	private QuestionnaireResultDao dao;
	
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmBusLinkService bpmBusLinkService;
	@Resource
	private BpmService bpmService;
	public QuestionnaireResultService()
	{
	}
	
	@Override
	protected IEntityDao<QuestionnaireResult,Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 重写getAll方法绑定流程runId
	 * @param queryFilter
	 */
	public List<QuestionnaireResult> getAll(QueryFilter queryFilter){
		List<QuestionnaireResult> QuestionnaireResultList=super.getAll(queryFilter);
		
		return QuestionnaireResultList;
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
			QuestionnaireResult QuestionnaireResult=getQuestionnaireResult(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				QuestionnaireResult.setId(genId);
				this.add(QuestionnaireResult);
			}else{
				QuestionnaireResult.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(QuestionnaireResult);
			}
			cmd.setBusinessKey(QuestionnaireResult.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取QuestionnaireResult对象
	 * @param json
	 * @return
	 */
	public QuestionnaireResult getQuestionnaireResult(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		QuestionnaireResult QuestionnaireResult = (QuestionnaireResult)JSONObject.toBean(obj, QuestionnaireResult.class);
		return QuestionnaireResult;
	}
	/**
	 * 保存 问卷调查 信息
	 * @param QuestionnaireResult
	 */

	public void save(QuestionnaireResult QuestionnaireResult) throws Exception{
		Long id=QuestionnaireResult.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			QuestionnaireResult.setId(id);
		    this.add(QuestionnaireResult);
		}
		else{
		    this.update(QuestionnaireResult);
		}
	}
	
	
	public List<QuestionnaireResult> getByUid(Long uid){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("uid", uid);
		List<QuestionnaireResult> list = dao.getBySqlKey("getByUid", params);
		return list;
	}
	
}
