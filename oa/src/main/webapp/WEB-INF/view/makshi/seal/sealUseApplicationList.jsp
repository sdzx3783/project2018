<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>公章使用申请表管理</title>
<%@include file="/commons/include/get.jsp" %>
<script type="text/javascript">
</script>
</head>
<body class="oa-mw-page">
	<div id="oa_list_title" class="oa-mgb20 oa-project-title">
        <!-- <h2>公章使用申请表管理列表</h2> -->
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
                <div class="oa-label">合同编号</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input class="oa-input"  name="contract_id"   value="${param['contract_id']}"/>
                </div>
            </div>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">使用时间</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input class="oa-input date"  name="beginappiDate"   value="${param['beginappiDate']}"/>
                </div>
            </div>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">到</div>
                <div class="oa-input-wrap oa-mgl100">
                   <input class="oa-input date"  name="endappiDate"   value="${param['endappiDate']}"/>
                </div>
            </div>
            <div class="oa-fl oa-mgb10 oa-mgh20">
	                <button class="oa-button oa-button--primary oa-button--blue">查询</button>
	            </div>
	        </form>
    	</div>
            
		<div class="oa-pdb20 oa-mgh20">
	    	<div id="oa_list_content" class="oa-table-scroll-wrap">
		   	    <display:table name="sealUseApplicationList" id="sealUseApplicationItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
					<display:column title="工单号">${sealUseApplicationItem.glowbalNo}</display:column>
					<display:column title="申请人">${sealUseApplicationItem.application_person}</display:column>
					<display:column title="申请时间">
						<fmt:formatDate value="${sealUseApplicationItem.createTime }"   pattern="yyyy-MM-dd " type="date" dateStyle="long" />
					</display:column>
					<display:column title="申请部门">${sealUseApplicationItem.department}</display:column>
					<display:column title="材料内容">${sealUseApplicationItem.contents}</display:column>
					<display:column title="份数">${sealUseApplicationItem.number}</display:column>
					<display:column title="合同编号">${sealUseApplicationItem.contract_id}</display:column>
					<display:column title="使用时间">
						<fmt:formatDate value="${sealUseApplicationItem.appiDate }"   pattern="yyyy-MM-dd " type="date" dateStyle="long" />
					</display:column>
					<display:column title="备注">${sealUseApplicationItem.remark}</display:column>
					<display:column title="流程状态">${sealUseApplicationItem.status}</display:column>
					<%-- <display:column title="管理" media="html" style="width:5%px">
						<a href="get.ht?id=${sealUseApplicationItem.id}">明细</a> 
					</display:column> --%>
			</display:table>
		</div><!-- end of panel-body -->				
		<hotent:paging tableId="sealUseApplicationItem"/>
	</div> <!-- end of panel -->
</body>
</html>


