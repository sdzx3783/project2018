<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>办公用品申购表管理</title>
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
<body class="oa-mw-page">
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
					<div class="oa-label">申请人</div>
					<div class="oa-input-wrap oa-mgl100">		
						<input date_name="condition" type="text" name="s.F_application_name"  ctltype="selector" class="user oa-input oa-trigger-hidden" value="${param['F_application_name']}"/>
					</div>
					<button class="oa-button-label oa-trigger-hidden-button" type="button">选择用户</button>		
				</li>
				<li class="oa-search-item oa-fl oa-mgb10">
					<div class="oa-label">申请部门</div>
					<div class="oa-input-wrap oa-mgl100">	
						<input date_name="condition"  data-type="7"  ctltype="selector" class="org oa-input  oa-trigger-hidden"  type="text" name="s.F_department" value="${param['F_department']}"/>
					</div>
					<button class="oa-button-label oa-trigger-hidden-button" type="button">选择部门</button>			
				</li>
<%-- 				<li class="oa-search-item oa-fl oa-mgb10">
					<div class="oa-label">用品名称</div>
					<div class="oa-input-wrap oa-mgl100">				
						<input date_name="condition" type="text" name="s.F_name" class="oa-input" value="${param['F_name']}"/>
					</div>
				</li>
				<li class="oa-search-item oa-fl oa-mgb10">
					<div class="oa-label">数量</div>
					<div class="oa-input-wrap oa-mgl100">				
						<input date_name="condition" type="text" name="s.F_purchase_number" class="oa-input" value="${param['F_purchase_number']}"/>
					</div>
				</li> --%>
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
				},{
					field : 's.F_application_name',//查询的字段
					title : '申请人',//显示的title
					sortable : true
				//是否支持排序
				},{
					field : 's.F_application_time',//查询的字段
					title : '申请时间',//显示的title
					sortable : true,
					dateFlag : true,//是否是时间，这个会弹出时间控件选择器
					dateFormat : "yyyy-MM-dd",//时间的显示格式
				//是否支持排序
				},{
					field : 's.F_department',//查询的字段
					title : '申请部门',//显示的title
					sortable : true
				},{
					field : 's.state',//查询的字段
					title : '审批状态',//显示的title
					sortable : true
				//是否支持排序
				},{
					field : '管理',//即使不去查询这个字段也必须。用来记录改变列的顺序用。如果不去查询必须带nosearch字段
					title : '管理',
					nosearch : true,//不去数据库查询
					formatter : 'edit'//显示的时候执行edit函数
				}, ],
				uniqueId : "s.id",//唯一主键字段
				type : "asset_stationeryList",//类型，用户存储在数据库中，每个项目的不同页面保证唯一
				tableName : 'asset_stationery s', //表明
				userId : '${sessionScope.LOGIN_USER_ID }', //用户ID
			//sortName : 's.seller_name',//初始化排序
			//sortOrder : 'desc'//初始化排序
			}
			initCommonTable(tableParam);
		});

		function edit(value, row, index) {
			var html = "";
					html += '<a href="get.ht?id=' + row["s.id"] + '" target="_blank">明细</a>';
			return html;
		}
		
		function chooseSearch(obj){
			if(obj==1){
				$(".superiorSearch").hide();
				$(".simpleSerch").show();
			}
			if(obj==2){
				$(".superiorSearch").show();
				$(".simpleSerch").hide();
			}
		}
		</script>
</body>
</html>



