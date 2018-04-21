<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/commons/include/get.jsp" %>
<title>已办事宜</title>
<script type="text/javascript" src="${ctx}/js/hotent/platform/tabOperator.js" ></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/FlowRightDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/FlowUtil.js" ></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/SelectUtil.js" ></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/UserInfo.js"></script>
<style>	
	.creator-search {
		position: absolute;
	    top: 8px;
	    right: 10px;
        display: inline-block;
        background: url('${ctx}/styles/default/images/icon/org.gif') 0px -1px no-repeat;
        padding: 0px 0px 0px 20px;
	    color: #555555;
	    cursor: pointer;
	    font-size: 12px;
	    padding-left: 20px;
	    text-decoration: none;
	    white-space: nowrap;
	    _float: left;
        height: 20px;
	}
</style>
<script type="text/javascript">
    function showDetail(obj){
        var url = $(obj).attr("action");
        jQuery.openFullWindow(url);
    };
    
</script>
</head>
<body>
	<div id="oa_list_search" class="oa-pdb10 oa-mgh20">
        <div class="oa-accordion">
            <div class="oa-accordion__title">查询条件</div>
            <div class="oa-accordion__content">               
                <form id="searchForm" method="post" action="alreadyMattersList.ht" class="oa-clear">
                    <input type="hidden" name="nodePath" value="${param['nodePath']}" title="流程分类节点路径" />
                    <div class="oa-fl oa-mgb10">
                        <div class="oa-label">请求标题</div>
                        <div class="oa-input-wrap oa-mgl100">
                            <input type="text" name="Q_subject_SUPL"  class="oa-input" value="${param['Q_subject_SUPL']}" />
                        </div>
                    </div>   
                    <div class="oa-fl oa-mgb10">
                        <div class="oa-label">流程名称</div>
                        <div class="oa-input-wrap oa-mgl100">
                            <input type="text" name="Q_processName_SUPL"  class="oa-input" value="${param['Q_processName_SUPL']}" />
                        </div>
                    </div>  
                    <div class="oa-fl oa-mgb10">
                        <div class="oa-label">创 建 人</div>
                        <div class="oa-input-wrap oa-mgl100">
                            <input type="hidden" id="creatorId"  class="inputText"  name="Q_creatorId_L"  value="${param['Q_creatorId_L']}" />
                            <input type="text"  id="creator" name="Q_creator_SL" class="oa-input"  value="${param['Q_creator_SL']}" onclick="selectUser('creatorId','creator');" readonly="readonly"  />
                            <a class="creator-search" href="javascript:;" onclick="selectUser('creatorId','creator');">选择</a>
                        </div>
                    </div> 
                    <div class="oa-fl oa-mgb10">
                        <div class="oa-label">状态</div>
                        <div class="oa-input-wrap oa-mgl100">
                            <select name="Q_status_SN" class="oa-select">
                                <option value="">所有</option>
                                    <option value="1" <c:if test="${param['Q_status_SN']==1}">selected</c:if>>审批中</option>
                                    <option value="5" <c:if test="${param['Q_status_SN']==5}">selected</c:if>>已撤销</option>
                                    <option value="6" <c:if test="${param['Q_status_SN']==6}">selected</c:if>>已驳回</option>
                                    <option value="11" <c:if test="${param['Q_status_SN']==7}">selected</c:if>>已追回</option> 
                            </select>
                        </div>
                    </div>                             
                    <div class="oa-fl oa-mgb10">
                        <div class="oa-label">创建时间从</div>
                        <div class="oa-input-wrap oa-mgl100 oa-input-wrap--ib">
                            <input name="Q_begincreatetime_DL"  class="oa-input datePicker" datetype="1"  value="${param['Q_begincreatetime_DL']}" />
                        </div>
                        <span>至</span>
                        <div class="oa-input-wrap oa-input-wrap--ib">
                            <input name="Q_endcreatetime_DG" class="oa-input datePicker" datetype="2" value="${param['Q_endcreatetime_DG']}"/>
                        </div>
                    </div> 
                    <!-- 是否有全局流水号 -->
                    <c:if test="${hasGlobalFlowNo }">  
	                    <div class="oa-fl oa-mgb10">
	                        <div class="oa-label">工单号</div>
	                        <div class="oa-input-wrap oa-mgl100">
	                            <input type="text" name="Q_globalFlowNo_SL"  class="oa-input" value="${param['Q_globalFlowNo_SL']}" />
	                        </div>
	                    </div>
	                </c:if>    
                    <div class="oa-fl oa-mgb10 oa-mgh20">
                    	<button class="oa-button oa-button--primary oa-button--blue" id="btnSearch" type="button" onclick="tosubmit()" >查询</button>
                    	<!--<div class="group"><a class="link reset" onclick="$.clearQueryForm()"><span></span>重置</a></div>-->
						<%--<div class="l-bar-separator"></div>
                   		 <div class="group"><f:a alias="batchApproval" css="link save" id="btnApprove" href="javascript:;" showNoRight="false" onclick="summaryBatch()"><span></span>批量审批</f:a></div> --%>
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
            <display:table name="processRunList" id="processRunItem" requestURI="alreadyMattersList.ht" sort="external" class="oa-table--default oa-table--nowrap">
                <display:column titleKey="序号" media="html" style="width:15px;">
                        ${processRunItem_rowNum}
                    </display:column>
                    <!-- 是否有全局流水号 -->
                    <c:if test="${hasGlobalFlowNo }">
                        <display:column property="globalFlowNo" title="工单号" sortable="true"  sortName="globalFlowNo" style="text-align:left;"></display:column>
                    </c:if>
                    
                    <display:column  titleKey="请求标题" sortable="true" sortName="subject" style="text-align:left">
                        <a name="processDetail" onclick="showDetail(this)" href="#${processRunItem.runId}"  action="/platform/bpm/processRun/info.ht?runId=${processRunItem.runId}&prePage=myFinishedTask" title='${processRunItem.subject}' >${processRunItem.subject}</a>
                    </display:column>
                    <display:column property="processName" titleKey="流程名称" sortable="true" sortName="processName" style="text-align:left"></display:column>
                    <display:column titleKey="创建人" sortable="true" sortName="creator" style="text-align:left">
                        <a href="javascript:userDetail('${processRunItem.creatorId}');">${processRunItem.creator}</a>
                    </display:column>
                    <display:column  titleKey="创建时间" sortable="true" sortName="createtime" style="text-align:left">
                        <fmt:formatDate value="${processRunItem.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                    </display:column>
                    <display:column titleKey="持续时间" style="text-align:left" >                   
                        <c:choose>
                            <c:when test="${processRunItem.status==10 or processRunItem.status ==2 or processRunItem.status ==3}">
                                    ${f:getTime(processRunItem.duration)}
                            </c:when>
                            <c:otherwise>
                            ${f:getDurationTime(processRunItem.createtime)} 
                            </c:otherwise>
                        </c:choose>
                    </display:column>
                    <display:column titleKey="状态" sortable="true" sortName="status" style="text-align:left">
                        <f:processStatus status="${processRunItem.status}"></f:processStatus>
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


