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
import com.hotent.makshi.model.waterprotectpark.EquipmentMaintenanceApplication;
import com.hotent.makshi.dao.waterprotectpark.EquipmentMaintenanceApplicationDao;
import com.hotent.core.util.UniqueIdUtil;

import net.sf.json.util.JSONUtils;
import net.sf.ezmorph.object.DateMorpher;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.util.StringUtil;
import net.sf.json.JSONObject;


import com.hotent.core.service.BaseService;


@Service
public class EquipmentMaintenanceApplicationService extends BaseService<EquipmentMaintenanceApplication>
{
	@Resource
	private EquipmentMaintenanceApplicationDao dao;
	
	public EquipmentMaintenanceApplicationService()
	{
	}
	
	@Override
	protected IEntityDao<EquipmentMaintenanceApplication,Long> getEntityDao() 
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
			EquipmentMaintenanceApplication equipmentMaintenanceApplication=getEquipmentMaintenanceApplication(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				equipmentMaintenanceApplication.setId(genId);
				this.add(equipmentMaintenanceApplication);
			}else{
				equipmentMaintenanceApplication.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(equipmentMaintenanceApplication);
			}
			cmd.setBusinessKey(equipmentMaintenanceApplication.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取EquipmentMaintenanceApplication对象
	 * @param json
	 * @return
	 */
	public EquipmentMaintenanceApplication getEquipmentMaintenanceApplication(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		EquipmentMaintenanceApplication equipmentMaintenanceApplication = (EquipmentMaintenanceApplication)JSONObject.toBean(obj, EquipmentMaintenanceApplication.class);
		return equipmentMaintenanceApplication;
	}
	/**
	 * 保存 设施设备维修申请(水保园流程) 信息
	 * @param equipmentMaintenanceApplication
	 */

	public void save(EquipmentMaintenanceApplication equipmentMaintenanceApplication) throws Exception{
		Long id=equipmentMaintenanceApplication.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			equipmentMaintenanceApplication.setId(id);
		    this.add(equipmentMaintenanceApplication);
		}
		else{
		    this.update(equipmentMaintenanceApplication);
		}
	}
}
