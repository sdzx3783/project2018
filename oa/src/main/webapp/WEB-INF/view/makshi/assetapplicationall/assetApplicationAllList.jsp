<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>资产申购汇总表管理</title>
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
						<input date_name="condition" type="text" name="s.F_user_name"  ctltype="selector" class="user oa-input oa-trigger-hidden" value="${param['F_user_name']}"/>
					</div>
					<button class="oa-button-label oa-trigger-hidden-button" type="button">选择用户</button>
				</li>
				<li class="oa-search-item oa-fl oa-mgb10">
					<div class="oa-label">使用人</div>
					<div class="oa-input-wrap oa-mgl100">
						<input date_name="condition" type="text" name="s.F_use_name"  ctltype="selector"  class="user oa-input oa-trigger-hidden"  value="${param['F_use_name']}"/>
					</div>
					<button class="oa-button-label oa-trigger-hidden-button" type="button">选择用户</button>
				</li>
				<li class="oa-search-item oa-fl oa-mgb10">
					<div class="oa-label">申请部门</div>
					<div class="oa-input-wrap oa-mgl100">
						<input date_name="condition" type="text" name="s.F_fist_department" data-type="7"  ctltype="selector" class="org oa-input oa-trigger-hidden" value="${param['F_fist_department']}"/>
					</div>
					<button class="oa-button-label oa-trigger-hidden-button" type="button">选择部门</button>
				</li>
				<li class="oa-search-item oa-fl oa-mgb10">
					<div class="oa-label">申请日期从</div>
					<div class="oa-input-wrap oa-mgl100 oa-input-wrap--ib">
						<input date_name="condition" data-type="2" type="text"  name="s.F_application_time"  class="date oa-input" validate="{date:true}" value="${param['F_application_time']}"/>
					</div>
					<span>至</span>
					<div class="oa-input-wrap oa-input-wrap--ib">
						<input date_name="condition" data-type="4" type="text"  name="s.F_application_time"  class="date oa-input" validate="{date:true}" value="${param['F_application_time']}"/>
					</div>
				</li>
				 <li class="oa-search-item oa-fl oa-mgb10">
                    <div class="oa-label">审批状态</div>
                    <div class="oa-select-wrap  oa-mgl100">
                        <select class="oa-select" date_name="condition" name="s.state" >
                            <option value="">请选择</option>
                            <option value="申请">申请</option>
                            <option value="项目部审批">项目部审批</option>
                            <option value="部门审批">部门审批</option>
                            <option value="分管领导审批">分管领导审批</option>
                            <option value="办公室审批">办公室审批</option>
                            <option value="办公室采购">办公室采购</option>
                            <option value="通知申请人">通知申请人</option>
                            <option value="部门自行采购">部门自行采购</option>
                            <option value="领取旧设备">部门审批</option>
                            <option value="登记领用">登记领用</option>
                            <option value="流程已结束">流程已结束</option>
                        </select>
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
			title : '序号',//显示的title
			formatter :'addNum'
		//是否支持排序
		}, {
			field : 's.f_account',//查询的字段
			title : '工号',//显示的title
			sortable : true
		//是否支持排序
		}, {
			field : 's.F_asset_name',//查询的字段
			title : '固定资产名称',//显示的title
			sortable : true
		//是否支持排序
		},{
			field : 's.F_number',//查询的字段
			title : '数量',//显示的title
			sortable : true
		//是否支持排序
		},{
			field : 's.F_user_name',//查询的字段
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
			field : 's.F_use_name',//查询的字段
			title : '使用人',//显示的title
			sortable : true,
		//是否支持排序
		},{
			field : 's.F_fist_department',//查询的字段
			title : '申请部门',//显示的title
			sortable : true
		//是否支持排序
		},{
			field : 's.state',//查询的字段
			title : '审批状态',//显示的title
			sortable : true,
			formatter : 'setState'//显示的时候执行edit函数
		//是否支持排序
		}, ],
		uniqueId : "s.id",//唯一主键字段
		type : "asset_purchase_listList",//类型，用户存储在数据库中，每个项目的不同页面保证唯一
		tableName : 'asset_purchase_list s', //表明
		userId : '${sessionScope.LOGIN_USER_ID }', //用户ID
	//sortName : 's.seller_name',//初始化排序
	//sortOrder : 'desc'//初始化排序
	}
	initCommonTable(tableParam);
});

function addNum(value, row, index) {  
	var page = $('#tb_common_show').bootstrapTable("getPage"); 
    return page.pageSize * (page.pageNumber - 1) + index + 1;   
} 

function setState(value, row, index){
	if (value) {
		return '<a  target="_blank" href="get.ht?id=' + row["s.id"]+ '">'+value+'</a>';
	} 
	return '<a target="_blank" href="get.ht?id=' + row["s.id"]+ '">&nbsp流程结束</a>';
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


