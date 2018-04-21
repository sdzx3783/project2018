

package com.hotent.makshi.controller.operation;

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

import com.hotent.makshi.model.operation.TestReport;
import com.hotent.makshi.service.operation.TestReportService;
import com.hotent.core.web.ResultMessage;
/**
 * 对象功能:运维部检测报告表 控制器类
 */
@Controller
@RequestMapping("/makshi/operation/testReport/")
public class TestReportController extends BaseController
{
	@Resource
	private TestReportService testReportService;
	
	/**
	 * 添加或更新运维部检测报告表。
	 * @param request
	 * @param response
	 * @param testReport 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新运维部检测报告表")
	public void save(HttpServletRequest request, HttpServletResponse response,TestReport testReport) throws Exception
	{
		String resultMsg=null;		
		try{
			if(testReport.getId()==null){
				testReportService.save(testReport);
				resultMsg=getText("添加","运维部检测报告表");
			}else{
			    testReportService.save(testReport);
				resultMsg=getText("更新","运维部检测报告表");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得运维部检测报告表分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看运维部检测报告表分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<TestReport> list=testReportService.getAll(new QueryFilter(request,"testReportItem"));
		ModelAndView mv=this.getAutoView().addObject("testReportList",list);
		return mv;
	}
	
	/**
	 * 删除运维部检测报告表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除运维部检测报告表")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"id");
			testReportService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除运维部检测报告表成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑运维部检测报告表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑运维部检测报告表")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		
		String returnUrl=RequestUtil.getPrePage(request);
		TestReport testReport=testReportService.getById(id);
		
		return getAutoView().addObject("testReport",testReport)
							.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得运维部检测报告表明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看运维部检测报告表明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		TestReport testReport=testReportService.getById(id);
		return getAutoView().addObject("testReport", testReport);
	}
	
}