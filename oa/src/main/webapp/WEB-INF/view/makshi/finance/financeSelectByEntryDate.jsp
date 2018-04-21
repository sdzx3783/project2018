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

	<h3 class="oa-h3">${year }年${month }月份公司招聘${pageBeanentryItem.totalCount }名员工，根据其学历、职称，拟定工资金额如下：</h3>
    <div class="oa-pdb20 oa-mgh20">
		<div class="oa-table-scroll-wrap">
			<table class="oa-table--default oa-table--nowrap">
				<tr>
					<th rowspan="2">序号</th>
					<th rowspan="2">员工编号</th>
					<th rowspan="2">姓名</th>
					<th rowspan="2">学历</th>
					<th rowspan="2">专业</th>
					<th rowspan="2">毕业时间</th>
					<th rowspan="2">职称</th>
					<th rowspan="2">注册持证情况</th>
					<th rowspan="2">标准工资</th>
					<th colspan="3">试用期工资</th>
					<th rowspan="2">社保标准</th>
					<th rowspan="2">通讯费标准</th>
					<th rowspan="2">籍贯</th>
					<th rowspan="2">项目部及岗位</th>
				</tr>
				<tr>
					<td>基准工资</td>
					<td>岗位工资</td>
					<td>工资总额</td>
				</tr>
				<c:forEach items="${entryList}" var="entry" varStatus="vs">
					<tr>
					<td>${vs.index+1 }</td>
					<td>${entry.account }</td>
					<td>${entry.fullname }</td>
					<td>${entry.education }</td>
					<td>${entry.major }</td>
					<td><fmt:formatDate value="${entry.graduateTime }"   pattern="yyyy-MM-dd" type="date" dateStyle="long" /></td>
					<td>${entry.positional }</td>
					<td>${entry.certificate }</td>
					<td></td>
					<td>${entry.baseSalary }</td>
					<td>${entry.posSalary }</td>
					<td></td>
					<td></td>
					<td></td>
					<td>${entry.address }</td>
					<td>${entry.jobName }</td>
					</tr>
				</c:forEach>

				<c:if test="${empty entryList || fn:length(entryList)<=0}">
					<tr>
						<td>暂无数据...</td>
					</tr>
				</c:if>
				
			</table>
		</div>
		<hotent:paging tableId="entryItem" showExplain="false" showPageSize="10" />
	</div>


<script>
	$(function(){
		window.parent.$(window).trigger('collapseClose', {id: 1});
	});
</script>
</body>
</html>


