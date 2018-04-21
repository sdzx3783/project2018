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
   <td class="formHead" colspan="4" style="word-break: break-all;"><span style="color: rgb(51, 51, 51); font-family: "Arial Normal", Arial; font-size: 18px;">个人执业资格初始注册及转入</span></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">员工编号</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">发证日期</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"><br /></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">申请人</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${practiceregist.applicant} </span> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">资格证书编号</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">执业资格证名称</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">有效日期</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">注册编号</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">证件状态</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">发证机构</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">是否已考过安全证</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"><br /></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">执业资格证专业</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${practiceregist.major} </span> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">附件</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"><br /> 
    <div name="div_attachment_container" right="r"> 
     <div class="attachement"></div> 
     <textarea style="display:none" controltype="attachment" name="file" lablename="附件" readonly="readonly">${practiceregist.file}</textarea> 
    </div> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">备注</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"><br /> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${practiceregist.remark} </span> </td> 
  </tr> 
 </tbody> 
</table>