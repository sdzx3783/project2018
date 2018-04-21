package com.hotent.weixin.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotent.core.api.org.model.ISysUser;
import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.bpm.model.ProcessTask;
import com.hotent.core.encrypt.EncryptUtil;
import com.hotent.core.engine.FreemarkEngine;
import com.hotent.core.util.TimeUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.CookieUitl;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.dao.bpm.TaskDao;
import com.hotent.platform.model.system.SysBulletin;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.bpm.ITaskService;
import com.hotent.platform.service.system.SecurityUtil;
import com.hotent.platform.service.system.SysBulletinService;
import com.hotent.platform.service.system.SysUserService;
import com.hotent.weixin.model.ListModel;
import com.hotent.weixin.model.RowModel;

import freemarker.template.TemplateException;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/weixin/")
public class IndexController {
	
	@Resource
	private FreemarkEngine freemarkEngine;
	@Resource
	private TaskDao taskDao;
	@Resource
	private SysBulletinService sysBulletinService;
	@Resource
	private SysUserService sysUserService;
	@Resource
	private ITaskService taskServiceImpl;
	
	@Resource
	private SessionAuthenticationStrategy sessionStrategy = new NullAuthenticatedSessionStrategy();
	
	@RequestMapping("login.ht")
	@ResponseBody
	public ResultMessage login(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "username") String username, @RequestParam(value = "password") String password) throws IOException, TemplateException, InterruptedException{
		String rememberMe=RequestUtil.getString(request, "rememberMe","0");
		ResultMessage message=new ResultMessage(ResultMessage.Success, "");
		try{
			String encrptPassword = EncryptUtil.encryptSha256(password);
			Authentication auth= SecurityUtil.login(request, username, password, false);
			//设置从微信端登录，方便退出时退出到指定的登录页面。
			CookieUitl.addCookie("loginAction", "weixin", request, response);
			sessionStrategy.onAuthentication(auth, request, response);
			if("1" .equals(rememberMe)){
				SecurityUtil.writeRememberMeCookie(request, response, username, encrptPassword);
			}
		}
		catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, ex.getMessage());
		}
		return message;
		
		
	}
	
	@RequestMapping("index")
	@ResponseBody
	public com.alibaba.fastjson.JSONObject index(HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateException, InterruptedException{
		ISysUser user=ContextUtil.getCurrentUser();
		JSONObject data=getData(request,user);
		data.put("curUser", user.getFullname());
		return data;
	}
	
	private JSONObject getData(HttpServletRequest request,ISysUser user){
		JSONObject jsonObj=new JSONObject();
		
		List<JSONObject> taskModel=getTaskModel(request,user.getUserId());
		List<JSONObject> bulletin=getBulletinModel();
		
		jsonObj.put("task", taskModel);
		jsonObj.put("bulletin", bulletin);
		
		return jsonObj;
	}
	
	/**
	 * 获取任务列表。
	 * @param userId
	 * @return
	 */
	private List<JSONObject> getTaskModel(HttpServletRequest request,Long userId){
		List<JSONObject> taskList=new ArrayList<JSONObject>();
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("userId",userId);
		QueryFilter queryFilter=new QueryFilter(request, false);
		List<ProcessTask> list= taskServiceImpl.getMyMobileTasks(queryFilter);
		int taskAmount=0;
		for(ProcessTask task:list){
			taskAmount++;
			if(taskAmount>5) break;
			JSONObject obj=new JSONObject();
			obj.put("id", task.getId());
			obj.put("subject", task.getSubject());
			obj.put("createTime", TimeUtil.getDateString(task.getCreateTime()));
			obj.put("defId", task.getDefId());
			obj.put("runId", task.getRunId());
			obj.put("creator", task.getCreator());
			obj.put("nodename", task.getName());
			
			taskList.add(obj);
			
		}
		return taskList;
	}
	
	/**
	 * 获取公告列表。
	 * @return
	 */
	private List<JSONObject> getBulletinModel(){
		List<JSONObject> bulletinList=new ArrayList<JSONObject>();
	
		List<SysBulletin> sysBulletins= sysBulletinService.getTopBulletin(5);
		for(SysBulletin bulletin:sysBulletins){
			String date=TimeUtil.getDateString(bulletin.getCreatetime() );
			JSONObject object=new JSONObject();
			
			object.put("subject", bulletin.getSubject());
			object.put("id", bulletin.getId());
			object.put("date", date);
			
			bulletinList.add(object);
		}
		return bulletinList;
	}

	
}
