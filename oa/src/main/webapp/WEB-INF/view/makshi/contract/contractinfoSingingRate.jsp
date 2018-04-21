<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
	<%@include file="/commons/include/list_get.jsp"%>
	<f:link href="Aqua/css/ligerui-all.css" ></f:link>
    <f:link href="tree/zTreeStyle.css" ></f:link>
    <script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
    
     <script type="text/javascript" src="${ctx}/js/lg/newligerui.all.js" ></script>
     <script type="text/javascript" src="${ctx}/js/tree/jquery.ztree.js"></script>
    <script type="text/javascript" src="${ctx}/js/lg/plugins/ligerComboBox.js"></script>
    <script type="text/javascript" src="${ctx}/js/lg/plugins/htDicCombo.js"></script> 
    
    <script type="text/javascript" src="${ctx}/js/jquery/jquery.form.js"></script>
    <script type="text/javascript" src="${ctx}/js/lg/plugins/ligerDrag.js" ></script>
    <script type="text/javascript" src="${ctx}/js/lg/plugins/newligerDialog.js" ></script>
    <script type="text/javascript" src="${ctx}/js/lg/plugins/ligerResizable.js" ></script>
    <%@include file="/commons/include/ueditor.jsp" %>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
    <script type="text/javascript" src="${ctx}/js/lg/util/DialogUtil.js" ></script>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/form/SelectorInit.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/ajaxgrid.js"></script>
    <script type="text/javascript" src="${ctx}/js/lg/util/DialogUtil.js" ></script>
	<%@ taglib prefix="hotent" uri="http://www.jee-soft.cn/paging" %>
	<link href="${ctx}/js/select2/select2.css" rel="stylesheet" />
	<link href="${ctx}/js/select2/select2-metronic.css" rel="stylesheet" />
	<style type="text/css">
		.form-body {
    margin-bottom: 20px;
    margin-left: 6px;}
    .form-group{margin-right: 20px;}
    .control-label{width:64px;font-size:15px;}
    .age{}
    .form-control{width:200px !important;}
    .control-label label{min-width:23px;}
    table tr th {
    font-size: 14px;
    color: #000;    
    font-weight: 600;
    }
    .departtd{
    font-weight: bold;
    }
    .executivetitle{
   		font-size: 20px !important;
    	color: #000;
    	font-weight: 600 !important;
    }
	</style>
