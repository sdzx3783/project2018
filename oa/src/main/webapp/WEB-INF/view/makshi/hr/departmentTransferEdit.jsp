<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 部门调动表</title>
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
			var frm=$('#departmentTransferForm').form();
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
							$('#departmentTransferForm').submit();
						});
					}else{
						frm.handleFieldName();
						$('#departmentTransferForm').submit();
					}
				}
			});
			$("a.run").click(function() {
				frm.ajaxForm(options);
				$("#saveData").val(0);
				$('#departmentTransferForm').attr("action","run.ht");
				if(frm.valid()){
					if(WebSignPlugin.hasWebSignField){
						WebSignPlugin.submit();
					}
					data=CustomForm.getData();
					//设置表单数据
					$("#formData").val(data);
					frm.handleFieldName();
					OfficePlugin.submit();
					$('#departmentTransferForm').submit();
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
						window.location.href = "${ctx}/makshi/hr/departmentTransfer/list.ht";
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
			    <c:when test="${not empty departmentTransferItem.id}">
			        <span class="tbar-label"><span></span>编辑部门调动表</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label"><span></span>添加部门调动表</span>
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
	<form id="departmentTransferForm" method="post" action="save.ht">
		<div type="custform">
			<div class="panel-detail">
				<table class="formTable" parser="addpermission" data-sort="sortDisabled" cellspacing="0" cellpadding="2" border="1">
 <tbody>
  <tr class="firstRow">
   <td class="formHead" colspan="4" style="word-break: break-all;">人员调动表</td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">员工姓名:</td>
   <td style="width: 35%" class="formInput"></td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">调出部门:</td>
   <td style="width: 35%" class="formInput"></td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">员工编号:</td>
   <td style="width: 35%" class="formInput"></td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">短号:</td>
   <td style="width: 35%" class="formInput"></td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">调整月份<br /></td>
   <td style="width: 35%" class="formInput"></td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">职称<br /></td>
   <td style="width: 35%" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:department_transfer:professional" lablename="职称" class="inputText" value="${departmentTransfer.professional}" validate="{maxlength:50,null:true}" isflag="tableflag" /> </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">调整原因<br /></td>
   <td style="width: 35%" class="formInput" rowspan="1" colspan="3"><br /></td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">调入部门<br /></td>
   <td class="formInput" colspan="3" rowspan="1" style="word-break: break-all;"><br /></td>
  </tr>
  <tr>
   <td class="formInput" rowspan="1" colspan="4" style="word-break: break-all;">调整前基本情况</td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">岗位<br /></td>
   <td class="formInput" colspan="1" rowspan="1"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:department_transfer:post" lablename="岗位" class="inputText" value="${departmentTransfer.post}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">岗位工资<br /></td>
   <td class="formInput" colspan="1" rowspan="1"></td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">基本工资<br /></td>
   <td class="formInput" colspan="1" rowspan="1"></td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">通讯费标准<br /></td>
   <td class="formInput" colspan="1" rowspan="1"></td>
  </tr>
  <tr>
   <td class="formInput" colspan="4" rowspan="1" style="word-break: break-all;">调整后基本情况</td>
  </tr>
  <tr>
   <td tyle="width: 15%; word-break: break-all;" class="formTitle" align="right">岗位<br /></td>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all;"><br /></td>
   <td tyle="width: 15%; word-break: break-all;" class="formTitle" align="right">岗位工资<br /></td>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all;"></td>
  </tr>
  <tr>
   <td tyle="width: 15%; word-break: break-all;" class="formTitle" align="right">基本工资<br /></td>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all;"></td>
   <td tyle="width: 15%; word-break: break-all;" class="formTitle" align="right">通讯费标准<br /></td>
   <td class="formInput" colspan="1" rowspan="1"></td>
  </tr>
  <tr>
   <td class="formInput" colspan="4" rowspan="1" style="word-break: break-all;">员工本人意见</td>
  </tr>
  <tr>
   <td tyle="width: 15%; word-break: break-all;" class="formTitle" align="right" style="word-break: break-all;">本人意见</td>
   <td class="formInput" colspan="3" rowspan="1"><br /></td>
  </tr>
 </tbody>
</table>
			</div>
		</div>
		<input type="hidden" name="id" value="${departmentTransfer.id}"/>
		<input type="hidden" id="saveData" name="saveData"/>
		<input type="hidden" name="executeType"  value="start" />
	</form>
</body>
</html>
