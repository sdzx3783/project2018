<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<%@include file="/commons/include/form.jsp"%>
<title>编辑日程交流信息</title>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/FlexUploadDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/CommonDialog.js"></script>
<script type="text/javascript" src="${ctx}/codegen/js/AttachMent.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysPlanScript.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
<script type="text/javascript" src="${ctx}/servlet/ValidJs?form=sysPlan"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/HtmlUploadDialog.js"></script>
<script type="text/javascript">
    
    var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)

	$(function(){

		//初始化附件选择器
		AttachMent.init();
		
		//初始化保存交流信息按键
	    initExchangeSubmit(dialog);	
		
	    //初始化关闭事件
		$('#btnClose').click(function(){
			dialog.close();
		});

	});
    

</script>
</head>
<body>
	<div class="oa-pd20 oa-pdb0">
		<a class="oa-button oa-button--primary oa-button--blue save">保存</a>
		<a id="btnClose" class="oa-button oa-button--primary oa-button--blue">关闭</a>		
	</div>

	<div class="oa-pd20">
		<form id="sysPlanExchangeEdit" method="post" action="${ctx}/platform/system/sysPlan/saveExchange.ht">
			<input type="hidden" name="id" value="${sysPlanExchange.id}"/>
			<input type="hidden" name="planId" value="${planId}"/>
			<input type="hidden" name="submitId" value="${sysPlanExchange.submitId}"/>
			<input type="hidden" name="submitor" value="${sysPlanExchange.submitor}"/>
			<input type="hidden" name="createTime" value="<fmt:formatDate value='${sysPlanExchange.createTime}' pattern='yyyy-MM-dd HH:mm:ss'/>"/>
			<input type="hidden" name="currentViweDate" value="${currentViweDate}"/>
			<input type="hidden" name="type" value="${type}"/>

			<table class="oa-table--default" cellpadding="0" cellspacing="0" border="0">				 

				<tr>
					<th width="20%">内容:</th>
					<td colspan="3">
						<div class="oa-textarea-wrap">
							<textarea class="oa-textarea" name="content" rows="6"  validate="{'required':true}" >${sysPlanExchange.content}</textarea>
						</div>
					</td>

				</tr>
				
				<tr>
					<th width="20%">附件:</th>
					<td colspan="3">
						<div name="div_attachment_container">
						<div class="attachement"></div>
						<textarea style="display:none" controltype="attachment" name="doc" lablename="相关文档">${sysPlanExchange.doc}</textarea>
						<a field="doc" class="oa-button-label selectFile" atype="select"  onclick="AttachMent.directUpLoadFile(this);">选择附件</a>
					</td>
				</tr>
				
				<c:if test="${sysPlanExchange.id!=null&&sysPlanExchange.id!=''}">
					<tr>
						<th width="20%">提交人:</th>
						<td width="30%">
							${sysPlanExchange.submitor}
						</td>
						<th width="20%">提交时间:</th>
						<td width="30%">
							<fmt:formatDate value='${sysPlanExchange.createTime}' pattern='yyyy-MM-dd HH:mm:ss'/>
						</td>
					</tr>
				</c:if>		
				
			</table>
		</form>
	</div>
	
<script>
	$(function(){
		//	初始话选择第一个input或者textarea元素聚焦
		$(".oa-table--default").find("input, textarea").first().focus();
	});
</script>
</body>
</html>

