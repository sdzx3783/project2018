<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>编辑物品</title>
<%@include file="/commons/include/customForm.jsp"%>
<%@include file="/commons/include/ueditor.jsp"%>
<script src="${ctx}/js/hotent/formdata.js"></script>
<script src="${ctx}/js/hotent/CustomValid.js"></script>
<script src="${ctx}/js/hotent/subform.js"></script>
<script src="${ctx}/js/hotent/platform/bpm/FlowDetailWindow.js"></script>
<script>
        $(function() {
            var options={};
            if(showResponse){
                options.success=showResponse;
            }
            var frm=$('#hjGoodsPurchasedsForm').form();
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
                            frm.sortList();
                            $('#hjGoodsPurchasedsForm').submit();
                        });
                    }else{
                        frm.handleFieldName();
                        frm.sortList();
                        $('#hjGoodsPurchasedsForm').submit();
                    }
                }
            });
            $("a.run").click(function() {
                frm.ajaxForm(options);
                $("#saveData").val(0);
                $('#hjGoodsPurchasedsForm').attr("action","run.ht");
                if(frm.valid()){
                    if(WebSignPlugin.hasWebSignField){
                        WebSignPlugin.submit();
                    }
                    data=CustomForm.getData();
                    //设置表单数据
                    $("#formData").val(data);
                    frm.handleFieldName();
                    OfficePlugin.submit();
                    frm.sortList();
                    $('#hjGoodsPurchasedsForm').submit();
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
                        window.location.href = "${ctx}/makshi/hj/hjGoodsPurchaseds/list.ht";
                    }
                });
            } else {
                $.ligerDialog.error(obj.getMessage(),"提示信息");
            }
        }
    </script>
    <style>
        .oa-input-wrap--ib{
            display: inline-block;
        }
    </style>
