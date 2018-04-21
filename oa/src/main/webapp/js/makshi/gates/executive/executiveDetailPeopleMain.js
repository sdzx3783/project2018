$(function() {
	loadInitPageData();
});

function loadInitPageData() {
	$.getJSON("/makshi/gates/executive/detail/peopleTotal.ht", {
		querytype : $("#querytype").val(),
		orgId : $("#orgId").val(),
		jobno : $("#jobno").val(),
		name : $("#name").val(),
		sex : $("#sex").val(),
		education : $("#education").val(),
		posname : $("#posname").val(),
		positional : $("#positional").val(),
		posid : $("#posid").val(),
		agesMin : $("#agesMin").val(),
		agesMax : $("#agesMax").val(),
		entrydateStart : $("#entrydateStart").val(),
		entrydateEnd : $("#entrydateEnd").val(),
		school : $("#school").val(),
		positionalMajor : $("#positionalMajor").val(),
		querygo : $("#querygo").val(),
	}, function(data) {
		initCustomerPagination(data.total, $("#Pagination"), loadEachPagePeopleItemInfo, loadInitPageData);
	});
}

// 异步加载分页数据
function loadEachPagePeopleItemInfo(pageNum) {
	pageNum = pageNum + 1;
	var queryData = {
		querytype : $("#querytype").val(),
		orgId : $("#orgId").val(),
		jobno : $("#jobno").val(),
		name : $("#name").val(),
		sex : $("#sex").val(),
		education : $("#education").val(),
		posname : $("#posname").val(),
		positional : $("#positional").val(),
		posid : $("#posid").val(),
		agesMin : $("#agesMin").val(),
		agesMax : $("#agesMax").val(),
		entrydateStart : $("#entrydateStart").val(),
		entrydateEnd : $("#entrydateEnd").val(),
		school : $("#school").val(),
		positionalMajor : $("#positionalMajor").val(),
		querygo : $("#querygo").val(),
		page : pageNum,
		pageSize : $(".eachPageSize").val(),
	};
	$.post("/makshi/gates/executive/detail/peoplePage.ht", queryData, function(data) {
		$("#result_field").html("");
		$("#result_field").append(data);
	});
}
