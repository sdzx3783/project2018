<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 参观登记信息-水保园</title>
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
			var frm=$('#visitorRegisterInfoForm').form();
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
							$('#visitorRegisterInfoForm').submit();
						});
					}else{
						frm.handleFieldName();
						$('#visitorRegisterInfoForm').submit();
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
						window.location.href = "${ctx}/makshi/waterprotectpark/visitorRegisterInfo/list.ht";
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
    	<form id="visitorRegisterInfoForm" method="post" action="save.ht">
            <table class="oa-table--default" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
                <caption>团体参观登记</caption>
                <tr>
                    <th>批次</th>
                    <td>
                        <span name="editable-input" isflag="tableflag">
                            <div class="oa-input-wrap oa-input-wrap--ib">
                                <input type="text" name="m:visitor_register_info:batch" lablename="批次" class="oa-input" value="${visitorRegisterInfo.batch}" validate="{required:true,maxlength:50}" validate="{maxlength:50}" isflag="tableflag" />
                            </div>
                        </span> 
                    </td>
                    <th>参观日期</th>
                    <td>
                        <div class="oa-input-wrap oa-input-wrap--ib">
                            <input id="visitTime" name="m:visitor_register_info:visitTime" type="text" class="Wdate oa-input" onFocus="WdatePicker({isShowWeek:true,onpicked:function() {getNewDate(this.value);},oncleared:function(dp){clearWeekDay();}})"  displaydate="0" datefmt="yyyy-MM-dd" value="<fmt:formatDate value='${visitorRegisterInfo.visitTime}' pattern='yyyy-MM-dd'/>" validate="{required:true,empty:false}" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <th>团体名称</th>
                    <td>
                        <span name="editable-input" isflag="tableflag">
                            <div class="oa-input-wrap oa-input-wrap--ib">
                                <input type="text" name="m:visitor_register_info:teamName" lablename="团体名称" class="oa-input" value="${visitorRegisterInfo.teamName}" validate="{required:true,maxlength:50}" isflag="tableflag" />
                            </div>
                        </span>
                    </td>
                    <th>所属类型</th>
                    <td>
                        <span name="editable-input" class="selectinput">
                            <div class="oa-select-wrap oa-select-wrap--ib">
                                <select name="m:visitor_register_info:type" class="oa-select" lablename="所属类型" controltype="select" validate="{empty:false}" val="${visitorRegisterInfo.type}">
                                    <option value="1">中小学生团体</option>
                                    <option value="2">政府部门团体</option>
                                </select>
                            </div>
                        </span>
                    </td>
                </tr>
                <tr>
                    <th>登记时间</th>
                    <td>
                        <div class="oa-input-wrap oa-input-wrap--ib">
                            <input name="m:visitor_register_info:registerTime" type="text" class="oa-input" displaydate="0" datefmt="yyyy-MM-dd" value="<fmt:formatDate value='${visitorRegisterInfo.registerTime}' pattern='yyyy-MM-dd'/>" readonly="readonly" validate="{required:true,empty:false}" />
                        </div>
                    </td>
                    <th>星期</th>
                    <td>
                        <span name="editable-input" isflag="tableflag">
                            <div class="oa-input-wrap oa-input-wrap--ib">
                                <input type="text" name="m:visitor_register_info:xq" readonly="readonly" lablename="星期" id="weekDay" class="oa-input" value="${visitorRegisterInfo.xq}" validate="{required:true,maxlength:4}" isflag="tableflag" />
                            </div>
                        </span>
                    </td>
                </tr>
                <tr>
                    <th>负责人</th>
                    <td>
                        <div class="oa-input-wrap oa-input-wrap--ib">
                            <input name="m:visitor_register_info:charger" type="text" ctltype="selector" class="users oa-input oa-hidden-trigger" lablename="负责人" value="${visitorRegisterInfo.charger}" validate="{empty:false}" readonly="readonly" />
                        </div>
                        <button class="oa-button-label oa-trigger-hidden-button" type="button">选择人员</button>
                    </td>
                    <th>联系电话</th>
                    <td>
                        <span name="editable-input" isflag="tableflag">
                            <div class="oa-input-wrap oa-input-wrap--ib">
                                <input type="text" name="m:visitor_register_info:tel" lablename="联系电话" class="oa-input" value="${visitorRegisterInfo.tel}" validate="{maxlength:50}" isflag="tableflag" />
                            </div>
                        </span>
                    </td>
                </tr>
                <tr>
                    <th>参观人数</th>
                    <td>
                        <span name="editable-input" isflag="tableflag">
                            <div class="oa-input-wrap oa-input-wrap--ib">
                                <input type="text" name="m:visitor_register_info:visitorNum" lablename="参观人数" class="oa-input" value="${visitorRegisterInfo.visitorNum}" validate="{required:true,number:true,maxIntLen:10,maxDecimalLen:0}" isflag="tableflag" />
                            </div>
                        </span>
                    </td>
                    <th>是否来函</th>
                    <td>
                        <span>
                            <label>
                                <input val="${visitorRegisterInfo.isLetter}" type="radio" name="m:visitor_register_info:isLetter" value="1" lablename="是否来函" validate="{required:true}" />是
                            </label>
                            <label>
                                <input val="${visitorRegisterInfo.isLetter}" type="radio" name="m:visitor_register_info:isLetter" value="0" lablename="是否来函" validate="{required:true}" />否
                            </label>
                        </span>
                    </td>
                </tr>
                <tr>
                    <th>接待员</th>
                    <td rowspan="1" colspan="3">
                        <div class="oa-input-wrap oa-input-wrap--ib">
                            <input name="m:visitor_register_info:greeter" type="text" ctltype="selector" class="users oa-input oa-hidden-trigger" lablename="接待员" value="${visitorRegisterInfo.greeter}" validate="{empty:false}" readonly="readonly" />
                        </div>
                        <button class="oa-button-label oa-trigger-hidden-button" type="button">选择人员</button>
                    </td>
                </tr>
                <tr>
                    <th>备注</th>
                    <td rowspan="1" colspan="3">
                        <span name="editable-input" isflag="tableflag">
                            <div class="oa-textarea-wrap">
                                <textarea name="m:visitor_register_info:remark" lablename="备注" class="oa-textarea" rows="5" cols="40" validate="{maxlength:1000}" isflag="tableflag">${visitorRegisterInfo.remark}</textarea>
                            </div>
                        </span>
                    </td>
                </tr>
            </table>

    		<input type="hidden" name="id" value="${visitorRegisterInfo.id}"/>
    		<input type="hidden" id="saveData" name="saveData"/>
    		<input type="hidden" name="executeType"  value="start" />
    	</form>
    </div>

<script type="text/javascript">
$(function(){
	$("input[name='m:visitor_register_info:visitTime']").blur(function(){
		var date=$(this).val();
		if(date.length<=0){
			$("#weekDay").val("");
		}else{
			getNewDate(date);
		}
	});

    // 触发隐藏的选择器组件
    $(".oa-trigger-hidden-button").on("click", function(){
        $(this).parent("td").find("a.oa-hidden-trigger").click();
    });

})
var dayArr=['日','一','二','三','四','五','六'];
function getNewDate(date){
	var date=new Date(date);
	var day=dayArr[date.getDay()];
	if(day){
		$("#weekDay").val(day);
	}
}
function clearWeekDay(){
	$("#weekDay").val('');
}
</script>
</body>
</html>
