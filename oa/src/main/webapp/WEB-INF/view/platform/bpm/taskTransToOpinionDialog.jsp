<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<%@include file="/commons/include/form.jsp" %>
<title>流转反馈</title>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
<style>
	.J_disagree {
		display: none;
	}
	.button__wrap {
		margin: 10px 0;
	}
	a.button.btn {
		padding: 6px 15px;
	}
	.panel-toolbar {
		margin-top: 0;
	}
</style>
<script type="text/javascript">
	/*KILLDIALOG*/
	var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)
	
	var taskId=${param.taskId};
	//var formData= window.dialogArguments.data;
	var formData=dialog.get("obj").data;
	
	
	function save(isAgree){
		var rtn=$("#frmComm").form().valid();
		if(!rtn) return;
		var content=$("#opinion").val();
		var informType=$.getChkValue("informType");
		
		var params= {isAgree:isAgree,opinion:content,informType:informType,taskId:taskId,formData:formData};
		var url=__ctx +"/platform/bpm/task/saveOpinion.ht";
		$.post(url,params,function(msg){
			var obj=new com.hotent.form.ResultMessage(msg);
			if(obj.isSuccess()){
				 $.ligerDialog.success("意见反馈成功","提示信息",function(){
					 //window.returnValue="ok";
					 dialog.get("sucCall")("ok");
		    		 dialog.close(); 
		    	 });
			}else{
				 $.ligerDialog.err("提示信息","意见反馈失败",obj.getMessage());
			}
		});
	 }
	function setValue(val){
		$("#opinion").val(val);
		$(".error").remove();
	}
	function toggleAgree(isAgree) {
		//$("#opinion").val('');
		//if(isAgree) {
			//$(".J_commonOne").attr("onclick", "setValue('同意')").text("同意");
			//$(".J_commonTwo").attr("onclick", "setValue('资料齐全')").text("资料齐全");
			//$(".J_confirm").attr("onclick", "save(true)");		
		//}else {
			//$(".J_commonOne").attr("onclick", "setValue('不同意')").text("不同意");
			//$(".J_commonTwo").attr("onclick", "setValue('信息不全')").text("信息不全");
			//$(".J_confirm").attr("onclick", "save(false)");
		//}
		/* var content=$.trim($("#opinion").val());
		if(!isAgree && content==''){
			$.ligerDialog.error("请填写反馈意见");
			return;
		} */
		save(isAgree);
	}
</script>
</head>
<body>
<div class="panel">
	<div class="panel-top">
		<div class="tbar-title">
			<span class="tbar-label">意见反馈</span>
		</div>
		<div class="panel-toolbar">
			<div class="toolBar">
				<div class="group"><a class="link agree" href="javascript:;" onclick="toggleAgree(true)"><span></span>同意</a></div>
				<div class="l-bar-separator"></div>
				<div class="group"><a class="link notAgree" href="javascript:;" onclick="toggleAgree(false)"><span></span>反对</a></div>
			</div>
		</div>
	</div>
	<div class="panel-body">
		<form id="frmComm">
		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<th>提醒方式:</th>
				<td>
					<c:forEach items="${handlersMap}" var="item">
						<input type="checkbox" name="informType" value="${item.key }"  <c:if test="${item.value.isDefaultChecked}">checked="checked"</c:if> />
					    ${item.value.title }
					</c:forEach>
				</td>
			</tr>
			<tr>
				<th>反馈意见:</th>
				<td>
					<textarea rows="4" cols="50" style="width:300px" id="opinion" name="opinion" validate="{required:true,maxLength:500}" maxLength="500"></textarea>
				</td>
			</tr>
			<tr>
				<th>常用语</th>
				<td>
					<div class="button__wrap">
						<a class="button reload J_commonOne" style="background: #f7a751 !important;" onclick="setValue('同意')">同意</a>
						<a class="button reload J_commonTwo" style="background: #f7a751 !important;" onclick="setValue('资料齐全')">资料齐全</a>						
						<a class="button reload J_commonThd" style="background: #f7a751 !important;" onclick="setValue('不同意')">不同意</a>						
						<a class="button reload J_commonFou" style="background: #f7a751 !important;" onclick="setValue('信息不全')">信息不全</a>						
					</div>
				</td>
			</tr>
			<tr style="display: none;">
				<th></th>
				<td>
					<div class="button__wrap">
						<a class="button btn J_confirm" href="javascript:;" onclick="save(true)">确定</a>
						<a class="button btn" href="javascript:;" onclick="dialog.close()">关闭</a>
					</div>
				</td>
			</tr>
		</table>
		</form>
	</div>
</div>
</body>
</html>