<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<%-- <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> --%>
<html>
<head>
<title>资产移交表管理</title>
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
</head>
<body>
	<div id="oa_list_title" class="oa-mgb10 oa-project-title"></div>
    <div class="oa-head-wrap">
        <!-- 在这里配置每个页面的简单查询模块 -->
        <div id="oa_simple_search" class="oa-simple-search-wrap oa-clear">
			<ul>
				<li class="oa-search-item oa-fl oa-mgb10">
					<div class="oa-label">工号</div>
					<div class="oa-input-wrap oa-mgl100">
						<input date_name="condition" type="text" name="s.f_account" class="oa-input" value="${param['f_account']}"/>
					</div>
				</li>
				<li class="oa-search-item oa-fl oa-mgb10">
					<div class="oa-label">移交人</div>
					<div class="oa-input-wrap oa-mgl100">
						<input date_name="condition" type="text" name="s.F_transfer_person"  ctltype="selector" class="user oa-input oa-trigger-hidden" value="${param['F_transfer_person']}"/>
					</div>
					<button class="oa-button-label oa-trigger-hidden-button" type="button">选择用户</button>
				</li>
				<li class="oa-search-item oa-fl oa-mgb10">
					<div class="oa-label">接交人</div>
					<div class="oa-input-wrap oa-mgl100">
						<input date_name="condition" type="text" name="s.F_receiption_person"  ctltype="selector"  class="user oa-input oa-trigger-hidden"  value="${param['F_receiption_person']}"/>
					</div>
					<button class="oa-button-label oa-trigger-hidden-button" type="button">选择用户</button>
				</li>
				<li class="oa-search-item oa-fl oa-mgb10">
					<div class="oa-label">移交部门</div>
					<div class="oa-input-wrap oa-mgl100">
						<input date_name="condition" type="text" name="s.F_transfer_department" data-type="7"  ctltype="selector" class="org oa-input oa-trigger-hidden" value="${param['F_transfer_department']}"/>
					</div>
					<button class="oa-button-label oa-trigger-hidden-button" type="button">选择部门</button>
				</li>
				<li class="oa-search-item oa-fl oa-mgb10">
					<div class="oa-label">移交日期从</div>
					<div class="oa-input-wrap oa-mgl100 oa-input-wrap--ib">
						<input date_name="condition" data-type="2" type="text"  name="s.F_time"  class="oa-input date" validate="{date:true}" value="${param['F_time']}"/>
					</div>
					<span>至</span>
					<div class="oa-input-wrap oa-input-wrap--ib">
						<input date_name="condition" data-type="4" type="text"  name="s.F_time"  class="oa-input date" validate="{date:true}" value="${param['F_time']}"/>
					</div>
				</li>
			</ul>
		</div>
	</div>

	<jsp:include page="/commons/include/list_common.jsp"></jsp:include> 

<script type="text/javascript">
		$(function() {
			var tableParam = {
				columns : [ {
					field : 's.f_account',//查询的字段
					title : '工号',//显示的title
					sortable : true
				//是否支持排序
				}, {
					field : 's.F_asset_id',//查询的字段
					title : '固定资产编号',//显示的title
					sortable : true
					//是否支持排序
				}, {
					field : 's.F_asset_name',//查询的字段
					title : '固定资产名称',//显示的title
					sortable : true
				//是否支持排序
				},{
					field : 's.F_transfer_person',//查询的字段
					title : '移交人',//显示的title
					sortable : true
				//是否支持排序
				},{
					field : 's.F_transfer_department',//查询的字段
					title : '移交部门',//显示的title
					sortable : true
				//是否支持排序
				},{
					field : 's.F_receiption_person',//查询的字段
					title : '接交人',//显示的title
					sortable : true
				//是否支持排序
				},{
					field : 's.F_receiption_department',//查询的字段
					title : '接交部门',//显示的title
					sortable : true
				//是否支持排序
				},{
					field : 's.F_reason',//查询的字段
					title : '移交原因',//显示的title
					sortable : true
				//是否支持排序
				},{
					field : 's.F_time',//查询的字段
					title : '移交日期',//显示的title
					sortable : true,
					dateFlag : true,//是否是时间，这个会弹出时间控件选择器
					dateFormat : "yyyy-MM-dd",//时间的显示格式
				//是否支持排序
				},{
					field : 's.state',//查询的字段
					title : '审批状态',//显示的title
					sortable : true
				//是否支持排序
				}, {
					field : '管理',//即使不去查询这个字段也必须。用来记录改变列的顺序用。如果不去查询必须带nosearch字段
					title : '管理',
					nosearch : true,//不去数据库查询
					formatter : 'edit'//显示的时候执行edit函数
				}, ],
				uniqueId : "s.id",//唯一主键字段
				type : "asset_transferList",//类型，用户存储在数据库中，每个项目的不同页面保证唯一
				tableName : 'asset_transfer s', //表明
				userId : '${sessionScope.LOGIN_USER_ID }', //用户ID
			//sortName : 's.seller_name',//初始化排序
			//sortOrder : 'desc'//初始化排序
			}
			initCommonTable(tableParam);
		});
		function edit(value, row, index) {
			var html = '<a  target="_blank" href="get.ht?id=' + row["s.id"]+ '">&nbsp明细</a>';
			return html;
		}
		</script>
</body>
</html>


