package com.hotent.platform.controller.bpm;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hotent.app.push.UmengPushService;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.model.system.SysAuditModelType;
import com.hotent.platform.service.bpm.BpmService;

@Controller
@RequestMapping(value={"/platform/bpm/testApp/"})
@Action
public class TestAppPushController extends BaseController {
	
	@Resource
	UmengPushService umengPushService;
	@Resource
	private BpmService bpmService;
	
	@RequestMapping("sendAndroidUnicast")
	@Action
	public void sendAndroidUnicast(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String content="节点时间test";
		String url="http://192.168.3.44:8088/service/CompanyBookService/list";
		String deviceToken=RequestUtil.getString(request, "deviceToken");
		System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz deviceToken:"+deviceToken);
		umengPushService.sendAndroidUnicast(deviceToken, "您有新的待办任务", "流程名称姓名test", content, url);
		System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
	}
	@RequestMapping("sendIOSUnicast")
	@Action
	public void sendIOSUnicast(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String content="testIOS20181.24";
		String url="http://192.168.3.44:8088/service/CompanyBookService/list";
		String deviceToken=RequestUtil.getString(request, "deviceToken");
		System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz deviceToken:"+deviceToken);
		umengPushService.sendIOSUnicast(deviceToken, content, url);
		System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
	}
}