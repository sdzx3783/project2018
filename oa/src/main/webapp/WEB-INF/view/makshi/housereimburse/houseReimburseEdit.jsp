<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
    <title>编辑 租房报销记录表</title>
    <%@include file="/codegen/include/customForm.jsp" %>
    <%@include file="/commons/include/ueditor.jsp" %>
    <script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
    <style>
        #houseReimburseForm{
            margin: 0 11px;
        }
        table.formTable{
            border-color: #dadfed;
        }
        .inputText.date{
            min-width: auto;
            width: 140px !important;
        }
        .montherror{
			border-color: #b94a48 !important;
		    -webkit-box-shadow: none;
		    box-shadow: none;
		}
    </style>
</head>
<body class="oa-mw-page">
	<div class="oa-pd20">
        <a class="oa-button oa-button--primary oa-button--blue save" id="dataFormSave">保存</a>
        <a class="oa-button oa-button--primary oa-button--blue" href="javascript:window.history.back(-1);">返回</a>
    </div>
    <div class="oa-mg20">
        <form id="houseReimburseForm" method="post" action="save.ht">
        	<input type="hidden" name="id" value="${houseReimburse.id}"/>
	        <input type="hidden" id="saveData" name="saveData"/>
	        <input type="hidden" id="items" name="items"/>
	        <input type="hidden" name="executeType"  value="start" />
        	<table class="oa-table--default" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
	            <caption>编辑租房报销记录表</caption>
  				<tr class="firstRow"> 
			   		<td>租房编号</td> 
			   		<td>${houseReimburse.house_id} </td> 
			   		<td>部门名称</td> 
			   		<td>${houseReimburse.department}</td>
			  	</tr> 
			  	<tr> 
			   		<td>承租人</td> 
			   		<td>${houseReimburse.rent_person}</td>
			   		<td>房屋地址</td> 
			   		<td>${houseReimburse.address}</td>
			  	</tr>
			  	<tr> 
				   <td>租房开始日期</td> 
				   <td><fmt:formatDate value='${houseReimburse.start_date}' pattern='yyyy-MM-dd'/></td>
				   <td>租房结束日期</td> 
				   <td><fmt:formatDate value='${houseReimburse.end_date}' pattern='yyyy-MM-dd'/></td>
			  	</tr>
			  	<tr> 
				   <td>租房金额</td> 
				   <td>${houseReimburse.rent_money}元</td>
				   <td>房屋结构</td>
			  	   <td>
				  		<c:if test="${houseReimburse.house_type=='1'}">1房</c:if>
				  		<c:if test="${houseReimburse.house_type=='2'}">2房</c:if>
				  		<c:if test="${houseReimburse.house_type=='3'}">3房</c:if>
				  		<c:if test="${houseReimburse.house_type=='4'}">4房</c:if>
				  		<c:if test="${houseReimburse.house_type=='5'}">5房</c:if>
				  		<c:if test="${houseReimburse.house_type=='6'}">6房</c:if>
				  		<c:if test="${houseReimburse.house_type=='7'}">7房</c:if>
				  		<c:if test="${houseReimburse.house_type=='8'}">8房</c:if>
				  		<c:if test="${houseReimburse.house_type=='9'}">9房</c:if>
			  	  </td>
			  	</tr>
			  	<tr>
				  	<td>入住人数</td>
				  	<td>${houseReimburse.number_people}</td>
				  	<td></td>
				  	<td></td>
			  	</tr>
				<tr style="display: none;"> 
			      <td>报销人</td> 
			      <td><input type="text" id="reimburse_person" name="m:house_reimburse:reimburse_person" class="oa-new-input" lablename="报销人" class="inputText" value="${houseReimburse.reimburse_person}" validate="{&quot;maxlength&quot;:50,&quot;required&quot;:&quot;true&quot;}" isflag="tableflag" /></td>
			      <td>报销日期</td> 
			      <td><input type="text" id="reimburse_date" name="m:house_reimburse:reimburse_date" data-class="oa-new-input" value="<fmt:formatDate value='${houseReimburse.reimburse_date}' pattern='yyyy-MM-dd'/>" class="inputText date"  /></td>
			    </tr> 
			  	<tr>
			   		<td>费用期间</td> 
			   		<td> 
			   			<input type="hidden" id="start_date"  name="m:house_reimburse:pay_start_date" value="<fmt:formatDate value='${houseReimburse.pay_start_date}' pattern='yyyy-MM-dd'/>" data-class="oa-new-input" class="inputText date" validate="{date:true}" />
			        	<input type="hidden" id="end_date"  name="m:house_reimburse:pay_end_date" value="<fmt:formatDate value='${houseReimburse.pay_end_date}' pattern='yyyy-MM-dd'/>" data-class="oa-new-input" class="inputText date" validate="{date:true}" />
			  			<span id="feePerdio"><fmt:formatDate value='${houseReimburse.pay_start_date}' pattern='yyyy-MM-dd'/> -- <fmt:formatDate value='${houseReimburse.pay_end_date}' pattern='yyyy-MM-dd'/></span>
			  		</td>
			  		<td>报销总费用</td> 
				  	<td>
				  		<input type="text" name="m:house_reimburse:reimburse_money" style="border: 0px;" lablename="租房金额" class="oa-new-input" readonly="readonly" value="${houseReimburse.reimburse_money}" validate="{maxlength:50}" isflag="tableflag" />
				  	</td>
			  	</tr> 
			</table>
		</form>
  </div>
  <div class="oa-mg20">
	<div><a class="btn blue oa-button oa-button--primary oa-button--blue" style="margin: 0 11px;" href="javascript:addRecord();">添加报销</a></div>
  	<form id="" style="margin: 14px 11px;" method="post" action="">
		<table class="oa-table--default" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
			<tr id="recordFields">
				<th></th>
				<th>报销日期</th>
				<th>报销金额(元)</th>
				<th>报销人</th>
			</tr>
			<c:choose>
				<c:when test="${empty records }">
					<tr id="recordNo"><td colspan="4" style="text-align: center;">无报销记录</td></tr>
				</c:when>
				<c:otherwise>
					<c:forEach items="${records }" var="e">
						<tr class="reimburseRecord">
							<td><a class="oa-button oa-button--primary oa-button--blue recorddel" href="javascript:;">删除</a></td>
							<td><input type="text" readonly="readonly"  class="reimburse_at oa-new-input Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:function(){changeReimburseAt();}})"  value="${e.reimburse_at }"></td>
							<td><input type="text"  class="moneys oa-new-input" value="${e.moneys }"></td>
							<td><input type="text"  class="person oa-new-input" value="${e.person }"></td>
						</tr>
					</c:forEach>
				</c:otherwise>
			</c:choose>
			<tr>
				<td></td>
				<td>合计金额</td>
				<td id="reimburse_money_show">${houseReimburse.reimburse_money}</td>
				<td></td>
			</tr>
		</table>
	</form>
  </div>
  <script type="text/javascript" src="${ctx}/js/makshi/house/houseReimburseEdit.js?t=<%=Math.random()%>"></script>
</body>
</html>
