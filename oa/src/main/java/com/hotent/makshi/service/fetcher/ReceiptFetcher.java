package com.hotent.makshi.service.fetcher;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hotent.core.util.StringUtil;
import com.hotent.makshi.model.receipt.ReceiptAll;
import com.hotent.makshi.service.receipt.ReceiptAllService;

@Component("receiptFetcher")
public class ReceiptFetcher implements IFetcher {
	@Resource
	private ReceiptAllService receiptAllService;
	
	@Override
	public void fetcheData(String tableName, String businessKey) {
		
		if(!StringUtil.isEmpty(businessKey)){
			ReceiptAll entry = receiptAllService.getById(Long.parseLong(businessKey));
		}
		
	}

}
