

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

import com.hotent.makshi.model.contract.ContractPaymentApplication;
import com.hotent.makshi.service.contract.ContractPaymentApplicationService;
import com.hotent.makshi.model.contract.InvoiceDetail;
import com.hotent.core.web.ResultMessage;
/**
 * 对象功能:合同付款申请 控制器类
 */
@Controller
@RequestMapping("/makshi/contract/contractPaymentApplication/")
public class ContractPaymentApplicationController extends BaseController
{
	@Resource
	private ContractPaymentApplicationService contractPaymentApplicationService;
	
	/**
	 * 添加或更新合同付款申请。
	 * @param request
	 * @param response
	 * @param contractPaymentApplication 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新合同付款申请")
	public void save(HttpServletRequest request, HttpServletResponse response,ContractPaymentApplication contractPaymentApplication) throws Exception
	{
		String resultMsg=null;		
		try{
			if(contractPaymentApplication.getId()==null){
				contractPaymentApplicationService.save(contractPaymentApplication);			
				resultMsg=getText("添加","合同付款申请");
			}else{
			    contractPaymentApplicationService.save(contractPaymentApplication);
				resultMsg=getText("更新","合同付款申请");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得合同付款申请分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看合同付款申请分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<ContractPaymentApplication> list=contractPaymentApplicationService.getAll(new QueryFilter(request,"contractPaymentApplicationItem"));
		ModelAndView mv=this.getAutoView().addObject("contractPaymentApplicationList",list);
		return mv;
	}
	
	/**
	 * 删除合同付款申请
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除合同付款申请")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"id");
			contractPaymentApplicationService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success,"删除合同付款申请及其从表成功!");
			contractPaymentApplicationService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除合同付款申请成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑合同付款申请
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑合同付款申请")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		
		String returnUrl=RequestUtil.getPrePage(request);
		ContractPaymentApplication contractPaymentApplication=contractPaymentApplicationService.getById(id);
		List<InvoiceDetail> invoiceDetailList=contractPaymentApplicationService.getInvoiceDetailList(id);
		
		return getAutoView().addObject("contractPaymentApplication",contractPaymentApplication)
							.addObject("invoiceDetailList",invoiceDetailList)
							.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得合同付款申请明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看合同付款申请明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		ContractPaymentApplication contractPaymentApplication=contractPaymentApplicationService.getById(id);
		List<InvoiceDetail> invoiceDetailList=contractPaymentApplicationService.getInvoiceDetailList(id);
		return getAutoView().addObject("contractPaymentApplication",contractPaymentApplication)
							.addObject("invoiceDetailList",invoiceDetailList);
	}
	
}