<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<table id="tb_common_show"
	class="table table-hover table-striped JCLRFlex JColResizer">
	<thead>
		<tr>
			<th width="100px;">序号</th>
			<th>租房编号</th>
			<th>部门名称</th>
			<th>承租人</th>
			<th>房屋面积（平米）</th>
			<th>房屋租金（元）</th>
			<th>房屋结构</th>
			<th>租房性质</th>
			<th>住宿人数</th>
			<th>报销日期</th>
		</tr>
	</thead>
	<tbody>
		<c:choose>
			<c:when test="${empty list }">
				<tr>
					<td colspan="10" style="text-align: center;">未查询到记录</td>
				</tr>
			</c:when>
			<c:otherwise>
				<c:forEach items="${list }" var="e" varStatus="v">
					<tr>
						<td>${v.count }</td>
						<td>${e.houseId }</td>
						<td>${e.orgpathname }</td>
						<td>${e.rentPerson }</td>
						<td>${e.sizes }</td>
						<td>${e.money }</td>
						<td>${e.houseType }</td>
						<td><c:if test="${e.rentType==0}">未选择</c:if> <c:if
								test="${e.rentType==1}">办公</c:if> <c:if test="${e.rentType==2}">宿舍</c:if>
							<c:if test="${e.rentType==3}">办公兼宿舍</c:if></td>
						<td>${e.numberPeople }</td>
						<td>${fn:substring(e.reimburseDate,0,10) }</td>

					</tr>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</tbody>
</table>
