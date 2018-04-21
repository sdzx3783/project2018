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
<table class="formTable" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled"> 
 <tbody> 
  <tr class="firstRow"> 
   <td class="formHead" colspan="4" style="word-break: break-all;">请假申请</td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">工号</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">参加工作时间</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">申请人</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:leave_application:applicant" lablename="申请人" class="inputText" value="${leaveApplication.applicant}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">入职时间</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">申请日期</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"><br /></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">婚否<br /></td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"><br /> <span> <label><input val="${leaveApplication.isMarry}" type="radio" name="m:leave_application:isMarry" value="0" lablename="婚否" validate="{}" />未婚</label> <label><input val="${leaveApplication.isMarry}" type="radio" name="m:leave_application:isMarry" value="1" lablename="婚否" validate="{}" />已婚</label> </span> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">所属部门</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"><br /> <input name="m:leave_application:department" type="text" ctltype="selector" class="org" lablename="所属部门" value="${leaveApplication.department}" validate="{empty:false}" readonly="readonly" showcurorg="0" /> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">请假类型</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"><br /></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">职位</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"> <input name="m:leave_application:position" type="text" ctltype="selector" class="positions" lablename="职位" value="${leaveApplication.position}" validate="{empty:false}" readonly="readonly" /> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">请假时间</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3">自 <input name="m:leave_application:startTime" type="text" class="Wdate" displaydate="0" datefmt="yyyy-MM-dd" value="<fmt:formatDate value='${leaveApplication.startTime}' pattern='yyyy-MM-dd'/>" validate="{empty:false}" /> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">请假事由</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"><br /> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <textarea name="m:leave_application:reason" lablename="请假事由" class="l-textarea" rows="5" cols="40" validate="{maxlength:1000}" isflag="tableflag">${leaveApplication.reason}</textarea> </span> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">备注</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <textarea name="m:leave_application:remark" lablename="备注" class="l-textarea" rows="5" cols="40" validate="{maxlength:1000}" isflag="tableflag">${leaveApplication.remark}</textarea> </span> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">上传相关证明</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"><br /></td> 
  </tr> 
 </tbody> 
</table>
