<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>物品管理</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body class="oa-mw-page">

	<div id="oa_list_title" class="oa-mgb20 oa-project-title">
	    <h2>物品采购管理列表</h2>
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
                <div class="oa-label">申请部门</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input type="text" name="Q_appli_department_SL"  class="oa-input"  value="${param['Q_appli_department_SL']}"/>
                </div>
            </div>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">领用人</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input type="text" name="Q_lname_SL"  class="oa-input"  value="${param['Q_lname_SL']}"/>
                </div>
            </div>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">领用部门</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input type="text" name="Q_ldepartment_SL"  class="oa-input"  value="${param['Q_ldepartment_SL']}"/>
                </div>
            </div>

            <div class="oa-fl oa-mgb10 oa-mgh20">
                <button class="oa-button oa-button--primary oa-button--blue">查询</button>
            </div>
        </form>
    </div>

    <div class="oa-pdb20 oa-mgh20">
	    <div id="oa_list_content" class="oa-table-scroll-wrap">
		    <display:table name="hjGoodsPurchasedsList" id="hjGoodsPurchasedsItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
				<c:set var="startNum" value="${startNum+1}"></c:set>	
				<display:column title="序号">
			  		${startNum}
				</display:column>
				<display:column title="申请人">
			  		${hjGoodsPurchasedsItem.applicant}
				</display:column>
				 <display:column title="申请日期">
			  		<fmt:formatDate value='${hjGoodsPurchasedsItem.appli_date}' pattern='yyyy-MM-dd'/>
				</display:column>
				<display:column title="申请部门">
			  		${hjGoodsPurchasedsItem.appli_department}
				</display:column>
				<display:column title="采购方式">
				<c:if test="${hjGoodsPurchasedsItem.type==1}">委托办公室</c:if>
				<c:if test="${hjGoodsPurchasedsItem.type==2}">部门自行采购</c:if>
				</display:column>
				<display:column title="物品名称">
			  		 ${hjGoodsPurchasedsItem.name}
				</display:column>
				<display:column title="规格型号">
			  		${hjGoodsPurchasedsItem.specification}
				</display:column>
				<display:column title="数量">
			  		${hjGoodsPurchasedsItem.number}
				</display:column>
				<display:column title="单价">
			  		${hjGoodsPurchasedsItem.price}
				</display:column>
				<display:column title="采购日期">
			  		<fmt:formatDate value='${hjGoodsPurchasedsItem.date}' pattern='yyyy-MM-dd'/>
				</display:column>
				<display:column title="领用人">
			  		${hjGoodsPurchasedsItem.lname}
				</display:column>
				<display:column title="领用部门">
			  		${hjGoodsPurchasedsItem.ldepartment}
				</display:column>
				<display:column title="管理" media="html">
					<a href="edit.ht?id=${hjGoodsPurchasedsItem.id1}" class="oa-button-label"><span></span>编辑</a>
					<a href="get.ht?id=${hjGoodsPurchasedsItem.id1}" class="oa-button-label"><span></span>明细</a>
				</display:column>
			</display:table>
	    </div>

		<hotent:paging tableId="hjGoodsPurchasedsItem"/>
	</div>

</body>
</html>


