package com.hotent.makshi.controller.project;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.makshi.model.project.ClassifyStage;
import com.hotent.makshi.model.project.ClassifyStageTask;
import com.hotent.makshi.model.project.Project;
import com.hotent.makshi.model.project.ProjectStage;
import com.hotent.makshi.model.project.ProjectStageTask;
import com.hotent.makshi.service.project.ClassifyStageService;
import com.hotent.makshi.service.project.ClassifyStageTaskService;
import com.hotent.makshi.service.project.ProjectService;
import com.hotent.makshi.service.project.ProjectStageService;
import com.hotent.makshi.service.project.ProjectStageTaskService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/makshi/project/")
public class ProjectRepairController extends BaseController {
	private final Logger log = Logger.getLogger(this.getClass());

	@Autowired
	private ClassifyStageService classifyStageService;
	@Autowired
	private ClassifyStageTaskService classifyTaskService;
	@Autowired
	private ProjectStageService projectStageService;
	@Autowired
	private ProjectStageTaskService taskService;
	@Autowired
	private ProjectService projectService;

	@RequestMapping("repair")
	public void repair(@RequestParam(required = true, value = "classifylibraryid") Long classifylibraryid, HttpServletRequest request, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		List<Project> pros = projectService.getAll(new QueryFilter(request, "projectItem"));
		for (Project pro : pros) {
			Integer proId = pro.getId();
			List<ClassifyStage> clStages = classifyStageService.getByClassifyId(classifylibraryid);
			for (ClassifyStage stage : clStages) {
				ProjectStage tem = projectStageService.getProjectStageByCstIdAndProId(stage.getId(), proId);
				if (null != tem) {
					ProjectStage stageTemp = new ProjectStage();
					stageTemp.setId(tem.getId());
					stageTemp.setStagename(stage.getStagename());
					stageTemp.setOrder(stage.getOrder());
					stageTemp.setStagetype(stage.getStagetype());
					projectStageService.update(stageTemp);
					continue;
				}
				Long clStageId = stage.getId();
				ProjectStage proStage = new ProjectStage(proId, stage.getStagename(), stage.getStageno(), stage.getStagetype(), stage.getOrder(), stage.getId(), stage.getPrestage(),
						stage.getAfterstage());
				projectStageService.add(proStage);
				int proStageId = proStage.getId();
				List<ClassifyStageTask> clTasks = classifyTaskService.getStageTaskByStageId(clStageId);
				for (ClassifyStageTask task : clTasks) {
					ProjectStageTask proStageTask = new ProjectStageTask(proStageId, task.getTaskno(), task.getTaskname(), task.getTemplatefile(), task.getTasktype(), task.getRemark(),
							task.getIsexamine(), task.getIsmore(), task.getOrder(), task.getFields(), task.getUploadfile(), task.getFlowid(), task.getId() + "", task.getPreclassifystagetaskid(),
							task.getAfterclassifystagetaskid(), task.getQueryclassifystagetaskid(), 0);
					taskService.add(proStageTask);
				}
			}
				}
		json.put("result", true);
		PrintWriter writer = null;
		try {
			response.setCharacterEncoding(StandardCharsets.UTF_8.name());
			response.setContentType("application/json; charset=UTF-8");
			writer = response.getWriter();
			writer.write(json.toString());
			writer.flush();
		} catch (Exception e) {
			log.error("错误信息",e);
		} finally {
			if (writer != null)
				writer.close();
		}
	}
}
