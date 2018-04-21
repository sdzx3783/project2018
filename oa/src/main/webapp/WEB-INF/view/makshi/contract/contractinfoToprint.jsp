<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<title>合同打印预览</title>
<%@include file="/commons/include/customForm.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/FlowDetailWindow.js"></script>
<style type="text/css">
@media print{ 
	.noprint{
		display: none;
	} 
	.oa-table{
		margin-top: 60px;
	}
	.my-w-100 {
		width: auto;
	}
	.my-w-100.w20 {
		width: 15%;
	}
	.my-w-200 {
		width: auto;
	}
	.my-w-200.w20 {
		width: 20%;
	}
	.my-w-80 {
		width: auto;
	}
	.my-r {
		text-align: left;
	}
}
body{
	font-size: 16px;
}
table{
	table-layout: fixed;
	margin-bottom: 30px;
}
h1{
	text-align: center;
	margin-bottom: 30px;
}
dl dt{
	float: left;
}
dl dd{
	margin-left: 3em;
}


.oa-table--print{
	width: 100%;
}
caption{
	 font-weight: bold;
	 margin-bottom: 10px;
}
.oa-table--print td{
	padding: 10px 10px 0 10px;
	border-bottom: 1px solid #333;
}
.oa-table--print th{
	padding: 10px 10px 0 10px;
}
.base-information th{
	white-space: nowrap;
}
.inner-box{
	position: relative;
}
.inner-box span{
	position: absolute;
	top: 0;
	right: 0;
}
.oa-table--grid th,
.oa-table--grid td{
	padding: 10px;
	border: 1px solid #333;
}
.w5{
	width: 5%;
}
.w10{
	width: 10%;
}
.w15{
	width: 15%;
}
.w20{
	width: 20%;
}
.w30{
	width: 30%;
}
.w40{
	width: 40%;
}
.w50{
	width: 50%;
}

.space{
	display: inline-block;
	width: 2em;
}
.my-w-100 {
	width: 66px;
}
.my-w-200 {
	width: 220px;
}
.my-w-80 {
	width: 80px;
}
.my-r {
	text-align: right;
}
.my-l {
	text-align: right;
}
</style>
</head>
<body>

	<div class="oa-pd20 oa-pdb0 noprint" >
		<a class="oa-button oa-button--primary oa-button--blue" onclick="window.print();">打印</a>	
	</div>	

