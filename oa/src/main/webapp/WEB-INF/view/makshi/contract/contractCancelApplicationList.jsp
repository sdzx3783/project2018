<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>合同作废申请管理</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body class="oa-mw-page">
		<!-- <div id="oa_list_title" class="oa-mgb20 oa-project-title">
        	<h2>合同作废申请管理列表</h2>
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
			                <div class="oa-label">作废原因</div>
			                <div class="oa-input-wrap oa-mgl100">
			                    <input class="oa-input"  name="Q_cancel_reason_SL"   value="${param['Q_cancel_reason_SL']}"/>
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
      	 	<display:table export="true" name="contractCancelApplicationList" id="contractCancelApplicationItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
            	<display:column title="${checkAll}"   media="html">
			  		<input type="checkbox" class="pk" name="id" value="${contractCancelApplicationItem.id}">
				</display:column>
				
				<display:column title="工单号">
					<a target="_blank" href="/makshi/contract/contractCancelApplication/get.ht?id=${contractCancelApplicationItem.id }">${contractCancelApplicationItem.globalFlowNo}</a>
				</display:column>
				<display:column title="申请人">
					${contractCancelApplicationItem.creator}
				</display:column>
				<display:column title="申请时间">
					<fmt:formatDate value="${contractCancelApplicationItem.createTime}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column title="合同编号">
					${contractCancelApplicationItem.contract_num}
				</display:column>
				<display:column title="甲方 ">
					${contractCancelApplicationItem.first_party}
				</display:column>
				<display:column title="合同归属部门">
					${contractCancelApplicationItem.contract_department}
				</display:column>
				<display:column title="项目总监">
					${contractCancelApplicationItem.project_director}
				</display:column>
				<display:column title="投资总额（万元）">
					${contractCancelApplicationItem.total_investment}
				</display:column>
				<display:column title="合同经手人">
					${contractCancelApplicationItem.handle_person}
				</display:column>
				<display:column title="项目负责人">
					${contractCancelApplicationItem.responsible_person}
				</display:column>
				<display:column title="合同金额（万元）">
					${contractCancelApplicationItem.account}
				</display:column>
				<display:column title="签约时间">
					<fmt:formatDate value="${contractCancelApplicationItem.singing_time}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column title="审批状态">
					${contractCancelApplicationItem.processStatus}
				</display:column>
				<%-- <display:column title="管理"   style="width:220px">
					<a href="del.ht?id=${contractCancelApplicationItem.id}" class="link del"><span></span>删除</a>
					<a href="edit.ht?id=${contractCancelApplicationItem.id}" class="link edit"><span></span>编辑</a>
					<a href="get.ht?id=${contractCancelApplicationItem.id}" class="link detail"><span></span>明细</a>
				</display:column> --%>
            </display:table>
        </div><!-- end of panel-body -->                
        <hotent:paging tableId="contractCancelApplicationItem"/>
    </div> <!-- end of panel -->
    <script>
		$(function(){
			$('.oa-accordion').oaAccordion({collapse: true});
		});
	</script>
</body>
</html>


