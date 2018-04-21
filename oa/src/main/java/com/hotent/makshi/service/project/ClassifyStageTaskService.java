package com.hotent.makshi.service.project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.makshi.dao.project.ClassifyStageTaskDao;
import com.hotent.makshi.model.project.ClassifyStageTask;

@Service
public class ClassifyStageTaskService extends BaseService<ClassifyStageTask> {
	@Resource
	private ClassifyStageTaskDao dao;
	
	public ClassifyStageTaskService() {

	}

	@Override
	protected IEntityDao<ClassifyStageTask, Long> getEntityDao() {
		return dao;
	}

	@Override
	public void delByIds(Long[] ids) {
		if (ids == null || ids.length == 0) {
			return;
		}
		for (Long id : ids) {
			delById(id);
		}
	}

	@Override
	public void delById(Long id) {
		super.delById(id);
		deleteRelation(id);
	}

	private void deleteRelation(Long id) {
		// 将前置节点为baseId的阶段查找出来 prestage过滤掉baseid
		Integer baseId=id.intValue();
		List<ClassifyStageTask> prelist = getNodesOfPrestageTaskContainId(baseId);
		for (ClassifyStageTask classifyStageTask : prelist) {
			String _prestagetask = classifyStageTask.getPreclassifystagetaskid();
			if (_prestagetask != null && _prestagetask.trim().split(",").length > 0) {
				String[] split = _prestagetask.split(",");
				List<String> listTemp = Arrays.asList(split);
				List<String> asList = new ArrayList<>(listTemp);
				if (asList.contains(baseId + "")) {
					asList.remove(baseId + "");
					if (asList.size() == 0) {
						classifyStageTask.setPreclassifystagetaskid("");
					} else {
						String join = StringUtils.join(asList, ",");
						classifyStageTask.setPreclassifystagetaskid(join);
					}
					dao.update(classifyStageTask);
				}
			}
		}
		
		List<ClassifyStageTask> afterlist = getNodesOfAfterstageTaskContainId(baseId);
		for (ClassifyStageTask classifyStageTask : afterlist) {
			String _afterstagetask = classifyStageTask.getAfterclassifystagetaskid();
			if (_afterstagetask != null && _afterstagetask.trim().split(",").length > 0) {
				String[] split = _afterstagetask.split(",");
				List<String> listTemp = Arrays.asList(split);
				List<String> asList = new ArrayList<>(listTemp);
				if (asList.contains(baseId + "")) {
					asList.remove(baseId + "");
					if (asList.size() == 0) {
						classifyStageTask.setAfterclassifystagetaskid("");
					} else {
						String join = StringUtils.join(asList, ",");
						classifyStageTask.setAfterclassifystagetaskid(join);
					}
					dao.update(classifyStageTask);
				}
			}
		}
		
		// 将可查看节点为baseId的阶段查找出来 querystage过滤掉baseid
		List<ClassifyStageTask> querylist = getNodesOfQuerystageTaskContainId(baseId);
		for (ClassifyStageTask classifyStageTask : querylist) {
			String _querystagetask = classifyStageTask.getQueryclassifystagetaskid();
			if (_querystagetask != null && _querystagetask.trim().split(",").length > 0) {
				String[] split = _querystagetask.split(",");
				List<String> listTemp = Arrays.asList(split);
				List<String> asList = new ArrayList<>(listTemp);
				if (asList.contains(baseId + "")) {
					asList.remove(baseId + "");
					if (asList.size() == 0) {
						classifyStageTask.setQueryclassifystagetaskid("");
					} else {
						String join = StringUtils.join(asList, ",");
						classifyStageTask.setQueryclassifystagetaskid(join);
					}
					dao.update(classifyStageTask);
				}
			}
		}
	}

	public void delByStageIds(Long[] lAryId) {
		if (lAryId == null || lAryId.length == 0) {
			return;
		} else {
			for (Long classifystageid : lAryId) {
				delByStageId(classifystageid);
			}
		}

	}

	private void delByStageId(Long classifystageid) {
		Map<String, Object> params = new HashMap<>();
		params.put("classifystageid", classifystageid);
		dao.getBySqlKey("delByStageId", params);
	}

	public List<ClassifyStageTask> getStageTaskByStageId(Long classifystageid) {
		Map<String, Object> params = new HashMap<>();
		params.put("classifystageid", classifystageid);
		params.put("isdelete", 0);
		return dao.getBySqlKey("getByStageId", params);
	}
	
	public List<ClassifyStageTask> getStageTaskByStageId(QueryFilter filter) {
		
		return dao.getBySqlKey("getByStageId", filter);
	}

	public int getMaxtasknoByClassifystageid(Long classifystageid) {
		Map<String, Object> params = new HashMap<>();
		params.put("classifystageid", classifystageid);
		params.put("orderField", "taskNo");
		params.put("orderSeq", "desc");
		List<ClassifyStageTask> bySqlKey = dao.getBySqlKey("getByStageId", params);
		if (bySqlKey == null || bySqlKey.size() == 0) {
			return 0;
		} else {
			return bySqlKey.get(0).getTaskno();
		}
	}

	public void update(ClassifyStageTask classifyStageTask, boolean updatePreAndAfterRelation) {
		super.update(classifyStageTask);
		if (updatePreAndAfterRelation) {
			updatePreAndAfterRelation(classifyStageTask);
		}
	}

