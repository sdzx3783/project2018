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
   <td class="formHead" colspan="4" style="word-break: break-all;">方案审批流程</td> 
   <td class="formHead" colspan="1" style="word-break: break-all;"><br /></td> 
   <td class="formHead" colspan="1" style="word-break: break-all;"><br /></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">项目名称</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">阶段名称<br /></td> 
   <td style="width: 35%" class="formInput"></td> 
   <td style="width: 35%" class="formInput"><br /></td> 
   <td style="width: 35%" class="formInput"><br /></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">任务名称</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right"><span style="font-size: 12px; text-align: right; background-color: rgb(240, 241, 241);">拟稿人</span><br /></td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"> ${hdProgrammeApproval.name} </td> 
   <td style="width: 35%" class="formInput"><br /></td> 
   <td style="width: 35%" class="formInput"><br /></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right"><span style="font-size: 12px; text-align: right; background-color: rgb(240, 241, 241);">拟稿时间</span></td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"> <fmt:formatDate value='${hdProgrammeApproval.date}' pattern='yyyy-MM-dd'/> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right"><span style="font-size: 12px; text-align: right; background-color: rgb(240, 241, 241);">方案名称</span></td> 
   <td style="width: 35%" class="formInput"></td> 
   <td style="width: 35%" class="formInput"><br /></td> 
   <td style="width: 35%" class="formInput"><br /></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right"><span style="font-size: 12px; text-align: right; background-color: rgb(240, 241, 241);">方案内容</span></td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${hdProgrammeApproval.content} </span> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right"><span style="font-size: 12px; text-align: right; background-color: rgb(240, 241, 241);">备注</span></td> 
   <td style="width: 35%" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${hdProgrammeApproval.note} </span> </td> 
   <td style="width: 35%" class="formInput"><br /></td> 
   <td style="width: 35%" class="formInput"><br /></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">附件</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"> 
    <div name="div_attachment_container" right="r"> 
     <div class="attachement"></div> 
     <textarea style="display:none" controltype="attachment" name="attachment" lablename="附件" readonly="readonly">${hdProgrammeApproval.attachment}</textarea> 
    </div> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right"><br /></td> 
   <td style="width: 35%" class="formInput"><br /></td> 
   <td style="width: 35%" class="formInput"><br /></td> 
   <td style="width: 35%" class="formInput"><br /></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">项目负责人</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right"><br /></td> 
   <td style="width: 35%" class="formInput"><br /></td> 
   <td style="width: 35%" class="formInput"><br /></td> 
   <td style="width: 35%" class="formInput"><br /></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">部门负责人</td> 
   <td style="width: 35%" class="formInput"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right"><br /></td> 
   <td style="width: 35%" class="formInput"><br /></td> 
   <td style="width: 35%" class="formInput"><br /></td> 
   <td style="width: 35%" class="formInput"><br /></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">是否需要审定</td> 
   <td style="width: 35%" class="formInput"> <span> <label><input data="${hdProgrammeApproval.isNeedCheck}" type="radio" name="isNeedCheck" value="1" lablename="是否需要审定" validate="{}" disabled="disabled" />需要</label> <label><input data="${hdProgrammeApproval.isNeedCheck}" type="radio" name="isNeedCheck" value="0" lablename="是否需要审定" validate="{}" disabled="disabled" />不需要</label> </span> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">总工办/分管领导<br /></td> 
   <td style="width: 35%" class="formInput"> ${hdProgrammeApproval.finalChecker} </td> 
   <td style="width: 35%" class="formInput"><br /></td> 
   <td style="width: 35%" class="formInput"><br /></td> 
  </tr> 
  <tr style="display:none"> 
   <td class="formInput" colspan="1" rowspan="1"><br /></td> 
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all;"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${hdProgrammeApproval.projectTaskId} </span> </td> 
   <td class="formInput" colspan="1" rowspan="1"><br /></td> 
   <td class="formInput" colspan="1" rowspan="1"><br /></td> 
   <td class="formInput" colspan="1" rowspan="1"><br /></td> 
   <td class="formInput" colspan="1" rowspan="1"><br /></td> 
  </tr> 
 </tbody> 
</table>
