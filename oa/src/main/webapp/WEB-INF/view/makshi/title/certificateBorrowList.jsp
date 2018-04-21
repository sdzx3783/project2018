<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>个人证书借阅申请管理</title>
<%@include file="/commons/include/get.jsp" %>
<script type="text/javascript" src="${ctx}/js/util/form.js"></script>
</head>
<body class="oa-mw-page">

    <div id="oa_list_title" class="oa-mgb20 oa-project-title">
    	<!-- <h2>个人证书(印章)借阅申请列表管理</h2> -->
	</div>


	<div id="oa_list_operation" class="oa-mgh20 oa-mgl20">
		<a class="oa-button oa-button--primary oa-button--blue" href="edit.ht" target="_blank">添加</a>
	</div>

    <div id="oa_list_search" class="oa-pdb10 oa-mgh20">

		<div class="oa-accordion">
	        <div class="oa-accordion__title">查询条件</div>
	        <div class="oa-accordion__content">

	            <form id="searchForm" method="post" action="list.ht" class="oa-clear">
		            <div class="oa-fl oa-mgb10">
		                <div class="oa-label">申请人</div>
		                <div class="oa-input-wrap oa-mgl100">
		                    <input type="text" name="applicant" class="oa-input"  value="${param['applicant']}"/>
		                </div>
		            </div>
		            <div class="oa-fl oa-mgb10">
		                <div class="oa-label">提交时间</div>
		                <div class="oa-input-wrap oa-mgl100 oa-input-wrap--ib">
		                     <input type="text" id="Q_beginap_date_DL" name="Q_beginap_date_DL"  class="oa-input Wdate" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'Q_endap_date_DG\');}'})" value="${param['Q_begincomplain_appli_date_DL']}"/>
		                </div>
		                <span>至</span>
		                <div class="oa-input-wrap oa-input-wrap--ib">
		                    <input type="text" id="Q_endap_date_DG" name="Q_endap_date_DG"  class="oa-input Wdate" onclick="WdatePicker({minDate:'#F{$dp.$D(\'Q_beginap_date_DL\');}'})"  value="${param['Q_endcomplain_appli_date_DG']}"/>
		                </div>                
		       	    </div>
		            <%-- <li>
						<span class="label">申请时间</span>
						<input type="text"  name="beginap_date"  class="inputText date" validate="{date:true}"  value="${param['beginap_date']}"/>
						<input type="text"  name="endap_date"  class="inputText date" validate="{date:true}" value="${param['endap_date']}"/> 
					</li> --%>
		             <div class="oa-fl oa-mgb10">
		                <div class="oa-label">持证人</div>
		                <div class="oa-input-wrap oa-mgl100">
		                    <input type="text" name="fullname" class="oa-input"  value="${param['fullname']}"/>
		                </div>  
		            </div>
					<div class="oa-fl oa-mgb10">
		                <div class="oa-label">证书名称</div>
		                <div class="oa-input-wrap oa-mgl100">
		                    <input type="text" name="steal_name" class="oa-input"  value="${param['steal_name']}"/>
		                </div>
		            </div>
		            <div class="oa-fl oa-mgb10">
		                <div class="oa-label">执业印章有效日期</div>
		                <div class="oa-input-wrap oa-mgl100 oa-input-wrap--ib">
		                     <input type="text" id="Q_begineffictiveDate_DL" name="Q_begineffictiveDate_DL"  class="oa-input Wdate" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'Q_endeffictiveDate_DG\');}'})" value="${param['Q_begincomplain_appli_date_DL']}"/>
		                </div>
		                <span>至</span>
		                <div class="oa-input-wrap oa-input-wrap--ib">
		                    <input type="text" id="Q_endeffictiveDate_DG" name="Q_endeffictiveDate_DG"  class="oa-input Wdate" onclick="WdatePicker({minDate:'#F{$dp.$D(\'Q_begineffictiveDate_DL\');}'})"  value="${param['Q_endcomplain_appli_date_DG']}"/>
		                </div>                
		       	    </div>
		       	     <div class="oa-fl oa-mgb10">
		                <div class="oa-label">归还日期</div>
		                <div class="oa-input-wrap oa-mgl100 oa-input-wrap--ib">
		                     <input type="text" id="Q_beginreturn_date_DL" name="Q_beginreturn_date_DL"  class="oa-input Wdate" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'Q_endeffictiveDate_DG\');}'})" value="${param['Q_endreturn_date_DG']}"/>
		                </div>
		                <span>至</span>
		                <div class="oa-input-wrap oa-input-wrap--ib">
		                    <input type="text" id="Q_endreturn_date_DG" name="Q_endreturn_date_DG"  class="oa-input Wdate" onclick="WdatePicker({minDate:'#F{$dp.$D(\'Q_beginreturn_date_DL\');}'})"  value="${param['Q_endcomplain_appli_date_DG']}"/>
		                </div>                
		       	    </div>
		            <div class="oa-fl oa-mgb10 oa-mgh20">
		                <button class="oa-button oa-button--primary oa-button--blue">查询</button>
		            </div>
		        </form> 

	        </div>
    	</div>

        
    </div>
    
    <div class="oa-pdb20 oa-mgh20">
		<c:set var="startNum" value="${(pageBeancertificateBorrowItem.currentPage-1)*pageBeancertificateBorrowItem.pageSize+1}"></c:set>
		<div id="oa_list_content" class="oa-table-scroll-wrap">
		    <display:table name="certificateBorrowList" id="certificateBorrowItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
			    <display:column title="序号"> 
			    	<c:out value="${startNum}"/>
					<c:set var="startNum" value="${startNum+1}"/>
			    </display:column>
				<display:column title="证书名称">${certificateBorrowItem.steal_name}</display:column>
				<display:column title="证书类型">
					<c:if test="${certificateBorrowItem.type==1}">资格证</c:if>
					<c:if test="${certificateBorrowItem.type==2}">注册证</c:if>
					<c:if test="${certificateBorrowItem.type==3}">执业印章</c:if>
					<c:if test="${certificateBorrowItem.type==4}">其他</c:if>
				</display:column>
				<display:column title="证书（印章）编号">${certificateBorrowItem.borrow_data_num}</display:column>
				<display:column title="印章有效日期"><fmt:formatDate value="${certificateBorrowItem.effictiveDate}" pattern='yyyy-MM-dd' /></display:column>
				<display:column title="持证人">${certificateBorrowItem.fullname}</display:column>
				<display:column title="归还日期"><fmt:formatDate value="${certificateBorrowItem.return_date}" pattern='yyyy-MM-dd' /></display:column>
				<display:column title="借阅日期"><fmt:formatDate value="${certificateBorrowItem.ap_date}" pattern='yyyy-MM-dd' /></display:column>
				<display:column title="申请人">${certificateBorrowItem.applicant}</display:column>
				<display:column title="管理" media="html" >
					<a href="del.ht?id=${certificateBorrowItem.id}" class="del"><span></span>删除</a>
				    <a href="edit.ht?id=${certificateBorrowItem.id}" class="edit" target = "_blank"><span></span>编辑</a>
				</display:column> 
			</display:table>
		</div>		

		<hotent:paging tableId="certificateBorrowItem"/>

	</div>
<script>
	$(function(){
		$('.oa-accordion').oaAccordion({collapse: true});
	});
</script>
</body>
</html>


