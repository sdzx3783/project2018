<%--
	time:2015-04-09 17:19:23
	desc:edit the 对象权限表
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<%@ taglib prefix="ht" tagdir="/WEB-INF/tags/wf"%>
<html>
<head>
	<title>组合对话框</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/angular/angular.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/angular/service/baseServices.js"></script>
	<script type="text/javascript" src="${ctx}/js/angular/service/arrayToolService.js"></script>
	<script type="text/javascript" src="${ctx}/js/angular/service/commonListService.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/bpmFormDialogCombinate/BpmFormDialogCombinateService.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/bpmFormDialogCombinate/ShowController.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/CommonDialog.js"></script>
	<script type="text/javascript">
		$(function() {
			$("#defLayout").ligerLayout({leftWidth:210,height: '100%',allowLeftResize:false});
		});
		
		var id="${param.id}";
		var dialog = frameElement.dialog;
		//树被点击的方法
		function treeClick(data){
			var scope=getScope("#frmSubmit");
			scope.treeDataChange(data);			
		}
	</script>
</head>
<body  ng-app="app" ng-controller="ShowController">
<form id="frmSubmit">
<div class="panel">
	<div id="defLayout" >
		<div position="left" title="treeTitle" style="overflow: auto;float:left;width:100%;height:100%">
			<iframe id="treeFrame"
		 		style="overflow: auto;width:100%;height:100%" frameborder="0"></iframe>
		</div>
		<div position="center" title="listTitle">
			<iframe id="listFrame"
		 		style="overflow: auto;width:100%;height:100%" frameborder="0"></iframe>
		</div>
     </div>
</div>
</form>
</body>
</html>
