<%@page import="com.hotent.core.web.util.RequestUtil" isErrorPage="true" pageEncoding="UTF-8"%>
<%
	String basePath=request.getContextPath();
%>
<%@include file="/commons/include/get.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<title>页面出错</title>
	<style>
		body{
			margin: 0;
			padding: 0;
			background: #e1e5f0;
		}
		.message-wrap{
			padding-top: 100px;
			text-align: center;
		}
	</style>
</head>
<body>
	<div class="message-wrap">
		<img src="/images/error-message/404img-3.png" alt="">
	</div>
</body>
</html>

