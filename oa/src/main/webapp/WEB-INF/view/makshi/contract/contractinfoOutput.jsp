<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
	<title>合同产值统计</title>
	<%@include file="/commons/include/get.jsp"%>
	<%@ taglib prefix="hotent" uri="http://www.jee-soft.cn/paging" %>

	<f:link href="Aqua/css/ligerui-all.css" ></f:link>
    <f:link href="tree/zTreeStyle.css" ></f:link>
    <script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
    
     <script type="text/javascript" src="${ctx}/js/lg/newligerui.all.js" ></script>
     <script type="text/javascript" src="${ctx}/js/tree/jquery.ztree.js"></script>
    <script type="text/javascript" src="${ctx}/js/lg/plugins/ligerComboBox.js"></script>
    <script type="text/javascript" src="${ctx}/js/lg/plugins/htDicCombo.js"></script> 
    
    <script type="text/javascript" src="${ctx}/js/jquery/jquery.form.js"></script>
    <script type="text/javascript" src="${ctx}/js/lg/plugins/ligerDrag.js" ></script>
    <script type="text/javascript" src="${ctx}/js/lg/plugins/newligerDialog.js" ></script>
    <script type="text/javascript" src="${ctx}/js/lg/plugins/ligerResizable.js" ></script>
    <%@include file="/commons/include/ueditor.jsp" %>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
    <script type="text/javascript" src="${ctx}/js/lg/util/DialogUtil.js" ></script>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/form/SelectorInit.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/ajaxgrid.js"></script>
    <script type="text/javascript" src="${ctx}/js/lg/util/DialogUtil.js" ></script>
	<%@ taglib prefix="hotent" uri="http://www.jee-soft.cn/paging" %>
	<link href="${ctx}/js/select2/select2.css" rel="stylesheet" />
	<link href="${ctx}/js/select2/select2-metronic.css" rel="stylesheet" />
	<style type="text/css">
		.form-body {
    margin-bottom: 20px;
    margin-left: 6px;}
    .form-group{margin-right: 20px;}
    .control-label{width:64px;font-size:15px;}
    .age{}
    .form-control{width:200px !important;}
    .control-label label{min-width:23px;}
    table tr th {
    font-size: 14px;
    color: #000;    
    font-weight: 600;
    }
    .executivetitle{
   	font-size: 20px !important;
    color: #000;
    font-weight: 600 !important;
    }
    .seson.hidden{
        display:none;
    }
    .month.hidden{
        display:none;
    }

    /*checkCollapse*/
    .checkCollapse-container{
        display: none;
        position: absolute;
        left: -1px;
        z-index: 999;
        width: 500px;
            border: 1px solid #e7eaf1;
        background: #fff;
        padding: 25px 20px 20px 20px;
            box-shadow: 0 0 15px 0 rgba(0,37,55,.05);
    }
    .checkCollapse-close{
        cursor: pointer;
        position: absolute;
        top: 5px;
        right: 5px;
    }
</style>
<script type="text/javascript">
    window.onload=function(){ 
    //设置年份的选择 
    var myDate= new Date(); 
    var startYear="1998";//起始年份 
    var endYear=myDate.getFullYear();//结束年份 
    var obj=document.getElementById('myYear');
    for (var i=endYear;i>=startYear;i--) 
    { 
    obj.options.add(new Option(i,i)); 
    } 
    var selectedYear = '${selectYear}';
    if(selectedYear){
    obj.options[endYear-selectedYear].selected=1;
    }
    var countType = '${countType}';
    var selectSeason = '${selectSeason}';
    var selectMonth = '${selectMonth}';
    var department = '${department}';
    if(department){
        $("select[name='department']").val(department);
    }
    if(countType){
        $("select[name='countType']").val(countType);
    }
    if(selectSeason){
        $("select[name='selectSeason']").val(selectSeason);
        $(".month").addClass("hidden");
        $(".seson").removeClass("hidden")
    }
    if(selectMonth){
        $("select[name='selectMonth']").val(selectMonth);
        $(".seson").addClass("hidden");
        $(".month").removeClass("hidden");
    }
    } 
