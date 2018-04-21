package com.hotent.makshi.service.operation;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.GenericService;
import com.hotent.core.util.BeanUtils;
import com.hotent.makshi.model.operation.CarRegist;
import com.hotent.makshi.model.operation.CarUse;
import com.hotent.makshi.dao.operation.CarUseDao;
import com.hotent.core.util.UniqueIdUtil;

import net.sf.json.util.JSONUtils;
import net.sf.ezmorph.object.DateMorpher;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.util.StringUtil;
import net.sf.json.JSONObject;


import com.hotent.core.service.BaseService;


@Service
public class CarUseService extends BaseService<CarUse>
{
	@Resource
	private CarUseDao dao;
	
	public CarUseService()
	{
	}
	
	@Override
	protected IEntityDao<CarUse,Long> getEntityDao() 
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
			CarUse carUse=getCarUse(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				carUse.setId(genId);
				this.add(carUse);
			}else{
				carUse.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(carUse);
			}
			cmd.setBusinessKey(carUse.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取CarUse对象
	 * @param json
	 * @return
	 */
	public CarUse getCarUse(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		CarUse carUse = (CarUse)JSONObject.toBean(obj, CarUse.class);
		return carUse;
	}
	/**
	 * 保存 大车使用申请 信息
	 * @param carUse
	 */

	public void save(CarUse carUse) throws Exception{
		Long id=carUse.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			carUse.setId(id);
		    this.add(carUse);
		}
		else{
		    this.update(carUse);
		}
	}

	public List<CarUse> getAllByTime() {
		return dao.getBySqlKey("getAllByTime");
	}
	
	public List<CarUse> getUnuseCar(Date startTime, Date endTime) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		return dao.getBySqlKey("getUnuseCar", params);
	}
}
