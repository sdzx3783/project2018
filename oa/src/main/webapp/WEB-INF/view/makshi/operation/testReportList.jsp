<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>运维部检测报告表管理</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">运维部检测报告表管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
					<!-- <div class="group"><a class="link add" href="edit.ht">添加</a></div>
					<div class="group"><a class="link update" id="btnUpd" action="edit.ht">修改</a></div>
					<div class="group"><a class="link del"  action="del.ht">删除</a></div> -->
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht">
					<div class="row">
						<ul class="row">
							<li>
								<span class="label">报告名称</span>
								<input type="text" name="name" class="inputText"   value="${param['name']}"/>
							</li>
							<li>
								<span class="label">项目名称</span>
								<input type="text" name="item" class="inputText"   value="${param['project']}"/>
							</li>
							<li>
								<span class="label">提交人</span>
								<input type="text" name="applicant" class="inputText"  value="${param['applicant']}"/>
							</li>
							<li>
								<span class="label">提交时间</span>
								<input type="text"  name="beginappliDate"  class="inputText date" validate="{date:true}"  value="${param['beginappliDate']}"/>
								<input type="text"  name="endappliDate"  class="inputText date" validate="{date:true}" value="${param['endappliDate']}"/> 
							</li>
						</ul>
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<%-- <c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set> --%>
		    <display:table name="testReportList" id="testReportItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="table-grid">
				<%-- <display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${testReportItem.id}">
				</display:column> --%>
				
				<display:column title="报告名称">${testReportItem.name}</display:column>
				<display:column title="项目名称">${testReportItem.project}</display:column>
				<display:column title="编写人员">${testReportItem.editor}</display:column>
				<display:column title="报告名称">${testReportItem.name}</display:column>
				<display:column title="报告名称">${testReportItem.name}</display:column>
				<display:column title="开始日期">
					<fmt:formatDate value='${testReportItem.startDate}' pattern='yyyy-MM-dd'/>
				</display:column>
				<display:column title="完成日期">
					<fmt:formatDate value='${testReportItem.endDate}' pattern='yyyy-MM-dd'/>
				</display:column>
				<display:column title="提交人">${testReportItem.applicant}</display:column>
				<display:column title="提交日期">
					<fmt:formatDate value='${testReportItem.appliDate}' pattern='yyyy-MM-dd'/>
				</display:column>
				<display:column title="打印日期">
					<fmt:formatDate value='${testReportItem.printDate}' pattern='yyyy-MM-dd'/>
				</display:column>
				<display:column title="盖章完成日期">
					<fmt:formatDate value='${testReportItem.sealDate}' pattern='yyyy-MM-dd'/>
				</display:column>
				<display:column title="提交业主日期">
					<fmt:formatDate value='${testReportItem.transferDate}' pattern='yyyy-MM-dd'/>
				</display:column>
				<display:column title="管理" media="html" style="width:10%">
					<%-- <a href="del.ht?id=${testReportItem.id}" class="link del">删除</a>
					<a href="edit.ht?id=${testReportItem.id}" class="link edit">编辑</a> --%>
					<a href="get.ht?id=${testReportItem.id}" class="link detail">详情</a>
				</display:column>
			</display:table>
			<hotent:paging tableId="testReportItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


