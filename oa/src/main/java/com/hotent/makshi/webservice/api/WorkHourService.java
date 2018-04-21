package com.hotent.makshi.webservice.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.annotations.GZIP;

@Path("/workHourService")
@Produces({"application/xml", "application/json"})
@GZIP(threshold=1)
public interface WorkHourService {
	
	@GET
	@Path("/list")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public String list(@QueryParam("account") String account);
	
	@GET
	@Path("/get")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public String get(@QueryParam("account") String account,@QueryParam("id") String id);
	
	@POST
	@Path("/save")
	//@Consumes({MediaType.APPLICATION_JSON})
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public String save(@QueryParam("data")String workhour);
	
	@GET
	@Path("/getProListData")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public String getProListData(@QueryParam("account") String account,@QueryParam("select_time") String select_time);
	@GET
	@Path("/del")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public String del(@QueryParam("account") String account,@QueryParam("id") String id);
	
	@GET
	@Path("/runStart")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public String runStart(@QueryParam("account") String account);
	
	@GET
	@Path("/isHjsyb")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public String isHjsyb(@QueryParam("account") String account);
}
