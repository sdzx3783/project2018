package com.hotent.makshi.service.hj;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.GenericService;
import com.hotent.core.util.BeanUtils;
import com.hotent.makshi.model.hj.HjOperationDaily;
import com.hotent.makshi.dao.hj.HjOperationDailyDao;
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
public class HjOperationDailyService extends WfBaseService<HjOperationDaily>
{
	@Resource
	private HjOperationDailyDao dao;
	
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmBusLinkService bpmBusLinkService;
	public HjOperationDailyService()
	{
	}
	
	@Override
	protected IEntityDao<HjOperationDaily,Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 重写getAll方法绑定流程runId
	 * @param queryFilter
	 */
	public List<HjOperationDaily> getAll(QueryFilter queryFilter){
		List<HjOperationDaily> hjOperationDailyList=super.getAll(queryFilter);
		List<HjOperationDaily> hjOperationDailys=new ArrayList<HjOperationDaily>();
		for(HjOperationDaily hjOperationDaily:hjOperationDailyList){
			ProcessRun processRun=processRunService.getByBusinessKey(hjOperationDaily.getId().toString());
			if(BeanUtils.isNotEmpty(processRun)){
				hjOperationDaily.setRunId(processRun.getRunId());
			}
			hjOperationDailys.add(hjOperationDaily);
		}
		return hjOperationDailys;
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
			HjOperationDaily hjOperationDaily=getHjOperationDaily(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				hjOperationDaily.setId(genId);
				this.add(hjOperationDaily);
			}else{
				hjOperationDaily.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(hjOperationDaily);
			}
			cmd.setBusinessKey(hjOperationDaily.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取HjOperationDaily对象
	 * @param json
	 * @return
	 */
	public HjOperationDaily getHjOperationDaily(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		HjOperationDaily hjOperationDaily = (HjOperationDaily)JSONObject.toBean(obj, HjOperationDaily.class);
		return hjOperationDaily;
	}
	/**
	 * 保存 运营日报审核表 信息
	 * @param hjOperationDaily
	 */

	@WorkFlow(flowKey="yyrbsh1",tableName="hj_operation_daily")
	public void save(HjOperationDaily hjOperationDaily) throws Exception{
		Long id=hjOperationDaily.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			hjOperationDaily.setId(id);
		    this.add(hjOperationDaily);
		    //添加到业务关联数据表
		    bpmBusLinkService.add(hjOperationDaily.getId().toString(), null , true,  "hj_operation_daily");
		}
		else{
		    this.update(hjOperationDaily);
		}
		BpmResult result=new BpmResult();
		//添加流程变量
//		result.addVariable("", "");
		result.setBusinessKey(id.toString());
		BpmAspectUtil.setBpmResult(result);
	}
}
