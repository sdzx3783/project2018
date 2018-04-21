
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>加班申请表单明细</title>
<%@include file="/commons/include/customForm.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
<script type="text/javascript"
	src="${ctx}/js/hotent/platform/bpm/FlowDetailWindow.js"></script>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">加班申请表单详细信息</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<div class="group">
							<a class="link detail"
								onclick="FlowDetailWindow({runId:${runId}})" href="javascript:;"><span></span>流程明细</a>
						</div>
						<div class="l-bar-separator"></div>
						<a class="link back" href="list.ht"><span></span>返回</a>
					</div>
				</div>
			</div>
		</div>
	</div>
	<p style="margin-top: 20px; margin-bottom: 5px;"><span style="font-size: 16px;">加班调休统计</span></p>
		<div class="panel-body">
	    <display:table name="hdOvertimeApplications" id="hdOvertimeApplicationsItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="table-grid">
				<display:column title="姓名">${hdOvertimeApplicationsItem.name}</display:column>
				<display:column title="加班总天数">${hdOvertimeApplicationsItem.overTimeDays}</display:column>
				<display:column title="调休天数">${hdOvertimeApplicationsItem.adjustDays}</display:column>
				<display:column title="剩余未调休天数">${hdOvertimeApplicationsItem.leftAdjustDays}</display:column>
			</display:table>
		</div>
		 <p style="margin-top: 20px; margin-bottom: 5px;"><span style="font-size: 16px;">加班明细信息</span></p>
		<div class="panel-body">
		<c:set var="startNum" value="${(pageBeangoodsPurchaseItem.currentPage-1)*pageBeangoodsPurchaseItem.pageSize}"></c:set>
	    <display:table name="overList" id="overListItem" requestURI="#" sort="external" cellpadding="1" cellspacing="1" class="table-grid">
	    <c:set var="startNum" value="${startNum+1}"></c:set>
			<display:column title="序号">${startNum}</display:column>
			<display:column title="加班开始时间"><fmt:formatDate value='${overListItem.startDate}' pattern='yyyy-MM-dd'/>
			<c:if test="${overListItem.morning==0}">上午</c:if>
			<c:if test="${overListItem.morning==1}">下午</c:if>
			</display:column>
			<display:column title="加班结束时间"><fmt:formatDate value='${overListItem.endDate}' pattern='yyyy-MM-dd'/>
			<c:if test="${overListItem.afternoon==0}">上午</c:if>
			<c:if test="${overListItem.afternoon==1}">下午</c:if>
			</display:column>
			<display:column title="加班天数">${overListItem.days}</display:column>
		</display:table>
		</div>
		<p style="margin-top: 20px; margin-bottom: 5px;"><span style="font-size: 16px;">调休明细信息</span></p>
		<div class="panel-body">
		<c:set var="startNum" value="${(pageBeangoodsPurchaseItem.currentPage-1)*pageBeangoodsPurchaseItem.pageSize}"></c:set>
	    <display:table name="adjustList" id="adjustListItem" requestURI="#" sort="external" cellpadding="1" cellspacing="1" class="table-grid">
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
		</tbody>
	</table>
</body>
</html>

