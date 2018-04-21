<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<table id="tb_common_show"
	class="table table-hover table-striped JCLRFlex JColResizer">
	<thead>
		<tr>
			<th width="50px">序号</th>
			<th>合同编号</th>
			<th width="200px">合同名称</th>
			<th width="80px">合同类型</th>
			<th width="80px">合同状态</th>
			<th>合同金额(万元)</th>
			<th>甲方</th>
			<th>合同归属部门</th>
			<th>项目部</th>
			<th>合同经手人</th>
			<th>合同批签人</th>
			<th>签约时间</th>
			<th>开工时间</th>
			<th>完工时间</th>
		</tr>
	</thead>
	<tbody>
		<c:choose>
			<c:when test="${empty list }">
				<tr>
					<td colspan="14" style="text-align: center;">未查询到记录</td>
				</tr>
			</c:when>
			<c:otherwise>
				<c:forEach items="${list }" var="e" varStatus="v">
					<tr>
						<td>
							 ${(page-1)*pageSize+v.count }
						</td>
						<td><a href="/makshi/contract/contractinfo/get.ht?id=${e.contract_id }" target="_blank">${e.contract_num }</a></td>
						<td title='${e.contract_name }'><a href="/makshi/contract/contractinfo/get.ht?id=${e.contract_id }" target="_blank">${e.contract_name }</a></td>
						<td>${e.contracttype }</td>
						<td>
							${e.contract_status==1?'作废':'正式合同' }
						</td>
						<td>${e.contract_money }</td>
						<td title='${e.first_party }'>${e.first_party }</td>
						<td>${e.contract_department }</td>
						<td>${e.project_department }</td>
						<td>${e.contract_handler }</td>
						<td>${e.contract_authorized_person }</td>
						<td>
							${fn:substring(e.singing_time,0,10) }
						</td>
						<td>
							${fn:substring(e.start_time,0,10) }
						</td>
						<td>
							${fn:substring(e.end_time,0,10) }
						</td>
					</tr>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</tbody>
</table>