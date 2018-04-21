<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<%@include file="/commons/include/form.jsp"%>
<title>日程交流--参与者</title>
<f:link href="form.css" ></f:link>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/CommonDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysPlanScript.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
<script type="text/javascript" src="${ctx}/servlet/ValidJs?form=sysPlan"></script>

<script type="text/javascript" src="${ctx}/js/hotent/platform/form/AttachMent.js" ></script>

<script type="text/javascript">

	$(function(){
		//初始化选择器
		initData();
		
		//初始化点击人员事件
	    openDetailEvent();
		
	    //初始化删除日程按键
	    initDelete();
	    
	    //初始化增加日程交流信息按键
	    initAddSysPlanExchange();
	    
	    //初始化删除日程交流信息按键
	    initDeleteExchang();

	    AttachMent.init("r");	
	});
	
	
</script>
</head>
<body class="oa-mw-page">
	
	<div class="oa-pd20 oa-pdb0">
		<c:choose>
			<c:when test="${type eq 'myPlan'}">
				<a class="oa-button oa-button--primary oa-button--blue" href="${ctx}/platform/system/sysPlan/myList.ht">返回</a>
			</c:when>
			<c:otherwise>
				<a class="oa-button oa-button--primary oa-button--blue" href="${ctx}/platform/system/sysPlan/participant.ht?currentViweDate=${currentViweDate}">返回</a>
			</c:otherwise>
		</c:choose>
	</div>


	<div class="oa-pd20">
		<form id="sysPlanExchange" method="post" action="">
			<input type="hidden" name="id" value="${sysPlan.id}"/>
			<input type="hidden" name="currentViweDate" value="${currentViweDate}"/>
			<input type="hidden" name="type" value="${type }"/>

			<table class="oa-table--default" cellpadding="0" cellspacing="0" border="0">				 
				<tr>
					<th>任务名称:</th>
					<td>${sysPlan.taskName}</td>
					<th>提交人:</th>
					<td>
						<input type="hidden" name="submitId" value="${sysPlan.submitId}"  />
						<input type="hidden" name="submitor" value="${sysPlan.submitor}"  />
						<span id='submitDiv'></span>
					</td>
				</tr>
				
				<tr>
					<th>负责人:</th>
					<td>
						<input type="hidden" name="chargeId" value="${sysPlan.chargeId}" />
						<input type="hidden" name="charge" value="${sysPlan.charge}"  />
						<span id='chargeDiv'></span>
					</td>
					<th>参与人:</th>
					<td>
						<input type="hidden" name="participantIds" value="${participantIds}"  />
						<input type="hidden" name="participants" value="${participants}"  />
						<span id='participantDiv'></span>
					</td>
				</tr>
				
				<tr>
					<th>开始时间:</th>
					<td>
						<fmt:formatDate value='${sysPlan.startTime}' pattern='yyyy-MM-dd HH:mm:ss'/>
					</td>
					<th>结束时间:</th>
					<td>
						<fmt:formatDate value='${sysPlan.endTime}' pattern='yyyy-MM-dd HH:mm:ss'/>
					</td>
				</tr>
				
				<tr>
					<th>项目名称:</th>
					<td>${sysPlan.projectName}</td>
					<th>日程进度:</th>
					<td>${sysPlan.rate}%</td>
				</tr>
				
				<tr>
					<th>相关文档: </th>
					<td colspan="3">
						<div name="div_attachment_container">
							<div class="attachement"></div>
							<textarea style="display: none" controltype="attachment"
								id="doc" name="doc" lablename="主表附件" validate="{}">${sysPlan.doc}</textarea>
						</div> 
					</td>
				</tr>
				
				<tr>
					<th>内容:</th>
					<td colspan="3">
						${sysPlan.description}
					</td>
				</tr>
				
				<tr>
					<th>交流信息:</th>
					<td colspan="3">

						<c:if test="${sysPlan.rate!=100}">
							<a id="addSysPlanExchange" class="oa-button oa-button--primary oa-button--blue" href="#">添加</a>
						</c:if>

						<table id="exchangeTable" class="oa-table--default oa-mgt10"  cellpadding="0" cellspacing="0" border="0">
							<thead>
								<tr>
									<th>提交内容</th>
									<th>提交人</th>
									<th>提交时间</th>
									<th>管理</th>
								</tr> 
							</thead>
						    <c:choose>
								<c:when test="${fn:length(sysPlanExchangeList)>0 }">
									<c:forEach items="${sysPlanExchangeList}" var="exchange">
										<tr>
											<td >
											    <c:choose>
													<c:when test="${fn:length(exchange.content)>21}">
														<c:out value="${fn:substring(exchange.content, 0, 20)}..." /> 
													</c:when>
													<c:otherwise>
														<c:out value="${exchange.content}" /> 
													</c:otherwise>
												</c:choose>	
											</td>
											<td>
												<c:if test="${fn:length(exchange.submitor)>0}">
													<span class="oa-user-label">${exchange.submitor}</span>
												</c:if>
											</td>
											<td>
											    <fmt:formatDate value='${exchange.createTime}' pattern='yyyy-MM-dd HH:mm:ss'/>
											</td>
											<td>
											    <a class="oa-button-label" onclick="openSysPlanExchange('${exchange.id}','${exchange.planId}')" href="#">查看</a>
											</td>
										</tr>
									</c:forEach>
								</c:when>
								
								<c:otherwise>
									<tr class="empty-div" >
										<td colspan="4">暂没有交流信息</td>
									</tr>
								</c:otherwise>
								
							</c:choose>							 
						</table>
					</td>
				</tr>
			</table>
		</form>
		
	</div>

</body>
</html>

