<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<%@include file="/commons/include/form.jsp" %>
<title>审批意见</title>
<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
<script type="text/javascript">
/*KILLDIALOG*/
var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)

$().ready(function (){
	var isRequired = '${isRequired}';
	if(isRequired == '1'){
		$("#opinion").attr("validate","{required:true,maxLength:500}");
	}else{
		$("#opinion").attr("validate","{maxLength:500}");
	}
	// 设置初始值
	var voteContent = dialog.get("voteContent");
	$("#opinion").val(voteContent);
	
});

function save(){
	var opinion=$("#opinion").val();
	if(opinion.length<=0){
		alert("请填写审批意见");
		return ;
	}
	var rtn=$("#frmComm").form().valid();
	if(!rtn) return;
	var opinion = $("#opinion").val();
	var call = dialog.get("sucCall");
	call(opinion);
	dialog.close(); 
}

function setValue(val){
	$("#opinion").val(val);
}
 
</script>
<style>
	body{
		padding: 0;
		margin: 0;
	}
	.section__wrap{
		margin: 0 10px;
	}
	.section-head{
		padding: 10px 0;
	}
	.textarea__wrap{
		padding: 10px;
		border: 1px solid #dadfed;
	}
	.textarea{
		border: 0;
		resize: none;
		width: 100%;
		outline: none;
		box-sizing: border-box;
	}
	a.button{
		padding: 0 15px;
		display: inline-block;
		height: 25px;

	}
	.button__wrap .button{
		margin: 2px 0;
	}
	dl{
		margin-bottom: 10px;
	}
	dl dt{
		font-size: 14px;
		margin-bottom: 10px;
	}
</style>
</head>
<body>
	<div class="section">
		<div class="section__wrap">
			<div class="section-main">
				<form id="frmComm">
					<dl>
						<dt>审批意见:</dt>
						<dd><div class="textarea__wrap"><textarea class="textarea"   rows="6" cols="50" id="opinion" name="opinion"  maxLength="500"></textarea></div></dd>
					</dl>
					
						<dl>
							<dt>常用语:</dt>
							<dd>
								<div class="button__wrap">
									<!-- 配置式的常用语  注释勿删-->
									<%-- <c:forEach var="item" items="${taskAppItems}">
										<a class="button reload" style="background: #f7a751 !important;" onclick="setValue('${item}')"><span></span>${item}</a>
									</c:forEach> --%>
									<c:if test="${param['operatorType']!=1 }">
										<a class="button reload" style="background: #f7a751 !important;" onclick="setValue('信息不全')"><span></span>信息不全</a>
										<a class="button reload" style="background: #f7a751 !important;" onclick="setValue('不同意')"><span></span>不同意</a>
									</c:if>
									<c:if test="${param['operatorType']==1 }">
										<a class="button reload" style="background: #f7a751 !important;" onclick="setValue('同意')"><span></span>同意</a>
										<a class="button reload" style="background: #f7a751 !important;" onclick="setValue('资料齐全')"><span></span>资料齐全</a>
									</c:if>
								</div>
							</dd>
						</dl>
				</form>
			</div>
			<div class="section-head">
				<a class="button " id="dataFormSave" href="javascript:;" onclick="save()">确定</a>
				<a class="button " href="javascript:;" onclick="dialog.close()">关闭</a>
			</div>
		</div>
	</div>

<script>
	/* $(function(){
		$('form input:first, form textarea:first').focus();
		var operatorType=${param['operatorType']};
		if(operatorType==1){
			$("#opinion").attr("rows",10);
		}
		
	}); */
</script>
</body>
</html>