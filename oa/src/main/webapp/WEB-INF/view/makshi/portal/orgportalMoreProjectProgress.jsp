<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>项目进程列表</title>
<%@include file="/commons/include/get.jsp"%>
<%@ taglib prefix="hotent" uri="http://www.jee-soft.cn/paging"%>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="l-bar-separator"></div>
					<div class="group">
						<a class="link back" href="/makshi/portal/orgportal/index.ht?orgId=${orgId }"><span></span>返回</a>
					</div>
				</div>
			</div>
			<div class="panel-search" >
				<form id="searchForm" method="post" action="moreProjectProgress.ht?orgId=${orgId}">
						<p>
						<span class="label">项目名称:</span><input type="text" name="Q_projectName_SL" value="${param['Q_projectName_SL'] }"  class="inputText" />
						<span class="label">任务名称:</span><input type="text" name="Q_taskName_SL" value="${param['Q_taskName_SL'] }"  class="inputText" />
                          <span class="label">提交时间从:</span><input type="text" id="Q_beginsubmitDate_DL" name="Q_beginsubmitDate_DL"  class="inputText Wdate" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'Q_endsubmitDate_DG\');}'})" value="${param['Q_beginsubmitDate_DL']}"/>
                          <span class="label">至</span><input type="text" id="Q_endsubmitDate_DG" name="Q_endsubmitDate_DG"  class="inputText Wdate" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'Q_beginsubmitDate_DL\');}'})"  value="${param['Q_endsubmitDate_DG']}"/>
							 <a class="link search button" id="btnSearch"><span class="icon-location-arrow"></span><span>查询</span></a>
						</p>
				</form>
			</div>
		</div>

		<div class="panel-body">
			<c:set var="checkAll">
				<input type="checkbox" id="chkall" />
			</c:set>
			<display:table name="taskLogslist" id="taskLog"
				requestURI="moreProjectProgress.ht?orgId=${orgId}" sort="external"
				cellpadding="1" cellspacing="1" class="table-grid">
				<display:column title="项目名称" style="width:70px">
					${taskLog.projectName}
				</display:column>
				<display:column title="任务名称" style="width:70px">
					<a href="/makshi/project/project/taskDetail.ht?id=${taskLog.taskid}&type=4&portalOrgId=${orgId }">${taskLog.taskName}</a>
				</display:column>
				<display:column title="提交人" style="width:70px">
					${taskLog.submittor}
				</display:column>
				<display:column title="提交时间" style="width:70px">
					<fmt:formatDate value='${taskLog.ctime}' pattern='yyyy/MM/dd' />
				</display:column>
				<display:column title="状态" style="width:70px">
					${taskLog.status}
				</display:column>
			</display:table>
			<hotent:paging tableId="taskLog" />
		</div>
		<!-- end of panel-body -->
	</div>
</body>
</html>


