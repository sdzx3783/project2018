<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>合同盖章申请管理</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body class="oa-mw-page">
		<!-- <div id="oa_list_title" class="oa-mgb20 oa-project-title">
        	<h2>合同盖章申请管理列表</h2>
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
			                <div class="oa-label">合同名称</div>
			                <div class="oa-input-wrap oa-mgl100">
			                    <input class="oa-input"  name="Q_contract_name_SL"   value="${param['Q_contract_name_SL']}"/>
			                </div>
			            </div>
						<div class="oa-fl oa-mgb10">
			                <div class="oa-label">合同类型</div>
			                <div class="oa-input-wrap oa-mgl100">
			                    <input class="oa-input"  name="Q_contract_type_SL"   value="${param['Q_contract_type_SL']}"/>
			                </div>
			            </div>
			            <div class="oa-fl oa-mgb10">
			                <div class="oa-label">项目审批人</div>
			                <div class="oa-input-wrap oa-mgl100">
			                    <input class="oa-input"  name="Q_project_administrator_SL"   value="${param['Q_project_administrator_SL']}"/>
			                </div>
			            </div>
			            <div class="oa-fl oa-mgb10 oa-mgh20">
			                <button class="oa-button oa-button--primary oa-button--blue">查询</button>
			            </div>
			        </form>    
	            </div>
		    </div>
    	</div>
    	
        <div class="oa-pdb20 oa-mgh20">
    	 <c:set var="startNum" value="${(pageBeancontractSealApplicationItem.currentPage-1)*pageBeancontractSealApplicationItem.pageSize+1}"></c:set>
      	 <div id="oa_list_content" class="oa-table-scroll-wrap">
            <display:table export="true" name="contractSealApplicationList" id="contractSealApplicationItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
            	<display:column title="序号"> 
			    	<c:out value="${startNum}"/>
					<c:set var="startNum" value="${startNum+1}"/>
			    </display:column>
                <display:column title="工单号">
                   <a target="_blank" href="/makshi/contract/contractSealApplication/get.ht?id=${contractSealApplicationItem.id }">${contractSealApplicationItem.globalFlowNo}</a>
                </display:column>
                <display:column title="申请人">
                    ${contractSealApplicationItem.creator}
                </display:column>
                <display:column title="申请时间">
                    <fmt:formatDate value="${contractSealApplicationItem.createTime}" pattern="yyyy-MM-dd"/>
                </display:column>
                <display:column title="合同编号"><span></span>
                    ${contractSealApplicationItem.contract_num}
                </display:column>
                <display:column title="合同名称">
                    ${contractSealApplicationItem.contract_name}
                </display:column>
                <display:column title="项目总监">
                    ${contractSealApplicationItem.project_director}
                </display:column>
                <display:column title="合同类型">
                    ${contractSealApplicationItem.contracttype}
                </display:column>
                <display:column title="合同归属部门">
                    ${contractSealApplicationItem.contract_department}
                </display:column>
                <display:column title="甲方">
                    ${contractSealApplicationItem.first_party}
                </display:column>
                <display:column title="乙方">
                    ${contractSealApplicationItem.second_party}
                </display:column>
                <display:column title="投资总额（万元）"><span></span>
                    ${contractSealApplicationItem.total_investment}
                </display:column>
                <display:column title="合同金额（万元）"><span></span>
                    ${contractSealApplicationItem.contract_money}
                </display:column>
                <display:column title="签约时间">
                    <fmt:formatDate value="${contractSealApplicationItem.signing_time}" pattern="yyyy-MM-dd"/>
                </display:column>
                <display:column title="结算金额（万元）"><span></span>
                    ${contractSealApplicationItem.settlement_money}
                </display:column>
                <display:column title="项目审批人">
                    ${contractSealApplicationItem.project_administrator}
                </display:column>
                <display:column title="审批状态">
                    ${contractSealApplicationItem.processStatus}
                </display:column>
            </display:table>
        </div><!-- end of panel-body -->                
        <hotent:paging tableId="contractSealApplicationItem"/>
    </div> <!-- end of panel -->
    <script>
		$(function(){
			$('.oa-accordion').oaAccordion({collapse: true});
		});
	</script>
</body>
</html>
