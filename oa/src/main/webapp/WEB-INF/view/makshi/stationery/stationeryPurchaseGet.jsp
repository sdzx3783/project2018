
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>办公用品申购表明细</title>
<%@include file="/commons/include/customForm.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/FlowDetailWindow.js"></script>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body class="oa-mw-page">
    <div class="oa-pd20"></div>
    <div class="oa-mg20">
		<table class="oa-table--default" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
			 <caption>办公用品及耗材申购表</caption>
		     <tr>
			     <th>申请人</th>
			     <td>${stationeryPurchase.user_name}</td>
			     <th>所在部门</th>
			     <td>${stationeryPurchase.first_department}</td>
			  </tr>
			  <tr>
			     <th>工号</th>
			     <td  colspan="3">${stationeryPurchase.account}</td>
			  </tr>
	    </table>
	    <br/>
		<table class="oa-table--default"> 
			 <thead>
			    <tr>
				    <th>名称</th>
				    <th>规格型号</th>
				    <th>数量</th>
				    <th>上月采购</th>
				    <th>上月发出</th>
				    <th>库存</th>
			    </tr>
		    </thead>
		    <c:forEach items="${stationeryStockList}" var="stationery_stock" varStatus="status">
		    	<tr>
		     		<td>${stationery_stock.name} </td>
		     		<td>${stationery_stock.specification} </td>
		     		<td>${stationery_stock.purchase_number} </td>
		     		<td>${stationery_stock.purchase_last_month} </td>
		     		<td>${stationery_stock.send_last_month} </td>
		     		<td>${stationery_stock.stock}</td>
			    </tr>
		   </c:forEach>
		</table>
	</div>
</body>
</html>

