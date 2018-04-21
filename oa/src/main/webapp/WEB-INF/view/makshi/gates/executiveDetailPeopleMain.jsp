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
    .age{}
    .form-control{width:180px !important;}
    .control-label label{min-width:23px;}
    table tr:first-child th {
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
		<c:if test="${querytype=='people' }">
	    	<h2 class='executivetitle'>人员统计明细表</h2>
	    </c:if>
		<c:if test="${querytype=='age' }">
	    	<h2 class='executivetitle'>人员年龄分布明细表</h2>
	    </c:if>
		<c:if test="${querytype=='education' }">
	    	<h2 class='executivetitle'>人员学历分布明细表</h2>
	    </c:if>
		<c:if test="${querytype=='positional' }">
	    	<h2 class='executivetitle'>人员职称分布明细表</h2>
	    </c:if>
	</div>
	<div  >
        <div class="col-md-12">
			<form class="form-inline" role="form" method="post" action="/makshi/gates/executive/detail/people.ht" id="search-form">
				<input type="hidden" id="orgId" name='orgId' value="${orgId }">
				<input type="hidden" id="querytype" name='querytype' value="${querytype }">
				<input type="hidden" id="querygo" name='querygo' value="${querygo }">
		         <div class="form-body">
		             <div class="form-group">
		               	<label class="control-label">员工编号</label>
		               	<input type="text" name="jobno" id="jobno" value="${jobno}"  class="form-control" />                           
		             </div>
		         	<div class="form-group">
		               	<label class="control-label">姓<label>&nbsp;</label>名</label>
		                <input type="text" name="name" id="name" value="${name}"  class="form-control" />          
		             </div>
		         	<div class="form-group">
		               	<label class="control-label">性<label>&nbsp;</label>别</label>
		                <select id="sex" name='sex' class='form-control'>
		                  <option value="">全部</option>
		                  <option value="1" <c:if test='${sex==1 }'>selected</c:if> >男</option>
		                  <option value="0" <c:if test='${sex==0 }'>selected</c:if> >女</option>
		                </select>
		             </div>
		        </div>
		        <div class="form-body">
		         	<div class="form-group">
		               	<label class="control-label">学<label>&nbsp;</label>历</label>
		                <select id="education" name='education' class='form-control'>
		                    <option value="">全部</option>
		                    <option value="硕士" <c:if test="${education=='硕士'}">selected</c:if> >研究生及以上</option>
		                    <option value="本科" <c:if test="${education=='本科'}">selected</c:if> >本科</option>
		                    <option value="大专" <c:if test="${education=='大专'}">selected</c:if> >大专</option>
		                    <option value="中专" <c:if test="${education=='中专'}">selected</c:if> >中专</option>
		                    <option value="其他" <c:if test="${education=='其他'}">selected</c:if> >其他</option>
		                 </select>
		             </div>
		             
		            <c:if test="${querytype=='people' || querytype=='age' }">
			         	<div class="form-group">
			               	<label class="control-label">职<label>&nbsp;</label>称</label>
			                <select id="positional" name='positional' class='form-control'>
			                    <option value="">全部</option>
			                    <option value="教高" <c:if test="${positional=='教高'}">selected</c:if> >教高</option>
			                    <option value="高级" <c:if test="${positional=='高级'}">selected</c:if> >高级</option>
			                    <option value="中级" <c:if test="${positional=='中级'}">selected</c:if> >中级</option>
			                    <option value="初级" <c:if test="${positional=='初级'}">selected</c:if> >初级</option>
			                    <option value="其他" <c:if test="${positional=='其他'}">selected</c:if> >其他</option>
			                 </select>
			             </div>
		             </c:if>
		            <c:if test="${querytype=='education'}">
			         	<div class="form-group">
			               	<label class="control-label">毕业院校</label>
			                <input type="text" name="school" id="school" value="${school}"  class="form-control" />             
			             </div>
		             </c:if>
		            <c:if test="${querytype=='positional'}">
			         	<div class="form-group">
			               	<label class="control-label">职<label>&nbsp;</label>称</label>
			                <select id="positional" name='positional' class='form-control'>
			                    <option value="">全部</option>
			                    <option value="教高" <c:if test="${positional=='教高'}">selected</c:if> >教高</option>
			                    <option value="高级" <c:if test="${positional=='高级'}">selected</c:if> >高级</option>
			                    <option value="中级" <c:if test="${positional=='中级'}">selected</c:if> >中级</option>
			                    <option value="初级" <c:if test="${positional=='初级'}">selected</c:if> >初级</option>
			                    <option value="其他" <c:if test="${positional=='其他'}">selected</c:if> >其他</option>
			                 </select>
			             </div>
			             <div class="form-group">
			               	<label class="control-label">职称专业</label>
			                <input type="text" name="positionalMajor" id="positionalMajor" value="${positionalMajor}"  class="form-control" />             
			             </div>
		             </c:if>
		             
		         	<div class="form-group">
		               	<label class="control-label">岗<label>&nbsp;</label>位</label>
		                <input type="text" name="posname" id="posname" value="${posname}"  class="form-control" />          
		            </div>
		        </div>
		        <div class="form-body"> 
		         	<div class="form-group">
		               	<label class="control-label">年<label>&nbsp;</label>龄</label>
		                <input type="text" name="agesMin" value="${agesMin}" id="agesMin" onkeyup="this.value=this.value.replace(/[^\d]/g,'') "  class="form-control age" style="width: 68px !important;" />
		                	&lt;至&lt;=
		                <input type="text" name="agesMax" value="${agesMax}" id="agesMax" onkeyup="this.value=this.value.replace(/[^\d]/g,'') "  class="form-control age" style="width: 67px !important;"/>
		            </div>
		             
		         	<div class="form-group">
		               	<label class="control-label">入职日期</label>
		               	<input onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'entrydateEnd\')}'})" type="text" name="entrydateStart" id="entrydateStart" value="${entrydateStart}" class="form-control Wdate" />
		               		至
		               	<input onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" type="text" name="entrydateEnd" id="entrydateEnd" value="${entrydateEnd}" class="form-control Wdate" />
		            </div>
		            <!--  href="javascript:loadInitPageData();" -->
		            <button class="oa-button oa-button--primary oa-button--blue">查询</button>
		         </div>
		     </form>
		 </div>
    </div>
	<div class="oa-pdb20 oa-mgh20">
		<div id="oa_list_content" class="oa-table-scroll-wrap">	
			<div id="result_field"></div>
	    	<div class="pagination" id="Pagination" style="margin: -2px 0;"></div>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
	<script type="text/javascript">
		$("#executiveTitle").text($(".executivetitle").text());
	</script>
	<script type="text/javascript" src="${ctx}/js/pagination/jquery.pagination.js"></script>
	<script type="text/javascript" src="${ctx}/js/pagination/pagination-common.js"></script>
	<script type="text/javascript" src="${ctx}/js/makshi/gates/executive/executiveDetailPeopleMain.js"></script>
</body>
</html>