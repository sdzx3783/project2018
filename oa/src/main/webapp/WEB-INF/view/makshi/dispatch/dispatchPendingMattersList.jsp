<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<%@include file="/commons/include/get.jsp" %>
<title>待办事宜</title>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/BpmTaskExeAssignDialog.js"></script>
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
$(function(){
    $("[name='subject']").click(function(){
        $("#treeFresh",window.parent.document).trigger("click");
    });
});

function executeTask(taskId){
    
    var url="${ctx}/platform/bpm/task/toStart.ht?taskId="+taskId;
    var rtn = jQuery.openFullWindow(url);
    
}

//重启任务
function restartTask(taskId){
    var url="${ctx}/platform/bpm/task/restartTask.ht?taskId="+taskId;
    var rtn = jQuery.openFullWindow(url);
}


function reload(){
    location.href=location.href.getNewUrl();
    parent.globalType.loadGlobalTree();
}



function batOperator(operator){
    if(operator=="approve"){
        if ($("#btnApprove").attr('class').indexOf('disabled')>0){return false;}    
    }
    else{
        if ($("#btnBatDelegate").attr('class').indexOf('disabled')>0){return false;}
    }
    
    var aryId = $("input[type='checkbox'][disabled!='disabled'][class='pk']:checked");
    var len=aryId.length;
    if(len==0){
        $.ligerDialog.warn('请选择记录!','提示');
        return;
    }
    
    var taskIds=new Array();
    $("input[name='id']:checked").each(function(){
        taskIds.push($(this).val());
    });
    var ids=taskIds.join(",");
    
    
    var url=__ctx + "/platform/bpm/task/pendingMattersListBatchApprovalCfm.ht?taskIds="+ids;
    if(operator=="delegate"){
        url=__ctx + "/platform/bpm/task/delegateDialog.ht?taskIds="+ids;
    }
    
    var winArgs="dialogWidth=500px;dialogHeight=250px;help=0;status=0;scroll=0;center=1";
    url=url.getNewUrl();
    /* var rtn=window.showModalDialog(url,"",winArgs);
    if (rtn=='ok'){
        location.href=location.href.getNewUrl();
    } */
    
    /*KILLDIALOG*/
    DialogUtil.open({
        height:350,
        width: 450,
        title : '批量审批',
        url: url, 
        isResize: true,
        //自定义参数
        sucCall:function(rtn){
            if (rtn=='ok'){
                location.href=location.href.getNewUrl();
            }
        }
    });
}

/**
 * 跳转到批量审批界面
 */
function summaryBatch(){
    parent.location.href= "${ctx}/platform/bpm/bpmBatchApproval/manage.ht";
}

