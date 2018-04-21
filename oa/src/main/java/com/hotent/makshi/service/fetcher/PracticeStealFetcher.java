/**
 * 
 */
package com.hotent.makshi.service.fetcher;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hotent.core.util.StringUtil;
import com.hotent.makshi.model.title.PersonalSeal;
import com.hotent.makshi.model.title.Practicesteal;
import com.hotent.makshi.service.title.PersonalSealService;
import com.hotent.makshi.service.title.PracticestealService;

/**
 * @author cesc
 *执业印章关联
 */
@Component("practiceStealFetcher")
public class PracticeStealFetcher implements IFetcher {

	@Override
	public void fetcheData(String tableName, String businessKey) {
		
	}
/*	@Resource
	private PracticestealService practicestealService;
	@Resource
	private PersonalSealService personalSealService;
	@Override
	public void fetcheData(String tableName, String businessKey) {
		
		if(!StringUtil.isEmpty(businessKey)){
			Practicesteal practicesteal = practicestealService.getById(Long.parseLong(businessKey));
			if(practicesteal!=null){
				PersonalSeal personalSeal = personalSealService.getBySealNum(practicesteal.getSteal_num());
				if(null!=personalSeal){
					personalSeal.setStatus("1");
					personalSeal.setBorrower(practicesteal.getApplicant());
					personalSeal.setBorrowerID(practicesteal.getApplicantID());
					personalSealService.update(personalSeal);
				}
			}
			
		}
		
	}*/

}
