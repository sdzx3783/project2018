<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>员工分析报表（月度）</title>
	<%@include file="/commons/include/list_get.jsp"%>
	<link href="${ctx}/js/select2/select2.css" rel="stylesheet" />
	<link href="${ctx}/js/select2/select2-metronic.css" rel="stylesheet" />
	<style type="text/css">
	.form-body {margin-bottom: 20px;margin-left: 6px;}
    .form-group{margin-right: 20px;}
    .control-label{width:64px;}
    .form-control{width:180px !important;}
    .control-label label{min-width:23px;}
    table tr th {
	    font-size: 14px;
	    color: #000;    
	    font-weight: 600;
    }
    .boottable-th{
	    font-size: 14px;
	    color: #000;
	    font-weight: 600;
    }
    .executivetitle{
	   	font-size: 20px !important;
	    color: #000;
	    font-weight: 600 !important;
    }
    .oa-table-scroll-wrap {
    	border: solid 1px #fff;;
    }
    .boottable{border: 1px solid #dddddd;}
    .select2-container-multi .select2-choices .select2-search-field input{padding:0px !important;}
    .fixed-table-body{height:100% !important;}
    .th-inner {cursor: pointer !important;}
	</style>
</head>
<body>
	<div id="oa_list_title" class="oa-mgb20 oa-project-title">
	    <!-- <h2 class='executivetitle'>员工分析报表（月度）</h2> -->
	</div>
        <div class="col-md-12">
			<form class="form-inline" role="form" method="post" action="/makshi/gates/executive/peopleMonth.ht" id="toolbootbar">
		         <div class="form-body">
		             <div class="form-group">
		               	<label class="control-label">部<label>&nbsp;</label>门</label>
		               	<select id="orgIds" name='orgIds' class='' multiple="multiple" style="width: 250px !important;">
		               		 <c:forEach items="${orgs }" var="e">
		               		 	<option value="${e.orgId }" <c:if test="${fn:contains(orgIds,e.orgId)}">selected</c:if>>${e.orgName }</option>
		               		 </c:forEach>
						</select>              
		             </div>
		         	<div class="form-group">
		               	<label class="control-label">年<label>&nbsp;</label>份</label>
		               	<select id="year" name="year" class="form-control" style="width: 89px !important;">
		               	    <c:forEach begin="1989" end="${cutYear }" var="e" >
			               		<option <c:if test="${(cutYear-e+1989)==year}">selected</c:if> >${cutYear-e+1989 }</option>
		               	    </c:forEach>
		               	</select>
		            </div>
		         	<div class="form-group">
		               	<label class="control-label">月<label>&nbsp;</label>份</label>
		               	<select id="month" name="month" class="form-control" style="width: 89px !important;">
		               	    <c:forEach begin="1" end="12" var="e" >
			               		<option <c:if test="${e==month}">selected</c:if> >${e }</option>
		               	    </c:forEach>
		               	</select>
		            </div>
		            <a class="oa-button oa-button--primary oa-button--blue" href="javascript:;" id="queryForm">查询</a>
		         </div>
		     </form>
    </div>
	<div class="oa-pdb20 oa-mgh20">
		<div id="oa_list_content11" class="oa-table-scroll-wrap11">	
			<div id="result_field">
				<%-- <table id="tb_common_show" class="table table-hover table-striped JCLRFlex JColResizer ">
					<thead>
						<tr>
							<th>部门</th>
							<th>月平均人数</th>
							<th>月员工离职率</th>
							<th>月员工新进率</th>
							<th>月员工留存率</th>
							<th>月员工损失率</th>
							<th>月员工进出比率</th>
							<th>新晋员工比率</th>
							<th>异动率</th>
							<th>人员流动率</th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${empty list }">
								<tr>
									<td colspan="10" style="text-align: center;">未查询到记录</td>
								</tr>
							</c:when>
							<c:otherwise>
								<c:forEach items="${list }" var="e" varStatus="v">
									<tr>
										<th>
											 ${e.orgName }
										</th>
										<td><fmt:formatNumber value="${e.monthAverageNum}" pattern="##.##"></fmt:formatNumber></td>
										<td><fmt:formatNumber value="${e.turnoverRate}" pattern="##.##%"></fmt:formatNumber></td>
										<td><fmt:formatNumber value="${e.newArrivalRate}" pattern="##.##%"></fmt:formatNumber></td>
										<td><fmt:formatNumber value="${e.retentionRate}" pattern="##.##%"></fmt:formatNumber></td>
										<td><fmt:formatNumber value="${e.lossRate}" pattern="##.##%"></fmt:formatNumber></td>
										<td><fmt:formatNumber value="${e.exportRatio}" pattern="##.##%"></fmt:formatNumber></td>
										<td><fmt:formatNumber value="${e.newEmployeeRatio}" pattern="##.##%"></fmt:formatNumber></td>
										<td><fmt:formatNumber value="${e.turnoverLostRate}" pattern="##.##%"></fmt:formatNumber></td>
										<td><fmt:formatNumber value="${e.staffTurnoverLostRate}" pattern="##.##%"></fmt:formatNumber></td>
									</tr>
								</c:forEach>
									<tr>
										<th>
											 合计
										</th>
										<td>${to.monthAverageNum}</td>
										<td><fmt:formatNumber value="${to.turnoverRate}" pattern="##.##%"></fmt:formatNumber></td>
										<td><fmt:formatNumber value="${to.newArrivalRate}" pattern="##.##%"></fmt:formatNumber></td>
										<td><fmt:formatNumber value="${to.retentionRate}" pattern="##.##%"></fmt:formatNumber></td>
										<td><fmt:formatNumber value="${to.lossRate}" pattern="##.##%"></fmt:formatNumber></td>
										<td><fmt:formatNumber value="${to.exportRatio}" pattern="##.##%"></fmt:formatNumber></td>
										<td><fmt:formatNumber value="${to.newEmployeeRatio}" pattern="##.##%"></fmt:formatNumber></td>
										<td><fmt:formatNumber value="${to.turnoverLostRate}" pattern="##.##%"></fmt:formatNumber></td>
										<td><fmt:formatNumber value="${to.staffTurnoverLostRate}" pattern="##.##%"></fmt:formatNumber></td>
									</tr>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table> --%>
				
				<table id="bootstrapTable" class="oa-table--default oa-table--nowrap" data-reorderable-columns="true">
				</table>
			</div>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
	<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerDrag.js" ></script>
    <script type="text/javascript" src="${ctx}/js/lg/plugins/newligerDialog.js" ></script>
    <script type="text/javascript" src="${ctx}/js/lg/plugins/ligerResizable.js" ></script>
	<script type="text/javascript" src="${ctx }/js/select2/select2.min.js"></script>
	<script type="text/javascript" src="${ctx }/js/makshi/gates/executive/executiveStaffMonth.js" ></script>
</body>
</html>