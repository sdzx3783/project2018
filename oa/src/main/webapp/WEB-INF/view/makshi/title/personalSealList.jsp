<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>个人执业印章管理</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body class="oa-mw-page">

		<div id="oa_list_title" class="oa-mgb20 oa-project-title">
        	<!-- <h2>个人执业印章管理列表</h2> -->
   		</div>

		<div id="oa_list_operation" class="oa-mgh20 oa-mgl20">
			 <a class="oa-button oa-button--primary oa-button--blue" href="edit.ht">添加</a>
			 <a class="oa-button oa-button--primary oa-button--blue del" action="del.ht">删除</a>
    	</div>

		<div id="oa_list_search" class="oa-pdb10 oa-mgh20">
		    <div class="oa-accordion">
	            <div class="oa-accordion__title">查询条件</div>
	            <div class="oa-accordion__content">
         	        <form id="searchForm" method="post" action="list.ht" class="oa-clear">
			            <div class="oa-fl oa-mgb10">
			                <div class="oa-label">印章编号</div>
			                <div class="oa-input-wrap oa-mgl100">
			                    <input class="oa-input"  name="seal_num"   value="${param['seal_num']}"/>
			                </div>
			            </div>
			            <div class="oa-fl oa-mgb10">
			                <div class="oa-label">印章名称</div>
			                <div class="oa-input-wrap oa-mgl100">
			                    <input class="oa-input"  name="seal_name"   value="${param['seal_name']}"/>
			                </div>
			            </div>
			            <div class="oa-fl oa-mgb10">
			                <div class="oa-label">当前借用人</div>
			                <div class="oa-input-wrap oa-mgl100">
			                    <input class="oa-input"  name="borrower"   value="${param['borrower']}"/>
			                </div>
			            </div>
						<div class="oa-fl oa-mgb10">
			                <div class="oa-label">持章人</div>
			                <div class="oa-input-wrap oa-mgl100">
			                    <input class="oa-input"  name="holder"   value="${param['holder']}"/>
			                </div>
			            </div>
			            <div class="oa-fl oa-mgb10">
			                <div class="oa-label">状态</div>
			                <div class="oa-input-wrap oa-mgl100">
			               		<select name="status" class="oa-select" value="${param['status']}">
									<option value="">请选择</option>
									<option value="1" <c:if test="${param['status']=='1'}">selected</c:if>>已借出</option>
									<option value="0" <c:if test="${param['status']=='0'}">selected</c:if>>未借出</option>
								</select>
			                </div>
			            </div>
			            <div class="oa-fl oa-mgb10 oa-mgh20">
			                <button class="oa-button oa-button--primary oa-button--blue">查询</button>
			            </div>
			        </form>    
	            </div>
		    </div>
		    
    	</div>
    
		<div class="oa-pdb20 oa-mgh20">
      	 <div id="oa_list_content" class="oa-table-scroll-wrap">
      	 	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
   			<display:table export="true" name="personalSealList" id="personalSealItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
				<display:column title="${checkAll}" media="html">
			  		<input type="checkbox" class="pk" name="id" value="${personalSealItem.id}">
				</display:column>
				<display:column title="印章编号" >
					${personalSealItem.seal_num}
				</display:column>
				<display:column title="印章名称" >
					${personalSealItem.seal_name}
				</display:column>
				<display:column title="当前借用人" >
					${personalSealItem.borrower}
				</display:column>
				
				<display:column title="持章人" >
					${personalSealItem.holder}
				</display:column>
				<display:column title="有效期" >
					<fmt:formatDate value="${personalSealItem.effictiveDate}" pattern='yyyy-MM-dd'  type="date" dateStyle="long"/>
				</display:column>
				<display:column title="借阅日期" >
					<fmt:formatDate value="${personalSealItem.borrowDate}" pattern='yyyy-MM-dd'  type="date" dateStyle="long"/>
				</display:column>
				<display:column title="状态">
					<c:if test="${personalSealItem.status==1}">已借出</c:if>
					<c:if test="${personalSealItem.status==0}">未借出</c:if>
				</display:column>
				<display:column title="备注" >
					${personalSealItem.remark}
				</display:column>
				<display:column title="操作" media="html">
					<a class="oa-button-label del" href="del.ht?id=${personalSealItem.id}" >删除</a>
					<a class="oa-button-label edit" href="edit.ht?id=${personalSealItem.id}" target = "_blank">编辑</a>
					<a class="oa-button-label detail" href="get.ht?id=${personalSealItem.id}" target = "_blank">明细</a>
				</display:column>
			</display:table>
		</div>
		<hotent:paging tableId="personalSealItem"/>
	</div>	

<script>
	$(function(){
		$('.oa-accordion').oaAccordion({collapse: true});
	});
</script>
</body>
</html>