</head>
<body class="oa-mw-page">

    <div class="oa-project-title">
        <c:choose>
            <c:when test="${not empty hjGoodsPurchasedsItem.id}">
                <h2>添加物品</h2>
            </c:when>
        </c:choose>
    </div>

	<div class="oa-top-wrap">
        <a class="oa-button oa-button--primary oa-button--blue" id="dataFormSave">保存</a>
        <c:if test="${runId!=0}">
           <%--  <a class="oa-button oa-button--primary oa-button--blue" onclick="FlowDetailWindow({runId:${runId}})">流程明细</a>--%>
        </c:if>
        <a class="oa-button oa-button--primary oa-button--blue" href="list.ht">返回</a>
    </div>

    <div class="oa-main-wrap">
        <form id="hjGoodsPurchasedsForm" method="post" action="save.ht">
            <table class="oa-table--default" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
                    <caption>物品采购</caption>
                    <tr>
                        <td>申请人:</td>
                        <td>${hjGoodsPurchaseds.applicant}</td>
                        <td>申请日期:</td>
                        <td>
                            <fmt:formatDate value='${hjGoodsPurchaseds.appli_date}' pattern='yyyy-MM-dd' />
                        </td>
                    </tr>
                    <tr>
                        <td>申请部门:</td>
                        <td>${hjGoodsPurchaseds.appli_department}</td>
                        <td>采购方式:</td>
                        <td>
                            <div class="oa-select-wrap">
                                <select class="oa-select" name="m:hj_goods_purchaseds:type" lablename="采购方式" controltype="select" validate="{empty:false}" val="${hjGoodsPurchaseds.type}">
                                    <option value="0">请选择采购方式</option>
                                    <option value="1">委托办公室</option>
                                    <option value="2">部门自行采购</option>
                                </select>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>物品名称:</td>
                        <td>
                            <div class="oa-input-wrap">
                                <input type="text"
                                    name="m:hj_goods_purchaseds:name" lablename="物品名称"
                                    class="oa-input" value="${hjGoodsPurchaseds.name}"
                                    validate="{maxlength:50}" isflag="tableflag" />
                            </div>
                        </td>
                        <td>规格型号:</td>
                        <td>
                            <div class="oa-input-wrap">
                                <input type="text"
                                    name="m:hj_goods_purchaseds:specification" lablename="规格型号"
                                    class="oa-input" value="${hjGoodsPurchaseds.specification}"
                                    validate="{maxlength:50}" isflag="tableflag" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>数量:</td>
                        <td>
                            <div class="oa-input-wrap">
                                <input type="text"
                                    name="number" lablename="数量" class="oa-input"
                                    value="${hjGoodsPurchaseds.number}" validate="{maxlength:50}"
                                    isflag="tableflag" />
                            </div>
                        </td>
                        <td>单价:</td>
                        <td>
                            <div class="oa-input-wrap">
                                <input type="text"
                                    name="price" lablename="质量要求" class="oa-input"
                                    value="${hjGoodsPurchaseds.price}" validate="{maxlength:50}"
                                    isflag="tableflag" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>领用人:</td>
                        <td>
                            <div class="oa-input-wrap oa-input-wrap--ib">
                                <input
                                    name="m:hj_goods_purchaseds:lname" type="text" id="lUserId"
                                    ctltype="selector" class="user oa-input oa-hidden-trigger" lablename="领用人"
                                    value="${hjGoodsPurchaseds.lname}" validate="{empty:false}"
                                    readonly="readonly" showcuruser="0" />
                            </div>
                            <button type="button" class="oa-trigger-hidden-button oa-button-label">选择人员</button>
                        </td>
                        <td>采购日期:</td>
                        <td>
                            <div class="oa-input-wrap">
                                <input
                                    name="m:hj_goods_purchaseds:date" type="text" class="Wdate oa-input"
                                    displaydate="0" datefmt="yyyy-MM-dd"
                                    value="<fmt:formatDate value='${hjGoodsPurchaseds.date}' pattern='yyyy-MM-dd'/>"
                                    validate="{empty:false}" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>领用部门:</td>
                        <td colspan="3">
                            <div class="oa-input-wrap oa-input-wrap--ib">
                                <input 
                                        name="m:hj_goods_purchaseds:ldepartment" type="text"
                                        ctltype="selector" class="org oa-input oa-hidden-trigger" lablename="领用部门"
                                        value="${hjGoodsPurchaseds.ldepartment}"
                                        validate="{empty:false}" readonly="readonly" showcurorg="0" />
                            </div>
                            <button type="button" class="oa-trigger-hidden-button oa-button-label">选择部门</button>
                        </td>
                    </tr>
                    <tr>
                        <td>备注:</td>
                        <td rowspan="1" colspan="3">
                            <div class="oa-textarea-wrap">
                                <textarea
                                    name="m:hj_goods_purchaseds:remarks" lablename="备注"
                                    class="oa-textarea" rows="5" cols="40"
                                    validate="{maxlength:1000}" isflag="tableflag">${hjGoodsPurchaseds.remarks}
                                </textarea>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>附件</td>
                        <td rowspan="1" colspan="3">
                            <input  ctltype="attachment" name="m:hj_goods_purchaseds:attachment" lablename="附件" isdirectupload="0" right="w" 
                                value='${hjGoodsPurchaseds.attachment}' validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}" />
                        </td>
                    </tr>
            </table>

            <input name="s:hjwppurchaselist:id" type="hidden" pk="true" value="" />
            <input type="hidden" name="id" value="${hjGoodsPurchaseds.id}" /> 
            <input type="hidden" name="refId" value="${hjGoodsPurchaseds.id}" /> 
            <input type="hidden" name="id1" value="${hjGoodsPurchaseds.id1}" /> 
            <input type="hidden" id="saveData" name="saveData" /> 
            <input type="hidden" name="executeType" value="start" />
        </form>
    </div>
    
<script type="text/javascript">
$(function(){
        $(".oa-trigger-hidden-button").on("click", function(){
            $(this).parent("td").find("a.oa-hidden-trigger").click();
        });


     $("#lUserId").on("change",function(){
      var userId =  $("input[name='m:hj_goods_purchaseds:lnameID']").val();
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
                      $("input[name='m:hj_goods_purchaseds:ldepartment']").val(rowData.orgname);
                  }
              }
          });
    });
});
</script>   
</body>
</html>
