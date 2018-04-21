<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>党员档案管理</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">党员档案管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch"><span></span>查询</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link add" href="edit.ht"><span></span>添加</a></div>
					<div class="l-bar-separator"></div>
					
					
					<div class="group"><a class="link update" id="btnUpd" action="edit.ht"><span></span>修改</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link del"  action="del.ht"><span></span>删除</a></div>
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht">
					<ul class="row">
						<li><span class="label">工号:</span><input type="text" name="Q_account_SL"  class="inputText"  value="${param['Q_account_SL']}"/></li>
						<li><span class="label">现任党内职务:</span><input type="text" name="Q_party_post_SL"  class="inputText"  value="${param['Q_party_post_SL']}"/></li>
						<div class="row_date">
								<li><span class="label">入党时间从:</span><input type="text" id="Q_beginapplytime_DL" name="Q_beginapplytime_DL"  class="inputText Wdate" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'Q_endapplytime_DG\');}'})" value="${param['Q_beginapplytime_DL']}"/></li>
								<li><span class="label">至</span><input type="text" id="Q_endapplytime_DG" name="Q_endapplytime_DG"  class="inputText Wdate" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'Q_beginapplytime_DL\');}'})"  value="${param['Q_endapplytime_DG']}"/></li>
						</div>
					</ul>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table export="true" name="partyFilesList" id="partyFilesItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${partyFilesItem.id}">
				</display:column>
				
				<display:column title="党员编号" style="width:70px;">
			  		${partyFilesItem.party_num}
				</display:column>
				<display:column title="工号" style="width:70px;">
			  		${partyFilesItem.account}
				</display:column>
				<display:column title="入党时间" style="width:70px;">
					<fmt:formatDate value="${partyFilesItem.join_party_date}" pattern='yyyy-MM-dd'/>
			  		
				</display:column>
				<display:column title="转正时间" style="width:70px;">
					<fmt:formatDate value="${partyFilesItem.regular_date}" pattern='yyyy-MM-dd'/>
				</display:column>
				<display:column title="入党时所在支部" style="width:70px;">
			  		${partyFilesItem.join_party_where}
				</display:column>
				<display:column title="转正时所在支部" style="width:70px;">
			  		${partyFilesItem.regular_where}
				</display:column>
				<display:column title="入党介绍人" style="width:70px;">
			  		${partyFilesItem.introducer}
				</display:column>
				<display:column title="所在支部" style="width:70px;">
			  		${partyFilesItem.branch}
				</display:column>
				<display:column title="进入党支部日期" style="width:70px;">
					<fmt:formatDate value="${partyFilesItem.join_branch_date}" pattern='yyyy-MM-dd'/>
				</display:column>
				<display:column title="现任党内职务" style="width:70px;">
			  		${partyFilesItem.party_post}
				</display:column>
				<display:column title="组织关系所在单位" style="width:70px;">
			  		${partyFilesItem.org_relationship}
				</display:column>
				<display:column title="是否已转出" style="width:70px;">
			  		<c:if test='${partyFilesItem.isout==0}'>否</c:if>
			  		<c:if test='${partyFilesItem.isout==1}'>是</c:if>
				</display:column>
				
				<display:column title="创建人" style="width:70px;">
			  		${partyFilesItem.creator}
				</display:column>
				<display:column title="创建时间" style="width:70px;">
					<fmt:formatDate value="${partyFilesItem.create_time}" pattern='yyyy-MM-dd HH:mm:ss'/>
				</display:column>
				<display:column title="修改人" style="width:70px;">
			  		${partyFilesItem.updater}
				</display:column>
				<display:column title="修改时间" style="width:70px;">
					<fmt:formatDate value="${partyFilesItem.update_time}" pattern='yyyy-MM-dd HH:mm:ss'/>
				</display:column>
				
				<display:column title="管理" media="html" style="width:220px">
					<a href="del.ht?id=${partyFilesItem.id}" class="link del"><span></span>删除</a>
					<a href="edit.ht?id=${partyFilesItem.id}" class="link edit"><span></span>编辑</a>
					<%-- <a href="get.ht?id=${partyFilesItem.id}" class="link detail"><span></span>明细</a> --%>
				</display:column>
			</display:table>
			<hotent:paging tableId="partyFilesItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


