<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>编辑 部门内部物资管理</title>
<%@include file="/codegen/include/customForm.jsp"%>
<%@include file="/commons/include/ueditor.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/AttachMent.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/TablePermission.js"></script>
<script type="text/javascript">
	$(function() {
		var options = {};
		if (showResponse) {
			options.success = showResponse;
		}
		var frm = $('#hdMaterialManagementForm').form();
		$("a.save").click(function() {
			 if (frm.item_name == '') {
        		 $.ligerDialog.warn('物资名称不能为空！','提示信息');
        		return false;}
			frm.ajaxForm(options);
			$("#saveData").val(1);
			if (frm.valid()) {
				//如果有编辑器的情况下
				$("textarea[name^='m:'].myeditor").each(function(num) {
					var name = $(this).attr("name");
					var data = getEditorData(editor[num]);
					$("textarea[name^='" + name + "']").val(data);
				});

				if (WebSignPlugin.hasWebSignField) {
					WebSignPlugin.submit();
				}
				if (OfficePlugin.officeObjs.length > 0) {
					OfficePlugin.submit(function() {
						frm.handleFieldName();
						$('#hdMaterialManagementForm').submit();
					});
				} else {
					frm.handleFieldName();
					$('#hdMaterialManagementForm').submit();
				}
			}
		});
	});

	function showResponse(responseText) {
		var obj = new com.hotent.form.ResultMessage(responseText);
		if (obj.isSuccess()) {
			$.ligerDialog
					.confirm(
							obj.getMessage() + ",是否继续操作",
							"提示信息",
							function(rtn) {
								if (rtn) {
									window.location.href = window.location.href;
								} else {
									window.location.href = "${ctx}/makshi/river/hdMaterialManagement/list.ht";
								}
							});
		} else {
			$.ligerDialog.error(obj.getMessage(), "提示信息");
		}
	}
</script>
<style>
	.oa-textarea{
		border: 0 !important;
	}
</style>
</head>
<body class="oa-mw-page">

	<div class="oa-project-title">
	   
	</div>

	<div class="oa-mg20">
		<a class="oa-button oa-button--primary oa-button--blue save" id="dataFormSave" href="javascript:;">保存</a>
	    <a class="oa-button oa-button--primary oa-button--blue" href="list.ht">返回</a>
	</div>


	<div class="oa-mg20">
		<form id="hdMaterialManagementForm" method="post" action="save.ht">
			<div type="custform">
				<div class="panel-detail">
					<table class="oa-table--default" border="1" cellspacing="0" cellpadding="2"
						parser="addpermission" data-sort="sortDisabled">
							<caption>部门内部物资管理</caption>
							<tr>
								<td>物资名称:</td>
								<td>
									<div class="oa-input-wrap oa-input-wrap--ib">
										<input type="text"name="m:hd_material_management:item_name" lablename="物品名称"
											class="oa-input" value="${hdMaterialManagement.item_name}"
											validate="{required:true,maxlength:50}" isflag="tableflag" /> 
									</div>
								</td>
								</td>
								<td>规格型号:</td>
								<td>
									<div class="oa-input-wrap oa-input-wrap--ib">
										<input type="text"
											name="m:hd_material_management:model" lablename="规格型号"
											class="oa-input" value="${hdMaterialManagement.model}"
											validate="{maxlength:50}" isflag="tableflag" />
									</div>
								</td>
							</tr>
							<tr>
								<td>数量:</td>
								<td>
									<div class="oa-input-wrap oa-input-wrap--ib">
										<input
											class="oa-input"
											name="m:hd_material_management:number" type="text"
											value="${hdMaterialManagement.number}" showtype="{"
											decimalValue":"0"}" validate="{required:true,number:true,maxIntLen:14,maxDecimalLen:0}" />
									</div>
								</td>
								<td>权属单位:</td>
								<td>
									<div class="oa-input-wrap oa-input-wrap--ib">
										<input
											type="text" name="m:hd_material_management:ownership_unit"
											lablename="权属单位" class="oa-input"
											value="${hdMaterialManagement.ownership_unit}"
											validate="{maxlength:50}" isflag="tableflag" />										
									</div>
								</td>
							</tr>
							<tr>
								<td>项目部:</td>
								<td>
									<div class="oa-input-wrap oa-input-wrap--ib">
										<input name="m:hd_material_management:department" type="text"
											ctltype="selector" class="org oa-input oa-hidden-trigger" lablename="项目部"
											value="${hdMaterialManagement.department}"
											validate="{empty:false}" readonly="readonly" showcurorg="0" />										
									</div>
									<button class="oa-button-label oa-trigger-hidden-button" type="button">选择部门</button>
								</td>
								<td>状态:</td>
								<td>
									<div class="oa-select-wrap  oa-select-wrap--ib">
										<select name="m:hd_material_management:state" lablename="状态"
											controltype="select" validate="{empty:false}" class="oa-select"
											val="${hdMaterialManagement.state}">
												<option value="0">在用</option>
												<option value="1">报废</option>
												<option value="2">回收</option>
										</select>										
									</div>
								</td>
							</tr>
							<tr>
								<td>使用人:</td>
								<td>
									<div class="oa-input-wrap oa-input-wrap--ib">
										<input name="m:hd_material_management:user" type="text"
											ctltype="selector" class="users oa-input oa-hidden-trigger" lablename="使用人"
											value="${hdMaterialManagement.user}" validate="{empty:false}"
											readonly="readonly" />										
									</div>
									<button class="oa-button-label oa-trigger-hidden-button" type="button">选择人员</button>
								</td>
								<td>备注:</td>
								<td>
									<div class="oa-textarea-wrap">
										<textarea name="m:hd_material_management:remarks" lablename="备注"
											class="oa-textarea" rows="3" cols="40"
											validate="{maxlength:1000}" isflag="tableflag">${hdMaterialManagement.remarks}</textarea>									
									</div>
								</td>
							</tr>
	
							<tr>
								<td>附件:</td>
								<td
									rowspan="1" colspan="3"><input ctltype="attachment"
									name="enclosure" lablename="附件" isdirectupload="0" right="w"
									value='${hdMaterialManagement.enclosure}' validatable="false"
									validate="{&quot;maxlength&quot;:&quot;500&quot;}" /></td>
							</tr>
					</table>
				</div>
			</div>
			<input type="hidden" name="id" value="${hdMaterialManagement.id}" />
			<input type="hidden" id="saveData" name="saveData" /> <input
				type="hidden" name="executeType" value="start" />
		</form>

	</div>


<script>
    $(function(){
        $(".oa-trigger-hidden-button").on("click", function(){
            $(this).parent("td").find("a.oa-hidden-trigger").click();
        });
    });
    
</script>
</body>
</html>