</script>
</head>
<body>
	<div id="oa_list_search" class="oa-pdb10 oa-mgh20">
        <div class="oa-accordion">
            <div class="oa-accordion__title">查询条件</div>
            <div class="oa-accordion__content">               
                <form id="searchForm" method="post" action="pendingMattersList.ht?porIndex=${porIndex}&tabIndex=${tabIndex}" class="oa-clear">
                    <div class="oa-fl oa-mgb10">
                        <div class="oa-label">事项名称</div>
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
                        <div class="oa-label">阅读状态</div>
                        <div class="oa-input-wrap oa-mgl100">
                            <select name="Q_hasRead_S" class="oa-select">
                                <option value="">所有</option>
                                <option value="0" <c:if test="${param['Q_hasRead_S'] == '0'}">selected</c:if>>未读</option>
                                <option value="1" <c:if test="${param['Q_hasRead_S'] == '1'}">selected</c:if>>已读</option>
                            </select>
                        </div>
                    </div> 
                    <div class="oa-fl oa-mgb10">
                        <div class="oa-label">待办类型</div>
                        <div class="oa-input-wrap oa-mgl100">
                            <select name="Q_type_S" class="oa-select">
                                <option value="-1" <c:if test="${param['Q_type_S'] == '-1'}">selected</c:if>>待办</option>
                                <option value="21" <c:if test="${param['Q_type_S'] == '21'}">selected</c:if>>转办</option>
                                <option value="15" <c:if test="${param['Q_type_S'] == '15'}">selected</c:if>>沟通意见</option>
                                <option value="26" <c:if test="${param['Q_type_S'] == '26'}">selected</c:if>>代理</option>
                                <option value="38" <c:if test="${param['Q_type_S'] == '38'}">selected</c:if>>流转意见</option>
                            </select>
                        </div>
                    </div> 
                    <div class="oa-fl oa-mgb10">
                        <div class="oa-label">状态</div>
                        <div class="oa-input-wrap oa-mgl100">
                            <select name="Q_status_N" class="oa-select">
                                <option value="">所有</option>
                                <option value="1" <c:if test="${param['Q_status_N'] == '1'}">selected</c:if>>审批中</option>
                                <option value="5" <c:if test="${param['Q_status_N'] == '5'}">selected</c:if>>已撤销</option>
                                <option value="6" <c:if test="${param['Q_status_N'] == '6'}">selected</c:if>>已驳回</option>
                            </select>
                        </div>
                    </div>                              
                    <div class="oa-fl oa-mgb10">
                        <div class="oa-label">创建时间从</div>
                        <div class="oa-input-wrap oa-mgl100 oa-input-wrap--ib">
                            <input name="Q_beginCreateTime_DL"  class="oa-input datePicker" datetype="1"  value="${param['Q_beginCreateTime_DL']}" />
                        </div>
                        <span>至</span>
                        <div class="oa-input-wrap oa-input-wrap--ib">
                            <input name="Q_endCreateTime_DG" class="oa-input datePicker" datetype="2" value="${param['Q_endCreateTime_DG']}"/>
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
            <display:table name="taskList" id="taskItem" requestURI="pendingMattersList.ht" sort="external" class="oa-table--default oa-table--nowrap">
                <display:column title="${checkAll}" media="html" style="width:20px;">
                            <%-- 15 为沟通意见 , 38为流转意见--%>
                            <c:choose>
                            <c:when test="${taskItem.description eq '15' || taskItem.description eq '38'}">
                                <input type="checkbox" class="pk" name="id"  disabled="disabled" value="${taskItem.id}">
                            </c:when>
                            <c:otherwise >
                                <input type="checkbox" class="pk" name="id"  value="${taskItem.id}">
                            </c:otherwise>
                        </c:choose>
                    </display:column>
                    <!-- 是否有全局流水号 -->
                    <c:if test="${hasGlobalFlowNo }">
                        <display:column property="globalFlowNo" title="工单号" sortable="true"  sortName="globalFlowNo" style="text-align:left;"></display:column>
                    </c:if>
                    <display:column title="事项名称"  sortable="true" sortName="subject"  style="text-align:left;" >
                            <c:choose> 
                                    <c:when test="${taskItem.description eq '36'}">
                                        <a name="subject"  href="#${taskItem.id}" onclick="javascript:restartTask(${taskItem.id})" style="color:${warningSet[taskItem.priority].color}" title='${taskItem.subject}'  <c:if test="${taskItem.hasRead == 0}"> class='message close-message'</c:if><c:if test="${taskItem.hasRead != 0}"> class='message open-message'</c:if> >${f:subString(taskItem.subject)}</a>
                                    </c:when>
                                <c:otherwise>
                                        <a name="subject"  href="#${taskItem.id}" onclick="javascript:executeTask(${taskItem.id})" style="color:${warningSet[taskItem.priority].color}" title='${taskItem.subject}' <c:if test="${taskItem.hasRead == 0}"> class='message close-message'</c:if><c:if test="${taskItem.hasRead != 0}"> class='message open-message'</c:if> >${f:subString(taskItem.subject)}</a>
                                </c:otherwise>
                            </c:choose>
                    </display:column>
                    <display:column title="任务名称" property="name"      style="text-align:left;" >
                    </display:column>
                    
                    <display:column property="processName" title="流程名称"  sortable="true" sortName="processName"  style="text-align:left"></display:column>
                    <display:column  title="创建人" sortable="true" sortName="creator" style="text-align:left">
                        <a href="javascript:userDetail('${taskItem.creatorId}');">${taskItem.creator}</a>
                    </display:column>
                    <display:column title="创建时间" sortable="true" sortName="create_time_">
                        <fmt:formatDate value="${taskItem.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                    </display:column>
                    <display:column title="状态" style="width:30px;" >
                        <c:choose>
                            <c:when test="${taskItem.taskStatus==1}">审批中</c:when>
                            <c:when test="${taskItem.taskStatus==5}">已撤销</c:when>
                            <c:when test="${taskItem.taskStatus==6}">已驳回</c:when>
                            <c:when test="${taskItem.taskStatus==7}">已追回</c:when>
                        </c:choose>
                    </display:column>
                    <display:column title="待办类型"  style="width:30px;">
                    
                        <c:choose>
                            <c:when test="${taskItem.description=='-1'}">
                                <span class="green">待办</span>
                            </c:when>
                            <c:when test="${taskItem.description eq '21' }" >
                                <span class="brown">转办</span>
                            </c:when>
                            <c:when test="${taskItem.description eq '15' }" >
                                <span class="orange">沟通意见</span>
                            </c:when>
                            <c:when test="${taskItem.description eq '26' }" >
                                <span class="brown">代理</span>
                            </c:when>
                            <c:when test="${taskItem.description eq '38' }" >
                                <span class="red">流转意见</span>
                            </c:when>
                        </c:choose>
                    </display:column>                                                                            
            </display:table>
        </div>
        <hotent:paging tableId="taskItem" />
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


