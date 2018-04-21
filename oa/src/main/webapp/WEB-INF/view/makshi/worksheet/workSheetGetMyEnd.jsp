<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>考勤记录管理</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">考勤记录管理列表</span>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="workSheetList" id="workSheetItem" requestURI="getMyDraftJson.ht" sort="external" cellpadding="1" cellspacing="1" class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${workSheetItem.id}">
				</display:column>
				<display:column property="fullname" title="姓名" sortable="true" sortName="F_FULLNAME"></display:column>
				<display:column property="org" title="部门" sortable="true" sortName="F_ORG"></display:column>
				<display:column property="postion" title="岗位" sortable="true" sortName="F_POSTION"></display:column>
				<display:column  title="签到时间" sortable="true" sortName="F_CLOCK_TIME">
					<fmt:formatDate value="${workSheetItem.clock_time}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column property="week" title="星期" sortable="true" sortName="F_WEEK"></display:column>
				<display:column property="type" title="类型" sortable="true" sortName="F_TYPE"></display:column>
				<display:column  title="创建时间" sortable="true" sortName="F_CREATE_TIME">
					<fmt:formatDate value="${workSheetItem.create_time}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column property="remark" title="备注" sortable="true" sortName="F_REMARK"></display:column>
				<display:column title="管理" media="html" style="width:220px">
				   <a href="get.ht?id=${workSheetItem.id}" class="link detail"><span></span>明细</a>
				</display:column>
			</display:table>
			<hotent:paging tableId="workSheetItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


