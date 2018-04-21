function executeTask(taskId){
    var url="/platform/bpm/task/toStart.ht?taskId="+taskId;
    var rtn = jQuery.openFullWindow(url);
}
function searchFinishTask(taskId){
    var url="/platform/bpm/processRun/info.ht?runId="+taskId+"&prePage=myFinishedTask";
    var rtn = jQuery.openFullWindow(url);
}
function refProcessData(){
    getdb();
   // getNotReadMsg();
    getjb();
    getScheduleDay();
    getMessage();
    getDoc(100,"#noticeTab");
    getEmail();
    getDoc(3,"#newDocTab");
}
function getdb(){
    var moreTemplate =  '<div class="mtab-line txr">' +
                            '<a href="javascript:morePendingMatters()">更多...</a>' +
                        '</div>';
    var emptyTemplate = '<div class="mtab-line txr">' +
                          '当前没有记录' +
                        '</div>';

     $.ajax({
         type : "get",
         dataType: 'JSON',
         url : "/platform/bpm/task/pd_mt_db_index.ht",
         success : function(data) {
            var html        = '',
                message     = JSON.parse(data.message),
                jsonObjArr  = message.data,
                count       = message.total;

            $("#pendingMatters").html("");
                if(null == message || message == ""){
                $("#pendingMatters").html(emptyTemplate);
                return;
            }

            jsonObjArr.splice(3, 2);
            $.each(jsonObjArr, function (i, item){  
                var date =  new Date(item.createTime.time);
                date     =  date.format('yyyy-MM-dd hh:mm:ss');
                html     += '<div class="mtab-line">' +
                                '<a href="javascript:executeTask('+item.id+')" class="mtab-title">' + item.subject + '</a>' +
                                '<span class="mtab-aside">' + item.creator +  '</span>' +
                            '</div>';
            });  
            if(count == 0){html += emptyTemplate;}
            if(count > 3){html += moreTemplate;}
            $("#pendingMatters").html(html);
            $("#db_span_show").html("我的待办("+count+")");
           // menudbResetHtml(count);
         }
     });
}

//获取未读消息数量
function getNotReadMsg(){
     $.ajax({
         type : "get",
         dataType: 'JSON',
         url : "/platform/system/messageReceiver/getNotReadCount.ht",
         success : function(data) {
            var html        = '',
                message     = JSON.parse(data.message),
                count       = message.total;
            menuMsgResetHtml(count);
            $("#msg").html(count);
         }
     });
}
function getMessage(){
//    var moreTemplate =  '<div class="mtab-line txr">' +
//                            '<a target="_blank" href="/platform/system/messageReceiver/list.ht?Q_receiveTime_S=1">更多...</a>' +
//                        '</div>';
	var moreTemplate =  '<div class="mtab-line txr">' +
						'<a target="_blank" href="/platform/system/messageSend/mylist.ht">更多...</a>' +
						'</div>';
    var emptyTemplate = '<div class="mtab-line txr">' +
                          '当前没有记录' +
                        '</div>';

	 $.ajax({
	     type : "get",
         dataType: 'JSON',
	     url : "/platform/system/messageReceiver/list_index.ht",
	     success : function(data) {
            var html        = '',
                message     = JSON.parse(data.message),
                jsonObjArr  = message.data,
                count       = message.total;

            $("#myMessageTab").html("");
            if(null == message || message == ""){
                $("#myMessageTab").html(emptyTemplate);
                return;
            }

            jsonObjArr.splice(3, 2);
	        $.each(jsonObjArr, function (i, item) {  
                var date =  new Date(item.sendTime.time);
                date     =  date.format('yyyy-MM-dd hh:mm:ss');
                html     += '<div class="mtab-line">' +item.subject+
                                '<span class="mtab-aside">' + item.userName + ' ' + date + '</span>' +
                            '</div>';
	          });  
            if(count == 0){html += emptyTemplate;}
            if(count > 3){html += moreTemplate;}
            $("#myMessageTab").append(html);
	     }
	 });
}
function getEmail(){
    var moreTemplate =  '<div class="mtab-line txr">' +
                            '<a target="_blank" href="/platform/mail/outMail/list.ht?Q_isRead_S=0">更多...</a>' +
                        '</div>';
    var emptyTemplate = '<div class="mtab-line txr">' +
                          '当前没有记录' +
                        '</div>';

	 $.ajax({
	     type : "get",
         dataType: 'JSON',
	     url : "/platform/mail/outMail/list_index.ht",
	     success : function(data) {
            var html        = '',
                message     = JSON.parse(data.message),
                jsonObjArr  = message.data,
                count       = message.total;

            $("#myEMailTab").html("");
            if(null == message || message == ""){
                $("#myEMailTab").html(emptyTemplate);
                return;
            }
            jsonObjArr.splice(3, 2);
            $.each(jsonObjArr, function (i, rowdata) {  
                 var date =  new Date(rowdata.mailDate.time);
                    date     =  date.format('yyyy-MM-dd hh:mm:ss');
                    html     += '<div class="mtab-line">' +
                                    '<a target="_blank" href="/platform/mail/outMail/get.ht?type=index&mailId='+item.mailId+'&amp;outMailSetId='+item.setId+'" class="mtab-title">' + item.title + '</a>' +
                                    '<span class="mtab-aside">' + item.userName + ' ' + date + '</span>' +
                                '</div>';
            });  
	        if(count == 0){html += emptyTemplate;}
            if(count > 3){html += moreTemplate;}
	        $("#myEMailTab").append(html); 
	     }
	 });
	}
