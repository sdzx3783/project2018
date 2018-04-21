<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="f" uri="http://www.jee-soft.cn/functions" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="hotent" uri="http://www.jee-soft.cn/paging" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>部门门户</title>
    <link rel="stylesheet" href="${ctx}/styles/custom/reset.css">
    <link rel="stylesheet" href="${ctx}/styles/custom/icon.css">
    <link rel="stylesheet" href="${ctx}/styles/custom/index.css">
    <link rel="stylesheet" href="${ctx}/styles/custom/idangerous.swiper.css">
        <style>
      
		.main-cell-body{
			padding: 15px 20px;
		}
        .friends-link{
            font-size: 14px;
            width: 302px;
        }

        .friends-link .main-cell-body{
            font-size: 14px;
            padding-top: 10px;
            overflow: hidden;
            height:612px;
        }
        .more{
        	color: #9babcc;
        	font-size: 12px;
        	float: right;
        	margin-right: 15px;
        }
        .main-cell-head:after{
        	left: 15px;
        }
        .right-cell{
            margin-bottom: 15px;
        }
		.news{
            min-height: 115px;
        }
		.news-item{
			position: relative;
		}
		.news-title{
			font-size: 14px;
			color: #657386;
			margin-right: 90px;
			overflow: hidden;
			text-overflow: ellipsis;
			white-space: nowrap;
		}
		.news-date{
			font-size: 14px;
			color: #999;
			position: absolute;
			right: 0;
			top: 0;
			height: 27px;
		}
		.media{
			margin-bottom: 15px;
		}
		.media:last-child{
			margin-bottom: 0;
		}
		.media-profile{
			width: 50px;
			height: 50px;
			border-radius: 25px;
			overflow: hidden;
			float: left;
		}
		.media-profile img{
            max-width: 100%;
			vertical-align: middle;
		}
		.media-info{
			color: #657386;
			font-weight: 700;
			padding-top: 2px;
			margin-left: 60px;
		}
		.media-name{
			margin-right: 10px;
		}
		.media-date{
			font-weight: normal;
			color: #afafaf;
		}
			
		.swiper-container{
			background: #fff;
			height: 225px;
			margin-bottom: 15px;
		}
		.pagination {
		  position: absolute;
		  z-index: 20;
		  bottom: 10px;
		  width: 100%;
		  text-align: center;
		}
		.swiper-pagination-switch {
		  display: inline-block;
		  width: 8px;
		  height: 8px;
		  border-radius: 8px;
		  background: #555;
		  margin: 0 5px;
		  opacity: 0.8;
		  border: 1px solid #fff;
		  cursor: pointer;
		}
		.swiper-active-switch {
		  background: #fff;
		}
		.swiper-slide {
		    text-align: center;
		}
    </style>
