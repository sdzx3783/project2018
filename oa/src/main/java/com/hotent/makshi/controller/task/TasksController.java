package com.hotent.makshi.controller.task;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hotent.core.util.DateUtil;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.api.org.model.ISysUser;
import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.makshi.model.task.Task;
import com.hotent.makshi.model.task.TaskCancel;
import com.hotent.makshi.model.task.TaskDelay;
import com.hotent.makshi.model.task.TaskLog;
import com.hotent.makshi.model.task.TaskSubmit;
import com.hotent.makshi.model.task.TaskTransfer;
import com.hotent.makshi.service.task.TaskCancelService;
import com.hotent.makshi.service.task.TaskDelayService;
import com.hotent.makshi.service.task.TaskLogService;
import com.hotent.makshi.service.task.TaskServices;
import com.hotent.makshi.service.task.TaskSubmitService;
import com.hotent.makshi.service.task.TaskTransferService;
import com.hotent.makshi.utils.DateUtils;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.model.system.SysPlan;
import com.hotent.platform.service.system.SysPlanService;

@Controller
@RequestMapping("/makshi/task/task/")
public class TasksController extends BaseController{
	
	@Autowired
	private TaskServices taskServices;
	@Autowired
	private TaskCancelService cancelService;
	@Autowired
	private TaskSubmitService submitService;
	@Autowired
	private TaskDelayService delayService;
	@Autowired
	private TaskTransferService transferService;
	@Autowired
	private TaskLogService logService;
	@Resource
	private SysPlanService sysPlanService;
	
	
	@RequestMapping("list")
	@Action(description = "任务列表")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long currentUserId = ContextUtil.getCurrentUserId();
		int type = RequestUtil.getInt(request, "type");
		ModelAndView mv = getAutoView();
		QueryFilter queryFilter=new QueryFilter(request,"taskItem");
		if(type==1){//我发起的
			queryFilter.addFilter("cuserID", currentUserId);
		}else if(type==2){//我相关的
			queryFilter.addFilter("join", currentUserId);
			queryFilter.addFilter("ispub", 1);
		}else if(type==3){//我负责的
			queryFilter.addFilter("chargerID", currentUserId);
		}
		