function getDoc(type, tab){
    var moreTemplate =  '<div class="mtab-line txr">' +
                            '<a target="_blank" href="/makshi/doc/docinfo/getfilelist.ht?type='+type+'">更多...</a>' +
                        '</div>';
    var emptyTemplate = '<div class="mtab-line txr">' +
                          '当前没有记录' +
                        '</div>';

	 $.ajax({
	     type : "get",
         dataType: 'JSON',
	     url : "/makshi/doc/docinfo/getfilelistindex.ht?type=" + type,
	     success : function(data) {
            var html        = '',
                message     = JSON.parse(data.message),
                jsonObjArr  = message.data,
                count       = message.total;

            $(tab).html("");
            if(null == message || message == ""){
                $(tab).html(emptyTemplate);
                return;
            }

            jsonObjArr.splice(3, 2);
	        $.each(jsonObjArr, function (i, item) {  
                var date   =  new Date(item.createtime.time);
                    date   =  date.format('yyyy-MM-dd hh:mm:ss');
                    html   += '<div class="mtab-line">' +
                                    '<a target="_blank" href="/makshi/doc/docinfo/filedetail.ht?type=index&id='+ item.dfid +'" class="mtab-title">' + item.title + '</a>' +
                                    '<span class="mtab-aside">' + date + '</span>' +
                                '</div>';
	        });  
            if(count == 0){html += emptyTemplate;}
            if(count > 3){html += moreTemplate;}
            $(tab).append(html);
	     }
	 });
}
function getCurrentSchedule(selectDate){
	var url="/platform/system/sysPlan/my_current_schedule.ht";
	if(null!=selectDate && selectDate!=""){
		url+="?selectDate="+selectDate;
	}
	 $.ajax({
	     type : "get",
	     url : url,
	     async:false,
	     success : function(data) {
	    	 $("#mySchedule").html("");
	         var json = eval('(' + data + ')');
	         var html="";
	         var message=json.message;
	         if(null==message || message==""){
	        	 return;
	         }
	         jsonObjArr=eval('(' + message + ')');
	         var userId=$('#userId', parent.document).val();
	         if(jsonObjArr.length>0){
	        	 $.each(jsonObjArr, function (i, item) {  
	        		 var projectName=item.projectName;
	        		 var title=item.taskName;
	        		 if(title>30){
	        			 title=title.substring(0,30)+"...";
	        		 }
	        		 if(item.chargeId!=userId){
	        			 html+='<li><a target="_blank" href="/platform/system/sysPlan/participantToExchange.ht?type=participant&id='+item.id+'"><dl><dt>'+projectName+'</dt><dd>'+title+'</dd></dl></a></li>';
	        		 }else{
	        			 html+='<li><a target="_blank" href="/platform/system/sysPlan/participantToExchange.ht?type=participant&id='+item.id+'"><dl><dt>'+projectName+'</dt><dd>'+title+'</dd></dl></a></li>';
	        		 }
	        		 
	        	 });
	        	 $("#mySchedule").html(html);
	         }
	     }
	 });
}

