package com.hotent.makshi.service.assetapplication;
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
import com.hotent.makshi.model.assetapplication.AssetAll;
import com.hotent.makshi.dao.assetapplication.AssetAllDao;
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
public class AssetAllService extends WfBaseService<AssetAll>
{
	@Resource
	private AssetAllDao dao;
	
	@Resource
	private BpmService bpmService;
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmBusLinkService bpmBusLinkService;
	public AssetAllService()
	{
	}
	
	@Override
	protected IEntityDao<AssetAll,Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 重写getAll方法绑定流程runId
	 * @param queryFilter
	 */
	public List<AssetAll> getAll(QueryFilter queryFilter){
		List<ProcessTask> tasks = null;
		List<AssetAll> assetAllList=super.getAll(queryFilter);
		List<AssetAll> assetAlls=new ArrayList<AssetAll>();
		for(AssetAll assetAll:assetAllList){
			ProcessRun processRun=processRunService.getByBusinessKey(assetAll.getId().toString());
			if(BeanUtils.isNotEmpty(processRun)){
				tasks = bpmService.getTasks(processRun.getActInstId());
				assetAll.setRunId(processRun.getRunId());
     		/*	assetAll.setGlobalflowno(processRun.getGlobalFlowNo());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				assetAll.setApplication_time(sdf.format(processRun.getCreatetime()));*/
				if(tasks!=null && tasks.size()>0){
					assetAll.setState(tasks.get(0).getName());
				}
			}
			assetAlls.add(assetAll);
		}
		return assetAlls;
	}
	
/*	public String getDepartmentNameByOrgId(Long OrgId){
		return dao.getDepartmentNameByOrgId(OrgId);
	}*/
		
	
	/**
	 * 流程处理器方法 用于处理业务数据
	 * @param cmd
	 * @throws Exception
	 */
	public void processHandler(ProcessCmd cmd)throws Exception{
		Map data=cmd.getFormDataMap();
		if(BeanUtils.isNotEmpty(data)){
			String json=data.get("json").toString();
			AssetAll assetAll=getAssetAll(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				assetAll.setId(genId);
				this.add(assetAll);
			}else{
				assetAll.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(assetAll);
			}
			cmd.setBusinessKey(assetAll.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取AssetAll对象
	 * @param json
	 * @return
	 */
	public AssetAll getAssetAll(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		AssetAll assetAll = (AssetAll)JSONObject.toBean(obj, AssetAll.class);
		return assetAll;
	}
	/**
	 * 保存 资产申购汇总表 信息
	 * @param assetAll
	 */

	@WorkFlow(flowKey="asset_application",tableName="asset_all")
	public void save(AssetAll assetAll) throws Exception{
		Long id=assetAll.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			assetAll.setId(id);
		    this.add(assetAll);
		    //添加到业务关联数据表
		    bpmBusLinkService.add(assetAll.getId().toString(), null , true,  "asset_all");
		}
		else{
		    this.update(assetAll);
		}
		BpmResult result=new BpmResult();
		//添加流程变量
//		result.addVariable("", "");
		result.setBusinessKey(id.toString());
		BpmAspectUtil.setBpmResult(result);
	}
}
