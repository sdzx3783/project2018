package com.hotent.platform.controller.system;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.api.util.PropertyUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.dao.system.SysUserDao;
import com.hotent.platform.model.system.SysBulletin;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.model.system.SysPropertyConstants;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.system.SysBulletinColumnService;
import com.hotent.platform.service.system.SysBulletinService;
import com.hotent.platform.service.system.SysOrgService;
import com.hotent.weixin.model.msg.impl.Article;
import com.hotent.weixin.model.msg.impl.NewsMessage;
import com.hotent.weixin.util.WeiXinUtil;

/**
 * 对象功能:公告表 控制器类
 */
@Controller
@RequestMapping("/platform/system/sysBulletin/")
public class SysBulletinFormController extends BaseController {
	@Resource
	private SysBulletinService sysBulletinService;
	@Resource
	private SysBulletinColumnService sysBulletinColumnService;
	@Resource
	private SysOrgService sysOrgService;
	@Resource
	private SysUserDao sysUserDao;
	
	
	/**
	 * 添加或更新可访问地址。
	 * @param request
	 * @param response
	 * @param sysAcceptIp 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description = "添加或更新公告表")
	public void save(HttpServletRequest request, HttpServletResponse response,
			SysBulletin sysBulletin) throws Exception {
		String resultMsg = null;
		try {
			if (sysBulletin.getId() == null) {
				SysUser sysUser = (SysUser) ContextUtil.getCurrentUser();
				sysBulletin.setCreatorid(sysUser.getUserId());
				sysBulletin.setCreator(sysUser.getFullname());
				sysBulletin.setId(UniqueIdUtil.genId());
				sysBulletinService.add(sysBulletin);
				resultMsg = getText("添加成功", "公告表");
			} else {
				sysBulletinService.update(sysBulletin);
				resultMsg = getText("更新成功", "公告表");
			}
			//发送微信通知消息
			sendWxInfo(sysBulletin, request);
			
			writeResultMessage(response.getWriter(), resultMsg,
					ResultMessage.Success);
		} catch (Exception e) {
			e.printStackTrace();
			writeResultMessage(response.getWriter(),
					resultMsg + "," + e.getMessage(), ResultMessage.Fail);
		}
	}
	
	/**
	 * 构建微信图文消息。
	 * @param sysBulletin
	 * @param request
	 * @return
	 */
	private NewsMessage getNewsMessage(SysBulletin sysBulletin,HttpServletRequest request){
		String serverUrl=PropertyUtil.getByAlias("serverUrl");
		String url=serverUrl +"/weixin/bulletin/detail.html?" + sysBulletin.getId();
		
		String redirect=serverUrl +"/proxy?redirect=" +url;
		
		Article article=new Article();
		article.setTitle(sysBulletin.getSubject());
		article.setUrl(redirect);
		
		String fileId="";
		//公告图片不为空
		if(StringUtil.isNotEmpty(sysBulletin.getImgUrl())){
			fileId=sysBulletin.getImgUrl();
		}
		//公告图片为空，再尝试取栏目的图片。
		else{
			Long columnId=sysBulletin.getColumnid();
			String imgUrl= sysBulletinColumnService.getById(columnId).getImgUrl();
			if(StringUtil.isNotEmpty(imgUrl)){
				fileId=imgUrl;
			}
		}
		if(StringUtil.isNotEmpty(fileId)){
			String picUrl=serverUrl +"/platform/system/sysFile/getFileById.ht?fileId="+fileId;
			article.setPicurl(picUrl);
		}
		
		
		NewsMessage message=new NewsMessage();
		message.addArticle(article);
		
		
		//如果是超管或 没有分公司的情况
		Long companyId=ContextUtil.getCurrentCompanyId();
		if(ContextUtil.isSuperAdmin() ||  companyId.equals(0L) ){
			//是否是选择人
			int selectUser=RequestUtil.getInt(request, "selectUser", 0);
			if(selectUser==0){
				message.setTouser("@all");
			}
			else{
				String[] accounts=request.getParameterValues("account");
				if(BeanUtils.isEmpty(accounts)) return null;
				String users=StringUtil.join(accounts, "|");
				message.setTouser(users);
			}
		}
		else{
			//根据公司获取微信联系人
			SysOrg sysOrg=sysOrgService.getById(companyId);
			List<SysUser> list= sysUserDao.getWxUserByPath(sysOrg.getPath() +"%");
			if(BeanUtils.isEmpty(list)) return null;
			int i=0;
			String users="";
			for(SysUser sysUser:list){
				if(i==0){
					users+=sysUser.getAccount();
				}
				else{
					users+="|" +sysUser.getAccount();
				}
				i++;
			}
			message.setTouser(users);
		}
		return message;
	}
	
	/**
	 * 
	 * @param sysBulletin
	 * @param request
	 */
	private void sendWxInfo(SysBulletin sysBulletin,HttpServletRequest request){
		boolean isSupportWeixin = PropertyUtil.getBooleanByAlias(SysPropertyConstants.WX_IS_SUPPORT,false);
		if(!isSupportWeixin) return;
		int sendWxInfo=RequestUtil.getInt(request, "sendWxInfo", 0);
		if(sendWxInfo==0) return;
		
		NewsMessage message=getNewsMessage(sysBulletin, request);
		if(message==null) return;
		
		WeiXinUtil.sendNewsMessage(message);
	}
	
	/**
	 * 在实体对象进行封装前，从对应源获取原实体
	 * @param acceptId
	 * @param model
	 * @return
	 * @throws Exception
	 */
    @ModelAttribute
    protected SysBulletin getFormObject(@RequestParam("id") Long id,Model model) throws Exception {
		logger.debug("enter SysBulletin getFormObject here....");
		SysBulletin sysBulletin=null;
		if(id!=null){
			sysBulletin=sysBulletinService.getById(id);
		}else{
			sysBulletin= new SysBulletin();
		}
		return sysBulletin;
    }

	
}