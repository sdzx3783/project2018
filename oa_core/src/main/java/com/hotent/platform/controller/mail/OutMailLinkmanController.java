package com.hotent.platform.controller.mail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.stat.TableStat.Condition;
import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.model.mail.OutMail;
import com.hotent.platform.model.mail.OutMailLinkman;
import com.hotent.platform.model.oa.OaLinkman;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.mail.OutMailLinkmanService;
import com.hotent.platform.service.mail.OutMailService;
import com.hotent.platform.service.oa.OaLinkmanService;
import com.hotent.platform.service.system.SysUserService;
/**
 * 对象功能:最近联系人 控制器类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:zyp
 * 创建时间:2012-04-13 11:11:56
 */
@Controller
@RequestMapping("/platform/mail/outMailLinkman/")
public class OutMailLinkmanController extends BaseController
{
	@Resource
	private OutMailLinkmanService outMailLinkmanService;
	@Resource
	private OaLinkmanService oaLinkmanService;
	@Resource
	private SysUserService sysUserService;
	@Resource
	private OutMailService outMailService;
	/**
	 * 最近联系人树形列表的json数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getOutMailLinkmanData")
	@ResponseBody
	public List<OutMailLinkman> getOutMailLinkmanData(HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		Long userId = ContextUtil.getCurrentUserId();
		String condition = request.getParameter("condition");
		List<OutMailLinkman> outMailLinkmans=outMailLinkmanService.getAllByUserId(userId,condition);
		List<OutMailLinkman> outMailLinkmanList = new ArrayList<OutMailLinkman>();
		for(OutMailLinkman man :outMailLinkmans){
			String linkName = outMailService.getNameByEmail(man.getLinkAddress());//查看联系人
			man.setLinkAddress(linkName+"("+man.getLinkAddress()+")");
			man.setLinkName(linkName);
			outMailLinkmanList.add(man);
		}
		return outMailLinkmanList;
	}
	
	/**
	 * 删除联系人
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除最近联系人")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "id");
			outMailLinkmanService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除联系人成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		writeResultMessage(response.getWriter(), message);
	}
}
