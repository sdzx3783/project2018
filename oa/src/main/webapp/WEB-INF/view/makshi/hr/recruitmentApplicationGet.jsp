
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>招聘申请明细</title>
<%@include file="/commons/include/customForm.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/FlowDetailWindow.js"></script>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body class="oa-mw-page">
	<div class="oa-mg20">
	<table class="oa-table--default oa-table--second" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
	<caption>招聘申请详情</caption>
	<tbody> 
		<tr> 
			<th>员工编号</th>
			<td>${recruitmentApplication.account}</td>
			<th>申请人</th>
			<td>${recruitmentApplication.applicant}</td>
		</tr>
		<tr> 
			<th>申报部门</th>
			<td>${recruitmentApplication.declare_department}</td>
			<th>申报时间</th>
			<td><fmt:formatDate value='${recruitmentApplication.declare_time}' pattern='yyyy-MM-dd'/></td>
		</tr>  
		<tr> 
			<th>补增岗位</th>
			<td>${recruitmentApplication.position_whereabouts}</td>
			<th>需求人数</th>
			<td>${recruitmentApplication.recruitment_number}</td>
		</tr>
		<tr> 
			<th>招聘方式</th>
			<td colspan>
				<c:if test="${recruitmentApplication.recruitment_method=='0'}">校园招聘会</c:if>
				<c:if test="${recruitmentApplication.recruitment_method=='1'}">校园网络招聘</c:if>
				<c:if test="${recruitmentApplication.recruitment_method=='2'}">社会招聘会</c:if>
				<c:if test="${recruitmentApplication.recruitment_method=='3'}">社会网络招聘</c:if>
				<c:if test="${recruitmentApplication.recruitment_method=='4'}">其他</c:if>
			</td>
		</tr>
		<tr> 
			<th>申请理由</th>
			<td>
				<c:if test="${recruitmentApplication.appli_reason=='0'}">扩大编制</c:if>
				<c:if test="${recruitmentApplication.appli_reason=='1'}">补充编制</c:if>
				<c:if test="${recruitmentApplication.appli_reason=='2'}">离职补充</c:if>
				<c:if test="${recruitmentApplication.appli_reason=='3'}">调动补充</c:if>
				<c:if test="${recruitmentApplication.appli_reason=='4'}">人力储备</c:if>
				<c:if test="${recruitmentApplication.appli_reason=='5'}">短期需求</c:if>
				<c:if test="${recruitmentApplication.appli_reason=='6'}">其他</c:if>
			</td>
			<th>其他原因</th>
			<td>${recruitmentApplication.other_reason_content}</td>
		</tr>
		<tr> 
			<th>性别</th>
			<td>
				<c:if test="${recruitmentApplication.sex=='0'}">男</c:if>
				<c:if test="${recruitmentApplication.sex=='1'}">女</c:if>
				<c:if test="${recruitmentApplication.sex=='2'}">不限</c:if>
			</td>
			<th>年龄区间</th>
			<td>
				<c:if test="${recruitmentApplication.age_limit=='0'}">不限</c:if>
				<c:if test="${recruitmentApplication.age_limit=='1'}">具体年限</c:if>
			</td>
		</tr>
		<c:if test="${recruitmentApplication.age_limit=='1'}">
		<tr>
			<th>最小年龄</th>
			<td>${recruitmentApplication.age_least}</td>
			<th>最大年龄</th>
			<td>${recruitmentApplication.age_most}</td>
		</tr>
		</c:if>
		<tr> 
			<th>婚姻状况</th>
			<td>
				<c:if test="${recruitmentApplication.marriage_status=='0'}">已婚</c:if>
				<c:if test="${recruitmentApplication.marriage_status=='1'}">未婚</c:if>
			</td>
			<th>户籍状况</th>
			<td>
				<c:if test="${recruitmentApplication.birthplace=='0'}">深户</c:if>
				<c:if test="${recruitmentApplication.birthplace=='1'}">非深户</c:if>
				<c:if test="${recruitmentApplication.birthplace=='2'}">不限</c:if>
			</td>
		</tr>
		<tr> 
			<th>政治面貌</th>
			<td>
				<c:if test="${recruitmentApplication.political_status=='0'}">党员</c:if>
				<c:if test="${recruitmentApplication.political_status=='1'}">不限</c:if>
			</td>
			<th>毕业时间要求</th>
			<td>
				<c:if test="${recruitmentApplication.graduation_year=='0'}">不限</c:if>
				<c:if test="${recruitmentApplication.graduation_year=='1'}">具体年限</c:if>
			</td>
		</tr>
		<c:if test="${recruitmentApplication.graduation_year=='1'}">
			<tr>
				<th>毕业年份</th>
				<td colspan="3">${recruitmentApplication.graduation_date}</td>
			</tr>
		</c:if>
		<tr> 
			<th>学历要求</th>
			<td>
				<c:if test="${recruitmentApplication.edu_requirement=='0'}">不限</c:if>
				<c:if test="${recruitmentApplication.edu_requirement=='1'}">大专</c:if>
				<c:if test="${recruitmentApplication.edu_requirement=='2'}">本科</c:if>
				<c:if test="${recruitmentApplication.edu_requirement=='3'}">研究生</c:if>
			</td>
			<th>专业要求</th>
			<td>
				${recruitmentApplication.recruitment_professional}
			</td>
		</tr>
		<tr> 
			<th>语言要求</th>
			<td>${recruitmentApplication.language_reqirement}</td>
			<th>技能等级(证书)要求</th>
			<td>${recruitmentApplication.degree_requirement}</td>
		</tr>
		<tr> 
			<th>经历/经验要求</th>
			<td colspan="3">${recruitmentApplication.experience_reqirement}</td>
		</tr>
		<tr> 
			<th>技能要求</th>
			<td colspan="3">${recruitmentApplication.skill_requirement}</td>
		</tr>
		<tr> 
			<th>个性要求</th>
			<td colspan="3">${recruitmentApplication.character_requirement}</td>
		</tr>
		<tr> 
			<th>其他要求</th>
			<td colspan="3">${recruitmentApplication.other_requirement}</td>
		</tr>
		<tr> 
			<th>其他补充</th>
			<td colspan="3">${recruitmentApplication.other_remark}</td>
		</tr>
	</tbody>
	</table>
	</div>
</body>
</html>

