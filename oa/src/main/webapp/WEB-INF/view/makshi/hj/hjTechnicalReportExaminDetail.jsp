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
   <td class="formHead" style="-ms-word-break: break-all;" colspan="4">技术报告</td>
  </tr>
  <tr>
   <td align="right" class="formTitle" style="width: 15%; -ms-word-break: break-all;">上传人:</td>
   <td class="formInput" style="width: 35%;"> ${hjTechnicalReportExamin.name} </td>
   <td align="right" class="formTitle" style="width: 15%; -ms-word-break: break-all;">上传日期:</td>
   <td class="formInput" style="width: 35%;"> <fmt:formatDate value='${hjTechnicalReportExamin.uploadTime}' pattern='yyyy-MM-dd'/> </td>
  </tr>
  <tr>
   <td align="right" class="formTitle" style="width: 15%; -ms-word-break: break-all;">所属部门:</td>
   <td class="formInput" style="width: 35%;"> ${hjTechnicalReportExamin.department} </td>
   <td align="right" class="formTitle" style="width: 15%; -ms-word-break: break-all;">项目名称:</td>
   <td class="formInput" style="width: 35%;"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${hjTechnicalReportExamin.itemName} </span> </td>
  </tr>
  <tr>
   <td align="right" class="formTitle" style="width: 15%; -ms-word-break: break-all;">报告名称:</td>
   <td class="formInput" style="width: 35%; -ms-word-break: break-all;"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${hjTechnicalReportExamin.reportName} </span> </td>
   <td align="right" class="formTitle" style="width: 15%; -ms-word-break: break-all;">报告内容:</td>
   <td class="formInput" style="width: 35%; -ms-word-break: break-all;"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${hjTechnicalReportExamin.reportContent} </span> </td>
  </tr>
  <tr>
   <td align="right" class="formTitle" style="width: 15%; -ms-word-break: break-all;">备注:</td>
   <td class="formInput" style="width: 35%; -ms-word-break: break-all;" rowspan="1" colspan="3"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${hjTechnicalReportExamin.tag} </span> </td>
  </tr>
  <tr>
   <td align="right" class="formTitle" style="width: 15%; -ms-word-break: break-all;">选择校核人员:</td>
   <td class="formInput" style="width: 35%; -ms-word-break: break-all;" rowspan="1" colspan="3"> ${hjTechnicalReportExamin.checkfirst} </td>
  </tr>
  <tr>
   <td align="right" class="formTitle" style="width: 15%; -ms-word-break: break-all;">选择审查人员:</td>
   <td class="formInput" style="width: 35%; -ms-word-break: break-all;" rowspan="1" colspan="3"> ${hjTechnicalReportExamin.checksecond} </td>
  </tr>
  <tr>
   <td align="right" class="formTitle" style="width: 15%; -ms-word-break: break-all;">选择审核人员:</td>
   <td class="formInput" style="width: 35%; -ms-word-break: break-all;" rowspan="1" colspan="3"> ${hjTechnicalReportExamin.checkthird} </td>
  </tr>
  <tr>
   <td align="right" class="formTitle" style="width: 15%; -ms-word-break: break-all;"><a target="_self" textvalue="是否需要审定">是否需要审定: <span> <label><input data="${hjTechnicalReportExamin.validation}" type="radio" name="validation" value="F" lablename="是否需要审定" validate="{}" disabled="disabled" />否</label> <label><input data="${hjTechnicalReportExamin.validation}" type="radio" name="validation" value="T" lablename="是否需要审定" validate="{}" disabled="disabled" />是</label> </span> </a></td>
   <td id="ischeck" class="formInput" style="width: 35%; -ms-word-break: break-all;" rowspan="1" colspan="3"> ${hjTechnicalReportExamin.checkfourth} </td>
  </tr>
  <tr>
   <td align="right" class="formTitle" style="width: 15%; -ms-word-break: break-all;">附件:</td>
   <td class="formInput" style="width: 35%; word-break: break-all;" rowspan="1" colspan="3"><br /> 
    <div name="div_attachment_container" right="r"> 
     <div class="attachement"></div> 
     <textarea style="display:none" controltype="attachment" name="enclosure" lablename="附件" readonly="readonly">${hjTechnicalReportExamin.enclosure}</textarea> 
    </div> </td>
  </tr>
  <tr style="display: none;">
   <td class="formInput" rowspan="1" colspan="1"><br /></td>
   <td class="formInput" style="-ms-word-break: break-all;" rowspan="1" colspan="1"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${hjTechnicalReportExamin.projectTaskId} </span> </td>
   <td class="formInput" rowspan="1" colspan="1"><br /></td>
   <td class="formInput" rowspan="1" colspan="1"><br /></td>
  </tr>
 </tbody>
</table>
