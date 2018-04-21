<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>集体荣誉</title>
	<%@include file="/codegen/include/customForm.jsp" %>
	<%@include file="/commons/include/ueditor.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/form/AttachMent.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/form/TablePermission.js"></script>
</head>
<body class="oa-mw-page">
    <div class="oa-mg20">
    	<form id="groupHonorForm" method="post" action="save.ht">
    		<table class="oa-table--default" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
			        <caption>集体荣誉</caption>
			  <tr>
			   <th>荣誉编号</th>
			   <td>${groupHonor.honor_num}</td>
			   <th>奖项名称</th>
			   <td>${groupHonor.honor_name}</td>
			  </tr>
			  <tr>
			   <th>奖项级别</th>
			   <td>${groupHonor.honor_level}</td>
			   <th>获奖项目</th>
			   <td>${groupHonor.award_project}</td>
			  </tr>
			  <tr>
			   <th>发证机构</th>
			   <td>${groupHonor.issuing_authority}</td>
			   <th>发证时间</th>
			   <td>${groupHonor.issuing_date}</td>
			  </tr>
			  <tr>
			   <th>颁发形式</th>
			   <td>${groupHonor.get_type}</td>
			   <th></th>
			   <td></td>
			  </tr>
			  <tr>
			   <th>附件</th>
			   <td rowspan="1" colspan="3">
			   	 <input  ctltype="attachment" right="r"  name="m:group_honor:file" isdirectupload="0"  value='${groupHonor.file}' validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}" />
			   </td>
			  </tr>
    		</table>
    	</form>
    </div>
</body>
</html>
