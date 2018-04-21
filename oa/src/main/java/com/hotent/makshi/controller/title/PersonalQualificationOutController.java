

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

import com.hotent.makshi.model.title.PersonalQualificationOut;
import com.hotent.makshi.service.title.PersonalQualificationOutService;
import com.hotent.core.web.ResultMessage;
/**
 * 对象功能:个人执业资格转出 控制器类
 */
@Controller
@RequestMapping("/makshi/title/personalQualificationOut/")
public class PersonalQualificationOutController extends BaseController
{
	@Resource
	private PersonalQualificationOutService personalQualificationOutService;
	
	/**
	 * 添加或更新个人执业资格转出。
	 * @param request
	 * @param response
	 * @param personalQualificationOut 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新个人执业资格转出")
	public void save(HttpServletRequest request, HttpServletResponse response,PersonalQualificationOut personalQualificationOut) throws Exception
	{
		String resultMsg=null;		
		try{
			if(personalQualificationOut.getId()==null){
				personalQualificationOutService.save(personalQualificationOut);
				resultMsg=getText("添加","个人执业资格转出");
			}else{
			    personalQualificationOutService.save(personalQualificationOut);
				resultMsg=getText("更新","个人执业资格转出");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得个人执业资格转出分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看个人执业资格转出分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<PersonalQualificationOut> list=personalQualificationOutService.getAll(new QueryFilter(request,"personalQualificationOutItem"));
		ModelAndView mv=this.getAutoView().addObject("personalQualificationOutList",list);
		return mv;
	}
	
	/**
	 * 删除个人执业资格转出
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除个人执业资格转出")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"id");
			personalQualificationOutService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除个人执业资格转出成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑个人执业资格转出
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑个人执业资格转出")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		
		String returnUrl=RequestUtil.getPrePage(request);
		PersonalQualificationOut personalQualificationOut=personalQualificationOutService.getById(id);
		
		return getAutoView().addObject("personalQualificationOut",personalQualificationOut)
							.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得个人执业资格转出明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看个人执业资格转出明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		PersonalQualificationOut personalQualificationOut=personalQualificationOutService.getById(id);
		return getAutoView().addObject("personalQualificationOut", personalQualificationOut);
	}
	
}