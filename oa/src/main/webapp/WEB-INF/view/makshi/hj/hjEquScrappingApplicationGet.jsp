<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
    <title>设备报废申请明细</title>
    <%@include file="/commons/include/customForm.jsp"%>
    <script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/FlowDetailWindow.js"></script>
</head>
<body class="oa-mw-page">

    <div class="oa-pd20">
        <a class="oa-button oa-button--primary oa-button--blue" onclick="FlowDetailWindow({runId:${runId}})">流程明细</a>
        <a class="oa-button oa-button--primary oa-button--blue" href="list.ht">返回</a>
    </div>

    <div class="oa-pd20 oa-pdt0">
        <table class="oa-table--default" border="1" cellspacing="0" cellpadding="2" data-sort="sortDisabled" parser="addpermission">
            <caption>设备报废</caption>
            <tr>
                <td >申请人:</td>
                <td> ${hjEquScrappingApplication.name} </td>
                <td >申请日期:</td>
                <td> <fmt:formatDate value='${hjEquScrappingApplication.date}' pattern='yyyy-MM-dd'/> </td>
                
            </tr>
            <tr>
                <td >所属部门:</td>
                <td > ${hjEquScrappingApplication.department} </td>
                <td >项目名称:</td>
                <td>${hjEquScrappingApplication.project_name}</td>
                
            </tr>
            <tr>
                <td >设备名称:</td>
                <td >${hjEquScrappingApplication.n_name}</td>
                <td >数量:</td>
                <td> <span name="editable-input" isflag="tableflag"> ${hjEquScrappingApplication.number} </span> </td>
            </tr>
            <tr>
                <td >购入时间:</td>
                <td><fmt:formatDate value='${hjEquScrappingApplication.d_date}' pattern='yyyy-MM-dd'/></td>
                <td >报废原因:</td>
                <td>${hjEquScrappingApplication.discard_reason}</td>
                
            </tr>
            <tr>
                <td >备注:</td>
                <td  rowspan="1" colspan="3"> <span name="editable-input" isflag="tableflag"> ${hjEquScrappingApplication.note} </span> </td>
                
            </tr>
            <tr>
                <td >附件:</td>
                <td  rowspan="1" colspan="3">
                    <div name="div_attachment_container" right="r">
                        <div class="attachement"></div>
                        <textarea style="display:none" controltype="attachment" name="enclosure" lablename="附件" readonly="readonly">${hjEquScrappingApplication.enclosure}</textarea>
                    </div>
                </td>
            </tr>
                
            <tr style="display:none">
                <td  colspan="1" rowspan="1"><br /></td>
                <td  colspan="1" rowspan="1"> <span name="editable-input" isflag="tableflag"> ${hjEquScrappingApplication.projectTaskId} </span> </td>
            </tr>
        </table>
    </div>

</body>
</html>