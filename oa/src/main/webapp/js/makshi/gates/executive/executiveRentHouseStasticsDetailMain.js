$(function() {
	loadInitPageData();
});

function loadInitPageData() {
	$.getJSON("/makshi/gates/executive/getRentHouseStasticsDetailCount.ht", {
		orgId : $("#orgId").val(),
		start : $("#start").val(),
		end : $("#end").val(),
		houseId : $("#houseId").val(),
		rentPerson : $("#rentPerson").val(),
	}, function(data) {
		initCustomerPagination(data.total, $("#Pagination"), loadEachPagePeopleItemInfo, loadInitPageData);
	});
}

// 异步加载分页数据
function loadEachPagePeopleItemInfo(pageNum) {
	pageNum = pageNum + 1;
	var queryData = {
		orgId : $("#orgId").val(),
		start : $("#start").val(),
		end : $("#end").val(),
		houseId : $("#houseId").val(),
		rentPerson : $("#rentPerson").val(),
		page : pageNum,
		pageSize : $(".eachPageSize").val(),
	};
	$.post("/makshi/gates/executive/getRentHouseStasticsDetailPage.ht", queryData, function(data) {
		$("#result_field").html("");
		$("#result_field").append(data);
	});
}
