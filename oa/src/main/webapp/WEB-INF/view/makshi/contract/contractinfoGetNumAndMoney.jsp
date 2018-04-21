<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>深水咨询主要客户</title>
<%@include file="/commons/include/get.jsp"%>
<%@ taglib prefix="hotent" uri="http://www.jee-soft.cn/paging" %>
<script type="text/javascript">
	window.onload=function(){ 
	//设置年份的选择 
	var myDate= new Date(); 
	var startYear=myDate.getFullYear()-20;//起始年份 
	var endYear=myDate.getFullYear();//结束年份 
	var obj=document.getElementById('myYear');
	var obje=document.getElementById('eYear');
	for (var i=endYear;i>=startYear;i--) 
	{ 
	obj.options.add(new Option(i,i)); 
	obje.options.add(new Option(i,i)); 
	} 
	obj.options[obj.options.length-21].selected=1; 
	obje.options[obje.options.length-21].selected=1; 
	var selectedSYear = '${syear}';
	var selectedEYear = '${eyear}';
	if(selectedSYear){
	obj.options[endYear-selectedSYear].selected=1;
	}
	if(selectedEYear){
	obje.options[endYear-selectedEYear].selected=1;
	}
	} 
</script>
</head>
<body>
    <div id="oa_list_title" class="oa-mgb20 oa-project-title">
        <h2>${syear}<c:if test="${eyear!=null}">-${eyear}</c:if>年深水咨询主要客户</h2>
    </div>

    <div id="oa_list_search" class="oa-pdb10 oa-mgh20">
        <form id="searchForm" method="post" action="customers.ht" class="oa-clear">
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">起始年份</div>
                <div class="oa-select-wrap oa-mgl100">
                    <select class="oa-select" id="myYear" name="startDate" ></select> 
                </div>
            </div>

            <div class="oa-fl oa-mgb10">
                <div class="oa-label">结束年份</div>
                <div class="oa-select-wrap oa-mgl100">
                    <select class="oa-select" id="eYear" name="endDate"></select> 
                </div>
            </div>

            <div class="oa-fl oa-mgb10 oa-mgh20">
                <button class="oa-button oa-button--primary oa-button--blue">查询</button>
            </div>
        </form>
    </div>
	
    <div class="oa-pdb20 oa-mgh20">
        <div id="oa_list_content" class="oa-table-scroll-wrap">
        <table>
        	<tr>
        		<td width="100px">1</td>
        		<td width="100px">2</td>
        		<td width="100px">3</td>
        		<td width="100px">4</td>
        		<td width="100px">5</td>
        		<td width="100px">6</td>
        	</tr>
			<c:forEach items="${result}" var="map"  >
			<tr>
				<td>
					${map.key}
				</td>
				<c:forEach items="${map.value.list}" var="list"  >
				<td>${list.year }</td>
				<td>${list.num }</td>
				<td>${list.money }</td>
				</c:forEach>
				<td>${map.value.rowTotalNum }</td>
				<td>${map.value.rowTotalMoney }</td>
			</tr>
 		    </c:forEach>  
		<tr>
			<td>合计</td>
			<c:forEach items="${coltotal}" var="col"  >
				<c:forEach items="${col.value.list}" var="colList"  >
					<td>${colList.year}</td>
					<td>${colList.num}</td>
					<td>${colList.money}</td>
				</c:forEach>
			<td>${col.value.rowTotalNum}</td>
			<td>${col.value.rowTotalMoney}</td>
			</c:forEach>
		</tr>
		 </table>			
		</div>
	</div>
</body>
</html>


