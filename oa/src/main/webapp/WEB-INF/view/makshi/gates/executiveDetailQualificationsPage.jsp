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
			<th>证书编号</th>
			<th>证书名称</th>
			<th>证书类型</th>
			<th>发证机构</th>
			<th>发证时间</th>
			<th>有效期限</th>
		</tr>
	</thead>
	<tbody>
		<c:choose>
			<c:when test="${empty list }">
				<tr>
						<td colspan="7" style="text-align: center;">未查询到记录</td>
				</tr>
			</c:when>
			<c:otherwise>
				<c:forEach items="${list }" var="e" varStatus="v">
					<tr>
						<td>
							 ${(page-1)*pageSize+v.count }
						</td>
						<td><a href="/makshi/qualification/companyQualificationCertificate/get.ht?id=${e.id }" target="_blank">${e.cno }</a></td>
						<td>${e.cname }</td>
						<td>${e.typeDesc }</td>
						<td>${e.institution }</td>
						<td>${fn:substring(e.certificationtime,0,10) }</td>
						<td>${fn:substring(e.certificationlimit,0,10) }</td>
					</tr>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</tbody>
</table>