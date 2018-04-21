<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>流程定义列表</title>
<%@include file="/codegen/include/customForm.jsp" %>
	<%@include file="/commons/include/ueditor.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
</head>
<style>
	form {
		padding: 20px;
	}
	.oa-new-select, .oa-new-input {
		width: 150px;
	}
	.oa-table--default th, .oa-table--default td {
		padding: 10px;
	}
	a.link.org.oa-new-input {
		display: inline;
	}
	#birthForm label {
		margin-right: 10px;
		display: block;
		float: left;
		cursor: pointer;
	}
	#birthForm label input {
		vertical-align: middle;
	}
</style>
<body>
	<form id="birthForm" action="/platform/system/sysUser/exportBirthExcel.ht">
		<table class="oa-table--default oa-table--second" cellspacing="0" cellpadding="2" border="1">
			<tr>
			    <th>在职状态</th>
			    <td>
					<label><input name="Q_userstatus_S" type="checkbox" value="正式员工" <c:if test="${param['Q_userstatus_S']=='正式员工'}">checked</c:if> />正式员工 </label>
					<label><input name="Q_userstatus_S" type="checkbox" value="试用员工" <c:if test="${param['Q_userstatus_S']=='试用员工'}">checked</c:if> />试用员工 </label>
					<label><input name="Q_userstatus_S" type="checkbox" value="返聘" <c:if test="${param['Q_userstatus_S']=='返聘'}">checked</c:if> />返聘 </label>
					<label><input name="Q_userstatus_S" type="checkbox" value="外聘" <c:if test="${param['Q_userstatus_S']=='外聘 '}">checked</c:if> />外聘 </label>
					<label><input name="Q_userstatus_S" type="checkbox" value="实习" <c:if test="${param['Q_userstatus_S']=='实习'}">checked</c:if> />实习 </label>
					<label><input name="Q_userstatus_S" type="checkbox" value="离职" <c:if test="${param['Q_userstatus_S']=='离职'}">checked</c:if> />离职 </label>
			    	<%-- <select name="Q_userstatus_S" class="oa-select oa-new-select"
					value="${param['Q_userstatus_S']}">
						<option value="">选择</option>
						<option value="正式员工"
							<c:if test="${param['Q_userstatus_S']=='正式员工'}">selected</c:if>>正式员工</option>
						<option value="试用员工"
							<c:if test="${param['Q_userstatus_S']=='试用员工'}">selected</c:if>>试用员工</option>
						<option value="返聘"
							<c:if test="${param['Q_userstatus_S']=='返聘'}">selected</c:if>>返聘</option>
						<option value="外聘"
							<c:if test="${param['Q_userstatus_S']=='外聘'}">selected</c:if>>外聘</option>
						<option value="实习"
							<c:if test="${param['Q_userstatus_S']=='实习'}">selected</c:if>>实习</option>
						<option value="离职"
							<c:if test="${param['Q_userstatus_S']=='离职'}">selected</c:if>>离职</option>
					</select> --%>
			    </td>
			</tr>
			<tr>
			    <th>生日月份</th>
			    <td>
			    	<label><input name="monbirth" type="checkbox" value="01" <c:if test="${param['monbirth']=='01'}">checked</c:if> />一月</label>
			    	<label><input name="monbirth" type="checkbox" value="02" <c:if test="${param['monbirth']=='02'}">checked</c:if> />二月</label>
			    	<label><input name="monbirth" type="checkbox" value="03" <c:if test="${param['monbirth']=='03'}">checked</c:if> />三月</label>
			    	<label><input name="monbirth" type="checkbox" value="04" <c:if test="${param['monbirth']=='04'}">checked</c:if> />四月</label>
			    	<label><input name="monbirth" type="checkbox" value="05" <c:if test="${param['monbirth']=='05'}">checked</c:if> />五月</label>
			    	<label><input name="monbirth" type="checkbox" value="06" <c:if test="${param['monbirth']=='06'}">checked</c:if> />六月</label>
			    	<label><input name="monbirth" type="checkbox" value="07" <c:if test="${param['monbirth']=='07'}">checked</c:if> />七月</label>
			    	<label><input name="monbirth" type="checkbox" value="08" <c:if test="${param['monbirth']=='08'}">checked</c:if> />八月</label>
			    	<label><input name="monbirth" type="checkbox" value="09" <c:if test="${param['monbirth']=='09'}">checked</c:if> />九月</label>
			    	<label><input name="monbirth" type="checkbox" value="10" <c:if test="${param['monbirth']=='10'}">checked</c:if> />十月</label>
			    	<label><input name="monbirth" type="checkbox" value="11" <c:if test="${param['monbirth']=='11'}">checked</c:if> />十一月</label>
			    	<label><input name="monbirth" type="checkbox" value="12" <c:if test="${param['monbirth']=='12'}">checked</c:if> />十二月</label>
			    	<%-- <select name="monbirth" class="oa-select oa-new-select"
					value="${param['monbirth']}">
						<option value="">-请选择-</option>
						<option value="01"
							<c:if test="${param['monbirth']=='01'}">selected</c:if>>一月</option>
						<option value="02"
							<c:if test="${param['monbirth']=='02'}">selected</c:if>>二月</option>
						<option value="03"
							<c:if test="${param['monbirth']=='03'}">selected</c:if>>三月</option>
						<option value="04"
							<c:if test="${param['monbirth']=='04'}">selected</c:if>>四月</option>
						<option value="05"
							<c:if test="${param['monbirth']=='05'}">selected</c:if>>五月</option>
						<option value="06"
							<c:if test="${param['monbirth']=='06'}">selected</c:if>>六月</option>
						<option value="07"
							<c:if test="${param['monbirth']=='07'}">selected</c:if>>七月</option>
						<option value="08"
							<c:if test="${param['monbirth']=='08'}">selected</c:if>>八月</option>
						<option value="09"
							<c:if test="${param['monbirth']=='09'}">selected</c:if>>九月</option>
						<option value="10"
							<c:if test="${param['monbirth']=='10'}">selected</c:if>>十月</option>
						<option value="11"
							<c:if test="${param['monbirth']=='11'}">selected</c:if>>十一月</option>
						<option value="12"
							<c:if test="${param['monbirth']=='12'}">selected</c:if>>十二月</option>
					</select> --%>
			    </td>
			</tr>
			<tr>
				<th>所属部门</th>
				<td>
					<input type="text" name="org" ctltype="selector" class="common-choose org oa-new-input" value="${param['org']}" intValue="${param['orgID']}" validate="{empty:false}" readonly="readonly" />
				</td>
			</tr>
			<tr>
				<th>性<span style="padding:0 1em;"></span>别</th>
				<td>
					<select name="Q_sex_S" class="oa-select oa-new-select"
					value="${param['Q_sex_S']}">
						<option value="">选择</option>
						<option value="1"
							<c:if test="${param['Q_sex_S']=='1'}">selected</c:if>>男</option>
						<option value="0"
							<c:if test="${param['Q_sex_S']=='0'}">selected</c:if>>女</option>
					</select>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>