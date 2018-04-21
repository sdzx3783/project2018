<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>人员分布情况统计</title>
<%@include file="/commons/include/get.jsp"%>
</head>
<body class="oa-mw-page">
	<div id="oa_list_title" class="oa-mgb20 oa-project-title"></div>
    <div id="oa_list_search" class="oa-pdb10 oa-mgh20">
        <form id="searchForm" method="post" action="list.ht" class="oa-clear">
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">统计时间</div>
                <div class="oa-mgl100">
                	<input type="text" name="selectedDate"  class="date"  data-class="oa-new-input" value="${param['selectedDate']}"/>
                </div>
            </div>
            <div class="oa-fl oa-mgb10 oa-mgh20">
                <button class="oa-button oa-button--primary oa-button--blue">统计</button>
            </div>
        </form>
    </div>
	
    <div class="oa-pdb20 oa-mgh20">
    	<div id="oa_list_content" class="oa-table-scroll-wrap">
	    	<table class="oa-table--default oa-table--nowrap">
	   			<tr>
	   				<th rowspan="2">部门</th>
	   				<th rowspan="2">部门总人数</th>
	   				<th colspan="2">性别分布</th>
	   				<th colspan="5">年龄分布</th>
	   				<th colspan="5">学历分布情况</th>
	   				<th colspan="5">职称分布情况</th>
	   				<th colspan="3">合同类型</th>
	   				<th colspan="4">工作状态变化情况</th>
	   				<th colspan="2">退休分布情况</th>
	   				<th colspan="2">个人晋升情况</th>
	   				<th colspan="25">执业资格证书情况</th>
	   			</tr>
	   			<tr>
		    		<td>女性</td>
		    		<td>男性</td>
		    		<td>a≤30</td>
		    		<td>30＜a≤40</td>
		    		<td>40＜a≤50</td>
		    		<td>50＜a≤60</td>
		    		<td>60以上</td>
		    		<td>研究生及以上</td>
		    		<td>本科</td>
		    		<td>大专</td>
		    		<td>中专</td>
		    		<td>其他</td>
		    		<td>教高</td>
					<td>高级</td>
					<td>中级</td>
					<td>初级</td>
					<td>其他</td>
					<td>劳动合同</td>
					<td>实习期</td>
					<td>劳务协议</td>
					<td>转正</td>
					<td>入职</td>
					<td>离职</td>
					<td>合同续签</td>
					<td>女性（年龄大于55周岁）</td>
					<td>男性（年龄大于60周岁）</td>
					<td>学历晋升</td>
					<td>职称晋升</td>
					<td>初始注册人次</td>
					<td>转入注册人数</td>
					<td>转出注册人数 </td>
					<td>建设部监理工程师</td>
					<td>建设部造价工程师</td>
					<td>水利部监理工程师</td>
					<td>水利部造价工程师</td>
					<td>水利部总监</td>
					<td>水利部监理员</td>
					<td>一级建造师</td>
					<td>二级建造师</td>
					<td>咨询工程师(投资)</td>
					<td>信息监理工程师</td>
					<td>注册设备监理工程师</td>
					<td>注册安全工程师</td>
					<td>注册公用设备工程师</td>
					<td>招标师</td>
					<td>系统集成项目管理工程师</td>
					<td>测绘师</td>
					<td>投资项目管理师</td>
					<td>一级结构工程师</td>
					<td>二级结构工程师</td>
	    		</tr>
		    	<c:forEach items="${list}" var="map" varStatus="status">
		    		<tr>
		    			<td>${map.orgName=="深水咨询"?"公司领导":map.orgName}</td>
		    			<td>${map.count}</td>
		    			<td>${map.woman}</td>
		    			<td>${map.man}</td>
		    			<td>${map.lessThirty}</td>
		    			<td>${map.thirtyToForty}</td>
		    			<td>${map.fortyToFifty}</td>
		    			<td>${map.fiftyToSixty}</td>
		    			<td>${map.greaterSixty}</td>
		    			<td>${map.master}</td>
			    		<td>${map.university}</td>
			    		<td>${map.college}</td>
			    		<td>${map.middlemaster}</td>
			    		<td>${map.otherDegree}</td>
		    			<td>${map.SN_ENGRpositional}</td>
						<td>${map.hightpositional}</td>
						<td>${map.middlepositional}</td>
						<td>${map.lestpositional}</td>
						<td>${map.otherpositional}</td>
		    			<td></td>
		    			<td></td>
		    			<td></td>
		    			<td>${map.entrycount}</td>
		    			<td>${map.formalcount}</td>
		    			<td>${map.resignationcount}</td>
		    			<td></td>
			    		<td>${map.womanretire}</td>
						<td>${map.manretire}</td>
						<td></td>
						<td></td>
						<td>${empty map.registNum?0:map.registNum}</td>
						<td>${empty map.inNum?0:map.inNum}</td>
						<td>${empty map.outNum?0:map.outNum} </td>
						<td>${empty map.qualification1?0:map.qualification1}</td>
						<td>${empty map.qualification2?0:map.qualification2}</td>
						<td>${empty map.qualification3?0:map.qualification3}</td>
						<td>${empty map.qualification4?0:map.qualification4}</td>
						<td>${empty map.qualification5?0:map.qualification5}</td>
						<td>${empty map.qualification6?0:map.qualification6}</td>
						<td>${empty map.qualification7?0:map.qualification7}</td>
						<td>${empty map.qualification8?0:map.qualification8}</td>
						<td>${empty map.qualification9?0:map.qualification9}</td>
						<td>${empty map.qualification10?0:map.qualification10}</td>
						<td>${empty map.qualification11?0:map.qualification11}</td>
						<td>${empty map.qualification12?0:map.qualification12}</td>
						<td>${empty map.qualification13?0:map.qualification13}</td>
						<td>${empty map.qualification14?0:map.qualification14}</td>
						<td>${empty map.qualification15?0:map.qualification15}</td>
						<td>${empty map.qualification16?0:map.qualification16}</td>
						<td>${empty map.qualification17?0:map.qualification17}</td>
						<td>${empty map.qualification18?0:map.qualification18}</td>
						<td>${empty map.qualification19?0:map.qualification19}</td>
		    		</tr>
		    	</c:forEach>
	    	</table>
    	</div>
	</div>
</body>
</html>


