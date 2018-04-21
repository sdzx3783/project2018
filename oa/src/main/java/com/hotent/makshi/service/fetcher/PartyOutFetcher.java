/**
 * 
 */
package com.hotent.makshi.service.fetcher;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.hotent.core.util.StringUtil;
import com.hotent.makshi.model.party.PartyFileOutApplication;
import com.hotent.makshi.model.party.PartyFiles;
import com.hotent.makshi.service.party.PartyFileOutApplicationService;
import com.hotent.makshi.service.party.PartyFilesService;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.service.bpm.ProcessRunService;

/**
 * @author cesc
 *党员转出关联
 */
@Component("partyOutFetcher")
public class PartyOutFetcher implements IFetcher {
	private final Logger log = Logger.getLogger(this.getClass());

	@Resource
	private PartyFileOutApplicationService fileOutApplicationService;
	@Resource
	private PartyFilesService filesService;
	@Resource
	private ProcessRunService processRunService;
	@Override
	public void fetcheData(String tableName, String businessKey) {
		
		if(!StringUtil.isEmpty(businessKey)){
			PartyFileOutApplication partyFileOutApplication = fileOutApplicationService.getById(Long.parseLong(businessKey));
			if(partyFileOutApplication!=null){
				ProcessRun processRun=processRunService.getByBusinessKey(partyFileOutApplication.getId().toString());
				String creator="";
				if(processRun!=null){
					creator=processRun.getCreator();
				}
				PartyFiles partyFiles = filesService.getByPartyNum(partyFileOutApplication.getParty_num());
				if(partyFiles!=null){
					partyFiles.setIsout("1");
					partyFiles.setUpdate_time(new Date());
					partyFiles.setUpdater(creator);
				}
				
				try {
					filesService.update(partyFiles);
				} catch (Exception e) {
					log.error("错误信息",e);
				}
			}
			
		}
		
	}

}
