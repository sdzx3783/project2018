<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<%@ taglib prefix="hotent" uri="http://www.jee-soft.cn/paging" %>
<html>
<head>
<title>收文汇总表管理</title>
<%@include file="/commons/include/get.jsp" %>
<script type="text/javascript">
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">收文汇总表管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch"><span></span>查询</a></div>
					<div class="l-bar-separator"></div>
					<!-- <div class="group"><a class="link add" href="edit.ht"><span></span>添加</a></div> -->
					<div class="l-bar-separator"></div>
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="checkList.ht">
					<div class="row">
					</div>
				</form>
			</div> 
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="receiptAllList" id="receiptAllItems" requestURI="checkList.ht" sort="external" cellpadding="1" cellspacing="1" class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${receiptAllItems.id}">
				</display:column>
				
				<display:column title="来文标题" media="html" style="width:200px;">
							${receiptAllItems.title}
				</display:column>
				<display:column title="收文编号" style="width:100px;">
							${receiptAllItems.receipt_id }
				</display:column>
				<display:column title="收文类型" style="width:100px;">
							${receiptAllItems.type }
				</display:column>
				<display:column title="紧急程度" style="width:100px;">
					<c:if test="${receiptAllItems.urgency_degree==0}">未选择</c:if>
					<c:if test="${receiptAllItems.urgency_degree==1}">普通</c:if>
					<c:if test="${receiptAllItems.urgency_degree==2}">紧急</c:if>
					<c:if test="${receiptAllItems.urgency_degree==3}">非常紧急</c:if>
				</display:column>
				<display:column title="来文单位" style="width:100px;">
							${receiptAllItems.dispatch_unit }
				</display:column>
				<display:column title="办理时限" style="width:100px;">
					<fmt:formatDate value="${receiptAllItems.time_limit}"  type="both" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column title="状态" style="width:100px;">
					${receiptAllItems.state }
				</display:column> 
				
				<display:column title="操作" media="html" style="width:10%">
				<c:if test="${receiptAllItems.checkState!=1}">
					 <a href="javascript:;"  onclick="executeTask(${receiptAllItems.taskId})">初审</a>
				 </c:if> 
				<c:if test="${receiptAllItems.checkState==1}">
					 <a href="javascript:;"  onclick="executeTask(${receiptAllItems.taskId})">办理</a>
				 </c:if>		
				</display:column>
			</display:table>     
			<hotent:paging tableId="receiptAllItems"/>   
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
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
	var url="/platform/bpm/task/toStart.ht?taskId="+taskId;
	var rtn = jQuery.openFullWindow(url);
}
</script>

	
</body>
</html>
