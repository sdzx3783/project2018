<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>径流实验申请(水保示范园)明细</title>
<%@include file="/commons/include/customForm.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
</head>
<body class="oa-mw-page">

    <div class="oa-pd20">
        <a class="oa-button oa-button--primary oa-button--blue" href="list.ht">返回</a>
    </div>

    <div class="oa-pd20">

        <table class="oa-table--default" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
            <caption>径流实验信息登记</caption>
            <tr>
                <th>实验名称</th>
                <td><span name="editable-input" isflag="tableflag"> ${riverExperiment.name}</span></td>
                <th>实验人员</th>
                <td>${riverExperiment.experimenter}</td>
            </tr>
            <tr>
                <th>取样时间</th>
                <td><fmt:formatDate value='${riverExperiment.samlpeTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
                <th>取样人员</th>
                <td>${riverExperiment.sampleMan}</td>
            </tr>
            <tr>
                <th>实验时间</th>
                <td colspan="3" rowspan="1"><fmt:formatDate value='${riverExperiment.experiTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
            </tr>
            <tr>
                <th>备注</th>
                <td colspan="3" rowspan="1"><span name="editable-input" isflag="tableflag">${riverExperiment.remark}</span></td>
            </tr>
            <tr>
                <th>附件</th>
                <td colspan="3" rowspan="1">
                    <div name="div_attachment_container" right="r">
                        <div class="attachement"></div>
                        <textarea style="display:none" controltype="attachment" name="attachment" lablename="附件" readonly="readonly">${riverExperiment.attachment}</textarea>
                    </div>
                </td>
            </tr>
        </table>

    </div>

</body>
</html>

