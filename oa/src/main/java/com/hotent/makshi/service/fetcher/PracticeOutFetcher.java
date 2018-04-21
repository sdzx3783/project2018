/**
 * 
 */
package com.hotent.makshi.service.fetcher;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.hotent.core.util.StringUtil;
import com.hotent.makshi.dao.userinfo.EntryVocationQualificationDao;
import com.hotent.makshi.model.title.PersonalQualificationOut;
import com.hotent.makshi.model.title.PersonalQualificationRegist;
import com.hotent.makshi.model.title.Practiceregist;
import com.hotent.makshi.model.userinfo.EntryVocationQualification;
import com.hotent.makshi.model.userinfo.UserInfomation;
import com.hotent.makshi.service.common.FlowToEntityService;
import com.hotent.makshi.service.title.PersonalQualificationOutService;
import com.hotent.makshi.service.title.PersonalQualificationRegistService;
import com.hotent.makshi.service.title.PracticeregistService;
import com.hotent.makshi.service.title.VocationQualificationService;
import com.hotent.makshi.service.userinfo.UserInfomationService;

/**
 * @author cesc
 *个人执业资格关联
 */
@Component("practiceOutFetcher")
public class PracticeOutFetcher implements IFetcher {
	private final Logger log = Logger.getLogger(this.getClass());

	@Resource
	private PersonalQualificationOutService outService;
	@Resource
	private UserInfomationService infomationService;
	@Resource
	private VocationQualificationService vocationQualificationService;
	@Resource
	private FlowToEntityService flowToEntityService;
	@Override
	public void fetcheData(String tableName, String businessKey) {
		
		if(!StringUtil.isEmpty(businessKey)){
			
			PersonalQualificationOut personalQualificationOut = outService.getById(Long.parseLong(businessKey));
			if(personalQualificationOut!=null){
				UserInfomation userInfomation = infomationService.getUserInfomationByUid(Long.parseLong(personalQualificationOut.getNameID()));
				if(userInfomation==null){
					try {
						userInfomation = new UserInfomation();
						userInfomation.setUserId(Long.parseLong(personalQualificationOut.getNameID()));
						infomationService.save(userInfomation);
						userInfomation = infomationService.getUserInfomationByUid(Long.parseLong(personalQualificationOut.getNameID()));
					} catch (Exception e) {
						log.error("错误信息",e);
					}
					
				}
				
//				List<EntryVocationQualification> entryVocationQualificationList = infomationService.getEntryVocationQualificationList(userInfomation.getId());
//				EntryVocationQualification vocationQualification = new EntryVocationQualification(userInfomation.getId(),personalQualificationRegist.getRegist_certificate_num(), 
//						personalQualificationRegist.getPractis_certificate_type(), personalQualificationRegist.getRegist_certificate_com(),personalQualificationRegist.getRegist_major(),
//						personalQualificationRegist.getRegist_certificate_issue_date(), flowToEntityService.flowToEntityFile(personalQualificationRegist.getRegist_file()),
//						"0","1",personalQualificationRegist.getRegist_remark(),personalQualificationRegist.getMajor(),personalQualificationRegist.getPractis_certificate_num(),
//						personalQualificationRegist.getPractis_certificate_issue_date(),personalQualificationRegist.getPractis_certificate_com(),personalQualificationRegist.getPractis_certificate_remark(),
//						flowToEntityService.flowToEntityFile(personalQualificationRegist.getPractis_certificate_file()),personalQualificationRegist.getRegist_num(),personalQualificationRegist.getRegist_certificate_effective_date(),
//						personalQualificationRegist.getSeal_num(),personalQualificationRegist.getOut_date(),personalQualificationRegist.getRegist_type(),personalQualificationRegist.getRegist_date(),
//						personalQualificationRegist.getContine_edu_comple());
//				
//				
//				if(entryVocationQualificationList==null){
//					entryVocationQualificationList = new ArrayList<EntryVocationQualification>();
//				}
//				entryVocationQualificationList.add(vocationQualification);
//				userInfomation.setEntryVocationQualificationList(entryVocationQualificationList);
//				try {
//					infomationService.addSubList(userInfomation);
//				} catch (Exception e) {
//					log.error("错误信息",e);
//				}
				
			}
			
			
			
		}
		
	}

}
