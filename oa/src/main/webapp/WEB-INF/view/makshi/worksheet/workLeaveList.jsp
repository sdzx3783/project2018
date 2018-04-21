<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>缺勤表管理</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">缺勤表管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch"><span></span>查询</a></div>
					<c:if test="${alias==null || (alias!=null && alias=='bpm_hr_manager')}">
						<div class="l-bar-separator"></div>
						<div class="group"><a class="link add" href="edit.ht"><span></span>登记</a></div>
					</c:if>
					<!-- <div class="l-bar-separator"></div>
					<div class="group"><a class="link update" id="btnUpd" action="edit.ht"><span></span>修改</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link del"  action="del.ht"><span></span>删除</a></div> -->
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht">
					<div class="row">
						<c:if test="${alias==null || (alias!=null && alias=='bpm_hr_manager')}">
							<span class="label">姓名:</span><input type="text" name="Q_fullname_SL"  class="inputText" value="${param['Q_fullname_SL']}"/>
							<span class="label">部门:</span><input type="text"  name="Q_org_SL" value="${param['Q_org_SL']}"  class="inputText" />
						</c:if>
						<span class="label">类型:</span>
						<select name="Q_type_S" value="${param['Q_type_S']}">
							<option value="">--选择--</option>
							<option value="因公办事" <c:if test="${param['Q_type_S']=='因公办事'}">selected</c:if>>因公办事</option>
							<option value="年假" <c:if test="${param['Q_type_S']=='年假'}">selected</c:if>>年假</option>
							<option value="调休" <c:if test="${param['Q_type_S']=='调休'}">selected</c:if>>调休</option>
							<option value="事假" <c:if test="${param['Q_type_S']=='事假'}">selected</c:if>>事假</option>
							<option value="病假" <c:if test="${param['Q_type_S']=='病假'}">selected</c:if>>病假</option>
						</select>
						<span class="label">缺勤时间:</span> <input  name="Q_beginleave_time_DL"  class="inputText date" value="${param['Q_beginleave_time_DL']}"/>
						<span class="label">至: </span><input  name="Q_endleave_time_DL" class="inputText date"  value="${param['Q_endleave_time_DL']}"/>
						<span class="label">登记时间:</span> <input  name="Q_begincreate_time_DL"  class="inputText date" value="${param['Q_begincreate_time_DL']}"/>
						<span class="label">至: </span><input  name="Q_endcreate_time_DL" class="inputText date" value="${param['Q_endcreate_time_DL']}"/>
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="workLeaveList" export="true" id="workLeaveItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="table-grid">
				<display:column property="fullname" title="姓名" sortable="true" sortName="F_FULLNAME"></display:column>
				<display:column property="org" title="部门" sortable="true" sortName="F_org"></display:column>
				<display:column  title="登记时间" sortable="true" sortName="F_CREATE_TIME">
					<fmt:formatDate value="${workLeaveItem.create_time}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</display:column>
				<display:column  title="缺勤时间" sortable="true" sortName="F_LEAVE_TIME">
					<fmt:formatDate value="${workLeaveItem.leave_time}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column title="缺勤范围" sortable="true" sortName="F_LEAVE_RANGE">
					<c:choose>
						<c:when test="${workLeaveItem.leave_range==0}">全天</c:when>
						<c:when test="${workLeaveItem.leave_range==1}">
							上午
						</c:when>
						<c:otherwise>
							下午
						</c:otherwise>
					</c:choose>
				</display:column>
				<display:column property="type" title="类型" sortable="true" sortName="F_leave_range">
					<c:if test="${workLeaveItem.leave_range==0}"></c:if>
				</display:column>
				<display:column property="remark" title="备注" sortable="true" sortName="F_REMARK"></display:column>
				<display:column title="管理" media="html" style="width:100px">
					<a href="get.ht?id=${workLeaveItem.id}" class="link detail"><span></span>明细</a>
				</display:column>
			</display:table>
			<hotent:paging tableId="workLeaveItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


