<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>添加 模板</title>
	<%@include file="/codegen/include/customForm.jsp" %>
	<%@include file="/commons/include/ueditor.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/AttachMent.js"></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			AttachMent.init("w");
			var frm=$('#docTemplateForm').form();
			$("a.save").click(function() {
				frm.ajaxForm(options);
				if(frm.valid()){
					var len=$("span.attachement-span").length;
					if(len>1){
						$.ligerDialog.warn('只能上传一个模板文件！');
						return ;
					}
					var file=$("div.attachement a.attachment").eq(0).attr("title");
					if(!file || file.length<=0){
						$.ligerDialog.warn('请上传文档模板文件！');
						return ;
					}
					var strs=file.split("."); 
					if(/^(doc|docx)/gi.test(strs[1])){
			            
			        }else{
			        	$.ligerDialog.warn('请上传文档doc、docx类型文件！');
						return ;
			        } 
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
							$('#docTemplateForm').submit();
						});
					}else{
						frm.handleFieldName();
						$('#docTemplateForm').submit();
					}
				}
			});
		});
		
		function showResponse(responseText) {
			var obj = new com.hotent.form.ResultMessage(responseText);
			if (obj.isSuccess()) {
				window.parent.location.reload(true);
				window.close();
			} else {
				$.ligerDialog.error(obj.getMessage(),"提示信息");
			}
			
		}
		
	</script>
	<style>
		.btn{display:inline-block;padding:3px 15px 3px 15px;border:0;border-radius:3px 3px 3px 3px;background:#478de4;-webkit-box-shadow:0 1px 1px rgba(0,0,0,.15);-moz-box-shadow:0 1px 1px rgba(0,0,0,.15);box-shadow:0 1px 1px rgba(0,0,0,.15);color:#fff;text-align:left;text-decoration:none;line-height:23px;cursor:pointer;}
		.panel-toolbar{margin-top:0;padding:5px;}
		.oa-table--default th {width: 60px;}
		.table{margin:0 16px;table-layout:fixed;}
		.table th{padding:0 15px;min-width:50px;text-align:right;}
		.table td{padding:8px;}
		.table td div:first-child{margin-bottom:16px;}
		.select{box-sizing:content-box;}
		.input-text,.select,input.orgs,input.users{padding:0 10px;width:225px;height:34px;border:1px solid #e5e5e5;border-radius:3px;line-height:34px;}
		.panel-wrap{margin:0 10px 0 10px;padding-top:10px;}
		.input{box-sizing:border-box;padding:0 10px;width:167px;height:36px;border:1px solid #dadfed;border-radius:3px;line-height:36px;}
		.form-line{margin-bottom:10px;}
		div.attachement{padding:0;}
		span.attachement-span{margin:3px 0;}
		span.attachement-span a.attachment {
		    max-width: 180px;
		    display: inline-block;
		    overflow: hidden;
		    text-overflow: ellipsis;
		    white-space: nowrap;
		}
	</style>
</head>
<body>
<div class="panel" style="height:100%;overflow:auto;">
	<div class="panel-wrap">
		<a href="javascript:;" field="attachment" class="btn" atype="select" onclick="AttachMent.addFile(this);">选择文件</a>
		<a class="btn save" id="dataFormSave" href="javascript:;">保存</a>
	</div>
	<div class="panel-wrap">
		<form id="docTemplateForm" method="post" action="save.ht">
			<table class="oa-table--default" cellspacing="0" cellpadding="2" border="1">
				<tr>
					<th>文档名称</th>
					<td><input type="text" class="input" name="name" validate="{required:true,maxlength:100}" value="${docTemplate.name }"></td>
				</tr>
				<tr>
					<th>文件列表</th>
					<td>
						<div class="attachement"></div>
						<textarea style="display: none" controltype="attachment" name="file" lablename="模板文件" validate="{}">${docTemplate.file}</textarea>
					</td>
				</tr>
			</table>
			<div class="form-line">
				<div name="div_attachment_container">
					
					
				</div>
			</div>
			
			<input name="id" type="hidden" value="${docTemplate.id }">
		</form>
	</div>		
</body>
</html>
