$(function() {
	loadInitPageData();
});

function loadInitPageData() {
	$.getJSON("/makshi/gates/executive/qualificationPeopleDetailCount.ht", {
		other : $("#other").val(),
		year : $("#year").val(),
		type : $("#type").val(),
		qualification : $("#qualification").val(),
		userNo : $("#userNo").val(),
		userName : $("#userName").val(),
		certificateNo : $("#certificateNo").val()
	}, function(data) {
		initCustomerPagination(data.total, $("#Pagination"), loadEachPagePeopleItemInfo, loadInitPageData);
	});
}

// 异步加载分页数据
function loadEachPagePeopleItemInfo(pageNum) {
	pageNum = pageNum + 1;
	var queryData = {
		other : $("#other").val(),
		year : $("#year").val(),
		type : $("#type").val(),
		qualification : $("#qualification").val(),
		userNo : $("#userNo").val(),
		userName : $("#userName").val(),
		certificateNo : $("#certificateNo").val(),
		page : pageNum,
		pageSize : $(".eachPageSize").val(),
	};
	$.post("/makshi/gates/executive/qualificationPeopleDetailPage.ht", queryData, function(data) {
		$("#result_field").html("");
		$("#result_field").append(data);
	});
}
