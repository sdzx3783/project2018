package com.hotent.makshi.webservice.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.hotent.core.page.PageBean;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.makshi.service.appPush.AppInnerMessageService;
import com.hotent.makshi.utils.DateUtils;
import com.hotent.makshi.webservice.api.StationMessageWebService;
import com.hotent.platform.model.system.MessageReceiver;
import com.hotent.platform.model.system.MessageReply;
import com.hotent.platform.model.system.MessageSend;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.system.MessageReadService;
import com.hotent.platform.service.system.MessageReceiverService;
import com.hotent.platform.service.system.MessageReplyService;
import com.hotent.platform.service.system.MessageSendService;
import com.hotent.platform.service.system.SysUserService;
import com.hotent.platform.webservice.exception.ApiExcetpion;
import com.hotent.platform.webservice.impl.BaseRuelt;
import com.hotent.platform.webservice.util.PropUtils;
import com.hotent.platform.webservice.util.ReturnCode;


public class StationMessageServiceImpl implements StationMessageWebService{

	@Resource
	private SysUserService sysUserService;
	@Resource
	private MessageSendService sendService;
	@Resource
	private MessageReplyService replyService;
	@Resource
	private MessageReadService readService;
	@Resource
	private MessageReceiverService receiverService;
	@Resource
	private AppInnerMessageService appInnerMessageService;
	@Context
	private HttpServletRequest request;
	
	protected enum messageType{
		个人信息("个人信息", 1), 日程安排("日程安排", 2), 计划任务("计划任务", 3), 系统信息("系统信息", 4),代办提醒("代办提醒", 5), 流程提醒("流程提醒", 6);  
		private String value;
		private int key;
		private messageType(String value,int key){
			this.value=value;
			this.key=key;
		}
		protected static String getName(int index) {  
	        for (messageType c : messageType.values()) {  
	            if (c.getKey() == index) {  
	                return c.value;  
	            }  
	        }  
	        return "其他";  
	    }  
		protected int getKey(){
			return key;
	    }
	}
	
	/**
	 * 未读消息列表
	 */
	@Override
	public String notReadMsglist(String account) {
		BaseRuelt result = new BaseRuelt();
		try {
			SysUser sysUser = sysUserService.getByAccount(account);
			if(sysUser==null){
				throw new ApiExcetpion(ReturnCode.NO_DATA_FOUND,"用户不存在！");
			}
			QueryFilter queryFilter=new QueryFilter(new net.sf.json.JSONObject());
			queryFilter.addFilter("receiverId", sysUser.getUserId());
			int pageNo = RequestUtil.getInt(request, "pageNo", 1);
			int pageSize = RequestUtil.getInt(request, "pageSize", 15);
			PageBean pageBean = new PageBean(pageNo,pageSize);
			queryFilter.setPageBean(pageBean);
			List<MessageSend> list=sendService.notReadMsglistDialog(queryFilter);
			List<Map<String, Object>> resultlist = new ArrayList<>();
			for(MessageSend messageSend:list){
				Map<String, Object> map = new HashMap<>();
				String userName=messageSend.getUserName();
				String subject=messageSend.getSubject();
				String messageTypeName=messageType.getName(Integer.valueOf(messageSend.getMessageType()));
				String sendTime=messageSend.getSendTime()==null?"":DateUtils.formatDateS(messageSend.getSendTime());
				String allMessage=userName+"-"+subject+"-"+messageTypeName+"-"+sendTime;
				map.put("messageId", messageSend.getId());
				map.put("userName", userName);
				map.put("subject", subject);
				map.put("messageTypeName", messageTypeName);
				map.put("sendTime", sendTime);
				map.put("allMessage", allMessage);
				resultlist.add(map);
			}
			Map<String, Object> resultMap = new HashMap<>();
			resultMap.put("list",resultlist);
			result.setResultMap(resultMap, ReturnCode.SUCCESS);
			return result.getResultMap().toString();
		} catch (ApiExcetpion e) {
			e.printStackTrace();
			throw e;
		}catch (Exception e) {
			e.printStackTrace();
			throw new ApiExcetpion(ReturnCode.SYSTEM_ERROR);
		}
	}

