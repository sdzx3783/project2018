var OA_SEARCH_TYPE_EQUAL = 3;
var searchValues = new Array();
var totalSearchValues = [];
var allOptions = [];
var sumData; // 获取后台的合计数

$(function() {
    /**
	 * 自动完成
	 */
    $( "[auto-table][auto-column]" ).autocomplete({
        source: function( request, response ) {
            var autoCompleteObj = this.element[0];
            var tabName = $(autoCompleteObj).attr('auto-table');
            var columnName = $(autoCompleteObj).attr('auto-column');
            var columnValue = request.term;
            $.post("/common/component/maishicommon", {
                "servlettype" : 'autocomplete',
                "tabName" : tabName,
                "columnName" : columnName,
                "columnValue" : columnValue
            }, function(data) {           	
                if(data != null && data.length>0){
                    response(JSON.parse(data ));
                } else {
                    response([]);
                }
            });
        }
    });


    $("#oa_condi_table").on("click", ".search-remove", function(e){
    	$(this).parents(".search-item").remove();
        $("#oa_condi_table").trigger("changeCondi");
    });

    /**
	 * 监听添加的查询条件条目数量，如果大于0则显示条目表格否则影藏
	 */
    $("#oa_condi_table").on("changeCondi", function(){
        var $this = $(this);

        if($this.find(".search-item").length > 0){
            $this.show();
        }else{
            $this.hide();
            searchCache = {};   // 清空查询缓存对象
        }
    });
});


function renderSearchItems(nameData, typeData, value,show){
    var item = '<tr class="search-item">' +
					'<td class="search-name" data-value="' + nameData.name + '">' + nameData.title + '</td>' +
					'<td class="search-type" data-value="' + typeData.name + '">' + typeData.title + '</td>' +
					'<td class="search-value" data-value="' + value + '">' + show + '</td>' +
					'<td class="search-remove">删除</td>' +
				'</tr>';


    var $container = $("#oa_condi_table");

    $container.append($(item));

    $container.trigger("changeCondi");
}

/**
 * 添加搜索
 */
var searchCache = {};

function addSearch(){
    var $container  = $("#oa_sarch_container");
    
    var $name  = $container.find("#oa_search_name");
    var $type  = $container.find("#oa_search_type");
    var $value = $container.find("#oa_search_value");

    if($name.val() != 0 && $type.val() > 0 && $value.val().length > 0){
            var name      = $.trim($name.val());
            var nameTitle = $.trim($name.find("option:selected").text());
            var type      = $.trim($type.val());
            var typeTitle = $.trim($type.find("option:selected").text());
            var show     = $.trim($value.val());
            var value     = $.trim($value.val());
            var dataSelect = $.trim($name.find("option:selected").attr("isSelectType"));
            if(dataSelect){
            	for(var i=0;i<allOptions.length;i++){
            		if(allOptions[i].typeName==dataSelect){
            			if(allOptions[i].firstName==show){
            				value=allOptions[i].fistValue;
            			}
            			if(allOptions[i].secondName==show){
            				value=allOptions[i].secondValue;
            			}
            			if(allOptions[i].thirdName==show){
            				value=allOptions[i].thirdValue;
            			}
            		}
            	}
            }

            var nameData = {
                name: name,
                title: nameTitle
            };

            var typeData = {
                name: type,
                title: typeTitle
            };

            // 这里做一道检查，如果查询条件没有被更新，则不生成查询列
            if(nameData.name != searchCache.name || 
                typeData.name != searchCache.type || 
                value != searchCache.value){
                renderSearchItems(nameData, typeData, value,show);
            }
            
            searchCache = {
                name: name,
                type: type,
                value: value
            }; 
    }else{
        alert("请设置完整的查询条件");
    }
}

/**
 * 简单查询操作
 */
