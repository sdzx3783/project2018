<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
    <title>印章表明细</title>
    <%@include file="/commons/include/customForm.jsp" %>
    <script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/FlowDetailWindow.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/system/HtmlUploadDialog.js"></script>
    <script type="text/javascript">
        //放置脚本
    </script>
</head>
<body class="oa-mw-page">
<div class="oa-mg20">
    <a class="oa-button oa-button--primary oa-button--blue" href="list.ht">返回</a>
    <a class="oa-button oa-button--primary oa-button--blue" id="btnFormSave">保存</a>
    <a class="oa-button oa-button--primary oa-button--blue" onclick="FlowDetailWindow({runId:${runId}})"
       href="javascript:;">流程明细</a>
</div>
<table class="oa-table--default" border="1" cellspacing="0" cellpadding="2" parser="addpermission"
       data-sort="sortDisabled">
    <caption>印章申请详细信息编辑</caption>
    <tbody>

    <tr class="firstRow">
        <th>合同编号:</th>
        <td>${makeChapter.contract_id}</td>
        <th>总投资额(万):</th>
        <td>${makeChapter.total_investment}</td>
    </tr>
    <tr>
        <th>申请人:</th>
        <td>${makeChapter.application_person}</td>
        <th>申请印章名称:</th>
        <td>${makeChapter.chapter_name}</td>
    </tr>
    <tr>
        <th>申请刻章事由:</th>
        <td>${makeChapter.reason}</td>
        <th>要求到位时间:</th>
        <td>
            <fmt:formatDate value="${makeChapter.limit_date}" type="both" pattern="yyyy-MM-dd"/></td>
    </tr>
    <tr>
        <th>项目名称:</th>
        <td>${makeChapter.project_name}</td>
        <th>刻章类型:</th>
        <td>
            <c:if test="${makeChapter.chapter_type==1}">合同类</c:if>
            <c:if test="${makeChapter.chapter_type==0}">其他类</c:if>
        </td>
    </tr>
    <tr>
        <th>保管人:</th>
        <td colspan="3">
            <input type="text" maxlength="15" name="keeper"
                   value="${empty makeChapter.take_person ? makeChapter.application_person : makeChapter.take_person}"
                   ctltype="selector" class="users input-text" readonly="readonly"/>
        </td>
    </tr>
    <tr>
        <th>备注:</th>
        <td colspan="3" rowspan="1">${makeChapter.remarks}</td>
    </tr>
    <tr>
        <th>附件:</th>
        <td rowspan="1" colspan="3">
            <div name="div_attachment_container" right="r">
                <div class="attachement"></div>
                <textarea style="display:none" controltype="attachment" name="attachment" lablename="附件"
                          readonly="readonly">${makeChapter.attachment}</textarea>
            </div>
        </td>
    </tr>
    <tr>
        <th>印章图片:</th>
        <td colspan="3" rowspan="1" align="center">
            <img onclick="outImg(this);" id="showSealImg" style="height: 300px;width: auto;" alt="印章图片"
                 src="${ctx}${empty makeChapter.sealImg ? "/js/pictureShow/images/image1.jpg?1=1&type=small" : makeChapter.sealImg}"/>
            <div class="oa-text-center">
                <button class="oa-button-label" onclick="addPic();">上传印章图片</button>
                <button class="oa-button-label" onclick="delPic();">删除印章图片</button>
            </div>
        </td>
    </tr>
    <form id="mcForm" method="post" action="newupdate.ht">
        <%--设置隐藏域--%>
        <input type="hidden" name="id" value="${makeChapter.id}"/>
        <input type="hidden" id="take_personID" name="take_personID" value=""/>
        <input type="hidden" id="sealImg" name="sealImg" value=""/>
    </form>
    </tbody>
</table>
<script type="text/javascript">

    var dialog = null;
    try {
        dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)
    } catch (e) {
    }

    //上传照片
    function addPic() {
        HtmlUploadDialog({max: 1, callback: picCallBack});
    };

    //删除照片
    function delPic() {
        $("#sealImg").val("");
        $("#showSealImg").attr("src", "${ctx}/js/pictureShow/images/image1.jpg?1=1&type=small");
    };

    //添加图片
    function picCallBack(array) {
        if (!array && array != "") return;
        var fileId = array[0].fileId,
            fileName = array[0].fileName,
            extName = array[0].extName;
        var path = __ctx + "/platform/system/sysFile/getFileById.ht?fileId=" + fileId;
        if (/(png|gif|jpg)$/gi.test(extName)) {
            $("#sealImg").val("/platform/system/sysFile/getFileById.ht?fileId=" + fileId);
            $("#showSealImg").attr("src", path);
        } else {
            $.ligerDialog.warn("请选择*png,*gif,*jpg类型图片!");
        }
    };


    //提交表单
    function submitForm() {

        if(!validInput()) {
            return;
        }

        var options = {};
        if (showResponse) {
            options.success = showResponse;
        }
        var frm = $('#mcForm').form();
        frm.ajaxForm(options);
        if (frm.valid()) {
            frm.submit();
        }
    }

    function showResponse(responseText) {
        console.log("this is response:" + responseText)
        if (typeof(responseText) != 'undefined' && responseText.code == '1') {
            $.ligerDialog.confirm("保存成功,是否继续操作", "提示信息", function (rtn) {
                if (rtn) {
                    window.location.reload();
                } else {
                    window.location.href = "list.ht";
                }
            });
        } else if(responseText.code == '0') {
            $.ligerDialog.err("提示信息", "信息保存失败!", "印章已注销。");
        } else {
            $.ligerDialog.err("提示信息", "信息保存失败!", "请校验参数是否正确");
        }
    }

    $(function () {
        $("#btnFormSave").click(function () {
            submitForm();
        });
    });

    $(function() {
        var tkPerson='${makeChapter.take_personID}';
        if(tkPerson != ''){
            $("input[name='keeperID']").val(tkPerson);
        }
    });

    var validInput = function () {

        if($("input[name='keeperID']").val() != '' && $("input[name='keeperID']").val().indexOf(',') != -1) {
            $.ligerDialog.err("提示信息", "保存失败!", "只能设置一个保管人");
            return false;
        }

        $("#take_personID").val($("input[name='keeperID']").val());
        return true;
    }

    var outImg = function (e) {
        if (typeof (e.src) == 'undefined' || e.src == '') {
            return;
        }
        $.fancybox({
            'type': 'image',
            'padding': 0,
            'href': e.src,
            'transitionIn': 'elastic',
            'transitionOut': 'elastic'
        })
    };
</script>
</body>
</html>

