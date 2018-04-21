<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
    <title>添加租房报销记录表</title>
    <%@include file="/codegen/include/customForm.jsp" %>
    <%@include file="/commons/include/ueditor.jsp" %>
    <script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
<style>
    #houseReimburseForm{
        margin: 0 10px;
    }
    table.formTable{
        border-color: #dadfed;
    }
    .inputText.date{
        width: 124px;
        min-width: auto;
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
        <a class="oa-button oa-button--primary oa-button--blue" href="list.ht">返回</a>
    </div>
    <div class="oa-mg20">
		<form id="houseReimburseForm" method="post" action="save.ht">
		    <input type="hidden" name="id" value="${houseReimburse.id}"/>
            <input type="hidden" id="saveData" name="saveData"/>
             <input type="hidden" id="items" name="items"/>
            <input type="hidden" name="executeType"  value="start" />
			<table class="oa-table--default" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
		    	<caption>添加租房报销记录表</caption>
			    <tr class="firstRow"> 
	   				<td>租房编号</td> 
	  				<td>
	  					<input type="text" class="oa-new-input"  name="m:house_reimburse:house_id" value="" validate="{&quot;maxlength&quot;:1000,&quot;required&quot;:&quot;true&quot;}">
	   				</td>
	   				<td>部门名称</td> 
	   				<td>
	   					<span id="department_field"></span>
	   					<input name="m:house_reimburse:department" class="oa-new-input" type="hidden"  lablename="所属项目部" value="" validate="{empty:false}" readonly="readonly" /> 
	   				</td> 
	  			</tr> 
	  			<tr> 
				   <td>承租人</td> 
				   <td><span id="rent_person_field"></span><input type="hidden" name="m:house_reimburse:rent_person" class="oa-new-input" lablename="承租人" class="inputText" value="" validate="{maxlength:50}" isflag="tableflag" readonly="readonly"/></td>
				   <td>房屋地址</td> 
				   <td><span id="address_field"></span><input type="hidden" name="m:house_reimburse:address" class="oa-new-input" lablename="房屋地址" class="inputText" value="" validate="{maxlength:50}" isflag="tableflag" readonly="readonly"/></td> 
			    </tr> 
			    <tr> 
			      <td>租房开始日期</td> 
			      <td><span id="start_date_field"></span><input type="hidden"  parser="datetable" disabled="disabled" name="m:house_reimburse:start_date" data-class="oa-new-input" class="inputText date" lablename="租房开始日期" datefmt="yyyy-MM-dd" value="<fmt:formatDate value='${houseReimburse.start_date}' pattern='yyyy-MM-dd'/>" validate="{empty:false}" type="text" readonly="readonly"/></td> 
			      <td>租房结束日期</td> 
			      <td><span id="end_date_field"></span><input type="hidden"  parser="datetable" disabled="disabled" name="m:house_reimburse:end_date" lablename="租房结束日期" data-class="oa-new-input" class="inputText date" value="<fmt:formatDate value='${houseReimburse.end_date}' pattern='yyyy-MM-dd'/>" validate="{empty:false}" type="text" readonly="readonly"/></td> 
			    </tr> 
			    <tr style="display: none;"> 
			      <td>报销人</td> 
			      <td><input type="text" id="reimburse_person"  name="m:house_reimburse:reimburse_person" class="oa-new-input" lablename="报销人" class="inputText" value="${houseReimburse.reimburse_person}" validate="{&quot;maxlength&quot;:50,&quot;required&quot;:&quot;true&quot;}" isflag="tableflag" /></td>
			      <td>报销日期</td> 
			      <td><input type="text" id="reimburse_date" name="m:house_reimburse:reimburse_date" data-class="oa-new-input" value="<fmt:formatDate value='${houseReimburse.reimburse_date}' pattern='yyyy-MM-dd'/>" class="inputText date"  /></td>
			    </tr> 
			    <tr> 
			      <td>租房金额</td> 
			      <td><span id="rent_money_field"></span><input type="hidden" name="m:house_reimburse:rent_money" lablename="租房金额" class="oa-new-input" value="${houseReimburse.rent_money}" validate="{maxlength:50}" isflag="tableflag" readonly="readonly"/></td>
			  	  <td>房屋结构</td>
			  	  <td>
			  	  	    <span id="house_type_field"></span>
			  	   		<select class="oa-new-select hidden" value="${houseReimburse.house_type}" name="m:house_reimburse:house_type" >
				   			 <option value='' >请选择</option>
						   	 <option value='1' <c:if test="${houseReimburse.house_type=='1'}">selected</c:if> >1房</option>
						   	 <option value='2' <c:if test="${houseReimburse.house_type=='2'}">selected</c:if> >2房</option>
						   	 <option value='3' <c:if test="${houseReimburse.house_type=='3'}">selected</c:if> >3房</option>
						   	 <option value='4' <c:if test="${houseReimburse.house_type=='4'}">selected</c:if> >4房</option>
						   	 <option value='5' <c:if test="${houseReimburse.house_type=='5'}">selected</c:if> >5房</option>
						   	 <option value='6' <c:if test="${houseReimburse.house_type=='6'}">selected</c:if> >6房</option>
						   	 <option value='7' <c:if test="${houseReimburse.house_type=='7'}">selected</c:if> >7房</option>
						   	 <option value='8' <c:if test="${houseReimburse.house_type=='8'}">selected</c:if> >8房</option>
						   	 <option value='9' <c:if test="${houseReimburse.house_type=='9'}">selected</c:if> >9房</option>
				   	    </select>
				   	</td>
			  </tr> 
			  <tr>
			  	<td>入住人数</td>
			 	<td><input id="inpersons" style="border: 0px;" type="text"  class="oa-new-input" readonly="readonly" /></td>
			 	<td></td>
			 	<td></td>
			  </tr>
			  <tr>
			      <td>费用期间</td> 
			      <td>
		   			 <input type="hidden" id="start_date"  name="m:house_reimburse:pay_start_date" value="<fmt:formatDate value='${houseReimburse.pay_start_date}' pattern='yyyy-MM-dd'/>" data-class="oa-new-input" class="inputText date" validate="{date:true}" />
		        	 <input type="hidden" id="end_date"  name="m:house_reimburse:pay_end_date" value="<fmt:formatDate value='${houseReimburse.pay_end_date}' pattern='yyyy-MM-dd'/>" data-class="oa-new-input" class="inputText date" validate="{date:true}" />
		  			 <span id="feePerdio"><fmt:formatDate value='${houseReimburse.pay_start_date}' pattern='yyyy-MM-dd'/> -- <fmt:formatDate value='${houseReimburse.pay_end_date}' pattern='yyyy-MM-dd'/></span>
			      </td> 
			  	  <td>报销总费用</td>
			  	  <td><input type="text" style="border: 0px;" name="m:house_reimburse:reimburse_money" lablename="租房金额" class="oa-new-input" value="${houseReimburse.reimburse_money}" validate="{maxlength:50}" isflag="tableflag" /></td>
			  </tr>
		  </table>
       </form>
   </div>
   <div class="oa-mg20">
		<div><a class="btn blue oa-button oa-button--primary oa-button--blue" style="margin: 0 11px;" href="javascript:addRecord();">添加报销</a></div>
	  	<form id="" style="margin: 14px 11px;" method="post" action="">
			<table class="oa-table--default" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
				<tbody id="recordFields">
					<tr id="">
						<th></th>
						<th>报销日期</th>
						<th>报销金额(元)</th>
						<th>报销人</th>
					</tr>
				</tbody>
				<tr>
					<td></td>
					<td>合计金额</td>
					<td id="reimburse_money_show">${houseReimburse.reimburse_money}</td>
					<td></td>
				</tr>
			</table>
		</form>
  </div>
  <script type="text/javascript" src="${ctx}/js/makshi/house/houseReimburseAdd.js?t=<%=Math.random()%>"></script>
</body>
</html>
