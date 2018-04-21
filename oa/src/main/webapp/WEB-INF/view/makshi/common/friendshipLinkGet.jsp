
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>友情链接明细</title>
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
				<span class="tbar-label">友情链接详细信息</span>
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
	<table cellpadding="2" cellspacing="0" border="1" class="formTable" parser="addpermission">
 <tbody>
  <tr class="firstRow">
   <td colspan="4" class="formHead">友情链接</td>
  </tr>
  <tr>
   <td align="right" style="width:20%;" class="formTitle" nowrap="nowarp">链接名称:</td>
   <td style="width:30%;" class="formInput">${friendshipLink.name}</td>
   <td align="right" style="width:20%;" class="formTitle" nowrap="nowarp">链接地址:</td>
   <td style="width:30%;" class="formInput">${friendshipLink.url}</td>
  </tr>
  <tr>
	   <td align="right" style="width:20%;" class="formTitle" nowrap="nowarp">链接优先级:</td>
	   <td style="width:30%;" class="formInput">${friendshipLink.priority}</td>
	   <td align="right" style="width:20%;" class="formTitle" nowrap="nowarp">创建时间&acute;:</td>
	   <td style="width:30%;" class="formInput">
	   <fmt:formatDate value="${friendshipLink.ctime}" pattern="yyyy-MM-dd HH:mm:ss"/>
	  </td>
  </tr>
 </tbody>
</table>
</body>
</html>

