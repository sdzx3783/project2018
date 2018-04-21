<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>资产维护表管理</title>
<%@include file="/commons/include/get.jsp" %>
<script type="text/javascript">
	function startFlow(obj,id,runId){
		var linkObj=$(obj);
		if(!linkObj.hasClass('disabled')) {
			linkObj.addClass('disabled');
			$.post("run.ht?isList=1&id="+id+"&runId="+runId,function(responseText){
				
				var obj = new com.hotent.form.ResultMessage(responseText);
				if (obj.isSuccess()) {
					$.ligerDialog.success("启动流程成功！", "成功", function(rtn) {
						this.close();
						window.location.href = "${ctx}/makshi/asset_repair/assetRepair/getMyDraft.ht";
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
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">资产维护表管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link add" href="edit.ht"><span></span>添加</a></div>
				</div>	
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="assetRepairList" id="assetRepairItem" requestURI="getMyDraftJson.ht" sort="external" cellpadding="1" cellspacing="1" class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${assetRepairItem.id}">
				</display:column>
				<display:column title="管理" media="html" style="width:220px">
					<c:if test="${assetRepairItem.runId==0}">
						<a href="del.ht?id=${assetRepairItem.id}" class="link del"><span></span>删除</a>
						<a href="javascript:;" onclick="startFlow(this,'${assetRepairItem.id}','${assetRepairItem.runId}')" class="link run"><span></span>提交</a>
					</c:if>
				</display:column>
			</display:table>
			<hotent:paging tableId="assetRepairItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


