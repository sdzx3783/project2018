<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>编辑 技术评审周计划(水保部)</title>
<%@include file="/codegen/include/customForm.jsp"%>
<%@include file="/commons/include/ueditor.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
<script type="text/javascript">
        $(function() {
            var options={};
            if(showResponse){
                options.success=showResponse;
            }
            var frm=$('#techReviewWeekPlanSbbForm').form();
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
                            frm.sortList();
                            $('#techReviewWeekPlanSbbForm').submit();
                        });
                    }else{
                        frm.handleFieldName();
                        frm.sortList();
                        $('#techReviewWeekPlanSbbForm').submit();
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
                        window.location.href = "${ctx}/makshi/waterprotectpark/techReviewWeekPlanSbb/list.ht";
                    }
                });
            } else {
                $.ligerDialog.error(obj.getMessage(),"提示信息");
            }
        }
    </script>
<style>
.oa-input {
    width: auto;
}

</style>
</head>
<body class="oa-mw-page">

    <div class="oa-pd20">
        <a class="oa-button oa-button--primary oa-button--blue save"
            id="dataFormSave">保存</a> <a
            class="oa-button oa-button--primary oa-button--blue" href="list.ht">返回</a>
    </div>

    <form id="techReviewWeekPlanSbbForm" method="post" action="save.ht">
        <div class="oa-mg20">
            <table cellpadding="2" cellspacing="0" border="1"
                class="oa-table--default" parser="addpermission"
                data-sort="sortDisabled">
                <caption>技术评审周计划</caption>
                <tr>
                    <th>起始时间:</th>
                    <td>
                        <div class="oa-input-wrap oa-input-wrap--ib">
                            <input parser="datetable"
                                name="m:techReviewWeekPlanSbb:start_time"
                                validate="{required:true,maxlength:50}" type="text"
                                id="startTime"
                                onfocus="WdatePicker({maxDate:&#39;#F{$dp.$D(\&#39;endTime\&#39;);}&#39;,isShowWeek:true,onpicked:function() {getNewDate(this.value);},oncleared:function(dp){clearWeekDay();}})"
                                class="Wdate oa-input" displaydate="0" datefmt="yyyy-MM-dd"
                                value="<fmt:formatDate value='${techReviewWeekPlanSbb.start_time}' pattern='yyyy-MM-dd'/>" />
                        </div>
                    </td>
                    <th>结束时间:</th>
                    <td>
                        <div class="oa-input-wrap oa-input-wrap--ib">
                            <input parser="datetable" name="m:techReviewWeekPlanSbb:end_time"
                                id="endTime" validate="{required:true,maxlength:50}"
                                onfocus="WdatePicker({minDate:&#39;#F{$dp.$D(\&#39;startTime\&#39;);}&#39;})"
                                type="text" class="Wdate oa-input" displaydate="0"
                                datefmt="yyyy-MM-dd"
                                value="<fmt:formatDate value='${techReviewWeekPlanSbb.end_time}' pattern='yyyy-MM-dd'/>" />
                        </div>
                        <button type="button" onclick="updateWeekPlan()" class="oa-button oa-button--primary oa-button--blue">更新周计划</button>
                    </td>
                </tr>
                <tr>
                    <th>计划安排人:</th>
                    <td>
                        <div class="oa-input-wrap oa-input-wrap--ib">
                            <input parser="selectortable"
                                name="m:techReviewWeekPlanSbb:plan_arranger" type="text"
                                ctltype="selector"
                                initvalue="${techReviewWeekPlanSbb.plan_arrangerID }"
                                class="users oa-input oa-hidden-trigger" lablename="计划安排人"
                                value="${techReviewWeekPlanSbb.plan_arranger}"
                                validate="{required:true,maxlength:50}" readonly="readonly"
                                scope="{'value':'all','type':'system'}" />
                        </div>
                        <button class="oa-button-label oa-trigger-hidden-button" type="button">选择人员</button>
                    </td>
                    <th>执行人:</th>
                    <td>
                        <div class="oa-input-wrap oa-input-wrap--ib">
                            <input parser="selectortable"
                                name="m:techReviewWeekPlanSbb:plan_executor" type="text"
                                ctltype="selector" class="users oa-input oa-hidden-trigger"
                                lablename="执行人"
                                initvalue="${techReviewWeekPlanSbb.plan_executorID }"
                                value="${techReviewWeekPlanSbb.plan_executor}"
                                validate="{required:true,maxlength:50}" readonly="readonly"
                                scope="{'value':'all','type':'system'}" />
                        </div>
                        <button class="oa-button-label oa-trigger-hidden-button" type="button">选择人员</button>
                    </td>
                </tr>
                <tr>
                    <th>周次:</th>
                    <td>
                        <div class="oa-input-wrap oa-input-wrap--ib">
                            <input parser="inputtable" class="oa-input"
                                name="m:techReviewWeekPlanSbb:week" id="weekDay" type="text"
                                value="${techReviewWeekPlanSbb.week}" readonly="readonly"
                                showtype="{"
                                decimalValue":"0"}" validate="{number:true,maxIntLen:3,maxDecimalLen:0}" />
                        </div>
                    </td>
                    <th>创建时间:</th>
                    <td>
                        <div class="oa-input-wrap oa-input-wrap--ib">
                            <input parser="datetable"
                                name="m:techReviewWeekPlanSbb:applicantTime" type="text"
                                class="Wdate oa-input" displaydate="1" datefmt="yyyy-MM-dd"
                                value="<fmt:formatDate value='${techReviewWeekPlanSbb.applicantTime}' pattern='yyyy-MM-dd'/>"
                                validate="{required:true,empty:false}" />
                        </div>
                    </td>
                </tr>
                <tr style="display: none">
                    <th>表单id:</th>
                    <td rowspan="1" colspan="3">
                        <input parser="inputtable"
                            type="text" name="m:techReviewWeekPlanSbb:week_plan_RefId"
                            lablename="表单id" 
                            value="${techReviewWeekPlanSbb.week_plan_RefId}"
                            validate="{maxlength:20}" isflag="tableflag" />
                    </td>
                </tr>
            </table>
        </div>
        
        <div class="oa-mg20">
            <h3 class="oa-h3">周计划</h3>
            <div class="oa-table-scroll-wrap">
                <table id="weekPlanContainer" class="oa-table--default oa-table--nowrap">
                    <thead>
                        <tr>
                            <th></th>
                            <th>任务名称</th>
                            <th>任务被分配人</th>
                            <th>会议安排时间</th>
                            <th>会议地点</th>
                            <th>评审专家</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${weekPlanSbbList}" var="weekPlanSbb" varStatus="status">
                            <tr id='weekplanlistindex_${status.index }'>
                            	<td>
                            		<a href="javascript:delWeekRank('weekplanlistindex_${status.index }');" class="oa-button-label oa-trigger-hidden-button">删除</a>
                            	</td>
                                <td>
                                    <div class="oa-input-wrap">
                                        <input parser="inputtable" type="text"
                                            name="weekPlanSbbList[${status.index }].taskName" lablename="任务名称"
                                            class="oa-input" value="${weekPlanSbb.taskName}"
                                            validate="{required:true,maxlength:50}" isflag="tableflag" />
                                    </div>
                                </td>
                                <td>
                                    <div class="oa-input-wrap oa-input-wrap--ib">
                                        <input parser="selectortable" name="weekPlanSbbList[${status.index }].assigners"
                                            type="text" ctltype="selector"
                                            class="users oa-input oa-hidden-trigger" lablename="任务被分配人"
                                            initvalue="${weekPlanSbb.assignersID }"
                                            value="${weekPlanSbb.assigners}"
                                            validate="{required:true,empty:false}" readonly="readonly"
                                            scope="{'value':'all','type':'system'}" />
                                    </div>
                                    <button class="oa-button-label oa-trigger-hidden-button"
                                        type="button">选择人员</button>
                                </td>
                                <td>
                                    <div class="oa-input-wrap">
                                        <input parser="datetable" name="weekPlanSbbList[${status.index }].meeting_time"
                                            type="text" class="Wdate oa-input" displaydate="0"
                                            datefmt="yyyy-MM-dd"
                                            value="<fmt:formatDate value='${weekPlanSbb.meeting_time}' pattern='yyyy-MM-dd'/>"
                                            validate="{required:true,empty:false}" />
                                    </div>
                                </td>
                                <td>
                                    <div class="oa-input-wrap">
                                        <input parser="inputtable" type="text"
                                            name="weekPlanSbbList[${status.index }].meeting_addr" lablename="会议地点"
                                            class="oa-input" value="${weekPlanSbb.meeting_addr}"
                                            validate="{required:true,maxlength:50}" isflag="tableflag" />
                                    </div>
                                </td>
                                <td>
                                    <div class="oa-input-wrap">
                                        <input class="oa-input" parser="inputtable"
                                            name="weekPlanSbbList[${status.index }].assessors" type="text" lablename="评审专家"
                                            value="${weekPlanSbb.assessors}"
                                            validate="{required:true,maxlength:100}" isflag="tableflag" />
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty weekPlanSbbList || fn:length(weekPlanSbbList)<=0}">
                            <tr>
                                <td>该时间段暂无数据</td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>


        <input type="hidden" name="id" value="${techReviewWeekPlanSbb.id}" />
        <input type="hidden" id="saveData" name="saveData" />
        <input type="hidden" name="executeType" value="start" />
    </form>

    <!-- 周计划单个数据项模板 -->
    <script id="weekPlanTemplate" type="text/template">
        <tr id='weekplanlistindex_{index}'>
			<td>
                <a href="javascript:delWeekRank('weekplanlistindex_{index}');" class="oa-button-label oa-trigger-hidden-button">删除</a>
            </td>
            <td>
                <div class="oa-input-wrap">
                    <input parser="inputtable" type="text"
                    name="weekPlanSbbList{index}.taskName" lablename="任务名称"
                    class="oa-input" validate="{required:true,maxlength:50}"
                    isflag="tableflag" />
                </div>
            </td>
            <td>
                <div class="oa-input-wrap oa-input-wrap--ib">
                    <input parser="selectortable" name="weekPlanSbbList{index}.assigners"
                    type="text" ctltype="selector"
                    class="users oa-input oa-hidden-trigger" lablename="任务被分配人"
                    validate="{required:true,empty:false}" readonly="readonly"
                    scope="{'value':'all','type':'system'}" />
                </div>
                <button class="oa-button-label oa-trigger-hidden-button"
                type="button">选择人员</button>
            </td>
            <td>
                <div class="oa-input-wrap">
                    <input parser="datetable" name="weekPlanSbbList{index}.meeting_time"
                    type="text" class="Wdate oa-input" displaydate="0"
                    datefmt="yyyy-MM-dd" validate="{required:true,empty:false}" />
                </div>
            </td>
            <td>
                <div class="oa-input-wrap">
                    <input parser="inputtable" type="text"
                    name="weekPlanSbbList{index}.meeting_addr" lablename="会议地点"
                    class="oa-input" validate="{required:true,maxlength:50}"
                    isflag="tableflag" />
                </div>
            </td>
            <td>
                <div class="oa-input-wrap">
                    <input parser="inputtable" name="weekPlanSbbList{index}.assessors"
                    type="text" class="oa-input" lablename="评审专家"
                    validate="{required:true,maxlength:100}" isflag="tableflag" />
                </div>
            </td>
        </tr>
    </script>

