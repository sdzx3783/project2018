<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<%@include file="/codegen/include/customForm.jsp" %>
	<%@include file="/commons/include/ueditor.jsp" %>
	<title><c:if test="${not empty projectStageTask.id}">编辑任务</c:if>
		<c:if test="${empty projectStageTask.id }">新增任务</c:if></title>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/AttachMent.js"></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			AttachMent.init("w");
			var frm=$('#stagetaskForm').form();
			$("#dataFormSave").click(function() {
				frm.ajaxForm(options);
				$("#saveData").val(1);
				var _temp=$("#tasktype").val();
				if(_temp==3 && $("#isexamine").attr("checked")){
					$("#flowid").attr("validate","{'required':true,'digits':true,'maxlength':20}");
				}
				if(frm.valid()){
					
					if($("#tasktype").val()=='1'){
						var jsonArr=getFieldJson();
						if(jsonArr==null || jsonArr.length==0){
							$.ligerDialog.warn("表单字段不能为空！");
							return ;
						}
						$("#fields").val(JSON.stringify(jsonArr));
					}else{
						$("#fields").val("");
					}
					
					if($("#tasktype").val()==1){//不是资料上传类或功能节点  清空checkbox选中
						$("#isexamine").attr("checked",false);
						//$("#ismore").attr("checked",false);
					}
					if($("#tasktype").val()==2){//资料上传类
						$("#isexamine").attr("checked",false);
					}
					
					if(!$("#isexamine").attr("checked")){
						$("input[name='flowid']").eq(0).val('');
					}
					
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
							$('#stagetaskForm').submit();
						});
					}else{
						frm.handleFieldName();
						$('#stagetaskForm').submit();
					}
				}
			});
		});
		
		function showResponse(responseText) {
			var obj = new com.hotent.form.ResultMessage(responseText);
			if (obj.isSuccess()) {
				window.opener.location.reload(true);
				window.close();
			} else {
				$.ligerDialog.error(obj.getMessage(),"提示信息");
			}
			
		}
		function getFieldJson(){
			var arr=[];
			var fields=$(".fieldDiv").eq(0).find(".fieldItem");
			if(fields==null || fields.length==0){
				return arr;
			}
			for(var i=0;i<fields.length;i++){
				var obj={};
				obj['fieldname']=$(fields[i]).find("input[name='fieldname']").eq(0).val();
				obj['fieldtype']=$(fields[i]).find("select option:selected'").eq(0).val();
				var req=$(fields[i]).find(".isRequired").eq(0);
				obj['required']=0;
				if(req.attr('checked')){
					obj['required']=1;
				}
				arr.push(obj);
			}
			return arr;
		}
	</script>
	<style>

		.oa-table th{
			padding: 10px 0;
		}
		.oa-table td{
			padding: 10px 20px;
		}


		.oa-table .oa-input-wrap,
		.oa-table .oa-select-wrap{
			display: inline-block;
			margin-right: 10px;
		}

		div.attachement{
			padding: 0;
			float: none;
		}
	</style>
</head>
<body>
<script type="text/javascript">
	function closeWin(){
	    window.close();
	}
	
	$(function(){
		$("#isexamine").click(function(){
			$(this).attr("checked",true);
			if($(this).attr("checked")){
				$("#relationflowid").show();
			}else{
				$("#relationflowid").next(".error").remove();
				$("#relationflowid").hide();
			}
		});
		
		$("#tasktype").change(function(){
			if($(this).val()==2){
				$("#fielddiv").hide();
				$(".gnjdtype").hide();
				//$(".zlsctype").show();
			}else if($(this).val()==1){
				$("#fielddiv").show();
				$(".gnjdtype").hide(); 
				//$(".zlsctype").hide();
			}else{
				$("#fielddiv").hide();
				$(".gnjdtype").show(); 
				//$(".zlsctype").show(); 
			}
		});
	})
