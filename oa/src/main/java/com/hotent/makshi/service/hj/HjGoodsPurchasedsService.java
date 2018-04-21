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
import com.hotent.makshi.model.hj.HjGoodsPurchaseds;
import com.hotent.makshi.model.hj.HjpurchaseList;
import com.hotent.makshi.dao.hj.HjGoodsPurchasedsDao;
import com.hotent.core.util.UniqueIdUtil;

import net.sf.json.util.JSONUtils;
import net.sf.ezmorph.object.DateMorpher;

import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.util.StringUtil;

import net.sf.json.JSONObject;

import com.hotent.makshi.model.hj.Hjwppurchaselist;
import com.hotent.makshi.dao.hj.HjwppurchaselistDao;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.core.service.WfBaseService;
import com.hotent.core.annotion.WorkFlow;
import com.hotent.platform.service.bpm.util.BpmAspectUtil;
import com.hotent.core.bpm.BpmResult;
import com.hotent.platform.service.bpm.BpmBusLinkService;


@Service
public class HjGoodsPurchasedsService extends WfBaseService<HjGoodsPurchaseds>
{
	@Resource
	private HjGoodsPurchasedsDao dao;
	 
	@Resource
	private HjwppurchaselistDao hjwppurchaselistDao;
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmBusLinkService bpmBusLinkService;
	public HjGoodsPurchasedsService()
	{
	}
	
	public List<HjGoodsPurchaseds> getSelectByRecord(QueryFilter queryFilter){
		return dao.getSelectByRecord(queryFilter);
	}
	public  HjGoodsPurchaseds getSelectByWPID(Long id) {
		return dao.getSelectByWPID(id);
	}
	
	@Override
	protected IEntityDao<HjGoodsPurchaseds,Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 根据外键删除子表记录
	 * @param id
	 */
	private void delByPk(Long id){
		hjwppurchaselistDao.delByMainId(id);
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
	 * @param hjGoodsPurchaseds
	 * @throws Exception
	 */
	public void addAll(HjGoodsPurchaseds hjGoodsPurchaseds) throws Exception{
		super.add(hjGoodsPurchaseds);
		addSubList(hjGoodsPurchaseds);
	}
	
	/**
	 * 更新数据
	 * @param hjGoodsPurchaseds
	 * @throws Exception
	 */
	public void updateAll(HjGoodsPurchaseds hjGoodsPurchaseds) throws Exception{
		super.update(hjGoodsPurchaseds);
		delByPk(hjGoodsPurchaseds.getId());
		addSubList(hjGoodsPurchaseds);
	}
	
	/**
	 * 添加子表记录
	 * @param hjGoodsPurchaseds
	 * @throws Exception
	 */
	public void addSubList(HjGoodsPurchaseds hjGoodsPurchaseds) throws Exception{
		
		List<Hjwppurchaselist> hjwppurchaselistList=hjGoodsPurchaseds.getHjwppurchaselistList();
		if(BeanUtils.isNotEmpty(hjwppurchaselistList)){
			
			for(Hjwppurchaselist hjwppurchaselist:hjwppurchaselistList){
				hjwppurchaselist.setRefId(hjGoodsPurchaseds.getId());
				Long id=UniqueIdUtil.genId();
				hjwppurchaselist.setId(id);				
				hjwppurchaselistDao.add(hjwppurchaselist);
			}
		}
	}
	
	/**
	 * 根据外键获得物品采购信息列表
	 * @param id
	 * @return
	 */
	public List<Hjwppurchaselist> getHjwppurchaselistList(Long id) {
		return hjwppurchaselistDao.getByMainId(id);
	}
	/**
	 * 重写getAll方法绑定流程runId
	 * @param queryFilter
	 */
	public List<HjGoodsPurchaseds> getAll(QueryFilter queryFilter){
		List<HjGoodsPurchaseds> hjGoodsPurchasedsList=super.getAll(queryFilter);
		List<HjGoodsPurchaseds> hjGoodsPurchasedss=new ArrayList<HjGoodsPurchaseds>();
		for(HjGoodsPurchaseds hjGoodsPurchaseds:hjGoodsPurchasedsList){
			ProcessRun processRun=processRunService.getByBusinessKey(hjGoodsPurchaseds.getId().toString());
			if(BeanUtils.isNotEmpty(processRun)){
				hjGoodsPurchaseds.setRunId(processRun.getRunId());
			}
			hjGoodsPurchasedss.add(hjGoodsPurchaseds);
		}
		return hjGoodsPurchasedss;
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
			HjGoodsPurchaseds hjGoodsPurchaseds=getHjGoodsPurchaseds(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				hjGoodsPurchaseds.setId(genId);
				this.addAll(hjGoodsPurchaseds);
			}else{
				hjGoodsPurchaseds.setId(Long.parseLong(cmd.getBusinessKey()));
				this.updateAll(hjGoodsPurchaseds);
			}
			cmd.setBusinessKey(hjGoodsPurchaseds.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取HjGoodsPurchaseds对象
	 * @param json
	 * @return
	 */
	public HjGoodsPurchaseds getHjGoodsPurchaseds(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		Map<String,  Class> map=new HashMap<String,  Class>();
		map.put("hjwppurchaselistList", Hjwppurchaselist.class);
		HjGoodsPurchaseds hjGoodsPurchaseds = (HjGoodsPurchaseds)JSONObject.toBean(obj, HjGoodsPurchaseds.class,map);
		return hjGoodsPurchaseds;
	}
	/**
	 * 保存 物品 信息
	 * @param hjGoodsPurchaseds
	 */

	@WorkFlow(flowKey="wpcgsq",tableName="hj_goods_purchaseds")
	public void save(HjGoodsPurchaseds hjGoodsPurchaseds) throws Exception{
		Long id=hjGoodsPurchaseds.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			hjGoodsPurchaseds.setId(id);
			this.addAll(hjGoodsPurchaseds);
		    //添加到业务关联数据表
		    bpmBusLinkService.add(hjGoodsPurchaseds.getId().toString(), null , true,  "hj_goods_purchaseds");
		}
		else{
			Hjwppurchaselist hjWppurchase = hjGoodsPurchaseds.getHjWppurchase();
			hjwppurchaselistDao.update(hjWppurchase);
			dao.update(hjGoodsPurchaseds);
			
		    //this.updateAll(hjGoodsPurchaseds);
		}
		BpmResult result=new BpmResult();
		//添加流程变量
//		result.addVariable("", "");
		result.setBusinessKey(id.toString());
		BpmAspectUtil.setBpmResult(result);
	}
}
