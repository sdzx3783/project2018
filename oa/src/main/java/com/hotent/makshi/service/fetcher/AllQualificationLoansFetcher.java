/**
 * 
 */
package com.hotent.makshi.service.fetcher;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.hotent.core.util.StringUtil;
import com.hotent.makshi.dao.qualification.JyzzDao;
import com.hotent.makshi.model.qualification.CompanyQualificationCertificate;
import com.hotent.makshi.model.qualification.Jyzz;
import com.hotent.makshi.service.qualification.CompanyQualificationCertificateService;

/**
 * 公司资质借用信息同步：跟新所借资质为未借出
 * @author zami
 *
 */
@Component("allQualificationLoansFetcher")
public class AllQualificationLoansFetcher implements IFetcher {
	@Resource
	private JyzzDao jyzzDao;
	@Resource
	private CompanyQualificationCertificateService companyQualificationCertificateService;
	
	private static Logger logger=Logger.getLogger(AllQualificationLoansFetcher.class);
	
	@Override
	public void fetcheData(String tableName, String businessKey) throws Exception {
		String message = "流程数据同步成功";
		try {
			if(!StringUtil.isEmpty(businessKey)){
				List<Jyzz> list = jyzzDao.getByMainId(Long.valueOf(businessKey));
				for(Jyzz jyzz : list){
					String companyQualificationId = jyzz.getListId();
					String listBack = jyzz.getListBack();
					CompanyQualificationCertificate companyQualificationCertificate = companyQualificationCertificateService.getById(Long.valueOf(companyQualificationId));
					if(!StringUtil.isEmpty(listBack) && Integer.valueOf(listBack)==1){
						companyQualificationCertificate.setIsborrowed("0");
					}
					if(StringUtil.isEmpty(listBack)){
						companyQualificationCertificate.setIsborrowed("1");
					}
					companyQualificationCertificateService.save(companyQualificationCertificate);
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