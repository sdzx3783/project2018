<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<script type="text/javascript">
	function beforeClick(operatorType){<c:if test="${not empty mapButton.button}">
		switch(operatorType){<c:forEach items="${mapButton.button }" var="btn"  ><c:if test="${not empty btn.prevscript}">
				case ${btn.operatortype}:
				${btn.prevscript}
				break;</c:if></c:forEach>
			}</c:if>
	}
	
	function afterClick(operatorType){<c:if test="${not empty mapButton.button}">
		switch(operatorType){<c:forEach items="${mapButton.button }" var="btn" ><c:if test="${not empty btn.afterscript}">
			case ${btn.operatortype}:
				${btn.afterscript}
				break;</c:if></c:forEach>
			}</c:if>
	}
	
	function showTaskOpinions(runId){
		var url="${ctx}/platform/bpm/taskOpinion/list.ht?runId="+runId+"&isOpenDialog=1";
		url=url.getNewUrl();
		DialogUtil.open({
			url:url,
			title:'审批历史',
			height:'600',
			width:'800'
		});
	}

</script>
<!-- 悬浮工具栏实现的CSS -->

<div id="topNavWrapper">
	<div id="topNav">
	<!-- <iframe  frameborder="0"  style="position:absolute; visibility:inherit; top:0px; left:0px; width:100%; height:42px; z-index:-1;"></iframe> -->


	<div class="hide-panel">
		<div class="panel-top">
			<div class="tbar-title noprint">
				<span class="tbar-label">启动流程--${bpmDefinition.subject} --V${bpmDefinition.versionNo}</span>
			</div>
			<div class="panel-toolbar noprint" >
				<div class="toolBar">
					
					<c:choose>
						<c:when test="${empty mapButton }">
							<div class="group"><a class="link run">提交</a></div>
							<c:choose>
								<c:when test="${isDraft}">
									<div class="group"><a class="link save isDraft">保存草稿</a></div>
								</c:when>
								<c:otherwise>
									<div class="group"><a class="link save">保存数据</a></div>
								</c:otherwise>
							</c:choose>
							<div class="group"><a class="link reset">重置</a></div>
							<div class="group"><a class="link flowDesign" onclick="showBpmImageDlg()">流程图</a></div>
						</c:when>
						<c:otherwise>
							<c:if test="${not empty mapButton.button}">
								<c:forEach items="${mapButton.button }" var="btn"  varStatus="status">
									<c:choose>
										
										<c:when test="${btn.operatortype==1 }">
											<!-- 启动流程 -->
											<div class="group"><a class="link run">${btn.btnname }</a></div>
										</c:when>
										
										<c:when test="${btn.operatortype==2 }">
											<!--流程示意图 -->
											<div class="group"><a class="link flowDesign" onclick="showBpmImageDlg()">${btn.btnname }</a></div>
										</c:when>
										
										<c:when test="${btn.operatortype==3 }">
											<!--打印 -->
											<div class="group"><a class="link print" onclick="window.print();">${btn.btnname }</a></div>
										</c:when>
										
										<c:when test="${btn.operatortype==6 }">
											<!--保存表单 -->
											<c:choose>
												<c:when test="${isDraft}">
													<div class="group"><a class="link save isDraft">保存草稿</a></div>
												</c:when>
												<c:otherwise>
													<div class="group"><a class="link save">保存数据</a></div>
												</c:otherwise>
											</c:choose>
											<div class="group"><a class="link reset">重置</a></div>
										</c:when>
										
										<c:when test="${btn.operatortype==14 }">
											<!--Web签章-->
											<div class="group"><a class="link addWebSigns" onclick="addWebSigns()">${btn.btnname }</a></div>
										</c:when>
									
										<c:when test="${btn.operatortype==15 }">
											<!--手写签章-->
											<div class="group"><a class="link addHangSigns" onclick="addHangSigns()">${btn.btnname }</a></div>
										</c:when>
									</c:choose>
									
									<c:if test="${not status.last}">
									</c:if>
								</c:forEach>
							</c:if>
							
						</c:otherwise>
						
					</c:choose>	
					<c:if test="${not empty param.relRunId}">
						<div class="group"><a class="link search" onclick="showTaskOpinions(${param.relRunId})">审批历史</a></div>
					</c:if>
					<%@include file="incHelp.jsp" %>
				</div>
			</div>
		</div>
	</div>

</div>
</div>
							
								
						