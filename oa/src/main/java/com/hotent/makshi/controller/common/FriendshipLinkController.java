

package com.hotent.makshi.controller.common;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.page.PageBean;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.makshi.model.common.FriendshipLink;
import com.hotent.makshi.service.common.FriendshipLinkService;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.model.bpm.ProcessRun;
/**
 * 对象功能:友情链接 控制器类
 */
@Controller
@RequestMapping("/makshi/common/friendshipLink/")
public class FriendshipLinkController extends BaseController
{
	@Resource
	private FriendshipLinkService friendshipLinkService;
	
	/**
	 * 添加或更新友情链接。
	 * @param request
	 * @param response
	 * @param friendshipLink 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新友情链接")
	public void save(HttpServletRequest request, HttpServletResponse response,FriendshipLink friendshipLink) throws Exception
	{
		String resultMsg=null;		
		try{ 
			if(friendshipLink.getId()==null){
				friendshipLinkService.save(friendshipLink);
				resultMsg=getText("添加","友情链接");
			}else{
			    friendshipLinkService.save(friendshipLink);
				resultMsg=getText("更新","友情链接");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得友情链接分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看友情链接分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		QueryFilter query=new QueryFilter(request,"friendshipLinkItem");
		if(null==query.getFilters().get("orderField") || "".equals(query.getFilters().get("orderField"))){
			query.getFilters().put("orderField", "PRIORITY");
			query.getFilters().put("orderSeq", "asc");
		}
		List<FriendshipLink> list=friendshipLinkService.getAll(query);
		ModelAndView mv=this.getAutoView().addObject("friendshipLinkList",list);
		return mv;
	}
	

	@RequestMapping("home_list")
	public void alreadyCompletedMattersJsonList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryFilter query=new QueryFilter(request);
		if(null==query.getFilters().get("orderField") || "".equals(query.getFilters().get("orderField"))){
			query.getFilters().put("orderField", "PRIORITY");
			query.getFilters().put("orderSeq", "asc");
		}
		List<FriendshipLink> list=friendshipLinkService.getAll(query);
		JSONArray json = JSONArray.fromObject(list); 
		String resultMsg=json.toString();
		writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
	}
	
	/**
	 * 删除友情链接
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除友情链接")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"id");
			friendshipLinkService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除友情链接成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑友情链接
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑友情链接")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		
		String returnUrl=RequestUtil.getPrePage(request);
		FriendshipLink friendshipLink=friendshipLinkService.getById(id);
		
		return getAutoView().addObject("friendshipLink",friendshipLink)
							.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得友情链接明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看友情链接明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		FriendshipLink friendshipLink=friendshipLinkService.getById(id);
		return getAutoView().addObject("friendshipLink", friendshipLink);
	}
	
}