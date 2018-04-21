$(function() {
	conttyps();
	loadInitPageData();
});
function conttyps() {
	var contractTypeText = $.trim($("#contractTypeText").val());
	$.ajax({
		type : "get",
		url : "/makshi/contract/contractinfo/queryContractType.ht?t=" + Math.random(),
		dataType : "json",
		contentType : "application/json",
		success : function(data) {
			var html = '<option value="">请选择</option >';
			var deepData = [];
			data.forEach(function(item, index) {
				var flag = 0;
				if (item.contractNumSecondList.length) {
					flag = 1;
					item.contractNumSecondList.forEach(function(item, index) {
						deepData.push({
							id : item.id,
							refId : item.refId,
							num : item.num,
							type : item.type
						});

					});
				}
				var checked = '';
				if (contractTypeText != '' && contractTypeText == item.type) {
					checked = "selected";
				}
				html += '<option ' + checked + ' data-flag="' + flag + '" data-flow="' + item.flowNo + '" data-num="' + item.contract_num + '" data-id="' + item.id + '" value ="'
						+ item.type + '">' + item.type + '</option>';
			});

			var str = JSON.stringify(deepData);
			$('#contracttype').html(html);
			assembelContractChild(deepData)

			$('#contracttype').on('change', function() {
				assembelContractChild(deepData);
			});

			$('#contracttype1').on('change', function() {
				var _$select = $(this).find("option:selected");
				if (_$select.val() == '') {
					return;
				}
			});
		}
	});
}
function assembelContractChild(deepData) {
	var contractTypeText1 = $.trim($("#contractTypeText1").val());
	var $select = $("#contracttype").find("option:selected");
	if ($select.val() == '') {
		$('#contracttype1').html("");
		$('#contracttype1').hide();
		return;
	}
	var flow = $select.attr('data-flow');
	var num = $select.attr('data-num');
	var flag = $select.attr('data-flag');
	var id = $select.attr('data-id');
	var html2 = '<option value="">请选择</option >';
	deepData
			.forEach(function(item, index) {
				if (item.refId == id) {
					var checked = '';
					if (contractTypeText1 != '' && contractTypeText1 == item.type) {
						checked = "selected";
					}
					html2 += '<option ' + checked + ' data-flow="' + flow + '" data-num="' + item.num + '" data-id="' + item.id + '" value ="' + item.type + '">' + item.type
							+ '</option>';
				}
			});
	$('#contracttype1').html(html2);
	if (flag == 0) {
		$('#contracttype1').hide();
	} else {
		$('#contracttype1').show();
	}
}

function loadInitPageData() {
	$.getJSON("/makshi/gates/executive/detail/contractTotal.ht", {
		contracttype : $("#contractTypeText").val(),
		contracttype1 : $("#contractTypeText1").val(),
		contract_num : $("#contract_num").val(),
		contract_name : $("#contract_name").val(),
		contract_status : $("#contract_status").val(),
		first_party : $("#first_party").val(),
		orgId : $("#orgId").val(),
		project_department : $("#project_department").val(),
		contract_handler : $("#contract_handler").val(),
		singingStart : $("#singingStart").val(),
		singingEnd : $("#singingEnd").val(),
		moneyMin : $("#moneyMin").val(),
		moneyMax : $("#moneyMax").val(),
	}, function(data) {
		initCustomerPagination(data.total, $("#Pagination"), loadEachPagePeopleItemInfo, loadInitPageData);
	});
}

// 异步加载分页数据
function loadEachPagePeopleItemInfo(pageNum) {
	pageNum = pageNum + 1;
	var queryData = {
		contracttype : $("#contractTypeText").val(),
		contracttype1 : $("#contractTypeText1").val(),
		contract_num : $("#contract_num").val(),
		contract_name : $("#contract_name").val(),
		contract_status : $("#contract_status").val(),
		first_party : $("#first_party").val(),
		orgId : $("#orgId").val(),
		project_department : $("#project_department").val(),
		contract_handler : $("#contract_handler").val(),
		singingStart : $("#singingStart").val(),
		singingEnd : $("#singingEnd").val(),
		moneyMin : $("#moneyMin").val(),
		moneyMax : $("#moneyMax").val(),
		page : pageNum,
		pageSize : $(".eachPageSize").val(),
	};
	$.post("/makshi/gates/executive/detail/contractPage.ht", queryData, function(data) {
		$("#result_field").html("");
		$("#result_field").append(data);
	});
}
