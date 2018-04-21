<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>收文汇总表管理</title>
<%@include file="/commons/include/get.jsp" %>
<script type="text/javascript">
</script>
</head>
<body>
	<div id="oa_list_title" class="oa-mgb20 oa-project-title">
	    <!-- <h2>收文汇总表管理列表</h2> -->
	</div>
	<div id="oa_list_operation" class="oa-pdb20 oa-mgh20 oa-mgl2">
	    <a class="oa-button oa-button--primary oa-button--blue" target="_blank" onclick="$.openFullWindow('${ctx}/platform/bpm/task/startFlowForm.ht?defId=10000001640041');">新建</a>
	</div>
	<div class="panel">
		<!--<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label"></span>
			</div>
			 <div class="panel-search">
				<form id="searchForm" method="post" action="list.ht">
					<div class="row">
					</div>
				</form> 
			</div>
		</div> -->
		<div class="oa-pdb20 oa-mgh20">
        	<div id="oa_list_content" class="oa-table-scroll-wrap">
		    	<c:set var="checkAll">
					<input type="checkbox" id="chkall"/>
				</c:set>
			    <display:table name="receiptAllList" id="receiptAllItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
					<display:column title="${checkAll}" media="html" style="width:30px;">
				  		<input type="checkbox" class="pk" name="id" value="${receiptAllItem.id}">
					</display:column>
					
					<display:column title="来文标题" media="html" style="width:200px;">
								${receiptAllItem.title}
					</display:column>
					<display:column title="收文编号" style="width:100px;">
								${receiptAllItem.receipt_id }
					</display:column>
					<display:column title="收文类型" style="width:100px;">
								${receiptAllItem.type }
					</display:column>
					<display:column title="紧急程度" style="width:100px;">
						<c:if test="${receiptAllItem.urgency_degree==0}">未选择</c:if>
						<c:if test="${receiptAllItem.urgency_degree==1}">普通</c:if>
						<c:if test="${receiptAllItem.urgency_degree==2}">紧急</c:if>
						<c:if test="${receiptAllItem.urgency_degree==3}">非常紧急</c:if>
					</display:column>
					<display:column title="来文单位" style="width:100px;">
								${receiptAllItem.dispatch_unit }
					</display:column>
					<display:column title="办理时限" style="width:100px;">
						<fmt:formatDate value="${receiptAllItem.time_limit}"  type="both" pattern="yyyy-MM-dd"/>
					</display:column>
					<display:column title="状态" style="width:100px;">
						<c:if test="${receiptAllItem.state=='1'}">已归档</c:if>
						<c:if test="${receiptAllItem.state!='1'}">${receiptAllItem.state }</c:if> 
					</display:column> 
					
					<display:column title="操作" media="html" style="width:10%">
					<c:if test="${type!=1 }">
							<a href="edit.ht?id=${receiptAllItem.id}"><span></span>编辑</a>
							
							<a class="del" href="del.ht?id=${receiptAllItem.id}">删除</a>
					</c:if>
					   <%-- <a href="javascript:;" onclick="$.openFullWindow('${ctx}/platform/bpm/task/startFlowForm.ht?defId=10000001640041&businessKey=${receiptAllItem.id}');" class="link run"><span></span>提交</a> --%>
					   <%-- <a href="get.ht?id=${receiptAllItem.id}" class="link detail"><span></span>明细</a> --%>
					</display:column>
				</display:table>
			</div>
			<hotent:paging tableId="receiptAllItem"/>
		</div>			
	</div> 
</body>
</html>
<script type="text/javascript">

$(".del").click(function(){
	if($(this).hasClass('disabled')) return false;
	
	var ele = this;
	$.ligerDialog.confirm('确认删除吗？','提示信息',function(rtn) {
		if(rtn) {
			if(ele.click) {
				$(ele).unbind('click');
				ele.click();
			} else {
				location.href=ele.href;
			}
		}
	});
	
	return false;
});
function executeTask(taskId){
	var uri = "/platform/bpm/task/pendingMattersReceiptList.ht";
	var rtn = jQuery.openFullWindow(uri);
	/* var url="/platform/bpm/task/toStart.ht?taskId="+taskId;
	var rtn = jQuery.openFullWindow(url); */
}

</script>

