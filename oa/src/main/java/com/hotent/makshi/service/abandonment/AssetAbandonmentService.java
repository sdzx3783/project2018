package com.hotent.makshi.service.abandonment;
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
import com.hotent.makshi.model.abandonment.AssetAbandonment;
import com.hotent.makshi.dao.abandonment.AssetAbandonmentDao;
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
public class AssetAbandonmentService extends WfBaseService<AssetAbandonment>
{
	@Resource
	private AssetAbandonmentDao dao;
	@Resource
	private BpmService bpmService;
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmBusLinkService bpmBusLinkService;
	public AssetAbandonmentService()
	{
	}
	
	@Override
	protected IEntityDao<AssetAbandonment,Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 重写getAll方法绑定流程runId
	 * @param queryFilter
	 */
	public List<AssetAbandonment> getAll(QueryFilter queryFilter){
		List<ProcessTask> tasks = null;
		List<AssetAbandonment> assetAbandonmentList=super.getAll(queryFilter);
		List<AssetAbandonment> assetAbandonments=new ArrayList<AssetAbandonment>();
		for(AssetAbandonment assetAbandonment:assetAbandonmentList){
			ProcessRun processRun=processRunService.getByBusinessKey(assetAbandonment.getId().toString());
			if(BeanUtils.isNotEmpty(processRun)){
				tasks = bpmService.getTasks(processRun.getActInstId());
				assetAbandonment.setRunId(processRun.getRunId());
				if(tasks!=null && tasks.size()>0){
					assetAbandonment.setState(tasks.get(0).getName());
				}
				assetAbandonment.setGlobalflowno(processRun.getGlobalFlowNo());
				assetAbandonment.setApplication_time(processRun.getCreatetime());
			}
			assetAbandonments.add(assetAbandonment);
		}
		return assetAbandonments;
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
			AssetAbandonment assetAbandonment=getAssetAbandonment(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				assetAbandonment.setId(genId);
				this.add(assetAbandonment);
			}else{
				assetAbandonment.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(assetAbandonment);
			}
			cmd.setBusinessKey(assetAbandonment.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取AssetAbandonment对象
	 * @param json
	 * @return
	 */
	public AssetAbandonment getAssetAbandonment(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		AssetAbandonment assetAbandonment = (AssetAbandonment)JSONObject.toBean(obj, AssetAbandonment.class);
		return assetAbandonment;
	}
	/**
	 * 保存 资产报废表 信息
	 * @param assetAbandonment
	 */

	@WorkFlow(flowKey="asset_abandonment",tableName="asset_abandonment")
	public void save(AssetAbandonment assetAbandonment) throws Exception{
		Long id=assetAbandonment.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			assetAbandonment.setId(id);
		    this.add(assetAbandonment);
		    //添加到业务关联数据表
		    bpmBusLinkService.add(assetAbandonment.getId().toString(), null , true,  "asset_abandonment");
		}
		else{
		    this.update(assetAbandonment);
		}
		BpmResult result=new BpmResult();
		//添加流程变量
//		result.addVariable("", "");
		result.setBusinessKey(id.toString());
		BpmAspectUtil.setBpmResult(result);
	}
	
	/*public void add (AssetAbandonment assetAbandonment){
		dao.insert("add", assetAbandonment);
	}*/
}
