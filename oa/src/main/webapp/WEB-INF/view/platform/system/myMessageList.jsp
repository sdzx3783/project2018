
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<%@ page language="java" import="com.hotent.platform.model.system.MessageSend" %>
<html>
<head>
    <title>我的消息</title>
    <%@include file="/commons/include/get.jsp" %>
    <c:set var="MESSAGETYPE_PERSON" value="<%=MessageSend.MESSAGETYPE_PERSON %>"></c:set>
    <c:set var="MESSAGETYPE_SCHEDULE" value="<%=MessageSend.MESSAGETYPE_SCHEDULE %>"></c:set>
    <c:set var="MESSAGETYPE_PLAN" value="<%=MessageSend.MESSAGETYPE_PLAN %>"></c:set>
    <c:set var="MESSAGETYPE_AGENCY" value="<%=MessageSend.MESSAGETYPE_AGENCY %>"></c:set>
    <c:set var="MESSAGETYPE_FLOWTASK" value="<%=MessageSend.MESSAGETYPE_FLOWTASK %>"></c:set>
</head>
<body class="oa-mw-page">

    <div id="oa_list_title" class="oa-mgb20 oa-project-title">
      
    </div>
    
    <div id="oa_list_operation" class="oa-pdb20 oa-mgh20 oa-mgl20">
        <a class="oa-button oa-button--primary oa-button--blue" href="edit.ht">发送消息</a>
    </div>

    <div class="oa-pdb20 oa-mgh20">
		<div id="oa_list_content" class="oa-table-scroll-wrap">
	        <display:table name="list" id="messageItem" requestURI="mylist.ht" 
	            sort="external" cellpadding="1" cellspacing="1" export="false" class="oa-table--default oa-table--nowrap">
	             
	            <c:choose>
                    <c:when test="${messageItem.receiveTime==null}">
                    	<display:column style="font-weight:bold;" property="userName" title="发信人" sortable="true" sortName="userName"></display:column>
			            <display:column style="font-weight:bold;"  title="标题" sortable="true" sortName="subject">
			            	<a href="getReply.ht?id=${messageItem.id}">${messageItem.subject}</a>
			            </display:column>
			            <display:column style="font-weight:bold;" title="消息类型" sortable="true" sortName="messageType">
				            <c:choose>
				                <c:when test="${messageItem.messageType==MESSAGETYPE_PERSON}">个人信息</c:when>
				                <c:when test="${messageItem.messageType==MESSAGETYPE_SCHEDULE}">日程安排</c:when>
				                <c:when test="${messageItem.messageType==MESSAGETYPE_PLAN}">计划任务</c:when>
				                <c:when test="${messageItem.messageType==MESSAGETYPE_AGENCY}">代办提醒</c:when>
				                <c:when test="${messageItem.messageType==MESSAGETYPE_FLOWTASK}"> 流程提醒</c:when>
				            </c:choose>
			            </display:column>
                        <display:column style="font-weight:bold;" title="发送时间" sortable="true" sortName="sendTime">
		                    <fmt:formatDate value="${messageItem.sendTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			            </display:column>
			            <display:column style="font-weight:bold;" title="收信人" sortable="false">
				            <c:choose>
				                <c:when test="${fn:length(messageItem.receiverName)>20}">
				                      ${fn:substring(messageItem.receiverName,0,20)}....
				                </c:when>
				                <c:otherwise>
				                      ${messageItem.receiverName}    
				                </c:otherwise>
				            </c:choose>
			            </display:column>
			            <display:column style="font-weight:bold;" title="操作" media="html">
			                <a href="getReply.ht?id=${messageItem.id}" class="oa-button-label">快速回复</a>
			                <a href="${ctx}/platform/system/messageRead/list.ht?messageId=${messageItem.id}" class="oa-button-label">详情</a>
			            </display:column>
                    </c:when>
                    <c:otherwise>
                    	<display:column property="userName" title="发信人" sortable="true" sortName="userName"></display:column>
			            <display:column title="标题" sortable="true" sortName="subject">
			            	<a href="getReply.ht?id=${messageItem.id}">${messageItem.subject}</a>
			            </display:column>
			            <display:column title="消息类型" sortable="true" sortName="messageType">
				            <c:choose>
				                <c:when test="${messageItem.messageType==MESSAGETYPE_PERSON}">个人信息</c:when>
				                <c:when test="${messageItem.messageType==MESSAGETYPE_SCHEDULE}">日程安排</c:when>
				                <c:when test="${messageItem.messageType==MESSAGETYPE_PLAN}">计划任务</c:when>
				                <c:when test="${messageItem.messageType==MESSAGETYPE_AGENCY}">代办提醒</c:when>
				                <c:when test="${messageItem.messageType==MESSAGETYPE_FLOWTASK}"> 流程提醒</c:when>
				            </c:choose>
			            </display:column>
                        <display:column title="时间" sortable="true" sortName="sendTime">
                    		<fmt:formatDate value="${messageItem.sendTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
	            		</display:column>
	            		<display:column title="收信人" sortable="false">
				            <c:choose>
				                <c:when test="${fn:length(messageItem.receiverName)>20}">
				                      ${fn:substring(messageItem.receiverName,0,20)}....
				                </c:when>
				                <c:otherwise>
				                      ${messageItem.receiverName}    
				                </c:otherwise>
				            </c:choose>
			            </display:column>
			            <display:column title="操作" media="html">
			                <a href="getReply.ht?id=${messageItem.id}" class="oa-button-label">快速回复</a>
			                <a href="${ctx}/platform/system/messageRead/list.ht?messageId=${messageItem.id}" class="oa-button-label">详情</a>
			            </display:column>
					</c:otherwise>
                </c:choose>                  	            	            		                	             
	        </display:table>
        </div>
        <hotent:paging tableId="messageItem"/>
    </div>


</body>
</html>


