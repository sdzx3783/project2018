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
import com.hotent.makshi.model.hj.HjArticlePurchasing;
import com.hotent.makshi.dao.hj.HjArticlePurchasingDao;
import com.hotent.core.util.UniqueIdUtil;

import net.sf.json.util.JSONUtils;
import net.sf.ezmorph.object.DateMorpher;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.util.StringUtil;
import net.sf.json.JSONObject;

import com.hotent.makshi.model.hj.ItemInformation;
import com.hotent.makshi.dao.hj.ItemInformationDao;

import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.core.service.WfBaseService;
import com.hotent.core.annotion.WorkFlow;
import com.hotent.platform.service.bpm.util.BpmAspectUtil;
import com.hotent.core.bpm.BpmResult;
import com.hotent.platform.service.bpm.BpmBusLinkService;


@Service
public class HjArticlePurchasingService extends WfBaseService<HjArticlePurchasing>
{
	@Resource
	private HjArticlePurchasingDao dao;
	
	@Resource
	private ItemInformationDao itemInformationDao;
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmBusLinkService bpmBusLinkService;
	public HjArticlePurchasingService()
	{
	}
	
	@Override
	protected IEntityDao<HjArticlePurchasing,Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 根据外键删除子表记录
	 * @param id
	 */
	private void delByPk(Long id){
		itemInformationDao.delByMainId(id);
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
	 * @param hjArticlePurchasing
	 * @throws Exception
	 */
	public void addAll(HjArticlePurchasing hjArticlePurchasing) throws Exception{
		super.add(hjArticlePurchasing);
		addSubList(hjArticlePurchasing);
	}
	
	/**
	 * 更新数据
	 * @param hjArticlePurchasing
	 * @throws Exception
	 */
	public void updateAll(HjArticlePurchasing hjArticlePurchasing) throws Exception{
		super.update(hjArticlePurchasing);
		delByPk(hjArticlePurchasing.getId());
		addSubList(hjArticlePurchasing);
	}
	
	/**
	 * 添加子表记录
	 * @param hjArticlePurchasing
	 * @throws Exception
	 */
	public void addSubList(HjArticlePurchasing hjArticlePurchasing) throws Exception{
		
		List<ItemInformation> itemInformationList=hjArticlePurchasing.getItemInformationList();
		if(BeanUtils.isNotEmpty(itemInformationList)){
			
			for(ItemInformation itemInformation:itemInformationList){
				itemInformation.setRefId(hjArticlePurchasing.getId());
				Long id=UniqueIdUtil.genId();
				itemInformation.setId(id);				
				itemInformationDao.add(itemInformation);
			}
		}
	}
	
	/**
	 * 根据外键获得物品采购信息列表
	 * @param id
	 * @return
	 */
	public List<ItemInformation> getItemInformationList(Long id) {
		return itemInformationDao.getByMainId(id);
	}
	/**
	 * 重写getAll方法绑定流程runId
	 * @param queryFilter
	 */
	public List<HjArticlePurchasing> getAll(QueryFilter queryFilter){
		List<HjArticlePurchasing> hjArticlePurchasingList=super.getAll(queryFilter);
		List<HjArticlePurchasing> hjArticlePurchasings=new ArrayList<HjArticlePurchasing>();
		for(HjArticlePurchasing hjArticlePurchasing:hjArticlePurchasingList){
			ProcessRun processRun=processRunService.getByBusinessKey(hjArticlePurchasing.getId().toString());
			if(BeanUtils.isNotEmpty(processRun)){
				hjArticlePurchasing.setRunId(processRun.getRunId());
			}
			hjArticlePurchasings.add(hjArticlePurchasing);
		}
		return hjArticlePurchasings;
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
			HjArticlePurchasing hjArticlePurchasing=getHjArticlePurchasing(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				hjArticlePurchasing.setId(genId);
				this.addAll(hjArticlePurchasing);
			}else{
				hjArticlePurchasing.setId(Long.parseLong(cmd.getBusinessKey()));
				this.updateAll(hjArticlePurchasing);
			}
			cmd.setBusinessKey(hjArticlePurchasing.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取HjArticlePurchasing对象
	 * @param json
	 * @return
	 */
	public HjArticlePurchasing getHjArticlePurchasing(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		Map<String,  Class> map=new HashMap<String,  Class>();
		map.put("itemInformationList", ItemInformation.class);
		HjArticlePurchasing hjArticlePurchasing = (HjArticlePurchasing)JSONObject.toBean(obj, HjArticlePurchasing.class,map);
		return hjArticlePurchasing;
	}
	/**
	 * 保存 物品采购申请 信息
	 * @param hjArticlePurchasing
	 */

	@WorkFlow(flowKey="wpcgsq",tableName="hj_Article_purchasing")
	public void save(HjArticlePurchasing hjArticlePurchasing) throws Exception{
		Long id=hjArticlePurchasing.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			hjArticlePurchasing.setId(id);
			this.addAll(hjArticlePurchasing);
		    //添加到业务关联数据表
		    bpmBusLinkService.add(hjArticlePurchasing.getId().toString(), null , true,  "hj_Article_purchasing");
		}
		else{
		    this.updateAll(hjArticlePurchasing);
		}
		BpmResult result=new BpmResult();
		//添加流程变量
//		result.addVariable("", "");
		result.setBusinessKey(id.toString());
		BpmAspectUtil.setBpmResult(result);
	}
}
