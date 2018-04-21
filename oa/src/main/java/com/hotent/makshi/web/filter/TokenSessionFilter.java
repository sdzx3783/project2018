package com.hotent.makshi.web.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.hotent.core.api.org.model.ISysUser;
import com.hotent.core.api.util.ContextUtil;

public class TokenSessionFilter implements Filter{
	Logger log = Logger.getLogger(TokenSessionFilter.class);
	private FilterConfig filterConfig;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpSession session = ((HttpServletRequest)request).getSession();
		String uri = ((HttpServletRequest)request).getRequestURI();
		String sessionid = session==null?"NUL":session.getId();
		Map<String, String[]> paramMap = request.getParameterMap();
		StringBuffer paramLog = new StringBuffer();
		if(paramMap!=null) {
			paramLog.append("\nparam-->\n");
			for(String key : paramMap.keySet()) {
				paramLog.append(key).append(":").append(paramMap.get(key)[0]).append("\n");
			}
		}

		log.info("uri: "+uri+":"+sessionid +paramLog.toString());
		
		ISysUser currentUser = ContextUtil.getContext().getCurrentUser();
		String account = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getHeader("X-AUTH-ID");
		if(account == null) {
			if(currentUser == null && uri.endsWith(filterConfig.getInitParameter("filterSuffix"))) {
				log.info("account is null; "+uri+":"+sessionid);
			}
		}else {
			if(ContextUtil.getContext().getCurrentUser()==null || !account.equalsIgnoreCase(ContextUtil.getContext().getCurrentUser().getAccount())) {
				ContextUtil.getContext().clearAll();
				ContextUtil.getContext().setCurrentUserAccount(account);
			}
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
