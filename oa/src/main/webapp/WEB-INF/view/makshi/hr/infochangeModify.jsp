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
<table class="formTable" border="1" cellspacing="0" cellpadding="2" parser="addpermission"> 
 <tbody> 
  <tr class="firstRow"> 
   <td class="formHead" colspan="4" style="word-break: break-all;">信息变更申请</td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">姓名</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:infochange:name" lablename="姓名" class="inputText" value="${infochange.name}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">部门</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:infochange:department" lablename="部门" class="inputText" value="${infochange.department}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">员工编号</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">公司短号</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:infochange:shortNum" lablename="公司短号" class="inputText" value="${infochange.shortNum}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">信息变更内容</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"><span style="font-size: 24px;"><strong>变更前</strong></span></td> 
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="2"><span style="font-size: 24px;"><strong>变更后</strong></span></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">户籍</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"><br /> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <textarea name="m:infochange:before" lablename="变更前" class="l-textarea" rows="5" cols="40" validate="{maxlength:1000}" isflag="tableflag">${infochange.before}</textarea> </span> </td> 
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="2"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <textarea name="m:infochange:after" lablename="变更后" class="l-textarea" rows="5" cols="40" validate="{maxlength:1000}" isflag="tableflag">${infochange.after}</textarea> </span> </td> 
  </tr> 
 </tbody> 
</table>
