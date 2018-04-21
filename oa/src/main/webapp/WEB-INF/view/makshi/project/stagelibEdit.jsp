<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 阶段</title>
	<%@include file="/commons/include/get.jsp"%>
	<%@include file="/commons/include/ueditor.jsp" %>
	<%@include file="/codegen/include/customForm.jsp" %>
	<script src="${ctx}/js/hotent/formdata.js"></script>
	<script src="${ctx}/js/hotent/CustomValid.js"></script>
	<script src="${ctx}/js/hotent/subform.js"></script>
	<script>
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#stagelibForm').form();
			$("#dataFormSave").click(function() {
				frm.ajaxForm(options);
				$("#saveData").val(1);
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
							$('#stagelibForm').submit();
						});
					}else{
						frm.handleFieldName();
						$('#stagelibForm').submit();
					}
				}
			});
		});
		
		function showResponse(responseText) {
			var obj = new com.hotent.form.ResultMessage(responseText);
			if (obj.isSuccess()) {
				window.parent.location.reload(true);
			} else {
				$.ligerDialog.error(obj.getMessage(),"提示信息");
			}
			
		}
		$(function(){
			$("input[name='createorgID']").val('${stageLib.createorgid }');
		})
	</script>
	<style>
		input.oa-input,
		textarea.oa-textarea {
		    display: inline-block;
		    width: 200px;
		    padding: 5px 10px;
		    border: 1px solid #8c9fd6;
		    background: #fff;
		    border-radius: 4px;
		}

		.oa-table td{
			padding: 10px 20px;
		}
		.input-wrapper {
			display: inline-block;
		}
		.my-choose {
			margin-left: 10px;
		}
		.my-space {
			display: inline-block;
			width: 2em;
		}
	</style>
</head>
<body style="padding-top: 1px; box-sizing: border-box;">
	<div class="oa-top-wrap">
		<button type="button" class="oa-button oa-button--primary oa-button--blue" id="dataFormSave">保存</button>
	</div>
	
	<div class="oa-main-wrap">
		<form id="stagelibForm" method="post" action="save.ht">
			<table class="oa-table">
				<tr>
					<th>阶段名称: </th>
					<td>
						<input name="stagename" class="oa-input" type="text" validate="{'required':true,'maxlength':100}"  value="${stageLib.stagename }"/>						
					</td>
				</tr>

				<tr>
					<th>备<span class="my-space"></span>注: </th>
					<td>
						<textarea maxlength="500" class="oa-textarea" name="remark">${stageLib.remark }</textarea>
					</td>
				</tr>

				<tr>
					<th>所属部门: </th>
					<td>
						<div class="input-wrapper">
							<input name="createorg" type="text" ctltype="selector" class="org oa-input oa-hidden-trigger" value="${stageLib.createorg }" validate="{'required':true}" readonly="readonly" />
						</div>
						<button type="button" class="my-choose oa-trigger-hidden-button oa-button-label">选择部门</button>
					</td>
				</tr>
			</table>

			<input type="hidden" id="stageno" name="stageno" value="${stageLib.stageno }"/>
			<input type="hidden" id="saveData" name="saveData"/>
		</form>
	</div>

<script>
	$(function(){
		$(".oa-trigger-hidden-button").on("click", function(){
	        $(this).parent("td").find("a.oa-hidden-trigger").click();
	    });
	});
</script>
</body>
</html>
