<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>缺勤表管理</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">年假剩余额度</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch"><span></span>查询</a></div>
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht">
					<div class="row">
						<c:if test="${alias==null || (alias!=null && alias=='bpm_hr_manager')}">
							<span class="label">姓名:</span><input type="text" name="Q_fullname_SL"  class="inputText" value="${param['Q_fullname_SL']}"/>
							<span class="label">部门:</span><input type="text"  name="Q_org_SL" value="${param['Q_org_SL']}"  class="inputText" />
						</c:if>
						<span class="label">统计年:</span> <input  name="year"  class="inputText" value="${year}" onclick="WdatePicker({maxDate:'%y-%M',dateFmt: 'yyyy'})"/>
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
				    <display:table export="true" name="vacationCountList" id="vacationCountItem" requestURI="list.ht" cellpadding="1" cellspacing="1" class="table-grid">
						<display:column property="fullname" title="姓名" ></display:column>
						<display:column property="org" title="部门"></display:column>
						<display:column  title="年份" property="year"></display:column>
						<display:column  title="年假（天）">
							<c:choose>
								<c:when test="${vacationCountItem.annualVacation==-1}">
									 <font color="red">未设置入职时间</font>
								</c:when>
								<c:otherwise>
									${vacationCountItem.annualVacation}
								</c:otherwise>
							</c:choose>
						</display:column>
						<display:column  title="已休年假（天）" property="usedAnnualVacation"></display:column>
						<display:column  title="剩余年假（天）">
							<c:choose>
								<c:when test="${vacationCountItem.annualVacation==-1}">
									-
								</c:when>
								<c:otherwise>
									${vacationCountItem.restAnnualVacation}
								</c:otherwise>
							</c:choose>
						</display:column>
					</display:table>
					<hotent:paging tableId="vacationCountItem"/>
		</div>			
	</div>
</body>
</html>


