<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
    <title>技术报告报审明细</title>
    <%@include file="/commons/include/customForm.jsp"%>
    <%@include file="/commons/include/ueditor.jsp"%>
    <script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
    <script type="text/javascript"
    src="${ctx}/js/hotent/platform/bpm/FlowDetailWindow.js"></script>
</head>
<body>

    <div class="oa-top-wrap">
        <a class="oa-button oa-button--primary oa-button--blue" onclick="FlowDetailWindow({runId:${runId}})">流程明细</a>
        <a class="oa-button oa-button--primary oa-button--blue" href="list.ht">返回</a>
    </div>
    
    <div class="oa-main-wrap">
        <table class="formTable" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
            <tbody>
                <tr class="firstRow">
                    <td class="formHead" colspan="4">技术报告</td>
                </tr>
                <tr>
                    <td align="right" class="formTitle">上传人:</td>
                    <td class="formInput"> ${hjTechnicalReportExamin.name} </td>
                    <td align="right" class="formTitle">上传日期:</td>
                    <td class="formInput"> <fmt:formatDate value='${hjTechnicalReportExamin.uploadTime}' pattern='yyyy-MM-dd'/> </td>
                </tr>
                <tr>
                    <td align="right" class="formTitle">所属部门:</td>
                    <td class="formInput"> ${hjTechnicalReportExamin.department} </td>
                    <td align="right" class="formTitle">项目名称:</td>
                    <td class="formInput"> <span name="editable-input" isflag="tableflag"> ${hjTechnicalReportExamin.itemName} </span> </td>
                </tr>
                <tr>
                    <td align="right" class="formTitle">报告名称:</td>
                    <td class="formInput"> <span name="editable-input" isflag="tableflag"> ${hjTechnicalReportExamin.reportName} </span> </td>
                    <td align="right" class="formTitle">报告内容:</td>
                    <td class="formInput"> <span name="editable-input" isflag="tableflag"> ${hjTechnicalReportExamin.reportContent} </span> </td>
                </tr>
                <tr>
                    <td align="right" class="formTitle">备注:</td>
                    <td class="formInput" rowspan="1" colspan="3"> <span name="editable-input" isflag="tableflag"> ${hjTechnicalReportExamin.tag} </span> </td>
                </tr>
                <tr>
                    <td align="right" class="formTitle">附件:</td>
                    <td class="formInput" rowspan="1" colspan="3">
                        <input  ctltype="attachment" name="m:hj_goods_purchaseds:enclosure" lablename="附件" isdirectupload="0" right="r"
                        value='${hjTechnicalReportExamin.enclosure}' validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}" />
                    </td>
                </tr>
                <tr style="display: none;">
                    <td class="formInput" rowspan="1" colspan="1"></td>
                    <td class="formInput" rowspan="1" colspan="1"> <span name="editable-input" isflag="tableflag"> ${hjTechnicalReportExamin.projectTaskId} </span> </td>
                </tr>
            </tbody>
        </table>      
    </div>

</body>
</html>