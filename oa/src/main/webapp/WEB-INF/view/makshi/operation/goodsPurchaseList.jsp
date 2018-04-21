<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>物品采购申请管理</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body class="oa-mw-page">

	<div id="oa_list_title" class="oa-mgb20 oa-project-title">
        <h2>物品采购列表</h2>
    </div>
	<c:if test="${isEditer==true }">
    <div id="oa_list_operation" class="oa-pdb20 oa-mgh20 oa-mgl40">
        <a class="oa-button oa-button--primary oa-button--blue" href="edit.ht">新增</a>
    </div>
	</c:if>
    <div id="oa_list_search" class="oa-pdb10 oa-mgh20">
        <form id="searchForm" method="post" action="list.ht" class="oa-clear">
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">申请人</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input class="oa-input" name="applicant" value="${param['applicant']}" />
                </div>
            </div>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">领用人</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input class="oa-input" name="recipients_user" value="${param['recipients_user']}" />
                </div>
            </div>

            <div class="oa-fl oa-mgb10 oa-mgh20">
                <button class="oa-button oa-button--primary oa-button--blue">查询</button>
            </div>
        </form>
    </div>

	<div class="oa-pdb20 oa-mgh20">
      	 <div id="oa_list_content" class="oa-table-scroll-wrap">
			<c:set var="startNum" value="${(pageBeangoodsPurchaseItem.currentPage-1)*pageBeangoodsPurchaseItem.pageSize}"></c:set>
		    <display:table name="goodsPurchaseList" id="goodsPurchaseItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
		   	<c:set var="startNum" value="${startNum+1}"></c:set>
				<display:column title="序号">${startNum}</display:column>
				<display:column title="申请人">${goodsPurchaseItem.applicant}</display:column>
				<display:column title="申请日期">
					<fmt:formatDate value='${goodsPurchaseItem.appli_date}' pattern='yyyy-MM-dd'/>
				</display:column>
				<display:column title="申请部门">${goodsPurchaseItem.appli_department}</display:column>
				<display:column title="物品名称">${goodsPurchaseItem.name}</display:column>
				<display:column title="规格型号">${goodsPurchaseItem.specification}</display:column>
				<display:column title="数量">${goodsPurchaseItem.number}</display:column>
				<display:column title="单价">${goodsPurchaseItem.price}</display:column>
				<display:column title="采购日期">
					<fmt:formatDate value='${goodsPurchaseItem.purchase_date}' pattern='yyyy-MM-dd'/>
				</display:column>
				<display:column title="领用人">${goodsPurchaseItem.recipients_user}</display:column>
				<display:column title="领用部门">${goodsPurchaseItem.recipients_department}</display:column>
				
				<display:column title="管理" media="html">
					<c:if test="${isEditer==true }">
					<a href="edit.ht?id=${goodsPurchaseItem.id}" class="oa-button-label">编辑</a>
					</c:if>
					<a href="get.ht?id=${goodsPurchaseItem.id}" class="oa-button-label">详情</a>
				</display:column>
			</display:table>
			</div>
			<hotent:paging tableId="goodsPurchaseItem"/>
	</div>   
</body>
</html>


