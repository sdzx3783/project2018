<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>项目阶段任务选择器</title>
<%@include file="/commons/include/get.jsp"%>
<style>
    .icon-location-arrow{
        margin-top: 3px;
        font-size: 16px;
    }
    .link.search{
        padding: 4px 15px;
        font-size: 14px;
        display: inline-block;
    }
    a.link.send{
        color: #fff;
        padding: 5px 10px;
        padding-left: 30px;
        border-radius: 3px;
        background: #478de4 url(/images/email_icon.png) 5px center no-repeat;
    }
    a.link.send + a{
        margin-left: 5px;
        background: #36bb5f url(/images/message_icon.png) 5px center no-repeat;
    }
    .panel-table{
            overflow-y: auto !important;
    }
    .table{
   		width:	500px;
		margin: 0 16px;
		table-layout: fixed;
	}
	.table th{
		padding: 10px 20px;
	    border: 1px solid #eceff8;
	}
	.table td{
		padding: 10px 20px;
	    border: 1px solid #eceff8;
	}
	.table td div:first-child{
		margin-bottom: 16px;
	}
	.chs {
		width: 20px;
	}
</style>
<%@ taglib prefix="hotent" uri="http://www.jee-soft.cn/paging" %>
</head>
<script type="text/javascript">
	
	
	function addProjectStageTask(){
		//单击删除超链接的事件处理
		$("div.group > a.link.save").click(function()
		{	
			
			if($(this).hasClass('disabled')) return false;
			
			var action=$(this).attr("action");
			var _infoMsg=$(this).attr("infoMsg");//自定义属性（用户修改原有批量操作时的提示信息）
			
			var $aryId = $("input[type='checkbox'][disabled!='disabled'][class='pk']:checked");
			
			if($aryId.length == 0){
				$.ligerDialog.warn("请选择记录！");
				return false;
			}
			
			//提交到后台服务器进行日志删除批处理的日志编号字符串
			var delId="";
			var keyName="";
			var len=$aryId.length;
			$aryId.each(function(i){
				var obj=$(this);
				if(i<len-1){
					delId+=obj.val() +",";
				}
				else{
					keyName=obj.attr("name");
					delId+=obj.val();
				}
			});
			var _arr=[];
			$aryId.each(function(i){
				var _parent=$(this).parent();
				var taskname=$(_parent).next().next().text();
				var taskno=$(_parent).next().next().next().text();
				var obj={};
				obj['taskid']=$(this).val();
				obj['taskname']=taskname;
				obj['taskno']=taskno;
				_arr.push(obj);
			})
			
			if(0=='${selectType}'){
				window.parent.addPreNode(_arr);
			}else if(1=='${selectType}'){
				window.parent.addAfterNode(_arr);
			}else{
				window.parent.addQueryNode(_arr);
			}
			parent.$.ligerDialog.close();
			parent.$(".l-dialog,.l-window-mask").css("display","none");
		});
	}
	$(function(){
		addProjectStageTask();
	})
	
</script>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">分类库阶段任务列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="l-bar-separator"></div>
					<div class="group">
						<a class="link save" infoMsg="保存" action="/makshi/project/project/afterAndPrestagetask.ht"><span></span>保存</a>
					</div>
				</div>	
			</div>
			<div class="panel-search">
				
			</div>
		</div>
		<div class="panel-body">
	    	
		   <%--  <display:table name="stagelibList" id="stagelib" requestURI="selector.ht" sort="external" cellpadding="1" cellspacing="1" class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
					<input type="checkbox" class="pk" name="stageno" value="${stagelib.stageno}">
				</display:column>
				<display:column property="stagename" title="阶段名称" sortable="false" ></display:column>
				<display:column property="stageno" title="阶段编号"  sortable="false" ></display:column>
                       
                 <display:column property="createorg" title="创建部门" sortable="false" ></display:column>
                 <display:column property="tasknum" title="包含任务数" sortable="false" ></display:column> 
                 <display:column property="remark" title="备注" sortable="false" ></display:column> 
				
			</display:table> --%>
			<table class="table">
			<tr>
				<th class="chs"></th>
				<th>序号</th>
				<th>任务名称</th>
				<th>任务编号</th>
			</tr>
			<c:forEach items="${projectStageTaskList }" var="stageTask" varStatus="vs">
				<tr>
					<td class="chs"><input type="checkbox" name="selectedId" class="pk" value="${stageTask.cstid }"/></td>
					<td>${vs.index+1 }</td>
					<td>${stageTask.taskname }</td>
					<td>${stageTask.taskno }</td>
				</tr>
			</c:forEach>
			</table>
		</div><!-- end of panel-body -->	
	</div> <!-- end of panel -->
	

</body>
</html>


