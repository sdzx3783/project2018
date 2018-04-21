<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>补充通知/标底公示管理</title>
<%@include file="/commons/include/get.jsp" %>
<script type="text/javascript" src="${ctx}/js/util/form.js"></script>
</head>
<body class="oa-mw-page">

	<div id="oa_list_title" class="oa-mgb20 oa-project-title">
	    <h2>补充通知/标底公示管理列表</h2>
	</div>

    <div id="oa_list_search" class="oa-pdb10 oa-mgh20">
        <form id="searchForm" method="post" action="list.ht" class="oa-clear">
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">申请人</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input type="text" name="publicity_applicant" class="oa-input"  value="${param['publicity_applicant']}"/>
                </div>
            </div>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">申请日期</div>
                <div class="oa-input-wrap oa-mgl100 oa-input-wrap--ib">
                     <input type="text" id="Q_beginpublicity_appli_date_DL" name="Q_beginpublicity_appli_date_DL"  class="oa-input Wdate" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'Q_endpublicity_appli_date_DG\');}'})" value="${param['Q_beginpublicity_appli_date_DL']}"/>
                </div>
                <span>至</span>
                <div class="oa-input-wrap oa-input-wrap--ib">
                    <input type="text" id="Q_endpublicity_appli_date_DG" name="Q_endpublicity_appli_date_DG"  class="oa-input Wdate" onclick="WdatePicker({minDate:'#F{$dp.$D(\'Q_beginpublicity_appli_date_DL\');}'})"  value="${param['Q_endpublicity_appli_date_DG']}"/>
                </div>
            </div>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">合同编号</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input type="text" name="publicity_contract_num" class="oa-input"   value="${param['publicity_contract_num']}"/>
                </div>
            </div>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">合同名称</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input type="text" name="publicity_contract_name" class="oa-input"   value="${param['publicity_contract_name']}"/>
                </div>
            </div>

            <div class="oa-fl oa-mgb10 oa-mgh20">
                <button class="oa-button oa-button--primary oa-button--blue">查询</button>
            </div>
        </form>
    </div>

	<div class="oa-pdb20 oa-mgh20">
	    <div id="oa_list_content" class="oa-table-scroll-wrap">
		    <display:table name="biddingPublicityList" id="biddingPublicityItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
				<display:column title="文件名称">${biddingPublicityItem.publicity_name}</display:column>
				<display:column title="编制依据">${biddingPublicityItem.publicity_evidence}</display:column>
				<display:column title="方案内容">${biddingPublicityItem.publicity_content}</display:column>
				<display:column title="申请人">${biddingPublicityItem.publicity_applicant}</display:column>
				<display:column title="申请日期"><fmt:formatDate value="${biddingPublicityItem.publicity_appli_date}" pattern='yyyy-MM-dd' /></display:column>
				<display:column title="合同编号">${biddingPublicityItem.publicity_contract_num}</display:column>
				<display:column title="合同名称">${biddingPublicityItem.publicity_contract_name}</display:column>
				<display:column title="管理" media="html">
					<a href="edit.ht?id=${biddingPublicityItem.id}" class="oa-button-label">编辑</a>
					<a href="get.ht?id=${biddingPublicityItem.id}" class="oa-button-label">详情</a>
				</display:column>
			</display:table>
	    </div>

	    <hotent:paging tableId="biddingPublicityItem"/>

	</div>
			
</body>
</html>


