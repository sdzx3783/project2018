<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>会议纪要管理</title>
<%@include file="/commons/include/get.jsp" %>
<script type="text/javascript">
</script>
</head>
<body class="oa-mw-page">

	<div id="oa_list_title" class="oa-mgb20 oa-project-title">
	    <h2>会议纪要管理列表</h2>
	</div>

	<div id="oa_list_search" class="oa-pdb10 oa-mgh20">
        <form id="searchForm" method="post" action="list.ht" class="oa-clear">
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">纪要提交人</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input type="text" name="Q_name_SL"  class="oa-input"  value="${param['Q_name_SL']}"/>
                </div>
            </div>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">会议名称</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input type="text" name="Q_conference_name_SL"  class="oa-input"  value="${param['Q_conference_name_SL']}"/>
                </div>
            </div>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">参会人员</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input type="text" name="Q_participants_SL"  class="oa-input"  value="${param['Q_participants_SL']}"/>
                </div>
            </div>

            <div class="oa-fl oa-mgb10 oa-mgh20">
                <button class="oa-button oa-button--primary oa-button--blue">查询</button>
            </div>
        </form>		
	</div>

	<div class="oa-pdb20 oa-mgh20">

	    <div id="oa_list_content" class="oa-table-scroll-wrap">
		    <display:table name="hdMeetingMinutesList" id="hdMeetingMinutesItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
				<display:column title="编号">
			  		${hdMeetingMinutesItem.number}
				</display:column>
				<display:column title="会议名称">
			  		${hdMeetingMinutesItem.conference_name}
				</display:column>
				<display:column title="会议时间">
			  		<fmt:formatDate value='${hdMeetingMinutesItem.date}' pattern='yyyy-MM-dd'/>
				</display:column>
				<display:column title="会议主持人">
			  		${hdMeetingMinutesItem.moderator}
				</display:column>
				<display:column title="参会人员">
			  		${hdMeetingMinutesItem.participants}
				</display:column>
				<display:column title="纪要提交人">
			  		${hdMeetingMinutesItem.name}
				</display:column>
				<display:column title="备注">
			  		${hdMeetingMinutesItem.note}
				</display:column>
				<display:column title="管理" media="html">
					<a href="get.ht?id=${hdMeetingMinutesItem.id}" class="oa-button-label">明细</a>
				</display:column>
			</display:table>
    	</div>	

    	<hotent:paging tableId="hdMeetingMinutesItem"/>
	</div>

</body>
</html>


