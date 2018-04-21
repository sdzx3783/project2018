<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 会议纪要</title>
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
			var frm=$('#hdMeetingMinutesForm').form();
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
							$('#hdMeetingMinutesForm').submit();
						});
					}else{
						frm.handleFieldName();
						$('#hdMeetingMinutesForm').submit();
					}
				}
			});
			$("a.run").click(function() {
				frm.ajaxForm(options);
				$("#saveData").val(0);
				$('#hdMeetingMinutesForm').attr("action","run.ht");
				if(frm.valid()){
					if(WebSignPlugin.hasWebSignField){
						WebSignPlugin.submit();
					}
					data=CustomForm.getData();
					//设置表单数据
					$("#formData").val(data);
					frm.handleFieldName();
					OfficePlugin.submit();
					$('#hdMeetingMinutesForm').submit();
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
						window.location.href = "${ctx}/makshi/river/hdMeetingMinutes/list.ht";
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
			    <c:when test="${not empty hdMeetingMinutesItem.id}">
			        <span class="tbar-label"><span></span>编辑会议纪要</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label"><span></span>添加会议纪要</span>
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
	<form id="hdMeetingMinutesForm" method="post" action="save.ht">
		<div type="custform">
			<div class="panel-detail">
				<table class="formTable" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
 <tbody>
  <tr class="firstRow">
   <td class="formHead" colspan="4" style="word-break: break-all;">会议纪要</td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">会议名称<br /></td>
   <td style="width: 35%" class="formInput"></td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">编号<br /></td>
   <td style="width: 35%" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:hd_meeting_minutes:number" lablename="编号" class="inputText" value="${hdMeetingMinutes.number}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">会议时间</td>
   <td style="width: 35%; word-break: break-all;" class="formInput"> <input name="m:hd_meeting_minutes:date" type="text" class="Wdate" displaydate="0" datefmt="yyyy-MM-dd" value="<fmt:formatDate value='${hdMeetingMinutes.date}' pattern='yyyy-MM-dd'/>" validate="{empty:false}" /> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">会议主持人</td>
   <td style="width: 35%; word-break: break-all;" class="formInput"> <input name="m:hd_meeting_minutes:moderator" type="text" ctltype="selector" class="users" lablename="会议主持人" value="${hdMeetingMinutes.moderator}" validate="{empty:false}" readonly="readonly" /> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">参会人员</td>
   <td style="width: 35%; word-break: break-all;" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <textarea name="m:hd_meeting_minutes:participants" lablename="参会人员" class="l-textarea" rows="5" cols="40" validate="{maxlength:1000}" isflag="tableflag">${hdMeetingMinutes.participants}</textarea> </span> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">纪要提交人</td>
   <td style="width: 35%" class="formInput"> <input name="m:hd_meeting_minutes:name" type="text" ctltype="selector" class="user" lablename="纪要提交人" value="${hdMeetingMinutes.name}" validate="{empty:false}" readonly="readonly" showcuruser="0" /> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">备注</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <textarea name="m:hd_meeting_minutes:note" lablename="备注" class="l-textarea" rows="5" cols="40" validate="{maxlength:1000}" isflag="tableflag">${hdMeetingMinutes.note}</textarea> </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">附件</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"></td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">项目负责人</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"></td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">部门负责人</td>
   <td style="width: 35%" class="formInput" rowspan="1" colspan="3"></td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">是否需要核定</td>
   <td style="width: 35%" class="formInput"> <span> <label><input val="${hdMeetingMinutes.isNeedCheck}" type="radio" name="m:hd_meeting_minutes:isNeedCheck" value="1" lablename="是否需要审定" validate="{}" />需要</label> <label><input val="${hdMeetingMinutes.isNeedCheck}" type="radio" name="m:hd_meeting_minutes:isNeedCheck" value="0" lablename="是否需要审定" validate="{}" />不需要</label> </span> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">总工/分管领导<br /></td>
   <td style="width: 35%; word-break: break-all;" class="formInput"> <input name="m:hd_meeting_minutes:finalChecker" type="text" ctltype="selector" class="user" lablename="总工/分管领导" value="${hdMeetingMinutes.finalChecker}" validate="{empty:false}" readonly="readonly" showcuruser="0" /> </td>
  </tr>
 </tbody>
</table>
			</div>
		</div>
		<input type="hidden" name="id" value="${hdMeetingMinutes.id}"/>
		<input type="hidden" id="saveData" name="saveData"/>
		<input type="hidden" name="executeType"  value="start" />
	</form>
</body>
</html>
