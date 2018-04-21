

package com.hotent.makshi.controller.title;

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

import com.hotent.makshi.model.title.PersonalQualificationTransfer;
import com.hotent.makshi.service.title.PersonalQualificationTransferService;
import com.hotent.core.web.ResultMessage;
/**
 * 对象功能:个人执业资格转入 控制器类
 */
@Controller
@RequestMapping("/makshi/title/personalQualificationTransfer/")
public class PersonalQualificationTransferController extends BaseController
{
	@Resource
	private PersonalQualificationTransferService personalQualificationTransferService;
	
	/**
	 * 添加或更新个人执业资格转入。
	 * @param request
	 * @param response
	 * @param personalQualificationTransfer 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新个人执业资格转入")
	public void save(HttpServletRequest request, HttpServletResponse response,PersonalQualificationTransfer personalQualificationTransfer) throws Exception
	{
		String resultMsg=null;		
		try{
			if(personalQualificationTransfer.getId()==null){
				personalQualificationTransferService.save(personalQualificationTransfer);
				resultMsg=getText("添加","个人执业资格转入");
			}else{
			    personalQualificationTransferService.save(personalQualificationTransfer);
				resultMsg=getText("更新","个人执业资格转入");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得个人执业资格转入分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看个人执业资格转入分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<PersonalQualificationTransfer> list=personalQualificationTransferService.getAll(new QueryFilter(request,"personalQualificationTransferItem"));
		ModelAndView mv=this.getAutoView().addObject("personalQualificationTransferList",list);
		return mv;
	}
	
	/**
	 * 删除个人执业资格转入
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除个人执业资格转入")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"id");
			personalQualificationTransferService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除个人执业资格转入成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑个人执业资格转入
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑个人执业资格转入")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		
		String returnUrl=RequestUtil.getPrePage(request);
		PersonalQualificationTransfer personalQualificationTransfer=personalQualificationTransferService.getById(id);
		
		return getAutoView().addObject("personalQualificationTransfer",personalQualificationTransfer)
							.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得个人执业资格转入明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看个人执业资格转入明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		PersonalQualificationTransfer personalQualificationTransfer=personalQualificationTransferService.getById(id);
		return getAutoView().addObject("personalQualificationTransfer", personalQualificationTransfer);
	}
	
}