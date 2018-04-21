package com.hotent.makshi.webservice.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.annotations.GZIP;

/**
 * 通讯录 Web服务对外接口类
 * 
 * 
 */
@Path("/CompanyBookService")
@Produces({"application/xml", "application/json"})
@GZIP(threshold=1)
public interface CompanyBookWebService {
	/**
	 * 获取某个组织的通讯录
	 * @param orgId
	 * @return
	 */
	@GET
	@Path("/list")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public String list(@QueryParam("orgId") String	orgId);
}