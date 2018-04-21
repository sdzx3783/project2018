

package com.hotent.makshi.controller.hr;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.engine.GroovyScriptEngine;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.makshi.model.hr.PoliticalIn;
import com.hotent.makshi.service.hr.PoliticalInService;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.model.system.Position;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.system.PositionService;
/**
 * 对象功能:党员转入 控制器类
 */
@Controller
@RequestMapping("/makshi/hr/politicalIn/")
public class PoliticalInController extends BaseController
{
	@Resource
	private PoliticalInService politicalInService;
	@Resource
	private GroovyScriptEngine engine;
	
	@Resource
	private PositionService positionService;
	
	/**
	 * 添加或更新党员转入。
	 * @param request
	 * @param response
	 * @param politicalIn 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新党员转入")
	public void save(HttpServletRequest request, HttpServletResponse response,PoliticalIn politicalIn) throws Exception
	{
		String resultMsg=null;		
		try{
			
			if(politicalIn.getId()==null){
				politicalInService.save(politicalIn);
				resultMsg=getText("添加","党员转入");
			}else{
			    politicalInService.save(politicalIn);
				resultMsg=getText("更新","党员转入");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得党员转入分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看党员转入分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		SysUser sysUser =(SysUser) ContextUtil.getContext().getCurrentUser();
		Long userid= sysUser.getUserId();
		//判断用户是否是管理员
		if(userid.longValue()==1l){
			return this.getAutoView().addObject("queryAll",true);
		}
		List<Position> positionlist = positionService.getByUserId(sysUser.getUserId());
		for(Position position:positionlist){
			//判断岗位
			if(position.getPosId().longValue()==20000000980812L){//办公室_党建工作管理员
				return this.getAutoView().addObject("queryAll",true);
			}
		}
		List<PoliticalIn> politicalInList = politicalInService.getByUserId(userid.toString());
		return this.getAutoView().addObject("queryAll",false).addObject("politicalInList",politicalInList);
	}
	
	/**
	 * 删除党员转入
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除党员信息")
	public @ResponseBody ResultMessage del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"id");
			politicalInService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
		return message;
	}
	
	/**
	 * 	编辑党员转入
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑党员转入")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		
		String returnUrl=RequestUtil.getPrePage(request);
		PoliticalIn politicalIn=politicalInService.getById(id);
		if(BeanUtils.isEmpty(politicalIn)){
			politicalIn=new PoliticalIn();
			String user_name_script="return scriptImpl.getCurrentName();";
			politicalIn.setUser_name(engine.executeObject(user_name_script, null).toString());
		}
		
		return getAutoView().addObject("politicalIn",politicalIn)
							.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得党员转入明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看党员转入明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		PoliticalIn politicalIn=politicalInService.getById(id);
		return getAutoView().addObject("politicalIn", politicalIn);
	}
	
}