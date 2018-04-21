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
   <td class="formHead" colspan="4" style="word-break: break-all;">物品采购申请</td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">申请人:</td> 
   <td style="width: 35%" class="formInput"> ${hjGoodsPurchaseds.applicant} </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">申请日期:</td> 
   <td style="width: 35%" class="formInput"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">申请部门:</td> 
   <td style="width: 35%" class="formInput"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">采购方式:</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"> ${hjGoodsPurchaseds.type} </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">备注:</td> 
   <td class="formInput" rowspan="1" colspan="3" style="word-break: break-all;"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${hjGoodsPurchaseds.remarks} </span> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">附件:</td> 
   <td class="formInput" rowspan="1" colspan="3"> 
    <div name="div_attachment_container" right="r"> 
     <div class="attachement"></div> 
     <textarea style="display:none" controltype="attachment" name="attachment" lablename="附件" readonly="readonly">${hjGoodsPurchaseds.attachment}</textarea> 
    </div> </td> 
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
    <td colspan="4"></td> 
   </tr> 
   <tr class="headRow"> 
    <th style="word-break: break-all;">物品名称</th> 
    <th style="word-break: break-all;">规格型号</th> 
    <th style="word-break: break-all;">数量</th> 
    <th style="word-break: break-all;">质量要求</th> 
   </tr> 
   <c:forEach items="${hjwppurchaselistList}" var="hjwppurchaselist" varStatus="status">
    <tr class="listRow" type="subdata"> 
     <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${hjwppurchaselist.name} </span> </td> 
     <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${hjwppurchaselist.specification} </span> </td> 
     <td> ${hjwppurchaselist.number} </td> 
     <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${hjwppurchaselist.price} </span> </td> 
    </tr>
   </c:forEach> 
  </tbody> 
 </table> 
</div>
