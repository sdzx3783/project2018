

package com.hotent.makshi.controller.party;

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

import com.hotent.makshi.model.party.PartyFileOutApplication;
import com.hotent.makshi.service.party.PartyFileOutApplicationService;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.engine.GroovyScriptEngine;
/**
 * 对象功能:党员档案转出 控制器类
 */
@Controller
@RequestMapping("/makshi/party/partyFileOutApplication/")
public class PartyFileOutApplicationController extends BaseController
{
	@Resource
	private PartyFileOutApplicationService partyFileOutApplicationService;
	@Resource
	private GroovyScriptEngine engine;
	
	/**
	 * 添加或更新党员档案转出。
	 * @param request
	 * @param response
	 * @param partyFileOutApplication 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新党员档案转出")
	public void save(HttpServletRequest request, HttpServletResponse response,PartyFileOutApplication partyFileOutApplication) throws Exception
	{
		String resultMsg=null;		
		try{
			if(partyFileOutApplication.getId()==null){
				partyFileOutApplicationService.save(partyFileOutApplication);
				resultMsg=getText("添加","党员档案转出");
			}else{
			    partyFileOutApplicationService.save(partyFileOutApplication);
				resultMsg=getText("更新","党员档案转出");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得党员档案转出分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看党员档案转出分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<PartyFileOutApplication> list=partyFileOutApplicationService.getAll(new QueryFilter(request,"partyFileOutApplicationItem"));
		ModelAndView mv=this.getAutoView().addObject("partyFileOutApplicationList",list);
		return mv;
	}
	
	/**
	 * 删除党员档案转出
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除党员档案转出")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"id");
			partyFileOutApplicationService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除党员档案转出成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑党员档案转出
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑党员档案转出")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		
		String returnUrl=RequestUtil.getPrePage(request);
		PartyFileOutApplication partyFileOutApplication=partyFileOutApplicationService.getById(id);
		if(BeanUtils.isEmpty(partyFileOutApplication)){
			partyFileOutApplication=new PartyFileOutApplication();
			String account_script="return scriptImpl.getAccount() ;";
			partyFileOutApplication.setAccount(engine.executeObject(account_script, null).toString());
		}
		
		return getAutoView().addObject("partyFileOutApplication",partyFileOutApplication)
							.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得党员档案转出明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看党员档案转出明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		PartyFileOutApplication partyFileOutApplication=partyFileOutApplicationService.getById(id);
		return getAutoView().addObject("partyFileOutApplication", partyFileOutApplication);
	}
	
}