<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>其他事项审批管理</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body class="oa-mw-page">

	<div id="oa_list_title" class="oa-project-title">
	    <h2>其他事项审批管理列表</h2>
	</div>

    <div id="oa_list_search" class="oa-pd20">
        <form id="searchForm" method="post" action="list.ht" class="oa-clear">
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">申请人</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input type="text" name="others_applicant" class="oa-input"  value="${param['others_applicant']}"/>
                </div>
            </div>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">申请日期</div>
                <div class="oa-input-wrap oa-mgl100 oa-input-wrap--ib">
                    <input type="text" id="Q_beginothers_appli_date_DL" name="Q_beginothers_appli_date_DL"  class="oa-input Wdate" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'Q_endothers_appli_date_DG\');}'})" value="${param['Q_beginothers_appli_date_DL']}"/>
                </div>
                <span>至</span>
                <div class="oa-input-wrap oa-input-wrap--ib">
                    <input type="text" id="Q_endothers_appli_date_DG" name="Q_endothers_appli_date_DG"  class="oa-input Wdate" onclick="WdatePicker({minDate:'#F{$dp.$D(\'Q_beginothers_appli_date_DL\');}'})"  value="${param['Q_endothers_appli_date_DG']}"/>
                </div>
            </div>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">事项名称</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input type="text" name="others_name" class="oa-input"   value="${param['others_name']}"/>
                </div>
            </div>

            <div class="oa-fl oa-mgb10 oa-mgh20">
                <button class="oa-button oa-button--primary oa-button--blue">查询</button>
            </div>
        </form>
    </div>

	<div class="oa-pd20">

	    <div id="oa_list_content" class="oa-table-scroll-wrap">
		    <display:table name="biddingOthersList" id="biddingOthersItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
				<display:column title="事项名称">${biddingOthersItem.others_name}</display:column>
				<display:column title="编制依据">${biddingOthersItem.others_evidence}</display:column>
				<display:column title="申请内容">${biddingOthersItem.others_content}</display:column>
				<display:column title="申请人">${biddingOthersItem.others_applicant}</display:column>
				<display:column title="申请日期"><fmt:formatDate value="${biddingOthersItem.others_appli_date}" pattern='yyyy-MM-dd' /></display:column>
				<display:column title="管理" media="html"><a href="get.ht?id=${biddingOthersItem.id}" class="oa-button-label">详情</a></display:column>
			</display:table>
	    </div>

	    <hotent:paging tableId="biddingOthersItem"/>

	</div>

</body>
</html>


