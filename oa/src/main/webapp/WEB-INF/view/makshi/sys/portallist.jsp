<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>门户管理</title>
<%@include file="/commons/include/get.jsp" %>
<style>

    .list li{
        float: left;
        margin-right: 10px;
    }
</style>
</head>
<body>
    <div class="oa-project-title">
        <h2>门户管理</h2>
    </div>

    <div class="oa-pd20">
        <ul class="list oa-clear">
            <li><a class="oa-button-label" href="/makshi/sys/orgres/download.ht?fileName=orgportalIndex_10000011000055.jsp">咨询部</a></li>
            <li><a class="oa-button-label" href="/makshi/sys/orgres/download.ht?fileName=orgportalIndex_10000011000073.jsp">运维部</a></li>
            <li><a class="oa-button-label" href="/makshi/sys/orgres/download.ht?fileName=orgportalIndex_10000007780656.jsp">水保部</a></li>
            <li><a class="oa-button-label" href="/makshi/sys/orgres/download.ht?fileName=orgportalIndex_10000011000078.jsp">环境事业部</a></li>
            <li><a class="oa-button-label" href="/makshi/sys/orgres/download.ht?fileName=orgportalIndex_10000011000053.jsp">招标代理部</a></li>
            <li><a class="oa-button-label" href="/makshi/sys/orgres/download.ht?fileName=orgportalIndex_10000011000075.jsp">河道管养部</a></li>
            <li><a class="oa-button-label" href="/makshi/sys/orgres/download.ht?fileName=orgportalIndex_10000011000072.jsp">综合部</a></li>
            <li><a class="oa-button-label" href="/makshi/sys/orgres/download.ht?fileName=orgportalIndex_10000007780857.jsp">监理部</a></li>
        </ul>
        
        <div class="oa-mgv10">
            上传文件
        </div>
        <div class="oa-mgv10">
            <form id="uploadForm">
                <label class="oa-button-label">
                    选择文件
                    <input id="upload_input" type="file" name="file" style="display: none;">
                </label>

                <button class="oa-button-label" id="upload_button" type="button" onclick="doUpload()">上传</button>
            </form>
        </div>
    </div>

<script type="text/javascript">
function doUpload() {  
    var formData = new FormData($( "#uploadForm" )[0]);  

    $("#upload_input").trigger("clearFileInput");

    $.ajax({  
         url: '/makshi/sys/orgres/upload.ht' ,  
         type: 'POST',  
         data: formData,  
         async: false,  
         cache: false,  
         contentType: false,  
         processData: false,  
         success: function (returndata) {  
             alert("上传成功");  
             $("#upload_input").trigger("file.state");
         },  
         error: function (returndata) {  
             alert("上传失败");  
         }  
    });  
}

$(function(){
    var $uploadBtn = $("#upload_button").hide();
    var $uploadInput = $("#upload_input");
    //  检测当前选择文件的数量，如果大于0，则显示是上传按钮，否则隐藏
    $("#upload_input").on("change", function(e){
        $uploadInput.trigger("file.state");
    });

    //  每次点击时，重置input的值， 避免前后选择同一个文件时不触发change事件
    $uploadInput.on("click", function(e){
        $uploadInput.trigger("clearFileInput");
    });

    //  监听clearFileInput事件，清除input上挂载的文件
    $uploadInput.on("clearFileInput", function(e){
        $(this).val("");
    })

    //  监听input file状态
    $uploadInput.on("file.state", function(e){
        var len = e.target.files.length;

        if(len > 0){
            $uploadBtn.fadeIn();
        }else{
            $uploadBtn.fadeOut();
        }
    })

});
</script>
</body>
</html>


