/**
 *  fullcalendar控件（详见:http://fullcalendar.io/docs/）。
 *  使用方法：
 *  var obj=new FullcalendarControl();
 *  obj.renderTo("divContainer",{queryParams{},setUpParams:{}});
 *  divContainer： 文档容器id
 *  queryParams:数据查询相关参数(先设置查询参数，因为设置参数中有用到查询参数)
 *  setUpParams：控件设置参数;
 * @returns {fullcalendarObj}
 * 
 * 例如：
 * 
 * 
    $(function() {
        var queryDataUrl = __ctx + "/platform/system/sysPlan/listBySubmitAndMonth.ht";
        var openPlanUrl = __ctx + "/platform/system/sysPlan/edit.ht";
        var queryParams={
                queryDataUrl:queryDataUrl,
                openPlanUrl:openPlanUrl,
                selectDate:""       //默认是没有的
            };
        var setUpParams={
                header : {
                    left : 'title',
                    center : 'prev, today, next',
                    right : 'agendaWeek,agendaDay'
                }
            };
        
        //开始创建控件
        var calendarObj = new FullcalendarControl();
        calendarObj.renderTo("calendar",{queryParams:queryParams,setUpParams:setUpParams});
    });
 * 
 */
FullcalendarControl=function(){
    {
        var _self=this;
        var queryDataUrl = __ctx + "/platform/system/sysPlan/listBySubmitAndMonth.ht";
        var openPlanUrl = __ctx + "/platform/system/sysPlan/edit.ht";

        _self.controlId="calendar";
        
        _self.controlObj=null;
        
        _self.queryParams={
            queryDataUrl:queryDataUrl,
            openPlanUrl:openPlanUrl,
            selectDate:""    
        };
        
        _self.setUpParams={
            //设置选项和回调    
            editable : false,
            //根据实际的每月天数显示日历。
            fixedWeekCount:false,
            currentTimezone : 'Asia/Beijing',
            allDayDefault : false,
            buttonText:{
                prev:'上一月',
                next:'下一月'
            },
            
            header : {
                center : 'title',
                left : 'prev,next today ',
                right : 'month,agendaWeek,agendaDay'
            },

            //  点击事项触发
            eventClick : function(event) {
                var selectType = $(".fc-state-active").text();
                var type = "month";
                if(selectType =="周"){
                    type = "agendaWeek";
                }else if(selectType =="日"){
                    type = "agendaDay";
                }


                setCookie("dateType", type, 7, "/");
                var date = _self.getCurrentViewDate();  //  获取当前日期

                _self.queryParams.currentViweDate = _self.changeDateToStr(date);


                _self.openEventSysPlan(event.planId, event.title, _self.queryParams.openPlanUrl, _self.queryParams.currentViweDate);

                //  直接用这里的event对象中的数据就可以做弹框显示了

            },
            
            eventMouseover : function(event, _event) {
                var obj = $(this);
                _self.toDoEventMouseover(obj, event, _event);
            },
            
            eventMouseout : function(event) {
                $('#js_OALayer').remove();
                //鼠标移出日程暂时未实现
            }, 

            dayClick : function(date, allDay, jsEvent, view) {
                var href = window.location.href;
                var type = jsEvent.type;
                setCookie("dateType", type, 7, "/");

                if(href.indexOf("submit") >= 0){
                    var startTime, endTime = null;

                    date          = new Date(date);
                    date          = date.format("yyyy-MM-dd");
                    
                    var srcElement=allDay.srcElement ? allDay.srcElement : allDay.target;
                    
                    var dateBetween=$(srcElement).text();
                    
                    var className=srcElement.className;
                    if(className=="fc-content" || className=="fc-time"){
                        var hourDeal=false;
                        var startStr=dateBetween.split("-")[0];
                        if(startStr.indexOf("下午")>=0 || startStr.indexOf("晚上")>=0){//下去或晚上的处理方式
                            hourDeal=true;
                        }
                        dateBetween=dateBetween.replace(new RegExp("凌晨",'gm'),'').replace(new RegExp("早上",'gm'),'')
                        .replace(new RegExp("上午",'gm'),'').replace(new RegExp("中午",'gm'),'')
                        .replace(new RegExp("下午",'gm'),'').replace(new RegExp("晚上",'gm'),'').replace(new RegExp(" ",'gm'),'');
                        startTime=date+" "+dateBetween.split("-")[0];
                        startTime=startTime.replace(new RegExp("点",'gm'),':')+":00";
                        endTime=date+" "+dateBetween.split("-")[1];
                        endTime=endTime.replace(new RegExp("点",'gm'),':')+":00";
                        
                        startTime=new Date(Date.parse(startTime.replace(/-/g,"/")));
                        endTime=new Date(Date.parse(endTime.replace(/-/g,"/")));
                        if(hourDeal){//下午则需修改成24小时制
                            startTime.setHours(startTime.getHours()+12);
                            endTime.setHours(endTime.getHours()+12);
                        }
                        startTime=startTime.format("yyyy-MM-dd hh:mm:ss");
                        endTime=endTime.format("yyyy-MM-dd hh:mm:ss");
                        
                        addCalendar(startTime,endTime);
                    }else if(type=="month"){//className=="fc-highlight" || className.indexOf("fc-day-number fc-mon")>-1){
                        startTime=date+" 00:00:00";
                        endTime=date+" 00:30:00";
                        addCalendar(startTime,endTime);
                    }else if(className=="fc-highlight" || className=="fc-content-skeleton"){
                        startTime=date+" 00:00:00";
                        endTime=date+" 00:30:00";
                        addCalendar(startTime,endTime);
                    }
                }
            },  


            selectable: true,
            selectHelper: true,
            editable: true,
            eventLimit: true, //更多
            views: {
                month:{
                    eventLimit: 5, 
                    eventLimitClick :'day'
                },
                agendaWeek: {
                    eventLimit: 5
                },
                agendaDay: {
                    eventLimit:5
                }
            },
            
            events:function(start,end,timezone,callback){ 
                var startDate = _self.changeDateToStr(start._d);
                var endDate = _self.changeDateToStr(end._d);
                $.ajax({ 
                     type:"post", 
                     url:_self.queryParams.queryDataUrl, 
                     dataType:"json", 
                     data:{
                             startDate:startDate,
                             endDate:endDate,
                             selectDate:_self.queryParams.selectDate
                          }, 
                     success:function(data){ 
                         var event = []; 
                         if(data){
                             event = data;
                         }
                         callback(event); 
                     } 
                 }); 
            } 
            
        }
    };
    
    
    /**
     * 将控件添加到div容器中。
     * 第一个参数：
     * div的容器ID
     * 第二个参数:
     * conf{
     *      queryParams:数据查询相关参数(先设置查询参数，因为设置参数中有用到查询参数)
     *      setUpParams：控件设置参数;
     * }
     */
    this.renderTo=function(divContainerId,conf){
        _self.controlId = divContainerId;
        _self.queryParams = $.extend({},_self.queryParams,conf.queryParams);
        _self.setUpParams = $.extend({},_self.setUpParams,conf.setUpParams);
        _self.controlObj = $('#'+_self.controlId).fullCalendar(_self.setUpParams);
        return _self.controlObj;
    };
    
    /**
     * 获取当前月份的时间；
     */
    this.getCurrentViewDate = function(){
         return $('#'+_self.controlId).fullCalendar('getDate') && $('#'+_self.controlId).fullCalendar('getDate')._d;
    };
    
    /**
     * 跳到指定日期的视图；
     * 
     */
    this.goToTheDate=function(date){
        $('#'+_self.controlId).fullCalendar( 'gotoDate', date );
    };
    
     /**
     * 把日期转为字符；(yyyy-mm-dd)
     */
    this.changeDateToStr = function(date){
        var dateStr = "";
        if(date){
            dateStr = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate();
        }
        return dateStr;
    };
    
    /**
     * 打开日程
     * 
     * id 主键
     * title 标题
     * url 地址
     * 
     */
    this.openEventSysPlan = function(id, title, url, currentViweDate){
        if(url.indexOf("?") > 0){
            url += "&id=" + id + "&currentViweDate=" + currentViweDate;
        }else{
            url += "?id=" + id + "&currentViweDate=" + currentViweDate;
        }

        // 只要不是日程交流 和 我参与的日程 页面就直接弹窗显示
        if(url.indexOf("charge") < 0 && url.indexOf("participant") < 0){
            layer.open({
                title: "日程详情",
              type: 2, 
              area: ['600px', '500px'],
              content: url
            }); 
        }else{
            window.location.href = url;
        }
    };

    String.prototype.repeat = function(data) {
      return this.replace(/\{\w+\}/g, function(str) {
      var prop = str.replace(/\{|\}/g, '');
      return data[prop] || '&nbsp;';
      });
    }

    function isString(str){
        return Object.prototype.toString.call(str) === '[object String]';
    }

    this.viewEventInfo = function(x, y, data){
        var temp = data.rate + '';

        if(isString(temp) && temp.indexOf('%') < 0){
            data.rate = data.rate + '%';
        }

        this.eventsHtml =  '<div id="js_OALayer" class="OALayer">' +
                            '<ul class="events">' +
                                '<li>' +
                                    '<dl>' +
                                        '<dt>项目名称：</dt>' +
                                        '<dd>{projectName}</dd>' +
                                    '</dl>' +
                                '</li>' +
                                '<li>' +
                                    '<dl>' +
                                        '<dt>日程进度：</dt>' +
                                        '<dd>{rate}</dd>' +
                                    '</dl>' +
                                '</li>' +
                                '<li>' +
                                    '<dl>' +
                                        '<dt>提交人：</dt>' +
                                        '<dd>{submitor}</dd>' +
                                    '</dl>' +
                                '</li>' +
                                '<li>' +
                                    '<dl>' +
                                        '<dt>负责人：</dt>' +
                                        '<dd>{charge}</dd>' +
                                    '</dl>' +
                                '</li>' +
                                '<li>' +
                                    '<dl>' +
                                        '<dt>开始时间：</dt>' +
                                        '<dd>{startTimeStr}</dd>' +
                                    '</dl>' +
                                '</li>' +
                                '<li>' +
                                    '<dl>' +
                                        '<dt>结束时间：</dt>' +
                                        '<dd>{endTimeStr}</dd>' +
                                    '</dl>' +
                                '</li>' +
                            '</ul>' +
                        '</div>';

        var temp = this.eventsHtml.repeat(data);

        $('body').append(temp);
        $('#js_OALayer').css({
            left: x,
            top: y
        });
    };
    
    
    this.toDoEventMouseover=function(obj,thisEvent, event){
        var sysPlan = thisEvent.sysPlan;
        if(sysPlan==null){
            return;
        } 
        var html = _self.getEventMouseoverHtml(sysPlan);
        this.viewEventInfo(event.clientX, event.clientY, sysPlan);
        return;
        $(".fc-content",obj).qtip({  
            content:{
                text:html,
                title:{
                    text:sysPlan.taskName           
                }
            },
            position: {
                at:'top left',
                target:'event',
                adjust: {
                        x:25,
                        y:15
                    }, 
                viewport:  $(window)
            },
            show: {
                effect: function() {
                    $(this).fadeTo(300, 1);
                }
            },
            hide: {
                effect: function() {
                    $(this).slideUp();
                }
            },
            style: {
              classes:'ui-tooltip-light ui-tooltip-shadow'
            }               
        }); 
    };
    
    
    this.getEventMouseoverHtml=function(sysPlan){
        var html = '';
        if(sysPlan==null){
            return html;
        }
        html = '<table class="table-detail" cellpadding="0" cellspacing="0" border="0"> ';
        html += '<tr><th width="20%">任务名称:</th><td>'+sysPlan.taskName+'</td><th width="20%">日程进度:</th><td>'+sysPlan.rate+'%</td></tr>';      
        html += '<tr><th width="20%">提交人:</th><td>'+sysPlan.submitor+'</td><th width="20%">负责人:</th><td>'+sysPlan.charge+'</td></tr>';       
        html += '<tr><th width="20%">开始时间:</th><td>'+sysPlan.startTimeStr+'</td><th width="20%">结束时间:</th><td>'+sysPlan.endTimeStr+'</td></tr>';         
        html += '</table>';
        return html;
    }
    
};

