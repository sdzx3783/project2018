$(function() {
	loadInitPageData();
});

function loadInitPageData() {
	$.getJSON("/makshi/director/organization/total.ht", {
		 
	}, function(data) {
		initCustomerPagination(data.total, $("#Pagination"), loadEachPagePeopleItemInfo, loadInitPageData);
	});
}

// 异步加载分页数据
function loadEachPagePeopleItemInfo(pageNum) {
	pageNum = pageNum + 1;
	var queryData = {
		page : pageNum,
		pageSize : $(".eachPageSize").val(),
	};
	$.post("/makshi/director/organization/page.ht", queryData, function(data) {
		$("#result_field").html("");
		$("#result_field").append(data);
		//$("#result_field").css("min-height",$(window).height()-$("#result_field").height()-150)
	});
}
