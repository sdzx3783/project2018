<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>值班调休统计</title>
<%@include file="/commons/include/customForm.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
</head>
<body class="oa-mw-page">
	
	<div class="oa-mg20">
	    <a class="oa-button oa-button--primary oa-button--blue" href="list.ht">返回</a>
	</div>

	<div class="oa-mg20">
		<h3 class="oa-h3">值班调休管理信息</h3>
		<div class="oa-table-scroll-wrap">
		    <display:table name="overTimeList" id="overTimeItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
				<display:column title="姓名">${overTimeItem.name}</display:column>
				<display:column title="值班总天数">${overTimeItem.overTimeDays}</display:column>
				<display:column title="调休总天数">${overTimeItem.adjustDays}</display:column>
				<display:column title="剩余未调休天数">${overTimeItem.leftAdjustDays}</display:column>
			</display:table>
		</div>
	</div>
	
	<div class="oa-mg20">
		<h3 class="oa-h3">值班明细信息</h3>
		<div class="oa-table-scroll-wrap">
			<c:set var="startNum" value="${(pageBeangoodsPurchaseItem.currentPage-1)*pageBeangoodsPurchaseItem.pageSize}"></c:set>
		    <display:table name="overList" id="overListItem" requestURI="#" sort="external" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
		    	<c:set var="startNum" value="${startNum+1}"></c:set>
				<display:column title="序号">${startNum}</display:column>
				<display:column title="值班开始时间"><fmt:formatDate value='${overListItem.startDate}' pattern='yyyy-MM-dd'/>
					<c:if test="${overListItem.morning==0}">上午</c:if>
					<c:if test="${overListItem.morning==1}">下午</c:if>
				</display:column>
				<display:column title="值班结束时间"><fmt:formatDate value='${overListItem.endDate}' pattern='yyyy-MM-dd'/>
					<c:if test="${overListItem.afternoon==0}">上午</c:if>
					<c:if test="${overListItem.afternoon==1}">下午</c:if>
				</display:column>
				<display:column title="值班天数">${overListItem.days}</display:column>
			</display:table>
		</div>
	</div>
	
	<div class="oa-mg20">
		<h3 class="oa-h3">调休明细信息</h3>
		<div class="oa-table-scroll-wrap">
			<c:set var="startNum" value="${(pageBeangoodsPurchaseItem.currentPage-1)*pageBeangoodsPurchaseItem.pageSize}"></c:set>
		    <display:table name="adjustList" id="adjustListItem" requestURI="#" sort="external" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
		     	<c:set var="startNum" value="${startNum+1}"></c:set>
				<display:column title="序号">${startNum}</display:column>
				<display:column title="调休开始时间"><fmt:formatDate value='${adjustListItem.startDate}' pattern='yyyy-MM-dd'/>
					<c:if test="${adjustListItem.morning==0}">上午</c:if>
					<c:if test="${adjustListItem.morning==1}">下午</c:if>
				</display:column>
				<display:column title="调休结束时间"><fmt:formatDate value='${adjustListItem.endDate}' pattern='yyyy-MM-dd'/>
					<c:if test="${adjustListItem.afternoon==0}">上午</c:if>
					<c:if test="${adjustListItem.afternoon==1}">下午</c:if>
				</display:column>
				<display:column title="调休天数">${adjustListItem.days}</display:column>
			</display:table>
		</div>
	</div>

</body>
</html>