</script>
</head>
<body class="oa-mw-page">

    <div id="oa_list_title" class="oa-mgb10 oa-project-title">
        <h2 class='executivetitle'>${selectYear}年 <c:if test="${department!=null}">${department}</c:if><c:if test="${department==null}">各部门</c:if>合同产值表</h2>
    </div>

	<div class="col-md-12 row" style="padding: 20px">
		<div class="oa-fl1 oa-mgb10 oa-mgh20">
			 <div class="oa-fl oa-mgb10" style="margin-right:10px;">
                <div class="oa-input-wrap">
					<select id="tabStatics" name="tabStatics" class="form-control oa-select" style="display: inline-block;width: 186px !important;    margin: 3px;    height: 21px !important;" >
						<option value="1" selected="selected" data-url="/makshi/contract/contractinfo/singingRate.ht">报表统计</option>
						<option value="2" data-url="/makshi/contract/contractinfo/customers.ht">深水咨询近年最大客户</option>
						<option value="3" data-url="/makshi/contract/contractinfo/supplier.ht">深水主要供应商</option>
					</select>
                </div>
            </div>
			<button class="oa-button oa-button--primary oa-button--blue" onclick='window.parent.frames.addToTab("/makshi/contract/contractinfo/singingRate.ht","合同签订归档统计表","10000011850001","/images/transparent.png");'>合同签订归档统计表</button>
	       	<button class="oa-button oa-button--primary oa-button--blue" onclick='window.parent.frames.addToTab("/makshi/contract/contractinfo/monthlyCollection.ht","各部门月收款进度表","10000011850002","/images/transparent.png");'>各部门月收款进度表</button>
	       	<button class="oa-button oa-button--primary oa-button--blue active" onclick='window.parent.frames.addToTab("/makshi/contract/contractinfo/output.ht","合同产值统计","20000001750803","/images/transparent.png");'>合同产值统计</button>
        </div>
	</div>
    <div id="oa_list_search" class="oa-pdb10 oa-mgh20">
        <form id="searchForm" method="post" action="output.ht" class="oa-clear">
            <input type="hidden" value="" name="contractTypes"/>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">合同类型</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input type="text" class="oa-input oa-checkCollapse">
                </div>
            </div>

            <div class="oa-fl oa-mgb10">
                <div class="oa-label">部门</div>
                <div class="oa-select-wrap oa-mgl100">
                    <select class="oa-select" name="department">
                        <option value = "">请选择</option>
                        <option value = "河道管养部">河道管养部</option>
                        <option value = "环境事业部">环境事业部</option>
                        <option value = "综合部">综合部</option>
                        <option value = "招标代理部">招标代理部</option>
                        <option value = "咨询部">咨询部</option>
                        <option value = "水保部">水保部</option>
                        <option value = "运维部">运维部</option>
                        <option value = "监理部">监理部</option>
                        <option value = "办公室">办公室</option>
                        <option value = "工程部">工程部</option>
                    </select>
                </div>
            </div>

            <div class="oa-fl oa-mgb10">
                <div class="oa-label">统计类型</div>
                <div class="oa-select-wrap oa-mgl100">
                    <select class="oa-select" name="countType">
                        <option value = "1">年产值</option>
                        <option value = "2">季度产值</option>
                        <option value = "3">月产值</option>
                    </select>
                </div>
            </div>

            <div class="oa-fl oa-mgb10">
                <div class="oa-label">时间(年份)</div>
                <div class="oa-input-wrap oa-mgl100">
                    <select class="oa-select" id="myYear" name="selectYear"></select> 
                </div>
            </div>


            <div class="oa-fl oa-mgb10 seson hidden">
                <div class="oa-label">季度</div>
                <div class="oa-input-wrap oa-mgl100">
                    <select class="oa-select"  name="selectSeason">
                        <option value = "">请选择季度</option>
                        <option value = "1">第一季度</option>
                        <option value = "2">第二季度</option>
                        <option value = "3">第三季度</option>
                        <option value = "4">第四季度</option>
                    </select>
                </div>
            </div>

            <div class="oa-fl oa-mgb10 month hidden">
                <div class="oa-label">月份</div>
                <div class="oa-input-wrap oa-mgl100">
                    <select class="oa-select"  name="selectMonth">
                        <option value = "">请选择月份</option>
                        <option value = "1">一月</option>
                        <option value = "2">二月</option>
                        <option value = "3">三月</option>
                        <option value = "4">四月</option>
                        <option value = "5">五月</option>
                        <option value = "6">六月</option>
                        <option value = "7">七月</option>
                        <option value = "8">八月</option>
                        <option value = "9">九月</option>
                        <option value = "10">十月</option>
                        <option value = "11">十一月</option>
                        <option value = "12">十二月</option>
                    </select>
                </div>
            </div>
            <div class="oa-fl oa-mgb10 oa-mgh20">
                <button id="outputSeach" type="button" class="oa-button oa-button--primary oa-button--blue">统计</button>
            </div>
        </form>
    </div>
    
    <div class="oa-pdb20 oa-mgh20">
        <div id="oa_list_content" class="oa-table-scroll-wrap">
            <c:set var="startNum" value="${(pageBeangoodsoutputItem.currentPage)*pageBeanoutputItem.pageSize}"></c:set>
            <display:table export="true" name="outputlist" id="outputItem" requestURI="#" sort="external" cellpadding="1" cellspacing="1"  class="oa-table--default oa-table--nowrap">
            <c:set var="startNum" value="${startNum+1}"></c:set>
                <display:column title="序号">${startNum}</display:column>
                <display:column title="合同类型" class="w30">${outputItem.contractType}</display:column>
                <display:column title="合同编号" class="w30"><a href="get.ht?id=${outputItem.id}">${outputItem.contractNum}</a></display:column>
                <display:column title="合同名称" class="w30">${outputItem.contractName}</display:column>
                <display:column title="总投资" class="w30">
                	<fmt:formatNumber type="number" value="${outputItem.totalInvestment}" pattern="0.00" maxFractionDigits="2"/> 
                </display:column>
                <c:if test="${countType==1}">
                    <display:column title="年产值" class="w30">
                        <fmt:formatNumber type="number" value="${outputItem.yearPut}" pattern="0.00" maxFractionDigits="2"/> 
                    </display:column>
                </c:if>
                <c:if test="${countType==2}">
                    <c:if test="${selectSeason==1}">
                        <display:column title="第一季度产值" class="w30">
                            <fmt:formatNumber type="number" value="${outputItem.sesonOne}" pattern="0.00" maxFractionDigits="2"/> 
                        </display:column>
                    </c:if>
                    <c:if test="${selectSeason==2}">
                        <display:column title="第二季度产值" class="w30">
                            <fmt:formatNumber type="number" value="${outputItem.sesonTwo}" pattern="0.00" maxFractionDigits="2"/> 
                        </display:column>
                    </c:if>
                    <c:if test="${selectSeason==3}">
                        <display:column title="第三季度产值" class="w30">
                         <fmt:formatNumber type="number" value="${outputItem.sesonThree}" pattern="0.00" maxFractionDigits="2"/> 
                        </display:column>
                    </c:if>
                    <c:if test="${selectSeason==4}">
                        <display:column title="第四季度产值" class="w30">
                             <fmt:formatNumber type="number" value="${outputItem.sesonFour}" pattern="0.00" maxFractionDigits="2"/> 
                        </display:column>
                    </c:if>
                </c:if>
                <c:if test="${countType==3}">
                    <display:column title="月产值" class="w30">
                        <fmt:formatNumber type="number" value="${outputItem.theOutput}" pattern="0.00" maxFractionDigits="2"/> 
                    </display:column>
                </c:if>
                <display:column title="甲方" class="w30">${outputItem.firstParty}</display:column>
                <display:column title="合同归属部门" class="w30">${outputItem.department}</display:column>
                <display:column title="项目总监" class="w30">${outputItem.projectDirector}</display:column>
                <display:column title="项目负责人" class="w30">${outputItem.projectLeader}</display:column>
                <display:column title="合同经手人" class="w30">${outputItem.contractHandler}</display:column>
            </display:table>
        </div>          
        <hotent:paging tableId="outputItem"/>
    </div>

