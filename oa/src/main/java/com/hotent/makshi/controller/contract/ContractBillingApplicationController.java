

package com.hotent.makshi.controller.contract;

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

import com.hotent.makshi.model.contract.ContractBillingApplication;
import com.hotent.makshi.service.contract.ContractBillingApplicationService;
import com.hotent.core.web.ResultMessage;
/**
 * 对象功能:合同开票申请 控制器类
 */
@Controller
@RequestMapping("/makshi/contract/contractBillingApplication/")
public class ContractBillingApplicationController extends BaseController
{
	@Resource
	private ContractBillingApplicationService contractBillingApplicationService;
	
	/**
	 * 添加或更新合同开票申请。
	 * @param request
	 * @param response
	 * @param contractBillingApplication 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新合同开票申请")
	public void save(HttpServletRequest request, HttpServletResponse response,ContractBillingApplication contractBillingApplication) throws Exception
	{
		String resultMsg=null;		
		try{
			if(contractBillingApplication.getId()==null){
				contractBillingApplicationService.save(contractBillingApplication);
				resultMsg=getText("添加","合同开票申请");
			}else{
			    contractBillingApplicationService.save(contractBillingApplication);
				resultMsg=getText("更新","合同开票申请");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得合同开票申请分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看合同开票申请分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<ContractBillingApplication> list=contractBillingApplicationService.getAll(new QueryFilter(request,"contractBillingApplicationItem"));
		ModelAndView mv=this.getAutoView().addObject("contractBillingApplicationList",list);
		return mv;
	}
	
	/**
	 * 删除合同开票申请
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除合同开票申请")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"id");
			contractBillingApplicationService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除合同开票申请成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑合同开票申请
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑合同开票申请")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		
		String returnUrl=RequestUtil.getPrePage(request);
		ContractBillingApplication contractBillingApplication=contractBillingApplicationService.getById(id);
		
		return getAutoView().addObject("contractBillingApplication",contractBillingApplication)
							.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得合同开票申请明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看合同开票申请明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		ContractBillingApplication contractBillingApplication=contractBillingApplicationService.getById(id);
		return getAutoView().addObject("contractBillingApplication", contractBillingApplication);
	}
	
}