package com.hotent.makshi.service.title;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.GenericService;
import com.hotent.core.util.BeanUtils;
import com.hotent.makshi.model.title.Practicesteal;
import com.hotent.makshi.dao.title.PracticestealDao;
import com.hotent.core.util.UniqueIdUtil;

import net.sf.json.util.JSONUtils;
import net.sf.ezmorph.object.DateMorpher;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.bpm.model.ProcessTask;
import com.hotent.core.util.StringUtil;
import net.sf.json.JSONObject;


import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.service.bpm.BpmService;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.core.service.WfBaseService;
import com.hotent.core.annotion.WorkFlow;
import com.hotent.platform.service.bpm.util.BpmAspectUtil;
import com.hotent.core.bpm.BpmResult;
import com.hotent.platform.service.bpm.BpmBusLinkService;


@Service
public class PracticestealService extends WfBaseService<Practicesteal>
{
	@Resource
	private PracticestealDao dao;
	
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmBusLinkService bpmBusLinkService;
	@Resource
	private BpmService bpmService;
	
	public PracticestealService()
	{
	}
	
	@Override
	protected IEntityDao<Practicesteal,Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 重写getAll方法绑定流程runId
	 * @param queryFilter
	 */
	public List<Practicesteal> getAll(QueryFilter queryFilter){
		List<Practicesteal> practicestealList=super.getAll(queryFilter);
		List<Practicesteal> practicesteals=new ArrayList<Practicesteal>();
		List<ProcessTask> tasks = null;
		for(Practicesteal practicesteal:practicestealList){
			ProcessRun processRun=processRunService.getByBusinessKey(practicesteal.getId().toString());
			if(BeanUtils.isNotEmpty(processRun)){
				practicesteal.setRunId(processRun.getRunId());
			}
			if(null!= processRun.getActInstId()){
				tasks = bpmService.getTasks(processRun.getActInstId());
			}
			if(tasks!=null && tasks.size()>0){
				if(null!=tasks.get(0).getName()&&!("").equals(tasks.get(0).getName())){
					practicesteal.setStatus(tasks.get(0).getName());
				}
			}else{
				practicesteal.setStatus("流程已结束");
			}
			practicesteals.add(practicesteal);
		}
		return practicesteals;
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
			Practicesteal practicesteal=getPracticesteal(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				practicesteal.setId(genId);
				this.add(practicesteal);
			}else{
				practicesteal.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(practicesteal);
			}
			cmd.setBusinessKey(practicesteal.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取Practicesteal对象
	 * @param json
	 * @return
	 */
	public Practicesteal getPracticesteal(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		Practicesteal practicesteal = (Practicesteal)JSONObject.toBean(obj, Practicesteal.class);
		return practicesteal;
	}
	/**
	 * 保存 个人执业印章申请 信息
	 * @param practicesteal
	 */

	@WorkFlow(flowKey="PracticeSteal",tableName="PracticeSteal")
	public void save(Practicesteal practicesteal) throws Exception{
		Long id=practicesteal.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			practicesteal.setId(id);
		    this.add(practicesteal);
		}
		else{
		    this.update(practicesteal);
		}
	}
}
