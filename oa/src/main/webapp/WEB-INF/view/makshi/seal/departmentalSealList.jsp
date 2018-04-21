<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>部门公章使用流程管理</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body class="oa-mw-page">
	<div id="oa_list_title" class="oa-mgb20 oa-project-title">
		<h2>部门公章使用流程管理列表</h2>
	</div>
	
<!-- 	<div id="oa_list_operation" class="oa-pdb20 oa-mgh20 oa-mgl20">
		<a class="oa-button oa-button--primary oa-button--blue" href="edit.ht" target="_blank">添加</a>
	</div> -->
	
	<div class="oa-pdb20 oa-mgh20">
		<c:set var="startNum" value="${(pageBeandepartmentalSealItem.currentPage-1)*pageBeandepartmentalSealItem.pageSize+1}"></c:set>
		<div id="oa_list_content" class="oa-table-scroll-wrap">
		    <display:table name="departmentalSealList" id="departmentalSealItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
				<display:column title="序号"> 
			    	<c:out value="${startNum}"/>
					<c:set var="startNum" value="${startNum+1}"/>
			    </display:column>
				<display:column title="工号">${departmentalSealItem.job_id}</display:column>
				<display:column title="申请人">${departmentalSealItem.applyer}</display:column>
				<display:column title="类型 ">
					<c:if test="${departmentalSealItem.type=='1'}">公章</c:if>
				</display:column>
				<display:column title="使用时间">
					<fmt:formatDate value="${departmentalSealItem.use_time }"   pattern="yyyy-MM-dd " type="date" dateStyle="long" />
				</display:column>
				<display:column title="合同编号">${departmentalSealItem.contract_id}</display:column>
				<display:column title="合同名称">${departmentalSealItem.contract_name}</display:column>
				<display:column title="材料内容 ">${departmentalSealItem.material_content}</display:column>
				<display:column title="份数">${departmentalSealItem.copies}</display:column>
				<display:column title="项目负责人">${departmentalSealItem.project_leader}</display:column>
				 <display:column title="管理" media="html">
					<%-- <a href="del.ht?id=${departmentalSealItem.id}" class="oa-button-label del" ><span></span>删除</a>
					<a href="edit.ht?id=${departmentalSealItem.id}" class="oa-button-label edit" target="_blank"><span></span>编辑</a>
					 --%>
					<a href="get.ht?id=${departmentalSealItem.id}" class="oa-button-label detail" target="_blank"><span></span>明细</a>
				</display:column>
			</display:table>
		</div>
		<hotent:paging tableId="departmentalSealItem"/>
	</div> <!-- end of panel -->
</body>
</html>


