package com.hotent.makshi.controller.operation;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.makshi.model.operation.AccessExamine;
import com.hotent.makshi.model.operation.OperationWeeklyManage;
import com.hotent.makshi.service.operation.OperationWeeklyManageService;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.bpm.impl.ScriptImpl;

/**
 * 对象功能:运维部周报管理 控制器类
 */
@Controller
@RequestMapping("/makshi/operation/operationWeeklyManage/")
public class OperationWeeklyManageController extends BaseController
{
	@Resource
	private OperationWeeklyManageService operationWeeklyManageService;
	/**
	 * 添加或更新运维部周报管理。
	 * @param request
	 * @param response
	 * @param operationWeeklyManage 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新运维部周报管理")
	public void save(HttpServletRequest request, HttpServletResponse response,OperationWeeklyManage operationWeeklyManage) throws Exception
	{
		String resultMsg=null;		
		try{
			if(operationWeeklyManage.getId()==null){
				operationWeeklyManageService.save(operationWeeklyManage);
				resultMsg=getText("添加","运维部周报管理");
			}else{
			    operationWeeklyManageService.save(operationWeeklyManage);
				resultMsg=getText("更新","运维部周报管理");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得运维部周报管理分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看运维部周报管理分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		QueryFilter fiter = new QueryFilter(request,"operationWeeklyManageItem");
		Boolean isEditer = false;
		AccessExamine accessExamine = operationWeeklyManageService.examineIsEditer(isEditer,fiter,1);
		List<OperationWeeklyManage> list=operationWeeklyManageService.getAll(accessExamine.getFiter());
		ModelAndView mv=this.getAutoView().addObject("operationWeeklyManageList",list).addObject("isEditer",accessExamine.getIsEditer());
		return mv;
	}
	
	/**
	 * 删除运维部周报管理
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除运维部周报管理")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"id");
			operationWeeklyManageService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除运维部周报管理成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑运维部周报管理
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑运维部周报管理")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		String returnUrl=RequestUtil.getPrePage(request);
		OperationWeeklyManage operationWeeklyManage=operationWeeklyManageService.getById(id);
		if(operationWeeklyManage==null||operationWeeklyManage.getEditer()==null){
			ScriptImpl scriptImpl = new ScriptImpl();
			operationWeeklyManage = new OperationWeeklyManage();
			SysUser user = scriptImpl.getCurrentUser();
			operationWeeklyManage.setEditer(user.getFullname());
			operationWeeklyManage.setEditerID(user.getUserId().toString());
			operationWeeklyManage.setEdit_date(new Date());
		}
		return getAutoView().addObject("operationWeeklyManage",operationWeeklyManage)
							.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得运维部周报管理明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看运维部周报管理明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		OperationWeeklyManage operationWeeklyManage=operationWeeklyManageService.getById(id);
		return getAutoView().addObject("operationWeeklyManage", operationWeeklyManage);
	}
	
}