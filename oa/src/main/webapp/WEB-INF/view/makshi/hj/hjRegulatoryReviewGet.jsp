<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
    <title>明细</title>
    <%@include file="/commons/include/customForm.jsp"%>
    <script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/FlowDetailWindow.js"></script>
</head>
<body>

    <div class="oa-top-wrap">
        <a class="oa-button oa-button--primary oa-button--blue" onclick="FlowDetailWindow({runId:${runId}})">流程明细</a>
        <a class="oa-button oa-button--primary oa-button--blue" href="list.ht">返回</a>
    </div>
    
    <div class="oa-main-wrap">
        <table class="formTable" border="1" cellspacing="0" cellpadding="2" data-sort="sortDisabled" parser="addpermission">
            <tbody>
                <tr class="firstRow">
                    <td class="formHead" colspan="4">监管周报</td>
                </tr>
                <tr>
                    <td align="right" class="formTitle">提交人:</td>
                    <td class="formInput"> ${hjRegulatoryReview.name} </td>
                    <td align="right" class="formTitle">提交日期:</td>
                    <td class="formInput"> <fmt:formatDate value='${hjRegulatoryReview.date}' pattern='yyyy-MM-dd'/> </td>
                </tr>
                <tr>
                    <td align="right" class="formTitle">所属部门:</td>
                    <td class="formInput"> ${hjRegulatoryReview.department} </td>
                    <td align="right" class="formTitle">项目名称:</td>
                    <td class="formInput">${hjRegulatoryReview.project_name}</td>
                </tr>
                <tr>
                    <td align="right" class="formTitle">周报名称:</td>
                    <td class="formInput">${hjRegulatoryReview.n_name}</td>
                    <td align="right" class="formTitle">周报内容:</td>
                    <td class="formInput"> <span name="editable-input" isflag="tableflag"> ${hjRegulatoryReview.content} </span> </td>
                </tr>
                <tr>
                    <td align="right" class="formTitle">备注:</td>
                    <td class="formInput" rowspan="1" colspan="3"> <span name="editable-input" isflag="tableflag"> ${hjRegulatoryReview.note} </span> </td>
                </tr>
                <tr>
                    <td align="right" class="formTitle">附件:</td>
                    <td class="formInput" rowspan="1" colspan="3">
                        <div name="div_attachment_container" right="r">
                            <div class="attachement"></div>
                            <textarea style="display:none" controltype="attachment" name="enclosure" lablename="附件" readonly="readonly">${hjRegulatoryReview.enclosure}</textarea>
                        </div>
                    </td>
                </tr>
                <tr style="display:none">
                    <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all;"> <span name="editable-input" isflag="tableflag"> ${hjRegulatoryReview.projectTaskId} </span> </td>
                </tr>
            </tbody>
        </table>
    </div>

</body>
</html>