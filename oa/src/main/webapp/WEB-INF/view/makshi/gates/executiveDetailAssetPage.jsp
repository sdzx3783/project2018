<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<table id="tb_common_show"
	class="table table-hover table-striped JCLRFlex JColResizer">
	<thead>
		<tr>
			<th width="80px">序号</th>
			<th>使用部门</th>
			<th>资产编号</th>
			<th>资产名称</th>
			<th>规格型号</th>
			<th>资产类别</th>
			<th>条码编号</th>
			<th>保管人</th>
		</tr>
	</thead>
	<tbody>
		<c:choose>
			<c:when test="${empty list }">
				<tr>
						<td colspan="8" style="text-align: center;">未查询到记录</td>
				</tr>
			</c:when>
			<c:otherwise>
				<c:forEach items="${list }" var="e" varStatus="v">
					<tr>
						<td>
							 ${(page-1)*pageSize+v.count }
						</td>
						<td>${e.orgpathname }</td>
						<td>${e.asset_id }</td>
						<td>${e.asset_name }</td>
						<td>${e.specification }</td>
						<td>${e.version }</td>
						<td>${e.card_number }</td>
						<td>${e.care_person }</td>
					</tr>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</tbody>
</table>