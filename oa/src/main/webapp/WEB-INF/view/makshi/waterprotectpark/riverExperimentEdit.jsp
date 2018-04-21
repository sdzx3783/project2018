<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 径流实验申请(水保示范园)</title>
	<%@include file="/codegen/include/customForm.jsp" %>
	<%@include file="/commons/include/ueditor.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/AttachMent.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery/plugins/jquery.attach.js" ></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/FlexUploadDialog.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/system/HtmlUploadDialog.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/form/TablePermission.js"></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#riverExperimentForm').form();
			$("#dataFormSave").click(function() {
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
							$('#riverExperimentForm').submit();
						});
					}else{
						frm.handleFieldName();
						$('#riverExperimentForm').submit();
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
						window.location.href = "${ctx}/makshi/waterprotectpark/riverExperiment/list.ht";
					}
				});
			} else {
				$.ligerDialog.error(obj.getMessage(),"提示信息");
			}
		}
	</script>
</head>
<body class="oa-mw-page">
    
    <div class="oa-pd20">
        <a class="oa-button oa-button--primary oa-button--blue save" id="dataFormSave">保存</a>
        <a class="oa-button oa-button--primary oa-button--blue" href="list.ht">返回</a>        
    </div>

    <div class="oa-pd20">

    	<form id="riverExperimentForm" method="post" action="save.ht">

            <table class="oa-table--default" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
                <tbody>
                    <caption>径流实验信息登记</caption>
                    <tr>
                        <td>实验名称</td>
                        <td>
                            <span name="editable-input" isflag="tableflag">
                                <div class="oa-input-wrap oa-input-wrap--ib">
                                    <input type="text" name="m:river_experiment:name" lablename="实验名称" class="oa-input" value="${riverExperiment.name}" validate='{"empty":false,"required":"true",maxlength:50}'  isflag="tableflag" />
                                </div>
                            </span>
                        </td>
                        <td>实验人员</td>
                        <td>
                            <div class="oa-input-wrap oa-input-wrap--ib">
                                <input name="m:river_experiment:experimenter" type="text" ctltype="selector" class="users oa-input oa-hidden-trigger" lablename="实验人员" value="${riverExperiment.experimenter}" validate='{"empty":false,"required":"true"}' readonly="readonly" />
                            </div>
                            <button class="oa-button-label oa-trigger-hidden-button" type="button">选择人员</button>
                        </td>
                    </tr>
                    <tr>
                        <td>取样时间</td>
                        <td>
                            <div class="oa-input-wrap oa-input-wrap--ib">
                                <input name="m:river_experiment:samlpeTime" type="text" class="Wdate oa-input" displaydate="0" datefmt="yyyy-MM-dd HH:mm:ss" value="<fmt:formatDate value='${riverExperiment.samlpeTime}' pattern='yyyy-MM-dd HH:mm:ss'/>" validate='{"empty":false,"required":"true"}' />
                            </div>
                        </td>
                        <td>取样人员</td>
                        <td>
                            <div class="oa-input-wrap oa-input-wrap--ib">
                                <input name="m:river_experiment:sampleMan" type="text" ctltype="selector" class="users oa-input oa-hidden-trigger" lablename="取样人员" value="${riverExperiment.sampleMan}" validate='{"empty":false,"required":"true"}' readonly="readonly" />
                            </div>
                            <button class="oa-button-label oa-trigger-hidden-button" type="button">选择人员</button>
                        </td>
                    </tr>
                    <tr>
                        <td>实验时间</td>
                        <td colspan="3" rowspan="1">
                            <div class="oa-input-wrap oa-input-wrap--ib">
                                <input name="m:river_experiment:experiTime" type="text" class="Wdate oa-input" displaydate="0" datefmt="yyyy-MM-dd HH:mm:ss" value="<fmt:formatDate value='${riverExperiment.experiTime}' pattern='yyyy-MM-dd HH:mm:ss'/>" validate='{"empty":false,"required":"true"}' />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>备注</td>
                        <td colspan="3" rowspan="1">
                            <span name="editable-input" isflag="tableflag">
                                <div class="oa-textarea-wrap">
                                    <textarea name="m:river_experiment:remark" lablename="备注" class="oa-textarea" rows="5" cols="40" validate="{maxlength:1000}" isflag="tableflag">${riverExperiment.remark}</textarea>
                                </div>
                            </span>
                        </td>
                    </tr>
                    <tr>
                        <td>附件</td>
                        <td colspan="3" rowspan="1">
                            <input ctltype="attachment" name="m:river_experiment:attachment" isdirectupload="0" right="w"  value='${riverExperiment.attachment}' validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}" />
                        </td>
                    </tr>
                </tbody>
            </table>

    		<input type="hidden" name="id" value="${riverExperiment.id}"/>
    		<input type="hidden" id="saveData" name="saveData"/>
    		<input type="hidden" name="executeType"  value="start" />

    	</form>

    </div>

<script>
    $(function(){
        // 触发隐藏的选择器组件
        $(".oa-trigger-hidden-button").on("click", function(){
            $(this).parent("td").find("a.oa-hidden-trigger").click();
        });
    });
</script>
</body>
</html>
