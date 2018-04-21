<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>工资核定表单</title>
	<%@include file="/codegen/include/customForm.jsp" %>
    <style>
        iframe{
            width: 100%;
        }
    </style>
</head>
<body class="oa-mw-page">

    <div class="oa-project-title">
        <!-- <h2>工资核定表单</h2> -->
    </div>
	
    <div class="oa-pd20">
        <form id="searchForm" method="post" action="salaryVerifyForm.ht" class="oa-clear">
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">月份</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input id="queryTime" name="queryTime" type="text" datefmt="yyyy-MM" value="${queryStr }" class="oa-input Wdate" />
                </div>
            </div>

            <div class="oa-fl oa-mgb10 oa-mgh20">
                <button class="oa-button oa-button--primary oa-button--blue">查询</button>
                <a class="oa-button oa-button--primary oa-button--blue" onclick="exportExcel()" href="#">导出</a>
            </div>
        </form>
    </div>


	<iframe id="viewFrame" frameborder="0" src="selectByEntryDate.ht?queryTime=${queryTime}"></iframe>
	<iframe id="viewFrame" frameborder="0" src="selectByFormatDate.ht?formalDate=${queryTime}"></iframe>
	<iframe id="viewFrame" frameborder="0" src="selectBySalaryAdjustDate.ht?queryTime=${queryTime}"></iframe>

<script type="text/javascript">
    function exportExcel(){
        if(confirm("确定导出")){
            window.location.href="exportExcel.ht?queryTime="+document.getElementById('queryTime').value;
        }
    }

    $(function(){

        var $iframes = $('iframe');

        $.each($iframes, function(index, iframe) {

            (function(_win, _iframe){

                $(_win).on('collapseClose', function(e, data){
                    var _body = $(_iframe.contentDocument).find("body");
                    var height = $(_body).height();

                    $(_iframe).height(height);
                });
                
            })(iframe.contentWindow, iframe);

        });

        $(window).on('resize', function(){

            var $iframes = $('iframe');

            $.each($iframes, function(index, iframe) {

                (function(_win, _iframe){
                    $(_win).on('collapseClose', function(e, data){
                        var _body = $(_iframe.contentDocument).find("body");
                        var height = $(_body).height();

                        $(_iframe).height(height);
                    });
                })(iframe.contentWindow, iframe);

            });
        });

    });
</script>
</body>
</html>
