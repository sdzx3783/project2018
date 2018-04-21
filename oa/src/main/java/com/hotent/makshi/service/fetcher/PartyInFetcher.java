/**
 * 
 */
package com.hotent.makshi.service.fetcher;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.hotent.core.util.StringUtil;
import com.hotent.makshi.model.party.PartyFileInApplication;
import com.hotent.makshi.model.party.PartyFiles;
import com.hotent.makshi.service.party.PartyFileInApplicationService;
import com.hotent.makshi.service.party.PartyFilesService;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.service.bpm.ProcessRunService;

/**
 * @author cesc 党员转入关联
 */
@Component("partyInFetcher")
public class PartyInFetcher implements IFetcher {
	private final Logger log = Logger.getLogger(this.getClass());

	@Resource
	private PartyFileInApplicationService fileInApplicationService;
	@Resource
	private PartyFilesService filesService;
	@Resource
	private ProcessRunService processRunService;

	@Override
	public void fetcheData(String tableName, String businessKey) {

		if (!StringUtil.isEmpty(businessKey)) {
			PartyFileInApplication partyFileInApplication = fileInApplicationService.getById(Long.parseLong(businessKey));
			if (partyFileInApplication != null) {
				ProcessRun processRun = processRunService.getByBusinessKey(partyFileInApplication.getId().toString());
				String creator = "";
				if (processRun != null) {
					creator = processRun.getCreator();
				}

				PartyFiles party = new PartyFiles(partyFileInApplication.getParty_num(), partyFileInApplication.getContact_situation(), partyFileInApplication.getUser_num(),
						partyFileInApplication.getAccount(), partyFileInApplication.getOut_contact(), partyFileInApplication.getJoin_party_date(), partyFileInApplication.getOut_tel(),
						partyFileInApplication.getRegular_date(), partyFileInApplication.getIn_contact(), partyFileInApplication.getJoin_party_where(), partyFileInApplication.getIn_tel(),
						partyFileInApplication.getRegular_where(), partyFileInApplication.getPlace_of_influx(), partyFileInApplication.getIntroducer(), partyFileInApplication.getLost_date(),
						partyFileInApplication.getBranch(), partyFileInApplication.getAbroad_date(), partyFileInApplication.getJoin_branch_date(), partyFileInApplication.getAbroad_reason(),
						partyFileInApplication.getParty_post(), partyFileInApplication.getAbroad_situation(), partyFileInApplication.getOrg_relationship(), partyFileInApplication.getParty_handling(),
						partyFileInApplication.getOut_date(), partyFileInApplication.getReturn_date(), partyFileInApplication.getOutgoing_flow(), partyFileInApplication.getRestore_org_life(),
						partyFileInApplication.getOut_type(), partyFileInApplication.getReviewer(), partyFileInApplication.getOut_reason(), partyFileInApplication.getBranch_review(),
						partyFileInApplication.getActivity_num(), creator, new Date());
				try {
					filesService.save(party);
				} catch (Exception e) {
					log.error("错误信息", e);
				}
			}

		}

	}

}
