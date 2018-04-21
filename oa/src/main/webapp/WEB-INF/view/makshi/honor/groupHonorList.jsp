<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>集体荣誉管理</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<script type="text/javascript">
	$(function(){
		$('.oa-accordion').oaAccordion({collapse: true});
	});
</script>
<body class="oa-mw-page">
	<div id="oa_list_title" class="oa-mgb10 oa-project-title">
       <!--  <h2>集体荣誉管理列表</h2> -->
   	</div>
	<c:if test="${isEditor}">
		<div id="oa_list_operation" class="oa-mgh20 oa-mgl20">
			 <a class="oa-button oa-button--primary oa-button--blue" href="edit.ht" target="_blank">添加</a>
			 <a class="oa-button oa-button--primary oa-button--blue del" action="del.ht">删除</a>
	   	</div>
	</c:if>
	<div id="oa_list_search" class="oa-pdb10 oa-mgh20">
		    <div class="oa-accordion">
	            <div class="oa-accordion__title">查询条件</div>
	            <div class="oa-accordion__content">
         	        <form id="searchForm" method="post" action="list.ht" class="oa-clear">
			            <div class="oa-fl oa-mgb10">
			                <div class="oa-label">荣誉编号</div>
			                <div class="oa-input-wrap oa-mgl100">
			                    <input class="oa-input"  name="honor_num"   value="${param['honor_num']}"/>
			                </div>
			            </div>
			            <div class="oa-fl oa-mgb10">
			                <div class="oa-label">奖项名称</div>
			                <div class="oa-input-wrap oa-mgl100">
			                    <input class="oa-input"  name="honor_name"   value="${param['honor_name']}"/>
			                </div>
			            </div>
			            <div class="oa-fl oa-mgb10">
			                <div class="oa-label">奖项级别</div>
			                <div class="oa-input-wrap oa-mgl100">
			                    <input class="oa-input"  name="honor_level"   value="${param['honor_level']}"/>
			                </div>
			            </div>
						<div class="oa-fl oa-mgb10">
			                <div class="oa-label">获奖项目</div>
			                <div class="oa-input-wrap oa-mgl100">
			                    <input class="oa-input"  name="award_project"   value="${param['award_project']}"/>
			                </div>
			            </div>
			            <div class="oa-fl oa-mgb10">
			                <div class="oa-label">发证机构</div>
			                <div class="oa-input-wrap oa-mgl100">
			                    <input class="oa-input"  name="issuing_authority"   value="${param['issuing_authority']}"/>
			                </div>
			            </div>
			            <div class="oa-fl oa-mgb10">
			                <div class="oa-label">发形式</div>
			                <div class="oa-input-wrap oa-mgl100">
			                    <input class="oa-input"  name="get_type"   value="${param['get_type']}"/>
			                </div>
			            </div>
			            <div class="oa-fl oa-mgb10">
		                <div class="oa-label">发证时间从</div>
		                <div class="oa-input-wrap oa-mgl100">
		                    <input  class="oa-input date"    name="beginissuing_date"   value="${param['beginissuing_date']}"/>
		                </div>
		           	    </div>
		                <div class="oa-fl oa-mgb10">
		                	<div class="oa-label">到</div>
		                	<div class="oa-input-wrap oa-mgl100">
		                   		<input class="oa-input date"  name="endissuing_date"   value="${param['endissuing_date']}"/>
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
    		<c:set var="startNum" value="${(pageBeangroupHonorItem.currentPage-1)*pageBeangroupHonorItem.pageSize+1}"></c:set>
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="groupHonorList" id="groupHonorItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1"  class="oa-table--default oa-table--nowrap">
				<display:column title="${checkAll}" media="html" >
			  		<input type="checkbox" class="pk" name="id" value="${groupHonorItem.id}">
				</display:column>
				<display:column title="序号"> 
			    	<c:out value="${startNum}"/>
					<c:set var="startNum" value="${startNum+1}"/>
			    </display:column>
				<display:column title="荣誉编号" >
					<span></span>${groupHonorItem.honor_num}
				</display:column>
				<display:column title="奖项名称" >
					${groupHonorItem.honor_name}
				</display:column>
				<display:column title="奖项级别" >
					${groupHonorItem.honor_level}
				</display:column>
				<display:column title="获奖项目" >
					${groupHonorItem.award_project}
				</display:column>
				<display:column title="发证机构" >
					${groupHonorItem.issuing_authority}
				</display:column>
				<display:column title="发证时间" >
					${groupHonorItem.issuing_date}
				</display:column>
				<display:column title="颁发形式" >
					${groupHonorItem.get_type}
				</display:column>
				<display:column title="管理" media="html" style="width:220px">
					<c:if test="${isEditor}">
						<a href="del.ht?id=${groupHonorItem.id}" class="del">删除</a>
						<a href="edit.ht?id=${groupHonorItem.id}" target="_blank" class="edit">编辑</a>
					</c:if>
					<a href="get.ht?id=${groupHonorItem.id}" target="_blank" class="edit">明细</a>
				</display:column>
			</display:table>			
		</div><!-- end of panel-body -->	
		<hotent:paging tableId="groupHonorItem"/>			
	</div> <!-- end of panel -->
</body>
</html>


