<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>个人执业印章申请管理</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body class="oa-mw-page">
		<div id="oa_list_title" class="oa-mgb20 oa-project-title">
       <!--  <h2>个人执业印章使用申请列表</h2> -->
   		</div>
		<div id="oa_list_search">
	        <form id="searchForm" method="post" action="list.ht" class="oa-clear">
	            <div class="oa-fl oa-mgb10">
	                <div class="oa-label">申请人</div>
	                <div class="oa-input-wrap oa-mgl100">
	                    <input type="text" name="applicant" class="oa-input"  value="${param['applicant']}"/>
	                </div>
	            </div>
	             <div class="oa-fl oa-mgb10">
	                <div class="oa-label">持章人</div>
	                <div class="oa-input-wrap oa-mgl100">
	                    <input type="text" name="fullname" class="oa-input"  value="${param['fullname']}"/>
	                </div>  
	            </div>
				<div class="oa-fl oa-mgb10">
	                <div class="oa-label">印章名称</div>
	                <div class="oa-input-wrap oa-mgl100">
	                    <input type="text" name="steal_name" class="oa-input"  value="${param['steal_name']}"/>
	                </div>
	            </div>
	            <div class="oa-fl oa-mgb10">
	                <div class="oa-label">印章编号</div>
	                <div class="oa-input-wrap oa-mgl100">
	                    <input type="text" name="steal_num" class="oa-input"  value="${param['steal_num']}"/>
	                </div>
	            </div>
	            <div class="oa-fl oa-mgb10 oa-mgh20">
	                <button class="oa-button oa-button--primary oa-button--blue">查询</button>
	            </div>
	        </form>
	    </div>
		<div class="oa-pd20">
			<c:set var="startNum" value="${(pageBeanpracticestealItem.currentPage-1)*pageBeanpracticestealItem.pageSize+1}"></c:set>
			<div  id="oa_list_content" class="oa-table-scroll-wrap">
			    <display:table name="practicestealList" id="practicestealItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
			    	<display:column title="序号"> 
				    	<c:out value="${startNum}"/>
						<c:set var="startNum" value="${startNum+1}"/>
				    </display:column>
					<display:column title="持章人">${practicestealItem.fullname}</display:column>
					<display:column title="印章名称">${practicestealItem.steal_name}</display:column>
					<display:column title="印章编号">${practicestealItem.steal_num}</display:column>
					<display:column title="工程名称">${practicestealItem.project_name}</display:column>
					<display:column title="申请人">${practicestealItem.applicant}</display:column>
					<display:column title="申请日期">
						<fmt:formatDate value='${practicestealItem.appliDate}' pattern='yyyy-MM-dd'/>
					</display:column>
					<display:column title="申请事由">${practicestealItem.reason}</display:column>
					<display:column title="状态">${practicestealItem.status}</display:column>
					
					<%-- <display:column title="管理" media="html" style="width:220px">
						<a href="del.ht?id=${practicestealItem.id}" class="link del"><span></span>删除</a>
						<a href="edit.ht?id=${practicestealItem.id}" class="link edit"><span></span>编辑</a>
						<a href="get.ht?id=${practicestealItem.id}" class="link detail"><span></span>明细</a>
					</display:column> --%>
				</display:table>
			</div>				
		<hotent:paging tableId="practicestealItem"/>
	</div>
</body>
</html>


