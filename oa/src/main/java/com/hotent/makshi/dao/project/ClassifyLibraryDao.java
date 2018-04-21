package com.hotent.makshi.dao.project;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
import com.hotent.core.util.StringUtil;
import com.hotent.makshi.model.project.ClassifyLibrary;

@Repository
public class ClassifyLibraryDao extends BaseDao<ClassifyLibrary> {

	@Override
	public Class<?> getEntityClass() {
		return ClassifyLibrary.class;
	}

	public List<ClassifyLibrary> getByCatePath(String path) {
		Map<String, String> params=new HashMap<String, String>();
		params.put("path",   StringUtil.isNotEmpty(path)?(path+"%"):"");
		return getBySqlKey("getByCatePath",params);
	}

}
