<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
    <title>会议纪要明细</title>
    <%@include file="/commons/include/customForm.jsp"%>
    <script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/FlowDetailWindow.js"></script>
</head>
<body class="oa-mw-page">

    <div class="oa-mg20">
        <a class="oa-button oa-button--primary oa-button--blue" onclick="FlowDetailWindow({runId:${runId}})">流程明细</a>
        <a class="oa-button oa-button--primary oa-button--blue" href="list.ht">返回</a>
    </div>

    <div class="oa-mg20">

        <table class="oa-table--default" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
            <caption>会议纪要</caption>
            <tr>
                <td>会议名称</td>
                <td>${hdMeetingMinutes.conference_name}</td>
                <td>编号</td>
                <td> <span name="editable-input" isflag="tableflag"> ${hdMeetingMinutes.number} </span> </td>
            </tr>
            <tr>
                <td>会议时间</td>
                <td> <fmt:formatDate value='${hdMeetingMinutes.date}' pattern='yyyy-MM-dd'/> </td>
                <td>会议主持人</td>
                <td> ${hdMeetingMinutes.moderator} </td>
            </tr>
            <tr>
                <td>参会人员</td>
                <td> <span name="editable-input" isflag="tableflag"> ${hdMeetingMinutes.participants} </span> </td>
                <td>纪要提交人</td>
                <td> ${hdMeetingMinutes.name} </td>
            </tr>
            <tr>
                <td>备注</td>
                <td rowspan="1" colspan="3"> <span name="editable-input" isflag="tableflag"> ${hdMeetingMinutes.note} </span> </td>
            </tr>
            <tr>
                <td>附件</td>
                <td rowspan="1" colspan="3">
                    <div name="div_attachment_container" right="r">
                        <div class="attachement"></div>
                        <textarea style="display:none" controltype="attachment" name="attachment" lablename="附件" readonly="readonly">${hdMeetingMinutes.attachment}</textarea>
                    </div>
                </td>
            </tr>
        </table>

    </div>
    
</body>
</html>