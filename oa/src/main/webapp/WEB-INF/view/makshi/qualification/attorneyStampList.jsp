<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>法定委托书盖章申请管理</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">法定委托书盖章申请管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch"><span></span>查询</a></div>
					<div class="l-bar-separator"></div>
					<!-- <div class="group"><a class="link add" href="edit.ht"><span></span>添加</a></div>
					<div class="l-bar-separator"></div> -->
					
					
					<!-- <div class="group"><a class="link update" id="btnUpd" action="edit.ht"><span></span>修改</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link del"  action="del.ht"><span></span>删除</a></div> -->
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht">
					<div class="row">
						<span class="label">申请人:</span><input type="text"  name="Q_applicant_S" value="${param['Q_applicant_S'] }" class="inputText" />
						<span class="label">申请部门:</span><input type="text" name="Q_org_S" value="${param['Q_org_S'] }"  class="inputText" />
						<span class="label">申请时间 从:</span> <input type="text" id="Q_beginsubmittime_DL" name="Q_beginsubmittime_DL" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'Q_endsubmittime_DL\');}'})"  value="${param['Q_beginsubmittime_DL'] }"  class="inputText Wdate" />
						<span class="label">至: </span><input type="text" id="Q_endsubmittime_DL" name="Q_endsubmittime_DL" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'Q_beginsubmittime_DL\');}'})" value="${param['Q_endsubmittime_DL'] }" class="inputText Wdate" />
						<span class="label">证书字号:</span><input type="text" name="Q_cerno_S"  value="${param['Q_cerno_S'] }" class="inputText" />
						<span class="label">姓名:</span><input type="text" name="Q_name_S" value="${param['Q_name_S'] }" class="inputText" />
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="attorneyStampList" id="attorneyStampItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="table-grid">
				<%-- <display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${attorneyStampItem.id}">
				</display:column> --%>
				<display:column property="cerno" title="证书字号" sortable="true"  sortName="F_CERNO"></display:column>
				<display:column property="applicant" title="申请人"  sortName="F_APPLICANT"></display:column>
				<display:column property="org" title="申请部门" sortName="F_ORG"></display:column>
				<display:column  title="申请时间"  sortName="F_SUBMITTIME">
					<fmt:formatDate value="${attorneyStampItem.submittime}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column property="name" title="姓名"  sortName="F_NAME"></display:column>
				<display:column property="runStatus" title="审批状态"  >${attorneyStampItem.runStatus }</display:column>
				<display:column title="管理" media="html" style="width:220px">
					<%-- <a href="del.ht?id=${attorneyStampItem.id}" class="link del"><span></span>删除</a>
					<a href="edit.ht?id=${attorneyStampItem.id}" class="link edit"><span></span>编辑</a> --%>
					<a href="get.ht?id=${attorneyStampItem.id}" class="link detail"><span></span>明细</a>
				</display:column>
			</display:table>
			<hotent:paging tableId="attorneyStampItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


