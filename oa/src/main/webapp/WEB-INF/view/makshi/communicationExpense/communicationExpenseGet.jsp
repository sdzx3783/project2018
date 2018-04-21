
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>通讯费消费记录表明细</title>
<%@include file="/commons/include/customForm.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">通讯费消费记录表详细信息</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<a class="link back" href="list.ht"><span></span>返回</a>
					</div>
				</div>
			</div>
		</div>
	</div>
	<table class="formTable" border="1" cellspacing="0" cellpadding="2" parser="addpermission">
 <tbody>
  <tr class="firstRow">
   <td class="formHead" colspan="4">主 &nbsp; &nbsp; &nbsp;表</td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">序号:</td>
   <td style="width: 35%" class="formInput"></td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">代付号码地市:</td>
   <td style="width: 35%" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${communicationExpense.address} </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">代付号码:</td>
   <td style="width: 35%" class="formInput"></td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">代付用户名称:</td>
   <td style="width: 35%" class="formInput"></td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">代付额度:</td>
   <td style="width: 35%" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${communicationExpense.limit} </span> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">实际代付金额:</td>
   <td style="width: 35%" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${communicationExpense.money} </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">套餐:</td>
   <td style="width: 35%" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${communicationExpense.package} </span> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">定额:</td>
   <td style="width: 35%" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${communicationExpense.quota} </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">限额:</td>
   <td style="width: 35%" class="formInput"></td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">需缴费用:</td>
   <td style="width: 35%" class="formInput"></td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">报销时间<br /></td>
   <td class="formInput" colspan="1" rowspan="1"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${communicationExpense.date} </span> </td>
   <td class="formInput" colspan="1" rowspan="1"><br /></td>
   <td class="formInput" colspan="1" rowspan="1"><br /></td>
  </tr>
 </tbody>
</table>
</body>
</html>

