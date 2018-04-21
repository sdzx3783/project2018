<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>参观登记信息-水保园明细</title>
<%@include file="/commons/include/customForm.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
</head>
<body class="oa-mw-page">

    <div class="oa-pd20">
        <a class="oa-button oa-button--primary oa-button--blue" href="list.ht">返回</a>
    </div>
    
    <div class="oa-pd20">
        <table class="oa-table--default" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
            <tbody>
                <caption>参观登记信息</caption>
                <tr>
                    <th>批次</th>
                    <td> <span name="editable-input" isflag="tableflag"> ${visitorRegisterInfo.batch} </span> </td>
                    <th>参观日期</th>
                    <td> <fmt:formatDate value='${visitorRegisterInfo.visitTime}' pattern='yyyy-MM-dd'/> </td>
                </tr>
                <tr>
                    <th>团体名称</th>
                    <td> <span name="editable-input" isflag="tableflag"> ${visitorRegisterInfo.teamName} </span> </td>
                    <th>所属类型</th>
                    <td> ${visitorRegisterInfo.type} </td>
                </tr>
                <tr>
                    <th>登记时间</th>
                    <td> <fmt:formatDate value='${visitorRegisterInfo.registerTime}' pattern='yyyy-MM-dd'/> </td>
                    <th>星期</th>
                    <td> <span name="editable-input" isflag="tableflag"> ${visitorRegisterInfo.xq} </span> </td>
                </tr>
                <tr>
                    <th>负责人</th>
                    <td> ${visitorRegisterInfo.charger} </td>
                    <th>联系电话</th>
                    <td> <span name="editable-input" isflag="tableflag"> ${visitorRegisterInfo.tel} </span> </td>
                </tr>
                <tr>
                    <th>参观人数</th>
                    <td> <span name="editable-input" isflag="tableflag"> ${visitorRegisterInfo.visitorNum} </span> </td>
                    <th>是否来函</th>
                    <td> <span> <label><input  checked="${visitorRegisterInfo.isLetter==1 }" type="radio" name="isLetter" value="1" lablename="是否来函" validate="{}" disabled="disabled" />是</label> <label><input checked="${visitorRegisterInfo.isLetter==0 }" type="radio" name="isLetter" value="0" lablename="是否来函" validate="{}" disabled="disabled" />否</label> </span> </td>
                </tr>
                <tr>
                    <th>接待员</th>
                    <td rowspan="1" colspan="3"> ${visitorRegisterInfo.greeter} </td>
                </tr>
                <tr>
                    <th>备注</th>
                    <td rowspan="1" colspan="3"> <span name="editable-input" isflag="tableflag"> ${visitorRegisterInfo.remark} </span> </td>
                </tr>
            </tbody>
        </table>
    </div>

</body>
</html>

