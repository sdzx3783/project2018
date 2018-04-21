<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>项目列表</title>
<%@include file="/commons/include/get.jsp"%>
<%@ taglib prefix="hotent" uri="http://www.jee-soft.cn/paging" %>
<script type="text/javascript">
	function addStage(){
		
		var obj=$.ligerDialog.open({ url: 'add.ht' ,title:'新建',width:400,height: 400, isResize:true });
		
		return true;
	}
	
	function check(id){
		$.post("${ctx}/makshi/project/classifylib/check.ht",{
			'id':id
			},function(responseText){
			var obj = new com.hotent.form.ResultMessage(responseText);
			if (obj.isSuccess()) {
				 window.location.href="toAdd.ht?id="+id;
					
			} else {
				$.ligerDialog.error(obj.getMessage(),"提示信息");
				return false;
			}
	});
	}
	
	function delProject(id){
		$.ligerDialog.confirm('确认删除吗？','提示信息',function(rtn) {
			if(rtn) {
				$.post("${ctx}/makshi/project/project/delProject.ht",
						{id:id},function(responseText){
					var obj = new com.hotent.form.ResultMessage(responseText);
					if (obj.isSuccess()) {
						$.ligerDialog.success("删除成功！");
						window.location.reload();
					} else {
						$.ligerDialog.error(obj.getMessage(),"提示信息");
					}
				});
			}else{
				return false;
			}
		});
		
	}
</script>
</head>
<body class="oa-mw-page">

	<div id="oa_list_title" class="oa-mgb20 oa-project-title">
        <!-- <h2>项目列表</h2> -->
    </div>

    <c:if test="${isCharge }">
        <div id="oa_list_operation" class="oa-pdb20 oa-mgh20 oa-mgl40">
            <button type="button" class="oa-button oa-button--primary oa-button--blue" onClick="return check(${classifylibraryid });">新建项目</button>
        </div>
    </c:if>

	<div id="oa_list_search" class="oa-pdb10 oa-mgh20">

        <form id="searchForm" method="post" action="list.ht?classifylibraryid=${classifylibraryid }" class="oa-clear">
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">项目名称</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input class="oa-input" type="text" name="Q_name_SL" value="${param['Q_name_SL'] }">
                </div>
            </div>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">项目负责人</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input class="oa-input" type="text" name="Q_applicant_SL" value="${param['Q_applicant_SL'] }">
                </div>
            </div>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">项目状态</div>
                <div class="oa-select-wrap oa-mgl100">
                    <select class="oa-select" name="status">
                        <option value="">请选择状态</option>
                        <option value="0" <c:if test="${param['status']==0}">selected=selected</c:if>>未开工</option>
                        <option value="1" <c:if test="${param['status']==1}">selected=selected</c:if>>在建</option>
                        <option value="2" <c:if test="${param['status']==2}">selected=selected</c:if>>停工</option>
                        <option value="3" <c:if test="${param['status']==3}">selected=selected</c:if>>已完工</option>
                        <option value="4" <c:if test="${param['status']==4}">selected=selected</c:if>>建设期</option>
                        <option value="5" <c:if test="${param['status']==5}">selected=selected</c:if>>运营期</option>
                    </select>
                </div>
            </div>

            <div class="oa-fl oa-mgb10 oa-mgh20">
                <button class="oa-button oa-button--primary oa-button--blue">查询</button>
            </div>

        </form>

    </div>
    

	<div class="oa-pdb20 oa-mgh20">
		<div id="oa_list_content" class="oa-table-scroll-wrap">
			<table class="oa-table--default oa-table--nowrap">
				<tr>
					<th>序号</th>
					<th>项目ID</th>
					<th>项目名称</th>
					<th>项目负责人</th>
					<th>项目状态</th>
					<th>项目年份</th>
					<c:if test="${list!=null && list.size()>0 }">
					<c:forEach items="${list.get(0).stages }" var="stage">
					<th>${stage.stagename }</th>
					</c:forEach>
					</c:if>
					<th>操作</th>
				</tr>
				<c:forEach items="${list }" var="projectItem" varStatus="a">
				<tr>
					<td class="J_index">${a.count}</td>
					<td>${projectItem.id}</td>
					<td><a href="projectDetail.ht?id=${projectItem.id}" >${projectItem.name}</a></td>
					<td>${projectItem.applicant}</td>
					<td>
						<c:if test="${projectItem.status==0}">未开工</c:if>
						<c:if test="${projectItem.status==1}">在建</c:if>
						<c:if test="${projectItem.status==2}">停工</c:if>
						<c:if test="${projectItem.status==3}">已完工</c:if>
						<c:if test="${projectItem.status==4}">建设期</c:if>
						<c:if test="${projectItem.status==5}">运营期</c:if>
					</td> 
					<td>${projectItem.proYear}</td>
					<c:if test="${projectItem.stages!=null}">
					<c:forEach items="${projectItem.stages }" var="stage">
					<td><a href="stageDetail.ht?id=${stage.id }">
						<c:if test="${stage.taskStatus==0 && stage.ends==0 }">
							未开始(${stage.ends })
						</c:if>
						<c:if test="${stage.taskStatus==0 && stage.ends!=0 }">
							进行中(${stage.ends })
						</c:if>
						<c:if test="${stage.taskStatus==1 }">
							已完成(${stage.ends })
						</c:if>
						</a>
					</td>
					</c:forEach>
					</c:if>
					<td>
						<c:if test="${projectItem.isCharger || isCharge}">
							<a href="edit.ht?id=${projectItem.id}" class="oa-button-label"><span></span>编辑</a>
							<a href="getTask.ht?id=${projectItem.id}" class="oa-button-label"><span></span>配置</a>
						</c:if>
						<c:if test="${isCharge}">
							<a onclick="delProject(${projectItem.id})" href="#" class="oa-button-label"><span></span>删除</a>
						</c:if>
					</td>
				</tr>
				</c:forEach>
			</table>
		</div>

		<hotent:paging tableId="projectItem"/>
	</div>
	<script>
		$(".J_index").each(function() {
			var text = parseInt($(this).text());
			text = text + (parseInt($(".oa-paging-input").val()) - 1) * parseInt($("#pageSize").val());
			 $(this).text(text)
		});
	</script>
</body>
</html>