		List<Task> list = taskServices.getAll(queryFilter);
		for(Task task :list){
			List<TaskSubmit> submitList = submitService.getSubmitByTaskId(task.getId());
			String progress= "0";
			if(submitList!=null && submitList.size()>0){
				progress= submitList.get(0).getProgress();
			}
			task.setProgress(progress);
			if((currentUserId+"").equals(task.getChargerID())){
				task.setIsCharge(true);
			}
		}
		mv.addObject("list", list).addObject("type", type);
		return mv;
	}
	
	
	@RequestMapping("toAdd")
	@Action(description = "跳转创建计划页面")
	public ModelAndView toAdd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String returnUrl=RequestUtil.getPrePage(request);
		ModelAndView mv = new ModelAndView("/makshi/task/taskEdit.jsp");
		return mv.addObject("returnUrl", returnUrl).addObject("type", 1);
	}
	
	
	@RequestMapping("save")
	@Action(description = "保存任务")
	public void save(HttpServletRequest request, HttpServletResponse response,Task task) throws Exception {
		String resultMsg = null;
		try {
			ISysUser currentUser = ContextUtil.getCurrentUser();
			int type = RequestUtil.getInt(request, "type",-1);
			Date date = new Date();
			if(task.getId()==null){
				if(type!=2 && task.getIspub()==1){
					//添加到日程
					String participantIds = task.getMemberID();
					String participants = task.getMember();
					SysPlan sysPlan = new SysPlan();
					long genId = UniqueIdUtil.genId();
					sysPlan.setId(genId);
					sysPlan.setEndTime(task.getEndDate());
					sysPlan.setStartTime(task.getStartDate());
					sysPlan.setTaskName(task.getName());
					sysPlan.setSubmitId(currentUser.getUserId());
					sysPlan.setSubmitor(currentUser.getFullname());
					sysPlan.setCharge(task.getCharger());
					sysPlan.setChargeId(Long.parseLong(task.getChargerID()));
					sysPlan.setDescription(task.getContent());
					sysPlan.setCreateTime(date);
					Map<String,Object> params = new HashMap<String,Object>();
					params.put("participantIds", participantIds);
					params.put("participants", participants);
					resultMsg = sysPlanService.saveTaskSysPlan(sysPlan, params);
					task.setSysplanid(genId);
				}
				//Long currentUserId = ContextUtil.getCurrentUserId();
				task.setCuser(currentUser.getFullname());
				task.setCuserid(currentUser.getUserId()+"");
				task.setCtime(date);
				task.setIsdelete(0);
				task.setStage(3);
				taskServices.add(task);
				resultMsg = getText("添加", "任务");
				
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
			}else{
				if(type!=2 && task.getIspub()==1){
					//更新日程
					String participantIds = task.getMemberID();
					String participants = task.getMember();
					Map<String,Object> params = new HashMap<String,Object>();
					params.put("participantIds", participantIds);
					params.put("participants", participants);
					
					SysPlan sysPlanTemp = sysPlanService.getById(task.getSysplanid());
					if(sysPlanTemp==null){
						sysPlanTemp= new SysPlan();
						long genId = UniqueIdUtil.genId();
						sysPlanTemp.setId(genId);
						sysPlanTemp.setEndTime(task.getEndDate());
						sysPlanTemp.setStartTime(task.getStartDate());
						sysPlanTemp.setTaskName(task.getName());
						sysPlanTemp.setSubmitId(currentUser.getUserId());
						sysPlanTemp.setSubmitor(currentUser.getFullname());
						sysPlanTemp.setCharge(task.getCharger());
						sysPlanTemp.setChargeId(Long.parseLong(task.getChargerID()));
						sysPlanTemp.setDescription(task.getContent());
						sysPlanTemp.setCreateTime(date);
						task.setSysplanid(genId);
						sysPlanService.saveTaskSysPlan(sysPlanTemp, params);
					}else{
						sysPlanTemp.setEndTime(task.getEndDate());
						sysPlanTemp.setStartTime(task.getStartDate());
						sysPlanTemp.setTaskName(task.getName());
						sysPlanTemp.setSubmitId(currentUser.getUserId());
						sysPlanTemp.setSubmitor(currentUser.getFullname());
						sysPlanTemp.setCharge(task.getCharger());
						sysPlanTemp.setChargeId(Long.parseLong(task.getChargerID()));
						sysPlanTemp.setDescription(task.getContent());
						sysPlanTemp.setCreateTime(date);
						sysPlanService.saveSysPlan(sysPlanTemp, params);
					}
					
					resultMsg = sysPlanService.saveSysPlan(sysPlanTemp, params);
				}
				task.setUtime(date);
				task.setUuserid(currentUser.getUserId()+"");
				task.setUuser(currentUser.getFullname());
				taskServices.update(task);
				resultMsg = getText("更新", "任务");
				
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
			}
			
			
			
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
		}
	}
	
	@RequestMapping("edit")
	@Action(description = "获取任务")
	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String returnUrl=RequestUtil.getPrePage(request);
		Long id = RequestUtil.getLong(request, "id");
		int type = RequestUtil.getInt(request, "type");
		ModelAndView mv = new ModelAndView("/makshi/task/taskEdit.jsp");
		Task task = taskServices.getById(id);
		List<TaskLog> logList =null;
		if(task!=null){
			QueryFilter queryFilter=new QueryFilter(request,"logItem");
			queryFilter.addFilter("taskId", id);
			logList = logService.getAll(queryFilter);
//			List<PlanReply> replyList = planReplyService.getReplyByPId(plan.getId());
//			plan.setReplyList(replyList);
		}
		return mv.addObject("task", task).addObject("returnUrl", returnUrl).addObject("logList", logList).addObject("type", type);
	}
	
	
	@RequestMapping("detail")
	@Action(description = "任务详情")
	public ModelAndView detail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String returnUrl=RequestUtil.getPrePage(request);
		Long id = RequestUtil.getLong(request, "id");
		int type = RequestUtil.getInt(request, "type");
		ModelAndView mv=new ModelAndView("/makshi/task/taskDetail.jsp");
		String currentUserId = ContextUtil.getCurrentUserId()+"";
		Task task = taskServices.getById(id);
		boolean isCharge = false;
		String chargerID = task.getChargerID();
		if(currentUserId.equals(chargerID)){
			isCharge=true;
		}
		List<TaskLog> logList =null;
		if(task!=null){
			QueryFilter queryFilter=new QueryFilter(request,"logItem");
			queryFilter.addFilter("taskId", id);
			logList = logService.getAll(queryFilter);
//			List<PlanReply> replyList = planReplyService.getReplyByPId(plan.getId());
//			plan.setReplyList(replyList);
		}
		return mv.addObject("task", task).addObject("returnUrl", returnUrl).addObject("logList", logList).addObject("isCharge", isCharge).addObject("type", type);
	}
	
	
	
	@RequestMapping("delTask")
	@Action(description = "删除任务")
	public void delTask(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resultMsg = null;
		Long id = RequestUtil.getLong(request, "id");
		Task task = taskServices.getById(id);
		if(task!=null){
			task.setIsdelete(1);
			taskServices.update(task);
		}
		resultMsg = getText("删除", "任务");
		writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
	}
	
	@RequestMapping("toSubmit")
	@Action(description = "跳转到提交进度")
	public ModelAndView toSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String returnUrl=RequestUtil.getPrePage(request);
		Long id = RequestUtil.getLong(request, "id");
		Integer type = RequestUtil.getInt(request, "type");
		Task task = taskServices.getById(id);
		String progress = "0";
		List<TaskSubmit> submitList = submitService.getSubmitByTaskId(task.getId());
		if(submitList!=null && submitList.size()>0){
			progress= submitList.get(0).getProgress();
		}
		ModelAndView mv =null;
		if(type==1){
			mv = new ModelAndView("/makshi/task/submitProgress.jsp");
		}else if(type==2){
			mv = new ModelAndView("/makshi/task/delay.jsp");
		}else if(type==3){
			mv = new ModelAndView("/makshi/task/transfer.jsp");
		}else if(type==4){
			mv = new ModelAndView("/makshi/task/cancel.jsp");
		}
		
		return mv.addObject("returnUrl", returnUrl).addObject("id", id).addObject("progress", progress).addObject("task", task);
	}
	
	
	@RequestMapping("submitProgress")
	@Action(description = "提交进度")
	public void submitProgress(HttpServletRequest request, HttpServletResponse response,TaskSubmit submit) throws Exception {
		String resultMsg = null;
		try {
			ISysUser currentUser = ContextUtil.getCurrentUser();
			if(submit.getId()==null){
				submit.setCuser(currentUser.getFullname());
				submit.setCuserid(currentUser.getUserId()+"");
				submit.setCtime(new Date());
				submitService.add(submit);
				if(submit.getProgress().equals("100")){
					Task task = taskServices.getById(submit.getTaskId().longValue());
					task.setStage(4);
					task.setUuser(currentUser.getFullname());
					task.setUuserid(currentUser.getUserId()+"");
					task.setUtime(new Date());
					taskServices.update(task);
				}
				TaskLog log = new TaskLog(1,submit.getProgress(),submit.getFeedback()+";"+submit.getProgress()+"%",currentUser.getFullname(),currentUser.getUserId()+"",new Date(),submit.getTaskId());
				logService.add(log);
				resultMsg = getText("添加", "任务进度");
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
			}
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
		}
	}
	
	@RequestMapping("delay")
	@Action(description = "任务延期")
	public void delay(HttpServletRequest request, HttpServletResponse response,TaskDelay delay) throws Exception {
		String resultMsg = null;
		try {
			ISysUser currentUser = ContextUtil.getCurrentUser();
			if(delay.getId()==null){
				delay.setCuser(currentUser.getFullname());
				delay.setCuserid(currentUser.getUserId()+"");
				delay.setCtime(new Date());
				delayService.add(delay);
				resultMsg = getText("添加", "任务延期");
				Task task = taskServices.getById(delay.getTaskId().longValue());
				task.setEndDate(delay.getEndDate());
				task.setUuser(currentUser.getFullname());
				task.setUuserid(currentUser.getUserId()+"");
				task.setUtime(new Date());
				if(task.getIspub()==1){
					SysPlan sysPlanTemp = sysPlanService.getById(task.getSysplanid());
					if(sysPlanTemp!=null){
						sysPlanTemp.setEndTime(task.getEndDate());
						Map<String,Object> params = new HashMap<String,Object>();
						String participantIds = task.getMemberID();
						String participants = task.getMember();
						params.put("participantIds", participantIds);
						params.put("participants", participants);
						sysPlanService.saveSysPlan(sysPlanTemp, params);
					}
				}
				taskServices.update(task);
				
				TaskLog log = new TaskLog(3,delay.getProgress(),delay.getReason()+";"+DateUtils.formatDateS(delay.getEndDate()),currentUser.getFullname(),currentUser.getUserId()+"",new Date(),delay.getTaskId());
				logService.add(log);
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
			}
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
		}
	}
	
	@RequestMapping("transfer")
	@Action(description = "转派责任人")
	public void transfer(HttpServletRequest request, HttpServletResponse response,TaskTransfer transfer) throws Exception {
		String resultMsg = null;
		try {
			ISysUser currentUser = ContextUtil.getCurrentUser();
			if(transfer.getId()==null){
				transfer.setCuser(currentUser.getFullname());
				transfer.setCuserid(currentUser.getUserId()+"");
				transfer.setCtime(new Date());
				transferService.add(transfer);
				resultMsg = getText("添加", "转派责任人");
				Task task = taskServices.getById(transfer.getTaskId().longValue());
				String beforeCharger = task.getCharger();
				task.setCharger(transfer.getCharger());
				task.setChargerID(transfer.getchargerID());
				task.setUuser(currentUser.getFullname());
				task.setUuserid(currentUser.getUserId()+"");
				task.setUtime(new Date());
				taskServices.update(task);
				
				TaskLog log = new TaskLog(4,transfer.getProgress(),transfer.getReason()+";"+"负责人:"+beforeCharger+"->"+transfer.getCharger(),currentUser.getFullname(),currentUser.getUserId()+"",new Date(),transfer.getTaskId());
				logService.add(log);
				
				
				if(task.getIspub()==1){
					SysPlan sysPlanTemp = sysPlanService.getById(task.getSysplanid());
					if(sysPlanTemp!=null){
						sysPlanTemp.setEndTime(task.getEndDate());
						Map<String,Object> params = new HashMap<String,Object>();
						sysPlanTemp.setCharge(task.getCharger());
						sysPlanTemp.setChargeId(Long.parseLong(task.getChargerID()));
						String participantIds = task.getMemberID();
						String participants = task.getMember();
						params.put("participantIds", participantIds);
						params.put("participants", participants);
						sysPlanService.saveSysPlan(sysPlanTemp, params);
					}
				}
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
			}
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
		}
	}
	
	
	@RequestMapping("cancel")
	@Action(description = "任务取消")
	public void cancel(HttpServletRequest request, HttpServletResponse response,TaskCancel cancel) throws Exception {
		String resultMsg = null;
		try {
			ISysUser currentUser = ContextUtil.getCurrentUser();
			if(cancel.getId()==null){
				cancel.setCuser(currentUser.getFullname());
				cancel.setCuserid(currentUser.getUserId()+"");
				cancel.setCtime(new Date());
				cancelService.add(cancel);
				resultMsg = getText("添加", "任务取消");
				Task task = taskServices.getById(cancel.getTaskId().longValue());
				task.setStage(2);
				task.setUuser(currentUser.getFullname());
				task.setUuserid(currentUser.getUserId()+"");
				task.setUtime(new Date());
				taskServices.update(task);
				
				TaskLog log = new TaskLog(2,cancel.getProgress(),cancel.getReason(),currentUser.getFullname(),currentUser.getUserId()+"",new Date(),cancel.getTaskId());
				logService.add(log);
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
			}
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
		}
	}


	/**
	 * 新的任务列表，分为分配任务和我的任务
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("newlist")
	@Action(description = "分配任务-我的任务")
	public ModelAndView newList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long currentUserId = ContextUtil.getCurrentUserId();
		int type = RequestUtil.getInt(request, "type");
		//3任务进行中 4任务已办结
		int stage = RequestUtil.getInt(request, "stage");

		//我的任务 3待办任务 4已办结任务
        int logStage = RequestUtil.getInt(request, "logstage");
		ModelAndView mv = getAutoView();
		QueryFilter queryFilter=new QueryFilter(request,"taskItem");
		if(type==1){//我发起的
			queryFilter.addFilter("cuserID", currentUserId);
            if(stage > 0) {
                queryFilter.addFilter("stage", stage);
            }
		}else if(type==2){//我相关的

			queryFilter.addFilter("join", currentUserId);
            queryFilter.addFilter("ispub", 1);

            if(logStage > 0) {
                queryFilter.addFilter("logstage", logStage);
            }

            queryFilter.addFilter("loguserid", currentUserId);

		}else if(type==3){//我负责的
			queryFilter.addFilter("chargerID", currentUserId);
		}


		List<Task> list = taskServices.getAll(queryFilter);
		for(Task task :list){
			List<TaskSubmit> submitList = submitService.getSubmitByTaskId(task.getId());
			String progress= "0";
			if(submitList!=null && submitList.size()>0){
				progress= submitList.get(0).getProgress();
			}
			task.setProgress(progress);
			if((currentUserId+"").equals(task.getChargerID())){
				task.setIsCharge(true);
			}
		}
		mv.addObject("list", list)
                .addObject("type", type)
                .addObject("stage", stage)
                .addObject("logstage", logStage)
                .addObject("today", new Date());
		return mv;
	}

    /**
     * 取消已发布的任务
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("cancelTask")
    @Action(description = "取消任务")
    public void cancelTask(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String resultMsg = null;
        Long id = RequestUtil.getLong(request, "id");
        Task task = taskServices.getById(id);
        if(task != null){
            //改为未发布
            task.setIspub(0);
            taskServices.update(task);
        }
        resultMsg = getText("取消", "任务");
        writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
    }

    /**
     * 新的编辑，不要负责人，参与人统一叫办理人
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("newedit")
    @Action(description = "新的获取任务")
    public ModelAndView newEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String returnUrl=RequestUtil.getPrePage(request);
        Long id = RequestUtil.getLong(request, "id");
        int type = RequestUtil.getInt(request, "type");
        ModelAndView mv = new ModelAndView("/makshi/task/taskNewEdit.jsp");
        Task task = taskServices.getById(id);
        List<TaskLog> logList =null;
        if(task!=null){
            QueryFilter queryFilter=new QueryFilter(request,"logItem");
            queryFilter.addFilter("taskId", id);
            logList = logService.getAll(queryFilter);
        }
        return mv.addObject("task", task)
                .addObject("returnUrl", returnUrl)
                .addObject("logList", logList)
                .addObject("type", type)
                .addObject("stage", 3);
    }


    /**
     * 新的保存，不要负责人，参与人统一叫办理人。
     * @param request
     * @param response
     * @param task
     * @throws Exception
     */
    @RequestMapping("newsave")
    @Action(description = "新的保存任务")
    public void newSave(HttpServletRequest request, HttpServletResponse response,Task task) throws Exception {
        String resultMsg = null;
        try {
            ISysUser currentUser = ContextUtil.getCurrentUser();
            int type = RequestUtil.getInt(request, "type",-1);
            Date date = new Date();
            if(task.getId()==null){
                if(type!=2 && task.getIspub()==1){
                    //添加到日程
                    String participantIds = task.getMemberID();
                    String participants = task.getMember();
                    SysPlan sysPlan = new SysPlan();
                    long genId = UniqueIdUtil.genId();
                    sysPlan.setId(genId);
                    sysPlan.setEndTime(task.getEndDate());
                    sysPlan.setStartTime(task.getStartDate());
                    sysPlan.setTaskName(task.getName());
                    sysPlan.setSubmitId(currentUser.getUserId());
                    sysPlan.setSubmitor(currentUser.getFullname());
//                    sysPlan.setCharge(task.getCharger());
//                    sysPlan.setChargeId(Long.parseLong(task.getChargerID()));
                    sysPlan.setDescription(task.getContent());
                    sysPlan.setCreateTime(date);
                    Map<String,Object> params = new HashMap<String,Object>();
                    params.put("participantIds", participantIds);
                    params.put("participants", participants);
                    resultMsg = sysPlanService.saveTaskSysPlan(sysPlan, params);
                    task.setSysplanid(genId);
                }
                //Long currentUserId = ContextUtil.getCurrentUserId();
                task.setCuser(currentUser.getFullname());
                task.setCuserid(currentUser.getUserId()+"");
                task.setCtime(date);
                task.setIsdelete(0);
                task.setStage(3);
                taskServices.add(task);
                resultMsg = getText("添加", "任务");

                writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
            }else{
                if(type!=2 && task.getIspub()==1){
                    //更新日程
                    String participantIds = task.getMemberID();
                    String participants = task.getMember();
                    Map<String,Object> params = new HashMap<String,Object>();
                    params.put("participantIds", participantIds);
                    params.put("participants", participants);

                    SysPlan sysPlanTemp = sysPlanService.getById(task.getSysplanid());
                    if(sysPlanTemp==null){
                        sysPlanTemp= new SysPlan();
                        long genId = UniqueIdUtil.genId();
                        sysPlanTemp.setId(genId);
                        sysPlanTemp.setEndTime(task.getEndDate());
                        sysPlanTemp.setStartTime(task.getStartDate());
                        sysPlanTemp.setTaskName(task.getName());
                        sysPlanTemp.setSubmitId(currentUser.getUserId());
                        sysPlanTemp.setSubmitor(currentUser.getFullname());
//                        sysPlanTemp.setCharge(task.getCharger());
//                        sysPlanTemp.setChargeId(Long.parseLong(task.getChargerID()));
                        sysPlanTemp.setDescription(task.getContent());
                        sysPlanTemp.setCreateTime(date);
                        task.setSysplanid(genId);
                        sysPlanService.saveTaskSysPlan(sysPlanTemp, params);
                    }else{
                        sysPlanTemp.setEndTime(task.getEndDate());
                        sysPlanTemp.setStartTime(task.getStartDate());
                        sysPlanTemp.setTaskName(task.getName());
                        sysPlanTemp.setSubmitId(currentUser.getUserId());
                        sysPlanTemp.setSubmitor(currentUser.getFullname());
//                        sysPlanTemp.setCharge(task.getCharger());
//                        sysPlanTemp.setChargeId(Long.parseLong(task.getChargerID()));
                        sysPlanTemp.setDescription(task.getContent());
                        sysPlanTemp.setCreateTime(date);
                        sysPlanService.saveSysPlan(sysPlanTemp, params);
                    }

                    resultMsg = sysPlanService.saveSysPlan(sysPlanTemp, params);
                }
                task.setUtime(date);
                task.setUuserid(currentUser.getUserId()+"");
                task.setUuser(currentUser.getFullname());
                taskServices.update(task);
                resultMsg = getText("更新", "任务");

                writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
            }

        } catch (Exception e) {
            writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
        }
    }


    /**
     * 新的创建页面
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("newAdd")
    @Action(description = "跳转新的创建计划页面")
    public ModelAndView newAdd(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String returnUrl=RequestUtil.getPrePage(request);
        ModelAndView mv = new ModelAndView("/makshi/task/taskNewEdit.jsp");
        return mv.addObject("returnUrl", returnUrl)
                .addObject("type", 1)
                .addObject("stage", 3);
    }


    /**
     * 新的任务详情
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("newdetail")
    @Action(description = "新的任务详情")
    public ModelAndView newDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String returnUrl=RequestUtil.getPrePage(request);

        ISysUser currentUser = ContextUtil.getCurrentUser();

        Long id = RequestUtil.getLong(request, "id");
        int type = RequestUtil.getInt(request, "type");
        ModelAndView mv=new ModelAndView("/makshi/task/taskNewDetail.jsp");
        String currentUserId = ContextUtil.getCurrentUserId()+"";
        Task task = taskServices.getById(id);
        boolean isCharge = false;
        String chargerID = task.getChargerID();
        if(currentUserId.equals(chargerID)){
            isCharge=true;
        }
        List<TaskLog> logList =null;
        if(task!=null){
            QueryFilter queryFilter=new QueryFilter(request,"logItem");
            queryFilter.addFilter("taskId", id);
            logList = logService.getAll(queryFilter);
        }
        int commited = -1;
        Integer cruserlogid = null;
        if(logList != null) {
            for(TaskLog t:logList) {
                if(currentUserId.equals(t.getCuserid())) {
                    //当前用户判断是否保存过或提交过
                    if(t.getCommited() != null) {
                        commited = t.getCommited();
                    }
                    cruserlogid = t.getId();

                    break;
                }
            }
        }


        //必须要从待办任务进来，并且，没有对该任务写过意见，才能添加一条意见。
        int canAddLog = 0;

        if(RequestUtil.getInt(request, "stage") > 0) {
            mv.addObject("stage", RequestUtil.getInt(request, "stage"));
        } else if (RequestUtil.getInt(request, "logstage") > 0) {
            mv.addObject("logstage", RequestUtil.getInt(request, "logstage"));

            if(RequestUtil.getInt(request, "logstage") == 3) {
                canAddLog = 1;
            }

        }

        return mv.addObject("task", task)
                .addObject("returnUrl", returnUrl)
                .addObject("logList", logList)
                .addObject("isCharge", isCharge)
                .addObject("type", type)
                .addObject("stage", task.getStage())
                .addObject("commited", commited)
                .addObject("cuserid", currentUserId)
                .addObject("cuser", currentUser.getFullname())
                .addObject("crtime", DateUtils.getNow())
                .addObject("cruserlogid", cruserlogid)
                .addObject("canAddLog", canAddLog);
    }


    /**
     * 新的保存/提交任务详情
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("newsavetasklog")
    @Action(description = "新的保存任务详情")
    @ResponseBody
    public String newSaveTaskLog(HttpServletRequest request, HttpServletResponse response, TaskLog taskLog) throws Exception {
        JSONObject json = new JSONObject();
        json.put("code", 1);
        json.put("msg",  "保存成功");

        if(taskLog == null) {
            json.put("code", 0);
            json.put("msg",  "参数错误!");
            return json.toString();
        }

        if(StringUtils.length(taskLog.getOpinion()) > 500
                || StringUtils.length(taskLog.getAttach()) > 2000) {
            json.put("code", 0);
            json.put("msg",  "参数错误!数据超长。");
            return json.toString();
        }

        Task taskToUdProc = null;

        if(taskLog.getTaskId() == null || (taskToUdProc = taskServices.getById((long)taskLog.getTaskId())) == null) {
            json.put("code", 0);
            json.put("msg",  "参数错误!任务ID不存在。");
            return json.toString();
        }

        ISysUser currentUser = ContextUtil.getCurrentUser();

        try {
            //taskLog.getCommited() 0是保存，1是提交
            if (taskLog.getCommited() == 0) {
                //新建
                if(taskLog.getId() == null) {
                    //保存的时候设置当前时间
                    taskLog.setCtime(new Date());
                    taskLog.setCuser(currentUser.getFullname());
                    taskLog.setCuserid(currentUser.getUserId() + "");
                    logService.add(taskLog);
                } else {
                    //保存
                    TaskLog taskLogOld = logService.getById((long)taskLog.getId());
                    if(taskLogOld == null) {
                        json.put("code", 0);
                        json.put("msg",  "参数错误!要保存的任务意见不存在。");
                        return json.toString();
                    }

                    //只更新时间、意见、附件
                    taskLogOld.setOpinion(taskLog.getOpinion());
                    taskLogOld.setAttach(taskLog.getAttach());
                    taskLogOld.setCtime(new Date());

                    logService.update(taskLogOld);
                }
            } else if (taskLog.getCommited() == 1) {
                //新建提交
                if(taskLog.getId() == null) {
                    taskLog.setCtime(new Date());
                    taskLog.setCuser(currentUser.getFullname());
                    taskLog.setCuserid(currentUser.getUserId() + "");
                    logService.add(taskLog);
                } else {
                    //保存提交
                    TaskLog taskLogOld = logService.getById((long)taskLog.getId());
                    if(taskLogOld == null) {
                        json.put("code", 0);
                        json.put("msg",  "参数错误!要提交的任务意见不存在。");
                        return json.toString();
                    }

                    //只更新时间、意见、附件
                    taskLogOld.setOpinion(taskLog.getOpinion());
                    taskLogOld.setAttach(taskLog.getAttach());
                    taskLogOld.setCtime(new Date());
                    taskLogOld.setCommited(1);

                    logService.update(taskLogOld);
                }

                //提交后，更新任务进度
                this.udTaskAfterTaskLogCommit(taskToUdProc, request);

            } else {
                json.put("code", 0);
                json.put("msg",  "参数错误!");
                return json.toString();
            }
        } catch (Exception e) {

            logger.error("newsavetasklog error!" + e);

            json.put("code", -1);
            json.put("msg",  "保存出错！请联系管理员");
        }

        return json.toString();
    }


    /**
     * 所有人都填意见完了并且提交了，把任务更新为已完成
     * @param taskToUd
     * @param request
     */
    private void udTaskAfterTaskLogCommit(Task taskToUd, HttpServletRequest request) {
        if(taskToUd == null) {

            logger.info("udTaskAfterTaskLogCommit, but taskId is taskToUd!");
            return;
        }

        //看所有的办理人是不是都提交了
        String memIds = taskToUd.getMemberID();
        if(StringUtils.isEmpty(memIds)) {
            //没有办理人。。
            logger.info("udTaskAfterTaskLogCommit, but no memIds!");
            return;
        }

        String[] memAry = memIds.split(",");

        if(memAry.length > 0) {
            QueryFilter queryFilter=new QueryFilter(request,"logItem");
            queryFilter.addFilter("taskId", taskToUd.getId());
            queryFilter.addFilter("commited", 1);
            List<TaskLog> logList = logService.getAll(queryFilter);

            if (logList != null && logList.size() == memAry.length) {

                logger.info("to update w_task stage = 4; id=" + taskToUd.getId());
                taskToUd.setStage(4);
                taskServices.update(taskToUd);
            }
        }
    }
}
