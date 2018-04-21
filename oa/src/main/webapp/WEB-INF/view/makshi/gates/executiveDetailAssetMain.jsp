<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
	<title id="executiveTitle">资产分布明细表</title>
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
	    	<h2 class='executivetitle'>资产分布明细表</h2>
	</div>
	<div  >
        <div class="col-md-12">
			<form class="form-inline" role="form" method="post" action="/makshi/gates/executive/detail/asset.ht" id="search-form">
				<input name="fixed" value="${fixed }" type="hidden">
		         <div class="form-body">
		         	<div class="form-group"  <c:if test="${fixed}"> style='display:none;' </c:if> >
		               	<label class="control-label">使用部门</label>
		               	<select id="orgId" name='orgId' class='form-control' >
		               		 <option value="">请选择</option>
		               		 <c:forEach items="${parts }" var="e">
		               		 	<option value="${e.orgId }" <c:if test="${orgId==e.orgId }">selected</c:if>>${e.orgName }</option>
		               		 </c:forEach>
						</select>                       
		            </div>
		            <div class="form-group">
		               	<label class="control-label">资产分类</label>
		               	<select id="assetCode" name='assetCode' class='form-control'>
		               		 <option value="">请选择</option>
		               		 <option value="台式主机" <c:if test="${'台式电脑'==assetCode || '台式主机'==assetCode }">selected</c:if>>台式电脑</option>
		               		 <option value="笔记本电脑" <c:if test="${'笔记本电脑'==assetCode }">selected</c:if>>笔记本电脑</option>
		               		 <option value="打印机" <c:if test="${'打印机'==assetCode }">selected</c:if>>打印机</option>
		               		 <%-- <option value="租房" <c:if test="${'租房'==assetCode }">selected</c:if>>租房</option>
		               		 <option value="车辆" <c:if test="${'车辆'==assetCode }">selected</c:if>>车辆</option> --%>
						</select>                       
		            </div>
		             <div class="form-group">
		               	<label class="control-label">资产编号</label>
		               	<input type="text" name="assetId" id="assetId" value="${assetId}"  class="form-control" />                           
		             </div>
		            </div>
		            <div class="form-body">
		             <div class="form-group">
		               	<label class="control-label">资产名称</label>
		               	<input type="text" name="assetName" id="assetName" value="${assetName}"  class="form-control" />                           
		             </div>
		         	<div class="form-group">
		               	<label class="control-label">保管人</label>
		                <input type="text" name="carePerson" id="carePerson" value="${carePerson}"  class="form-control" />          
		             </div>
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
	<script type="text/javascript">
		$("#executiveTitle").text($(".executivetitle").text());
	</script>
	<script type="text/javascript" src="${ctx}/js/pagination/jquery.pagination.js"></script>
	<script type="text/javascript" src="${ctx}/js/pagination/pagination-common.js"></script>
	<script type="text/javascript" src="${ctx}/js/makshi/gates/executive/executiveDetailAssetMain.js"></script>
</body>
</html>