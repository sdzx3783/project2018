

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

import com.hotent.makshi.model.title.PersonalQualificationRegist;
import com.hotent.makshi.service.title.PersonalQualificationRegistService;
import com.hotent.core.web.ResultMessage;
/**
 * 对象功能:个人执业资格初始注册 控制器类
 */
@Controller
@RequestMapping("/makshi/title/personalQualificationRegist/")
public class PersonalQualificationRegistController extends BaseController
{
	@Resource
	private PersonalQualificationRegistService personalQualificationRegistService;
	
	/**
	 * 添加或更新个人执业资格初始注册。
	 * @param request
	 * @param response
	 * @param personalQualificationRegist 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新个人执业资格初始注册")
	public void save(HttpServletRequest request, HttpServletResponse response,PersonalQualificationRegist personalQualificationRegist) throws Exception
	{
		String resultMsg=null;		
		try{
			if(personalQualificationRegist.getId()==null){
				personalQualificationRegistService.save(personalQualificationRegist);
				resultMsg=getText("添加","个人执业资格初始注册");
			}else{
			    personalQualificationRegistService.save(personalQualificationRegist);
				resultMsg=getText("更新","个人执业资格初始注册");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得个人执业资格初始注册分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看个人执业资格初始注册分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<PersonalQualificationRegist> list=personalQualificationRegistService.getAll(new QueryFilter(request,"personalQualificationRegistItem"));
		ModelAndView mv=this.getAutoView().addObject("personalQualificationRegistList",list);
		return mv;
	}
	
	/**
	 * 删除个人执业资格初始注册
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除个人执业资格初始注册")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"id");
			personalQualificationRegistService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除个人执业资格初始注册成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑个人执业资格初始注册
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑个人执业资格初始注册")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		
		String returnUrl=RequestUtil.getPrePage(request);
		PersonalQualificationRegist personalQualificationRegist=personalQualificationRegistService.getById(id);
		
		return getAutoView().addObject("personalQualificationRegist",personalQualificationRegist)
							.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得个人执业资格初始注册明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看个人执业资格初始注册明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		PersonalQualificationRegist personalQualificationRegist=personalQualificationRegistService.getById(id);
		return getAutoView().addObject("personalQualificationRegist", personalQualificationRegist);
	}
	
}