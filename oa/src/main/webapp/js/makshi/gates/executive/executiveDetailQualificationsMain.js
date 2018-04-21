$(function() {
	loadInitPageData();
});

function loadInitPageData() {
	$.getJSON("/makshi/gates/executive/detail/qualificationsTotal.ht", {
		type : $("#type").val(),
	}, function(data) {
		initCustomerPagination(data.total, $("#Pagination"), loadEachPagePeopleItemInfo, loadInitPageData);
	});
}

// 异步加载分页数据
function loadEachPagePeopleItemInfo(pageNum) {
	pageNum = pageNum + 1;
	var queryData = {
		type : $("#type").val(),
		page : pageNum,
		pageSize : $(".eachPageSize").val(),
	};
	$.post("/makshi/gates/executive/detail/qualificationsPage.ht", queryData, function(data) {
		$("#result_field").html("");
		$("#result_field").append(data);
	});
}
