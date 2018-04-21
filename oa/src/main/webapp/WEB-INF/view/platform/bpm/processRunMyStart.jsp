<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>我启动的流程列表</title>
<%@include file="/commons/include/get.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/ProcessUrgeDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/FlowDetailWindow.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/FlowUtil.js"></script>
<style>
	div.panel-top {
		margin-left: 0 !important;
		overflow: hidden;
	}
</style>
<script type="text/javascript">
	function urge(id){
		ProcessUrgeDialog({
			actInstId : id
		});
	};
	
	function printForm(runId){
		var url="${ctx}/platform/bpm/processRun/printForm.ht?runId="+runId;
		jQuery.openFullWindow(url);
	}
	
	function recover(runId){
		FlowUtil.recover({runId:runId,backToStart:1,callback:function(){
			window.location.reload();
		}});
	}
	function abolish(runId){
		$.ligerDialog.confirm("是否确认废除流程？",'提示信息',function() {
			window.location.href="${ctx}/platform/bpm/processRun/abolish.ht?runId="+runId;
		});
		/* FlowUtil.abolish({runId:runId,backToStart:1,callback:function(){
			window.location.reload();
		}}); */
	}
	function del(runId){
		$.ligerDialog.confirm("是否确认删除流程, 删除后将不可找回！",'提示信息',function() {
			window.location.href="${ctx}/platform/bpm/processRun/del.ht?runId="+runId;
		});
		/* FlowUtil.del({runId:runId,backToStart:1,callback:function(){
			window.location.reload();
		}}); */
	}
</script>

