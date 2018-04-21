$(function() {
	$("#orgIds").select2({
		placeholder : "请选择",
		allowClear : true
	});
	querys();
	$("#queryForm").click(function() {
		$("#bootstrapTable").bootstrapTable('refresh', queryParams);
	});
});

function queryParams(params) {
	return {
		year : $("#year").val(),
		month : $("#month").val(),
		orgIds : $.trim($("#orgIds").val())
	};
}

function querys() {
	$("#bootstrapTable").bootstrapTable({
		url : '/makshi/gates/executive/peopleMonthJson.ht',
		// height : 500,
		classes : 'boottable',
		undefinedText : '-',
		pagination : false, // 分页
		striped : true, // 是否显示行间隔色
		queryParams : queryParams,
		cache : false, // 是否使用缓存
		// pageList : [ 5, 10, 20 ],
		toolbar : "#toolbootbar",// 指定工具栏
		showColumns : true, // 显示隐藏列
		showRefresh : true, // 显示刷新按钮
		uniqueId : "orgId", // 每一行的唯一标识
		sidePagination : "server", // 服务端处理分页
		columns : [ {
			title : '部门',
			field : 'orgName', // 字段
			align : 'center', // 对齐方式（左 中 右）
			valign : 'middle', //  
			'class' : 'boottable-th',
			sortable : false
		}, {
			title : '月平均人数',
			field : 'monthAverageNum', // 字段
			align : 'center', // 对齐方式（左 中 右）
			valign : 'middle', // 
			sortable : false,
			visible : false
		}, {
			title : '新进率',
			field : 'newArrivalRate', // 字段
			align : 'center', // 对齐方式（左 中 右）
			valign : 'middle', //  
			formatter : getDetail,
			sortable : false,
			visible : false
		}, {
			title : '留存率',
			field : 'retentionRate', // 字段
			align : 'center', // 对齐方式（左 中 右）
			valign : 'middle', //  
			formatter : getDetail,
			sortable : false,
			visible : false
		}, {
			title : '损失率',
			field : 'lossRate', // 字段
			align : 'center', // 对齐方式（左 中 右）
			valign : 'middle', //  
			formatter : getDetail,
			sortable : false,
			visible : false
		}, {
			title : '进出比率',
			field : 'exportRatio', // 字段
			align : 'center', // 对齐方式（左 中 右）
			valign : 'middle', //  
			formatter : getDetail,
			sortable : false,
			visible : false
		}, {
			title : '新晋员工比率',
			field : 'newEmployeeRatio', // 字段
			align : 'center', // 对齐方式（左 中 右）
			valign : 'middle', //  
			formatter : getDetail,
			sortable : false,
			visible : false
		}, {
			title : '异动率',
			field : 'turnoverLostRate', // 字段
			align : 'center', // 对齐方式（左 中 右）
			valign : 'middle', //  
			formatter : getDetail,
			sortable : false,
			visible : false
		}, {
			title : '人员流动率',
			field : 'staffTurnoverLostRate', // 字段
			align : 'center', // 对齐方式（左 中 右）
			valign : 'middle', //  
			formatter : getDetail,
			sortable : false,
			visible : false
		},

		{
			title : '本月期初人数',
			field : 'perMonthTotal', // 字段
			align : 'center', // 对齐方式（左 中 右）
			valign : 'middle', //  
			sortable : false
		}, {
			title : '本月新增',
			field : 'inMonthNum', // 字段
			align : 'center', // 对齐方式（左 中 右）
			valign : 'middle', //  
			sortable : false
		}, {
			title : '本月离职',
			field : 'goMonthNum', // 字段
			align : 'center', // 对齐方式（左 中 右）
			valign : 'middle', //  
			sortable : false
		}, {
			title : '本月期末人数',
			field : 'cutMonthTotal', // 字段
			align : 'center', // 对齐方式（左 中 右）
			valign : 'middle', //  
			sortable : false,
		}, {
			title : '离职率',
			field : 'turnoverRate', // 字段
			align : 'center', // 对齐方式（左 中 右）
			valign : 'middle', //  
			formatter : getDetail,
			sortable : false,
		} ],
		responseHandler : function(res) {
			return {
				total : res.total,
				rows : res.items
			};
		}
	})
}
function getDetail(value, row, index) {
	var v = value * 100;
	if (v == 0)
		return v;
	return v.toFixed(2) + '%';
}