<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 缺勤表</title>
	<%@include file="/codegen/include/customForm.jsp" %>
	<%@include file="/commons/include/ueditor.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#workLevelForm').form();
			$("a.save").click(function() {
				frm.ajaxForm(options);
				$("#saveData").val(1);
				if(frm.valid()){
					var leave_start=$("#leave_start").val();
					var leave_end=$("#leave_end").val();
					if(leave_start!=null && leave_end!=null){
						 var d1 = new Date(leave_start.replace(/\-/g, "\/"));  
						 var d2 = new Date(leave_end.replace(/\-/g, "\/"));  
						 if(d1 >d2)  
						 {  
							 $.ligerDialog.error("缺勤时间的日期范围不正确","提示信息");
							 return;
						 }
						 var levea_start_select=$("#levea_start_select").val();
						 var levea_end_select=$("#levea_end_select").val();
						 if(d1=d2){
							 if(levea_end_select==2 || levea_start_select==1){
								 $.ligerDialog.error("缺勤时间的日期范围不正确","提示信息");
								 return;
							 }
						 }
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
							$('#workLevelForm').submit();
						});
					}else{
						frm.handleFieldName();
						$('#workLevelForm').submit();
					}
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
						window.location.href = "${ctx}/makshi/worksheet/workLeave/list.ht";
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
			    <c:when test="${not empty workLevelItem.id}">
			        <span class="tbar-label"><span></span>编辑缺勤表</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label"><span></span>添加缺勤表</span>
			    </c:otherwise>	
		    </c:choose>
		</div>
		<div class="panel-toolbar">
			<div class="toolBar">
				<div class="group"><a class="link save" id="dataFormSave" href="javascript:;"><span></span>保存</a></div>
				<div class="l-bar-separator"></div>
				<div class="group"><a class="link back" href="list.ht"><span></span>返回</a></div>
			</div>
		</div>
	</div>
	<form id="workLevelForm" method="post" action="save.ht">
		<div type="custform">
			<div class="panel-detail">
				<table class="formTable" border="1" cellspacing="0" cellpadding="2" parser="addpermission">
 <tbody>
  <tr class="firstRow">
   <td class="formHead" colspan="2">缺勤登记</td>
  </tr>
  <tr>
   <td style="width: 20%; word-break: break-all;" class="formTitle" align="right">姓名:</td>
   <td style="width: 80%" class="formInput"> <input name="m:work_level:fullname" type="text" ctltype="selector" class="users" lablename="姓名" value="${workLevel.fullname}" validate="{required:true}" readonly="readonly" /> </td>
  </tr>
  <tr>
   <td style="width: 20%; word-break: break-all;" class="formTitle" align="right">类型:</td>
   <td style="width: 80%" class="formInput"> <span name="editable-input" style="display:inline-block;padding:2px;" class="selectinput"> 
   <select name="m:work_level:type" lablename="类型" controltype="select" validate="{required:true}" val="${workLevel.type}"> <option value="因公办事">因公办事</option> <option value="年假">年假</option> <option value="调休">调休</option><option value="事假">事假</option>
   <option value="病假">病假</option>
   <option value="产假">产假</option>
   <option value="婚假">婚假</option>
   <option value="丧假">丧假</option>
   </select> </span> </td>
  </tr>
  <tr>
	     <td style="width: 20%; word-break: break-all;" class="formTitle" align="right">缺勤时间:</td><td style="width: 80%; word-break: break-all;" class="formInput"><span name="editable-input" style="display:inline-block;"><input type="text" name="leave_start" id="leave_start" lablename="缺勤时间" class="inputText date" validate="{required:true}"/>
		<select name="levea_start_select" controltype="select" ><option value="1">上午</option><option value="2">下午</option></select>
		</span>
		至
		<span name="editable-input" style="display:inline-block;">
		<input type="text" name="leave_end" id="leave_end" lablename="缺勤时间" class="inputText date" validate="{required:true}"/>
		<select name="levea_end_select" controltype="select" ><option value="1">上午</option><option value="2">下午</option></select>
		</span>
	</td>
  </tr>
  <tr>
   <td style="width: 20%; word-break: break-all;" class="formTitle" align="right">备注:</td>
   <td style="width: 80%" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:work_level:remark" lablename="备注" class="inputText" value="${workLevel.remark}" validate="{maxlength:1000}" isflag="tableflag" /> </span> </td>
  </tr>
 </tbody>
</table>
			</div>
		</div>
		<input type="hidden" name="id" value="${workLevel.id}"/>
		<input type="hidden" id="saveData" name="saveData"/>
		<input type="hidden" name="executeType"  value="start" />
	</form>
</body>
</html>
