package com.hotent.makshi.service.stationery;
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
import com.hotent.makshi.model.stationery.StationeryPurchase;
import com.hotent.makshi.dao.stationery.StationeryPurchaseDao;
import com.hotent.core.util.UniqueIdUtil;

import net.sf.json.util.JSONUtils;
import net.sf.ezmorph.object.DateMorpher;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.bpm.model.ProcessTask;
import com.hotent.core.util.StringUtil;
import net.sf.json.JSONObject;

import com.hotent.makshi.model.stock.StationeryStock;
import com.hotent.makshi.dao.stock.StationeryStockDao;

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
public class StationeryPurchaseService extends WfBaseService<StationeryPurchase>
{
	@Resource
	private StationeryPurchaseDao dao;
	
	@Resource
	private BpmService bpmService;
	@Resource
	private StationeryStockDao stationeryStockDao;
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmBusLinkService bpmBusLinkService;
	public StationeryPurchaseService()
	{
	}
	
	@Override
	protected IEntityDao<StationeryPurchase,Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 根据外键删除子表记录
	 * @param id
	 */
	private void delByPk(Long id){
		stationeryStockDao.delByMainId(id);
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
	 * @param stationeryPurchase
	 * @throws Exception
	 */
	public void addAll(StationeryPurchase stationeryPurchase) throws Exception{
		super.add(stationeryPurchase);
		addSubList(stationeryPurchase);
	}
	
	/**
	 * 更新数据
	 * @param stationeryPurchase
	 * @throws Exception
	 */
	public void updateAll(StationeryPurchase stationeryPurchase) throws Exception{
		super.update(stationeryPurchase);
		delByPk(stationeryPurchase.getId());
		addSubList(stationeryPurchase);
	}
	
	/**
	 * 添加子表记录
	 * @param stationeryPurchase
	 * @throws Exception
	 */
	public void addSubList(StationeryPurchase stationeryPurchase) throws Exception{
		
		List<StationeryStock> stationeryStockList=stationeryPurchase.getStationeryStockList();
		if(BeanUtils.isNotEmpty(stationeryStockList)){
			
			for(StationeryStock stationeryStock:stationeryStockList){
				stationeryStock.setRefId(stationeryPurchase.getId());
				Long id=UniqueIdUtil.genId();
				stationeryStock.setId(id);				
				stationeryStockDao.add(stationeryStock);
			}
		}
	}
	
	/**
	 * 根据外键获得文具库存表列表
	 * @param id
	 * @return
	 */
	public List<StationeryStock> getStationeryStockList(Long id) {
		return stationeryStockDao.getByMainId(id);
	}
	/**
	 * 
	 * 重写getAll方法绑定流程runId
	 * @param queryFilter
	 */
	public List<StationeryPurchase> getAll(QueryFilter queryFilter){
		List<ProcessTask> tasks = null;
		List<StationeryPurchase> stationeryPurchaseList=super.getAll(queryFilter);
		List<StationeryPurchase> stationeryPurchases=new ArrayList<StationeryPurchase>();
		for(StationeryPurchase stationeryPurchase:stationeryPurchaseList){
			ProcessRun processRun=processRunService.getByBusinessKey(stationeryPurchase.getId().toString());
			if(BeanUtils.isNotEmpty(processRun)){
				if(null!=processRun.getActInstId()){
					tasks = bpmService.getTasks(processRun.getActInstId());}
				if(null!=processRun.getRunId()){
					stationeryPurchase.setRunId(processRun.getRunId());};
				if(null!=processRun.getGlobalFlowNo()){
					stationeryPurchase.setGlobalflowno(processRun.getGlobalFlowNo());};
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				stationeryPurchase.setApplication_time(sdf.format(processRun.getCreatetime()));
				if(tasks!=null && tasks.size()>0){
					stationeryPurchase.setState(tasks.get(0).getName());
				}
			}
			stationeryPurchases.add(stationeryPurchase);
		}
		return stationeryPurchases;
	}
	/**
	 * 根据id获取实例
	 */
	public StationeryPurchase getById(Long id){
		return dao.getById(id);
		
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
			StationeryPurchase stationeryPurchase=getStationeryPurchase(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				stationeryPurchase.setId(genId);
				this.addAll(stationeryPurchase);
			}else{
				stationeryPurchase.setId(Long.parseLong(cmd.getBusinessKey()));
				this.updateAll(stationeryPurchase);
			}
			cmd.setBusinessKey(stationeryPurchase.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取StationeryPurchase对象
	 * @param json
	 * @return
	 */
	public StationeryPurchase getStationeryPurchase(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		Map<String,  Class> map=new HashMap<String,  Class>();
		map.put("stationeryStockList", StationeryStock.class);
		StationeryPurchase stationeryPurchase = (StationeryPurchase)JSONObject.toBean(obj, StationeryPurchase.class,map);
		return stationeryPurchase;
	}
	/**
	 * 保存 办公用品申购表 信息
	 * @param stationeryPurchase
	 */

	@WorkFlow(flowKey="stationery_purchase",tableName="stationery_purchase")
	public void save(StationeryPurchase stationeryPurchase) throws Exception{
		Long id=stationeryPurchase.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			stationeryPurchase.setId(id);
			this.addAll(stationeryPurchase);
		    //添加到业务关联数据表
		    bpmBusLinkService.add(stationeryPurchase.getId().toString(), null , true,  "stationery_purchase");
		}
		else{
		    this.updateAll(stationeryPurchase);
		}
		BpmResult result=new BpmResult();
		//添加流程变量
//		result.addVariable("", "");
		result.setBusinessKey(id.toString());
		BpmAspectUtil.setBpmResult(result);
	}
}
