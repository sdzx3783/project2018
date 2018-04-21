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
   <td class="formHead" colspan="4" style="word-break: break-all;">资产移交表</td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right" rowspan="2" colspan="1">移交人:</td>
   <td style="width: 35%" class="formInput" rowspan="2" colspan="1"></td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">移交一级部门:</td>
   <td style="width: 35%" class="formInput"><br /></td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">移交二级部门或项目部:</td>
   <td style="width: 35%" class="formInput"></td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right" rowspan="2" colspan="1">接交人:<br /></td>
   <td style="width: 35%" class="formInput" rowspan="2" colspan="1"></td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right"><span style="font-size: 12px; text-align: right; background-color: rgb(240, 241, 241);"><span style="font-size: 12px; text-align: right; background-color: rgb(240, 241, 241);">接</span>交一级部门</span>:</td>
   <td style="width: 35%" class="formInput"></td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right"><span style="font-size: 12px; text-align: right; background-color: rgb(240, 241, 241);"><span style="font-size: 12px; text-align: right; background-color: rgb(240, 241, 241);">接</span>交二级部门或项目部</span>:</td>
   <td style="width: 35%" class="formInput"></td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">资产编号:</td>
   <td style="width: 35%" class="formInput"></td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">数量:</td>
   <td style="width: 35%" class="formInput"> <input name="m:asset_transfer:number" type="text" value="${assetTransfer.number}" showtype="{"decimalValue":"0"}" validate="{number:true,maxIntLen:14,maxDecimalLen:0}" /> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">固定资产名称:</td>
   <td style="width: 35%" class="formInput"></td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">移交原因:</td>
   <td style="width: 35%" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:asset_transfer:reason" lablename="移交原因" class="inputText" value="${assetTransfer.reason}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">规格型号:</td>
   <td style="width: 35%" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:asset_transfer:specifications" lablename="规格型号" class="inputText" value="${assetTransfer.specifications}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">移交日期:</td>
   <td style="width: 35%" class="formInput"><br /> <input name="m:asset_transfer:time" type="text" class="Wdate" displaydate="0" datefmt="yyyy-MM-dd" value="<fmt:formatDate value='${assetTransfer.time}' pattern='yyyy-MM-dd'/>" validate="{empty:false}" /> </td>
  </tr>
  <tr style="display:">
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">工单号:<br /></td>
   <td class="formInput" colspan="1" rowspan="1"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:asset_transfer:globalflowno" lablename="工单号" class="inputText" value="${assetTransfer.globalflowno}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">审批状态:<br /></td>
   <td class="formInput" colspan="1" rowspan="1"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:asset_transfer:state" lablename="审批状态" class="inputText" value="${assetTransfer.state}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
  </tr>
  <tr style="display:none">
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">申请时间:</td>
   <td class="formInput" colspan="3" rowspan="1"></td>
  </tr>
 </tbody>
</table>
