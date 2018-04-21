
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>资产移交表明细</title>
<%@include file="/commons/include/customForm.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/FlowDetailWindow.js"></script>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body class="oa-mw-page">
	    <div class="oa-mg20">
    	    	<table class="oa-table--default" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
		             <caption>资产移交表</caption>
					 <tbody>
						  <tr class="firstRow">
						      <th>申请人</th>
						      <td>${assetTransfer.application_Person}</td>
						      <th>工号</th>
						      <td>${assetTransfer.account}</td>
						  </tr>
						  <tr>
						      <th>移交人</th>
						      <td>${assetTransfer.transfer_person}</td>
						      <th>移交部门</th>
						      <td>${assetTransfer.transfer_first_department}</td>
						  </tr>
						  <tr>
						      <th>接交人</th>
						      <td>${assetTransfer.receiption_person}</td>
						      <th>接交部门</th>
						      <td>${assetTransfer.receiption_department}</td>
						  </tr>
						  <tr>
						      <th>资产编号</th>
						      <td>${assetTransfer.asset_id}</td>
						      <th>固定资产名称</th>
						      <td>${assetTransfer.asset_name}</td>
						  </tr>
						  <tr>
						      <th>规格型号</th>
						      <td>${assetTransfer.specifications}</td>
						      <th>数量</th>
						      <td>${assetTransfer.number}</td>
						  </tr>
						  <tr>
						      <th>移交原因</th>
						      <td>${assetTransfer.reason}</td>
						      <th>移交日期</th>
						      <td><fmt:formatDate value='${assetTransfer.time}' pattern='yyyy-MM-dd'/></td>
						  </tr>
						  <tr>
						      <th>附件</th>
						      <td  rowspan="1" colspan="3">
						      <input  ctltype="attachment" right="r"   isdirectupload="0"  value='${assetTransfer.attachment}' validatable="false"  />
						      </td>
						  </tr>
					 </tbody>
				</table>
  	</div>
</body>

</html>
