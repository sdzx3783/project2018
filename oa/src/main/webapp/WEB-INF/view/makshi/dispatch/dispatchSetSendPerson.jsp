<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>发文总表管理</title>
<%@include file="/commons/include/get.jsp" %>
<%@include file="/codegen/include/customForm.jsp" %>
<%@include file="/commons/include/ueditor.jsp" %>
<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
</head>
<body>
	<div class="panel">
		<div class="oa-mg20">
    	    <form id="dispatchSendForm" method="post" action="sendToPerson.ht">
    	    	<table class="oa-table--default" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
	  				<input  name="receiptPerson" type="text" ctltype="selector" class="user-search oa-new-input oa-middle users"   validate="{required:true}" /></td>
    	    	</table>
  				<input type="hidden" name="runId" include="1" id="runId" value='${runId}'>
  				<input type="hidden" name="title" include="1" id="runId" value='${dispatch.title}'>
    	    </form>
		</div> <!-- end of panel -->
		 <div class="oa-fl oa-mgb10 oa-mgh20">
             <button id="sendDisDoc" class="oa-button oa-button--primary oa-button--blue">发送</button>
         </div>
</body>
<script type="text/javascript">
$(function() {
	var options={};
	if(showResponse){
		options.success=showResponse;
	}
	var frm=$('#dispatchSendForm').form();
	frm.ajaxForm(options);
	$("#sendDisDoc").click(function(){
		$.ligerDialog.confirm("确定发送?","提示",function(rtn){
		    if(rtn){
		    	$("#dispatchSendForm").submit();
		    }
		});
	});
});
function showResponse(responseText) {
    var obj = new com.hotent.form.ResultMessage(responseText);
    if (obj.isSuccess()) {
        $.ligerDialog.warn("发送成功","提示信息", function(rtn) {
            	window.close(); 
        });
    } else {
        $.ligerDialog.error(obj.getMessage(),"提示信息");
    }
}
</script>
</html>


