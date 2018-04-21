<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>任务列表</title>
<%@include file="/commons/include/get.jsp"%>
<%@ taglib prefix="hotent" uri="http://www.jee-soft.cn/paging" %>
</head>
<script type="text/javascript">
	
	function delTask(id){
		$.ligerDialog.confirm('确认删除吗？','提示信息',function(rtn) {
			if(rtn) {
				$.post("${ctx}/makshi/task/task/delTask.ht",
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
<body class="oa-mw-page">

	<div id="oa_list_title" class="oa-mgb20 oa-project-title">
        <h2>任务列表</h2>
    </div>

    <c:if test="${type==1}">
        <div id="oa_list_operation" class="oa-pdb20 oa-mgh20 oa-mgl40">
            <button class="oa-button oa-button--primary oa-button--blue" onClick="window.location.href='toAdd.ht';">创建</button>
        </div>
    </c:if>

    <div id="oa_list_search" class="oa-pdb10 oa-mgh20">
        <form id="searchForm" method="post" action="list.ht?type=1" class="oa-clear">
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">任务名称</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input type="text" name="Q_name_SL"  class="oa-input"  value="${param['Q_name_SL']}"/>
                </div>
            </div>
            <div class="oa-fl oa-mgb10">
                 <div class="oa-label">开始时间从</div>
                <div class="oa-input-wrap oa-mgl100  oa-input-wrap--ib">
                    <input type="text" id="Q_bstartDate_DL" name="Q_bstartDate_DL"  class="oa-input Wdate" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'Q_estartDate_DG\');}'})" value="${param['Q_bstartDate_DL']}"/>
                </div>
                <span>至</span>
                <div class="oa-input-wrap oa-input-wrap--ib">
                    <input type="text" id="Q_estartDate_DG" name="Q_estartDate_DG"  class="oa-input Wdate" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'Q_bstartDate_DL\');}'})"  value="${param['Q_estartDate_DG']}"/>
                </div>
            </div>
             <div class="oa-fl oa-mgb10">
                 <div class="oa-label">结束时间从</div>
                <div class="oa-input-wrap oa-mgl100 oa-input-wrap--ib">
                    <input type="text" id="Q_bendDate_DL" name="Q_bendDate_DL"  class="oa-input Wdate" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'Q_eendDate_DG\');}'})" value="${param['Q_bendDate_DL']}"/>
                </div>
                <span>至</span>
                <div class="oa-input-wrap oa-input-wrap--ib">
                    <input type="text" id="Q_eendDate_DG" name="Q_eendDate_DG"  class="oa-input Wdate" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'Q_bendDate_DL\');}'})"  value="${param['Q_eendDate_DG']}"/>
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
					<th>任务名称</th>
					<th>负责人</th>
					<th>参与人</th>
					<th>进度</th>
					 <th>状态</th>
					<th>开始时间</th>
					<th>结束时间</th>
					<th>分配人</th>
					<c:if test="${type==1}">
					<th>是否发布</th>
					<th>操作</th>
					</c:if>
				</tr>
				<c:forEach items="${list }" var="taskItem" varStatus="a">
				<tr>
					<td>${a.index+1}</td>
					<td><a href="/makshi/task/task/detail.ht?id=${taskItem.id}&type=${type}" >${taskItem.name }	</a></td>
					<td>${taskItem.charger }</td>
					<td>${taskItem.member }</td>
					<td>${taskItem.progress }%</td>
					 <td>
						<c:if test="${taskItem.stage==1}">未开始</c:if>
						<c:if test="${taskItem.stage==2}">取消</c:if>
						<c:if test="${taskItem.stage==3}">进行中</c:if>
						<c:if test="${taskItem.stage==4}">完成</c:if>
					</td>
					<td><fmt:formatDate value='${taskItem.startDate }' pattern='yyyy-MM-dd'/></td>
					<td><fmt:formatDate value='${taskItem.endDate }' pattern='yyyy-MM-dd'/></td>
					<td>${taskItem.cuser }</td>
					<c:if test="${type==1}">
					<td>
						<c:if test="${taskItem.ispub==1}">已发布</c:if>
						<c:if test="${taskItem.ispub==0}">未发布</c:if>
					</td>
					<td>
						<c:if test="${taskItem.ispub==0}">
						<a href="/makshi/task/task/edit.ht?type=1&id=${taskItem.id}" class="oa-button-label"><span></span>编辑</a>
						<a onclick="delTask(${taskItem.id})" href="#" class="oa-button-label"><span></span>删除</a>
						</c:if>
						<%-- <c:if test="${type==2 && taskItem.isCharge}">	
						<a href="/makshi/task/task/edit.ht?type=2&id=${taskItem.id}" class="oa-button-label"><span></span>办理</a>
						</c:if> --%>
					</td>
					</c:if>
				</tr>
				</c:forEach>
			</table>
		</div>
		<hotent:paging tableId="taskItem"/>
	</div>
</body>
</html>


