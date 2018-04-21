package com.hotent.makshi.webservice.api;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.annotations.GZIP;

/**
 * 个人信息 Web服务对外接口类
 * 
 * 
 */
@Path("/SysuserService")
@Produces({"application/xml", "application/json"})
@GZIP(threshold=1)
public interface SysuserWebService {
	/**
	 * 获取某个用户的个人信息包括组织信息
	 * @param orgId
	 * @return
	 */
	@GET
	@Path("/getuserinfo")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public String getuserinfo(@QueryParam("account") String account);
	
	/**
	 * 修改个人信息
	 * @param orgId
	 * @return
	 */
	@POST
	@Path("/updateUserInfomation")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public String updateUserInfomation(@QueryParam("account") String account);
	
	/**
	 * 修改密码
	 * @param orgId
	 * @return
	 */
	@POST
	@Path("/updatePwd")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public String updatePwd(@QueryParam("account") String account);
	
}