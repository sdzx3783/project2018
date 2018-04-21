<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>加班申请表单管理</title>
<%@include file="/commons/include/get.jsp" %>
<script type="text/javascript">
</script>
</head>
<body>
	<div class="oa-project-title">
        <h2>值班调休管理列表</h2>
    </div>
	<div class="oa-project-search">
        <form id="searchForm" method="post" action="list.ht">
            <span class="oa-fl">姓名</span>
            <div class="oa-input-wrap oa-w165 oa-fl">
                <input class="oa-input"  name="name"   value="${param['name']}"/>
            </div>
            <button class="oa-button oa-button--primary oa-button--blue">查询</button>
        </form>
    </div>
		<div class="panel-body">
		     <div class="oa-table-scroll-wrap">
		    <display:table name="hdOvertimeApplicationsList" id="hdOvertimeApplicationsItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
				 
				<display:column title="姓名">${hdOvertimeApplicationsItem.name}</display:column>
				<display:column title="值班总天数">${hdOvertimeApplicationsItem.overTimeDays}</display:column>
				<display:column title="调休天数">${hdOvertimeApplicationsItem.adjustDays}</display:column>
				<display:column title="剩余未调休天数">${hdOvertimeApplicationsItem.leftAdjustDays}</display:column>
			    <display:column title="管理" media="html" style="width:220px">
					<a href="get.ht?id=${hdOvertimeApplicationsItem.userId}" ><span></span>明细</a> 
				    <a href="past.ht?id=${hdOvertimeApplicationsItem.userId}" >历史数据</a>
				</display:column> 
			</display:table>
			</div>
			<hotent:paging tableId="hdOvertimeApplicationsItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


