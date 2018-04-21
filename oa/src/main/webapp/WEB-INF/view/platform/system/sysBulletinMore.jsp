<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>公告表管理</title>
<%@include file="/commons/include/get.jsp" %>
<script type="text/javascript">
function setHasRead(obj){
	$tr = $(obj).closest("tr");
	$tr.find(".message").removeClass("close-message").addClass("open-message");
}
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<a class="link search" id="btnSearch"><span></span>查询</a>
					</div>
				</div>
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="more.ht">
					<div class="row">
						<span class="label">主题:</span><input type="text" name="Q_subject_SL" value="${param['Q_subject_SL']}" class="inputText" />
						<span class="label">创建人:</span><input type="text" name="Q_creator_SL"  value="${param['Q_creator_SL']}" class="inputText" />
						<span class="label">创建时间从:</span> <input  name="Q_begincreatetime_DL" value="${param['Q_begincreatetime_DL']}"  class="inputText date" />
						<span class="label">至: </span><input  name="Q_endcreatetime_DG" value="${param['Q_endcreatetime_DG']}"  class="inputText date" />
					</div>
					<input name="alias" value="${alias }" type="hidden"/>
				</form>
			</div>
		</div>
		<div class="panel-body">
		    <display:table name="sysBulletinList" id="sysBulletinItem" requestURI="more.ht" sort="external" cellpadding="1" cellspacing="1" class="table-grid">
				<display:column title="主题" sortable="true" sortName="SUBJECT">
					<span <c:if test="${sysBulletinItem.hasRead eq 0}">class='message close-message'</c:if><c:if test="${sysBulletinItem.hasRead ne 0}"> class='message open-message'</c:if> >${sysBulletinItem.subject }</span>
				</display:column>
				<display:column property="columnname" title="栏目" sortable="true" sortName="columnname"></display:column>
				<display:column property="creator" title="创建人" sortable="true" sortName="CREATOR"></display:column>
				<display:column  title="创建时间" sortable="true" sortName="CREATETIME">
					<fmt:formatDate value="${sysBulletinItem.createtime}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column title="管理" media="html" style="width:80px">
					<a href="javascript:;" onclick="setHasRead(this);$.openFullWindow('get.ht?id=${sysBulletinItem.id}');" class="link detail" ><span></span>查看</a>
				</display:column>
			</display:table>
			<hotent:paging tableId="sysBulletinItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


