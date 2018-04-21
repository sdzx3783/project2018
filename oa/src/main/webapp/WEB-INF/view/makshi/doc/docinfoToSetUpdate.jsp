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
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#docinfoForm').form();
			$("#updateSetting").click(function() {
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
			var readorgsID='${docinfo.readorgsID}';
			var readuserID='${docinfo.readuserID}';
			var writeorgsID='${docinfo.writeorgsID}';
			var writeuserID='${docinfo.writeuserID}';
			$("input[name='readorgsID']").val(readorgsID);
			$("input[name='readuserID']").val(readuserID);
			$("input[name='writeorgsID']").val(writeorgsID);
			$("input[name='writeuserID']").val(writeuserID);
		});
		
		function showResponse(responseText) {
			var obj = new com.hotent.form.ResultMessage(responseText);
			if (obj.isSuccess()) {
				$.ligerDialog.confirm(obj.getMessage()+",是否继续操作","提示信息", function(rtn) {
					if(rtn){
						window.location.href = window.location.href;
					}else{
						window.location.href = "${ctx}/makshi/doc/docinfo/toSetUpdate.ht?id=${docinfo.docid}";
					}
				});
			} else {
				$.ligerDialog.error(obj.getMessage(),"提示信息");
			}
		}
	</script>
	<style>
		.table-detail{
			margin-top: 0;
		}
		.table-detail th{
			min-width: 60px;
		}
		#docinfoForm{
			margin: 0 16px;
		}
		.title{
			margin: 0 16px;
			padding: 15px 0;
		}
		input{
			width: 200px;
			border: 1px solid #dadfed;
		}
		input.input-text{
			width: 250px;
			height: 30px;
			padding: 0 10px;
			line-height: 30px;
		}
		.table-detail td{
			padding: 10px;
		}
		.btn{
			display: inline-block;
			background: #f39800;
			color: #fff;
			padding: 0 20px;
			border-radius: 3px;
			height: 26px;
			line-height: 26px;
		}
		.icon{
			vertical-align: middle;
			font-size: 16px;
		}
		.text{
			vertical-align: middle;
		}
		.link.input-text{
			font-size: 14px;
		}
	</style>
</head>
<body>
<div class="panel" style="height:100%;overflow:auto;">
	
	<form id="docinfoForm" method="post" action="save.ht">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<th>目录名称: </th>
				<td colspan="2">
					<input name="docname" class="input-text" type="text" validate='{"required":"true","maxlength":50}'  value="${docinfo.docname}"  />
					<a class="btn" href="javascript:;" id="updateSetting"><span class="icon icon-edit"></span><span class="text">修改</span></a>
				</td>
			</tr>
			
			<tr>
				<th>读权限: </th>
				<td>
					<input name="readorgs" type="text" ctltype="selector" class="orgs input-text"  value="${docinfo.readorgs}" validate="{empty:false}" readonly="readonly" />			
				</td>
				<td>
					<input name="readuser" type="text" ctltype="selector" class="users input-text"  value="${docinfo.readuser}" validate="{empty:false}" readonly="readonly" />
				</td>
			</tr>
			<tr>
				<th>写权限: </th>
				<td>
					<input name="writeorgs" type="text" ctltype="selector" class="orgs input-text"  value="${docinfo.writeorgs}" validate="{empty:false}" readonly="readonly" />			
				</td>
				<td>
					<input name="writeuser" type="text" ctltype="selector" class="users input-text"  value="${docinfo.writeuser}" validate="{empty:false}" readonly="readonly" />
				</td>
			</tr>
		</table>
		<input type="hidden" name="docid" value="${docinfo.docid}"/>
		<input type="hidden" id="saveData" name="saveData"/>
		<input type="hidden" name="executeType"  value="start" />
	</form>
	<div  id="logView" style="height: 500px;">
			<h3 class="title">目录变更记录</h3>
			<iframe id="viewFrame" src="docloglist.ht?type=0&refid=${docinfo.docid }" frameborder="0" width="100%"
							height="100%" scrolling="auto"></iframe>
		</div>
</body>
</html>
