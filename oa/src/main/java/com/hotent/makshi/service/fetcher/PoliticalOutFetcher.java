/**
 * 
 */
package com.hotent.makshi.service.fetcher;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.hotent.core.util.StringUtil;
import com.hotent.makshi.model.hr.PoliticalIn;
import com.hotent.makshi.model.hr.PoliticalOut;
import com.hotent.makshi.model.party.PartyFileInApplication;
import com.hotent.makshi.model.party.PartyFileOutApplication;
import com.hotent.makshi.model.party.PartyFiles;
import com.hotent.makshi.service.hr.PoliticalInService;
import com.hotent.makshi.service.hr.PoliticalOutService;
import com.hotent.makshi.service.party.PartyFileInApplicationService;
import com.hotent.makshi.service.party.PartyFileOutApplicationService;
import com.hotent.makshi.service.party.PartyFilesService;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.service.bpm.ProcessRunService;

/**
 * @author cesc
 *党员转出关联
 */
@Component("politicalOutFetcher")
public class PoliticalOutFetcher implements IFetcher {
	private final Logger log = Logger.getLogger(this.getClass());
	@Resource
	private PoliticalOutService politicalOutService;
	@Resource
	private PoliticalInService politicalInService;
	@Override
	public void fetcheData(String tableName, String businessKey) {
		
		if(!StringUtil.isEmpty(businessKey)){
			PoliticalOut politicalOut = politicalOutService.getById(Long.parseLong(businessKey));
			if(politicalOut!=null){
				PoliticalIn politicalIn = null;
				List<PoliticalIn> politicalInList = politicalInService.getByPartyName(politicalOut.getUser_name());
				if(politicalInList.size()>0){
				  politicalIn = politicalInList.get(0);
				}
				if(politicalIn!=null){
					politicalIn.setIs_out("1");
					politicalIn.setUpdatetime(new Date());
					politicalIn.setOut_date(politicalOut.getAppi_date());
					if(politicalOut.getPay_month()!=null && !("").equals(politicalOut.getPay_month())){
						politicalIn.setPay_date(politicalOut.getPay_month());
					}
				}
				try {
					politicalInService.update(politicalIn);
				} catch (Exception e) {
					log.error("错误信息",e);
				}
			}
			
		}
		
	}

}
