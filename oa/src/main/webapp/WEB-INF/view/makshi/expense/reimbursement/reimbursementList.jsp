<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
	<title id="executiveTitle">单位汇总表</title>
	<%@include file="/commons/include/list_get.jsp"%>
	<f:link href="Aqua/css/ligerui-all.css" ></f:link>
    <f:link href="tree/zTreeStyle.css" ></f:link>
	<link href="${ctx}/js/pagination/pagination.css" rel="stylesheet" />
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
	<style type="text/css">
		.form-body {
    margin-bottom: 20px;
    margin-left: 6px;}
    .form-group{margin-right: 20px;}
    .control-label{width:64px;}
    .form-control{width:180px !important;}
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
    .center{text-align: center !important;}
	</style>
</head>
<body>
	<div id="oa_list_title" class="oa-mgb20 oa-project-title">
	    	<h2 class='executivetitle'>部门费用汇总表</h2>
	</div>
	<div  >
        <div class="col-md-12">
			<form class="form-inline" role="form" method="post" action="/makshi/expense/reimbursement/list.ht" id="search-form">
		         <div class="form-body">
		         	<div class="form-group">
		               	<label class="control-label">日期</label>
		               	<input onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false,maxDate:'#F{$dp.$D(\'entrydateEnd\')}'})" type="text" readonly="readonly" name="start" id="entrydateStart" value="${start}" class="form-control Wdate" />
		               		至
		               	<input onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false,maxDate:'%y-%M'})" type="text" name="end" id="entrydateEnd" readonly="readonly" value="${end}" class="form-control Wdate" />
		            </div>
		            <!--  href="javascript:loadInitPageData();" -->
		            <button class="oa-button oa-button--primary oa-button--blue">查询</button>
		            <a  href="javascript:;" class="oa-button oa-button--primary oa-button--blue ${empty orgs?'hidden':'' } " id="btnExport">导出</a>
		         </div>
		     </form>
		 </div>
    </div>
	<div class="oa-pdb20 oa-mgh20">
		<div id="oa_list_content" class="oa-table-scroll-wrap">	
			<div id="result_field">
				<table id="tb_common_show" class="oa-table--default oa-table--nowrap">
					<%-- <tr>
						<th class="center" colspan="${fn:length(dates) +3}">部门费用汇总表</th>
					</tr> --%>
					<c:choose>
						<c:when test="${empty orgs }">
							<tr>
								<th class="center">未查询到记录</th>
							</tr>
						</c:when>
						<c:otherwise>
							<c:set var="ttotal" value="0"></c:set>
							<c:forEach items="${orgs }" var="o">
								<tr>
									<th class="center" colspan="2">费用类别</th>
									<th class="center" colspan="${fn:length(dates) +1}">${o.orgName }</th>
								</tr>
								<tr>
									<th class="center">编号</th>
									<th class="center">名称</th>
									<c:forEach items="${dates }" var="d" varStatus="i">
										<th class="center">${fn:replace(d,'-','年') }月</th>
									</c:forEach>
									<th class="center">合计</th>
								</tr>
								<c:forEach items="${typesmaps[o.orgId] }" var="t">
									<tr>
										<c:set var="rtotal" value="0"></c:set>
										<td class="center">${t.typesId }</td>
										<td class="center">${t.typeName }</td>
										<c:forEach items="${dates }" var="d">
											<c:set var="v">${o.orgId}${t.typeName}${d}</c:set>
											<td class="center"><fmt:formatNumber value="${empty valuesmaps[v]?0: valuesmaps[v]}" pattern="##.##"></fmt:formatNumber></td>
											<c:set var="rtotal" value="${rtotal+ (empty valuesmaps[v]?0: valuesmaps[v])}"/>
										</c:forEach>
										<td class="center">
											<fmt:formatNumber value="${rtotal }" pattern="##.##"></fmt:formatNumber>
											<c:set var="ttotal" value="${ttotal+ rtotal}"/>
										</td>
									</tr>
								</c:forEach>
							</c:forEach>
							<tr>
								<th class="center"></th>
								<th class="center">合计</th>
								<c:forEach items="${dates }" var="d" varStatus="i">
									<th class="center"><fmt:formatNumber value="${empty dateTotalValuesmaps[d]?0: dateTotalValuesmaps[d]}" pattern="##.##"></fmt:formatNumber> </th>
								</c:forEach>
								<th class="center"><fmt:formatNumber value="${ttotal }" pattern="##.##"></fmt:formatNumber></th>
							</tr>
						</c:otherwise>
					</c:choose>
				</table>
			</div>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
	<script type="text/javascript">
		$("#executiveTitle").text($(".executivetitle").text());
		$(function(){
			$("#btnExport").click(function(){
		        $('#tb_common_show').tableExport({
			        type:'excel',
			        escape:'false',
			        htmlContent :true,
			        fileName: $(".executivetitle").text()+('('+$("#entrydateStart").val()+'—'+$("#entrydateEnd").val()+')')
		        });
		    });
		})
	</script>
</body>
</html>