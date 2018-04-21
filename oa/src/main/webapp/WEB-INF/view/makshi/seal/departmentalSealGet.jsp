<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 部门公章使用流程</title>
	<%@include file="/codegen/include/customForm.jsp" %>
	<%@include file="/commons/include/ueditor.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/AttachMent.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/FormUtil.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery/plugins/jquery.attach.js" ></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/FlexUploadDialog.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/system/HtmlUploadDialog.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/form/TablePermission.js"></script> 
</head>
<body class="oa-mw-page" >
	<div class="oa-pd20">
    </div>
    <div class="oa-mg20">
		<form id="departmentalSealForm" method="post" action="save.ht">
			<table class="oa-table--default" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
		         <caption>部门公章使用详情</caption>
				 <tbody>
				  <tr class="firstRow">
				   <td>工号:</td>
				   <td>${departmentalSeal.job_id }</td>
				   <td>申请人:</td>
			       <td>${departmentalSeal.applyer }</td>
				  </tr>
				  <tr>
				   <td>类型:</td>
				   <td><c:if test="${departmentalSeal.type == '1'}">公章</c:if></td>
				   <td>使用时间:</td>
				   <td><fmt:formatDate value='${departmentalSeal.use_time}' pattern='yyyy-MM-dd'/></td>
				  </tr>
				  <tr>
				   <td>合同编号:</td>
				   <td>${departmentalSeal.contract_id }</td>
				   <td>合同名称:</td>
				   <td>${departmentalSeal.contract_name }</td>
				  </tr>
				  <tr>
				   <td>材料内容:</td>
				   <td rowspan="1" colspan="3">${departmentalSeal.material_content }</td>
				  </tr>
				  <tr>
				   <td>份数:</td>
				   <td>${departmentalSeal.copies }</td>
				   <td>附件:</td>
				   <td>
					   	<div name="div_attachment_container" right="r"> 
					     	<div class="attachement"></div> 
					    	 <textarea style="display:none" controltype="attachment" name="attachment" lablename="附件" readonly="readonly">${departmentalSeal.attachment}</textarea> 
					    </div>
				   </td>
				  </tr>
				  <tr>
				   <td>项目负责人:</td>
				   <td rowspan="1" colspan="3">${departmentalSeal.project_leader }</td>
				  </tr>
				 </tbody>
			</table>
		</form>
	</div>
</body>
</html>
