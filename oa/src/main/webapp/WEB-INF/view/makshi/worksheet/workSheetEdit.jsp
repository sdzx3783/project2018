<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
    <title>编辑 考勤记录</title>
    <%@include file="/codegen/include/customForm.jsp" %>
    <%@include file="/commons/include/ueditor.jsp" %>
    <script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
    <script type="text/javascript" src="${ctx}/js/custom/common.js"></script>
    <script type="text/javascript">
        $(function() {
            var options={};
            if(showResponse){
                options.success=showResponse;
            }
            var frm=$('#workSheetForm').form();
            $("a.save").click(function() {
                frm.ajaxForm(options);
                $("#saveData").val(1);
                if(frm.valid()){
                    var clockTime=$("#clock_time").val();
                    var threeDayBefore=getCurDateAddDayDate(-6);
                    var clockDate=new Date(clockTime);
                    if(clockDate<threeDayBefore){
                        $.ligerDialog.error("只能补录五天以内的出勤记录!","提示信息");
                        return;
                    }
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
                            $('#workSheetForm').submit();
                        });
                    }else{
                        frm.handleFieldName();
                        $('#workSheetForm').submit();
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
                        window.location.href = "${ctx}/makshi/worksheet/workSheet/list.ht?ty=cq";
                    }
                });
            } else {
                $.ligerDialog.error(obj.getMessage(),"提示信息");
            }
        }
    </script>
    <style>
        #workSheetForm{
            margin: 0 11px;
        }
    </style>
</head>
<body>
<div class="panel" style="height:100%;overflow:auto;">
    <div class="panel-top">
        <div class="tbar-title">
            <c:choose>
                <c:when test="${not empty workSheetItem.id}">
                    <span class="tbar-label"><span></span>编辑考勤记录</span>
                </c:when>
                <c:otherwise>
                    <span class="tbar-label"><span></span>添加考勤记录</span>
                </c:otherwise>  
            </c:choose>
        </div>
        <div class="panel-toolbar">
            <div class="toolBar">
                <div class="group"><a class="link save" id="dataFormSave" href="javascript:;"><span></span>保存</a></div>
                <div class="l-bar-separator"></div>
                <div class="group"><a class="link back" href="list.ht?ty=cq"><span></span>返回</a></div>
            </div>
        </div>
    </div>
    <form id="workSheetForm" method="post" action="save.ht">
        <div type="custform">
            <div class="panel-detail">
                <table class="formTable" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled" width="-418">
 <tbody>
  <tr class="firstRow">
   <td class="formHead" colspan="6">出勤补录</td>
  </tr>
  <tr>
   <td style="width: 10%; word-break: break-all;" class="formTitle" align="right">姓名:</td>
   <td style="width: 15%" class="formInput"><br /> <input name="m:work_sheet:fullname" type="text" ctltype="selector" class="users" lablename="姓名" value="${workSheet.fullname}" validate="{empty:false,required:true}" readonly="readonly" /> </td>
   <%-- <td style="width: 10%; word-break: break-all;" class="formTitle" align="right">部门:</td>
   <td style="width: 15%" class="formInput"> <input name="m:work_sheet:org" type="text" ctltype="selector" class="orgs" lablename="部门" value="${workSheet.org}" validate="{empty:false,required:true}" readonly="readonly" /> </td>
   <td style="width: 10%; word-break: break-all;" class="formTitle" align="right">岗位:</td>
   <td style="width: 15%" class="formInput"> <input name="m:work_sheet:postion" type="text" ctltype="selector" class="positions" lablename="岗位" value="${workSheet.postion}" validate="{empty:false,required:true}" readonly="readonly" /> </td> --%>
  <%--  <td class="formTitle" align="right" colspan="1" rowspan="1" style="word-break: break-all;"><span style="font-size: 13.3333px; text-align: right; background-color: rgb(240, 241, 241);">类型:</span></td>
   <td class="formInput" align="left" colspan="1" rowspan="1"> <span name="editable-input" style="display:inline-block;padding:2px;" class="selectinput"> <select name="m:work_sheet:type" lablename="类型" controltype="select" validate="{empty:false,required:true}" val="${workSheet.type}"> <option value="补录">补录</option><option value="签到">签到</option>  </select> </span> </td> --%>
    <td class="formTitle" align="right" colspan="1" rowspan="1" style="word-break: break-all;">签到时间:
    </td>
    <td class="formInput"><span name="editable-input" style="display:inline-block;"> <input type="text" id="clock_time" name="m:work_sheet:clock_time" lablename="签到时间" class="inputText" validate="{required:true}" onclick="WdatePicker({minDate:'%y-%M-{%d-4}',maxDate:'%y-%M-%d'})"/> </span><font color="gray" style="font-size: 10px">只能补录5天内的出勤记录</font></td>
  </tr>
  <tr>
    <td style="width: 10%; word-break: break-all;" class="formTitle" align="right">备注</td>
   <td style="width: 15%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:work_sheet:remark" lablename="备注" class="inputText" value="${workSheet.remark}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
  </tr>
 </tbody>
</table>
            </div>
        </div>
        <input type="hidden" name="id" value="${workSheet.id}"/>
        <input type="hidden" id="saveData" name="saveData"/>
        <input type="hidden" name="executeType"  value="start" />
    </form>
</body>
</html>