/**
 * 合并两个传入的数组，并且分别给两个数组添加state标志，返回合并后的对象数组
 * @param  {[Array]} arr1 [对象数组]
 * @param  {[Array]} arr2 [对象数组]
 * @return {[Array]}      [对象数组]
 */
function mergeJsonData(arr1, arr2){

    $.each(arr1, function(index, item) {
        if(item){
            if(Object.prototype.toString.call(item) === '[object Object]'){
                item.state = 0;
            }
        }
    });

    $.each(arr2, function(index, item) {
        if(item){
            if(Object.prototype.toString.call(item) === '[object Object]'){
                item.state = 1;
            }
        }
    });

    return arr1.concat(arr2);
}

function getScheduleDay(){
	 var jsonObjArr=null;
   var jsonFinishedArr = null;
	 $.ajax({
	     type : "get",
	     url : "/platform/system/sysPlan/my_scheduleDay_index.ht",
	     async:false,
	     success : function(data) {
	         var json = eval('(' + data + ')');
	         var message=json.message;
	         if(message!=null && message!=""){
	        	 var jsonObj=eval('(' + message + ')');
	        	 jsonObjArr=jsonObj.notfinish;
                jsonFinishedArr = jsonObj.finish;
                jsonObjArr = mergeJsonData(jsonObjArr, jsonFinishedArr);
	         }
	     }
	 });
	 if(jsonObjArr!=null && jsonObjArr.length>0){
		 $('#ca').calendar({
			 width: 302,
			 height: 170,
			 data:jsonObjArr,
			 onSelected: function(view, date, data) {
				 date=new Date(date);
				 var dateStr=date.format("yyyy-MM-dd");
				 getCurrentSchedule(dateStr);
			 }
		 });
		 getCurrentSchedule();
	 }else{
		 $('#ca').calendar({
			 width: 302,
			 height: 170,
			 data:[
		            {
		                date: '2017/1/27',
		                value: '春节'
		            }
		     ],
			 onSelected: function(view, date, data) {
			 }
		 });
	 }
}

function menudbResetHtml(count){
	
 $("#db_span_show").html("我的待办("+count+")");
 $("#leftTree", window.parent.document).find("span").each(function(i,value){
     var html=$(this).html();
     if(html.indexOf("我的待办")>=0){
         $(this).html("我的待办("+count+")");
         return false;
     }
 });
}

function menujbResetHtml(count){
	
	 $("#jb_span_show").html("我的经办("+count+")");
	 $("#leftTree", window.parent.document).find("span").each(function(i,value){
	     var html=$(this).html();
	     if(html.indexOf("我的经办")>=0){
	         /*$(this).html("我的经办("+count+")");*/
	    	 $(this).html("我的经办");
	         return false;
	     }
	 });
	}
//未读消息数量
function menuMsgResetHtml(count){
//	 $("#db_span_show_msg").html("消息管理("+count+")");
	 $("#leftTree", window.parent.document).find("span").each(function(i,value){
		 
	     var html=$(this).html();
	     if(html.indexOf("我的消息")>=0){
	         $(this).html("我的消息("+count+")");
	         return false;
	     }
	 });
}



