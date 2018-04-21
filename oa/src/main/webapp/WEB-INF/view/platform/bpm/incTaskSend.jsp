<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://www.jee-soft.cn/functions" %>
<div class="noprint">
	<div class="panel-toolbar">
		<div class="toolBar">
						<div class="group"><a id="sendDoc" href="#" class="link" ><span></span>归档</a></div>
						<div class="l-bar-separator"></div>
		</div>	
	</div>
</div>
<script type="text/javascript">
$("#sendDoc").click(function(){
	var runId = $("#runId").val();
	var businessKey = $("#businessKey").val();
	$.ligerDialog.open({ url: '/makshi/dispatch/dispatch/setSendPerson.ht?runId='+runId+'&businessKey='+businessKey,title:'选择人员',width:600,height: 600, isResize:true});
});

</script>