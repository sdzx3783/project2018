<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>文档详情</title>
<%@include file="/codegen/include/customForm.jsp"%>
<%@include file="/commons/include/ueditor.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
<script type="text/javascript"
	src="${ctx}/js/hotent/platform/form/AttachMent.js"></script>
<script type="text/javascript"
	src="${ctx}/js/hotent/platform/system/HtmlUploadDialog.js"></script>
<script type="text/javascript"
	src="${ctx}/js/hotent/platform/system/FlexUploadDialog.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${ctx}/js/ueditor1433/ueditor_default.config.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${ctx}/js/ueditor1433/ueditor.all.min.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${ctx}/js/ueditor1433/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${ctx}/js/ueditor1433/extend/picture.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${ctx}/js/ueditor1433/themes/default/css/ueditor.css"></script>

</head>
<style>
	.collec {
		color: red;
		font-size: 20px;
	}
	.collec .collect{
		display: none;
	}
	.no-collec .uncollect{
		display: none;
	}
	.tabcontent{
		padding: 10px;
	}
	.tabtitle{
		background: #eeeeee;
		overflow: hidden;
		border-bottom: 1px solid #d2d2d2;
	}
	.tabtitle ul {
	 list-style-type: none;
	 margin:0px;
	 padding:0px;
	}
	.tabtitle li {
		float:left;
		font-size: 14px;
		width:129px;
		text-align: center;
		line-height:53px;
	}
	.tabtitle a{
		color: #333;
	}
	.tabhover{
		background: #478de4;
	}
	.tabhover a{
		color: #fff;
	}
	.link{
		display: inline-block;
	}
	.link span{
		background: none !important;
	}
	.link .icon{
		vertical-align: middle;
	}
	.link .text{
		vertical-align: middle;
	}
	.icon{
		vertical-align: middle;
		font-size: 16px;
	}
	.btn{
		color: #478de4 !important;
		display: inline-block;
		padding: 5px 20px;
		border-radius: 2px;
		border: 1px solid #478de4;
	}
	.btn--blue{
		color: #478de4 !important;
		border-color: #478de4 !important;
	}
	.btn--yellow{
		color: #fa9410 !important;
		border-color: #fa9410 !important;
	}

	.tabcontent{
		height: 500px;
		    padding: 20px;
		overflow: auto;
	}
	.toolBar{
		overflow: hidden;
	}
	.toolBar h2{
		font-size: 20px;
		    padding-left: 0;
		height: auto;
	}
	.panel-toolbar{
		height: auto;
		margin-top: 0;
		    padding: 20px;
	}
	.panel-top{
		margin: 0!important;
		border-bottom: 1px solid #dadfed;
	}
	.btns{
		float: right;
	}
	.btns li{
		display: inline-block;
	}
	.panel-toolbar a{
		float: none;
	}
	.text{
		vertical-align: middle;
	}
	.collec {
		font-size: 12px;
	}
	.document-attr{
		width: 100%;
	}
	.document-attr th,
	.document-attr td{
		padding: 15px 5px;
		border: 1px solid #dadfed;
	}
</style>
<body>
	<script type="text/javascript">

	function collection(dfid){
		var url='collection.ht';
		$.post(url,{'id':dfid},function(data){
			if(data=='ok'){
				//收藏成功
				$("#collect").attr("class","btn btn--blue  collec");
			}else if(data=="cancel"){
				//取消收藏
				$("#collect").attr("class","btn btn--blue no-collec");
			}else{
				$.ligerDialog.alert(data,"提示");
			}
		});
	}
	$(function () {
		$('.tabtitle li').click(function () {
			var index = $(this).index();
			$(this).attr('class',"tabhover").siblings('li').attr('class','taba');
			$('.tabcontent').eq(index).show().siblings('.tabcontent').hide();
		});
	});
	
	function toEdit(){
		/* if(window.location.href.indexOf("&lastReturnUrl=")<0){
			var href=encodeURIComponent(window.location.href);
			window.location.href="fileedit.ht?id=${docFile.dfid}&docId=${docId}&lastReturnUrl="+href;
		}else{
			var index=window.location.href.indexOf("&lastReturnUrl=");
			var href=encodeURIComponent(window.location.href.substring(0,index));
			window.location.href="fileedit.ht?id=${docFile.dfid}&docId=${docId}&lastReturnUrl="+href;
		} */
		var index=window.location.href.indexOf("&lastReturnUrl=");
		var href="";
		if(index>=0){
			href=encodeURIComponent(window.location.href.substring(0,index));
		}else{
			href=encodeURIComponent(window.location.href);
		}
		if('${param.type}'=='index'){
			window.location.href="fileedit.ht?id=${docFile.dfid}&docId=${docId}";
		}else{
			window.location.href="fileedit.ht?id=${docFile.dfid}&docId=${docId}&lastReturnUrl="+encodeURIComponent(href);
		}
	}
	
	function toEdit2(){
		window.location.href="fileedit.ht?id=${docFile.dfid}&docId=${docId}&lastReturnUrl="+encodeURIComponent("${returnUrl}");
	}
