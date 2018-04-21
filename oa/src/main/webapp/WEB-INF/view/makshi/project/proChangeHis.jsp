<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>项目变更历史</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body class="oa-mw-page">
	<div class="oa-project-title">
        <h2>项目变更历史</h2>
    </div>

	<div class="oa-top-wrap">
		<a class="oa-button oa-button--primary oa-button--blue" href="javascript:window.history.go(-1)">返回</a>
	</div>

	<div class="oa-main-wrap">
		<div class="oa-table-scroll-wrap">
			<display:table  name="changeHisList" id="changeHisItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
				<display:column title="变更字段">
					${changeHisItem.field}
				</display:column>
				<display:column title="变更前">
					${changeHisItem.before}
				</display:column>
				<display:column title="变更后">
					${changeHisItem.after}
				</display:column>
			</display:table>
		</div>
	</div>
</body>
</html>
