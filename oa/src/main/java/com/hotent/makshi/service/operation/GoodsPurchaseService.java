package com.hotent.makshi.service.operation;
import java.util.ArrayList;
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
import com.hotent.makshi.dao.operation.GoodsPurchaseDao;
import com.hotent.makshi.dao.operation.PurchaselistsDao;
import com.hotent.makshi.model.operation.GoodsPurchase;
import com.hotent.makshi.model.operation.Purchaselists;


@Service
public class GoodsPurchaseService extends BaseService<GoodsPurchase>
{
	@Resource
	private GoodsPurchaseDao dao;
	
	@Resource
	private PurchaselistsDao purchaselistsDao;
	public GoodsPurchaseService()
	{
	}
	
	@Override
	protected IEntityDao<GoodsPurchase,Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 根据外键删除子表记录
	 * @param id
	 */
	private void delByPk(Long id){
		purchaselistsDao.delByMainId(id);
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
	 * @param goodsPurchase
	 * @throws Exception
	 */
	public void addAll(GoodsPurchase goodsPurchase) throws Exception{
		super.add(goodsPurchase);
		addSubList(goodsPurchase);
	}
	
	/**
	 * 更新数据
	 * @param goodsPurchase
	 * @throws Exception
	 */
	public void updateAll(GoodsPurchase goodsPurchase) throws Exception{
		super.update(goodsPurchase);
		delByPk(goodsPurchase.getId());
		addSubList(goodsPurchase);
	}
	
	/**
	 * 添加子表记录
	 * @param goodsPurchase
	 * @throws Exception
	 */
	public void addSubList(GoodsPurchase goodsPurchase) throws Exception{
		
		List<Purchaselists> purchaselistsList=goodsPurchase.getPurchaselistsList();
		if(BeanUtils.isNotEmpty(purchaselistsList)){
			
			for(Purchaselists purchaselists:purchaselistsList){
				purchaselists.setRefId(goodsPurchase.getId());
				Long id=UniqueIdUtil.genId();
				purchaselists.setId(id);				
				purchaselistsDao.add(purchaselists);
			}
		}
	}
	
	/**
	 * 根据外键获得物品采购信息列表
	 * @param id
	 * @return
	 */
	public List<Purchaselists> getPurchaselistsList(Long id) {
		return purchaselistsDao.getByMainId(id);
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
			GoodsPurchase goodsPurchase=getGoodsPurchase(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				goodsPurchase.setId(genId);
				this.addAll(goodsPurchase);
			}else{
				goodsPurchase.setId(Long.parseLong(cmd.getBusinessKey()));
				this.updateAll(goodsPurchase);
			}
			cmd.setBusinessKey(goodsPurchase.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取GoodsPurchase对象
	 * @param json
	 * @return
	 */
	public GoodsPurchase getGoodsPurchase(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		Map<String,  Class> map=new HashMap<String,  Class>();
		map.put("purchaselistsList", Purchaselists.class);
		GoodsPurchase goodsPurchase = (GoodsPurchase)JSONObject.toBean(obj, GoodsPurchase.class,map);
		return goodsPurchase;
	}
	/**
	 * 保存 物品采购申请 信息
	 * @param goodsPurchase
	 */

	public void save(GoodsPurchase goodsPurchase) throws Exception{
		Long refId=goodsPurchase.getRefId();
		Long id = goodsPurchase.getId();
		List<Purchaselists> purchaselistsList = new ArrayList<Purchaselists>();
		Purchaselists purchaselists = new Purchaselists();
		purchaselists.setRefId(refId);
		purchaselists.setName(goodsPurchase.getName());
		purchaselists.setNumber(goodsPurchase.getNumber());
		purchaselists.setPrice(goodsPurchase.getPrice());
		purchaselists.setSpecification(goodsPurchase.getSpecification());
		purchaselistsList.add(purchaselists);
		purchaselists.setRecipients_department(goodsPurchase.getRecipients_department());
		purchaselists.setRecipients_departmentID(goodsPurchase.getRecipients_departmentID());
		purchaselists.setRecipients_userID(goodsPurchase.getRecipients_userID());
		purchaselists.setRecipients_user(goodsPurchase.getRecipients_user());
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			goodsPurchase.setId(id);
			goodsPurchase.setPurchaselistsList(purchaselistsList);
			this.addAll(goodsPurchase);
		}
		else{
			purchaselists.setId(goodsPurchase.getId());
			goodsPurchase.setId(refId);
			dao.update(goodsPurchase);
			purchaselistsDao.update(purchaselists);
		}
	}
}
