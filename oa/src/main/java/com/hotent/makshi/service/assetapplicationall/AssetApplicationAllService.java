package com.hotent.makshi.service.assetapplicationall;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.bpm.model.ProcessTask;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.WfBaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.makshi.dao.assetapplicationall.AssetApplicationAllDao;
import com.hotent.makshi.model.assetapplicationall.AssetApplicationAll;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.service.bpm.BpmService;
import com.hotent.platform.service.bpm.ProcessRunService;


@Service
public class AssetApplicationAllService extends WfBaseService<AssetApplicationAll>
{
	@Resource
	private AssetApplicationAllDao dao;
	@Resource
	private BpmService bpmService;
	@Resource
	private ProcessRunService processRunService;
	public AssetApplicationAllService()
	{
	}
	
	@Override
	protected IEntityDao<AssetApplicationAll,Long> getEntityDao() 
	{
		return dao;
	}
	public List<AssetApplicationAll> getAllById(Long id){
		return dao.getBySqlKey("getAllById",id);
	}
	
	public List<AssetApplicationAll> getTypeName(String TtemValue){
		return dao.getBySqlKey("getTypeName",TtemValue);
	}
	/**
	 * 重写getAll方法绑定流程runId
	 * @param queryFilter
	 */
	public List<AssetApplicationAll> getAll(QueryFilter queryFilter){
		List<ProcessTask> tasks = null;
		List<AssetApplicationAll> assetApplicationAllList=super.getAll(queryFilter);
		List<AssetApplicationAll> assetApplicationAlls=new ArrayList<AssetApplicationAll>();
		for(AssetApplicationAll assetApplicationAll:assetApplicationAllList){
			ProcessRun processRun=processRunService.getByBusinessKey(assetApplicationAll.getId().toString());
			if(BeanUtils.isNotEmpty(processRun)){
				if(null!= processRun.getRunId()){
					assetApplicationAll.setRunId(processRun.getRunId());
				}
				if(null!= processRun.getActInstId()){
					tasks = bpmService.getTasks(processRun.getActInstId());
				}
				if(tasks!=null && tasks.size()>0){
					assetApplicationAll.setState(tasks.get(0).getName());
				}
			}
			assetApplicationAlls.add(assetApplicationAll);
		}
		return assetApplicationAlls;
	}
	
}
