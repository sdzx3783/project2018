<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 招标方案审批</title>
	<%@include file="/codegen/include/customForm.jsp" %>
	<%@include file="/commons/include/ueditor.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/FlowDetailWindow.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/AttachMent.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/TablePermission.js"></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#biddingSchemeExamineForm').form();
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
							$('#biddingSchemeExamineForm').submit();
						});
					}else{
						frm.handleFieldName();
						$('#biddingSchemeExamineForm').submit();
					}
				}
			});
			$("a.run").click(function() {
				frm.ajaxForm(options);
				$("#saveData").val(0);
				$('#biddingSchemeExamineForm').attr("action","run.ht");
				if(frm.valid()){
					if(WebSignPlugin.hasWebSignField){
						WebSignPlugin.submit();
					}
					data=CustomForm.getData();
					//设置表单数据
					$("#formData").val(data);
					frm.handleFieldName();
					OfficePlugin.submit();
					$('#biddingSchemeExamineForm').submit();
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
						window.location.href = "${ctx}/makshi/bidding/biddingSchemeExamine/list.ht";
					}
				});
			} else {
				$.ligerDialog.error(obj.getMessage(),"提示信息");
			}
		}
	</script>
</head>
<body class="oa-mw-page">

    <div class="oa-mg20">
        <a class="oa-button oa-button--primary oa-button--blue save" id="dataFormSave" href="javascript:;">保存</a>
        <a class="oa-button oa-button--primary oa-button--blue" onclick="FlowDetailWindow({runId:${runId}})">流程明细</a>
        <a class="oa-button oa-button--primary oa-button--blue" href="list.ht">返回</a>
    </div>
    
    <div class="oa-mg20">
    	<form id="biddingSchemeExamineForm" method="post" action="save.ht">
            <table class="oa-table--default" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
                <caption>招标方案审批表</caption>
                <tr>
                    <td>方案名称:</td>
                    <td rowspan="1" colspan="3">${biddingSchemeExamine.scheme_name}
                        <input type="hidden" name="m:bidding_scheme_examineItem:scheme_name" lablename="方案名称" value="${biddingSchemeExamine.scheme_name}" validate="{maxlength:50}" isflag="tableflag" />
                    </td>
                </tr>
                <tr>
                    <td>编制依据:</td>
                    <td rowspan="1" colspan="3">${biddingSchemeExamine.scheme_evidence}
                        <input type="hidden" name="m:bidding_scheme_examineItem:scheme_evidence" lablename="编制依据" value="${biddingSchemeExamine.scheme_evidence}" validate="{maxlength:50}" isflag="tableflag" />
                    </td>
                </tr>
                <tr>
                    <td>方案内容:</td>
                    <td rowspan="1" colspan="3">${biddingSchemeExamine.scheme_content}
                        <textarea style="display: none;" name="m:bidding_scheme_examineItem:scheme_content" lablename="方案内容" rows="5" cols="40" validate="{maxlength:1000}" isflag="tableflag">${biddingSchemeExamine.scheme_content}</textarea>
                    </td>
                </tr>
                <tr>
                    <td>合同编号:</td>
                    <td>
                        <div class="oa-input-wrap">
                            <input class="oa-input" type="text" name="m:bidding_scheme_examineItem:scheme_contract_num" lablename="合同编号" value="${biddingSchemeExamine.scheme_contract_num}" validate="{maxlength:50}" isflag="tableflag" />
                        </div>
                    </td>
                    <td>合同名称:</td>
                    <td>
                        <div class="oa-input-wrap">
                            <input class="oa-input" type="text" name="m:bidding_scheme_examineItem:scheme_contract_name" lablename="合同名称" value="${biddingSchemeExamine.scheme_contract_name}" validate="{maxlength:50}" isflag="tableflag" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>申请人:</td>
                    <td>${biddingSchemeExamine.scheme_applicant}
                        <input type="hidden" name="m:bidding_scheme_examineItem:scheme_applicant" lablename="申请人" value="${biddingSchemeExamine.scheme_applicant}" validate="{maxlength:50}" isflag="tableflag" />
                    </td>
                    <td>申请日期:</td>
                    <td><fmt:formatDate value="${biddingSchemeExamine.scheme_appli_date}" pattern='yyyy-MM-dd'/>
                        <input type="hidden" name="m:bidding_scheme_examineItem:scheme_appli_date" lablename="申请日期" value="<fmt:formatDate value="${biddingSchemeExamine.scheme_appli_date}" pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" isflag="tableflag" />
                    </td>
                </tr>
                <tr>
                    <td>项目初审人员:</td>
                    <td rowspan="1" colspan="3">${biddingSchemeExamine.scheme_first_check_person}
                        <input  name="m:bidding_scheme_examineItem:scheme_first_check_person" type="hidden" ctltype="selector" class="users" lablename="项目初审人员" value="${biddingSchemeExamine.scheme_first_check_person}" validate="{required:true}" />
                    </td>
                </tr>
                <tr>
                    <td>附件:</td>
                    <td rowspan="1" colspan="3">
                        <input ctltype="attachment" right="r" name="m:bidding_scheme_examineItem:scheme_attachment" isdirectupload="0"  value='${biddingSchemeExamine.scheme_attachment}' validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}"/>
                    </td>
                </tr>
            </table>

    		<input type="hidden" name="id" value="${biddingSchemeExamine.id}"/>
    		<input type="hidden" id="saveData" name="saveData"/>
    		<input type="hidden" name="executeType"  value="start" />
    	</form>
    </div>
</body>
</html>
