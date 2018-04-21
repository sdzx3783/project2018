<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>部门管理</title>
<%@include file="/commons/include/get.jsp" %>
<style>
	.oa-select-wrap{
		min-width: 80px;
	}
	.oa-table{
		table-layout: fixed;
	}
	.oa-table th{
		font-size: 14px;
		font-weight: bold;
		padding: 10px 0;
		width: 180px;
	}
	.oa-table td{
		padding: 10px 20px;
		text-align: center;
	}
	td input,
	td span{
		vertical-align: middle;
	}
</style>
</head>
<body>
	<div class="oa-project-title">
		<h2>部门管理</h2>
	</div>

	<div class="oa-pd20">

		<table class="oa-table">
			<tr>
				<th>部门：</th>
				<td>
					<div class="oa-select-wrap">
						<select id="org" class="oa-select" name="org" onchange="changeOrg()" >
							<option value="10000010330001" <c:if test="${orgId==10000010330001 }">selected="selected"</c:if>>咨询部</option>
							<option value="10000008060007" <c:if test="${orgId==10000008060007 }">selected="selected"</c:if>>招标代理部</option>
							<option value="10000010880097" <c:if test="${orgId==10000010880097 }">selected="selected"</c:if>>环境事业部</option>
							<option value="10000011390000" <c:if test="${orgId==10000011390000 }">selected="selected"</c:if>>水保部</option>
							<option value="10000008480017" <c:if test="${orgId==10000008480017 }">selected="selected"</c:if>>运维部</option>
							<option value="10000014580000" <c:if test="${orgId==10000014580000 }">selected="selected"</c:if>>河道管养部</option>
							<option value="10000000430001" <c:if test="${orgId==10000000430001 }">selected="selected"</c:if>>综合部</option>
							<option value="10000000430002" <c:if test="${orgId==10000000430002 }">selected="selected"</c:if>>监理部</option>
							<option value="40000003690095" <c:if test="${orgId==40000003690095 }">selected="selected"</c:if>>办公室</option>
						</select>
					</div>
				</td>
			</tr>

			<c:forEach items="${resList }" var="res">
				<tr>
					<th>${res.resName}：</th>
					<td>
						<input type="checkbox" <c:if test="${res.resChoose}">checked="checked"</c:if> id="${res.resId }" value="${res.resId }" onchange="changeRes(this)" />
						<span>是否显示</span>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>

<script type="text/javascript">
	function changeOrg(){
		var orgId=$("#org").val();
		window.location="/makshi/sys/orgres/orgreslist.ht?id="+orgId;
	}
	
	function changeRes(obj){
		var orgId=$("#org").val();
		var resId=$(obj).attr("value");
		var checked=false;
		if($(obj).attr("checked")=="checked"){
			checked=true;
		}
		$.post("/makshi/sys/orgres/setOrgRes.ht?orgId="+orgId+"&resId="+resId+"&checked="+checked,
			function(data) {
				window.location.reload();
		});
	}
</script>
</body>
</html>


