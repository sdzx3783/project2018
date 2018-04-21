<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>考勤记录管理</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body class="oa-mw-page">
	
   <div id="oa_list_title" class="oa-mgb20 oa-project-title">
	    <!-- <h2>考勤记录管理列表</h2> -->
	</div>
	<c:if test="${ty=='cq' && (alias==null || (alias!=null && alias=='bpm_hr_manager'))}">
	    <div id="oa_list_operation" class="oa-pdb20 oa-mgh20 oa-mgl40">
			<!-- <a class="oa-button oa-button--primary oa-button--blue">查询</a> -->		
			<a class="oa-button oa-button--primary oa-button--blue" href="edit.ht">补录</a>			
		</div>
	</c:if>


    <div id="oa_list_search" class="oa-pdb10 oa-mgh20">
        <form id="searchForm" method="post" action="list.ht?ty=${ty}" class="oa-clear">
        	<c:if test="${ty=='cq' && (alias==null || (alias!=null && alias=='bpm_hr_manager'))}">
	            <div class="oa-fl oa-mgb10">
	                <div class="oa-label">姓名</div>
	                <div class="oa-input-wrap oa-mgl100">
	                    <input type="text" name="Q_fullname_SL"  class="oa-input" value="${param['Q_fullname_SL']}"/>
	                </div>
	            </div>
	            <div class="oa-fl oa-mgb10">
	                <div class="oa-label">部门</div>
	                <div class="oa-input-wrap oa-mgl100">
	                    <input type="text"  name="Q_org_SL" value="${param['Q_org_SL']}"  class="oa-input" />
	                </div>
	            </div>
            </c:if>

            <div class="oa-fl oa-mgb10">
                <div class="oa-label">类型</div>
                <div class="oa-select-wrap oa-mgl100">
                    <select class="oa-select" name="Q_type_S" value="${param['Q_type_S']}">
						<option value="">选择</option>
						<option value="签到" <c:if test="${param['Q_type_S']=='签到'}">selected</c:if> >签到</option>
						<option value="补录" <c:if test="${param['Q_type_S']=='补录'}">selected</c:if> >补录</option>
					</select>
                </div>
            </div>

            <div class="oa-fl oa-mgb10">
                <div class="oa-label">签到时间</div>
                <div class="oa-input-wrap oa-mgl100 oa-input-wrap--ib">
                    <input  name="Q_beginclock_time_DL"  class="oa-input date" value="${param['Q_beginclock_time_DL']}"/>
                </div>
				<span>至</span>
                <div class="oa-input-wrap oa-input-wrap--ib">
                    <input  name="Q_endclock_time_DL" class="oa-input date"  value="${param['Q_endclock_time_DL']}"/>
                </div>
            </div>

            <div class="oa-fl oa-mgb10">
                <div class="oa-label">创建时间</div>
                <div class="oa-input-wrap oa-mgl100 oa-input-wrap--ib">
                    <input  name="Q_begincreate_time_DL"  class="oa-input date" value="${param['Q_begincreate_time_DL']}"/>
                </div>
				<span>至</span>
                <div class="oa-input-wrap oa-input-wrap--ib">
                    <input  name="Q_endcreate_time_DL" class="oa-input date" value="${param['Q_endcreate_time_DL']}"/>
                </div>
            </div>
            <div class="oa-fl oa-mgb10 oa-mgh20">
                <button class="oa-button oa-button--primary oa-button--blue">查询</button>
            </div>
        </form>
    </div>

    <div class="oa-pdb20 oa-mgh20">
	    <div id="oa_list_content" class="oa-table-scroll-wrap">
		    <display:table name="workSheetList" export="true" id="workSheetItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
				<display:column property="fullname" title="姓名" sortable="true" sortName="F_FULLNAME"></display:column>
				<display:column property="org" title="部门" sortable="true" sortName="F_ORG"></display:column>
				<display:column  title="签到时间" sortable="true" sortName="F_CLOCK_TIME">
					<fmt:formatDate value="${workSheetItem.clock_time}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column property="week" title="星期" sortable="true" sortName="F_WEEK"></display:column>
				<display:column  title="创建时间" sortable="true" sortName="F_CREATE_TIME">
					<fmt:formatDate value="${workSheetItem.create_time}" pattern="yyyy-MM-dd  HH:mm:ss"/>
				</display:column>
				<display:column property="type" title="类型" sortable="true" sortName="F_TYPE"></display:column>
				<display:column property="remark" title="备注" sortable="true" sortName="F_REMARK"></display:column>
				<display:column title="管理" media="html">
					<a href="get.ht?id=${workSheetItem.id}" class="oa-button-label">明细</a>
				</display:column>
			</display:table>
	    </div>

		<hotent:paging tableId="workSheetItem"/>
	</div>
	
</body>
</html>


