<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 人才引进</title>
	<%@include file="/codegen/include/customForm.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/AttachMent.js"></script>
	<%@include file="/commons/include/ueditor.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/FlowDetailWindow.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/TablePermission.js"></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#personInForm').form();
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
							$('#personInForm').submit();
						});
					}else{
						frm.handleFieldName();
						$('#personInForm').submit();
					}
				}
			});
			$("a.run").click(function() {
				frm.ajaxForm(options);
				$("#saveData").val(0);
				$('#personInForm').attr("action","run.ht");
				if(frm.valid()){
					if(WebSignPlugin.hasWebSignField){
						WebSignPlugin.submit();
					}
					data=CustomForm.getData();
					//设置表单数据
					$("#formData").val(data);
					frm.handleFieldName();
					OfficePlugin.submit();
					$('#personInForm').submit();
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
						window.location.href = "${ctx}/makshi/talente/personIn/list.ht";
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
			    <c:when test="${not empty personInItem.id}">
			        <span class="tbar-label"><span></span>编辑人才引进</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label"><span></span>添加人才引进</span>
			    </c:otherwise>	
		    </c:choose>
		</div>
		<div class="panel-toolbar">
			<div class="toolBar">
				<div class="group"><a class="link save" id="dataFormSave" href="javascript:;"><span></span>保存</a></div>
				<div class="l-bar-separator"></div>
				<%-- <c:if test="${runId!=0}">
					<div class="group"><a class="link detail"  onclick="FlowDetailWindow({runId:${runId}})" href="javascript:;" ><span></span>流程明细</a></div>
					<div class="l-bar-separator"></div>
				</c:if> --%>
				<div class="group"><a class="link back" href="list.ht"><span></span>返回</a></div>
			</div>
		</div>
	</div>
	<form id="personInForm" method="post" action="save.ht">
		<div type="custform">
			<div class="panel-detail">
				<table class="formTable" border="1" cellspacing="0" cellpadding="2" parser="addpermission"> 
 <tbody> 
  <tr class="firstRow">
   <td class="formHead" colspan="4" style="word-break: break-all;">人才引进申报</td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">人才引进记录编号</td>
   <td style="width: 35%; word-break: break-all;" class="formInput"><span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:person_in:person_in_num" lablename="人才引进记录编号" class="inputText" value="${personIn.person_in_num}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">工号</td>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all;"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:person_in:account" lablename="工号" class="inputText" value="${personIn.account}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">申请人</td>
   <td style="width: 35%; word-break: break-all;" class="formInput"> <input name="m:person_in:applicant" type="text" ctltype="selector" class="user" lablename="申请人" value="${personIn.applicant}" validate="{empty:false}" readonly="readonly" showcuruser="0" /> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">申请时间</td>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all;"> <input name="m:person_in:application_time" type="text" class="Wdate" displaydate="0" datefmt="yyyy-MM-dd" value="<fmt:formatDate value='${personIn.application_time}' pattern='yyyy-MM-dd'/>" validate="{empty:false}" /> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">年龄</td>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all;"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:person_in:age" lablename="年龄" class="inputText" value="${personIn.age}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">身份证号</td>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all;">
   <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:person_in:id_number" lablename="身份证号" class="inputText" value="${personIn.id_number}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">全日制学历</td>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all;"> <span name="editable-input" style="display:inline-block;padding:2px;" class="selectinput"> <select name="m:person_in:edu" lablename="全日制学历" controltype="select" validate="{empty:false}" val="${personIn.edu}"> <option value="0">研究生</option> <option value="1">本科</option> <option value="2">大专</option> <option value="3">其他</option> </select> </span> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">入职时间</td>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all;"> <input name="m:person_in:entryDate" type="text" class="Wdate" displaydate="0" datefmt="yyyy-MM-dd" value="<fmt:formatDate value='${personIn.entryDate}' pattern='yyyy-MM-dd'/>" validate="{empty:false}" /> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">户口所属派出所</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"><span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input   type="text" name="m:person_in:police_station" lablename="户口所属派出所" class="inputText" value="${personIn.police_station}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">户口类型</td>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all;"><span name="editable-input" style="display:inline-block;padding:2px;" class="selectinput"> 
   <select name="m:person_in:residence_type" lablename="户口类型" controltype="select" validate="{empty:false}" val="${personIn.residence_type}">
    <option value="0">非农</option> 
    <option value="1">农业</option> 
     </select> </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">档案保管单位</td>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all;">
   <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:person_in:trust_unit" lablename="档案保管单位" class="inputText" value="${personIn.trust_unit}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">联系方式</td>
   <td class="formInput" colspan="1" rowspan="1">
    <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:person_in:tel_number" lablename="联系方式" class="inputText" value="${personIn.tel_number}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">房产情况</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"><select name="m:person_in:house_status" lablename="户口类型" controltype="select" validate="{empty:false}" val="${personIn.house_status}">
    <option value="0">有</option> 
    <option value="1">无</option> 
     </select> </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">落户地址</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"><select name="m:person_in:settled_situation" lablename="户口类型" controltype="select" validate="{empty:false}" val="${personIn.settled_situation}">
    <option value="0">公司集体户</option> 
    <option value="1">个人/亲戚处</option> 
     </select> </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">申请理由</td>
   <td class="formInput" rowspan="1" colspan="3" style="word-break: break-all;"><span name="editable-input" style="display:inline-block;" isflag="tableflag"> <textarea name="m:person_in:appli_reason" lablename="申请理由" class="l-textarea" rows="5" cols="40" validate="{maxlength:1000}" isflag="tableflag">${personIn.appli_reason}</textarea> </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">备注</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <textarea name="m:person_in:remark" lablename="备注" class="l-textarea" rows="5" cols="40" validate="{maxlength:1000}" isflag="tableflag">${personIn.remark}</textarea> </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">附件</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"><span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input   ctltype="attachment" name="m:person_in:file" lablename="附件" class="inputText" value='${personIn.file}' validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
   </tr>
<%--   <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">附件</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"><span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input   ctltype="attachment" name="m:person_in:file" lablename="附件" class="inputText" value='${personIn.file}' validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">引进状态</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput">
   		<select name="m:person_in:introduction_state" class="select" style="width:245px !important">
			<option value="1" <c:if test="${personIn.introduction_state==1}">selected</c:if> >已完成</option>
			<option value="0" <c:if test="${personIn.introduction_state==0}">selected</c:if> >未完成</option>
		</select>	
   </td> 
  </tr>  --%>
 </tbody> 
</table>
</div>
		</div>
		<input type="hidden" name="id" value="${personIn.id}"/>
		<input type="hidden" id="saveData" name="saveData"/>
		<input type="hidden" name="executeType"  value="start" />
	</form>
</body>
</html>