	/**
	 * 消息列表
	 */
	@Override
	public String getMessagelist(String account) {
		BaseRuelt result = new BaseRuelt();
		try {
			SysUser sysUser = sysUserService.getByAccount(account);
			if(sysUser==null){
				throw new ApiExcetpion(ReturnCode.NO_DATA_FOUND,"用户不存在！");
			}
			String onlyCount = request.getParameter("onlyCount");
			QueryFilter queryFilter=new QueryFilter(new net.sf.json.JSONObject());
			queryFilter.addFilter("receiverId", sysUser.getUserId());
			int pageNo = RequestUtil.getInt(request, "pageNo", 1);
			int pageSize = RequestUtil.getInt(request, "pageSize", 15);
			PageBean pageBean = new PageBean(pageNo,pageSize);
			queryFilter.setPageBean(pageBean);
			if(onlyCount!=null&&"notRead".equals(onlyCount)){//只需要返回未读消息数量
            	queryFilter.addFilter("receiveTime", 1);
            }
			List<MessageSend> list=sendService.getReceiverByUser(queryFilter);
			Map<String, Object> resultMap = new HashMap<>();
			if(onlyCount!=null&&"notRead".equals(onlyCount)){
				resultMap.put("count", queryFilter.getPageBean().getTotalCount());
			}else{
				List<Map<String, Object>> resultlist = new ArrayList<>();
				for(MessageSend messageSend:list){
					Map<String, Object> map = new HashMap<>();
					String userName=messageSend.getUserName();
					String subject=messageSend.getSubject();
					String messageTypeName=messageType.getName(Integer.valueOf(messageSend.getMessageType()));
					String sendTime=messageSend.getSendTime()==null?"":DateUtils.formatDateS(messageSend.getSendTime());
					String allMessage=userName+"-"+subject+"-"+messageTypeName+"-"+sendTime;
					map.put("readFlag", messageSend.getReceiveTime()==null?0:1);
					map.put("messageId", messageSend.getId());
					map.put("userName", userName);
					map.put("subject", subject);
					map.put("messageTypeName", messageTypeName);
					map.put("sendTime", sendTime);
					map.put("allMessage", allMessage);
					resultlist.add(map);
				}
				resultMap.put("list",resultlist);
			}
			result.setResultMap(resultMap, ReturnCode.SUCCESS);
			return result.getResultMap().toString();
		} catch (ApiExcetpion e) {
			e.printStackTrace();
			throw e;
		}catch (Exception e) {
			e.printStackTrace();
			throw new ApiExcetpion(ReturnCode.SYSTEM_ERROR);
		}
	}
	
	/**
	 * 消息详情
	 */
	@Override
	public String getMessageDetail(String messageId) {
		BaseRuelt result = new BaseRuelt();
		try{
			Long messageIdL=null;
			try {
				messageIdL=Long.valueOf(messageId);
			} catch (NumberFormatException e) {
				throw new ApiExcetpion(ReturnCode.PRAMS_ERROR,"messageId 错误！messageId="+messageId);
			}	
			MessageSend messageSend = new MessageSend();
			MessageSend messageSendtemp = sendService.getById(messageIdL);
			messageSend=messageSendtemp==null?messageSend:messageSendtemp;
			Map<String, Object> map = new HashMap<>();
			map.put("messageId", messageSend.getId());
			map.put("subject", messageSend.getSubject());
			map.put("userName", messageSend.getUserName());
			map.put("sendTime", messageSend.getSendTime()==null?"":DateUtils.formatDateL(messageSend.getSendTime()));
			map.put("contentText", messageSend.getContentText());
			List<Object> fileList = new ArrayList<>();
			if(StringUtils.isNotBlank(messageSend.getAttachment())){
				JSONArray jsonArray =new JSONArray(messageSend.getAttachment());
				Map<String, Object> fileNameMap = new HashMap<>();
				for(int i=0;i<jsonArray.length();i++){
					JSONObject maptemp = (JSONObject) jsonArray.get(i);
					fileNameMap.clear();
					fileNameMap.put("fileId", maptemp.get("id"));
					fileNameMap.put("fileName", maptemp.get("name"));
					fileNameMap.put("fileUrl", PropUtils.getPropertyByDirKey("app_file_download_url", "api")+maptemp.get("id"));
					fileList.add(fileNameMap);
				}
			}
			map.put("fileList", fileList);
			result.setResultMap(map, ReturnCode.SUCCESS);
			/* 获取回复的消息列表
			 * QueryFilter queryFilter=new QueryFilter(new JSONObject());
			queryFilter.addFilter("userId", userId);
			List<MessageReply> list=replyService.getAll(queryFilter);*/
			
			// 插入已读信息表
			//readService.addMessageRead(messageId, curUser);
			return result.getResultMap().toString();
		}catch (ApiExcetpion e) {
			e.printStackTrace();
			throw e;
		}catch (Exception e) {
			e.printStackTrace();
			throw new ApiExcetpion(ReturnCode.SYSTEM_ERROR);
		}
	}

	/**
	 * 取得所有回复消息
	 */
	@Override
	public String getReply(String account,String messageId) {
		BaseRuelt result = new BaseRuelt();
		try {
			SysUser sysUser = sysUserService.getByAccount(account);
			Long messageIdL=null;
			if(sysUser==null){
				throw new ApiExcetpion(ReturnCode.NO_DATA_FOUND,"用户不存在！");
			}
			try {
				messageIdL=Long.valueOf(messageId);
			} catch (NumberFormatException e) {
				throw new ApiExcetpion(ReturnCode.PRAMS_ERROR,"messageId 错误！messageId="+messageId);
			}	
			List<MessageSend> messageSendlist = sendService.getMessageReply(messageIdL);
			MessageSend msg = sendService.getById(messageIdL);
			String replyName = "";
			if(msg!=null){
				if(sysUser.getFullname().equals(msg.getUserName())){
					replyName=msg.getReceiverName();
				}else{
					replyName=msg.getUserName();
				}
				
			}
			Map<String, Object> resultMap = new HashMap<>();
			List<Map<String, Object>> replyList = new ArrayList<>();
			for(MessageSend messageSend:messageSendlist){
				Map<String, Object> map = new HashMap<>();
				map.put("images", "/images/default.png");
				map.put("userName", messageSend.getUserName());
				map.put("userId", messageSend.getUserId());
				map.put("contentText", messageSend.getContentText());
				map.put("sendTime",messageSend.getSendTime()==null?"":DateUtils.formatDateL(messageSend.getSendTime()));
				replyList.add(map);
			}
			resultMap.put("replyList", replyList);
			resultMap.put("receiverName", replyName);
			resultMap.put("senderId", sysUser.getUserId());
			resultMap.put("messageId", messageId);
			result.setResultMap(resultMap, ReturnCode.SUCCESS);
			return result.getResultMap().toString();
		}catch (ApiExcetpion e){
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApiExcetpion(ReturnCode.SYSTEM_ERROR);
		}
	}

