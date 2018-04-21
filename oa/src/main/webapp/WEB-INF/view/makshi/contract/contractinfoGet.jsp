
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>合同基本信息明细</title>
<%@include file="/codegen/include/customForm.jsp" %>
<%@include file="/commons/include/ueditor.jsp" %>
<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerTab.js" ></script>
<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/AttachMent.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/TablePermission.js"></script>
<style>
	body {
		padding: 20px;
		height: auto;
	}
	.oa-table--second td,
    .oa-table--second th{
        padding: 10px 20px;
        border: 1px solid #eceff8;
    }
    .oa-table--second th{
        font-weight: bold;
        width: 100px;
        background: #f6f7fb;
    }
    .tab-content h2 {
    	text-align: center;
    	margin: 10px 0 15px;
    	font-size: 16px;
    }
    .tab-content.active {
    	display: block;
    }
    .nav-pills > li {
	  	float: left;
	}
	.nav-pills > li > a {
	 	 border-radius: 4px;
	}
	.nav-pills > li + li {
	 	 margin-left: 2px;
	}
	.nav-pills > li.active > a,
	.nav-pills > li.active > a:hover,
	.nav-pills > li.active > a:focus {
	 	 color: #fff;
	 	 background-color: #337ab7;
	}
	.nav {
	    padding-left: 0;
	    margin: 0 0 10px;
	    list-style: none;
	    overflow: hidden;
	}
	.nav > li {
	    position: relative;
	  	display: block;
	}
	.nav > li > a {
	 	 position: relative;
	 	 display: block;
	 	 padding: 10px 15px;
	 	 color: #337ab7;
    	 text-decoration: none;
	}
	.nav > li > a:hover,
	.nav > li > a:focus {
	 	 text-decoration: none;
	 	 background-color: #eee;
	}
	.nav > li.disabled > a {
	 	 color: #777;
	}
	.nav > li.disabled > a:hover,
	.nav > li.disabled > a:focus {
		 color: #777;
	 	 text-decoration: none;
	  	 cursor: not-allowed;
	  	 background-color: transparent;
	}
	#tabMyInfo .l-tab-links {
            border: 1px solid #eceff8;
        background: #eceff8;
    }
    .l-tab-links li {
        cursor: pointer;
        float: left;
        font-size: 14px;
        height: 42px;
        line-height: 42px;
        margin: 0;
        overflow: hidden;
        position: relative;
    }
    #tabMyInfo .l-tab-links li.l-selected a {
        display: block;
        padding: 0 10px;
        border-radius: 0;
    }
    #tabMyInfo .l-tab-links li a {
        color: #657386;
        padding: 0 6px;
        font-size: 13px;
    }
    #tabMyInfo {
		margin: 0;
	}
</style>
<script>
	$(function() {
	    $("#tabMyInfo").ligerTab({});
	});
