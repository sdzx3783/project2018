package com.hotent.platform.controller.ats;

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
import com.hotent.core.util.BeanUtils;
import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.util.StringUtil;
import org.apache.commons.lang.exception.ExceptionUtils;
import com.hotent.platform.model.system.SysUser;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import com.hotent.core.util.StringUtil;

import com.hotent.platform.model.ats.AtsOverTime;
import com.hotent.platform.service.ats.AtsOverTimeService;
import com.hotent.core.web.ResultMessage;
/**
 *<pre>
 * 对象功能:考勤加班单 控制器类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:zxh
 * 创建时间:2015-07-15 15:00:33
 *</pre>
 */
@Controller
@RequestMapping("/platform/ats/atsOverTime/")
public class AtsOverTimeController extends BaseController
{
	@Resource
	private AtsOverTimeService atsOverTimeService;
	
	
	/**
	 * 添加或更新考勤加班单。
	 * @param request
	 * @param response
	 * @param atsOverTime 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新考勤加班单")
	public void save(HttpServletRequest request, HttpServletResponse response,AtsOverTime atsOverTime) throws Exception
	{
		String resultMsg=null;		
		try{
			if(atsOverTime.getId()==null||atsOverTime.getId()==0){
				atsOverTimeService.save(atsOverTime);
				resultMsg=getText("添加","考勤加班单");
			}else{
			    atsOverTimeService.save(atsOverTime);
				resultMsg=getText("更新","考勤加班单");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	
	/**
	 * 取得考勤加班单分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看考勤加班单分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<AtsOverTime> list=atsOverTimeService.getAll(new QueryFilter(request,"atsOverTimeItem"));
		ModelAndView mv=this.getAutoView().addObject("atsOverTimeList",list);
		return mv;
	}
	
	/**
	 * 删除考勤加班单
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除考勤加班单")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "id");
			atsOverTimeService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除考勤加班单成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑考勤加班单
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑考勤加班单")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id",0L);
		String returnUrl=RequestUtil.getPrePage(request);
		AtsOverTime atsOverTime=atsOverTimeService.getById(id);
		
		return getAutoView().addObject("atsOverTime",atsOverTime)
							.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得考勤加班单明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看考勤加班单明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		AtsOverTime atsOverTime = atsOverTimeService.getById(id);	
		return getAutoView().addObject("atsOverTime", atsOverTime);
	}
	
}

