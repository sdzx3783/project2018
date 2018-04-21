<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>文档详情</title>
<%@include file="/codegen/include/customForm.jsp"%>
<%@include file="/commons/include/ueditor.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
<script type="text/javascript"
	src="${ctx}/js/hotent/platform/form/AttachMent.js"></script>
<script type="text/javascript"
	src="${ctx}/js/hotent/platform/system/HtmlUploadDialog.js"></script>
<script type="text/javascript"
	src="${ctx}/js/hotent/platform/system/FlexUploadDialog.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${ctx}/js/ueditor1433/ueditor_default.config.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${ctx}/js/ueditor1433/ueditor.all.min.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${ctx}/js/ueditor1433/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${ctx}/js/ueditor1433/extend/picture.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${ctx}/js/ueditor1433/themes/default/css/ueditor.css"></script>

</head>
<style>
.collec{color:red;font-size:20px}
.tabtitle ul{margin:0;padding:0;list-style-type:none}
.tabtitle li{float:left;margin:7px;padding:5px;width:150px;height:190px}
.toolBar{overflow:hidden}
.toolBar h2{padding-left:0;height:auto;font-size:20px}
.tabcontent{overflow:auto;padding:20px}
.panel-toolbar a{float:none}
.btn{display:inline-block;padding:5px 20px;border:1px solid #478de4;border-radius:2px;color:#478de4!important}
.icon{vertical-align:middle;font-size:16px}
.text{vertical-align:middle}
.btns{float:right}
.panel-toolbar{margin-top:0;padding:20px;height:auto}
.panel-top{margin:0!important;border-bottom:1px solid #dadfed}
</style>
<body>


	<div class="panel" style="height: 100%; overflow: auto;">
		<div class="panel-top">
			<div class="panel-toolbar">
				<div class="toolBar">
					<h2>${docFile.title}</h2>
					
					<div class="btns">
						<a class="btn" href="javascript:history.go(-1);"><span class="icon icon-arrow-back"></span><span class="text">返回</span></a>
						
					</div>
				</div>
			</div>
		</div>
		<div class="tabcontent">${docFile.content }</div>
	</div>

</body>
</html>
