<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>合同借阅申请管理</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body class="oa-mw-page">
		<!-- <div id="oa_list_title" class="oa-mgb20 oa-project-title">
        	<h2>合同借阅申请管理列表</h2>
   		</div> -->
        <div id="oa_list_search" class="oa-pdb10 oa-mgh20">
		    <div class="oa-accordion">
	            <div class="oa-accordion__title">查询条件</div>
	            <div class="oa-accordion__content">
         	        <form id="searchForm" method="post" action="list.ht" class="oa-clear">
			            <div class="oa-fl oa-mgb10">
			                <div class="oa-label">工单号</div>
			                <div class="oa-input-wrap oa-mgl100">
			                    <input class="oa-input"  name="Q_globalflowno_SL"   value="${param['Q_globalflowno_SL']}"/>
			                </div>
			            </div>
			            <div class="oa-fl oa-mgb10">
			                <div class="oa-label">申请人</div>
			                <div class="oa-input-wrap oa-mgl100">
			                    <input class="oa-input"  name="Q_creator_SL"   value="${param['Q_creator_SL']}"/>
			                </div>
			            </div>
			            <div class="oa-fl oa-mgb10">
			                <div class="oa-label">合同编号</div>
			                <div class="oa-input-wrap oa-mgl100">
			                    <input class="oa-input"  name="Q_contract_num_SL"   value="${param['Q_contract_num_SL']}"/>
			                </div>
			            </div>
			            <div class="oa-fl oa-mgb10">
			                <div class="oa-label">合同名称</div>
			                <div class="oa-input-wrap oa-mgl100">
			                    <input class="oa-input"  name="Q_contract_name_SL"   value="${param['Q_contract_name_SL']}"/>
			                </div>
			            </div>
			            <div class="oa-fl oa-mgb10">
			                <div class="oa-label">借阅人</div>
			                <div class="oa-input-wrap oa-mgl100">
			                    <input class="oa-input"  name="Q_borrower_SL"   value="${param['Q_borrower_SL']}"/>
			                </div>
			            </div>	
			            <div class="oa-fl oa-mgb10">
				            <div class="oa-fl oa-mgb10">
				                <div class="oa-label">借阅日期从</div>
				                <div class="oa-input-wrap oa-mgl100">
				                    <input class="oa-input Wdate" id="Q_beginborrow_date_DL" name="Q_borrower_SL"  onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'Q_endborrow_date_DG\');}'})" value="${param['Q_beginborrow_date_DL']}"/>
				                </div>			                
				            </div>
				            <div class="oa-fl oa-mgb10">
				            	<div class="oa-label" style="width:50px;">至</div>
				                <div class="oa-input-wrap oa-mgl100" style="margin-left:50px;">
				                    <input class="oa-input Wdate" id="Q_endborrow_date_DG" name="Q_endborrow_date_DG"  onfocus="WdatePicker({minDate:'#F{$dp.$D(\'Q_beginborrow_date_DL\');}'})"  value="${param['Q_endborrow_date_DG']}"/>
				                </div>
				            </div>	
				        </div>
				        <div class="oa-fl oa-mgb10">
				            <div class="oa-fl oa-mgb10">
				                <div class="oa-label">预计归还日期从</div>
				                <div class="oa-input-wrap oa-mgl100">
				                    <input class="oa-input Wdate" id="Q_beginexpected_return_date_DL" name="Q_beginexpected_return_date_DL"  onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'Q_endexpected_return_date_DG\');}'})" value="${param['Q_beginexpected_return_date_DL']}"/>
				                </div>			                
				            </div>
				            <div class="oa-fl oa-mgb10">
				            	<div class="oa-label" style="width:50px;">至</div>
				                <div class="oa-input-wrap oa-mgl100" style="margin-left:50px;">
				                    <input class="oa-input Wdate" id="Q_endexpected_return_date_DG" name="Q_endexpected_return_date_DG"  onfocus="WdatePicker({minDate:'#F{$dp.$D(\'Q_beginexpected_return_date_DL\');}'})"  value="${param['Q_endexpected_return_date_DG']}"/>
				                </div>
				            </div>
				        </div>
			            <div class="oa-fl oa-mgb10 oa-mgh20">
			                <button id="btnSearch" class="oa-button oa-button--primary oa-button--blue">查询</button>
							<!-- <div class="l-bar-separator"></div>
							<div class="group"><a class="link add" href="edit.ht"><span></span>添加</a></div>
							<div class="l-bar-separator"></div>							
							<div class="group"><a class="link update" id="btnUpd" action="edit.ht"><span></span>修改</a></div>
							<div class="l-bar-separator"></div>
							<div class="group"><a class="link del"  action="del.ht"><span></span>删除</a></div> -->
			            </div>
			        </form>    
	            </div>
		    </div>
    	</div>
        <div class="oa-pdb20 oa-mgh20">
    	 <c:set var="checkAll">
			<input type="checkbox" id="chkall"/>
		 </c:set>
      	 <div id="oa_list_content" class="oa-table-scroll-wrap">
      	 	<display:table export="true" name="contractBorrowApplicationList" id="contractBorrowApplicationItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
            	<display:column title="${checkAll}"   media="html">
			  		<input type="checkbox" class="pk" name="id" value="${contractBorrowApplicationItem.id}">
				</display:column>
				
				<display:column title="工单号" >
					<a target="_blank" href="/makshi/contract/contractBorrowApplication/get.ht?id=${contractBorrowApplicationItem.id }">${contractBorrowApplicationItem.globalFlowNo}</a>
				</display:column>
				<display:column title="申请人">
					${contractBorrowApplicationItem.creator}
				</display:column>
				<display:column title="申请时间">
					<fmt:formatDate value="${contractBorrowApplicationItem.createTime}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column title="合同编号">
					${contractBorrowApplicationItem.contract_num}
				</display:column>
				<display:column title="合同名称">
					${contractBorrowApplicationItem.contract_name}
				</display:column>
				<display:column title="合同金额(万元)">
					${contractBorrowApplicationItem.contract_amount}
				</display:column>
				<display:column title="借阅人">
					${contractBorrowApplicationItem.borrower}
				</display:column>
				<display:column title="借阅日期">
					<fmt:formatDate value="${contractBorrowApplicationItem.borrow_date}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column title="预计归还日期">
					<fmt:formatDate value="${contractBorrowApplicationItem.expected_return_date}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column title="借阅用途">
					${contractBorrowApplicationItem.borrow_reason}
				</display:column>
<%-- 				<display:column title="归还日期">
					<fmt:formatDate value="${contractBorrowApplicationItem.return_date}" pattern="yyyy-MM-dd"/>
				</display:column> --%>
				<%-- <display:column title="归还日期">
					${contractBorrowApplicationItem.singing_time}
				</display:column> --%>
				<display:column title="备注">
					${contractBorrowApplicationItem.remark}
				</display:column>
				<display:column title="审批状态">
					${contractBorrowApplicationItem.processStatus}
				</display:column>
				
				<%-- <display:column title="管理">
					<a href="del.ht?id=${contractBorrowApplicationItem.id}" class="link del"><span></span>删除</a>
					<a href="edit.ht?id=${contractBorrowApplicationItem.id}" class="link edit"><span></span>编辑</a>
					<a href="get.ht?id=${contractBorrowApplicationItem.id}" class="link detail"><span></span>明细</a>
				</display:column> --%>
            </display:table>
        </div><!-- end of panel-body -->                
        <hotent:paging tableId="contractBorrowApplicationItem"/>
    </div> <!-- end of panel -->
    <script>
		$(function(){
			$('.oa-accordion').oaAccordion({collapse: true});
		});
	</script>
</body>
</html>


