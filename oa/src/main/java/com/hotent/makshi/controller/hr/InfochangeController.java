

package com.hotent.makshi.controller.hr;

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
import com.hotent.platform.model.system.SysUser;
import org.apache.commons.lang.exception.ExceptionUtils;
import com.hotent.core.bpm.util.BpmUtil;
import net.sf.json.JSONObject;
import com.hotent.core.util.MapUtil;

import com.hotent.makshi.model.hr.Infochange;
import com.hotent.makshi.service.hr.InfochangeService;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.engine.GroovyScriptEngine;
/**
 * 对象功能:信息变更 控制器类
 */
@Controller
@RequestMapping("/makshi/hr/infochange/")
public class InfochangeController extends BaseController
{
	@Resource
	private InfochangeService infochangeService;
	@Resource
	private GroovyScriptEngine engine;
	
	/**
	 * 添加或更新信息变更。
	 * @param request
	 * @param response
	 * @param infochange 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新信息变更")
	public void save(HttpServletRequest request, HttpServletResponse response,Infochange infochange) throws Exception
	{
		String resultMsg=null;		
		try{
			if(infochange.getId()==null){
				infochangeService.save(infochange);
				resultMsg=getText("添加","信息变更");
			}else{
			    infochangeService.save(infochange);
				resultMsg=getText("更新","信息变更");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得信息变更分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看信息变更分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<Infochange> list=infochangeService.getAll(new QueryFilter(request,"infochangeItem"));
		ModelAndView mv=this.getAutoView().addObject("infochangeList",list);
		return mv;
	}
	
	/**
	 * 删除信息变更
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除信息变更")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"id");
			infochangeService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除信息变更成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑信息变更
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑信息变更")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		
		String returnUrl=RequestUtil.getPrePage(request);
		Infochange infochange=infochangeService.getById(id);
		if(BeanUtils.isEmpty(infochange)){
			infochange=new Infochange();
			String name_script="return scriptImpl.getCurrentName();";
			infochange.setName(engine.executeObject(name_script, null).toString());
			String department_script="return scriptImpl.getCurrentOrgName();";
			infochange.setDepartment(engine.executeObject(department_script, null).toString());
			String user_num_script="return scriptImpl.getCurrentUserId();";
			//infochange.setUser_num(engine.executeObject(user_num_script, null).toString());
		}
		
		return getAutoView().addObject("infochange",infochange)
							.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得信息变更明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看信息变更明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		Infochange infochange=infochangeService.getById(id);
		return getAutoView().addObject("infochange", infochange);
	}
	
}