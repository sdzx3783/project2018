
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>人才引进明细</title>
<%@include file="/commons/include/customForm.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/FlowDetailWindow.js"></script>
</head>
<body class="oa-mw-page">
<div class="oa-mg20">
	<table class="oa-table--default" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled"> 
	    <caption>人才引进详细信息</caption>
	    <tbody> 
		    <tr>
		    	<th>人才引进记录编号</th> 
		    	<td>${personIn.person_in_num}</td>
		    	<th>工号</th> 
		    	<td>${personIn.account}</td>
		    </tr>
		    <tr>
		    	<th>申请人</th> 
		    	<td>${personIn.applicant}</td>
		    	<th>申请时间</th> 
		    	<td><fmt:formatDate value='${personIn.application_time}' pattern='yyyy-MM-dd'/></td>
		    </tr>
		    <tr>
		    	<th>年龄</th> 
		    	<td>${personIn.age}</td>
		    	<th>身份证号</th> 
		    	<td>${personIn.id_number}</td>
		    </tr>
		    <tr>
		    	<th>全日制学历</th> 
		    	<td>
	    		    <c:if test="${personIn.edu=='0'}">研究生</c:if>
	    		    <c:if test="${personIn.edu=='1'}">本科</c:if>
	    		    <c:if test="${personIn.edu=='2'}">大专</c:if>
	    		    <c:if test="${personIn.edu=='3'}">其他</c:if>
		    	</td>
		    	<th>入职时间</th> 
		    	<td><fmt:formatDate value='${personIn.entryDate}' pattern='yyyy-MM-dd'/></td>
		    </tr>
		    <tr>
		    	<th>户口所属派出所</th> 
		    	<td>${personIn.police_station}</td>
		    	<th>户口类型</th> 
		    	<td>
		    		<c:if test="${personIn.residence_type=='0'}">非农</c:if>
	    		    <c:if test="${personIn.residence_type=='1'}">农业</c:if>
		    	</td>
		    </tr>    
		    <tr>
		    	<th>档案保管单位</th> 
		    	<td>${personIn.trust_unit}</td>
		    	<th>联系方式</th> 
		    	<td>${personIn.tel_number}</td>
		    </tr>
		    <tr>
		    	<th>房产情况</th> 
		    	<td>
		    		<c:if test="${personIn.house_status=='0'}">有</c:if>
	    		    <c:if test="${personIn.house_status=='1'}">无</c:if>
		    	</td>
		    	<th>落户地址</th> 
		    	<td>
		    		<c:if test="${personIn.settled_situation=='0'}">公司集体户</c:if>
	    		    <c:if test="${personIn.settled_situation=='1'}">个人/亲戚处</c:if>
		    	</td>
		    </tr>
		    <tr>
		    	<th>申请理由</th> 
		    	<td colspan="3">${personIn.appli_reason}</td>
		    </tr>
		    <tr>
		    	<th>备注</th> 
		    	<td colspan="3">${personIn.remark}</td>
		    </tr>
		    <tr>
		    	<th>附件</th> 
		    	<td colspan="3">
		    		<input  ctltype="attachment" right="r"  name="m:person_in:file" isdirectupload="0"  value='${personIn.file}' validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}"/>
		    	</td>
		    </tr>
	    </tbody> 
	</table>
</div>
</body>
</html>

