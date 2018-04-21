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
    var calendar;
    var calendarObj;
    $(function() {
        var queryDataUrl = __ctx + "/platform/system/sysPlan/listByChargeAndMonth.ht";
        var openPlanUrl = __ctx + "/platform/system/sysPlan/exchange.ht?type=charge";
        var queryParams={
                queryDataUrl:queryDataUrl,
                openPlanUrl:openPlanUrl
            };
        //开始创建控件
        calendarObj = new FullcalendarControl();
        calendar = calendarObj.renderTo("calendar",{queryParams:queryParams});
        
        //初始化跳转事件和有指定的视图日期
        initGoToTheDate();
        var dateType=getCookie("dateType");
        if(null!=dateType && dateType!=""){
            var nameOfCh="";
            if(dateType=="agendaDay"){
                nameOfCh="日";
            }else if(dateType=="agendaWeek"){
                nameOfCh="周";
            }else{
                nameOfCh="月";
            }
            $(".fc-button-group").find("button").each(function(){
                var txt=$(this).text();
                if(nameOfCh==txt){
                    $(this).click();
                    return;
                }
            });
        }
    });
</script>
<style>
    .oa-body-container{
        width: 900px;
        margin: 0 auto;
    }
    .oa-pd20{width:96%;}
</style>
</head>
<body class="oa-mw-page">

   <!--  <div class="oa-body-container">
        <div class="oa-project-title">
            <h2>日程展示</h2>
        </div>    --> 

        <div class="oa-pd20 oa-pdb0 oa-clear" style="margin-top:5px;">
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


        <div class="oa-pd20" style="margin-top:0px;padding:20px;">
            <div id='calendar'></div>
        </div>

    </div>
    
</body>
</html>


