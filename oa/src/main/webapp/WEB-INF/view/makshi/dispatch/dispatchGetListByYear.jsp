<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>发文总表管理</title>
	<%@include file="/commons/include/get.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	<style type="text/css">
		.ddxx{
			width: 400px;
		    word-break: break-all;
		    word-wrap: break-word;
		    white-space: normal !important;
		}
	</style>
</head>
<body class="oa-mw-page">
    <div id="oa_list_title" class="oa-mgb20 oa-project-title">
	    <!-- <h2>发文总表管理列表</h2> -->
	</div>
    <div id="oa_list_operation" class="oa-pdb20 oa-mgh20 oa-mgl2">
    	<a class="oa-button oa-button--primary oa-button--blue" href="docList.ht?docId=${params.docId}">返回</a>
    	<c:if test='${canCreate}'>
	  	    <a class="oa-button oa-button--primary oa-button--blue" href="edit.ht?docId=${params.docId}&&dicId=${params.dicId}" target="_blank">新建</a>
	        <a class="oa-button oa-button--primary oa-button--blue del" action="del.ht">删除</a>
	    </c:if>
	</div>
    <div class="oa-pdb20 oa-mgh20">
	    <div id="oa_list_content" class="oa-table-scroll-wrap">
	        <c:if test='${canCreate}'>
		    	<c:set var="checkAll">
					<input type="checkbox" id="chkall"/>
				</c:set>
			</c:if>
		    <display:table name="dispatchList" id="dispatchItem" requestURI="list.ht" sort="external" class="oa-table--default oa-table--nowrap">
		    	<c:if test='${canCreate}'>
					<display:column title="${checkAll}" media="html">
				  		<input type="checkbox" class="pk" name="id" value="${dispatchItem.id}">
					</display:column>
				</c:if>
				<display:column title="发文标题" class="ddxx"><a href="get.ht?id=${dispatchItem.id}" target="_blank">${dispatchItem.title}</a></display:column>
				<display:column title="发文编号">${dispatchItem.dispatch_id}</display:column>
				<display:column title="发文类型">${dispatchItem.type}</display:column>
				<display:column title="拟稿日期"><fmt:formatDate value="${dispatchItem.time}" pattern="yyyy-MM-dd"/></display:column>
				<display:column title="拟稿人">${dispatchItem.draft_person}</display:column>
				<%-- <display:column title="状态">
					<c:if test="${dispatchItem.state==null}">流程结束</c:if>
					<c:if test="${dispatchItem.state!=null}">${dispatchItem.state}</c:if>
				</display:column> --%>
				 <display:column title="管理" media="html">
					 <a class="oa-button-label detail" href="get.ht?id=${dispatchItem.id}" target="_blank">明细</a> 
					 <c:if test='${canCreate}'>
				 		<a class="oa-button-label edit" href="edit.ht?id=${dispatchItem.id}" target="_blank">编辑</a> 
						<a class="oa-button-label del"  href="del.ht?id=${dispatchItem.id}">删除</a> 
					 </c:if>
			     </display:column> 
			</display:table>
	    </div>
		<hotent:paging tableId="dispatchItem"/>
	</div>

</body>
</html>


