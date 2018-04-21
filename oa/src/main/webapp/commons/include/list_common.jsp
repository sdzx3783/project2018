<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<style>
    /**
     *  #dadfed 边框 线条的颜色 
     */
	body{
        font-family: "Microsoft YaHei" !important;
        min-width: 1000px;
	}
	
	.bootstrap-table-result-container{
		padding: 0 10px 10px 10px;
	}
    .oa-page-container{
        font-size: 14px;
    }
    .oa-main-wrap{
        margin: 0 10px;
        padding: 0;
    }

    .fixed-table-toolbar .bs-bars, .fixed-table-toolbar .search, .fixed-table-toolbar .columns {
    	margin: 6px 0;
    }
    .oa-input-wrap, .oa-select-wrap {
        width: 180px;
    }
    /**
     * 查询条件
     */
     .oa-search-item.oa-fl.oa-mgb10 {
     	margin-bottom: 6px;
     }
	 .oa-condi-item{font-size:12px;}
    .oa-condi-head{
        cursor: pointer;
        padding: 10px 0px;
        background: #3f95dc;
		 text-indent:15px;
        color: #666;
    }
    .oa-condi-head{
        color: #fff;
        cursor: pointer;
        -webkit-border-radius: 3px;
                border-radius: 3px;
        background: #0f88eb url(/images/oa_arrow_down.png) no-repeat right center;
        -webkit-background-size: 20px 20px;
                background-size: 20px 20px;
        background-position: 99% center;
    -webkit-box-shadow: 0 1px 3px 0 rgba(0,37,55,.1);
            box-shadow: 0 1px 3px 0 rgba(0,37,55,.1);
    }
    .oa-search-wrap.active .oa-condi-head{
    	padding: 6px 0;
        background: #0f88eb url(/images/oa_arrow_up.png) no-repeat right center;
        -webkit-background-size: 20px 20px;
                background-size: 20px 20px;
        background-position: 99% center;
    }
    .oa-search-wrap.active .oa-condi-main{
    	display: block;
    }
    .oa-condi-main{
        padding: 10px 0 0 0;
        display: none;
    }
    .oa-condi-navs{
        margin-bottom: 10px;
    }
    .oa-condi-item{
    	/*display: none;*/
    }
    .oa-condi-table th,
    .oa-condi-table td{
         padding: 5px 10px 5px 0;
    }
    .oa-condi-table-result{
    	display: none;
    }
    .oa-condi-table-result.active{
		display: block;
    }

    .search-table{
        width: 100%;
    }
    .search-table thead{
        background: #e9eaec;
    }
    .search-table th{
    	width: 25%;
    }
    .search-table th,
    .search-table td{
        padding: 10px 20px;
        border: 1px solid #e1e1e1;
    }
    .search-remove{
        cursor: pointer;
        color: #5bc0de;
    }

/*    .oa-simple-search-wrap ul li{
        display: inline-block;
        width: 260px;
        margin-bottom: 10px;
        margin-right: 10px;
    }*/
    .oa-simple-search-wrap .oa-input,
    .oa-simple-search-wrap .oa-select{
        width: 100%;
    }
    .oa-simple-search-wrap .oa-label{
        height: 31px;
		font-size:12px;
        line-height: 31px;
        float: left;
    }

    
    /**
     * 隐藏自定义人员 组织选择器不雅的图标
     */
    a.oa-trigger-hidden{
        display: none;
    }
    
    /**
     * 将自定义人员 组织选择器的触发按钮定位到容器上，布局限制了，暂时没有
     * 更好的方式处理，如果你有，请修改
     */
    .oa-search-item{
        position: relative;
    }
    .oa-trigger-hidden-button{
        position: absolute;
        top: 5px;
        right: 5px;
    }

    .l-box-select-inner{
        width: 179px;
        height: 200px;
    }

    /**
     * bootstrap-table 释放宽度的开关高量
     */
    .change-mode[resizeMode="overflow"] {
        color: #333;
        background-color: #e6e6e6;
        border-color: #adadad;
    }
    /* 导出按钮 */
    .export-f-control {
    	width: 200px;
    	margin-right: 20px;
    }
    .changed-export .btn-group {
    	vertical-align: top;
    }
    .oa-mgt3 {
    	margin-top: 3px;
    }
