<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 部门公章使用流程</title>
	<%@include file="/codegen/include/customForm.jsp" %>
	<%@include file="/commons/include/ueditor.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/AttachMent.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/FormUtil.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery/plugins/jquery.attach.js" ></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/FlexUploadDialog.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/system/HtmlUploadDialog.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/form/TablePermission.js"></script> 
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#departmentalSealForm').form();
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
							$('#departmentalSealForm').submit();
						});
					}else{
						frm.handleFieldName();
						$('#departmentalSealForm').submit();
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
						window.location.href = "${ctx}/makshi/seal/departmentalSeal/list.ht";
					}
				});
			} else {
				$.ligerDialog.error(obj.getMessage(),"提示信息");
			}
		}
	</script>
</head>
<body class="oa-mw-page" >
	<div class="oa-pd20">
       	<a class="oa-button oa-button--primary oa-button--blue save" id="dataFormSave">保存</a>
    </div>
    <div class="oa-mg20">
		<form id="departmentalSealForm" method="post" action="save.ht">
			<table class="oa-table--default" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
			    <c:choose>
				    <c:when test="${not empty departmentalSealItem.id}">
				       <caption>编辑部门公章使用申请</caption>
				    </c:when>
				    <c:otherwise>
				    	<caption>添加部门公章使用</caption>
				    </c:otherwise>	
			    </c:choose>
				 <tbody>
				  <tr class="firstRow">
				   <td>工号:</td>
				   <td><input name="m:departmental_seal:job_id" value="${departmentalSeal.job_id }" type="text" class="oa-new-input"/></td>
				   <td>申请人:</td>
				   <td><input  name="m:departmental_seal:applyer" type="text" ctltype="selector" class="user-search oa-new-input oa-middle user" lablename="申请人" value="${departmentalSeal.applyer}"  /></td>
				  </tr>
				  <tr>
				   <td>类型:</td>
				   <td><select name="m:departmental_seal:type" value="${departmentalSeal.type }" class="oa-new-select">
				   		    <option  value="1" <c:if test="${departmentalSeal.type == '1'}">selected</c:if> >公章</option>
				   	   </select>
				   </td>
				   <td>使用时间:</td>
				   <td><input type="text" name="m:departmental_seal:use_time" value="<fmt:formatDate value='${departmentalSeal.use_time}' pattern='yyyy-MM-dd'/>" class="date" data-class="oa-new-input"  /></td>
				  </tr>
				  <tr>
				   <td>合同编号:</td>
				   <td><input name="m:departmental_seal:contract_id" value="${departmentalSeal.contract_id }" type="text" class="oa-new-input" /></span><a parser="custombtntable" href="javaScript:void(0)" class="extend message" name="contract_name" eventbtn="[{'name':'contractinfo','fields':[{'src':'F_contract_num','target':['departmental_seal-contract_id']},{'src':'F_contract_name','target':['departmental_seal-contract_name']}],'query':[],'tabName':'自定义对话框','isAddRow':true,'type':'dialog'}]">选择</a></td>
				   <td>合同名称:</td>
				   <td><input name="m:departmental_seal:contract_name" value="${departmentalSeal.contract_name }" type="text" class="oa-new-input" name="m:departmental_seal:contract_id" value=${departmentalSeal.contract_id }/></td>
				  </tr>
				  <tr>
				   <td>材料内容:</td>
				   <td rowspan="1" colspan="3"><textarea name="m:departmental_seal:material_content"  rows="3" cols="20" class="oa-new-textarea">${departmentalSeal.contract_name }</textarea></span></td>
				  </tr>
				  <tr>
				   <td>份数:</td>
				   <td><input input name="m:departmental_seal:copies" value="${departmentalSeal.copies }" type="text" class="oa-new-input" /></td>
				   <td>附件:</td>
				   <td><input ctltype="attachment" name="m:departmental_seal:attachment" isdirectupload="0" right="w" value='${departmentalSeal.attachment}' validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}" /></td>
				  </tr>
				  <tr>
				   <td>项目负责人:</td>
				   <td rowspan="1" colspan="3"><input  name="m:departmental_seal:project_leader" type="text" ctltype="selector" class="user-search oa-new-input oa-middle user" lablename="项目负责人" value="${departmentalSeal.project_leader}"  /></td>
				  </tr>
				 </tbody>
			</table>
			<input type="hidden" name="id" value="${departmentalSeal.id}"/>
			<input type="hidden" id="saveData" name="saveData"/>
			<input type="hidden" name="executeType"  value="start" />
		</form>
	</div>
</body>
<script type="text/javascript">
$(function(){
    //设置保管人id和使用部门Id
    var applyerID = "${departmentalSeal.applyerID}";
    var project_leaderID = "${departmentalSeal.project_leaderID}";
    if(applyerID!=null&&applyerID!=""){
        $("input[name='m:departmental_seal:applyerID']").val(applyerID);
    };
    if(project_leaderID!=null&&project_leaderID!=""){
        $("input[name='m:departmental_seal:project_leaderID']").val(project_leaderID);
    };
}); 
</script>
</html>
