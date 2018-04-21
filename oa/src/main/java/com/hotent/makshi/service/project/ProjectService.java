package com.hotent.makshi.service.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.model.TaskExecutor;
import com.hotent.core.service.BaseService;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.makshi.dao.project.ProjectDao;
import com.hotent.makshi.dao.project.ProjectStageDao;
import com.hotent.makshi.model.project.Project;
import com.hotent.makshi.model.project.ProjectStage;
import com.hotent.makshi.model.project.ProjectStageTask;
import com.hotent.makshi.model.waterprotectpark.WeekPlan;
import com.hotent.platform.model.system.UserPosition;
import com.hotent.platform.model.system.UserUnder;

@Service
public class ProjectService extends BaseService<Project> {
	@Resource
	private ProjectDao dao;
	@Resource
	private ProjectStageDao projectStageDao;
	@Resource
	private ProjectStageTaskService projectStageTaskService;
	@Resource
	private ProjectStageService projectStageService;
	public ProjectService(){
		
	}
	
	@Override
	protected IEntityDao<Project, Long> getEntityDao() {
		return dao;
	}
	
	public long getCountByClassifyLibId(Long classifyLibId){
		Map<String,Object>  params=new HashMap<String, Object>();
		params.put("classifylibraryid", classifyLibId);
		long count = (long) dao.getOne("getCountByClassifyLibId", params);
		return count;
	}
	
	public long getCountByClassifyLibIdAndStatus(Long classifyLibId,int status){
		Map<String,Object>  params=new HashMap<String, Object>();
		params.put("classifylibraryid", classifyLibId);
		params.put("status", status);
		long count = (long) dao.getOne("getCountByClassifyLibIdAndStatus", params);
		return count;
	}
	
	public List<Project> getCollectionedProject(QueryFilter filter){
		List<Project> list = dao.getBySqlKey("getCollectionedProject", filter);
		return list;
	}
	
	public List<Project> getByClassifyLibId(Long classifylibraryid){
		return dao.getBySqlKey("getByClassifyLibId", classifylibraryid);
	}
	
	public List<Project> getProsByContractnum(String contractnum){
		Map<String,Object> param=new HashMap<>();
		param.put("contractnum", contractnum);
		List<Project> list = dao.getBySqlKey("getProsByContractnum", param);
		return list;
	}
	public ResultMessage checkStageTask(Integer stageId) {
		ProjectStage projectStage = projectStageDao.getById(Long.valueOf(stageId+""));
		if (projectStage == null ) {
			return new ResultMessage(ResultMessage.Fail, "该分类阶段已经被删除！");
		} else {
			List<ProjectStageTask> projectStageTasks = projectStageTaskService.getProjectStageTaskByStageId(stageId);
			if (projectStageTasks == null || projectStageTasks.size() == 0) {
				return new ResultMessage(ResultMessage.Fail, "该分类阶段没有配置可用的任务！");
			}
			List<String> checkIds = new ArrayList<>();
			Map<String, ProjectStageTask> map = new HashMap<>();// 键为阶段任务id字符串
			boolean flag = true;
			for (ProjectStageTask projectStageTask : projectStageTasks) {
				if(StringUtils.isEmpty(projectStageTask.getCstid())){
					checkIds.add(projectStageTask.getId() + "");
					map.put("" + projectStageTask.getId(), projectStageTask);
				}else{
					checkIds.add(projectStageTask.getCstid() + "");
					map.put("" + projectStageTask.getCstid(), projectStageTask);
				}
			}
			for (ProjectStageTask projectStageTask : projectStageTasks) {
				if (!checkId(projectStageTask, checkIds)) {
					return new ResultMessage(ResultMessage.Fail,
							"任务编号为" + projectStageTask.getTaskno() + "的前后置任务中存在已经被删除的任务！");
				}
			}
			for (ProjectStageTask projectStageTask : projectStageTasks) {
				if (!check(projectStageTask, map, 0)) {
					// 校验前后置关系
					flag = false;
					break;
				}
			}
			if (!flag) {
				return new ResultMessage(ResultMessage.Fail, "任务前后置关系配置错误，请注意检查！");
			}

			return new ResultMessage(ResultMessage.Success, "检查校验通过！");
		}
	}
	

	private boolean check(ProjectStageTask projectStageTask, Map<String, ProjectStageTask> map, int deep) {
		boolean flag=true;
		if((++deep)>=32){
			return false;
		}
		String prestagetask = projectStageTask.getPretaskid();
		if(StringUtils.isEmpty(prestagetask)){
			return true;
		}else{
			String[] split = prestagetask.trim().split(",");
			for (String string : split) {
				ProjectStageTask cs = map.get(string);
				if(!check(cs,map,deep)){
					flag=false;
					break;
				}
			}
		}
		return flag;
	}

	private boolean checkId(ProjectStageTask projectStageTask, List<String> checkIds) {
		String prestagetask = projectStageTask.getPretaskid();
		String afterstagetask = projectStageTask.getAftertaskid();
		if(!StringUtils.isEmpty(prestagetask)){
			String[] split = prestagetask.trim().split(",");
			for (String string : split) {
				if(!checkIds.contains(string)){
					return false;
				}
			}
		}
		if(!StringUtils.isEmpty(afterstagetask)){
			String[] split = afterstagetask.trim().split(",");
			for (String string : split) {
				if(!checkIds.contains(string)){
					return false;
				}
			}
		}
		return true;

	}

	public Project getProjectByTaskId(Long taskId) {
		ProjectStageTask byId = projectStageTaskService.getById(taskId);
		if(byId!=null){
			Integer prostageid = byId.getProstageid();
			ProjectStage stage = projectStageService.getById(Long.valueOf(prostageid+""));
			if(stage!=null){
				Integer proid = stage.getProid();
				Project pro = getById(Long.valueOf(proid+""));
				if(pro!=null){
					return pro;
				}
			}
		}
		return null;
	}
	
	/**
	 * 根据任务id获取项目负责人
	 * @param userId
	 * @return
	 */
	public Set<TaskExecutor> getProjectCharger(Long taskId){
		Set<TaskExecutor> list=new HashSet<TaskExecutor>();
		Project pro = getProjectByTaskId(taskId);
		if(pro!=null){
			String applicantID = pro.getApplicantID();
			String applicant = pro.getApplicant();
			if(StringUtils.isNotEmpty(applicantID) && StringUtils.isNotEmpty(applicant)){
				String[] split = applicantID.split(",");
				String[] split2 = applicant.split(",");
				for(int i=0;i<split.length;i++){
					list.add(TaskExecutor.getTaskUser(split[i],split2[i]));
				}
			}
		}
		return list;
	}

	public List<Project> getMyProList(QueryFilter queryFilter) {
		
		return dao.getBySqlKey("getMyProList", queryFilter);
	}

	public List<Project> getProList(QueryFilter queryFilter) {
		return dao.getBySqlKey("getProList", queryFilter);
	}

	public Project getProByName(String proname){
		
		List<Project> bySqlKey = dao.getBySqlKey("getProByName",proname);
		if(bySqlKey!=null && bySqlKey.size()>0){
			return bySqlKey.get(0);
		}else{
			return null;
		}
	}
	
}
