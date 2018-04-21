<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>投票</title>
<%@include file="/codegen/include/customForm.jsp"%>
<%@include file="/commons/include/ueditor.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
</head>
<body class="oa-mw-page">

	<div class="oa-pd20">
	    <a class="oa-button oa-button--primary oa-button--blue" href="list.ht">返回</a>
	</div>

	<div class="oa-pdh20">
		<p style="font-size: 14px; margin-bottom: 10px;">问卷信息</p>
		<table class="oa-table--default">
			<tr>
				<th>问卷调查</th>
				<td>${questionnaire.title}</td>
			</tr>
			<tr>
				<th>描述</th>
				<td>
					${questionnaire.desc}
				</td>
			</tr>
			<tr>
				<th>截止时间</th>
				<td>
					<fmt:formatDate value="${questionnaire.end_date}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
			</tr>
		</table>

	</div>

	<div class="oa-pd20">
			<p style="font-size: 14px; margin-bottom: 10px;">问卷结果</p>

			<c:forEach items="${questionnaire.questionnaireQuestionList }" var="item" varStatus="i">
				<div class="oa-mgb20">
					<table class="oa-table--default oa-table--fixed">
						<tr>
							<th colspan="3">问题${i.index+1}：${item.title}<c:if test="${item.max_choice !=null}">（最多可选${item.max_choice }项）</c:if></th>
						</tr>

						<c:if test="${item.type ==0}">
							<c:forEach items="${item.questionnaireOptionList }" var="item2" varStatus="j">
								<tr>
									<td>${item2.desc}</td>
									<td>${item2.count}人</td>
									<td>
										<div class="progress" title="${item2.percentum}%">
										  <div class="progress-bar" style="width: ${item2.percentum}%;">
										  </div>
										</div>
									</td>
								</tr>
							</c:forEach>
						</c:if>

						<c:if test="${item.type ==1}">
							<tr>
								<td colspan="3">
									<c:forEach items="${item.resultList }" var="result">
										${result.result}<br/>
									</c:forEach>
								</td>
							</tr>
						</c:if>
					</table>
				</div>
			</c:forEach>


	</div>
		
	<c:if test="${questionnaire.status==0 || questionnaire.status==1 || questionnaire.status==3}">
		
		<div class="oa-pd20">
			<c:choose>
				<c:when test="${questionnaire.status==0}">未开始</c:when>
				<c:when test="${questionnaire.status==1}">进行中</c:when>
				<c:when test="${questionnaire.status==3}">已结束</c:when>
			</c:choose>
		</div>

	</c:if>		
</body>
</html>