</body>
<script type="text/javascript">

    $.fn.checkCollapse = function(options){
        var $_this = $(this);
        var $html = $('<div class="checkCollapse-container"><div class="checkCollapse-content"></div><div class="checkCollapse-close oa-button-label">关闭</div></div>');

        function render(data){
            var temp = "";
            for(var i = 0 ; i < data.length; i++){
                var checkArr = options.checkdata && options.checkdata.split(",");
                var flag = false;

                for(var j = 0; j < checkArr.length; j++){
                    if(checkArr[j] == data[i]){
                        flag = true;
                    }
                }
                if(flag){
                    temp += '<label><input class="oa-middle" name="contractType" type="checkbox" checked="checked" value=' + data[i] + ' /><span class="oa-middle">' + data[i] + '</span></label>'; 

                }else{
                    temp += '<label><input class="oa-middle" name="contractType" type="checkbox" value=' + data[i] + ' /><span class="oa-middle">' + data[i] + '</span></label>'; 

                }
            }

            return temp;
        }

        $html.find(".checkCollapse-content").html(render(options.data));

        $_this.parent().append($html);

        $(this).on("click", function(){
            $html.fadeIn();
        });

        $html.on("click", ".checkCollapse-close", function(e){
            $html.fadeOut();
        });

        $html.on("change", "input[type='checkbox']", function(e){
            var viewArr = [];
            $html.find("input[type='checkbox']").each(function(index, item) {
                if($(item).attr("checked") === "checked"){
                    viewArr.push($(item).val());
                }
            });


            $_this.val(viewArr.join("，"));
            $_this.animate({
                scrollLeft: 1000
            }, 500);
        });

    }

    $(function(){

    	 $("#tabStatics").change(function(){
			 var url = $("#tabStatics option:selected").data("url");
			 location.href = url;
		 });
    	 
        //  切换统计时间范围的类型
        $("select[name='countType']").on("change",function(){
            var _val = $(this).val();
            if(_val==1){
                $(".seson").addClass("hidden");$(".month").addClass("hidden");
                $("select[name='selectSeason']").val('');
                $("select[name='selectMonth']").val('');
            }
            if(_val==2){
                $(".month").addClass("hidden");
                $(".seson").removeClass("hidden");
                $("select[name='selectMonth']").val('');
            }
            if(_val==3){
                $(".seson").addClass("hidden");
                $(".month").removeClass("hidden");
                $("select[name='selectSeason']").val('');
            }
        });

        //  初始化页面数据
        $.ajax({
            type : "POST", 
            url:"/makshi/contract/contractinfo/getContractType.ht",
            dataType: "json",
            success:function(data){ 
                if(data!=null && data.length>0){
                    // 初始化checkCollapse插件
                    $(".oa-checkCollapse").checkCollapse({
                        data: data,
                        checkdata: '${contractTypes}'
                    });  
                    //  初始触发一下
                   $("input[type='checkbox']").eq(0).trigger('change');
                }
            }
        }); 
    });
    

    // 统计按钮的处理
    $("#outputSeach").on("click",function(){
        var selectedType = '';
        $("input[name='contractType']").each(function(){
            var ischecked = $(this).attr("checked");
            if(ischecked){
                selectedType = selectedType+','+$(this).val();
            }
        });
        if(selectedType.trim().length>0){
            selectedType = selectedType.substring(1,selectedType.length);
            $("input[ name='contractTypes']").val(selectedType);
        };
        $("#searchForm").submit();
    });
</script>
</html>


