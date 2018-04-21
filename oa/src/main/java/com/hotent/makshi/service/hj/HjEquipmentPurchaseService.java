package com.hotent.makshi.service.hj;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.GenericService;
import com.hotent.core.util.BeanUtils;
import com.hotent.makshi.model.hj.HjEquipmentPurchase;
import com.hotent.makshi.model.hj.HjGoodsPurchaseds;
import com.hotent.makshi.dao.hj.HjEquipmentPurchaseDao;
import com.hotent.core.util.UniqueIdUtil;

import net.sf.json.util.JSONUtils;
import net.sf.ezmorph.object.DateMorpher;

import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.util.StringUtil;

import net.sf.json.JSONObject;

import com.hotent.makshi.model.hj.HjpurchaseList;
import com.hotent.makshi.dao.hj.HjpurchaseListDao;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.core.service.WfBaseService;
import com.hotent.core.annotion.WorkFlow;
import com.hotent.platform.service.bpm.util.BpmAspectUtil;
import com.hotent.core.bpm.BpmResult;
import com.hotent.platform.service.bpm.BpmBusLinkService;


@Service
public class HjEquipmentPurchaseService extends WfBaseService<HjEquipmentPurchase>
{
	@Resource
	private HjEquipmentPurchaseDao dao;
	
	@Resource
	private HjpurchaseListDao hjpurchaseListDao;
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmBusLinkService bpmBusLinkService;
	public HjEquipmentPurchaseService()
	{
	}
	
	@Override
	protected IEntityDao<HjEquipmentPurchase,Long> getEntityDao() 
	{
		return dao;
	}
	public List<HjEquipmentPurchase> getSelectByRecord(QueryFilter queryFilter){
		return dao.getSelectByRecord(queryFilter);
	}
	public  HjEquipmentPurchase getSelectByWPID(Long id) {
		return dao.getSelectByWPID(id);
	}
	/**
	 * 根据外键删除子表记录
	 * @param id
	 */
	private void delByPk(Long id){
		hjpurchaseListDao.delByMainId(id);
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
	 * @param hjEquipmentPurchase
	 * @throws Exception
	 */
	public void addAll(HjEquipmentPurchase hjEquipmentPurchase) throws Exception{
		super.add(hjEquipmentPurchase);
		addSubList(hjEquipmentPurchase);
	}
	
	/**
	 * 更新数据
	 * @param hjEquipmentPurchase
	 * @throws Exception
	 */
	public void updateAll(HjEquipmentPurchase hjEquipmentPurchase) throws Exception{
		super.update(hjEquipmentPurchase);
		delByPk(hjEquipmentPurchase.getId());
		addSubList(hjEquipmentPurchase);
	}
	
	/**
	 * 添加子表记录
	 * @param hjEquipmentPurchase
	 * @throws Exception
	 */
	public void addSubList(HjEquipmentPurchase hjEquipmentPurchase) throws Exception{
		
		List<HjpurchaseList> hjpurchaseListList=hjEquipmentPurchase.getHjpurchaseListList();
		if(BeanUtils.isNotEmpty(hjpurchaseListList)){
			
			for(HjpurchaseList hjpurchaseList:hjpurchaseListList){
				hjpurchaseList.setRefId(hjEquipmentPurchase.getId());
				Long id=UniqueIdUtil.genId();
				hjpurchaseList.setId(id);				
				hjpurchaseListDao.add(hjpurchaseList);
			}
		}
	}
	
	/**
	 * 根据外键获得物品采购信息列表
	 * @param id
	 * @return
	 */
	public List<HjpurchaseList> getHjpurchaseListList(Long id) {
		return hjpurchaseListDao.getByMainId(id);
	}
	/**
	 * 重写getAll方法绑定流程runId
	 * @param queryFilter
	 */
	public List<HjEquipmentPurchase> getAll(QueryFilter queryFilter){
		List<HjEquipmentPurchase> hjEquipmentPurchaseList=super.getAll(queryFilter);
		List<HjEquipmentPurchase> hjEquipmentPurchases=new ArrayList<HjEquipmentPurchase>();
		for(HjEquipmentPurchase hjEquipmentPurchase:hjEquipmentPurchaseList){
			ProcessRun processRun=processRunService.getByBusinessKey(hjEquipmentPurchase.getId().toString());
			if(BeanUtils.isNotEmpty(processRun)){
				hjEquipmentPurchase.setRunId(processRun.getRunId());
			}
			hjEquipmentPurchases.add(hjEquipmentPurchase);
		}
		return hjEquipmentPurchases;
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
			HjEquipmentPurchase hjEquipmentPurchase=getHjEquipmentPurchase(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				hjEquipmentPurchase.setId(genId);
				this.addAll(hjEquipmentPurchase);
			}else{
				hjEquipmentPurchase.setId(Long.parseLong(cmd.getBusinessKey()));
				this.updateAll(hjEquipmentPurchase);
			}
			cmd.setBusinessKey(hjEquipmentPurchase.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取HjEquipmentPurchase对象
	 * @param json
	 * @return
	 */
	public HjEquipmentPurchase getHjEquipmentPurchase(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		Map<String,  Class> map=new HashMap<String,  Class>();
		map.put("hjpurchaseListList", HjpurchaseList.class);
		HjEquipmentPurchase hjEquipmentPurchase = (HjEquipmentPurchase)JSONObject.toBean(obj, HjEquipmentPurchase.class,map);
		return hjEquipmentPurchase;
	}
	/**
	 * 保存 设备采购 信息
	 * @param hjEquipmentPurchase
	 */

	@WorkFlow(flowKey="sbcgsq",tableName="hj_equipment_purchase")
	public void save(HjEquipmentPurchase hjEquipmentPurchase) throws Exception{
		Long id=hjEquipmentPurchase.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			hjEquipmentPurchase.setId(id);
			this.addAll(hjEquipmentPurchase);
		    //添加到业务关联数据表
		    bpmBusLinkService.add(hjEquipmentPurchase.getId().toString(), null , true,  "hj_equipment_purchase");
		}
		else{
			HjpurchaseList hjPurchase = hjEquipmentPurchase.getHjPurchase();
			hjpurchaseListDao.update(hjPurchase);
			dao.update(hjEquipmentPurchase);
		    //this.updateAll(hjEquipmentPurchase);
		}
		BpmResult result=new BpmResult();
		//添加流程变量
//		result.addVariable("", "");
		result.setBusinessKey(id.toString());
		BpmAspectUtil.setBpmResult(result);
	}
}
