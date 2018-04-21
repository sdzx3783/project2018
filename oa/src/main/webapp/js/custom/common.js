//格式化时间
Date.prototype.format = function(format){
    var o = {
    "M+" : this.getMonth()+1, //month
    "d+" : this.getDate(), //day
    "h+" : this.getHours(), //hour
    "m+" : this.getMinutes(), //minute
    "s+" : this.getSeconds(), //second
    "q+" : Math.floor((this.getMonth()+3)/3), //quarter
    "S" : this.getMilliseconds() //millisecond
    };
    if(/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    }

    for(var k in o) {
        if(new RegExp("("+ k +")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
        }
    }
    return format;
};
//判断对象是否为空
isEmptyObject=function(obj){
    for ( var name in obj ){
        return false;
    }
        return true;
};


function isArray(obj){
    return Object.prototype.toString.call(obj) === '[object Array]';
}

//内容相同的单元格合并
jQuery.fn.rowspan = function(colIdx) { //封装的一个JQuery小插件
    return this.each(function(){
    var that=null;
            $('tr', this).each(function(row) {
                $('td:eq('+colIdx+')', this).filter(':visible').each(function(col) {
                if (that!=null && $(this).html() == $(that).html()) {
                    rowspan = $(that).attr("rowSpan");
                    if (rowspan == undefined) {
                        $(that).attr("rowSpan",1);
                        rowspan = $(that).attr("rowSpan"); 
                    }
                    rowspan = Number(rowspan)+1;
                    $(that).attr("rowSpan",rowspan);
                    $(this).hide();
                } else {
                    that = this;
                }
            });
        });
    });
};


//添加随机数
function addRomdom(idHref)
{
     var random = Math.floor(Math.random()*10000);
     var index=idHref.indexOf("?");
     var addStr="";
     if(index>0){
         addStr="&t="+random;
     }else{
         addStr="?t="+random;
     }
     var rStr=idHref+addStr;
     return rStr;
}

function getCurDateAddDayDate(day){
    var date = new Date();
    date.setTime(date.getTime()+24*60*60*1000*day);
    return date;
}


$(function(){
    /**
     * 找到当前页面的table，然后遍历所有的td，对其字符串进行截断
     * 对以下情况不予处理
     * 时间格式 td里面的元素 如果当前td里面有元素，则跳过
     */
    function subStringTableString($table, len){
        $("td", $table).each(function(index, el) {
            if($(el).hasClass('notscroll')){return;}
            if($(el).contents().length === 1
                && $.trim($(el).contents().eq(0).text()).length > len
                && ($(el).contents().eq(0)[0].nodeType === 3 || $(el).contents().eq(0)[0].nodeName.toUpperCase() === 'A')
                && !(/^\d{4}-\d{2}-\d{2}/.test($.trim($(el).contents().eq(0).text())))){

                    //  当前是个a元素
                    if($(el).contents().eq(0)[0].nodeName.toUpperCase() === 'A'){
                        var $a = $(el).contents().eq(0);
                        var text = $.trim($(el).text());
                        $a.attr('title', text);
                        var temp = $.trim($a.text()).substring(0, len);
                        $a.text(temp +　"...");  
                    }else{
                        //  将text添加到容器td上
                        var text = $.trim($(el).text());
                        $(el).attr('title', text);
                        var temp = $.trim($(el).text()).substring(0, len);
                        $(el).text(temp +　"...");                        
                    }
            }    

        });
    }
    subStringTableString($(".oa-table--default.oa-table--nowrap, .oa-table--default.oa-table--fixed")
    		.not("#dispatchItem").not("#expenseItem").not(".workHourApplicationTable"), 10);
    subStringTableString($(".oa-table--default.oa-table--nowrap#dispatchItem"), 10);

    /**
     * 将所有更新样式的table底部的分页条固定在页面底部
     */
    (function(){
        //  检测计算前提的依赖元素是否存在
        if(!($('#oa_list_content').length && $('#oa_list_content').length > 0)) return;

        function resizeTableHeight(){
            var winH     = $(window).height(),
                titleH   = $('#oa_list_title').outerHeight(true) || 0,
                operaH   = $("#oa_list_operation").outerHeight(true) || 0, //   oa_list_operation 是可选的参数，如果没有获取到则默认为0
                searchH  = $('#oa_list_search').outerHeight(true) || 0, //   oa_list_search 是可选的参数，如果没有获取到则默认为0
                pagingH  = $('#oa_list_paging').outerHeight(true) || 0,
                contentWrapH = parseInt($("#oa_list_content").parent().css("padding-top")) + parseInt($("#oa_list_content").parent().css("padding-bottom")),
                fianlH   = winH - titleH - searchH - pagingH - operaH - contentWrapH - 2;   //  这里的20是table容器的上下padding 2是border

            $('#oa_list_content').height(fianlH);
            $('.oa-table-fixed-header tbody').height(fianlH - 39);
        }

        $(window).resize(function(){
            resizeTableHeight();
        });

        $(window).on('resize.resizeTableHeight', function(e){
            resizeTableHeight();
        })
        
        $(window).trigger('resize');
    })();

});


//  精简的tab切换 
//  如下模板
// <div class="oa-tab">
//     <div class="oa-tab-head">
//         <button class="oa-tab-nav">nav1</button>
//         <button class="oa-tab-nav">nav2</button>
//     </div>

//     <div class="oa-tab-main">
//         <div class="oa-tab-item">
//             tab1
//         </div>
//         <div class="oa-tab-item">
//             tab2
//         </div>
//     </div>
// </div>

$.fn.oaTab = function(func){

    this.each(function(){
        var _self = this;
        var $this = $(this);
        var previous = 0;

        $this.$head = $this.find('.oa-tab-head');
        $this.$main = $this.find('.oa-tab-main');
        $this.$navs = $this.$head.find('.oa-tab-nav');
        $this.$items = $this.$main.find('.oa-tab-item');

        $this.$items.removeClass('active');

        $this.$head.on('click', '.oa-tab-nav', clickToggleNav);

        $this.$main.on('navToggle', switchTab);

        $this.$main.trigger('navToggle', previous);

        function clickToggleNav(e){
            e.preventDefault();
            e.stopPropagation();

            var index = $(this).index();
            $this.$main.trigger('navToggle', index);
        }

        function switchTab(e, index){
            $this.$navs.eq(_self.previous).removeClass('active');
            $this.$navs.eq(index).addClass('active');

            $this.$items.eq(_self.previous).removeClass('active');
            $this.$items.eq(index).addClass('active');

            _self.previous = index;
            func && func(index);
        }
        
    });

};


(function($, _win, _doc, undefined){

    // <div class="oa-accordion">
    //         <div class="oa-accordion__title">查询条件</div>
    //         <div class="oa-accordion__content">
    //              要隐藏的内容放这
    //         </div>
    // </div>
    
    // 这样调用
    // $('.oa-accordion').oaAccordion({collapse: true});
    // 如果你使用的页面没有人员选择器组件，那么传入collapse: true

    function OaAccordion(element, options){
        var that = this;
        this.$element = $(element);

        this.$element.on('click', '.oa-accordion__title', function(e){
            var $title = $(this);
            var $content = $(this).next('.oa-accordion__content');

            if($content.hasClass('active')){
                $title.removeClass('active')
                $content.removeClass('active')
            }else{
                $title.addClass('active')
                $content.addClass('active')
            }

            //  触发table的高度计算
            $(window).trigger('resize.resizeTableHeight');

        });

        if(!options.collapse){
            setTimeout(function(){
                that.$element.find('.oa-accordion__title').trigger('click');
            }, 10);            
        }

    }

    $.prototype.oaAccordion = function(options){

        return this.each(function(){
            new OaAccordion(this, options);
        });
    }

})(jQuery, window, document);

// 人员选择输入提示
(function($, _win, _doc, undefined){

    function getResult(name, callback){
        var url = encodeURI('/platform/system/sysUser/getUserByName.ht?Q_fullname_SL=' + name + '&page=1&pageSize=15');

        $.get(url, function(data){
            if(data){
                callback && callback(data);
            }else if(!isArray(data)){
                alert('服务器返回人员列表数据出错(要求为数组类型)，请联系管理员');
            }
        });
    }

    //  渲染人员数组
    function renderPeopleList(arr){
        var i = 0, len = arr.length, html = null;

        if(!!len){
            html = '<ul class="oa-search-list">';
            for(; i < len; i++){
                html += '<li class="oa-search-item" data-id="'+ arr[i].userid +'"><label>'+ arr[i].username +'</label><span>'+ arr[i].orgName +'</span></li>';
            }
            html += '</ul>';
        }

        return html;
    }

    // 显示人员结构
    function showPeopleList($input, html){
        var $ul    = $(html);
        var x      = $input.offset().left;
        var y      = $input.offset().top;
        var height = $input.outerHeight();
        var width  = $input.outerWidth();
        
        if($ul.find('li').length){
            $ul.css('width', width);
            $input.parent('div').find('.oa-search-list').remove();
            $input.after($ul.show());
        }else{
            $input.parent('div').find('.oa-search-list').remove();
        }
    }

    $(function(){

        var $input = $('.user-search');

        $input.parent('div').css('position', 'relative');
        $input.parent('div').find('a.user-search').remove();
        $input.attr('readonly', false);

        $('body').on('input', '.user-search', function(e){
            var $this      = $(this);
            var inputValue = $.trim($this.val());
                    
            $this.parent().find('input[type="hidden"]').val('');

            getResult(inputValue, function(data){
                if(isArray(data)){
                    var list = renderPeopleList(data);
                    showPeopleList($this, list);
                }
            });
        });   

        $('body').on('click', '.oa-search-item', function(e){
            var $this   = $(this);
            var id      = $this.attr('data-id');
            var name    = $this.find('label').text();
            var $parent = $this.parent('.oa-search-list').parent('div');

            $parent.find('input[type="hidden"]').val(id);
            $parent.find('input.user-search').val(name);
            $this.parent('.oa-search-list').remove();

            return false;
        });   

        // 这里需要先于提交按钮执行，如何做到？
        $('body').on('blur', '.user-search', function(e){
            var $this = $(this);
            var id = $this.parent().find('input[type="hidden"]').val();
            if(!id){$this.val('');}

            $('.oa-search-list').fadeOut(500);

            return false;
        })


    });

})(jQuery, window, document);


$(function(){
    // 所有更新过样式页面 输入框回车查询功能
    $('body').on('keypress', '#oa_list_search input', function(e){
        if(e.keyCode === 13){
            var $buttons = $(this).parents('form').find('button');

            $buttons.each(function(index, item){
                if($(item).text() === '查询'){
                    $(item).click();
                    return false;
                }
            });
        }

    });
});


// 阿拉伯数字金额转换为中文金额
function convertCurrency(money) {
  //汉字的数字
  var cnNums = new Array('零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖');
  //基本单位
  var cnIntRadice = new Array('', '拾', '佰', '仟');
  //对应整数部分扩展单位
  var cnIntUnits = new Array('', '万', '亿', '兆');
  //对应小数部分单位
  var cnDecUnits = new Array('角', '分', '毫', '厘');
  //整数金额时后面跟的字符
  var cnInteger = '整';
  //整型完以后的单位
  var cnIntLast = '元';
  //负数
  var cnMinus = '负';
  //最大处理的数字
  var maxNum = 999999999999999.9999;
  //金额整数部分
  var integerNum;
  //金额小数部分
  var decimalNum;
  //输出的中文金额字符串
  var chineseStr = '';
  //分离金额后用的数组，预定义
  var parts;
  var isMinus = false;
  if (money == '') { return ''; }
  money = parseFloat(money);
  if (money >= maxNum) {
    //超出最大处理数字
    return '';
  }
  if (money == 0) {
    chineseStr = cnNums[0] + cnIntLast + cnInteger;
    return chineseStr;
  }
  //当金额小于0时
  if(money < 0) {
	  money = Math.abs(money);
	  isMinus = true;
  }
  //转换为字符串
  money = money.toString();
  if (money.indexOf('.') == -1) {
    integerNum = money;
    decimalNum = '';
  } else {
    parts = money.split('.');
    integerNum = parts[0];
    decimalNum = parts[1].substr(0, 4);
  }
  //获取整型部分转换
  if (parseInt(integerNum, 10) > 0) {
    var zeroCount = 0;
    var IntLen = integerNum.length;
    for (var i = 0; i < IntLen; i++) {
      var n = integerNum.substr(i, 1);
      var p = IntLen - i - 1;
      var q = p / 4;
      var m = p % 4;
      if (n == '0') {
        zeroCount++;
      } else {
        if (zeroCount > 0) {
          chineseStr += cnNums[0];
        }
        //归零
        zeroCount = 0;
        chineseStr += cnNums[parseInt(n)] + cnIntRadice[m];
      }
      if (m == 0 && zeroCount < 4) {
        chineseStr += cnIntUnits[q];
      }
    }
    chineseStr += cnIntLast;
  }
  //小数部分
  if (decimalNum != '') {
    var decLen = decimalNum.length;
    for (var i = 0; i < decLen; i++) {
      var n = decimalNum.substr(i, 1);
      if (n != '0') {
        chineseStr += cnNums[Number(n)] + cnDecUnits[i];
      }
    }
  }
  if (chineseStr == '') {
    chineseStr += cnNums[0] + cnIntLast + cnInteger;
  } else if (decimalNum == '') {
    chineseStr += cnInteger;
  }
  if(isMinus) chineseStr = cnMinus + chineseStr;
  return chineseStr;
}