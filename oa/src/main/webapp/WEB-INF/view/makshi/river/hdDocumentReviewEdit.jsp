<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 文件</title>
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
			var frm=$('#hdDocumentReviewForm').form();
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
							$('#hdDocumentReviewForm').submit();
						});
					}else{
						frm.handleFieldName();
						$('#hdDocumentReviewForm').submit();
					}
				}
			});
			$("a.run").click(function() {
				frm.ajaxForm(options);
				$("#saveData").val(0);
				$('#hdDocumentReviewForm').attr("action","run.ht");
				if(frm.valid()){
					if(WebSignPlugin.hasWebSignField){
						WebSignPlugin.submit();
					}
					data=CustomForm.getData();
					//设置表单数据
					$("#formData").val(data);
					frm.handleFieldName();
					OfficePlugin.submit();
					$('#hdDocumentReviewForm').submit();
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
						window.location.href = "${ctx}/makshi/river/hdDocumentReview/list.ht";
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
			    <c:when test="${not empty hdDocumentReviewItem.id}">
			        <span class="tbar-label"><span></span>编辑文件</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label"><span></span>添加文件</span>
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
	<form id="hdDocumentReviewForm" method="post" action="save.ht">
		<div type="custform">
			<div class="panel-detail">
				<table class="formTable" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
 <tbody>
  <tr class="firstRow">
   <td class="formHead" colspan="4" style="word-break: break-all;">文件审查</td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">文件名称</td>
   <td style="width: 35%" class="formInput"></td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">编号<br /></td>
   <td style="width: 35%" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:hd_document_review:number" lablename="编号" class="inputText" value="${hdDocumentReview.number}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right"><span style="font-size: 12px; text-align: right; background-color: rgb(240, 241, 241);">项目部</span></td>
   <td style="width: 35%; word-break: break-all;" class="formInput"> <input name="m:hd_document_review:department" type="text" ctltype="selector" class="org" lablename="项目部" value="${hdDocumentReview.department}" validate="{empty:false}" readonly="readonly" showcurorg="0" /> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">申请人</td>
   <td class="formInput" style="width: 35%; word-break: break-all;"> <input name="m:hd_document_review:applicant" type="text" ctltype="selector" class="user" lablename="申请人" value="${hdDocumentReview.applicant}" validate="{empty:false,required:true}" readonly="readonly" showcuruser="0" /> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right"><span style="font-size: 12px; text-align: right; background-color: rgb(240, 241, 241);">申请时间</span></td>
   <td style="width: 35%; word-break: break-all;" class="formInput"> <input name="m:hd_document_review:date" type="text" class="Wdate" displaydate="0" datefmt="yyyy-MM-dd" value="<fmt:formatDate value='${hdDocumentReview.date}' pattern='yyyy-MM-dd'/>" validate="{empty:false}" /> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">审批类型</td>
   <td style="width: 35%; word-break: break-all;" class="formInput"> <span name="editable-input" style="display:inline-block;padding:2px;" class="selectinput"> <select name="m:hd_document_review:type" lablename="审批类型" controltype="select" validate="{empty:false}" val="${hdDocumentReview.type}"> <option value="0">技术文件</option> <option value="1">通知</option> <option value="2">请示事</option> </select> </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">紧急程度</td>
   <td style="width: 35%" class="formInput"> <span name="editable-input" style="display:inline-block;padding:2px;" class="selectinput"> <select name="m:hd_document_review:level" lablename="紧急程度" controltype="select" validate="{empty:false}" val="${hdDocumentReview.level}"> <option value="0">普通</option> <option value="1">紧急</option> <option value="2">加急</option> </select> </span> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">内容摘要</td>
   <td style="width: 35%; word-break: break-all;" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <textarea name="m:hd_document_review:content" lablename="内容摘要" class="l-textarea" rows="5" cols="40" validate="{maxlength:1000}" isflag="tableflag">${hdDocumentReview.content}</textarea> </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">申请意见</td>
   <td style="width: 35%" class="formInput" rowspan="1" colspan="3"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:hd_document_review:opinion" lablename="申请意见" class="inputText" value="${hdDocumentReview.opinion}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">附件</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"></td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">项目负责人</td>
   <td style="width: 35%" class="formInput" rowspan="1" colspan="3"></td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">部门负责人</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"></td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">是否需要审定<br /></td>
   <td style="width: 35%" class="formInput"> <span> <label><input val="${hdDocumentReview.isNeedCheck}" type="radio" name="m:hd_document_review:isNeedCheck" value="1" lablename="是否需要审定" validate="{}" />需要</label> <label><input val="${hdDocumentReview.isNeedCheck}" type="radio" name="m:hd_document_review:isNeedCheck" value="0" lablename="是否需要审定" validate="{}" />不需要</label> </span> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">总工办/分管领导</td>
   <td style="width: 35%; word-break: break-all;" class="formInput"> <input name="m:hd_document_review:finalChecker" type="text" ctltype="selector" class="user" lablename="总工办/分管领导" value="${hdDocumentReview.finalChecker}" validate="{empty:false}" readonly="readonly" showcuruser="0" /> </td>
  </tr>
 </tbody>
</table>
			</div>
		</div>
		<input type="hidden" name="id" value="${hdDocumentReview.id}"/>
		<input type="hidden" id="saveData" name="saveData"/>
		<input type="hidden" name="executeType"  value="start" />
	</form>
</body>
</html>
