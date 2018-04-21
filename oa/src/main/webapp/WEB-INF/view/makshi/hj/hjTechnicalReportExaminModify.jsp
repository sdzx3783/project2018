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
   <td class="formHead" style="-ms-word-break: break-all;" colspan="4">技术报告</td>
  </tr>
  <tr>
   <td align="right" class="formTitle" style="width: 15%; -ms-word-break: break-all;">上传人:</td>
   <td class="formInput" style="width: 35%;"> <input name="m:hj_Technical_report_examin:name" type="text" ctltype="selector" class="user" lablename="上传人" value="${hjTechnicalReportExamin.name}" validate="{empty:false}" readonly="readonly" showcuruser="0" /> </td>
   <td align="right" class="formTitle" style="width: 15%; -ms-word-break: break-all;">上传日期:</td>
   <td class="formInput" style="width: 35%;"> <input name="m:hj_Technical_report_examin:uploadTime" type="text" class="Wdate" displaydate="0" datefmt="yyyy-MM-dd" value="<fmt:formatDate value='${hjTechnicalReportExamin.uploadTime}' pattern='yyyy-MM-dd'/>" validate="{empty:false}" /> </td>
  </tr>
  <tr>
   <td align="right" class="formTitle" style="width: 15%; -ms-word-break: break-all;">所属部门:</td>
   <td class="formInput" style="width: 35%;"> <input name="m:hj_Technical_report_examin:department" type="text" ctltype="selector" class="orgs" lablename="所属部门" value="${hjTechnicalReportExamin.department}" validate="{empty:false}" readonly="readonly" /> </td>
   <td align="right" class="formTitle" style="width: 15%; -ms-word-break: break-all;">项目名称:</td>
   <td class="formInput" style="width: 35%;"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:hj_Technical_report_examin:itemName" lablename="项目名称" class="inputText" value="${hjTechnicalReportExamin.itemName}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
  </tr>
  <tr>
   <td align="right" class="formTitle" style="width: 15%; -ms-word-break: break-all;">报告名称:</td>
   <td class="formInput" style="width: 35%; -ms-word-break: break-all;"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:hj_Technical_report_examin:reportName" lablename="报告名称" class="inputText" value="${hjTechnicalReportExamin.reportName}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
   <td align="right" class="formTitle" style="width: 15%; -ms-word-break: break-all;">报告内容:</td>
   <td class="formInput" style="width: 35%; -ms-word-break: break-all;"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <textarea name="m:hj_Technical_report_examin:reportContent" lablename="报告内容" class="l-textarea" rows="5" cols="40" validate="{maxlength:1000}" isflag="tableflag">${hjTechnicalReportExamin.reportContent}</textarea> </span> </td>
  </tr>
  <tr>
   <td align="right" class="formTitle" style="width: 15%; -ms-word-break: break-all;">备注:</td>
   <td class="formInput" style="width: 35%; -ms-word-break: break-all;" rowspan="1" colspan="3"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <textarea name="m:hj_Technical_report_examin:tag" lablename="备注" class="l-textarea" rows="5" cols="40" validate="{maxlength:1000}" isflag="tableflag">${hjTechnicalReportExamin.tag}</textarea> </span> </td>
  </tr>
  <tr>
   <td align="right" class="formTitle" style="width: 15%; -ms-word-break: break-all;">选择校核人员:</td>
   <td class="formInput" style="width: 35%; -ms-word-break: break-all;" rowspan="1" colspan="3"> <input name="m:hj_Technical_report_examin:checkfirst" type="text" ctltype="selector" class="users" lablename="专业负责人、专业技术人员" value="${hjTechnicalReportExamin.checkfirst}" validate="{empty:false}" readonly="readonly" /> </td>
  </tr>
  <tr>
   <td align="right" class="formTitle" style="width: 15%; -ms-word-break: break-all;">选择审查人员:</td>
   <td class="formInput" style="width: 35%; -ms-word-break: break-all;" rowspan="1" colspan="3"> <input name="m:hj_Technical_report_examin:checksecond" type="text" ctltype="selector" class="users" lablename="专业负责人/专业技术人员" value="${hjTechnicalReportExamin.checksecond}" validate="{empty:false}" readonly="readonly" /> </td>
  </tr>
  <tr>
   <td align="right" class="formTitle" style="width: 15%; -ms-word-break: break-all;">选择审核人员:</td>
   <td class="formInput" style="width: 35%; -ms-word-break: break-all;" rowspan="1" colspan="3"> <input name="m:hj_Technical_report_examin:checkthird" type="text" ctltype="selector" class="users" lablename="项目负责人、项目副负责人" value="${hjTechnicalReportExamin.checkthird}" validate="{empty:false}" readonly="readonly" /> </td>
  </tr>
  <tr>
   <td align="right" class="formTitle" style="width: 15%; -ms-word-break: break-all;"><a target="_self" textvalue="是否需要审定">是否需要审定: <span> <label><input val="${hjTechnicalReportExamin.validation}" type="radio" name="m:hj_Technical_report_examin:validation" value="F" lablename="是否需要审定" validate="{}" />否</label> <label><input val="${hjTechnicalReportExamin.validation}" type="radio" name="m:hj_Technical_report_examin:validation" value="T" lablename="是否需要审定" validate="{}" />是</label> </span> </a></td>
   <td id="ischeck" class="formInput" style="width: 35%; -ms-word-break: break-all;" rowspan="1" colspan="3"> <input name="m:hj_Technical_report_examin:checkfourth" type="text" ctltype="selector" class="users" lablename="总工程师、分管领导" value="${hjTechnicalReportExamin.checkfourth}" validate="{empty:false}" readonly="readonly" /> </td>
  </tr>
  <tr>
   <td align="right" class="formTitle" style="width: 15%; -ms-word-break: break-all;">附件:</td>
   <td class="formInput" style="width: 35%; word-break: break-all;" rowspan="1" colspan="3"><br /></td>
  </tr>
  <tr style="display: none;">
   <td class="formInput" rowspan="1" colspan="1"><br /></td>
   <td class="formInput" style="-ms-word-break: break-all;" rowspan="1" colspan="1"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:hj_Technical_report_examin:projectTaskId" lablename="项目任务id" class="inputText" value="${hjTechnicalReportExamin.projectTaskId}" validate="{maxlength:20}" isflag="tableflag" /> </span> </td>
   <td class="formInput" rowspan="1" colspan="1"><br /></td>
   <td class="formInput" rowspan="1" colspan="1"><br /></td>
  </tr>
 </tbody>
</table>
