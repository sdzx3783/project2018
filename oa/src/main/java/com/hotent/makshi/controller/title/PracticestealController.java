

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

import com.hotent.makshi.model.title.Practicesteal;
import com.hotent.makshi.service.title.PracticestealService;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.engine.GroovyScriptEngine;
/**
 * 对象功能:个人执业印章申请 控制器类
 */
@Controller
@RequestMapping("/makshi/title/practicesteal/")
public class PracticestealController extends BaseController
{
	@Resource
	private PracticestealService practicestealService;
	@Resource
	private GroovyScriptEngine engine;
	
	/**
	 * 添加或更新个人执业印章申请。
	 * @param request
	 * @param response
	 * @param practicesteal 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新个人执业印章申请")
	public void save(HttpServletRequest request, HttpServletResponse response,Practicesteal practicesteal) throws Exception
	{
		String resultMsg=null;		
		try{
			if(practicesteal.getId()==null){
				practicestealService.save(practicesteal);
				resultMsg=getText("添加","个人执业印章申请");
			}else{
			    practicestealService.save(practicesteal);
				resultMsg=getText("更新","个人执业印章申请");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得个人执业印章申请分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看个人执业印章申请分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<Practicesteal> list=practicestealService.getAll(new QueryFilter(request,"practicestealItem"));
		ModelAndView mv=this.getAutoView().addObject("practicestealList",list);
		return mv;
	}
	
	/**
	 * 删除个人执业印章申请
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除个人执业印章申请")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"id");
			practicestealService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除个人执业印章申请成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑个人执业印章申请
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑个人执业印章申请")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		
		String returnUrl=RequestUtil.getPrePage(request);
		Practicesteal practicesteal=practicestealService.getById(id);
		if(BeanUtils.isEmpty(practicesteal)){
			practicesteal=new Practicesteal();
			String user_id_script="return scriptImpl.getCurrentUserId();";
			practicesteal.setUser_id(engine.executeObject(user_id_script, null).toString());
			String gh_script="return scriptImpl.getAccount() ;";
			practicesteal.setGh(engine.executeObject(gh_script, null).toString());
		}
		
		return getAutoView().addObject("practicesteal",practicesteal)
							.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得个人执业印章申请明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看个人执业印章申请明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		Practicesteal practicesteal=practicestealService.getById(id);
		return getAutoView().addObject("practicesteal", practicesteal);
	}
	
}