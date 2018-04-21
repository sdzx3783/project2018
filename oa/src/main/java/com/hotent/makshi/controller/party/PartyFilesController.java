

package com.hotent.makshi.controller.party;

import java.util.Date;
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
import com.hotent.makshi.model.party.PartyFiles;
import com.hotent.makshi.model.userinfo.UserInfomation;
import com.hotent.makshi.service.party.PartyFilesService;
import com.hotent.makshi.service.userinfo.UserInfomationService;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.system.SysUserService;
/**
 * 对象功能:党员档案 控制器类
 */
@Controller
@RequestMapping("/makshi/party/partyFiles/")
public class PartyFilesController extends BaseController
{
	@Resource
	private PartyFilesService partyFilesService;
	@Resource
	private UserInfomationService userInfomationService;
	@Resource
	private SysUserService userService;
	
	/**
	 * 添加或更新党员档案。
	 * @param request
	 * @param response
	 * @param partyFiles 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新党员档案")
	public void save(HttpServletRequest request, HttpServletResponse response,PartyFiles partyFiles) throws Exception
	{
		String resultMsg=null;		
		try{
			
			String account = partyFiles.getAccount();
			SysUser user = userService.getByAccount(account);
			if(user==null){
				resultMsg=getText("员工编号错误,没有该员工编号","员工编号错误,没有该员工编号");
				writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Fail);
				return;
			}else{
				UserInfomation userInfomation = userInfomationService.getUserInfomationByUid(user.getUserId());
				if(userInfomation==null){
					userInfomation=new UserInfomation();
					userInfomation.setUserId(user.getUserId());
				}
				userInfomation.setPolitical_status("党员");
				userInfomationService.save(userInfomation);
			}
			
			SysUser curUser = (SysUser) ContextUtil.getCurrentUser();
			if(partyFiles.getId()==null){
				partyFiles.setCreate_time(new Date());
				partyFiles.setCreator(curUser.getFullname());
				partyFilesService.save(partyFiles);
				resultMsg=getText("添加","党员档案");
			}else{
				partyFiles.setUpdate_time(new Date());
				partyFiles.setUpdater(curUser.getFullname());
			    partyFilesService.save(partyFiles);
				resultMsg=getText("更新","党员档案");
			}
			
			
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
			
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得党员档案分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看党员档案分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<PartyFiles> list=partyFilesService.getAll(new QueryFilter(request,"partyFilesItem"));
		ModelAndView mv=this.getAutoView().addObject("partyFilesList",list);
		return mv;
	}
	
	/**
	 * 删除党员档案
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除党员档案")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"id");
			
			for(int i = 0; i < lAryId.length; i++){
				 PartyFiles partyFiles = partyFilesService.getById(lAryId[i]);
				 String account = partyFiles.getAccount();
				 SysUser user = userService.getByAccount(account);
				 if(user!=null){
					 UserInfomation userInfomation = userInfomationService.getUserInfomationByUid(user.getUserId());
					 if(userInfomation!=null){
						 userInfomation.setPolitical_status("");
						 userInfomationService.update(userInfomation);
					 }
				 }
				
				 
	        }
			
			
			
			partyFilesService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除党员档案成功!");
			
			
			
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		 
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑党员档案
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑党员档案")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		
		String returnUrl=RequestUtil.getPrePage(request);
		PartyFiles partyFiles=partyFilesService.getById(id);
		
		return getAutoView().addObject("partyFiles",partyFiles)
							.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得党员档案明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看党员档案明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		PartyFiles partyFiles=partyFilesService.getById(id);
		return getAutoView().addObject("partyFiles", partyFiles);
	}
	
}