<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 合同编号管理</title>
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
			var frm=$('#contractNumAdminForm').form();
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
							frm.sortList();
							$('#contractNumAdminForm').submit();
						});
					}else{
						frm.handleFieldName();
						frm.sortList();
						$('#contractNumAdminForm').submit();
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
						window.location.href = "/makshi/contract/contractNumAdmin/list.ht";
					}
				});
			} else {
				$.ligerDialog.error(obj.getMessage(),"提示信息");
			}
		}
	</script>
</head>
<body class="oa-mw-page">
	<div class="oa-pd20">
        <a class="oa-button oa-button--primary oa-button--blue save" id="dataFormSave">保存</a>
    </div>
     <div class="oa-mg20">
    	<form id="contractNumAdminForm" method="post" action="save.ht">
    		<table class="oa-table--default" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
			    <c:choose>
				    <c:when test="${not empty contractNumAdminItem.id}">
				       <caption>编辑合同编号</caption>
				    </c:when>
				    <c:otherwise>
				        <caption>添加合同编号</caption>
				    </c:otherwise>	
			    </c:choose>
    <tr> 
	   <th>合同年份</th> 
	   <td>
        ${curYear}
		<input  name="m:contract_num_admin:year" value="${curYear}" type="hidden"/>
	   </td>
    </tr> 
  <tr> 
   <th>合同类型</th> 
   <td><input type="text" name="m:contract_num_admin:type" lablename="合同类型" class="oa-new-input" value="${contractNumAdmin.type}" validate="{maxlength:50}" isflag="tableflag" /></td> 
  </tr> 
  <tr> 
   <th>合同编号</th> 
   <td><input type="text" name="m:contract_num_admin:contract_num" lablename="合同编号" class="oa-new-input" value="${contractNumAdmin.contract_num}" validate="{maxlength:50}" isflag="tableflag" /></td> 
  </tr> 
  <tr> 
   <th>流水号</th> 
   <td><input type="text" name="m:contract_num_admin:flowNo" lablename="流水号" class="oa-new-input" value="${contractNumAdmin.flowNo}" validate="{maxlength:50,正整数:true}" isflag="tableflag" /></td> 
  </tr> 
 </tbody> 
</table>  
<div type="subtable" tablename="contractNumSecond" tabledesc="二级监理合同" show="true" parser="rowmodeedit" id="contractNumSecond" formtype="edit"> 
 <table class="listTable" border="0" cellpadding="2" cellspacing="0"> 
  <tbody> 
   <tr class="toolBar firstRow"> 
    <td colspan="2"><a class="link add" href="javascript:;">添加</a></td> 
   </tr> 
   <tr class="headRow"> 
    <th style="word-break: break-all;">合同子类型</th> 
    <th style="word-break: break-all;">合同子编号</th> 
   </tr> 
   <c:forEach items="${contractNumSecondList}" var="contract_num_second" varStatus="status">
    <tr class="listRow" type="subdata"> 
     <td style="word-break: break-all;"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:contract_num_second:type" lablename="合同子类型" class="inputText" value="${contract_num_second.type}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
     <td style="word-break: break-all;"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:contract_num_second:num" lablename="合同子编号" class="inputText" value="${contract_num_second.num}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
    </tr>
   </c:forEach> 
   <tr class="listRow" type="append" style="display:none;"> 
    <td style="word-break: break-all;"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:contract_num_second:type" lablename="合同子类型" class="inputText" value="${contractNumSecond.type}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
    <td style="word-break: break-all;"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:contract_num_second:num" lablename="合同子编号" class="inputText" value="${contractNumSecond.num}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
   </tr>
  </tbody> 
 </table> 
 <input name="s:contract_num_second:id" type="hidden" pk="true" value="" />
</div>
			</div>
		</div>
		<input type="hidden" name="id" value="${contractNumAdmin.id}"/>
		<input type="hidden" id="saveData" name="saveData"/>
		<input type="hidden" name="executeType"  value="start" />
	</form>
</body>
</html>
