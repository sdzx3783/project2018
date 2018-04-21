<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>文档列表</title>
<%@include file="/commons/include/get.jsp"%>
<%@ taglib prefix="hotent" uri="http://www.jee-soft.cn/paging"%>

</head>
<body>	
	<div id="oa_list_title" class="oa-mgb10 oa-project-title"></div>
	<div id="oa_list_operation" class="oa-mgh20">
		<c:if test="${type==4 }">
			<a class="oa-button oa-button--primary oa-button--blue back" href="/makshi/portal/compportal/index.ht">返回</a>
		</c:if>
		<c:if test="${type==0 }">
			<a class="oa-button oa-button--primary oa-button--blue del" action="/makshi/doc/docinfo/del.ht">删除</a>
		</c:if>		
	</div>
	<div id="oa_list_search" style="padding: 0 20px;">
		<div class="oa-accordion">
			<div class="oa-accordion__title">查询条件</div>
	        <div class="oa-accordion__content">
		        <form id="searchForm" method="post" action="getfilelist.ht?type=${type}" class="oa-clear">
		            <div class="oa-fl oa-mgb10">
		                <div class="oa-label">文档名称</div>
		                <div class="oa-input-wrap oa-mgl100">
		               	 <input type="text" class="oa-input" name="Q_title_SL" value="${param['Q_title_SL'] }"/>
		                </div>
		            </div>
		            <div class="oa-fl oa-mgb10">
		                <div class="oa-label">申请时间从</div>
		                <div class="oa-input-wrap oa-mgl100 oa-input-wrap--ib">
		                    <input id="Q_begincreatetime_DL" name="Q_begincreatetime_DL" class="oa-input datePicker" datetype="1" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'Q_endcreatetime_DG\');}'})" value="${param['Q_begincreatetime_DL']}" />
		                </div>
		                <span>至</span>
		                <div class="oa-input-wrap oa-input-wrap--ib">
		                    <input id="Q_endcreatetime_DG" name="Q_endcreatetime_DG" class="oa-input datePicker" datetype="2" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'Q_begincreatetime_DL\');}'})"  value="${param['Q_endcreatetime_DG']}"/>
		                </div>
		            </div>
		            <div class="oa-fl oa-mgb10 oa-mgh20">
		            	<a href="javascript:;" class="oa-button oa-button--primary oa-button--blue" id="btnSearch" onclick="tosubmit()">查询</a>
		            </div>
		        </form>
	        </div>
        </div>
    </div>
    <div class="oa-pd20">
		<c:if test="${type==0 }">
			<c:set var="checkAll">
				<input type="checkbox" id="chkall" />
			</c:set>
		</c:if>
	    <div id="oa_list_content" class="oa-table-scroll-wrap">
		    <display:table name="docFileList" id="docFile" requestURI="getfilelist.ht?docId=${docId}" sort="external" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
		    	<c:if test="${type==0 }">
					<display:column title="${checkAll}" media="html" style="width:30px;">
						<input type="checkbox" class="pk" name="id" value="${docFile.dfid}">
					</display:column>
				</c:if>
				<display:column title="文档" style="width:170px">
					<c:if test="${type!=1 }"><a href="filedetail.ht?id=${docFile.dfid}">${docFile.title}</a></c:if>
					<c:if test="${type==1 }">${docFile.title}</c:if>
				</display:column>
				<display:column title="所属目录" style="width:70px">
					${docFile.docname}
				</display:column>
				<c:if test="${type==1 }">
					<display:column title="删除者"  style="width:30px">
						${docFile.updateor}
					</display:column>
				</c:if>
				<c:if test="${type!=1 }">
					<display:column title="所有者"  style="width:30px">
						${docFile.creator}
					</display:column>
				</c:if>
				
				<display:column title="创建日期" style="width:30px">
					<fmt:formatDate value='${docFile.createtime}' pattern='yyyy-MM-dd' />
				</display:column>
				<c:if test="${type==1 }">
					<display:column title="删除日期"  sortable="true" sortName="updatetime" style="width:30px">
						<fmt:formatDate value='${docFile.updatetime}' pattern='yyyy-MM-dd' />
					</display:column>
				</c:if>
				<c:if test="${type!=1 }">
					<display:column title="修改日期"  sortable="true" sortName="updatetime" style="width:30px">
						<fmt:formatDate value='${docFile.updatetime}' pattern='yyyy-MM-dd' />
					</display:column>
				</c:if>
				<display:column title="附件数" style="width:30px">
					${docFile.filecount}
				</display:column>
				<display:column title="状态" class="state" style="width:30px">
					<c:if test='${docFile.isdelete==1}'>已删除</c:if>
					<c:if test='${docFile.isdelete==0 && docFile.status==0 }'>草稿</c:if>
					<c:if test='${docFile.isdelete==0 && docFile.status==1 }'>正常</c:if>
				</display:column>
				<c:if test="${type==1 }">
					<display:column title="操作" style="width:30px" media="html">
						<f:a alias="backUser" css="link back" href="backDel.ht?id=${docFile.dfid}" >还原 </f:a>
					</display:column>
				</c:if>
			</display:table>
	    </div>
	    <hotent:paging tableId="docFile"/>
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


