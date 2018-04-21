package com.hotent.makshi.service.waterprotectpark;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.GenericService;
import com.hotent.core.util.BeanUtils;
import com.hotent.makshi.model.waterprotectpark.RiverExperiment;
import com.hotent.makshi.dao.waterprotectpark.RiverExperimentDao;
import com.hotent.core.util.UniqueIdUtil;

import net.sf.json.util.JSONUtils;
import net.sf.ezmorph.object.DateMorpher;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.util.StringUtil;
import net.sf.json.JSONObject;


import com.hotent.core.service.BaseService;


@Service
public class RiverExperimentService extends BaseService<RiverExperiment>
{
	@Resource
	private RiverExperimentDao dao;
	
	public RiverExperimentService()
	{
	}
	
	@Override
	protected IEntityDao<RiverExperiment,Long> getEntityDao() 
	{
		return dao;
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
			RiverExperiment riverExperiment=getRiverExperiment(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				riverExperiment.setId(genId);
				this.add(riverExperiment);
			}else{
				riverExperiment.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(riverExperiment);
			}
			cmd.setBusinessKey(riverExperiment.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取RiverExperiment对象
	 * @param json
	 * @return
	 */
	public RiverExperiment getRiverExperiment(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		RiverExperiment riverExperiment = (RiverExperiment)JSONObject.toBean(obj, RiverExperiment.class);
		return riverExperiment;
	}
	/**
	 * 保存 径流实验申请(水保示范园) 信息
	 * @param riverExperiment
	 */

	public void save(RiverExperiment riverExperiment) throws Exception{
		Long id=riverExperiment.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			riverExperiment.setId(id);
		    this.add(riverExperiment);
		}
		else{
		    this.update(riverExperiment);
		}
	}
}
