<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>单位汇总表</title>
<%@include file="/commons/include/get.jsp"%>
<f:link href="Aqua/css/ligerui-all.css" ></f:link>
    <f:link href="tree/zTreeStyle.css" ></f:link>
	<%@ taglib prefix="hotent" uri="http://www.jee-soft.cn/paging" %>
</head>
<body class="oa-mw-page">
    <div id="oa_list_title" class="oa-mgb10 oa-project-title">
        <!-- <h2>单位会员汇总表</h2> -->
    </div>
	<div id="oa_list_operation" class="oa-mgh20 oa-pdb10 oa-mgl20">
		<a class="oa-button oa-button--primary oa-button--blue add" href="edit.ht">添加</a>
		<a class="oa-button oa-button--primary oa-button--blue del"  action="del.ht">删除</a>
	</div>
    
	<div class="oa-pdb20 oa-mgh20">
		<div id="oa_list_content" class="oa-table-scroll-wrap">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="expenseList" id="expenseItem"  requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
				<display:column title="${checkAll}" media="html">
			  		<input type="checkbox" class="pk" name="id" value="${expenseItem.id}">
				</display:column>
				 <display:column property="id" title="编号" sortable="false" sortName="id"></display:column>
				 <display:column property="name" title="名称"  sortable="false" sortName="name"></display:column>
				 <display:column property="sorting" title="排序(越小越前)"  sortable="sorting" sortName="name"></display:column>
				 <display:column title="操作" media="html" style="width:220px">
					 <a href="edit.ht?id=${expenseItem.id}" class="oa-button-label"><span></span>编辑</a>
					 <a href="del.ht?id=${expenseItem.id}" class="oa-button-label del"><span></span>删除</a>
				</display:column>
			</display:table>			
		</div><!-- end of panel-body -->	
		<hotent:paging tableId="expenseItem"/>			
	</div> <!-- end of panel -->
</body>
</html>