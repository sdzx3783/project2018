<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑单位会员汇总表</title>
	<%@include file="/codegen/include/customForm.jsp" %>
	<%@include file="/commons/include/ueditor.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/AttachMent.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/TablePermission.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	 
</head>
<body>
<div class="panel" style="height:100%;overflow:auto;">
	<div class="panel-top">
		<div class="tbar-title">
			        <span class="tbar-label"><span></span>单位会员汇总表</span>
		</div>
		<div class="panel-toolbar">
			<div class="toolBar">
				<div class="group"><a class="link back" href="list.ht"><span></span>返回</a></div>
			</div>
		</div>
	</div>
	<form id="summaryCompanyVipForm" method="post" action="save.ht">
		<div type="custform">
			<div class="panel-detail">
				<table class="oa-table--default" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
					 <caption>单位会员汇总表</caption>
					 <tbody>
						  <tr>
						   <th style="width: 15%;">发证机构:</th>
						   <td style="width: 35%"><span name="editable-input" style="display:inline-block;" isflag="tableflag">
						   ${summaryCompanyVip.organization}</span> </td>
						   <th style="width: 15%;">级别:</th>
						   <td style="width: 35%;"><span name="editable-input" style="display:inline-block;" isflag="tableflag">${summaryCompanyVip.level}</span> </td>
						  </tr>
						  <tr>
						   <th style="width: 15%;">所属部门:</th>
						   <td style="width: 35%"><span name="editable-input" style="display:inline-block;" isflag="tableflag"> 
						    ${summaryCompanyVip.department} </span> </td>
						   <th style="width: 15%;">董事长协会职务:</th>
						   <td style="width: 35%"><span name="editable-input" style="display:inline-block;" isflag="tableflag"> 
						    ${summaryCompanyVip.duties} </span> 
						   </td>
						  </tr>
						  <tr>
						   <th style="width: 15%;">证书编号:</th>
						   <td style="width: 35%"><span name="editable-input" style="display:inline-block;" isflag="tableflag"> 
						   ${summaryCompanyVip.certificateNo}</span></td>
						   <th style="width: 15%;">首次入会时间:</th>
						   <td style="width: 35%"><span name="editable-input" style="display:inline-block;" isflag="tableflag"> 
						    ${summaryCompanyVip.membershipTime} </span> 
						   </td>
						  </tr>
						  <tr>
						   <th style="width: 15%;">缴费标准(元):</th>
						   <td style="width: 35%"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> 
						    ${summaryCompanyVip.paymentStandard} </span> </td>
						   <th style="width: 15%;">会费已缴纳年度:</th>
						   <td style="width: 35%"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> 
						    ${summaryCompanyVip.vipYears} </span> </td>
						  </tr>
						  <tr>
						   <th style="width: 15%;">发证日期:</th>
						   <td style="width: 35%;"> 
						    ${summaryCompanyVip.certificationDate} </td>
						   <th style="width: 15%;">有效日期:</th>
						   <td style="width: 35%">${summaryCompanyVip.certeffectiveTime}</td>
						  </tr>
						  <tr>
						   <th style="width: 15%;">联系人:</th>
						   <td style="width: 35%"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> 
						    ${summaryCompanyVip.contactser} </span> </td>
						   <th style="width: 15%;">联系电话:</th>
						   <td style="width: 35%"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> 
						    ${summaryCompanyVip.contactsPhone} </span> 
						   </td>
						  </tr>
						  <tr>
						   <th style="width: 15%;">备注:</th>
						   <td style="width: 35%;" rowspan="1" colspan="3"> <span name="editable-input" style="display:inline-block;" isflag="tableflag">${summaryCompanyVip.remark}</span> </td>
						  </tr>
					 </tbody>
				</table>
			</div>
		</div>
	</form>
</body>
</html>
