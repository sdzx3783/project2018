

package com.hotent.makshi.controller.hr;

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

import com.hotent.makshi.model.hr.Entry;
import com.hotent.makshi.service.hr.EntryService;
import com.hotent.makshi.model.hr.EntryJtcy;
import com.hotent.makshi.model.hr.EntryXxjl;
import com.hotent.makshi.model.hr.EntryGzjl;
import com.hotent.makshi.model.hr.EntryZyzc;
import com.hotent.makshi.model.hr.EntryZyzg;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.engine.GroovyScriptEngine;
import com.hotent.platform.service.system.IdentityService;
import com.hotent.core.util.StringUtil;
import com.hotent.core.api.org.model.ISysOrg;
import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.service.bpm.ProcessRunService;
/**
 * 对象功能:入职信息 控制器类
 */
@Controller
@RequestMapping("/makshi/hr/entry/")
public class EntryController extends BaseController
{
	@Resource
	private EntryService entryService;
	@Resource
	private GroovyScriptEngine engine;
	@Resource
	private IdentityService identityService;
	@Resource
	private ProcessRunService processRunService;
	
	/**
	 * 添加或更新入职信息。
	 * @param request
	 * @param response
	 * @param entry 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新入职信息")
	public void save(HttpServletRequest request, HttpServletResponse response,Entry entry) throws Exception
	{
		String resultMsg=null;		
		try{
			if(entry.getId()==null){
				entryService.save(entry);			
				resultMsg=getText("添加","入职信息");
			}else{
			    entryService.save(entry);
				resultMsg=getText("更新","入职信息");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得入职信息分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看入职信息分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		//List<Entry> list=entryService.getAll(new QueryFilter(request,"entryItem"));
		 Long currentOrgId = ContextUtil.getCurrentOrgId();
		 boolean isOrg = true;
		//判断是否为部门人事
		//获取部门id
		ModelAndView mv=this.getAutoView().addObject("currentOrgId",currentOrgId).addObject("isOrg",isOrg);
		return mv;
	}
	
	/**
	 * 删除入职信息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除入职信息")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"id");
			entryService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success,"删除入职信息及其从表成功!");
			entryService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除入职信息成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑入职信息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑入职信息")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		
		Long runId=0L;
		ProcessRun processRun=processRunService.getByBusinessKey(id.toString());
		if(BeanUtils.isNotEmpty(processRun)){
			runId=processRun.getRunId();
		}
		String returnUrl=RequestUtil.getPrePage(request);
		Entry entry=entryService.getById(id);
		if(BeanUtils.isEmpty(entry)){
			entry=new Entry();
			String xh_id=identityService.nextId("globalFlowNo");
			entry.setXh(xh_id);
			String ygbh_script="return scriptImpl.getCurrentUserId();";
			entry.setYgbh(Long.parseLong(engine.executeObject(ygbh_script, null).toString()));
			String xm_script="return scriptImpl.getCurrentName();";
			entry.setXm(engine.executeObject(xm_script, null).toString());
		}
		List<EntryJtcy> entryJtcyList=entryService.getEntryJtcyList(id);
		List<EntryXxjl> entryXxjlList=entryService.getEntryXxjlList(id);
		List<EntryGzjl> entryGzjlList=entryService.getEntryGzjlList(id);
		List<EntryZyzc> entryZyzcList=entryService.getEntryZyzcList(id);
		List<EntryZyzg> entryZyzgList=entryService.getEntryZyzgList(id);
		
		return getAutoView().addObject("entry",entry)
							.addObject("entryJtcyList",entryJtcyList)
							.addObject("entryXxjlList",entryXxjlList)
							.addObject("entryGzjlList",entryGzjlList)
							.addObject("entryZyzcList",entryZyzcList)
							.addObject("entryZyzgList",entryZyzgList)
							.addObject("runId", runId)
							.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得入职信息明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看入职信息明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		Entry entry=entryService.getById(id);
		Long runId=0L;
		ProcessRun processRun=processRunService.getByBusinessKey(id.toString());
		if(BeanUtils.isNotEmpty(processRun)){
			runId=processRun.getRunId();
		}
		List<EntryJtcy> entryJtcyList=entryService.getEntryJtcyList(id);
		List<EntryXxjl> entryXxjlList=entryService.getEntryXxjlList(id);
		List<EntryGzjl> entryGzjlList=entryService.getEntryGzjlList(id);
		List<EntryZyzc> entryZyzcList=entryService.getEntryZyzcList(id);
		List<EntryZyzg> entryZyzgList=entryService.getEntryZyzgList(id);
		return getAutoView().addObject("entry",entry)
							.addObject("runId", runId)
							.addObject("entryJtcyList",entryJtcyList)
							.addObject("entryXxjlList",entryXxjlList)
							.addObject("entryGzjlList",entryGzjlList)
							.addObject("entryZyzcList",entryZyzcList)
							.addObject("entryZyzgList",entryZyzgList);
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
		Entry entry = entryService.getById(id);	
		List<EntryJtcy> entryJtcyList=entryService.getEntryJtcyList(id);
		List<EntryXxjl> entryXxjlList=entryService.getEntryXxjlList(id);
		List<EntryGzjl> entryGzjlList=entryService.getEntryGzjlList(id);
		List<EntryZyzc> entryZyzcList=entryService.getEntryZyzcList(id);
		List<EntryZyzg> entryZyzgList=entryService.getEntryZyzgList(id);
		return getAutoView().addObject("entry",entry)
							.addObject("entryJtcyList",entryJtcyList)
							.addObject("entryXxjlList",entryXxjlList)
							.addObject("entryGzjlList",entryGzjlList)
							.addObject("entryZyzcList",entryZyzcList)
							.addObject("entryZyzgList",entryZyzgList);
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
		Entry entry = entryService.getById(id);	
		List<EntryJtcy> entryJtcyList=entryService.getEntryJtcyList(id);
		List<EntryXxjl> entryXxjlList=entryService.getEntryXxjlList(id);
		List<EntryGzjl> entryGzjlList=entryService.getEntryGzjlList(id);
		List<EntryZyzc> entryZyzcList=entryService.getEntryZyzcList(id);
		List<EntryZyzg> entryZyzgList=entryService.getEntryZyzgList(id);
		return getAutoView().addObject("entry",entry)
							.addObject("entryJtcyList",entryJtcyList)
							.addObject("entryXxjlList",entryXxjlList)
							.addObject("entryGzjlList",entryGzjlList)
							.addObject("entryZyzcList",entryZyzcList)
							.addObject("entryZyzgList",entryZyzgList);
	}
	
	
	
	/**
	 * 启动流程
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 由于修改了流程启动方式，不需要这个方法，因此先注释
	@RequestMapping("run")
	@Action(description="启动流程")
	public void run(HttpServletRequest request, HttpServletResponse response,Entry entry) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id",0L);
		Integer isList=RequestUtil.getInt(request, "isList",0);
		ProcessCmd processCmd=new ProcessCmd();
		//添加表单数据，当人员为流程变量的时候用到,先注释，因为会引起流程提交报错
		//if(isList==0){
		//	processCmd = BpmUtil.getProcessCmd(request);
		//}else{
		//	Entry entryTemd = entryService.getById(entry.getId()); 
		//	Map<String,Object> map = MapUtil.transBean2Map(entryTemd);
		//	JSONObject jsonObject = JSONObject.fromObject(map);
		//	String str = "{'main':{'fields':"+jsonObject+"},'sub':[],'opinion':[]}";
		//	JSONObject formData = JSONObject.fromObject(str); 
		//	processCmd.setFormData(formData.toString());
		//}
		processCmd.setFlowKey("rzlc");
		processCmd.setUserAccount(ContextUtil.getCurrentUser().getAccount());
		try {
			if(id!=0L){
				if(isList==1){
				entry=entryService.getById(id);
				}
				processCmd.setBusinessKey(id.toString());
				processRunService.startProcess(processCmd);
				entryService.save(entry);
			}else{
				Long genId=UniqueIdUtil.genId();
				processCmd.setBusinessKey(genId.toString());
				entry.setId(genId);
				processRunService.startProcess(processCmd);
				entryService.save(entry);
			}
			writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.Success, "启动流程成功"));
		} catch (Exception e) {
			log.error("错误信息",e);
			writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.Fail, ExceptionUtils.getRootCauseMessage(e)));
		}
	}*/


