<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<%@include file="/commons/include/form.jsp"%>
<title>日程交流信息详情</title>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/FlexUploadDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/CommonDialog.js"></script>
<script type="text/javascript" src="${ctx}/codegen/js/AttachMent.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysPlanScript.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
<script type="text/javascript" src="${ctx}/servlet/ValidJs?form=sysPlan"></script>
<script type="text/javascript">
    
    var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)

	$(function(){

		//初始化附件选择器
		AttachMent.init();
		
	    //初始化关闭事件
		$('#btnClose').click(function(){
			dialog.close();
		});

	});
	
</script>
</head>
<body>

	<div class="oa-pd20 oa-pdb0">
		<a id="btnClose" class="oa-button oa-button--primary oa-button--blue" href="#">关闭</a>
	</div>

	<div class="oa-pd20">
		<form id="sysPlanExchangeEdit" method="post">
			<input type="hidden" name="id" value="${sysPlanExchange.id}"/>
			<input type="hidden" name="planId" value="${planId}"/>
			<input type="hidden" name="submitId" value="${sysPlanExchange.submitId}"/>
			<input type="hidden" name="submitor" value="${sysPlanExchange.submitor}"/>
			<input type="hidden" name="createTime" value="<fmt:formatDate value='${sysPlanExchange.createTime}' pattern='yyyy-MM-dd HH:mm:ss'/>"/>
			<input type="hidden" name="currentViweDate" value="${currentViweDate}"/>
			<input type="hidden" name="type" value="${type}"/>

			<table class="oa-table--default" cellpadding="0" cellspacing="0" border="0">				 

				<tr>
					<th>内容:</th>
					<td colspan="3">
						${sysPlanExchange.content}
					</td>
				</tr>
				
				<tr>
					<th>附件:</th>
					<td colspan="3">
						<div name="div_attachment_container">
						<div class="attachement"></div>
						<textarea style="display:none" controltype="attachment" name="doc" lablename="相关文档">${sysPlanExchange.doc}</textarea>
					</td>
				</tr>

				<tr>
					<th>提交人:</th>
					<td>
						${sysPlanExchange.submitor}
					</td>
					<th>提交时间:</th>
					<td>
						<fmt:formatDate value='${sysPlanExchange.createTime}' pattern='yyyy-MM-dd HH:mm:ss'/>
					</td>
				</tr>
				
			</table>

		</form>
	</div>
	
</body>
</html>

