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
   <td class="formHead" colspan="4" style="word-break: break-all;">人员调动表</td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">员工姓名:</td>
   <td style="width: 35%" class="formInput"></td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">调出部门:</td>
   <td style="width: 35%" class="formInput"></td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">员工编号:</td>
   <td style="width: 35%" class="formInput"></td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">短号:</td>
   <td style="width: 35%" class="formInput"></td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">调整月份<br /></td>
   <td style="width: 35%" class="formInput"></td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">职称<br /></td>
   <td style="width: 35%" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:department_transfer:professional" lablename="职称" class="inputText" value="${departmentTransfer.professional}" validate="{maxlength:50,null:true}" isflag="tableflag" /> </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">调整原因<br /></td>
   <td style="width: 35%" class="formInput" rowspan="1" colspan="3"><br /></td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">调入部门<br /></td>
   <td class="formInput" colspan="3" rowspan="1" style="word-break: break-all;"><br /></td>
  </tr>
  <tr>
   <td class="formInput" rowspan="1" colspan="4" style="word-break: break-all;">调整前基本情况</td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">岗位<br /></td>
   <td class="formInput" colspan="1" rowspan="1"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:department_transfer:post" lablename="岗位" class="inputText" value="${departmentTransfer.post}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">岗位工资<br /></td>
   <td class="formInput" colspan="1" rowspan="1"></td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">基本工资<br /></td>
   <td class="formInput" colspan="1" rowspan="1"></td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">通讯费标准<br /></td>
   <td class="formInput" colspan="1" rowspan="1"></td>
  </tr>
  <tr>
   <td class="formInput" colspan="4" rowspan="1" style="word-break: break-all;">调整后基本情况</td>
  </tr>
  <tr>
   <td tyle="width: 15%; word-break: break-all;" class="formTitle" align="right">岗位<br /></td>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all;"><br /></td>
   <td tyle="width: 15%; word-break: break-all;" class="formTitle" align="right">岗位工资<br /></td>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all;"></td>
  </tr>
  <tr>
   <td tyle="width: 15%; word-break: break-all;" class="formTitle" align="right">基本工资<br /></td>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all;"></td>
   <td tyle="width: 15%; word-break: break-all;" class="formTitle" align="right">通讯费标准<br /></td>
   <td class="formInput" colspan="1" rowspan="1"></td>
  </tr>
  <tr>
   <td class="formInput" colspan="4" rowspan="1" style="word-break: break-all;">员工本人意见</td>
  </tr>
  <tr>
   <td tyle="width: 15%; word-break: break-all;" class="formTitle" align="right" style="word-break: break-all;">本人意见</td>
   <td class="formInput" colspan="3" rowspan="1"><br /></td>
  </tr>
 </tbody>
</table>
