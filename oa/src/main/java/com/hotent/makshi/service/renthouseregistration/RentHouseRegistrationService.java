package com.hotent.makshi.service.renthouseregistration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.GenericService;
import com.hotent.core.util.BeanUtils;
import com.hotent.makshi.model.renthouseregistration.RentHouseRegistration;
import com.hotent.makshi.dao.renthouseregistration.RentHouseRegistrationDao;
import com.hotent.core.util.UniqueIdUtil;

import net.sf.json.util.JSONUtils;
import net.sf.ezmorph.object.DateMorpher;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.util.StringUtil;
import net.sf.json.JSONObject;


import com.hotent.core.service.BaseService;


@Service
public class RentHouseRegistrationService extends BaseService<RentHouseRegistration>
{
	@Resource
	private RentHouseRegistrationDao dao;
	
	public RentHouseRegistrationService()
	{
	}
	
	@Override
	protected IEntityDao<RentHouseRegistration,Long> getEntityDao() 
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
			RentHouseRegistration rentHouseRegistration=getRentHouseRegistration(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				rentHouseRegistration.setId(genId);
				this.add(rentHouseRegistration);
			}else{
				rentHouseRegistration.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(rentHouseRegistration);
			}
			cmd.setBusinessKey(rentHouseRegistration.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取RentHouseRegistration对象
	 * @param json
	 * @return
	 */
	public RentHouseRegistration getRentHouseRegistration(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		RentHouseRegistration rentHouseRegistration = (RentHouseRegistration)JSONObject.toBean(obj, RentHouseRegistration.class);
		return rentHouseRegistration;
	}
	/**
	 * 保存 租房登记表 信息
	 * @param rentHouseRegistration
	 */

	public void save(RentHouseRegistration rentHouseRegistration) throws Exception{
		Long id=rentHouseRegistration.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			rentHouseRegistration.setId(id);
			rentHouseRegistration.setHouse_id(id.toString());
		    this.add(rentHouseRegistration);
		}
		else{
		    this.update(rentHouseRegistration);
		}
	}

	public List<RentHouseRegistration> getAlls(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return null;
	}
}
