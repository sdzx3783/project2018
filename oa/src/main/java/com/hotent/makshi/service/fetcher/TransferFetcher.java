package com.hotent.makshi.service.fetcher;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.hotent.core.util.StringUtil;
import com.hotent.makshi.model.assetregistration.AssetRegistration;
import com.hotent.makshi.model.assettransfer.AssetTransfer;
import com.hotent.makshi.service.assetregistration.AssetRegistrationService;
import com.hotent.makshi.service.assettransfer.AssetTransferService;

@Component("transferFetcher")
public class TransferFetcher implements IFetcher {
	private final Logger log = Logger.getLogger(this.getClass());
	@Resource
	private AssetTransferService assetTransferService;
	@Resource
	private AssetRegistrationService assetRegistrationService;
	@Override
	public void fetcheData(String tableName, String businessKey) {
		try{
			if(!StringUtil.isEmpty(businessKey)){
				AssetRegistration assetRegistration = new AssetRegistration();
				AssetTransfer assetTransfer = assetTransferService.getById(Long.parseLong(businessKey));
				String assetId = assetTransfer.getAsset_id();
				assetRegistration = (assetRegistrationService.getByAssetId(assetId)).get(0);
				assetRegistration.setCare_person(assetTransfer.getReceiption_person());
				assetRegistration.setCare_personID(assetTransfer.getReceiption_personID());
				assetRegistration.setGet_date(new Date());
				assetRegistration.setUse_department(assetTransfer.getReceiption_department());
				assetRegistrationService.save(assetRegistration);
			}
			
		}catch (Exception e) {
			log.error("错误信息",e);
		}
	}

}
