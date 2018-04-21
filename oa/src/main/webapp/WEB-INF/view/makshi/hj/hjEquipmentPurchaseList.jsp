<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>设备采购管理</title>
<%@include file="/commons/include/get.jsp"%>
</head>
<body class="oa-mw-page">

	<div id="oa_list_title" class="oa-mgb20 oa-project-title">
	    <h2>设备采购管理列表</h2>
	</div>
	

	<div id="oa_list_search" class="oa-pdb10 oa-mgh20">
        <form id="searchForm" method="post" action="list.ht" class="oa-clear">
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">申请人</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input type="text" name="Q_applicant_SL"  class="oa-input"  value="${param['Q_applicant_SL']}"/>
                </div>
            </div>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">设备名称</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input type="text" name="Q_name_SL"  class="oa-input"  value="${param['Q_name_SL']}"/>
                </div>
            </div>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">项目名称</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input type="text" name="Q_project_name_SL"  class="oa-input"  value="${param['Q_project_name_SL']}"/>
                </div>
            </div>

            <div class="oa-fl oa-mgb10 oa-mgh20">
                <button class="oa-button oa-button--primary oa-button--blue">查询</button>
            </div>
        </form>
    </div>

	<div class="oa-pdb20 oa-mgh20">
	    <div id="oa_list_content" class="oa-table-scroll-wrap">
			<display:table name="hjEquipmentPurchaseList"
				id="hjEquipmentPurchaseItem" requestURI="list.ht" sort="external"
				cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
				<c:set var="startNum" value="${startNum+1}"></c:set>	
				
				<display:column title="序号">${startNum}</display:column>
				<display:column title="申请人">${hjEquipmentPurchaseItem.applicant}</display:column>
				<display:column title="申请日期">
					<fmt:formatDate value='${hjEquipmentPurchaseItem.appli_date}' pattern='yyyy-MM-dd'/>
				</display:column>
				<display:column title="项目名称">${hjEquipmentPurchaseItem.project_name}</display:column>
				<display:column title="采购方式">
					<c:if test="${hjEquipmentPurchaseItem.type==1}">委托办公室</c:if>
					<c:if test="${hjEquipmentPurchaseItem.type==2}">部门自行采购</c:if>
				</display:column>
				<display:column title="设备名称">${hjEquipmentPurchaseItem.name}</display:column>
				<display:column title="规格型号">${hjEquipmentPurchaseItem.specification}</display:column>
				<display:column title="数量">${hjEquipmentPurchaseItem.number}</display:column>
				<display:column title="单价">${hjEquipmentPurchaseItem.price}</display:column>
				<display:column title="管理" media="html">
					<a href="get.ht?id=${hjEquipmentPurchaseItem.id1}" class="oa-button-label">明细</a>
				</display:column>
			</display:table>
	    </div>

	    <hotent:paging tableId="hjEquipmentPurchaseItem" />
	</div>

</body>
</html>


