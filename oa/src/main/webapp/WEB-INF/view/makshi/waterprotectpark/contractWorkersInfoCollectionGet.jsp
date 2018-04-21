<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
    <title>劳务人员信息采集(水保示范园流程)明细</title>
    <%@include file="/commons/include/customForm.jsp"%>
    <script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
</head>
<body class="oa-mw-page">

    <div class="oa-pd20">
        <a class="oa-button oa-button--primary oa-button--blue" href="list.ht">返回</a>
    </div>

    <div class="oa-pd20">

        <table class="oa-table--default" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
            <caption>劳务人员信息采集</caption>
            <tr>
                <th>名称</th>
                <td> <span name="editable-input" isflag="tableflag"> ${contractWorkersInfoCollection.name} </span> </td>
                <th>入职时间</th>
                <td>
                    <span name="editable-input" isflag="tableflag"><fmt:formatDate   value="${contractWorkersInfoCollection.entry_date}" pattern="yyyy-MM-dd" type="date" dateStyle="long" /></span>
                </td>
            </tr>
            <tr>
                <th>身份证号</th>
                <td>
                    <span name="editable-input" isflag="tableflag"> ${contractWorkersInfoCollection.id_card} </span>
                </td>
                <th>登记人</th>
                <td> ${contractWorkersInfoCollection.registerer} </td>
            </tr>
            <tr>
                <th>户籍</th>
                <td> <span name="editable-input" isflag="tableflag"> ${contractWorkersInfoCollection.address} </span> </td>
                <th>紧急联系人</th>
                <td> <span name="editable-input" isflag="tableflag"> ${contractWorkersInfoCollection.emergencyer} </span> </td>
            </tr>
            <tr>
                <th>紧急联系人电话</th>
                <td>
                    <span name="editable-input" isflag="tableflag"> ${contractWorkersInfoCollection.emergency_phone} </span>
                </td>
                <th>劳务工种</th>
                <td>
                    <span name="editable-input" isflag="tableflag"><c:if test="${contractWorkersInfoCollection.work_type==2 }">其他</c:if><c:if test="${contractWorkersInfoCollection.work_type==1 }">保安</c:if><c:if test="${contractWorkersInfoCollection.work_type==0 }">保结</c:if></span>
                </td>
            </tr>
            <tr>
                <th>是否住宿</th>
                <td>
                    <span name="editable-input" isflag="tableflag"><c:if test="${contractWorkersInfoCollection.is_stay==1 }">是</c:if><c:if test="${contractWorkersInfoCollection.is_stay==0 }">否</c:if></span>
                </td>
                <th>性别</th>
                <td>
                    <span name="editable-input" isflag="tableflag"><c:if test="${contractWorkersInfoCollection.gender==1 }">男</c:if><c:if test="${contractWorkersInfoCollection.gender==0 }">女</c:if></span>
                </td>
            </tr>
            <tr>
                <th>年龄</th>
                <td> ${contractWorkersInfoCollection.age} </td>
                <th>在职状态</th>
                <td>
                    <span name="editable-input" isflag="tableflag"><c:if test="${contractWorkersInfoCollection.status==1 }">在职</c:if><c:if test="${contractWorkersInfoCollection.status==0 }">离职</c:if></span>
                </td>
            </tr>
            <tr>
                <th>备注</th>
                <td colspan="3"> <span name="editable-input" isflag="tableflag"> ${contractWorkersInfoCollection.remark} </span> </td>
            </tr>
            <tr>
                <th>相片</th>
                <td colspan="3">
                    <div name="div_attachment_container" right="r">
                        <div class="attachement"></div>
                        <textarea style="display:none" controltype="attachment" name="attachment" lablename="相片" readonly="readonly">${contractWorkersInfoCollection.attachment}</textarea>
                    </div> 
                </td>
            </tr>
        </table>

    </div>

</body>
</html>