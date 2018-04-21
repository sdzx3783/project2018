<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑单位会员汇总表</title>
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
			var frm=$('#summaryCompanyVipForm').form();
			$("a.save").click(function() {
				frm.ajaxForm(options);
				
				var r = /^\d+(\.\d{1,2})?$/;
				if($.trim($("#paymentStandard").val()) != '' && !r.test($("#paymentStandard").val())){
					$.ligerDialog.error("缴费标准，必须为数字","提示信息");
					return;
				}
				
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
							$('#summaryCompanyVipForm').submit();
						});
					}else{
						frm.handleFieldName();
						$('#summaryCompanyVipForm').submit();
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
						window.location.href = "${ctx}/makshi/summary/company/list.ht";
					}
				});
			} else {
				$.ligerDialog.error(obj.getMessage(),"提示信息");
			}
		}
	</script>
	<style>
		input.oa-new-input, textarea.oa-new-textarea {
			border: 1px solid #8c9fd6 !important;
		}
	</style>
</head>
<body>
<div class="panel" style="height:100%;overflow:auto;">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${not empty summaryCompanyVip.id}">
			        <span class="tbar-label"><span></span>编辑单位会员汇总表</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label"><span></span>添加单位会员汇总表</span>
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
	<form id="summaryCompanyVipForm" method="post" action="save.ht">
		<div type="custform">
			<div class="panel-detail">
				<table class="oa-table--default" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
					 <caption>单位会员汇总表</caption>
					 <tbody>
						  <tr>
						   <th style="width: 15%;">发证机构:</th>
						   <td style="width: 35%"><span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:summary_company_vip:organization" lablename="发证机构" class="oa-new-input" value="${summaryCompanyVip.organization}"  isflag="tableflag" /> </span> </td>
						   <th style="width: 15%;">级别:</th>
						   <td style="width: 35%;"><span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:summary_company_vip:level" lablename="证书名称" class="oa-new-input" value="${summaryCompanyVip.level}"  isflag="tableflag" /> </span> </td>
						  </tr>
						  <tr>
						   <th style="width: 15%;">所属部门:</th>
						   <td style="width: 35%"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> 
						   <input name="m:summary_company_vip:department" type="text" ctltype="selector" class="oa-new-input oa-middle org" lablename="所属部门" readonly="readonly" value="${summaryCompanyVip.department }"  />
						   <th style="width: 15%;">董事长协会职务:</th>
						   <td style="width: 35%"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> 
						   <input type="text" name="m:summary_company_vip:duties" lablename="董事长协会职务"   class="oa-new-input"  value="${summaryCompanyVip.duties}" validate="{maxlength:10}" isflag="tableflag" /> </span> 
						   </td>
						  </tr>
						  <tr>
						   <th style="width: 15%;">证书编号:</th>
						   <td style="width: 35%"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> 
						   <input type="text" name="m:summary_company_vip:certificateNo" lablename="证书编号" class="oa-new-input" value="${summaryCompanyVip.certificateNo}"  isflag="tableflag" /> </span> </td>
						   <th style="width: 15%;">首次入会时间:</th>
						   <td style="width: 35%"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> 
						   <input type="text" name="m:summary_company_vip:membershipTime" lablename="首次入会时间"   class="Wdate oa-new-input" displaydate="0" datefmt="yyyy" value="${summaryCompanyVip.membershipTime}" validate="{maxlength:10}" isflag="tableflag" /> </span> 
						   </td>
						  </tr>
						  <tr>
						   <th style="width: 15%;">缴费标准(元):</th>
						   <td style="width: 35%"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> 
						   <input type="text" name="m:summary_company_vip:paymentStandard" lablename="缴费标准(元)" class="oa-new-input" id="paymentStandard"  value="${summaryCompanyVip.paymentStandard}"  isflag="tableflag" /> </span> </td>
						   <th style="width: 15%;">会费已缴纳年度:</th>
						   <td style="width: 35%"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> 
						   <input type="text" name="m:summary_company_vip:vipYears" lablename="会费已缴纳年度" class="oa-new-input" onkeyup="this.value=this.value.replace(/[^\d]/g,'') "  value="${summaryCompanyVip.vipYears}" validate="{maxlength:5}" isflag="tableflag" /> </span> </td>
						  </tr>
						  <tr>
						   <th style="width: 15%;">发证日期:</th>
						   <td style="width: 35%;"> <input name="m:summary_company_vip:certificationDate" type="text" class="Wdate oa-new-input" displaydate="0" datefmt="yyyy-MM-dd" value="${summaryCompanyVip.certificationDate}"  /> </td>
						   <th style="width: 15%;">有效日期:</th>
						   <td style="width: 35%"> <input name="m:summary_company_vip:certeffectiveTime" type="text" class="Wdate oa-new-input" displaydate="0" datefmt="yyyy-MM-dd" value="${summaryCompanyVip.certeffectiveTime}" /> </td>
						  </tr>
						  <tr>
						   <th style="width: 15%;">联系人:</th>
						   <td style="width: 35%"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> 
						   <input type="text" name="m:summary_company_vip:contactser" lablename="联系人" class="oa-new-input" value="${summaryCompanyVip.contactser}"  isflag="tableflag" /> </span> </td>
						   <th style="width: 15%;">联系电话:</th>
						   <td style="width: 35%"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> 
						   <input type="text" name="m:summary_company_vip:contactsPhone" lablename="联系电话 "   class="oa-new-input"  value="${summaryCompanyVip.contactsPhone}"  isflag="tableflag" /> </span> 
						   </td>
						  </tr>
						  <tr>
						   <th style="width: 15%;">备注:</th>
						   <td style="width: 35%;" rowspan="1" > <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <textarea name="m:summary_company_vip:remark" lablename="备注" class="oa-new-textarea" rows="5" cols="40" validate="{maxlength:500}" isflag="tableflag">${summaryCompanyVip.remark}</textarea> </span> </td>
						   <th style="width: 15%;">附件:</th>
						   <td style="width: 35%;" rowspan="1" > 
						   	 <input ctltype="attachment" name="m:summary_company_vip:attachment" isdirectupload="0" right="w"  value='${summaryCompanyVip.attachment}' validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}" />
						   </td>
						  </tr>
					 </tbody>
				</table>
			</div>
		</div>
		<input type="hidden" name="id" value="${summaryCompanyVip.id}"/>
		<input type="hidden" id="saveData" name="saveData"/>
		<input type="hidden" name="executeType"  value="start" />
	</form>
</body>
</html>
