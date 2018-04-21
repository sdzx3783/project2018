<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>开票统计表</title>
	<%@include file="/commons/include/get.jsp" %>
	<%@ taglib prefix="hotent" uri="http://www.jee-soft.cn/paging"%>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	<style>
	.link.oa-input.org {
	    position: absolute;
	    top: 8px;
	    right: 10px;
	}</style>
</head>
<body class="oa-mw-page">

    <!-- <div id="oa_list_title" class="oa-mgb20 oa-project-title">
	    <h2>开票统计表</h2>
	</div> -->
	
	<!-- <div id="oa_search_table_button_wrap" class="oa-head-wrap">
		<button type="button" onclick="tosubmit()" class="oa-button oa-button--primary oa-button--blue">查询</button>
        <a class="oa-button oa-button--primary oa-button--blue" onclick="exportContractExcel()" href="#">导出</a>
	</div> -->
    <div id="oa_list_search" class="oa-pdb10 oa-mgh30" style="margin-top: 20px;">
    	<div class="oa-accordion">
           	<div class="oa-accordion__title" style="margin-left:20px;">查询条件</div>
           	<div class="oa-accordion__content">
		        <form id="searchForm" method="post" action="contractBillingStats.ht" class="oa-clear">
		            <div class="oa-fl oa-mgb10">
		                <div class="oa-label">申请人</div>
		                <div class="oa-input-wrap oa-mgl100 oa-input-wrap--ib">
		                    <input type="text"  name="applicant"  class="oa-input" value="${param['applicant']}"/>
		                </div>
		            </div>
		            <c:if test="${empty chooseOrgId || chooseOrgId==-1}">
			            <div class="oa-fl oa-mgb10">
			                <div class="oa-label">申请部门</div>
			                <div class="oa-input-wrap oa-mgl100">
			                	<%-- <input name="department" id="department"  type="text"  class="oa-input org"  value="${param['department']}"  /> --%>
			                	<select id="orgId" name='orgId' class='oa-select' >
			                		 <option value="-1" >---请选择---</option>
				               		 <c:forEach items="${orgs }" var="e">
				               		 	<option value="${e.orgId }" <c:if test="${orgId==e.orgId}">selected</c:if>>${e.orgName }</option>
				               		 </c:forEach>
								</select>
			                </div>
			            </div>
		            </c:if>
		             
		            <div class="oa-fl oa-mgb10">
		                <div class="oa-label">申请日期从</div>
		                <div class="oa-input-wrap oa-mgl100 oa-input-wrap--ib">
		                    <input type="text" id="Q_beginapplication_time_DL" name="Q_beginapplication_time_DL"  class="oa-input Wdate" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'Q_endapplication_time_DG\');}'})" value="${param['Q_beginapplication_time_DL']}"/>
		                </div>
		                <span>至</span>
		                <div class="oa-input-wrap oa-input-wrap--ib">
		                    <input type="text" id="Q_endapplication_time_DG" name="Q_endapplication_time_DG"  class="oa-input Wdate" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'Q_beginapplication_time_DL\');}'})"  value="${param['Q_endapplication_time_DG']}"/>
		                </div>
		            </div>
		
		            <div class="oa-fl oa-mgb10">
		                <div class="oa-label">开票状态</div>
		                <div class="oa-select-wrap oa-mgl100">
		                    <select class="oa-select" id="billStatus" name="billStatus">
								<option value="-1">-请选择-</option>
								<option value="0" <c:if test="${param['billStatus']==0 }">selected=selected</c:if>>未开</option>
								<option value="1" <c:if test="${param['billStatus']==1 }">selected=selected</c:if>>已开</option>
							</select>
		                </div>
		            </div>
		            <div class="oa-fl oa-mgb10">
		                <div class="oa-label">开票金额</div>
		                <div class="oa-input-wrap oa-mgl100 oa-input-wrap--ib">
		                    <input type="text" class="oa-input" id="minbillsum" name="minbillsum" validate="{'number':true,'maxIntLen':14,'maxDecimalLen':2,'range':[0,99999999999]}"   value="${param['minbillsum']}"/>
		                </div>
		                <span>至</span>
		                <div class="oa-input-wrap oa-input-wrap--ib">
		                    <input type="text" class="oa-input" id="maxbillsum" name="maxbillsum" validate="{'number':true,'maxIntLen':14,'maxDecimalLen':2,'range':[0,99999999999]}"   value="${param['maxbillsum']}"/>
		                </div>
		            	<button type="button" onclick="tosubmit()" class="oa-button oa-button--primary oa-button--blue">查询</button>
		        		<a class="oa-button oa-button--primary oa-button--blue" onclick="exportContractExcel()" href="#">导出</a>
		            </div>
		        </form>
		     </div>
		 </div>
    </div>


	<div class="oa-pdb20 oa-mgh20">
		<div id="oa_list_content" class="oa-table-scroll-wrap">
	
			<c:set var="checkAll">
				<input type="checkbox" id="chkall" />
			</c:set>
	
			<display:table name="contractBillingStatsList" id="contractBillingStats"
				requestURI="contractBillingStats.ht" sort="external" class="oa-table--default oa-table--nowrap">
				
				<display:column titleKey="序号">
					${contractBillingStats_rowNum}
				</display:column>
				<display:column title="工单号">
					${contractBillingStats.globalflowno }
				</display:column>
				<display:column title="申请人">
					${contractBillingStats.applicant}
				</display:column>
				<display:column title="申请部门">
					${contractBillingStats.department}
				</display:column>
				<display:column title="申请日期">
					<fmt:formatDate value='${contractBillingStats.application_time}' pattern='yyyy-MM-dd' />
				</display:column>
				<display:column title="紧急程度">
					<c:if test="${contractBillingStats.urgency_level==1}">正常</c:if>
					<c:if test="${contractBillingStats.urgency_level==2}">重要</c:if>
					<c:if test="${contractBillingStats.urgency_level==3}">紧急</c:if>
				</display:column>
				<display:column title="合同编号">
					${contractBillingStats.contract_num}
				</display:column>
				<display:column title="合同名称">
					${contractBillingStats.contract_name}
				</display:column>
				<display:column title="开票类型">
					<c:if test='${contractBillingStats.billing_type==1 }'>普通</c:if>
					<c:if test='${contractBillingStats.billing_type==2 }'>专票</c:if>
				</display:column>
				<display:column title="购买方名称">
					${contractBillingStats.buyerName}
				</display:column>
				<display:column title="货物或应税劳务、服务名称">
					${contractBillingStats.goodsName}
				</display:column>
				<display:column title="开票金额">
					<fmt:formatNumber value="${contractBillingStats.billing_money}" pattern="#.##" type="number"/> 
				</display:column>
				<display:column title="纳税人识别号">
					${contractBillingStats.taxNum}
				</display:column>
				<display:column title="购买方地址">
					${contractBillingStats.buyerAddress}
				</display:column>
				<display:column title="购买方电话">
					${contractBillingStats.buyerPhone}
				</display:column>
				<display:column title="购买方开户银行">
					${contractBillingStats.buyerBank}
				</display:column>
				<display:column title="购买方账号">
					${contractBillingStats.buyerAccount}
				</display:column>
				<display:column title="销售方开户行">
					${contractBillingStats.salerBank}
				</display:column>
				<display:column title="销售方账号">
					${contractBillingStats.salerAccount}
				</display:column>
				<display:column title="备注">
					${contractBillingStats.remark}
				</display:column>
				<display:column title="开票状态">
					<c:if test="${empty contractBillingStats.ticketId }">未开票</c:if>
					<c:if test="${not empty contractBillingStats.ticketId }">已开票</c:if>
				</display:column>
				
			</display:table>
		</div>
		<hotent:paging tableId="contractBillingStats" />
	</div>
	
	
	<script type="text/javascript">
		$(function(){
			$('.oa-accordion').oaAccordion({collapse: true});
		});
		function exportContractExcel(){
			var frm=$('#searchForm').form();
			if(frm.valid()){
				if(confirm("确定导出")){
					window.location.href="exportContractExcel.ht?Q_beginapplication_time_DL="+document.getElementById('Q_beginapplication_time_DL').value
							+"&Q_endapplication_time_DG="+document.getElementById('Q_endapplication_time_DG').value+"&billStatus="
							+document.getElementById('billStatus').value+"&minbillsum="+document.getElementById('minbillsum').value
							+"&maxbillsum="+document.getElementById('maxbillsum').value+"&orgId="+$.trim($("#orgId").val());
				}
			}
		}
		
		function tosubmit(){
			var frm=$('#searchForm').form();
			if(frm.valid()){
				$('#searchForm').submit();
			}
		}
	</script>
</body>
</html>


