<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>公司资质证书</title>
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
			 <span class="tbar-label"><span></span>公司资质证书</span>
		</div>
	</div>
	<form id="companyQualificationCertificateForm" method="post" action="save.ht">
		<div type="custform">
			<div class="panel-detail">
				<table class="formTable" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
 <tbody>
  <tr class="firstRow">
   <td class="formHead" colspan="4" style="word-break: break-all;"><span style="font-size:14px;line-height:125%;font-family:宋体">公司资质证书</span></td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">证书编号:</td>
   <td style="width: 35%" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${companyQualificationCertificate.cno}</span> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">证书类型:</td>
   <td style="width: 35%" class="formInput"> 
   	<span name="editable-input" style="display:inline-block;padding:2px;" class="selectinput"> 
   		<c:if test="${companyQualificationCertificate.ctype==1}">企业资质</c:if> 
   		<c:if test="${companyQualificationCertificate.ctype==2}">施工监理</c:if> 
   		<c:if test="${companyQualificationCertificate.ctype==3}">工程咨询</c:if> 
   		<c:if test="${companyQualificationCertificate.ctype==4}">招标代理</c:if> 
   		<c:if test="${companyQualificationCertificate.ctype==5}">造价咨询</c:if> 
   		<c:if test="${companyQualificationCertificate.ctype==6}">水土保持</c:if> 
   		<c:if test="${companyQualificationCertificate.ctype==7}">污水运营、环境</c:if> 
   		<c:if test="${companyQualificationCertificate.ctype==8}">信息</c:if> 
   		<c:if test="${companyQualificationCertificate.ctype==9}">测绘</c:if> 
   		<c:if test="${companyQualificationCertificate.ctype==10}">施工</c:if>
   	</span> 
   </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">证书名称:</td>
   <td style="width: 35%; word-break: break-all;" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag">${companyQualificationCertificate.cname}</span> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">发证机构:</td>
   <td style="width: 35%" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag">${companyQualificationCertificate.institution}</span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">发证时间:</td>
   <td style="width: 35%; word-break: break-all;" class="formInput"><fmt:formatDate value='${companyQualificationCertificate.certificationtime}' pattern='yyyy-MM-dd'/> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">有效期限:</td>
   <td style="width: 35%" class="formInput"><fmt:formatDate value='${companyQualificationCertificate.certificationlimit}' pattern='yyyy-MM-dd'/> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">证书状态:</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="1"> 
		<c:if test="${companyQualificationCertificate.status==0}">在用</c:if>
    	<c:if test="${companyQualificationCertificate.status==1}">过期</c:if>
    	<c:if test="${companyQualificationCertificate.status==2}">废弃</c:if>
   </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">借用状态:</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="1"> 
   		<c:if test="${companyQualificationCertificate.isborrowed==0}">未借出</c:if>
    	<c:if test="${companyQualificationCertificate.isborrowed==1}">已借出</c:if>
   </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">承包范围:</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"> <span name="editable-input" style="display:inline-block;" isflag="tableflag">${companyQualificationCertificate.contractscope}</span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">备注:</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"> <span name="editable-input" style="display:inline-block;" isflag="tableflag">${companyQualificationCertificate.remark}</span> </td>
  </tr>
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">上传原件扫描:</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3">
		<input  ctltype="attachment" right="r"  name="m:company_qualification_certificate:attachment" isdirectupload="0"  value='${companyQualificationCertificate.attachment}' validatable="false" validate="{&quot;maxlength&quot;:&quot;1000&quot;}"/> 
   </td>
   </td> 
  </tr> 
  
 </tbody>
</table>
			</div>
		</div>
		<input type="hidden" id="saveData" name="saveData"/>
		<input type="hidden" name="executeType"  value="start" />
	</form>
</body>
</html>
