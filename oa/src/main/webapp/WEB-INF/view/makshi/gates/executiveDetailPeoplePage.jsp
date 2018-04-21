<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<table id="tb_common_show"
	class="table table-hover table-striped JCLRFlex JColResizer">
	<thead>
		<tr>
			<th>序号</th>
			<th>员工编号</th>
			<th>姓名</th>
			<th>性别</th>
			<th>年龄</th>
			<th>学历</th>
			<c:if test="${querytype=='people' || querytype=='age' }">
				<th>职称</th>
			</c:if>
			<c:if test="${querytype=='education' }">
				<th>毕业院校</th>
				<th>专业</th>
			</c:if>
			<c:if test="${querytype=='positional' }">
				<th>职称</th>
				<th>职称专业</th>
			</c:if>
			<th>入职日期</th>
			<th>岗位</th>
		</tr>
	</thead>
	<tbody>
		<c:choose>
			<c:when test="${empty list }">
				<tr>
					<c:if test="${querytype=='people' || querytype=='age' }">
						<td colspan="9" style="text-align: center;">未查询到记录</td>
					</c:if>
					<c:if test="${querytype=='education' || querytype=='positional'}">
						<td colspan="10" style="text-align: center;">未查询到记录</td>
					</c:if>
				</tr>
			</c:when>
			<c:otherwise>
				<c:forEach items="${list }" var="e" varStatus="v">
					<tr>
						<td>
							 ${(page-1)*pageSize+v.count }
						</td>
						<td><a href="/platform/system/sysUser/get.ht?userId=${e.userId }">${e.jobno }</a></td>
						<td><a href="/platform/system/sysUser/get.ht?userId=${e.userId }">${e.name }</a></td>
						<td>${e.sex==1?'男':'女' }</td>
						<td>${e.ages==0?'':e.ages }</td>
						<td>${e.education }</td>
						<c:if test="${querytype=='people' || querytype=='age' }">
							<td>${e.positional }</td>
						</c:if>
						<c:if test="${querytype=='education'}">
							<td>${e.school }</td>
							<td>${e.schoolMajor }</td>
						</c:if>
						<c:if test="${querytype=='positional'}">
							<td>${e.positional }</td>
							<td>${e.positionalMajor }</td>
						</c:if>
						<td>${e.entrydate }</td>
						<td>${e.posname }</td>
					</tr>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</tbody>
</table>