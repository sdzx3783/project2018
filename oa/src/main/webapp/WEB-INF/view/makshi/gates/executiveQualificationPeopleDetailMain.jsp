<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
    <title id="executiveTitle">注册会员汇总明细表</title>
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
		.form-body {margin-bottom: 20px;margin-left: 6px;}
	    .form-group{margin-right: 20px;}
	    .control-label{width:64px;}
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
		<c:if test="${type=='1' }">
	    	<h2 class='executivetitle'>在册人次明细表 (${year }年)</h2>
	    </c:if>
		<c:if test="${type=='2' }">
	    	<h2 class='executivetitle'>初始注册人次明细表 (${year }年)</h2>
	    </c:if>
		<c:if test="${type=='3' }">
	    	<h2 class='executivetitle'>转入注册人次明细表 (${year }年)</h2>
	    </c:if>
		<c:if test="${type=='4' }">
	    	<h2 class='executivetitle'>转出注册人次明细表 (${year }年)</h2>
	    </c:if>
		<c:if test="${type=='5' }">
	    	<h2 class='executivetitle'>上岗培训人次明细表 (${year }年)</h2>
	    </c:if>
	</div>
	<div  >
        <div class="col-md-12">
			<form class="form-inline" role="form" method="post" action="/makshi/gates/executive/qualificationPeopleDetail.ht" id="search-form">
				<input type="hidden" id="type" name='type' value="${type }">
				<input type="hidden" id="qualification" name='qualification' value="${qualification }">
				<%-- <input type="hidden" id="year" name='year' value="${year }"> --%>
				<input type="hidden" id="other" name='other' value="${other }">
		         <div class="form-body">
		         	<div class="form-group">
		               	<label class="control-label">年<label>&nbsp;</label>份</label>
		               	<select id="year" name="year" class="form-control" style="width: 89px !important;">
		               	    <c:forEach begin="1989" end="${cutYear }" var="e" >
			               		<option <c:if test="${(cutYear-e+1989)==year}">selected</c:if> >${cutYear-e+1989 }</option>
		               	    </c:forEach>
		               	</select>
		            </div>
		             <div class="form-group">
		               	<label class="control-label">员工编号</label>
		               	<input type="text" name="userNo" id="userNo" value="${userNo}"  class="form-control" />                           
		             </div>
		         	<div class="form-group">
		               	<label class="control-label">姓<label>&nbsp;</label>名</label>
		                <input type="text" name="userName" id="userName" value="${userName}"  class="form-control" />          
		             </div>
		         	<div class="form-group">
		               	<label class="control-label">证书编号</label>
		                <input type="text" name="certificateNo" id="certificateNo" value="${certificateNo}"  class="form-control" />          
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
	<script type="text/javascript" src="${ctx}/js/makshi/gates/executive/executiveQualificationPeopleDetailMain.js"></script>
</body>
</html>