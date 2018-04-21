<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>租房统计表</title>
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
    .control-label{width:64px;}
    .age{}
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
	</style>
</head>
<body>
	<div id="oa_list_title" class="oa-mgb20 oa-project-title">
	    <h2 class='executivetitle'>租房统计表</h2>
	</div>
	<div  >
        <div class="col-md-12">
			<form class="form-inline" role="form" method="post" action="/makshi/gates/executive/getRentHouseStastics.ht" id="search-form">
		         <div class="form-body">
		             <%-- <div class="form-group">
		               	<label class="control-label">部<label>&nbsp;</label>门</label>
		               	<select id="orgIds" name='orgIds' class='' multiple="multiple" style="width: 250px !important;">
		               		 <c:forEach items="${orgs }" var="e">
		               		 	<option value="${e.orgId }" <c:if test="${fn:contains(orgIds,e.orgId)}">selected</c:if>>${e.orgName }</option>
		               		 </c:forEach>
						</select>              
		             </div> --%>
		         	<div class="form-group">
		               	<label class="control-label">时间段</label>
		               	<input class="form-control Wdate" id="start" name="start" value="${start }" onclick="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM',maxDate:'#F{$dp.$D(\'end\')}'})">
		               	到
		               	<input class="form-control Wdate" id="end" name="end"  value="${end }" onclick="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM',minDate:'#F{$dp.$D(\'start\')}',maxDate:'%y-%M'})">
		            </div>
		         	 
		            <button class="oa-button oa-button--primary oa-button--blue">查询</button>
		         </div>
		     </form>
		 </div>
    </div>
	<div class="oa-pdb20 oa-mgh20">
		<div id="oa_list_content" class="oa-table-scroll-wrap">	
			<div id="result_field">
				<table id="tb_common_show" class="table table-hover table-striped JCLRFlex JColResizer">
					<thead>
						<tr>
							<th>部门</th>
							<th>租房数（套）</th>
							<th>房间数</th>
							<th>月租总额（元）</th>
							<th>入住人数</th>
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
										<td>
											<%-- <c:if test="${e.houseNums>0 }"> --%>
												<a href="/makshi/gates/executive/getRentHouseStasticsDetailMain.ht?start=${start }&end=${end}&orgId=${e.orgId}" target="_blank">${e.houseNums }</a>
											<%-- </c:if>
											<c:if test="${e.houseNums<=0 }">
												${e.houseNums}
											</c:if> --%>
										</td>
										<td>${e.roomNums }</td>
										<td>
											<%-- <c:if test="${e.monthMoneys>0 }"> --%>
												<a href="/makshi/gates/executive/getRentHouseStasticsDetailMain.ht?start=${start }&end=${end}&orgId=${e.orgId}" target="_blank">${e.monthMoneys }</a>
											<%-- </c:if>
											<c:if test="${e.monthMoneys<=0 }">
												${e.monthMoneys}
											</c:if> --%>
										</td>
										<td>${e.peopleNums }</td>
									</tr>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
			</div>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
	<script type="text/javascript" src="${ctx }/js/select2/select2.min.js"></script>
	<script type="text/javascript" >
		$("#orgIds").select2({
			placeholder : "请选择",
			allowClear : true
		});
	</script>
</body>
</html>