<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
    <title>投诉处理审批明细</title>
    <%@include file="/commons/include/customForm.jsp"%>
    <script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/FlowDetailWindow.js"></script>
</head>
<body class="oa-mw-page">

    <div class="oa-mg20">
        <a class="oa-button oa-button--primary oa-button--blue" href="list.ht">返回</a>
    </div>

    <div class="oa-mg20">
        <table class="oa-table--default" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
            <caption>投诉处理审批申请</caption>
            <tr>
                <th>事项名称:</th>
                <td rowspan="1" colspan="3">${biddingComplain.complain_name}</td>
            </tr>
            <tr>
                <th>事项内容:</th>
                <td rowspan="1" colspan="3">${biddingComplain.complain_content}</td>
            </tr>
            <tr>
                <th>投诉单位:</th>
                <td rowspan="1" colspan="3">${biddingComplain.complain_unit}</td>
            </tr>
            <tr>
                <th>提交人:</th>
                <td>${biddingComplain.complain_applicant}</td>
                <th>提交时间:</th>
                <td><fmt:formatDate value="${biddingComplain.complain_appli_date}" pattern='yyyy-MM-dd' /></td>
            </tr>
            <tr>
                <th>备注:</th>
                <td rowspan="1" colspan="3">${biddingComplain.complain_remarks}</td>
            </tr>
            <tr>
                <th>项目初审人员:</th>
                <td rowspan="1" colspan="3">${biddingComplain.complain_first_check_person}</td>
            </tr>
            <tr>
                <th>附件:</th>
                <td rowspan="1" colspan="3"><input  ctltype="attachment" right="r"  name="m:bidding_complainItem:complain_attachment" isdirectupload="0"  value='${biddingComplain.complain_attachment}' validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}"/></td>
            </tr>
        </table>
    </div>

</body>
</html>