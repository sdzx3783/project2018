<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>管理</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body class="oa-mw-page">

	<div id="oa_list_title" class="oa-mgb20 oa-project-title">
		<h2>运营周报管理列表</h2>
	</div>
	
	<div id="oa_list_search" class="oa-pdb10 oa-mgh20">
		<form class="oa-clear" id="searchForm" method="post" action="list.ht">
			<div class="oa-fl oa-mgb10">
				<div class="oa-label">周报名称</div>
				<div class="oa-input-wrap oa-mgl100">
					<input type="text" name="Q_n_name_SL"  class="oa-input"  value="${param['Q_n_name_SL']}"/>
				</div>
			</div>
			<div class="oa-fl oa-mgb10">
				<div class="oa-label">项目名称</div>
				<div class="oa-input-wrap oa-mgl100">
					<input type="text" name="Q_project_name_SL"  class="oa-input"  value="${param['Q_project_name_SL']}"/>
				</div>
			</div>
			<div class="oa-fl oa-mgb10">
				<div class="oa-label">提交人</div>
				<div class="oa-input-wrap oa-mgl100">
					<input type="text" name="Q_name_SL"  class="oa-input"  value="${param['Q_name_SL']}"/>
				</div>
			</div>
			<div class="oa-fl oa-mgb10">
				<div class="oa-label">所属部门</div>
				<div class="oa-input-wrap oa-mgl100">
					<input type="text" name="Q_department_SL"  class="oa-input"  value="${param['Q_department_SL']}"/>
				</div>
			</div>
			<div class="oa-fl oa-mgb10 oa-mgh20">
				<button class="oa-button oa-button--primary oa-button--blue">查询</button>
			</div>
		</form>
	</div>
	
	<div class="oa-pdb20 oa-mgh20">
		<div id="oa_list_content" class="oa-table-scroll-wrap">
		    <display:table name="hjRunWeeklyList" id="hjRunWeeklyItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
				<c:set var="startNum" value="${startNum+1}"></c:set>
				<display:column title="序号">
			  		${startNum}
				</display:column>
				<display:column title="周报名称">
			  		${hjRunWeeklyItem.n_name}
				</display:column>
				<display:column title="周报内容">
			  		${hjRunWeeklyItem.content}
				</display:column>
				<display:column title="项目名称">
			  		${hjRunWeeklyItem.project_name}
				</display:column>
				<display:column title="提交人">
			  		${hjRunWeeklyItem.name}
				</display:column>
				<display:column title="所属部门">
			  		${hjRunWeeklyItem.department}
				</display:column>
				<display:column title="提交日期">
			  		<fmt:formatDate value='${hjRunWeeklyItem.date}' pattern='yyyy-MM-dd'/>
				</display:column>
				<display:column title="备注">
			  		${hjRunWeeklyItem.note}
				</display:column>
				<display:column title="管理" media="html">
					<a href="get.ht?id=${hjRunWeeklyItem.id}" class="oa-button-label">明细</a>
				</display:column>
			</display:table>
		</div>
		<hotent:paging tableId="hjRunWeeklyItem"/>
	</div>
</body>
</html>