</script>
	<c:choose>
		<c:when test="${empty docFile }">
			<div class="error-info">
				<div class="toolBar">
					<div class="group">您要浏览的内容已被删除！</div>
					<div class="l-bar-separator"></div>
					
					
					
				<c:choose>
					<c:when test="${param.type!='index' }">
						<div class="group">
							<a class="link back" href="${returnUrl}"><span></span>返回</a>
						</div>
					</c:when>
					<c:otherwise>
						<div class="group">
							<a class="link close" href="javascript:void(0)" onclick="window.close();"><span></span>关闭</a>
						</div>
					</c:otherwise>
				</c:choose>
				</div>
			</div>
		</c:when>
		<c:otherwise>
			<div class="panel" style="height: 100%; overflow: auto;">
				<div class="panel-top">
					<div class="panel-toolbar">
						<div class="toolBar">
							<h2>${docFile.title}</h2>

							<ul class="btns">
								<li>
										<c:choose>
											<c:when test="${param.type!='index' }">
												<a href="${returnUrl}" class="btn"><span class="icon icon-arrow-back"></span><span class="text">返回</span></a>
											</c:when>
											<c:otherwise>
												<a href="javascrpt:void(0)" class="btn" onclick="window.close();"><span class="link close"></span><span class="text">关闭</span></a>
											</c:otherwise>
										</c:choose>
								</li>
								<c:if test="${isWrite }">
								<li>
									<c:choose>
											<c:when test="${param.type!='index' }">
												<%-- <a href="fileedit.ht?id=${docFile.dfid}&docId=${docId}&lastReturnUrl=${returnUrl}" class="btn btn--yellow"><span class="icon icon-edit"></span><span class="text">编辑</span></a> --%>
												<a href="javascript:;" onclick='toEdit2()' class="btn btn--yellow"><span class="icon icon-edit"></span><span class="text">编辑</span></a>
											</c:when>
											<c:otherwise>
												<a onclick='toEdit()' href="javascript:;" class="btn btn--yellow"><span class="icon icon-edit"></span><span class="text">编辑</span></a>
											</c:otherwise>
									</c:choose>
								</li>
								</c:if>
								<li><a id="collect" href="javascript:;"
									<c:if test="${!isCollection}">class="btn btn--blue no-collec"</c:if>
									<c:if test="${isCollection}">class="btn btn--blue collec"</c:if>
									onclick="collection(${docFile.dfid})"
								 	><span class="icon icon-star"></span><span class="text"><span class="uncollect">取消收藏</span><span class="collect">收藏</span></span>
								</a></li>
							</ul>
						</div>
					</div>
				</div>
				<div class="tabcontent">
					${docFile.content }
				</div>
				<div class="tabcontent" style="DISPLAY: none">
					<table class="document-attr">
						<tr>
							<td>文档编号</td>
							<td colspan="3">${docFile.filenum}</td>
						</tr>
						<tr>
							<td>文档版本</td>
							<td>${docFile.version}</td>
							<td>文档状态</td>
							<td><c:if test='${docFile.status==0 }'>草稿</c:if><c:if test='${docFile.status==1 }'>正常</c:if></td>
						</tr>
						<tr>
							<td>文档目录</td>
							<td>${docName}</td>
							<td>文档所有者</td>
							<td>${docFile.creator}</td>
						</tr>
						<tr>
							<td>部门</td>
							<td colspan="3">${orgName}</td>
						</tr>
						<tr>
							<td>关键字</td>
							<td colspan="3">${docFile.keyword}</td>
						</tr>
					</table>
				</div>
				<div class="tabcontent" style="DISPLAY: none">
					<div name="div_attachment_container">
						<div class="attachement"></div>
						<textarea style="display: none" controltype="attachment"
							name="file" lablename="附件" validate="{}">${docFile.file}</textarea>
						<!-- <a href="javascript:;" field="attachment" class="link selectFile"
							atype="select" onclick="AttachMent.addFile(this);">选择</a> -->
					</div>
				</div>
				<div class="tabcontent" style="DISPLAY: none">
					<div style="margin: 0 16px 10px 16px !important; font-size: 16px;">文档日志记录</div>
					<iframe id="viewFrame" src="docloglist.ht?type=1&refid=${docFile.dfid }" frameborder="0" width="100%"
							height="100%" scrolling="auto"></iframe>
				</div>
				<div>
					<ul class="tabtitle">
						<li class="tabhover"><a href="#">正文内容</a></li>
						<li class="taba"><a href="#">文档属性</a></li>
						<li class="taba"><a href="#">文档附件${docFile.filecount }</a></li>
						<li class="taba"><a href="#">文档日志</a></li>
					</ul>
				</div>
		</c:otherwise>
	</c:choose>

</body>
</html>
