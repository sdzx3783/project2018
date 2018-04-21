<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>招聘申请管理</title>
<%@include file="/commons/include/get.jsp" %>
<script type="text/javascript">
</script>
</head>
<body>
	<div id="oa_list_search" class="oa-pdb10 oa-mgh20">
        <div class="oa-accordion">
            <div class="oa-accordion__title">查询条件</div>
            <div class="oa-accordion__content">               
                <form id="searchForm" method="post" action="list.ht" class="oa-clear">
                    <div class="oa-fl oa-mgb10">
                        <div class="oa-label">工单号</div>
                        <div class="oa-input-wrap oa-mgl100">
                            <input type="text" name="Q_globalflowno_SL"  class="oa-input" value="${param['Q_globalflowno_SL']}" />
                        </div>
                    </div>
                    <div class="oa-fl oa-mgb10">
                        <div class="oa-label">申请人</div>
                        <div class="oa-input-wrap oa-mgl100">
                            <input type="text" name="Q_creator_SL"  class="oa-input" value="${param['Q_creator_SL']}" />
                        </div>
                    </div>
                    <div class="oa-fl oa-mgb10">
                        <div class="oa-label">招聘方式</div>
                        <div class="oa-input-wrap oa-mgl100">
                            <input type="text" name="Q_recruitment_method_SL"  class="oa-input" value="${param['Q_recruitment_method_SL']}" />
                        </div>
                    </div>
                    <div class="oa-fl oa-mgb10">
                        <div class="oa-label">招聘人数</div>
                        <div class="oa-input-wrap oa-mgl100">
                            <input type="text" name="Q_recruitment_number_SL"  class="oa-input" value="${param['Q_recruitment_number_SL']}" />
                        </div>
                    </div>
                    <div class="oa-fl oa-mgb10">
                        <div class="oa-label">招聘专业</div>
                        <div class="oa-input-wrap oa-mgl100">
                            <input type="text" name="Q_recruitment_professional_SL"  class="oa-input" value="${param['Q_recruitment_professional_SL']}" />
                        </div>
                    </div>
                    <div class="oa-fl oa-mgb10">
                        <div class="oa-label">审批状态</div>
                        <div class="oa-input-wrap oa-mgl100">
                            <input type="text" name="Q_processStatus_SL"  class="oa-input" value="${param['Q_processStatus_SL']}" />
                        </div>
                    </div>                 
                    <div class="oa-fl oa-mgb10">
                        <div class="oa-label">申请时间从</div>
                        <div class="oa-input-wrap oa-mgl100 oa-input-wrap--ib">
                            <input id="Q_beginapplytime_DL" name="Q_beginapplytime_DL" class="oa-input datePicker" datetype="1" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'Q_endapplytime_DG\');}'})"  value="${param['Q_beginapplytime_DL']}" />
                        </div>
                        <span>至</span>
                        <div class="oa-input-wrap oa-input-wrap--ib">
                            <input id="Q_endapplytime_DG" name="Q_endapplytime_DG" class="oa-input datePicker" datetype="2" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'Q_beginapplytime_DL\');}'})" value="${param['Q_endapplytime_DG']}"/>
                        </div>
                    </div>
                    <div class="oa-fl oa-mgb10 oa-mgh20">
		                <button class="oa-button oa-button--primary oa-button--blue" type="button" onclick="tosubmit()" >查询</button>
		            </div>                    
                </form>
            </div>
        </div>
    </div>
	<div class="oa-pdb20 oa-mgh20">
        <div id="oa_list_content" class="oa-table-scroll-wrap">
            <display:table export="true" name="recruitmentApplicationList" id="recruitmentApplicationItem" requestURI="list.ht" sort="external" class="oa-table--default oa-table--nowrap">
                <display:column title="工单号">
					<a href="get.ht?id=${recruitmentApplicationItem.id}" target="_blank">${recruitmentApplicationItem.globalFlowNo}</a>
				</display:column>
				<display:column title="申请人">
					${recruitmentApplicationItem.creator}
				</display:column>
				<display:column title="申请时间">
					<fmt:formatDate value="${recruitmentApplicationItem.createTime}" pattern="yyyy-MM-dd"/>
				</display:column>
				
				<display:column title="申报时间">
					<fmt:formatDate value="${recruitmentApplicationItem.declare_time}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column title="招聘方式">
					<c:if test="${recruitmentApplicationItem.recruitment_method=='0'}">校园招聘会</c:if>
					<c:if test="${recruitmentApplicationItem.recruitment_method=='1'}">校园网络招聘</c:if>
					<c:if test="${recruitmentApplicationItem.recruitment_method=='2'}">社会招聘会</c:if>
					<c:if test="${recruitmentApplicationItem.recruitment_method=='3'}">社会网络招聘</c:if>
					<c:if test="${recruitmentApplicationItem.recruitment_method=='4'}">其他</c:if>
				</display:column>
				<display:column title="岗位去向">
					${recruitmentApplicationItem.position_whereabouts}
				</display:column>
				<display:column title="招聘人数">
					${recruitmentApplicationItem.recruitment_number}
				</display:column>
				<display:column title="招聘专业">
					${recruitmentApplicationItem.recruitment_professional}
				</display:column>
				<display:column title="审批状态">
					${recruitmentApplicationItem.processStatus}
				</display:column>
								
				<%-- <display:column title="审批状态">
					${recruitmentApplicationItem.processStatus}
				</display:column> --%>				
				
				<display:column title="操作" media="html">
					<%-- <c:if test="${recruitmentApplicationItem.runId==0}">
						<a href="del.ht?id=${recruitmentApplicationItem.id}" class="link del"><span></span>删除</a>
						<a href="javascript:;" onclick="$.openFullWindow('${ctx}/platform/bpm/task/startFlowForm.ht?defId=10000002180270&businessKey=${recruitmentApplicationItem.id}');" class="link run"><span></span>提交</a>
					</c:if> --%>
					<%-- <a href="edit.ht?id=${recruitmentApplicationItem.id}" class="link edit"><span></span>编辑</a> --%>
					<%-- <a href="get.ht?id=${recruitmentApplicationItem.id}" class="link detail"><span></span>明细</a> --%>
				</display:column>                                                                            
            </display:table>
        </div>
        <hotent:paging tableId="recruitmentApplicationItem" />
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


