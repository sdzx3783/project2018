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
        <a class="oa-button oa-button--primary oa-button--blue" href="list.ht"><span></span>返回</a>
    </div>
    
    <div class="oa-main-wrap">
        
        <table class="formTable" border="1" cellspacing="0" cellpadding="2" data-sort="sortDisabled" parser="addpermission">
            <tbody>
                <tr class="firstRow">
                    <td class="formHead" style="-ms-word-break: break-all;" colspan="4">运营周报</td>
                </tr>
                <tr>
                    <td align="right" class="formTitle" style="width: 15%; -ms-word-break: break-all;">提交人:</td>
                    <td class="formInput" style="width: 35%;"> ${hjRunWeekly.name} </td>
                    <td align="right" class="formTitle" style="width: 15%; -ms-word-break: break-all;">提交日期:</td>
                    <td class="formInput" style="width: 35%;"> <fmt:formatDate value='${hjRunWeekly.date}' pattern='yyyy-MM-dd'/> </td>
                </tr>
                <tr>
                    <td align="right" class="formTitle" style="width: 15%; -ms-word-break: break-all;">所属部门:</td>
                    <td class="formInput" style="width: 35%;"> ${hjRunWeekly.department} </td>
                    <td align="right" class="formTitle" style="width: 15%; -ms-word-break: break-all;">项目名称:</td>
                    <td class="formInput" style="width: 35%;">${hjRunWeekly.project_name}</td>
                </tr>
                <tr>
                    <td align="right" class="formTitle" style="width: 15%; word-break: break-all;">周报名称:</td>
                    <td class="formInput" style="width: 35%;">${hjRunWeekly.n_name}</td>
                    <td align="right" class="formTitle" style="width: 15%; word-break: break-all;">周报内容:</td>
                    <td class="formInput" style="width: 35%;"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${hjRunWeekly.content} </span> </td>
                </tr>
                <tr>
                    <td align="right" class="formTitle" style="width: 15%; -ms-word-break: break-all;">备注:</td>
                    <td class="formInput" style="width: 35%; -ms-word-break: break-all;" rowspan="1" colspan="3"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${hjRunWeekly.note} </span> </td>
                </tr>
                <tr>
                    <td align="right" class="formTitle" style="width: 15%; -ms-word-break: break-all;">附件:</td>
                    <td class="formInput" style="width: 35%; -ms-word-break: break-all;" rowspan="1" colspan="3">
                        <div name="div_attachment_container" right="r">
                            <div class="attachement"></div>
                            <textarea style="display:none" controltype="attachment" name="enclosure" lablename="附件" readonly="readonly">${hjRunWeekly.enclosure}</textarea>
                        </div>
                    </td>
                </tr>
                <tr style="display:none">
                    <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all;"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${hjRunWeekly.projectTaskId} </span> </td>
                </tr>
            </tbody>
        </table>
    </div>
</body>
</html>