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
                <form id="searchForm" method="post" action="/makshi/dialog/bpmdefdialog/dialogObj.ht?id=${businessKey}">       
                    <ul class="row">
                     <li><span class="label">车牌号:</span>
                     <input type="text" name="Q_carId_SL"  class="inputText" value="${param['Q_carId_SL']}"/></li>
                    <li><span class="label">车辆型号:</span>
                     <input type="text" name="Q_version_SL"  class="inputText" value="${param['Q_version_SL']}"/></li>
                    <li><span class="label">保管人:</span>
                     <input type="text" name="Q_kepper_SL"  class="inputText" value="${param['Q_kepper_SL']}"/></li>
                    </ul>
                </form>
            </div>
        </div>
        <div class="panel-body">
                <display:table name="carRegistList" id="carRegistItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="false"  class="table-grid">
                    <display:column  style="width:30px;">
               			<input type="radio" name="selectCarId"  data-id = "${carRegistItem.id}" data-status = "${carRegistItem.status}" value="${carRegistItem.carId}">
                    </display:column>
                    <display:column  title="车牌号">${carRegistItem.carId}</display:column>
                    <display:column title="车辆型号">${carRegistItem.version}</display:column>
                    <display:column title="保管人">${carRegistItem.kepper}</display:column>
                    <display:column title="状态">
                    	<c:if test="${carRegistItem.status=='0'}">可用</c:if>
                    	<c:if test="${carRegistItem.status=='1'}">不可用</c:if>
                    </display:column>
                </display:table>
                <hotent:paging tableId="bpmDefinitionItem" showExplain="false"/>
            
        </div><!-- end of panel-body -->                
    </div> <!-- end of panel -->
</body>
</html>


