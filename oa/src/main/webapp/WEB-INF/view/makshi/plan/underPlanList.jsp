<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>我的计划</title>
<%@include file="/commons/include/get.jsp"%>
<%@ taglib prefix="hotent" uri="http://www.jee-soft.cn/paging" %>
</head>
<script type="text/javascript">
	function addStage(){
		
		var obj=$.ligerDialog.open({ url: 'add.ht' ,title:'新建',width:400,height: 400, isResize:true });
		
		return true;
	}
	
	
	function delPlan(id){
		$.ligerDialog.confirm('确认删除吗？','提示信息',function(rtn) {
			if(rtn) {
				$.post("${ctx}/makshi/plan/plan/delPlan.ht",
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
        <h2>计划列表</h2>
    </div>
    <div id="oa_list_search" class="oa-pdb10 oa-mgh20">
        <form id="searchForm" method="post" action="underList.ht" class="oa-clear">
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">计划周期</div>
                <div class="oa-select-wrap oa-mgl100">
                    <select name="Q_cycle_S" class="oa-select" value="${param['Q_cycle_S']}">
                        <option value="">选择</option>
                        <option value="1" <c:if test="${param['Q_cycle_S']=='1'}">selected</c:if>>日计划</option>
                        <option value="2" <c:if test="${param['Q_cycle_S']=='2'}">selected</c:if>>周计划</option>
                        <option value="3" <c:if test="${param['Q_cycle_S']=='3'}">selected</c:if>>月计划</option>
                        <option value="4" <c:if test="${param['Q_cycle_S']=='4'}">selected</c:if>>季度计划</option>
                        <option value="5" <c:if test="${param['Q_cycle_S']=='5'}">selected</c:if>>半年计划</option>
                        <option value="6" <c:if test="${param['Q_cycle_S']=='6'}">selected</c:if>>年计划</option>
                    </select>
                </div>
            </div>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">创建时间从</div>
                <div class="oa-input-wrap oa-mgl100 oa-input-wrap--ib">
                    <input type="text" id="Q_beginctime_DL" name="Q_beginctime_DL"  class="oa-input Wdate" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'Q_endctime_DG\');}'})" value="${param['Q_beginctime_DL']}"/>
                </div>
                <span>至</span>
                <div class="oa-input-wrap oa-input-wrap--ib">
                    <input type="text" id="Q_endctime_DG" name="Q_endctime_DG"  class="oa-input Wdate" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'Q_beginctime_DL\');}'})"  value="${param['Q_endctime_DG']}"/>
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
					<th>计划名称</th>
					<th>计划人</th>
					<th>创建时间</th>
					<th>是否总结</th>
					<th>上级评论</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${list }" var="planItem" varStatus="a">
				<tr>
					<td>${a.index+1}</td>
					<td>
						<fmt:formatDate value='${planItem.date }' pattern='yyyy-MM-dd'/>
						<c:if test="${planItem.cycle==1}">日计划</c:if>
						<c:if test="${planItem.cycle==2}">周计划</c:if>
						<c:if test="${planItem.cycle==3}">月计划</c:if>
						<c:if test="${planItem.cycle==4}">季度计划</c:if>
						<c:if test="${planItem.cycle==5}">半年计划</c:if>
						<c:if test="${planItem.cycle==6}">年计划</c:if>
					</td>
					<td>${planItem.cuser }</td>
					<td><fmt:formatDate value='${planItem.ctime }' pattern='yyyy-MM-dd'/></td>
					<td>
						<c:if test="${planItem.isSummary}">是</c:if>
						<c:if test="${!planItem.isSummary}">否</c:if>
					</td> 
					<td>
						<c:if test="${planItem.isReply}">是</c:if>
						<c:if test="${!planItem.isReply}">否</c:if>
					</td>  
					<td>
						<a href="/makshi/plan/plan/detail.ht?id=${planItem.id}&type=2" class="oa-button-label"><span></span>评价</a>
					</td>
				</tr>
				</c:forEach>
			</table>
		</div>
		<hotent:paging tableId="planItem"/>
	</div>
</body>
</html>


