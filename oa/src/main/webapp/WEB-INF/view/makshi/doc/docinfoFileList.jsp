<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>文档列表</title>
<%@include file="/commons/include/get.jsp"%>
<%@ taglib prefix="hotent" uri="http://www.jee-soft.cn/paging"%>
</head>
<body class="oa-mw-page">
	<div class="panel-toolbar">
		<div class="toolBar">
			<c:if test='${isWrite}'>
				<div class="group">
					<a class="link add" href="fileedit.ht?docId=${docId }"><span></span>新建文档</a>
				</div>
			
				<div class="l-bar-separator"></div>
				<div class="group">
					<a class="link del" action="del.ht"><span></span>删除</a>
				</div>
			</c:if>
			<c:if test="${from==null || from=='' }">
				<div class="l-bar-separator"></div>
				<div class="group">
					<a class="link back" href="list.ht?orgId=${orgId }&supId=${supId}"><span></span>返回目录</a>
				</div>
			</c:if>
			<c:if test="${from=='compportalindex'}">
				<div class="l-bar-separator"></div>
				<div class="group">
					<a class="link back" href="/makshi/portal/compportal/index.ht"><span></span>返回</a>
				</div>
			</c:if>
			<c:if test="${from=='orgportalindex'}">
				<div class="l-bar-separator"></div>
				<div class="group">
					<a class="link back" href="/makshi/portal/orgportal/index.ht?orgId=${param.portalOrgId }"><span></span>返回</a>
				</div>
			</c:if>
		</div>
	</div>
       <div id="oa_list_search" class="oa-pdb10 oa-mgh20">
	    <div class="oa-accordion">
            <div class="oa-accordion__title">查询条件</div>
            <div class="oa-accordion__content">
        	        <form id="searchForm" method="post" action="filelist.ht?docId=${docId}" class="oa-clear">
		            <div class="oa-fl oa-mgb10">
		                <div class="oa-label">所有者</div>
		                <div class="oa-input-wrap oa-mgl100">
		                    <input class="oa-input"  name="Q_creator_SL" value="${param['Q_creator_SL'] }"/>
		                </div>
		            </div>
		            <div class="oa-fl oa-mgb10">
		                <div class="oa-label">文档名称</div>
		                <div class="oa-input-wrap oa-mgl100">
		                    <input class="oa-input"  name="Q_title_SL" value="${param['Q_title_SL'] }"/>
		                </div>
		            </div>
		            <div class="oa-fl oa-mgb10">
			            <div class="oa-fl oa-mgb10">
			                <div class="oa-label">创建时间从</div>
			                <div class="oa-input-wrap oa-mgl100">
			                    <input class="oa-input Wdate" id="Q_begincreatetime_DL" name="Q_begincreatetime_DL" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'Q_endcreatetime_DG\');}'})" value="${param['Q_begincreatetime_DL']}"/>
			                </div>			                
			            </div>
			            <div class="oa-fl oa-mgb10">
			            	<div class="oa-label" style="width:50px;">至</div>
			                <div class="oa-input-wrap oa-mgl100" style="margin-left:50px;">
			                    <input class="oa-input Wdate" id="Q_endcreatetime_DG" name="Q_endcreatetime_DG" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'Q_begincreatetime_DL\');}'})"  value="${param['Q_endcreatetime_DG']}"/>
			                </div>
			            </div>	
			        </div>
		            <div class="oa-fl oa-mgb10 oa-mgh20">
		                <button id="btnSearch" class="oa-button oa-button--primary oa-button--blue">查询</button>
		            </div>
		        </form>    
            </div>
	    </div>
   	</div>
    <div class="oa-pdb20 oa-mgh20">
    	 <c:set var="checkAll">
			<input type="checkbox" id="chkall" />
		 </c:set>
      	 <div id="oa_list_content" class="oa-table-scroll-wrap">
      	 	<display:table export="true" name="docFileList" id="docFile" requestURI="filelist.ht?docId=${docId}" sort="external" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
            	<display:column title="${checkAll}" media="html">
					<input type="checkbox" class="pk" name="id" value="${docFile.dfid}">
				</display:column>
				<display:column title="标题">
					${docFile.title}
				</display:column>
				<display:column title="编号">
					${docFile.filenum}
				</display:column>
				<display:column title="所有者">
					${docFile.creator}
				</display:column>
				<display:column title="创建日期">
					<fmt:formatDate value='${docFile.createtime}' pattern='yyyy-MM-dd' />
				</display:column>
				<display:column title="修改日期">
					<fmt:formatDate value='${docFile.updatetime}' pattern='yyyy-MM-dd' />
				</display:column>
				<display:column title="附件数">
					${docFile.filecount}
				</display:column>
				<display:column title="状态">
					<c:if test='${docFile.status==0 }'>草稿</c:if>
					<c:if test='${docFile.status==1 }'>正常</c:if>
				</display:column>

				<display:column title="管理" media="html">
					<c:if test='${isWrite}'>
						<a href="del.ht?id=${docFile.dfid}" class="link del"><span></span>删除</a>
					
						<a href="fileedit.ht?id=${docFile.dfid}&docId=${docId}"	class="link edit"><span></span>编辑</a>
					</c:if>
					<a href="filedetail.ht?id=${docFile.dfid}&docId=${docId}&isWrite=${isWrite}"
						class="link detail" ><span></span>详情</a>
				</display:column>
            </display:table>
        </div><!-- end of panel-body -->                
        <hotent:paging tableId="docFile"/>
    </div> <!-- end of panel -->
    <script>
		$(function(){
			$('.oa-accordion').oaAccordion({collapse: true});
		});
	</script>
</body>
</html>


