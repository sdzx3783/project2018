

package com.hotent.makshi.controller.honor;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.makshi.model.honor.GroupHonor;
import com.hotent.makshi.service.honor.GroupHonorService;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.model.system.Position;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.system.PositionService;
/**
 * 对象功能:集体荣誉 控制器类
 */
@Controller
@RequestMapping("/makshi/honor/groupHonor/")
public class GroupHonorController extends BaseController
{
	@Resource
	private GroupHonorService groupHonorService;
	@Resource
	private PositionService positionService;
	/**
	 * 添加或更新集体荣誉。
	 * @param request
	 * @param response
	 * @param groupHonor 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新集体荣誉")
	public void save(HttpServletRequest request, HttpServletResponse response,GroupHonor groupHonor) throws Exception
	{
		String resultMsg=null;		
		try{
			if(groupHonor.getId()==null){
				groupHonorService.save(groupHonor);
				resultMsg=getText("添加","集体荣誉");
			}else{
			    groupHonorService.save(groupHonor);
				resultMsg=getText("更新","集体荣誉");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得集体荣誉分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看集体荣誉分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		SysUser sysUser =(SysUser) ContextUtil.getContext().getCurrentUser();
		Long userid= sysUser.getUserId();
		boolean isEditor = false;
		//判断用户是否是管理员
		if(userid.longValue()==1l){
			isEditor = true;
		}
		List<Position> positionlist = positionService.getByUserId(sysUser.getUserId());
		for(Position position:positionlist){
			//判断岗位
			if(position.getPosId().longValue()==20000000980812L){//办公室——党建工作管理员
				isEditor = true;
			}
		}
		List<GroupHonor> list=groupHonorService.getAll(new QueryFilter(request,"groupHonorItem"));
		ModelAndView mv=this.getAutoView().addObject("groupHonorList",list).addObject("isEditor",isEditor);
		return mv;
	}
	
	/**
	 * 删除集体荣誉
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除集体荣誉")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"id");
			groupHonorService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除集体荣誉成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑集体荣誉
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑集体荣誉")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		
		String returnUrl=RequestUtil.getPrePage(request);
		GroupHonor groupHonor=groupHonorService.getById(id);
		
		return getAutoView().addObject("groupHonor",groupHonor)
							.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得集体荣誉明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看集体荣誉明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		GroupHonor groupHonor=groupHonorService.getById(id);
		return getAutoView().addObject("groupHonor", groupHonor);
	}
	
	
	
	
	
	
}