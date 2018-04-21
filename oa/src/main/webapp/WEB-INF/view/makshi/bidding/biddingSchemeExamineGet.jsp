<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
    <title>招标方案审批明细</title>
    <%@include file="/commons/include/customForm.jsp"%>
    <script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/FlowDetailWindow.js"></script>
</head>
<body class="oa-mw-page">


    <div class="oa-mg20">
        <a href="detail.ht?id=${biddingSchemeExamine.id}"  target="_bank" class="oa-button oa-button--primary oa-button--blue">打印表单</a>
        <a class="oa-button oa-button--primary oa-button--blue" href="list.ht">返回</a>
    </div>

    <div class="oa-mg20">
        <table class="oa-table--default" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
            <caption>招标方案审批表</caption>
            <tr>
                <td>方案名称:</td>
                <td rowspan="1" colspan="3">${biddingSchemeExamine.scheme_name}</td>
            </tr>
            <tr>
                <td>编制依据:</td>
                <td rowspan="1" colspan="3">${biddingSchemeExamine.scheme_evidence}</td>
            </tr>
            <tr>
                <td>方案内容:</td>
                <td rowspan="1" colspan="3">${biddingSchemeExamine.scheme_content}</td>
            </tr>
            <tr>
                <td>合同编号:</td>
                <td>${biddingSchemeExamine.scheme_contract_num}</td>
                <td>合同名称:</td>
                <td>${biddingSchemeExamine.scheme_contract_name}</td>
            </tr>
            <tr>
                <td>申请人:</td>
                <td>${biddingSchemeExamine.scheme_applicant}</td>
                <td>申请日期:</td>
                <td><fmt:formatDate value="${biddingSchemeExamine.scheme_appli_date}" pattern='yyyy-MM-dd'/></td>
            </tr>
            <tr>
                <td>项目初审人员:</td>
                <td rowspan="1" colspan="3">${biddingSchemeExamine.scheme_first_check_person}</td>
            </tr>
            <tr>
                <td>附件:</td>
                <td rowspan="1" colspan="3">
                    <input  ctltype="attachment" right="r"  name="m:bidding_scheme_examineItem:scheme_attachment" isdirectupload="0"  value='${biddingSchemeExamine.scheme_attachment}' validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}"/>
                </td>
            </tr>
        </table>
    </div>

</body>
</html>