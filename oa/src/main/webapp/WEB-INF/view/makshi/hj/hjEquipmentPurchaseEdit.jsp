<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>编辑 设备采购</title>
<%@include file="/commons/include/customForm.jsp"%>
<%@include file="/commons/include/ueditor.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
<script type="text/javascript"
	src="${ctx}/js/hotent/platform/bpm/FlowDetailWindow.js"></script>
<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#hjEquipmentPurchaseForm').form();
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
							$('#hjEquipmentPurchaseForm').submit();
						});
					}else{
						frm.handleFieldName();
						frm.sortList();
						$('#hjEquipmentPurchaseForm').submit();
					}
				}
			});
			$("a.run").click(function() {
				frm.ajaxForm(options);
				$("#saveData").val(0);
				$('#hjEquipmentPurchaseForm').attr("action","run.ht");
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
					$('#hjEquipmentPurchaseForm').submit();
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
						window.location.href = "${ctx}/makshi/hj/hjEquipmentPurchase/list.ht";
					}
				});
			} else {
				$.ligerDialog.error(obj.getMessage(),"提示信息");
			}
		}
	</script>
</head>
<body>
	<div class="panel" style="height: 100%; overflow: auto;">
		<div class="panel-top">
			<div class="tbar-title">
				<c:choose>
					<c:when test="${not empty hjEquipmentPurchaseItem.id}">
						<span class="tbar-label"><span></span>编辑设备采购</span>
					</c:when>
					<c:otherwise>
						<span class="tbar-label"><span></span>添加设备采购</span>
					</c:otherwise>
				</c:choose>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<a class="link save" id="dataFormSave" href="javascript:;"><span></span>保存</a>
					</div>
					<div class="l-bar-separator"></div>
					<c:if test="${runId!=0}">
						<div class="group">
							<a class="link detail"
								onclick="FlowDetailWindow({runId:${runId}})" href="javascript:;"><span></span>流程明细</a>
						</div>
						<div class="l-bar-separator"></div>
					</c:if>
					<div class="group">
						<a class="link back" href="list.ht"><span></span>返回</a>
					</div>
				</div>
			</div>
		</div>
		<form id="hjEquipmentPurchaseForm" method="post" action="save.ht">
			<div type="custform">
				<div class="panel-detail">
					<table class="formTable" border="1" cellspacing="0" cellpadding="2"
						parser="addpermission" data-sort="sortDisabled">
						<tbody>
							<tr class="firstRow">
								<td class="formHead" colspan="4" style="word-break: break-all;">设备采购申请</td>
							</tr>
							<tr>
								<td style="width: 15%; word-break: break-all;" class="formTitle"
									align="right">申请人:</td>
								<td style="width: 35%" class="formInput"><input
									name="m:hjEquipmentPurchase:applicant" type="text"
									ctltype="selector" class="user" lablename="申请人"
									value="${hjEquipmentPurchase.applicant}"
									validate="{empty:false}" readonly="readonly" showcuruser="0" />
								</td>
								<td style="width: 15%; word-break: break-all;" class="formTitle"
									align="right">申请日期:</td>
								<td style="width: 35%" class="formInput"><input
									name="m:hj_equipment_purchase:appli_date" type="text"
									class="Wdate" displaydate="0" datefmt="yyyy-MM-dd"
									value="<fmt:formatDate value='${hjEquipmentPurchase.appli_date}' pattern='yyyy-MM-dd'/>"
									validate="{empty:false}" /></td>
							</tr>
							<tr>
								<td style="width: 15%; word-break: break-all;" class="formTitle"
									align="right">项目名称:</td>
								<td class="formInput" style="width: 35%;"><input 
								    name="m:hj_equipment_purchase:project_name" lablename="备注" 
								    class="inputText" value="${hjEquipmentPurchase.project_name}" 
								    validate="{maxlength:50}" isflag="tableflag" />
								</td>
								<td style="width: 15%; word-break: break-all;" class="formTitle"
									align="right">采购方式:</td>
								<td style="width: 35%; word-break: break-all;" class="formInput">
									<span name="editable-input"
									style="display: inline-block; padding: 2px;"
									class="selectinput"> <select
										name="m:hj_equipment_purchase:type" lablename="采购方式"
										controltype="select" validate="{empty:false}"
										val="${hjEquipmentPurchase.type}">
											<option value="1">委托办公室</option>
											<option value="0">请选择采购方式</option>
											<option value="2">部门自行采购</option>
									</select>
								</span>
								</td>
							</tr>
							<tr>
								<td style="width: 15%; word-break: break-all;" class="formTitle"
									align="right">设备名称:</td>
								<td style="width: 35%" class="formInput"><input type="text"
									name="m:hj_equipment_purchase:name" lablename="物品名称"
									class="inputText" value="${hjEquipmentPurchase.name}"
									validate="{maxlength:50}" isflag="tableflag" /></td>
								<td style="width: 15%; word-break: break-all;" class="formTitle"
									align="right">规格型号:</td>
								<td style="width: 35%" class="formInput"><input type="text"
									name="m:hj_equipment_purchase:specification" lablename="规格型号"
									class="inputText" value="${hjEquipmentPurchase.specification}"
									validate="{maxlength:50}" isflag="tableflag" /></td>
							</tr>
							<tr>
								<td style="width: 15%; word-break: break-all;" class="formTitle"
									align="right">数量:</td>
								<td style="width: 35%" class="formInput"><input type="text"
									name="number" lablename="数量" class="inputText"
									value="${hjEquipmentPurchase.number}" validate="{maxlength:50}"
									isflag="tableflag" /></td>
								<td style="width: 15%; word-break: break-all;" class="formTitle"
									align="right">单价:</td>
								<td style="width: 35%" class="formInput"><input type="text"
									name="price" lablename="单价" class="inputText"
									value="${hjEquipmentPurchase.price}" validate="{maxlength:50}"
									isflag="tableflag" /></td>
							</tr>
							<tr>
								<td style="width: 15%; word-break: break-all;" class="formTitle"
									align="right">备注:</td>
								<td class="formInput" rowspan="1" colspan="3"
									style="word-break: break-all;"><span name="editable-input"
									style="display: inline-block;" isflag="tableflag"> <textarea
											name="m:hj_equipment_purchase:remarks" lablename="备注"
											class="l-textarea" rows="3" cols="40"
											validate="{maxlength:1000}" isflag="tableflag">${hjEquipmentPurchase.remarks}</textarea>
								</span></td>
							</tr>
							<tr>
								<td style="width: 15%; word-break: break-all;" class="formTitle"
									align="right">附件:</td>
								<td class="formInput" rowspan="1" colspan="3">
								   <input  ctltype="attachment" name="attachment" lablename="附件" isdirectupload="0" right="w" 
						                   value='${hjEquipmentPurchase.attachment}' validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}" />
									<%-- <div name="div_attachment_container" right="r">
										<div class="attachement"></div>
										<textarea style="display: none" controltype="attachment"
											name="attachment" lablename="附件" readonly="readonly">${hjEquipmentPurchase.attachment}</textarea>
									</div>--%>
								</td>
							</tr>
							<%-- <tr>
								<td style="width: 15%; word-break: break-all;" class="formTitle"
									align="right">现场负责人审核:</td>
								<td class="formInput" rowspan="1" colspan="3"
									style="word-break: break-all;"><input
									name="m:hj_equipment_purchase:person_charge" type="text"
									ctltype="selector" class="users" lablename="现场负责人审核"
									value="${hjEquipmentPurchase.person_charge}"
									validate="{empty:false}" readonly="readonly" /></td>
							</tr>
							<tr>
								<td style="width: 15%; word-break: break-all;" class="formTitle"
									align="right">部门副经理核定:</td>
								<td class="formInput" rowspan="1" colspan="3"
									style="word-break: break-all;"><input
									name="deputy_manager" type="text" ctltype="selector"
									class="users" lablename="部门副经理核定"
									value="${hjEquipmentPurchase.deputy_manager}"
									validate="{empty:false}" readonly="readonly" /></td>
							</tr>--%>
							<tr style="display:none"> 
                                <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all;"> 
                                   <span name="editable-input" style="display:inline-block;" isflag="tableflag"> 
                                   <input type="text" name="m:hj_Equ_scrapping_application:projectTaskId" lablename="项目任务id" 
                                         class="inputText" value="${hjEquipmentPurchase.projectTaskId}" 
                                        validate="{maxlength:20}" isflag="tableflag" /> 
                                   </span> 
                                </td> 
                            </tr>
						</tbody>
					</table>
					</tbody>
					</table>
					<input name="s:hjpurchase_list:id" type="hidden" pk="true" value="" />
				</div>
			</div>
	</div>
	<input type="hidden" name="id" value="${hjEquipmentPurchase.id}" />
	<input type="hidden" name="refId" value="${hjEquipmentPurchase.id}" />
	<input type="hidden" name="id1" value="${hjEquipmentPurchase.id1}" />
	<input type="hidden" id="saveData" name="saveData" />
	<input type="hidden" name="executeType" value="start" />
	</form>
</body>
</html>
