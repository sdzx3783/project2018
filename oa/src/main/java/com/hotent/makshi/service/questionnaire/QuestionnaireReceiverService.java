package com.hotent.makshi.service.questionnaire;

import java.util.Date;
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
import com.hotent.makshi.dao.questionnaire.QuestionnaireReceiverDao;
import com.hotent.makshi.model.questionnaire.Questionnaire;
import com.hotent.makshi.model.questionnaire.QuestionnaireReceiver;
import com.hotent.platform.model.system.MessageReceiver;
import com.hotent.platform.model.system.MessageSend;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.bpm.BpmBusLinkService;
import com.hotent.platform.service.bpm.BpmService;
import com.hotent.platform.service.bpm.ProcessRunService;


@Service
public class QuestionnaireReceiverService extends WfBaseService<QuestionnaireReceiver>
{
	@Resource
	private QuestionnaireReceiverDao dao;
	
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmBusLinkService bpmBusLinkService;
	@Resource
	private BpmService bpmService;
	public QuestionnaireReceiverService()
	{
	}
	
	@Override
	protected IEntityDao<QuestionnaireReceiver,Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 重写getAll方法绑定流程runId
	 * @param queryFilter
	 */
	public List<QuestionnaireReceiver> getAll(QueryFilter queryFilter){
		List<QuestionnaireReceiver> QuestionnaireReceiverList=super.getAll(queryFilter);
		
		return QuestionnaireReceiverList;
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
			QuestionnaireReceiver QuestionnaireReceiver=getQuestionnaireReceiver(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				QuestionnaireReceiver.setId(genId);
				this.add(QuestionnaireReceiver);
			}else{
				QuestionnaireReceiver.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(QuestionnaireReceiver);
			}
			cmd.setBusinessKey(QuestionnaireReceiver.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取QuestionnaireReceiver对象
	 * @param json
	 * @return
	 */
	public QuestionnaireReceiver getQuestionnaireReceiver(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		QuestionnaireReceiver QuestionnaireReceiver = (QuestionnaireReceiver)JSONObject.toBean(obj, QuestionnaireReceiver.class);
		return QuestionnaireReceiver;
	}
	/**
	 * 保存 问卷调查 信息
	 * @param QuestionnaireReceiver
	 */

	public void save(QuestionnaireReceiver QuestionnaireReceiver) throws Exception{
		Long id=QuestionnaireReceiver.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			QuestionnaireReceiver.setId(id);
		    this.add(QuestionnaireReceiver);
		}
		else{
		    this.update(QuestionnaireReceiver);
		}
	}
	
	/**
	 * 插入addQuestionnaireReceiver数据
	 * 
	 * @param questionnaire
	 * @param curUser
	 * @param receiverOrgName
	 * @throws Exception
	 */
	public void addQuestionnaireReceiver(Questionnaire questionnaire,SysUser curUser, String receiverId, String receiverName,
			String receiverOrgId, String receiverOrgName)
		throws Exception
	{

		String[] idArr = receiverId.split(",");
		String[] nameArr = receiverName.split(",");
		String[] orgIdArr = receiverOrgId.split(",");
		String[] orgNameArr = receiverOrgName.split(",");

		// 插入MessageReceiver
		QuestionnaireReceiver questionnaireReceiver = null;
		if (receiverId.length() > 0){
			for (int i = 0; i < idArr.length; i++){
				questionnaireReceiver = new QuestionnaireReceiver();
				questionnaireReceiver.setId(UniqueIdUtil.genId());
				questionnaireReceiver.setQuestionnaire_id(questionnaire.getId());
				if (StringUtil.isNotEmpty(idArr[i])){
					questionnaireReceiver.setUser_id(idArr[i]);
					if (nameArr.length > i)
						questionnaireReceiver.setUser(nameArr[i]);
					questionnaireReceiver.setReceiver_type(0);
				}
				this.add(questionnaireReceiver);
			}
		}

		if (receiverOrgId.length() > 0){
			for (int i = 0; i < orgIdArr.length; i++){
				questionnaireReceiver = new QuestionnaireReceiver();
				questionnaireReceiver.setId(UniqueIdUtil.genId());
				questionnaireReceiver.setQuestionnaire_id(questionnaire.getId());
				if (StringUtil.isNotEmpty(orgIdArr[i])){
					questionnaireReceiver.setUser_id(orgIdArr[i]);
					if (orgNameArr.length > i)
						questionnaireReceiver.setUser(orgNameArr[i]);
					questionnaireReceiver.setReceiver_type(MessageReceiver.TYPE_ORG.intValue());
				}
				this.add(questionnaireReceiver);
			}
		}
	}
	
	
	public List<QuestionnaireReceiver> getByUid(Long uid){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("uid", uid);
		List<QuestionnaireReceiver> list = dao.getBySqlKey("getByUid", params);
		return list;
	}
	
}
