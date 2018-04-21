<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>值班调休信息</title>
<%@include file="/commons/include/get.jsp" %>
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
    	width: 20%;
    	height: 39px;
    	border-top: 0;
    }
</style>
</head>
<body class="oa-mw-page">

	<div id="oa_list_title" class="oa-mgb20 oa-project-title">
        <h2>值班调休列表</h2>
    </div>

	<div id="oa_list_search" class="oa-pdb10 oa-mgh20">
        <form id="searchForm" method="post" action="list.ht" class="oa-clear">
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">姓名</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input class="oa-input"  name="name"   value="${param['name']}"/>
                </div>
            </div>

            <div class="oa-fl oa-mgb10 oa-mgh20">
                <button class="oa-button oa-button--primary oa-button--blue">查询</button>
            </div>
            <!-- <a class="oa-button oa-button--primary oa-button--blue upload"><span class="icon-location-arrow"></span><span>导出</span></a> -->
            <!-- <div class="oa-fl oa-mgb10 oa-mgh20">
               <a class="oa-button oa-button--primary oa-button--blue" href = "upload.ht"> 导出</a>
            </div> -->
        </form>
    </div>

	<div class="oa-pdb20 oa-mgh20">

      	 <div id="oa_list_content" class="oa-table-scroll-wrap oa-table-fixed-header">

    	    <display:table name="overTimeList" id="overTimeItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
    			<display:column title="姓名">${overTimeItem.name}</display:column>
    			<display:column title="值班总天数">${overTimeItem.overTimeDays}</display:column>
    			<display:column title="调休总天数">${overTimeItem.adjustDays}</display:column>
    			<display:column title="剩余未调休天数">${overTimeItem.leftAdjustDays}</display:column>
    		    <display:column title="管理" media="html">
    				<a href="get.ht?id=${overTimeItem.userId}" class="oa-button-label">明细</a> 
    				<a href="past.ht?id=${overTimeItem.userId}" class="oa-button-label">历史数据</a> 
    			</display:column> 
    		</display:table>

		</div>
		<hotent:paging tableId="overTimeItem"/>

	</div>	
	<script type="text/javascript">
		setTimeout(function() {
			var pd = $('tbody')[0].offsetWidth - $('tbody')[0].scrollWidth;
			$('thead').attr("style","padding-right:"+ pd +"px");
		},100);		
		
		$("a.upload").click(function(){
			$.ligerDialog.confirm("确定导出?","提示",function(rtn){
                if(rtn){
                	location.href = "/makshi/operation/overTimeAndAdjust/upload.ht"; 
                }
            });
		});
	</script>
</body>
</html>


