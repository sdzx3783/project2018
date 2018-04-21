<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 llyy</title>
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
			var frm=$('#hjRunMonthlyReportsForm').form();
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
							$('#hjRunMonthlyReportsForm').submit();
						});
					}else{
						frm.handleFieldName();
						$('#hjRunMonthlyReportsForm').submit();
					}
				}
			});
			$("a.run").click(function() {
				frm.ajaxForm(options);
				$("#saveData").val(0);
				$('#hjRunMonthlyReportsForm').attr("action","run.ht");
				if(frm.valid()){
					if(WebSignPlugin.hasWebSignField){
						WebSignPlugin.submit();
					}
					data=CustomForm.getData();
					//设置表单数据
					$("#formData").val(data);
					frm.handleFieldName();
					OfficePlugin.submit();
					$('#hjRunMonthlyReportsForm').submit();
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
						window.location.href = "${ctx}/makshi/hj/hjRunMonthlyReports/list.ht";
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
			    <c:when test="${not empty hjRunMonthlyReportsItem.id}">
			        <span class="tbar-label"><span></span>编辑llyy</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label"><span></span>添加llyy</span>
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
	<form id="hjRunMonthlyReportsForm" method="post" action="save.ht">
		<div type="custform">
			<div class="panel-detail">
				<table class="formTable" border="1" cellspacing="0" cellpadding="2" data-sort="sortDisabled" parser="addpermission"> 
 <tbody> 
  <tr class="firstRow"> 
   <td class="formHead" style="-ms-word-break: break-all;" colspan="4">运营日报审核</td> 
   <td class="formHead" style="-ms-word-break: break-all;" colspan="1"><br /></td> 
   <td class="formHead" style="-ms-word-break: break-all;" colspan="1"><br /></td> 
  </tr> 
  <tr> 
   <td align="right" class="formTitle" style="width: 15%; -ms-word-break: break-all;">提交人:</td> 
   <td class="formInput" style="width: 35%;"><br /> <input name="m:hj_run_monthly_reports:name" type="text" ctltype="selector" class="user" lablename="提交人" value="${hjRunMonthlyReports.name}" validate="{empty:false,required:true}" readonly="readonly" showcuruser="0" /> </td> 
   <td align="right" class="formTitle" style="width: 15%; -ms-word-break: break-all;">提交日期:</td> 
   <td class="formInput" style="width: 35%;"><br /> <input name="m:hj_run_monthly_reports:date" type="text" class="Wdate" displaydate="0" datefmt="yyyy-MM-dd" value="<fmt:formatDate value='${hjRunMonthlyReports.date}' pattern='yyyy-MM-dd'/>" validate="{empty:false,required:true}" /> </td> 
   <td class="formInput" style="width: 35%;"><br /></td> 
   <td class="formInput" style="width: 35%;"><br /></td> 
  </tr> 
  <tr> 
   <td align="right" class="formTitle" style="width: 15%; -ms-word-break: break-all;">所属部门:</td> 
   <td class="formInput" style="width: 35%;"> <input name="m:hj_run_monthly_reports:department" type="text" ctltype="selector" class="org" lablename="所属部门" value="${hjRunMonthlyReports.department}" validate="{empty:false,required:true}" readonly="readonly" showcurorg="0" /> </td> 
   <td align="right" class="formTitle" style="width: 15%; -ms-word-break: break-all;">项目名称:</td> 
   <td class="formInput" style="width: 35%;"></td> 
   <td class="formInput" style="width: 35%;"><br /></td> 
   <td class="formInput" style="width: 35%;"><br /></td> 
  </tr> 
  <tr> 
   <td align="right" class="formTitle" style="width: 15%; word-break: break-all;">月报名称:</td> 
   <td class="formInput" style="width: 35%;"></td> 
   <td align="right" class="formTitle" style="width: 15%; word-break: break-all;">月报内容:</td> 
   <td class="formInput" style="width: 35%;"><br /> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <textarea name="m:hj_run_monthly_reports:content" lablename="月报内容" class="l-textarea" rows="5" cols="40" validate="{maxlength:1000}" isflag="tableflag">${hjRunMonthlyReports.content}</textarea> </span> </td> 
   <td class="formInput" style="width: 35%;"><br /></td> 
   <td class="formInput" style="width: 35%;"><br /></td> 
  </tr> 
  <tr> 
   <td align="right" class="formTitle" style="width: 15%; -ms-word-break: break-all;">备注:</td> 
   <td class="formInput" style="width: 35%; -ms-word-break: break-all;" rowspan="1" colspan="3"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:hj_run_monthly_reports:note" lablename="备注" class="inputText" value="${hjRunMonthlyReports.note}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
   <td class="formInput" style="width: 35%; -ms-word-break: break-all;" rowspan="1" colspan="1"><br /></td> 
   <td class="formInput" style="width: 35%; -ms-word-break: break-all;" rowspan="1" colspan="1"><br /></td> 
  </tr> 
  <tr> 
   <td align="right" class="formTitle" style="width: 15%; -ms-word-break: break-all;">附件:</td> 
   <td class="formInput" style="width: 35%; -ms-word-break: break-all;" rowspan="1" colspan="3"><br /></td> 
   <td class="formInput" style="width: 35%; -ms-word-break: break-all;" rowspan="1" colspan="1"><br /></td> 
   <td class="formInput" style="width: 35%; -ms-word-break: break-all;" rowspan="1" colspan="1"><br /></td> 
  </tr> 
  <tr> 
   <td align="right" class="formTitle" style="width: 15%; word-break: break-all;">现场负责人审核:</td> 
   <td class="formInput" style="width: 35%; word-break: break-all;" rowspan="1" colspan="3"><br /> <input name="m:hj_run_monthly_reports:charger" type="text" ctltype="selector" class="users" lablename="现场负责人审核" value="${hjRunMonthlyReports.charger}" validate="{empty:false}" readonly="readonly" /> </td> 
   <td class="formInput" style="width: 35%; -ms-word-break: break-all;" rowspan="1" colspan="1"><br /></td> 
   <td class="formInput" style="width: 35%; -ms-word-break: break-all;" rowspan="1" colspan="1"><br /></td> 
  </tr> 
  <tr> 
   <td align="right" class="formTitle" style="width: 15%; word-break: break-all;">部门副经理审核:</td> 
   <td class="formInput" style="width: 35%; word-break: break-all;" rowspan="1" colspan="3"><br /></td> 
   <td class="formInput" style="width: 35%; -ms-word-break: break-all;" rowspan="1" colspan="1"><br /></td> 
   <td class="formInput" style="width: 35%; -ms-word-break: break-all;" rowspan="1" colspan="1"><br /></td> 
  </tr> 
  <tr style="display:none"> 
   <td class="formInput" colspan="1" rowspan="1"><br /></td> 
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all;"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:hj_run_monthly_reports:projectTaskId" lablename="项目任务id" class="inputText" value="${hjRunMonthlyReports.projectTaskId}" validate="{maxlength:20}" isflag="tableflag" /> </span> </td> 
   <td class="formInput" colspan="1" rowspan="1"><br /></td> 
   <td class="formInput" colspan="1" rowspan="1"><br /></td> 
   <td class="formInput" colspan="1" rowspan="1"><br /></td> 
   <td class="formInput" colspan="1" rowspan="1"><br /></td> 
  </tr> 
 </tbody> 
</table>
			</div>
		</div>
		<input type="hidden" name="id" value="${hjRunMonthlyReports.id}"/>
		<input type="hidden" id="saveData" name="saveData"/>
		<input type="hidden" name="executeType"  value="start" />
	</form>
</body>
</html>
