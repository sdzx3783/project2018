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
		.select,
		.input-text,
		input.orgs,
		input.users{
			padding: 0 10px;
			width: 225px;
			height: 34px;
			line-height: 34px;
			border: 1px solid #e5e5e5;
			border-radius: 3px;
		}
		.dialogsave{
			background-color: #32a3ff;
		    color: #fff;
		    padding: 7px 12px;
		    height: 30px;
		    line-height: 30px;
		}
		.dialogcal{
			background-color: #32a3ff;
		    color: #fff;
		    padding: 7px 12px;
		    height: 30px;
		    line-height: 30px;
		}
		.ui-popup-show{top:100px !important;}
	</style>
</head>
<body>
<div class="panel" style="height:100%;overflow:auto;">
	<form id="docinfoForm" method="post" action="saveDic.ht">
		<table class="table">
			<tr>
				<th>分类名称: </th>
				<td>
					 <select name="dicName" class="oa-new-select" style="width: 100px;">
						<c:forEach  var="e" items="${dicList }" >
			               	<option value="${e.itemName}">${e.itemName }</option>
	               	    </c:forEach>
					</select>
					<%-- <input name="dicName"   value="${dic.dicName}" class="oa-new-input" /> --%>
				</td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: center;">
					<a class="save dialogsave" id="dataFormSave" href="javascript:;">保存</a>
					<a class="dialogcal"  href="javascript:;">取消</a>
				</td>
			</tr>
		</table>
		<input type="hidden" name="dicId" value="${dic.dicId}"/>
		<input type="hidden" name="docId" value="${docId}"/>
		<input type="hidden" id="saveData" name="saveData"/>
		<input type="hidden" name="executeType"  value="start" />
	</form>
</div>
<script type="text/javascript">
	$(function() {
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
			$.ligerDialog.confirm(obj.getMessage()+",是否继续操作","提示信息", function(rtn) {
				if(rtn){
					closeAll();
					$.post("/makshi/dispatch/dispatch/editDic.ht",{},function(data){
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
