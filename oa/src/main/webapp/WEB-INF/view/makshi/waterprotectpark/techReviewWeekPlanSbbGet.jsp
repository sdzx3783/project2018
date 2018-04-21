
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>技术评审周计划(水保部)明细</title>
<%@include file="/commons/include/customForm.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">技术评审周计划(水保部)详细信息</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<a class="link back" href="list.ht"><span></span>返回</a>
					</div>
				</div>
			</div>
		</div>
	</div>
	<table cellpadding="2" cellspacing="0" border="1" class="formTable" parser="addpermission" data-sort="sortDisabled">
 <tbody>
  <tr class="firstRow">
   <td colspan="4" class="formHead" style="word-break: break-all;">技术评审周计划</td>
  </tr>
  <tr>
   <td align="right" style="width:15%;" class="formTitle" nowrap="nowarp">起始时间:</td>
   <td style="width: 35%; word-break: break-all;" class="formInput">${techReviewWeekPlanSbb.start_time}</td>
   <td align="right" style="width:15%;" class="formTitle" nowrap="nowarp">结束时间:</td>
   <td style="width:35%;" class="formInput">${techReviewWeekPlanSbb.end_time}</td>
  </tr>
  <tr>
   <td align="right" style="width:15%;" class="formTitle" nowrap="nowarp">计划安排人:</td>
   <td style="width: 35%; word-break: break-all;" class="formInput">${techReviewWeekPlanSbb.plan_arranger}</td>
   <td align="right" style="width:15%;" class="formTitle" nowrap="nowarp">执行人:</td>
   <td style="width:35%;" class="formInput">${techReviewWeekPlanSbb.plan_executor}</td>
  </tr>
  <tr>
   <td align="right" style="width:15%;" class="formTitle" nowrap="nowarp">周次:</td>
   <td style="width:35%;" class="formInput">${techReviewWeekPlanSbb.week}</td>
   <td align="right" style="width:15%;" class="formTitle" nowrap="nowarp">时间:</td>
   <td style="width:35%;" class="formInput">${techReviewWeekPlanSbb.applicantTime}</td>
  </tr>
  <tr>
   <td align="right" style="width: 15%; word-break: break-all;" class="formTitle" nowrap="nowarp">表单id:</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3">${techReviewWeekPlanSbb.week_plan_RefId}</td>
  </tr>
 </tbody>
</table>
<div type="subtable" tablename="weekPlanSbb" tabledesc="周计划" parser="piecemodetable" id="weekPlanSbb" formtype="edit">
 <br />
 <div class="subTableToolBar l-tab-links"></div>
 <c:forEach items="${weekPlanSbbList}" var="weekPlanSbb" varStatus="status">
  <div class="block" type="subdata">
   <table class="listTable">
    <tbody>
     <tr class="firstRow">
      <td colspan="10" class="formHead">周计划</td>
     </tr>
     <tr>
      <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">任务名称:</td>
      <td style="width:23%;" class="formInput">${weekPlanSbb.taskName}</td>
      <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">任务被分配人:</td>
      <td style="width:23%;" class="formInput">${weekPlanSbb.task_assignee}</td>
      <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">会议安排时间:</td>
      <td style="width:23%;" class="formInput">${weekPlanSbb.meeting_time}</td>
      <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">会议地点:</td>
      <td style="width:23%;" class="formInput">${weekPlanSbb.meeting_addr}</td>
      <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">评审专家:</td>
      <td style="width:23%;" class="formInput">${weekPlanSbb.assessors}</td>
     </tr>
    </tbody>
   </table>
  </div>
 </c:forEach>
 <br />
</div>
</body>
</html>

