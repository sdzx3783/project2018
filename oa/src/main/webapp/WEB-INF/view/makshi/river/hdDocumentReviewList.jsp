<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>文件管理</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body class="oa-mw-page">

	<div id="oa_list_title" class="oa-project-title" >
	    <h2>文件审查管理列表</h2>
	</div>

    <div id="oa_list_search" class="oa-mg20 oa-mgb10">

        <form id="searchForm" method="post" action="list.ht" class="oa-clear">
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">申请人</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input type="text" name="Q_applicant_SL"  class="oa-input"  value="${param['Q_applicant_SL']}"/>
                </div>
            </div>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">文件名称</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input type="text" name="Q_file_name_SL"  class="oa-input"  value="${param['Q_file_name_SL']}"/>
                </div>
            </div>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">项目部</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input type="text" name="Q_department_SL"  class="oa-input"  value="${param['Q_department_SL']}"/>
                </div>
            </div>

            <div class="oa-fl oa-mgb10 oa-mgh20">
                <button class="oa-button oa-button--primary oa-button--blue">查询</button>
            </div>
        </form>
        
    </div>

	<div class="oa-mg20 oa-mgt10">

		<div id="oa_list_content" class="oa-table-scroll-wrap">

		    <display:table name="hdDocumentReviewList" id="hdDocumentReviewItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
				<display:column title="编号">
			  		${hdDocumentReviewItem.number}
				</display:column>
				<display:column title="文件名称">
			  		${hdDocumentReviewItem.file_name}
				</display:column>
				<display:column title="类型">
			  		<c:if test="${hdDocumentReviewItem.type==0}">技术文件</c:if>
			  		<c:if test="${hdDocumentReviewItem.type==1}">通知</c:if>
			  		<c:if test="${hdDocumentReviewItem.type==2}">请示</c:if>
				</display:column>
				<display:column title="项目部">
			  		${hdDocumentReviewItem.department}
				</display:column>
				<display:column title="申请人">
			  		${hdDocumentReviewItem.applicant}
				</display:column>
				<display:column title="填报时间">
			  		<fmt:formatDate value='${hdDocumentReviewItem.date}' pattern='yyyy-MM-dd'/>
				</display:column>
				<display:column title="紧急程度">
				 <c:if test="${hdDocumentReviewItem.level==0}">普通</c:if>
			     <c:if test="${hdDocumentReviewItem.level==1}">紧急</c:if>
			     <c:if test="${hdDocumentReviewItem.level==2}">加急</c:if>
				</display:column>
				<%-- <display:column title="状态">
			  		${hdDocumentReviewItem.number}
				</display:column>--%>
				<display:column title="管理" media="html">
					<a href="get.ht?id=${hdDocumentReviewItem.id}" class="oa-button-label">详情</a>
				</display:column>
			</display:table>
			
		</div>

		<hotent:paging tableId="hdDocumentReviewItem"/>

	</div>

</body>
</html>