function simpleSearch(){
	searchValues = new Array();
    var $container = $("#oa_simple_search_container");
    var $items     = $container.find(".oa-search-item");
    $items.each(function(index, item){
        // 这里需要对select元素做一下判断处理
        var $item        = $(item).find("select.oa-select,input.oa-input");
        $item.each(function(index,item){
	    	var $item = $(this);
	    	var $hiddenItem = $item.prev();
	        var searchColumn = $item.attr("name");
	        var searchValue  = $item.val();
	        if($hiddenItem.val() && $hiddenItem.val().length>0){
	        	 var searchColumn = $hiddenItem.attr("name");
	 	         var searchValue  = $hiddenItem.val();
	        }
	        var dateType = $(this).attr("data-type");
	        var searchType = "3";
	        if(dateType){
	            searchType = dateType;
	        }
	        // 将字段没有填写完整的排除在外
	        if($.trim(searchValue).length > 0 ){
	            searchValues.push({
	                searchColumn: searchColumn,
	                searchType: searchType,
	                searchValue: searchValue
	            });
	        }
        });
    });
    if(searchValues.length > 0){
        $.each(searchValues, function(index, item) {
            totalSearchValues.push(item);
        });
    }
}

function totalSearch(){
	totalSearchValues = [];
    simpleSearch();
    tableWhereSearch();

    $('#tb_common_show').bootstrapTable('resetSearch',JSON.stringify(totalSearchValues));
   /* $(".oa-condi-head").click(); */
}

/**
 * 搜索列
 */
function tableWhereSearch(){
    searchValues = new Array();
    var $searchItemsWrap = $("#oa_condi_table");
    var $items = $searchItemsWrap.find(".search-item");

    $items.each(function(index, item) {
        var searchColumn = $(item).find('.search-name').attr("data-value");
        var searchType  = $(item).find('.search-type').attr("data-value");
        var searchValue   = $(item).find('.search-value').attr("data-value");

        if(searchColumn != null && searchColumn.length > 0 && searchValue != null && searchValue.length > 0){
            searchValues.push({
                searchColumn: searchColumn,
                searchType: searchType,
                searchValue: searchValue
            });
        }
    });
    
    if(searchValues.length > 0){
        $.each(searchValues, function(index, item) {
            totalSearchValues.push(item);
        });
    }
}
/**
 * 简单查询
 */
// function tableSimpleSearch(){
// searchValues = new Array();
// $("#simpleFormSearch [date_name='condition']").each(function(obj){
// var searchColumn = $(this).attr("name");
// var searchValue = $(this).val();
// var dateType = $(this).attr("data-type");
// var searchType = "3";
// if(dateType){
// searchType = dateType;
// }
// if(searchColumn != null && searchColumn.length>0 && searchValue != null &&
// searchValue.length>0){
// searchValues.push({
// searchColumn: searchColumn,
// searchValue: searchValue,
// searchType: searchType
// });
// }
// });
// $('#tb_common_show').bootstrapTable('resetSearch',JSON.stringify(searchValues));
// }

/**
 * 表格初始化
 * 
 * @param maishiCommonTableParams
 */
function initCommonTable(maishiCommonTableParams, customCallbackFn) {
    initSearch(maishiCommonTableParams);
    var maishisn = maishiCommonTableParams.sortName;
    $.post(
    "/common/component/maishicommon",
    {
        "servlettype" : 'brootstraptable',
        "tableType" : maishiCommonTableParams.type,
        "userId" : maishiCommonTableParams.userId,
        "configType" : 'initParam',
        'tableParam': JSON.stringify(maishiCommonTableParams)
    },
    function(data) {
        if (data != null) {
         var resultDate = data;
         var sn = resultDate.sortName;
         var isExist = false;
         for(var i=0;i<resultDate.columns.length;i++) {
          if(resultDate.columns[i].field && resultDate.columns[i].field.indexOf(sn) != -1) isExist = true;
         }
         if(!isExist) {
        	 resultDate.sortName = maishisn;
        	 $.post("/common/component/maishicommon",{"servlettype" : 'brootstraptable',"tableType" : resultDate.type,"userId" : resultDate.userId,
                         "sortName" : maishisn,
                         "sortOrder" : resultDate.sortOrder,
                         "configType" : 'sort'
                     });
         }
            initCommonTableByFormatParam(resultDate);
            if(customCallbackFn) customCallbackFn();
        }
    });
}
function subStringTableString($table, len){
    $("td", $table).each(function(index, el) {
        if($(el).hasClass('notscroll')){return;}
        if($(el).contents().length === 1
            && $.trim($(el).contents().eq(0).text()).length > len
            && ($(el).contents().eq(0)[0].nodeType === 3 || $(el).contents().eq(0)[0].nodeName.toUpperCase() === 'A')
            && !(/^\d{4}-\d{2}-\d{2}/.test($.trim($(el).contents().eq(0).text())))){

                // 当前是个a元素
                if($(el).contents().eq(0)[0].nodeName.toUpperCase() === 'A'){
                    var $a = $(el).contents().eq(0);
                    var text = $.trim($(el).text());
                    $a.attr('title', text);
                    var temp = $.trim($a.text()).substring(0, len);
                    $a.text(temp +　"...");  
                }else{
                    // 将text添加到容器td上
                    var text = $.trim($(el).text());
                    $(el).attr('title', text);
                    var temp = $.trim($(el).text()).substring(0, len);
                    $(el).text(temp +　"...");                        
                }
        }    

    });
}
/**
 * 初始化搜索列
 * 
 * @param maishiCommonTableParams
 */
