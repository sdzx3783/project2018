package com.hotent.makshi.service.addrBook;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.makshi.dao.addrBook.CompanyBookDao;
import com.hotent.makshi.model.addrBook.CompanyBook;


@Service
public class CompanyBookService extends BaseService<CompanyBook>
{
	@Resource
	private CompanyBookDao CompanyBookDao;
	
	public CompanyBookService()
	{
	}
	
	@Override
	protected IEntityDao<CompanyBook, Long> getEntityDao() 
	{
		return CompanyBookDao;
	}

	public List<CompanyBook> getAlls(Map<String, Object> param) {

		return CompanyBookDao.getBySqlKey("getAlls", param);
	}
	
	
	public List<CompanyBook> exportAll(Map<String, Object> param) {

		return CompanyBookDao.getBySqlKey("exportAll", param);
	}
	
}
