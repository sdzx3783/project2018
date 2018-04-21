

package com.hotent.makshi.controller.title;

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

import com.hotent.makshi.model.title.TitleDeclaration;
import com.hotent.makshi.service.title.TitleDeclarationService;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.engine.GroovyScriptEngine;
import com.hotent.platform.service.system.IdentityService;
/**
 * 对象功能:职称申报 控制器类
 */
@Controller
@RequestMapping("/makshi/title/titleDeclaration/")
public class TitleDeclarationController extends BaseController
{
	@Resource
	private TitleDeclarationService titleDeclarationService;
	@Resource
	private GroovyScriptEngine engine;
	@Resource
	private IdentityService identityService;
	
	/**
	 * 添加或更新职称申报。
	 * @param request
	 * @param response
	 * @param titleDeclaration 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新职称申报")
	public void save(HttpServletRequest request, HttpServletResponse response,TitleDeclaration titleDeclaration) throws Exception
	{
		String resultMsg=null;		
		try{
			if(titleDeclaration.getId()==null){
				titleDeclarationService.save(titleDeclaration);
				resultMsg=getText("添加","职称申报");
			}else{
			    titleDeclarationService.save(titleDeclaration);
				resultMsg=getText("更新","职称申报");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得职称申报分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看职称申报分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<TitleDeclaration> list=titleDeclarationService.getAll(new QueryFilter(request,"titleDeclarationItem"));
		ModelAndView mv=this.getAutoView().addObject("titleDeclarationList",list);
		return mv;
	}
	
	/**
	 * 删除职称申报
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除职称申报")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"id");
			titleDeclarationService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除职称申报成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑职称申报
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑职称申报")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		
		String returnUrl=RequestUtil.getPrePage(request);
		TitleDeclaration titleDeclaration=titleDeclarationService.getById(id);
		if(BeanUtils.isEmpty(titleDeclaration)){
			titleDeclaration=new TitleDeclaration();
			String gloalFlowNo_id=identityService.nextId("globalFlowNo");
			titleDeclaration.setGloalFlowNo(gloalFlowNo_id);
			String userNo_script="return scriptImpl.getCurrentUserId();";
			titleDeclaration.setUserNo(engine.executeObject(userNo_script, null).toString());
			String name_script="return scriptImpl.getCurrentName();";
			titleDeclaration.setName(engine.executeObject(name_script, null).toString());
		}
		
		return getAutoView().addObject("titleDeclaration",titleDeclaration)
							.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得职称申报明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看职称申报明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		TitleDeclaration titleDeclaration=titleDeclarationService.getById(id);
		return getAutoView().addObject("titleDeclaration", titleDeclaration);
	}
	
}