<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>执业证书</title>
<%@include file="/commons/include/get.jsp" %>
<style type="text/css">
	.l-dialog-content{min-height:460px !important;}
</style>
</head>
<body>      
	<div id="defLayout">
    	<div class="panel">
	        <div class="panel-body">
	                <display:table name="list" id="item" requestURI="dialogVocation.ht" sort="external" cellpadding="1" cellspacing="1" export="false"  class="table-grid">
	                    <display:column  style="width:30px;">
	               			<input type="radio" name="vocationId"  data-id="${item.id}" data-registid="${item.regist_id}" data-sealid="${item.seal_id}" value="${item.id}">
	                    </display:column>
	                    <display:column title="姓名">${item.name}</display:column>
	                    <display:column title="资格证书类型">${item.certificate_type}</display:column>
	                    <display:column title="注册号">${item.regist_id}</display:column>
	                    <display:column title="执业印章号">${item.seal_id}</display:column>
	                </display:table>
	        </div><!-- end of panel-body -->                
	    </div> <!-- end of panel -->
	</div>
	<script type="text/javascript">
	
	var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)、
	$("input[name='vocationId']").click(function(){
		var obj = {};
		obj.regist_id = $(this).data('registid');
		obj.seal_id = $(this).data('sealid');
		dialog.get("sucCall")(obj);
		dialog.close();
	});
	</script>
</body>

</html>


