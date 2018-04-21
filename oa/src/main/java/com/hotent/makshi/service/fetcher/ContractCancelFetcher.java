/**
 * 
 */
package com.hotent.makshi.service.fetcher;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hotent.core.util.StringUtil;
import com.hotent.makshi.model.contract.ContractCancelApplication;
import com.hotent.makshi.model.contract.Contractinfo;
import com.hotent.makshi.service.contract.ContractCancelApplicationService;
import com.hotent.makshi.service.contract.ContractinfoService;

/**
 * @author cesc
 *合同作废关联
 */
@Component("contractCancelFetcher")
public class ContractCancelFetcher implements IFetcher {
	@Resource
	private ContractCancelApplicationService cancelApplicationService;
	@Resource
	private ContractinfoService infoService;
	@Override
	public void fetcheData(String tableName, String businessKey) {
		
		if(!StringUtil.isEmpty(businessKey)){
			ContractCancelApplication contractCancel = cancelApplicationService.getById(Long.parseLong(businessKey));
			if(contractCancel!=null){
				Contractinfo contractinfo = infoService.getContractinfoByNum(contractCancel.getContract_num());
				if(null!=contractinfo){
					contractinfo.setContract_status("1");
					infoService.update(contractinfo);
				}
			}
			
		}
		
	}

}
