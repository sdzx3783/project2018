<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>其他事项审批明细</title>
<%@include file="/commons/include/customForm.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/FlowDetailWindow.js"></script>
</head>
<body class="oa-mw-page">

    <div class="oa-mg20">
        <a href="detail.ht?id=${biddingOthers.id}"  target="_bank"  class="oa-button oa-button--primary oa-button--blue">打印表单</a>
        <a class="oa-button oa-button--primary oa-button--blue" href="list.ht">返回</a>
    </div>

    <div class="oa-mg20">
        <table class="oa-table--default" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
              <caption>其他事项审批申请</caption>
              <tr>
               <th>事项名称:</th>
               <td rowspan="1" colspan="3">${biddingOthers.others_name}</td>
              </tr>
              <tr>
               <th>编制依据:</th>
               <td rowspan="1" colspan="3">${biddingOthers.others_evidence}</td>
              </tr>
              <tr>
               <th>申请内容:</th>
               <td rowspan="1" colspan="3">${biddingOthers.others_content}</td>
              </tr>
              <tr>
               <th>申请人:</th>
               <td>${biddingOthers.others_applicant}</td>
               <th>申请日期:</th>
               <td><fmt:formatDate value="${biddingOthers.others_appli_date}" pattern='yyyy-MM-dd'/></td>
              </tr>
               <th>附件:</th>
               <td rowspan="1" colspan="3"><input  ctltype="attachment" right="r"  name="m:bidding_othersItem:others_attachment" isdirectupload="0"  value='${biddingOthers.others_attachment}' validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}"/></td>
              </tr>
        </table>
    </div>

</body>
</html>

