<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="com.hotent.platform.model.system.SysUser"%>
<%@include file="/commons/include/html_doctype.html" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
    <title>用户部门调动列表</title>
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
		<div id="oa_search_table_button_wrap" class="oa-head-wrap"></div>
        <!-- 在这里配置每个页面的简单查询模块 -->
        <div id="oa_simple_search" class="oa-simple-search-wrap oa-clear">
            <ul>
				 <li class="oa-search-item oa-fl oa-mgb10">
                    <div class="oa-label">申请人</div>
                    <div class="oa-input-wrap oa-mgl100">
                        <input data-type="7"  date_name="condition" type="text" name="s.F_user_name" class="oa-input" value="${param['F_user_name']}"/>
                    </div>
                </li>
                <li class="oa-search-item oa-fl oa-mgb10">
                    <div class="oa-label">员工编号</div>
                    <div class="oa-input-wrap oa-mgl100">
                        <input data-type="7"  date_name="condition" type="text" name="s.F_account" class="oa-input" value="${param['F_account']}"/>
                    </div>
                </li>
                <li class="oa-search-item oa-fl oa-mgb10">
                    <div class="oa-label">调动日期从</div>
                    <div class="oa-input-wrap oa-mgl100 oa-input-wrap--ib">
                    	<input date_name="condition" data-type="2" type="text"  name="s.F_out_month" onclick="WdatePicker({dateFmt:'yyyy-MM'})"  class="oa-input Wdate"   value="${param['F_out_month']}"/>
                    </div>
                    <span>至</span>
                    <div class="oa-input-wrap oa-input-wrap--ib">
                        <input date_name="condition" data-type="4" type="text"  name="s.F_out_month" onclick="WdatePicker({dateFmt:'yyyy-MM'})"  class="oa-input Wdate"   value="${param['F_out_month']}"/>
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
				field : 's.F_user_name',//查询的字段
				title : '申请人',//显示的title
				sortable : true,
				formatter : 'getDetail',
			},{
				field : 's.F_user_id',//查询的字段
				title : '用户编号',//显示的title
				sortable : false,
				visible : false
			},{
				field : 's.education',//查询的字段
				title : '学历',//显示的title
				sortable : true
			},{
				field : 's.f_major',//查询的字段
				title : '毕业专业',//显示的title
				sortable : true
			},{
				field : 's.graduate_school',//查询的字段
				title : '毕业院校',//显示的title
				sortable : true
			},{
				field : 's.positional',//查询的字段
				title : '职称',//显示的title
				sortable : true
			},{
				field : 's.F_out_department',//查询的字段
				title : '调出部门',//显示的title
				sortable : true
			},{
				field : 's.F_out_month',//查询的字段
				title : '调动日期',//显示的title
				sortable : true,
				dateFlag : true,//是否是时间，这个会弹出时间控件选择器
				dateFormat : "yyyy-MM",//时间的显示格式 
			},{
				field : 's.F_account',//查询的字段
				title : '员工编号',//显示的title
				sortable : true
			},{
				field : 's.F_transfer_reason',//查询的字段
				title : '调整原因',//显示的title
				sortable : true
			},{
				field : 's.F_in_department',//查询的字段
				title : '调入部门',//显示的title
				sortable : true
			},{
				field : 's.F_in_department_manager',//查询的字段
				title : '调入部门经理',//显示的title
				sortable : true
			},{
				field : 's.F_beforePost',//查询的字段
				title : '岗位',//显示的title
				sortable : true
			},{
				field : 's.F_in_post',//查询的字段
				title : '调整后岗位',//显示的title
				sortable : true
			},{
				field : 's.F_leader',//查询的字段
				title : '直属领导',//显示的title
				sortable : true
			},{
				field : 's.processStatus',//查询的字段
				title : '审批状态',//显示的title
				sortable : true
			}],
			uniqueId : "s.ID",//唯一主键字段
			type : "departmentTransferViewList",//类型，用户存储在数据库中，每个项目的不同页面保证唯一
			tableName :'departmentTransferView s', //表明
			userId : '${sessionScope.LOGIN_USER_ID }', //用户ID
		//sortName : 'seller_name',//初始化排序
		//sortOrder : 'desc'//初始化排序
		}
		initCommonTable(tableParam);
	});
	function getDetail(value, row, index) { 
		var id = row["s.F_user_id"];
		var html =  '<a href="/platform/system/sysUser/get.ht?userId='+id+'" target="_blank">'+value+'</a>';
		return html;
	}
</script>
</body>
</html>





