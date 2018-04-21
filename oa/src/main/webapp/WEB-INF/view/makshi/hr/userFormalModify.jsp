<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>

<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${ctx}/js/hotent/scriptMgr.js"></script>
<script type="text/javascript">
	function afterOnload(){
		var afterLoadJs=[
		     			'${ctx}/js/hotent/formdata.js',
		     			'${ctx}/js/hotent/subform.js'
		 ];
		ScriptMgr.load({
			scripts : afterLoadJs
		});
	}
</script>
<table class="formTable" parser="addpermission" data-sort="sortDisabled" cellspacing="0" cellpadding="2" border="1">
 <tbody>
  <tr class="firstRow">
   <td class="formHead" colspan="4" style="word-break: break-all;">员工转正表</td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" width="0" align="right">申请人:</td>
   <td style="width: 35%" class="formInput" id="fullname" width="467"><br /></td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">劳动合同约定转正时间:</td>
   <td style="width: 35%" class="formInput"></td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" width="0" align="right">员工编号:</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" id="userId_procedure" width="467"></td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">职称:</td>
   <td style="width: 35%" class="formInput" id="positional"><br /></td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" width="0" align="right">入职日期:</td>
   <td class="formInput" colspan="1" rowspan="1" id="entrydate" width="467"><br /></td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">专业:</td>
   <td class="formInput" colspan="1" rowspan="1" id="major"><br /></td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" width="0" align="right">试用期所在部门及项目部:</td>
   <td class="formInput" colspan="1" rowspan="1" id="orgpathname" width="467"><br /></td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">学历:</td>
   <td class="formInput" colspan="1" rowspan="1" id="education"><br /></td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" width="0" align="right">岗位工资</td>
   <td class="formInput" colspan="3" rowspan="1"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:user_formal:salary" lablename="工资" class="inputText" value="${userFormal.salary}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
  </tr>
 </tbody>
</table>
<table class="formTable" parser="addpermission" cellspacing="0" cellpadding="2" border="1">
 <tbody id="qualification">
  <tr class="firstRow"></tr>
 </tbody>
</table>
