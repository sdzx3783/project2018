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
   <td class="formHead" colspan="4" style="word-break: break-all;">物品采购申请</td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">申请人:</td> 
   <td style="width: 35%" class="formInput"> <input name="m:hj_goods_purchaseds:applicant" type="text" ctltype="selector" class="user" lablename="申请人" value="${hjGoodsPurchaseds.applicant}" validate="{empty:false}" readonly="readonly" showcuruser="0" /> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">申请日期:</td> 
   <td style="width: 35%" class="formInput"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">申请部门:</td> 
   <td style="width: 35%" class="formInput"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">采购方式:</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"> <span name="editable-input" style="display:inline-block;padding:2px;" class="selectinput"> <select name="m:hj_goods_purchaseds:type" lablename="采购方式" controltype="select" validate="{empty:false}" val="${hjGoodsPurchaseds.type}"> <option value="1">委托办公室</option> <option value="0">请选择采购方式</option> <option value="2">部门自行采购</option> </select> </span> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">备注:</td> 
   <td class="formInput" rowspan="1" colspan="3" style="word-break: break-all;"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <textarea name="m:hj_goods_purchaseds:remarks" lablename="备注" class="l-textarea" rows="5" cols="40" validate="{maxlength:1000}" isflag="tableflag">${hjGoodsPurchaseds.remarks}</textarea> </span> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">附件:</td> 
   <td class="formInput" rowspan="1" colspan="3"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">现场负责人审核:</td> 
   <td class="formInput" rowspan="1" colspan="3" style="word-break: break-all;"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">部门副经理核定:</td> 
   <td class="formInput" rowspan="1" colspan="3" style="word-break: break-all;"></td> 
  </tr> 
 </tbody> 
</table>  
<div type="subtable" tablename="hjwppurchaselist" tabledesc="物品采购信息" show="true" parser="rowmodeedit" id="hjwppurchaselist" formtype="edit"> 
 <table class="listTable" border="0" cellpadding="2" cellspacing="0"> 
  <tbody> 
   <tr class="toolBar firstRow"> 
    <td colspan="4"><a class="link add" href="javascript:;">添加</a></td> 
   </tr> 
   <tr class="headRow"> 
    <th style="word-break: break-all;">物品名称</th> 
    <th style="word-break: break-all;">规格型号</th> 
    <th style="word-break: break-all;">数量</th> 
    <th style="word-break: break-all;">质量要求</th> 
   </tr> 
   <c:forEach items="${hjwppurchaselistList}" var="hjwppurchaselist" varStatus="status">
    <tr class="listRow" type="subdata"> 
     <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:hjwppurchaselist:name" lablename="物品名称" class="inputText" value="${hjwppurchaselist.name}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
     <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:hjwppurchaselist:specification" lablename="规格型号" class="inputText" value="${hjwppurchaselist.specification}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
     <td> <input name="s:hjwppurchaselist:number" type="text" value="${hjwppurchaselist.number}" showtype="{"decimalValue":"0"}" validate="{number:true,maxIntLen:14,maxDecimalLen:0}" /> </td> 
     <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:hjwppurchaselist:price" lablename="质量要求" class="inputText" value="${hjwppurchaselist.price}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
    </tr>
   </c:forEach> 
   <tr class="listRow" type="append" style="display:none;"> 
    <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:hjwppurchaselist:name" lablename="物品名称" class="inputText" value="${hjwppurchaselist.name}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
    <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:hjwppurchaselist:specification" lablename="规格型号" class="inputText" value="${hjwppurchaselist.specification}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
    <td> <input name="s:hjwppurchaselist:number" type="text" value="${hjwppurchaselist.number}" showtype="{"decimalValue":"0"}" validate="{number:true,maxIntLen:14,maxDecimalLen:0}" /> </td> 
    <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:hjwppurchaselist:price" lablename="质量要求" class="inputText" value="${hjwppurchaselist.price}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
   </tr>
  </tbody> 
 </table> 
 <input name="s:hjwppurchaselist:id" type="hidden" pk="true" value="" />
</div>
