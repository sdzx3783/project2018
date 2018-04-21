package com.hotent.platform.controller.system;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.api.util.PropertyUtil;
import com.hotent.core.encrypt.EncryptUtil;
import com.hotent.core.log.SysAuditThreadLocalHolder;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.filter.HtSwitchUserFilter;
import com.hotent.core.web.servlet.ValidCode;
import com.hotent.core.web.util.CookieUitl;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.annotion.ActionExecOrder;
import com.hotent.platform.model.system.LoginLog;
import com.hotent.platform.model.system.PwdStrategy;
import com.hotent.platform.model.system.SubSystem;
import com.hotent.platform.model.system.SysAuditModelType;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.ldap.LdapUserService;
import com.hotent.platform.service.system.LoginLogService;
import com.hotent.platform.service.system.PwdStrategyService;
import com.hotent.platform.service.system.SecurityUtil;
import com.hotent.platform.service.system.SysUserService;

/**
 * 登录访问控制器，用于扩展Spring Security 缺省的登录处理器
 * 
 * @author csx
 * 
 */
@Controller
@RequestMapping("/login.ht")
@Action(ownermodel = SysAuditModelType.LOGIN_MANAGEMENT)
public class LoginController extends BaseController {
	@Resource
	private SysUserService sysUserService;
	@Resource(name = "authenticationManager")
	private AuthenticationManager authenticationManager = null;
	@Resource
	private Properties configproperties;
	@Resource
	private LdapUserService ldapUserService;
	@Resource
	private SessionLocaleResolver sessionLocaleResolver;
	@Resource
	private PwdStrategyService pwdStrategyService;
	@Resource
	private LoginLogService loginLogService;
	@Resource
	private SessionAuthenticationStrategy sessionStrategy = new NullAuthenticatedSessionStrategy();

	//	@Resource
	//	HtSecurityMetadataSource securityMetadataSource;
	//must the same as <remember-me key="bpm3PrivateKey"/> of app-security.xml 
	
	public final static String TRY_MAX_COUNT = "tryMaxCount";
	public final static int maxTryCount = 5;

	/**
	 * 登录成功跳转的路径
	 */
	private String succeedUrl = "/platform/console/main.ht";

