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
   <td class="formHead" colspan="4" style="word-break: break-all;"><span style="font-size: 12px;">离职表</span></td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">员工姓名<br /></td>
   <td style="width: 35%; word-break: break-all;" class="formInput" id="fullname"></td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">所在部门</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" id="orgpathname"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:user_resignation:department" lablename="所在部门" class="inputText" value="${userResignation.department}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">员工编号<br /></td>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all;" id="userId_procedure"></td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">新手机号<br /></td>
   <td class="formInput" colspan="1" rowspan="1"></td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">入职时间<br /></td>
   <td style="width: 35%; word-break: break-all;" class="formInput" id="entrydate"></td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">解除劳动合同时间 <br /></td>
   <td style="width: 35%; word-break: break-all;" class="formInput"></td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">解除劳动合同原因</td>
   <td class="formInput" colspan="3" rowspan="1" style="word-break: break-all;"><br /> <textarea name="m:user_resignation:reason" validate="{empty:false}">${userResignation.reason}</textarea> </td>
  </tr>
  <tr id="leader">
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">项目部资料移交情况</td>
   <td class="formInput" colspan="1" rowspan="1"></td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">项目部资料情况</td>
   <td class="formInput" colspan="1" rowspan="1"></td>
  </tr>
  <tr id="leader">
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">项目章移交情况</td>
   <td class="formInput" colspan="1" rowspan="1"></td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">物品移交情况</td>
   <td class="formInput" colspan="1" rowspan="1"></td>
  </tr>
  <tr id="tr">
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">离职原因</td>
   <td class="formInput" colspan="3" rowspan="1"><br /></td>
  </tr>
 </tbody>
</table>
