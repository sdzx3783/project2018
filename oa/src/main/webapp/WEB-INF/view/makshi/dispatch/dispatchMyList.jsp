<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>发文总表管理</title>
	<%@include file="/commons/include/get.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
<script type="text/javascript">
</script>
</head>
<body class="oa-mw-page">


    <div id="oa_list_title" class="oa-mgb20 oa-project-title">
	    <!-- <h2>发文总表管理列表</h2> -->
	</div>

    <div id="oa_list_operation" class="oa-pdb20 oa-mgh20 oa-mgl2">
	    <a class="oa-button oa-button--primary oa-button--blue"  target="_blank" onclick="$.openFullWindow('${ctx}/platform/bpm/task/startFlowForm.ht?defId=10000003390004');">新建</a>
	</div>

    <div class="oa-pdb20 oa-mgh20">
	    <div id="oa_list_content" class="oa-table-scroll-wrap">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="dispatchList" id="dispatchItem" requestURI="list.ht" sort="external" class="oa-table--default oa-table--nowrap">
				<display:column title="${checkAll}" media="html">
			  		<input type="checkbox" class="pk" name="id" value="${dispatchItem.id}">
				</display:column>
				<display:column title="发文标题">${dispatchItem.title}</display:column>
				<display:column title="发文编号">${dispatchItem.dispatch_id}</display:column>
				<display:column title="发文类型">${dispatchItem.type}</display:column>
				<display:column title="拟稿日期">${dispatchItem.time}</display:column>
				<display:column title="拟稿人">${dispatchItem.draft_person}</display:column>
				<display:column title="状态">
					草稿
				</display:column>
			</display:table>
	    </div>
		<hotent:paging tableId="dispatchItem"/>
	</div>

</body>
</html>


