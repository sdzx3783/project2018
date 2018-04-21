<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
    <title>qq明细</title>
    <%@include file="/commons/include/customForm.jsp"%>
    <script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/FlowDetailWindow.js"></script>
</head>
<body class="oa-mw-page">


    <div class="oa-top-wrap">
        <a class="oa-button oa-button--primary oa-button--blue" onclick="FlowDetailWindow({runId:${runId}})">流程明细</a>
        <a class="oa-button oa-button--primary oa-button--blue" href="list.ht">返回</a>
    </div>

    <div class="oa-main-wrap">
        <table class="oa-table--default" border="1" cellspacing="0" cellpadding="2" data-sort="sortDisabled" parser="addpermission">
            <caption>设备维修</caption>
            <tr>
                <td>提交人:</td>
                <td> ${hjEquipment.name} </td>
                <td>提交日期:</td>
                <td> <fmt:formatDate value='${hjEquipment.date}' pattern='yyyy-MM-dd'/> </td>
            </tr>
            <tr>
                <td>所属部门:</td>
                <td> ${hjEquipment.department} </td>
                <td>项目名称:</td>
                <td>${hjEquipment.project_name}</td>
            </tr>
            <tr>
                <td>设备名称:</td>
                <td>${hjEquipment.n_name}</td>
                <td>维修内容:</td>
                <td> <span name="editable-input" isflag="tableflag"> ${hjEquipment.content} </span> </td>
            </tr>
            <tr>
                <td>数量:</td>
                <td> <span name="editable-input" isflag="tableflag"> ${hjEquipment.quantity} </span> </td>
                <td>维修单位:</td>
                <td>${hjEquipment.maintaining_unit}</td>
            </tr>
            <tr>
                <td>维修费用:</td>
                <td colspan="3"> <span
                    name="editable-input" 
                isflag="tableflag"> ${hjEquipment.cost} </span></td>
            </tr>
            <tr>
                <td>备注:</td>
                <td rowspan="1" colspan="3"> <span name="editable-input" isflag="tableflag"> ${hjEquipment.note} </span> </td>
            </tr>
            <tr>
                <td>附件:</td>
                <td rowspan="1" colspan="3">
                    <div name="div_attachment_container" right="r">
                        <div class="attachement"></div>
                        <textarea style="display:none" controltype="attachment" name="enclosure" lablename="附件" readonly="readonly">${hjEquipment.enclosure}</textarea>
                    </div>
                </td>
            </tr>
            <tr style="display:none">
                <td colspan="1" rowspan="1"> <span name="editable-input" isflag="tableflag"> ${hjEquipment.projectTaskId} </span> </td>
            </tr>
        </table>
    </div>

</body>
</html>