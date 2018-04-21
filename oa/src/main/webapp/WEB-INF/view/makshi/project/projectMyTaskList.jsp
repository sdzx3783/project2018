<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>我的任务列表</title>
	<%@include file="/commons/include/get.jsp"%>
	<%@ taglib prefix="hotent" uri="http://www.jee-soft.cn/paging" %>
</head>
<body class="oa-mw-page">

   <div id="oa_list_title" class="oa-mgb20 oa-project-title">
        <!-- <h2>任务列表</h2> -->
    </div>
	
	<div id="oa_list_search" class="oa-pdb10 oa-mgh20">
		<div class="oa-accordion">
           	<div class="oa-accordion__title">查询条件</div>
           	<div class="oa-accordion__content">
		        <form id="searchForm" method="post" action="myTaskList.ht" class="oa-clear">
		            <div class="oa-fl oa-mgb10">
		                <div class="oa-label">项目名称</div>
		                <div class="oa-input-wrap oa-mgl100">
		                    <input type="text" name="projectname" value="${projectname}"  class="oa-input" />
		                </div>
		            </div>
		            <div class="oa-fl oa-mgb10">
		                <div class="oa-label">阶段名称</div>
		                <div class="oa-input-wrap oa-mgl100">
		                    <input type="text" name="stagename" value="${stagename}" class="oa-input" />
		                </div>
		            </div>
		            <div class="oa-fl oa-mgb10">
		                <div class="oa-label">任务名称</div>
		                <div class="oa-input-wrap oa-mgl100">
		                    <input type="text" name="taskname" value="${taskname}" class="oa-input" />
		                </div>
		            </div>
		            <div class="oa-search-item oa-fl oa-mgb10">
		                <div class="oa-label">状态</div>
		                <div class="oa-input-wrap  oa-mgl100">
		                	<select name="status" id="status" class="oa-select">
		                		<option value="-1"  <c:if test="${status==-1 }">selected</c:if>>请选择</option>
		                		<option value="0" <c:if test="${status==0 }">selected</c:if>>未完成</option>
		                		<option value="1" <c:if test="${status==1 }">selected</c:if>>已完成</option>
		                	</select>
		                </div>
		            </div>
		            <div class="oa-fl oa-mgb10 oa-mgh20">
		                <button class="oa-button oa-button--primary oa-button--blue" onclick="tosubmit(0);">查询</button>
		            </div>
		        </form>
		    </div>
		</div>
    </div>
    
    <div class="oa-pdb20 oa-mgh20">
        <c:set var="startNum" value="${(taskItem.currentPage-1)*taskItem.pageSize}"></c:set>

        <div id="oa_list_content" class="oa-table-scroll-wrap">
            <display:table  name="list" id="taskItem" requestURI="myTaskList.ht" sort="external" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
                <c:set var="startNum" value="${startNum+1}"></c:set>
                <display:column title="序号">
                    ${startNum}
                </display:column>
                <display:column title="项目名称">
                    ${taskItem.projectname}
                </display:column>
                <display:column title="阶段名称">
                    ${taskItem.stagename}
                </display:column>
                <display:column title="任务名称">
                    ${taskItem.taskname}
                </display:column>
                <display:column title="负责人">
                    ${taskItem.charge}
                </display:column>
                <display:column title="开始时间">
                    <fmt:formatDate value='${taskItem.starttime}' pattern='yyyy-MM-dd'/>
                </display:column>
                <display:column title="结束时间">
                    <fmt:formatDate value='${taskItem.endtime}' pattern='yyyy-MM-dd'/>
                </display:column>
                <display:column title="状态">
                    <c:if test="${taskItem.status==0}">
                        未完成
                    </c:if>
                    <c:if test="${taskItem.status==1}">
                       已完成
                    </c:if>
                </display:column>
                <display:column title="操作" media="html">
                    <a href="taskDetail.ht?id=${taskItem.id}&type=1" class="oa-button-label">详情</a>
                </display:column>
            </display:table>
        </div>

        <hotent:paging tableId="taskItem"/>
    </div>
    <script type="text/javascript">
		$('.oa-accordion').oaAccordion({collapse: true});
	</script>
</body>
</html>


