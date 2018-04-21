<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>文档目录</title>
<%@include file="/commons/include/get.jsp" %>
<style>
body.empty{background:url(/images/error-message/404img-6.png) no-repeat center center}
.group{display:inline-block;margin-right:0}
.group,.panel-toolbar a{float:none}
.panel-toolbar .btn{display:inline-block;margin-left:0;padding:0 15px;border-radius:3px;background:#ffb966;color:#fff;line-height:36px}
.icon{font-size:18px}
.icon,.text{vertical-align:middle}
.panel-toolbar{padding:15px;height:auto; overflow: hidden;}
.content-wrap{padding:15px}
#orgs{padding:0 10px;height:36px;border:1px solid #e5e5e5;border-radius:3px}
.btn-wrap{float:right;display:inline-block}
.content-wrap{overflow:hidden}
.file-item{float:left;margin:10px;width:213px;height:166px;background:#eee url(/images/file_icon.png) no-repeat center 40%;cursor:pointer;transition:all .3s}
.file-item:hover{background:#eee url(/images/file_icon_hover.png) no-repeat center 40%;transform:scale(1.1)}
.file-item:hover a{color:#478de4}
.file-item.dir-item{background:#eee url(/images/dir_icon.png) no-repeat center 40%}
.dir-item:hover{background:#eee url(/images/dir_icon_hover.png) no-repeat center 40%;transform:scale(1.1)}
.dir-item:hover a{color:#478de4}
.file-item a{display:block;padding-top:55%;color:#666;text-align:center;text-decoration:none;font-size:14px;outline: none;}
.file-item .icon{font-size:75pt}
.add-item{background:#eee url(/images/add_icon.png) no-repeat center 40%}
.add-item:hover{background:#eee url(/images/add_icon_hover.png) no-repeat center 40%;transform:scale(1.1)}
</style>
</head>
<body>
	<form id="docForm" action="list.ht" method="post">
		<div class="content-wrap">
			<div class="file-list">
				<c:forEach items="${dicList}" var="dic" >
					<div class="file-item dir-item">
						<a href="getListByYear.ht?docId=${dic.docId}&&dicId=${dic.dicId}">${dic.dicName}</a>
					</div>
				</c:forEach>
			</div>
			<c:if test='${canCreate}'>
				<div class="file-item add-item">
					<a href="javascript:;" class="create_newcat">新建分类</a>
				</div>
				<div class="file-item add-item">
					<a href="javascript:;" class="del_newcat">删除分类</a>
				</div>
			</c:if> 
		</div>
	</form>
<script type="text/javascript" src="${ctx }/js/artDialog/dialog.js?t=<%=Math.random()%>"></script>
<script type="text/javascript" src="${ctx }/js/artDialog/alert-msg.js?t=<%=Math.random()%>"></script>
<script>
	$(function(){
		var docId = '${docId}';
		// 如果文件项目数小于等于0，被body加上背景class
		if($('.file-list .file-item').length <= 0){
			$('body').addClass('empty');
		}
		$(".create_newcat").click(function(){
			$.post("/makshi/dispatch/dispatch/editDic.ht?docId="+docId,{},function(data){
			//	closeAll();
				var d = dialog({
					title : "新建分类",
					fixed : true,
					content : data
				});
				d.show();
			});
		});
		$(".del_newcat").click(function(){
			$.post("/makshi/dispatch/dispatch/delDicList.ht?docId="+docId,{},function(data){
				//closeAll();
				var d = dialog({
					title : "删除分类",
					fixed : true,
					content : data
				});
				d.show();
			});
		});
	});
</script>
</body>
</html>


