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
import com.hotent.makshi.model.title.TitleDeclaration;
import com.hotent.makshi.model.userinfo.EntryProfessional;
import com.hotent.makshi.model.userinfo.UserInfomation;
import com.hotent.makshi.service.title.TitleDeclarationService;
import com.hotent.makshi.service.userinfo.UserInfomationService;

/**
 * @author cesc
 *职称申报关联
 */
@Component("titleDeclarationFetcher")
public class TitleDeclarationFetcher implements IFetcher {
	private final Logger log = Logger.getLogger(this.getClass());
	@Resource
	private TitleDeclarationService titleDeclarationService;
	@Resource
	private UserInfomationService infomationService;
	@Override
	public void fetcheData(String tableName, String businessKey) {
		
		if(!StringUtil.isEmpty(businessKey)){
			
			TitleDeclaration titleDeclaration = titleDeclarationService.getById(Long.parseLong(businessKey));
			if(titleDeclaration!=null){
				UserInfomation userInfomation = infomationService.getUserInfomationByUid(Long.parseLong(titleDeclaration.getUserNo()));
				if(userInfomation==null){
					try {
						userInfomation = new UserInfomation();
						userInfomation.setUserId(Long.parseLong(titleDeclaration.getUserNo()));
						infomationService.save(userInfomation);
						userInfomation = infomationService.getUserInfomationByUid(Long.parseLong(titleDeclaration.getUserNo()));
					} catch (Exception e) {
						log.error("错误信息",e);
					}
					
				}
				List<EntryProfessional> entryProfessionalList = infomationService.getEntryProfessionalList(userInfomation.getId());
				EntryProfessional professional = new EntryProfessional(userInfomation.getId(),titleDeclaration.getCertificate_num(), titleDeclaration.getTitle_name(), 
				titleDeclaration.getIssuing_authority(), titleDeclaration.getTitle_professional(), titleDeclaration.getIssuing_time(), titleDeclaration.getFile());
				
				if(entryProfessionalList==null){
					entryProfessionalList = new ArrayList<EntryProfessional>();
				}
				entryProfessionalList.add(professional);
				userInfomation.setEntryProfessionalList(entryProfessionalList);
				try {
					infomationService.addSubList(userInfomation);
				} catch (Exception e) {
					log.error("错误信息",e);
				}
				
			}
			
			
			
		}
		
	}

}