function initSearch(maishiCommonTableParams){
    var columns = maishiCommonTableParams.columns;
    if(columns != null && columns.length>0){
        for(var i=0;i<columns.length;i++){
            if(columns[i] != null && columns[i].field != null){
                var dateStr = "";
                if(columns[i].dateFlag){
                    dateStr = '" columnType="'+columns[i].dateFlag+'"';
                }
               var select =  columns[i].selectType && columns[i].selectType;
               if(select =='select'){
            	   $('#oa_search_name').append(
                           '<option isSelectType='+columns[i].formatter+' value="'+columns[i].field + '"'+dateStr+'>'+(columns[i].title?columns[i].title:columns[i].field)+'</option>');
               }else{
                $('#oa_search_name').append(
                        '<option value="'+columns[i].field + '"'+dateStr+'>'+(columns[i].title?columns[i].title:columns[i].field)+'</option>');
            }
            }
        }
        // $('#formSearch
		// select[coloumn-select]').attr('onchange',"searchColumnChange(this);");
}
}
/**
 * 选择搜索列为时间时候，选择时间控件
 * 
 * @param obj
 */
function searchColumnChange(obj){
    $(obj).parent().parent().find('input').val('');

    if($(obj).find('option:selected').attr('columnType')){
        $(obj).parent().parent().find('input').datepicker({
            format : "yyyy-mm-dd",
            language : 'zh-CN',
            todayBtn : true,
            autoclose : true,
            todayHighlight : true,
            minView : 2
        });
    } else {
        $(obj).parent().parent().find('input').datepicker('destroy');
    }
}



/**
 * 初始化表格
 * 
 * @param maishiCommonTableParams
 */
