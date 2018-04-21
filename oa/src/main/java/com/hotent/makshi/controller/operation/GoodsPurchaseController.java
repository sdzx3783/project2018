

package com.hotent.makshi.controller.operation;

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
import com.hotent.makshi.model.operation.GoodsPurchase;
import com.hotent.makshi.model.operation.MonthlyManage;
import com.hotent.makshi.model.operation.Purchaselists;
import com.hotent.makshi.service.operation.GoodsPurchaseService;
import com.hotent.makshi.service.operation.OperationWeeklyManageService;
import com.hotent.platform.annotion.Action;
/**
 * 对象功能:物品采购申请 控制器类
 */
@Controller
@RequestMapping("/makshi/operation/goodsPurchase/")
public class GoodsPurchaseController extends BaseController
{
	@Resource
	private GoodsPurchaseService goodsPurchaseService;
	@Resource
	private OperationWeeklyManageService operationWeeklyManageService;
	/**
	 * 添加或更新物品采购申请。
	 * @param request
	 * @param response
	 * @param goodsPurchase 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新物品采购申请")
	public void save(HttpServletRequest request, HttpServletResponse response,GoodsPurchase goodsPurchase) throws Exception
	{
		String resultMsg=null;		
		try{
			if(goodsPurchase.getId()==null){
				goodsPurchaseService.save(goodsPurchase);			
				resultMsg=getText("添加","物品采购申请");
			}else{
			    goodsPurchaseService.save(goodsPurchase);
				resultMsg=getText("更新","物品采购申请");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得物品采购申请分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看物品采购申请分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		QueryFilter fiter = new QueryFilter(request,"goodsPurchaseItem");
		Boolean isEditer = false;
		AccessExamine accessExamine = operationWeeklyManageService.examineIsEditer(isEditer,fiter,3);
		List<GoodsPurchase> list=goodsPurchaseService.getAll(accessExamine.getFiter());
		ModelAndView mv=this.getAutoView().addObject("goodsPurchaseList",list).addObject("isEditer",accessExamine.getIsEditer());
		return mv;
	}
	
	/**
	 * 删除物品采购申请
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除物品采购申请")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"id");
			goodsPurchaseService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success,"删除物品采购申请及其从表成功!");
			goodsPurchaseService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除物品采购申请成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑物品采购申请
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑物品采购申请")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		
		String returnUrl=RequestUtil.getPrePage(request);
		GoodsPurchase goodsPurchase=goodsPurchaseService.getById(id);
		List<Purchaselists> purchaselistsList=goodsPurchaseService.getPurchaselistsList(id);
		
		return getAutoView().addObject("goodsPurchase",goodsPurchase)
							.addObject("purchaselistsList",purchaselistsList)
							.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得物品采购申请明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看物品采购申请明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		GoodsPurchase goodsPurchase=goodsPurchaseService.getById(id);
		List<Purchaselists> purchaselistsList=goodsPurchaseService.getPurchaselistsList(id);
		return getAutoView().addObject("goodsPurchase",goodsPurchase)
							.addObject("purchaselistsList",purchaselistsList);
	}
	
}