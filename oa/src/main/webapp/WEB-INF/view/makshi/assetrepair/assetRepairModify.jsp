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
   <td class="formHead" colspan="4" style="word-break: break-all;">资产维护表</td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">资产编号:</td>
   <td style="width: 35%" class="formInput"></td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">保养单位:</td>
   <td style="width: 35%" class="formInput"></td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">保养日期:</td>
   <td style="width: 35%" class="formInput"> <input name="m:asset_repair:date" type="text" class="Wdate" displaydate="0" datefmt="yyyy-MM-dd" value="<fmt:formatDate value='${assetRepair.date}' pattern='yyyy-MM-dd'/>" validate="{empty:false}" /> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">验收人:</td>
   <td style="width: 35%" class="formInput"><br /></td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">保养内容:</td>
   <td style="width: 35%" class="formInput"></td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">备注:</td>
   <td style="width: 35%" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:asset_repair:remarks" lablename="备注" class="inputText" value="${assetRepair.remarks}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">申请人:</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"></td>
  </tr>
  <tr style="display:none">
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">工单号:</td>
   <td class="formInput" colspan="1" rowspan="1"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:asset_repair:globalflowno" lablename="工单号" class="inputText" value="${assetRepair.globalflowno}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right"><span style="font-size: 12px; text-align: right; background-color: rgb(240, 241, 241);">申请时间:</span></td>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all;"></td>
  </tr>
  <tr style="display:none">
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">审批状态:</td>
   <td class="formInput" colspan="3" rowspan="1"><br /> <span name="editable-input" style="display:inline-block;padding:2px;" class="selectinput"> <select name="m:asset_repair:state" lablename="审批状态" controltype="select" validate="{empty:false}" val="${assetRepair.state}"> <option value="0">审批中</option> <option value="1">办理中</option> <option value="2">驳回</option> <option value="3">结束</option> </select> </span> </td>
  </tr>
 </tbody>
</table>
