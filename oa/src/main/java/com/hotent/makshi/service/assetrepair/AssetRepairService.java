package com.hotent.makshi.service.assetrepair;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.GenericService;
import com.hotent.core.util.BeanUtils;
import com.hotent.makshi.model.assetrepair.AssetRepair;
import com.hotent.makshi.dao.assetrepair.AssetRepairDao;
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
public class AssetRepairService extends WfBaseService<AssetRepair>
{
	@Resource
	private AssetRepairDao dao;
	@Resource
	private BpmService bpmService;
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmBusLinkService bpmBusLinkService;
	public AssetRepairService()
	{
	}
	
	@Override
	protected IEntityDao<AssetRepair,Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 重写getAll方法绑定流程runId
	 * @param queryFilter
	 */
	public List<AssetRepair> getAll(QueryFilter queryFilter){
		List<ProcessTask> tasks = null;
		List<AssetRepair> assetRepairList=super.getAll(queryFilter);
		List<AssetRepair> assetRepairs=new ArrayList<AssetRepair>();
		for(AssetRepair assetRepair:assetRepairList){
			ProcessRun processRun=processRunService.getByBusinessKey(assetRepair.getId().toString());
			if(BeanUtils.isNotEmpty(processRun)){
				if(processRun.getActInstId()!=null){
					tasks = bpmService.getTasks(processRun.getActInstId());
					assetRepair.setRunId(processRun.getRunId());
				}
				if(tasks!=null && tasks.size()>0){
					assetRepair.setState(tasks.get(0).getName());
				}
			}
			assetRepairs.add(assetRepair);
		}
		return assetRepairs;
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
			AssetRepair assetRepair=getAssetRepair(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				assetRepair.setId(genId);
				this.add(assetRepair);
			}else{
				assetRepair.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(assetRepair);
			}
			cmd.setBusinessKey(assetRepair.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取AssetRepair对象
	 * @param json
	 * @return
	 */
	public AssetRepair getAssetRepair(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		AssetRepair assetRepair = (AssetRepair)JSONObject.toBean(obj, AssetRepair.class);
		return assetRepair;
	}
	/**
	 * 保存 资产维护表 信息
	 * @param assetRepair
	 */

	@WorkFlow(flowKey="asset_repair",tableName="asset_repair")
	public void save(AssetRepair assetRepair) throws Exception{
		Long id=assetRepair.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			assetRepair.setId(id);
		    this.add(assetRepair);
		    //添加到业务关联数据表
		    bpmBusLinkService.add(assetRepair.getId().toString(), null , true,  "asset_repair");
		}
		else{
		    this.update(assetRepair);
		}
		BpmResult result=new BpmResult();
		//添加流程变量
//		result.addVariable("", "");
		result.setBusinessKey(id.toString());
		BpmAspectUtil.setBpmResult(result);
	}
}