<script type="text/javascript">
$(function(){

    // 触发隐藏的选择器组件
    $("body").on("click", ".oa-trigger-hidden-button", function(){
        $(this).parent("td").find("a.oa-hidden-trigger").click();
    });

    $("#startTime").blur(function(){
        var date=$(this).val();
        if(date.length<=0){
            $("#weekDay").val("");
        }else{
            getNewDate(date);
        }
    });
    
})
function getNewDate(date){
    var date=new Date(date);
    var day=$dp.cal.getP('W','W');
    if(day){
        $("#weekDay").val(day);
    }
}
function clearWeekDay(){
    $("#weekDay").val('');
}

String.prototype.repeat = function(data) {
    return this.replace(/\{\w+\}/g, function(str) {
        var prop = str.replace(/\{|\}/g, '');
        return data || '';
    });
}

/**
 * 更新周计划
 */
function updateWeekPlan(){
    var startTime = $("#startTime").val();
    var endTime   = $("#endTime").val();

    var reg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/; 
    var url = "/makshi/waterprotectpark/techReviewWeekPlanSbb/getWeekPlan.ht";

    if(startTime.length > 0 && endTime.length > 0){
        if(reg.test(startTime) && reg.test(endTime)){
            $.post(url, {
                'startTime': startTime,
                'endTime':endTime
            }, function(data){
                /**
                 * 清空原有周计划，这里获取的周计划数据填充至目标容器
                 */
                $("#weekPlanContainer tbody").html("");
                for(var i = 0; i < data.length; i++){
                    $("#weekPlanContainer tbody").append($("#weekPlanTemplate").text().repeat("["+ i +"]"));
                    fillWeekHtml(data[i], i);
                }
                if(data.length <= 0){
                    $("#weekPlanContainer tbody").html("<tr><td>该时间段暂无数据</td></tr>");
                }
            });
        }else{
            $.ligerDialog.warning("请输入合法的日期格式!");
        }

    }else{
        $.ligerDialog.warning("请输入起始时间和结束时间!");
        return false;
    }
}

function fillWeekHtml(weekplan, i){
    var last = $("#weekPlanContainer tr:last");
    $(last).find("input[name='weekPlanSbbList["+ i +"].taskName']").eq(0).val(weekplan.taskName);
    $(last).find("input[name='weekPlanSbbList["+ i +"].assigners']").eq(0).val(weekplan.assigners);
    $(last).find("input[name='weekPlanSbbList["+ i +"].assignersID']").eq(0).val(weekplan.assignersID);
    $(last).find("input[name='weekPlanSbbList["+ i +"].meeting_time']").eq(0).val(weekplan.meeting_time);
    $(last).find("input[name='weekPlanSbbList["+ i +"].meeting_addr']").eq(0).val(weekplan.meeting_addr);
    $(last).find("input[name='weekPlanSbbList["+ i +"].assessors']").eq(0).val(weekplan.assessors);
    /* $(last).find("input[name='s:weekPlanSbb:assessorsID']").eq(0).val(weekplan.assessorsID); */
}

function delWeekRank(id){
	$("#"+id).remove();
}
</script>
</body>
</html>
