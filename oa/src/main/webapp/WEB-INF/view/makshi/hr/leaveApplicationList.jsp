<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="com.hotent.platform.model.system.SysUser"%>
<%@include file="/commons/include/html_doctype.html" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
    <title>用户请假申请列表</title>
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
</head>
<body class="oa-mw-page">
		<div id="oa_search_table_button_wrap" class="oa-head-wrap">
		</div>
        <!-- 在这里配置每个页面的简单查询模块 -->
        <div id="oa_simple_search" class="oa-simple-search-wrap oa-clear">
            <ul>
				 <li class="oa-search-item oa-fl oa-mgb10">
                    <div class="oa-label">申请人</div>
                    <div class="oa-input-wrap oa-mgl100">
                        <input data-type="7"  date_name="condition" type="text" name="s.F_applicant" class="oa-input" value="${param['F_applicant']}"/>
                    </div>
                </li>
                <li class="oa-search-item oa-fl oa-mgb10">
                    <div class="oa-label">员工编号</div>
                    <div class="oa-input-wrap oa-mgl100">
                        <input data-type="7"  date_name="condition" type="text" name="s.F_account" class="oa-input" value="${param['F_account']}"/>
                    </div>
                </li>
                <li class="oa-search-item oa-fl oa-mgb10">
                    <div class="oa-label">所属部门</div>
                    <div class="oa-input-wrap oa-mgl100">
                        <input date_name="condition" data-type="7"  ctltype="selector" class="org oa-input oa-trigger-hidden"   type="text" name="s.F_department" value="${param['F_department']}"/>
                    </div>
                    <button id="nowDepartment" class="oa-button-label oa-trigger-hidden-button" type="button">选择部门</button>
                </li>
                <li class="oa-search-item oa-fl oa-mgb10">
                    <div class="oa-label">请假时间从</div>
                    <div class="oa-input-wrap oa-mgl100 oa-input-wrap--ib">
                    	<input date_name="condition" data-type="2" type="text"  name="s.F_startTime"  class="oa-input date" validate="{date:true}" value="${param['F_startTime']}"/>
                    </div>
                    <span>至</span>
                    <div class="oa-input-wrap oa-input-wrap--ib">
                        <input date_name="condition" data-type="4" type="text"  name="s.F_endTime"  class="oa-input date" validate="{date:true}" value="${param['F_endTime']}"/>
                    </div>
                </li>
            </ul>
        </div>

   	<!-- 高级查询模块 -->
	<jsp:include page="/commons/include/list_common.jsp"></jsp:include>
    <script type="text/javascript">
	$(function() {
		var tableParam = {
			columns : [ {
				field : 's.F_applicant',//查询的字段
				title : '申请人',//显示的title
				formatter : 'getDetail',
			},{
				field : 's.F_user_num',//查询的字段
				title : '用户编号',//显示的title
				sortable : false,
				visible : false
			},{
				field : 's.F_join_work_time',//查询的字段
				title : '参加工作时间',//显示的title
				sortable : true,
				dateFlag : true,//是否是时间，这个会弹出时间控件选择器
				dateFormat : "yyyy-MM-dd",//时间的显示格式 
			},{
				field : 's.F_induction_time',//查询的字段
				title : '入职时间',//显示的title
				sortable : true,
				dateFlag : true,//是否是时间，这个会弹出时间控件选择器
				dateFormat : "yyyy-MM-dd",//时间的显示格式 
			},{
				field : 's.F_application_date',//查询的字段
				title : '申请日期',//显示的title
				sortable : true,
				dateFlag : true,//是否是时间，这个会弹出时间控件选择器
				dateFormat : "yyyy-MM-dd",//时间的显示格式 
			},{
				field : 's.F_account',//查询的字段
				title : '员工编号',//显示的title
				sortable : true
			},{
				field : 's.F_department',//查询的字段
				title : '所属部门',//显示的title
				sortable : true
			},{
				field : 's.F_leave_type',//查询的字段
				title : '请假类型',//显示的title
				sortable : true
			},/* {
				field : 's.F_position',//查询的字段
				title : '职位',//显示的title
				sortable : true
			}, */{
				field : 's.F_leave_days',//查询的字段
				title : '请假天数',//显示的title
				sortable : true
			}, {
				field : 's.F_startTimeView',//查询的字段
				title : '请假开始时间',//显示的title
				sortable : true,
				/* dateFlag : true,//是否是时间，这个会弹出时间控件选择器
				dateFormat : "yyyy-MM-dd",//时间的显示格式  */
			},{
				field : 's.F_endTimeView',//查询的字段
				title : '请假结束时间',//显示的title
				sortable : true,
				/* dateFlag : true,//是否是时间，这个会弹出时间控件选择器
				dateFormat : "yyyy-MM-dd",//时间的显示格式  */
			},{
				field : 's.F_reason',//查询的字段
				title : '请假事由',//显示的title
				sortable : true
			},{
				field : 's.F_remain_annual_leave',//查询的字段
				title : '剩余年假天数',//显示的title
				sortable : true
			},{
				field : 's.processStatus',//查询的字段
				title : '审批状态',//显示的title
				sortable : true
			}],
			uniqueId : "s.ID",//唯一主键字段
			type : "vactionViewList",//类型，用户存储在数据库中，每个项目的不同页面保证唯一
			tableName :'vactionView s', //表明
			userId : '${sessionScope.LOGIN_USER_ID }', //用户ID
		//sortName : 'seller_name',//初始化排序
		//sortOrder : 'desc'//初始化排序
		}
		initCommonTable(tableParam);
	});
	function getDetail(value, row, index) { 
		var id = row["s.F_user_num"];
		var html =  '<a href="/platform/system/sysUser/get.ht?userId='+id+'" target="_blank">'+value+'</a>';
		return html;
	}
</script>
</body>
</html>





