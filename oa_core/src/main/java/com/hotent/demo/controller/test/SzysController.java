package com.hotent.demo.controller.test;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hotent.platform.annotion.Action;
import org.springframework.web.servlet.ModelAndView;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.model.system.SysUser;

import com.hotent.demo.model.test.Szys;
import com.hotent.demo.service.test.SzysService;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.api.util.ContextUtil;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.service.bpm.ProcessRunService;
/**
 * 对象功能:数字运算 控制器类
 */
@Controller
@RequestMapping("/demo/test/szys/")
public class SzysController extends BaseController
{
	@Resource
	private SzysService szysService;
	@Resource
	private ProcessRunService processRunService;
	
	/**
	 * 添加或更新数字运算。
	 * @param request
	 * @param response
	 * @param szys 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新数字运算")
	public void save(HttpServletRequest request, HttpServletResponse response,Szys szys) throws Exception
	{
		String resultMsg=null;		
		try{
			if(szys.getId()==null){
				szysService.save(szys);
				resultMsg=getText("添加","数字运算");
			}else{
			    szysService.save(szys);
				resultMsg=getText("更新","数字运算");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得数字运算分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看数字运算分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<Szys> list=szysService.getAll(new QueryFilter(request,"szysItem"));
		ModelAndView mv=this.getAutoView().addObject("szysList",list);
		return mv;
	}
	
	/**
	 * 删除数字运算
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除数字运算")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"id");
			szysService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除数字运算成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑数字运算
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑数字运算")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		
		Long runId=0L;
		ProcessRun processRun=processRunService.getByBusinessKey(id.toString());
		if(BeanUtils.isNotEmpty(processRun)){
			runId=processRun.getRunId();
		}
		String returnUrl=RequestUtil.getPrePage(request);
		Szys szys=szysService.getById(id);
		
		return getAutoView().addObject("szys",szys)
							.addObject("runId", runId)
							.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得数字运算明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看数字运算明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		Szys szys=szysService.getById(id);
		Long runId=0L;
		ProcessRun processRun=processRunService.getByBusinessKey(id.toString());
		if(BeanUtils.isNotEmpty(processRun)){
			runId=processRun.getRunId();
		}
		return getAutoView().addObject("szys", szys).addObject("runId", runId);
	}
	
	/**
	 * 流程url表单 绑定的表单明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("detail")
	@Action(description="表单明细")
	public ModelAndView detail(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		Szys szys = szysService.getById(id);	
		return getAutoView().addObject("szys", szys);
	}
	
	/**
	 * 流程url表单 绑定的表单编辑页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("modify")
	public ModelAndView modify(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		Szys szys = szysService.getById(id);	
		return getAutoView().addObject("szys", szys);
	}
	

    @RequestMapping("getMyTodoTask")
	@Action(description="查看数字运算任务分页列表")
	public ModelAndView getMyTodoTask(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		SysUser sysUser=(SysUser)ContextUtil.getCurrentUser();
		Long userId=sysUser.getUserId();
		List<Szys> list=szysService.getMyTodoTask(userId, new QueryFilter(request,"szysItem"));
		ModelAndView mv=this.getAutoView().addObject("szysList",list);
		return mv;
	}
	
	@RequestMapping("getMyDraft")
	@Action(description="查看数字运算草稿")
	public ModelAndView getMyDraft(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		SysUser sysUser=(SysUser)ContextUtil.getCurrentUser();
		Long userId=sysUser.getUserId();
		List<Szys> list=szysService.getMyDraft(userId, new QueryFilter(request,"szysItem"));
		ModelAndView mv=this.getAutoView().addObject("szysList",list);
		return mv;
	}
	
	@RequestMapping("getMyEnd")
	@Action(description="查看我结束的数字运算实例")
	public ModelAndView getMyEnd(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		SysUser sysUser=(SysUser)ContextUtil.getCurrentUser();
		Long userId=sysUser.getUserId();
		List<Szys> list=szysService.getMyEnd(userId, new QueryFilter(request,"szysItem"));
		ModelAndView mv=this.getAutoView().addObject("szysList",list);
		return mv;
	}
}