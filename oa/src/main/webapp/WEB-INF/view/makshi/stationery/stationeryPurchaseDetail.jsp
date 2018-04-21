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
   <td class="formHead" colspan="4" style="word-break: break-all;">办公用品及耗材申购表</td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">申请人:</td>
   <td style="width: 35%" class="formInput">${stationeryPurchase.state}</td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">一级部门:</td>
   <td style="width: 35%" class="formInput"><br /></td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">工号:</td>
   <td style="width: 35%" class="formInput"></td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">二级部门或项目部:</td>
   <td style="width: 35%" class="formInput"></td>
  </tr>
  <tr style="display:none">
   <td class="formInput" colspan="4" rowspan="1"><br /> ${stationeryPurchase.state} </td>
  </tr>
 </tbody>
</table>
<div type="subtable" tablename="stationery_stock" tabledesc="文具库存表" show="true" parser="rowmodeedit" id="stationery_stock" formtype="edit">
 <table class="listTable" border="0" cellpadding="2" cellspacing="0">
  <tbody>
   <tr class="toolBar firstRow">
    <td colspan="6"></td>
   </tr>
   <tr class="headRow">
    <th style="word-break: break-all;">名称</th>
    <th style="word-break: break-all;">规格型号</th>
    <th style="word-break: break-all;">数量</th>
    <th style="word-break: break-all;">上月采购</th>
    <th style="word-break: break-all;">上月发出</th>
    <th style="word-break: break-all;">库存</th>
   </tr>
   <c:forEach items="${stationery_stockList}" var="stationery_stock" varStatus="status">
    <tr class="listRow" type="subdata">
     <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${stationeryStock.name} </span> </td>
     <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${stationeryStock.specification} </span> </td>
     <td></td>
     <td></td>
     <td></td>
     <td> ${stationeryStock.stock} </td>
    </tr>
   </c:forEach>
  </tbody>
 </table>
</div>