function initCommonTableByFormatParam(maishiCommonTableParams) {
    var tabWidth = '';
    
    if(maishiCommonTableParams.tabWidth != null){
        tabWidth = ' style="width:'+maishiCommonTableParams.tabWidth+'" ';
    }
    $('body').append('<div class="bootstrap-table-result-container"><table id="tb_common_show"'+tabWidth+'></table></div>').addClass("my-bootstrap-table");
    var TableInit = function(params) {
        if (params.type == null) {
            alert("请输入页面的名称");
        }
        var oTableInit = new Object();
        // 初始化Table
        oTableInit.Init = function() {
            $('#tb_common_show').bootstrapTable(
                            {
                                url : (maishiCommonTableParams.sumFooter == null ? '/common/component/maishicommon?servlettype=brootstraptable&configType=search': maishiCommonTableParams.sumFooter), // 请求后台的URL（*）
                                exportUrl: '/common/component/maishicommon', // 导出文件的后台服务URL
                                exportParams: {
                                	"servlettype" : 'brootstraptable',
                                    "tableType" : params.type,
                                    "userId" : maishiCommonTableParams.userId,
                                    "configType" : 'export'
                                },
                                method : 'post', // 请求方式（*）
                                dataType : 'json', // 服务器返回的数据类型
                                toolbar : '#toolbar', // 工具按钮用哪个容器
                                striped : true, // 是否显示行间隔色
                                cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                                pagination : true, // 是否显示分页（*）
                                sortName : (maishiCommonTableParams.sortName == null ? '': maishiCommonTableParams.sortName),// 定义排序列,通过url方式获取数据填写字段名
                                sortable : true, // 是否启用排序
                                sortOrder : (maishiCommonTableParams.sortOrder == null ? '': maishiCommonTableParams.sortOrder), // 排序方式
                                queryParams : oTableInit.queryParams,// 传递参数（*）
                                sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
                                pageNumber : 1, // 初始化加载第一页，默认第一页
                                pageSize : 10, // 每页的记录行数（*）
                                pageList : [ 10, 25, 50, 100 ,500,1000], // 可供选择的每页的行数（*）
                                search : false, // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
                                trimOnSearch : false,
                                strictSearch : true,
                                showColumns : true, // 是否显示所有的列
                                showRefresh : true, // 是否显示刷新按钮
                                showFooter:(maishiCommonTableParams.showFooter? true:false),
                                minimumCountColumns : 1, // 最少允许的列数
                                clickToSelect : true, // 是否启用点击选中行
                                height : 500, // 行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                                uniqueId : maishiCommonTableParams.uniqueId != null ? maishiCommonTableParams.uniqueId : "", // 每一行的唯一标识，一般为主键列
                                columns : maishiCommonTableParams.columns,
                                reorderableColumns : true,// Set true to allow
															// the reorder
															// feature.
                                resizable : true,// Set true to allow the
													// resize in each column.
                                minWidth : 40,
                                resizeMode : (maishiCommonTableParams.resizeMode == null ? 'fit': maishiCommonTableParams.resizeMode),
                                liveDrag : true,
                                partialRefresh : true,
                                showExport : true,// set true to show export
													// button.
                                exportDataType : "all",
                                exportTypes : ['json', 'xml', 'csv', 'txt', 'sql', 'doc', 'excel'],
                                userId : maishiCommonTableParams.userId,
                                tabType : maishiCommonTableParams.type,
                                responseHandler : function(res) {// 加载服务器数据之前的处理程序，可以用来格式化数据.参数：res为从服务器请求到的数据。
                                	sumData = res.footsum;
                                    rpYearVac(res);
                                    return res;
                                },
                                getExportSearchDatas: function(){
                                	searchParams = {};
                                	totalSearchValues = [];
                                    simpleSearch();
                                    tableWhereSearch();
                                    
                                    searchParams["searchColumn"] = totalSearchValues;
                                    searchParams["tableParams"] = maishiCommonTableParams;
                                	return JSON.stringify(searchParams);
                                }
                                ,
                                onSort : function(name, order) {
                                    $.post(
                                        "/common/component/maishicommon",
                                        {
                                            "servlettype" : 'brootstraptable',
                                            "tableType" : params.type,
                                            "userId" : maishiCommonTableParams.userId,
                                            "sortName" : name,
                                            "sortOrder" : order,
                                            "configType" : 'sort'
                                        },
                                        function(data) {
                                        	
                                        });
                                    return false;
                                },
                                onReorderColumn : function(headerFields) {
                                    $.post("/common/component/maishicommon",
                                        {
                                            "servlettype" : 'brootstraptable',
                                            "tableType" : params.type,
                                            "userId" : maishiCommonTableParams.userId,
                                            "columnValue" : headerFields,
                                            "configType" : 'columnorder'
                                        },
                                        function(data) {
                                        	setTotalCommon();
                                        });
                                    return false;
                                },
                                onResizableResize : function() {
                                    var tabWidth = $('#tb_common_show').css("width");
                                    var columnWidthStr = '';
                                    $('#tb_common_show thead tr th').each(function(){
                                        var columnName = $(this).attr("data-field");
                                        var columnWidth = $(this).width()  + 'px';
                                        if(columnWidth != null && columnWidth.length>0){
                                            if(columnWidthStr.length>0){
                                                columnWidthStr = columnWidthStr + ',';
                                            }
                                            columnWidthStr = columnWidthStr + columnName + ":" + columnWidth;
                                        }
                                    });
                                    
                                    $.post("/common/component/maishicommon",
                                    {
                                        "servlettype" : 'brootstraptable',
                                        "tableType" : maishiCommonTableParams.type,
                                        "userId" : maishiCommonTableParams.userId,
                                        "columnWidth" : columnWidthStr,
                                        "tabWidth" : tabWidth,
                                        "configType" : 'columnWidth'
                                    },
                                    function(data) {
                                    });
                                    return false;
                                },
                                onColumnSwitch : function(field, checked) {
                                    var hiddenColumns = $('#tb_common_show').bootstrapTable('getHiddenColumns');
                                    var hiddenColumnStr = "";
                                    if(hiddenColumns != null && hiddenColumns.length>0){
                                        for(i=0;i<hiddenColumns.length;i++){
                                            if(hiddenColumnStr.length>0){
                                                hiddenColumnStr = hiddenColumnStr + ",";
                                            }
                                            hiddenColumnStr = hiddenColumnStr + hiddenColumns[i].field;
                                        }
                                    }
                                    $.post("/common/component/maishicommon",
                                            {
                                                "servlettype" : 'brootstraptable',
                                                "tableType" : params.type,
                                                "userId" : maishiCommonTableParams.userId,
                                                "hiddenColumn" : hiddenColumnStr,
                                                "configType" : 'hiddenColumn'
                                            },
                                            function(data) {
                                            	setTotalCommon();                                            	
                                            });
                                },
                                onLoadSuccess : function(data){
                                	setTotalCommon();
                                }
                            }
            );
        };

        // 得到查询的参数
        oTableInit.queryParams = function(params) {
            var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                limit : params.limit, // 页面大小
                offset : params.offset, // 页码
                sortName : params.sort,
                sortOrder : params.order,
                departmentname : $("#txt_search_departmentname").val(),
                tableParams : maishiCommonTableParams,
                searchColumn : totalSearchValues
            };
            return temp;
        };
        return oTableInit;
    };

    function setTotalCommon(){   	
    	var html = $(".fixed-table-footer").find("table tbody").html();
    	$("#tb_common_show").find("tbody").append(html);
    	setTimeout(function(){
        	$('#tb_common_show').bootstrapTable("resetWidth");
        },200);
    }

    // 1.初始化Table
    var oTable = new TableInit(maishiCommonTableParams);
    oTable.Init();     
}

