<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>监管方案管理列表</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body class="oa-mw-page">

	<div id="oa_list_title" class="oa-mgb20 oa-project-title">
	    <h2>监管方案管理列表</h2>
	</div>

    <div id="oa_list_search" class="oa-pdb10 oa-mgh20">
        <form id="searchForm" method="post" action="list.ht" class="oa-clear">
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">方案名称</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input type="text" name="Q_n_name_SL"  class="oa-input"  value="${param['Q_n_name_SL']}"/>
                </div>
            </div>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">项目名称</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input type="text" name="Q_project_name_SL"  class="oa-input"  value="${param['Q_project_name_SL']}"/>
                </div>
            </div>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">提交人</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input type="text" name="Q_name_SL"  class="oa-input"  value="${param['Q_name_SL']}"/>
                </div>
            </div>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">所属部门</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input type="text" name="Q_department_SL"  class="oa-input"  value="${param['Q_department_SL']}"/>
                </div>
            </div>

            <div class="oa-fl oa-mgb10 oa-mgh20">
                <button class="oa-button oa-button--primary oa-button--blue">查询</button>
            </div>
        </form>
    </div>
	
	<div class="oa-pdb20 oa-mgh20">
	    <div id="oa_list_content" class="oa-table-scroll-wrap">
		    <display:table name="hjRegulatorySchemeList" id="hjRegulatorySchemeItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
				<c:set var="startNum" value="${startNum+1}"></c:set>	
				<display:column title="序号">
			  		${startNum}
				</display:column>
				<display:column title="方案名称">
			  		${hjRegulatorySchemeItem.n_name}
				</display:column>
				<display:column title="方案内容">
			  		${hjRegulatorySchemeItem.content}
				</display:column>
				<display:column title="项目名称">
			  		${hjRegulatorySchemeItem.project_name}
				</display:column>
				<display:column title="提交人">
			  		${hjRegulatorySchemeItem.name}
				</display:column>
				<display:column title="提交日期">
			  		<fmt:formatDate value='${hjRegulatorySchemeItem.date}' pattern='yyyy-MM-dd'/>
				</display:column>
				<display:column title="所属部门">
			  		${hjRegulatorySchemeItem.department}
				</display:column>
				<display:column title="备注">
			  		${hjRegulatorySchemeItem.note}
				</display:column>
				<display:column title="管理" media="html">
					<a href="get.ht?id=${hjRegulatorySchemeItem.id}" class="oa-button-label">明细</a>
				</display:column>
			</display:table>
	    </div>

		<hotent:paging tableId="hjRegulatorySchemeItem"/>
	</div>

</body>
</html>