</head>
<body class="oa-mw-page">

    <div id="oa_list_title" class="oa-mgb10 oa-project-title">
		<h2 class='executivetitle'>历年合同签订归档统计表</h2>
    </div>

	<div class="col-md-12" style="padding: 20px">
		<div class="oa-fl oa-mgb10 oa-mgh20">
			<select id="tabStatics" name="tabStatics" class="form-control" style="display: inline-block;" >
				<option value="1" selected="selected" data-url="/makshi/contract/contractinfo/singingRate.ht">报表统计</option>
				<option value="2" data-url="/makshi/contract/contractinfo/customers.ht">深水咨询近年最大客户</option>
				<option value="3" data-url="/makshi/contract/contractinfo/supplier.ht">深水主要供应商</option>
			</select>
       		<button class="oa-button oa-button--primary oa-button--blue active" onclick='window.parent.frames.addToTab("/makshi/contract/contractinfo/singingRate.ht","合同签订归档统计表","10000011850001","/images/transparent.png");'>合同签订归档统计表</button>
       		<button class="oa-button oa-button--primary oa-button--blue" onclick='window.parent.frames.addToTab("/makshi/contract/contractinfo/monthlyCollection.ht","各部门月收款进度表","10000011850002","/images/transparent.png");'>各部门月收款进度表</button>
       		<button class="oa-button oa-button--primary oa-button--blue" onclick='window.parent.frames.addToTab("/makshi/contract/contractinfo/output.ht","合同产值统计","20000001750803","/images/transparent.png");'>合同产值统计</button>
		</div>
    </div>
    
    <div>
        <div class="col-md-12  oa-mgh20">
			<form class="form-inline" role="form" method="post" action="/makshi/contract/contractinfo/singingRate.ht" id="search-form">
		         <div class="form-body">
		         	<div class="form-group">
		               	<label class="control-label">年<label>&nbsp;</label>份</label>
		               	<select id="years" name="years" class="form-control" multiple="multiple" style="width: 250px !important;">
		               	    <c:forEach begin="1989" end="${cutYear }" var="e" >
			               		<option <c:if test="${fn:contains(years,(cutYear-e+1989))}">selected</c:if> >${cutYear-e+1989 }</option>
		               	    </c:forEach>
		               	</select>
		            </div>
		         	<div class="form-group">
		               	<label class="control-label">部<label>&nbsp;</label>门</label>
		               	<select id="orgIds" name='orgIds' class='' multiple="multiple" style="width: 250px !important;">
		               		 <c:forEach items="${orgs }" var="e">
		               		 	<option value="${e.orgId }" <c:if test="${fn:contains(orgIds,e.orgId)}">selected</c:if>>${e.orgName }</option>
		               		 </c:forEach>
						</select>              
		             </div>
		         	<div class="form-group">
		               	<label class="control-label">合同类型</label>
		               	<select id="cotps" name='cotps' class='' multiple="multiple" style="width: 250px !important;">
		               		 <c:forEach items="${contyps }" var="e">
		               		 	<option value="${e }" <c:if test="${fn:contains(cotps,e)}">selected</c:if>>${e }</option>
		               		 </c:forEach>
						</select>              
		             </div>
		             <input type="hidden" name="contractType" value="0"/>
		            <button class="oa-button oa-button--primary oa-button--blue">查询</button>
		         </div>
		     </form>
		 </div>
    </div>

    <div class="oa-pdb20 oa-mgh20">
       	 <div id="oa_list_content" class="oa-table-scroll-wrap">
		    <display:table export="true" name="contractSingingRateList" id="contractSingingRateItem" requestURI="#" sort="external" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
				<display:column title="部门" class="departtd">${contractSingingRateItem.department}</display:column>
				
				<c:if test="${not empty cotps }">
					<display:column title="合同类型" class="departtd">${contractSingingRateItem.contracttype}</display:column>
				</c:if>
				
			<%-- 	<display:column title="采购合同签订(份数)">${contractSingingRateItem.cgContract}</display:column> --%>
				<%-- <display:column title="合同类型(份数)">${contractSingingRateItem.allContractTypes}</display:column> --%>
				<%-- <display:column title="业务合同签订(份数)">${contractSingingRateItem.fcgContract}</display:column> --%>
				<display:column title="合同金额(万元)">
					<fmt:formatNumber value="${contractSingingRateItem.allContractMoney}" pattern="#,###.####"/>
				</display:column>
				<display:column title="总投资(万元)">
					<fmt:formatNumber value="${contractSingingRateItem.investmentContractMoney}" pattern="#,###.####"/>
				</display:column>
			    <display:column title="合同签订总数(份数)">${contractSingingRateItem.allContract}</display:column> 
				<%-- <display:column title="采购合同金额(万元)">${contractSingingRateItem.cgContractMoney}</display:column> --%>
				<%-- <display:column title="业务合同金额(万元)">${contractSingingRateItem.fcgContractMoney}</display:column> --%>
				<display:column title="合同归档(份数)">${contractSingingRateItem.allGd}</display:column>
				<%-- <display:column title="采购归档(份数)">${contractSingingRateItem.cgGd}</display:column> --%>
				<%-- <display:column title="业务归档(份数)">${contractSingingRateItem.fcgGd}</display:column> --%>
				<%-- <display:column title="待归档(份数)">${contractSingingRateItem.waitGd}</display:column> --%>
				<display:column title="合同归档率（%）">${contractSingingRateItem.allRate}</display:column>
				<%-- <display:column title="业务合同归档率（%）">${contractSingingRateItem.fcgRate}</display:column> --%>
			</display:table>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
	<script type="text/javascript" src="${ctx }/js/select2/select2.min.js"></script>
	<script type="text/javascript" >
	 $(function(){
		 $("#years").select2({
				placeholder : "请选择",
				allowClear : true
		 });
		 $("#orgIds").select2({
				placeholder : "请选择",
				allowClear : true
		 });
		 $("#cotps").select2({
				placeholder : "请选择",
				allowClear : true
		 });
		 
		 $("#tabStatics").change(function(){
			 var url = $("#tabStatics option:selected").data("url");
			 location.href = url;
		 });
	 });
	 </script>	
</body>
</html>


