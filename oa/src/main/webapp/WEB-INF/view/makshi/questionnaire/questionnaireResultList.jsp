<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>问卷调查</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body class="oa-mw-page">

    <div id="oa_list_title" class="oa-mgb20 oa-project-title">
	    <!-- <h2>问卷调查列表</h2> -->
	</div>

    <div class="oa-pdb20 oa-mgh20">
	    <div id="oa_list_content" class="oa-table-scroll-wrap">
		    <display:table name="questionnaireList" id="questionnaireItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
				<display:column property="id" title="序号" sortable="true" sortName="ID"></display:column>
				<display:column property="title" title="名称" sortable="true" sortName="F_title">
				</display:column>
				<display:column property="creater" title="创建人" sortable="true" sortName="F_creater"></display:column>
				<display:column  title="开始日期" sortable="true" sortName="F_begin_date">
					<fmt:formatDate value="${questionnaireItem.begin_date}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</display:column>
				<display:column  title="结束日期" sortable="true" sortName="F_end_date">
					<fmt:formatDate value="${questionnaireItem.end_date}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</display:column>
				<display:column  title="状态" >
				<c:choose>
					<c:when test="${questionnaireItem.status==0}">未开始</c:when>
					<c:when test="${questionnaireItem.status==1}">进行中</c:when>
					<c:when test="${questionnaireItem.status==3}">已结束</c:when>
				</c:choose>
				</display:column>
					<display:column title="管理" media="html" >
					<c:if test="${sessionScope.LOGIN_USER_ID == questionnaireItem.creater_id
					 && questionnaireItem.creater_partin!=null && questionnaireItem.creater_partin==true}">
						<a href="ballot.ht?id=${questionnaireItem.id}" class="oa-button-label edit">投票</a>
					</c:if>
					<c:if test="${sessionScope.LOGIN_USER_ID != questionnaireItem.creater_id}">
						<a href="ballot.ht?id=${questionnaireItem.id}" class="oa-button-label edit">投票</a>
					</c:if>
					<c:if test="${sessionScope.LOGIN_USER_ID == questionnaireItem.creater_id
					 && questionnaireItem.status==0}">
						<a href="del.ht?id=${questionnaireItem.id}" class="oa-button-label del">删除</a>
						<a href="edit.ht?id=${questionnaireItem.id}" class="oa-button-label edit">编辑</a>
					</c:if>
					<c:if test="${sessionScope.LOGIN_USER_ID == questionnaireItem.creater_id}">
						<a href="get.ht?id=${questionnaireItem.id}" class="oa-button-label edit">详情</a>
					</c:if>
				</display:column>
			</display:table>
	    </div>

		<hotent:paging tableId="questionnaireItem"/>

	</div>




</body>
</html>


