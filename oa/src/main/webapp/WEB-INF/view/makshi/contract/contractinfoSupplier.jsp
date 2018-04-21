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
    .executivetitle{
   	font-size: 20px !important;
    color: #000;
    font-weight: 600 !important;
    }
    .seson.hidden{
        display:none;
    }
    .month.hidden{
        display:none;
    }

    /*checkCollapse*/
    .checkCollapse-container{
        display: none;
        position: absolute;
        left: -1px;
        z-index: 999;
        width: 500px;
            border: 1px solid #e7eaf1;
        background: #fff;
        padding: 25px 20px 20px 20px;
            box-shadow: 0 0 15px 0 rgba(0,37,55,.05);
    }
    .checkCollapse-close{
        cursor: pointer;
        position: absolute;
        top: 5px;
        right: 5px;
    }
</style>
<script type="text/javascript">
	window.onload=function(){ 
	//设置年份的选择 
	var myDate= new Date(); 
	var startYear="1998";//起始年份 
	var endYear=myDate.getFullYear();//结束年份 
	var obj=document.getElementById('myYear');
	var obje=document.getElementById('eYear');
	for (var i=endYear;i>=startYear;i--) 
	{ 
	obj.options.add(new Option(i,i)); 
	obje.options.add(new Option(i,i)); 
	} 
	var selectedSYear = '${syear}';
	var selectedEYear = '${eyear}';
	if(selectedSYear){
	obj.options[endYear-selectedSYear].selected=1;
	}
	if(selectedEYear){
	obje.options[endYear-selectedEYear].selected=1;
	}
	} 
</script>
<style>
.w30 {
	width: 30%;
}
</style>
</head>
<body class="oa-mw-page">

    <div id="oa_list_title" class="oa-mgb10 oa-project-title">
        <h2 class='executivetitle'>
            ${syear}<c:if test="${eyear!=null}">-${eyear}</c:if>年深水咨询主要供应商
        </h2>
    </div>
	<div class="col-md-12" style="padding: 20px">
		<div class="oa-fl oa-mgb10 oa-mgh20">
			<select id="tabStatics" name="tabStatics" class="form-control" style="display: inline-block;" >
				<option value="1" data-url="/makshi/contract/contractinfo/singingRate.ht">报表统计</option>
				<option value="2" data-url="/makshi/contract/contractinfo/customers.ht">深水咨询近年最大客户</option>
				<option value="3" selected="selected"  data-url="/makshi/contract/contractinfo/supplier.ht">深水主要供应商</option>
			</select>
        </div>
	</div>
    <div id="oa_list_search" class="oa-pdb10 oa-mgh20">
        <form id="searchForm" method="post" action="supplier.ht" class="oa-clear">
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">起始年份</div>
                <div class="oa-input-wrap oa-mgl100">
                    <select class="oa-select" id="myYear" name="startDate"></select> 
                </div>
            </div>

            <div class="oa-fl oa-mgb10">
                <div class="oa-label">结束年份</div>
                <div class="oa-input-wrap oa-mgl100">
                    <select class="oa-select" id="eYear" name="endDate"></select>
                </div>
            </div>

            <div class="oa-fl oa-mgb10 oa-mgh20">
                <button class="oa-button oa-button--primary oa-button--blue">查询</button>
            </div>
        </form>
    </div>
	
    <div class="oa-pdb20 oa-mgh20">
        <div id="oa_list_content" class="oa-table-scroll-wrap">
			<c:set var="startNum" value="${(pageBeangoodssupplierItem.currentPage-1)*pageBeansupplierItem.pageSize}"></c:set>
		    <display:table export="true" name="supplierlist" id="supplierItem" requestURI="#" sort="external" cellpadding="1" cellspacing="1"  class="oa-table--default oa-table--nowrap">
		   	<c:set var="startNum" value="${startNum+1}"></c:set>
				<display:column title="序号">${startNum}</display:column>
				<display:column title="供应商" class="w30"><span title='${supplierItem.ownerName}'>${supplierItem.ownerName}</span></display:column>
				<display:column title="采购额(万元)" class="w30">${supplierItem.contractMoney}</display:column>
				<display:column title="占比" class="w30">${supplierItem.rate}</display:column>
			</display:table>
		</div>			
	</div>
	<script type="text/javascript">
	 $(function(){
		 $("#tabStatics").change(function(){
			 var url = $("#tabStatics option:selected").data("url");
			 location.href = url;
		 });
	 });
	</script>	
</body>
</html>


