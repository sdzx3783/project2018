<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>合同人员变更申请管理</title>
<%@include file="/commons/include/get.jsp" %>
<script type="text/javascript">
</script>
</head>
<body class="oa-mw-page">
		<!-- <div id="oa_list_title" class="oa-mgb20 oa-project-title">
        	<h2>合同人员变更申请管理列表</h2>
   		</div> -->
        <div id="oa_list_search" class="oa-pdb10 oa-mgh20">
		    <div class="oa-accordion">
	            <div class="oa-accordion__title">查询条件</div>
	            <div class="oa-accordion__content">
         	        <form id="searchForm" method="post" action="list.ht" class="oa-clear">
			            <div class="oa-fl oa-mgb10">
			                <div class="oa-label">工单号</div>
			                <div class="oa-input-wrap oa-mgl100">
			                    <input class="oa-input"  name="Q_globalflowno_SL"   value="${param['Q_globalflowno_SL']}"/>
			                </div>
			            </div>
			            <div class="oa-fl oa-mgb10">
			                <div class="oa-label">申请人</div>
			                <div class="oa-input-wrap oa-mgl100">
			                    <input class="oa-input"  name="Q_creator_SL"   value="${param['Q_creator_SL']}"/>
			                </div>
			            </div>		
			            <div class="oa-fl oa-mgb10 oa-mgh20">
			                <button id="btnSearch" class="oa-button oa-button--primary oa-button--blue">查询</button>
							<!-- <div class="l-bar-separator"></div>
							<div class="group"><a class="link add" href="edit.ht"><span></span>添加</a></div>
							<div class="l-bar-separator"></div>							
							<div class="group"><a class="link update" id="btnUpd" action="edit.ht"><span></span>修改</a></div>
							<div class="l-bar-separator"></div>
							<div class="group"><a class="link del"  action="del.ht"><span></span>删除</a></div> -->
			            </div>
			        </form>    
	            </div>
		    </div>
    	</div>
        <div class="oa-pdb20 oa-mgh20">
    	 <c:set var="checkAll">
			<input type="checkbox" id="chkall"/>
		 </c:set>
      	 <div id="oa_list_content" class="oa-table-scroll-wrap">
      	 	<display:table export="true" name="contractChangeApplicationList" id="contractChangeApplicationItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
            	<display:column title="${checkAll}"   media="html">
			  		<input type="checkbox" class="pk" name="id" value="${contractChangeApplicationItem.id}">
				</display:column>
				
				
				<display:column title="工单号">
					<a target="_blank" href="/makshi/contract/contractChangeApplication/get.ht?id=${contractChangeApplicationItem.id }">${contractChangeApplicationItem.globalFlowNo}</a>
				</display:column>
				<display:column title="申请人">
					${contractChangeApplicationItem.creator}
				</display:column>
				<display:column title="申请时间">
					<fmt:formatDate value="${contractChangeApplicationItem.createTime}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column title="合同名称">
					${contractChangeApplicationItem.contract_name}
				</display:column>
				<display:column title="合同金额">
					${contractChangeApplicationItem.contract_account}
				</display:column>
				<display:column title="变更内容">
					<c:if test="${contractChangeApplicationItem.change_content=='0'}"></c:if>
			       	<c:if test="${contractChangeApplicationItem.change_content=='1'}">合同金额</c:if>
					<c:if test="${contractChangeApplicationItem.change_content=='2'}">总投资</c:if>
					<c:if test="${contractChangeApplicationItem.change_content=='3'}">合同经手人</c:if>
					<c:if test="${contractChangeApplicationItem.change_content=='4'}">项目总监</c:if>
					<c:if test="${contractChangeApplicationItem.change_content=='5'}">项目负责人</c:if>
					<c:if test="${contractChangeApplicationItem.change_content=='6'}">项目归属部门</c:if>
					<c:if test="${contractChangeApplicationItem.change_content=='7'}">甲方</c:if>
					<c:if test="${contractChangeApplicationItem.change_content=='8'}">工程地址</c:if>
					<c:if test="${contractChangeApplicationItem.change_content=='9'}">开完工时间</c:if>
					<c:if test="${contractChangeApplicationItem.change_content=='10'}">其他</c:if>
				</display:column>
				<display:column title="变更时间">
					<fmt:formatDate value="${contractChangeApplicationItem.change_time}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column title="变更原因">
					${contractChangeApplicationItem.application_reason}
				</display:column>
				<display:column title="变更前">
					${contractChangeApplicationItem.change_before}
				</display:column>
				<display:column title="变更后">
					${contractChangeApplicationItem.change_after}
				</display:column>
<%-- 				<display:column title="备注">
				</display:column> --%>
				<display:column title="审批状态">
					${contractChangeApplicationItem.processStatus}
				</display:column>
				
				
				<%-- <display:column title="管理">
					<c:if test="${contractChangeApplicationItem.runId==0}">
						<a href="del.ht?id=${contractChangeApplicationItem.id}" class="link del"><span></span>删除</a>
						<a href="javascript:;" onclick="$.openFullWindow('${ctx}/platform/bpm/task/startFlowForm.ht?defId=10000001770018&businessKey=${contractChangeApplicationItem.id}');" class="link run"><span></span>提交</a>
					</c:if>
					<a href="edit.ht?id=${contractChangeApplicationItem.id}" class="link edit"><span></span>编辑</a>
					<a href="get.ht?id=${contractChangeApplicationItem.id}" class="link detail"><span></span>明细</a>
				</display:column> --%>
            </display:table>
        </div><!-- end of panel-body -->                
        <hotent:paging tableId="contractChangeApplicationItem"/>
    </div> <!-- end of panel -->
    <script>
		$(function(){
			$('.oa-accordion').oaAccordion({collapse: true});
		});
	</script>
</body>
</html>


