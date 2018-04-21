package com.hotent.platform.service.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.hotent.core.api.org.ICurrentContext;
import com.hotent.core.api.org.model.IPosition;
import com.hotent.core.api.org.model.ISysOrg;
import com.hotent.core.api.org.model.ISysUser;
import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.bpm.cache.ActivitiDefCache;
import com.hotent.core.cache.ICache;
import com.hotent.core.db.datasource.DbContextHolder;
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.StringUtil;
import com.hotent.core.web.util.RequestContext;
import com.hotent.platform.model.system.Position;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.model.system.SystemConst;
import com.hotent.platform.service.bpm.thread.MessageUtil;
import com.hotent.platform.service.bpm.thread.TaskThreadService;
import com.hotent.platform.service.bpm.thread.TaskUserAssignService;
import com.hotent.platform.service.system.PositionService;
import com.hotent.platform.service.system.SysOrgService;
import com.hotent.platform.service.system.SysOrgTacticService;
import com.hotent.platform.service.system.SysPaurService;
import com.hotent.platform.service.system.SysUserService;
import com.hotent.platform.web.tag.AnchorTag;

public class CurrentContext implements ICurrentContext {
	
	private static ThreadLocal<String> curUserAccount=new ThreadLocal<String>();
	private static ThreadLocal<ISysUser> curUser=new ThreadLocal<ISysUser>();
	private static ThreadLocal<Locale> curLocale = new ThreadLocal<Locale>();
	

	@Override
	public ISysUser getCurrentUser() {
		//通过setCurrentUserAccount设置的用户。
			if(curUser.get()!=null){
				ISysUser user=curUser.get();
				return user;
			}
			ISysUser sysUser=null;
			SecurityContext securityContext = SecurityContextHolder.getContext();
	        if (securityContext != null) {
	            Authentication auth = securityContext.getAuthentication();
	            if (auth != null) {
	                Object principal = auth.getPrincipal();
	                if (principal instanceof ISysUser) {
	                	sysUser=(ISysUser)principal;
	                }
	            } 
	        }
	        return sysUser;
	}

	@Override
	public Locale getLocale() {
		if(curLocale.get()!=null){
			return curLocale.get();
		}
		setLocale(new Locale("zh","CN"));
		return curLocale.get();
	}

	@Override
	public void setLocale(Locale locale) {
		curLocale.set(locale);
		
	}

	@Override
	public Long getCurrentUserId() {
		ISysUser curUser=getCurrentUser();
		if(curUser!=null) return curUser.getUserId();
		return 0L;
	}

	@Override
	public void setCurrentUserAccount(String account) {
		SysUserService sysUserService=(SysUserService)AppUtil.getBean(SysUserService.class);
		ISysUser sysUser=(ISysUser) sysUserService.getByAccount(account);
		curUser.set(sysUser);
		
	}

	@Override
	public void setCurrentUser(ISysUser sysUser) {
		curUser.set(sysUser);
	}

	@Override
	public void setCurrentPos(Long posId) {
		ISysUser user=getCurrentUser();
		PositionService positionService=(PositionService)AppUtil.getBean(PositionService.class);
		SysOrgService orgService=(SysOrgService)AppUtil.getBean(SysOrgService.class);
		Position position=positionService.getById(posId);
		SysOrg sysOrg=orgService.getById(position.getOrgId());
		ICache iCache=AppUtil.getBean(ICache.class);
		
		Long userId=user.getUserId();
		
		String posKey=ContextUtil.getPositionKey(userId);
		String orgKey=ContextUtil.getOrgKey(userId);
		iCache.add(posKey ,position);
		iCache.add(orgKey ,sysOrg);
		
		String company = ContextUtil.CurrentCompany + getCurrentUserId();
		iCache.delByKey(company); 
	}

	@Override
	public ISysOrg getCurrentOrg() {
		ICache iCache=(ICache)AppUtil.getBean(ICache.class);
		SysOrgService sysOrgService=AppUtil.getBean(SysOrgService.class);
		Long userId=getCurrentUserId();
		
		String orgKey=ContextUtil.getOrgKey(userId);
		SysOrg sysOrg=(SysOrg)iCache.getByKey(orgKey);
		if(sysOrg==null){
			//现在当前组织为岗位，故转换获取行政级别的公司Id
			IPosition position = getCurrentPos();   
			if(position!=null){
				Long orgId=position.getOrgId();
				sysOrg=sysOrgService.getById(orgId);
			}
		}
		if(sysOrg != null) {
			iCache.add(orgKey,sysOrg);
		}
		return (ISysOrg) sysOrg;
	}

