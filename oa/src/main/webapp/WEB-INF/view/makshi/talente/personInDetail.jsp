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
   <td class="formHead" colspan="4" style="word-break: break-all;">人才引进信息</td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">人才引进记录编号</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">档案托管单位<br /></td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">员工编号</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">落户时间</td> 
   <td style="width: 35%" class="formInput"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">申请人</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${personIn.applicant} </span> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">户口所属派出所</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">申请时间</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"><br /></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">到档情况</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${personIn.isArrive} </span> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">申报方式</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${personIn.method} </span> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">落户情况</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">申报类型</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${personIn.type} </span> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">缴费时间</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"><br /></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">到档时间</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"><br /></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">缴费金额</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">人事档案编号</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">备注</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${personIn.remark} </span> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">附件</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"><br /> 
    <div name="div_attachment_container" right="r"> 
     <div class="attachement"></div> 
     <textarea style="display:none" controltype="attachment" name="file" lablename="附件" readonly="readonly">${personIn.file}</textarea> 
    </div> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">引进状态</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"><br /></td> 
  </tr> 
 </tbody> 
</table>
