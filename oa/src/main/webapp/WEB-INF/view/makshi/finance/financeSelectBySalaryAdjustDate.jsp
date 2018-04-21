<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>当月入职员工工资单</title>
<%@include file="/commons/include/get.jsp"%>
<%@ taglib prefix="hotent" uri="http://www.jee-soft.cn/paging"%>
<style>
	body{
		height: auto;
	}
</style>
</head>
<body class="oa-mw-page">
	<h3 class="oa-h3">${year }年${month }月份有${pageBeanadjustItem.totalCount }名员工因职务任命或工作岗位变动或取得注册资质等，基准工资、岗位工资及通讯标准拟需调整：</h3>
	
	<div class="oa-pdb20 oa-mgh20">
		<div class="oa-table-scroll-wrap">
		    <table class="oa-table--default oa-table--nowrap">
				<tr>
					<th>序号</th>
					<th>员工编号</th>
					<th>姓名</th>
					<th>学历</th>
					<th>原基准工资</th>
					<th>原岗位工资</th>
					<th>调前工资总额</th>
					<th>调后基准工资</th>
					<th>调后岗位工资</th>
					<th>调后工资总额</th>
					<th>调后通讯标准</th>
					<th>项目部及岗位</th>
					<th>备注</th>
				</tr>
				<c:forEach items="${adjustList}" var="adjust" varStatus="vs">
					<tr>
						<td>${vs.index+1 }</td>
						<td>${adjust.account }</td>
						<td>${adjust.fullname }</td>
						<td>${adjust.education }</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td>${adjust.jobName }</td>
						<td></td>
					</tr>
				</c:forEach>
				<c:if test="${empty adjustList || fn:length(adjustList)<=0}">
					<tr>
						<td>暂无数据...</td>
					</tr>
				</c:if>
			</table>
		</div>
		<hotent:paging tableId="adjustItem" showExplain="false" showPageSize="10" />
	</div>


<script>
	$(function(){
		window.parent.$(window).trigger('collapseClose', {id: 3});
	});
</script>
</body>
</html>


