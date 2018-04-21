<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html ng-app="busQueryRuleApp">
<head>
	<title>高级查询设置</title>
	<%@include file="/commons/include/form.jsp"%>
	<link href="${ctx}/styles/default/css/jquery.qtip.css" rel="stylesheet" />
	<link href="${ctx}/styles/default/css/hotent/dataRights.css" rel="stylesheet" />
	<script type="text/javascript" src="${ctx}/js/jquery/plugins/jquery.fix.clone.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/util/easyTemplate.js"></script>
	<script type="text/javascript" src="${ctx}/js/codemirror/lib/codemirror.js"></script>
	<script type="text/javascript" src="${ctx}/js/codemirror/lib/util/matchbrackets.js"></script>
	<script type="text/javascript" src="${ctx}/js/codemirror/mode/groovy/groovy.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery/plugins/jquery.qtip.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/ScriptDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/angular/angular.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/angular/service/baseServices.js"></script>
	<script type="text/javascript" src="${ctx}/js/angular/module/DataRightsApp.js"></script>
	<script type="text/javascript" src="${ctx}/js/angular/controller/busQueryRuleController.js"></script>
	<script type="text/javascript">
		var DataRightsJson=${DataRightsJson};
		var bpmFormTableJSON=${bpmFormTableJSON};
	</script>
</head>
<body ng-controller="busQueryRuleCtrl">
	<div class="panel" ng-show="hasInitTab">
		<div class="hide-panel">
			<div class="panel-top">
				<div class="panel-top">
					<div class="tbar-title">
						<span class="tbar-label">查询规则设置</span>
					</div>
					<div class="panel-toolbar">
						<div class="toolBar">
							<div class="group">
								<a class="link save" ng-click="save()" href="javascript:;">
									<span></span>
									保存
								</a>
							</div>
							<div class="l-bar-separator"></div>
							<div class="group">
								<a class="link close" href="javascript:;" onclick="window.close();">
									<span></span>
									关闭
								</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="panel-body">
			<form id="dataRightsForm" >
				<div id="tab">
					<!-- 基本信息  start-->
					<div tabid="baseSetting" id="table" title="基本信息">
						<div>
							<div class="tbar-title">
								<span class="tbar-label">基本信息</span>
							</div>
							<table class="table-detail" cellpadding="0" cellspacing="0"
								border="0" type="main" style="border-width: 0 !important;">
								<tr>
									<th width="10%">表名:</th>
									<td>{{bpmFormTable.tableName+bpmFormTable.comment}}</td>
								</tr>
								<tr>
									<th >是否分页:</th>
									<td>
										<input type="radio" ng-model="dataRightsJson.needPage" value="0" >
										不分页
										<input type="radio" ng-model="dataRightsJson.needPage" value="1" >
										分页
										<span style="color:red;" ng-if="dataRightsJson.needPage==1">
											分页大小：
											<select ng-model="dataRightsJson.pageSize" >
												<option value="5"  >5</option>
												<option value="10" >10</option>
												<option value="15" >15</option>
												<option value="20" >20</option>
												<option value="50" >50</option>
											</select>
										</span>
									</td>
								</tr>
								<tr>
									<th>是否初始查询:</th>
									<td>
										<select ng-model="dataRightsJson.isQuery"  validate="{required:true}">
											<option value="0"  >是</option>
											<option value="1"  >否</option>
										</select>
									</td>
								</tr>
								<tr>
									<th>
										没有过滤条件
										<br/>
										是否需要默认过滤:
									</th>
									<td>
										<select ng-model="dataRightsJson.isFilter"  validate="{required:true}">
											<option value="0"  >是</option>
											<option value="1"  >否</option>
										</select>
									</td>
								</tr>
							</table>
						</div>
					</div>

					<div tabid="displaySetting"  title="显示列字段">
						<display-setting ></display-setting>
					</div>
					<div tabid="sortSetting"  title="排序字段">
						<sort-setting listkey="tableFieldList" namekey="name" desckey="desc"></sort-setting>
					</div>
					<div tabid="filterSetting"  title="过滤条件">
						<filter-setting ></filter-setting>
					</div>
				</div>
			</form>
		</div>
	</div>
</body>
</html>