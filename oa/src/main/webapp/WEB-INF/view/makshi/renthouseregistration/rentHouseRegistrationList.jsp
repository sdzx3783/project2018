<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>租房登记表管理</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body class="oa-mw-page">
     <div id="oa_list_title" class="oa-mgb10 oa-project-title">
        <!-- <h2>租房登记表管理列表</h2> -->
  	 </div>
  	 <div id="oa_list_operation" class="oa-pdb20 oa-mgh20 oa-mgl20">
		 <a class="oa-button oa-button--primary oa-button--blue" href="edit.ht">添加</a>
		 <a class="oa-button oa-button--primary oa-button--blue del" action="del.ht">删除</a>
		 <a onclick="tosubmit('2')" class="oa-button oa-button--primary oa-button--blue" id="btnSearch">导出</a>
   	</div>
	<div id="oa_list_search" class="oa-pdb10 oa-mgh20">
        <div class="oa-accordion">
            <div class="oa-accordion__title">查询条件</div>
            <div class="oa-accordion__content">
                <form id="searchForm" method="post" action="list.ht" class="oa-clear">
                    <div class="oa-fl oa-mgb10">
                        <div class="oa-label">租房编号</div>
                        <div class="oa-input-wrap oa-mgl100">
                            <input class="oa-input"  name="house_id"   value="${param['house_id']}"/>
                        </div>
                    </div>
                    <div class="oa-fl oa-mgb10">
                        <div class="oa-label">承租人</div>
                        <div class="oa-input-wrap oa-mgl100">
                            <input class="oa-input"  name="rent_person"   value="${param['rent_person']}"/>
                        </div>
                    </div>
                    <div class="oa-fl oa-mgb10">
                        <div class="oa-label">房屋面积</div>
                        <div class="oa-input-wrap oa-mgl100">
                            <input class="oa-input"  name="size"   value="${param['size']}"/>
                        </div>
                    </div>
                    <div class="oa-fl oa-mgb10">
                        <div class="oa-label">房租金额</div>
                        <div class="oa-input-wrap oa-mgl100">
                            <input class="oa-input"  name="money"   value="${param['money']}"/>
                        </div>
                    </div>
                    <div class="oa-fl oa-mgb10">
                        <div class="oa-label">房屋结构</div>
                        <div class="oa-input-wrap oa-mgl100">
                            <input class="oa-input"  name="house_type"   value="${param['house_type']}"/>
                        </div>
                    </div>
                    <div class="oa-fl oa-mgb10">
                        <div class="oa-label">住宿人数</div>
                        <div class="oa-input-wrap oa-mgl100">
                            <input class="oa-input"  name="number_people"   value="${param['number_people']}"/>
                        </div>
                    </div>
                    <div class="oa-fl oa-mgb10">
                        <div class="oa-label">房屋地址</div>
                        <div class="oa-input-wrap oa-mgl100">
                            <input class="oa-input"  name="address"   value="${param['address']}"/>
                        </div>
                    </div>
                    <div class="oa-fl oa-mgb10">
                        <div class="oa-label">部门</div>
                        <div class="oa-input-wrap oa-mgl100">
                            <input class="oa-input"  name="department"   value="${param['department']}"/>
                        </div>
                    </div>
                    <div class="oa-fl oa-mgb10 oa-mgh20">
                    	  <a onclick="tosubmit('1')" class="oa-button oa-button--primary oa-button--blue" id="btnSearch">查询</a>
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
            <display:table  name="rentHouseRegistrationList" id="rentHouseRegistrationItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
                <display:column title="${checkAll}" media="html">
                    <input type="checkbox" class="pk" name="id" value="${rentHouseRegistrationItem.id}">
                </display:column>
                <display:column title="租房编号 "><p title="${rentHouseRegistrationItem.house_id}">${rentHouseRegistrationItem.house_id}</p></display:column>
                <display:column title="承租人">${rentHouseRegistrationItem.rent_person}</display:column>
                <display:column title="部门">${rentHouseRegistrationItem.department}</display:column>
                <display:column title="房屋面积(平米)">${rentHouseRegistrationItem.size}</display:column>
                <display:column title="房租金额(元/月)">${rentHouseRegistrationItem.money}</display:column>
                <display:column title="住宿人数">${rentHouseRegistrationItem.number_people}</display:column>
                <display:column title="租房开始时间"><fmt:formatDate value="${rentHouseRegistrationItem.start_date}" pattern='yyyy-MM-dd'/></display:column>
                <display:column title="租房结束时间"><fmt:formatDate value="${rentHouseRegistrationItem.end_date}" pattern='yyyy-MM-dd'/></display:column>
                 <display:column title="租房地址">${rentHouseRegistrationItem.address}</display:column>
                <display:column title="操作" media="html">
                    <a class="oa-button-label" href="edit.ht?id=${rentHouseRegistrationItem.id}">编辑</a>
                    <a class="oa-button-label" href="historyList.ht?id=${rentHouseRegistrationItem.id}" >变更历史</a>
                    <a class="oa-button-label" href="/makshi/housereimburse/houseReimburse/list.ht?house_id=${rentHouseRegistrationItem.house_id}">报销记录</a>
                </display:column>
            </display:table>
        </div>            
            <hotent:paging tableId="rentHouseRegistrationItem"/>
    </div>

<script>
    $(function(){
        $('.oa-accordion').oaAccordion({collapse: true});
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


