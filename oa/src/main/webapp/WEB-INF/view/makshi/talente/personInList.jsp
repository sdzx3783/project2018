<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>人才引进管理</title>
<%@include file="/commons/include/list_get.jsp"%>
<style>
	.panel-body {
	    margin: 0 !important;
	}
	.change-mode[resizeMode="overflow"] {
	    color: #333;
	    background-color: #e6e6e6;
	    border-color: #adadad;
	}
	.bootstrap-table {
	    padding: 15px;
	}
</style>
</head>
<body>
	
	<div class="panel" style="margin-bottom: 0;">
		<div id="oa_list_title" class="oa-mgb10 oa-project-title"></div>
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">人才引进管理列表</span>
			</div>
		</div>
		<jsp:include page="/commons/include/list_common.jsp"></jsp:include>
		<!-- end of panel-body -->
	</div>
	
	<!-- end of panel -->
	<script type="text/javascript">
		$(function() {
			var tableParam = {
				columns : [ {
					field : 's.F_person_in_num',//查询的字段
					title : '工单号',//显示的title
					formatter : 'getDetail',
					sortable : true
				//是否支持排序
				}, {
					field : 's.F_applicant',
					title : '申请人',
					sortable : true
				//是否支持排序
				}, {
					field : 's.F_account',
					title : '员工编号',
					sortable : true
				//是否支持排序
				}, {
					field : 's.F_application_time',
					title : '申请时间',
					dateFlag : true,//是否是时间，这个会弹出时间控件选择器
					dateFormat : "yyyy-MM-dd",//时间的显示格式
					sortable : true
				//是否支持排序
				}, {
					field : 's.F_age',
					title : '年龄',
					sortable : true
				//是否支持排序
				}, {
					field : 's.F_edu',
					title : '全日制学历',
					sortable : true,
					formatter : 'edu'//显示的时候执行edit函数
				//是否支持排序
				}, {
					field : 's.F_residence_type',
					title : '户口类型',
					sortable : true,
					formatter : 'residenceType'//显示的时候执行edit函数
				//是否支持排序
				}, {
					field : 's.F_house_status',
					title : '房产情况',
					sortable : true,
					formatter : 'houseStatus'//显示的时候执行edit函数
				//是否支持排序
				}, {
					field : 's.F_trust_unit',
					title : '档案托管单位',
					sortable : true
				//是否支持排序
				}, {
					field : 's.F_settled_situation',
					title : '落户地址',
					sortable : true,
					formatter : 'settledSituation'
				//是否支持排序
				}],
				uniqueId : "s.ID",//唯一主键字段
				type : "w_person_inList",//类型，用户存储在数据库中，每个项目的不同页面保证唯一
				tableName : 'w_person_in s', //表明
				userId : '${sessionScope.LOGIN_USER_ID }', //用户ID
			//sortName : 's.seller_name',//初始化排序
			//sortOrder : 'desc'//初始化排序
			}
			initCommonTable(tableParam);
			handlerDelSelect();
		});

		/* *
		修改显示列
		value: the field value. 
		row: the row record data.
		index: the row index
		 **/
		function edit(value, row, index) {
			var html = '<a href="edit.ht?id=' + row["s.ID"]
					+ '" class="link edit"><span></span>编辑</a>';
			return html;
		}
		function getDetail(value, row, index) {
			var id = row["s.ID"];
			var html = '--';
			if(value && value.length>0){
				html =  '<a href=get.ht?id='+id+' target="_blank">'+value+'</a>';
			}
			return html;
		}
		function settledSituation(value, row, index) {
			if (value == 0) {
				return "公司集体户";
			} else if (value == 1) {
				return "个人/亲戚处";
			}
			return "";
		}

		function houseStatus(value, row, index) {
			if (value == 0) {
				return "有";
			} else if (value == 1) {
				return "无";
			}
			return "";
		}
		function residenceType(value, row, index) {
			if (value == 0) {
				return "非农";
			} else if (value == 1) {
				return "农业";
			}
			return "";
		}
		function edu(value, row, index) {
			if (value == 0) {
				return "研究生";
			} else if (value == 1) {
				return "本科";
			} else if (value == 2) {
				return "大专";
			} else if (value == 3) {
				return "其他";
			}
			return "";
		}

		/**
		 * 获取选择的数据
		 */
		function getSelect() {
			return $('#tb_common_show').bootstrapTable('getSelections');
		}
	</script>
</body>
</html>


