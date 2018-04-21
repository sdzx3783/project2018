<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 办公用品申购表</title>
	<%@include file="/codegen/include/customForm.jsp" %>
	<%@include file="/commons/include/ueditor.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/FlowDetailWindow.js"></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#stationeryPurchaseForm').form();
			$("a.save").click(function() {
				frm.ajaxForm(options);
				$("#saveData").val(1);
				if(frm.valid()){
					//如果有编辑器的情况下
					$("textarea[name^='m:'].myeditor").each(function(num) {
						var name=$(this).attr("name");
						var data=getEditorData(editor[num]);
						$("textarea[name^='"+name+"']").val(data);
					});
					
					if(WebSignPlugin.hasWebSignField){
						WebSignPlugin.submit();
					}
					if(OfficePlugin.officeObjs.length>0){
						OfficePlugin.submit(function(){
							frm.handleFieldName();
							frm.sortList();
							$('#stationeryPurchaseForm').submit();
						});
					}else{
						frm.handleFieldName();
						frm.sortList();
						$('#stationeryPurchaseForm').submit();
					}
				}
			});
			$("a.run").click(function() {
				frm.ajaxForm(options);
				$("#saveData").val(0);
				$('#stationeryPurchaseForm').attr("action","run.ht");
				if(frm.valid()){
					if(WebSignPlugin.hasWebSignField){
						WebSignPlugin.submit();
					}
					data=CustomForm.getData();
					//设置表单数据
					$("#formData").val(data);
					frm.handleFieldName();
					OfficePlugin.submit();
					frm.sortList();
					$('#stationeryPurchaseForm').submit();
				}
			});
		});
		
		function showResponse(responseText) {
			var obj = new com.hotent.form.ResultMessage(responseText);
			if (obj.isSuccess()) {
				$.ligerDialog.confirm(obj.getMessage()+",是否继续操作","提示信息", function(rtn) {
					if(rtn){
						window.location.href = window.location.href;
					}else{
						window.location.href = "${ctx}/makshi/stationery/stationeryPurchase/list.ht";
					}
				});
			} else {
				$.ligerDialog.error(obj.getMessage(),"提示信息");
			}
		}
	</script>
</head>
<body>
<div class="panel" style="height:100%;overflow:auto;">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${not empty stationeryPurchaseItem.id}">
			        <span class="tbar-label"><span></span>编辑办公用品申购表</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label"><span></span>添加办公用品申购表</span>
			    </c:otherwise>	
		    </c:choose>
		</div>
		<div class="panel-toolbar">
			<div class="toolBar">
				<div class="group"><a class="link save" id="dataFormSave" href="javascript:;"><span></span>保存</a></div>
				<div class="l-bar-separator"></div>
				<c:if test="${runId!=0}">
					<div class="group"><a class="link detail"  onclick="FlowDetailWindow({runId:${runId}})" href="javascript:;" ><span></span>流程明细</a></div>
					<div class="l-bar-separator"></div>
				</c:if>
				<div class="group"><a class="link back" href="list.ht"><span></span>返回</a></div>
			</div>
		</div>
	</div>
	<form id="stationeryPurchaseForm" method="post" action="save.ht">
		<div type="custform">
			<div class="panel-detail">
				<table class="formTable" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
 <tbody>
  <tr class="firstRow">
   <td class="formHead" colspan="4" style="word-break: break-all;">办公用品及耗材申购表</td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">申请人:</td>
   <td style="width: 35%" class="formInput">${stationeryPurchase.user_name}</td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">一级部门:</td>
   <td style="width: 35%" class="formInput"><br />
   	   <span name="editable-input" style="display:inline-block;" isflag="tableflag"> 
	  	 <input type="text" name="m:stationery_purchase:first_department" lablename="一级部门" class="inputText" value="${stationeryPurchase.first_department}" validate="{maxlength:50}" isflag="tableflag" /> 
	   </span> 
   </td>
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
			</div>
		</div>
		<input type="hidden" name="id" value="${stationeryPurchase.id}"/>
		<input type="hidden" id="saveData" name="saveData"/>
		<input type="hidden" name="executeType"  value="start" />
	</form>
</body>
</html>
