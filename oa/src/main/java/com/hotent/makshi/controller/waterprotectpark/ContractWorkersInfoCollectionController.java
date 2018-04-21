package com.hotent.makshi.controller.waterprotectpark;

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
import com.hotent.makshi.model.waterprotectpark.ContractWorkersInfoCollection;
import com.hotent.makshi.service.waterprotectpark.ContractWorkersInfoCollectionService;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.model.system.SysRole;
import com.hotent.platform.service.system.SysRoleService;
/**
 * 对象功能:劳务人员信息采集(水保示范园流程) 控制器类
 */
@Controller
@RequestMapping("/makshi/waterprotectpark/contractWorkersInfoCollection/")
public class ContractWorkersInfoCollectionController extends BaseController
{
	@Resource
	private ContractWorkersInfoCollectionService contractWorkersInfoCollectionService;
	@Resource
	private SysRoleService sysRoleService;
	/**
	 * 添加或更新劳务人员信息采集(水保示范园流程)。
	 * @param request
	 * @param response
	 * @param contractWorkersInfoCollection 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新劳务人员信息采集(水保示范园流程)")
	public void save(HttpServletRequest request, HttpServletResponse response,ContractWorkersInfoCollection contractWorkersInfoCollection) throws Exception
	{
		String resultMsg=null;		
		try{
			if(contractWorkersInfoCollection.getId()==null){
				contractWorkersInfoCollection.setStatus(1);
				contractWorkersInfoCollection.setIsfinished(1);
				contractWorkersInfoCollectionService.save(contractWorkersInfoCollection);
				resultMsg=getText("添加","劳务人员信息采集");
			}else{
			    contractWorkersInfoCollectionService.save(contractWorkersInfoCollection);
				resultMsg=getText("更新","劳务人员信息采集");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得劳务人员信息采集(水保示范园流程)分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看劳务人员信息采集分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<ContractWorkersInfoCollection> list=contractWorkersInfoCollectionService.getAll(new QueryFilter(request,"contractWorkersInfoCollectionItem"));
		ModelAndView mv=this.getAutoView().addObject("contractWorkersInfoCollectionList",list);
		Long currentUserId = ContextUtil.getCurrentUserId();
		Boolean hasAccess=false;
		if(currentUserId==1l){
			hasAccess=true;
		}else{
			List<SysRole> roles = sysRoleService.getByUserId(currentUserId);
			for (SysRole sysRole : roles) {
				if((sysRole.getRoleId()+"").equals("10000013290005")){//行政管理员——水保部
					hasAccess=true;
					break;
				}
			}
		}
		return mv.addObject("hasAccess", hasAccess);
	}
	
	/**
	 * 删除劳务人员信息采集(水保示范园流程)
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除劳务人员信息采集")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"id");
			contractWorkersInfoCollectionService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除劳务人员信息采集成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑劳务人员信息采集(水保示范园流程)
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑劳务人员信息采集")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		
		String returnUrl=RequestUtil.getPrePage(request);
		ContractWorkersInfoCollection contractWorkersInfoCollection=contractWorkersInfoCollectionService.getById(id);
		if(contractWorkersInfoCollection==null){
			contractWorkersInfoCollection=new ContractWorkersInfoCollection();
			contractWorkersInfoCollection.setRegisterer(ContextUtil.getCurrentUser().getFullname());
			contractWorkersInfoCollection.setRegistererID(ContextUtil.getCurrentUserId().toString());
		}
		return getAutoView().addObject("contractWorkersInfoCollection",contractWorkersInfoCollection)
							.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得劳务人员信息采集(水保示范园流程)明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看劳务人员信息采集明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		ContractWorkersInfoCollection contractWorkersInfoCollection=contractWorkersInfoCollectionService.getById(id);
		return getAutoView().addObject("contractWorkersInfoCollection", contractWorkersInfoCollection);
	}
	
}