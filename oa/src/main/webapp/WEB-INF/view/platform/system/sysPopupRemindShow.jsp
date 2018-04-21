<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<%@ taglib prefix="ht" tagdir="/WEB-INF/tags/wf"%>
<html>
<head>
<title>弹框演示</title>
<%@include file="/commons/include/get.jsp"%>
<script type="text/javascript">
	$(function() {
		//处理定时刷新页面
		var refreshTime = "${param.refreshTime}";
		if (refreshTime != "") {
			window.setInterval(function() {
				window.location.reload();
			}, Number(refreshTime) * 1000);
		}
		var jsonList='${jsonList}';


		jsonList=eval('('+jsonList+')');
		var $btn = $(window.parent.document).find(".l-dialog-winbtn.l-dialog-collapse").eq(0);
		
		if(jsonList!=null && jsonList.length < 1){
			//	如果为展开状态
			if(!$btn.hasClass('l-dialog-extend')){
				$btn.click();
			}
		}else{
			// if(!$btn.hasClass('l-dialog-extend')){
			// 	$btn.click();
			// }
		}
	});

	function openerLinkTo(url, text,popupType,height,width) {
		if (!startWith(url, "http://") && !startWith(url, "www.")) {
			url = __ctx + url;
		}
		if(popupType=="tab"){
			window.top.tab.addTabItem({
				url : url,
				text : text,
				tabid : text,
				icon : ""
			});
		}else if(popupType=="newWin"){
			window.open(url);
		}else if(popupType=="dialog"){
			DialogUtil.open({
		        height:height,
		        width: width,
		        title : text,
		        url: url, 
		        isResize: true
		    });
		}
	}

	function startWith(source, str) {
		if (str == null || str == "" || source.length == 0
				|| str.length > source.length)
			return false;
		return source.substr(0, str.length) == str;
	}
</script>
<style>
	body{
		margin: 0;
	}
	html, body{
		height: auto;
	}
	.message{
		padding: 10px 0 0 20px;
	}
	.message__item{
		position: relative;
		color: #333;
		font-size: 14px;
		text-decoration: none;
		display: inline-block;
		padding-left: 55px;
		line-height: 40px;
		background: url(/images/message2_icon.png) no-repeat;
		background-size: 42px 37px;
	}
	.message__num{
		font-size: 12px;
		color: #fff;
		position: absolute;
		top: 0;
		right: -32px;
		background: #fa2713;
		line-height: 16px;
		padding: 0 8px;
		border-radius: 8px;
	}
</style>
</head>
<body>
	<div class="message">
		<ul>
			<c:if test="${empty massage}">
				<c:forEach var="item" items="${jsonList}">
					<li>
						<a class="message__item" href="javaScript:openerLinkTo('${item.url}','${item.subject}','${item.popupType}','${item.height}','${item.width}')">
							${item.msg}
							<!-- <span class="message__num">10</span> -->
						</a>
					</li>
				</c:forEach>
			</c:if>
			<c:if test="${ not empty massage }">
				<li><span>${massage }</span></li>
			</c:if>
		</ul>
	</div>
</body>
</html>
