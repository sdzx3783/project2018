<%@page import="com.hotent.core.api.util.PropertyUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"  
  pageEncoding="UTF-8" 
  import="com.hotent.platform.model.system.Resources,
        com.hotent.core.util.AppUtil,
        java.util.Properties"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="f" uri="http://www.jee-soft.cn/functions" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="skinStyle" value="${skinStyle}" />
<%
    String appName=PropertyUtil.getByAlias("appName");
%>
<head>
    <title><%=appName%></title>
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="shortcut icon" href="${ctx}/favicon.ico" type="image/x-icon" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <f:link href="Aqua/css/ligerui-all.css"></f:link>
    <f:link href="index.css"></f:link>
    <f:link href="select.css"></f:link>
    <f:link href="tree/zTreeStyle.css"></f:link>
    <f:js pre="js/lang/common" ></f:js>
    <f:js pre="js/lang/js" ></f:js>
    <link rel="stylesheet" href="${ctx}/styles/custom/all_update.css" />
    <style>
    *{
        font-family: "Microsoft YaHei", "Verdana, Arial, Helvetica, sans-serif" !important;
    }
    .l-tab-links {
        background: #eef1f7;
    }
 
    .l-tab-content {
        margin: 0;
        padding-top: 16px;
        background: #e1e5f0;
    }
 
    .l-tab-links a img {
        display: none;
    }
 
    .l-tab-links-item-close {
        top: 13px;
    }
 
    .l-tab-links li a {
        display: block;
        color: #fff;
        border-right: 0;
        border-radius: 0;
        background: #9da6bb;
    }
 
    .l-tab-links ul {
        height: 32px;
        margin-top: 10px;
        margin-left: 16px;
    }
 
    .l-tab-links li {
        line-height: 32px;
        position: relative;
        overflow: visible;
        height: 32px;
        margin: 0;
        margin: 0 12px;
        margin-right: 0;
    }
 
    .l-tab-links li.l-selected a {
        padding: 0 18px 0 6px;
        color: #333;
        border-radius: 0;
        background: #e1e5f0;
    }
 
    .l-tab-links li:after {
        position: absolute;
        top: 0;
        left: -13px;
        width: 13px;
        height: 32px;
        content: '';
        background: url(/images/label_bg.png) no-repeat left bottom;
        background-position: -26px 1px;
    }
 
    .l-tab-links li:before {
        position: absolute;
        top: 0;
        right: -13px;
        width: 13px;
        height: 32px;
        content: '';
        background: url(/images/label_bg.png) no-repeat left bottom;
        background-position: -39px 1px;
    }
 
    .l-tab-links li.l-selected:after {
        position: absolute;
        top: 0;
        left: -13px;
        width: 13px;
        height: 33px;
        content: '';
        background: url(/images/label_bg.png) no-repeat left bottom;
        background-position: 0 1px;
    }
 
    .l-tab-links li.l-selected:before {
        position: absolute;
        top: 0;
        right: -13px;
        width: 13px;
        height: 33px;
        content: '';
        background: url(/images/label_bg.png) no-repeat left bottom;
        background-position: -13px 1px;
    }
 
    .l-tab-links li.l-selected {
        z-index: 12;
        border: 0;
        background: #e1e5f0;
    }
 
    .l-tab-links-left {
        top: 9px;
    }
 
    .l-tab-links-right {
        top: 9px;
    }
 
    .l-dialog-extend {
        background-position: -51px 0 !important;
    }
 
    .l-dialog-collapse {
        background-position: -68px 0;
    }
 
    body,
    html {
        height: 100%;
    }
 
    body {
        overflow: hidden;
        margin: 0;
        padding: 0;
    }
 
    #pageloading {
        display: none;
        position: fixed;
        z-index: 99999;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background: white url('${ctx}/styles/${skinStyle}/images/loading.gif') no-repeat center;
    }
 
    #top {
        height: 80px;
        color: White;
    }
 
    #top a {
        color: white;
    }
 
    div.menuPanel {
        overflow: hidden;
        background: #478de4;
    }
 
    div.menuPanel.more {
        height: auto;
    }
 
    .l-tab-content-item.hide{
        display: none !important;
    }
    </style>
    <script src="${ctx}/js/dynamic.jsp"></script>
    <script src="${ctx}/js/jquery/jquery.js"></script>
    <script src="${ctx}/js/util/util.js"></script>
    <script src="${ctx}/js/util/form.js"></script>
    <script src="${ctx}/js/lg/base.js"></script>
    <script src="${ctx}/js/lg/plugins/ligerDialog.js"></script>
    <script src="${ctx}/js/lg/plugins/ligerDrag.js"></script>
    <script src="${ctx}/js/lg/plugins/ligerLayout.js"></script>
    <script src="${ctx}/js/lg/plugins/ligerMenu.js"></script>
    <script src="${ctx}/js/lg/plugins/ligerTab.js"></script>
    <script src="${ctx}/js/lg/plugins/ligerAccordion.js"  ></script>
    <script src="${ctx}/js/tree/jquery.ztree.js"></script>
    <script src="${ctx}/js/hotent/platform/system/sysPopupRemind/SysPopupRemindUtil.js"></script>
    <script src="${ctx}/js/custom/jquery.nicescroll.js"></script><!-- 自定义滚动条插件 -->
    <script type="text/javascript">
        if(top != this){//当这个窗口出现在iframe里，表示其目前已经timeout，需要把外面的框架窗口也重定向登录页面
              top.location = '<%=request.getContextPath()%>/platform/console/main.ht';
        }
 
        var mian_layout_;
        var tab       = null;
        var tree      = null;
        var ctxPath   = __ctx;
        var accordion = null;
        var currTabId ='home';
 
        var sysStr         = '个人办公 组织管理 流程管理 系统管理';
        var companyStr     = '公司 深水咨询 咨询部 水保部 招标代理部 环境事业部 运维部 河道管养部 综合部 监理部 办公室';
        var companyArrSort = [];
 
        var aryTreeData = null;
 
        var setting = {
                view: {showLine: false, nameIsHTML: true, showIcon: true, dblClickExpand: false},
                data: {
                    key : {name: "resName"},
                    simpleData: {enable: true,idKey: "resId",pIdKey: "parentId"}
                },
                callback: {onClick: zTreeOnClick, beforeExpand: zTreeExpand, beforeCollapse: zTreeCollapse}
        };
 
        // 左侧树形菜单收缩或者拓展时需要reize nicescroll
        function zTreeExpand(){
            setTimeout(function(){
                 $("#leftTree").getNiceScroll().resize();
            }, 100);
        }
 
        function zTreeCollapse(){
            setTimeout(function(){
                $("#leftTree").getNiceScroll().resize();
            }, 100);
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
                    menudbResetHtml(count);
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
                                        '<span class="mtab-aside">' + item.creator + '</span>' +
                                    '</div>';
                    });  
                    if(count == 0){html += emptyTemplate;}
                    if(count > 3){html += moreTemplate;}
 
                    $("#completedMatters").html(html);
                    menujbResetHtml(count);
                 }
             });
        }
        //获取批量审批数量
        function getBatchApproval(){
            
             $.ajax({
                 type : "get",
                 dataType: 'JSON',
                 url : "/platform/bpm/task/pd_mt_batch_index.ht",
                 success : function(data) {
                    var html        = '',
                        message     = JSON.parse(data.message),
                        count       = message.total;
                    menubatchResetHtml(count);
                 }
             });
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
                     /* $(this).html("我的经办("+count+")"); */
                     $(this).html("我的经办");
                     return false;
                 }
             });
         }
        
        function menubatchResetHtml(count){
            $("#leftTree", window.parent.document).find("span").each(function(i,value){
                var html=$(this).html();
                if(html.indexOf("批量审批")>=0){
                    $(this).html("批量审批("+count+")");
                    return false;
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
                 }
             });
        }
        
        //未读消息数量
       function menuMsgResetHtml(count){
             $("#leftTree", window.parent.document).find("span").each(function(i,value){
                 var html=$(this).html();
                 if(html.indexOf("我的消息")>=0){
                     $(this).html("我的消息("+count+")");
                     return false;
                 }
             });
        }
        
        /**
         *  主页发生resize时调用
         */
        function heightChanged(options){
 
            $("#leftTree").height(options.middleHeight);
            if (tab){
                var tabContent = $(".l-tab-content"),
                    h = tabContent.height();
                tabContent.height(h + options.diff);
            }
 
            if(accordion && options.middleHeight - 25 > 0){
                accordion.setHeight(options.middleHeight);
            }
 
            $("iframe").each(function(){
                var tabName = $(this).attr("name");
                if(tabName != undefined){
                    $(this).height(h + options.diff);
                }
                if(tabName == "home"){
                    $(this).attr('src', $(this).attr('src'));
                }
            });
            // 更新工作台resize后的滚动条
            $('.l-tab-content').height(h + options.diff);
            // 只给当前的item添加高度
            // $('.l-tab-content').find('.l-tab-content-item').height(h + options.diff);
        }
          
        // 返回根节点
        function getRootNodes(){
            var i, len = aryTreeData.length, nodes = [];
 
            for(i = 0; i < len; i++){
                if(aryTreeData[i].parentId == 0){
                    nodes.push(aryTreeData[i]);
                }
            }
            return nodes;
        };
 
        //  将菜单项目节点渲染成DOM元素
        function renderMenuItem(node){
            var url = node.defaultUrl ? node.defaultUrl : "";
            var str = '<a class="menuItem" id="' + node.resId + '" url="' + url + '" icon="' + node.icon + '">';
 
            str += '<span>' + node.resName + '</span></a>';
 
            return str;
        }
 
        //加载菜单面板
        function loadMenu(id, callback){
            $("#leftTree").empty();
            $("#menuPanel").empty();
            $("#company").empty();
            $(".dorpdown-layer ul").empty();
 
            //  拉取所有id子项目
            $.post("${ctx}/platform/console/getSysRolResTreeData.ht?id=" + id, function(result){
                aryTreeData = result;
                var tempArgument=arguments[0];
                if(tempArgument && typeof(tempArgument)=="string"){
                    var _obj=JSON.parse(tempArgument);
                    if(_obj.message && _obj.result==0 && _obj.message.indexOf("登录超时，请重新登录")!=-1){
                        window.location.reload(true);
                    }
                }
                //  获取根节点，加载顶部按钮菜单。
                var headers          = getRootNodes();
                var len              = headers.length;
 
                var menuContainer    = $("#menuPanel");
                var innerMenu        = $('#inner_menu');
                var companyContainer = $('#company');
                
                // 向齿轮下面硬性添加修改密码和个人资料项目
                $('.dorpdown-layer ul').append('<li><a href="${ctx}/platform/system/sysUser/modifyPwdView.ht?userId=${userId}" resid="-001" target="_blank">修改密码</a></li>');
                $('.dorpdown-layer ul').append('<li><a resid="-002" href="${ctx}/platform/system/sysUser/get.ht?userId=${userId}&canReturn=1" target="_blank">个人资料</a></li>');
 
                //  遍历所有根节点 根据各种条件，将各个节点插入到不同的容器中
                for(var i = 0; i < len; i++){
                    var menuItemHtml = renderMenuItem(headers[i]);
 
                    // 判断项目字符长度后再次判断是否属于系统项目，如果均通过，则将其插入到顶部齿轮图标内
                    if(/<span\s*>[\u4e00-\u9fa5]{4}<\/span>/.test(menuItemHtml)){
                        var account=$("#account").val();
                        if(sysStr.indexOf(menuItemHtml.match(/[\u4e00-\u9fa5]{4}/)[0]) > -1){
                            $('.dorpdown-layer ul').append('<li>' + menuItemHtml + '</li>');
                            continue;
                        }
                    }
                    
                    if(companyStr.indexOf(menuItemHtml.match(/[\u4e00-\u9fa5]{1,4}/)[0]) > -1){
                        //  收集属于公司的菜单项
                        var companyArr = companyStr.split(" ");
                        var indexArr   = [];
                        var arr        = [];
 
                        for(var c = 0; c < companyArr.length; c++){
                            if(companyArr[c].indexOf(menuItemHtml.match(/[\u4e00-\u9fa5]{1,4}/)[0]) > -1){
                                companyArrSort.push(menuItemHtml);
                            }
                        }
                    }else{
                        menuContainer.append(menuItemHtml);
                    }
                }
                
                companyArrSort = bubbleCompanyItem(companyArrSort);
                companyArrSort = sortCompanyItem(companyArrSort, id);   // 切换公司部门后，将当前选中的项目置顶
 
                while(companyArrSort.length){
                    companyContainer.append(companyArrSort.pop());
                }
 
                // 如果公司类目下面的项目数量大于1，则显示下拉的箭头否则隐藏
                if(companyContainer.find("a").length > 1){
                    companyContainer.addClass('show-arrow');
                }else{
                    companyContainer.removeClass('show-arrow');
                }
 
                if(len > 0){
                    var selectTab = 10000002740006; //jQuery.getCookie("selectTab"); //设置工作台为默认显示页
                    var obj       = $("#" + selectTab);
 
                    if(selectTab && obj.length > 0){
                        $("#" + selectTab).addClass("menuItem_hover");
                        loadTree(selectTab);
                    }else{
                        var head  = headers[0];
                        var resId = head.resId;
                        $("#" + resId).addClass("menuItem_hover");
                        loadTree(resId);
                    }
                }
                menuResize();
                if(callback){callback();}
            });
        }   
 
        /**
         * 在这里需要将companyArrSort进行排序下，将当前点击的项目移至最前
         * 思路是匹配出当前点击项目在数组中的索引，然后将其移除数组，然后再插入数组头部
         */
        function sortCompanyItem(arr, id){
            var i, current = null;
 
            for(i = 0, len = arr.length; i < len; i++){
                if(parseInt(arr[i].match(/id="(\d+)"/)[1]) == id){
                    current = arr.splice(i, 1);
                    arr.push(current[0]);
                    break;
                }
            }
 
            return arr;
        }
        
        /**
        *  冒泡排序：将字数少的排在前面
        */
        function bubbleCompanyItem(arr) {
        	var temp, current = null;
        	for(var i = 0; i < arr.length - 1; i++){
        		for(var j = 0; j < arr.length - 1 - i; j++) {
        			if(arr[j].match(/[\u4e00-\u9fa5]{1,4}/)[0].length < arr[j + 1].match(/[\u4e00-\u9fa5]{1,4}/)[0].length){
                        temp = arr[j];
                        arr[j] = arr[j + 1];
                        arr[j + 1] = temp;
                    }
        			if(parseInt(arr[i].match(/id="(\d+)"/)[1]) == "10000005010038"){
        				current = arr.splice(i, 1);
                        arr.push(current[0]);
                        break;
        			}
        		}
            }
        	return arr;
        }
        
        //加载资源树
        function loadTree(resId, callback){
            var targetRes  = $("#"+resId);
            var defaultUrl = targetRes.attr("url");
            
            if(defaultUrl && defaultUrl.length>8){
                if(!defaultUrl.startWith("http",false)) defaultUrl=ctxPath +defaultUrl; 
                addToTab(defaultUrl,targetRes.text().trim(),resId,targetRes.attr("icon"));
            }
            
            var nodes = new Array();
            getChildByParentId(resId,nodes);
            var zTreeObj = $.fn.zTree.init($("#leftTree"), setting, nodes);
 
            //根据配置的是否展开
            if(nodes.length>0){
                mian_layout_.setLeftCollapse(false);
                for(var idx = 0; idx < nodes.length; idx++){
                    zTreeObj.expandNode(nodes[idx],nodes[idx].isOpen==0?true:false,false);
                }
            }else{
                mian_layout_.setLeftCollapse(true);
            }
 
            getdb();//待办
            getjb();//经办
            getBatchApproval();//批量审批
            getNotReadMsg();//我的消息
 
            if(callback){callback();}
        }
        
        function getChildByParentId(parentId,nodes){
            for(var i=0;i<aryTreeData.length;i++){
                var node=aryTreeData[i];
                if(node.parentId==parentId){
                    nodes.push(node);
                    getChildByParentId(node.resId,nodes);
                }
            }
        };
        
        //处理点击事件
        function zTreeOnClick(event, treeId, treeNode) {
            // 展开深层节点
            var zTree = $.fn.zTree.getZTreeObj("leftTree");
            zTree.expandNode(treeNode);
 
            var url= treeNode.defaultUrl;
            if(url!=null && url!='' && url!='null'){
                if(!url.startWith("http",false)) url=ctxPath +url;
                if(treeNode.newOpen=="true"){
                    $.openFullWindow(url);
                }else{
                    //扩展了tab方法。
                    addToTab(url,treeNode.resName,treeNode.resId,treeNode.icon);
                }
            }
        };
 
        function removeTab(id){
            if(tab.isTabItemExist(id)){
                tab.removeTabItem(id);
            }
        };
 
        //添加到tab或者刷新
        function addToTab(url,txt,id,icon){
            if(tab.isTabItemExist(id)){
                tab.selectTabItem(id);
                tab.reload(id);
            }
            else{ 
                tab.addTabItem({ tabid:id,text:txt,url:url,icon:icon});
           } 
        };
        
        //切换系统
        function saveCurrentSys(){
            var systemId=$("#setSubSystem").val();
            var form=new com.hotent.form.Form();
            form.creatForm("form", "${ctx}/platform/console/saveCurrSys.ht");
            form.addFormEl("systemId", systemId);
            form.submit();
        }
 
        $(function(){
            /**
             * 公司模块的显示与隐藏
             */
            $('.company').on({
                mouseenter: function(){
                    $(this).addClass('view');
                },
                mouseleave: function(){
                    $(this).removeClass('view');
                }
            });
            
            // 初始化main.ht的基础布局，后续更换为easyUI，直接去掉
            mian_layout_ = $("#layoutMain").ligerLayout({
                topHeight :60,
                leftWidth: 218, 
                height: '100%',
                onHeightChanged: heightChanged 
            });
 
            //取得layout的高度
            var height = $(".l-layout-center").height();
 
            // Tab
            $("#framecenter").ligerTab({
                height: height - 16,
                onBeforeSelectTabItem: function(tabid){
                    currTabId = tabid; 
                }
            });
 
            //  左侧面板
            $("#accordion1").ligerAccordion({ height: height, speed: null });
 
            //  获取tab的引用
            tab       = $("#framecenter").ligerGetTabManager();
            accordion = $("#accordion1").ligerGetAccordionManager();
 
            //  初始读取所有顶部菜单项
            loadMenu(10000005010038);
 
            // 顶部菜单项目点击处理
            $("#menuPanel").delegate("a.menuItem", "click", function(){
                var id = $(this).attr("id");
                loadTree(id);
                // 去除顶部菜单的当前高亮
                $(this).siblings().removeClass("menuItem_hover").end().addClass("menuItem_hover");
 
                // 如果点击的是工作台则需要再加载待办和经办数据
                if(id == 10000002740006){
                    $("li[tabid='home']").click();
                }
            });
 
            // 齿轮下的点击处理
            $(".dorpdown-layer").delegate("a.menuItem", "click", function(){
                var id = $(this).attr("id");
                loadTree(id);
            });
 
            // 公司下的列表点击处理
            $(".company").delegate("a.menuItem", "click", function(){
                var id = $(this).attr("id");
                $(".company").removeClass('view');
                $("#pageloading").show();
                
                /**
                 * 切换部门时清空所有tab
                 */
                tab.removeAll();
 
                loadMenu(id, function(){
                    loadTree(id, function(){
                        $("#pageloading").hide();
 
                        // 如果当前选择的不是公司，则展开当前部门的项目菜单
                        //if(id != "10000005010038"){
                           // loadTree(10000005830017);
                       // }
                    });
                });
            });
            
            //右下角提醒框
            SysPopupRemindUtil.show("", null, "60");
 
            function initRemoveIframe(){
                if($('.l-dialog-frame').remove().length <= 0){
                    setTimeout(function(){
                        initRemoveIframe();
                    }, 500)                
                }
            }
            //  删除主页下面的一个iframe标签，测试下是否能解决底边露白的Bug
            initRemoveIframe();
 
            // 初始化左侧菜单的滚动条
            $('#leftTree').niceScroll({
                zindex: 9999,
                cursorcolor: "#999",//#CC0071 光标颜色
                cursoropacitymax: 1, //改变不透明度非常光标处于活动状态（scrollabar“可见”状态），范围从1到0
                touchbehavior: false, //使光标拖动滚动像在台式电脑触摸设备
                cursorwidth: "8px", //像素光标的宽度
                cursorborder: "0", // 游标边框css定义
                cursorborderradius: "4px",//以像素为光标边界半径
                scrollspeed: 30,
                hwacceleration: true,
                autohidemode: true //是否隐藏滚动条,
            });
 
            /**
             * 将不为选中的tab全部隐藏 这里的代码是lingerUI的修复代码，后续
             * 更换为easyUI后直接删掉
             */
            // var tabIndex        = 0;
            // var resize_interval = 1500;
            // var resize_idto     = undefined;
 
            // $(window).resize(function(){
            //     if(resize_idto){return false;}
 
            //     var $tabls = $('.l-tab-links li');
            //     $tabls.each(function(index, el) {
            //         if($(el).hasClass('l-selected')){
            //             tabIndex = index;
            //             return;
            //         }
            //     });   
 
            //     $('.l-tab-content .l-tab-content-item').each(function(index, el){
            //         if(index !== tabIndex){
            //             $(el).addClass('hide');
            //         }
            //     });
 
            //     resize_idto = setTimeout(function(){
            //         resize_idto = undefined;
            //     }, resize_interval);
            // });
        });
      //窗口大小改变
          function menuResize() {
              var mwei = $("#menuPanel").width();
              var iwei = 0;
              var more = "<a class='menuItem more-menu-item'><span>更多</span></a>";
              
              $("#menuPanel .menuItem").each(function() {
                  iwei = iwei + $(this).width() + 20;
              });
              if(mwei < iwei && $("#menuPanel .more-menu-item").length == 0) {
                  $("#menuPanel").append(more).addClass("over-width");
              }
          }         
     </script> 
