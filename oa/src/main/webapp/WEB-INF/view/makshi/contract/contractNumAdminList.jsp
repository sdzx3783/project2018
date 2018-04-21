<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>合同编号管理管理</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body class="oa-mw-page">

	<div id="oa_list_title" class="oa-mgb20 oa-project-title">
        	<!-- <h2>合同编号管理列表</h2> -->
   	</div>
   		
  <!--  	<div id="oa_list_operation" class="oa-pdb20 oa-mgh20 oa-mgl20">
		 <a class="oa-button oa-button--primary oa-button--blue" href="edit.ht" target="_blank">添加</a>
		 <a class="oa-button oa-button--primary oa-button--blue del" action="del.ht">删除</a>
   	</div> -->
    
    <div id="oa_list_search" class="oa-pdb10 oa-mgh20">
        <form id="searchForm" method="post" action="list.ht" class="oa-clear">
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">合同年份</div>
                <div class=" oa-mgl100">
                    <select name="year" value="${param['year']}" class="oa-new-select">
	   					<c:forEach begin="2016" end="${curYear }" var="e">
   							<option <c:if test="${fn:contains(param['year'],(curYear-e+2016))}">selected</c:if> >${curYear-e+2016 }</option>
	   					</c:forEach>
	   				</select>
                </div>
            </div>
            <div class="oa-fl oa-mgb10 oa-mgh20">
	                <button class="oa-button oa-button--primary oa-button--blue">查询</button>
	            </div>
	        </form>
    	</div>
    	
    <div class="oa-pdb20 oa-mgh20">
      	<div id="oa_list_content" class="oa-table-scroll-wrap">	
    	<c:set var="startNum" value="${(pageBeancontractNumAdminItem.currentPage-1)*pageBeancontractNumAdminItem.pageSize+1}"></c:set>
		<display:table name="contractNumAdminList" id="contractNumAdminItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
			<display:column title="序号"> 
			    	<c:out value="${startNum}"/>
					<c:set var="startNum" value="${startNum+1}"/>
		    </display:column>
			<display:column title="合同类型" >
					${contractNumAdminItem.type}
			</display:column>
			<display:column title="合同编号" >
					${contractNumAdminItem.contract_num}
			</display:column>
			<display:column title="流水号" >
					${contractNumAdminItem.flowNo}
			</display:column>
				<display:column title="管理" media="html" >
					<a href="del.ht?id=${contractNumAdminItem.id}" class="del">删除</a>
					<a onclick="editByYear(${contractNumAdminItem.id})" href="javascript:void(0);" class="edit"><span></span>编辑</a>
					<a href="get.ht?id=${contractNumAdminItem.id}" target="_blank" class="detail"><span></span>明细</a>
				</display:column>
		</display:table>
		</div><!-- end of panel-body -->				
		<%-- <hotent:paging tableId="contractNumAdminItem"/> --%>
	</div> <!-- end of panel -->
</body>
<script type="text/javascript">
	function editByYear(obj){
		var year = $("select[name='year']").val();
		href ="edit.ht?id="+obj+"&year="+year;
		location.href = href;
	}
</script>
</html>


