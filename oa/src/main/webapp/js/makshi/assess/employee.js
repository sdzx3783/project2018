$(function() {
	loadInitPageData();
});

function loadInitPageData() {
	$.getJSON("/makshi/assess/employee/total.ht", {
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
	$.post("/makshi/assess/employee/page.ht", queryData, function(data) {
		$("#result_field").html("");
		$("#result_field").append(data);
	});
}

function del(id) {
	$.ligerDialog.confirm("确认删除吗？", "提示信息", function(rtn) {
		if (rtn) {
			$.getJSON("/makshi/assess/employee/del.ht", {
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
			// $.getJSON("/makshi/assess/employee/submit.ht", {
			// id : id
			// }, function(data) {
			// window.location.href = window.location.href;
			// });

			var url = "/makshi/assess/employee/submit.ht";
			$.post(url, {
				id : id
			}, function(responseText) {
				// console.info(responseText);
				var obj = new com.hotent.form.ResultMessage(responseText);
				if (obj.isSuccess()) {
					$.ligerDialog.success('<p><font color="green">' + obj.getMessage() + '</font></p>');
					setTimeout("window.location.reload(true);", 2000);
				} else {
					$.ligerDialog.error(obj.getMessage(), "提示信息");
				}
			});

		} else {

		}
	});
}