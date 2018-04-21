<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 发文总表</title>
	<%@include file="/codegen/include/customForm.jsp" %>
	<%@include file="/commons/include/ueditor.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/AttachMent.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/FlowDetailWindow.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery/plugins/jquery.attach.js" ></script>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/system/FlexUploadDialog.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/system/HtmlUploadDialog.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/form/TablePermission.js"></script>
    <script type="text/javascript" src="${ctx}/js/lg/plugins/ligerComboBox.js"></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#dispatchForm').form();
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
							$('#dispatchForm').submit();
						});
					}else{
						frm.handleFieldName();
						$('#dispatchForm').submit();
					}
				}
			});
			$("a.run").click(function() {
				frm.ajaxForm(options);
				$("#saveData").val(0);
				$('#dispatchForm').attr("action","run.ht");
				if(frm.valid()){
					if(WebSignPlugin.hasWebSignField){
						WebSignPlugin.submit();
					}
					data=CustomForm.getData();
					//设置表单数据
					$("#formData").val(data);
					frm.handleFieldName();
					OfficePlugin.submit();
					$('#dispatchForm').submit();
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
						window.location.href = "${ctx}/makshi/dispatch/dispatch/send.ht";
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
			    <c:when test="${not empty dispatchItem.id}">
			        <span class="tbar-label"><span></span>编辑发文总表</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label"><span></span>添加发文总表</span>
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
				<div class="group"><a class="link back" href="send.ht"><span></span>返回</a></div>
			</div>
		</div>
	</div>
	<form id="dispatchForm" method="post" action="save.ht">
		<div type="custform">
			<div class="panel-detail">
				<table class="formTable" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
 <tbody>
  <tr class="firstRow">
   <td class="formHead" colspan="4" style="word-break: break-all;">发文总表 &nbsp; &nbsp;</td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">发文字号:</td>
   <td style="width: 35%" class="formInput">
   	   <span name="editable-input" style="display:inline-block;" isflag="tableflag"> 
	  	 <input type="text" name="m:dispatch:dispatch_id" lablename="发文字号" class="inputText" value="${dispatch.dispatch_id}" validate="{maxlength:50}" isflag="tableflag" /> 
	   </span>
   </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">发文时间:</td>
   <td style="width: 35%" class="formInput"> 
	   <span name="editable-input" style="display:inline-block;" isflag="tableflag"> 
	  	 <input type="text" name="m:dispatch:time" lablename="发文时间" class="inputText" value="${dispatch.time}" validate="{maxlength:50}" isflag="tableflag" /> 
	   </span>
  </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">发文标题:</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3">
	   	<span name="editable-input" style="display:inline-block;" isflag="tableflag"> 
	   		<input type="text" name="m:dispatch:title" lablename="发文标题" class="inputText" value="${dispatch.title}" validate="{maxlength:50}" isflag="tableflag" /> 
		</span> 
  </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">发文类型:</td>
   <td style="width: 35%" class="formInput">
   		<input lablename="发文类型" class="dicComboTree l-text-field" nodekey="gwlx" value="${dispatch.type}" validate="{empty:false}" name="m:dispatch:type" height="200" width="125" /> 
   </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">紧急程度:</td>
   <td style="width: 35%" class="formInput">
   
    <span name="editable-input" style="display:inline-block;padding:2px;" class="selectinput"> 
    <select name="m:dispatch:urgency_degree" lablename="紧急程度" controltype="select" validate="{empty:false}" val="${dispatch.urgency_degree}"> 
    <option value="0">未选择</option> <option value="1">普通</option> <option value="2">紧急</option><option value="3">非常紧急</option></select> </span> 
   
   </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">主题词:</td>
   <td style="width: 35%" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> 
   	<input type="text" name="m:dispatch:keyword" lablename="主题词" class="inputText" value="${dispatch.keyword}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">秘密等级:</td>
   <td style="width: 35%" class="formInput">
  	 <span name="editable-input" style="display:inline-block;padding:2px;" class="selectinput"> 
   	 <select name="m:dispatch:secret_level" lablename="秘密等级" controltype="select" validate="{empty:false}" val="${dispatch.secret_level}"> 
    	<option value="0">未选择</option> <option value="1">秘密</option> <option value="2">机密</option><option value="3">绝密</option></select> </span> 
   </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">主送单位:</td>
   <td style="width: 35%" class="formInput">
  		<span name="editable-input" style="display:inline-block;" isflag="tableflag"> 
	  	 <input type="text" name="m:dispatch:send_unit" lablename="主送单位" class="inputText" value="${dispatch.send_unit}" validate="{maxlength:50}" isflag="tableflag" /> 
	   </span>
   </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">抄送单位:</td>
   <td style="width: 35%" class="formInput"><span name="editable-input" style="display:inline-block;" isflag="tableflag"> 
	  	 <input type="text" name="m:dispatch:cc_unit" lablename="抄送单位" class="inputText" value="${dispatch.cc_unit}" validate="{maxlength:50}" isflag="tableflag" /> 
	   </span></td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">正文:</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3">
<%--     <input parser="officetable" type="hidden" class="hidden" name="m:dispatch:dispatch_content" lablename="正文" controltype="office" value="${dispatch.dispatch_content}"/> --%>
   
   </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">拟稿人：</td>
   <td class="formInput" rowspan="1" colspan="3">
   	  <input type="text" name="m:dispatch:draft_person" lablename="拟稿人" class="inputText" value="${dispatch.draft_person}" validate="{maxlength:50}" isflag="tableflag" /> 
   </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">核稿人:</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3">
   <input parser="selectortable" name="m:dispatch:check_person" type="text" ctltype="selector" class="users" lablename="核稿人" value="${dispatch.check_person}" validate="{empty:false}" scope="{&#39;value&#39;:&#39;all&#39;,&#39;type&#39;:&#39;system&#39;}"/>
	</td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">会签人员:</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3">
   	<input parser="selectortable" name="m:dispatch:countersign_person" type="text" ctltype="selector" class="users" lablename="会签人员" value="${dispatch.countersign_person}" validate="{empty:false}" scope="{&#39;value&#39;:&#39;all&#39;,&#39;type&#39;:&#39;system&#39;}"/>
   </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">附件:</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3">
   	 <input ctltype="attachment" name="m:dispatch:attachment" isdirectupload="0" right="w" value='${dispatch.attachment}' validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}" /></td>
   </td>
  </tr>
 </tbody>
</table>
			</div>
		</div>
		<input type="hidden" name="id" value="${dispatch.id}"/>
		<input type="hidden" id="saveData" name="saveData"/>
		<input type="hidden" name="executeType"  value="start" />
	</form>
</body>
</html>
