package com.hotent.makshi.service.assetregistration;
import java.util.ArrayList;
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
import com.hotent.makshi.dao.assetapplicationall.AssetApplicationAllDao;
import com.hotent.makshi.dao.assetregistration.AssetRegistrationDao;
import com.hotent.makshi.model.abandonment.AssetAbandonment;
import com.hotent.makshi.model.assetregistration.AssetRegistration;
import com.hotent.makshi.service.abandonment.AssetAbandonmentService;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.service.bpm.impl.ScriptImpl;


@Service
public class AssetRegistrationService extends BaseService<AssetRegistration>
{
	@Resource
	private AssetRegistrationDao dao;
	
	@Resource
	private AssetApplicationAllDao applicationDao;
	
	@Resource
	private AssetAbandonmentService assetAbandonmentService;
	
	@Resource
	private ScriptImpl scriptImpl;
	
	public AssetRegistrationService()
	{
	}
	
	@Override
	protected IEntityDao<AssetRegistration,Long> getEntityDao() 
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
			AssetRegistration assetRegistration=getAssetRegistration(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				assetRegistration.setId(genId);
				this.add(assetRegistration);
			}else{
				assetRegistration.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(assetRegistration);
			}
			cmd.setBusinessKey(assetRegistration.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取AssetRegistration对象
	 * @param json
	 * @return
	 */
	public AssetRegistration getAssetRegistration(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		AssetRegistration assetRegistration = (AssetRegistration)JSONObject.toBean(obj, AssetRegistration.class);
		return assetRegistration;
	}
	/**
	 * 保存 资产登记表 信息
	 * @param assetRegistration
	 */

	public void save(AssetRegistration assetRegistration) throws Exception{
		Long id=assetRegistration.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			assetRegistration.setId(id);
		    this.add(assetRegistration);
		}
		else{
		    this.update(assetRegistration);
		}
	}
	
	/**
	 * 新增报废信息
	 * @param assetRegistration
	 */

	public void addAbandoment(AssetRegistration assetRegistration) throws Exception{
		AssetAbandonment assetAbandonment = new AssetAbandonment();
		assetAbandonment.setAsset_id(assetRegistration.getAsset_id());
		assetAbandonment.setAsset_name(assetRegistration.getAsset_name());
		assetAbandonment.setSpecification(assetRegistration.getSpecification());
		assetAbandonment.setApplication_name(scriptImpl.getCurrentName());
		assetAbandonment.setApplication_time(new Date());
		assetAbandonment.setId(UniqueIdUtil.genId());
		assetAbandonment.setReason("手工报废");
		assetAbandonmentService.add(assetAbandonment);
	}
	
	public void saveHistory(AssetRegistration assetRegistration) throws Exception{
		dao.insert("saveHistory", assetRegistration);
	}

	public List<AssetRegistration> getMyAssetList(String FullName) {
		List<AssetRegistration> list = new ArrayList<AssetRegistration>();
		list = dao.getBySqlKey("getMyAssetList", FullName);
		return list;
	}
	public List<AssetRegistration> getByAssetId(String assetId) {
		return dao.getBySqlKey("getByAssetId",assetId);
	}
	public String getLastAssetId(String assetType){
		return  dao.getBySqlKey("getLastAssetId",assetType).get(0).getAsset_id();
	}
	public List<AssetRegistration> getAllAssetType(){
		return dao.getBySqlKey("getAllAssetType");
	}

	public int complete(String orgName,SysOrg sysOrg) {
		Map<String,Object> params = new HashMap<String,Object>();
		Long orgId = sysOrg.getOrgId();
		params.put("orgId", orgId);
		params.put("orgName", orgName);
		return dao.update("complete", params);
	}

	public List<AssetRegistration> getAllInfo() {
		return dao.getBySqlKey("getAllInfo");
	}

	public int setInfo(String newdepartment, String department) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("newdepartment", newdepartment);
		params.put("department", department);
		return dao.update("setInfo", params);
		
	}

	public int completeUserInfo(String userName, Long userId) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userName", userName);
		params.put("userId", userId);
		return dao.update("setUserInfo", params);
		
	}
	
	public int addInfo(Map<String,Object> params) {
		return dao.update("addInfo", params);
		
	}
	
}
