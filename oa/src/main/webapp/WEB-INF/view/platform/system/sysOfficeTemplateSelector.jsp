<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统模版管理</title>
<%@include file="/commons/include/get.jsp" %>
<style type="text/css">
	html,body{ padding:0px; margin:0; width:100%;height:100%;overflow: hidden;}
	.panel-search {
		margin-left: 6px;
	}
	
</style>	
<script type="text/javascript">
	$(function(){		
		$("tr.odd,tr.even").unbind("hover");
		$("tr.odd,tr.even").click(function(){
			var obj=$(this);
			$("input[name='rtn']",obj).attr("checked","checked");
			$(this).siblings().removeClass("over").end().addClass("over");
		});	
	});
</script>
</head>
<body>
	<div id="oa_list_search" style="padding: 10px 20px;">
		<div class="oa-accordion">
			<div class="oa-accordion__title">查询条件</div>
	        <div class="oa-accordion__content">
	        	<form class="oa-clear" id="searchForm" method="post" action="selector.ht?templatetype=${templatetype}">
					<div class="oa-fl">
		                <div class="oa-label">主题</div>
		                <div class="oa-input-wrap oa-mgl100">
		                   <input type="text" name="Q_subject_S"  class="oa-input"  value="${param['Q_subject_S']}"/>
		                </div>
		                <!-- <li><span class="label">模版类型:</span>
						<select name="Q_templatetype_S">
							<option value="">请选择</option>
							<option value="1">普通模版</option>
							<option value="2">套红模版</option>
						</select> </li> -->
		            </div>
		            <div class="oa-fl oa-mgh20">
		            	<a class="oa-button oa-button--primary oa-button--blue" id="btnSearch" onclick="tosubmit()">查询</a>
		            </div>
				</form>
	        </div>
	    </div>
	</div>
	<div class="oa-mgh20">
		<div id="oa_list_content" class="oa-table-scroll-wrap">
			 <display:table  name="sysOfficeTemplateList" id="sysOfficeTemplateItem" requestURI="selector.ht" sort="external" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
			 	<display:column  media="html" style="width:30px;">
					  <input type="radio" class="rtn" name="rtn" value="${sysOfficeTemplateItem.id},${sysOfficeTemplateItem.subject},${sysOfficeTemplateItem.path}">
				</display:column>
				<display:column property="subject" title="主题" sortable="true" sortName="subject"></display:column>
				<display:column title="模版类型" sortable="true" sortName="templatetype">
					<c:choose>
						<c:when test="${sysOfficeTemplateItem.templatetype==1 }">普通模版</c:when>
						<c:otherwise>
							套红模版
						</c:otherwise>
					</c:choose>
				</display:column>
			 </display:table>			 
		</div>
		<hotent:paging tableId="sysOfficeTemplateItem"/>
	</div>
	<script type="text/javascript">
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


