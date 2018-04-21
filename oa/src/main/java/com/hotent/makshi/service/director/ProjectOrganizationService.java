package com.hotent.makshi.service.director;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.makshi.dao.director.ProjectOrganizationDao;
import com.hotent.makshi.model.director.ProjectOrganization;

@Service
public class ProjectOrganizationService extends BaseService<ProjectOrganization> {

	@Autowired
	private ProjectOrganizationDao projectOrganizationDao;

	@Override
	protected IEntityDao<ProjectOrganization, Long> getEntityDao() {
		return projectOrganizationDao;
	}

	public int count() {
		return (int) projectOrganizationDao.getOne("count", new HashMap<String, Object>());
	}

	public List<ProjectOrganization> page(Integer page, Integer pageSize) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (page <= 0)
			page = 1;
		params.put("begNum", (page - 1) * pageSize);
		params.put("pageSize", pageSize);
		return projectOrganizationDao.getBySqlKey("pages", params);
	}

}
