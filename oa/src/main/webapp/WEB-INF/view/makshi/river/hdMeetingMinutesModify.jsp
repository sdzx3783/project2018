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
   <td class="formHead" colspan="4" style="word-break: break-all;">会议纪要</td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">会议名称<br /></td>
   <td style="width: 35%" class="formInput"></td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">编号<br /></td>
   <td style="width: 35%" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:hd_meeting_minutes:number" lablename="编号" class="inputText" value="${hdMeetingMinutes.number}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">会议时间</td>
   <td style="width: 35%; word-break: break-all;" class="formInput"> <input name="m:hd_meeting_minutes:date" type="text" class="Wdate" displaydate="0" datefmt="yyyy-MM-dd" value="<fmt:formatDate value='${hdMeetingMinutes.date}' pattern='yyyy-MM-dd'/>" validate="{empty:false}" /> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">会议主持人</td>
   <td style="width: 35%; word-break: break-all;" class="formInput"> <input name="m:hd_meeting_minutes:moderator" type="text" ctltype="selector" class="users" lablename="会议主持人" value="${hdMeetingMinutes.moderator}" validate="{empty:false}" readonly="readonly" /> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">参会人员</td>
   <td style="width: 35%; word-break: break-all;" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <textarea name="m:hd_meeting_minutes:participants" lablename="参会人员" class="l-textarea" rows="5" cols="40" validate="{maxlength:1000}" isflag="tableflag">${hdMeetingMinutes.participants}</textarea> </span> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">纪要提交人</td>
   <td style="width: 35%" class="formInput"> <input name="m:hd_meeting_minutes:name" type="text" ctltype="selector" class="user" lablename="纪要提交人" value="${hdMeetingMinutes.name}" validate="{empty:false}" readonly="readonly" showcuruser="0" /> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">备注</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <textarea name="m:hd_meeting_minutes:note" lablename="备注" class="l-textarea" rows="5" cols="40" validate="{maxlength:1000}" isflag="tableflag">${hdMeetingMinutes.note}</textarea> </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">附件</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"></td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">项目负责人</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"></td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">部门负责人</td>
   <td style="width: 35%" class="formInput" rowspan="1" colspan="3"></td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">是否需要核定</td>
   <td style="width: 35%" class="formInput"> <span> <label><input val="${hdMeetingMinutes.isNeedCheck}" type="radio" name="m:hd_meeting_minutes:isNeedCheck" value="1" lablename="是否需要审定" validate="{}" />需要</label> <label><input val="${hdMeetingMinutes.isNeedCheck}" type="radio" name="m:hd_meeting_minutes:isNeedCheck" value="0" lablename="是否需要审定" validate="{}" />不需要</label> </span> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">总工/分管领导<br /></td>
   <td style="width: 35%; word-break: break-all;" class="formInput"> <input name="m:hd_meeting_minutes:finalChecker" type="text" ctltype="selector" class="user" lablename="总工/分管领导" value="${hdMeetingMinutes.finalChecker}" validate="{empty:false}" readonly="readonly" showcuruser="0" /> </td>
  </tr>
 </tbody>
</table>
