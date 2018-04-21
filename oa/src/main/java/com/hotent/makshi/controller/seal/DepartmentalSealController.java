

package com.hotent.makshi.controller.seal;

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

import com.hotent.makshi.model.seal.DepartmentalSeal;
import com.hotent.makshi.service.seal.DepartmentalSealService;
import com.hotent.core.web.ResultMessage;
/**
 * 对象功能:部门公章使用流程 控制器类
 */
@Controller
@RequestMapping("/makshi/seal/departmentalSeal/")
public class DepartmentalSealController extends BaseController
{
	@Resource
	private DepartmentalSealService departmentalSealService;
	
	/**
	 * 添加或更新部门公章使用流程。
	 * @param request
	 * @param response
	 * @param departmentalSeal 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新部门公章使用流程")
	public void save(HttpServletRequest request, HttpServletResponse response,DepartmentalSeal departmentalSeal) throws Exception
	{
		String resultMsg=null;		
		try{
			if(departmentalSeal.getId()==null){
				departmentalSealService.save(departmentalSeal);
				resultMsg=getText("添加","部门公章使用流程");
			}else{
			    departmentalSealService.save(departmentalSeal);
				resultMsg=getText("更新","部门公章使用流程");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得部门公章使用流程分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看部门公章使用流程分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<DepartmentalSeal> list=departmentalSealService.getAll(new QueryFilter(request,"departmentalSealItem"));
		ModelAndView mv=this.getAutoView().addObject("departmentalSealList",list);
		return mv;
	}
	
	/**
	 * 删除部门公章使用流程
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除部门公章使用流程")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"id");
			departmentalSealService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除部门公章使用流程成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑部门公章使用流程
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑部门公章使用流程")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		
		String returnUrl=RequestUtil.getPrePage(request);
		DepartmentalSeal departmentalSeal=departmentalSealService.getById(id);
		
		return getAutoView().addObject("departmentalSeal",departmentalSeal)
							.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得部门公章使用流程明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看部门公章使用流程明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		DepartmentalSeal departmentalSeal=departmentalSealService.getById(id);
		return getAutoView().addObject("departmentalSeal", departmentalSeal);
	}
	
}