

package com.hotent.makshi.controller.vacation;

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
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.system.SysUserService;

import org.apache.commons.lang.exception.ExceptionUtils;

import com.hotent.core.api.org.model.ISysUser;
import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.bpm.util.BpmUtil;
import net.sf.json.JSONObject;
import com.hotent.core.util.MapUtil;

import com.hotent.makshi.model.vacation.CompanyYearVaction;
import com.hotent.makshi.service.vacation.AnuualLeaveService;
import com.hotent.makshi.service.vacation.CompanyYearVactionService;
import com.hotent.makshi.utils.DateUtils;
import com.hotent.core.web.ResultMessage;
/**
 * 对象功能:公司年假福利 控制器类
 */
@Controller
@RequestMapping("/makshi/vacation/CompanyYearVaction/")
public class CompanyYearVactionController extends BaseController
{
	@Resource
	private CompanyYearVactionService CompanyYearVactionService;
	@Resource
	private SysUserService sysUserService;
	@Resource
	private AnuualLeaveService anuualLeaveService;
	/**
	 * 添加或更新公司年假福利。
	 * @param request
	 * @param response
	 * @param CompanyYearVaction 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新公司年假福利")
	public void save(HttpServletRequest request, HttpServletResponse response,CompanyYearVaction companyYearVaction) throws Exception
	{
		String resultMsg=null;
		try{
			Date now=new Date();
			String year = DateUtils.getYear(now);
			ISysUser currentUser = ContextUtil.getCurrentUser();
			companyYearVaction.setYear(year);
			Double days = companyYearVaction.getDays();
			if(days==null){
				throw new RuntimeException("年假发放天数不能为空！");
			}
			if(days>5){
				throw new RuntimeException("年假发放天数不能超过5天！");
			}
			if(companyYearVaction.getId()==null){
				companyYearVaction.setCreatorID(currentUser.getUserId()+"");
				companyYearVaction.setCreator(currentUser.getFullname());
				companyYearVaction.setCtime(now);
				CompanyYearVaction byYear = CompanyYearVactionService.getByYear(year);
				if(byYear!=null){
					throw new RuntimeException("该年份已存在放假记录！");
				}
				CompanyYearVactionService.save(companyYearVaction);
				resultMsg=getText("添加","公司年假福利");
			}else{
				companyYearVaction.setUpdatorID(currentUser.getUserId()+"");
				companyYearVaction.setUpdator(currentUser.getFullname());
				companyYearVaction.setUtime(now);
			    CompanyYearVactionService.save(companyYearVaction);
				resultMsg=getText("更新","公司年假福利");
			}
			QueryFilter queryFilter=new QueryFilter(request, false);
			queryFilter.getFilters().clear();
			queryFilter.addFilter("excludestatus", "离职");
			List<SysUser> all = sysUserService.getAll(queryFilter);
			for (SysUser sysUser : all) {
				anuualLeaveService.calculateAnuualVacation(sysUser.getUserId());
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得公司年假福利分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看公司年假福利分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<CompanyYearVaction> list=CompanyYearVactionService.getAll(new QueryFilter(request,"CompanyYearVactionItem"));
		ModelAndView mv=this.getAutoView().addObject("CompanyYearVactionList",list);
		return mv;
	}
	
	/**
	 * 删除公司年假福利
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	/*@RequestMapping("del")
	@Action(description="删除公司年假福利")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"id");
			CompanyYearVactionService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除公司年假福利成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}*/
	
	/**
	 * 	编辑公司年假福利
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑公司年假福利")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		Date now=new Date();
		String year = DateUtils.getYear(now);
		String returnUrl=RequestUtil.getPrePage(request);
		CompanyYearVaction companyYearVaction=CompanyYearVactionService.getById(id);
		if(companyYearVaction==null){
			companyYearVaction=new CompanyYearVaction();
			companyYearVaction.setYear(year);
		}
		return getAutoView().addObject("CompanyYearVaction",companyYearVaction)
							.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得公司年假福利明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看公司年假福利明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		CompanyYearVaction CompanyYearVaction=CompanyYearVactionService.getById(id);
		return getAutoView().addObject("CompanyYearVaction", CompanyYearVaction);
	}
	
}