<div class="oa-pd20">
	<h1>
		${fn:substring(fn:substringBefore(contractinfo.contract_num, "-"),fn:length(fn:substringBefore(contractinfo.contract_num, "-"))-4, fn:length(fn:substringBefore(contractinfo.contract_num, "-")))}年合同执行情况表
	</h1>
	
	<table class="oa-table--print base-information">
		<caption>合同执行情况之一：基本资料</caption>
		<tr>
			<th class="w15 my-w-100">合同名称：</th>
			<td colspan="3" width="50%">${contractinfo.contract_name }</td>
			<th class="w15 my-w-100">合同类型：</th>
			<td>${contractinfo.contracttype} ${contractinfo.type}</td>
		</tr>
		<tr>
			<th class="w15 my-w-100">甲<span class="space"></span>方：</th>
			<td colspan="3">${contractinfo.first_party}</td>
			<th class="w15 my-w-100">合同编号：</th>
			<td>${contractinfo.contract_num}</td>
		</tr>
		<tr>
			<th class="w15 my-w-100">乙<span class="space"></span>方：</th>
			<td colspan="3">${contractinfo.second_party}</td>
			<th class="w15 my-w-100">签约时间：</th>
			<td><fmt:formatDate value='${contractinfo.singing_time}' pattern='yyyy-MM-dd'/></td>
		</tr>
		<tr>
			<th>项目总监：</th>
			<td>${contractinfo.project_director}</td>
			<th class="my-r">合同归属部门：</th>
			<td>${contractinfo.contract_department}</td>
			<th>投资总额：</th>
			<td>
				<div class="inner-box">
					${contractinfo.total_investment}
					<span>万元</span>
				</div>
			</td>
		</tr>
		<tr>
			<th>项目负责人：</th>
			<td>${contractinfo.project_leader}</td>
			<th  class="my-r">合同经手人：</th>
			<td>${contractinfo.contract_handler}</td>
			<th>合同金额：</th>
			<td>
				<div class="inner-box">
					${contractinfo.contract_money}
					<span>万元</span>
				</div>
			</td>
		</tr>
		<tr>
			<th>合同审核人：</th>
			<td>${contractinfo.contract_reviewer}</td>
			<th  class="my-r">合同批签人：</th>
			<td>${contractinfo.contract_authorized_person}</td>
			<th>结算金额：</th>
			<td>
				<div class="inner-box">
					${contractinfo.settlement_money}
					<span>万元</span>
				</div>
			</td>
		</tr>
	</table>

	<table class="oa-table--print">
		<caption>合同执行情况之二：文件传递</caption>
		<tr>
			<th class="" width="25%">合同签收：办公室：</th>
			<td>
				<div class="inner-box">
					${contractinfo.fj_sencond_copies}
					<span>份</span>
				</div>
			</td>
			<th class="w20 my-w-80">资料室：</th>
			<td>
				<div class="inner-box">
					<br>
					<span>份</span>
				</div>
			</td>
			<th class="w20 my-w-80">项目部：</th>
			<td>
				<div class="inner-box">
					${contractinfo.fj_sencond_copies}<span>份</span>
				</div>
			</td>
		</tr>
		<tr>
			<th class="my-w-200">本表签收：办公室：</th>
			<td><br></td>
			<th class="my-w-80">资料室：</th>
			<td><br></td>
			<th class="my-w-80">项目部：</th>
			<td><br></td>
		</tr>

		<tr>
			<th class="my-w-200">竣工资料归档签收：  资料室：</th>
			<td><c:choose><c:when test="${contractinfo.issave==1}">已存档</c:when><c:otherwise>未存档</c:otherwise></c:choose></td>
			<td><br></td>
			<th class="my-w-200 my-r">本项目是否备案：</th>
			<td><c:choose><c:when test="${contractinfo.isrecord==1}">已备案</c:when><c:otherwise>未备案</c:otherwise></c:choose></td>
			<td><br></td>
		</tr>
		<tr>
			<th class="my-w-200">项目部经手人变更签收：</th>
			<td><br></td>
			<td><br></td>
			<td><br></td>
			<td><br></td>
			<td><br></td>
		</tr>
	</table>

	<table class="oa-table--print oa-table--grid">
		<caption>合同执行情况之三：收款记录</caption>
		<tr>
			<th class="w5">序号</th>
			<th class="w20">摘要</th>
			<th>开票时间</th>
			<th>发票金额</th>
			<th>经办人</th>
			<th>到帐时间</th>
			<th>到帐金额</th>
			<th>备注</th>
		</tr>
		<c:forEach items="${contractinfo.contractBillingRecordList}" var="contractBillingRecord" varStatus="status">
			<tr>
				<td>${status.index+1 }</td>
				<td></td>
				<td><fmt:formatDate value='${contractBillingRecord.billing_date}' pattern='yyyy-MM-dd'/></td>
				<td>${contractBillingRecord.invoice_money}</td>
				<td>${contractBillingRecord.operator}</td>
				<td><fmt:formatDate value='${contractBillingRecord.payment_date}' pattern='yyyy-MM-dd'/></td>
				<td>${contractBillingRecord.arrival_money}</td>
				<td></td>
			</tr>
		</c:forEach>
	</table>


	<dl>
		<dt>注：</dt>
		<dd>
			<ol>
				<li>1、本表一式两份，由合同签订经办人负责前期填写，办公室、项目部各执一份；</li>
				<li>2、本表后续内容由项目总监或负责人跟踪填写；</li>
				<li>3、到办公室开具发票或办理本合同相关事宜时请带上此表；</li>
				<li>4、本表请妥善保管，保存期10年。</li>
			</ol>
		</dd>
	</dl>


</div>			

</body>
</html>
