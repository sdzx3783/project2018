<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>租房报销记录表管理</title>
<%@include file="/commons/include/get.jsp" %>
<style type="text/css">
	.area-export{
	 display: none;
	}
</style>
</head>
<body class="oa-mw-page">

    <div id="oa_list_title" class="oa-mgb10 oa-project-title">
	    <!-- <h2>租房报销记录表管理</h2> -->
	</div>

    <div id="oa_list_operation" class="oa-pdb20 oa-mgh20 oa-mgl20">
		<a class="oa-button oa-button--primary oa-button--blue" href="add.ht">添加</a>
		<a class="oa-button oa-button--primary oa-button--blue del" action="del.ht">删除</a>
	    <a onclick="tosubmit('2')" class="oa-button oa-button--primary oa-button--blue" id="btnSearch">导出</a>
	</div>

    <div id="oa_list_search" class="oa-pdb10 oa-mgh20">
	
	    <div class="oa-accordion">
            <div class="oa-accordion__title">查询条件</div>
            <div class="oa-accordion__content">
			    <form id="searchForm" method="post" action="#" class="oa-clear">
			        <div class="oa-fl oa-mgb10">
			            <div class="oa-label">租房编号</div>
			            <div class="oa-input-wrap oa-mgl100">
			                <input type="text" name="house_id" class="oa-input" value="${param['house_id']}"/>
			            </div>
			        </div>
			        <div class="oa-fl oa-mgb10">
			            <div class="oa-label">报销人</div>
			            <div class="oa-input-wrap oa-mgl100">
							<input type="text" name="reimburse_person" class="oa-input"  value="${param['reimburse_person']}"/>
			            </div>
			        </div>
			        <div class="oa-fl oa-mgb10">
			            <div class="oa-label">费用期间</div>
			            <div class="oa-input-wrap oa-mgl100 oa-input-wrap--ib">
							<input type="text"  name="beginpay_start_date"  class="oa-input date" validate="{date:true}"  value="${param['beginpay_start_date']}"/>
			            </div>
						<span>至</span>
						<div class="oa-input-wrap oa-input-wrap--ib">
							<input type="text"  name="endpay_start_date" class="oa-input date" validate="{date:true}"  value="${param['endpay_start_date']}"/>
			            </div>
			        </div>
			        <%-- <div class="oa-fl oa-mgb10">
			            <div class="oa-label">房租金额</div>
			            <div class="oa-input-wrap oa-mgl100">
			                <input type="text" name="rent_money" class="oa-input"  value="${param['rent_money']}"/>
			            </div>
			        </div> --%>
			        <div class="oa-fl oa-mgb10">
			            <div class="oa-label">部门名称</div>
			            <div class="oa-input-wrap oa-mgl100">
							<input type="text" name="department" class="oa-input"  value="${param['department']}"/>
			            </div>
			        </div>
			        <a onclick="tosubmit('1')" class="oa-button oa-button--primary oa-button--blue" id="btnSearch">查询</a>
			         
			    </form>
            </div>
	    </div>
	</div>

    <div class="oa-pdb20 oa-mgh20">
	    <div id="oa_list_content" class="oa-table-scroll-wrap">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table  name="houseReimburseList" id="houseReimburseItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
		    	<display:column title="${checkAll}" media="html">
                    <input type="checkbox" class="pk" name="id" value="${houseReimburseItem.id}">
                </display:column>
				<display:column title="租房编号"><span></span>${houseReimburseItem.house_id}</display:column>
				<display:column title="报销人">${houseReimburseItem.reimburse_person}</display:column>
				<display:column title="报销日期">
					<fmt:formatDate value="${houseReimburseItem.reimburse_date}"  type="both" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column title="费用期间">
					<fmt:formatDate value="${houseReimburseItem.pay_start_date}"  type="both" pattern="yyyy-MM-dd"/> -- 
					<fmt:formatDate value="${houseReimburseItem.pay_end_date}"  type="both" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column title="部门名称">${houseReimburseItem.department}</display:column>
				<display:column title="房租金额">${houseReimburseItem.rent_money}</display:column>
				<display:column title="报销总金额">${houseReimburseItem.reimburse_money}</display:column>
				<display:column title="房屋结构">${houseReimburseItem.house_type}</display:column>
				<display:column title="操作" media="html">
					<a class="oa-button-label" href="edit.ht?id=${houseReimburseItem.id}" class="">编辑</a>
					<%-- <a class="oa-button-label" href="historyList.ht?id=${houseReimburseItem.id}" class="">变更历史</a> --%>
				</display:column>
			</display:table>
	    </div>

		<hotent:paging tableId="houseReimburseItem"/>

	</div>

<script>
	$(function(){
		$('.oa-accordion').oaAccordion({collapse: true});
		$('#btnSearch1').click(function(){
			 var hr = $("#divExportAll").find("a").attr("href") + "&exportAll=1";
			 location.href = hr;
		});
	});
	
	  function tosubmit(str){
			if(1==str){
				$("#searchForm").attr("action","list.ht");
				$("#searchForm").submit();
			}else if(2==str){
				  $.ligerDialog.confirm("确定导出?","提示",function(rtn){
					  $("#searchForm").attr("action","upload.ht");
					  $("#searchForm").submit();
				  });
			}
		}
	  
</script>
</body>
</html>