function getjb(){
    var moreTemplate =  '<div class="mtab-line txr">' +
                            '<a href="javascript:alreadyMatters()">更多...</a>' +
                        '</div>';
    var emptyTemplate = '<div class="mtab-line txr">' +
                          '当前没有记录' +
                        '</div>';

 $.ajax({
     type : "get",
     dataType: 'JSON',
     url : "/platform/bpm/task/pd_mt_yb_index.ht",
     success : function(data) {
            var html        = '',
                message     = JSON.parse(data.message),
                jsonObjArr  = message.data,
                count       = message.total;
            $("#completedMatters").html("");
            if(null == message || message == ""){
                $("#completedMatters").html(emptyTemplate);
                return;
            }

            jsonObjArr.splice(3, 2);
            $.each(jsonObjArr, function (i, item){  
                var date =  new Date(item.createtime.time);
                date     =  date.format('yyyy-MM-dd hh:mm:ss');
                html     += '<div class="mtab-line">' +
                                '<a href="javascript:searchFinishTask('+item.runId+')" class="mtab-title">' + item.subject + '</a>' +
                                '<span class="mtab-aside">' + item.creator +  '</span>' +
                            '</div>';
            });  
            if(count == 0){html += emptyTemplate;}
            if(count > 3){html += moreTemplate;}

            $("#completedMatters").html(html);
           // menujbResetHtml(count);
            $("#jb_span_show").html("我的经办("+count+")");
     }
 });
}
function getyqlj(){
 $.ajax({
     type : "get",
     url : "/makshi/common/friendshipLink/home_list.ht",
     success : function(data) {
           var json = eval('(' + data + ')');
           $("#friendshipLink").html("");
           var message=json.message;
           var html="";
           if(null==message || message==""){
        	   html += '<a href="javascript:void(0)" style="text-align: center; color: #8798b0;">当前没有记录。</a>';
        	   $("#friendshipLink").html(html);
        	   return;
           }
           var jsonObjArr=eval("("+message+")"); 
          $.each(jsonObjArr, function (i, rowdata) {  
               html+="<a target='_blank' href='"+rowdata.url+"' >"+rowdata.name+"</a>";  
          });  
          if(jsonObjArr.length==0){
              html += '<a href="javascript:void(0)" style="text-align: center; color: #8798b0;">当前没有记录。</a>';
         }
         $("#friendshipLink").html(html);

         $('#friendshipLink').trigger('getyqlj');
     }
 });
}
function morePendingMatters(){
 window.parent.frames.addToTab("/platform/bpm/task/pendingMatters.ht","待办/处理","10000002800009","/images/transparent.png");
}
function alreadyMatters(){
 window.parent.frames.addToTab("/platform/bpm/processRun/alreadyCompletedMatters.ht","已办事项","10000002800007","/images/transparent.png");
}

