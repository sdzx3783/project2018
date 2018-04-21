<%@ page language="java" contentType="text/html; charset=UTF-8"  %>
<%@include file="/commons/include/html_doctype.html"%>
<head>
    <title>业务流程管理系统</title>
<%--    <%@include file="/commons/include/home.jsp"%> --%>  
    <link rel="stylesheet" href="${ctx}/styles/custom/reset.css">
    <link rel="stylesheet" href="${ctx}/styles/custom/icon.css">
    <link rel="stylesheet" href="${ctx}/styles/custom/index.css">
    <link rel="stylesheet" href="${ctx}/styles/custom/calendar.css">
    <style>
    .city-wrap {
        position: absolute;
        right: 1px;
        bottom: 15px;
        padding-left: 15px;
        background: url(/images/map_icon.png) no-repeat left center;
        color: #657386;
    }

    .cityinput {
        color: #657386;
    }

    .case-cell,.friends-link {
        width: 302px;
        font-size: 14px;
    }

    .friends-link a {
        float: left;
        margin: 0 10px;
        color: #888;
        line-height: 30px;
    }

    .friends-link a:hover {
        color: #48abff;
    }

    .friends-link .main-cell-body {
        overflow: hidden;
        padding-top: 10px;
        font-size: 13px;
    }

    .right-cell {
        margin-bottom: 15px;
    }

    .calendar {
        height: 245px;
    }

    .case-cell .main-cell-body {
        background: #eef1f7;
    }

    .calendar-ct {
        padding: 12px 0;
    }

    .calendar-ct {
        border-bottom: 0;
        border-radius: 0;
    }

    .btn {
        display: inline-block;
        padding: 0 20px;
        border-radius: 3px;
        background: #478de4;
        -webkit-box-shadow: 0 1px 1px rgba(0,0,0,.15);
        -moz-box-shadow: 0 1px 1px rgba(0,0,0,.15);
        box-shadow: 0 1px 1px rgba(0,0,0,.15);
        color: #fff;
        line-height: 30px;
    }

    .icon {
        vertical-align: middle;
        font-size: 18px;
    }

    .text {
        vertical-align: middle;
        font-size: 14px;
    }

    .case-list a {
        color: #8798b0;
    }

    .weather {
        padding-top: 10px;
        height: 115px;
    }

    .calendar .calendar-views .mark-day {
        background: yellow;

    }
    .calendar-finished{
        background: #4be77b!important;
        color: #fff!important;
    }

    .calendar .days li[data-calendar-day].mark-day:hover,.calendar .view-month li[data-calendar-month]:hover {
        background: yellow;
    }

    .extend #friendshipLink {
        height: auto!important;
    }

    .main-cell-body {
        padding-bottom: 0;
    }

    .aside .main-cell-body {
        padding-bottom: 10px;
    }


    #friendshipLink.main-cell-body {
        padding-bottom: 0;
    }

    .friends-link .sq {
        display: none;
    }

    .friends-link.extend .sq {
        display: inline;
    }

    .friends-link.extend .zk {
        display: none;
    }

    .extend-btn {
        float: right;
        margin-right: 10px;
        cursor: pointer;
    }
    .mtab-body{
        padding: 8px;
        min-height: 95px;
    }

    .mtab-title{
        padding-right: 190px;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
    }
    .mtab-line{
        color: #8798b0;
        position: relative;
    }
    .mtab-line a{
        color: #8798b0;
    }
    .mtab-aside{
        position: absolute;
        right: 0;
        top: 0;
    }
    .txr{
        text-align: right;
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
    </style>
</head>
<body>
<%-- <div class="index-page">${html}</div> --%>    
        <div class="content-wrap">
            <div class="section-main" style="width: auto;">
                <div class="main-cell">
                    <div class="oa-section-head">
                        <h2>流程处理</h2>
                        <div class="inner-wrap">
                            <h3>Procedure Processing</h3>
                        </div>
                    </div>
                    <div class="main-cell-body">

                         <div class="mtab">
                            <div class="mtab-head">
                                <div id="db_span_show" class="mtab-link active">我的待办</div>
                                <div id="jb_span_show" class="mtab-link">我的经办</div>
                            </div>
                            <div class="mtab-body">
                                <div class="mtab-item active">
                                    <div class="mtab-content">
                                        <div id="pendingMatters">
                                            <!-- <div class="mtab-line">
                                                <a href="" class="mtab-title">水保项目进度汇报</a>
                                                <span class="mtab-aside">黄经理 2016-8-26 10:00am</span>
                                            </div>
                                            <div class="mtab-line">
                                                <a href="" class="mtab-title">水保项目进度汇报</a>
                                                <span class="mtab-aside">黄经理 2016-8-26 10:00am</span>
                                            </div>
                                            <div class="mtab-line txr">
                                                <a target="_blank" href="/platform/mail/outMail/list.ht">更多...</a>
                                            </div> -->
                                        </div>
                                    </div>
                                </div>
                                <div class="mtab-item">
                                    <div class="mtab-content">
                                        <div id="completedMatters">
                                            <!-- <div class="mtab-line">
                                                <a href="" class="mtab-title">水保项目进度汇报水保项目进度汇报水保项目进度汇报水保项目进度汇报水保项目进度汇报</a>
                                                <span class="mtab-aside">黄经理 2016-8-26 10:00am</span>
                                            </div>
                                            <div class="mtab-line txr">
                                                <a target="_blank" href="/platform/mail/outMail/list.ht">更多...</a>
                                            </div> -->
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="main-cell">
                    <div class="oa-section-head">
                        <h2>消息/邮件</h2>
                        <div class="inner-wrap">
                            <h3>Message/Email</h3>
                        </div>
                    </div>
                    <div class="main-cell-body">
                         <div class="mtab">
                            <div class="mtab-head">
                                <div id="db_span_show_msg" class="mtab-link active">我的消息</div>
                                <div id="jb_span_show" class="mtab-link">我的邮件</div>
                            </div>
                            <div class="mtab-body">
                                <div class="mtab-item active">
                                    <div class="mtab-content"  >
                                        <div id="myMessageTab">
                                            <!-- <div class="mtab-line">
                                                <a href="" class="mtab-title">水保项目进度汇报</a>
                                                <span class="mtab-aside">黄经理 2016-8-26 10:00am</span>
                                            </div>
                                            <div class="mtab-line">
                                                <a href="" class="mtab-title">水保项目进度汇报</a>
                                                <span class="mtab-aside">黄经理 2016-8-26 10:00am</span>
                                            </div>
                                            <div class="mtab-line txr">
                                                <a target="_blank" href="/platform/mail/outMail/list.ht">更多...</a>
                                            </div> -->
                                        </div>
                                    </div>
                                </div>
                                <div class="mtab-item">
                                    <div class="mtab-content" >
                                        <div id="myEMailTab">
                                            <!-- <div class="mtab-line">
                                                <a href="" class="mtab-title">水保项目进度汇报</a>
                                                <span class="mtab-aside">黄经理 2016-8-26 10:00am</span>
                                            </div>
                                            <div class="mtab-line">
                                                <a href="" class="mtab-title">水保项目进度汇报</a>
                                                <span class="mtab-aside">黄经理 2016-8-26 10:00am</span>
                                            </div>
                                            <div class="mtab-line">
                                                <a href="" class="mtab-title">水保项目进度汇报</a>
                                                <span class="mtab-aside">黄经理 2016-8-26 10:00am</span>
                                            </div>
                                            <div class="mtab-line txr">
                                                <a target="_blank" href="/platform/mail/outMail/list.ht">更多...</a>
                                            </div> -->
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="main-cell">
                    <div class="oa-section-head">
                        <h2>公告/文档</h2>
                        <div class="inner-wrap">
                            <h3>Notice/Document</h3>
                        </div>
                    </div>
                    <div class="main-cell-body">
                         <div class="mtab">
                            <div class="mtab-head">
                                <div id="db_span_show" class="mtab-link active">通知公告</div>
                                <div id="jb_span_show" class="mtab-link">最新文档</div>
                            </div>
                            <div class="mtab-body">
                                <div class="mtab-item active">
                                    <div class="mtab-content"  >
                                        <div id="noticeTab">
                                            <!-- <div class="mtab-line">
                                                <a href="" class="mtab-title">水保项目进度汇报</a>
                                                <span class="mtab-aside">2016-8-26 10:00am</span>
                                            </div>
                                            <div class="mtab-line">
                                                <a href="" class="mtab-title">水保项目进度汇报</a>
                                                <span class="mtab-aside">2016-8-26 10:00am</span>
                                            </div>
                                            <div class="mtab-line">
                                                <a href="" class="mtab-title">水保项目进度汇报</a>
                                                <span class="mtab-aside">2016-8-26 10:00am</span>
                                            </div>
                                            <div class="mtab-line txr">
                                                <a target="_blank" href="/platform/mail/outMail/list.ht">更多...</a>
                                            </div> -->
                                        </div>
                                    </div>
                                </div>
                                <div class="mtab-item">
                                    <div class="mtab-content" >
                                        <div id="newDocTab">
                                            <!-- <div class="mtab-line">
                                                <a href="" class="mtab-title">水保项目进度汇报</a>
                                                <span class="mtab-aside">2016-8-26 10:00am</span>
                                            </div>
                                            <div class="mtab-line">
                                                <a href="" class="mtab-title">水保项目进度汇报</a>
                                                <span class="mtab-aside">2016-8-26 10:00am</span>
                                            </div>
                                            <div class="mtab-line txr">
                                                <a target="_blank" href="/platform/mail/outMail/list.ht">更多...</a>
                                            </div> -->
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- 日历模块 -->
                <div class="aside">
                    <div class="right-cell">
                        <div id="ca"></div>

                        <div class="right-cell">
                            <div class="case-cell">
                                <div class="main-cell">
                                    <div class="main-cell-body">
                                        <ul class="case-list" id="mySchedule" data-id="${userId}">
                                        </ul>

                                        <a target="_blank" href="/platform/system/sysPlan/charge.ht" class="btn"><span class="text">更多</span><span class="icon icon-arrow-right-thick"></span></a>
                                        <%-- <a target="_blank" href="/platform/system/sysPlan/edit.ht" class="btn"><span class="text">添加</span><span class="icon icon-plus"></span></a>--%>
                                        <a target="_blank" class="btn" id="addSysPlan" onclick="addCalendar()" ><span></span>添加<span class="icon icon-plus"></span></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="right-cell">
                        <div class="main-cell">
                            <div class="main-cell-head yellow white">
                                <span>天气预报</span>
                                <div class="city-wrap">
                                     <input type="text" readonly="readonly" style="border: 0;width: 44px;" value="深圳市" size="10" class="cityinput fr" id="citySelect" placeholder="请输入目的地">
                                </div>
                            </div>
                            <div class="main-cell-weather">
                                    <div class="weather">

                                        <!-- 当天天气 -->
                              <!--           <div class="weather-left fl fixFloat">
                                                <div class="weather-today">
                                                    <div class="today-info"><p class="today-week">周三</p><p>小雨</p><p>微风</p><p>24 ~ 16℃</p></div>
                                                    <div class="today-icon"><img src="http://api.map.baidu.com/images/weather/day/zhenyu.png" alt=""></div>
                                                </div>
                                        </div> -->
                                        <!-- 当天天气 -->

                                        <!-- 未来三天天气 -->
                                        <div class="weather-right fixFloat">
                                        	<div style="text-align:center;margin-top:10%;">天气获取中...</div>
                                            <!-- <div class="item"><div class="weather-icon"><img src="http://api.map.baidu.com/images/weather/day/xiaoyu.png" alt=""></div><p class="weather-week">周四</p><p class="weather-temper">17 ~ 13℃</p></div>
                                            <div class="item"><div class="weather-icon"><img src="http://api.map.baidu.com/images/weather/day/xiaoyu.png" alt=""></div><p class="weather-week">周四</p><p class="weather-temper">17 ~ 13℃</p></div>
                                            <div class="item"><div class="weather-icon"><img src="http://api.map.baidu.com/images/weather/day/xiaoyu.png" alt=""></div><p class="weather-week">周四</p><p class="weather-temper">17 ~ 13℃</p></div> -->
                                        </div>
                                        <!-- 未来三天天气 -->
                                    </div>
                            </div>
                        </div>
                    </div>

                    <div class="right-cell">
                        <div class="friends-link">
                            <div class="main-cell">
                                <div class="main-cell-head orange">友情链接
                                    <span class="extend-btn zk">展开</span>
                                    <span class="extend-btn sq">收起</span>
                                </div>
                                <div class="main-cell-body" style="padding:5px" id="friendshipLink">

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- 日历模块 -->

            </div>
        </div>
    <script type="text/javascript" src="${ctx}/js/custom/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/custom/calendar.js"></script>
     <script type="text/javascript" src="${ctx}/js/custom/common.js"></script>
     <script type="text/javascript" src="${ctx}/js/util/util.js"></script>
    <script type="text/javascript" src="${ctx}/js/custom/main_home.js"></script>
    <script type="text/javascript" src="${ctx}/js/custom/cityselect.js"></script>
    <script>
        $(function(){
            var $fl = $('.friends-link');
            
            // 监听友情链接插入完成事件
            $('#friendshipLink').on('getyqlj', function(){
                if($('#friendshipLink').height() <= 120){
                    $('.friends-link .main-cell-head .extend-btn').remove();
                }else{
                    $('#friendshipLink').height(120);
                }
            });

            $('.extend-btn').on('click', function(){
                if($fl.hasClass('extend')){
                    $fl.removeClass('extend');
                }else{
                    $fl.addClass('extend');
                }
            });

        });
        function addCalendar(){
        	//获得窗口的垂直位置 
            var iTop = (window.screen.availHeight - 530) / 2; 
            //获得窗口的水平位置 
            var iLeft = (window.screen.availWidth - 690) / 2; 
            // window.location.href="/platform/system/sysPlan/edit.ht?startTime="+st+"&endTime="+ed+"&t="+new Date().getTime();
         window.open('/platform/system/sysPlan/edit.ht', 'addaddField','height=500, width=680,top=' + iTop + ',left=' + iLeft + ', toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
     }
    </script>
</html>