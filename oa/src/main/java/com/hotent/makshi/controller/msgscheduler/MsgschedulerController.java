

package com.hotent.makshi.controller.msgscheduler;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quartz.SchedulerException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.scheduler.SchedulerService;
import com.hotent.core.util.StringPool;
import com.hotent.core.util.StringUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.makshi.model.msgscheduler.SchedulerParams;
import com.hotent.makshi.model.msgscheduler.SendMsgUser;
import com.hotent.makshi.service.msgscheduler.SchedulerParamsService;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.model.system.SysPlan;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.bpm.thread.MessageUtil;
import com.hotent.platform.service.system.SysPlanService;
/**
 * 对象功能:租房报销记录表 控制器类
 */
@Controller
@RequestMapping("/makshi/msgscheduler/sysPlan/")
public class MsgschedulerController extends BaseController
{
	
	@Resource
	private SysPlanService sysPlanService;

	@Resource
	private SchedulerService schedulerService;
	
	@Resource
	private SchedulerParamsService SchedulerParamsService;
	
	/**
	 * 添加或更新日程表。
	 * @param request
	 * @param response
	 * @param sysPlan 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新日程表")
	public void save(HttpServletRequest request, HttpServletResponse response,SysPlan sysPlan) throws Exception{
		String resultMsg="保存日程失败！";		
		try{
			String participantIds = RequestUtil.getString(request, "participantIds", "");
			String participants = RequestUtil.getString(request, "participants", "");
			Date startTime = RequestUtil.getDate(request, "startTime" ,StringPool.DATE_FORMAT_DATETIME_NOSECOND);
			Date endTime = RequestUtil.getDate(request, "endTime" ,StringPool.DATE_FORMAT_DATETIME_NOSECOND);
			//Date noteTime = RequestUtil.getDate(request, "noteTime" ,StringPool.DATE_FORMAT_DATETIME_NOSECOND);
			sysPlan.setEndTime(endTime);
			sysPlan.setStartTime(startTime);
			String noteTime = RequestUtil.getString(request, "noteTime", "");
			PrintWriter out=response.getWriter();
			
			String jobName = "planMsgJob";
			String trigName = Long.valueOf(System.currentTimeMillis()).toString();
			//保存参数
			if(StringUtil.isNotEmpty(noteTime)){
				SchedulerParams schedulerParams = new SchedulerParams();
				List<SendMsgUser> sendMsgUserList = new ArrayList<SendMsgUser>();
				SendMsgUser sendMsgUser = new SendMsgUser();
				Long userId = ContextUtil.getCurrentUserId();
				sendMsgUser.setUserId(userId);
				schedulerParams.setJobName(jobName);
				schedulerParams.setTrigger(trigName);
				sendMsgUserList.add(sendMsgUser);
				schedulerParams.setSendMsgUserList(sendMsgUserList);
				SchedulerParamsService.save(schedulerParams);
				String planJson = "{\"type\":1,\"timeInterval\":\""+noteTime+"\"}";
				//判断触发器是否存在
				boolean rtn=schedulerService.isTriggerExists(trigName);
				if(rtn)
				{
					ResultMessage obj=new ResultMessage(ResultMessage.Fail,"指定的计划名称已经存在!");
					out.print(obj.toString());
				}
				try {
					schedulerService.addTrigger(jobName, trigName, planJson);
				} catch (SchedulerException e) {
					String str = MessageUtil.getMessage();
					if (StringUtil.isNotEmpty(str)) {
						ResultMessage resultMessage = new ResultMessage(ResultMessage.Fail,"添加计划失败:" + str);
						writeResultMessage(response.getWriter(),resultMessage.getMessage()+","+e.getMessage(),ResultMessage.Fail);
					} 
				}
			}
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("participantIds", participantIds);
			params.put("participants", participants);
			resultMsg = sysPlanService.saveSysPlan(sysPlan, params);
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
}