</head>
<body>
		<div class="content-wrap">
            <div class="section-main">
				
				<%-- <div class="swiper-container">
				  <div class="swiper-wrapper">
						<c:if test="${imgSrcList==null || imgSrcList.size()==0 }">
							<div class="swiper-slide">没有配置首页门户图片</div>
						</c:if>
						<c:forEach items="${imgSrcList }" var="imgSrc">
							<div class="swiper-slide"><img src="${imgSrc}" alt=""></div>
						</c:forEach>

				      <!-- <div class="swiper-slide"><img src="/images/banner1.png" alt=""></div>
				      <div class="swiper-slide"><img src="/images/banner2.png" alt=""></div> -->
				  </div>
				  <div class="pagination"></div>
				</div> --%>

				<div class="main-cell">
                    <div class="main-cell-head orange">项目变更
						
                    </div>
                    <div class="main-cell-body">
                    	<div class="news">
                    	    <span>项目名称</span><span>时间</span><span>任务名称</span>
                    		<c:forEach items="${taskLogslist}" var="taskLog">
	                    		<div class="news-item">
	                    			
	                    			<span>${taskLog.projectName}</span>
	                    		
	                    			<span><fmt:formatDate value='${taskLog.ctime}' pattern='yyyy/MM/dd' /></span>
	                    			<span class="news-title"><a href="/makshi/project/project/taskDetail.ht?id=${taskLog.taskid}">${taskLog.taskName}</a></span>
	                    		</div>
							</c:forEach>
                    	</div>
                    </div>
                </div>

                <div class="main-cell">
                    <div class="main-cell-head orange">部门通知
						<a href="/makshi/doc/docinfo/filelist.ht?docId=${tzggId}&portalOrgId=${orgId }&from=orgportalindex" class="more">更多 ></a>
                    </div>
                    <div class="main-cell-body">
                    	<div class="news">
                    		<c:forEach items="${tzgg_list}" var="docFile">
	                    		<div class="news-item">
	                    			<a href="/makshi/doc/docinfo/filedetail.ht?id=${docFile.dfid}">
	                    				<div class="news-title">${docFile.title}</div>
	                    			</a>
	                    			<div class="news-date"><fmt:formatDate value='${docFile.createtime}' pattern='yyyy/MM/dd' /></div>
	                    		</div>
							</c:forEach>
                    	</div>
                    </div>
                </div>

                <div class="main-cell">
                    <div class="main-cell-head orange">部门制度
						<a href="/makshi/doc/docinfo/filelist.ht?docId=${gszdId}&portalOrgId=${orgId }&from=orgportalindex" class="more">更多 ></a>
                    </div>
                    <div class="main-cell-body">
                    	<div class="news">
							<c:forEach items="${gszd_list}" var="docFile">
	                    		<div class="news-item">
	                    			<a href="/makshi/doc/docinfo/filedetail.ht?id=${docFile.dfid}">
	                    				<div class="news-title">${docFile.title}</div>
	                    			</a>
	                    			<div class="news-date"><fmt:formatDate value='${docFile.createtime}' pattern='yyyy/MM/dd' /></div>
	                    		</div>
                    		</c:forEach>
                    	</div>
                    </div>
                </div>
				<!-- 右侧 -->
                <div class="aside">
                    <style>
                        .medias-wrap{
                            position: relative;
                            height: 310px;
                            overflow: hidden;
                        }
                        .medias{
                            position: absolute;
                            left: 0;
                            top: 0;
                        }
                        .media{
                            overflow: hidden;
                        }
                    </style>
					
					<%-- <div class="right-cell">
                        <div class="friends-link">
                            <div class="main-cell">
                                <div class="main-cell-head orange">本月寿星</div>
                                <div class="main-cell-body">
                                    <div class="medias-wrap">
                                        <div class="medias">
                                            <c:forEach items="${userlist}" var="user">
                                                <div class="media">
                                                    <div class="media-profile">
                                                        <img onerror="this.src='/commons/image/default_image_male.jpg'" src="${user.picture}" alt="">
                                                    </div>
                                                    <div class="media-info">
                                                        <span class="media-name">${user.fullname}</span>
                                                        <span class="media-department">${user.orgName }</span>
                                                        <div class="media-date"><fmt:formatDate value='${user.birthday}' pattern='MM/dd' />
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div> --%>

                </div>
                <!-- 右侧 -->

            </div>
        </div>
<script src="/js/custom/jquery-1.8.3.min.js"></script>
<script src="${ctx}/js/custom/idangerous.swiper.min.js"></script>
<script type="text/javascript">
	window.onload = function() {
	  var mySwiper = new Swiper('.swiper-container',{
	    loop: true,
	    autoplay : 3000,
	    pagination : '.pagination',
	    paginationClickable :true
	    //其他设置
	  });  
	}

    $(function(){
        var sinHeight = $('.media').height() + 15;
        var $medias  = $(".medias");
        var top = 0;
        var maxTop = $medias.find(".media").length * sinHeight - 220;


        function startScroll(){
            setInterval(function(){

                if(top < -maxTop){
                    // 重置top为0 
                    top = 0;
                }
                $medias.css("top", top--);
            }, 50);
        }
        startScroll();

    });
</script>
</body>
</html>