<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>编辑 问卷调查</title>
<%@include file="/codegen/include/customForm.jsp"%>
<%@include file="/commons/include/ueditor.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
<script type="text/javascript"
	src="${ctx}/servlet/ValidJs?form=messageSend"></script>
<script type="text/javascript"
	src="${ctx }/js/hotent/platform/system/SysDialog.js"></script>
<script type="text/javascript"
	src="${ctx }/js/hotent/platform/system/MsgDialog.js"></script>
<script type="text/javascript">
	$(function() {
		var options = {};
		if (showResponse) {
			options.success = showResponse;
		}

		function isDate(obj) {
	        return Object.prototype.toString.call(obj) === '[object Date]';
	    }

		function checkTime(){
			var $startTime = $('.start-time');
			var $endTime = $('.end-time');
			var DELAY = 1000;
			var pattern = /^(?:19|20)[0-9][0-9]-(?:(?:0[1-9])|(?:1[0-2]))-(?:(?:[0-2][1-9])|(?:[1-3][0-1])) (?:(?:[0-2][0-3])|(?:[0-1][0-9])):[0-5][0-9]:[0-5][0-9]$/

			if(pattern.test($.trim($startTime.val())) && pattern.test($.trim($endTime.val()))){

				var start = $.trim($startTime.val()).replace(/-/g, '/');
				var end = $.trim($endTime.val()).replace(/-/g, '/');

				var startTime = new Date(start);
				var endTime = new Date(end);

				if((endTime.getTime() - startTime.getTime()) < DELAY){
					$.ligerDialog.warn('投票开始时间不能小于结束时间!', '提示信息');
					return false;
				}

				return true;
			}else{
				$.ligerDialog.warn('请填写投票起止时间!', '提示信息');
			}

			return false;
		}

		var frm = $('#questionnaireForm').form();
		$("a.save").click(function() {
					
					// 检查开始和结束时间是否冲突
					if(!checkTime()){
						return;
					}

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
						if ($("#receiverName").val() == ""
								&& $('#receiverOrgName').val() == "") {
							$.ligerDialog.warn('请选择参与员工或部门！', '提示信息');
							return;
						}
						if (OfficePlugin.officeObjs.length > 0) {
							OfficePlugin.submit(function() {
								frm.handleFieldName();
								$('#detailList').val(getDetailList());
								$('#questionnaireForm').submit();
							});
						} else {
							frm.handleFieldName();
							$('#detailList').val(getDetailList());
							$('#questionnaireForm').submit();
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
									window.location.href = "${ctx}/makshi/questionnaire/questionnaire/list.ht";
								}
							});
		} else {
			$.ligerDialog.error(obj.getMessage(), "提示信息");
		}
	}
</script>
<style>
table.formTable {
	table-layout: fixed;
	width: 100%;
	border: 0;
	margin-bottom: 12px;
}

.panel-toolbar {
	margin-top: 0;
}

.panel-detail {
	margin-left: 16px;
	margin-right: 16px;
}

.questionTable {
	table-layout: fixed;
	width: 100%;
	margin-bottom: 20px;
}

.questionTable td, .questionTable th {
	line-height: 49px;
	border: 1px solid #dadfed;
}

.questionTable th {
	text-align: right;
	padding: 0 10px;
	width: 230px;
}

.btns {
	margin-bottom: 12px;
}

.questionTable td {
	padding: 0 6px;
}

.new-item {
	padding: 0 10px;
	height: 34px;
	line-height: 34px;
	width: 255px;
	border: 1px solid #dadfed;
}

.new-item--sm {
	width: 54px;
}

.btn {
	text-align: left;
	border: 0;
	color: #fff;
	padding: 3px 15px 3px 15px;
	background: #478de4;
	-moz-box-shadow: 0 1px 1px rgba(0, 0, 0, 0.15);
	-webkit-box-shadow: 0 1px 1px rgba(0, 0, 0, 0.15);
	box-shadow: 0 1px 1px rgba(0, 0, 0, 0.15);
	border-radius: 3px 3px 3px 3px;
	cursor: pointer;
	line-height: 23px;
	display: inline-block;
}

.btn--warning {
	background: #ff6161;
}

.new-btns {
	margin-left: 40px;
}

.vm {
	vertical-align: middle;
}

.formInput {
	line-height: 49px;
}

