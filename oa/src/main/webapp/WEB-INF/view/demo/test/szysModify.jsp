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
   <td class="formHead" colspan="4">主 &nbsp; &nbsp; &nbsp;表</td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">数字一:</td>
   <td style="width: 35%" class="formInput"> <input name="m:szys:szy" type="text" value="${szys.szy}" showtype="{"isShowComdify":1,"coinValue":"","decimalValue":"0","minValue":1,"maxValue":9999}" validate="{number:true,maxIntLen:14,maxDecimalLen:0}" /> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">数字二:</td>
   <td style="width: 35%; word-break: break-all;" class="formInput"> <input name="m:szys:sze" type="text" value="${szys.sze}" showtype="{"isShowComdify":0,"coinValue":"","decimalValue":"0"}" validate="{number:true,maxIntLen:14,maxDecimalLen:0}" /> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">合计:</td>
   <td style="width: 35%" class="formInput"> <input name="m:szys:hj" type="text" value="${szys.hj}" showtype="{"decimalValue":"2"}" validate="{number:true,maxIntLen:14,maxDecimalLen:2}" funcexp="{数字一(m:szys:szy)}+{数字二(m:szys:sze)}" /> </td>
   <td style="width: 15%" class="formTitle" align="right">主表字段四:</td>
   <td style="width: 35%" class="formInput"><br /></td>
  </tr>
 </tbody>
</table>
