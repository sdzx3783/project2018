<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>任务列表</title>
<%@include file="/commons/include/get.jsp"%>
<%@ taglib prefix="hotent" uri="http://www.jee-soft.cn/paging" %>
</head>
<script type="text/javascript">
	function addStageTask(){
		
		window.open('editStageTask.ht?classifystageid=${classifystageid}', 'addclassifyStageTask','height=500, width=900, top=200, left=300, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=n o, status=no');
		
	}
	function editStageTask(id){
		
		window.open('editStageTask.ht?classifystageid=${classifystageid}&id='+id, 'addclassifyStageTask','height=500, width=900, top=200, left=300, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=n o, status=no');
		
	}
	function viewStageTask(id){
		
		window.open('viewStageTask.ht?classifystageid=${classifystageid}&id='+id, 'addclassifyStageTask','height=500, width=900, top=200, left=300, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=n o, status=no');
		
	}
	function setStageTask(id){
		window.open('setStageTask.ht?classifystageid=${classifystageid}&id='+id, 'addclassifyStageTask','height=500, width=900, top=200, left=300, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=n o, status=no');
	}
	
	function confirmdel(id){
		$.ligerDialog.confirm('确认删除吗？','提示信息',function(rtn) {
			if(rtn) {
				var url="delStageTask.ht?id="+id+'&classifylibid=${classifyLib.id}';
				window.location.href=url;
			}else{
				return false;
			}
		});
	}
	function showFlowChart(){
		
		$.post("${ctx}/makshi/project/classifylib/stageTaskCheck.ht",{id:'${classifystageid}'},function(responseText){
			var obj = new com.hotent.form.ResultMessage(responseText);
			if (obj.isSuccess()) {
				window.location.href="${ctx}/makshi/project/classifylib/taskFlowchart.ht?id=${classifystageid}";
					
			} else {
				$.ligerDialog.error(obj.getMessage(),"提示信息");
			}
		});
	
	}
	
	function check(){
		$.post("${ctx}/makshi/project/classifylib/stageTaskCheck.ht",{id:'${classifystageid}'},function(responseText){
			var obj = new com.hotent.form.ResultMessage(responseText);
			if (obj.isSuccess()) {
				$.ligerDialog.success("检查校验成功！");
					
			} else {
				$.ligerDialog.error(obj.getMessage(),"提示信息");
			}
		});
	}
</script>
<body>

	<div id="oa_list_search" class="oa-pd20">
		<button class="oa-button oa-button--primary oa-button--blue" type="button" onclick="showFlowChart();">流程图</button>
		<button class="oa-button oa-button--primary oa-button--blue" type="button" onclick="check();">检查</button>
		<c:if test="${1!=classifyLib.isused }">
			<button class="oa-button oa-button--primary oa-button--blue" type="button" onclick="addStageTask();">新增任务</button>
		</c:if>
	</div>
	
	<div class="oa-pd20 oa-pdt0">
		<div id="oa_list_content" class="oa-table-scroll-wrap">
			<c:set var="cnt" value="${(pageBeantaskItem.currentPage-1)*pageBeantaskItem.pageSize+1 }"/>
			<display:table name="stageTaskList" id="taskItem" requestURI="stageTaskList.ht" sort="external" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap table-list">
				<display:column title="序号">
					<c:out value="${cnt}"/>
					<c:set var="cnt" value="${cnt+1}"/>
				</display:column>
				<display:column title="任务名称">
					${taskItem.taskname}
				</display:column>
				<display:column title="任务编号">
					${taskItem.taskno }
				</display:column>
				<display:column title="操作" media="html">
					<button class="oa-button-label" type="button" onclick="viewStageTask(${taskItem.id});">查看</button>
					<button class="oa-button-label" type="button" onclick="setStageTask(${taskItem.id});">配置</button>
					<c:if test="${1!=classifyLib.isused}">
						<a class="oa-button-label oa-button-label-remove"  href="delStageTask.ht?id=${taskItem.id }&classifylibid=${classifyLib.id}">删除</a>
					</c:if>
				</display:column>
			</display:table>
		</div>

		<hotent:paging tableId="taskItem" />
	</div>

<script>
	$(function(){
		//处理删除一行
        $(".oa-button-label-remove").click(function(){
            if($(this).hasClass('disabled')) return false;
            
            var ele = this;
            $.ligerDialog.confirm('确认删除吗？','提示信息',function(rtn) {
                if(rtn) {
                    if(ele.click) {
                        $(ele).unbind('click');
                        ele.click();
                    } else {
                        location.href=ele.href;
                    }
                }
            });
            
            return false;
        });
	});
</script>
</body>
</html>


