$(function() {
	loadInitPageData();
});

function loadInitPageData() {
	$.getJSON("/makshi/selfcheck/monthmain/total.ht", {
		submitAt : $("#submitAt").val(),
		status : $("#status").val(),
	}, function(data) {
		initCustomerPagination(data.total, $("#Pagination"), loadEachPagePeopleItemInfo, loadInitPageData);
	});
}

// 异步加载分页数据
function loadEachPagePeopleItemInfo(pageNum) {
	pageNum = pageNum + 1;
	var queryData = {
		submitAt : $("#submitAt").val(),
		status : $("#status").val(),
		page : pageNum,
		pageSize : $(".eachPageSize").val(),
	};
	$.post("/makshi/selfcheck/monthmain/page.ht", queryData, function(data) {
		$("#result_field").html("");
		$("#result_field").append(data);
	});
}

function del(id) {
	$.ligerDialog.confirm("确认删除吗？", "提示信息", function(rtn) {
		if (rtn) {
			$.getJSON("/makshi/selfcheck/monthmain/del.ht", {
				id : id
			}, function(data) {
				window.location.href = window.location.href;
			});
		} else {

		}
	});
}
function submit(id) {
	$.ligerDialog.confirm("确认提交吗？", "提示信息", function(rtn) {
		if (rtn) {
			$.getJSON("/makshi/selfcheck/monthmain/submit.ht", {
				id : id
			}, function(data) {
				window.location.href = window.location.href;
			});
		} else {

		}
	});
}