<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 公司资质证书</title>
	<%@include file="/codegen/include/customForm.jsp" %>
	<%@include file="/commons/include/ueditor.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/AttachMent.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/TablePermission.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#companyQualificationCertificateForm').form();
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
							$('#companyQualificationCertificateForm').submit();
						});
					}else{
						frm.handleFieldName();
						$('#companyQualificationCertificateForm').submit();
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
						window.location.href = "${ctx}/makshi/qualification/companyQualificationCertificate/list.ht";
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
			    <c:when test="${not empty companyQualificationCertificateItem.id}">
			        <span class="tbar-label"><span></span>编辑公司资质证书</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label"><span></span>添加公司资质证书</span>
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
	<form id="companyQualificationCertificateForm" method="post" action="save.ht">
		<div type="custform">
			<div class="panel-detail">
				<table class="formTable" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
 <tbody>
  <tr class="firstRow">
   <td class="formHead" colspan="4" style="word-break: break-all;"><span style="font-size:14px;line-height:125%;font-family:宋体">公司资质证书</span></td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">证书编号:</td>
   <td style="width: 35%" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:company_qualification_certificate:cno" lablename="证书编号" class="inputText" value="${companyQualificationCertificate.cno}" validate="{maxlength:50,required:true}" isflag="tableflag" /> </span> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">证书类型:</td>
   <td style="width: 35%" class="formInput"> <span name="editable-input" style="display:inline-block;padding:2px;" class="selectinput"> <select name="m:company_qualification_certificate:ctype" lablename="证书类型" controltype="select" validate="{empty:false,required:true}" val="${companyQualificationCertificate.ctype}"> <option value="1">企业资质</option> <option value="2">施工监理</option> <option value="3">工程咨询</option> <option value="4">招标代理</option> <option value="5">造价咨询</option> <option value="6">水土保持</option> <option value="7">污水运营、环境</option> <option value="8">信息</option> <option value="9">测绘</option> <option value="10">施工</option> </select> </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">证书名称:</td>
   <td style="width: 35%; word-break: break-all;" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:company_qualification_certificate:cname" lablename="证书名称" class="inputText" value="${companyQualificationCertificate.cname}" validate="{maxlength:150,required:true}" isflag="tableflag" /> </span> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">发证机构:</td>
   <td style="width: 35%" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:company_qualification_certificate:institution" lablename="发证机构" class="inputText" value="${companyQualificationCertificate.institution}" validate="{maxlength:150}" isflag="tableflag" /> </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">发证时间:</td>
   <td style="width: 35%; word-break: break-all;" class="formInput"> <input name="m:company_qualification_certificate:certificationtime" type="text" class="Wdate" displaydate="0" datefmt="yyyy-MM-dd" value="<fmt:formatDate value='${companyQualificationCertificate.certificationtime}' pattern='yyyy-MM-dd'/>" validate="{empty:false}" /> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">有效期限:</td>
   <td style="width: 35%" class="formInput"> <input name="m:company_qualification_certificate:certificationlimit" type="text" class="Wdate" displaydate="0" datefmt="yyyy-MM-dd" value="<fmt:formatDate value='${companyQualificationCertificate.certificationlimit}' pattern='yyyy-MM-dd'/>" validate="{empty:false}" /> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">证书状态:</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="1"> 
   		<select name="m:company_qualification_certificate:status" lablename="证书类型" controltype="select" validate="{empty:false,required:true}" val="${companyQualificationCertificate.status}" >
   			<option value="0">在用</option>
   			<option value="1">过期</option>
   			<option value="2">废弃</option>
   		</select>
   </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">借用状态:</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="1"> 
   		<select name="m:company_qualification_certificate:isborrowed" lablename="证书类型" controltype="select" validate="{empty:false,required:true}" val="${companyQualificationCertificate.isborrowed}" >
   			<option value="0">未借出</option>
   			<option value="1">已借出</option>
   		</select>
   </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">承包范围:</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <textarea name="m:company_qualification_certificate:contractscope" lablename="承包范围" class="l-textarea" rows="5" cols="40" validate="{maxlength:2000}" isflag="tableflag">${companyQualificationCertificate.contractscope}</textarea> </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">备注:</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <textarea name="m:company_qualification_certificate:remark" lablename="承包范围" class="l-textarea" rows="5" cols="40" validate="{maxlength:2000}" isflag="tableflag">${companyQualificationCertificate.remark}</textarea> </span> </td>
  </tr>
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">上传原件扫描:</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3">
	<input  ctltype="attachment" right="w"  name="m:company_qualification_certificate:attachment" isdirectupload="0"  value='${companyQualificationCertificate.attachment}' validatable="false" validate="{&quot;maxlength&quot;:&quot;1000&quot;}"/> 
   </td>
   </td> 
  </tr> 
  
 </tbody>
</table>
			</div>
		</div>
		<input type="hidden" name="id" value="${companyQualificationCertificate.id}"/>
		<input type="hidden" id="saveData" name="saveData"/>
		<input type="hidden" name="executeType"  value="start" />
	</form>
</body>
</html>
