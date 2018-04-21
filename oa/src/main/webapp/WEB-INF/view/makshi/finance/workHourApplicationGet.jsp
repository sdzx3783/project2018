<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
    <head>
        <title>工时填报申请明细</title>
        <%@include file="/commons/include/customForm.jsp"%>
        <script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
    </head>
    <body>

        <div class="oa-project-title">
            <h2>工时填报申请详细信息</h2>
        </div>

        <div class="oa-pd20">
            <a class="oa-button oa-button--primary oa-button--blue" href="list.ht">返回</a>
        </div>

        <div class="oa-pd20">
            <table class="oa-table--default  oa-table--fixed" data-sort="sortDisabled" parser="addpermission">
                <tbody>
                    <tr>
                        <th>姓名:</th>
                        <td> ${workHourApplication.applicant} </td>
                        <th>所属部门:</th>
                        <td> ${workHourApplication.org} </td>
                        <th>考勤日期:</th>
                        <td><fmt:formatDate value='${workHourApplication.applicant_time}' pattern='yyyy-MM-dd'/></td>
                    </tr>
                    <tr>
                        <th>累计工作时间比例:(%)</th>
                        <td ${!isHjsyb?'colspan="5"':''}>${workHourApplication.work_hour_rate}</td>
                        <th <c:if test="${!isHjsyb}">style="display:none"</c:if>>考勤制度:</th>
                        <td <c:if test="${!isHjsyb}">style="display:none"</c:if>><c:choose>
                        	<c:when test="${workHourApplication.basehour==7}">7小时制</c:when>
                        	<c:when test="${workHourApplication.basehour==3}">3小时制</c:when>
                        	<c:when test="${workHourApplication.basehour==8}">8小时倒班制（环境事业部）</c:when>
                        	<c:when test="${workHourApplication.basehour==12}">12小时倒班制（环境事业部）</c:when>
                        </c:choose></td>
                        <th style="display:none">累计加班工时:</th>
                        <td style="display:none" colspan="3">${workHourApplication.work_hour_total}</td>
                    </tr>
                </tbody>
            </table>
        </div>
    

        <div class="oa-pd20 ">
            <table class="oa-table--default oa-table--fixed workHourApplicationTable" id="workHourApplicationTable">
                <tr>
                    <th width="26%">项目名称<br /></th>
                    <th width="29%">任务名称</th>
                    <th width="10%">工作时间比例</th>
                    <th style="display:none">加班工时</th>
                    <th width="8%">进度</th>
                    <th>备注</th>
                </tr>
                <c:forEach items="${projectTaskHourList}" var="projectTaskHour" varStatus="status">
	                <tr>
	                    <td> ${projectTaskHour.projectName}  </td>
	                    <td> ${projectTaskHour.taskName}  </td>
	                    <td>${projectTaskHour.project_work_rate}</td>
	                    <td style="display:none">${projectTaskHour.overtime_hour}</td>
	                    <td>${projectTaskHour.progress_rate}</td>
	                    <td> ${projectTaskHour.remark}  </td>
	                </tr>
                </c:forEach>
            </table>
        </div>

        <div class="oa-pd20">
            <table class="oa-table--default oa-table--fixed workHourApplicationTable">
                <tr>
                    <th width="26%">任务名称</th>
                    <th width="29%">工作内容</th>
                    <th width="10%">工作时间比例</th>
                    <th style="display:none">加班工时</th>
                    <th width="8%">进度</th>
                    <th>备注</th>
                </tr>
                <c:forEach items="${customTaskHourList}" var="customTaskHour" varStatus="status">
                <tr>
                    <td>
                        <c:choose>
                        <c:when test="${customTaskHour.taskName=='1'}">培训</c:when>
                        <c:when test="${customTaskHour.taskName=='2'}">会议</c:when>
                        <c:when test="${customTaskHour.taskName=='3'}">请假</c:when>
                        <c:when test="${customTaskHour.taskName=='4'}">集体活动</c:when>
                        <c:when test="${customTaskHour.taskName=='5'}">空闲</c:when>
                        <c:when test="${customTaskHour.taskName=='6'}">其他</c:when>
                        <c:when test="${customTaskHour.taskName=='7'}">日常工作</c:when>
                        </c:choose>
                    </td>
                    <td>${customTaskHour.work_content}</td>
                    <td>${customTaskHour.task_work_rate}</td>
                    <td style="display:none">${customTaskHour.overtime_hour}</td>
                    <td>${customTaskHour.progress_rate}</td>
                    <td> ${customTaskHour.remark}  </td>
                </tr>
                </c:forEach>
            </table>
        </div>


    </body>
</html>