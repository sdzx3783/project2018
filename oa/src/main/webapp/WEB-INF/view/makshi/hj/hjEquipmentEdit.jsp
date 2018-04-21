<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>编辑 qq</title>
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
			var frm=$('#hjEquipmentForm').form();
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
							$('#hjEquipmentForm').submit();
						});
					}else{
						frm.handleFieldName();
						$('#hjEquipmentForm').submit();
					}
				}
			});
			$("a.run").click(function() {
				frm.ajaxForm(options);
				$("#saveData").val(0);
				$('#hjEquipmentForm').attr("action","run.ht");
				if(frm.valid()){
					if(WebSignPlugin.hasWebSignField){
						WebSignPlugin.submit();
					}
					data=CustomForm.getData();
					//设置表单数据
					$("#formData").val(data);
					frm.handleFieldName();
					OfficePlugin.submit();
					$('#hjEquipmentForm').submit();
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
						window.location.href = "${ctx}/makshi/hj/hjEquipment/list.ht";
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
					<c:when test="${not empty hjEquipmentItem.id}">
						<span class="tbar-label"><span></span>编辑qq</span>
					</c:when>
					<c:otherwise>
						<span class="tbar-label"><span></span>添加qq</span>
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
		<form id="hjEquipmentForm" method="post" action="save.ht">
			<div type="custform">
				<div class="panel-detail">
					<table class="formTable" border="1" cellspacing="0" cellpadding="2"
						data-sort="sortDisabled" parser="addpermission">
						<tbody>
							<tr class="firstRow">
								<td class="formHead" style="-ms-word-break: break-all;"
									colspan="4">设备维修申请</td>
							</tr>
							<tr>
								<td align="right" class="formTitle"
									style="width: 15%; -ms-word-break: break-all;">提交人:</td>
								<td class="formInput" style="width: 35%;"><br /> <input
									name="m:hj_equipment:name" type="text" ctltype="selector"
									class="user" lablename="提交人" value="${hjEquipment.name}"
									validate="{empty:false,required:true}" readonly="readonly"
									showcuruser="0" /></td>
								<td align="right" class="formTitle"
									style="width: 15%; -ms-word-break: break-all;">提交日期:</td>
								<td class="formInput" style="width: 35%;"><br /> <input
									name="m:hj_equipment:date" type="text" class="Wdate"
									displaydate="0" datefmt="yyyy-MM-dd"
									value="<fmt:formatDate value='${hjEquipment.date}' pattern='yyyy-MM-dd'/>"
									validate="{empty:false,required:true}" /></td>
							</tr>
							<tr>
								<td align="right" class="formTitle"
									style="width: 15%; -ms-word-break: break-all;">所属部门:</td>
								<td class="formInput" style="width: 35%;"><input
									name="m:hj_equipment:department" type="text" ctltype="selector"
									class="org" lablename="所属部门" value="${hjEquipment.department}"
									validate="{empty:false,required:true}" readonly="readonly"
									showcurorg="0" /></td>
								<td style="width: 15%; word-break: break-all;" class="formTitle"
									align="right">项目名称:</td>
								<td style="width: 35%" class="formInput"><input type="text"
									name="m:hj_equipment:project_name" lablename="项目名称"
									class="inputText" value="${hjEquipment.project_name}"
									validate="{maxlength:50}" isflag="tableflag" /></td>
							</tr>
							<tr>
								<td style="width: 15%; word-break: break-all;" class="formTitle"
									align="right">设备名称:</td>
								<td style="width: 35%" class="formInput"><input type="text"
									name="m:hj_equipment:n_name" lablename="设备名称"
									class="inputText" value="${hjEquipment.n_name}"
									validate="{maxlength:50}" isflag="tableflag" /></td>
								<td align="right" class="formTitle"
									style="width: 15%; word-break: break-all;">维修内容:</td>
								<td class="formInput" style="width: 35%;"><br /> <span
									name="editable-input" style="display: inline-block;"
									isflag="tableflag"> <textarea
											name="m:hj_equipment:content" lablename="维修内容"
											class="l-textarea" rows="3" cols="40"
											validate="{maxlength:1000}" isflag="tableflag">${hjEquipment.content}</textarea>
								</span></td>
							</tr>
							<tr>
								<td align="right" class="formTitle"
									style="width: 15%; word-break: break-all;">数量:</td>
								<td class="formInput" style="width: 35%; word-break: break-all;">
									<span name="editable-input" style="display: inline-block;"
									isflag="tableflag"> <input type="text"
										name="m:hj_equipment:quantity" lablename="数量"
										class="inputText" value="${hjEquipment.quantity}"
										validate="{maxlength:50,正整数:true}" isflag="tableflag" />
								</span>
								</td>
								<td style="width: 15%; word-break: break-all;" class="formTitle"
									align="right">维修单位:</td>
								<td style="width: 35%" class="formInput"><input type="text"
									name="m:hj_equipment:maintaining_unit" lablename="维修单位"
									class="inputText" value="${hjEquipment.maintaining_unit}"
									validate="{maxlength:50}" isflag="tableflag" /></td>
							</tr>
							<tr>
								<td style="width: 15%; word-break: break-all;" class="formTitle"
									align="right">维修费用:</td>
								<td style="width: 35%" class="formInput"><input type="text"
									name="m:hj_equipment:cost" lablename="维修费用"
									class="inputText" value="${hjEquipment.cost}"
									validate="{maxlength:50}" isflag="tableflag" /></td>
							</tr>
							<tr>
								<td align="right" class="formTitle"
									style="width: 15%; -ms-word-break: break-all;">备注:</td>
								<td class="formInput"
									style="width: 35%; -ms-word-break: break-all;" rowspan="1"
									colspan="3"><span name="editable-input"
									style="display: inline-block;" isflag="tableflag"> <input
										type="text" name="m:hj_equipment:note" lablename="备注"
										class="inputText" value="${hjEquipment.note}"
										validate="{maxlength:50}" isflag="tableflag" />
								</span></td>
							</tr>
							<tr>
								<td style="width: 15%; word-break: break-all;" class="formTitle"
									align="right">附件:</td>
								<td style="width: 35%" class="formInput" rowspan="1" colspan="3">
									<input ctltype="attachment" right="w"
									name="m:hj_equipment:enclosure" isdirectupload="0"
									value='${hjEquipment.enclosure}' validatable="false"
									validate="{&quot;maxlength&quot;:&quot;500&quot;}" />
								</td>
							</tr>
							<%-- <tr>
								<td align="right" class="formTitle"
									style="width: 15%; word-break: break-all;">现场负责人审核:</td>
								<td class="formInput" style="width: 35%; word-break: break-all;"
									rowspan="1" colspan="3"><br /> <input
									name="m:hj_equipment:charger" type="text" ctltype="selector"
									class="users" lablename="现场负责人审核"
									value="${hjEquipment.charger}" validate="{empty:false}"
									readonly="readonly" /></td>
							</tr>
							<tr>
								<td align="right" class="formTitle"
									style="width: 15%; word-break: break-all;">维修主管审核:</td>
								<td class="formInput" style="width: 35%; word-break: break-all;"
									rowspan="1" colspan="3"><br />
									<input name="m:hj_equipment:mai_supervisor" type="text" ctltype="selector"
									class="users" lablename="维修主管审核"
									value="${hjEquipment.mai_supervisor}" validate="{empty:false}"
									readonly="readonly" /></td>
							</tr>
							<tr>
								<td align="right" class="formTitle"
									style="width: 15%; word-break: break-all;">部门副经理审核:</td>
								<td class="formInput" style="width: 35%; word-break: break-all;"
									rowspan="1" colspan="3"><br /><input name="m:hj_equipment:deputy_manager" type="text" ctltype="selector"
									class="users" lablename="部门副经理审核"
									value="${hjEquipment.deputy_manager}" validate="{empty:false}"
									readonly="readonly" />
									</td>
							</tr>--%>
							<tr style="display: none">
								<td class="formInput" colspan="1" rowspan="1"><br /></td>
								<td class="formInput" colspan="1" rowspan="1"
									style="word-break: break-all;"><span name="editable-input"
									style="display: inline-block;" isflag="tableflag"> <input
										type="text" name="m:hj_equipment:projectTaskId"
										lablename="项目任务id" class="inputText"
										value="${hjEquipment.projectTaskId}" validate="{maxlength:20}"
										isflag="tableflag" />
								</span></td>
								<td class="formInput" colspan="1" rowspan="1"><br /></td>
								<td class="formInput" colspan="1" rowspan="1"><br /></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<input type="hidden" name="id" value="${hjEquipment.id}" /> <input
				type="hidden" id="saveData" name="saveData" /> <input type="hidden"
				name="executeType" value="start" />
		</form>
</body>
</html>
