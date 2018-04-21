/**
 * 
 */
package com.hotent.makshi.service.fetcher;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hotent.core.util.StringUtil;
import com.hotent.makshi.model.contract.ContractBorrowApplication;
import com.hotent.makshi.model.contract.Contractinfo;
import com.hotent.makshi.service.contract.ContractBorrowApplicationService;
import com.hotent.makshi.service.contract.ContractinfoService;

/**
 * @author cesc
 *合同借阅关联
 */
@Component("contractBorrowFetcher")
public class ContractBorrowFetcher implements IFetcher {
	@Resource
	private ContractBorrowApplicationService borrowService;
	@Resource
	private ContractinfoService infoService;
	@Override
	public void fetcheData(String tableName, String businessKey) {
		
		if(!StringUtil.isEmpty(businessKey)){
			ContractBorrowApplication borrowContract = borrowService.getById(Long.parseLong(businessKey));
			if(borrowContract!=null){
				Contractinfo contractinfo = infoService.getContractinfoByNum(borrowContract.getContract_num());
				if(null!=contractinfo){
					contractinfo.setCustodian(borrowContract.getBorrower());
					infoService.update(contractinfo);
					borrowContract.setReturn_date(new Date());
					borrowService.update(borrowContract);
				}
			}
			
		}
		
	}

}
