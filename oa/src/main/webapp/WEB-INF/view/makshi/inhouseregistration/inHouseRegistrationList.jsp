<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>租房入住人员登记表管理</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body class="oa-mw-page">
     <div id="oa_list_title" class="oa-mgb10 oa-project-title">
        <!-- <h2>租房入住人员登记表管理列表</h2> -->
  	 </div>
	 <div id="oa_list_operation" class="oa-pdb20 oa-mgh20 oa-mgl20">
		 <a class="oa-button oa-button--primary oa-button--blue" href="edit.ht">添加</a>
		 <a class="oa-button oa-button--primary oa-button--blue del" action="del.ht">删除</a>
   	 </div>		
	 <div id="oa_list_search" class="oa-pdb10 oa-mgh20">
        <div class="oa-accordion">
            <div class="oa-accordion__title">查询条件</div>
            <div class="oa-accordion__content">
                 <form id="searchForm" method="post" action="list.ht" class="oa-clear">
                    <div class="oa-fl oa-mgb10">
                        <div class="oa-label">租房编号</div>
                        <div class="oa-input-wrap oa-mgl100">
                            <input class="oa-input"  name="houseId"   value="${param['houseId']}"/>
                        </div>
                    </div>
                    <div class="oa-fl oa-mgb10">
                        <div class="oa-label">入住人员姓名</div>
                        <div class="oa-input-wrap oa-mgl100">
                            <input class="oa-input"  name="userName"   value="${param['userName']}"/>
                        </div>
                    </div>
                    <div class="oa-fl oa-mgb10">
                        <div class="oa-label">入住时间</div>
                        <div class="oa-input-wrap oa-mgl100 oa-input-wrap--ib">
                            <input type="text" name="begininDate"  class="oa-input Wdate"  value="${param['begininDate']}"/>
                        </div>
                    </div>
                    <div class="oa-fl oa-mgb10">
                        <div class="oa-label">离开时间</div>
                        <div class="oa-input-wrap oa-mgl100 oa-input-wrap--ib">
                            <input type="text" name="endleftDate"  class="oa-input Wdate"  value="${param['endleftDate']}"/>
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
	   		<display:table export="true" name="inHouseRegistrationList" id="inHouseRegistrationItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
	   			<display:column title="${checkAll}" media="html">
                    <input type="checkbox" class="pk" name="id" value="${inHouseRegistrationItem.id}">
                </display:column>
				<display:column title="租房编号"><span>${ inHouseRegistrationItem.house_id}</span></display:column>
				<display:column title="入住人员姓名">${inHouseRegistrationItem.user_name}</display:column>
				<display:column title="入住家属">${inHouseRegistrationItem.family}</display:column>
				<display:column title="入住时间">
					<fmt:formatDate value="${inHouseRegistrationItem.in_date}"  type="both" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column title="离开时间">
					<fmt:formatDate value="${inHouseRegistrationItem.left_date}"  type="both" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column title="操作" media="html">
					<a class="oa-button-label" href="edit.ht?id=${inHouseRegistrationItem.refId}" >编辑</a>
					<a class="oa-button-label" href="historyList.ht?id=${inHouseRegistrationItem.refId}" >变更历史</a>
				</display:column>
		    </display:table> 
		</div>			
		<hotent:paging tableId="inHouseRegistrationItem"/>
	</div>

<script>
    $(function(){
        $('.oa-accordion').oaAccordion({collapse: true});
    });
</script>
</body>
</html>


