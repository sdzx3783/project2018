<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>文档模板</title>
<%@include file="/commons/include/get.jsp"%>
<%@ taglib prefix="hotent" uri="http://www.jee-soft.cn/paging" %>
</head>
<body class="oa-mw-page">

    <div id="oa_list_title" class="oa-mgb20 oa-project-title">
        <h2>文档模板</h2>
    </div>

    <div id="oa_list_operation" class="oa-pdb20 oa-mgh20 oa-mgl20">
        <a href="${ctx }/makshi/doc/template/list.ht" class="oa-button oa-button--primary oa-button--blue" onclick="addStage()">模板管理</a>
        <a href="${ctx }/makshi/doc/rule/edit.ht" class="oa-button oa-button--primary oa-button--blue">新建规则</a>
    </div>

    <div class="oa-pdb20 oa-mgh20">
    	<div class="oa-table-scroll-wrap" id="oa_list_content">  	
        	<display:table name="ruleManagerList" id="ruleManagerItem" class="oa-table--default oa-table--nowrap">
        		<display:column title="规则名称">
                    <a href="${ctx }/makshi/doc/rule/edit.ht?id=${ruleManagerItem.id}">${ruleManagerItem.name}</a>
        		</display:column>
        		<display:column title="创建人">
        			${ruleManagerItem.creator}
        		</display:column>
        		<display:column title="时间">
        			<fmt:formatDate value='${ruleManagerItem.ctime}' pattern='yyyy-MM-dd' />
        		</display:column>
        		<display:column title="管理" media="html">
        			<a href="del.ht?id=${ruleManagerItem.id}" class="oa-button-label del">删除</a>
        			<a href="${ctx }/makshi/doc/rule/edit.ht?id=${ruleManagerItem.id}" class="oa-button-label">编辑</a>
        		</display:column>
        	</display:table>
        </div>
    	<hotent:paging tableId="ruleManagerItem" />
    </div>

</body>
</html>


