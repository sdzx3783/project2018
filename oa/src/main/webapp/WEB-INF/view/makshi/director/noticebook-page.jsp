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
			<th>建设单位</th>
			<th>工程名称</th>
			<th>任命书编号</th>
			<th width="5%">总监姓名</th>
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
						<td title="${e.applyer }">${e.applyer }</td>
						<td title="${e.department }">${e.department }</td>
						<td title="${fn:substring(e.apply_at,0,10) }">${fn:substring(e.apply_at,0,10) }</td>
						<td title="${e.build_name }">${e.build_name }</td>
						<td title="${e.project_name }">${e.project_name }</td>
						<td title="${e.auto_num }">${e.auto_num }</td>
						<td title="${e.director_name }">${e.director_name }</td>
					</tr>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</tbody>
</table>