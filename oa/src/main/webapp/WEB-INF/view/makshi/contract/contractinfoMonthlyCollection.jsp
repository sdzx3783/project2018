<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
	<%@include file="/commons/include/list_get.jsp"%>
	<f:link href="Aqua/css/ligerui-all.css" ></f:link>
    <f:link href="tree/zTreeStyle.css" ></f:link>
    <script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
    
     <script type="text/javascript" src="${ctx}/js/lg/newligerui.all.js" ></script>
     <script type="text/javascript" src="${ctx}/js/tree/jquery.ztree.js"></script>
    <script type="text/javascript" src="${ctx}/js/lg/plugins/ligerComboBox.js"></script>
    <script type="text/javascript" src="${ctx}/js/lg/plugins/htDicCombo.js"></script> 
    
    <script type="text/javascript" src="${ctx}/js/jquery/jquery.form.js"></script>
    <script type="text/javascript" src="${ctx}/js/lg/plugins/ligerDrag.js" ></script>
    <script type="text/javascript" src="${ctx}/js/lg/plugins/newligerDialog.js" ></script>
    <script type="text/javascript" src="${ctx}/js/lg/plugins/ligerResizable.js" ></script>
    <%@include file="/commons/include/ueditor.jsp" %>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
    <script type="text/javascript" src="${ctx}/js/lg/util/DialogUtil.js" ></script>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/form/SelectorInit.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/ajaxgrid.js"></script>
    <script type="text/javascript" src="${ctx}/js/lg/util/DialogUtil.js" ></script>
	<%@ taglib prefix="hotent" uri="http://www.jee-soft.cn/paging" %>
	<link href="${ctx}/js/select2/select2.css" rel="stylesheet" />
	<link href="${ctx}/js/select2/select2-metronic.css" rel="stylesheet" />
	<style type="text/css">
		.form-body {
    margin-bottom: 20px;
    margin-left: 6px;}
    .form-group{margin-right: 20px;}
    .control-label{width:64px;font-size:15px;}
    .age{}
    .form-control{width:200px !important;}
    .control-label label{min-width:23px;}
    table tr th {
    font-size: 14px;
    color: #000;    
    font-weight: 600;
    }
    .executivetitle{
   	font-size: 20px !important;
    color: #000;
    font-weight: 600 !important;
    }
	</style>
