/**
 * 
 */
package com.hotent.makshi.service.fetcher;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hotent.core.util.StringUtil;
import com.hotent.makshi.model.hr.Transferposition;
import com.hotent.makshi.service.hr.TransferpositionService;
import com.hotent.platform.model.system.Position;
import com.hotent.platform.model.system.UserPosition;
import com.hotent.platform.service.system.PositionService;
import com.hotent.platform.service.system.UserPositionService;

/**
 * @author cesc
 *
 */
@Component("transferpositionFetcher")
public class TransferpositionFetcher implements IFetcher {
	@Resource
	private TransferpositionService transferpositionService;
	@Resource
	private UserPositionService userPositionService;
	@Resource
	private PositionService positionService;
	@Override
	public void fetcheData(String tableName, String businessKey) {
		
		if(!StringUtil.isEmpty(businessKey)){
			Transferposition transferposition = transferpositionService.getById(Long.parseLong(businessKey));
			if(transferposition!=null){
				UserPosition userPosition = userPositionService.getUserPosition(Long.parseLong(transferposition.getUserId()), Long.parseLong(transferposition.getBeforePostionID()));
				if(userPosition!=null){
					Position position = positionService.getById(Long.parseLong(transferposition.getAfterPostionID()));
					Long orgId = position.getOrgId();
					userPosition.setPosId(Long.parseLong(transferposition.getAfterPostionID()));
					userPosition.setPosName(transferposition.getAfterPostion());
					userPosition.setOrgId(orgId);
					if(position!=null){
						userPosition.setJobId(position.getJobId());
					}
					
					userPositionService.update(userPosition);
				}
				
			}
			
		}
		
	}

}