$(function() {
    var index = 0;
    var weaterTimeOut="";
    var weaterErrorNum=0;
    $('.menu').on('click', '.link', function(event) {
        var target = event.target;
        index = $(this).index();

        return false;
    });
  
    $('.mtab').each(function(index, el) {
        var $links = $(this).find('.mtab-link');
        var $items = $(this).find('.mtab-item');

        $(this).on('click', '.mtab-link',function(){
            var index = $(this).index();

            $links.removeClass('active');
            $links.eq(index).addClass('active');

            $items.removeClass('active');
            $items.eq(index).addClass('active');
        });
    });


    // ==================================================================
    // =============================天气模块=============================

    // 从cookie中获取缓存地址，默认用深圳市
    var cacheCidy = '深圳市';
    var WEATHER_KEY = 'OA_Weather';
    if(decodeURIComponent($.cookie('cacheCidy')) != 'undefined'){
        cacheCidy = decodeURIComponent($.cookie('cacheCidy'))
    }

    // 用户选取时间后重新调用获取天气函数更新数据
    $('#citySelect').on('cityChange', function(event, data){
        var city = data;

        if(!city || (cacheCidy == city)){return;}
        weaterErrorNum=0;
        cacheCidy = city;
        $.cookie('cacheCidy', cacheCidy);
        changeLocal(city);
    });

    new Vcity.CitySelector({input:'citySelect'});

    function getWeather(data){
        var weatherData = data.results[0].weather_data;
        var city = data.results[0].currentCity;
        var todayData = weatherData[0];
        var $weatherList = $('.weather-right');
        var $today = $('.weather-today');
        var i, html = '';

        var todayHtml = '<div class="today-info">' +
                '<p class="today-week">'+ todayData.date.split(" ")[0] +'</p>' +
                '<p>'+ todayData.weather +'</p>' +
                '<p>'+ todayData.wind +'</p>' +
                '<p>'+ todayData.temperature +'</p>' +
            '</div>' +
            '<div class="today-icon"><img src="' + todayData.dayPictureUrl + '" alt=""></div>';

        for(i = 1; i <= 3; i++){
            html += '<div class="item">' +
                        '<div class="weather-icon"><img src="' + weatherData[i].dayPictureUrl + '" alt=""></div>' +
                        '<p class="weather-week">' + weatherData[i].date + '</p>' +
                        '<p class="weather-temper">' + weatherData[i].temperature + '</p>' +
                    '</div>';
        }

        $('#citySelect').val(city);
        $weatherList.html(html);
        $today.html(todayHtml);
    }

    function changeLocal(city){
        getFromNet(city);
    }

    function getFromLocal(){
        var result = localStorage.getItem(WEATHER_KEY);
        if(result){
          try{
            var temp = JSON.parse(result);
            var toDay = new Date();
            var dataDay = new Date(temp.date);
            if(equealDay(toDay, dataDay)){
                render(temp);
            }else{
                return false;
            }
          }catch(err){
            console.log('天气数据异常');
          }
        }
        return result;
    }

    function equealDay(date1, date2){
        if(date1.getDate() != date2.getDate()){
            return false;
        }
        if(date1.getMonth() != date2.getMonth()){
            return false;
        }
        if(date1.getFullYear() != date2.getFullYear()){
            return false;
        }
        return true;
    }

    function getFromNet(city){
        var url = 'http://api.jirengu.com/weather.php?city=' + encodeURIComponent(city);
        $.get(url, function(response){
        	clearTimeout(weaterTimeOut);
        	var $weatherList = $('.weather-right');
            $weatherList.html( '<div style="text-align:center;margin-top:10%;">' +'天气获取中...'+'</div>');
            try{
              render(JSON.parse(response));
              save(response);
            }catch(err){
            	console.log('天气数据异常');
            	weaterErrorNum++;
            	if(weaterErrorNum<4){
            		weaterTimeOut = setInterval(function(){
		        	  	getFromNet(city);
		        	  }, 5000);
            	}else{
                    $weatherList.html( '<div style="text-align:center;margin-top:10%;">' +'天气获取失败，请用其他方式查询。'+'</div>');
            	}
            }
        }); 
    }

    function save(data){
        localStorage.setItem(WEATHER_KEY, data);
    }

    function render(data){
        var today = new Date(data.date);
        var tomo = new Date(new Date().setDate(today.getDate()+1));
        var th = new Date(new Date().setDate(tomo.getDate()+1));

        var date = [
          today.getMonth() + 1 + '-' + today.getDate(),
          tomo.getMonth() + 1 + '-' + tomo.getDate(),
          th.getMonth() + 1 + '-' + th.getDate()
        ];

        var weatherData = data.results[0].weather_data;
        var city = data.results[0].currentCity;
        var todayData = weatherData[0];
        var $weatherList = $('.weather-right');
        var $today = $('.weather-today');
        var i, html = '';

        var todayHtml = '<div class="today-info">' +
                '<p class="today-week">'+ todayData.date.split(" ")[0] +'</p>' +
                '<p>'+ todayData.weather +'</p>' +
                '<p>'+ todayData.wind +'</p>' +
                '<p>'+ todayData.temperature +'</p>' +
            '</div>' +
            '<div class="today-icon"><img src="' + todayData.dayPictureUrl + '" alt=""></div>';

        for(i = 0; i < 3; i++){
            html += '<div class="item">' +
                        '<div class="weather-icon"><img src="' + weatherData[i].dayPictureUrl + '" alt=""></div>' +
                        '<p class="weather-week">' + weatherData[i].date.split(" ")[0] + '</p>' +
                        '<p class="weather-week">' + date[i] + '</p>' +
                        '<p class="weather-temper">' + weatherData[i].temperature + '</p>' +
                    '</div>';
        }

        $('#citySelect').val(city);
        $weatherList.html(html);
        $today.html(todayHtml);
    }

    function initWeather(){
        if(getFromLocal()){
            
        }else{
            getFromNet(cacheCidy);
        }
    }

    // 初始化调用天气接口
    initWeather();
    // ==================================================================
    // =============================天气模块=============================

    getdb();
   // getNotReadMsg();
    getjb();
    getScheduleDay();
    getMessage();
    getDoc(100,"#noticeTab");
    getyqlj();
    getEmail();
    getDoc(3,"#newDocTab");
});



