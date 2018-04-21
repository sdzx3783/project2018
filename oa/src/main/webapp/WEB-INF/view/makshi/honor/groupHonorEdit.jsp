<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>集体荣誉</title>
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
			var frm=$('#groupHonorForm').form();
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
							$('#groupHonorForm').submit();
						});
					}else{
						frm.handleFieldName();
						$('#groupHonorForm').submit();
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
						window.location.href = "${ctx}/makshi/honor/groupHonor/list.ht";
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
    	<form id="groupHonorForm" method="post" action="save.ht">
    		<table class="oa-table--default" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
    			<c:choose>
			    <c:when test="${not empty groupHonor.id}">
			        <caption>编辑集体荣誉</caption>
			    </c:when>
			    <c:otherwise>
			        <caption>添加集体荣誉</caption>
			    </c:otherwise>	
		    	</c:choose>
			  <tr>
			   <th>荣誉编号</th>
			   <td><input type="text" name="m:group_honor:honor_num"  class="oa-new-input" value="${groupHonor.honor_num}" validate="{maxlength:200}" isflag="tableflag" /></td>
			   <th>奖项名称</th>
			    <td><input type="text" name="m:group_honor:honor_name"  class="oa-new-input" value="${groupHonor.honor_name}" validate="{maxlength:200}" isflag="tableflag" /></td>
			  </tr>
			  <tr>
			   <th>奖项级别</th>
			   <td><input type="text" name="m:group_honor:honor_level"  class="oa-new-input" value="${groupHonor.honor_level}" validate="{maxlength:200}" isflag="tableflag" /></td>
			   <th>获奖项目</th>
			   <td><input type="text" name="m:group_honor:award_project"  class="oa-new-input" value="${groupHonor.award_project}" validate="{maxlength:200}" isflag="tableflag" /></td>
			  </tr>
			  <tr>
			   <th>发证机构</th>
			   <td><input type="text" name="m:group_honor:issuing_authority"  class="oa-new-input" value="${groupHonor.issuing_authority}" validate="{maxlength:200}" isflag="tableflag" /></td>
			   <th>发证时间</th>
			   <td><input name="m:group_honor:issuing_date" type="text"  data-class="oa-new-input" class="Wdate"  datefmt="yyyy-MM" value='${groupHonor.issuing_date}' /></td>
			  </tr>
			  <tr>
			   <th>颁发形式</th>
			   <td><input name="m:group_honor:get_type" type="text" class="oa-new-input" validate="{maxlength:200}" value='${groupHonor.get_type}' /></td>
			   <th></th>
			   <td></td>
			  </tr>
			  <tr>
			   <th>附件</th>
			   <td rowspan="1" colspan="3">
			   	 <input  ctltype="attachment" name="m:group_honor:file" isdirectupload="0" right="w" value='${groupHonor.file}' validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}" />
			   </td>
			  </tr>
    		</table>
    		<input type="hidden" name="id" value="${groupHonor.id}"/>
			<input type="hidden" id="saveData" name="saveData"/>
			<input type="hidden" name="executeType"  value="start" />
    	</form>
    </div>
</body>
</html>
