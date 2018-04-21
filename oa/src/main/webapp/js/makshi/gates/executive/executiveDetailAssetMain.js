$(function() {
	loadInitPageData();
});

function loadInitPageData() {
	$.getJSON("/makshi/gates/executive/detail/assetTotal.ht", {
		orgId : $("#orgId").val(),
		assetCode : $("#assetCode").val(),
		assetId : $("#assetId").val(),
		assetName : $("#assetName").val(),
		carePerson : $("#carePerson").val(),
	}, function(data) {
		initCustomerPagination(data.total, $("#Pagination"), loadEachPagePeopleItemInfo, loadInitPageData);
	});
}

// 异步加载分页数据
function loadEachPagePeopleItemInfo(pageNum) {
	pageNum = pageNum + 1;
	var queryData = {
		orgId : $("#orgId").val(),
		assetCode : $("#assetCode").val(),
		assetId : $("#assetId").val(),
		assetName : $("#assetName").val(),
		carePerson : $("#carePerson").val(),
		page : pageNum,
		pageSize : $(".eachPageSize").val(),
	};
	$.post("/makshi/gates/executive/detail/assetPage.ht", queryData, function(data) {
		$("#result_field").html("");
		$("#result_field").append(data);
	});
}
