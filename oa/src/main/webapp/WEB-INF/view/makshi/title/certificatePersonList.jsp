<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>注册人员汇总表</title>
<%@include file="/commons/include/get.jsp"%>
<%@ taglib prefix="hotent" uri="http://www.jee-soft.cn/paging" %>
<script type="text/javascript">
	window.onload=function(){ 
	//设置年份的选择 
	var myDate= new Date(); 
	var startYear="1998";//起始年份 
	var endYear=myDate.getFullYear();//结束年份 
	var obj=document.getElementById('myYear');
	for (var i=endYear;i>=startYear;i--) 
	{ 
	obj.options.add(new Option(i,i)); 
	} 
	var selectedYear = '${syear}';
	if(selectedYear){
	obj.options[endYear-selectedYear].selected=1;
	}
	} 
</script>
<style>
	.oa-table-fixed-header {
		overflow: hidden;
		
	}
	.oa-table-fixed-header * {
		box-sizing: border-box;
	}
	.oa-table-fixed-header .panel-table {
		height: 100%;
	}
    .oa-table-fixed-header table {
    	display: block;
    	height: 100%;
    }
    .oa-table-fixed-header thead {
    	display: block;
    	width: 100%;
    }
    .oa-table-fixed-header tbody {
    	display: block;
    	width: 100%;
    	overflow-x: auto;
    }
    .oa-table-fixed-header tr {
    	display: block;
    	width: 100%;
    	overflow: hidden;
    }
    .oa-table-fixed-header td,
    .oa-table-fixed-header th {
    	display: block;
    	float: left;
    	width: 16.66666666667%;
    	height: 39px;
    	border-top: 0;
    }
