<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
    <title>文件明细</title>
    <%@include file="/commons/include/customForm.jsp"%>
    <script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/FlowDetailWindow.js"></script>
</head>
<body class="oa-mw-page">

    <div class="oa-pd20">
        <a class="oa-button oa-button--primary oa-button--blue" onclick="FlowDetailWindow({runId:${runId}})">流程明细</a>
        <a class="oa-button oa-button--primary oa-button--blue" href="detail.ht?id=${hdDocumentReview.id}">打印表单</a>
        <a class="oa-button oa-button--primary oa-button--blue" href="list.ht">返回</a>
    </div>

    <div class="oa-pd20">

        <table class="oa-table--default" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
            <caption>文件审查</caption>
            <tr>
                <th>文件名称</th>
                <td>${hdDocumentReview.file_name}</td>
                <th>编号</th>
                <td> <span name="editable-input" isflag="tableflag"> ${hdDocumentReview.number} </span> </td>
            </tr>
            <tr>
                <th>项目部</th>
                <td> ${hdDocumentReview.department} </td>
                <th>申请人</th>
                <td> ${hdDocumentReview.applicant} </td>
            </tr>
            <tr>
                <th>申请时间</th>
                <td> <fmt:formatDate value='${hdDocumentReview.date}' pattern='yyyy-MM-dd'/> </td>
                <th>审批类型</th>
                <td>
                    <c:if test="${hdDocumentReview.type==0}">技术文件</c:if>
                    <c:if test="${hdDocumentReview.type==1}">通知</c:if>
                    <c:if test="${hdDocumentReview.type==2}">请示</c:if>
                </td>
            </tr>
            <tr>
                <th>紧急程度</th>
                <td>
                    <c:if test="${hdDocumentReview.level==0}">普通</c:if>
                    <c:if test="${hdDocumentReview.level==1}">紧急</c:if>
                    <c:if test="${hdDocumentReview.level==2}">加急</c:if>
                </td>
                <th>内容摘要</th>
                <td> <span name="editable-input" isflag="tableflag"> ${hdDocumentReview.content} </span> </td>
            </tr>
            <tr>
                <th>申请意见</th>
                <td rowspan="1" colspan="3"> <span name="editable-input" isflag="tableflag"> ${hdDocumentReview.opinion} </span> </td>
            </tr>
            <tr>
                <th>附件</th>
                <td rowspan="1" colspan="3">
                    <div name="div_attachment_container" right="r">
                        <div class="attachement"></div>
                        <textarea style="display:none" controltype="attachment" name="attachment" lablename="附件" readonly="readonly">${hdDocumentReview.attachment}</textarea>
                    </div>
                </td>
            </tr>
        </table>

    </div>

</body>
</html>