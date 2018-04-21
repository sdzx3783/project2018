package com.hotent.makshi.service.title;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.makshi.dao.title.CertificatePersonDao;
import com.hotent.makshi.model.title.CertificatePerson;

@Service
public class CertificatePersonService extends BaseService<CertificatePerson> {
	@Resource
	private CertificatePersonDao dao;

	public CertificatePersonService() {
	}

	@Override
	protected IEntityDao<CertificatePerson, Long> getEntityDao() {
		return dao;
	}

	public List<CertificatePerson> getAllinfo(String selectYear) {
		return dao.getBySqlKey("getAllinfo",selectYear);
	}

}
