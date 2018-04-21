package com.hotent.makshi.webservice.api;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.annotations.GZIP;

/**
 * 站内消息相关  Web服务对外接口类
 * 
 * 
 */
@Path("/StationMessage")
@Produces({"application/xml", "application/json"})
@GZIP(threshold=1)
public interface StationMessageWebService {
	
	/**
	 * 未读消息列表
	 * @param account
	 * @return
	 */
	@GET
	@Path("/notReadMsglist")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public String notReadMsglist(@QueryParam("account") String account);
	
	/**
	 * 消息列表
	 * @param account
	 * @return
	 */
	@GET
	@Path("/getMessagelist")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public String getMessagelist(@QueryParam("account") String account);
	
	
	/**
	 * 消息详情
	 * @param messageId
	 * @return
	 */
	@GET
	@Path("/getMessageDetail")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public String getMessageDetail(@QueryParam("messageId") String messageId);
	
	/**
	 * 取得所有回复消息
	 * @param account , messageId
	 * @return
	 */
	@GET
	@Path("/getReply")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public String getReply(@QueryParam("account") String account,@QueryParam("messageId") String messageId);
	
	
	/**
	 * 回复消息
	 * @param account , messageId
	 * @return
	 */
	@POST
	@Path("/reply")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public String reply(@QueryParam("account") String account,@QueryParam("messageId") String messageId,@QueryParam("content") String content);
	
	/**
	 * 标记为已读
	 * @param account , messageId
	 * @return
	 */
	@GET
	@Path("/addMessageRead")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public String addMessageRead(@QueryParam("account") String account,@QueryParam("messageIds") String messageIds);
}