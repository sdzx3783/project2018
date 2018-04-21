<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>

<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="/commons/include/customForm.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/scriptMgr.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
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
<table class="formTable" border="1" cellspacing="0" cellpadding="2" parser="addpermission">
 <tbody>
  <tr class="firstRow">
   <td class="formHead" colspan="2">主表</td>
  </tr>
  <tr>
   <td style="width: 20%; word-break: break-all;" class="formTitle" align="right">姓名:</td>
   <td style="width: 80%" class="formInput"><br /> ${workLevel.fullname} </td>
  </tr>
  <tr>
   <td style="width: 20%; word-break: break-all;" class="formTitle" align="right">部门:</td>
   <td style="width: 80%" class="formInput"><br /> ${workLevel.department} </td>
  </tr>
  <tr>
   <td style="width: 20%; word-break: break-all;" class="formTitle" align="right">职务:</td>
   <td style="width: 80%" class="formInput"> ${workLevel.position} </td>
  </tr>
  <tr>
   <td style="width: 20%; word-break: break-all;" class="formTitle" align="right">类型:</td>
   <td style="width: 80%" class="formInput"><br /> ${workLevel.type} </td>
  </tr>
  <tr>
   <td style="width: 20%; word-break: break-all;" class="formTitle" align="right">缺勤时间:</td>
   <td style="width: 80%; word-break: break-all;" class="formInput"><br /></td>
  </tr>
  <tr>
   <td style="width: 20%; word-break: break-all;" class="formTitle" align="right">登记时间:</td>
   <td style="width: 80%" class="formInput"></td>
  </tr>
  <tr>
   <td style="width: 20%; word-break: break-all;" class="formTitle" align="right">备注:</td>
   <td style="width: 80%" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${workLevel.remark} </span> </td>
  </tr>
 </tbody>
</table>
