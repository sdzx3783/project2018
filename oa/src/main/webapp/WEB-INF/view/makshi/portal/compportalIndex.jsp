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
    <title>公司门户</title>
    <link rel="stylesheet" href="${ctx}/styles/custom/reset.css">
    <link rel="stylesheet" href="${ctx}/styles/custom/icon.css">
    <link rel="stylesheet" href="${ctx}/styles/custom/index.css">
    <link rel="stylesheet" href="${ctx}/styles/custom/idangerous.swiper.css">
        <style>
        .content-wrap{
            min-width: 1365px;
            overflow: hidden;
        }

        .section-main{
            width: 1035px;
            margin-right: auto;
            margin-left: 15px;
            float: left;
        }
        .aside{
            position: static;
            min-width: 300px;
            margin-left: 1065px;
            right: auto;
            margin-right: 15px;
        }
        .main-cell-body{
            padding: 15px 20px;
        }
        .friends-link{
            font-size: 14px;
            /*width: 302px;*/
        }
        .content-wrap {
            /* overflow: hidden; */
            margin-left: 0;
        }

        .friends-link .main-cell-body{
            font-size: 14px;
            padding-top: 10px;
            overflow: hidden;
            height:612px;
        }
        .more-box{
            overflow: hidden;
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
            margin-right: 5px;
        }
        .media-profile img{
            max-width: 100%;
            vertical-align: middle;
        }
        .media-info{
            color: #657386;
            font-weight: 700;
            padding-top: 2px;
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

        .medias-wrap{
            position: relative;
            height: 610px;
            overflow: hidden;
        }
        .medias{
            position: absolute;
            left: 0;
            top: 0;
            transition: top 3.25s linear;
        }
        .media{
            overflow: hidden;
        }
        
    

        /*修改后的标题样式*/
        .oa-section-head{
            font-weight: bold;
            color: #478de4;
            padding: 15px 0;
        }
        .oa-section-head h2{
            float: left;
            font-size: 18px;
            padding-left: 20px;
            border-left: 3px solid #478de4;
        }
        .oa-section-head .inner-wrap{
            font-size: 12px;
            margin-top: 2px;
            margin-left: 120px;
            border-bottom: 2px solid;
            -webkit-border-image: -webkit-gradient(linear, left top, right top, from(#b7b9c2), to(transparent)) 10 0;
            -webkit-border-image: -webkit-linear-gradient(left, #b7b9c2, transparent) 10 0;
            -o-border-image: -o-linear-gradient(left, #b7b9c2, transparent) 10 0;
            border-image: -webkit-gradient(linear, left top, right top, from(#b7b9c2), to(transparent)) 10 0;
            border-image: linear-gradient(to right, #b7b9c2, transparent) 10 0;
        }
        .oa-section-head .inner-wrap h3{
            display: inline-block;
            border-bottom: 2px solid #4086d5;
            margin-bottom: -2px;
        }
        .media-title{
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;  
        }
    </style>
</head>
<body>
        <div class="content-wrap">
            <div class="section-main">
                
                <div class="swiper-container">
                  <div class="swiper-wrapper">
                        <c:if test="${imgSrcList==null || imgSrcList.size()==0 }">
                            <div class="swiper-slide">没有配置首页门户图片</div>
                        </c:if>
                        <c:forEach items="${imgSrcList }" var="imgSrc">
                            <div class="swiper-slide"><img src="${imgSrc}" alt=""></div>
                        </c:forEach>

                  </div>
                  <div class="pagination"></div>
                </div>
                

                <div class="main-cell">
                    <div class="oa-section-head">
                        <h2>通知公告</h2>
                        <div class="inner-wrap">
                            <h3>Announcement Notice</h3>
                        </div>
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
                        <div class="more-box">
                            <a href="/makshi/doc/docinfo/filelist.ht?docId=${tzggId}&from=compportalindex" class="more">更多 ></a>
                        </div>
                    </div>
                </div>

                <div class="main-cell">
                    <div class="oa-section-head">
                        <h2>公司制度</h2>
                        <div class="inner-wrap">
                            <h3>Company Policy</h3>
                        </div>
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
                        <div class="more-box">
                            <a href="/makshi/doc/docinfo/filelist.ht?docId=${gszdId}&from=compportalindex" class="more">更多 ></a>
                        </div>
                    </div>
                </div>



            </div>

                <!-- 右侧 -->
                <div class="aside">
                    <div class="right-cell">
                        <div class="friends-link">
                            <div class="main-cell">
                                <div class="oa-section-head">
                                    <h2>本月寿星</h2>
                                    <div class="inner-wrap">
                                        <h3>Happy Birthday</h3>
                                    </div>
                                </div>

                                <div class="main-cell-body">
                                    <div class="medias-wrap">
                                        <div class="medias">
                                            <c:forEach items="${userlist}" var="user">
                                                <div class="media">
                                                    <div class="media-profile">
                                                        <img 
                                                        
                                                            <c:choose>
                                                                <c:when test="${user.sex==1}">
                                                                    onerror="this.src='/commons/image/default_image_male.jpg'"
                                                                </c:when>
                                                                <c:otherwise>
                                                                     onerror="this.src='/commons/image/default_image_female.jpg'" 
                                                                </c:otherwise>

                                                            </c:choose>
                                                    
                                                            src="${user.picture}" alt="">
                                                    </div>
                                                    <div class="media-info">
                                                        <div class="media-title">${user.fullname}&nbsp;&nbsp;${user.orgName}</div>
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
                    </div>
                </div>
                <!-- 右侧 -->
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
        var sinHeight = $('.media').outerHeight(true);
        var $medias  = $(".medias");
        var top = 0;

        var mediaHeight = $medias.find(".media").length * sinHeight;
        var maxTop = $medias.find(".media").length * sinHeight - 15 - 310;
		
        function startScroll() {
            /*自动播放*/
            var h = $(".medias .media:eq(0)").height() + 15;
            var l = $(".medias .media").length; 
            var round = setInterval(function() {
                
                clearTimeout(ap);
                $medias.attr("style","transition: transform 1s linear; transform: translate(0px, -"+ h +"px);");
                var ap = setTimeout(function() {
                	$medias.attr("style","transition: none; transform: translate(0px, 0px);");  
                    $medias.append($(".medias .media:eq(0)"));                                              
                },1000);     
            }, 2000);
        }
        
        function startScroll11(){
            var timter = setInterval(function(){

                if(top < -maxTop){
                    // 重置top为0 
                    top = 0;
                }else {
                	top = top - 65;
                }
                
                $medias.css("top", top);
            }, 3250);
        }
        
        if(mediaHeight > 310){
            startScroll();
        }
    });
</script>
</body>
</html>