<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<table id="tb_common_show"
	class="table table-hover table-striped JCLRFlex JColResizer">
	<thead>
		<tr>
			<th width="5%">序号</th>
			<th width="5%">申请人</th>
			<th>申请部门</th>
			<th width="7%">申请时间</th>
			<th width="14%">工程名称</th>
			<th width="14%">建设单位</th>
			<th width="5%">授权总监</th>
			<th width="14%">监理工程师注册号</th>
			<th width="14%">项目总监理工程师</th>
			<th width="8%">任命书编号</th>
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
						<td>
							 ${(page-1)*pageSize+v.count }
						</td>
						<td title="${e.applyer }" >${e.applyer }</td>
						<td title="${e.department }" >${e.department }</td>
						<td title="${fn:substring(e.apply_at,0,10)}" >${fn:substring(e.apply_at,0,10) }</td>
						<td title="${e.engineering }" >${e.engineering }</td>
						<td title="${e.build_unit }" >${e.build_unit }</td>
						<td title="${e.director_name }" >${e.director_name }</td>
						<td title="${e.register_num }" >${e.register_num }</td>
						<td title="${e.engineer_name }" >${e.engineer_name }</td>
						<td title="${e.appointment }" >${e.appointment }</td>
					</tr>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</tbody>
</table>