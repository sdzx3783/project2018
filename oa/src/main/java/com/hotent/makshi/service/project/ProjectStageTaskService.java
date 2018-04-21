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
import com.hotent.makshi.dao.project.ProjectStageTaskDao;
import com.hotent.makshi.model.project.ProjectStageTask;

@Service
public class ProjectStageTaskService extends BaseService<ProjectStageTask> {
	@Resource
	private ProjectStageTaskDao dao;

	public ProjectStageTaskService() {

	}

	@Override
	protected IEntityDao<ProjectStageTask, Long> getEntityDao() {
		return dao;
	}

	public void setTask(ProjectStageTask task) {
		dao.update("setTask", task);
	}

	public List<ProjectStageTask> getProjectStageTaskByStageId(Integer stageId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("stageId", stageId);
		return dao.getBySqlKey("getProjectStageTaskByStageId", params);
	}

	public List<ProjectStageTask> getStageTaskByStageId(Integer stageId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("stageId", stageId);
		return dao.getBySqlKey("getStageTaskByStageId", params);
	}

	public ProjectStageTask getProjectStageTaskByStageIdAndCstId(Integer stageId, String cstId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("stageId", stageId);
		params.put("cstId", cstId);
		return dao.getUnique("getProjectStageTaskByStageIdAndCstId", params);
	}

	/**
	 * 包括isdelete=0
	 * 
	 * @param stageId
	 * @param cstId
	 * @return
	 */
	public ProjectStageTask getStageTaskByStageIdAndCstId(Integer stageId, String cstId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("stageId", stageId);
		params.put("cstId", cstId);
		return dao.getUnique("getStageTaskByStageIdAndCstId", params);
	}

	public long getMaxCstIdByProjectstageid(Integer stageId) {
		Map<String, Object> params = new HashMap<>();
		params.put("stageId", stageId);
		return (long) dao.getOne("getMaxCstIdByProjectstageid", params);
	}

	public int getMaxtasknoByProjectstageid(Integer stageid) {
		Map<String, Object> params = new HashMap<>();
		params.put("prostageid", stageid);
		params.put("isdelete", 0);
		params.put("orderField", "taskNo");
		params.put("orderSeq", "desc");
		List<ProjectStageTask> bySqlKey = dao.getBySqlKey("getByStageId", params);
		if (bySqlKey == null || bySqlKey.size() == 0) {
			return 0;
		} else {
			return bySqlKey.get(0).getTaskno();
		}
	}

	@Override
	public void delByIds(Long[] ids) {
		if (ids == null || ids.length == 0) {
			return;
		}
		for (Long id : ids) {
			ProjectStageTask byId = getById(id);
			if (byId != null) {
				delById(id, byId.getProstageid(), byId.getCstid());
			}
		}
	}

	public void delById(Long id, Integer stageid, String cstId) {
		super.delById(id);
		deleteRelation(id, stageid, cstId);
	}

	private void deleteRelation(Long id, Integer stageid, String cstId) {
		// 将前置节点为baseId的阶段查找出来 prestage过滤掉baseid
		String baseId = cstId;
		List<ProjectStageTask> prelist = getNodesOfPrestageTaskContainId(stageid, cstId);
		for (ProjectStageTask projectStageTask : prelist) {
			String _prestagetask = projectStageTask.getPretaskid();
			if (_prestagetask != null && _prestagetask.trim().split(",").length > 0) {
				String[] split = _prestagetask.split(",");
				List<String> listTemp = Arrays.asList(split);
				List<String> asList = new ArrayList<>(listTemp);
				if (asList.contains(baseId + "")) {
					asList.remove(baseId + "");
					if (asList.size() == 0) {
						projectStageTask.setPretaskid("");
					} else {
						String join = StringUtils.join(asList, ",");
						projectStageTask.setPretaskid(join);
					}
					dao.update(projectStageTask);
				}
			}
		}

		List<ProjectStageTask> afterlist = getNodesOfAfterstageTaskContainId(stageid, cstId);
		for (ProjectStageTask projectStageTask : afterlist) {
			String _afterstagetask = projectStageTask.getAftertaskid();
			if (_afterstagetask != null && _afterstagetask.trim().split(",").length > 0) {
				String[] split = _afterstagetask.split(",");
				List<String> listTemp = Arrays.asList(split);
				List<String> asList = new ArrayList<>(listTemp);
				if (asList.contains(baseId + "")) {
					asList.remove(baseId + "");
					if (asList.size() == 0) {
						projectStageTask.setAftertaskid("");
					} else {
						String join = StringUtils.join(asList, ",");
						projectStageTask.setAftertaskid(join);
					}
					dao.update(projectStageTask);
				}
			}
		}

		// 将可查看节点为baseId的阶段查找出来 querystage过滤掉baseid
		List<ProjectStageTask> querylist = getNodesOfQuerystageTaskContainId(stageid, cstId);
		for (ProjectStageTask projectStageTask : querylist) {
			String _querystagetask = projectStageTask.getQuerytaskid();
			if (_querystagetask != null && _querystagetask.trim().split(",").length > 0) {
				String[] split = _querystagetask.split(",");
				List<String> listTemp = Arrays.asList(split);
				List<String> asList = new ArrayList<>(listTemp);
				if (asList.contains(baseId + "")) {
					asList.remove(baseId + "");
					if (asList.size() == 0) {
						projectStageTask.setQuerytaskid("");
					} else {
						String join = StringUtils.join(asList, ",");
						projectStageTask.setQuerytaskid(join);
					}
					dao.update(projectStageTask);
				}
			}
		}
	}

