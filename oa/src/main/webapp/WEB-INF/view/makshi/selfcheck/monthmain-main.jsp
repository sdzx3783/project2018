<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
	<title id="executiveTitle">工作自查月报列表</title>
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
	    <h2 class='executivetitle'>工作自查月报列表</h2>
	</div>
	<div>
        <div class="col-md-12">
        	<a class="oa-button oa-button--primary oa-button--blue" href="/makshi/selfcheck/monthmain/edit.ht" >添加</a>
			<form class="form-inline" role="form" method="post" action="/makshi/selfcheck/monthmain/main.ht" id="search-form">
		         <div class="form-body">
		             <div class="form-group">
		               	<label class="control-label">时间</label>
		               	<input type="text" name="submitAt" id="submitAt" value="${submitAt}"  class="form-control Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM',maxDate:'%y-%M'})" />                           
		             </div>
	                <div class="form-group">
		               	<label class="control-label">状态</label>
		               	<select id="status" name="status" class="form-control">
		               	   <option value="">请选择</option>
		                   <option value="1" <c:if test="${status=='1' }">selected</c:if> >未提交</option>
		                   <option value="2" <c:if test="${status=='2' }">selected</c:if> >已提交</option>
		               	</select>       
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
	<script type="text/javascript" src="${ctx}/js/makshi/selfcheck/monthmain.js"></script>
</body>
</html>