	/**
	 * 回复消息
	 */
	@Override
	public String reply(String account,String messageId,String content) {
		BaseRuelt result = new BaseRuelt();
		try {
			messageId = messageId==null?request.getParameter("messageId"):messageId;
			content = content==null?request.getParameter("content"):content;
			SysUser sysUser = sysUserService.getByAccount(account);
			Long messageIdL=null;
			try {
				messageIdL=Long.valueOf(messageId);
			} catch (NumberFormatException e) {
				throw new ApiExcetpion(ReturnCode.PRAMS_ERROR,"messageId 含有非法数字！messageId="+messageId);
			}	
			MessageReply messageReply = new MessageReply();
			messageReply.setContent(content);
			messageReply.setMessageId(messageIdL);
			replyService.saveReply(messageReply, sysUser);
			MessageSend messageSend = sendService.getById(messageReply.getMessageId());
			Long sendUserId = messageSend.getUserId();//消息发送者
			List<MessageReceiver> messageRes = receiverService.getByMessageId(messageReply.getMessageId());
			MessageReceiver messageReceiver =null;
			if(messageReply.getReplyId().longValue()==sendUserId.longValue()){
				for(MessageReceiver re :messageRes){
					if(re.getReceiverId().longValue()!=sendUserId.longValue()){
						readService.deleteByMsgIdReId(messageReply.getMessageId(), re.getReceiverId());
						appInnerMessageService.addMyMessage("0",null,re.getReceiverId().longValue(),null,null,messageReply.getContent(),"{\"url\":\"oaapp_messagelist:\"}","新的消息回复","站内消息");
					}
					
	//				messageReceiver = new MessageReceiver();
	//				messageReceiver.setMessageId(messageReply.getMessageId());
	//				messageReceiver.setReceiveType(re.getReceiveType());
	//				messageReceiver.setReceiverId(re.getReceiverId());
	//				messageReceiver.setReceiver(re.getReceiver());
	//				receiverService.add(messageReceiver);
				}
			}else{
				MessageReceiver temp = receiverService.getByMsgIdReId(messageReply.getMessageId(), sendUserId);
				if(temp==null){
					messageReceiver = new MessageReceiver();
					messageReceiver.setId(UniqueIdUtil.genId());
					messageReceiver.setMessageId(messageReply.getMessageId());
					messageReceiver.setReceiveType((short)0);
					messageReceiver.setReceiverId(sendUserId);
					receiverService.add(messageReceiver);
				}else{
					readService.deleteByMsgIdReId(messageReply.getMessageId(), sendUserId);
				}
				appInnerMessageService.addMyMessage("0",null,sendUserId,null,null,messageReply.getContent(),"{\"url\":\"oaapp_messagelist:\"}","新的消息回复","站内消息");
			
			}
			result.setResultMap("SUCCESS", ReturnCode.SUCCESS);
			return result.getResultMap().toString();
		}catch (ApiExcetpion e){
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApiExcetpion(ReturnCode.SYSTEM_ERROR,"操作失败！");
		}
	}
	
	/**
	 * 标记为已读
	 */
	@Override
	public String addMessageRead(String account,String messageIds) {
		try {
			messageIds = messageIds==null?request.getParameter("messageIds"):messageIds;
			BaseRuelt result = new BaseRuelt();
			SysUser sysUser = sysUserService.getByAccount(account);
			if(sysUser==null){
				throw new ApiExcetpion(ReturnCode.NO_DATA_FOUND,"用户不存在！");
			}
			String[] ids = messageIds.split(",");
			Long messageIdL=null;
			for(String id : ids){
				try {
					messageIdL=Long.valueOf(id);
				} catch (NumberFormatException e) {
					throw new ApiExcetpion(ReturnCode.PRAMS_ERROR,"messageIds 含有非法数字！messageId="+id);
				}	
				readService.addMessageRead(messageIdL, sysUser);
			}
			result.setResultMap("SUCCESS", ReturnCode.SUCCESS);
			return result.getResultMap().toString();
		}catch (ApiExcetpion e){
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApiExcetpion(ReturnCode.SYSTEM_ERROR,"操作失败！");
		}
	}
	
}
