<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>编辑 物品信息</title>
<%@include file="/codegen/include/customForm.jsp"%>
<%@include file="/commons/include/ueditor.jsp"%>
<script src="${ctx}/js/hotent/formdata.js"></script>
<script src="${ctx}/js/hotent/CustomValid.js"></script>
<script src="${ctx}/js/hotent/subform.js"></script>
<script src="${ctx}/js/hotent/platform/form/AttachMent.js"></script>
<script src="${ctx}/js/hotent/platform/form/TablePermission.js"></script>
<script>
    $(function() {
        var options = {};
        if (showResponse) {
            options.success = showResponse;
        }
        var frm = $('#goodsPurchaseForm').form();
        $("#dataFormSave").click(function() {
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
                        frm.sortList();
                        $('#goodsPurchaseForm').submit();
                    });
                } else {
                    frm.handleFieldName();
                    frm.sortList();
                    $('#goodsPurchaseForm').submit();
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
                                    window.location.href = "${ctx}/makshi/operation/goodsPurchase/list.ht";
                                }
                            });
        } else {
            $.ligerDialog.error(obj.getMessage(), "提示信息");
        }
    }
</script>
</head>
<body class="oa-mw-page">

    <div class="oa-pd20">
        <a class="oa-button oa-button--primary oa-button--blue save" id="dataFormSave" href="javascript:;">保存</a>
        <a class="oa-button oa-button--primary oa-button--blue back" href="list.ht">返回</a>
    </div>

    <div class="oa-pd20">
        <form id="goodsPurchaseForm" method="post" action="save.ht">
            <table class="oa-table--default" border="1" cellspacing="0" cellpadding="2"
                parser="addpermission" data-sort="sortDisabled">
                    <caption>
                        <c:if test="${not empty goodsPurchase.id}">
                            编辑物品信息
                        </c:if>
                        <c:if test="${ empty goodsPurchase.id}">
                            添加物品信息
                        </c:if>
                    </caption>
                    <tr>
                        <th>申请人：</th>
                        <td>
                            <div class="oa-input-wrap oa-input-wrap--ib">
                                <input name="m:goods_purchase:applicant" type="text"
                                ctltype="selector" class="user oa-input oa-hidden-trigger" lablename="申请人"
                                value="${goodsPurchase.applicant}" validate="{empty:false}"
                                readonly="readonly" showcuruser="0" />                              
                            </div>
                            <button class="oa-button-label oa-trigger-hidden-button" type="button">选择人员</button>
                        </td>
                        <th>申请部门：</th>
                        <td>
                            <div class="oa-input-wrap oa-input-wrap--ib">
                                <input
                                name="m:goods_purchase:appli_department" type="text"
                                ctltype="selector" class="org oa-input oa-hidden-trigger" lablename="申请部门"
                                value="${goodsPurchase.appli_department}"
                                validate="{empty:false}" readonly="readonly" showcurorg="0" />
                            </div>
                            <button class="oa-button-label oa-trigger-hidden-button" type="button">选择部门</button>
                        </td>
                    </tr>
                    <tr>
                        <th>申请日期：</th>
                        <td>
                            <div class="oa-input-wrap oa-input-wrap--ib">
                                <input
                                name="m:goods_purchase:appli_date" type="text" class="Wdate oa-input"
                                displaydate="0" datefmt="yyyy-MM-dd"
                                value="<fmt:formatDate value='${goodsPurchase.appli_date}' pattern='yyyy-MM-dd'/>"
                               validate="{&quot;date&quot;:&quot;true&quot;,&quot;required&quot;:&quot;true&quot;}"/>
                            </div>

                        </td>
                        <th>物品名称：</th>
                        <td>
                            <div class="oa-input-wrap oa-input-wrap--ib">
                                <input type="text" name="m:goods_purchase:name" lablename="物品名称"
                                    class="oa-input" value="${goodsPurchase.name}"
                                    validate="{maxlength:50}" isflag="tableflag" />                             
                            </div>

                        </td>   
                    </tr>
                    <tr>
                        <th>规格型号：</th>
                        <td>
                            <div class="oa-input-wrap oa-input-wrap--ib">
                                <input type="text"
                                    name="m:goods_purchase:specification" lablename="规格型号"
                                    class="oa-input" value="${goodsPurchase.specification}"
                                    validate="{maxlength:50}" isflag="tableflag" />
                            </div>
                        </td>
                        <th>数量：</th>
                        <td>
                            <div class="oa-input-wrap oa-input-wrap--ib">
                                <input type="text"
                                    name="number" lablename="数量"
                                    class="oa-input" value="${goodsPurchase.number}"
                                    validate="{maxlength:50,required:true}" isflag="tableflag" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th>单价：</th>
                        <td>
                            <div class="oa-input-wrap oa-input-wrap--ib">
                                <input type="text"
                                    name="m:goods_purchase:price" lablename="单价" class="oa-input"
                                    value="${goodsPurchase.price}" validate="{maxlength:50}"
                                    isflag="tableflag" />
                            </div>
                        </td>
                        <th>采购日期：</th>
                        <td>
                            <div class="oa-input-wrap oa-input-wrap--ib">
                                <input
                                    name="m:goods_purchase:purchase_date" type="text" class="Wdate oa-input"
                                    displaydate="0" datefmt="yyyy-MM-dd"
                                    value="<fmt:formatDate value='${goodsPurchase.purchase_date}' pattern='yyyy-MM-dd'/>"
                                    validate="{empty:false}" />                             
                            </div>

                        </td>
                    </tr>
                    <tr>
                        <th>领用人：</th>
                        <td>
                            <div class="oa-input-wrap oa-input-wrap--ib">
                                <input
                                name="m:goods_purchase:recipients_user" type="text"
                                ctltype="selector" class="user oa-input oa-hidden-trigger" lablename="领用人"
                                value="${goodsPurchase.recipients_user}" validate="{empty:false}"
                                readonly="readonly" showcuruser="0" />
                            </div>
                            <button class="oa-button-label oa-trigger-hidden-button" type="button">选择人员</button>
                        </td>
                        <th>领用部门：</th>
                        <td>
                            <div class="oa-input-wrap oa-input-wrap--ib">
                                <input
                                name="m:goods_purchase:recipients_department" type="text"
                                ctltype="selector" class="org oa-input oa-hidden-trigger" lablename="领用部门"
                                value="${goodsPurchase.recipients_department}"
                                validate="{empty:false}" readonly="readonly" showcurorg="0" />                              
                            </div>
                            <button class="oa-button-label oa-trigger-hidden-button" type="button">选择部门</button>
                        </td>
                    </tr>
                    <tr>
                        <th>附件：</th>
                        <td colspan="3">
                            <input ctltype="attachment" right="w"
                            name="m:goods_purchase:attachment" isdirectupload="0"
                            value='${goodsPurchase.attachment}' validatable="false"
                            validate="{&quot;maxlength&quot;:&quot;500&quot;}" />
                        </td>
                    </tr>
            </table>

            <input type="hidden" name="id" value="${goodsPurchase.id}" /> 
            <input type="hidden" name="price" value="${goodsPurchase.price}" /> 
            <input type="hidden" id="saveData" name="saveData" /> 
            <input type="hidden" name="executeType" value="start" />
            <input type="hidden" name="refId" value="${goodsPurchase.refId}" />
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
