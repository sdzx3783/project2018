<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>修改表单</title>
	<%@include file="/commons/include/customForm.jsp" %>
	<%@include file="/commons/include/ueditor.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
    <script type="text/javascript" src="${ctx}/js/lg/plugins/ligerTab.js" ></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#fieldForm').form();
			$("#dataFormSave").click(function() {
				frm.ajaxForm(options);
				if(frm.valid()){
					
					
					
					
					//如果有编辑器的情况下
					$("textarea[name^='m:'].myeditor").each(function(num) {
						var name=$(this).attr("name");
						var data=getEditorData(editor[num]);
						$("textarea[name^='"+name+"']").val(data);
					});
					
					if(WebSignPlugin.hasWebSignField){
						WebSignPlugin.submit();
					}
					if(OfficePlugin.officeObjs.length>0){
						OfficePlugin.submit(function(){
							frm.handleFieldName();
							$('#fieldForm').submit();
						});
					}else{
						frm.handleFieldName();
						$('#fieldForm').submit();
					}
				}
			});
		});
		
		function showResponse(responseText) {
			var obj = new com.hotent.form.ResultMessage(responseText);
			if (obj.isSuccess()) {
				window.opener.location.reload(true);
				window.close();
			} else {
				$.ligerDialog.error(obj.getMessage(),"提示信息");
			}
			
		}

	</script>
	<style>
		.oa-table th{
			padding: 10px 0;
		}
		.oa-table td{
			padding: 10px 20px;
		}
		span.attachement-span{
			float: none;
		}
		div.attachement{
			padding: 0;
		}
		.attachement li{
			margin-top: 0 !important;
		}
	</style>
</head>
<body>
	<div class="oa-top-wrap">
		<a class="oa-button oa-button--primary oa-button--blue" id="dataFormSave">提交</a>
	</div>
	
	<div class="oa-main-wrap">
		<form id="fieldForm" method="post" action="editField.ht">
			<table class="oa-table">
				<c:forEach items="${list}" var="taskField" varStatus="no">
				<tr>
					<th>字段名称：${taskField.fieldName}</th>
					<td>
						<c:if test="${taskField.fieldType=='varchar'}">
							<div class="oa-input-wrap">
							<input type="text" class="oa-input" id="fieldvalue" name="list[${no.index }].fieldValue" value="${taskField.fieldValue }"  validate="{'required':true,'maxlength':150}">
							</div>
						</c:if>

						<c:if test="${taskField.fieldType=='date'}">
							<div class="oa-input-wrap">
								<input type="text" id="fieldvalue"  name="list[${no.index }].fieldValue" value="${taskField.fieldValue }" class="oa-input date" validate="{date:true}" />
							</div>
						</c:if>

						<c:if test="${taskField.fieldType=='number'}">
							<div class="oa-input-wrap">
								<input type="text" id="fieldvalue" class="oa-input" name="list[${no.index }].fieldValue" value="${taskField.fieldValue }" validate="{'number':true,'maxIntLen':13}">
							</div>
						</c:if>

						<c:if test="${taskField.fieldType=='attach'}">
							<input name="list[${no.index }].fieldValue" id="fieldvalue" value='${taskField.fieldValue }' class="file oa-input" ctltype="attachment"  isdirectupload="0" right="w"  validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}" />
						</c:if>
						<c:if test="${taskField.fieldType=='user'}">
							<input name="list[${no.index }].fieldValue" id="fieldvalue" value="${fn:split(taskField.fieldValue,'|')[0]}" intValue="${fn:split(taskField.fieldValue,'|')[1]}" class="file oa-input users" type="text" ctltype="selector"  validate="{empty:false}" readonly="readonly"  />
						</c:if>
					</td>
					<input type="hidden" id="id" name="list[${no.index }].id" value="${taskField.id }"/>
				</tr>
				</c:forEach>
			</table>
				
			<input type="hidden" id="saveData" name="saveData"/>
			
		</form>
	</div>
</body>
</html>
