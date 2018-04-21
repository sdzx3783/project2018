package com.hotent.makshi.service.renhouse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.GenericService;
import com.hotent.core.util.BeanUtils;
import com.hotent.makshi.model.renhouse.RentHouse;
import com.hotent.makshi.dao.renhouse.RentHouseDao;
import com.hotent.core.util.UniqueIdUtil;

import net.sf.json.util.JSONUtils;
import net.sf.ezmorph.object.DateMorpher;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.util.StringUtil;
import net.sf.json.JSONObject;

import com.hotent.makshi.model.reninfo.RentInfo;
import com.hotent.makshi.dao.reninfo.RentInfoDao;

import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.core.service.WfBaseService;
import com.hotent.core.annotion.WorkFlow;
import com.hotent.platform.service.bpm.util.BpmAspectUtil;
import com.hotent.core.bpm.BpmResult;
import com.hotent.platform.service.bpm.BpmBusLinkService;


@Service
public class RentHouseService extends WfBaseService<RentHouse>
{
	@Resource
	private RentHouseDao dao;
	
	@Resource
	private RentInfoDao rentInfoDao;
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmBusLinkService bpmBusLinkService;
	public RentHouseService()
	{
	}
	
	@Override
	protected IEntityDao<RentHouse,Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 根据外键删除子表记录
	 * @param id
	 */
	private void delByPk(Long id){
		rentInfoDao.delByMainId(id);
	}
	
	/**
	 * 删除数据 包含相应子表记录
	 * @param lAryId
	 */
	public void delAll(Long[] lAryId) {
		for(Long id:lAryId){	
			delByPk(id);
			dao.delById(id);	
		}	
	}
	
	/**
	 * 添加数据 
	 * @param rentHouse
	 * @throws Exception
	 */
	public void addAll(RentHouse rentHouse) throws Exception{
		super.add(rentHouse);
		addSubList(rentHouse);
	}
	
	/**
	 * 更新数据
	 * @param rentHouse
	 * @throws Exception
	 */
	public void updateAll(RentHouse rentHouse) throws Exception{
		super.update(rentHouse);
		delByPk(rentHouse.getId());
		addSubList(rentHouse);
	}
	
	/**
	 * 添加子表记录
	 * @param rentHouse
	 * @throws Exception
	 */
	public void addSubList(RentHouse rentHouse) throws Exception{
		
		List<RentInfo> rentInfoList=rentHouse.getRentInfoList();
		if(BeanUtils.isNotEmpty(rentInfoList)){
			
			for(RentInfo rentInfo:rentInfoList){
				rentInfo.setRefId(rentHouse.getId());
				Long id=UniqueIdUtil.genId();
				rentInfo.setId(id);				
				rentInfoDao.add(rentInfo);
			}
		}
	}
	
	/**
	 * 根据外键获得拟租信息列表
	 * @param id
	 * @return
	 */
	public List<RentInfo> getRentInfoList(Long id) {
		return rentInfoDao.getByMainId(id);
	}
	/**
	 * 重写getAll方法绑定流程runId
	 * @param queryFilter
	 */
	public List<RentHouse> getAll(QueryFilter queryFilter){
		List<RentHouse> rentHouseList=super.getAll(queryFilter);
		List<RentHouse> rentHouses=new ArrayList<RentHouse>();
		for(RentHouse rentHouse:rentHouseList){
			ProcessRun processRun=processRunService.getByBusinessKey(rentHouse.getId().toString());
			if(BeanUtils.isNotEmpty(processRun)){
				rentHouse.setRunId(processRun.getRunId());
			}
			rentHouses.add(rentHouse);
		}
		return rentHouses;
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
			RentHouse rentHouse=getRentHouse(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				rentHouse.setId(genId);
				this.addAll(rentHouse);
			}else{
				rentHouse.setId(Long.parseLong(cmd.getBusinessKey()));
				this.updateAll(rentHouse);
			}
			cmd.setBusinessKey(rentHouse.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取RentHouse对象
	 * @param json
	 * @return
	 */
	public RentHouse getRentHouse(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		Map<String,  Class> map=new HashMap<String,  Class>();
		map.put("rentInfoList", RentInfo.class);
		RentHouse rentHouse = (RentHouse)JSONObject.toBean(obj, RentHouse.class,map);
		return rentHouse;
	}
	/**
	 * 保存 租房申请表 信息
	 * @param rentHouse
	 */

	@WorkFlow(flowKey="rent_house",tableName="rent_house")
	public void save(RentHouse rentHouse) throws Exception{
		Long id=rentHouse.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			rentHouse.setId(id);
			this.addAll(rentHouse);
		    //添加到业务关联数据表
		    bpmBusLinkService.add(rentHouse.getId().toString(), null , true,  "rent_house");
		}
		else{
		    this.updateAll(rentHouse);
		}
		BpmResult result=new BpmResult();
		//添加流程变量
//		result.addVariable("", "");
		result.setBusinessKey(id.toString());
		BpmAspectUtil.setBpmResult(result);
	}
}
