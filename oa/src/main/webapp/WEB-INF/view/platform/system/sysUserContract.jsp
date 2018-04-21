<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>经手合同</title>
<link href="${ctx}/styles/default/css/form.css" rel="stylesheet" />
<%@include file="/commons/include/form.jsp" %>
<%@include file="/codegen/include/customForm.jsp" %>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/AttachMent.js"></script>
<%@include file="/commons/include/ueditor.jsp" %>
<script type="text/javascript" src="${ctx}/servlet/ValidJs?form=sysUser"></script>
<f:link href="tree/zTreeStyle.css"></f:link>
<script type="text/javascript" src="${ctx}/js/tree/jquery.ztree.js"></script>
<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerTab.js" ></script>
<script type="text/javascript" src="${ctx}/js/hotent/displaytag.js" ></script>
<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerWindow.js" ></script>
<script type="text/javascript"  src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/FlexUploadDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/handlebars/handlebars.min.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/HtmlUploadDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/OfficePlugin.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/rule.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery/plugins/jquery.attach.js" ></script>
<script type="text/javascript" src="${ctx}/js/ntkoWebSign/WebSignPlugin.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/FormUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/ReadOnlyQuery.js"></script>
<script type="text/javascript" src="${ctx}/js/pictureShow/PictureShowPlugin.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/FormMath.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/Cascadequery.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/TablePermission.js"></script>
<script type="text/javascript">

    var dialog =null;
    try{
        dialog = frameElement.dialog;
    }catch(e){
        
    }
    $(function() {
        var h = $('body').height();
        $("#tabMyInfo").ligerTab({});
    });
</script>
<style>
    #tabMyInfo .l-tab-links {
            border: 1px solid #eceff8;
        background: #eceff8;
    }
    .l-tab-links li {
        cursor: pointer;
        float: left;
        font-size: 14px;
        height: 42px;
        line-height: 42px;
        margin: 0;
        overflow: hidden;
        position: relative;
    }
    #tabMyInfo .l-tab-links li.l-selected a {
        display: block;
        padding: 0 10px;
        border-radius: 0;
    }
    #tabMyInfo .l-tab-links li a {
        color: #657386;
        padding: 0 10px;
    }

    .oa-table--second td,
    .oa-table--second th{
        padding: 10px 20px;
        border: 1px solid #eceff8;
    }
    .oa-table--second th{
        font-weight: bold;
        width: 100px;
        background: #f6f7fb;
    }
    .profile{
        text-align: center;
        margin: 20px auto;
        width: 100px;
        height: 100px;
        line-height: 100px;
        border-radius: 50%;
        background: 50%/cover;
        background-color: #fff;
    }
    .profile img{
        max-width: 100%;
    }
    .my-data-info {
    	text-align: center;
    	font-size: 16px !important;
    }
</style>
</head>
<body>
    
    <div class="oa-pd20">
    </div>
    
    <div id="tabMyInfo">
        <div title="经手合同" tabid="seal" icon="${ctx}/styles/default/images/resicon/article.gif">
            <display:table  name="contractList" id="sealItem" requestURI="list.ht" sort="external" class="oa-table--default">
                <display:column title="合同编号">${sealItem.contract_num }</display:column>
                <display:column title="名称">${sealItem.contract_name }</display:column>
                <display:column title="经手人">${sealItem.contract_handler }</display:column>
                <display:column title="是否归档">
                    <c:choose>
                        <c:when test="${sealItem.isrecovery==0 || sealItem.isrecovery==null}">
                            <span class='green'>未归档</span>
                        </c:when>
                        <c:when test="${sealItem.isrecovery==1}">
                            <span class='red'>已归档</span>
                        </c:when>
                    </c:choose>
                </display:column>
            </display:table>
        </div>
        
    </div>
</body>
</html>
