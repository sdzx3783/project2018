package com.hotent.makshi.controller.operation;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hotent.platform.annotion.Action;
import org.springframework.web.servlet.ModelAndView;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.bpm.impl.ScriptImpl;
import com.hotent.makshi.model.operation.AccessExamine;
import com.hotent.makshi.model.operation.MonthlyManage;
import com.hotent.makshi.service.operation.MonthlyManageService;
import com.hotent.makshi.service.operation.OperationWeeklyManageService;
import com.hotent.core.web.ResultMessage;

/**
 * 对象功能:月报管理 控制器类
 */
@Controller
@RequestMapping("/makshi/operation/monthlyManage/")
public class MonthlyManageController extends BaseController
{
	@Resource
	private MonthlyManageService monthlyManageService;
	@Resource
	private OperationWeeklyManageService operationWeeklyManageService;
	/**
	 * 添加或更新月报管理。
	 * @param request
	 * @param response
	 * @param monthlyManage 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新月报管理")
	public void save(HttpServletRequest request, HttpServletResponse response,MonthlyManage monthlyManage) throws Exception
	{
		String resultMsg=null;		
		try{
			if(monthlyManage.getId()==null){
				monthlyManageService.save(monthlyManage);
				resultMsg=getText("添加","月报管理");
			}else{
			    monthlyManageService.save(monthlyManage);
				resultMsg=getText("更新","月报管理");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得月报管理分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看月报管理分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	 
		QueryFilter fiter = new QueryFilter(request,"monthlyManageItem");
		Boolean isEditer = false;
		AccessExamine accessExamine = operationWeeklyManageService.examineIsEditer(isEditer,fiter,1);
		List<MonthlyManage> list=monthlyManageService.getAll(accessExamine.getFiter());
		isEditer = true;
		ModelAndView mv=this.getAutoView().addObject("monthlyManageList",list).addObject("isEditer",accessExamine.getIsEditer());
		return mv;
	}
	
	/**
	 * 删除月报管理
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除月报管理")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"id");
			monthlyManageService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除月报管理成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑月报管理
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑月报管理")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		String returnUrl=RequestUtil.getPrePage(request);
		MonthlyManage monthlyManage=monthlyManageService.getById(id);
		if(monthlyManage==null||monthlyManage.getEditer()==null){
			ScriptImpl scriptImpl = new ScriptImpl();
			monthlyManage = new MonthlyManage();
			SysUser user = scriptImpl.getCurrentUser();
			monthlyManage.setEditer(user.getFullname());
			monthlyManage.setEditerID(user.getUserId().toString());
			monthlyManage.setEdit_date(new Date());
		}
		return getAutoView().addObject("monthlyManage",monthlyManage)
							.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得月报管理明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看月报管理明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		MonthlyManage monthlyManage=monthlyManageService.getById(id);
		return getAutoView().addObject("monthlyManage", monthlyManage);
	}
	
}