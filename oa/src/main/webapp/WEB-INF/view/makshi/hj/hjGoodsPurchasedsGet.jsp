
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>物品明细</title>
<%@include file="/commons/include/customForm.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
<script type="text/javascript"
	src="${ctx}/js/hotent/platform/bpm/FlowDetailWindow.js"></script>
</head>
<body class="oa-mw-page">
	

	<div class="oa-top-wrap">
	    <a class="oa-button oa-button--primary oa-button--blue" onclick="FlowDetailWindow({runId:${runId}})">流程明细</a>
	    <a class="oa-button oa-button--primary oa-button--blue" href="list.ht">返回</a>
	</div>

	<div class="oa-main-wrap">
		<table class="oa-table--default" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
			<caption>物品采购</caption>
			<tr>
				<td>申请人:</td>
				<td>${hjGoodsPurchaseds.applicant}</td>
				<td>申请日期:</td>
				<td>
					<fmt:formatDate value='${hjGoodsPurchaseds.appli_date}' pattern='yyyy-MM-dd' />
				</td>
			</tr>
			<tr>
				<td>申请部门:</td>
				<td>${hjGoodsPurchaseds.appli_department}</td>
				<td>采购方式:</td>
				<td>
					<c:if test="${hjGoodsPurchaseds.type==0}">请选择采购方式</c:if>
					<c:if test="${hjGoodsPurchaseds.type==1}">委托办公室</c:if>
					<c:if test="${hjGoodsPurchaseds.type==2}">部门自行采购</c:if>
				</td>
			</tr>
			<tr>
				<td>物品民称:</td>
				<td>
					${hjGoodsPurchaseds.name}</td>
				<td>规格型号:</td>
				<td>${hjGoodsPurchaseds.specification}</td>
			</tr>
			<tr>
				<td>数量:</td>
				<td>${hjGoodsPurchaseds.number}</td>
				<td>单价:</td>
				<td>${hjGoodsPurchaseds.price}</td>
			</tr>
			<tr>
				<td>采购日期:</td>
				<td>
					<fmt:formatDate value='${hjGoodsPurchaseds.date}' pattern='yyyy-MM-dd'/>
				</td>
				<td>领用人:</td>
				<td>${hjGoodsPurchaseds.lname}</td>
			</tr>
			<tr>
				<td>领用部门:</td>
				<td>${hjGoodsPurchaseds.ldepartment}</td>
				<td>备注:</td>
				<td>${hjGoodsPurchaseds.remarks}</td>
			</tr>
			
			<tr>
				<td>附件:</td>
				<td rowspan="1" colspan="3">
					<div name="div_attachment_container" right="r">
						<div class="attachement"></div>
						<textarea style="display: none" controltype="attachment"
							name="attachment" lablename="附件" readonly="readonly">${hjGoodsPurchaseds.attachment}</textarea>
					</div>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>

