/**
 * 
 */
package com.hotent.makshi.service.fetcher;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.hotent.core.util.StringUtil;
import com.hotent.makshi.model.contract.ContractBorrowApplication;
import com.hotent.makshi.model.contract.Contractinfo;
import com.hotent.makshi.model.seal.SealBorrow;
import com.hotent.makshi.service.contract.ContractBorrowApplicationService;
import com.hotent.makshi.service.contract.ContractinfoService;
import com.hotent.makshi.service.seal.SealBorrowService;



/**
 * 跟新归还日期
 * @author zami
 *
 */
@Component("sealBorrowFetcher")
public class SealBorrowFetcher implements IFetcher {
	@Resource
	private SealBorrowService  sealBorrowService;
	private static Logger logger=Logger.getLogger(QualificationFetcher.class);
	
	
	public void fetcheData(String tableName, String businessKey) {
		logger.info("--------流程数据转移到业务表开始---------------");
		try {
			SealBorrow sealBorrow = sealBorrowService.getById(Long.parseLong(businessKey));
			sealBorrow.setBackDate(new Date());
			sealBorrowService.update(sealBorrow);
		} catch (Exception e) {
			logger.error("同步归还时间失败",e);
		}
	}

}
