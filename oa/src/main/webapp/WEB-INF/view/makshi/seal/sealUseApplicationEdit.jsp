<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 公章使用申请表</title>
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
			var frm=$('#sealUseApplicationForm').form();
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
							$('#sealUseApplicationForm').submit();
						});
					}else{
						frm.handleFieldName();
						$('#sealUseApplicationForm').submit();
					}
				}
			});
			$("a.run").click(function() {
				frm.ajaxForm(options);
				$("#saveData").val(0);
				$('#sealUseApplicationForm').attr("action","run.ht");
				if(frm.valid()){
					if(WebSignPlugin.hasWebSignField){
						WebSignPlugin.submit();
					}
					data=CustomForm.getData();
					//设置表单数据
					$("#formData").val(data);
					frm.handleFieldName();
					OfficePlugin.submit();
					$('#sealUseApplicationForm').submit();
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
						window.location.href = "${ctx}/makshi/seal/sealUseApplication/list.ht";
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
			    <c:when test="${not empty sealUseApplicationItem.id}">
			        <span class="tbar-label"><span></span>编辑公章使用申请表</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label"><span></span>添加公章使用申请表</span>
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
	<form id="sealUseApplicationForm" method="post" action="save.ht">
		<div type="custform">
			<div class="panel-detail">
				<table class="formTable" parser="addpermission" data-sort="sortDisabled" cellspacing="0" cellpadding="2" border="1"> 
 <tbody> 
  <tr class="firstRow"> 
   <td class="formHead" colspan="4" style="word-break: break-all;">公章使用申请表</td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">工号:</td> 
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all;"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:seal_use_application:account" lablename="工号" class="inputText" value="${sealUseApplication.account}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">申请时间:</td> 
   <td class="formInput" colspan="1" rowspan="1"> <input name="m:seal_use_application:appiDate" type="text" class="Wdate" displaydate="0" datefmt="yyyy-MM-dd" value="<fmt:formatDate value='${sealUseApplication.appiDate}' pattern='yyyy-MM-dd'/>" validate="{empty:false}" /> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">申请人:</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">申请部门:</td> 
   <td style="width: 35%" class="formInput"> <input name="m:seal_use_application:department" type="text" ctltype="selector" class="orgs" lablename="申请部门" value="${sealUseApplication.department}" validate="{empty:false}" readonly="readonly" /> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">材料内容:</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:seal_use_application:contents" lablename="材料内容" class="inputText" value="${sealUseApplication.contents}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">类型:</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"> <span name="editable-input" style="display:inline-block;padding:2px;" class="selectinput"> <select name="m:seal_use_application:type" lablename="类型" controltype="select" validate="{empty:false}" val="${sealUseApplication.type}"> <option value="1">工程监理业务类</option> <option value="2">行政文书类</option> <option value="3">其他业务</option> </select> </span> </td> 
  </tr> 
  <tr class="toggle-view"> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">关联合同编号:</td> 
   <td style="width: 35%" class="formInput"><a parser="custombtntable" href="javaScript:void(0)" class="extend message" eventbtn="[{'name':'contractinfo','fields':[{'src':'F_contract_num','target':['seal_use_application-contract_id']},{'src':'F_contract_name','target':['seal_use_application-contract_name']}],'query':[],'tabName':'自定义对话框','isAddRow':true,'type':'dialog'}]" name="contract_id">选择</a></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">关联合同名称:</td> 
   <td style="width: 35%" class="formInput"></td> 
  </tr> 
  <tr class="toggle-view"> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">工程进度:</td> 
   <td style="width: 35%" class="formInput" rowspan="1" colspan="3"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">是否直接盖章</td> 
   <td class="formInput" rowspan="1" colspan="3" style="word-break: break-all;"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">份数:</td> 
   <td style="width: 35%" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:seal_use_application:number" lablename="份数" class="inputText" value="${sealUseApplication.number}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">附件:</td> 
   <td style="width: 35%" class="formInput"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">备注:</td> 
   <td class="formInput" colspan="3" rowspan="1"><br /> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <textarea name="m:seal_use_application:remark" lablename="备注" class="l-textarea" rows="5" cols="40" validate="{maxlength:1000}" isflag="tableflag">${sealUseApplication.remark}</textarea> </span> </td> 
  </tr> 
 </tbody> 
</table>
			</div>
		</div>
		<input type="hidden" name="id" value="${sealUseApplication.id}"/>
		<input type="hidden" id="saveData" name="saveData"/>
		<input type="hidden" name="executeType"  value="start" />
	</form>
</body>
</html>
