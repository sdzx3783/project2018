<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 资产移交表</title>
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
			var frm=$('#assetTransferForm').form();
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
							$('#assetTransferForm').submit();
						});
					}else{
						frm.handleFieldName();
						$('#assetTransferForm').submit();
					}
				}
			});
			$("a.run").click(function() {
				frm.ajaxForm(options);
				$("#saveData").val(0);
				$('#assetTransferForm').attr("action","run.ht");
				if(frm.valid()){
					if(WebSignPlugin.hasWebSignField){
						WebSignPlugin.submit();
					}
					data=CustomForm.getData();
					//设置表单数据
					$("#formData").val(data);
					frm.handleFieldName();
					OfficePlugin.submit();
					$('#assetTransferForm').submit();
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
						window.location.href = "${ctx}/makshi/asset_transfer/assetTransfer/list.ht";
					}
				});
			} else {
				$.ligerDialog.error(obj.getMessage(),"提示信息");
			}
		}
		
		$(function(){
			
			
		});
	</script>
</head>
<body>
<div class="panel" style="height:100%;overflow:auto;">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${not empty assetTransferItem.id}">
			        <span class="tbar-label"><span></span>编辑资产移交表</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label"><span></span>添加资产移交表</span>
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
	<form id="assetTransferForm" method="post" action="save.ht">
		<div type="custform">
			<div class="panel-detail">
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
			</div>
		</div>
		<input type="hidden" name="id" value="${assetTransfer.id}"/>
		<input type="hidden" id="saveData" name="saveData"/>
		<input type="hidden" name="executeType"  value="start" />
	</form>
</body>
</html>
