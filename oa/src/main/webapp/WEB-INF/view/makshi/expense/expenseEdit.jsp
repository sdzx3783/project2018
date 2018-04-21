<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑选项</title>
	<%@include file="/codegen/include/customForm.jsp" %>
	<%@include file="/commons/include/ueditor.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/AttachMent.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/TablePermission.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#expenseForm').form();
			$("a.save").click(function() {
				frm.ajaxForm(options);
				
				var r = /^\d+(\.\d{1,2})?$/;
				if(!r.test($("#paymentStandard").val())){
					$.ligerDialog.error("排序必须为数字","提示信息");
					return;
				}
				
				
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
							$('#expenseForm').submit();
						});
					}else{
						frm.handleFieldName();
						$('#expenseForm').submit();
					}
				}
			});
		});
		
		function showResponse(responseText) {
			var obj = new com.hotent.form.ResultMessage(responseText);
			if (obj.isSuccess()) {
				$.ligerDialog.confirm(obj.getMessage()+",是否继续操作","提示信息", function(rtn) {
					if(rtn){
						window.location.href = window.location.href;
					}else{
						window.location.href = "${ctx}/makshi/expense/options/list.ht";
					}
				});
			} else {
				$.ligerDialog.error(obj.getMessage(),"提示信息");
			}
		}
	</script>
	<style>
		input.oa-new-input, textarea.oa-new-textarea {
			border: 1px solid #8c9fd6 !important;
		}
	</style>
</head>
<body>
<div class="panel" style="height:100%;overflow:auto;">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${not empty expense.id}">
			        <span class="tbar-label"><span></span>编辑选项</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label"><span></span>添加选项</span>
			    </c:otherwise>	
		    </c:choose>
		</div>
		<div class="panel-toolbar">
			<div class="toolBar">
				<div class="group"><a class="link save" id="dataFormSave" href="javascript:;"><span></span>保存</a></div>
				<div class="l-bar-separator"></div>
				<div class="group"><a class="link back" href="list.ht"><span></span>返回</a></div>
			</div>
		</div>
	</div>
	<form id="expenseForm" method="post" action="save.ht">
		<div type="custform">
			<div class="panel-detail">
				<table class="oa-table--default" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
					 <caption>选项</caption>
					 <tbody>
						  <tr>
						   <th style="width: 15%;">名称:</th>
						   <td style="width: 35%"><span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:expense_options:name" lablename="发证机构" class="oa-new-input" value="${expense.name}"  isflag="tableflag" /> </span> </td>
						  </tr>
						  <tr>
						   <th style="width: 15%;">排序(越小越前):</th>
						   <td style="width: 35%;"><span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:expense_options:sorting" id="paymentStandard" lablename="证书名称" class="oa-new-input" value="${expense.sorting}"  isflag="tableflag" /> </span> </td>
						  </tr>
					 </tbody>
				</table>
			</div>
		</div>
		<input type="hidden" name="id" value="${expense.id}"/>
		<input type="hidden" id="saveData" name="saveData"/>
		<input type="hidden" name="executeType"  value="start" />
	</form>
</body>
</html>
