<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>招标方案审批管理</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body>

	<div id="oa_list_title" class="oa-project-title">
	    <h2>招标方案审批管理列表</h2>
	</div>

    <div id="oa_list_search" class="oa-pd20">
        <form id="searchForm" method="post" action="list.ht" class="oa-clear">
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">申请人</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input type="text" name="scheme_applicant" class="oa-input" value="${param['scheme_applicant']}"/>
                </div>
            </div>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">申请日期</div>
                <div class="oa-input-wrap oa-mgl100 oa-input-wrap--ib">
                    <input type="text" id="Q_beginscheme_appli_date_DL" name="Q_beginscheme_appli_date_DL"  class="oa-input Wdate" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'Q_endscheme_appli_date_DG\');}'})" value="${param['Q_beginscheme_appli_date_DL']}"/>
                </div>
                <span>至</span>
                <div class="oa-input-wrap oa-input-wrap--ib">
                    <input type="text" id="Q_endscheme_appli_date_DG" name="Q_endscheme_appli_date_DG"  class="oa-input Wdate" onclick="WdatePicker({minDate:'#F{$dp.$D(\'Q_beginscheme_appli_date_DL\');}'})"  value="${param['Q_endscheme_appli_date_DG']}"/>
                </div>
            </div>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">合同编号</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input type="text" name="scheme_contract_num" class="oa-input"  value="${param['scheme_contract_num']}"/>
                </div>
            </div>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">合同名称</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input type="text" name="scheme_contract_name" class="oa-input"  value="${param['scheme_contract_name']}"/>
                </div>
            </div>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">方案名称</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input type="text" name="scheme_name" class="oa-input"  value="${param['scheme_name']}"/>
                </div>
            </div>

            <div class="oa-fl oa-mgb10 oa-mgh20">
                <button class="oa-button oa-button--primary oa-button--blue">查询</button>
            </div>
        </form>
    </div>

	<div class="oa-pd20">

	    <div id="oa_list_content" class="oa-table-scroll-wrap">
		    <display:table name="biddingSchemeExamineList" id="biddingSchemeExamineItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
				<display:column title="方案名称">${biddingSchemeExamineItem.scheme_name}</display:column>
				<display:column title="编制依据">${biddingSchemeExamineItem.scheme_evidence}</display:column>
				<display:column title="方案内容">${biddingSchemeExamineItem.scheme_content}</display:column>
				<display:column title="申请人">${biddingSchemeExamineItem.scheme_applicant}</display:column>
				<display:column title="申请日期"><fmt:formatDate value="${biddingSchemeExamineItem.scheme_appli_date}" pattern='yyyy-MM-dd' /></display:column>
				<display:column title="合同编号">${biddingSchemeExamineItem.scheme_contract_num}</display:column>
				<display:column title="合同名称">${biddingSchemeExamineItem.scheme_contract_name}</display:column>
				<display:column title="管理" media="html">
					<a href="edit.ht?id=${biddingSchemeExamineItem.id}" class="oa-button-label">编辑</a>
					<a href="get.ht?id=${biddingSchemeExamineItem.id}" class="oa-button-label">详情</a>
				</display:column>
			</display:table>
	    </div>

	    <hotent:paging tableId="biddingSchemeExamineItem"/>

	</div>

</body>
</html>


