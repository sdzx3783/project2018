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
    <script type="text/javascript" src="${ctx}/codegen/js/OfficeControl.js"></script>
    <script type="text/javascript" src="${ctx}/codegen/js/OfficePlugin.js"></script>
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
						window.location.href = window.close();
					}
				});
			} else {
				$.ligerDialog.error(obj.getMessage(),"提示信息");
			}
		}
	</script>
	<style type="text/css">
		#selectType{
		width:auto !important;
		min-width:auto;
		border:0px !important;
		height:auto !important;
		}
        
	.l-box-select.l-box-select-absolute{
	  height:149px !important;
	  width:250px !important;
	}

	.l-text.l-text-combobox{
	    width: 250px !important;
	    height: 32px !important;
	    border-radius: 3px;
	}    
	
	#office_div_m_dispatch_dispatch_content{
		height:750px !important;
	}
	
	.l-trigger, .l-trigger-hover, .l-trigger-pressed  {
 		 margin-top: 4px;
	}
	</style>
</head>
<body class="oa-mw-page">
<div class="oa-pd20">
	<div class="panel-top">
		<a class="oa-button oa-button--primary oa-button--blue save" id="dataFormSave">保存</a>
	    <a class="oa-button oa-button--primary oa-button--blue" href="javascript:void(0);"  onclick="window.opener=null;window.close()">返回</a> 
	</div>
	
	<div class="oa-mg20">
		<form id="dispatchForm" method="post" action="save.ht">
			<table class="oa-table--default" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
				<caption>发文总表</caption>
 		    	<tbody>
 					<tr>
 						<th>发文类型</th>
 						<td><input id="selectType" lablename="发文类型" class="oa-new-input dicComboTree" nodekey="gwlx" <c:if test="${ empty dispatch.type}"> value='${dic.dicName}'</c:if> <c:if test="${ not empty dispatch.type}"> value='${dispatch.type}' </c:if> validate="{empty:false}" name="m:dispatch:type"  /></td>
 						<th>发文时间</th>
 						<td>
 							<input data-class="oa-new-input" red-element="time" type="text" name="m:dispatch:time" lablename="发文时间" class="Wdate" value="<fmt:formatDate value="${dispatch.time}" pattern='yyyy-MM-dd'/>" validate="{&quot;maxlength&quot;:50,&quot;required&quot;:&quot;true&quot;}" isflag="tableflag" />
 						</td>
 					</tr>
 					<tr>
 						<th>发文标题</th>
 						<td colspan="3"><input type="text" red-element="disTitle" name="m:dispatch:title" lablename="发文标题" class="oa-new-input" value="${dispatch.title}" validate="{maxlength:200}" isflag="tableflag" style="width:800px;"/></td>
 					</tr>
 					<tr>
 						<th>发文字号</th>
 						<td colspan="3"><input type="text" red-element="dispatch_id" name="m:dispatch:dispatch_id" id="fontSize" lablename="发文字号" class="oa-new-input" value="${dispatch.dispatch_id}" validate="{maxlength:50}" isflag="tableflag" /></td>
 					</tr>
 					<tr>
 						<th>主题词</th>
 						<td colspan="3"><input type="text" red-element="mainKey" name="m:dispatch:keyword" lablename="主题词" class="oa-new-input" value="${dispatch.keyword}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td></td>
 					</tr>
 					<tr>
 						<th rowspan="2">主送单位</th>
 						<td><input parser="selectortable" red-element="send_unit1" name="m:dispatch:send_unit_department" type="text" ctltype="selector" class="link oa-new-input oa-middle orgs" lablename="主送单位" value="${dispatch.send_unit_department}" validate="{empty:false}" readonly="readonly" scope="{&#39;value&#39;:&#39;all&#39;,&#39;type&#39;:&#39;system&#39;}"/></td>
 						<th rowspan="2">抄送单位</th>
 						<td><input parser="selectortable" red-element="cc_unit1" name="m:dispatch:cc_unit_department" lablename="抄送单位" type="text" ctltype="selector" class="link oa-new-input oa-middle orgs"  value="${dispatch.cc_unit_department}" validate="{empty:false}" readonly="readonly" scope="{&#39;value&#39;:&#39;all&#39;,&#39;type&#39;:&#39;system&#39;}"/></td>
 					</tr>
 					<tr>
 						<td>
 							<input parser="selectortable" red-element="send_unit2" name="m:dispatch:send_unit_person" lablename="主送人" type="text" ctltype="selector" class="link oa-new-input oa-middle users"  value="${dispatch.send_unit_person}" validate="{empty:false}" readonly="readonly" scope="{&#39;value&#39;:&#39;all&#39;,&#39;type&#39;:&#39;system&#39;}"/>
 						</td>
 						<td>
 							<input parser="selectortable" red-element="cc_unit2" name="m:dispatch:cc_unit_person" lablename="抄送人" type="text" ctltype="selector" class="link oa-new-input oa-middle users"  value="${dispatch.cc_unit_person}" validate="{empty:false}" readonly="readonly" scope="{&#39;value&#39;:&#39;all&#39;,&#39;type&#39;:&#39;system&#39;}"/>
 						</td>
 					</tr>
 					<tr>
 						<th>正文</th>
 						<td colspan="3">
 							<%-- <input parser="officetable" type="hidden" class="hidden" name="m:dispatch:dispatch_content" lablename="正文"  right="w" controltype="office" value="${dispatch.dispatch_content}"/> --%>
 						</td>
 					</tr>
  					<tr>
  						<th>拟稿人</th>
   							<td  colspan="3">
   	  							<input parser="selectortable" name="m:dispatch:draft_person" lablename="拟稿人" type="text" ctltype="selector" class="link oa-new-input oa-middle user"  value="${dispatch.draft_person}" validate="{empty:false}" readonly="readonly" scope="{&#39;value&#39;:&#39;all&#39;,&#39;type&#39;:&#39;system&#39;}"/>
   							</td>
   					</tr>
   					<tr>
   						<th>附件</th>
  						<td colspan="3">
   	 						<input ctltype="attachment" name="m:dispatch:attachment" isdirectupload="0" right="w" value='${dispatch.attachment}' validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}" /></td>
   						</td>
  					</tr>
				</tbody> 
			</table>
			<input type="hidden" name="id" value="${dispatch.id}"/>
			<input type="hidden" name="dicId" value="${docDic.dicId}"/>
			<input type="hidden" name="docId" value="${docDic.docId}"/>
			<input type="hidden" id="saveData" name="saveData"/>
			<input type="hidden" name="executeType"  value="start" />
		</form>
	</div>
