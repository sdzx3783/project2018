
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>资产报废表明细</title>
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
		             <caption>固定资产报废详情</caption>
					 <tbody>
						  <tr class="firstRow">
						      <th>申请人</th>
						      <td>${assetAbandonment.application_name}</td>
						      <th>工号</th>
						      <td>${assetAbandonment.account}</td>
						  </tr>
						  <tr>
						      <th>申请部门</th>
						      <td>${assetAbandonment.department}</td>
						      <th>使用人</th>
						      <td>${assetAbandonment.use_name}</td>
						  </tr>
						  <tr>
						      <th>固定资产编号</th>
						      <td>${assetAbandonment.asset_id}</td>
						      <th>固定资产名称</th>
						      <td>${assetAbandonment.asset_name}</td>
						  </tr>
						  <tr>
						      <th>规格型号</th>
						      <td>${assetAbandonment.specification}</td>
						      <th>数量</th>
						      <td>${assetAbandonment.number}</td>
						  </tr>
						  <tr>
						      <th>质量要求及验收标准</th>
						      <td>${assetAbandonment.standard}</td>
						      <th>固定资产取得方式</th>
						      <td>${assetAbandonment.get_type}</td>
						  </tr>
						  <tr>
						      <th>购入时间</th>
						      <td><fmt:formatDate value='${assetAbandonment.buy_date}' pattern='yyyy-MM-dd'/></td>
						       <th>报废原因</th>
						      <td>${assetAbandonment.reason}</td>
						  </tr>
						  <tr>
						      <th>附件</th>
						      <td  rowspan="1" colspan="3">
						      <input  ctltype="attachment" right="r"   isdirectupload="0"  value='${assetAbandonment.attachment}' validatable="false"  />
						      </td>
						  </tr>
					 </tbody>
				</table>
  	</div>
</body>

</html>
