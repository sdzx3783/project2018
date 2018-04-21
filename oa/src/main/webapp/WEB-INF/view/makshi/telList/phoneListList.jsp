<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>手机号码列表管理</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body class="oa-mw-page">

    <div id="oa_list_title" class="oa-mgb10 oa-project-title">
	    <!-- <h2>手机号码列表管理</h2> -->
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
				        <div class="oa-label">员工编号</div>
				        <div class="oa-input-wrap oa-mgl100">
				            <input type="text" name="user_id" class="oa-input"  value="${param['user_id']}"/>
				        </div>
				    </div>
				    <div class="oa-fl oa-mgb10">
				        <div class="oa-label">员工姓名</div>
				        <div class="oa-input-wrap oa-mgl100">
				            <input type="text" name="user_name" class="oa-input"  value="${param['user_name']}"/>
				        </div>
				    </div>
				    <div class="oa-fl oa-mgb10">
				        <div class="oa-label">部门</div>
				        <div class="oa-input-wrap oa-mgl100">
				            <input type="text" name="department" class="oa-input" value="${param['department']}"/>
				        </div>
				    </div>
				    <div class="oa-fl oa-mgb10">
				        <div class="oa-label">手机号码</div>
				        <div class="oa-input-wrap oa-mgl100">
				            <input type="text" name="phone_number" class="oa-input" value="${param['phone_number']}"/>
				        </div>
				    </div>
				    <div class="oa-fl oa-mgb10">
				        <div class="oa-label">短号</div>
				        <div class="oa-input-wrap oa-mgl100">
				            <input type="text" name="short_munber" class="oa-input" value="${param['short_munber']}"/>
				        </div>
				    </div>
				    <div class="oa-fl oa-mgb10">
				        <div class="oa-label">状态</div>
				        <div class="oa-select-wrap oa-mgl100">
				            <select class="oa-select" name="state">
								<option value="">--请选择--</option>
								<option value="0">在职</option>
								<option value="1">离职</option>
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
		    <display:table export="true" name="phoneListList" id="phoneListItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
				<display:column title="${checkAll}" media="html">
			  		<input type="checkbox" class="pk" name="id" value="${phoneListItem.id}">
				</display:column>
				<display:column title="员工编号">
				${phoneListItem.user_id}
				</display:column>
				<display:column title="员工姓名">
				${phoneListItem.user_name}
				</display:column>
				<display:column title="部门">
				${phoneListItem.department}
				</display:column>
				<display:column title="手机号码">
				${phoneListItem.phone_number}
				</display:column>
				<display:column title="短号">
				${phoneListItem.short_munber}
				</display:column>
				<display:column title="状态">
					<c:if test="${phoneListItem.state==-1}">-</c:if>
					<c:if test="${phoneListItem.state==0}">在职</c:if>
					<c:if test="${phoneListItem.state==1}">离职</c:if>
				</display:column>
				<display:column title="套餐">
				${phoneListItem.packages}
				</display:column>
				<display:column title="定额">
				${phoneListItem.limit}
				</display:column>
				<display:column title="限额">
				${phoneListItem.max_position}
				</display:column>
				<display:column title="备注">
				${phoneListItem.remarks}
				</display:column>
				<display:column title="操作" media="html">
					<a href="edit.ht?id=${phoneListItem.id}" class="oa-button-label"><span></span>编辑</a>
					<a href="historyList.ht?id=${phoneListItem.id}" class="oa-button-label"><span></span>变更历史</a>
				</display:column>
			</display:table>
	    </div>

		<hotent:paging tableId="phoneListItem"/>

	</div>

<script>
	$(function(){
		$('.oa-accordion').oaAccordion({collapse: true});
	});
</script>
</body>
</html>


