<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>银行账户表管理</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body>
	<div id="oa_list_title" class="oa-mgb20 oa-project-title">
	    <h2>银行账户表管理列表</h2>
	</div>
	<div id="oa_list_search" class="oa-pdb10 oa-mgh20">
		<a class="oa-button-label" href="edit.ht"><span></span>添加</a>
	</div>
		
		<div class="oa-pdb20 oa-mgh20">
	    <div id="oa_list_content" class="oa-table-scroll-wrap">
		    <display:table name="bankandaccountList" id="bankandaccountItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
				<display:column title="开户银行"><span>${bankandaccountItem.bankName}</span></display:column>
				<display:column title="开户账户"><span>${bankandaccountItem.bankAccount}</span></display:column>
				<display:column title="管理" media="html">
					<a href="del.ht?id=${bankandaccountItem.id}" class="oa-button-label"><span></span>删除</a>
					<a href="edit.ht?id=${bankandaccountItem.id}" class="oa-button-label"><span></span>编辑</a>
				</display:column>
			</display:table>
	    </div>
	    <hotent:paging tableId="bankandaccountItem"/>
	</div> <!-- end of panel -->
</body>
</html>


