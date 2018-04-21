<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>合同编号管理明细</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body class="oa-mw-page">
	<div id="oa_list_title" class="oa-mgb20 oa-project-title">
        	<h2>${typeName}类</h2>
   	</div>
   	<div id="oa_list_operation" class="oa-pdb20 oa-mgh20 oa-mgl20">
		 <a class="oa-button oa-button--primary oa-button--blue" href="upload.ht?contracType=${contracType}" target="_blank">导出</a>
   	</div>
 	<div class="oa-pdb20 oa-mgh20">
      	<div id="oa_list_content" class="oa-table-scroll-wrap">	
    	<c:set var="startNum" value="${(pageBeancontractinfoItem.currentPage-1)*pageBeancontractinfoItem.pageSize+1}"></c:set>
		<display:table name="infoList" id="infoItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
			<display:column title="序号"> 
		    	<c:out value="${startNum}"/>
				<c:set var="startNum" value="${startNum+1}"/>
		    </display:column>
			<%-- <display:column title="合同类型" >
			</display:column>  --%>
			<display:column title="合同编号" >
					<span></span>${infoItem.contract_num}
			</display:column>
			<c:if test="${isJL}">
				<display:column title="合同类型" >
					<span></span>${infoItem.type}
				</display:column>
			</c:if>
			<display:column title="使用人" >
					<span></span>${infoItem.flowNo}
			</display:column>
			<display:column title="合同名称" >
					<span></span>${infoItem.contractNo}
			</display:column>
		</display:table>
		</div><!-- end of panel-body -->				
		<hotent:paging tableId="contractinfoItem"/>
	</div> <!-- end of panel -->
</body>
</html>

