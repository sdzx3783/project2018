<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>资产申购汇总表管理</title>
<%@include file="/commons/include/get.jsp" %>
<script type="text/javascript">
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">资产申购汇总表管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch"><span></span>查询</a></div>
					<div class="l-bar-separator"></div>
					
					<%-- <div class="group"><a class="link add" href="edit.ht"><span></span>添加</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link update" id="btnUpd" action="edit.ht"><span></span>修改</a></div>
				    <div class="l-bar-separator"></div>
					<div class="group"><a class="link run"  onclick="$.openFullWindow('${ctx}/platform/bpm/task/startFlowForm.ht?defId=10000001671574');"><span></span>启动流程</a></div>
					 --%>
				</div> 
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht">
					<div class="row">
					<ul class="row">
						<li>
							<span class="label">申请人</span>
							<input type="text" name="user_name" class="inputText"/>
						</li>
						<li>
							<span class="label">工单号</span>
							<input type="text" name="globalflowno" class="inputText"/>
						</li>
						<li>
							<span class="label">使用人</span>
							<input type="text" name="use_name" class="inputText"/>
						</li>
						<li>
							<span class="label">申购日期</span>
							<input type="text"  name="application_time_begin"  class="inputText date" validate="{date:true}" />
							<input type="text"  name="application_time_end"  class="inputText date" validate="{date:true}" /> 
						</li>
					</ul>
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body" >
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table export="true" name="assetAllList" id="assetAllItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${assetAllItem.id}">
				</display:column>
				
				<display:column title="工单号">
					${assetAllItem.globalflowno}
				</display:column>
				<display:column title="固定资产名称">
					${assetAllItem.asset_name}
				</display:column>
				<display:column title="申请人">
					${assetAllItem.user_name}
				</display:column>
				<display:column title="申请时间">
					<fmt:formatDate value="${assetAllItem.application_time }"   pattern="yyyy-MM-dd " type="date" dateStyle="long" />
				</display:column>
				<display:column title="使用人">
					${assetAllItem.use_name}
				</display:column>
				<display:column title="一级部门">
					${assetAllItem.fist_department}
				</display:column>
				<display:column title="二级部门或项目部">
					${assetAllItem.second_department}
				</display:column>
				<display:column title="审批状态">
					${assetAllItem.state}
				</display:column>
				
			<%-- 	<display:column title="管理" media="html" style="width:220px">
					<c:if test="${assetAllItem.runId==0}">
						<a href="del.ht?id=${assetAllItem.id}" class="link del"><span></span>删除</a>
						<a href="javascript:;" onclick="$.openFullWindow('${ctx}/platform/bpm/task/startFlowForm.ht?defId=10000001671574&businessKey=${assetAllItem.id}');" class="link run"><span></span>提交</a>
					</c:if>
					<a href="edit.ht?id=${assetAllItem.id}" class="link edit"><span></span>编辑</a>
					<a href="get.ht?id=${assetAllItem.id}" class="link detail"><span></span>明细</a>
				</display:column> --%>
			</display:table>
			<hotent:paging tableId="assetAllItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


