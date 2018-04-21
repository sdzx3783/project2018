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
	<h3 class="oa-h3">${year }年${month }月份公司有${pageBeanformalItem.totalCount }名员工转正，拟定工资金额如下：</h3>
    <div class="oa-pdb20 oa-mgh20">
		<div class="oa-table-scroll-wrap">
			<table class="oa-table--default oa-table--nowrap">
				<tr>
					<th>序号</th>
					<th>员工编号</th>
					<th>姓名</th>
					<th>学历</th>
					<th>专业</th>
					<th>毕业时间</th>
					<th>职称</th>
					<th>注册持证情况</th>
					<th>基准工资</th>
					<th>岗位工资</th>
					<th>工资总额</th>
					<th>社保标准</th>
					<th>通讯费标准</th>
					<th>户籍</th>
					<th>项目部及岗位</th>
					<th>备注</th>
				</tr>
				<c:forEach items="${formalList}" var="format" varStatus="vs">
					<tr>
					<td>${vs.index+1 }</td>
					<td>${format.account }</td>
					<td>${format.fullname }</td>
					<td>${format.education }</td>
					<td>${format.major }</td>
					<td><fmt:formatDate value="${format.graduateTime }"   pattern="yyyy-MM-dd" type="date" dateStyle="long" /></td>
					<td>${format.positional }</td>
					<td>${format.certificate }</td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td>${format.address }</td>
					<td>${format.jobName }</td>
					<td></td>
				</tr>
				</c:forEach>
				<c:if test="${empty formalList || fn:length(formalList)<=0}">
					<tr>
						<td>暂无数据...</td>
					</tr>
				</c:if>
			</table>
		</div>
		<hotent:paging tableId="formalItem" showExplain="false" showPageSize="10" />
	</div>


<script>
	$(function(){
		window.parent.$(window).trigger('collapseClose', {id: 2});
	});
</script>
</body>
</html>


