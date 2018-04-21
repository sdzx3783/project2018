<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>合同签订明细表</title>
	<%@include file="/commons/include/list_get.jsp"%>
	<f:link href="Aqua/css/ligerui-all.css" ></f:link>
    <f:link href="tree/zTreeStyle.css" ></f:link>
	<link href="${ctx}/js/pagination/pagination.css" rel="stylesheet" />
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
	<style type="text/css">
		.form-body {
    margin-bottom: 20px;
    margin-left: 6px;}
    .form-group{margin-right: 20px;}
    .control-label{width:64px;}
    .age{}
    .form-control{width:180px !important;}
    .control-label label{min-width:23px;}
    table tr:first-child th {
    font-size: 14px;
    color: #000;    
    font-weight: 600;
    }
    .executivetitle{
   	font-size: 20px !important;
    color: #000;
    font-weight: 600 !important;
    }
	</style>
</head>
<body>
	<div id="oa_list_title" class="oa-mgb20 oa-project-title">
	    <h2 class='executivetitle'>合同签订明细表</h2>
	</div>
	<div  >
        <div class="col-md-12">
			<form class="form-inline" role="form" method="post" action="/makshi/gates/executive/detail/contract.ht" id="search-form">
				<input type="hidden" id="querytype" name='querytype' value="${querytype }">
				<input type="hidden" id="querygo" name='querygo' value="${querygo }">
		         <div class="form-body">
		         	<div class="form-group">
		               	<label class="control-label">合同类型</label>
		               	<select id="contracttype" name="contracttype" validate="{required:true}" class="form-control"></select>
     					<select id="contracttype1" name="contracttype1" style="display: none;" class="form-control"></select>
     					
     					<input id="contractTypeText" type="hidden"   value="${contracttype}"   />
	   					<input id="contractTypeText1" type="hidden"  value="${contracttype1}"   />
		   			
		             </div>
		             
		             <div class="form-group">
		               	<label class="control-label">合同编号</label>
		               	<input type="text" name="contract_num" id="contract_num" value="${contract_num}"  class="form-control" />                           
		             </div>
		         	<div class="form-group">
		               	<label class="control-label">合同名称</label>
		                <input type="text" name="contract_name" id="contract_name" value="${contract_name}"  class="form-control" />          
		             </div>
		         	<div class="form-group">
		               	<label class="control-label">合同状态</label>
		                <select id="contract_status" name='contract_status' class='form-control'>
		                  <option value="">请选择</option>
		                  <option value="1" <c:if test="${contract_status=='1' }">selected</c:if> >作废</option>
		                  <option value="0" <c:if test="${contract_status=='0' }">selected</c:if> >正式合同</option>
		                </select>
		             </div>
		        </div>
		        <div class="form-body">
		        	<div class="form-group">
		               	<label class="control-label">甲<label>&nbsp;</label>方</label>
		               	<input type="text" name="first_party" id="first_party" value="${first_party}"  class="form-control" />                           
		            </div>
		        	<div class="form-group">
		               	<label class="control-label">合同归属部门</label>
		               	<select id="orgId" name='orgId' class='form-control'>
		               		 <option value="">请选择</option>
		               		 <c:forEach items="${parts }" var="e">
		               		 	<option value="${e.orgId }" <c:if test="${orgId==e.orgId }">selected</c:if>>${e.orgName }</option>
		               		 </c:forEach>
						</select>                       
		            </div>
		        	<div class="form-group">
		               	<label class="control-label">项&nbsp;目&nbsp;部</label>
		               	<input type="text" name="project_department" id="project_department" value="${project_department }"  class="form-control" />                           
		            </div>
		        	<div class="form-group">
		               	<label class="control-label">合同经手人</label>
		               	<input type="text" name="contract_handler" id="contract_handler" value="${contract_handler }"  class="form-control" />                           
		            </div>
		        </div>
		        <div class="form-body"> 
		         	<div class="form-group">
		               	<label class="control-label">签约时间</label>
		               	<input onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'singingEnd\')}'})" type="text" name="singingStart" id="singingStart" value="${singingStart}" class="form-control Wdate" />
		               		至
		               	<input onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" type="text" name="singingEnd" id="singingEnd" value="${singingEnd}" class="form-control Wdate" />
		            </div>
		            <div class="form-group">
		               	<label class="control-label">合同金额（万元）</label>
		                <input type="text" name="moneyMin" value="${moneyMin}" id="moneyMin" onkeyup="this.value=this.value.replace(/[^\d]/g,'') "  class="form-control age" style="width: 200px !important;" />
		                	至
		                <input type="text" name="moneyMax" value="${moneyMax}" id="moneyMax" onkeyup="this.value=this.value.replace(/[^\d]/g,'') "  class="form-control age" style="width: 200px !important;"/>
		            </div>
		            <!--  href="javascript:loadInitPageData();" -->
		            <button class="oa-button oa-button--primary oa-button--blue">查询</button>
		         </div>
		     </form>
		 </div>
    </div>
	<div class="oa-pdb20 oa-mgh20">
		<div id="oa_list_content" class="oa-table-scroll-wrap">	
			<div id="result_field"></div>
	    	<div class="pagination" id="Pagination" style="margin: -2px 0;"></div>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
	<script type="text/javascript" src="${ctx}/js/pagination/jquery.pagination.js"></script>
	<script type="text/javascript" src="${ctx}/js/pagination/pagination-common.js"></script>
	<script type="text/javascript" src="${ctx}/js/makshi/gates/executive/executiveDetailContractMain.js"></script>
</body>
</html>