<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<%@include file="/codegen/include/customForm.jsp" %>
	<title>查看任务</title>
	<%@include file="/commons/include/ueditor.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/AttachMent.js"></script>
	<style>
		.oa-table th{
			padding: 10px 0;
		}
		.oa-table td{
			padding: 10px 20px;
		}
		.oa-table dt,
		.oa-table dd{
			display: inline-block;
		}
		.oa-table dd{
			margin-right: 10px;
			font-size: 12px;
			color: #fff;
			padding: 2px 10px;
			background: #ffa800;
			display: inline-block;
			border-radius: 3px;
		}

	</style>
</head>
<body style="padding-top: 1px; box-sizing: border-box;">

	<div class="oa-top-wrap" style="margin-bottom: 0;">
		<button type="button" class="oa-button oa-button--primary oa-button--blue" onclick="closeWin()">关闭</button>
	</div>

	<div class="oa-main-wrap">
		<table class="oa-table">
			<tr>
				<th>阶段名：</th>
				<td>${stageLib.stagename}</td>
			</tr>
			<tr>
				<th>任务名称：</th>
				<td>${taskLib.taskname }</td>
			</tr>
			<tr>
				<th>模板文件：</th>
				<td>
					<div name="div_attachment_container">
						<div class="attachement"></div>
						<textarea style="display: none" controltype="attachment" name="file" lablename="附件" validate="{}">${taskLib.templatefile}</textarea>
					</div>
				</td>
			</tr>
			<tr>
				<th>备注：</th>
				<td>${taskLib.remark}</td>
			</tr>
			<tr>
				<th>任务类型：</th>
				<td>
					<c:if test="${taskLib.tasktype==1 }">表单填写类</c:if>
					<c:if test="${taskLib.tasktype==2 }">资料上传类</c:if>
					<c:if test="${taskLib.tasktype==3 }">流程审批类</c:if>
				</td>
			</tr>
			
			<tr class="gnjdtype" <c:if test="${taskLib.tasktype!=3 }">style="display:none" </c:if>>
				<th>
					
				</th>
				<td>
					<input type="checkbox" disabled="disabled" id="isexamine" name="isexamine" value="1" <c:if test="${taskLib.isexamine}">checked="checked"</c:if>>
					是否需要审批 
					<span id="relationflowid" <c:if test="${!taskLib.isexamine}">style="display:none"</c:if>>
						关联流程 
						<input name="flowid" disabled="disabled" validate="{'digits':true,'maxlength':20}"  value="${taskLib.flowid }">
					</span>
				</td>
			</tr>
			<tr class="zlsctype">
				<th></th>
				<td>
					<input type="checkbox" disabled="disabled" id="ismore" name="ismore" value="1" <c:if test="${taskLib.ismore}">checked="checked"</c:if>>
					是否需要提交多次
				</td>
			</tr>	

			<div <c:if test="${taskLib!=null && taskLib.tasktype!=1 }">style="display:none" </c:if>>
				<c:forEach items="${taskLib.fieldArr}" var="js">
					<tr>
						<th>字段名：</th>
						<td>
							<dl>
								<dd>
									<c:if test="${js.fieldtype=='varchar'}">字符串</c:if>
									<c:if test="${js.fieldtype=='date'}">日期</c:if>
									<c:if test="${js.fieldtype=='number'}">数字</c:if>
									<c:if test="${js.fieldtype=='attach'}">附件</c:if>
									<c:if test="${js.fieldtype=='user'}">用户选择器</c:if>
								</dd>
								<dt>${js.fieldname}&nbsp;&nbsp;是否必填：<input type="checkbox" readonly="readonly" disabled="disabled" <c:if test="${js.required==1}">checked=checked</c:if> class="isRequired" value="1"/></dt>
							</dl>
						</td>
					</tr>
				</c:forEach>
			</div>
		</table>
	</div>

<script type="text/javascript">
	function closeWin(){
		parent.$.ligerDialog.close();
		parent.$(".l-dialog,.l-window-mask").remove();
	}
</script>
</body>
</html>
