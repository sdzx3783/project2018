
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>公章外借申请表明细</title>
<%@include file="/commons/include/customForm.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/FlowDetailWindow.js"></script>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body class="oa-mw-page">
	<div class="oa-mg20">
		<!-- <a class="oa-button oa-button--primary oa-button--blue" href="list.ht">返回</a> -->
	</div>
	<table class="oa-table--default" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
	<caption>公章外借申请表详细信息</caption>
	 <tbody> 
  		<tr class="firstRow">
   			<th>申请人</th>
   			<td> ${sealBorrow.application_person}</td>
   			<th>申请部门</th>
   			<td> ${sealBorrow.department}</td>
  		</tr>
  		<tr>
   			<th>外出携带公章事由</th>
   			<td> ${sealBorrow.reason}</td>
   			<th>前往单位</th>
   			<td> ${sealBorrow.go_to_unit}</td>
  		</tr>
  		<tr>
   			<th>使用印章名称</th>
   			<td> ${sealBorrow.seal_name}</td>
   			<th>借出时间</th>
   			<td><fmt:formatDate value='${sealBorrow.borrow_time}' pattern='yyyy-MM-dd'/></td>
  		</tr>
  		<tr>
   			<th>预计归还时间</th>
   			<td><fmt:formatDate value='${sealBorrow.return_time}' pattern='yyyy-MM-dd'/></td>
   			<th></th>
   			<td></td>
  		</tr>
  		<tr>
   			<th>备注</th>
   			<td rowspan="1" colspan="3">${sealBorrow.remark}</td>    
  		</tr>
  		<tr>
   			<th>附件</th>
   			<td rowspan="1" colspan="3">
   				<input  ctltype="attachment" right="r"  name="m:sealBorrow:attachment" isdirectupload="0"  value='${sealBorrow.attachment}' validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}" />
   			</td>    
  		</tr>
  <tr>
 </tbody>
</table>
</body>
</html>