function resizeTableHeightA(){
	var hhh = $("#oa_search_table_button_wrap").length == 0 ? 0 : ($("#oa_search_table_button_wrap").height() + 20);
	var fianlH = $(window).height() - 148 - $(".oa-condi-wrap").height() - hhh;
    $('.fixed-table-body').height(fianlH);    
}

$(window).resize(function(){
    resizeTableHeightA();
});

$(window).on('resize.resizeTableHeight', function(e){
    resizeTableHeightA();
})
setTimeout(function(){resizeTableHeightA();},500);

$(window).trigger('resize');
/**
 * 选择导出方式
 */
$(function () {
    $('#toolbar').find('select').change(function () {
        var options = $('#tb_common_show').bootstrapTable('getOptions');
        options.exportDataType = $(this).val();
        $('#tb_common_show').bootstrapTable(options);
    });
});


var rpYearVac = function (res) {
    var data = '';
    if(res.total <=0 ) return res;

    if(typeof (tempUser) == 'undefined' || tempUser == null || tempUser == '') {
        return res;
    }

    $.each(res.rows, function (index, item) {
        data += item.userId + ',';
    });

    /*$.ajax({
        async : false,
        type: "post",
        url: '/makshi/userinfo/userInfomation/yearvacs.ht',
        data: {userids : data},
        success: function (result) {
            if(typeof(result) != 'undefined' && result.code == 1) {
                $.each(res.rows, function (index, item) {
                    item['s.year_vacation'] = result[item['userId']];
                    item['year_vacation'] = result[item['userId']];
                });
            }
        }
    });*/

    return res;
}
