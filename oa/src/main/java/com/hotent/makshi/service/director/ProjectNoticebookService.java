package com.hotent.makshi.service.director;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.makshi.dao.director.ProjectNoticebookDao;
import com.hotent.makshi.model.director.ProjectNoticebook;

@Service
public class ProjectNoticebookService extends BaseService<ProjectNoticebook> {

	@Autowired
	private ProjectNoticebookDao projectNoticebookDao;

	@Override
	protected IEntityDao<ProjectNoticebook, Long> getEntityDao() {
		return projectNoticebookDao;
	}

	public int count() {
		return (int) projectNoticebookDao.getOne("count", new HashMap<String, Object>());
	}

	public List<ProjectNoticebook> page(Integer page, Integer pageSize) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (page <= 0)
			page = 1;
		params.put("begNum", (page - 1) * pageSize);
		params.put("pageSize", pageSize);
		return projectNoticebookDao.getBySqlKey("pages", params);
	}
}