	@Override
	public ISysOrg getCurrentCompany() {
		ICache iCache=(ICache)AppUtil.getBean(ICache.class);
		String orgKey=ContextUtil.CurrentCompany + getCurrentUserId();
		ISysOrg sysCompany=(ISysOrg)iCache.getByKey(orgKey);
		
		if(sysCompany==null){
			ISysOrg org = getCurrentOrg();
			if(org == null) return null;
			
			SysOrgTacticService orgTacticService=AppUtil.getBean(SysOrgTacticService.class);
			List<SysOrg> sysOrgList= orgTacticService.getSysOrgListByOrgTactic();
			
			if(BeanUtils.isEmpty(sysOrgList)) {
				return null;
			}
			List<Long> orgIdList=new ArrayList<Long>();
			
			for(SysOrg orgTmp:sysOrgList){
				orgIdList.add(orgTmp.getOrgId());
			}
			
			SysOrgService orgService = AppUtil.getBean(SysOrgService.class);
			
			while(!orgIdList.contains(org.getOrgId())){
				Long parentId=org.getOrgSupId();
				if(parentId.equals(SysOrg.BEGIN_ORGID)) break;
				org=(ISysOrg) orgService.getById(parentId);
			}
			
			if(orgIdList.contains(org.getOrgId())){
				sysCompany=org;
			}

			if(sysCompany != null){
				iCache.add(orgKey, sysCompany);
			}
		}
		return sysCompany;
	}

	@Override
	public Long getCurrentCompanyId() {
		if(isSuperAdmin()) return 0L; 
		ISysOrg org= getCurrentCompany();
		if(org != null) {
			return org.getOrgId();
		}
		else{
			return 0L;
		} 
	}

	@Override
	public IPosition getCurrentPos() {
		Long userId=getCurrentUserId();
		ICache iCache=(ICache)AppUtil.getBean(ICache.class);
		String positionKey=ContextUtil.getPositionKey(userId);
		Position position=(Position)iCache.getByKey(positionKey);
		if(position==null){
			PositionService positionService=AppUtil.getBean(PositionService.class);
			position=positionService.getDefaultPosByUserId(userId);
			if(position!=null){
				iCache.add(positionKey, position);
			}
		}
		return (IPosition) position;
	}

	@Override
	public Long getCurrentPosId() {
		IPosition pos= getCurrentPos();
		if(pos!=null) return pos.getPosId();
		return 0L;
	}

	@Override
	public Long getCurrentOrgId() {
		ISysOrg sysOrg= getCurrentOrg();
		if(sysOrg==null) return 0L;
		return sysOrg.getOrgId();
	}

	@Override
	public String getCurrentUserSkin(HttpServletRequest request) {
		String skinStyle="default";
		
		HttpSession session=request.getSession();
		String skin=(String)session.getAttribute("skinStyle");
		if(StringUtil.isNotEmpty(skin)) return skin;
		
		SysPaurService sysPaurService=(SysPaurService)AppUtil.getBean("sysPaurService");
		Long userId = getCurrentUserId();	
		skinStyle=sysPaurService.getCurrentUserSkin(userId);
		if(userId != 0L) session.setAttribute("skinStyle", skinStyle); 
		return skinStyle;
	}

	@Override
	public void cleanCurUser() {
		curUser.remove();
		
	}

	@Override
	public void removeCurrentOrg() {
		Long userId=ContextUtil.getCurrentUserId();
		ICache iCache=(ICache)AppUtil.getBean(ICache.class);
		String positionKey=ContextUtil.getPositionKey( userId);
		String orgKey=ContextUtil.getOrgKey( userId);
		iCache.delByKey(positionKey);
		iCache.delByKey(orgKey);
		iCache.delByKey(ContextUtil.CurrentCompany + userId);
		
	}

	@Override
	public void clearAll() {
		curUser.remove();
		
		curLocale.remove();
		
		RequestContext.clearHttpReqResponse();
		TaskThreadService.clearAll();
		TaskUserAssignService.clearAll();
		MessageUtil.clean();
		//清除流程缓存。
		
		SysUser.removeRoleList();
		
		AnchorTag.cleanFuncRights();
		
		ActivitiDefCache.clearLocal();
		
		//清空数据源
		DbContextHolder.clearDataSource();
		DbContextHolder.setDefaultDataSource();
		
	}

	@Override
	public void removeCurrentUser() {
		curUserAccount.remove();
		
	}

	@Override
	public boolean isSuperAdmin() {
		SysUser user=(SysUser) getCurrentUser();
		Collection<GrantedAuthority> auths = user.getAuthorities();
		// 是否是超级管理员
		if (auths != null && auths.size() > 0 && auths.contains(SystemConst.ROLE_GRANT_SUPER)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isSuperAdmin(ISysUser user) {
		SysUser sysUser=(SysUser)user;
		String admin=SysUser.getAdminAccount();
		Collection<GrantedAuthority> rtnList= new ArrayList<GrantedAuthority>();
		if (admin.equals(sysUser.getAccount())) {
			rtnList.add(SystemConst.ROLE_GRANT_SUPER);
		}
		// 是否是超级管理员
		if (rtnList != null && rtnList.size() > 0 && rtnList.contains(SystemConst.ROLE_GRANT_SUPER)) {
			return true;
		}
		return false;
	}

	

}
