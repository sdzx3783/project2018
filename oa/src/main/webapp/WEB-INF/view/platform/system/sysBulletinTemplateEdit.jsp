<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 公告</title>
	<%@include file="/commons/include/form.jsp"%>
	<f:link href="form.css" ></f:link>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/AttachMent.js" ></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/HtmlUploadDialog.js" ></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/FlexUploadDialog.js" ></script>
	<script type="text/javascript" charset="utf-8" src="${ctx}/js/ueditor1433/ueditor_default.config.js"></script>
	<script type="text/javascript" charset="utf-8" src="${ctx}/js/ueditor1433/ueditor.all.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="${ctx}/js/ueditor1433/lang/zh-cn/zh-cn.js"></script>
	<script type="text/javascript">
		$(function() {
		    var options={};
		    var bulletinTemUeditor = new baidu.editor.ui.Editor({minFrameHeight:300,initialFrameWidth:800,lang:'zh_cn'});
		    bulletinTemUeditor.render("txtHtml"); 
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#sysBulletinTemlateForm').form();
			
			$("#name").blur(function(){
				var obj=$(this);
				autoPingin(obj);
			});
			$("a.save").click(function() {
				frm.ajaxForm(options);
				if(frm.valid()){
					$('#template').val(bulletinTemUeditor.getContent());
					$('#sysBulletinTemlateForm').submit();
				}
			});
		});
		function autoPingin(obj){
			var value=obj.val();
			Share.getPingyin({
				input:value,
				postCallback:function(data){
					$("#alias").val(data.output);
				}
			});
		}
		function showResponse(responseText) {
			var obj = new com.hotent.form.ResultMessage(responseText);
			if (obj.isSuccess()) {
				$.ligerDialog.confirm(obj.getMessage()+",是否继续操作","提示信息", function(rtn) {
					if(rtn){
						window.location.href = window.location.href;
					}else{
						window.location.href = "${returnUrl}";
					}
				});
			} else {
				$.ligerDialog.err("提示信息","公告模版保存失败!",obj.getMessage());
			}
		}
	</script>
</head>
<body>
<div class="panel">
	<div class="panel-top">
		<div class="panel-toolbar">
			<div class="toolBar">
				<div class="group">
					<a class="link save" id="dataFormSave" href="javascript:;"><span></span>保存</a>
				</div>
				<div class="l-bar-separator"></div>
				<div class="group">
					<a class="link back" href="list.ht"><span></span>返回</a>
				</div>
			</div>
		</div>
	</div>
	<div class="panel-body">
		<form id="sysBulletinTemlateForm" method="post" action="save.ht">
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
				<tbody>
					<tr>
						<th width="20%">名称:<span class="required red">*</span></th>
						<td>
							<input type="text" name="name" id ="name" value="${sysbulletintemplate.name}" validate="{required:true,maxlength:128}" class="inputText" style="width: 50%;"  />
						</td>
					</tr>
					<tr>
						<th width="15%">别名: </th>
						<td>
			<input type="text" name="alias" id="alias" value="${sysbulletintemplate.alias}" validate="{required:true,maxlength:128}" class="inputText" style="width: 50%;"  />
						</td>
					</tr>
					<tr>
						<td align="right" nowrap="nowarp" style="width: 20%;"
							class="formTitle">内容</td>
						<td class="formInput">
						<div id="editor" position="center"
								style="overflow: hidden; height: 100%;">
								<textarea id="txtHtml" name="html">${sysbulletintemplate.template}</textarea>
								<textarea id="template" name="template" style="display:none;"></textarea>
							</div>
						</td>
				</tbody>
			</table>
			<input type="hidden" name="id" value="${sysbulletintemplate.id}"/>
		</form>
	</div>
</body>
</html>
