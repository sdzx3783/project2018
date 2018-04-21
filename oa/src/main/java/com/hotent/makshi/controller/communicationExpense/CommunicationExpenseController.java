

package com.hotent.makshi.controller.communicationExpense;

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

import com.hotent.makshi.model.communicationExpense.CommunicationExpense;
import com.hotent.makshi.service.communicationExpense.CommunicationExpenseService;
import com.hotent.core.web.ResultMessage;
/**
 * 对象功能:通讯费消费记录表 控制器类
 */
@Controller
@RequestMapping("/makshi/communicationExpense/communicationExpense/")
public class CommunicationExpenseController extends BaseController
{
	@Resource
	private CommunicationExpenseService communicationExpenseService;
	
	/**
	 * 添加或更新通讯费消费记录表。
	 * @param request
	 * @param response
	 * @param communicationExpense 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新通讯费消费记录表")
	public void save(HttpServletRequest request, HttpServletResponse response,CommunicationExpense communicationExpense) throws Exception
	{
		String resultMsg=null;		
		try{
			if(communicationExpense.getId()==null){
				communicationExpenseService.save(communicationExpense);
				resultMsg=getText("添加","通讯费消费记录表");
			}else{
			    communicationExpenseService.save(communicationExpense);
				resultMsg=getText("更新","通讯费消费记录表");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得通讯费消费记录表分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看通讯费消费记录表分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<CommunicationExpense> list=communicationExpenseService.getAll(new QueryFilter(request,"communicationExpenseItem"));
		ModelAndView mv=this.getAutoView().addObject("communicationExpenseList",list);
		return mv;
	}
	
	/**
	 * 删除通讯费消费记录表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除通讯费消费记录表")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"id");
			communicationExpenseService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除通讯费消费记录表成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑通讯费消费记录表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑通讯费消费记录表")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		
		String returnUrl=RequestUtil.getPrePage(request);
		CommunicationExpense communicationExpense=communicationExpenseService.getById(id);
		
		return getAutoView().addObject("communicationExpense",communicationExpense)
							.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得通讯费消费记录表明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看通讯费消费记录表明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		CommunicationExpense communicationExpense=communicationExpenseService.getById(id);
		return getAutoView().addObject("communicationExpense", communicationExpense);
	}
	
}