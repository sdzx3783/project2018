

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

import com.hotent.makshi.model.contract.Bankandaccount;
import com.hotent.makshi.service.contract.BankandaccountService;
import com.hotent.core.web.ResultMessage;
/**
 * 对象功能:银行账户表 控制器类
 */
@Controller
@RequestMapping("/makshi/contract/bankandaccount/")
public class BankandaccountController extends BaseController
{
	@Resource
	private BankandaccountService bankandaccountService;
	
	/**
	 * 添加或更新银行账户表。
	 * @param request
	 * @param response
	 * @param bankandaccount 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新银行账户表")
	public void save(HttpServletRequest request, HttpServletResponse response,Bankandaccount bankandaccount) throws Exception
	{
		String resultMsg=null;		
		try{
			if(bankandaccount.getId()==null){
				bankandaccountService.save(bankandaccount);
				resultMsg=getText("添加","银行账户表");
			}else{
			    bankandaccountService.save(bankandaccount);
				resultMsg=getText("更新","银行账户表");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得银行账户表分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看银行账户表分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<Bankandaccount> list=bankandaccountService.getAll(new QueryFilter(request,"bankandaccountItem"));
		ModelAndView mv=this.getAutoView().addObject("bankandaccountList",list);
		return mv;
	}
	
	/**
	 * 删除银行账户表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除银行账户表")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"id");
			bankandaccountService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除银行账户表成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑银行账户表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑银行账户表")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		
		String returnUrl=RequestUtil.getPrePage(request);
		Bankandaccount bankandaccount=bankandaccountService.getById(id);
		
		return getAutoView().addObject("bankandaccount",bankandaccount)
							.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得银行账户表明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看银行账户表明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		Bankandaccount bankandaccount=bankandaccountService.getById(id);
		return getAutoView().addObject("bankandaccount", bankandaccount);
	}
	
}