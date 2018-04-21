<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>排水处月报管理管理</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body class="oa-mw-page">

	<div id="oa_list_title" class="oa-mgb20 oa-project-title">
	    <h2>排水处月报管理列表</h2>
	</div>

    <div id="oa_list_search" class="oa-pdb10 oa-mgh20">
        <form id="searchForm" method="post" action="list.ht" class="oa-clear">
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">月报名称</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input type="text" name="name" class="oa-input"   value="${param['name']}"/>
                </div>
            </div>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">提交时间</div>
                <div class="oa-input-wrap oa-mgl100 oa-input-wrap--ib">
                    <input type="text" id="Q_beginedit_date_DL" name="Q_beginedit_date_DL"  class="oa-input Wdate" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'Q_endedit_date_DG\');}'})" value="${param['Q_beginedit_date_DL']}"/>
                </div>
                <span>至</span>
                <div class="oa-input-wrap oa-input-wrap--ib">
                    <input type="text" id="Q_endedit_date_DG" name="Q_endedit_date_DG"  class="oa-input Wdate" onclick="WdatePicker({minDate:'#F{$dp.$D(\'Q_beginedit_date_DL\');}'})"  value="${param['Q_endedit_date_DG']}"/>
                </div>
            </div>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">月份</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input type="text" name="month"  class="oa-input Wdate"  value="${param['month']}" onfocus="WdatePicker({dateFmt:'yyyy-MM'})"/> 
                </div>
            </div>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">片区</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input type="text" name="grop" class="oa-input"   value="${param['grop']}"/>
                </div>
            </div>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">项目名称</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input type="text" name="item" class="oa-input"   value="${param['item']}"/>
                </div>
            </div>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">提交人</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input type="text" name="editer" class="oa-input"  value="${param['editer']}"/>
                </div>
            </div>

            <div class="oa-fl oa-mgb10 oa-mgh20">
                <button class="oa-button oa-button--primary oa-button--blue">查询</button>
            </div>
        </form>
    </div>

	<div class="oa-pdb20 oa-mgh20">
	    <div id="oa_list_content" class="oa-table-scroll-wrap">
		    <display:table name="drainageMonthlyList" id="drainageMonthlyItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
				<display:column title="月报名称">${drainageMonthlyItem.name}</display:column>
				<display:column title="片区">${drainageMonthlyItem.grop}</display:column>
				<display:column title="项目名称">${drainageMonthlyItem.item}</display:column>
				<display:column title="月份">${drainageMonthlyItem.month}</display:column>
				<display:column title="提交人">${drainageMonthlyItem.editer}</display:column>
				<display:column title="提交日期">
					<fmt:formatDate value='${drainageMonthlyItem.edit_date}' pattern='yyyy-MM-dd'/>
				</display:column>
				<display:column title="备注">${drainageMonthlyItem.remarks}</display:column>
				
				<display:column title="管理" media="html">
					<a href="get.ht?id=${drainageMonthlyItem.id}" class="oa-button-label">详情</a>
				</display:column>
			</display:table>
    	</div>

		<hotent:paging tableId="drainageMonthlyItem"/>
	</div>

</body>
</html>


