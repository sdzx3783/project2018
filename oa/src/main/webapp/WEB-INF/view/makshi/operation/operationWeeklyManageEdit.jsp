<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 运维部周报管理</title>
	<%@include file="/codegen/include/customForm.jsp" %>
	<%@include file="/commons/include/ueditor.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/AttachMent.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/TablePermission.js"></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#operationWeeklyManageForm').form();
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
							$('#operationWeeklyManageForm').submit();
						});
					}else{
						frm.handleFieldName();
						$('#operationWeeklyManageForm').submit();
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
						window.location.href = "${ctx}/makshi/operation/operationWeeklyManage/list.ht";
					}
				});
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
		    <c:choose>
			    <c:when test="${not empty operationWeeklyManageItem.id}">
			        <span class="tbar-label"><span></span>编辑运维部周报管理</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label"><span></span>添加运维部周报管理</span>
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
	<form id="operationWeeklyManageForm" method="post" action="save.ht">
		<div type="custform">
			<div class="panel-detail">
				<table class="formTable" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled"> 
 <tbody> 
  <tr class="firstRow"> 
   <td class="formHead" colspan="4" style="word-break: break-all;">周报管理</td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">周报名称:</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> 
   <input type="text" name="m:operation_weekly_manage:name" lablename="周报名称" class="inputText" value="${operationWeeklyManage.name}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">周报内容:</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> 
   <input type="text" name="m:operation_weekly_manage:content" lablename="周报内容" class="inputText" value="${operationWeeklyManage.content}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">项目名称:</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:operation_weekly_manage:item" lablename="项目名称" class="inputText" value="${operationWeeklyManage.item}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">片区:</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> 
      <input class="dicComboTree l-text-field" nodekey="operationOrg" value="${operationWeeklyManage.grop}" name="m:operation_weekly_manage:grop" validate="{empty:false}"  style="height:250px; width:250px"/>
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">开始日期:</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"> <input name="m:operation_weekly_manage:start" type="text" class="Wdate" displaydate="0" datefmt="yyyy-MM-dd" value="<fmt:formatDate value='${operationWeeklyManage.start}' pattern='yyyy-MM-dd'/>" validate="{empty:false}" /> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right" width="0">结束日期:</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput" width="529"> <input name="m:operation_weekly_manage:end" type="text" class="Wdate" displaydate="0" datefmt="yyyy-MM-dd" value="<fmt:formatDate value='${operationWeeklyManage.end}' pattern='yyyy-MM-dd'/>" validate="{empty:false}" /> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">提交人</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput">${operationWeeklyManage.editer}
    <input name="m:operation_weekly_manage:editer" type="hidden" ctltype="selector" class="user" lablename="提交人" value="${operationWeeklyManage.editer}" validate="{empty:false}" readonly="readonly" showcuruser="0" /> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right" width="0">提交日期:</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput" width="529">
   <fmt:formatDate value='${operationWeeklyManage.edit_date}' pattern='yyyy-MM-dd'/>
   <input name="m:operation_weekly_manage:edit_date" type="hidden" class="Wdate" displaydate="0" datefmt="yyyy-MM-dd" value="<fmt:formatDate value='${operationWeeklyManage.edit_date}' pattern='yyyy-MM-dd'/>" validate="{empty:false}" /> </td>
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">备注:</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <textarea name="m:operation_weekly_manage:remarks" lablename="备注" class="l-textarea" rows="5" cols="40" validate="{maxlength:1000}" isflag="tableflag">${operationWeeklyManage.remarks}</textarea> </span> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">附件:</td> 
   	<td style="width: 35%" class="formInput" rowspan="1" colspan="3">
    <input  ctltype="attachment" right="w"  name="m:operation_weekly_manage:attachment" isdirectupload="0"  value='${operationWeeklyManage.attachment}' validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}"/>
   </td> 
  </tr> 
 </tbody> 
</table>
			</div>
		</div>
		<input type="hidden" name="id" value="${operationWeeklyManage.id}"/>
		<input type="hidden" id="saveData" name="saveData"/>
		<input type="hidden" name="executeType"  value="start" />
	</form>
</body>
</html>