</style>
</head>
<body class="oa-mw-page">
    <div id="oa_list_title" class="oa-mgb20 oa-project-title">
        <h2>${syear}年注册人员汇总表</h2>
    </div>

    <div id="oa_list_search" class="oa-pdb10 oa-mgh20">
        <form id="searchForm" method="post" action="certificatePerson.ht" class="oa-clear">
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">年份</div>
                <div class="oa-select-wrap oa-mgl100">
                    <select class="oa-select" id="myYear" name="selectYear" ></select> 
                </div>
            </div>
            <div class="oa-fl oa-mgb10 oa-mgh20">
                <button class="oa-button oa-button--primary oa-button--blue">查询</button>
            </div>
        </form>
    </div>

    <div class="oa-pdb20 oa-mgh20">
        <div id="oa_list_content" class="oa-table-scroll-wrap oa-table-fixed-header">
            <display:table export="true" name="certificatePersonlist" id="certificatePersonItem" requestURI="#" sort="external" cellpadding="1" cellspacing="1"  class="oa-table--default oa-table--nowrap">
                <display:column title="类型" class="w30"><span></span>${certificatePersonItem.certificate_type}</display:column>
                <display:column title="在册人次" class="w30">
                	<c:choose>
	                	<c:when test="${certificatePersonItem.existNum>0 && certificatePersonItem.certificate_type!='合计' }">
	                		<c:choose>
	                			<c:when test="${certificatePersonItem.certificate_type=='三类人员安全生产考核合格证' }">
			               			<a href="/makshi/gates/executive/qualificationPeopleDetail.ht?type=1&year=${syear }&qualification=1&other=1" target="_blank">${certificatePersonItem.existNum}</a> 
	                			</c:when>
	                			<c:when test="${certificatePersonItem.certificate_type=='深圳市监理员' }">
			               			<a href="/makshi/gates/executive/qualificationPeopleDetail.ht?type=1&year=${syear }&qualification=2&other=1" target="_blank">${certificatePersonItem.existNum}</a> 
	                			</c:when>
	                			<c:when test="${certificatePersonItem.certificate_type=='深圳市监理工程师' }">
			               			<a href="/makshi/gates/executive/qualificationPeopleDetail.ht?type=1&year=${syear }&qualification=3&other=1" target="_blank">${certificatePersonItem.existNum}</a> 
	                			</c:when>
	                			<c:when test="${certificatePersonItem.certificate_type=='水利部监理工程师信用手册' }">
			               			<a href="/makshi/gates/executive/qualificationPeopleDetail.ht?type=1&year=${syear }&qualification=4&other=1" target="_blank">${certificatePersonItem.existNum}</a> 
	                			</c:when>
	                			<c:when test="${certificatePersonItem.certificate_type=='建设部监理工程师信用手册' }">
			               			<a href="/makshi/gates/executive/qualificationPeopleDetail.ht?type=1&year=${syear }&qualification=5&other=1" target="_blank">${certificatePersonItem.existNum}</a> 
	                			</c:when>
	                			<c:when test="${certificatePersonItem.certificate_type=='深圳市档案员' }">
			               			<a href="/makshi/gates/executive/qualificationPeopleDetail.ht?type=1&year=${syear }&qualification=6&other=1" target="_blank">${certificatePersonItem.existNum}</a> 
	                			</c:when>
	                			<c:otherwise>
			               			<a href="/makshi/gates/executive/qualificationPeopleDetail.ht?type=1&year=${syear }&qualification=${certificatePersonItem.certificate_type}" target="_blank">${certificatePersonItem.existNum}</a> 
	                			</c:otherwise>
	                		</c:choose>
	               		</c:when>
	               		<c:otherwise>
	               			${certificatePersonItem.existNum }
	               		</c:otherwise>
                	</c:choose>
                </display:column>
                <display:column title="初始注册人次" class="w30">
                	<c:choose>
	                	<c:when test="${certificatePersonItem.registNum>0  && certificatePersonItem.certificate_type!='合计' }">
	                		<c:choose>
	                			<c:when test="${certificatePersonItem.certificate_type=='三类人员安全生产考核合格证' }">
			               			<a href="/makshi/gates/executive/qualificationPeopleDetail.ht?type=1&year=${syear }&qualification=1&other=1" target="_blank">${certificatePersonItem.registNum}</a> 
	                			</c:when>
	                			<c:when test="${certificatePersonItem.certificate_type=='深圳市监理员' }">
			               			<a href="/makshi/gates/executive/qualificationPeopleDetail.ht?type=2&year=${syear }&qualification=2&other=1" target="_blank">${certificatePersonItem.registNum}</a> 
	                			</c:when>
	                			<c:when test="${certificatePersonItem.certificate_type=='深圳市监理工程师' }">
			               			<a href="/makshi/gates/executive/qualificationPeopleDetail.ht?type=2&year=${syear }&qualification=3&other=1" target="_blank">${certificatePersonItem.registNum}</a> 
	                			</c:when>
	                			<c:when test="${certificatePersonItem.certificate_type=='水利部监理工程师信用手册' }">
			               			<a href="/makshi/gates/executive/qualificationPeopleDetail.ht?type=2&year=${syear }&qualification=4&other=1" target="_blank">${certificatePersonItem.registNum}</a> 
	                			</c:when>
	                			<c:when test="${certificatePersonItem.certificate_type=='建设部监理工程师信用手册' }">
			               			<a href="/makshi/gates/executive/qualificationPeopleDetail.ht?type=2&year=${syear }&qualification=5&other=1" target="_blank">${certificatePersonItem.registNum}</a> 
	                			</c:when>
	                			<c:when test="${certificatePersonItem.certificate_type=='深圳市档案员' }">
			               			<a href="/makshi/gates/executive/qualificationPeopleDetail.ht?type=2&year=${syear }&qualification=6&other=1" target="_blank">${certificatePersonItem.registNum}</a> 
	                			</c:when>
	                			<c:otherwise>
			               			<a href="/makshi/gates/executive/qualificationPeopleDetail.ht?type=2&year=${syear }&qualification=${certificatePersonItem.certificate_type}" target="_blank">${certificatePersonItem.registNum}</a> 
	                			</c:otherwise>
	                		</c:choose>
	               		</c:when>
	               		<c:otherwise>
	               			${certificatePersonItem.registNum }
	               		</c:otherwise>
                	</c:choose>
                </display:column>
                <display:column title="转入注册人次" class="w30">
                	<c:choose>
	                	<c:when test="${certificatePersonItem.inNum>0  && certificatePersonItem.certificate_type!='合计' }">
	                		<c:choose>
	                			<c:when test="${certificatePersonItem.certificate_type=='三类人员安全生产考核合格证' }">
			               			<a href="/makshi/gates/executive/qualificationPeopleDetail.ht?type=3&year=${syear }&qualification=1&other=1" target="_blank">${certificatePersonItem.inNum}</a> 
	                			</c:when>
	                			<c:when test="${certificatePersonItem.certificate_type=='深圳市监理员' }">
			               			<a href="/makshi/gates/executive/qualificationPeopleDetail.ht?type=3&year=${syear }&qualification=2&other=1" target="_blank">${certificatePersonItem.inNum}</a> 
	                			</c:when>
	                			<c:when test="${certificatePersonItem.certificate_type=='深圳市监理工程师' }">
			               			<a href="/makshi/gates/executive/qualificationPeopleDetail.ht?type=3&year=${syear }&qualification=3&other=1" target="_blank">${certificatePersonItem.inNum}</a> 
	                			</c:when>
	                			<c:when test="${certificatePersonItem.certificate_type=='水利部监理工程师信用手册' }">
			               			<a href="/makshi/gates/executive/qualificationPeopleDetail.ht?type=3&year=${syear }&qualification=4&other=1" target="_blank">${certificatePersonItem.inNum}</a> 
	                			</c:when>
	                			<c:when test="${certificatePersonItem.certificate_type=='建设部监理工程师信用手册' }">
			               			<a href="/makshi/gates/executive/qualificationPeopleDetail.ht?type=3&year=${syear }&qualification=5&other=1" target="_blank">${certificatePersonItem.inNum}</a> 
	                			</c:when>
	                			<c:when test="${certificatePersonItem.certificate_type=='深圳市档案员' }">
			               			<a href="/makshi/gates/executive/qualificationPeopleDetail.ht?type=3&year=${syear }&qualification=6&other=1" target="_blank">${certificatePersonItem.inNum}</a> 
	                			</c:when>
	                			<c:otherwise>
			               			<a href="/makshi/gates/executive/qualificationPeopleDetail.ht?type=3&year=${syear }&qualification=${certificatePersonItem.certificate_type}" target="_blank">${certificatePersonItem.inNum}</a> 
	                			</c:otherwise>
	                		</c:choose>
	               		</c:when>
	               		<c:otherwise>
	               			${certificatePersonItem.inNum }
	               		</c:otherwise>
                	</c:choose>
                </display:column>
                <display:column title="转出注册人次" class="w30">
                	<c:choose>
	                	<c:when test="${certificatePersonItem.outNum>0  && certificatePersonItem.certificate_type!='合计' }">
	                		<c:choose>
	                			<c:when test="${certificatePersonItem.certificate_type=='三类人员安全生产考核合格证' }">
			               			<a href="/makshi/gates/executive/qualificationPeopleDetail.ht?type=4&year=${syear }&qualification=1&other=1" target="_blank">${certificatePersonItem.outNum}</a> 
	                			</c:when>
	                			<c:when test="${certificatePersonItem.certificate_type=='深圳市监理员' }">
			               			<a href="/makshi/gates/executive/qualificationPeopleDetail.ht?type=4&year=${syear }&qualification=2&other=1" target="_blank">${certificatePersonItem.outNum}</a> 
	                			</c:when>
	                			<c:when test="${certificatePersonItem.certificate_type=='深圳市监理工程师' }">
			               			<a href="/makshi/gates/executive/qualificationPeopleDetail.ht?type=4&year=${syear }&qualification=3&other=1" target="_blank">${certificatePersonItem.outNum}</a> 
	                			</c:when>
	                			<c:when test="${certificatePersonItem.certificate_type=='水利部监理工程师信用手册' }">
			               			<a href="/makshi/gates/executive/qualificationPeopleDetail.ht?type=4&year=${syear }&qualification=4&other=1" target="_blank">${certificatePersonItem.outNum}</a> 
	                			</c:when>
	                			<c:when test="${certificatePersonItem.certificate_type=='建设部监理工程师信用手册' }">
			               			<a href="/makshi/gates/executive/qualificationPeopleDetail.ht?type=4&year=${syear }&qualification=5&other=1" target="_blank">${certificatePersonItem.outNum}</a> 
	                			</c:when>
	                			<c:when test="${certificatePersonItem.certificate_type=='深圳市档案员' }">
			               			<a href="/makshi/gates/executive/qualificationPeopleDetail.ht?type=4&year=${syear }&qualification=6&other=1" target="_blank">${certificatePersonItem.outNum}</a> 
	                			</c:when>
	                			<c:otherwise>
			               			<a href="/makshi/gates/executive/qualificationPeopleDetail.ht?type=4&year=${syear }&qualification=${certificatePersonItem.certificate_type}" target="_blank">${certificatePersonItem.outNum}</a> 
	                			</c:otherwise>
	                		</c:choose>
	               		</c:when>
	               		<c:otherwise>
	               			${certificatePersonItem.outNum }
	               		</c:otherwise>
                	</c:choose>
                </display:column>
                <display:column title="上岗培训人次" class="w30">
                	<c:choose>
	                	<c:when test="${certificatePersonItem.trainingNum>0  && certificatePersonItem.certificate_type!='合计' }">
	                		<c:choose>
	                			<c:when test="${certificatePersonItem.certificate_type=='三类人员安全生产考核合格证' }">
			               			<a href="/makshi/gates/executive/qualificationPeopleDetail.ht?type=5&year=${syear }&qualification=1&other=1" target="_blank">${certificatePersonItem.trainingNum}</a> 
	                			</c:when>
	                			<c:when test="${certificatePersonItem.certificate_type=='深圳市监理员' }">
			               			<a href="/makshi/gates/executive/qualificationPeopleDetail.ht?type=5&year=${syear }&qualification=2&other=1" target="_blank">${certificatePersonItem.trainingNum}</a> 
	                			</c:when>
	                			<c:when test="${certificatePersonItem.certificate_type=='深圳市监理工程师' }">
			               			<a href="/makshi/gates/executive/qualificationPeopleDetail.ht?type=5&year=${syear }&qualification=3&other=1" target="_blank">${certificatePersonItem.trainingNum}</a> 
	                			</c:when>
	                			<c:when test="${certificatePersonItem.certificate_type=='水利部监理工程师信用手册' }">
			               			<a href="/makshi/gates/executive/qualificationPeopleDetail.ht?type=5&year=${syear }&qualification=4&other=1" target="_blank">${certificatePersonItem.trainingNum}</a> 
	                			</c:when>
	                			<c:when test="${certificatePersonItem.certificate_type=='建设部监理工程师信用手册' }">
			               			<a href="/makshi/gates/executive/qualificationPeopleDetail.ht?type=5&year=${syear }&qualification=5&other=1" target="_blank">${certificatePersonItem.trainingNum}</a> 
	                			</c:when>
	                			<c:when test="${certificatePersonItem.certificate_type=='深圳市档案员' }">
			               			<a href="/makshi/gates/executive/qualificationPeopleDetail.ht?type=5&year=${syear }&qualification=6&other=1" target="_blank">${certificatePersonItem.trainingNum}</a> 
	                			</c:when>
	                			<c:otherwise>
			               			<a href="/makshi/gates/executive/qualificationPeopleDetail.ht?type=5&year=${syear }&qualification=${certificatePersonItem.certificate_type}" target="_blank">${certificatePersonItem.trainingNum}</a> 
	                			</c:otherwise>
	                		</c:choose>
	               		</c:when>
	               		<c:otherwise>
	               			${certificatePersonItem.trainingNum}
	               		</c:otherwise>
                	</c:choose>
                </display:column>
            </display:table>
        </div>          
    </div>
    <script type="text/javascript">
		setTimeout(function() {
			var pd = $('tbody')[0].offsetWidth - $('tbody')[0].scrollWidth;
			$('thead').attr("style","padding-right:"+ pd +"px");
		},100);		
		
	</script>
</body>
</html>