	/**
	 * 维护前后置节点的更新
	 * 
	 * @param entity
	 */
	public void updatePreAndAfterRelation(ClassifyStageTask entity) {
		Integer baseId = entity.getId();
		String afterstagetask = entity.getAfterclassifystagetaskid();
		String prestagetask = entity.getPreclassifystagetaskid();
		// 将前置节点为baseId的阶段查找出来 prestage过滤掉baseid
		List<ClassifyStageTask> prelist = getNodesOfPrestageTaskContainId(baseId);
		for (ClassifyStageTask classifyStageTask : prelist) {
			String _prestagetask = classifyStageTask.getPreclassifystagetaskid();
			if (_prestagetask != null && _prestagetask.trim().split(",").length > 0) {
				String[] split = _prestagetask.split(",");
				List<String> listTemp = Arrays.asList(split);
				List<String> asList = new ArrayList<>(listTemp);
				if (asList.contains(baseId + "")) {
					asList.remove(baseId + "");
					if (asList.size() == 0) {
						classifyStageTask.setPreclassifystagetaskid("");
					} else {
						String join = StringUtils.join(asList, ",");
						classifyStageTask.setPreclassifystagetaskid(join);
					}
					dao.update(classifyStageTask);
				}
			}
		}

		if (afterstagetask != null && afterstagetask.trim().length() > 0) {
			String[] split = afterstagetask.trim().split(",");
			for (String string : split) {
				ClassifyStageTask afternode = dao.getById(Long.parseLong(string));
				if (afternode != null) {
					String prestagetaskOfAfterNode = afternode.getPreclassifystagetaskid();
					if (prestagetaskOfAfterNode != null && prestagetaskOfAfterNode.trim().length() > 0) {
						String[] split2 = prestagetaskOfAfterNode.trim().split(",");
						List<String> listTemp = Arrays.asList(split2);
						List<String> list = new ArrayList<>(listTemp);
						if (!list.contains(baseId + "")) {
							list.add(baseId + "");
							String join = StringUtils.join(list, ",");
							afternode.setPreclassifystagetaskid(join);
							dao.update(afternode);
						}

					} else {
						afternode.setPreclassifystagetaskid(baseId + "");
						dao.update(afternode);
					}
				}
			}
		}
		List<ClassifyStageTask> afterlist = getNodesOfAfterstageTaskContainId(baseId);
		for (ClassifyStageTask classifyStageTask : afterlist) {
			String _afterstagetask = classifyStageTask.getAfterclassifystagetaskid();
			if (_afterstagetask != null && _afterstagetask.trim().split(",").length > 0) {
				String[] split = _afterstagetask.split(",");
				List<String> listTemp = Arrays.asList(split);
				List<String> asList = new ArrayList<>(listTemp);
				if (asList.contains(baseId + "")) {
					asList.remove(baseId + "");
					if (asList.size() == 0) {
						classifyStageTask.setAfterclassifystagetaskid("");
					} else {
						String join = StringUtils.join(asList, ",");
						classifyStageTask.setAfterclassifystagetaskid(join);
					}
					dao.update(classifyStageTask);
				}
			}
		}

		if (prestagetask != null && prestagetask.trim().length() > 0) {
			String[] split = prestagetask.trim().split(",");
			for (String string : split) {
				ClassifyStageTask prenode = dao.getById(Long.parseLong(string));
				if (prenode != null) {
					String afterstagetaskOfPreNode = prenode.getAfterclassifystagetaskid();
					if (afterstagetaskOfPreNode != null && afterstagetaskOfPreNode.trim().length() > 0) {
						String[] split2 = afterstagetaskOfPreNode.trim().split(",");
						List<String> listTemp = Arrays.asList(split2);
						List<String> list = new ArrayList<>(listTemp);
						if (!list.contains(baseId + "")) {
							list.add(baseId + "");
							String join = StringUtils.join(list, ",");
							prenode.setAfterclassifystagetaskid(join);
							dao.update(prenode);
						}

					} else {
						prenode.setAfterclassifystagetaskid(baseId + "");
						dao.update(prenode);
					}
				}
			}
		}
	}

	public List<ClassifyStageTask> getNodesOfPrestageTaskContainId(Integer baseId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("containedId", baseId);
		params.put("preclassifystagetaskid", 1);
		List<ClassifyStageTask> list = dao.getBySqlKey("getNodesOfstageTaskContainId", params);
		return list;
	}

	public List<ClassifyStageTask> getNodesOfAfterstageTaskContainId(Integer baseId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("containedId", baseId);
		params.put("afterclassifystagetaskid", 1);
		List<ClassifyStageTask> list = dao.getBySqlKey("getNodesOfstageTaskContainId", params);
		return list;
	}
	
	public List<ClassifyStageTask> getNodesOfQuerystageTaskContainId(Integer baseId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("containedId", baseId);
		params.put("queryclassifystagetaskid", 1);
		List<ClassifyStageTask> list = dao.getBySqlKey("getNodesOfstageTaskContainId", params);
		return list;
	}

	public long getTaskCountByClassifyid(Long id) {
		Map<String,Object>  params=new HashMap<String, Object>();
		params.put("classifylibraryid", id);
		long count = (long) dao.getOne("getTaskCountByClassifyId", params);
		return count;
	}

	public long getTaskCountByStageid(Long id) {
		Map<String,Object>  params=new HashMap<String, Object>();
		params.put("classifystageid", id);
		params.put("isdelete", 0);
		long count = (long) dao.getOne("getTaskCountByStageId", params);
		return count;
	}
}
