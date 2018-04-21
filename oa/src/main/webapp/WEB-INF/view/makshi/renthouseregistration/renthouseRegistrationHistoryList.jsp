<%--
	time:2011-11-28 10:17:09
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户变更历史</title>
<%@include file="/commons/include/get.jsp" %>
	<script type="text/javascript" src="${ctx}/js/util/form.js"></script>


<script type="text/javascript">
	/*KILLDIALOG*/
	var dialog =null;
	try{
		dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)
	}catch(e){
		
	}
	$(function() {
		var h = $('body').height();
		$("#tabMyInfo").ligerTab({
		//	height : h - 60
		});
	});
	
</script>
</head>
<body>
	<div class="panel-top">
			<div class="panel-toolbar">
				<div class="toolBar">
					<!-- <div class="group"><a class="link save" id="dataFormSave" href="javascript:;"><span></span>保存</a></div>
					<div class="l-bar-separator"></div> -->
					<div class="group"><a class="link back" href="${returnUrl}"><span></span>返回</a></div>
				</div>
			</div>
		</div>
	<div id="tabMyInfo" style="overflow: hidden; position: relative;">

		<div title="变更历史" tabid="userdetail"
			icon="${ctx}/styles/default/images/resicon/user.gif">
			<div class="panel-detail">
				<display:table  name="changeHisList" id="changeHisItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="table-grid">
								
				<display:column title="变更字段  " style="width:70px">
					${changeHisItem.field}
				</display:column>
				<display:column title="变更前"   style="width:70px">
					${changeHisItem.before}
				</display:column>
				<display:column title="变更后"   style="width:70px">
					${changeHisItem.after}
				</display:column>
				
				
				
				<%-- <display:column title="管理" media="html" style="width:220px">
					<c:if test="${changeHisItem.runId==0}">
						<a href="del.ht?id=${changeHisItem.id}" class="link del"><span></span>删除</a>
						<a href="javascript:;" onclick="$.openFullWindow('${ctx}/platform/bpm/task/startFlowForm.ht?defId=10000002200654&businessKey=${changeHisItem.id}');" class="link run"><span></span>提交</a>
					</c:if>
					<a href="edit.ht?id=${changeHisItem.id}" class="link edit"><span></span>编辑</a>
					<a href="get.ht?id=${changeHisItem.id}" class="link detail"><span></span>明细</a>
				</display:column> --%>
			</display:table>
			</div>
		</div>
		
		
		
	
		
	</div>
</body>
</html>