    @RequestMapping("getMyTodoTask")
	@Action(description="查看入职信息任务分页列表")
	public ModelAndView getMyTodoTask(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		SysUser sysUser=(SysUser)ContextUtil.getCurrentUser();
		Long userId=sysUser.getUserId();
		List<Entry> list=entryService.getMyTodoTask(userId, new QueryFilter(request,"entryItem"));
		ModelAndView mv=this.getAutoView().addObject("entryList",list);
		return mv;
	}
	
	@RequestMapping("getMyDraft")
	@Action(description="查看入职信息草稿")
	public ModelAndView getMyDraft(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		SysUser sysUser=(SysUser)ContextUtil.getCurrentUser();
		Long userId=sysUser.getUserId();
		List<Entry> list=entryService.getMyDraft(userId, new QueryFilter(request,"entryItem"));
		ModelAndView mv=this.getAutoView().addObject("entryList",list);
		return mv;
	}
	
	@RequestMapping("getMyEnd")
	@Action(description="查看我结束的入职信息实例")
	public ModelAndView getMyEnd(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		SysUser sysUser=(SysUser)ContextUtil.getCurrentUser();
		Long userId=sysUser.getUserId();
		List<Entry> list=entryService.getMyEnd(userId, new QueryFilter(request,"entryItem"));
		ModelAndView mv=this.getAutoView().addObject("entryList",list);
		return mv;
	}
}