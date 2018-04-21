<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>上传资料</title>
	<%@include file="/codegen/include/customForm.jsp" %>
	<%@include file="/commons/include/ueditor.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
    <script type="text/javascript" src="${ctx}/js/lg/plugins/ligerTab.js" ></script>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/form/AttachMent.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/form/TablePermission.js"></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#fieldForm').form();
			$("a.save").click(function() {
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
	
</head>
<body>
<div class="panel" style="height:100%;overflow:auto;">
	<div class="panel-top">
		<div class="tbar-title">
		   
		</div>
		<div class="panel-toolbar">
			<div class="toolBar">
				<div class="group"><a class="link save" id="dataFormSave" href="javascript:;"><span></span>提交</a></div>
				<div class="l-bar-separator"></div>
			</div>
		</div>
		<form id="fieldForm" method="post" action="fileSave.ht">
		<div>
			<div>
				<span>任务名称：</span>
				<span>${task.taskname}</span>
			</div>
			
			<div class="fieldDiv">
				<input ctltype="attachment" name="files" isdirectupload="0" right="w" value="" validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}" />
			</div>
			<input type="hidden" id="saveData" name="saveData"/>
			<input type="hidden" id="id" name="id" value="${task.id }"/>
		</div>		
		</form>
	</div>
</div>
</body>
</html>
