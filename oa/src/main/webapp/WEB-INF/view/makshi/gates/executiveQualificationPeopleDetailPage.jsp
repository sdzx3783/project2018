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
			<th width="80px">员工编号</th>
			<th width="100px">姓名</th>
			<th width="80px;">性别</th>
			<th>证书类型</th>
			<th>证书编号</th>
			<c:choose>
				<c:when test="${type=='4' }">
					<th>初始日期</th>
					<th>转出日期</th>
				</c:when>
				<c:when test="${type=='5' }">
					<th>发证日期</th>
				</c:when>
				<c:otherwise>
					<th>初始日期</th>
					<th>转入日期</th>
				</c:otherwise>
			</c:choose>
			<th>发证单位</th>
		</tr>
	</thead>
	<tbody>
		<c:choose>
			<c:when test="${empty list }">
				<tr>
					<c:set value="9" var="col"></c:set>
					<c:choose>
						<c:when test="${type=='4' }">
							<c:set value="9" var="col"></c:set>
						</c:when>
						<c:when test="${type=='5' }">
							<c:set value="8" var="col"></c:set>
						</c:when>
						<c:otherwise>
							<c:set value="9" var="col"></c:set>
						</c:otherwise>
					</c:choose>
					<td colspan="${col }" style="text-align: center;">未查询到记录</td>
				</tr>
			</c:when>
			<c:otherwise>
				<c:forEach items="${list }" var="e" varStatus="v">
					<tr>
						<td>
							 ${(page-1)*pageSize+v.count }
						</td>
						<td><a href="/platform/system/sysUser/get.ht?userId=${e.userId }" target="_blank">${e.account }</a></td>
						<td>${e.name }</td>
						<td>${e.sex}</td>
						<td>
							<c:choose>
								<c:when test="${e.type=='1' }">
								三类人员安全生产考核合格证
								</c:when>
								<c:when test="${e.type=='2' }">
								深圳市监理员
								</c:when>
								<c:when test="${e.type=='3' }">
								深圳市监理工程师
								</c:when>
								<c:when test="${e.type=='4' }">
								水利部监理工程师信用手册
								</c:when>
								<c:when test="${e.type=='5' }">
								建设部监理工程师信用手册
								</c:when>
								<c:when test="${e.type=='6' }">
								深圳市档案员
								</c:when>
								<c:otherwise>
									${e.type }
								</c:otherwise>
							</c:choose>
						</td>
						<td>
							<a href="/makshi/title/vocationQualification/edit.ht?id=${e.id }" target="_blank">${e.certificateNo }</a>
						</td>
						<c:choose>
							<c:when test="${type=='4' }">
								<td>${fn:substring(e.initDate,0,10) }</td>
								<td>${fn:substring(e.outDate,0,10)}</td>
							</c:when>
							<c:when test="${type=='5' }">
								<td>${fn:substring(e.getDate,0,10) }</td>
							</c:when>
							<c:otherwise>
								<td>${fn:substring(e.initDate,0,10) }</td>
								<td>${fn:substring(e.inDate,0,10)}</td>
							</c:otherwise>
						</c:choose>
						<td>${e.sendUnit }</td>
					</tr>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</tbody>
</table>