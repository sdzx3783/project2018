package com.hotent.makshi.service.talente;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.GenericService;
import com.hotent.core.util.BeanUtils;
import com.hotent.makshi.model.talente.PersonIn;
import com.hotent.makshi.dao.talente.PersonInDao;
import com.hotent.core.util.UniqueIdUtil;

import net.sf.json.util.JSONUtils;
import net.sf.ezmorph.object.DateMorpher;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.util.StringUtil;
import net.sf.json.JSONObject;


import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.core.service.WfBaseService;
import com.hotent.core.annotion.WorkFlow;
import com.hotent.platform.service.bpm.util.BpmAspectUtil;
import com.hotent.core.bpm.BpmResult;
import com.hotent.platform.service.bpm.BpmBusLinkService;


@Service
public class PersonInService extends WfBaseService<PersonIn>
{
	@Resource
	private PersonInDao dao;
	
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmBusLinkService bpmBusLinkService;
	public PersonInService()
	{
	}
	
	@Override
	protected IEntityDao<PersonIn,Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 重写getAll方法绑定流程runId
	 * @param queryFilter
	 */
	public List<PersonIn> getAll(QueryFilter queryFilter){
		List<PersonIn> personInList=super.getAll(queryFilter);
		List<PersonIn> personIns=new ArrayList<PersonIn>();
		for(PersonIn personIn:personInList){
			ProcessRun processRun=processRunService.getByBusinessKey(personIn.getId().toString());
			if(BeanUtils.isNotEmpty(processRun)){
				personIn.setRunId(processRun.getRunId());
			}
			personIns.add(personIn);
		}
		return personIns;
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
			PersonIn personIn=getPersonIn(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				personIn.setId(genId);
				this.add(personIn);
			}else{
				personIn.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(personIn);
			}
			cmd.setBusinessKey(personIn.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取PersonIn对象
	 * @param json
	 * @return
	 */
	public PersonIn getPersonIn(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		PersonIn personIn = (PersonIn)JSONObject.toBean(obj, PersonIn.class);
		return personIn;
	}
	/**
	 * 保存 人才引进 信息
	 * @param personIn
	 */

	@WorkFlow(flowKey="talent_introduction",tableName="person_in")
	public void save(PersonIn personIn) throws Exception{
		Long id=personIn.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			personIn.setId(id);
		    this.add(personIn);
		    //添加到业务关联数据表
		    bpmBusLinkService.add(personIn.getId().toString(), null , true,  "person_in");
		}
		else{
		    this.update(personIn);
		}
		BpmResult result=new BpmResult();
		//添加流程变量
//		result.addVariable("", "");
		result.setBusinessKey(id.toString());
		BpmAspectUtil.setBpmResult(result);
	}
}
