<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>日程展示</title>
<%@include file="/commons/include/form.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/styles/common/css/fullcalendar/fullcalendar.min.css"></link>
<link rel="stylesheet" type="text/css" href="${ctx}/styles/default/css/jquery.qtip.css"/>
<script type="text/javascript" src="${ctx}/js/jquery/plugins/jquery.qtip.js"></script>
<script type="text/javascript" src="${ctx}/js/fullcalendar/moment.min.js"></script>
<script type="text/javascript" src="${ctx}/js/fullcalendar/fullcalendar.min.js"></script>
<script type="text/javascript" src="${ctx}/js/lang/fullcalendar/zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/js/fullcalendar/FullcalendarControl.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysPlanShowScript.js"></script>
<script type="text/javascript" src="${ctx}/js/custom/common.js"></script>
<script type="text/javascript" src="${ctx}/js/custom/cookie.js"></script>
<script src="${ctx}/js/custom/layer.js"></script>
<script type="text/javascript">

    var calendar, calendarObj;

    //  初始化展示的视图
    function initView(dateType){
        if(dateType){
            var type = "月";
            switch(dateType){
                case "agendaDay":
                    type = "日"
                    break;
                case "agendaWeek":
                    type = "周"
                    break;
                default:
                    type = "月"
            }

            $(".fc-button-group").find("button").each(function(){
                if(type == $(this).text()){
                    $(this).click();
                    return;
                }
            });
        }
    }

    $(function() {


        var queryDataUrl = __ctx + "/platform/system/sysPlan/listBySubmitAndMonth.ht";
        var openPlanUrl = __ctx + "/platform/system/sysPlan/get.ht";
        var queryParams = {
                queryDataUrl:queryDataUrl,
                openPlanUrl:openPlanUrl,
            };

        //开始创建控件
        calendarObj = new FullcalendarControl();
        calendar = calendarObj.renderTo("calendar",{queryParams:queryParams});
        
        //初始化跳转事件和有指定的视图日期
        initGoToTheDate();
        initView(getCookie("dateType"));
        
    });
    
    // 打开新增日程视图
    function addCalendar(st,ed){
        layer.open({
            title: "新增日程",
            type: 2, 
            area: ['600px', '400px'],
            content: "/platform/system/sysPlan/edit.ht?startTime="+st+"&endTime="+ed+"&t="+new Date().getTime()
        }); 
    }
</script>
<style>
    .oa-body-container{
        width: 900px;
        margin: 0 auto;
    }
    .OALayer{
        position: fixed;
        top: 0;
        left: 0;
        background: #fff;
        padding: 20px;
        z-index: 999;
        min-width: 200px;
        max-width: 350px;
        -webkit-border-radius: 2px;
                border-radius: 2px;
        -webkit-box-shadow: 0 1px 8px rgba(0,0,0,.2);
                box-shadow: 0 1px 8px rgba(0,0,0,.2);
    }
    .events{
        font-size: 14px;
    }
    .events li{
        margin-bottom: 5px;
    }
    .events li:last-child{
        margin-bottom: 0;
    }
    .events dt{
        font-weight: bold;
        float: left;
        width: 80px;
        text-align: right;
    }
    .events dd{
        color: #333;
        min-width: 100px;
        height: 100%;
        margin-left: 80px;
        display: -webkit-box;
        -webkit-box-orient: vertical;
        -webkit-line-clamp: 3;
        overflow: hidden;
    }
</style>
</head>
<body class="oa-mw-page">

    <div class="oa-body-container">
        
        <div class="oa-project-title">
            <!-- <h2>日程展示</h2> -->
        </div>      

        <div class="oa-pd20 oa-pdb0">
            <a class="oa-button oa-button--primary oa-button--blue" id="addSysPlan" href="#" onclick="addCalendar();">添加日程</a>
            <div class="oa-mgb10 oa-fl">
                <div class="oa-label">日期：</div>
                <div class="oa-input-wrap oa-mgl100 oa-input-wrap--ib">
                    <input type="text" name="theDate"  class="oa-input date"  value="${currentViweDate}" />
                </div>
            </div>
            <div class="oa-fl oa-mgb10 oa-mgh20">
                <a class="oa-button oa-button--primary oa-button--blue" id="goToTheDate" href="#">跳转到指定日期</a>
            </div>
        </div>

        <div class="oa-pd20">
            <div id='calendar'></div>
        </div>
    </div>
    

</body>
</html>


