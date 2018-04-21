<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>投诉处理审批管理</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body class="oa-mw-page">

	<div id="oa_list_title" class="oa-mgb20 oa-project-title">
	    <h2>投诉处理审批管理列表</h2>
	</div>

    <div id="oa_list_search" class="oa-pdb10 oa-mgh20">
        <form id="searchForm" method="post" action="list.ht" class="oa-clear">
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">提交人</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input type="text" name="complain_applicant" class="oa-input"  value="${param['complain_applicant']}"/>
                </div>
            </div>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">提交时间</div>
                <div class="oa-input-wrap oa-mgl100 oa-input-wrap--ib">
                     <input type="text" id="Q_begincomplain_appli_date_DL" name="Q_begincomplain_appli_date_DL"  class="oa-input Wdate" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'Q_endcomplain_appli_date_DG\');}'})" value="${param['Q_begincomplain_appli_date_DL']}"/>
                </div>
                <span>至</span>
                <div class="oa-input-wrap oa-input-wrap--ib">
                    <input type="text" id="Q_endcomplain_appli_date_DG" name="Q_endcomplain_appli_date_DG"  class="oa-input Wdate" onclick="WdatePicker({minDate:'#F{$dp.$D(\'Q_begincomplain_appli_date_DL\');}'})"  value="${param['Q_endcomplain_appli_date_DG']}"/>
                </div>                
            </div>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">事项名称</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input type="text" name="complain_name" class="oa-input"   value="${param['complain_name']}"/>
                </div>
            </div>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">投诉单位</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input type="text" name="complain_unit" class="oa-input"   value="${param['complain_unit']}"/>
                </div>
            </div>

            <div class="oa-fl oa-mgb10 oa-mgh20">
                <button class="oa-button oa-button--primary oa-button--blue">查询</button>
            </div>
        </form>
    </div>

	<div class="oa-pdb20 oa-mgh20">

	    <div id="oa_list_content" class="oa-table-scroll-wrap">
		    <display:table name="biddingComplainList" id="biddingComplainItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
				<display:column title="事项名称">${biddingComplainItem.complain_name}</display:column>
				<display:column title="事项内容">${biddingComplainItem.complain_content}</display:column>
				<display:column title="投诉单位">${biddingComplainItem.complain_unit}</display:column>
				<display:column title="提交人">${biddingComplainItem.complain_applicant}</display:column>
				<display:column title="提交时间"><fmt:formatDate value="${biddingComplainItem.complain_appli_date}" pattern='yyyy-MM-dd' /></display:column>
				<display:column title="管理" media="html"><a href="get.ht?id=${biddingComplainItem.id}" class="oa-button-label">详情</a></display:column>
			</display:table>
	    </div>

	    <hotent:paging tableId="biddingComplainItem"/>

	</div>

</body>
</html>


