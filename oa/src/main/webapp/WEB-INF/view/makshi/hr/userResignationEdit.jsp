<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 员工离职表</title>
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
			var frm=$('#userResignationForm').form();
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
							$('#userResignationForm').submit();
						});
					}else{
						frm.handleFieldName();
						$('#userResignationForm').submit();
					}
				}
			});
			$("a.run").click(function() {
				frm.ajaxForm(options);
				$("#saveData").val(0);
				$('#userResignationForm').attr("action","run.ht");
				if(frm.valid()){
					if(WebSignPlugin.hasWebSignField){
						WebSignPlugin.submit();
					}
					data=CustomForm.getData();
					//设置表单数据
					$("#formData").val(data);
					frm.handleFieldName();
					OfficePlugin.submit();
					$('#userResignationForm').submit();
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
						window.location.href = "${ctx}/makshi/hr/userResignation/list.ht";
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
			    <c:when test="${not empty userResignationItem.id}">
			        <span class="tbar-label"><span></span>编辑员工离职表</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label"><span></span>添加员工离职表</span>
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
	<form id="userResignationForm" method="post" action="save.ht">
		<div type="custform">
			<div class="panel-detail">
				<table class="formTable" parser="addpermission" data-sort="sortDisabled" cellspacing="0" cellpadding="2" border="1">
 <tbody>
  <tr class="firstRow">
   <td class="formHead" colspan="4" style="word-break: break-all;"><span style="font-size: 12px;">离职表</span></td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">员工姓名<br /></td>
   <td style="width: 35%; word-break: break-all;" class="formInput" id="fullname"></td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">所在部门</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" id="orgpathname"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:user_resignation:department" lablename="所在部门" class="inputText" value="${userResignation.department}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">员工编号<br /></td>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all;" id="userId_procedure"></td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">新手机号<br /></td>
   <td class="formInput" colspan="1" rowspan="1"></td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">入职时间<br /></td>
   <td style="width: 35%; word-break: break-all;" class="formInput" id="entrydate"></td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">解除劳动合同时间 <br /></td>
   <td style="width: 35%; word-break: break-all;" class="formInput"></td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">解除劳动合同原因</td>
   <td class="formInput" colspan="3" rowspan="1" style="word-break: break-all;"><br /> <textarea name="m:user_resignation:reason" validate="{empty:false}">${userResignation.reason}</textarea> </td>
  </tr>
  <tr id="leader">
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">项目部资料移交情况</td>
   <td class="formInput" colspan="1" rowspan="1"></td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">项目部资料情况</td>
   <td class="formInput" colspan="1" rowspan="1"></td>
  </tr>
  <tr id="leader">
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">项目章移交情况</td>
   <td class="formInput" colspan="1" rowspan="1"></td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">物品移交情况</td>
   <td class="formInput" colspan="1" rowspan="1"></td>
  </tr>
  <tr id="tr">
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">离职原因</td>
   <td class="formInput" colspan="3" rowspan="1"><br /></td>
  </tr>
 </tbody>
</table>
			</div>
		</div>
		<input type="hidden" name="id" value="${userResignation.id}"/>
		<input type="hidden" id="saveData" name="saveData"/>
		<input type="hidden" name="executeType"  value="start" />
	</form>
</body>
</html>
