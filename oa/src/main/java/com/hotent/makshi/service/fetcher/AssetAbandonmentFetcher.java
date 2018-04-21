package com.hotent.makshi.service.fetcher;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.hotent.core.util.StringUtil;
import com.hotent.makshi.model.abandonment.AssetAbandonment;
import com.hotent.makshi.model.assetregistration.AssetRegistration;
import com.hotent.makshi.service.abandonment.AssetAbandonmentService;
import com.hotent.makshi.service.assetregistration.AssetRegistrationService;

@Component("assetAbandonmentFetcher")
public class AssetAbandonmentFetcher implements IFetcher {
	private final Logger log = Logger.getLogger(this.getClass());
	@Resource
	private AssetAbandonmentService assetAbandonmentService;
	@Resource
	private AssetRegistrationService assetRegistrationService;
	@Override
	public void fetcheData(String tableName, String businessKey) {
		try{
			if(!StringUtil.isEmpty(businessKey)){
				AssetRegistration assetRegistration = new AssetRegistration();
				AssetAbandonment assetAbandonment = assetAbandonmentService.getById(Long.parseLong(businessKey));
				String assetId = assetAbandonment.getAsset_id();
				assetRegistration = assetRegistrationService.getByAssetId(assetId).get(0);
				assetRegistration.setState("1");
				Date date = new Date();
				assetRegistration.setAbandonment_date(date);
				assetRegistrationService.save(assetRegistration);
			}
		}catch (Exception e) {
			log.error("错误信息",e);
		}
	}

}
