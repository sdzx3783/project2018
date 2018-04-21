<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>设施设备维修申请(水保园流程)明细</title>
<%@include file="/commons/include/customForm.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
</head>
<body class="oa-mw-page">

    <div class="oa-pd20">
        <a class="oa-button oa-button--primary oa-button--blue" href="list.ht">返回</a>
    </div>

    <div class="oa-pd20">

        <table class="oa-table--default" parser="addpermission">
            <caption>设施设备维修</caption>
            <tr>
                <th>申请人</th>
                <td> ${equipmentMaintenanceApplication.applicant} </td>
                <th>申请时间</th>
                <td> <fmt:formatDate value='${equipmentMaintenanceApplication.applicationTime}' pattern='yyyy-MM-dd'/> </td>
            </tr>
            <tr>
                <th>职能组</th>
                <td> ${equipmentMaintenanceApplication.org} </td>
                <th>故障发生时间</th>
                <td> <fmt:formatDate value='${equipmentMaintenanceApplication.faultTime}' pattern='yyyy-MM-dd'/> </td>
            </tr>
            <tr>
                <th>设备名称</th>
                <td> <span name="editable-input" isflag="tableflag"> ${equipmentMaintenanceApplication.equipmentName} </span> </td>
                <th>存在问题及故障部位</th>
                <td> <span name="editable-input" isflag="tableflag"> ${equipmentMaintenanceApplication.problemDesc} </span> </td>
            </tr>
            <tr>
                <th>维修方案</th>
                <td> <span name="editable-input" isflag="tableflag"> ${equipmentMaintenanceApplication.maintenancePlan} </span> </td>
                <th>维修费用</th>
                <td> ${equipmentMaintenanceApplication.maintenanceCost} </td>
            </tr>
            <%-- <tr>
                <th>部门负责人</th>
                <td> ${equipmentMaintenanceApplication.orgCharger} </td>
                 <th>部门领导</th>
                <td> ${equipmentMaintenanceApplication.orgLeader} </td>
            </tr> --%>
            <tr>
                <th>备注</th>
                <td colspan="3"><span name="editable-input" isflag="tableflag"> ${equipmentMaintenanceApplication.remark} </span> </td>
            </tr>
            <tr>
                <th>附件</th>
                <td colspan="3">
                    <div name="div_attachment_container" right="r">
                        <div class="attachement"></div>
                        <textarea style="display:none" controltype="attachment" name="attachment" lablename="附件" readonly="readonly">${equipmentMaintenanceApplication.attachment}</textarea>
                    </div>
                </td>
            </tr>
        </table>

    </div>

</body>
</html>

