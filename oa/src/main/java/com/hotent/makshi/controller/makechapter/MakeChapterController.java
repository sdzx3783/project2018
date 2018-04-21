

package com.hotent.makshi.controller.makechapter;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.engine.GroovyScriptEngine;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.makshi.model.makechapter.MakeChapter;
import com.hotent.makshi.service.makechapter.MakeChapterService;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.platform.service.system.SysUserService;

import net.sf.json.JSONObject;
/**
 * 对象功能:印章表 控制器类
 */
@Controller
@RequestMapping("/makshi/makechapter/makeChapter/")
public class MakeChapterController extends BaseController
{
	@Resource
	private MakeChapterService makeChapterService;
	@Resource
	private GroovyScriptEngine engine;
	@Resource
	private ProcessRunService processRunService;

	@Resource
	private SysUserService sysUserService;
	
	/**
	 * 添加或更新印章表。
	 * @param request
	 * @param response
	 * @param makeChapter 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新印章表")
	public void save(HttpServletRequest request, HttpServletResponse response,MakeChapter makeChapter) throws Exception
	{
		String resultMsg=null;		
		try{
			if(makeChapter.getId()==null){
				makeChapterService.save(makeChapter);
				resultMsg=getText("添加","印章表");
			}else{
			    makeChapterService.save(makeChapter);
				resultMsg=getText("更新","印章表");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得印章表分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看印章表分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<MakeChapter> list=makeChapterService.getAll(new QueryFilter(request,"makeChapterItem"));
		ModelAndView mv=this.getAutoView().addObject("makeChapterList",list);
		return mv;
	}
	/**
	 * 删除印章表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除印章表")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"id");
			makeChapterService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除印章表成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑印章表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑印章表")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		
		Long runId=0L;
		ProcessRun processRun=processRunService.getByBusinessKey(id.toString());
		if(BeanUtils.isNotEmpty(processRun)){
			runId=processRun.getRunId();
		}
		String returnUrl=RequestUtil.getPrePage(request);
		MakeChapter makeChapter=makeChapterService.getById(id);
		if(BeanUtils.isEmpty(makeChapter)){
			makeChapter=new MakeChapter();
			String application_person_script="return scriptImpl.getCurrentName();";
			makeChapter.setApplication_person(engine.executeObject(application_person_script, null).toString());
		}
		
		return getAutoView().addObject("makeChapter",makeChapter)
							.addObject("runId", runId)
							.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得印章表明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看印章表明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		MakeChapter makeChapter=makeChapterService.getById(id);
		Long runId=0L;
		ProcessRun processRun=processRunService.getByBusinessKey(id.toString());
		if(BeanUtils.isNotEmpty(processRun)){
			runId=processRun.getRunId();
		}
		ModelAndView mv=getAutoView().addObject("makeChapter", makeChapter).addObject("runId", runId);
		return getAutoView().addObject("makeChapter", makeChapter).addObject("runId", runId);
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
		MakeChapter makeChapter = makeChapterService.getById(id);	
		return getAutoView().addObject("makeChapter", makeChapter);
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
		MakeChapter makeChapter = makeChapterService.getById(id);	
		return getAutoView().addObject("makeChapter", makeChapter);
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
	public void run(HttpServletRequest request, HttpServletResponse response,MakeChapter makeChapter) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id",0L);
		Integer isList=RequestUtil.getInt(request, "isList",0);
		ProcessCmd processCmd=new ProcessCmd();
		//添加表单数据，当人员为流程变量的时候用到,先注释，因为会引起流程提交报错
		//if(isList==0){
		//	processCmd = BpmUtil.getProcessCmd(request);
		//}else{
		//	MakeChapter makeChapterTemd = makeChapterService.getById(makeChapter.getId()); 
		//	Map<String,Object> map = MapUtil.transBean2Map(makeChapterTemd);
		//	JSONObject jsonObject = JSONObject.fromObject(map);
		//	String str = "{'main':{'fields':"+jsonObject+"},'sub':[],'opinion':[]}";
		//	JSONObject formData = JSONObject.fromObject(str); 
		//	processCmd.setFormData(formData.toString());
		//}
		processCmd.setFlowKey("make_chapter");
		processCmd.setUserAccount(ContextUtil.getCurrentUser().getAccount());
		try {
			if(id!=0L){
				if(isList==1){
				makeChapter=makeChapterService.getById(id);
				}
				processCmd.setBusinessKey(id.toString());
				processRunService.startProcess(processCmd);
				makeChapterService.save(makeChapter);
			}else{
				Long genId=UniqueIdUtil.genId();
				processCmd.setBusinessKey(genId.toString());
				makeChapter.setId(genId);
				processRunService.startProcess(processCmd);
				makeChapterService.save(makeChapter);
			}
			writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.Success, "启动流程成功"));
		} catch (Exception e) {
			log.error("错误信息",e);
			writeResultMessage(response.getWriter(), new ResultMessage(ResultMessage.Fail, ExceptionUtils.getRootCauseMessage(e)));
		}
	}*/