	@RequestMapping
	@Action(description = "用户登录", execOrder = ActionExecOrder.AFTER, detail = "<#if success>用户【${SysAuditLinkService.getSysUserLink(username)}】登录系统成功<#else>账号【${username}】登录失败</#if>")
	public void login(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "username") String username, @RequestParam(value = "password") String password) throws IOException {
		String succeedUrl = this.succeedUrl;//每次都初始化这个

		//因为页面jsp也用到validCodeEnabled这个写死的变量
		String validCodeEnabled = RequestUtil.getString(request, "validCodeEnabled", "false");

		SecurityContextHolder.clearContext();

		//添加系统日志信息 -B
		try {
			SysAuditThreadLocalHolder.putParamerter("success", false);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		LoginLog loginLog = new LoginLog();//登录记录
		loginLog.setStatus(LoginLog.Status.SUCCESS);//默认登录成功
		loginLog.setDesc("登陆成功");
//		boolean isUrlAddJsessionId =true;//url是否添加jsessionid 
		
		boolean error = false;
		try {
			//如果有验证码
			if (validCodeEnabled != null && "true".equals(validCodeEnabled)) {
				String validCode = (String) request.getSession().getAttribute(ValidCode.SessionName_Randcode);
				String code = request.getParameter("validCode");
				if (validCode == null || StringUtils.isEmpty(code) || !validCode.equals(code)) {
					error = true;

					String msg = "验证码不正确";
					//记录登录信息
					loginLog.setStatus(LoginLog.Status.VCODE_ERR);
					loginLog.setDesc(msg);

					request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, msg);
					request.getSession().setAttribute("validCodeEnabled", "true");//还是需要验证码
					throw new AccessDeniedException(msg);
				}
			} else {//没有验证码就验证一下是不是需要验证码
				if (pwdStrategyService.checkUserVcodeEnabled(username)) {
					//显示验证码
					request.getSession().setAttribute("validCodeEnabled", "true");

					String msg = "由于该账号登录错误次数过多，请输入验证码后重试";
					//记录登录信息
					loginLog.setStatus(LoginLog.Status.PWDSTRATEGY_UNPASS);
					loginLog.setDesc(msg);

					request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, msg);
					throw new AccessDeniedException(msg);
				}
			}
			if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
				error = true;

				String msg = "用户名密码为空";
				//记录登录信息
				loginLog.setStatus(LoginLog.Status.ACCOUNT_PWD_EMPTY);
				loginLog.setDesc(msg);

				request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, msg);
				throw new AccessDeniedException(msg);
			}
			
			SysUser sysUser = sysUserService.getByAccount(username);
			String encrptPassword = EncryptUtil.encryptSha256(password);
			//ad 用户登录
			if (sysUser != null && sysUser.getFromType() == 1) {
				boolean authenticated = ldapUserAuthentication(username, password);
				if (!authenticated) {
					error = true;

					String msg = "用户名密码输入错误";
					//记录登录信息
					loginLog.setStatus(LoginLog.Status.ACCOUNT_PWD_ERR);
					loginLog.setDesc(msg);

					request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, msg);

					throw new AccessDeniedException(msg);
				} else {
					if (!encrptPassword.equals(sysUser.getPassword())) {
						sysUserService.updPwd(sysUser.getUserId(), password);
					}
				}
			}
			//通过sys_user 验证。
			else {
				if (sysUser == null || !encrptPassword.equals(sysUser.getPassword())) {
					error = true;
					//return;
					String msg = "用户名密码输入错误";
					//记录登录信息
					loginLog.setStatus(LoginLog.Status.ACCOUNT_PWD_ERR);
					loginLog.setDesc(msg);

					request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, msg);

					throw new AccessDeniedException(msg);
				}
			}

			//添加系统日志信息 -B
			try {
				SysAuditThreadLocalHolder.putParamerter("success", true);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}

			//----------------->到这里说明登录成功了，检查密码策略
			JSONObject result = pwdStrategyService.checkUser(sysUser, password);

			short status = Short.parseShort(result.getString("status"));
			if (status != PwdStrategy.Status.SUCCESS) {
				error = true;
				//处理返回
				if (status == PwdStrategy.Status.NEED_TO_CHANGE_PWD || status == PwdStrategy.Status.LENGTH_TOO_SHORT || status == PwdStrategy.Status.NO_MATCH_NUMANDWORD || status == PwdStrategy.Status.NO_MATCH_NUMANDWORDANDSPECIAL || status == PwdStrategy.Status.PWD_OVERDUE) {
					//这些状态都修改密码
					succeedUrl = "/platform/system/sysUser/commonResetPwdView.ht";
				}

				String msg = result.getString("msg");
				loginLog.setStatus(LoginLog.Status.PWDSTRATEGY_UNPASS);
				loginLog.setDesc(msg);
				request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, msg);
				throw new AccessDeniedException(msg);
			}
			//检查是否提醒过期
			boolean remind = result.get("remind") != null ? result.getBoolean("remind") : false;
			if (remind) {
				String msg = result.getString("remindMsg");
				succeedUrl = "/platform/system/sysUser/pwdRemind.ht";
				request.getSession().setAttribute("remindMsg", msg);
			}

			//<--------------------------------

			Authentication auth = SecurityUtil.login(request, username, password, false);
			//			String[] lan = language.split("_");
			//			Locale curLocale = new Locale(lan[0],lan[1]);
			//			//设置多语言支持
			//			sessionLocaleResolver.setLocale(request, response, curLocale);

			//request.getSession().setAttribute(WebAttributes.LAST_USERNAME, username);	
			//登陆时移除管理员标识
			request.getSession().removeAttribute("isAdmin");

			sessionStrategy.onAuthentication(auth, request, response);
			//从session中删除组织数据。
			ContextUtil.removeCurrentOrg();
			//从session中删除当前子系统。
			request.getSession().removeAttribute(SubSystem.CURRENT_SYSTEM);
			request.getSession().setAttribute("SPRING_SECURITY_LAST_USERNAME", username);
			request.getSession().setAttribute("LOGIN_USER_ID", sysUser.getUserId());
			//删除cookie。
			CookieUitl.delCookie("loginAction", request, response);
			//删除切换用户的cookie
			CookieUitl.delCookie(HtSwitchUserFilter.SwitchAccount, request, response);

			SecurityUtil.writeRememberMeCookie(request, response, username, encrptPassword);
		} catch (LockedException e) {
			String msg = username + ":用户被锁定";
			//记录登录信息
			loginLog.setStatus(LoginLog.Status.ACCOUNT_LOCKED);
			loginLog.setDesc(msg);

			request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, msg);
			error = true;
		} catch (DisabledException e) {
			String msg = username + ":用户被禁用";
			//记录登录信息
			loginLog.setStatus(LoginLog.Status.ACCOUNT_DISABLED);
			loginLog.setDesc(msg);

			request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, msg);
			error = true;
		} catch (AccountExpiredException e) {
			String msg = username + ":用户已过期";
			//记录登录信息
			loginLog.setStatus(LoginLog.Status.ACCOUNT_OVERDUE);
			loginLog.setDesc(msg);

			request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, msg);
			error = true;
		} catch (AccessDeniedException e) {//捉住密码错误的异常，这里纯粹为了不让控制台抛出错误信息
		} finally {
			//添加登录日志
			loginLog.setAccount(username);
			loginLog.setIp(RequestUtil.getIpAddr(request));
			loginLogService.save(loginLog);

			if (error == true) {//登录失败开始检查密码策略的锁定策略和验证码
				if (pwdStrategyService.checkUserLockable(username)) {
					//锁定账号
					sysUserService.updStatus(username, SysUser.STATUS_OK, SysUser.LOCKED);
					String msg = "密码错误次数过多 ,账号被锁住了,请联系管理解锁";
					request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, msg);
					loginLog.setStatus(LoginLog.Status.PWDSTRATEGY_UNPASS);
					loginLog.setDesc(msg);
				} else if (pwdStrategyService.checkUserVcodeEnabled(username)) {
					request.getSession().setAttribute("validCodeEnabled", "true");
					String msg = "由于该账号登录错误次数过多，请输入验证码后重试";
					request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, msg);
				}
			}

			//读取配置文件app.properties
			String loginpop = PropertyUtil.getByAlias("loginPop", "0");
			//登陆后是否弹出显示主页，1表示弹出显示，否则表示不弹出
			if ("1".equals(loginpop)) {
				succeedUrl = "/platform/console/turnToMain.ht";
			}
			
			response.sendRedirect(request.getContextPath() + succeedUrl);

		}

	}

	

	private boolean ldapUserAuthentication(String userId, String password) {
		return ldapUserService.authenticate(userId, password);
	}

}
