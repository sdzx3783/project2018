<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>缺勤表管理</title>
<%@include file="/commons/include/customForm.jsp"%>
<%@ taglib prefix="hotent" uri="http://www.jee-soft.cn/paging" %>
<script type="text/javascript" src="${ctx}/js/hotent/displaytag.js" ></script>
<style type="text/css">
	.oa-input-wrap.select{
        position: relative;
        margin-right: 50px;
    }
    .link.org{
        position: absolute;
        right: -50px;
        top: 7px;
    }
    .file-prefer label {
    	color: #657386;
    }
	.showFileName {
		margin-left: 20px;
	}
	.my-file input {
	    position: absolute;
	    display: block;
	    width: 100%;
	    height: 100%;
	    right: 0;
	    top: 0;
	    opacity: 0;
	    font-size: 0;
	}
	
</style>
</head>

<body>
	<div id="oa_list_title" class="oa-project-title">
	</div>
	<div id="oa_list_search" class="oa-pd20 my-condition">
        <form id="searchForm" method="post" action="list.ht" class="oa-clear">
            <c:if test="${hadReadRight }">
            	<div class="oa-fl oa-mgb10">
	                <div class="oa-label">部门</div>
	                <div class="oa-input-wrap select oa-mgl100">
	                    <input name="org"  type="text" required="required" ctltype="selector" class="org oa-input"  value="${org}" initValue="${orgID}" validate="{empty:false}" readonly="readonly" />
	                </div>
            	</div>
	            <div class="oa-fl oa-mgb10">
	                <div class="oa-label">工号</div>
	                <div class="oa-input-wrap select oa-mgl100">
	                    <input name="account"  type="text"  class="oa-input"  value="${param['account']}"  validate="{empty:false}" />
	                </div>
	            </div>
	            <div class="oa-fl oa-mgb10">
	                <div class="oa-label">姓名</div>
	                <div class="oa-input-wrap select oa-mgl100">
	                    <input name="username"  type="text"  class="oa-input"  value="${param['username']}" />
	                </div>
	            </div>
            </c:if>
            
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">月份</div>
                <div class="oa-input-wrap oa-mgl100">
                   <input id="queryMonth" name="queryMonth" type="text" datefmt="yyyy-MM" value="${queryMonth }" class="oa-input Wdate" />
                </div>
            </div>
            
            <div class="oa-fl oa-mgb10 oa-mgh20 my-options">
                <button  class="oa-button oa-button--primary oa-button--blue">查询</button>
            </div>
        </form>
    </div>
    	<c:if test="${errorMsg }">
    		<div class="oa-pdb20 oa-mgh20" style="text-align:center;color:red;font-weight:900;">${errorMsg }</div>
    	</c:if>
    	<c:if test="${!errorMsg }">
    		<div class="panel-body">
				    <display:table  name="workStatsCountList" id="workCountItem" requestURI="list.ht" cellpadding="1" cellspacing="1" class="table-grid">
						<display:column property="username" title="姓名" ></display:column>
						<display:column property="orgname" title="部门"></display:column>
						<display:column  title="年-月">${queryMonth}</display:column>
						<display:column  title="工作日（天）"><fmt:formatNumber value="${workCountItem.workDays }" pattern="##.#"/></display:column>
						<display:column  title="考勤（天）"><fmt:formatNumber value="${workCountItem.workcount }" pattern="##.#"/></display:column>
						<display:column  title="加班（天）"><fmt:formatNumber value="${workCountItem.overdays }" pattern="##.#"/></display:column>
						<display:column  title="请假（天）">
							<fmt:formatNumber value="${workCountItem.allvactions }" pattern="##.#"/>
						</display:column>
						<display:column  title="缺勤（次）">
							<fmt:formatNumber value="${workCountItem.absence }" pattern="##.#"/>
						</display:column>
					</display:table>
					<hotent:paging tableId="workCountItem"/>
			</div>
    	</c:if>
		
</body>
</html>


