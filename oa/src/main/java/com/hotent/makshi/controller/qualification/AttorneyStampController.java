

package com.hotent.makshi.controller.qualification;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hotent.platform.annotion.Action;
import org.springframework.web.servlet.ModelAndView;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.page.PageList;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.bpm.BpmService;
import com.hotent.platform.service.bpm.ProcessRunService;

import org.apache.commons.lang.exception.ExceptionUtils;

import com.hotent.core.bpm.model.ProcessTask;
import com.hotent.core.bpm.util.BpmUtil;
import net.sf.json.JSONObject;
import com.hotent.core.util.MapUtil;

import com.hotent.makshi.model.qualification.AttorneyStamp;
import com.hotent.makshi.service.qualification.AttorneyStampService;
import com.hotent.core.web.ResultMessage;
/**
 * 对象功能:法定委托书盖章申请 控制器类
 */
@Controller
@RequestMapping("/makshi/qualification/attorneyStamp/")
public class AttorneyStampController extends BaseController
{
	@Resource
	private AttorneyStampService attorneyStampService;
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmService bpmService;
	/**
	 * 添加或更新法定委托书盖章申请。
	 * @param request
	 * @param response
	 * @param attorneyStamp 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新法定委托书盖章申请")
	public void save(HttpServletRequest request, HttpServletResponse response,AttorneyStamp attorneyStamp) throws Exception
	{
		String resultMsg=null;		
		try{
			if(attorneyStamp.getId()==null){
				attorneyStampService.save(attorneyStamp);
				resultMsg=getText("添加","法定委托书盖章申请");
			}else{
			    attorneyStampService.save(attorneyStamp);
				resultMsg=getText("更新","法定委托书盖章申请");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得法定委托书盖章申请分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看法定委托书盖章申请分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<AttorneyStamp> list=attorneyStampService.getAll(new QueryFilter(request,"attorneyStampItem"));
		for (AttorneyStamp attorneyStamp : list) {
			ProcessRun processRun = processRunService.getById(Long.parseLong(attorneyStamp.getRunid()));
			List<ProcessTask> proTasks = null;
			if(BeanUtils.isNotEmpty(processRun)){
				if(processRun.getActInstId()==null) continue;
				proTasks = bpmService.getTasks(processRun.getActInstId());
				attorneyStamp.setRunStatus("已结束");
				if(proTasks!=null && proTasks.size()>0){
					attorneyStamp.setRunStatus(proTasks.get(0).getName());
				}
			}
		}
		ModelAndView mv=this.getAutoView().addObject("attorneyStampList",list);
		return mv;
	}
	
	/**
	 * 删除法定委托书盖章申请
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除法定委托书盖章申请")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"id");
			attorneyStampService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除法定委托书盖章申请成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑法定委托书盖章申请
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑法定委托书盖章申请")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		
		String returnUrl=RequestUtil.getPrePage(request);
		AttorneyStamp attorneyStamp=attorneyStampService.getById(id);
		
		return getAutoView().addObject("attorneyStamp",attorneyStamp)
							.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得法定委托书盖章申请明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看法定委托书盖章申请明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		AttorneyStamp attorneyStamp=attorneyStampService.getById(id);
		return getAutoView().addObject("attorneyStamp", attorneyStamp);
	}
	
}