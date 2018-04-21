<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>流程定义列表</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body>      
    <div class="panel">
    <div class="panel-top"> 
            <div class="panel-toolbar">
                <div class="toolBar">
                    <div class="group">
                    
                    <a class="link search" id="btnSearch"><span></span>查询</a></div>
                    <div class="l-bar-separator"></div>
                </div>
            </div>
            <div class="panel-search">
                   
                <form id="searchForm" method="post" action="/makshi/dialog/bpmdefdialog/list.ht">       
                    <ul class="row">
                        <li><span class="label">分类:</span>
                        	<select name="typeId">
                        		<option value="">-请选择-</option>
                        		<c:forEach items="${globalTypelist }" var="globalType">
                        			<option value="${globalType.typeId }" <c:if test="${globalType.typeId==param['typeId']}">selected=selected</c:if>>${globalType.typeName }</option>
                        		</c:forEach>
                        	</select>
                        </li>
                        <li><span class="label">标题:</span><input type="text" name="Q_subject_SL"  class="inputText" value="${param['Q_subject_SL']}"/></li>
                    </ul>
                </form>
            </div>
        </div>
        <div class="panel-body">
                <display:table name="bpmDefinitionList" id="bpmDefinitionItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="false"  class="table-grid">
                    <display:column  style="width:30px;">
               			<input type="radio" name="selectDefId"  value="${bpmDefinitionItem.defId}">
                    </display:column>
                    <display:column property="subject" title="标题" sortable="true" sortName="subject" ></display:column>
                    
                    <display:column title="分类" sortable="true" sortName="typeId">
                        <c:out value="${bpmDefinitionItem.typeName}"></c:out>
                    </display:column>
                    <%-- 
                    <display:column title="流程定义ID" media="html" style="width:50px;text-align:center" class="rowOps">
                        ${bpmDefinitionItem.defId}
                    </display:column> --%>
                </display:table>
                <hotent:paging tableId="bpmDefinitionItem" showExplain="false"/>
            
        </div><!-- end of panel-body -->                
    </div> <!-- end of panel -->
</body>
</html>