table.formTable input[type="text"] {
	margin: 0;
	padding: 0 10px;
	height: 34px !important;
	line-height: 34px !important;
	width: 255px;
}

.formTable th {
	text-align: right;
	padding: 0 10px;
	width: 230px;
	border: 1px solid #dadfed;
}

table.formTable td {
	padding: 0 6px;
}

a.link.back, .panel-toolbar a.link.back span {
	background: none;
}

.icon-arrow-back {
	font-size: 16px;
}
</style>
</head>
<body>
	<div class="panel" style="height: 100%; overflow: auto;">
		<div class="panel-top">
			<div class="tbar-title">
				<c:choose>
					<c:when test="${not empty questionnaire.id}">
						<span class="tbar-label"><span></span>编辑问卷调查</span>
					</c:when>
					<c:otherwise>
						<span class="tbar-label"><span></span>添加问卷调查</span>
					</c:otherwise>
				</c:choose>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<a class="link save" id="dataFormSave" href="javascript:;"><span></span>
						<c:choose>
					<c:when test="${not empty questionnaire.id}">
						保存
					</c:when>
					<c:otherwise>
						创建
					</c:otherwise>
				</c:choose>
						</a>
					</div>
					<div class="l-bar-separator"></div>
					<div class="group">
						<a class="link back" href="list.ht"><span
							class="icon-arrow-back"></span>返回</a>
					</div>
				</div>
			</div>
		</div>
		<form id="questionnaireForm" method="post" action="save.ht">
			<div type="custform">
				<div class="panel-detail">
					<table cellpadding="2" cellspacing="0" border="1" class="formTable"
						parser="addpermission">
						<tbody>
							<tr>
								<th class="formInput"><span>标题</span></th>
								<td class="formInput"><span name="editable-input"
									style="display: inline-block;" isflag="tableflag"> <input
										type="text" name="m:questionnaire:title" lablename="标题"
										class="inputText" value="${questionnaire.title}"
										validate="{required:true,maxlength:100}" isflag="tableflag" />
								</span></td>
							</tr>
							<tr>
								<th class="formInput"><span>描述</span></th>
								<td class="formInput"><span name="editable-input"
									style="display: inline-block;" isflag="tableflag"> <input
										type="text" name="m:questionnaire:desc" lablename="描述"
										style="width: 630px;" class="inputText"
										value="${questionnaire.desc}" isflag="tableflag"
										validate="{required:true}" />
								</span></td>
							</tr>
								<tr>
								<th class="formInput">参与部门:</th>
								<td><input id="receiverOrgName" type="text"
									class="inputText" name="receiverOrgName"
									size="80" readonly="readonly"
									value="${questionnaire.questionnaireReceiverOrgName}" /> <a
									href="javascript:;" onclick="showOrgDialog()" class="link get">选择</a>
									<a href="javascript:;" onclick="reSetOrg()" class="link clean">清空</a>
									<input type="hidden" id="receiverOrgId" name="receiverOrgId"
									value="${questionnaire.questionnaireReceiverOrg}" class="inputText" /></td>
							</tr>
							<%-- <tr>
								<th class="formInput">参与员工:</th>
								<td><input id="receiverName" class="inputText" type="text"
									name="receiverName" size="80" readonly="readonly"
									value="${questionnaire.questionnaireReceiverPerName}" /> <a href="javascript:;"
									onclick="addClick()" class="link get">选择</a> <a
									href="javascript:;" onclick="addLinkman()" class="link get">常用联系人</a>

									<a href="javascript:;" onclick="reSet()" class="link clean">清空</a>
									<input type="hidden" id="receiverId" name="receiverId" value="${questionnaire.questionnaireReceiverPer}"
									class="inputText" /> <input type="hidden" id="receiveType"
									name="receiveType" value="" class="inputText" /></td>
							</tr> --%>
						
							<tr>
								<th class="formInput"><span>开始时间</span></th>
								<td class="formInput"><span name="editable-input"
									style="display: inline-block;" isflag="tableflag"> 
										<input type="text" class="time-limit start-time" name="m:questionnaire:begin_date"
										lablename="描述"
										value="<fmt:formatDate value="${questionnaire.begin_date}" pattern='yyyy-MM-dd HH:mm:ss'/>"
										class="inputText" isflag="tableflag"
										onFocus="WdatePicker({isShowClear:false,readOnly:false,dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d'})"
										validate="{required:true,date:true}" />&nbsp;结束时间 <input
										type="text" class="time-limit end-time" name="m:questionnaire:end_date" lablename="描述"
										value="<fmt:formatDate value="${questionnaire.end_date}" pattern='yyyy-MM-dd HH:mm:ss'/>"
										class="inputText" isflag="tableflag"
										onFocus="WdatePicker({isShowClear:false,readOnly:false,dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d'})"
										validate="{required:true,date:true}" />&nbsp;
								</span></td>
							</tr>
							<%-- <tr>
								<th class="formInput"><span>重复投票时间</span></th>
								<td class="formInput"><span name="editable-input"
									style="display: inline-block;" isflag="tableflag"> <input
										type="text" name="m:questionnaire:repeat_time" lablename="描述"
										class="inputText" value="${questionnaire.repeat_time}"
										isflag="tableflag" />
								</span>单位：小时。为空禁止重复投票；0为不限制重复投票。</td>
							</tr> --%>
							<tr>
								<th class="formInput"><span>限制IP</span></th>
								<td class="formInput"><input type="radio" name="limit_ip"
									value="true"
									<c:if test='${questionnaire.limit_ip==true}'>checked="checked"</c:if> />是
									<input type="radio" name="limit_ip" value="false"
									<c:if test='${questionnaire.id==null}'>checked="checked"</c:if>
									<c:if test='${questionnaire.limit_ip==false}'>checked="checked"</c:if> />否</td>
							</tr>
							<tr>
								<th class="formInput"><span>限制COOKIE</span></th>
								<td class="formInput"><input type="radio"
									name="limit_cookie" value="true"
									<c:if test='${questionnaire.limit_cookie==true}'>checked="checked"</c:if> />是
									<input type="radio" name="limit_cookie" value="false"
									<c:if test='${questionnaire.id==null}'>checked="checked"</c:if>
									<c:if test='${questionnaire.limit_cookie==false}'>checked="checked"</c:if> />否
									<span>(可以识别同一IP的不同电脑)</span>
									</td>
							</tr>
						</tbody>
					</table>

					<div class="btns">
						<input type="button" class="btn" onclick="addTable()"
							value="新增单选问题"> <input type="button" class="btn"
							onclick="addTable2()" value="新增多选问题">
							<input type="button" class="btn"
							onclick="addTable3()" value="新增问答题">
					</div>

					<div id="tables">
						<c:forEach items="${questionnaire.questionnaireQuestionList}"
							var="question" varStatus="i">
							<table class="questionTable">
								<tr>
									<th>
										<span>${i.index+1}</span> 
										<c:choose>
											<c:when test="${question.checkbox==true}">、多</c:when><c:otherwise>、单</c:otherwise></c:choose>选题标题</th>
									<td> <input
										class="new-item" name="questionnaire_question.title"
										value="${question.title }" validate="{required:true}">
										<input type="hidden" name="questionnaire_question.checkbox"
										value="${question.checkbox}"> <span class="new-btns">
											<input type="button" class="btn" onclick="addRow(this)"
											value="新增选项"> <input type="button"
											class="btn btn--warning"
											onclick="$(this).parents('table').remove()" value="删除问题">
									</span></td>

								</tr>
								<c:if test="${question.checkbox==true}">
									<tr>
										<th>用户最多选择</th>
										<td><input class="new-item new-item--sm"
											value="${question.max_choice }"
											name="questionnaire_question.max_choice"> <span
											class="vm">选项</span> <label> <input type="checkbox"
												<c:if test='${question.required==true}'>checked="checked"</c:if>
												class="vm" name="questionnaire_question.required"
												value="true"> <span class="vm">是否必填</span>
										</label></td>
									</tr>
								</c:if>

								<c:forEach items="${question.questionnaireOptionList }"
									var="option" varStatus="i">
									<tr>
										<th>选项</th>
										<td> <input
											class="new-item" value="${option.desc }"
											name="questionnaire_option.desc" validate="{required:true}"><span
											class="new-btns"> <c:if test="${i.index>0 }">
													<input type="button" class="btn" onclick="removeRow(this)"
														value="删除">
												</c:if></td>
										</span>
										</td>
									</tr>
								</c:forEach>

							</table>
						</c:forEach>
					</div>
				</div>
			</div>
			<input type="hidden" name="id" value="${questionnaire.id}" /> <input
				type="hidden" id="saveData" name="saveData" /> <input type="hidden"
				name="executeType" value="start" />
			<textarea style="display: none" id="detailList" name="detailList"></textarea>
		</form>
		<table id="single" class="questionTable" style="display: none;">
			<tr>
				<th><span></span>、单选题标题</th>
				<td><input class="new-item" name="questionnaire_question.title"
					validate="{required:true}"> <input type="hidden"
					name="questionnaire_question.checkbox" value="false">
					<input type="hidden" name="questionnaire_question.type"	value="0"> 
					 <input
					type="hidden" checked="checked"
					name="questionnaire_question.required" value="true"> <span
					class="new-btns"> <input type="button" class="btn"
						onclick="addRow(this);" value="新增选项"> <input type="button"
						class="btn btn--warning"
						onclick="$(this).parents('table').remove();tableSort();" value="删除问题">
				</span></td>
			</tr>
			<tr>
				<th>选项A</th>
				<td><input class="new-item" name="questionnaire_option.desc"
					validate="{required:true}"></td>
			</tr>
			<tr>
				<th>选项B</th>
				<td><input class="new-item" name="questionnaire_option.desc"
					validate="{required:true}"> <span class="new-btns">
						<input type="button" class="btn" onclick="removeRow(this);"
						value="删除"></td>
				</span>
				</td>
			</tr>
			<tr>
				<th>选项C</th>
				<td><input class="new-item" name="questionnaire_option.desc"
					validate="{required:true}"> <span class="new-btns">
						<input type="button" class="btn" onclick="removeRow(this);"
						value="删除"></td>
				</span>
			</tr>

		</table>
		<table id="checkbox" class="questionTable" style="display: none;">
			<tr>
				<th><span></span>、多选题标题</th>
				<td><input class="new-item" name="questionnaire_question.title"
					validate="{required:true}"> <input type="hidden"
					name="questionnaire_question.checkbox" value="true"> 
					<input type="hidden" name="questionnaire_question.type"	value="0"> 
					<span
					class="new-btns"> <input type="button" class="btn"
						onclick="addRow(this);" value="新增选项"> <input type="button"
						class="btn btn--warning"
						onclick="$(this).parents('table').remove();tableSort();" value="删除问题">
				</span></td>

			</tr>
			<tr>
				<th>用户最多选择</th>
				<td><input class="new-item new-item--sm"
					name="questionnaire_question.max_choice"> <span class="vm">选项</span>
					<label> <input type="checkbox" class="vm"
						name="questionnaire_question.required" value="true"> <span
						class="vm">是否必填</span>
				</label></td>
			</tr>

			<tr>
				<th>选项A</th>
				<td><input class="new-item" name="questionnaire_option.desc"
					validate="{required:true}"></td>
			</tr>
			<tr>
				<th>选项B</th>
				<td><input class="new-item" name="questionnaire_option.desc"
					validate="{required:true}"><span class="new-btns"> <input
						type="button" class="btn" onclick="removeRow(this);" value="删除"></td>
				</span>
				</td>
			</tr>
			<tr>
				<th>选项C</th>
				<td><input class="new-item" name="questionnaire_option.desc"
					validate="{required:true}"> <span class="new-btns">
						<input type="button" class="btn" onclick="removeRow(this);"
						value="删除">
				</span></td>
			</tr>

		</table>
		
		
		<table id="answer" class="questionTable" style="display: none;">
			<tr>
				<th><span></span>、问答题标题</th>
				<td><input class="new-item" name="questionnaire_question.title"	validate="{required:true}"> 
					<input type="hidden" name="questionnaire_question.type"	value="1"> 
					<input type="hidden" name="questionnaire_question.checkbox" value="false"> 
				<input type="button" class="btn btn--warning"	onclick="$(this).parents('table').remove();tableSort();" value="删除问题">
				</span></td>

			</tr>
		</table>

		<script type="text/javascript">
		$(function(){
			tableSort();

		});

				function tableSort(){
				var length  = $(".questionTable").length;
				for(var i=0;i<length;i++){
					var dom = $(".questionTable").eq(i);
					$(dom).find("th span").html(i+1);
					var checkbox= $(dom).find("input[name='questionnaire_question.checkbox']").val();
					var len = $(dom).find("th").length;
					for(var j=0;j<len;j++){
						var charI = String.fromCharCode(65+j);
						if(checkbox=='true'){
							$(dom).find("th").eq(j+2).html("选项"+charI);
						}else{
							$(dom).find("th").eq(j+1).html("选项"+charI);
						}
					}
				}
			}
			function addTable() {
				if ($(".questionTable").length > 100) {
					$.ligerDialog.warn('最多100个问题！', '提示信息');
					return false;
				}
				var table = $("#single").clone(true, true);
				table.removeAttr("id");
				table.show();
				$("#tables").append(table);
				tableSort();
			}
			function addTable2() {
				if ($(".questionTable").length > 100) {
					$.ligerDialog.warn('最多100个问题！', '提示信息');
					return false;
				}

				var table = $("#checkbox").clone(true, true);
				table.removeAttr("id");
				table.show();
				$("#tables").append(table);
				tableSort();
			}
			
			function addTable3() {
				if ($(".questionTable").length > 100) {
					$.ligerDialog.warn('最多100个问题！', '提示信息');
					return false;
				}

				var table = $("#answer").clone(true, true);
				table.removeAttr("id");
				table.show();
				$("#tables").append(table);
				tableSort();
			}
			function addRow(dom) {
				var table = $(dom).parents('table');
				var checkbox= $(table).find("input[name='questionnaire_question.checkbox']").val();
				var max = 21;
				if(checkbox=='true') max = 22;
				if ($(table).find("tr").length > max) {
					$.ligerDialog.warn('最多20个选项！', '提示信息');
					return false;
				}
				$(table)
						.append(
								'<tr><th>选项</th><td><input class="new-item" name="questionnaire_option.desc"><span class="new-btns"> <input type="button"  class="btn" onclick="removeRow(this);" value="删除"></span></td></tr>');
				tableSort();
			}
			function removeRow(dom) {
				var table = $(dom).parents('table');
				$(dom).parents('tr').remove();
				tableSort();
			}
			
			function getDetailList() {
				var json = [];
				$("form .questionTable")
						.each(
								function() {
									var me = $(this), obj = {}, descs = [];
									$(
											"input[name='questionnaire_option.desc']",
											me).each(function() {
										descs.push($(this).attr("value"));
									});
									obj.desc = descs;
									obj.title = $(
											"input[name='questionnaire_question.title']",
											me).val();
									obj.checkbox = $(
											"input[name='questionnaire_question.checkbox']",
											me).val();
									obj.type = $(
											"input[name='questionnaire_question.type']",
											me).val();
									obj.max_choice = $(
											"input[name='questionnaire_question.max_choice']",
											me).val();
									var req = $(
											"input[name='questionnaire_question.required']",
											me);
									if (req.attr('checked') == 'checked') {
										obj.required = req.val();
									}

									json.push(obj);
								});
				return JSON2.stringify(json)
			}
			function dlgCallBack(userIds, fullnames, emails, mobiles, retypes) {
				$("#receiverName").val(fullnames);
				$("#receiverId").val(userIds);
				$("#receiveType").val(retypes);

			};
			function addClick(oName) {
				var selectUserIds = $("#receiverId").val();
				var selectUserNames = $("#receiverName").val();
				UserDialog({
					callback : dlgCallBack,
					selectUserIds : selectUserIds,
					selectUserNames : selectUserNames,
					isSingle : false
				});
			};
			//清空
			function reSet(obj) {
				$("#receiverName").val("");
				$("#receiverId").val("");
				$("#receiveType").val("");
			}

			// 弹出组织框
			function showOrgDialog() {
				OrgDialog({
					callback : dlgOrgCallBack,
					isSingle : false
				});
			};

			// 组织框返回数据   
			function dlgOrgCallBack(orgIds, orgNames) {
				$("#receiverOrgName").val(orgNames);
				$("#receiverOrgId").val(orgIds);
				//$("#receiveType").val(retypes);	
			}

			// 清空所选组织
			function reSetOrg(obj) {
				$("#receiverOrgName").val("");
				$("#receiverOrgId").val("");
			}

			function addLinkman() {
				LinkmanGroupDialog({
					callback : linkmanCallBack
				});
			}

			function linkmanCallBack(obj) {
				$("#receiverId").val(obj.id);
				$("#receiverName").val(obj.name);
			}
		</script>
</body>
</html>
