<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<%@include file="/codegen/include/customForm.jsp" %>
	<%@include file="/commons/include/ueditor.jsp" %>
	<title>查看任务</title>
	<script src="${ctx}/js/hotent/formdata.js"></script>
	<script src="${ctx}/js/hotent/CustomValid.js"></script>
	<script src="${ctx}/js/hotent/subform.js"></script>
	<script src="${ctx}/js/hotent/platform/form/AttachMent.js"></script>
	<style>
		.oa-table th{
			padding: 10px 0;
		}
		.oa-table td{
			padding: 10px 20px;
		}
	</style>
</head>
<body>
<div class="panel" style="height:100%;overflow:auto;">
	<div class="oa-top-wrap">
		<a class="oa-button oa-button--primary oa-button--blue" onclick="closeWin()">关闭</a>
	</div>

	<div class="oa-main-wrap">
		<form id="stagetaskForm" method="post" action="stageTaskSave.ht">
			<table class="oa-table">
				<tr>
					<th>项目分类名：</th>
					<td>${classifyname}</td>
				</tr>
				<tr>
					<th>阶段名：</th>
					<td>${stagename}</td>
				</tr>
				<tr>
					<th>任务名称：</th>
					<td>${classifyStageTask.taskname }</td>
				</tr>
				<tr>
					<th>模板文件：</th>
					<td>
						<div name="div_attachment_container">
							<div class="attachement"></div>
							<textarea style="display: none" controltype="attachment" name="file" lablename="附件" validate="{}">${classifyStageTask.templatefile}</textarea>
						</div>
					</td>
				</tr>
				<tr>
					<th>备注：</th>
					<td>
						${classifyStageTask.remark}
					</td>
				</tr>
				<tr>
					<th>任务类型：</th>
					<td>
						<c:if test="${classifyStageTask.tasktype==1 }">表单填写类</c:if>
						<c:if test="${classifyStageTask.tasktype==2 }">资料上传类</c:if>
						<c:if test="${classifyStageTask.tasktype==3 }">流程审批类</c:if>
					</td>
				</tr>
				
				<tr class="gnjdtype" <c:if test="${classifyStageTask.tasktype!=3 }">style="display:none" </c:if>>
					<td></td>
					<td>
						<input type="checkbox" id="isexamine" disabled="disabled" name="isexamine" value="1" <c:if test="${classifyStageTask.isexamine}">checked="checked"</c:if>>
						是否需要审批 <span id="relationflowid" <c:if test="${!classifyStageTask.isexamine}">style="display:none"</c:if>>
						关联流程：${classifyStageTask.flowid}
					</td>
				</tr>
				<tr class="zlsctype">
					<td></td>
					<td>
						<input type="checkbox" id="ismore" disabled="disabled" name="ismore" value="1" <c:if test="${classifyStageTask.ismore}">checked="checked"</c:if>>
						是否需要提交多次
					</td>
				</tr>
				
			</table>


			<div id="fielddiv" <c:if test="${classifyStageTask!=null && classifyStageTask.tasktype!=1 }">style="display:none" </c:if>
				<div>待填写的表单</div>
				<table class="oa-table">
					<c:forEach items="${classifyStageTask.fieldArr}" var="js">
					<tr>
						<th>字段名：</th>
						<td>${js.fieldname}&nbsp;&nbsp;是否必填：<input type="checkbox" readonly="readonly" disabled="disabled" <c:if test="${js.required==1}">checked=checked</c:if> class="isRequired" value="1"/></td>
						<td>
							<c:if test="${js.fieldtype=='varchar'}">字符串</c:if>
							<c:if test="${js.fieldtype=='date'}">日期</c:if>
							<c:if test="${js.fieldtype=='number'}">数字</c:if>
							<c:if test="${js.fieldtype=='attach'}">附件</c:if>
							<c:if test="${js.fieldtype=='user'}">用户选择器</c:if>
						</td>
					</tr>
					</c:forEach>
				</table>
			</div>
	</div>

</body>
<script type="text/javascript">
	function closeWin(){
	    window.close();
	}
</script>
</html>
