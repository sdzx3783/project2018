<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>我的关注项目列表</title>
<%@include file="/commons/include/get.jsp"%>
<%@ taglib prefix="hotent" uri="http://www.jee-soft.cn/paging" %>
</head>
<script type="text/javascript">
	function cancelCollec(id){
		var url="cancelCollect.ht";
		$.post(url,{'id':id},function(data){
			if(data=='ok'){
				//取消成功
				window.location.reload(true);
			}else{
				$.ligerDialog.alert(data,"提示");
			}
		})
	}
</script>
<body class="oa-mw-page">

    <div id="oa_list_title" class="oa-mgb20 oa-project-title">
        <!-- <h2>我的关注项目列表</h2> -->
    </div>

    <div id="oa_list_search" class="oa-pdb10 oa-mgh20">
        <form id="searchForm" method="post" action="myCollections.ht" class="oa-clear">
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">项目名称</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input class="oa-input" type="text" name="Q_name_SL" value="${param['Q_name_SL'] }">
                </div>
            </div>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">项目负责人</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input class="oa-input" type="text" name="Q_applicant_SL" value="${param['Q_applicant_SL'] }">
                </div>
            </div>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">项目状态</div>
                <div class="oa-select-wrap oa-mgl100">
                    <select class="oa-select" name="status">
                        <option value="">请选择状态</option>
                         <option value="0" <c:if test="${param['status']==0}">selected=selected</c:if>>未开工</option>
                        <option value="1" <c:if test="${param['status']==1}">selected=selected</c:if>>在建</option>
                        <option value="2" <c:if test="${param['status']==2}">selected=selected</c:if>>停工</option>
                        <option value="3" <c:if test="${param['status']==3}">selected=selected</c:if>>已完工</option>
                    </select>
                </div>
            </div>

            <div class="oa-fl oa-mgb10 oa-mgh20">
                <button class="oa-button oa-button--primary oa-button--blue">查询</button>
            </div>
        </form>

    </div>

    <div class="oa-pdb20 oa-mgh20">
        <div id="oa_list_content" class="oa-table-scroll-wrap">
            <display:table name="list" id="projectItem" requestURI="myCollections.ht" sort="external" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
        		<display:column title="序号">
        			${projectItem_rowNum}
        		</display:column>
        		<display:column title="项目名称">
        		<a href="/makshi/project/project/projectDetail.ht?id=${projectItem.id }">${projectItem.name}</a>
        		</display:column>
        		<display:column title="负责人">
        			${projectItem.applicant}
        		</display:column>
        		<display:column title="项目状态">
        			<c:if test="${projectItem.status==0}">未开工</c:if>
        			<c:if test="${projectItem.status==1}">在建</c:if>
        			<c:if test="${projectItem.status==2}">停工</c:if>
        			<c:if test="${projectItem.status==3}">已完工</c:if>
        		</display:column>
        		<display:column title="合同编码">
        			${projectItem.contractnum }
        		</display:column>
        		<display:column title="合同名称">
        			${projectItem.contractname }
        		</display:column>
        		<display:column title="建设单位">
        			${projectItem.procompany }
        		</display:column>
        		<display:column title="操作" media="html">
        			<button href="javascript:;" onclick="cancelCollec(${projectItem.id})" class="oa-button-label">取消关注</button>
        		</display:column>
        	</display:table>
        </div>
    	<hotent:paging tableId="projectItem"/>
    </div>

</body>
</html>


