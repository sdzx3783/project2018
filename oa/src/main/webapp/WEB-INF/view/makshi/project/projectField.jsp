<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>填写表单</title>
	<%@include file="/codegen/include/customForm.jsp" %>
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
					var jsonArr=getFieldJson();
					if(jsonArr==null || jsonArr.length==0){
						$.ligerDialog.warn("表单字段不能为空！");
						return ;
					}
					$("#fields").val(JSON.stringify(jsonArr));
					
					
					
					
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
		
		 function getFieldJson(){
			var arr=[];
			var fields=$(".fieldDiv").eq(0).find(".fieldItem");
			if(fields==null || fields.length==0){
				return arr;
			}
			for(var i=0;i<fields.length;i++){
				var obj={};
				obj['fieldname']=$(fields[i]).find("input[name='fieldname']").eq(0).val();
				obj['fieldtype']=$(fields[i]).find("input[name='fieldtype']'").eq(0).val();
				
				if($(fields[i]).find("input[name='fieldvalue']'").length > 0){
					obj['fieldvalue']=$(fields[i]).find("input[name='fieldvalue']'").eq(0).val();
				}else if($(fields[i]).find("textarea[name='fieldvalue']'").length > 0){
					obj['fieldvalue']=$(fields[i]).find("textarea[name='fieldvalue']'").eq(0).val();
				}
				if($(fields[i]).find("input[name='fieldvalueID']'").length > 0){
					var fieldvalueTemp1=$(fields[i]).find("input[name='fieldvalueID']'").eq(0).val();
					var fieldvalueTemp2=obj['fieldvalue'];
					if(fieldvalueTemp2 && fieldvalueTemp1 && fieldvalueTemp2.length>0 && fieldvalueTemp2.length>0){
						obj['fieldvalue']=fieldvalueTemp2+"|"+fieldvalueTemp1;
					}
				}
				//var files=$(fields[i]).find("textarea[name='fieldvalue']'").eq(0).val();
				//console.log(files);
				arr.push(obj);
			}
			return arr;
		}

	</script>
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
	<div class="oa-top-wrap">
		<button type="button" class="oa-button oa-button--primary oa-button--blue"  id="dataFormSave" >提交</button>
	</div>
	
	<div class="oa-main-wrap">
		<form id="fieldForm" method="post" action="fieldSave.ht">
			<div class="fieldDiv">
					
				<table class="oa-table">
					<tr>
						<th>任务名称：</th>
						<td>${task.taskname}</td>
					</tr>
					
					<c:forEach items="${task.fieldArr}" var="js">
						<tr class="fieldItem">
							<th>${js.fieldname}</th>
							<td>
								<c:if test="${js.fieldtype=='varchar'}">
									<div class="oa-input-wrap">
										<input type="text" name="fieldvalue" class="oa-input" validate="{<c:if test="${js.required=='1'}">'required':true,</c:if>'maxlength':150}">
									</div>
								</c:if>
								<c:if test="${js.fieldtype=='date'}">
									<div class="oa-input-wrap">
										<input type="text" name="fieldvalue" value="" class="oa-input date" validate="{<c:if test="${js.required=='1'}">'required':true,</c:if>date:true}" />
									</div>
								</c:if>
								<c:if test="${js.fieldtype=='number'}">
									<div class="oa-input-wrap">
										<input type="text" name="fieldvalue" class="oa-input" validate="{<c:if test="${js.required=='1'}">'required':true,</c:if>'number':true,'maxIntLen':13}">
									</div>
								</c:if>
								<c:if test="${js.fieldtype=='attach'}">
									<div class="oa-input-wrap">
										<input name="fieldvalue" class="oa-input file" ctltype="attachment"  isdirectupload="0" right="w"  validatable="true" validate="{<c:if test="${js.required=='1'}">'required':true</c:if>}" />
									</div>
								</c:if>
								<c:if test="${js.fieldtype=='user'}">
									<div class="oa-input-wrap">
										<input name="fieldvalue" class="oa-input file users"  type="text" ctltype="selector"  validate="{<c:if test="${js.required=='1'}">'required':true,</c:if>empty:false}" readonly="readonly"  />
									</div>
								</c:if>
								
								<input type="hidden" name="fieldname" value="${js.fieldname }"/>
								<input type="hidden" name="fieldtype" value="${js.fieldtype }"/>
							</td>
						</tr>
					</c:forEach>
				</table>
		
			</div>
			
			<input type="hidden" id="saveData" name="saveData"/>
			<input type="hidden" id="id" name="id" value="${task.id }"/>
			<textarea style="display: none" id="fields" name="fields" >${task.fields }</textarea>
		</form>
	</div>
</body>
</html>