</head>
<body onresize="menuResize();">  
    <div id="pageloading"></div>
 
    <%@include file="main_top.jspf" %>
 
    <div id="layoutMain">
        
        <!-- 左侧树形菜单 -->
        <div position="left" id="accordion1"   title=" ${currentSystem.sysName }"> 
            <ul id='leftTree' class='ztree main__ztree' style="overflow:auto;height: 100%" ></ul>
        </div>   
 
        <!-- 右侧内容 -->
        <div position="center" id="framecenter"> 
            <div tabid="home" title="<img src='${ctx}/styles/${skinStyle}/images/icon/work.jpg' style='vertical-align:middle;'> 我的工作台">
                <c:if test="${not empty currentSystem.homePage }">
                    <c:choose>
                        <c:when test="${currentSystem.isLocal==1}">
                            <iframe frameborder="0" id="iframe" name="home" src="${ctx}${currentSystem.homePage}"></iframe>
                        </c:when>
                        <c:otherwise>
                            <iframe frameborder="0" id="iframe" name="home" src="${currentSystem.defaultUrl}${currentSystem.homePage}"></iframe>
                        </c:otherwise>
                    </c:choose>
                </c:if>
            </div>
        </div>
 
        <input id="userId" type="hidden" value="${userId}" />
    </div>
<script>
    $(function(){
        $("li[tabid='home']").click(function(){
            home.window.refProcessData();
        });
    });
    
    function changeOrgFunc(id){
        $("#company").find("#"+id).click();
    }
      function changeOrgFuncByUrl(url){
          $("#company").find("a[url='"+url+"']").click();
      }
</script>
</body>
</html>