</script>
	<div class="oa-top-wrap">
		<button class="oa-button oa-button--primary oa-button--blue" id="dataFormSave">保存</button>
		<button class="oa-button oa-button--primary oa-button--blue" onclick="closeWin();">关闭</button>
	</div>
	
	<div class="oa-main-wrap">
		<form id="stagetaskForm" method="post" action="stageTaskSave.ht">
			<table class="oa-table">
				<tr>
					<th>项目名：</th>
					<td>${projectname}</td>
				</tr>
				<tr>
					<th>阶段名：</th>
					<td>${stagename}</td>
				</tr>
				<tr>
					<th>任务名称 ：</th>
					<td>
						<div class="oa-input-wrap">
							<input class="oa-input" type="text" name="taskname" validate="{'required':true,'maxlength':150}" value="${projectStageTask.taskname }"/>
						</div>
					</td>
				</tr>
				<tr>
					<th>模板文件：</th>
					<td>
						<div name="div_attachment_container">
							<div class="attachement"></div>
							<textarea style="display: none" controltype="attachment" name="templatefile" lablename="模板文件" validate="{}">${projectStageTask.templatefile}</textarea>
							<button class="oa-button-label" type="button" field="attachment" atype="select" onclick="AttachMent.addFile(this);">选择</button>
						</div>
					</td>
				</tr>
				<tr>
					<th>备注：</th>
					<td>
						<div class="oa-textarea-wrap">
							<textarea class="oa-textarea" name="remark">${projectStageTask.remark}</textarea>
						</div>
					</td>
				</tr>
				<tr>
					<th>任务类型：</th>
					<td>
						<div class="oa-select-wrap">
							<select class="oa-select" name="tasktype" id="tasktype" >
								<option value="1" <c:if test="${projectStageTask.tasktype==1 }">selected="selected"</c:if>>表单填写类</option>
								<option value="2" <c:if test="${projectStageTask.tasktype==2 }">selected="selected"</c:if>>资料上传类</option>
								<option value="3" <c:if test="${projectStageTask.tasktype==3 }">selected="selected"</c:if>>流程审批类</option>
							</select>
						</div>
					</td>
				</tr>
				<tr class="gnjdtype" <c:if test="${projectStageTask.tasktype!=3 }">style="display:none" </c:if>>
					<td></td>
					<td>
						<input type="checkbox" id="isexamine" name="isexamine" value="1" checked="checked">
						是否需要审批<span id="relationflowid" <%-- <c:if test="${!projectStageTask.isexamine}">style="display:none"</c:if> --%>>
						关联流程
						<div class="oa-input-wrap">
							<input class="oa-input" name="flowid" id="flowid" readonly="readonly" validate="{'digits':true,'maxlength':20}" value="${projectStageTask.flowid }"></span>
						</div><button type="button"  onclick="getDefId()">选择</button>
					</td>
				</tr>
				<tr class="zlsctype">
					<td></td>
					<td>
						<input type="checkbox" id="ismore" name="ismore" value="1" <c:if test="${projectStageTask.ismore}">checked="checked"</c:if>>
						是否需要提交多次
					</td>
				</tr>
			</table>


			<div id="fielddiv" <c:if test="${projectStageTask!=null && projectStageTask.tasktype!=1 }">style="display:none" </c:if>>
				<table class="oa-table">
					<tr>
						<th>待填写的表单</th>
						<td><button type="button" class="oa-button-label" onclick="addField();">添加</button></td>
					</tr>
				</table>
					
				<div class="fieldDiv">
					<table class="oa-table">
						<c:forEach items="${projectStageTask.fieldArr}" var="js">
							<tr class="fieldItem">
								<th>字段名</th>
								<td>
									<div class="oa-input-wrap">
										<input class="oa-input" name="fieldname" value="${js.fieldname }" validate="{'required':true,'maxlength':150}">
									</div>
									<div class="oa-select-wrap">
										<select class="oa-select" name="fieldtype">
											<option value="varchar" <c:if test="${js.fieldtype=='varchar'}">selected="selected"</c:if>>字符串</option>
											<option value="date" <c:if test="${js.fieldtype=='date'}">selected="selected"</c:if>>日期</option>
											<option value="number" <c:if test="${js.fieldtype=='number'}">selected="selected"</c:if>>数字</option>
											<option value="attach" <c:if test="${js.fieldtype=='attach'}">selected="selected"</c:if>>附件</option>
											<option value="user" <c:if test="${js.fieldtype=='user'}">selected="selected"</c:if>>用户选择器</option>
										</select>
									</div><span>&nbsp;&nbsp;是否必填：<input type="checkbox"  <c:if test="${js.required==1}">checked=checked</c:if> class="isRequired" value="1"/></span>
									<button type="button" class="oa-button-label" onclick="removeField(this);">删除</button>
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
			<input type="hidden" id="saveData" name="saveData"/>
			<input type="hidden" id="id" name="id" value="${projectStageTask.id }"/>
			<input type="hidden" id="stageid" name="prostageid" value="${stageid }">
			<input type="hidden" id="projectid" name="projectid" value="${projectid }">
			<textarea style="display: none" id="fields" name="fields" >${projectStageTask.fields }</textarea>
		</form>
	</div>

<script type="text/javascript">
	function addField(){
		var item = '<tr class="fieldItem">' +
						'<th>字段名</th>' +
						'<td>' +
							'<div class="oa-input-wrap">' +
								'<input class="oa-input" name="fieldname" value="${js.fieldname }" validate="{\'required\':true,\'maxlength\':150}">' +
							'</div>' +
							'<div class="oa-select-wrap">' +
								'<select class="oa-select" name="fieldtype">' +
									'<option value="varchar" <c:if test="${js.fieldtype==\'varchar\'}">selected="selected"</c:if>>字符串</option>' +
									'<option value="date" <c:if test="${js.fieldtype==\'date\'}">selected="selected"</c:if>>日期</option>' +
									'<option value="number" <c:if test="${js.fieldtype==\'number\'}">selected="selected"</c:if>>数字</option>' +
									'<option value="attach" <c:if test="${js.fieldtype==\'attach\'}">selected="selected"</c:if>>附件</option>' +
									'<option value="user" <c:if test="${js.fieldtype==\'user\'}">selected="selected"</c:if>>用户选择器</option>' +
								'</select>' +
							'</div>' +
							'<span>&nbsp;&nbsp;是否必填：<input type="checkbox" class="isRequired" value="1"/></span>'+
							'<button type="button" class="oa-button-label" onclick="removeField(this);">删除</button>' +
						'</td>' +
					'</tr>';
		$(".fieldDiv table").append(item);
	}
	function removeField(obj){
		
		$(obj).parents(".fieldItem").remove();
	}
	function getDefId(){
		 var outerWindow =window.top;
		 outerWindow.$.ligerDialog.open({ url: '/makshi/dialog/bpmdefdialog/list.ht',title:'配置流程ID',width:600,height: 400, isResize:true,
			 buttons: [{ text: '确定', onclick: function (item, dialog) {
				 				if(dialog.frame.$('input:radio[name="selectDefId"]:checked').length<=0){
					    			alert("请选择一条记录！");
					    		}else{
					    			var selectedId=dialog.frame.$('input:radio[name="selectDefId"]:checked').val();
					    			if(selectedId.length>0){
					    				$("input[name='flowid']").eq(0).val(selectedId);
					    			}
					    			dialog.close();
					    		}
	       				 }
	      	  },
	                  { text: '取消', onclick: function (item, dialog) { dialog.close(); } }]
		 });
	}
</script>
</body>
</html>