</div>
<script type="text/javascript">
$(function(){
	var draft_personID = '${dispatch.draft_personID}';
	$("input[name='m:dispatch:draft_personID']").val(draft_personID);
	var send_unit_departmentID = '${dispatch.send_unit_departmentID}';
	$("input[name='m:dispatch:send_unit_departmentID']").val(send_unit_departmentID);
	var send_unit_personID = '${dispatch.send_unit_personID}';
	$("input[name='m:dispatch:send_unit_personID']").val(send_unit_personID);
	var cc_unit_departmentID = '${dispatch.cc_unit_departmentID}';
	$("input[name='m:dispatch:cc_unit_departmentID']").val(cc_unit_departmentID);
	var cc_unit_personID = '${dispatch.cc_unit_personID}';
	$("input[name='m:dispatch:cc_unit_personID']").val(cc_unit_personID);
});

	$('body').on('change','#selectType',function(){
	    var selectType = $(this).val();
	    if(!selectType){
	    	return;
	    }
	    $.ajax({
	      type : "post",
	      url :"/makshi/fontsize/fontSize/selectType.ht",  
	      data :{type:selectType},
	      dataType: "json",
	      success:function(data){
	        $("#fontSize").val(data);
	      },
	      error:function(data){
	        alert("获取发文字号失败");
	        return false;
	      }
	    });
	});
</script>
</body>
</html>
