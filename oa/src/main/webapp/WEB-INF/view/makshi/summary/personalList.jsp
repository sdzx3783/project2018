<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>个人会员汇总表</title>
<%@include file="/commons/include/get.jsp"%>
<%@ taglib prefix="hotent" uri="http://www.jee-soft.cn/paging" %>
</head>
<body class="oa-mw-page">
    <div id="oa_list_title" class="oa-mgb10 oa-project-title">
        <!-- <h2>个人会员汇总表</h2> -->
    </div>
	<div id="oa_list_operation" class="oa-mgh20 oa-mgl20">
		<a class="oa-button oa-button--primary oa-button--blue add" href="edit.ht">添加</a>
		<a class="oa-button oa-button--primary oa-button--blue del"  action="del.ht">删除</a>					
		<c:if test="${not empty vipList }">
			<a class="oa-button oa-button--primary oa-button--blue " href='javascript:;' onclick="tosubmit(1);" >导出</a>
		</c:if>					
	</div>
	<div id="oa_list_search" class="oa-pdb10 oa-mgh20">
		<div class="oa-accordion">
           	<div class="oa-accordion__title">查询条件</div>
           	<div class="oa-accordion__content">
           		
	            <form id="searchForm" method="post" action="list.ht" class="oa-clear">
		            <div class="oa-fl oa-mgb10">
		                <div class="oa-label">入会机构</div>
		                <div class="oa-input-wrap oa-mgl100">
		                    <input type="text" name="Q_organization_SL" value="${param['Q_organization_SL'] }"  class="oa-input" />
		                </div>
		            </div>
		            <div class="oa-fl oa-mgb10">
		                <div class="oa-label">级别</div>
		                <div class="oa-input-wrap oa-mgl100">
		                    <input type="text" name="Q_level_SL" value="${param['Q_level_SL'] }" class="oa-input" />
		                </div>
		            </div>
		            <div class="oa-fl oa-mgb10">
		                <div class="oa-label">证书编号</div>
		                <div class="oa-input-wrap oa-mgl100">
		                    <input type="text" name="Q_certificateNo_SL" value="${param['Q_certificateNo_SL'] }" class="oa-input" />
		                </div>
		            </div>
		            <div class="oa-fl oa-mgb10">
		                <div class="oa-label">发证日期</div>
		                <div class="oa-input-wrap oa-mgl100">
		                	<input onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" type="text" name="certificationDate" value="${param['certificationDate'] }" class="oa-input Wdate" />
		                </div>
		            </div>
		            <div class="oa-fl oa-mgb10">
		                <div class="oa-label">有效日期</div>
		                <div class="oa-input-wrap oa-mgl100">
		                	<input onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" type="text" name="certeffectiveTime" value="${param['certeffectiveTime'] }" class="oa-input Wdate" />
		                </div>
		            </div>
		            <div class="oa-fl oa-mgb10 oa-mgh20">
		                <button class="oa-button oa-button--primary oa-button--blue" onclick="tosubmit(0);">查询</button>
		            </div>
		        </form>
	        </div>
		</div>
    </div>
    
	<div class="oa-pdb20 oa-mgh20">
		<div id="oa_list_content" class="oa-table-scroll-wrap">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="vipList" id="vipItem"  requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
				<display:column title="${checkAll}" media="html">
			  		<input type="checkbox" class="pk" name="id" value="${vipItem.id}">
				</display:column>
				 <display:column property="organization" title="入会机构" sortable="false" sortName="organization"></display:column>
				 <display:column property="level" title="级别"  sortable="false" sortName="level"></display:column>
                 <display:column property="certificateNo" title="证书编号" sortable="false" sortName="certificateNo"></display:column>
                 <display:column title="首次入会时间" sortable="false" sortName="membershipTime">
                 	<c:if test="${vipItem.membershipTime!=null && vipItem.membershipTime!=''}">
                 		${vipItem.membershipTime}年
                 	</c:if>
                 </display:column> 
                 <display:column property="paymentStandard" title="缴费标准（元）" sortable="false" sortName="paymentStandard"></display:column> 
                 <display:column title="会费缴纳至" sortable="false" sortName="effectiveTime">
                	 <c:if test="${vipItem.effectiveTime!=null && vipItem.effectiveTime!=''}">
                 		${vipItem.effectiveTime}年
                 	 </c:if>
                 </display:column> 
                 <display:column property="certificationDate" title="发证日期" sortable="false" sortName="certificationDate">
                 </display:column> 
                 <display:column property="certeffectiveTime" title="有效日期" sortable="false" sortName="certeffectiveTime">
                 </display:column> 
                 <display:column property="remark" title="备注" sortable="false" sortName="remark"></display:column> 
				 <display:column title="操作" media="html" style="width:220px">
					 <a href="edit.ht?id=${vipItem.id}" class="oa-button-label"><span></span>编辑</a>
					 <a href="detail.ht?id=${vipItem.id}" class="oa-button-label"><span></span>明细</a>
					 <a href="del.ht?id=${vipItem.id}" class="oa-button-label del"><span></span>删除</a>
				</display:column>
			</display:table>
		</div>
		<hotent:paging tableId="vipItem"/>			
	</div> <!-- end of panel -->
	
	<script type="text/javascript">
		$('.oa-accordion').oaAccordion({collapse: true});
		function tosubmit(type){
			if(type==0){
				$("#searchForm").attr("action","list.ht")
				$("#searchForm").submit();
			}else if(type==1){
				$("#searchForm").attr("action","export.ht")
				$("#searchForm").submit();
			}
		}
	</script>
</body>
</html>