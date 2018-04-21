<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>流程定义列表</title>
<%@include file="/commons/include/get.jsp" %>
<style type="text/css">
	.l-dialog-content{min-height:460px !important;}
</style>
</head>
<body>      
	<div id="defLayout">
    	<div class="panel">
	    	<div class="panel-top"> 
	            <div class="panel-toolbar">
	                <div class="toolBar">
	                    <div class="group">
	                    
	                    <a class="link search" id="btnSearch"><span></span>查询</a></div>
	                    <div class="l-bar-separator"></div>
	                </div>
	            </div>
	            <div class="panel-search">
	                <form id="searchForm" method="post" action="/makshi/dialog/bpmdefdialog/dialogDefs.ht">       
	                    <ul class="row">
	                     <li><span class="label">流程名称:</span>
	                     <input type="text" name="Q_subject_SL"  class="inputText" value="${param['Q_subject_SL']}"/></li>
	                    </ul>
	                </form>
	            </div>
	        </div>
	        <div class="panel-body">
	                <display:table name="list" id="item" requestURI="dialogDefs.ht" sort="external" cellpadding="1" cellspacing="1" export="false"  class="table-grid">
	                     <display:column  style="width:30px;">
	               			<input type="radio" name="dialogDefId"  data-id="${item.defId}" data-name="${item.subject}" value="${item.defId}">
	                    </display:column>
	                    <display:column title="流程编号">${item.defId}</display:column>
	                    <display:column title="流程名称">${item.subject}</display:column>
	                </display:table>
	                <hotent:paging tableId="bpmDefinitionItem" showExplain="false"/>
	        </div><!-- end of panel-body -->                
	    </div> <!-- end of panel -->
	</div>
	<script type="text/javascript">
	
	var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)、
	$("input[name='dialogDefId']").click(function(){
		var obj = {};
		obj.defId = $(this).data('id');
		obj.defName = $(this).data('name');
		dialog.get("sucCall")(obj);
		dialog.close();
	});
	</script>
</body>

</html>