</style>

	<div class="oa-page-container">
		<div class="oa-main-wrap">
			<div id="oa_sarch_container" class="oa-search-wrap">
				<!-- 查询条件 -->
				<div class="oa-condi-wrap">
					<div class="oa-condi-head oa-clear" onclick="switchViewSearch()">
					    <div class="oa-fl">查询条件</div>
					</div>
					<div id="oa_condi_main" class="oa-condi-main">

					    <div class="oa-condi-items">
					        <div id="oa_simple_search_container" class="oa-condi-item">
								<!-- 怎么把配置的简单查询导入到这里来？ -->
								<!-- 直接移动节点位置到这里 -->
                                
					        </div>
					        <div class="oa-condi-item">
				                <table class="oa-condi-table">
				                    <thead>
				                        <tr>
				                            <th>查询字段</th>
				                            <th>查询条件</th>
				                            <th>查询内容</th>
				                            <th>操作</th>
				                        </tr>
				                    </thead>
				                    <tbody>
				                        <tr>
				                            <td>
				                                <div class="oa-select-wrap">
				                                    <select class="oa-select" id="oa_search_name">
				                                        <option value="0">请选择搜索列</option>
				                                    </select>
				                                </div>
				                            </td>
				                            <td>
				                                <div class="oa-select-wrap">
				                                    <select class="oa-select" id="oa_search_type">
				                                        <option value="0">请选择搜索列</option>
				                                        <option value="1">大于</option>
				                                        <option value="2">大于等于</option>
				                                        <option value="3">等于</option>
				                                        <option value="4">小于等于</option>
				                                        <option value="5">小于</option>
				                                        <option value="6">不等于</option>
				                                        <option value="7">模糊匹配</option>
				                                    </select>
				                                </div>
				                            </td>
				                            <td>
				                                <div class="oa-input-wrap">
				                                    <input class="oa-input" id="oa_search_value" type="text">
				                                </div>
				                            </td>
				                            <td>
				                                <button class="oa-button oa-button--primary oa-button--blue" onclick="addSearch();">增加</button>
				                                <button class="oa-button oa-button--primary oa-button--blue" onClick="totalSearch()" style="margin-left: 10px;">查询</button>
				                            </td>
				                        </tr>
				                    </tbody>
				                </table>

				                <!-- 添加的搜索条件 -->
								<table id="oa_condi_table" class="search-table oa-condi-table-result">
								    <thead>
								        <tr>
								            <th>搜索列</th>
								            <th>搜索方式</th>
								            <th>列值</th>
								            <th>操作</th>
								        </tr>
								    </thead>
								    <tbody>

								    </tbody>
								</table>
					        </div>
					    </div>
                        
                        <!-- 查询按钮     -->
                        <div class="oa-mgt3">
                            <div class="oa-result-wrap">
                                
                            </div>
                        </div>
					</div>
				</div>
				
               
			</div>
		</div>
	</div>

<!-- 工具栏模板 -->
<div id="toolbar" class="btn-group">
    <select class="export-f-control form-control pull-left">
        <option value="all">导出所有</option>
        <option value="basic">导出当前页</option>
        <option value="selected">导出选择的</option>
    </select>
    <div class="changed-export pull-left"></div>
    <div class="search" style="display: none;">
        <input class="form-control" type="text">
    </div>
</div>


<script>
	/**
	 * 默认显示简单视图 将previousIndex修改为1，则默认显示高级视图
	 * @param  {[type]} 		 index number类型
	 */
	var showSearchView = (function(){
		var previousIndex  = 0,
			$views 		   = $("#oa_sarch_container .oa-condi-item");

		$views.eq(previousIndex).show();

		return function(index){
			$views.eq(previousIndex).hide();
			$views.eq(index).show();

			previousIndex = index;
		}
	})();

	/**
	 * 切换查询条件的显示和隐藏
	 */
	function switchViewSearch(){		
        if($.trim($("#oa_simple_search").html()).length < 13){
            $("#oa_simple_search_button").hide();
            showSearchView(1);
        }

		$("#oa_sarch_container").toggleClass('active');
		var hhh = $("#oa_search_table_button_wrap").length == 0 ? 0 : ($("#oa_search_table_button_wrap").height() + 20);
		var fianlH = $(window).height() - 148 - $(".oa-condi-wrap").height() - hhh;
	    $('.fixed-table-body').height(fianlH);
	}

	/**
	 * 将每个独立页面的简单查询模块导入到list_common.jsp的简单视图中
     * 如果独立页面没有配置简单查询内容则隐藏简单查询按钮
     *
     * 这里用到了定时器是为了延后移动节点操作的队列顺序
	 */
    setTimeout(function(){
        //  将先前承载的容器隐藏掉，因为有个margin，不隐藏的话会撑起一点高度
        //  后续可以将边距设置到内容上，用css控制
        if($("#oa_simple_search_container").find("#oa_simple_search").length<=0){
        	$("#oa_simple_search").parent(".oa-head-wrap").hide();
            $("#oa_simple_search_container").prepend($("#oa_simple_search").show());
        }
    }, 0);

    /**
     * 触发自定义的人员 组织选择器
     */
    $(".oa-trigger-hidden-button").on("click", function(){
        $(this).parent(".oa-search-item").find("a.oa-trigger-hidden").click();
    });

    /**
     * 监测页面的键盘按下事件，如果是回车键则调用简单查询
     */
    $(window).on("keyup", function(e){
        if(e.keyCode === 13){
            totalSearch();
        }
    });

</script>