<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>文档目录日志列表</title>
<%@include file="/commons/include/get.jsp"%>
<%@ taglib prefix="hotent" uri="http://www.jee-soft.cn/paging"%>
<style>
	body {
	    margin: 0;
	}
	.table-grid {
	    margin-top: 0;
	    border: 1px solid #dadfed;
	}
	.table-grid.table-list th,
	.table-grid.table-list td{
		border: 1px solid #dadfed;
	}
</style>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			
		</div>

		<div class="panel-body">
			
			<display:table name="docLogList" id="docLog"
				requestURI="docloglist.ht?type=${type}" sort="external"
				cellpadding="1" cellspacing="1" class="table-grid">
				<c:if test="${type==1 }">
					<display:column title="客户端IP" style="width:70px">
						${docLog.ip}
					</display:column>
				</c:if>
				<c:if test="${type==0 }">
					<display:column title="文件名" style="width:70px">
						${docLog.title}
					</display:column>
				</c:if>
				<c:if test="${type==1 }">
					<display:column title="标题" style="width:70px">
						${docLog.title}
					</display:column>
				</c:if>
				<c:if test="${type==0 }">
					<display:column title="路径" style="width:70px">
						${docLog.pathname}
					</display:column>
				</c:if>
				<display:column title="操作人" style="width:70px">
					${docLog.userName}
				</display:column>
				<display:column title="操作时间" style="width:70px">
					<fmt:formatDate value='${docLog.ctime}' pattern='yyyy/MM/dd HH:mm' />
				</display:column>
				<display:column title="操作类型" style="width:70px">
					<c:if test='${docLog.operType==0 }'>新增</c:if>
					<c:if test='${docLog.operType==1 }'>修改</c:if>
					<c:if test='${docLog.operType==2 }'>删除</c:if>
					<c:if test='${docLog.operType==3 }'>还原</c:if>
				</display:column>

			</display:table>
			<hotent:paging tableId="docLog" />
		</div>
		<!-- end of panel-body -->
	</div>
</body>
</html>


