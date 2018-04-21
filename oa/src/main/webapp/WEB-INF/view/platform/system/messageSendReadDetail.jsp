<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<%@include file="/commons/include/get.jsp" %>
</head>
<body class="oa-mw-page">

	<c:if test="${canReply==1}">
		<f:tab curTab="readDetail" tabName="msgSend"/>
	</c:if>

	<div class="oa-pdb20 oa-mgh20">
	    <div class="oa-table-scroll-wrap">
			<display:table name="readlist" id="readItem" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">						
				<display:column property="receiver" title="读信人"></display:column>
				<display:column title="读信时间">
					<fmt:formatDate value="${readItem.receiveTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</display:column>
			</display:table>
	    </div>
	</div>

</body>
</html>
