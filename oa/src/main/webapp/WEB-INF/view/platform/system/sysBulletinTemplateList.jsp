<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>公告模板管理</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<a class="link search" id="btnSearch"><span></span>查询</a>
					</div>
						<div class="l-bar-separator"></div>
						<div class="group">
							<a class="link add" href="edit.ht"><span></span>添加</a>
						</div>
						<div class="l-bar-separator"></div>
						<div class="group">
							<a class="link del" action="del.ht"><span></span>删除</a>
						</div>
				</div>
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht">
					<div class="row">
						<span class="label">名称:</span><input type="text" name="Q_name_SL" value="${param['Q_name_SL']}" class="inputText" />
						<span class="label">别名:</span><input type="text" name="Q_alias_SL" value="${param['Q_alias_SL']}" class="inputText" />
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="sysbulletintemplateList" id="sysbulletintemplateItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${sysbulletintemplateItem.id}">
				</display:column>
				<display:column property="name" title="名称" sortable="true" sortName="name"></display:column>
				<display:column property="alias" title="别名" sortable="true" sortName="alias"></display:column>
				<display:column title="管理" media="html" style="width:220px">
					<a href="edit.ht?id=${sysbulletintemplateItem.id}" class="link edit"><span></span>编辑</a>
					<a href="del.ht?id=${sysbulletintemplateItem.id}" class="link del"><span></span>删除</a>
				    <a href="" onclick="$.openFullWindow('get.ht?id=${sysbulletintemplateItem.id}')" class="link detail"><span></span>查看</a>
				</display:column>
			</display:table>
			<hotent:paging tableId="sysbulletintemplateItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