	public void update(ProjectStageTask projectStageTask, boolean updatePreAndAfterRelation) {
		super.update(projectStageTask);
		if (updatePreAndAfterRelation) {
			updatePreAndAfterRelation(projectStageTask);
		}
	}

	/**
	 * 维护前后置节点的更新
	 * 
	 * @param entity
	 */
	public void updatePreAndAfterRelation(ProjectStageTask entity) {
		String baseId = entity.getCstid();
		String afterstagetask = entity.getAftertaskid();
		String prestagetask = entity.getPretaskid();
		// 将前置节点为baseId的阶段查找出来 prestage过滤掉baseid
		List<ProjectStageTask> prelist = getNodesOfPrestageTaskContainId(entity.getProstageid(), baseId);
		for (ProjectStageTask projectStageTask : prelist) {
			String _prestagetask = projectStageTask.getPretaskid();
			if (_prestagetask != null && _prestagetask.trim().split(",").length > 0) {
				String[] split = _prestagetask.split(",");
				List<String> listTemp = Arrays.asList(split);
				List<String> asList = new ArrayList<>(listTemp);
				if (asList.contains(baseId + "")) {
					asList.remove(baseId + "");
					if (asList.size() == 0) {
						projectStageTask.setPretaskid("");
					} else {
						String join = StringUtils.join(asList, ",");
						projectStageTask.setPretaskid(join);
					}
					dao.update(projectStageTask);
				}
			}
		}

		if (afterstagetask != null && afterstagetask.trim().length() > 0) {
			String[] split = afterstagetask.trim().split(",");
			for (String string : split) {
				ProjectStageTask afternode = getStageTaskByStageIdAndCstId(entity.getProstageid(), string.trim());
				if (afternode != null) {
					String prestagetaskOfAfterNode = afternode.getPretaskid();
					if (prestagetaskOfAfterNode != null && prestagetaskOfAfterNode.trim().length() > 0) {
						String[] split2 = prestagetaskOfAfterNode.trim().split(",");
						List<String> listTemp = Arrays.asList(split2);
						List<String> list = new ArrayList<>(listTemp);
						if (!list.contains(baseId + "")) {
							list.add(baseId + "");
							String join = StringUtils.join(list, ",");
							afternode.setPretaskid(join);
							dao.update(afternode);
						}

					} else {
						afternode.setPretaskid(baseId + "");
						dao.update(afternode);
					}
				}
			}
		}
		List<ProjectStageTask> afterlist = getNodesOfAfterstageTaskContainId(entity.getProstageid(), baseId);
		for (ProjectStageTask projectStageTask : afterlist) {
			String _afterstagetask = projectStageTask.getAftertaskid();
			if (_afterstagetask != null && _afterstagetask.trim().split(",").length > 0) {
				String[] split = _afterstagetask.split(",");
				List<String> listTemp = Arrays.asList(split);
				List<String> asList = new ArrayList<>(listTemp);
				if (asList.contains(baseId + "")) {
					asList.remove(baseId + "");
					if (asList.size() == 0) {
						projectStageTask.setAftertaskid("");
					} else {
						String join = StringUtils.join(asList, ",");
						projectStageTask.setAftertaskid(join);
					}
					dao.update(projectStageTask);
				}
			}
		}

		if (prestagetask != null && prestagetask.trim().length() > 0) {
			String[] split = prestagetask.trim().split(",");
			for (String string : split) {
				ProjectStageTask prenode = getStageTaskByStageIdAndCstId(entity.getProstageid(), string.trim());
				if (prenode != null) {
					String afterstagetaskOfPreNode = prenode.getAftertaskid();
					if (afterstagetaskOfPreNode != null && afterstagetaskOfPreNode.trim().length() > 0) {
						String[] split2 = afterstagetaskOfPreNode.trim().split(",");
						List<String> listTemp = Arrays.asList(split2);
						List<String> list = new ArrayList<>(listTemp);
						if (!list.contains(baseId + "")) {
							list.add(baseId + "");
							String join = StringUtils.join(list, ",");
							prenode.setAftertaskid(join);
							dao.update(prenode);
						}

					} else {
						prenode.setAftertaskid(baseId + "");
						dao.update(prenode);
					}
				}
			}
		}
	}

	public List<ProjectStageTask> getNodesOfPrestageTaskContainId(Integer stageid, String baseId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("containedId", baseId);
		params.put("stageId", stageid);
		params.put("preprojectstagetaskid", 1);
		List<ProjectStageTask> list = dao.getBySqlKey("getNodesOfstageTaskContainId", params);
		return list;
	}

	public List<ProjectStageTask> getNodesOfAfterstageTaskContainId(Integer stageid, String baseId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("containedId", baseId);
		params.put("stageId", stageid);
		params.put("afterprojectstagetaskid", 1);
		List<ProjectStageTask> list = dao.getBySqlKey("getNodesOfstageTaskContainId", params);
		return list;
	}

	public List<ProjectStageTask> getNodesOfQuerystageTaskContainId(Integer stageid, String baseId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("containedId", baseId);
		params.put("stageId", stageid);
		params.put("queryprojectstagetaskid", 1);
		List<ProjectStageTask> list = dao.getBySqlKey("getNodesOfstageTaskContainId", params);
		return list;
	}

	public void delByProjectId(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("projectId", id);
		dao.getBySqlKey("delByProjectId", params);
	}

}