</script>
</head>
<body class="oa-mw-page">
	<%-- <div class="oa-mg20">
		<c:choose>
			<c:when test="${not empty param.projectId }">
				<a class="oa-button oa-button--primary oa-button--blue" href="/makshi/project/project/projectDetail.ht?id=${param.projectId }">返回</a>
			</c:when>
			<c:otherwise>
				<a class="oa-button oa-button--primary oa-button--blue" href="list.ht">返回</a>
			</c:otherwise>
		</c:choose>
	</div> --%>
	<div id="tabMyInfo">
		<div title="基本资料" tabid="basicinformation" icon="${ctx}/styles/default/images/resicon/user.gif">
			<div class="tab-content">
		 		<h2>合同基本信息</h2>
		 		<table class="oa-table--default oa-table--second" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
		  			<tr>  
		   				<th>合同类型 </th> 
		   				<td>
		    				<c:choose>
		    					<c:when test="${contractinfo.contracttype == '补充协议'}">
		    						${contractinfo.contracttype}-${contractinfo.type}${contractinfo.thirdcontracttype}
		    					</c:when>
		    					<c:when test="${contractinfo.contracttype == '监理合同'}">
		    						${contractinfo.contracttype}${contractinfo.type}
		    					</c:when>
		    					<c:otherwise>
		    						${contractinfo.contracttype}
		    					</c:otherwise>
		    				</c:choose>
		  				</td> 
				   		<th>合同状态</th> 
				   		<td>
				   		 	<c:if test="${contractinfo.contract_status==1}">作废</c:if>
					     	<c:if test="${contractinfo.contract_status==0}">正式合同</c:if>
					    </td>
					   	<th></th> 
					    <td></td> 
				     </tr> 
					 <tr>
					     <th>合同编号</th> 
						 <td>${contractinfo.contract_num}</td>
						 <th>合同名称</th> 
						 <td>${contractinfo.contract_name}</td> 
						 <th>项目状态</th> 
						 <td> ${contractinfo.project_status}</td> 
					  </tr> 
					  <tr>  
					  	<th>甲方</th> 
						<td>${contractinfo.first_party}</td>
						<th>乙方</th> 
						<td>${contractinfo.second_party}</td> 
						<th></th>
						<td></td>
					  </tr> 				  
				  	<tr> 
				   <th>委托方式</th> 
				   <td> 
				   		<c:if test="${empty contractinfo.bidding_method}"></c:if>
				   		<c:if test="${!empty contractinfo.bidding_method}">
					   		<c:if test="${contractinfo.bidding_method==0}">公开招标</c:if>
						    <c:if test="${contractinfo.bidding_method==1}">邀请招标</c:if>
						    <c:if test="${contractinfo.bidding_method==2}">直接委托</c:if>
						    <c:if test="${contractinfo.bidding_method==3}">抽签</c:if>
				   		 </c:if>
					  </td>
				   <th>招标平台</th> 
				   <td>
				   		<c:if test="${empty contractinfo.bidding_platform}"></c:if>
				   		<c:if test="${!empty contractinfo.bidding_platform}">
						    <c:if test="${contractinfo.bidding_platform==0}">深圳市建设工程交易服务中心</c:if>
						    <c:if test="${contractinfo.bidding_platform==1}">政府采购中心</c:if>
						    <c:if test="${contractinfo.bidding_platform==2}">无</c:if>
						    <c:if test="${contractinfo.bidding_platform==3}">其他</c:if>
					    </c:if>
				   </td>
				   <th>签约时间</th> 
				   <td><fmt:formatDate value='${contractinfo.singing_time}' pattern='yyyy-MM-dd'/></td> 
				  </tr> 
				  <tr>  
					  <th>总投资(万元)</th> 
					  <td>${contractinfo.total_investment}</td> 
					  <th>合同金额（万元）</th> 
					  <td>${contractinfo.contract_money}</td> 
					  <th>开工时间</th> 
				   	  <td><fmt:formatDate value='${contractinfo.start_time}' pattern='yyyy-MM-dd'/></td>	
				  </tr>    
				  <tr>
				   <th>工程标底价（万元）</th> 
				   <td>${contractinfo.project_bid_floorprice}</td>  
				   <th>工程中标价（万元）</th> 
				   <td>${contractinfo.project_bid_price}</td>    
				   <th>完工时间</th> 
				   <td><fmt:formatDate value='${contractinfo.end_time}' pattern='yyyy-MM-dd'/></td>	
				  </tr>
				  <tr>  
				   <th>结算金额（万元）</th> 
				   <td>${contractinfo.settlement_money}</td>  
				   <th>系统内外</th> 
				   <td>
				   		<c:if test="${contractinfo.inout==1}">内</c:if>
					    <c:if test="${contractinfo.inout==0}">外</c:if>
				   </td> 
				   <th>费率(%)</th> 
				   <td>${contractinfo.rate} </span> </td> 
				  </tr>
				  <tr> 
				   <th>合同归属部门</th> 
				   <td>${contractinfo.contract_department}</td> 
				   <th>项目部</th>
				   <td>${contractinfo.project_department}</td>
				   <th>合同经手人</th> 
				   <td>${contractinfo.contract_handler}</td> 	   
				   </tr> 
				  <tr>
				   <th>项目总监/项目经理</th> 
				   <td>${contractinfo.project_director}</td> 
				   <th>项目负责人</th> 
				   <td>${contractinfo.project_leader}</td> 
				   <th>合同审核人</th> 
				   <td>${contractinfo.contract_reviewer}</td>	   
				  </tr> 
				  <tr>  	   
				   <th>项目内容</th> 
				   <td rowspan="1" colspan="5">${contractinfo.project_content}</td>    
				  </tr> 
				  <tr>   
				   <th>项目规模</th> 
				   <td  rowspan="1" colspan="5">${contractinfo.project_scale}</td>   
				  <tr>  
				   <th>工程地址</th> 
				   <td  rowspan="1" colspan="5">${contractinfo.project_place}</td>  
				  </tr>
				  <tr>
				   <th>合同批签人</th> 
				   <td>${contractinfo.contract_authorized_person}</td>   
				   <th>归档时间</th>
				   <td><fmt:formatDate value='${contractinfo.instock_date}' pattern='yyyy-MM-dd'/></td>	   
				   <th>申请时间</th>
				   <td ><fmt:formatDate value='${contractinfo.create_time}' pattern='yyyy-MM-dd'/></td>
				   </tr>
				   <tr>
				   <th>进度</th> 
				   <td> ${contractinfo.progress} </td> 	   	   
				   <th>修改时间</th>
				   <td ><fmt:formatDate value='${contractinfo.update_time}' pattern='yyyy-MM-dd'/></td>
				   <th>修改人</th>
				   <td >${contractinfo.updater} </td>  
				  </tr> 
				  <tr>
				  	<th>项目组成员</th>
				  	<td><c:forEach items="${pros }" var="pro">${pro.member },</c:forEach></td>
			  		<th>是否有项目章</th>
				  	<td>
				  		<c:if test="${empty contractinfo.project_chapter}"></c:if>
				   		<c:if test="${!empty contractinfo.project_chapter}">
						    <c:if test="${contractinfo.project_chapter==0}">无</c:if>
						    <c:if test="${contractinfo.project_chapter==1}">有</c:if>
					    </c:if>
				  	</td>
				  </tr>
				</table>
		 	</div>
		</div>
		<div title="文件传递" tabid="filetransfer" icon="${ctx}/styles/default/images/resicon/user.gif">
			<div class="tab-content">
		 		<h2>文件传递</h2>
		 		<table class="oa-table--default oa-table--second" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
				  <tr> 
				   <th>合同是否归档</th> 
				   <td>
				   		<c:if test="${contractinfo.isrecovery==1}">是</c:if>
					    <c:if test="${contractinfo.isrecovery==0}">否</c:if>
				   </td> 
				   <th>签收人</th>
				   <td>${contractinfo.instock_sign}</td>
				   <th>份数</th>
				   <td>${contractinfo.fj_sencond_copies}</td>
				</tr>
				<tr> 
				   <th>部门保管人</th> 
				   <td>${contractinfo.custodian}</td>
			       <th></th>
				   <td></td>
				   <th>份数</th>
				   <td>${contractinfo.fj_sencond_copies}</td>
			   </tr>  
				<tr> 
				   <th>合同是否备案</th> 
				   <td>
				   		<c:if test="${contractinfo.isrecord==1}">是</c:if>
					    <c:if test="${contractinfo.isrecord==0}">否</c:if>
				   </td>
				    <th>签收人</th>
				   <td>${contractinfo.sure_sign}</td>
				   <th>份数</th>
				   <td>${contractinfo.fj_sencond_copies}</td>
			   </tr> 
				<tr> 
				   <th>竣工子资料是否存档</th> 
				   <td colspan="5">
				   		<c:if test="${contractinfo.issave==1}">是</c:if>
					    <c:if test="${contractinfo.issave==0}">否</c:if>
				   </td>
				  </tr> 
				</table>
		 	</div>
		</div>
		<div title="详细资料和附件" tabid="detailinformation" icon="${ctx}/styles/default/images/resicon/user.gif">
			<div class="tab-content">
		 		<h2>合同详细资料和附件</h2>
		 		<table class="oa-table--default oa-table--second" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
				  <tr> 
				   <th>附件1：合同电子档</th> 
				   <td colspan="5">
				   	<input  ctltype="attachment" right="r"  name="m:contractinfo:file" isdirectupload="0"  value='${contractinfo.file}' validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}" />
				   </td> 
				</tr> 
				<tr> 
				   <th>附件2：合同扫描件</th> 
				   <td colspan="5">
				   	<input  ctltype="attachment" right="r"  name="m:contractinfo:file_second" isdirectupload="0"  value='${contractinfo.file_second}' validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}" />
				   </td>
				   </tr> 
				<tr> 
				   <th>附件3：备案回执、中标通知书等其他资料</th> 
				   <td colspan="5">
				   	<input  ctltype="attachment" right="r"  name="m:contractinfo:file_third" isdirectupload="0"  value='${contractinfo.file_third}' validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}" />
				   </td>
				  </tr> 
				</table>
		 	</div>
		</div>
		<div title="收款情况" tabid="receivables" icon="${ctx}/styles/default/images/resicon/user.gif">
			<div class="tab-content">
			 	<c:if test="${contractinfo.contract_type==0}">
			 		<h2>收款情况</h2>
			 		<table class="oa-table--default oa-table--second" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
			 		   <tr> 
						   <th>开票金额（万元）</th> 
						   <td id="invoice_amount_total">${contractinfo.invoice_amount}</td>  
						   <th>开票时间</th> 
						   <td><fmt:formatDate value='${contractinfo.billing_time}' pattern='yyyy-MM-dd'/></td> 
						   <th>到账金额（万元）</th> 
						   <td>${contractinfo.arrival_amount}</td>  
					   </tr> 
					 
					   <tr> 
						   <th>到账时间</th> 
						   <td><fmt:formatDate value='${contractinfo.arrival_time}' pattern='yyyy-MM-dd'/></td> 
						   <th>备注</th> 
						   <td >${contractinfo.remark}</td>  
						   <th></th> 
						   <td>
						   </td> 
					   </tr>
					</table>
					<p style="margin-top: 20px; margin-bottom: 5px;"><span style="font-size: 18px;">合同开票记录</p> 
					<div type="subtable" tablename="contract_billing_record" tabledesc="合同开票记录" show="true" parser="rowmodeedit" id="contract_billing_record" formtype="edit"> 
					 	<table class="listTable" border="0" cellpadding="2" cellspacing="0"> 
					  		<tbody> 
							   <tr> 
							    	<td colspan="8"></td> 
							   </tr> 
							   <tr> 
								    <th>序号</th> 
								    <th>开票时间</th> 
								    <th>发票金额（元）</th> 
								    <th>经办人</th> 
								    <th>审批状态</th> 
								    <th>领票人</th> 
								    <th>到账时间</th> 
								    <th>到账金额（元）</th> 
							   </tr> 
					   		   <c:set value="1" var="linkidConunt"/>
					   		   <c:set value="1" var="linkid"/>
				   	   		   <c:forEach items="${contractinfo.contractBillingRecordList}" var="contractBillingRecord" varStatus="status">
						   	   	  <%-- <c:set value="${contractBillingRecord.linkId }" var="linkid"/> --%>
							      <c:set value="${linkMap[(empty contractBillingRecord.linkId?0:contractBillingRecord.linkId)] }" var="linkidConunt"/>
						   	   	  <tr class="listRow" type="subdata"> 
										 <c:if test="${linkid!=contractBillingRecord.linkId }">    
										     <td rowspan="${linkidConunt }" class="tdNo">${status.index+1}</td> 
										     <td rowspan="${linkidConunt }"><fmt:formatDate value='${contractBillingRecord.billing_date}' pattern='yyyy-MM-dd'/></td> 
										     <td rowspan="${linkidConunt }" class="each_row_invoice_money">${contractBillingRecord.invoice_money}</td> 
										     <td rowspan="${linkidConunt }"> ${contractBillingRecord.operator} </td> 
										     <td rowspan="${linkidConunt }">${contractBillingRecord.status} </span> </td> 
										     <td rowspan="${linkidConunt }"> ${contractBillingRecord.bearer} </td> 
									     </c:if>
									     <td><fmt:formatDate value='${contractBillingRecord.payment_date}' pattern='yyyy-MM-dd'/></td> 
									     <td>${contractBillingRecord.arrival_money}</td> 
								      	 <c:set value="${contractBillingRecord.linkId }" var="linkid"/>
							      </tr>
							   </c:forEach>
					  		</tbody> 
					 	</table> 
					</div>
				</c:if>
				<c:if test="${contractinfo.contract_type==1}">
					<p style="margin-top: 20px; margin-bottom: 5px;"><span style="font-size: 18px;">合同付款记录</p> 
					<div type="subtable" tablename="contract_payment_record" tabledesc="合同付款记录" show="true" parser="rowmodeedit" id="contract_payment_record" formtype="edit"> 
						   <table class="listTable" border="0" cellpadding="2" cellspacing="0"> 
						    	<tbody> 
								     <tr> 
								      	  <td colspan="7"></td> 
								     </tr> 
							     	<tr class="headRow"> 
									      <th>序号</th> 
									      <th>申请付款时间</th> 
									      <th>申请付款金额（万元）</th> 
									      <th>经办人</th> 
									      <th>审批状态</th> 
									      <th>付款时间</th> 
									      <th>实际支付金额（万元）</th> 
								     </tr> 
							     	 <c:forEach items="${contractinfo.contractPaymentRecordList}" var="contractPaymentRecord" varStatus="status">
									      <tr class="listRow" type="subdata"> 
										       <td>${status.index+1}</td> 
										       <td><fmt:formatDate value='${contractPaymentRecord.apply_payment_date}' pattern='yyyy-MM-dd'/></td> 
										       <td>${contractPaymentRecord.apply_payment_money}</td> 
										       <td> ${contractPaymentRecord.operator} </td> 
										       <td>${contractPaymentRecord.status}</td> 
										       <td><fmt:formatDate value='${contractPaymentRecord.payment_time}' pattern='yyyy-MM-dd'/></td> 
										       <td>${contractPaymentRecord.actual_amount_paid}</td> 
									      </tr>
								     </c:forEach> 
							    </tbody> 
					   		</table> 
				  	 </div>
				</c:if>     
			</div>
		</div>
		<div title="变更记录" tabid="changerecord" icon="${ctx}/styles/default/images/resicon/user.gif">
			<div class="tab-content">
		 		<h2>变更记录</h2>
		 		 <div type="subtable" tablename="contractChangeApplicationList"  show="true" parser="rowmodeedit" id="contractChangeApplication" formtype="edit"> 
				   <table class="listTable" border="0" cellpadding="2" cellspacing="0"> 
				    <tbody> 
				     <tr> 
				      <td colspan="8"></td> 
				     </tr> 
				     <tr class="headRow"> 
				      <th>序号</th> 
				      <th>工单号</th> 
				      <th>申请人</th> 
				      <th>申请时间</th> 
				      <th>合同名称</th> 
				      <th>合同金额</th> 
				      <th>变更内容</th> 
				      <th>变更时间</th> 
				      <th>变更原因</th> 
				     </tr> 
				     <c:forEach items="${contractChangeApplicationList}" var="contractChangeApplication" varStatus="status">
				      <tr class="listRow" type="subdata"> 
				       <td>${status.index+1}</td> 
				       <td>${contractChangeApplication.globalFlowNo}</td>
				       <td>${contractChangeApplication.applicant}</td> 
				       <td><fmt:formatDate value='${contractChangeApplication.createTime}' pattern='yyyy-MM-dd'/></td> 
				       <td>${contractChangeApplication.contract_name}</td> 
				       <td> ${contractChangeApplication.contract_name} </td> 
				       <td>
			       		<c:if test="${contractChangeApplication.change_content=='0'}"></c:if>
				       	<c:if test="${contractChangeApplication.change_content=='1'}">合同金额</c:if>
						<c:if test="${contractChangeApplication.change_content=='2'}">总投资</c:if>
						<c:if test="${contractChangeApplication.change_content=='3'}">合同经手人</c:if>
						<c:if test="${contractChangeApplication.change_content=='4'}">项目总监</c:if>
						<c:if test="${contractChangeApplication.change_content=='5'}">项目负责人</c:if>
						<c:if test="${contractChangeApplication.change_content=='6'}">项目归属部门</c:if>
						<c:if test="${contractChangeApplication.change_content=='7'}">甲方</c:if>
						<c:if test="${contractChangeApplication.change_content=='8'}">工程地址</c:if>
						<c:if test="${contractChangeApplication.change_content=='9'}">开完工时间</c:if>
						<c:if test="${contractChangeApplication.change_content=='10'}">其他</c:if>
						</td>
				       <td><fmt:formatDate value='${contractChangeApplication.change_time}' pattern='yyyy-MM-dd'/></td> 
				       <td>${contractChangeApplication.application_reason}</td> 
				      </tr>
				     </c:forEach> 
				    </tbody> 
			   </table> 
			  </div>
		 	</div>
		</div>
	</div>
	 <script type="text/javascript">
		 //放置脚本
/* 		 $("li[role='presentation']").on("click", function() {
			 if(! $(this).hasClass("active")) {
				 
			 }
			 var index = $(this).index();
			 $("li[role='presentation']").removeClass("active");
			 $(".tab-content").removeClass("active");
			 $(this).addClass("active");
			 $(".tab-content:eq("+ index +")").addClass("active");
		 }); */
		 var invoice_amount_total = 0;
		 $(".each_row_invoice_money").each(function(i,item){
			 invoice_amount_total += parseFloat($(this).text()) ;
		 });
		 $("#invoice_amount_total").text((parseFloat(invoice_amount_total) * 0.0001).toFixed(4));
	 </script>
</body>
</html>

