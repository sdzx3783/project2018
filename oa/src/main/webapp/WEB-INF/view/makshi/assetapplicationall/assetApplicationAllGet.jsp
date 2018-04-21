
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>资产申购信息</title>
<%@include file="/commons/include/customForm.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/FlowDetailWindow.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/AttachMent.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/TablePermission.js"></script>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
<body class="oa-mw-page">
	<div class="oa-pd20"></div>
	<table class="oa-table--default" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
		<caption>资产申请</caption>
  		<tbody> 
	  		<tr>  
	   			<th>申请人 </th> 
	   			<td>${assetApplicationAll.user_name}</td> 
	   			<th>工号</th> 
	   			<td>${assetApplicationAll.account}</td>
     		</tr> 
     		<tr>  
	   			<th>使用人</th> 
	   			<td>${assetApplicationAll.use_name}</td> 
	   			<th>申请部门</th> 
	   			<td>${assetApplicationAll.fist_department}</td>
     		</tr> 
     		<tr>  
	   			<th>固定资产类别 </th> 
	   			<td>${assetApplicationAll.assetType}</td> 
	   			<th></th> 
	   			<td></td>
     		</tr> 
     		<tr>  
	   			<th>固定资产名称</th> 
	   			<td>${assetApplicationAll.asset_name}</td> 
	   			<th>规格型号</th> 
	   			<td>${assetApplicationAll.specifications}</td>
     		</tr> 
     		<tr>  
	   			<th>数量 </th> 
	   			<td>${assetApplicationAll.number}</td> 
	   			<th>质量要求及验收标准</th> 
	   			<td>${assetApplicationAll.standard}</td>
     		</tr>
     		<tr>  
	   			<th>类型</th> 
	   			<td colspan = "3">
	   				<c:if test="${assetApplicationAll.type==0}">领用旧设备</c:if>
	   				<c:if test="${assetApplicationAll.type==1}">购置新设备</c:if>
	   			</td> 
	   			<th></th> 
	   			<td></td>
     		</tr> 
     		<tr>  
	   			<th>采购方式</th> 
	   			<td colspan = "3">
	   				<c:if test="${assetApplicationAll.buy_type==0}">委托办公室</c:if>
	   				<c:if test="${assetApplicationAll.buy_type==1}">部门自行采购</c:if>
	   			</td> 
     		</tr>  
 		</tbody>
	</table>
</body>
</html>

