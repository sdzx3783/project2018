package com.hotent.makshi.service.fetcher;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.hotent.core.util.AppUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.makshi.dao.assetapplicationall.AssetAppiListDao;
import com.hotent.makshi.model.assetapplicationall.AssetAppiList;
import com.hotent.makshi.model.assetapplicationall.AssetApplicationAll;
import com.hotent.makshi.model.assetregistration.AssetRegistration;
import com.hotent.makshi.service.assetapplicationall.AssetApplicationAllService;
import com.hotent.makshi.service.assetregistration.AssetRegistrationService;
import com.hotent.makshi.service.common.FlowToEntityService;
import com.hotent.platform.dao.system.SysOrgDao;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.service.bpm.impl.ScriptImpl;

@Component("assetApplicationAllFetcher")
public class AssetApplicationAllFetcher implements IFetcher {
	@Resource
	private AssetApplicationAllService assetApplicationAllService;
	@Resource
	private AssetRegistrationService assetRegistrationService;
	@Resource
	private FlowToEntityService flowToEntityService;
	@Resource
	private AssetAppiListDao assetAppiListDao;
	@Resource
	private SysOrgDao sysOrgDao;
	
	private static Logger logger=Logger.getLogger(AssetApplicationAllFetcher.class);
	
	@Override
	public void fetcheData(String tableName, String businessKey) throws Exception {
		String message = "流程数据同步失败";
		try {
			if(!StringUtil.isEmpty(businessKey)){
				List<AssetApplicationAll> assetApplicationAllList = assetApplicationAllService.getAllById(Long.parseLong(businessKey));
				List<AssetAppiList> assetAppList = assetAppiListDao.getByMainId(Long.parseLong(businessKey));
				if(assetApplicationAllList!=null && assetApplicationAllList.size()>0){
					if(assetAppList!=null && assetAppList.size()>0){
						for(AssetAppiList assetAppiList :assetAppList){
							//创建资产信息对象
							AssetRegistration assetRegistration = new AssetRegistration();
							AssetApplicationAll assetApplicationAll = assetApplicationAllList.get(0);
							Integer type = Integer.valueOf(assetApplicationAll.getType());
							//取得方式：0：领取旧设备，1：购买新设备
							Date getDate = new Date();
						/*	if(0==type){
								assetRegistration.setGet_type(assetApplicationAll.getType());
								String assetId = assetAppiList.getAssetId();
								Long id = assetRegistrationService.getByAssetId(assetId).get(0).getId();
								assetRegistration.setCare_person(assetApplicationAll.getUse_name());
								assetRegistration.setCare_personID(assetApplicationAll.getUse_nameID());
								assetRegistration.setId(id);
								assetRegistration.setGet_date(getDate);
								assetRegistrationService.save(assetRegistration);
							}else{*/
								//1位部门自行购买，0位委托办公室购买
								if(0==type){
									assetRegistration.setGet_type(assetApplicationAll.getType());
								}
							    String buy = assetApplicationAll.getBuy_type();
							    if(StringUtil.isNotEmpty(buy)){
							    	Integer buyType = Integer.valueOf(assetApplicationAll.getBuy_type());
							    	if(0==buyType){
							    		assetRegistration.setGet_type("1");
							    	}
							    	if(1==buyType){
							    		assetRegistration.setGet_type("2");
							    	}
							    }
								assetRegistration.setAsset_name(assetApplicationAll.getAsset_name());
								assetRegistration.setAsset_id(assetAppiList.getAssetId());
								assetRegistration.setAsset_type(assetApplicationAll.getAssetType());
								String assetType = assetApplicationAllService.getTypeName(assetApplicationAll.getAssetType()).get(0).getAssetType();
								assetRegistration.setAsset_code(assetType);
								String mianType = assetApplicationAll.getAssetType().substring(0,2);
								String version = assetApplicationAllService.getTypeName(mianType).get(0).getAssetType();
								assetRegistration.setVersion(version.substring(version.lastIndexOf("]")+1,version.length()));
								assetRegistration.setCard_number(assetAppiList.getCard_number());
								//assetRegistration.setAttachment(assetApplicationAll.getAttachment());
								if(null!=assetApplicationAll.getAttachment()){
									assetRegistration.setAttachment(flowToEntityService.flowToEntityFile(assetApplicationAll.getAttachment()));
								}
								/*assetRegistration.setNumber(assetApplicationAll.getNumber().toString());*/
								assetRegistration.setNumber("1");
								assetRegistration.setCare_person(assetApplicationAll.getUse_name());
								assetRegistration.setCare_personID(assetApplicationAll.getUse_nameID());
								assetRegistration.setCreatetime(assetApplicationAll.getCreatetime());
								assetRegistration.setSpecification(assetApplicationAll.getSpecifications());
								//获取申请人所在部门
								SysOrg sysOrg = sysOrgDao.getOrgByAccount(assetApplicationAll.getAccount());
								String orgId = sysOrg.getOrgId().toString();
								//orgId = script.getDepartmentId(orgId);
								assetRegistration.setUse_department(assetApplicationAll.getFist_department());
								assetRegistration.setUse_departmentID(orgId);
								assetRegistration.setGet_date(getDate);
								assetRegistration.setUse_date(getDate);
								//assetRegistration.setUse_departmentID(assetApplicationAll.getFist_departmentID());
								assetRegistration.setState("0");
								/*	String asset_code =  assetApplicationAllService.getTypeName(assetRegistration.getAsset_type()).get(0).getAssetType();
							assetRegistration.setAsset_code(asset_code);*/
								assetRegistrationService.save(assetRegistration);
				//			}
						}
						
					}else{
						message="未发现资产编号";
						throw new Exception(message);
					}
					
					
				}else{
					message="未找到流程数据";
					throw new Exception(message);
				}
			}else{
				message="未找到流程数据";
				throw new Exception(message);
			}
			}catch (Exception e) {
				logger.error(message);
				throw new Exception(message);
			}
		}
		
	}

