<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 资产申购汇总表</title>
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
			var frm=$('#assetApplicationAllForm').form();
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
							$('#assetApplicationAllForm').submit();
						});
					}else{
						frm.handleFieldName();
						$('#assetApplicationAllForm').submit();
					}
				}
			});
			$("a.run").click(function() {
				frm.ajaxForm(options);
				$("#saveData").val(0);
				$('#assetApplicationAllForm').attr("action","run.ht");
				if(frm.valid()){
					if(WebSignPlugin.hasWebSignField){
						WebSignPlugin.submit();
					}
					data=CustomForm.getData();
					//设置表单数据
					$("#formData").val(data);
					frm.handleFieldName();
					OfficePlugin.submit();
					$('#assetApplicationAllForm').submit();
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
						window.location.href = "${ctx}/makshi/assetapplicationall/assetApplicationAll/list.ht";
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
			    <c:when test="${not empty assetApplicationAllItem.id}">
			        <span class="tbar-label"><span></span>编辑资产申购汇总表</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label"><span></span>添加资产申购汇总表</span>
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
	<form id="assetApplicationAllForm" method="post" action="save.ht">
		<div type="custform">
			<div class="panel-detail">
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
			</div>
		</div>
		<input type="hidden" name="id" value="${assetApplicationAll.id}"/>
		<input type="hidden" id="saveData" name="saveData"/>
		<input type="hidden" name="executeType"  value="start" />
	</form>
</body>
</html>
