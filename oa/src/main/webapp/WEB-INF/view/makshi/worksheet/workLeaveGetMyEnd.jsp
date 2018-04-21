<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>缺勤表管理</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">缺勤表管理列表</span>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="workLevelList" id="workLevelItem" requestURI="getMyDraftJson.ht" sort="external" cellpadding="1" cellspacing="1" class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${workLevelItem.id}">
				</display:column>
				<display:column property="fullname" title="姓名" sortable="true" sortName="F_FULLNAME"></display:column>
				<display:column property="position" title="岗位" sortable="true" sortName="F_POSITION"></display:column>
				<display:column property="type" title="类型" sortable="true" sortName="F_TYPE"></display:column>
				<display:column  title="登记时间" sortable="true" sortName="F_CREATE_TIME">
					<fmt:formatDate value="${workLevelItem.create_time}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column property="remark" title="备注" sortable="true" sortName="F_REMARK"></display:column>
				<display:column  title="缺勤时间" sortable="true" sortName="F_LEAVE_TIME">
					<fmt:formatDate value="${workLevelItem.leave_time}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column title="管理" media="html" style="width:220px">
				   <a href="get.ht?id=${workLevelItem.id}" class="link detail"><span></span>明细</a>
				</display:column>
			</display:table>
			<hotent:paging tableId="workLevelItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


