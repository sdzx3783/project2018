<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 方案审批表</title>
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
			var frm=$('#hdProgrammeApprovalForm').form();
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
							$('#hdProgrammeApprovalForm').submit();
						});
					}else{
						frm.handleFieldName();
						$('#hdProgrammeApprovalForm').submit();
					}
				}
			});
			$("a.run").click(function() {
				frm.ajaxForm(options);
				$("#saveData").val(0);
				$('#hdProgrammeApprovalForm').attr("action","run.ht");
				if(frm.valid()){
					if(WebSignPlugin.hasWebSignField){
						WebSignPlugin.submit();
					}
					data=CustomForm.getData();
					//设置表单数据
					$("#formData").val(data);
					frm.handleFieldName();
					OfficePlugin.submit();
					$('#hdProgrammeApprovalForm').submit();
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
						window.location.href = "${ctx}/makshi/river/hdProgrammeApproval/list.ht";
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
			    <c:when test="${not empty hdProgrammeApprovalItem.id}">
			        <span class="tbar-label"><span></span>编辑方案审批表</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label"><span></span>添加方案审批表</span>
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
	<form id="hdProgrammeApprovalForm" method="post" action="save.ht">
		<div type="custform">
			<div class="panel-detail">
				<table class="formTable" border="1" cellspacing="0" cellpadding="2" parser="addpermission"> 
 <tbody> 
  <tr class="firstRow"> 
   <td class="formHead" colspan="4" style="word-break: break-all;">方案审批流程</td> 
   <td class="formHead" colspan="1" style="word-break: break-all;"><br /></td> 
   <td class="formHead" colspan="1" style="word-break: break-all;"><br /></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">项目名称</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">阶段名称<br /></td> 
   <td style="width: 35%" class="formInput"></td> 
   <td style="width: 35%" class="formInput"><br /></td> 
   <td style="width: 35%" class="formInput"><br /></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">任务名称</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right"><span style="font-size: 12px; text-align: right; background-color: rgb(240, 241, 241);">拟稿人</span><br /></td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"> <input name="m:hd_programme_approval:name" type="text" ctltype="selector" class="user" lablename="拟稿人" value="${hdProgrammeApproval.name}" validate="{empty:false}" readonly="readonly" showcuruser="0" /> </td> 
   <td style="width: 35%" class="formInput"><br /></td> 
   <td style="width: 35%" class="formInput"><br /></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right"><span style="font-size: 12px; text-align: right; background-color: rgb(240, 241, 241);">拟稿时间</span></td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"> <input name="m:hd_programme_approval:date" type="text" class="Wdate" displaydate="0" datefmt="yyyy-MM-dd" value="<fmt:formatDate value='${hdProgrammeApproval.date}' pattern='yyyy-MM-dd'/>" validate="{empty:false}" /> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right"><span style="font-size: 12px; text-align: right; background-color: rgb(240, 241, 241);">方案名称</span></td> 
   <td style="width: 35%" class="formInput"></td> 
   <td style="width: 35%" class="formInput"><br /></td> 
   <td style="width: 35%" class="formInput"><br /></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right"><span style="font-size: 12px; text-align: right; background-color: rgb(240, 241, 241);">方案内容</span></td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <textarea name="m:hd_programme_approval:content" lablename="方案内容" class="l-textarea" rows="5" cols="40" validate="{maxlength:1000}" isflag="tableflag">${hdProgrammeApproval.content}</textarea> </span> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right"><span style="font-size: 12px; text-align: right; background-color: rgb(240, 241, 241);">备注</span></td> 
   <td style="width: 35%" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <textarea name="m:hd_programme_approval:note" lablename="备注" class="l-textarea" rows="5" cols="40" validate="{maxlength:1000}" isflag="tableflag">${hdProgrammeApproval.note}</textarea> </span> </td> 
   <td style="width: 35%" class="formInput"><br /></td> 
   <td style="width: 35%" class="formInput"><br /></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">附件</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right"><br /></td> 
   <td style="width: 35%" class="formInput"><br /></td> 
   <td style="width: 35%" class="formInput"><br /></td> 
   <td style="width: 35%" class="formInput"><br /></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">项目负责人</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right"><br /></td> 
   <td style="width: 35%" class="formInput"><br /></td> 
   <td style="width: 35%" class="formInput"><br /></td> 
   <td style="width: 35%" class="formInput"><br /></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">部门负责人</td> 
   <td style="width: 35%" class="formInput"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right"><br /></td> 
   <td style="width: 35%" class="formInput"><br /></td> 
   <td style="width: 35%" class="formInput"><br /></td> 
   <td style="width: 35%" class="formInput"><br /></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">是否需要审定</td> 
   <td style="width: 35%" class="formInput"> <span> <label><input val="${hdProgrammeApproval.isNeedCheck}" type="radio" name="m:hd_programme_approval:isNeedCheck" value="1" lablename="是否需要审定" validate="{}" />需要</label> <label><input val="${hdProgrammeApproval.isNeedCheck}" type="radio" name="m:hd_programme_approval:isNeedCheck" value="0" lablename="是否需要审定" validate="{}" />不需要</label> </span> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">总工办/分管领导<br /></td> 
   <td style="width: 35%" class="formInput"> <input name="m:hd_programme_approval:finalChecker" type="text" ctltype="selector" class="user" lablename="总工办/分管领导" value="${hdProgrammeApproval.finalChecker}" validate="{empty:false}" readonly="readonly" showcuruser="0" /> </td> 
   <td style="width: 35%" class="formInput"><br /></td> 
   <td style="width: 35%" class="formInput"><br /></td> 
  </tr> 
  <tr style="display:none"> 
   <td class="formInput" colspan="1" rowspan="1"><br /></td> 
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all;"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:hd_programme_approval:projectTaskId" lablename="项目任务id" class="inputText" value="${hdProgrammeApproval.projectTaskId}" validate="{maxlength:20}" isflag="tableflag" /> </span> </td> 
   <td class="formInput" colspan="1" rowspan="1"><br /></td> 
   <td class="formInput" colspan="1" rowspan="1"><br /></td> 
   <td class="formInput" colspan="1" rowspan="1"><br /></td> 
   <td class="formInput" colspan="1" rowspan="1"><br /></td> 
  </tr> 
 </tbody> 
</table>
			</div>
		</div>
		<input type="hidden" name="id" value="${hdProgrammeApproval.id}"/>
		<input type="hidden" id="saveData" name="saveData"/>
		<input type="hidden" name="executeType"  value="start" />
	</form>
</body>
</html>
