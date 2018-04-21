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
<table class="formTable" parser="addpermission" data-sort="sortDisabled" cellspacing="0" cellpadding="2" border="1">
 <tbody>
  <tr class="firstRow">
   <td class="formHead" colspan="4" style="word-break: break-all;">资产申购表</td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">申请人:</td>
   <td style="width: 35%" class="formInput" id="fullname"></td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">所在部门:</td>
   <td style="width: 35%" class="formInput" id="orgpathname"></td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">使用人:</td>
   <td style="width: 35%" class="formInput"></td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">员工编号:</td>
   <td style="width: 35%;" class="formInput" id="userId_procedure"></td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">固定资产名称:</td>
   <td style="width: 35%" class="formInput"></td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">数量:</td>
   <td style="width: 35%" class="formInput"> <input name="m:asset_application_all:number" type="text" value="${assetApplicationAll.number}" showtype="{"isShowComdify":0,"coinValue":"","decimalValue":"0"}" validate="{number:true,maxIntLen:14,maxDecimalLen:0}" /> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">规格型号:</td>
   <td style="width: 35%" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:asset_application_all:specifications" lablename="规格型号" class="inputText" value="${assetApplicationAll.specifications}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">质量要求及验收标准:</td>
   <td style="width: 35%" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:asset_application_all:standard" lablename="质量要求及验收标准" class="inputText" value="${assetApplicationAll.standard}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">类型:</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"><br /> <span> <label><input val="${assetApplicationAll.type}" type="radio" name="m:asset_application_all:type" value="0" lablename="类型" validate="{}" />领用旧设备</label> <label><input val="${assetApplicationAll.type}" type="radio" name="m:asset_application_all:type" value="1" lablename="类型" validate="{}" />购置新设备</label> </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">采购方式:</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"><br /></td>
  </tr>
  <tr>
   <td class="formInput" rowspan="1" colspan="4" style="word-break: break-all;"></td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">资产类别<br /></td>
   <td class="formInput" colspan="3" rowspan="1"><br /> <input lablename="资产类别" class="dicComboTree" nodekey="assetType" value="${assetApplicationAll.assetType}" validate="{empty:false}" name="m:asset_application_all:assetType" height="200" width="125" /> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">条码:</td>
   <td style="width: 35%; word-break: break-all;" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:asset_application_all:assetId" lablename="条码" class="inputText" value="${assetApplicationAll.assetId}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">资产信息附件:</td>
   <td class="formInput" style="width: 35%"><br /></td>
  </tr>
 </tbody>
</table>
