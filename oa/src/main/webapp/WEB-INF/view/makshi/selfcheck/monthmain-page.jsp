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
			<th>姓名</th>
			<th>时间</th>
			<th>状态</th>
			<th>管理</th>
		</tr>
	</thead>
	<tbody>
		<c:choose>
			<c:when test="${empty list }">
				<tr>
					<td colspan="5" style="text-align: center;">未查询到记录</td>
				</tr>
			</c:when>
			<c:otherwise>
				<c:forEach items="${list }" var="e" varStatus="v">
					<tr>
						<td>
							 ${(page-1)*pageSize+v.count }
						</td>
						<td><a href="/makshi/selfcheck/monthmain/detail.ht?id=${e.id }">${e.username }</a></td>
						<td>${e.submitAt }</td>
						<td>${e.status==1?'未提交':'已提交' }</td>
						<td>
							<c:if test="${e.status==1 }">
							<a href="/makshi/selfcheck/monthmain/edit.ht?id=${e.id }" class="oa-button-label">编辑</a>
							<a href="javascript:del('${e.id }');" class="oa-button-label">删除</a>
							<a href="javascript:submit('${e.id }');" class="oa-button-label">提交</a>
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</tbody>
</table>