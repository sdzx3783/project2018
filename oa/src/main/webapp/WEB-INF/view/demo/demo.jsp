<%--
	time:2015-04-09 17:19:23
	desc:edit the 对象权限表
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<%@ taglib prefix="ht" tagdir="/WEB-INF/tags/wf"%>
<html>
<head>
	<title>表单树结构预览</title>
	<%@include file="/commons/include/form.jsp" %>
	<!-- zTree引入 -->
	<f:link href="tree/zTreeStyle.css"></f:link>
	<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerMenu.js"></script>
	<script type="text/javascript" src="${ctx}/js/tree/jquery.ztree.js"></script>
	<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerLayout.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/GlobalMenu.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/GlobalType.js"></script>
	<script type="text/javascript" src="${ctx}/js/util/ZtreeCreator.js"></script>	
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/CommonDialog.js"></script>	
	<!-- ngjs引入 -->
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/angular/angular.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/angular/service/baseServices.js"></script>
	<script type="text/javascript" src="${ctx}/js/angular/service/arrayToolService.js"></script>
	<script type="text/javascript" src="${ctx}/js/angular/service/commonListService.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/formDefTree/FormDefTreeService.js"></script>
	<script type="text/javascript" src="${ctx}/js/demo/ShowController.js"></script>
	<script type="text/javascript">
		var ztreeCreator=null;
		var formKey="${formKey}";
		
		$(function() {
			$("#defLayout").ligerLayout({leftWidth:300,height: '100%',allowLeftResize:false});
		});
		
		function refreshTheTree(){
			var scope=getScope("body");
			scope.refreshTheTree();
		}
	</script>
</head>
<body  ng-app="app" ng-controller="ShowController">
<div class="panel">
	<div id="defLayout" >
		<div position="left" title="树" style="overflow: auto;float:left;width:100%">
			<ul id="tree" class="ztree"></ul>
		</div>
		<div position="center" title="内容">
			<input type="button" value="保存" ng-click="save()"/>
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
				<thead>
					<tr>
						<th>NAME</th>
						<th>{{nz}}</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td><input type="text" ng-model="name"/></td>
						<td><input type="text" ng-model="nv"/></td>
					</tr>
				</tbody>
			</table>
		</div>
     </div>
</div>
</body>
</html>
