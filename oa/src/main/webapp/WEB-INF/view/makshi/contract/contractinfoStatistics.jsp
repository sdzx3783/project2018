<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>深水咨询近${year }年合同签订情况统计表</title>
<%@include file="/commons/include/get.jsp"%>
<%@ taglib prefix="hotent" uri="http://www.jee-soft.cn/paging" %>
</head>
<body class="oa-mw-page">

    <div id="oa_list_title" class="oa-mgb20 oa-project-title">
        <h2>深水咨询近${year}年合同签订情况统计表</h2>
    </div>

    <div id="oa_list_search" class="oa-pdb10 oa-mgh20">
        <form id="searchForm" method="post" action="statistics.ht" class="oa-clear">

            <div class="oa-fl oa-mgb10">
                <div class="oa-label oa-label150">输入要查询年份的数量</div>
                <div class="oa-input-wrap oa-mgl150">
                    <input class="oa-input" type="text" name="years" onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,'');}).call(this)" onblur="this.v();" value="${param['years']}"/>
                </div>
            </div>

            <div class="oa-fl oa-mgb10 oa-mgh20">
                <button class="oa-button oa-button--primary oa-button--blue">查询</button>
                <button class="oa-button oa-button--primary oa-button--blue" onclick='window.parent.frames.addToTab("/makshi/contract/contractinfo/singingRate.ht","历年合同签订及归档统计","10000011850001","/images/transparent.png");'>历年合同签订及归档统计</button>
                <button class="oa-button oa-button--primary oa-button--blue" onclick='window.parent.frames.addToTab("/makshi/contract/contractinfo/monthlyCollection.ht","各部门月收款进度表","10000011850002","/images/transparent.png");'>各部门月收款进度表</button>
                <button class="oa-button oa-button--primary oa-button--blue active" onclick='window.parent.frames.addToTab("/makshi/contract/contractinfo/statistics.ht","深水咨询近年合同签订情况统计表","10000015080002","/images/transparent.png");'>深水咨询近年合同签订情况统计表</button>
                <button class="oa-button oa-button--primary oa-button--blue" onclick='window.parent.frames.addToTab("/makshi/contract/contractinfo/output.ht","合同产值统计","20000001750803","/images/transparent.png");'>合同产值统计</button>
            </div>
        </form>
    </div>

    <div class="oa-pdb20 oa-mgh20">
        <div id="oa_list_content" class="oa-table-scroll-wrap">
             <display:table  name="statisticsList" id="statisticsItem" requestURI="#" sort="external" cellpadding="1" cellspacing="1"  class="oa-table--default oa-table--nowrap">
                <display:column title="年度">${statisticsItem.singingYear}</display:column>
				<display:column title="合同总数/个">${statisticsItem.allNumber}</display:column>
				<display:column title="合同总金额/万元">${statisticsItem.allMoney}</display:column>
				<display:column title="采购合同总数/个">${statisticsItem.cgNumber}</display:column>
				<display:column title="采购合同总金额/万元">${statisticsItem.cgMoney}</display:column>
				<display:column title="非采购合同总数/个">${statisticsItem.fcgNumber}</display:column>
				<display:column title="非采购合同总金额/万元">${statisticsItem.fcgMoney}</display:column>
            </display:table>
        </div>
    </div>
</body>
</html>


