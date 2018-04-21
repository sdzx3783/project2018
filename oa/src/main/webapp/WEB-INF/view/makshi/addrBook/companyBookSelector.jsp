<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>联系人管理</title>
<%@include file="/commons/include/get.jsp" %>
<script type="text/javascript" src="${ctx}/js/hotent/platform/bus/BusQueryRuleUtil.js" ></script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">联系人管理列表</span>
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="selector.ht">
					<ul class="row">
					<li>
						<span class="label">姓名:</span><input type="text" name="Q_fullname_SL"  class="inputText" value="${param['Q_fullname_SL']}" />
						<span class="label">邮箱:</span><input type="text" name="Q_email_SL"  class="inputText" value="${param['Q_email_SL']}" />
						&nbsp;<a href="javascript:;" onclick="$('#searchForm').submit()" class='button'><span>查询</span></a>
					</li>
					</ul>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="oaLinkmanList" id="oaLinkmanItem" requestURI="selector.ht" cellpadding="1" cellspacing="1" class="table-grid">
					<display:column title="${checkAll}" media="html" style="width:30px;">
				  		<input type="checkbox" class="pk" name="linkmandata" value="${oaLinkmanItem.userPosId}#${oaLinkmanItem.userName}#${oaLinkmanItem.email}#${oaLinkmanItem.company}">
					</display:column>
					<display:column property="userName" title="姓名"></display:column>
					<display:column property="email" title="邮箱"  ></display:column>
					<display:column property="mobile" title="电话"  ></display:column>
				    <div position="bottom"  class="bottom" style="margin-top:10px;"></div>	
			</display:table>
			<hotent:paging tableId="oaLinkmanItem"/>
		</div><!-- end of panel-body -->
			
	</div> <!-- end of panel -->
</body>
</html>


