<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 收文汇总表</title>
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
			var frm=$('#receiptAllForm').form();
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
							$('#receiptAllForm').submit();
						});
					}else{
						frm.handleFieldName();
						$('#receiptAllForm').submit();
					}
				}
			});
			$("a.run").click(function() {
				frm.ajaxForm(options);
				$("#saveData").val(0);
				$('#receiptAllForm').attr("action","run.ht");
				if(frm.valid()){
					if(WebSignPlugin.hasWebSignField){
						WebSignPlugin.submit();
					}
					data=CustomForm.getData();
					//设置表单数据
					$("#formData").val(data);
					frm.handleFieldName();
					OfficePlugin.submit();
					$('#receiptAllForm').submit();
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
						window.location.href = "${ctx}/makshi/receipt/receiptAll/list.ht";
					}
				});
			} else {
				$.ligerDialog.error(obj.getMessage(),"提示信息");
			}
		}
		$(function(){
			var time_limit = $("#time_limit").text().trim();
			$("#time_limit_value").val(time_limit); 
			var urgency_degree = $("#urgency_degree").val();
			$("option[value='"+urgency_degree+"']").attr("selected",true);
		});
	</script>
</head>
<body>
<div class="panel" style="height:100%;overflow:auto;">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${not empty receiptAllItem.id}">
			        <span class="tbar-label"><span></span>编辑收文汇总表</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label"><span></span>添加收文汇总表</span>
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
	<form id="receiptAllForm" method="post" action="save.ht">
		<div type="custform">
			<div class="panel-detail">
				<table class="formTable" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
				    <tbody>
				        <tr class="firstRow">
				            <td colspan="4" class="formHead" style="word-break: break-all;">
					收文汇总表
				            </td>
				        </tr>
				        <tr>
				              <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">收文编号:</td>
				            </td>
				            <td style="width:35%;" class="formInput">
				            
				            	<span name="editable-input" style="display:inline-block;" isflag="tableflag">
				                <input name="m:receipt_all:receipt_id" lablename="收文编号" class="inputText" value="${receiptAll.id}" validate="{maxlength:50}" isflag="tableflag" type="text"/>
				                </span>
				            </td>
				            <td style="width:15%;" class="formTitle" nowrap="nowarp" align="right">
				                收文日期:
				            </td>
				            <td style="width:35%;" class="formInput">
				                <input parser="inputtable" name="m:receipt_all:time" lablename="收文日期" class="inputText" value="${receiptAll.time}" validate="{maxlength:50}" isflag="tableflag" type="text"/>
				            </td>
				        </tr>
				        <tr>
				            <td style="width:15%;" class="formTitle" nowrap="nowarp" align="right">
				                来文标题:
				            </td>
				            <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3">
				            	<span name="editable-input" style="display:inline-block;" isflag="tableflag">
				                <input parser="inputtable" name="m:receipt_all:title" lablename="来文标题" class="inputText" value="${receiptAll.title}" validate="{maxlength:50}" isflag="tableflag" style="width: 600px; height: 18px;" type="text"/><br/>
				                </span>
				            </td>
				        </tr>
				        <tr>
				            <td style="width: 15%; word-break: break-all;" class="formTitle" nowrap="nowarp" align="right">
				                来文单位:
				            </td>
				            <td style="width:35%;" class="formInput">
				                <span name="editable-input" style="display:inline-block;" isflag="tableflag">
				                <input parser="inputtable" name="m:receipt_all:dispatch_unit" lablename="来文单位" class="inputText" value="${receiptAll.dispatch_unit}" validate="{maxlength:50}" isflag="tableflag" type="text"/><br/>
				                </span>
				            </td>
				            <td style="width: 15%; word-break: break-all;" class="formTitle" nowrap="nowarp" align="right">
				                <span style="font-size: 12px; text-align: right; background-color: rgb(240, 241, 241);">紧急程度</span>:
				            </td>
				            <td style="width:35%;" class="formInput">
				            	<input id="urgency_degree" type="hidden" value="${receiptAll.urgency_degree}"/> 
				                <select id="urgency_degree-value" parser="selecttable" name="m:receipt_all:urgency_degree"  lablename="紧急程度" controltype="select" validate="{empty:false}">
				                <option value="0">
				                    请选择
				                </option>
				                <option value="1">
				                    普通
				                </option>
				                <option value="2">
				                    紧急
				                </option>
				                <option value="3">
				                    非常紧急
				                </option></select>
				            </td>
				        </tr>
				        <tr>
				            <td style="width: 15%; word-break: break-all;" class="formTitle" nowrap="nowarp" align="right">
				                <span style="font-size: 12px; text-align: right; background-color: rgb(240, 241, 241);">来文字号</span>:
				            </td>
				            <td style="width: 35%; word-break: break-all;" class="formInput">
				              	<span name="editable-input" style="display:inline-block;" isflag="tableflag">
				                <input parser="inputtable" name="m:receipt_all:type" lablename="来文字号" class="inputText" value="${receiptAll.type}" validate="{maxlength:50}" isflag="tableflag" type="text"/><br/>
				                </span>
				            </td>
				            <td style="width: 15%; word-break: break-all;" class="formTitle" nowrap="nowarp" align="right">
				                <span style="font-size: 12px; text-align: right; background-color: rgb(240, 241, 241);">办理时限</span>:
				            </td>
				            <td style="width:35%;" class="formInput">
				            	<span  id="time_limit" style="display:none">
				            	<fmt:formatDate   value="${receiptAll.time_limit}" pattern="yyyy-MM-dd" type="date" dateStyle="long" />
				            	</span>
				                <input id="time_limit_value" parser="datetable" name="m:receipt_all:time_limit" class="Wdate" displaydate="0" datefmt="yyyy-MM-dd" value="${receiptAll.time_limit}" validate="{empty:false}" type="text"/>
				            </td>
				        </tr>
				        <tr>
				            <td style="width: 15%; word-break: break-all;" class="formTitle" nowrap="nowarp" align="right">
				                <span style="font-size: 12px; text-align: right; background-color: rgb(240, 241, 241);">相关内容:</span>
				            </td>
				            <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3">
				          		 <input parser="inputtable" id="ipt_content"  lablename="相关内容" class="inputText" value="${receiptAll.content}" validate="{maxlength:9000}" isflag="tableflag" type="hidden"/> 
				                <textarea class="inputText" id="txt_content2" parser="textareaedit" name="m:receipt_all:content" style="width: 600px; height: 300px;"  value="${receiptAll.content}" ></textarea>
				            </td>
				        </tr>
				        <tr>
				            <td style="width:15%;" class="formTitle" nowrap="nowarp" align="right">
				                初审人员:
				            </td>
				            <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3">
				                <input parser="selectortable" name="m:receipt_all:first_check_person" ctltype="selector" class="users" lablename="初审人员" value="${receiptAll.first_check_person}" validate="{empty:false}" readonly="readonly" scope="{&#39;value&#39;:&#39;all&#39;,&#39;type&#39;:&#39;system&#39;}" type="text"/><br/>
				            </td>
				        </tr>
				        <tr>
				            <td style="width:15%;" class="formTitle" nowrap="nowarp" align="right">
				                附件
				            </td>
				            <td style="width:35%;" class="formInput" colspan="3">
				                <input parser="attachmenttable" ctltype="attachment" name="m:receipt_all:attachment" validatable="true" validate="{}" isdirectupload="0"/>
				            </td>
				        </tr>
				    </tbody>
				</table>
			</div>
		</div>
		<input type="hidden" name="id" value="${receiptAll.id}"/>
		<input type="hidden" id="saveData" name="saveData"/>
		<input type="hidden" name="executeType"  value="start" />
	</form>
</body>
</html>
<script>$(function(){
	   var ue = UE.getEditor('txt_content2');
	   ue.ready(function() {
	   var content = $("#ipt_content").val();
       ue.setContent(content);
});
});</script> 