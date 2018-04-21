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
	a.link.del {
		text-decoration: none;
	    display: inline-block;
	    padding: 0 16px;
	    font-size: 14px;
	    height: 32px;
	    line-height: 32px;
	    text-align: center;
	    cursor: pointer;
	    color: #fff;
    	background: #0f88eb;
	    border: 1px solid #0f88eb;
	    -webkit-box-sizing: border-box;
	    -moz-box-sizing: border-box;
	    box-sizing: border-box;
	    -webkit-border-radius: 3px;
	    border-radius: 3px;
	    -webkit-box-shadow: 0 1px 1px rgba(0,0,0,0.15);
	    box-shadow: 0 1px 1px rgba(0,0,0,0.15);
	}
	div.panel-top {
		margin-left: 0 !important;
		overflow: hidden;
	}
	.panel-top .group {
		float: none;
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
			
		}});
	}
</script>

</head>
<body>
	<div id="oa_list_title">
		<div class="panel-top">
			<div class="oa-mgt10 oa-mgh20 group">		    	
		        <a class="oa-button oa-button--primary oa-button--blue link del" action="del.ht">删除</a>
		        <button class="oa-button oa-button--primary oa-button--blue" id="btnSearch" type="button" onclick="tosubmit()" >查询</button>
		    </div>
	    </div>
	</div>
	<div id="oa_list_search" class="oa-pdb10 oa-mgh20">
        <div class="oa-accordion">
            <div class="oa-accordion__title">查询条件</div>
            <div class="oa-accordion__content">               
                <form id="searchForm" method="post" action="myList.ht" class="oa-clear"> 
                	<%-- <li> <span class="label">流程定义名称:</span><input type="text" name="Q_processName_SL" class="inputText" value="${param['Q_processName_SL']}"/></li>
							<li> <span class="label">流程实例标题:</span><input type="text" name="Q_subject_SL" class="inputText" value="${param['Q_subject_SL']}"/> </li> --%>                                                
                    <div class="oa-fl oa-mgb10">
                        <div class="oa-label">创建时间从</div>
                        <div class="oa-input-wrap oa-mgl100 oa-input-wrap--ib">
                            <input name="Q_begincreatetime_DL"  class="oa-input datePicker" datetype="1"  value="${param['Q_begincreatetime_DL']}" />
                        </div>
                        <span>至</span>
                        <div class="oa-input-wrap oa-input-wrap--ib">
                            <input name="Q_endCreateTime_DG" class="oa-input datePicker" datetype="2" value="${param['Q_endCreateTime_DG']}"/>
                        </div>
                    </div>   
                    <div class="oa-fl oa-mgb10">
                        <div class="oa-label">状态</div>
                        <div class="oa-input-wrap oa-mgl100">
                            <select name="Q_status_SN" class="oa-select">
                                <option value="">所有</option>
								<option value="1" <c:if test="${param['Q_status_SN'] == 1}">selected</c:if>>正在运行</option>
								<option value="2" <c:if test="${param['Q_status_SN'] == 2}">selected</c:if>>结束</option>
                            </select>
                        </div>
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
				<display:column property="subject" title="收文标题"   sortable="true" 
					sortName="subject" style="text-align:left"></display:column>
				<display:column property="processName" title="收文编号"
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
				<display:column title="持续时间" sortable="true" sortName="duration">
								${f:getTime(processRunItem.duration)}
							</display:column>
				<display:column title="状态" sortable="true" sortName="status">
					<c:choose>
						<c:when test="${processRunItem.status==6}">
							<span class="red">驳回</span>
						</c:when>
						<c:when test="${processRunItem.status==1}">
							<span class="green">正在运行</span>
						</c:when>
						<c:when test="${processRunItem.status==2}">
							<span class="red">结束 </span>
						</c:when>
						<c:when test="${processRunItem.status==3}">
								<span class="brown">手工结束 </span>
						</c:when>
					</c:choose>
				</display:column>
				<display:column title="管理" media="html" style="width:110px;text-align:left;white-space: nowrap;">
					<a href="javascript:;" onclick="FlowDetailWindow({runId:${processRunItem.runId}});" class="link detail">明细</a>
					<%-- <c:if test="${processRunItem.status==1}">
						&nbsp;<a href="javascript:;" onclick="urge(${processRunItem.actInstId})" class="link urge">催办</a>
					</c:if> --%>
					
					<c:if test="${processRunItem.status==2&&processRunItem.isPrintForm==1}">
						&nbsp;<a href="javascript:;" onclick="printForm(${processRunItem.runId})" class="link print">打印表单</a>
					</c:if>
					<c:if test="${processRunItem.status!=2 && !processRunItem.isRead  }">
						&nbsp;<a href="javascript:;" onclick="recover(${processRunItem.runId})" class="link back">追回</a>
					</c:if>
				</display:column>                                                                         
            </display:table>
        </div>
        <hotent:paging tableId="processRunItem" />
    </div>
	<script>
		$(function(){
			$('.oa-accordion').oaAccordion({collapse: true});
		});
		function tosubmit(){
			$("input[name='isexpand']").val(1);
			$("#searchForm").submit();
		}	 
	</script>
</body>
</html>


