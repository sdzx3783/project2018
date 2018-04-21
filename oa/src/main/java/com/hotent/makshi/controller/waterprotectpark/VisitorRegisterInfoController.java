

package com.hotent.makshi.controller.waterprotectpark;

import java.util.Date;
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
import com.hotent.platform.model.system.SysRole;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.system.SysRoleService;

import org.apache.commons.lang.exception.ExceptionUtils;

import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.bpm.util.BpmUtil;
import net.sf.json.JSONObject;
import com.hotent.core.util.MapUtil;

import com.hotent.makshi.model.waterprotectpark.VisitorRegisterInfo;
import com.hotent.makshi.service.waterprotectpark.VisitorRegisterInfoService;
import com.hotent.core.web.ResultMessage;
/**
 * 对象功能:参观登记信息-水保园 控制器类
 */
@Controller
@RequestMapping("/makshi/waterprotectpark/visitorRegisterInfo/")
public class VisitorRegisterInfoController extends BaseController
{
	@Resource
	private VisitorRegisterInfoService visitorRegisterInfoService;
	@Resource
	private SysRoleService sysRoleService;
	
	/**
	 * 添加或更新参观登记信息-水保园。
	 * @param request
	 * @param response
	 * @param visitorRegisterInfo 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新参观登记信息-水保园")
	public void save(HttpServletRequest request, HttpServletResponse response,VisitorRegisterInfo visitorRegisterInfo) throws Exception
	{
		String resultMsg=null;		
		try{
			if(visitorRegisterInfo.getId()==null){
				visitorRegisterInfoService.save(visitorRegisterInfo);
				resultMsg=getText("添加","参观登记信息-水保园");
			}else{
			    visitorRegisterInfoService.save(visitorRegisterInfo);
				resultMsg=getText("更新","参观登记信息-水保园");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得参观登记信息-水保园分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看参观登记信息-水保园分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<VisitorRegisterInfo> list=visitorRegisterInfoService.getAll(new QueryFilter(request,"visitorRegisterInfoItem"));
		ModelAndView mv=this.getAutoView().addObject("visitorRegisterInfoList",list);
		Long currentUserId = ContextUtil.getCurrentUserId();
		Boolean hasAccess=false;
		if(currentUserId==1l){
			hasAccess=true;
		}else{
			List<SysRole> roles = sysRoleService.getByUserId(currentUserId);
			for (SysRole sysRole : roles) {
				if((sysRole.getRoleId()+"").equals("10000013290008")){//宣教服务管理员——水保部
					hasAccess=true;
					break;
				}
			}
		}
		return mv.addObject("hasAccess", hasAccess);
	}
	
	/**
	 * 删除参观登记信息-水保园
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除参观登记信息-水保园")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"id");
			visitorRegisterInfoService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除参观登记信息-水保园成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑参观登记信息-水保园
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑参观登记信息-水保园")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		
		String returnUrl=RequestUtil.getPrePage(request);
		VisitorRegisterInfo visitorRegisterInfo=visitorRegisterInfoService.getById(id);
		if(visitorRegisterInfo==null){
			visitorRegisterInfo=new VisitorRegisterInfo();
			visitorRegisterInfo.setRegisterTime(new Date());
		}
		return getAutoView().addObject("visitorRegisterInfo",visitorRegisterInfo)
							.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得参观登记信息-水保园明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看参观登记信息-水保园明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		VisitorRegisterInfo visitorRegisterInfo=visitorRegisterInfoService.getById(id);
		return getAutoView().addObject("visitorRegisterInfo", visitorRegisterInfo);
	}
	
}