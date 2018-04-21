<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 员工转正表</title>
	<%@include file="/codegen/include/customForm.jsp" %>
	<%@include file="/commons/include/ueditor.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/FlowDetailWindow.js"></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#userFormalForm').form();
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
							$('#userFormalForm').submit();
						});
					}else{
						frm.handleFieldName();
						$('#userFormalForm').submit();
					}
				}
			});
			$("a.run").click(function() {
				frm.ajaxForm(options);
				$("#saveData").val(0);
				$('#userFormalForm').attr("action","run.ht");
				if(frm.valid()){
					if(WebSignPlugin.hasWebSignField){
						WebSignPlugin.submit();
					}
					data=CustomForm.getData();
					//设置表单数据
					$("#formData").val(data);
					frm.handleFieldName();
					OfficePlugin.submit();
					$('#userFormalForm').submit();
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
						window.location.href = "${ctx}/makshi/hr/userFormal/list.ht";
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
			    <c:when test="${not empty userFormalItem.id}">
			        <span class="tbar-label"><span></span>编辑员工转正表</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label"><span></span>添加员工转正表</span>
			    </c:otherwise>	
		    </c:choose>
		</div>
		<div class="panel-toolbar">
			<div class="toolBar">
				<div class="group"><a class="link save" id="dataFormSave" href="javascript:;"><span></span>保存</a></div>
				<div class="l-bar-separator"></div>
				<c:if test="${runId!=0}">
					<div class="group"><a class="link detail"  onclick="FlowDetailWindow({runId:${runId}})" href="javascript:;" ><span></span>流程明细</a></div>
					<div class="l-bar-separator"></div>
				</c:if>
				<div class="group"><a class="link back" href="list.ht"><span></span>返回</a></div>
			</div>
		</div>
	</div>
	<form id="userFormalForm" method="post" action="save.ht">
		<div type="custform">
			<div class="panel-detail">
				<table class="formTable" parser="addpermission" data-sort="sortDisabled" cellspacing="0" cellpadding="2" border="1">
 <tbody>
  <tr class="firstRow">
   <td class="formHead" colspan="4" style="word-break: break-all;">员工转正表</td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" width="0" align="right">申请人:</td>
   <td style="width: 35%" class="formInput" id="fullname" width="467"><br /></td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">劳动合同约定转正时间:</td>
   <td style="width: 35%" class="formInput"></td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" width="0" align="right">员工编号:</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" id="userId_procedure" width="467"></td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">职称:</td>
   <td style="width: 35%" class="formInput" id="positional"><br /></td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" width="0" align="right">入职日期:</td>
   <td class="formInput" colspan="1" rowspan="1" id="entrydate" width="467"><br /></td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">专业:</td>
   <td class="formInput" colspan="1" rowspan="1" id="major"><br /></td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" width="0" align="right">试用期所在部门及项目部:</td>
   <td class="formInput" colspan="1" rowspan="1" id="orgpathname" width="467"><br /></td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">学历:</td>
   <td class="formInput" colspan="1" rowspan="1" id="education"><br /></td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" width="0" align="right">岗位工资</td>
   <td class="formInput" colspan="3" rowspan="1"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:user_formal:salary" lablename="工资" class="inputText" value="${userFormal.salary}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
  </tr>
 </tbody>
</table>
<table class="formTable" parser="addpermission" cellspacing="0" cellpadding="2" border="1">
 <tbody id="qualification">
  <tr class="firstRow"></tr>
 </tbody>
</table>
			</div>
		</div>
		<input type="hidden" name="id" value="${userFormal.id}"/>
		<input type="hidden" id="saveData" name="saveData"/>
		<input type="hidden" name="executeType"  value="start" />
	</form>
</body>
</html>
