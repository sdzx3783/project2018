<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>排水处月报管理明细</title>
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
        <table class="oa-table--default" parser="addpermission" data-sort="sortDisabled" cellspacing="0" cellpadding="2" border="1">
            <caption>月报管理</caption>
          <tr>
           <th>月报名称:</th>
           <td rowspan="1" colspan="3"> 
           <span name="editable-input" isflag="tableflag"> ${drainageMonthly.name} </span> </td>
          </tr>
          <tr>
           <th>项目名称:</th>
           <td rowspan="1" colspan="3"> 
           <span name="editable-input" isflag="tableflag"> ${drainageMonthly.item} </span> </td>
          </tr>
          <tr>
           <th>片区:</th>
           <td rowspan="1" colspan="3"> ${drainageMonthly.grop} </td>
          </tr>
          <tr>
           <th>月份:</th>
           <td> 
           <span name="editable-input" isflag="tableflag"> ${drainageMonthly.month} </span> </td>
           <td width="0"></td>
           <td width="529"></td>
          </tr>
          <tr>
           <th>提交人</th>
           <td> ${drainageMonthly.editer} </td>
           <th width="0">提交日期:</th>
           <td width="529"><fmt:formatDate value='${drainageMonthly.edit_date}' pattern='yyyy-MM-dd'/></td>
          </tr>
          <tr>
           <th>备注:</th>
           <td rowspan="1" colspan="3"> 
           <span name="editable-input" isflag="tableflag"> ${drainageMonthly.remarks} </span> </td>
          </tr>
          <tr>
           <th>附件:</th>
           <td rowspan="1" colspan="3"> 
            <div name="div_attachment_container" right="r"> 
             <div class="attachement"></div> 
             <textarea style="display:none" controltype="attachment" name="attachment" lablename="附件" readonly="readonly">${drainageMonthly.attachment}</textarea> 
            </div> </td>
          </tr>
        </table>
    </div>

</body>
</html>

