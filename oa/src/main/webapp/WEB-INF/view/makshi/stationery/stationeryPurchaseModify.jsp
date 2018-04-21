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
   <td class="formInput" colspan="4" rowspan="1"><br /> <span name="editable-input" style="display:inline-block;padding:2px;" class="selectinput"> <select name="m:stationery_purchase:state" lablename="审批状态" controltype="select" validate="{empty:false}" val="${stationeryPurchase.state}"> <option value="0">审批中</option> <option value="1">办理中</option> <option value="2">驳回</option> <option value="3">结束</option> </select> </span> </td>
  </tr>
 </tbody>
</table>
<div type="subtable" tablename="stationery_stock" tabledesc="文具库存表" show="true" parser="rowmodeedit" id="stationery_stock" formtype="edit">
 <table class="listTable" border="0" cellpadding="2" cellspacing="0">
  <tbody>
   <tr class="toolBar firstRow">
    <td colspan="6"><a class="link add" href="javascript:;">添加</a></td>
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
     <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:stationery_stock:name" lablename="名称" class="inputText" value="${stationeryStock.name}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
     <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:stationery_stock:specification" lablename="规格型号" class="inputText" value="${stationeryStock.specification}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
     <td></td>
     <td></td>
     <td></td>
     <td> <input name="s:stationery_stock:stock" type="text" value="${stationeryStock.stock}" showtype="{"decimalValue":"0"}" validate="{number:true,maxIntLen:14,maxDecimalLen:0}" /> </td>
    </tr>
   </c:forEach>
   <tr class="listRow" type="append" style="display:none;"> 
    <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:stationery_stock:name" lablename="名称" class="inputText" value="${stationeryStock.name}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
    <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:stationery_stock:specification" lablename="规格型号" class="inputText" value="${stationeryStock.specification}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
    <td></td> 
    <td></td> 
    <td></td> 
    <td> <input name="s:stationery_stock:stock" type="text" value="${stationeryStock.stock}" showtype="{"decimalValue":"0"}" validate="{number:true,maxIntLen:14,maxDecimalLen:0}" /> </td> 
   </tr>
  </tbody>
 </table>
 <input name="s:stationery_stock:id" type="hidden" pk="true" value="" />
</div>
