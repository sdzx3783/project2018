<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>编辑 个人荣誉</title>
<%@include file="/codegen/include/customForm.jsp"%>
<%@include file="/commons/include/ueditor.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
<script type="text/javascript">
	$(function() {
		var options = {};
		if (showResponse) {
			options.success = showResponse;
		}
		var frm = $('#personalHonorForm').form();
		$("a.save").click(function() {
			frm.ajaxForm(options);
			$("#saveData").val(1);
			if (frm.valid()) {
				//如果有编辑器的情况下
				$("textarea[name^='m:'].myeditor").each(function(num) {
					var name = $(this).attr("name");
					var data = getEditorData(editor[num]);
					$("textarea[name^='" + name + "']").val(data);
				});

				if (WebSignPlugin.hasWebSignField) {
					WebSignPlugin.submit();
				}
				if (OfficePlugin.officeObjs.length > 0) {
					OfficePlugin.submit(function() {
						frm.handleFieldName();
						$('#personalHonorForm').submit();
					});
				} else {
					frm.handleFieldName();
					$('#personalHonorForm').submit();
				}
			}
		});
	});

	function showResponse(responseText) {
		var obj = new com.hotent.form.ResultMessage(responseText);
		if (obj.isSuccess()) {
			$.ligerDialog
					.confirm(
							obj.getMessage() + ",是否继续操作",
							"提示信息",
							function(rtn) {
								if (rtn) {
									window.location.href = window.location.href;
								} else {
									window.location.href = "${ctx}/makshi/honor/personalHonor/list.ht";
								}
							});
		} else {
			$.ligerDialog.error(obj.getMessage(), "提示信息");
		}
	}
</script>

</head>
<body class="oa-mw-page">

	<div class="oa-project-title"></div>

	<div class="oa-mg20">
		<a class="oa-button oa-button--primary oa-button--blue save"
			id="dataFormSave" href="javascript:;">保存</a> <a
			class="oa-button oa-button--primary oa-button--blue" href="list.ht">返回</a>
	</div>

	<div class="oa-mg20">

		<form id="personalHonorForm" method="post" action="save.ht">
			<div type="custform">
				<div class="panel-detail">
					<table class="oa-table--default" border="1" cellspacing="0"
						cellpadding="2" parser="addpermission" data-sort="sortDisabled">
						<caption>个人荣誉</caption>
						<tr>
							<td>员工荣誉编号</td>
							<td>
								<div class="oa-input-wrap  oa-input-wrap--ib">
									<input type="text" name="m:w_personal_honor:honor_num"
										lablename="员工荣誉编号" class="oa-input"
										value="${personalHonor.honor_num}" validate="{maxlength:50}"
										isflag="tableflag" />
								</div>
							</td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td>姓名</td>
							<td>
								<div class="oa-input-wrap oa-input-wrap--ib">
									<%--<input type="text" name="m:w_personal_honor:name" ctltype="selector"
								class="user oa-input oa-hidden-trigger" lablename="姓名"
								value="${personalHonor.name}" validate="{maxlength:50}"
								isflag="tableflag" />
								 --%>
									<input name="m:w_personal_honor:name" type="text" id="userId"
										ctltype="selector" class="users oa-input oa-hidden-trigger"
										lablename="姓名" value="${personalHonor.name}"
										validate="{empty:false}" readonly="readonly" />
								</div>
								<button class="oa-button-label oa-trigger-hidden-button"
									type="button">选择用户</button>
							</td>

							<td>工号</td>
							<td>
								<div class="oa-input-wrap  oa-input-wrap--ib">
									<input type="text" name="m:w_personal_honor:user_num"
										lablename="员工编号" class="oa-input"
										value="${personalHonor.user_num}" validate="{maxlength:50}"
										isflag="tableflag" />
								</div>
							</td>
						</tr>
						<tr>
							<td>奖项名称</td>
							<td>
								<div class="oa-input-wrap  oa-input-wrap--ib">
									<input type="text" name="m:w_personal_honor:honor_name"
										lablename="奖项名称" class="oa-input"
										value="${personalHonor.honor_name}" validate="{maxlength:50}"
										isflag="tableflag" />
								</div>
							</td>
							<td>奖项级别</td>
							<td>
								<div class="oa-input-wrap oa-input-wrap--ib">
									<input type="text" name="m:w_personal_honor:honor_level"
										ctltype="selector" class="user oa-input oa-hidden-trigger"
										lablename="奖项级别" value="${personalHonor.honor_level}"
										validate="{maxlength:50}" isflag="tableflag" />
								</div>
							</td>
						</tr>
						<tr>
							<td>颁发机构</td>
							<td>
								<div class="oa-input-wrap  oa-input-wrap--ib">
									<input type="text" name="m:w_personal_honor:issuing_authority"
										lablename="颁发机构" class="oa-input"
										value="${personalHonor.issuing_authority}"
										validate="{maxlength:50}" isflag="tableflag" />
								</div>
							</td>
							<td>颁发日期</td>
							<td>
								<div class="oa-input-wrap  oa-input-wrap--ib">
									<input name="m:w_personal_honor:issue_date" type="text"
										class="Wdate oa-input" datefmt="yyyy-MM-dd"
										value="<fmt:formatDate value='${personalHonor.issue_date}' pattern='yyyy-MM-dd'/>"
										validate="{empty:false}" />
								</div>
							</td>

						</tr>
						<tr>
							<td>备注</td>
							<td>
								<div class="oa-input-wrap  oa-input-wrap--ib">
									<input type="text" name="m:w_personal_honor:remark"
										lablename="备注" class="oa-input"
										value="${personalHonor.remark}" validate="{maxlength:50}"
										isflag="tableflag" />
								</div>
							</td>
							<td>查询网址</td>
							<td>
								<div class="oa-input-wrap  oa-input-wrap--ib">
									<input type="text" name="m:w_personal_honor:query_url"
										lablename="查询网址" class="oa-input"
										value="${personalHonor.query_url}" validate="{maxlength:50}"
										isflag="tableflag" />
								</div>
							</td>
						</tr>
						<tr>
							<td>附件</td>
							<td rowspan="1" colspan="3"><input ctltype="attachment"
								right="w" name="m:w_personal_honor:file" isdirectupload="0"
								value='${personalHonor.file}' validatable="false"
								validate="{&quot;maxlength&quot;:&quot;500&quot;}" /></td>
						</tr>
					</table>
					<input type="hidden" name="id" value="${personalHonor.id}" />
					<input type="hidden" id="saveData" name="saveData" /> <input
						type="hidden" name="executeType" value="start" />
		</form>

	</div>

	<script>
	 $(function(){
	        $(".oa-trigger-hidden-button").on("click", function(){
	            $(this).parent("td").find("a.oa-hidden-trigger").click();
	        });
	        
	        $("#userId").on("change",function(){
	            var userId =  $("input[name='m:w_personal_honor:nameID']").val();
	            if(userId==null||userId==""){return false;};
	            var alias="common_user_info_search";
	                $.ajax({
	                    type : "POST", 
	                    url:"/platform/bpm/bpmFormQuery/doQuery.ht",
	                    data:{alias:alias,querydata:"{userId:"+userId+"}",page:1,pagesize:10},
	                    dataType: "json",
	                    success:function(data){ 
	                        if(data!=null && data.list!=null && data.list.length>0){
	                            var rowData=data.list[0];
	                            $("input[name='m:w_personal_honor:user_num']").val(rowData.account);
	                        }
	                    }
	                });
	          });
	    });
	</script>
</body>
</html>