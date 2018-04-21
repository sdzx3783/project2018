<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>刻章记录管理</title>
<%@include file="/commons/include/get.jsp" %>
<style type="text/css">
	.area-export{
	 display: none;
	}
</style>
</head>
<body>
	
    <div id="oa_list_title" class="oa-mgb10 oa-project-title">
	    <!-- <h2>刻章记录</h2> -->
	</div>
	<div id="oa_list_operation" class="oa-pdb20 oa-mgh20 oa-mgl20">
	    <a href="javascript:;" class="oa-button oa-button--primary oa-button--blue" id="btnSearch1">导出</a>
	</div>

    <div class="oa-pdb20 oa-mgh20">
	    <div id="oa_list_content" class="oa-table-scroll-wrap">
		    <display:table export="true"  name="makeChapterList" id="makeChapterItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
				<display:column title="刻章记录编号"><span></span>${makeChapterItem.id}</display:column>
				<display:column title="合同编号">${makeChapterItem.contract_id}</display:column>
				<display:column title="申请人">${makeChapterItem.application_person}</display:column>
				<display:column title="申请时间"><fmt:formatDate value="${makeChapterItem.application_time}"  type="both" pattern="yyyy-MM-dd"/></display:column>
				<display:column title="申请刻章事由">${makeChapterItem.reason}</display:column>
				<display:column title="项目名称">${makeChapterItem.project_name}</display:column>
				<display:column title="总投资额（万）">${makeChapterItem.total_investment}</display:column>
				<display:column title="申请印章名称">${makeChapterItem.chapter_name}</display:column>
				<display:column title="刻章类型">
					<c:if test="${makeChapterItem.chapter_type==1}">合同类</c:if>
					<c:if test="${makeChapterItem.chapter_type==0}">其他类</c:if>
				</display:column>
				<display:column title="要求到位时间">
					<fmt:formatDate value="${makeChapterItem.limit_date}"  type="both" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column title="保管人">${empty makeChapterItem.take_person ? makeChapterItem.application_person : makeChapterItem.take_person}</display:column>
				<display:column title="工程状态"></display:column>
				<display:column title="备注">${makeChapterItem.remarks}</display:column>
				<display:column title="审批状态">${makeChapterItem.state}</display:column>
                <display:column title="印章状态">
                    <c:if test="${makeChapterItem.ifSealDel == 0}">在用</c:if>
                    <c:if test="${makeChapterItem.ifSealDel == 1}">已注销</c:if>
                </display:column>
				<display:column title="操作" media="html">
                <a class="oa-button-label" href="get.ht?id=${makeChapterItem.id}">查看</a>
                <c:if test="${makeChapterItem.ifSealDel == 0}">
                    <a class="oa-button-label" href="newedit.ht?id=${makeChapterItem.id}">编辑</a>
                </c:if>
				</display:column>
			</display:table>
	    </div>

		<hotent:paging tableId="makeChapterItem"/>

	</div>
	<script type="text/javascript">
		$(function(){
			$('#btnSearch1').click(function(){
				$.ligerDialog.confirm("确定导出?","提示",function(rtn){
					var hr = $("#divExportAll").find("a").attr("href") + "&exportAll=1";
					 location.href = hr;
				 });
			});
		});
		
	</script>

</body>
</html>


