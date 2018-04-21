<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>阶段详情</title>
<%@include file="/commons/include/get.jsp"%>
<%@include file="/codegen/include/customForm.jsp"%>
<%@include file="/commons/include/ueditor.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerTab.js"></script>
<script type="text/javascript"
	src="${ctx}/js/hotent/platform/form/AttachMent.js"></script>
<script type="text/javascript"
	src="${ctx}/js/hotent/platform/form/TablePermission.js"></script>
<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#projectForm').form();
			$("a.save").click(function() {
				frm.ajaxForm(options);
				if(frm.valid()){
					//如果有编辑器的情况下
					$("textarea[name^='m:'].myeditor").each(function(num) {
						var name=$(this).attr("name");
						var data=getEditorData(editor[num]);
						$("textarea[name^='"+name+"']").val(data);
					});
					
					if(WebSignPlugin.hasWebSignField){
						WebSignPlugin.submit();
					}
					if(OfficePlugin.officeObjs.length>0){
						OfficePlugin.submit(function(){
							frm.handleFieldName();
							$('#projectForm').submit();
						});
					}else{
						frm.handleFieldName();
						$('#projectForm').submit();
					}
				}
			});
			
			$("#check").click(function() {
				if(frm.valid()){
					$.post("${ctx}/makshi/project/project/check.ht",{id:'${stage.id}'},function(responseText){
						var obj = new com.hotent.form.ResultMessage(responseText);
						if (obj.isSuccess()) {
							$.ligerDialog.success("检查校验成功！");
								
						} else {
							$.ligerDialog.error(obj.getMessage(),"提示信息");
						}
					});
					
				}
				
			});
		});
		
		function showResponse(responseText) {
			var obj = new com.hotent.form.ResultMessage(responseText);
			if (obj.isSuccess()) {
				$.ligerDialog.confirm(obj.getMessage()+",是否继续操作","提示信息", function(rtn) {
					if(rtn){
						window.location.href = window.location.href;
					}else{
						var classifyLibraryId = "${project.classifylibraryid}";
						window.location.href = "${ctx}/makshi/project/project/list.ht?classifylibraryid="+classifyLibraryId;
					}
				});
			} else {
				$.ligerDialog.error(obj.getMessage(),"提示信息");
			}
			
		}
		 
		 function end(stageId) {
				if(stageId=='' || stageId ==null){
					return ;
				}

				$.post("/makshi/project/project/endStage.ht", {
					'id' : stageId,

				}, function(data) {
					showResponse(data);
				});

			}
		 
		 function addStageTask(id){
				window.open('editStageTask.ht?stageid='+id, 'addProjectStageTask','height=500, width=900, top=200, left=300, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=n o, status=no');
			}
		 
		 function setStageTask(id){
				window.open('setStageTask.ht?projectstageid=${stage.id}&id='+id, 'addProjectStageTask','height=500, width=900, top=200, left=300, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=n o, status=no');
			}
		 function viewStageTask(id){
				window.open('viewStageTask.ht?taskId='+id, 'addProjectStageTask','height=500, width=900, top=200, left=300, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=n o, status=no');
		 }
		 function confirmdel(id){
				$.ligerDialog.confirm('确认删除吗？','提示信息',function(rtn) {
					if(rtn) {
						var url="delStageTask.ht?id="+id+"&projectid=${project.id}";
						window.location.href=url;
					}else{
						return false;
					}
				});
			}
	</script>
	<style>
		dt{
			float: left;
		}
	</style>
</head>
<body class="oa-mw-page">
	<div class="oa-project-title">
        <h2>阶段详情</h2>
    </div>
    <div class="oa-top-wrap">
        <a class="oa-button oa-button--primary oa-button--blue" href="list.ht?classifylibraryid=${project.classifylibraryid}">返回项目列表</a>

		<c:if test="${stage.status==0 }">
			<c:if test="${project.isCharger }">   
	        	<button class="oa-button oa-button--primary oa-button--blue" id="check">检查</button>
	        	<button class="oa-button oa-button--primary oa-button--blue" onClick="end(${stage.id })">关闭阶段</button>
				<button class="oa-button oa-button--primary oa-button--blue" onClick="addStageTask(${stage.id })">新增任务</button>
			</c:if>
		</c:if>
    </div>
    <div class="oa-main-wrap">
        <dl>
            <dt>阶段名称：</dt>
            <dd>${stage.stagename}</dd>
        </dl>
		
		<div class="oa-table-scroll-wrap">
			<table class="oa-table--default oa-table--nowrap">
				<thead>	
					<tr>
						<th>序号</th>
						<th>任务名称</th>
						<th>负责人</th>
						<th>任务起始时间</th>
						<th>任务终止时间</th>
						<th>任务状态</th>
						<th>任务执行次数</th>
						<c:if test="${project.isCharger }">
							<th>操作</th>
						</c:if>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${stage.tasks }" var="task" varStatus="taskNum">
						<tr>
							<td>${taskNum.index+1 }</td>
							<td><a href="taskDetail.ht?id=${task.id}">${task.taskname}</a></td>
							<td>${task.charge}</td>
							<td><fmt:formatDate value='${task.starttime }' pattern='yyyy-MM-dd' /></td>
							<td><fmt:formatDate value='${task.endtime }' pattern='yyyy-MM-dd' /></td>
							<td>
								<c:if test="${task.status==1 }">完成</c:if>
								<c:if test="${task.status==0 }">未完成</c:if>
							</td>
							<td>${task.endcount}</td>
								<td>
									<c:if test="${project.isCharger }">
										<button class="oa-button-label" onclick="setStageTask(${task.id })">配置</button>
										<button class="oa-button-label" onclick="confirmdel(${task.id })">删除</button>
									</c:if>
									<%-- <button class="oa-button-label" onclick="viewStageTask(${task.id })">查看</button> --%>
								</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
    </div>
</body>
</html>
