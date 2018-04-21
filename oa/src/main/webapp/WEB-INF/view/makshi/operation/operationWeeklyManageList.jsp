<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>运维部周报管理管理</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">运维部周报管理管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch">查询</a></div>
					<div class="l-bar-separator"></div>
						<c:if test="${isEditer }">
					<div class="group"><a class="link add" href="edit.ht">添加</a></div>
					</c:if>
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht">
					<div class="row">
						<ul class="row">
							<li>
								<span class="label">周报名称</span>
								<input type="text" name="name" class="inputText"   value="${param['name']}"/>
							</li>
							<li>
								<span class="label">片区</span>
								<input type="text" name="grop" class="inputText"   value="${param['grop']}"/>
							</li>
							<li>
								<span class="label">项目名称</span>
								<input type="text" name="item" class="inputText"   value="${param['item']}"/>
							</li>
							<li>
								<span class="label">开始日期</span>
								<input type="text"  name="beginstart"  class="inputText date" validate="{date:true}"  value="${param['beginstart']}"/>
								<input type="text"  name="endstart"  class="inputText date" validate="{date:true}" value="${param['endstart']}"/> 
							</li>
							<li>
								<span class="label">结束日期</span>
								<input type="text"  name="beginend"  class="inputText date" validate="{date:true}"  value="${param['beginend']}"/>
								<input type="text"  name="endend"  class="inputText date" validate="{date:true}" value="${param['endend']}"/> 
							</li>
							<li>
								<span class="label">提交人</span>
								<input type="text" name="editer" class="inputText"  value="${param['editer']}"/>
							</li>
							<li>
								<span class="label">提交时间</span>
								<input type="text"  name="beginedit_date"  class="inputText date" validate="{date:true}"  value="${param['beginedit_date']}"/>
								<input type="text"  name="endedit_date"  class="inputText date" validate="{date:true}" value="${param['endedit_date']}"/> 
							</li>
						</ul>
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<%-- <c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set> --%>
		    <display:table name="operationWeeklyManageList" id="operationWeeklyManageItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="table-grid">
				<%-- <display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${operationWeeklyManageItem.id}">
				</display:column> --%>
				<display:column title="周报名称">${operationWeeklyManageItem.name}</display:column>
				<display:column title="周报内容">${operationWeeklyManageItem.content}</display:column>
				<display:column title="片区">${operationWeeklyManageItem.grop}</display:column>
				<display:column title="项目名称">${operationWeeklyManageItem.item}</display:column>
				<display:column title="开始日期">
					<fmt:formatDate value='${operationWeeklyManageItem.start}' pattern='yyyy-MM-dd'/>
				</display:column>
				<display:column title="结束日期">
					<fmt:formatDate value='${operationWeeklyManageItem.end}' pattern='yyyy-MM-dd'/>
				</display:column>
				<display:column title="提交人">${operationWeeklyManageItem.editer}</display:column>
				<display:column title="提交日期">
					<fmt:formatDate value='${operationWeeklyManageItem.edit_date}' pattern='yyyy-MM-dd'/>
				</display:column>
				<display:column title="备注">${operationWeeklyManageItem.remarks}</display:column>
				<display:column title="管理" media="html" style="width:10%">
					<c:if test="${isEditer }">
						<a href="del.ht?id=${operationWeeklyManageItem.id}" >删除</a>
						<a href="edit.ht?id=${operationWeeklyManageItem.id}" >编辑</a>
					</c:if>
					<a href="get.ht?id=${operationWeeklyManageItem.id}" >详情</a>
				</display:column>
			</display:table>
			<hotent:paging tableId="operationWeeklyManageItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