    @RequestMapping("getMyTodoTask")
	@Action(description="查看印章表任务分页列表")
	public ModelAndView getMyTodoTask(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		SysUser sysUser=(SysUser)ContextUtil.getCurrentUser();
		Long userId=sysUser.getUserId();
		List<MakeChapter> list=makeChapterService.getMyTodoTask(userId, new QueryFilter(request,"makeChapterItem"));
		ModelAndView mv=this.getAutoView().addObject("makeChapterList",list);
		return mv;
	}
	
	@RequestMapping("getMyDraft")
	@Action(description="查看印章表草稿")
	public ModelAndView getMyDraft(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		SysUser sysUser=(SysUser)ContextUtil.getCurrentUser();
		Long userId=sysUser.getUserId();
		List<MakeChapter> list=makeChapterService.getMyDraft(userId, new QueryFilter(request,"makeChapterItem"));
		ModelAndView mv=this.getAutoView().addObject("makeChapterList",list);
		return mv;
	}
	
	@RequestMapping("getMyEnd")
	@Action(description="查看我结束的印章表实例")
	public ModelAndView getMyEnd(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		SysUser sysUser=(SysUser)ContextUtil.getCurrentUser();
		Long userId=sysUser.getUserId();
		List<MakeChapter> list=makeChapterService.getMyEnd(userId, new QueryFilter(request,"makeChapterItem"));
		ModelAndView mv=this.getAutoView().addObject("makeChapterList",list);
		return mv;
	}

	/**
	 * 编辑印章的保管人和印章图片
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("newedit")
	@Action(description="查看印章表明细")
	public ModelAndView newedit(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		MakeChapter makeChapter=makeChapterService.getById(id);
		Long runId=0L;
		ProcessRun processRun=processRunService.getByBusinessKey(id.toString());
		if(BeanUtils.isNotEmpty(processRun)){
			runId=processRun.getRunId();
		}

		return getAutoView().addObject("makeChapter", makeChapter).addObject("runId", runId);
	}

    /**
     * 更新印章，只更新相应的值。因为map.xml未做空值判断，所以在这里设置，非空的值才做更新。
     * @param makeChapter
     * @return 1成功 ，其他不成功
     * @throws Exception
     */
    @RequestMapping("newupdate")
    @ResponseBody
    public JSONObject newUpdate(MakeChapter makeChapter) throws Exception {
        JSONObject json = new JSONObject();
        json.put("code", 1);

        if(makeChapter == null) {
            json.put("code", -1);
            return json;
        }

        MakeChapter mcOld = makeChapterService.getById(makeChapter.getId());
        if(mcOld == null) {
            json.put("code", -2);
            return json;
        }

        if(mcOld.getIfSealDel() == 1) {
            json.put("code", 0);
            return json;
        }

        //只更新需要更新设置的值
        //根据领取人ID
        if(StringUtils.isNotEmpty(makeChapter.getTake_personID())) {
            SysUser user = sysUserService.getById(Long.parseLong(makeChapter.getTake_personID()));

            if(user == null) {
                logger.info("make chater newupdate:user not exist!" + makeChapter.getTake_personID());
                json.put("code", -1);
                return json;
            }

            mcOld.setTake_person(user.getFullname());
            mcOld.setTake_personID(user.getUserId() + "");
        }
//        if(makeChapter.getIfSealDel() != null) {
//            mcOld.setIfSealDel(makeChapter.getIfSealDel());
//        }
        //印章图片
        if(StringUtils.isNotEmpty(makeChapter.getSealImg())) {
            mcOld.setSealImg(makeChapter.getSealImg());
        }

        makeChapterService.update(mcOld);

        logger.info("suc to newupdate makechapter ");
        return json;
    }


	/**
	 * 校验要申请注销的印章是否合法
	 * @param request
	 * @return
	 */
	@RequestMapping("isvalid")
	@ResponseBody
	public String isValid(HttpServletRequest request){
		JSONObject json = new JSONObject();
		json.put("code", 1);

        Long id = RequestUtil.getLong(request,"theSealId");
		MakeChapter makeChapter=makeChapterService.getById(id);

		if(makeChapter == null) {
			json.put("msg", "申请注销的印章不存在，请检查。");
			json.put("code", 0);
			return json.toString();
		}

		SysUser sysUser=(SysUser)ContextUtil.getCurrentUser();
		Long userId=sysUser.getUserId();
		String fName = sysUser.getFullname();
		//当前用户是否有权限
		if ((StringUtils.isNotEmpty(makeChapter.getTake_personID()) && !(userId + "").equals(makeChapter.getTake_personID()))
				|| (StringUtils.isEmpty(makeChapter.getTake_personID()) && StringUtils.isNotEmpty(makeChapter.getApplication_person()) && !fName.equals(makeChapter.getApplication_person())))
		{
			json.put("msg", "当前用户没有该印章的注销的权限，请检查。");
			json.put("code", 0);
			return json.toString();
		}

		if(makeChapter.getIfSealDel() == 1) {
			json.put("msg", "该印章已注销，不能重复申请注销，请检查。");
			json.put("code", 0);
			return json.toString();
		}

		return json.toString();
	}
}