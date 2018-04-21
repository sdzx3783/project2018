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
   <td style="width: 35%" class="formInput"> ${assetApplicationAll.number} </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">规格型号:</td>
   <td style="width: 35%" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${assetApplicationAll.specifications} </span> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">质量要求及验收标准:</td>
   <td style="width: 35%" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${assetApplicationAll.standard} </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">类型:</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"><br /> <span> <label><input data="${assetApplicationAll.type}" type="radio" name="type" value="0" lablename="类型" validate="{}" disabled="disabled" />领用旧设备</label> <label><input data="${assetApplicationAll.type}" type="radio" name="type" value="1" lablename="类型" validate="{}" disabled="disabled" />购置新设备</label> </span> </td>
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
   <td class="formInput" colspan="3" rowspan="1"><br /> ${assetApplicationAll.assetType} </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">条码:</td>
   <td style="width: 35%; word-break: break-all;" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${assetApplicationAll.assetId} </span> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">资产信息附件:</td>
   <td class="formInput" style="width: 35%"><br /> 
    <div name="div_attachment_container" right="r"> 
     <div class="attachement"></div> 
     <textarea style="display:none" controltype="attachment" name="attachment" lablename="资产信息附件" readonly="readonly">${assetApplicationAll.attachment}</textarea> 
    </div> </td>
  </tr>
 </tbody>
</table>