/*!
 * jQuery Cookie Plugin v1.4.1
 * https://github.com/carhartl/jquery-cookie
 *
 * Copyright 2013 Klaus Hartl
 * Released under the MIT license
 */
(function (factory) {
    if (typeof define === 'function' && define.amd) {
        // AMD
        define(['jquery'], factory);
    } else if (typeof exports === 'object') {
        // CommonJS
        factory(require('jquery'));
    } else {
        // Browser globals
        factory(jQuery);
    }
}(function ($) {

    var pluses = /\+/g;

    function encode(s) {
        return config.raw ? s : encodeURIComponent(s);
    }

    function decode(s) {
        return config.raw ? s : decodeURIComponent(s);
    }

    function stringifyCookieValue(value) {
        return encode(config.json ? JSON.stringify(value) : String(value));
    }

    function parseCookieValue(s) {
        if (s.indexOf('"') === 0) {
            // This is a quoted cookie as according to RFC2068, unescape...
            s = s.slice(1, -1).replace(/\\"/g, '"').replace(/\\\\/g, '\\');
        }

        try {
            // Replace server-side written pluses with spaces.
            // If we can't decode the cookie, ignore it, it's unusable.
            // If we can't parse the cookie, ignore it, it's unusable.
            s = decodeURIComponent(s.replace(pluses, ' '));
            return config.json ? JSON.parse(s) : s;
        } catch(e) {}
    }

    function read(s, converter) {
        var value = config.raw ? s : parseCookieValue(s);
        return $.isFunction(converter) ? converter(value) : value;
    }

    var config = $.cookie = function (key, value, options) {

        // Write

        if (value !== undefined && !$.isFunction(value)) {
            options = $.extend({}, config.defaults, options);

            if (typeof options.expires === 'number') {
                var days = options.expires, t = options.expires = new Date();
                t.setTime(+t + days * 864e+5);
            }

            return (document.cookie = [
                encode(key), '=', stringifyCookieValue(value),
                options.expires ? '; expires=' + options.expires.toUTCString() : '', // use expires attribute, max-age is not supported by IE
                options.path    ? '; path=' + options.path : '',
                options.domain  ? '; domain=' + options.domain : '',
                options.secure  ? '; secure' : ''
            ].join(''));
        }

        // Read

        var result = key ? undefined : {};

        // To prevent the for loop in the first place assign an empty array
        // in case there are no cookies at all. Also prevents odd result when
        // calling $.cookie().
        var cookies = document.cookie ? document.cookie.split('; ') : [];

        for (var i = 0, l = cookies.length; i < l; i++) {
            var parts = cookies[i].split('=');
            var name = decode(parts.shift());
            var cookie = parts.join('=');

            if (key && key === name) {
                // If second argument (value) is a function it's a converter...
                result = read(cookie, value);
                break;
            }

            // Prevent storing a cookie that we couldn't decode.
            if (!key && (cookie = read(cookie)) !== undefined) {
                result[name] = cookie;
            }
        }

        return result;
    };

    config.defaults = {};

    $.removeCookie = function (key, options) {
        if ($.cookie(key) === undefined) {
            return false;
        }

        // Must not alter options, thus extending a fresh object...
        $.cookie(key, '', $.extend({}, options, { expires: -1 }));
        return !$.cookie(key);
    };

}));
