<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>考勤计算管理</title>
<%@include file="/commons/include/ats.jsp"%>
<script type="text/javascript" src="${ctx}/js/jquery/plugins/jquery-ui.min.js"></script>
<f:link href="jquery/plugins/jquery.multiselect.css"></f:link>
<script type="text/javascript" src="${ctx}/js/jquery/plugins/jquery.multiselect.min.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/ats/AtsAttenceCalculate.js"></script>
</head>
<body>
<div class="panel">
	<div  class="panel-search">
			<div class="toolbar-box">
				<div class="toolbar-head clearfix">
					<!-- 顶部按钮 -->
					<div class="buttons"> 		
						<a class="btn btn-sm btn-primary fa fa-search" href="javascript:void(0);" ><span>查询</span></a>
						<a class="btn btn-sm btn-primary allCalculate"   href="javascript:void(0);" ><span>计算全部</span></a>
				        <a class="btn btn-sm btn-primary  calculateSelect"   href="javascript:void(0);"   ><span>计算选中行</span></a>	
				        <a class="btn btn-sm btn-primary summary "   href="javascript:void(0);"   ><span>汇总显示</span></a>	
				        <a class="btn btn-sm btn-primary detail"   href="javascript:void(0);"   ><span>明细显示</span></a>	
				         <a class="btn btn-sm btn-primary export"   href="javascript:void(0);"   ><span>导出</span></a>	
					</div>
				</div>
				<!-- #查询条件-->
				<div class="toolbar-body" >
					<form  id="searchForm" class="search-form" >
						<ul>
							<li>
								<span>组织:</span>
								<input id="orgId"  name="Q_orgId_L" type="hidden"/>
								<input  id="orgName"  class="inputText"  readonly="readonly" />
								<a class="btn btn-sm btn-primary fa fa-search-plus" href="javascript:selectOrg();" ><span></span></a>
							</li>
							<li>
								<span>姓名:</span>
								<input  id="fullname"  class="inputText" />
							</li>
							<li>
								<span>工号:</span>
								<input  id="account"  class="inputText" />
							</li>
							
							<li id="attenceTypeLi" style="display: none;">
								<span>类型:</span>
								<select id="attenceType"  multiple="multiple">
									<option value="000"> 缺卡</option>
									<option value="001"> 正常</option>
									<option value="002"> 迟到</option>
									<option value="003"> 早退</option>
									<option value="004"> 旷工</option>
									<option value="005"> 加班</option>
									<option value="006"> 请假</option>
									<option value="007"> 出差</option>
								</select>
							</li>
						</ul>
						<ul>
							<li>
								<span>考勤制度:</span>
									<input type="hidden" id="attencePolicy" name="Q_attencePolicy_L" class="inputText" value="${atsAttencePolicy.id }"/>
									<input id="attencePolicyName" class="inputText" readonly="readonly" value="${atsAttencePolicy.name }"/>
									<a class="btn btn-sm btn-primary fa fa-search-plus" href="javascript:selectAttencePolicy();" ><span></span></a>
							</li>
							<li>
								<span>考勤日期从:</span>
								<input id="startTime" name="startTime"  class="inputText date"  value="${startTime}"/>
								
							</li>
							<li>	
								<span>至: </span>
								<input id="endTime"   name="endTime" class="inputText date"  value="${endTime}"/>
							</li>
							
							<li id="abnormityLi" style="display: none;">
								<span>异常:</span> 
								<select id="abnormity">
									<option value=""> 请选择</option>
									<option value="0"> 正常</option>
									<option value="-1"> 异常</option>
								</select>
							</li>
						</ul>
					</form>
				</div><!--/ 查询条件-->
			</div>
		</div><!--/ 操作、查询-->
	
		<ul class="nav nav-tabs">
		  <li class="active" action="1"><a href="javascript:void(0);" >已计算人员</a></li>
		  <li  action="2"><a href="javascript:void(0);" >未计算人员</a></li>
		  <li  action="3"><a href="javascript:void(0);" >结果明细</a></li>
		</ul>
		<div class="panel-body">
    			<div id="dataGrid" >请设置考勤制度</div>
    	</div>	
	
		</div><!-- end of panel-body -->
		<form id="exportForm" name="exportForm" method="post"  action="exportReportDetail.ht">
			<input id="orgPath1"  name="orgPath" type="hidden"/>
			<input id="fullname1" name="Q_fullname_SL" type="hidden"/>
			<input type="hidden" id="attencePolicy1" name="Q_attencePolicy_L" type="hidden"/>
			<input id="startTime1" name="Q_beginattenceTime_DL" type="hidden" />
			<input id="endTime1" name="Q_endattenceTime_DL" type="hidden"/>
			<input id="attenceType1" name="type" type="hidden"/>
			<input id="action" name="action" type="hidden"/>
			
		</form>
</div>		
</body>
</html>