</head>
<body>
	<div id="oa_list_title" class="oa-mgb10 oa-project-title">
	    <h2 class='executivetitle'>各部门月收款进度表</h2>
	</div>
	
	<div class="col-md-12" style="padding: 20px">
		<div class="oa-fl oa-mgb10">
			<select id="tabStatics" name="tabStatics" class="form-control" style="display: inline-block;"  >
				<option value="1" selected="selected" data-url="/makshi/contract/contractinfo/singingRate.ht">报表统计</option>
				<option value="2" data-url="/makshi/contract/contractinfo/customers.ht">深水咨询近年最大客户</option>
				<option value="3" data-url="/makshi/contract/contractinfo/supplier.ht">深水主要供应商</option>
			</select>
	       <button class="oa-button oa-button--primary oa-button--blue" onclick='window.parent.frames.addToTab("/makshi/contract/contractinfo/singingRate.ht","合同签订归档统计表","10000011850001","/images/transparent.png");'>合同签订归档统计表</button>
	       <button class="oa-button oa-button--primary oa-button--blue active" onclick='window.parent.frames.addToTab("/makshi/contract/contractinfo/monthlyCollection.ht","各部门月收款进度表","10000011850002","/images/transparent.png");'>各部门月收款进度表</button>
	       <button class="oa-button oa-button--primary oa-button--blue" onclick='window.parent.frames.addToTab("/makshi/contract/contractinfo/output.ht","合同产值统计","20000001750803","/images/transparent.png");'>合同产值统计</button>
		</div>
    </div>
            
	<div>
        <div class="col-md-12">
			<form class="form-inline" role="form" method="post" action="/makshi/contract/contractinfo/monthlyCollection.ht" id="search-form">
		         <div class="form-body">
		         	<div class="form-group">
		               	<label class="control-label">年<label>&nbsp;</label>份</label>
		               	<select id="year" name="year" class="form-control" style="width: 89px !important;">
		               	    <c:forEach begin="1989" end="${cutYear }" var="e" >
			               		<option <c:if test="${(cutYear-e+1989)==year}">selected</c:if> >${cutYear-e+1989 }</option>
		               	    </c:forEach>
		               	</select>
		            </div>
		            <button class="oa-button oa-button--primary oa-button--blue">查询</button>
		         </div>
		     </form>
		 </div>
    </div>
    <div class="oa-pdb20 oa-mgh20">
		<div id="oa_list_content" class="oa-table-scroll-wrap">	
			<div id="result_field">
		       <table id="tb_common_show" class="table table-hover table-striped JCLRFlex JColResizer1">
				     <thead>
						<tr>
							<th>&nbsp;&nbsp;合同所在部门</th>
							<th>1月收款金额（万元）</th>
							<th>2月收款金额（万元）</th>
							<th>3月收款金额（万元）</th>
							<th>4月收款金额（万元）</th>
							<th>5月收款金额（万元）</th>
							<th>6月收款金额（万元）</th>
							<th>7月收款金额（万元）</th>
							<th>8月收款金额（万元）</th>
							<th>9月收款金额（万元）</th>
							<th>10月收款金额（万元）</th>
							<th>11月收款金额（万元）</th>
							<th>12月收款金额（万元）</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${monthlyCollectionList }" var="monthlyCollectionItem">
							<tr>
								<c:set var="january" value="${january +  monthlyCollectionItem.january}"/>
								<c:set var="february" value="${february +  monthlyCollectionItem.february}"/>
								<c:set var="march" value="${march +  monthlyCollectionItem.march}"/>
								<c:set var="april" value="${april +  monthlyCollectionItem.april}"/>
								<c:set var="may" value="${may +  monthlyCollectionItem.may}"/>
								<c:set var="june" value="${june +  monthlyCollectionItem.june}"/>
								<c:set var="july" value="${july +  monthlyCollectionItem.july}"/>
								<c:set var="august" value="${august +  monthlyCollectionItem.august}"/>
								<c:set var="september" value="${september +  monthlyCollectionItem.september}"/>
								<c:set var="october" value="${october +  monthlyCollectionItem.october}"/>
								<c:set var="november" value="${november +  monthlyCollectionItem.november}"/>
								<c:set var="december" value="${december +  monthlyCollectionItem.december}"/>
								
								<th>&nbsp;&nbsp;${monthlyCollectionItem.department}</th>
								<td><fmt:formatNumber value="${monthlyCollectionItem.january}" pattern="#.####"></fmt:formatNumber></td>
								<td><fmt:formatNumber value="${monthlyCollectionItem.february}" pattern="#.####"></fmt:formatNumber></td>
								<td><fmt:formatNumber value="${monthlyCollectionItem.march}" pattern="#.####"></fmt:formatNumber></td>
								<td><fmt:formatNumber value="${monthlyCollectionItem.april}" pattern="#.####"></fmt:formatNumber></td>
								<td><fmt:formatNumber value="${monthlyCollectionItem.may}" pattern="#.####"></fmt:formatNumber></td>
								<td><fmt:formatNumber value="${monthlyCollectionItem.june}" pattern="#.####"></fmt:formatNumber></td>
								<td><fmt:formatNumber value="${monthlyCollectionItem.july}" pattern="#.####"></fmt:formatNumber></td>
								<td><fmt:formatNumber value="${monthlyCollectionItem.august}" pattern="#.####"></fmt:formatNumber></td>
								<td><fmt:formatNumber value="${monthlyCollectionItem.september}" pattern="#.####"></fmt:formatNumber></td>
								<td><fmt:formatNumber value="${monthlyCollectionItem.october}" pattern="#.####"></fmt:formatNumber></td>
								<td><fmt:formatNumber value="${monthlyCollectionItem.november}" pattern="#.####"></fmt:formatNumber></td>
								<td><fmt:formatNumber value="${monthlyCollectionItem.december}" pattern="#.####"></fmt:formatNumber></td>
							</tr>
						</c:forEach>
						<tr>
							<th>&nbsp;&nbsp;合&nbsp;&nbsp;&nbsp;&nbsp;计</th>
							<td><fmt:formatNumber value="${january}" pattern="#.####"></fmt:formatNumber></td>
							<td><fmt:formatNumber value="${february}" pattern="#.####"></fmt:formatNumber></td>
							<td><fmt:formatNumber value="${march}" pattern="#.####"></fmt:formatNumber></td>
							<td><fmt:formatNumber value="${april}" pattern="#.####"></fmt:formatNumber></td>
							<td><fmt:formatNumber value="${may}" pattern="#.####"></fmt:formatNumber></td>
							<td><fmt:formatNumber value="${june}" pattern="#.####"></fmt:formatNumber></td>
							<td><fmt:formatNumber value="${july}" pattern="#.####"></fmt:formatNumber></td>
							<td><fmt:formatNumber value="${august}" pattern="#.####"></fmt:formatNumber></td>
							<td><fmt:formatNumber value="${september}" pattern="#.####"></fmt:formatNumber></td>
							<td><fmt:formatNumber value="${october}" pattern="#.####"></fmt:formatNumber></td>
							<td><fmt:formatNumber value="${november}" pattern="#.####"></fmt:formatNumber></td>
							<td><fmt:formatNumber value="${december}" pattern="#.####"></fmt:formatNumber></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	 $(function(){
		 $("#tabStatics").change(function(){
			 var url = $("#tabStatics option:selected").data("url");
			 location.href = url;
		 });
	 });
	</script>			
</body>
</html>


