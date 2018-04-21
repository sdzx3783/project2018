<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>发文总表管理</title>
<%@include file="/commons/include/get.jsp" %>
<style>
	div.panel-top {
		margin-left: 0 !important;
		overflow: hidden;
	}
</style>
<script type="text/javascript">
	function startFlow(obj,id,runId){
		var linkObj=$(obj);
		if(!linkObj.hasClass('disabled')) {
			linkObj.addClass('disabled');
			$.post("/platform/bpm/task/startFlowForm.ht?runId="+runId,function(responseText){
				var obj = new com.hotent.form.ResultMessage(responseText);
				if (obj.isSuccess()) {
					$.ligerDialog.success("启动流程成功！", "成功", function(rtn) {
						this.close();
						window.location.href = "${ctx}/makshi/dispatch/dispatch/getMyDraft.ht";
					});
				} else {
					$.ligerDialog.error(obj.getMessage(),"提示信息");
					linkObj.removeClass("disabled");
				}
				
			});
		}
		
	}
</script>
</head>
<body>
	<div id="oa_list_title">
		<div class="panel-top">
			<div class="oa-mgt10 oa-mgh20 oa-mgb10">		    	
		        <a class="oa-button oa-button--primary oa-button--blue" target="_blank" href="/platform/bpm/task/startFlowForm.ht?defId=10000003390004">添加</a>
		    </div>
	    </div>
	</div>
	<div class="oa-pdb20 oa-mgh20">
        <div id="oa_list_content" class="oa-table-scroll-wrap">
            <c:set var="checkAll">
				<input type="checkbox" id="chkall" />
			</c:set>
            <display:table name="dispatchList" id="dispatchItem" requestURI="getMyDraftJson.ht" sort="external" class="oa-table--default oa-table--nowrap">
                <display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${dispatchItem.id}">
				</display:column>
				<display:column title="发文标题">${dispatchItem.title}</display:column>
				<display:column title="发文编号">${dispatchItem.dispatch_id}</display:column>
				<display:column title="发文类型">${dispatchItem.type}</display:column>
				<display:column title="拟稿日期">
				<fmt:formatDate value="${dispatchItem.time}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column title="拟稿人">${dispatchItem.draft_person}</display:column>
				<display:column title="状态">
					草稿
				</display:column>
				<display:column title="管理" media="html" style="width:220px">
					<c:if test="${dispatchItem.runId!=0}">
						<a href="del.ht?id=${dispatchItem.id}" class="link del">删除</a>
						<a href="/platform/bpm/task/startFlowForm.ht?runId=${dispatchItem.runId}" target="_blank"><span></span>提交</a>
					</c:if>
				</display:column>                                                                            
            </display:table>
        </div>
        <hotent:paging tableId="dispatchItem" />
    </div>
</body>
</html>


