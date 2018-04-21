<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 文档目录</title>
	<%@include file="/codegen/include/customForm.jsp" %>
	<%@include file="/commons/include/ueditor.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	<style>
		.panel-toolbar{
			margin-top: 0;
		}
		.panel-top{
			margin-bottom: 20px !important;
		}

		.table{
			margin: 0 16px;
			table-layout: fixed;
		}
		.table th{
			padding: 0 15px;
			text-align: right;
			min-width: 50px;
		}
		.table td{
			padding: 8px;
		}
		.table td div:first-child{
			margin-bottom: 16px;
		}
		.select{
			box-sizing: content-box;
		}
		.select,.input-text,input.orgs,input.users{
			padding: 0 10px;
			width: 225px;
			height: 34px;
			line-height: 34px;
			border: 1px solid #e5e5e5;
			border-radius: 3px;
		}
		a.link.select_user{
		  	background: #32a3ff !important;
		    padding: 3px 10px !important;
		    white-space: nowrap !important;
		    border-radius: 3px;
		    color: #fff !important;
		    display: inline-block !important;
		}
		.dialogsave{
			background-color: #32a3ff;
		    color: #fff;
		    padding: 7px 12px;
		    height: 30px;
		    line-height: 30px;
		}
		.dialogcal{
			background-color: #aba3a3f2;
		    color: #fff;
		    padding: 7px 12px;
		    height: 30px;
		    line-height: 30px;
		}
		.ui-popup-show{top:10px !important;}
		.table {
			margin: 15px 0 0;
		}
		.table td {
			padding: 6px 20px 8px;
			border-bottom: solid 1px #ddd;
		}
		.table th {
			padding: 6px 20px;
			border-bottom: solid 1px #ddd;
		}
		.table .no-border-bottom td, .table .no-border-bottom th {
			border-bottom: 0;
		}
		.table td div:first-child {
			margin-bottom: 8px;
		}
		a.link {
			margin-left: 6px;
		}
	</style>
</head>
<body>
<div class="panel" style="height:100%;overflow:auto;">
	<form id="docinfoForm" method="post" action="save.ht">
		<table class="table">
			<tr class="no-border-bottom">
				<th>分类名称: </th>
				<td><input name="docname" class="input-text" type="text" validate="{'required':true,'maxlength':150}"  value="${docinfo.docname}"  /></td>
			</tr>

			<tr>
				<th>文档类型: </th>
				<td>
					<select class="select" name="doctype">
						<option value="0">分类目录</option>
						<option value="1">文档目录</option>
					</select>
				</td>
			</tr>

			<tr>
				<th>查看权限: </th>
				<td>
					<input name="readorgs"  type="text" ctltype="selector" class="orgs select_user dep"  value="${docinfo.readorgs}" validate="{empty:false}" readonly="readonly" />			
					<input name="readuser"  type="text" ctltype="selector" class="users select_user"  value="${docinfo.readuser}" validate="{empty:false}" readonly="readonly" />
				</td>
			</tr>

			<tr>
				<th>编辑权限: </th>
				<td>
					<input name="writeorgs"  type="text" ctltype="selector" class="orgs select_user dep"  value="${docinfo.writeorgs}" validate="{empty:false}" readonly="readonly" />			
					<input name="writeuser"  type="text" ctltype="selector" class="users select_user"  value="${docinfo.writeuser}" validate="{empty:false}" readonly="readonly" />
				</td>
			</tr>
			<tr class="no-border-bottom">
				<td colspan="2" style="text-align: center;">
					<a class="save dialogsave" id="dataFormSave" href="javascript:;">保存</a>
					<a class="dialogcal"  href="javascript:;">取消</a>
				</td>
			</tr>
		</table>
		
		<input type="hidden" name="id" value="${docinfo.docid}"/>
		<input type="hidden" id="orgid" name="orgid" value="${orgId}"/>
		<input type="hidden" id="supid" name="docsupid" value="${supId}"/>
		<input type="hidden" id="saveData" name="saveData"/>
		<input type="hidden" name="executeType"  value="start" />
	</form>
	<script type="text/javascript">
		$(function(){
			$("a.select_user").html('选择人员');
			$("a.dep").html('选择部门');
			$(".dialogcal").click(function(){
				closeAll();
			});
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#docinfoForm').form();
			$("a.save").click(function() {
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
							$('#docinfoForm').submit();
						});
					}else{
						frm.handleFieldName();
						$('#docinfoForm').submit();
					}
				}
			});
		});
		
		function showResponse(responseText) {
			var obj = new com.hotent.form.ResultMessage(responseText);
			if (obj.isSuccess()) {
				//closeAll();
				//$.ligerDialog.success(obj.getMessage(),"提示信息");
				$.ligerDialog.confirm(obj.getMessage()+",是否继续操作","提示信息", function(rtn) {
					if(rtn){
						closeAll();
						$.post("/makshi/doc/docinfo/edit.ht?orgId=${orgId }&supId=${supId}",{},function(data){
							var d = dialog({
								title : "新建文档目录",
								fixed : true,
								content : data
							});
							d.show();
						}); 
					}else{
						 location.reload();
					}
				});
				
			} else {
				$.ligerDialog.error(obj.getMessage(),"提示信息");
			}
		}
		
	</script>
</body>
</html>
