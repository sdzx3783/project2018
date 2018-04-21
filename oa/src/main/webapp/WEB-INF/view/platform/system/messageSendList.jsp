
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<%@ page language="java" import="com.hotent.platform.model.system.MessageSend" %>
<html>
<head>
	<title>发送消息管理</title>
	<%@include file="/commons/include/get.jsp" %>
	<c:set var="MESSAGETYPE_PERSON" value="<%=MessageSend.MESSAGETYPE_PERSON %>"></c:set>
	<c:set var="MESSAGETYPE_SCHEDULE" value="<%=MessageSend.MESSAGETYPE_SCHEDULE %>"></c:set>
	<c:set var="MESSAGETYPE_PLAN" value="<%=MessageSend.MESSAGETYPE_PLAN %>"></c:set>
	<c:set var="MESSAGETYPE_AGENCY" value="<%=MessageSend.MESSAGETYPE_AGENCY %>"></c:set>
	<c:set var="MESSAGETYPE_FLOWTASK" value="<%=MessageSend.MESSAGETYPE_FLOWTASK %>"></c:set>
</head>
<body class="oa-mw-page">

    <div id="oa_list_title" class="oa-mgb20 oa-project-title">
	    <!--  <h2>发送消息管理列表</h2> -->
	</div>

    <div id="oa_list_operation" class="oa-pdb20 oa-mgh20 oa-mgl40">
		<a class="oa-button oa-button--primary oa-button--blue del" action="del.ht">删除</a>
	</div>

    <div id="oa_list_search" class="oa-pdb10 oa-mgh20">
		<form id="searchForm" method="post" action="list.ht" class="oa-clear">
	        <div class="oa-fl oa-mgb10">
	            <div class="oa-label">标题</div>
	            <div class="oa-input-wrap oa-mgl100">
	                <input type="text" name="Q_subject_SL"  class="oa-input" value="${param['Q_subject_SL']}"/>
	            </div>
	        </div>
	        <div class="oa-fl oa-mgb10">
	            <div class="oa-label">发送时间</div>
	            <div class="oa-input-wrap oa-mgl100  oa-input-wrap--ib">
	                <input  name="Q_beginsendTime_DL"  class="oa-input datetime" value="${param['Q_beginsendTime_DL']}"/>
	            </div>
	            <span>至</span>
	            <div class="oa-input-wrap  oa-input-wrap--ib">
	                <input  name="Q_endsendTime_DG" class="oa-input datetime" value="${param['Q_endsendTime_DG']}"/>
	            </div>
	        </div>

	        <div class="oa-fl oa-mgb10 oa-mgh20">
	            <button class="oa-button oa-button--primary oa-button--blue">查询</button>
	        </div>
		</form>
	</div>


    <div class="oa-pdb20 oa-mgh20">
	    <div id="oa_list_content" class="oa-table-scroll-wrap">
			<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="messageSendList" id="messageSendItem" requestURI="list.ht" 
		    	sort="external" cellpadding="1" cellspacing="1" export="false" class="oa-table--default oa-table--nowrap">
				<display:column title="${checkAll}" media="html" >
					  	<input type="checkbox" class="pk" name="id" value="${messageSendItem.id}">
				</display:column>
				<display:column property="subject" title="标题" sortable="true" sortName="subject"></display:column>
				<display:column title="消息类型" sortable="true" sortName="messageType">
				<c:choose>
					<c:when test="${messageSendItem.messageType==MESSAGETYPE_PERSON}">
					       个人信息
				   	</c:when>
				   	<c:when test="${messageSendItem.messageType==MESSAGETYPE_SCHEDULE}">
					        日程安排
				   	</c:when>
				   	<c:when test="${messageSendItem.messageType==MESSAGETYPE_PLAN}">
					       计划任务
				   	</c:when>
			       	<c:when test="${messageSendItem.messageType==MESSAGETYPE_AGENCY}">
				                   代办提醒     
				   	</c:when>
				   	<c:when test="${messageSendItem.messageType==MESSAGETYPE_FLOWTASK}">
				                  流程提醒             
				   	</c:when>
			    </c:choose>
				</display:column>							
				<display:column  title="发送时间" sortable="true" sortName="sendTime">
					<fmt:formatDate value="${messageSendItem.sendTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</display:column>
				<display:column title="收信人" sortable="false">
				<c:choose>
					<c:when test="${fn:length(messageSendItem.receiverName)>20}">
					      ${fn:substring(messageSendItem.receiverName,0,20)}....
				   	</c:when>
			       	<c:otherwise>
				          ${messageSendItem.receiverName}    
				   	</c:otherwise>
			    </c:choose>
				</display:column>
				<display:column title="管理" media="html" >
					<a href="del.ht?id=${messageSendItem.id}" class="oa-button-label del">删除</a>
					<a href="edit.ht?id=${messageSendItem.id}" class="oa-button-label edit" style='${longTime-messageSendItem.sendTime.time > spanTime ?'display:none':'display:'}'>编辑</a>
					<a href="get.ht?id=${messageSendItem.id}" class="oa-button-label detail">详情</a>
				</display:column>
			</display:table>
	    </div>

		<hotent:paging tableId="messageSendItem"/>

	</div>


</body>
</html>


