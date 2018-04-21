package com.hotent.makshi.service.contract;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.makshi.dao.contract.ContractStatisticsDao;
import com.hotent.makshi.model.contract.ContractStatistics;

@Service
public class ContractStatisticsService extends BaseService<ContractStatistics> {
	@Resource
	private ContractStatisticsDao dao;

	public ContractStatisticsService() {
	}

	@Override
	protected IEntityDao<ContractStatistics, Long> getEntityDao() {
		return dao;
	}

	public List<ContractStatistics> getAllContractStatistics(String num) {
		List<Integer> yearList = new ArrayList<Integer>();
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		Integer year = Integer.valueOf(c.get(Calendar.YEAR));
		yearList.add(year);
		if(null!=num && !("").equals(num) && Integer.valueOf(num)>0 ){
			for(int i=0;i<Integer.valueOf(num);i++){
				yearList.add(year-i);
			}
		}
		List<ContractStatistics> list = dao.getBySqlKey("getAllContractStatistics", yearList);
		return list;
	}

}