</head>
<body>
	<div id="oa_list_search" class="oa-pdb10 oa-mgh20">
        <div class="oa-accordion">
            <div class="oa-accordion__title">查询条件</div>
            <div class="oa-accordion__content">               
                <form id="searchForm" method="post" action="myStart.ht" class="oa-clear">
                    <div class="oa-fl oa-mgb10">
                        <div class="oa-label">流程名称</div>
                        <div class="oa-input-wrap oa-mgl100">
                            <input type="text" name="Q_processName_SL"  class="oa-input" value="${param['Q_processName_SL']}" />
                        </div>
                    </div>
                    <%-- <li> <span class="label">流程实例标题:</span><input type="text" name="Q_subject_SL" class="inputText" value="${param['Q_subject_SL']}"/> </li> --%>
                    <div class="oa-fl oa-mgb10">
                        <div class="oa-label">状态</div>
                        <div class="oa-select-wrap oa-mgl100">
                            <select class="oa-select" name="Q_status_SN" value="${param['Q_status_SN']}">
                                <option value="">所有</option>
								<option value="1" <c:if test="${param['Q_status_SN'] == 1}">selected</c:if>>正在运行</option>
								<option value="2" <c:if test="${param['Q_status_SN'] == 2}">selected</c:if>>结束</option>
								<option value="3" <c:if test="${param['Q_status_SN'] == 3}">selected</c:if>>手工结束</option>
								<option value="6" <c:if test="${param['Q_status_SN'] == 6}">selected</c:if>>已驳回</option>
								<option value="7" <c:if test="${param['Q_status_SN'] == 7}">selected</c:if>>已追回</option>
								<option value="11" <c:if test="${param['Q_status_SN'] == 11}">selected</c:if>>已废除</option>
                            </select>
                        </div>
                    </div>                   
                    <div class="oa-fl oa-mgb10">
                        <div class="oa-label">创建时间从</div>
                        <div class="oa-input-wrap oa-mgl100 oa-input-wrap--ib">
                            <input  name="Q_begincreatetime_DL"  class="oa-input datePicker" datetype="1"  value="${param['Q_begincreatetime_DL']}" />
                        </div>
                        <span>至</span>
                        <div class="oa-input-wrap oa-input-wrap--ib">
                            <input name="Q_endcreatetime_DG" class="oa-input datePicker" datetype="2" value="${param['Q_endcreatetime_DG']}"/>
                        </div>
                    </div>  
                    <div class="oa-fl oa-mgb10 oa-mgh20">
                    	<button class="oa-button oa-button--primary oa-button--blue" type="button" onclick="tosubmit()" >查询</button>
		                <button type="button" class="oa-button oa-button--primary oa-button--blue" onclick="$.clearQueryForm()">重置</button>
		                <!-- <div class="l-bar-separator"></div>
						<div class="group">
							<a class="link del" action="del.ht"><span></span>删除</a>
						</div> -->
                    </div>                  
                </form>
            </div>
        </div>
    </div>
    <div class="oa-pdb20 oa-mgh20">
        <div id="oa_list_content" class="oa-table-scroll-wrap">
            <c:set var="checkAll">
				<input type="checkbox" id="chkall" />
			</c:set>
            <display:table name="processRunList" id="processRunItem" requestURI="myStart.ht" sort="external" class="oa-table--default oa-table--nowrap">
                <display:column title="${checkAll}" media="html" style="width:30px;">
					<input type="checkbox" class="pk" name="runId"
						value="${processRunItem.runId}">
				</display:column>
				<display:column property="processName" title="流程名称"
					sortable="true" sortName="processName" style="text-align:left"></display:column>
			<%-- 	<display:column property="subject" title="流程实例标题" sortable="true"
					sortName="subject" style="text-align:left"></display:column> --%>
				<display:column property="creator" title="创建人" sortable="true"
					sortName="creator" style="text-align:left"></display:column>
				<display:column title="创建时间" sortable="true" sortName="createtime">
					<fmt:formatDate value="${processRunItem.createtime}"
						pattern="yyyy-MM-dd HH:mm:ss" />
				</display:column>
				<display:column title="结束时间" sortable="true" sortName="endTime">
					<fmt:formatDate value="${processRunItem.endTime}"
						pattern="yyyy-MM-dd HH:mm:ss" />
				</display:column>
				<%-- <display:column title="持续时间" sortable="true" sortName="duration">
								${f:getTime(processRunItem.duration)}
							</display:column> --%>
				<display:column title="状态" sortable="true" sortName="status">
					<c:choose>
						<c:when test="${processRunItem.status==1}">
							<span class="green">正在运行</span>
						</c:when>
						<c:when test="${processRunItem.status==2}">
							<span class="red">结束 </span>
						</c:when>
						<c:when test="${processRunItem.status==3}">
								<span class="brown">手工结束 </span>
						</c:when>
						<c:when test="${processRunItem.status==6}">
							<span class="red">已驳回</span>
						</c:when>
						<c:when test="${processRunItem.status==7}">
							<span class="red">已追回</span>
						</c:when>
						<c:when test="${processRunItem.status==11}">
							<span class="red">已废除</span>
						</c:when>
					</c:choose>
				</display:column>
				<display:column title="管理" media="html">
					<a href="javascript:;" onclick="FlowDetailWindow({runId:${processRunItem.runId}});" class="oa-button-label">明细</a>
				<%-- 	<c:if test="${processRunItem.status==1}">
						&nbsp;<a href="javascript:;" onclick="urge(${processRunItem.actInstId})" class="link urge">催办</a>
					</c:if> --%>
					
					<c:if test="${processRunItem.status==2&&processRunItem.isPrintForm==1}">
						&nbsp;<a href="javascript:;" onclick="printForm(${processRunItem.runId})" class="oa-button-label">打印表单</a>
					</c:if>
					<c:if test="${processRunItem.status!=2 && !processRunItem.isRead && processRunItem.status!=4 &&processRunItem.status!=11 && processRunItem.status!=7}">
						&nbsp;<a href="javascript:;" onclick="recover(${processRunItem.runId})" class="oa-button-label">追回</a>
					</c:if>
					<c:if test="${(processRunItem.status==6 && processRunItem.taskKey=='UserTask1')||processRunItem.status==7}">
						&nbsp;<a href="javascript:;" onclick="abolish(${processRunItem.runId})" class="oa-button-label">废除</a>
					</c:if>
					<c:if test="${processRunItem.status==11 }">
						&nbsp;<a href="javascript:;" onclick="del(${processRunItem.runId})" class="oa-button-label">删除</a>
					</c:if>
				</display:column>                                                                               
            </display:table>
        </div>
        <hotent:paging tableId="processRunItem" />
    </div>
    
</body>
<script>
	$(function(){
		$('.oa-accordion').oaAccordion({});
	});
	function tosubmit(){
		$("input[name='isexpand']").val(1);
		$("#searchForm").submit();
	}	 
</script>
</html>


