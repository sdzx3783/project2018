<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>文档模板</title>
<%@include file="/commons/include/get.jsp"%>
<%@ taglib prefix="hotent" uri="http://www.jee-soft.cn/paging" %>
</head>
<script type="text/javascript">
	function addtemplate(){
		var obj=$.ligerDialog.open({ url: 'edit.ht' ,title:'新建',width:400,height: 400, isResize:true });
		return true;
	}
	function edittemplate(id){
		var obj=$.ligerDialog.open({ url: 'edit.ht?id='+id ,title:'新建',width:400,height: 400, isResize:true });
		return true;
	}
</script>
<body class="oa-mw-page">

    <div id="oa_list_title" class="oa-mgb20 oa-project-title">
        <h2>文档模板</h2>
    </div>

    <div id="oa_list_operation" class="oa-pdb20 oa-mgh20 oa-mgl20">
        <a href="${ctx}/makshi/doc/rule/list.ht" class="oa-button oa-button--primary oa-button--blue">规则管理</a>
        <a href="javascript:;" class="oa-button oa-button--primary oa-button--blue" onclick="addtemplate()">上传模板</a>
    </div>

    <div class="oa-pdb20 oa-mgh20">
        <div class="oa-table-scroll-wrap" id="oa_list_content">	
        	<display:table name="templatelist" id="templateItem" requestURI="list.ht" sort="external" class="oa-table--default oa-table--nowrap">
    			<display:column title="模板名称">
    				<a href="${ctx }/makshi/doc/template/detail.ht?id=${templateItem.id}">${templateItem.name}</a>
    			</display:column>
    			<display:column title="创建人">
    				${templateItem.creator}
    			</display:column>
    			<display:column title="时间">
    				<fmt:formatDate value='${templateItem.ctime}' pattern='yyyy-MM-dd' />
    			</display:column>
    			<display:column title="管理" media="html">
    				<a href="del.ht?id=${templateItem.id}" class="oa-button-label del">删除</a>
    				<a href="javascript:;" class="oa-button-label" onclick="edittemplate('${templateItem.id}')">编辑</a>
    			</display:column>
    		</display:table>
        </div>
    	<hotent:paging tableId="templateItem" />
    </div>  

</body>
</html>


