<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>发文总表管理</title>
<%@include file="/commons/include/get.jsp" %>
<script type="text/javascript">
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">发文总表管理列表</span>
			</div>
		   <%--  <div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch"><span></span>查询</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link add" href="edit.ht"><span></span>添加</a></div>
					<div class="l-bar-separator"></div>
					
					
					<div class="group"><a class="link update" id="btnUpd" action="edit.ht"><span></span>修改</a></div>
					    <div class="l-bar-separator"></div>
						<div class="group"><a class="link run"  onclick="$.openFullWindow('${ctx}/platform/bpm/task/startFlowForm.ht?defId=10000003390004');"><span></span>启动流程</a></div>
				</div>	
			</div> --%>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht">
					<div class="row">
					</div>
				</form>
			</div> 
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="dispatchList" id="dispatchItem" requestURI="check.ht" sort="external" cellpadding="1" cellspacing="1" class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${dispatchItem.id}">
				</display:column>
				
				<display:column title="发文标题">${dispatchItem.title}</display:column>
				<display:column title="发文编号">${dispatchItem.dispatch_id}</display:column>
				<display:column title="发文类型">${dispatchItem.type}</display:column>
				<display:column title="紧急程度">
					<c:if test="${dispatchItem.urgency_degree==0}">未选择</c:if>
					<c:if test="${dispatchItem.urgency_degree==1}">普通</c:if>
					<c:if test="${dispatchItem.urgency_degree==2}">紧急</c:if>
					<c:if test="${dispatchItem.urgency_degree==3}">非常紧急</c:if>
				</display:column>
				<display:column title="秘密等级">
					<c:if test="${dispatchItem.secret_level==0}">未选择</c:if>
					<c:if test="${dispatchItem.secret_level==3}">绝密</c:if>
					<c:if test="${dispatchItem.secret_level==2}">机密</c:if>
					<c:if test="${dispatchItem.secret_level==1}">秘密</c:if>
				</display:column>
				<display:column title="拟稿日期">${dispatchItem.time}</display:column>
				<display:column title="拟稿人">${dispatchItem.draft_person}</display:column>
				<display:column title="核稿人">${dispatchItem.check_person}</display:column>
				<display:column title="状态">
					<c:if test="${dispatchItem.examineState==1}">已审批</c:if>
					<c:if test="${dispatchItem.examineState!=1}">待审批</c:if>
				</display:column>
				
				
				<display:column title="操作" media="html" style="width:10%">
					<c:if test="${dispatchItem.runId==0}">
						<a href="del.ht?id=${dispatchItem.id}" class="link del"><span></span>删除</a>
					</c:if>
					<c:if test="${dispatchItem.examineState!=1}">
					 <a href="javascript:;"  onclick="executeTask(${dispatchItem.taskId})">审批</a> 
					 </c:if>
				<%-- 	<a href="edit.ht?id=${dispatchItem.id}" class="link edit"><span></span>编辑</a>
					<a href="get.ht?id=${dispatchItem.id}" class="link detail"><span></span>明细</a> --%>
				</display:column>
			</display:table>
			<hotent:paging tableId="dispatchItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
<script type="text/javascript">


function executeTask(taskId){
	var url="/platform/bpm/task/toStart.ht?taskId="+taskId;
	var rtn = jQuery.openFullWindow(url);
}

function searchFinishTask(taskId){
	var url="/platform/bpm/processRun/info.ht?runId="+taskId+"&prePage=myFinishedTask";
	var rtn = jQuery.openFullWindow(url);
}
/*
$(function() {
    var index = 0;

    $('.menu').on('click', '.link', function(event) {
        var target = event.target;
        index = $(this).index();

        return false;
    });

    // 切换显示长菜单
    $('.view-menu-btn').on('click', function(){


    });


    // tab切换
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

    $('#ca').calendar({
		width: 302,
		height: 300,
		data: [{
				date: '2015/12/24',
				value: 'Christmas Eve'
			},
			{
				date: '2015/12/25',
				value: 'Merry Christmas'
			},
			{
				date: '2016/01/01',
				value: 'Happy New Year'
			}
		],
		onSelected: function(view, date, data) {

		}
	});
    $.ajax({
		type : "get",
		url : "/platform/bpm/task/pd_mt_db_index.ht",
		success : function(data) {
			var json = eval('(' + data + ')');
			var list=json.message;
			var jsonObjArr=eval("("+list+")");  
			$("#pendingMatters").html("");
			var html="";
			 $.each(jsonObjArr, function (i, rowdata) {  
				 var time=rowdata.createTime.time;
				 var d=	new Date();
				 d.setTime(time);
				 var s=d.format('yyyy-MM-dd hh:mm:ss');
				  html+='<a href="javascript:executeTask('+rowdata.id+')"><div class="title">'+rowdata.subject+'</div><div class="info">'+rowdata.creator+' <span class="date">'+s+'</span></div></a>';  
		     });  
			$("#pendingMatters").html(html);
		}
    });
    $.ajax({
		type : "get",
		url : "/platform/bpm/task/pd_mt_yb_index.ht",
		success : function(data) {
			var json = eval('(' + data + ')');
			var list=json.message;
			var jsonObjArr=eval("("+list+")");  
			$("#completedMatters").html("");
			var html="";
			 $.each(jsonObjArr, function (i, rowdata) {  
				 var time=rowdata.createtime.time;
				 var d=	new Date();
				 d.setTime(time);
				 var s=d.format('yyyy-MM-dd hh:mm:ss');
				  html+='<a href="javascript:searchFinishTask('+rowdata.runId+')"><div class="title">'+rowdata.subject+'</div><div class="info">'+rowdata.creator+' <span class="date">'+s+'</span></div></a>';  
		     });  
			$("#completedMatters").html(html);
		}
    });
});
 */
</script>
</html>


