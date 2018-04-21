<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>历史值班调休统计</title>
<%@include file="/commons/include/customForm.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">历史值班调休统计</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<a class="link back" href="list.ht"><span></span>返回</a>
					</div>
				</div>
			</div>
		</div>
	</div>
	<p style="margin-top: 20px; margin-bottom: 5px;"><span style="font-size: 18px;">历史值班调休统计</span></p> 
		<div class="panel-body">
	    <display:table name="hdOvertimeApplicationsList" id="hdOvertimeApplicationsItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="table-grid">
				<display:column title="姓名">${overTimeItem.name}</display:column>
				<display:column title="年份">${overTimeItem.appliYear}</display:column>
				<display:column title="值班总天数">${overTimeItem.overTimeDays}</display:column>
				<display:column title="调休天数">${overTimeItem.adjustDays}</display:column>
			</display:table>
		</div>
</body>
</html>


