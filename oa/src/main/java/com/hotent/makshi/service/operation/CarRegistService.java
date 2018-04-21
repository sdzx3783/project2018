package com.hotent.makshi.service.operation;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.springframework.stereotype.Service;

import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.makshi.dao.operation.CarRegistDao;
import com.hotent.makshi.dao.operation.CarUseSegmentDao;
import com.hotent.makshi.model.operation.CarRegist;
import com.hotent.makshi.model.operation.CarUseSegment;


@Service
public class CarRegistService extends BaseService<CarRegist>
{
	@Resource
	private CarRegistDao dao;
	
	@Resource
	private CarUseSegmentDao carUseSegmentDao;
	
	public CarRegistService()
	{
	}
	
	@Override
	protected IEntityDao<CarRegist,Long> getEntityDao() 
	{
		return dao;
	}
	
	public List<CarUseSegment> getTimePoint(String date){
		return carUseSegmentDao.getBySqlKey("getList",date);
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
			CarRegist carRegist=getCarRegist(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				carRegist.setId(genId);
				this.add(carRegist);
			}else{
				carRegist.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(carRegist);
			}
			cmd.setBusinessKey(carRegist.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取CarRegist对象
	 * @param json
	 * @return
	 */
	public CarRegist getCarRegist(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		CarRegist carRegist = (CarRegist)JSONObject.toBean(obj, CarRegist.class);
		return carRegist;
	}
	/**
	 * 保存 车辆登记 信息
	 * @param carRegist
	 */

	public void save(CarRegist carRegist) throws Exception{
		Long id=carRegist.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			carRegist.setId(id);
		    this.add(carRegist);
		}
		else{
		    this.update(carRegist);
		}
	}

	public void updateStatus(List<String> carIdList) {
		dao.getBySqlKey("updateStatus", carIdList);
		dao.getBySqlKey("updateStatusFree", carIdList);
	}

	public List<CarRegist> getUnuseCar(List<String> idList) {
		return dao.getBySqlKey("getUnuseCar", idList);
	}

}
