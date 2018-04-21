
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<%@include file="/commons/include/getById.jsp"%>
<html>
<head>
<title>公告明细</title>
<%@include file="/commons/include/form.jsp"%>
<f:link href="article.css"></f:link>
<f:link href="form.css" ></f:link>
<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/AttachMent.js" ></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/HtmlUploadDialog.js" ></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/FlexUploadDialog.js" ></script>
<script type="text/javascript">
    $().ready(function (){
        AttachMent.init("r");
    });
    
    function FontZoom(size) {
        $("#read-con").css("font-size", size);
    }
    
</script>
<style>
    .content{
        margin-top: 0;
    }
</style>
</head>
<body>
    <div id="main" align="center">
        <div class="content">
            <div class="read">
                <h1 class="title">${sysbulletintemplate.name}</h1>
                <div id="read-con">
                    <div style="padding-bottom: 10px;" align="left">${sysbulletintemplate.template}</div>
                </div>
            </div>
        </div>
        <div class="footer">
            <p>
                Copyright ?2015 <a href="" title="@@@@@@@@@@">深圳海雅</a> 版权所有
                沪ICP证@@@@@@@号
            </p>
        </div>
    </div>
</body>
</html>

