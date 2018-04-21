<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>公章外借申请表管理</title>
<%@include file="/commons/include/get.jsp" %>
<script type="text/javascript">
</script>
</head>
<body class="oa-mw-page">
		<div id="oa_list_title" class="oa-mgb20 oa-project-title">
        <!-- <h2>公章外借申请表管理列表</h2> -->
   		</div>
 		<div id="oa_list_search" class="oa-pdb10 oa-mgh20">
	        <form id="searchForm" method="post" action="list.ht" class="oa-clear">
	            <div class="oa-fl oa-mgb10">
	                <div class="oa-label">申请人</div>
	                <div class="oa-input-wrap oa-mgl100">
	                    <input class="oa-input"  name="application_person"   value="${param['application_person']}"/>
	                </div>
	            </div>
	            <div class="oa-fl oa-mgb10">
	                <div class="oa-label">申请部门</div>
	                <div class="oa-input-wrap oa-mgl100">
	                    <input class="oa-input"  name="department"   value="${param['department']}"/>
	                </div>
	            </div>
	             <div class="oa-fl oa-mgb10">
	                <div class="oa-label">前往单位</div>
	                <div class="oa-input-wrap oa-mgl100">
	                    <input class="oa-input"  name="go_to_unit"   value="${param['go_to_unit']}"/>
	                </div>
	            </div>
	            <div class="oa-fl oa-mgb10">
	                <div class="oa-label">使用印章名称</div>
	                <div class="oa-input-wrap oa-mgl100">
	                    <input class="oa-input"  name="seal_name"   value="${param['seal_name']}"/>
	                </div>
	            </div>
	            <div class="oa-fl oa-mgb10">
	                <div class="oa-label">借出时间</div>
	                <div class="oa-input-wrap oa-mgl100">
	                    <input class="oa-input date"  name="beginborrow_time"   value="${param['beginborrow_time']}"/>
	                </div>
	            </div>
	            <div class="oa-fl oa-mgb10">
	                <div class="oa-label">到</div>
	                <div class="oa-input-wrap oa-mgl100">
	                   <input class="oa-input date"  name="endborrow_time"   value="${param['endborrow_time']}"/>
	                </div>
	            </div>
	            <div class="oa-fl oa-mgb10 oa-mgh20">
		                <button class="oa-button oa-button--primary oa-button--blue">查询</button>
	            </div>
	        </form>
        </div>
				<%--
				<li>
					<span class="label">归还时间</span>
					<input type="text"  name="beginbackDate"  class="inputText date" validate="{date:true}" value="${param['(beginbackDate']}"/>
					至<input type="text"  name="endbackDate"  class="inputText date" validate="{date:true}" value="${param['endbackDate']}"/>
				</li> --%>
		<div class="oa-pdb20 oa-mgh20">
	    	<div id="oa_list_content" class="oa-table-scroll-wrap">
		    	<display:table name="sealBorrowList" id="sealBorrowItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
					<display:column title="工单号">${sealBorrowItem.globalNo}</display:column>
					<display:column title="申请人">${sealBorrowItem.application_person}</display:column>
					<display:column title="申请部门">${sealBorrowItem.department}</display:column>
					<display:column title="外出携带公章事由">${sealBorrowItem.reason}</display:column>
					<display:column title="前往单位">${sealBorrowItem.go_to_unit}</display:column>
					<display:column title="使用印章名称">${sealBorrowItem.seal_name}</display:column>
					<display:column title="借出时间">
						<fmt:formatDate value="${sealBorrowItem.borrow_time }"   pattern="yyyy-MM-dd " type="date" dateStyle="long" />
					</display:column>
					<display:column title="预计归还时间">
						<fmt:formatDate value="${sealBorrowItem.return_time }"   pattern="yyyy-MM-dd " type="date" dateStyle="long" />
					</display:column>
					<display:column title="归还时间">
						<fmt:formatDate value="${sealBorrowItem.backDate }"   pattern="yyyy-MM-dd " type="date" dateStyle="long" />
					</display:column>
					<display:column title="备注">${sealBorrowItem.remark}</display:column>
					<display:column title="流程状态">${sealBorrowItem.status}</display:column>
				<display:column title="管理" media="html" style="width:220px">
					<a href="get.ht?id=${sealBorrowItem.id}" target = "_blank">明细</a>
				</display:column>
				</display:table>
			</div><!-- end of panel-body -->				
			<hotent:paging tableId="sealBorrowItem"/>
		</div> <!-- end of panel -->
</body>
</html>


