<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>车辆使用情况</title>
<%@include file="/commons/include/get.jsp" %>
<script type="text/javascript" src="${ctx}/js/makshi/userInfo.js"></script>
</head>
<script type="text/javascript">
$(function(){
	var val_ = $('.inputText.date').val();
	if(val_){
		return false;
	}
	var seachDay  = getdate(new Date());
	 $('.inputText.date').val(seachDay);
	
});
</script>
<body class="oa-mw-page">

	<div id="oa_list_title" class="oa-mgb20 oa-project-title">
        <h2>车辆使用情况列表</h2>
    </div>

    <div id="oa_list_search" class="oa-pdb10 oa-mgh20">
        <form id="searchForm" method="post" action="getUseInfoList.ht" class="oa-clear">
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">日期</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input class="oa-input date"  validate="{date:true}" name="date" value="${param['date']}"/>
                </div>
            </div>

            <div class="oa-fl oa-mgb10 oa-mgh20">
                <button class="oa-button oa-button--primary oa-button--blue">查询</button>
            </div>
        </form>
    </div>

	<div class="oa-pdb20 oa-mgh20">

      	 <div id="oa_list_content" class="oa-table-scroll-wrap">
      	 
		    <display:table name="useInfoList" id="carRegistItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
		    	<display:column title="车牌号码">${carRegistItem.carName}</display:column>
				<display:column title="00:00-07:00">
					<c:if test="${carRegistItem.zeroToSeven==1}">使用中</c:if>
					<c:if test="${carRegistItem.zeroToSeven!=1}"></c:if>
				</display:column>
				<display:column title="07:00-08:00">
					<c:if test="${carRegistItem.sevenToEight==1}">使用中</c:if>
					<c:if test="${carRegistItem.sevenToEight!=1}"></c:if>
				</display:column>
				<display:column title="08:00-09:00">
					<c:if test="${carRegistItem.eightToNine==1}">使用中</c:if>
					<c:if test="${carRegistItem.eightToNine!=1}"></c:if>
				</display:column>
				<display:column title="09:00-10:00">
					<c:if test="${carRegistItem.nineToTen==1}">使用中</c:if>
					<c:if test="${carRegistItem.nineToTen!=1}"></c:if>
				</display:column>
				<display:column title="10:00-11:00">
					<c:if test="${carRegistItem.tenToEleven==1}">使用中</c:if>
					<c:if test="${carRegistItem.tenToEleven!=1}"></c:if>
				</display:column>
				<display:column title="11:00-12:00">
					<c:if test="${carRegistItem.elevenToTwelve==1}">使用中</c:if>
					<c:if test="${carRegistItem.elevenToTwelve!=1}"></c:if>
				</display:column>
				<display:column title="12:00-13:00">
					<c:if test="${carRegistItem.twelveToThirteen==1}">使用中</c:if>
					<c:if test="${carRegistItem.twelveToThirteen!=1}"></c:if>
				</display:column>
				<display:column title="13:00-14:00">
					<c:if test="${carRegistItem.thirteenToFourteen==1}">使用中</c:if>
					<c:if test="${carRegistItem.thirteenToFourteen!=1}"></c:if>
				</display:column>
				<display:column title="14:00-15:00">
					<c:if test="${carRegistItem.fourteenToFifteen==1}">使用中</c:if>
					<c:if test="${carRegistItem.fourteenToFifteen!=1}"></c:if>
				</display:column>
				<display:column title="15:00-16:00">
					<c:if test="${carRegistItem.fifteenToSixteen==1}">使用中</c:if>
					<c:if test="${carRegistItem.fifteenToSixteen!=1}"></c:if>
				</display:column>
				<display:column title="16:00-17:00">
					<c:if test="${carRegistItem.sixteenToSeventeen==1}">使用中</c:if>
					<c:if test="${carRegistItem.sixteenToSeventeen!=1}"></c:if>
				</display:column>
				<display:column title="17:00-18:00">
					<c:if test="${carRegistItem.seventeenToEighteen==1}">使用中</c:if>
					<c:if test="${carRegistItem.seventeenToEighteen!=1}"></c:if>
				</display:column>
				<display:column title="18:00-19:00">
					<c:if test="${carRegistItem.eighteenToNineteen==1}">使用中</c:if>
					<c:if test="${carRegistItem.eighteenToNineteen!=1}"></c:if>
				</display:column>
				<display:column title="19:00-20:00">
					<c:if test="${carRegistItem.nineteenToTwenty==1}">使用中</c:if>
					<c:if test="${carRegistItem.nineteenToTwenty!=1}"></c:if>
				</display:column>
				<display:column title="20:00-21:00">
					<c:if test="${carRegistItem.twentyToTwentyOne==1}">使用中</c:if>
					<c:if test="${carRegistItem.twentyToTwentyOne!=1}"></c:if>
				</display:column>
				<display:column title="21:00-22:00">
					<c:if test="${carRegistItem.twentyOneToTwentyTwo==1}">使用中</c:if>
					<c:if test="${carRegistItem.twentyOneToTwentyTwo!=1}"></c:if>
				</display:column>
				<display:column title="22:00-23:00">
					<c:if test="${carRegistItem.twentyTwoToTwentyThree==1}">使用中</c:if>
					<c:if test="${carRegistItem.twentyTwoToTwentyThree!=1}"></c:if>
				</display:column>
				<display:column title="23:00-24:00">
					<c:if test="${carRegistItem.twentyThreeToTwentyFour==1}">使用中</c:if>
					<c:if test="${carRegistItem.twentyThreeToTwentyFour!=1}"></c:if>
				</display:column>
			</display:table>
		</div>	

		<hotent:paging tableId="carRegistItem"/>

	</div>
</body>
</html>


