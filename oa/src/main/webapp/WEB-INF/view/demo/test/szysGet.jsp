
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>数字运算明细</title>
<%@include file="/commons/include/customForm.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/FlowDetailWindow.js"></script>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">数字运算详细信息</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<div class="group"><a class="link detail"  onclick="FlowDetailWindow({runId:${runId}})" href="javascript:;" ><span></span>流程明细</a></div>
						<div class="l-bar-separator"></div>
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
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">数字一:</td>
   <td style="width: 35%" class="formInput"> ${szys.szy} </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">数字二:</td>
   <td style="width: 35%; word-break: break-all;" class="formInput"> ${szys.sze} </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">合计:</td>
   <td style="width: 35%" class="formInput"> ${szys.hj} </td>
   <td style="width: 15%" class="formTitle" align="right">主表字段四:</td>
   <td style="width: 35%" class="formInput"><br /></td>
  </tr>
 </tbody>
</table>
</body>
</html>

