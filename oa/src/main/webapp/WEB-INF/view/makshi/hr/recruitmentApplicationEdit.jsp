<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 招聘申请</title>
	<%@include file="/codegen/include/customForm.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/AttachMent.js"></script>
	<%@include file="/commons/include/ueditor.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/FlowDetailWindow.js"></script>
	 <script type="text/javascript" src="${ctx}/js/hotent/platform/form/TablePermission.js"></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#recruitmentApplicationForm').form();
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
							$('#recruitmentApplicationForm').submit();
						});
					}else{
						frm.handleFieldName();
						$('#recruitmentApplicationForm').submit();
					}
				}
			});
			$("a.run").click(function() {
				frm.ajaxForm(options);
				$("#saveData").val(0);
				$('#recruitmentApplicationForm').attr("action","run.ht");
				if(frm.valid()){
					if(WebSignPlugin.hasWebSignField){
						WebSignPlugin.submit();
					}
					data=CustomForm.getData();
					//设置表单数据
					$("#formData").val(data);
					frm.handleFieldName();
					OfficePlugin.submit();
					$('#recruitmentApplicationForm').submit();
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
						window.location.href = "${ctx}/makshi/hr/recruitmentApplication/list.ht";
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
			    <c:when test="${not empty recruitmentApplicationItem.id}">
			        <span class="tbar-label"><span></span>编辑招聘申请</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label"><span></span>添加招聘申请</span>
			    </c:otherwise>	
		    </c:choose>
		</div>
		<div class="panel-toolbar">
			<div class="toolBar">
				<div class="group"><a class="link save" id="dataFormSave" href="javascript:;"><span></span>保存</a></div>
				<div class="l-bar-separator"></div>
				<%-- <c:if test="${runId!=0}">
					<div class="group"><a class="link detail"  onclick="FlowDetailWindow({runId:${runId}})" href="javascript:;" ><span></span>流程明细</a></div>
					<div class="l-bar-separator"></div>
				</c:if> --%>
				<div class="group"><a class="link back" href="list.ht"><span></span>返回</a></div>
			</div>
		</div>
	</div>
	<form id="recruitmentApplicationForm" method="post" action="save.ht">
		<div type="custform">
			<div class="panel-detail">
				<table class="formTable" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled"> 
 <tbody>
  <tr class="firstRow">
   <td class="formHead" colspan="4" style="word-break: break-all;">招聘需求申请表</td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">工号</td>
   <td style="width: 35%; word-break: break-all;" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:recruitment_application:account" lablename="工号" class="inputText" value="${recruitmentApplication.account}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">申请人</td>
   <td style="width: 35%; word-break: break-all;" class="formInput">
   <input name="m:recruitment_application:applicant" type="text" ctltype="selector" class="user" lablename="申请人" value="${recruitmentApplication.applicant}" validate="{empty:false}" readonly="readonly" showcuruser="0" /> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">申报部门</td>
   <td style="width: 35%; word-break: break-all;" class="formInput">
   <input name="m:recruitment_application:declare_department" type="text" ctltype="selector" class="orgs" lablename="申报部门" value="${recruitmentApplication.declare_department}" validate="{empty:false}" readonly="readonly" showcuruser="0" /> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">申报时间</td>
   <td style="width: 35%; word-break: break-all;" class="formInput">
   <input name="m:recruitment_application:declare_time" type="text" class="Wdate" displaydate="0" datefmt="yyyy-MM-dd" value="<fmt:formatDate value='${recruitmentApplication.declare_time}' pattern='yyyy-MM-dd'/>" validate="{empty:false}" /> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">补增岗位</td>
   <td class="formInput" colspan="1" rowspan="1"><input readonly="readonly" type="text" name="m:recruitment_application:position_whereabouts" lablename="岗位去向" class="inputText" value="${recruitmentApplication.position_whereabouts}" validate="{maxlength:500}" isflag="tableflag" /> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right"><span style="font-size: 12px; text-align: right; background-color: rgb(240, 241, 241);">需求人数</span></td>
   <td class="formInput" colspan="1" rowspan="1"><input readonly="readonly" type="text" name="m:recruitment_application:recruitment_number" lablename="招聘人数" class="inputText" value="${recruitmentApplication.recruitment_number}" validate="{maxlength:500}" isflag="tableflag" /> </td> 
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">招聘方式</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"> 
   <span name="editable-input" style="display:inline-block;padding:2px;" class="selectinput"> 
   <select name="m:recruitment_application:recruitment_method" lablename="招聘方式" controltype="select" validate="{empty:false}" val="${recruitment_application.recruitment_method}"> 
   <option value="0">校园招聘会</option> 
   <option value="1">校园网络招聘</option>
   <option value="2">社会招聘会</option> 
   <option value="3">社会网络招聘</option> 
   <option value="4">其他</option> 
   </select> </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">申请理由</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan=""> 
   <span name="editable-input" style="display:inline-block;padding:2px;" class="selectinput"> 
   <select name="m:recruitment_application:appli_reason" lablename="招聘方式" controltype="select" validate="{empty:false}" val="${recruitment_application.appli_reason}"> 
   <option value="0">扩大编制</option> 
   <option value="1">补充编制</option>
   <option value="2">离职补充</option> 
   <option value="3">调动补充</option> 
   <option value="4">人力储备</option> 
   <option value="5">短期需求</option>
   <option value="6">其他</option>
   </select> </span> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">其他原因</td>
   <td style="width: 35%; word-break: break-all;" class="formInput"><span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:recruitment_application:other_reason_content" lablename="工号" class="inputText" value="${recruitmentApplication.other_reason_content}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
  </tr>
  <tr>
   <td class="formInput" colspan="4" rowspan="1" style="word-break: break-all;"></td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">性别</td>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all;"> <span name="editable-input" style="display:inline-block;padding:2px;" class="selectinput"> <select name="m:recruitment_application:sex" lablename="性别" controltype="select" validate="{empty:false}" val="${recruitmentApplication.sex}"> <option value="0">男</option> <option value="1">女</option> <option value="2">不限</option> </select> </span> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">年龄区间</td>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all;" id="tdIsEmpty"> 
   <span name="editable-input" style="display:inline-block;padding:2px;" class="selectinput"> 
   <select name="m:recruitment_application:age_limit" lablename="年龄区间" controltype="select" validate="{empty:false}" val="${recruitmentApplication.age_limit}"> 
   <option value="0">不限</option> <option value="1">具体年限</option> 
   </select> </span> </td>
  </tr>
  <tr id="ageLimit" style="display:none">
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">最小年龄</td>
   <td class="formInput" colspan="1" rowspan="1"><span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:recruitment_application:age_least" lablename="最小年龄" class="inputText" value="${recruitmentApplication.age_least}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">最大年龄</td>
   <td class="formInput" colspan="1" rowspan="1"><span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:recruitment_application:age_most" lablename="最大年龄" class="inputText" value="${recruitmentApplication.age_most}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">婚姻状况</td>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all;">
   <span name="editable-input" style="display:inline-block;padding:2px;" class="selectinput"> 
   <select name="m:recruitment_application:marriage_status" lablename="婚姻状况" controltype="select" validate="{empty:false}" val="${recruitmentApplication.marriage_status}"> 
   <option value="0">已婚</option> 
   <option value="1">未婚</option> 
   </select> </span> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">户籍状况</td>
   <td class="formInput" colspan="1" rowspan="1"> <span name="editable-input" style="display:inline-block;padding:2px;" class="selectinput"> <select name="m:recruitment_application:birthplace" lablename="户籍状况" controltype="select" validate="{empty:false}" val="${recruitmentApplication.birthplace}"> <option value="0">深户</option> <option value="1">非深户</option> <option value="2">不限</option> </select> </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">政治面貌</td>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all;"> 
   <span name="editable-input" style="display:inline-block;padding:2px;" class="selectinput"> 
   <select name="m:recruitment_application:political_status" lablename="政治面貌" controltype="select" validate="{empty:false}" val="${recruitmentApplication.political_status}"> 
   <option value="0">党员</option> 
   <option value="1">不限</option> 
   </select> </span> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">毕业时间要求</td>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all;" id="graduationIsEmpty">
   <span name="editable-input" style="display:inline-block;padding:2px;" class="selectinput"> 
   <select name="m:recruitment_application:graduation_year" lablename="毕业时间要求" controltype="select" validate="{empty:false}" val="${recruitmentApplication.graduation_year}"> 
   <option value="0">不限</option> 
   <option value="1">具体年限</option> 
   </select> </span> </td>
  </tr>
  <tr id="graduationYear" style="display:none">
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">毕业年份</td>
   <td class="formInput" colspan="3" rowspan="1"><span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:recruitment_application:graduation_date" lablename="毕业年份" class="inputText" value="${recruitmentApplication.graduation_date}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">学历要求</td>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all;">
   <span name="editable-input" style="display:inline-block;padding:2px;" class="selectinput"> 
   <select name="m:recruitment_application:edu_requirement" lablename="学历要求" controltype="select" validate="{empty:false}" val="${recruitmentApplication.edu_requirement}"> 
   <option value="0">不限</option> 
   <option value="1">大专</option> 
   <option value="2">本科</option> 
   <option value="3">研究生</option> 
   </select> </span> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">专业要求</td>
   <td class="formInput" colspan="1" rowspan="1"><span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:recruitment_application:recruitment_professional" lablename="专业要求" class="inputText" value="${recruitmentApplication.recruitment_professional}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">语言要求</td>
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all;"><span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:recruitment_application:language_reqirement" lablename="语言要求" class="inputText" value="${recruitmentApplication.language_reqirement}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">技能等级(证书)要求</td>
   <td class="formInput" colspan="1" rowspan="1"><span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:recruitment_application:degree_requirement" lablename="技能等级(证书)要求" class="inputText" value="${recruitmentApplication.degree_requirement}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">经历/经验要求</td>
   <td class="formInput" colspan="3" rowspan="1" style="word-break: break-all;">
   <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <textarea name="m:recruitment_application:experience_reqirement" lablename="经历/经验要求" class="l-textarea" rows="3" cols="80" validate="{maxlength:1000}" isflag="tableflag">${recruitmentApplication.experience_reqirement}</textarea> </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">技能要求</td>
   <td class="formInput" colspan="3" rowspan="1" style="word-break: break-all;">
   <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <textarea name="m:recruitment_application:skill_requirement" lablename="技能要求" class="l-textarea" rows="3" cols="40" validate="{maxlength:1000}" isflag="tableflag">${recruitmentApplication.skill_requirement}</textarea> </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">个性要求</td>
   <td class="formInput" colspan="3" rowspan="1" style="word-break: break-all;">
   <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <textarea name="m:recruitment_application:character_requirement" lablename="个性要求" class="l-textarea" rows="3" cols="40" validate="{maxlength:1000}" isflag="tableflag">${recruitmentApplication.character_requirement}</textarea> </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">其他要求</td>
   <td class="formInput" colspan="3" rowspan="1">
   <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <textarea name="m:recruitment_application:other_requirement" lablename="其他要求" class="l-textarea" rows="3" cols="40" validate="{maxlength:1000}" isflag="tableflag">${recruitmentApplication.other_requirement}</textarea> </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">其他补充</td>
   <td class="formInput" colspan="3" rowspan="1">
   <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <textarea name="m:recruitment_application:other_remark" lablename="其他补充" class="l-textarea" rows="3" cols="40" validate="{maxlength:1000}" isflag="tableflag">${recruitmentApplication.other_remark}</textarea> </span> </td>
  <tr style="display:none">
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">附件</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"><br /><br /></td>
  </tr>
  <tr style="display:none">
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">员工编号</td>
   <td class="formInput" rowspan="1" colspan="1"></td>
   <td class="formInput" rowspan="1" colspan="1"><br /></td>
   <td class="formInput" rowspan="1" colspan="1"><br /></td>
  </tr>
 </tbody>
</table>
			</div>
		</div>
		<input type="hidden" name="id" value="${recruitmentApplication.id}"/>
		<input type="hidden" id="saveData" name="saveData"/>
		<input type="hidden" name="executeType"  value="start" />
	</form>
</body>
<script type="text/javascript">
$(function(){
	checkType();
});

function checkType(){
	var ageType = $("select[name='m:recruitment_application:age_limit']").val();
	var eduType = $("select[name='m:recruitment_application:graduation_year']").val();
	if(ageType==0){
		$("#ageLimit").attr("style","display:none");
		$("#input[name='m:recruitment_application:age_least']").val("");
		$("#input[name='m:recruitment_application:age_most']").val("");
	}else{
		$("#ageLimit").attr("style","display:");
	};
	if(eduType==0){
	    $("#graduationYear").attr("style","display:none");
	    $("input[name='m:recruitment_application:graduation_date']").val("");
	}else{
	    $("#graduationYear").attr("style","display:");
	};
};

$("select[name='m:recruitment_application:age_limit']").on("change",function(){
	var ageValue = $(this).val();
    if(ageValue==0){
      $("#ageLimit").attr("style","display:none");
      $("input[name='m:recruitment_application:age_least']").val("");
      $("input[name='m:recruitment_application:age_most']").val("");
    }else{
      $("#ageLimit").attr("style","display:");
    }
});

$("select[name='m:recruitment_application:graduation_year']").on("change",function(){
    var typeValue = $(this).val();
    if(typeValue==0){
       $("#graduationYear").attr("style","display:none");
       $("input[name='m:recruitment_application:graduation_date']").val("");
    }else{
      $("#graduationYear").attr("style","display:");
    }
  });
</script>
</html>
