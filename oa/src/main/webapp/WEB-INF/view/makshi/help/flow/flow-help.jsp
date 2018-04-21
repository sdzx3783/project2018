<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
	<title>流程帮助文档信息</title>
	<%@include file="/commons/include/list_get.jsp"%>
	<style type="text/css">
		.executivetitle{
		   	font-size: 20px !important;
		    color: #000;
		    font-weight: 600 !important;
	    }
		.filedoc{max-width: 850px;margin: auto;font-size: 15px;line-height: 25px;}
		.filedoc img{max-height: 300px;max-width: 300px;}
	</style>
</head>
<body>
	<div id="oa_list_title" class="oa-mgb20 oa-project-title">
	    <h2 class='executivetitle'>${docfile.title}</h2>
	</div>
	<div>
        <div class="col-md-12">
			 <div class="filedoc">
			 	<div>${docfile.content }</div>
			 </div>
		</div>
    </div>
    <script type="text/javascript">
    	$("img").before("<p>").after("<p>");
    </script>
</body>
</html>