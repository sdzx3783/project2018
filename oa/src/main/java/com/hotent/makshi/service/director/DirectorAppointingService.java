package com.hotent.makshi.service.director;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.makshi.dao.director.DirectorAppointingDao;
import com.hotent.makshi.model.director.DirectorAppointing;

@Service
public class DirectorAppointingService extends BaseService<DirectorAppointing> {
	@Autowired
	private DirectorAppointingDao directorAppointingDao;

	@Override
	protected IEntityDao<DirectorAppointing, Long> getEntityDao() {
		return directorAppointingDao;
	}

	public int count() {
		return (int) directorAppointingDao.getOne("count", new HashMap<String, Object>());
	}

	public List<DirectorAppointing> page(Integer page, Integer pageSize) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (page <= 0)
			page = 1;
		params.put("begNum", (page - 1) * pageSize);
		params.put("pageSize", pageSize);
		return directorAppointingDao.getBySqlKey("pages", params);
	}
}
