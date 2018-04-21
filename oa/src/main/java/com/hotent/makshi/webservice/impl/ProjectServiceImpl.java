package com.hotent.makshi.webservice.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import com.fr.process.engine.processservice.TaskService;
import com.hotent.core.api.org.model.ISysUser;
import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.makshi.model.project.ClassifyLibrary;
import com.hotent.makshi.model.project.Project;
import com.hotent.makshi.model.project.ProjectStage;
import com.hotent.makshi.model.project.ProjectStageTask;
import com.hotent.makshi.service.project.ClassifyLibraryService;
import com.hotent.makshi.service.project.ProjectService;
import com.hotent.makshi.service.project.ProjectStageService;
import com.hotent.makshi.service.project.ProjectStageTaskService;
import com.hotent.makshi.webservice.api.ProjectWebService;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.system.SysUserService;
import com.hotent.platform.webservice.exception.ApiExcetpion;
import com.hotent.platform.webservice.impl.BaseRuelt;
import com.hotent.platform.webservice.util.ReturnCode;

public class ProjectServiceImpl implements ProjectWebService {
	private final Logger log = Logger.getLogger(this.getClass());

	@Context
	private HttpServletRequest request;
	@Resource
	private ProjectStageTaskService projectStageTaskService;
	@Resource
	private ProjectStageService projectStageService;
	@Resource
	private ProjectService projectService;
	@Resource
	private SysUserService sysuserService;
	@Resource
	private ClassifyLibraryService classifyLibraryService;

	@Override
	public String myTask(String account, String flowId) {
		try {
			BaseRuelt result = new BaseRuelt();
			SysUser sysUser = sysuserService.getByAccount(account);
			if (sysUser == null) {
				throw new ApiExcetpion(ReturnCode.NO_DATA_FOUND, "用户不存在！");
			}
			String flowid = request.getParameter("flowId");
			if (StringUtils.isEmpty(flowid)) {
				throw new ApiExcetpion(ReturnCode.PRAMS_IS_NULL, "flowId 不能为空！");
			}
			QueryFilter queryFilter = new QueryFilter(request);
			queryFilter.getFilters().clear();
			queryFilter.getFilters().put("chargeID", sysUser.getUserId());
			queryFilter.getFilters().put("status", 0);
			queryFilter.getFilters().put("flowId", Long.valueOf(flowid));
			List<ProjectStageTask> list = projectStageTaskService.getAll(queryFilter);
			List<Map<String, Object>> resultList = new ArrayList<>();
			for (ProjectStageTask task : list) {
				if (task != null) {
					Integer stageId = task.getProstageid();
					ProjectStage stage = projectStageService.getById(stageId.longValue());
					if (stage != null) {
						Map<String, Object> map = new HashMap<>();
						Integer proId = stage.getProid();
						Project project = projectService.getById(proId.longValue());
						ClassifyLibrary classifyLibrary = classifyLibraryService.getById(project.getClassifylibraryid());
						map.put("classifyLibraryName", classifyLibrary.getName());
						map.put("projectName", project.getName());
						map.put("projectTaskId", task.getId());
						resultList.add(map);
					}
				}
			}
			Map<String, Object> map = new HashMap<>();
			map.put("list", resultList);
			result.setResultMap(map, ReturnCode.SUCCESS);
			return result.getResultMap().toString();
		} catch (ApiExcetpion e) {
			throw e;
		} catch (Exception e) {
			log.error("错误信息", e);
			throw new ApiExcetpion(ReturnCode.SYSTEM_ERROR);
		}
	}

}
