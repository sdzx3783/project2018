<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>流程定义列表</title>
<%@include file="/commons/include/get.jsp"%>
</head>
<style>
	html {
		-ms-text-size-adjust: 100%;
	    -webkit-text-size-adjust: 100%;
	    font-size: 10px;
	}
	.search-bar {
		position: relative;
		width: 80%;
		height: 3.2rem;
		max-width: 800px;
		margin: 20px auto;
		font-size: 1.6rem;
	}
	.search-bar:before {
		content: '';
		position: absolute;
		left: 10px;
		top: .6rem;
		display: block;
		width: 2rem;
		height: 2rem;
		background: url("/images/search_icon.png") no-repeat 0 0;
		background-size: 100% 100%;
	}
	.search-bar .search-input {
		width: 100%;
		height: 100%;
		border: solid 1px #b5b5b5;
		border-radius: 18px;
		outline: none;
		padding: 0 5.5rem 0 3.5rem;
		box-sizing: border-box;
	}
	.search-bar .search-btn {
		position: absolute;
		right: 10px;
		top: .6rem;
		width: 5rem;
		height: 2rem;
		line-height: 2rem;
		display: block;
		text-align: center;
		background: #4d7ae9;
		color: #fff;
		border-radius: 1rem;
		text-decoration: none;
	}
	.contract-bar {
		font-size: 1.6rem;
		border-top: solid 1px #ededed;
	}
	.contract-item {
		padding: 1.6rem 2rem;
		border-bottom: solid 1px #ededed;
	}
	.contract-item p {
		line-height: 2rem;
	}
</style>

<body>
	<div id="body">
		<div>
			<input type="text" hidden="hidden" id="listTotal" value="${page.totalCount}"> 
			<input type="text" hidden="hidden" id="curLength" value="${(page.currentPage-1)*page.totalCount+contractList.size()}">
		</div>
		<div class="contract-bar" id="bpmFormDialogTable">	
		     <c:if test="${not empty contractList}">
				<c:forEach items="${contractList}" var="contract">
					<div class="contract-item">
						<p>
							<textarea style="display: none;">{"F_contract_num":"${contract.contract_num}","F_contract_name":"${contract.contract_name}"}</textarea>
							<span>${contract.contract_num}</span>
						</p>												
						<p>${contract.contract_name}</p>
					</div>
				</c:forEach>
			</c:if>
		</div>
	</div>
	<script type="text/javascript">
	$(function(){
		var curPage = 2;
		var is_scroll = true; //防止一次滚动多次请求数据
		$(window).scroll( function() { 
			var totalheight = parseFloat($(window).height()) + parseFloat($(window).scrollTop() + 10);     //浏览器的高度加上滚动条的高度 
			if (is_scroll && $(document).height() <= totalheight && $("#curLength") && $("#curLength").val()*1*(curPage-1)< $("#listTotal").val()*1)//当文档的高度小于或者等于总的高度的时候，开始动态加载数据
		    {
				is_scroll = false;
		    	$.post("/weixin/mobiledialog/showContract.ht?page="+ curPage, {
				}, function(data) {
					var d = JSON.parse(data);
					if(d.status == 1){
						var list = d.data;
						if(list.length>0){
							var appendBodyStr = "";
							for(var i=0;i<list.length;i++){
								appendBodyStr += "<div class=\"contract-item\">" +
								"<p><textarea style=\"display: none;\">{\"F_contract_num\":\""+ list[i].contract_num +"\",\"F_contract_name\":\""+ list[i].contract_name +"\"}</textarea>" + 
								"<span>"+list[i].contract_num+"</span></p>" +
								"<p>"+list[i].contract_name+"</p></div>"							
							}
							$("#bpmFormDialogTable").append(appendBodyStr);
						}
						curPage = curPage + 1;
						setTimeout(function() {
							is_scroll = true;
						},200)
					}
				});
		    } 
		});	
	});
	
	</script>
</body>
</html>