package com.hotent.makshi.controller.portal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.bpm.model.ProcessTask;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.StringUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.makshi.model.doc.Doc;
import com.hotent.makshi.model.doc.DocFile;
import com.hotent.makshi.model.project.ProjectStageTaskField;
import com.hotent.makshi.model.project.ProjectTaskLogs;
import com.hotent.makshi.service.doc.DocFileService;
import com.hotent.makshi.service.doc.DocService;
import com.hotent.makshi.service.project.ProjectStageTaskFieldService;
import com.hotent.makshi.service.project.ProjectTaskLogsService;
import com.hotent.makshi.utils.DateUtils;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.model.system.SysFile;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.bpm.BpmService;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.platform.service.system.SysFileService;
import com.hotent.platform.service.system.SysUserService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Controller
@RequestMapping("/makshi/portal/orgportal/")
public class OrgPortalController extends BaseController {
	@Resource
	private DocService docService;
	@Resource
	private DocFileService docFileService;
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmService bpmService;
	@Resource
	private ProjectTaskLogsService projectTaskLogsService;
	@Resource
	private ProjectStageTaskFieldService taskFieldService;
	@Resource
	private SysFileService fileService;
	@Resource
	private SysUserService userService;
	@RequestMapping("index")
	@Action(description = "公司门户")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv=getAutoView();
		int pageSize=5; //默认取5条数据
		Doc temp=null;
		long orgId = RequestUtil.getLong(request, "orgId", 0l);
		Map<String, Object> params=new HashMap<>();
		QueryFilter queryFilter=new QueryFilter(request);
		queryFilter.getFilters().put("orgId", orgId);
		queryFilter.getPageBean().setPagesize(pageSize);
		List<ProjectTaskLogs> taskLogslist = projectTaskLogsService.getAll(queryFilter);
		for (ProjectTaskLogs projectTaskLogs : taskLogslist) {
			Long runid = projectTaskLogs.getRunid();
			if(runid==null){
				projectTaskLogs.setStatus("已提交");
			}else{
				projectTaskLogs.setStatus("审批结束");
				ProcessRun processRun = processRunService.getById(runid);
				if(BeanUtils.isNotEmpty(processRun)){
					projectTaskLogs.setSubmittor(processRun.getCreator());
				}
				List<ProcessTask> proTasks = null;
				if(BeanUtils.isNotEmpty(processRun)){ 
					if(processRun.getActInstId()==null) continue;
					proTasks = bpmService.getTasks(processRun.getActInstId());
					if(proTasks!=null && proTasks.size()>0){
						projectTaskLogs.setStatus("审批中");
					}
				}
			}
			Integer type = projectTaskLogs.getType();
			if(type!=null && type==1){
				//表单类型
				List<ProjectStageTaskField> list = taskFieldService.getFieldByTaskAndTime(projectTaskLogs.getTaskid(), DateUtils.formatDateL(projectTaskLogs.getCtime()));
				if(list!=null && list.size()>0){
					projectTaskLogs.setSubmittor(list.get(0).getCuser());
				}
			}else if(type!=null && type==2){
				String uploadfile = projectTaskLogs.getUploadFile();
				if(!StringUtil.isEmpty(uploadfile)){
					JSONArray jsonArray = JSONArray.fromObject(uploadfile);
					for(int i =0; i<jsonArray.size();i++){
						JSONObject object = jsonArray.getJSONObject(i);
						String fileId = (String) object.get("id");
						SysFile file = fileService.getById(Long.parseLong(fileId));
						if(file !=null){
							SysUser user = userService.getById(file.getCreatorId()) ;
							if(user!=null){
								projectTaskLogs.setSubmittor(user.getFullname());
							}
						}
					}
				}
			}
		}
		List<DocFile> tzgg_list=null; //通知列表
		List<DocFile> gszd_list=null; //制度列表
		List<DocFile> hyjy_list=null; //会议纪要列表
		List<DocFile> sz_list=null; //时政列表
		List<DocFile> fg_list=null; //法规列表
		List<DocFile> zfcg_list=null; //政府采购列表
		List<DocFile> jsgczb_list=null; //建设工程招标列表
		List<DocFile> yjsj_list=null; //应急事件列表
		List<DocFile> xxzl_list=null; //学习资料列表
		List<DocFile> xwjb_list=null; //新闻简报列表
		List<DocFile> gzdt_list=null; //工作动态列表
		List<DocFile> xwjbjszl_list=null; //新闻简报、技术质量列表
		List<DocFile> kpbhyap_list=null; //开评标会议安排列表
		List<DocFile> zdgwhd_list=null;//重点公务活动
		if(orgId==0l){
			throw new RuntimeException("参数错误!");
		}
		temp=docService.getCommonOrgDocByName(orgId,"部门通知");
		if(temp!=null){
			tzgg_list = docFileService.getByDocIdForPortal(temp.getDocid(),0,pageSize);
			mv.addObject("tzggId", temp.getDocid());
		}
		temp=null;
		params.clear();
		temp=docService.getCommonOrgDocByName(orgId,"部门制度");
		if(temp!=null){
			gszd_list = docFileService.getByDocIdForPortal(temp.getDocid(),0,pageSize);
			mv.addObject("gszdId", temp.getDocid());
		}
		temp=null;
		params.clear();
		temp=docService.getCommonOrgDocByName(orgId,"会议纪要");
		if(temp!=null){
			hyjy_list = docFileService.getByDocIdForPortal(temp.getDocid(),0,pageSize);
			mv.addObject("hyjyId", temp.getDocid());
		}
		temp=null;
		params.clear();
		temp=docService.getCommonOrgDocByName(orgId,"新闻简报、技术质量");
		if(temp!=null){
			xwjbjszl_list = docFileService.getByDocIdForPortal(temp.getDocid(),0,pageSize);
			mv.addObject("xwjbjszlId", temp.getDocid());
		}
		temp=null;
		params.clear();
		temp=docService.getCommonOrgDocByName(orgId,"新闻简报");
		if(temp!=null){
			xwjb_list = docFileService.getByDocIdForPortal(temp.getDocid(),0,pageSize);
			mv.addObject("xwjbId", temp.getDocid());
		}
		temp=null;
		params.clear();
		temp=docService.getCommonOrgDocByName(orgId,"工作动态");
		if(temp!=null){
			gzdt_list = docFileService.getByDocIdForPortal(temp.getDocid(),0,pageSize);
			mv.addObject("gzdtId", temp.getDocid());
		}
		temp=null;
		params.clear();
		temp=docService.getCommonOrgDocByName(orgId,"政策");
		if(temp!=null){
			sz_list = docFileService.getByDocIdForPortal(temp.getDocid(),0,pageSize);
			mv.addObject("szId", temp.getDocid());
		}
		temp=null;
		params.clear();
		temp=docService.getCommonOrgDocByName(orgId,"法规");
		if(temp!=null){
			fg_list = docFileService.getByDocIdForPortal(temp.getDocid(),0,pageSize);
			mv.addObject("fgId", temp.getDocid());
		}
		temp=null;
		params.clear();
		temp=docService.getCommonOrgDocByName(orgId,"应急事件");
		if(temp!=null){
			yjsj_list = docFileService.getByDocIdForPortal(temp.getDocid(),0,pageSize);
			mv.addObject("yjsjId", temp.getDocid());
		}
		temp=null;
		params.clear();
		temp=docService.getCommonOrgDocByName(orgId,"学习资料");
		if(temp!=null){
			xxzl_list = docFileService.getByDocIdForPortal(temp.getDocid(),0,pageSize);
			mv.addObject("xxzlId", temp.getDocid());
		}
		temp=null;
		params.clear();
		temp=docService.getCommonOrgDocByName(orgId,"开评标会议安排");
		if(temp!=null){
			kpbhyap_list = docFileService.getByDocIdForPortal(temp.getDocid(),0,pageSize);
			mv.addObject("kpbhyapId", temp.getDocid());
		}
		temp=null;
		params.clear();
		temp=docService.getCommonOrgDocByName(orgId,"政府采购");
		if(temp!=null){
			zfcg_list = docFileService.getByDocIdForPortal(temp.getDocid(),0,pageSize);
			mv.addObject("zfcgId", temp.getDocid());
		}
		temp=null;
		params.clear();
		temp=docService.getCommonOrgDocByName(orgId,"建设工程招标");
		if(temp!=null){
			jsgczb_list = docFileService.getByDocIdForPortal(temp.getDocid(),0,pageSize);
			mv.addObject("jsgczbId", temp.getDocid());
		}
		temp=null;
		params.clear();
		temp=docService.getCommonOrgDocByName(orgId,"重点公务活动");
		if(temp!=null){
			zdgwhd_list = docFileService.getByDocIdForPortal(temp.getDocid(),0,pageSize);
			mv.addObject("zdgwhdId", temp.getDocid());
		}
		mv.addObject("tzgg_list", tzgg_list);
		mv.addObject("gszd_list", gszd_list);
		mv.addObject("hyjy_list", hyjy_list);
		mv.addObject("sz_list", sz_list);
		mv.addObject("fg_list", fg_list);
		mv.addObject("yjsj_list", yjsj_list);
		mv.addObject("xxzl_list", xxzl_list);
		mv.addObject("xwjb_list", xwjb_list);
		mv.addObject("gzdt_list", gzdt_list);
		mv.addObject("xwjbjszl_list", xwjbjszl_list);
		mv.addObject("kpbhyap_list", kpbhyap_list);
		mv.addObject("zfcg_list", zfcg_list);
		mv.addObject("jsgczb_list", jsgczb_list);
		mv.addObject("orgId", orgId);
		mv.addObject("taskLogslist", taskLogslist);
		mv.addObject("zdgwhd_list", zdgwhd_list);
		return getOrgPortalView(mv,orgId);
	}
	
	private ModelAndView getOrgPortalView(ModelAndView mv,Long orgId){
		String viewName="/makshi/portal/orgportalIndex";
		mv.setViewName(viewName+"_"+orgId+".jsp");
		return mv;
	}
	@RequestMapping("moreProjectProgress")
	@Action(description = "公司门户")
	public ModelAndView moreProjectProgress(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mv=getAutoView();
		long orgId = RequestUtil.getLong(request, "orgId", 0l);
		QueryFilter queryFilter=new QueryFilter(request,"taskLog");
		queryFilter.getFilters().put("orgId", orgId);
		List<ProjectTaskLogs> taskLogslist = projectTaskLogsService.getAll(queryFilter);
		for (ProjectTaskLogs projectTaskLogs : taskLogslist) {
			Long runid = projectTaskLogs.getRunid();
			if(runid==null){
				projectTaskLogs.setStatus("已提交");
			}else{
				projectTaskLogs.setStatus("审批结束");
				ProcessRun processRun = processRunService.getById(runid);
				if(BeanUtils.isNotEmpty(processRun)){
					projectTaskLogs.setSubmittor(processRun.getCreator());
				}
				List<ProcessTask> proTasks = null;
				if(BeanUtils.isNotEmpty(processRun)){ 
					if(processRun.getActInstId()==null) continue;
					proTasks = bpmService.getTasks(processRun.getActInstId());
					if(proTasks!=null && proTasks.size()>0){
						projectTaskLogs.setStatus("审批中");
					}
				}
			}
			Integer type = projectTaskLogs.getType();
			if(type!=null && type==1){
				//表单类型
				List<ProjectStageTaskField> list = taskFieldService.getFieldByTaskAndTime(projectTaskLogs.getTaskid(), DateUtils.formatDateL(projectTaskLogs.getCtime()));
				if(list!=null && list.size()>0){
					projectTaskLogs.setSubmittor(list.get(0).getCuser());
				}
			}else if(type!=null && type==2){
				String uploadfile = projectTaskLogs.getUploadFile();
				if(!StringUtil.isEmpty(uploadfile)){
					JSONArray jsonArray = JSONArray.fromObject(uploadfile);
					for(int i =0; i<jsonArray.size();i++){
						JSONObject object = jsonArray.getJSONObject(i);
						String fileId = (String) object.get("id");
						SysFile file = fileService.getById(Long.parseLong(fileId));
						if(file !=null){
							SysUser user = userService.getById(file.getCreatorId()) ;
							if(user!=null){
								projectTaskLogs.setSubmittor(user.getFullname());
							}
						}
					}
				}
			}
		}
		mv.addObject("orgId", orgId);
		mv.addObject("taskLogslist", taskLogslist);
		return mv;
	}
}
