<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>历史值班调休统计</title>
<%@include file="/commons/include/customForm.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
</head>
<body class="oa-mw-page">

	<div class="oa-mg20">
	    <a class="oa-button oa-button--primary oa-button--blue" href="list.ht">返回</a>
	</div>

	<div class="oa-mg20">

		<h3 class="oa-h3">历史值班调休统计</h3>
	    <div class="oa-table-scroll-wrap">
		    <display:table name="overTimeList" id="overTimeItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
				<display:column title="姓名">${overTimeItem.name}</display:column>
				<display:column title="年份">${overTimeItem.appliYear}</display:column>
				<display:column title="值班总天数">${overTimeItem.overTimeDays}</display:column>
				<display:column title="调休天数">${overTimeItem.adjustDays}</display:column>
			</display:table>
	    </div>

	</div>

</body>
</html>


