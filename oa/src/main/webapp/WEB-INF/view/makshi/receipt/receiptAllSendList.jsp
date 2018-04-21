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
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">收文汇总表管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					 <div class="group"><a class="link search" id="btnSearch"><span></span>查询</a></div> 
					<div class="l-bar-separator"></div>
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="sendList.ht">
					<div class="row">
					
						<ul class="row">
						<li>
							<span class="label">紧急程度</span>
							<select name="urgency_degree">
								<option value="">请选择</option>
								<option value="1">普通</option>
								<option value="2">紧急</option>
								<option value="3">非常紧急</option>
							</select>
						</li>
						<li>
							<span class="label">发文标题</span>
							<input type="text" name="title" class="inputText"/>
						</li>
						<li>
							<span class="label">类型</span>
							<input type="text" name="type" class="inputText"/>
						</li>
					 	<li>
							<span class="label">状态</span>
							<select name="readState">
								<option value="">请选择</option>
								<option value="0">未读</option>
								<option value="1">已读</option>
							</select>
						</li> 
					</ul>
					
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
		    <display:table name="receiptAllList" id="receiptAllItem" requestURI="myList.ht" sort="external" cellpadding="1" cellspacing="1" class="table-grid">
				
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
					<c:if test="${receiptAllItem.readState=='1'}">已读</c:if>
					<c:if test="${receiptAllItem.readState!='1'}">未读</c:if>
				</display:column> 
				
				<display:column title="操作" media="html" style="width:10%">
				    <a href="get.ht?type=1&id=${receiptAllItem.id}"><span></span>查看</a> 
				</display:column>
			</display:table>
			<hotent:paging tableId="receiptAllItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
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

</script>

