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
<table class="formTable" border="1" cellspacing="0" cellpadding="2" data-sort="sortDisabled" parser="addpermission"> 
 <tbody> 
  <tr class="firstRow"> 
   <td class="formHead" style="-ms-word-break: break-all;" colspan="4">监管方案报审</td> 
   <td class="formHead" style="-ms-word-break: break-all;" colspan="1"><br /></td> 
   <td class="formHead" style="-ms-word-break: break-all;" colspan="1"><br /></td> 
  </tr> 
  <tr> 
   <td align="right" class="formTitle" style="width: 15%; -ms-word-break: break-all;">提交人:</td> 
   <td class="formInput" style="width: 35%;"><br /> ${hjRegulatoryScheme.name} </td> 
   <td align="right" class="formTitle" style="width: 15%; -ms-word-break: break-all;">提交日期:</td> 
   <td class="formInput" style="width: 35%;"><br /> <fmt:formatDate value='${hjRegulatoryScheme.date}' pattern='yyyy-MM-dd'/> </td> 
   <td class="formInput" style="width: 35%;"><br /></td> 
   <td class="formInput" style="width: 35%;"><br /></td> 
  </tr> 
  <tr> 
   <td align="right" class="formTitle" style="width: 15%; -ms-word-break: break-all;">所属部门:</td> 
   <td class="formInput" style="width: 35%;"> ${hjRegulatoryScheme.department} </td> 
   <td align="right" class="formTitle" style="width: 15%; -ms-word-break: break-all;">项目名称:</td> 
   <td class="formInput" style="width: 35%;"></td> 
   <td class="formInput" style="width: 35%;"><br /></td> 
   <td class="formInput" style="width: 35%;"><br /></td> 
  </tr> 
  <tr> 
   <td align="right" class="formTitle" style="width: 15%; word-break: break-all;">方案名称:</td> 
   <td class="formInput" style="width: 35%;"></td> 
   <td align="right" class="formTitle" style="width: 15%; word-break: break-all;">方案内容:</td> 
   <td class="formInput" style="width: 35%;"><br /> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${hjRegulatoryScheme.content} </span> </td> 
   <td class="formInput" style="width: 35%;"><br /></td> 
   <td class="formInput" style="width: 35%;"><br /></td> 
  </tr> 
  <tr> 
   <td align="right" class="formTitle" style="width: 15%; -ms-word-break: break-all;">备注:</td> 
   <td class="formInput" style="width: 35%; -ms-word-break: break-all;" rowspan="1" colspan="3"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${hjRegulatoryScheme.note} </span> </td> 
   <td class="formInput" style="width: 35%; -ms-word-break: break-all;" rowspan="1" colspan="1"><br /></td> 
   <td class="formInput" style="width: 35%; -ms-word-break: break-all;" rowspan="1" colspan="1"><br /></td> 
  </tr> 
  <tr> 
   <td align="right" class="formTitle" style="width: 15%; -ms-word-break: break-all;">附件:</td> 
   <td class="formInput" style="width: 35%; -ms-word-break: break-all;" rowspan="1" colspan="3"><br /> 
    <div name="div_attachment_container" right="r"> 
     <div class="attachement"></div> 
     <textarea style="display:none" controltype="attachment" name="enclosure" lablename="附件" readonly="readonly">${hjRegulatoryScheme.enclosure}</textarea> 
    </div> </td> 
   <td class="formInput" style="width: 35%; -ms-word-break: break-all;" rowspan="1" colspan="1"><br /></td> 
   <td class="formInput" style="width: 35%; -ms-word-break: break-all;" rowspan="1" colspan="1"><br /></td> 
  </tr> 
  <tr> 
   <td align="right" class="formTitle" style="width: 15%; word-break: break-all;">项目负责人审核:</td> 
   <td class="formInput" style="width: 35%; word-break: break-all;" rowspan="1" colspan="3"><br /></td> 
   <td class="formInput" style="width: 35%; -ms-word-break: break-all;" rowspan="1" colspan="1"><br /></td> 
   <td class="formInput" style="width: 35%; -ms-word-break: break-all;" rowspan="1" colspan="1"><br /></td> 
  </tr> 
  <tr> 
   <td align="right" class="formTitle" style="width: 15%; word-break: break-all;">部门副经理审核:</td> 
   <td class="formInput" style="width: 35%; word-break: break-all;" rowspan="1" colspan="3"><br /></td> 
   <td class="formInput" style="width: 35%; -ms-word-break: break-all;" rowspan="1" colspan="1"><br /></td> 
   <td class="formInput" style="width: 35%; -ms-word-break: break-all;" rowspan="1" colspan="1"><br /></td> 
  </tr> 
  <tr style="display:none"> 
   <td class="formInput" colspan="1" rowspan="1"><br /></td> 
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all;"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${hjRegulatoryScheme.projectTaskId} </span> </td> 
   <td class="formInput" colspan="1" rowspan="1"><br /></td> 
   <td class="formInput" colspan="1" rowspan="1"><br /></td> 
   <td class="formInput" colspan="1" rowspan="1"><br /></td> 
   <td class="formInput" colspan="1" rowspan="1"><br /></td> 
  </tr> 
 </tbody> 
</table>
