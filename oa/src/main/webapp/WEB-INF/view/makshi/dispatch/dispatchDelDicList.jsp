<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 文档目录</title>
	<%@include file="/codegen/include/customForm.jsp" %>
	<%@include file="/commons/include/ueditor.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	<style type="text/css">
	.panel{
		width:500px;
	}
	</style>
</head>
<body>
<div class="panel" style="height:100%;overflow:auto;">
	<display:table  name="dicList" id="dicItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
				<display:column title="分类名称" >
					${dicItem.dicName}
				</display:column>
				<display:column title="操作" media="html">
					<a class="oa-button-label del" onclick = "docDel(${dicItem.dicId})" >删除</a>
				</display:column>
			</display:table>
</div>
<script type="text/javascript">
	function docDel(obj){
		$.ligerDialog.confirm("确定删除？","提示信息", function(rtn) {
			if(rtn){
				$.post("/makshi/dispatch/dispatch/delDic.ht",{id:obj},function(data){
					if(data.success=="true"){
						$.ligerDialog.warn("删除成功!", function(rtn) { location.reload();});
					}
				});
			}
		});
	}
</script>
</body>
</html>
