/**
 * 
 */
package com.hotent.makshi.service.fetcher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.hotent.core.util.StringUtil;
import com.hotent.makshi.dao.title.PersonalSealDao;
import com.hotent.makshi.model.qualification.CertificateAndBorrow;
import com.hotent.makshi.model.title.CertificateBorrow;
import com.hotent.makshi.model.title.PersonalSeal;
import com.hotent.makshi.service.qualification.CertificateAndBorrowService;
import com.hotent.makshi.service.title.CertificateBorrowService;
import com.hotent.platform.model.system.Dictionary;
import com.hotent.platform.service.system.DictionaryService;

@Component("certificateBorrowFetcher")
public class CertificateBorrowFetcher implements IFetcher {
	private final Logger log = Logger.getLogger(this.getClass());
	// private static final String String = null;
	@Resource
	private CertificateBorrowService certificateBorrowService;
	@Resource
	private PersonalSealDao personalSealDao;
	@Resource
	private CertificateAndBorrowService certificateAndBorrowService;
	@Resource
	private DictionaryService dictionaryService;
	@Override
	public void fetcheData(String tableName, String businessKey) {
		if (!StringUtil.isEmpty(businessKey)) {
			try {
				CertificateBorrow certificateBorrow = certificateBorrowService.getById(Long.parseLong(businessKey));
				if (null != certificateBorrow) {
					certificateBorrow.setFinish(1);
					certificateBorrowService.update(certificateBorrow);
					Integer type = 1;
					type = Integer.valueOf(certificateBorrow.getType());
					String certificateId = certificateBorrow.getBorrow_data_num();
					String name = certificateBorrow.getSteal_name();
					CertificateAndBorrow certificateAndBorrow = new CertificateAndBorrow();
					if (Integer.valueOf(type) != 3) {
						//修改从业证书证书名称问题
						if(Integer.valueOf(type) == 4){
							List<Dictionary> dicList = dictionaryService.getByTypeId(40000000130135L, false);
							for(Dictionary dictionary : dicList){
								if(dictionary.getItemName().equals(name)){
									name = dictionary.getItemValue();
								}
							}
						}
						List<CertificateAndBorrow> cerList = certificateAndBorrowService.getByTypeAndCertificateId(type, name, certificateId);
						if (cerList.size() > 0) {
							certificateAndBorrow = cerList.get(0);
							certificateAndBorrow.setBorrower(null);
							certificateAndBorrow.setBorrowerId(null);
							certificateAndBorrowService.update(certificateAndBorrow);
						}
					}
					// 执业印章证
					if (type == 3) {
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("name", name);
						params.put("userName", certificateBorrow.getFullname());
						params.put("certificateId", certificateId);
						List<PersonalSeal> personalSealList = personalSealDao.getBySqlKey("getByCertificateId", params);
						if (personalSealList.size() > 0) {
							PersonalSeal personalSeal = personalSealList.get(0);
							personalSeal.setStatus("0");
							personalSeal.setBorrower(null);
							personalSeal.setBorrowerID(null);
							personalSeal.setBorrowDate(certificateBorrow.getBorrow_date());
							personalSealDao.update(personalSeal);
						}
					}
				}
			} catch (Exception e) {
				log.error("错误信息", e);
			}
		}

	}

}
