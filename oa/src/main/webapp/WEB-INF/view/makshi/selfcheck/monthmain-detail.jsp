<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
    <title id="executiveTitle">工作自查月报</title>
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
    .control-label{width:64px;}
    .age{}
    .form-control{width:180px !important;}
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
    .td-button{margin-left: 9px !important;}
    .panel-toolbar a.link{height: 29px !important;}
    .tbar-title{
    	background:none  !important;
    	border:0px  !important;
    	display:inline-block  !important;
    }
    .montherror{
		border-color: #b94a48 !important;
	    -webkit-box-shadow: none;
	    box-shadow: none;
	}
	</style>
</head>
<body>
	<div class="panel-top">
		<div class="panel-toolbar">
			<div class="toolBar">
				<div class="group"><a class="link back " href="/makshi/selfcheck/monthmain/main.ht"><span></span>返回</a></div>
			</div>
		</div>
	</div>
	<div id="oa_list_title" class="oa-mgb20 oa-project-title">
	    <h2 class='executivetitle'>
			 <span class="tbar-label"><span></span>查看工作自查月报</span>
	    </h2>
	</div>
	<div class="oa-pdb20 oa-mgh20">
		<div id="oa_list_content" class="oa-table-scroll-wrap">	
			<div id="result_field">
				<table id="tb_common_show" class="table table-hover table-striped JCLRFlex JColResizer">
					<thead>
						<tr>
						    <td style="text-align: left;" colspan="3">月报时间: ${dto.submitAt }</th>
						</tr>
						<tr>
						   <th style="text-align: center;">内容</th>
						   <th style="text-align: center;">要求</th>
						   <th style="text-align: center;">本月自查</th>
						</tr>
					</thead>
					 <tbody>
					 	<c:choose>
							<c:when test="${empty items }">
								 <tr><td colspan="3">无记录...</td></tr>
							</c:when>
							<c:otherwise>
								<c:forEach items="${items }" var="e" varStatus="v">
									<tr class="monthtr">
									  <td><textarea style="width: 98%;" disabled="disabled" class="monthcontent" rows="3">${e.content }</textarea></td>
									  <td><textarea style="width: 98%;" disabled="disabled" class="monthrequire" rows="3">${e.requires }</textarea></td>
									  <td><textarea style="width: 98%;" disabled="disabled" class="monthchecks" rows="3">${e.checks }</textarea></td>
									</tr>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					 </tbody>
				</table>
			</div>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
	<input type="hidden" name="mainid" id="mainid" value="${dto.id}"/>
	<script type="text/javascript">
		$("#executiveTitle").text($(".executivetitle").text());
	</script>
</body>
</html>
