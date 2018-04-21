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
   <td class="formHead" colspan="4">主 &nbsp; &nbsp; &nbsp;表</td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">租房编号:</td> 
   <td style="width: 35%" class="formInput"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">所属项目部:</td> 
   <td style="width: 35%" class="formInput"><br /> ${houseReimburse.department} </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">承租人:</td> 
   <td style="width: 35%" class="formInput"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">房屋地址:</td> 
   <td style="width: 35%" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${houseReimburse.address} </span> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">报销人:</td> 
   <td style="width: 35%" class="formInput"><br /></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">租房开始日期:</td> 
   <td style="width: 35%" class="formInput"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">报销日期:</td> 
   <td style="width: 35%" class="formInput"><br /></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">租房结束日期:</td> 
   <td style="width: 35%" class="formInput"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">费用期间:</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"><br /></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">租房金额:</td> 
   <td style="width: 35%" class="formInput"></td> 
  </tr> 
 </tbody> 
</table>
