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
<table class="formTable" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
 <tbody>
  <tr class="firstRow">
   <td class="formHead" colspan="4" style="word-break: break-all;">会议纪要</td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">会议名称<br /></td>
   <td style="width: 35%" class="formInput"></td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">编号<br /></td>
   <td style="width: 35%" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${hdMeetingMinutes.number} </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">会议时间</td>
   <td style="width: 35%; word-break: break-all;" class="formInput"> <fmt:formatDate value='${hdMeetingMinutes.date}' pattern='yyyy-MM-dd'/> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">会议主持人</td>
   <td style="width: 35%; word-break: break-all;" class="formInput"> ${hdMeetingMinutes.moderator} </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">参会人员</td>
   <td style="width: 35%; word-break: break-all;" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${hdMeetingMinutes.participants} </span> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">纪要提交人</td>
   <td style="width: 35%" class="formInput"> ${hdMeetingMinutes.name} </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">备注</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${hdMeetingMinutes.note} </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">附件</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"> 
    <div name="div_attachment_container" right="r"> 
     <div class="attachement"></div> 
     <textarea style="display:none" controltype="attachment" name="attachment" lablename="附件" readonly="readonly">${hdMeetingMinutes.attachment}</textarea> 
    </div> </td>
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
   <td style="width: 35%" class="formInput"> <span> <label><input data="${hdMeetingMinutes.isNeedCheck}" type="radio" name="isNeedCheck" value="1" lablename="是否需要审定" validate="{}" disabled="disabled" />需要</label> <label><input data="${hdMeetingMinutes.isNeedCheck}" type="radio" name="isNeedCheck" value="0" lablename="是否需要审定" validate="{}" disabled="disabled" />不需要</label> </span> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">总工/分管领导<br /></td>
   <td style="width: 35%; word-break: break-all;" class="formInput"> ${hdMeetingMinutes.finalChecker} </td>
  </tr>
 </tbody>
</table>
