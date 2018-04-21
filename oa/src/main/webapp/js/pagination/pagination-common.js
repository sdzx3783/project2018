// 初始化分页组件

/**
 * fn 分页回调函数
 */
function initCustomerPagination(totalItemsSize, pagination, fn, fb) {
	// var totalItemsSize = Number($("#totalItemsSize").val());
	var eachPageSize = Number($(".eachPageSize").val());
	if ($.trim(eachPageSize) == 'NaN' || $.trim(eachPageSize) == '' || $.trim(eachPageSize) == '0')
		eachPageSize = 10;

	var prePage = $("#page").val();
	if (prePage == null) {
		prePage = "1";
	}
	var current_page_val = 0;
	if (prePage > 1) {
		current_page_val = Number(prePage) - 1;
	}
	var totalPageVal = parseInt(totalItemsSize / eachPageSize);
	if ((totalItemsSize % eachPageSize) > 0) {
		totalPageVal = totalPageVal + 1;
	}
	if (current_page_val >= totalPageVal) {
		current_page_val = totalPageVal - 1;
	}
//	if (current_page_val < 0)
//		current_page_val = 0;
	// if (totalItemsSize > 0 && totalItemsSize > eachPageSize) {
	// $(".pagination")
	pagination.pagination(totalItemsSize, {
		num_edge_entries : 1,
		num_display_entries : 6,
		callbackMain : fb,
		callback : fn,
		items_per_page : eachPageSize,
		current_page : current_page_val,
		showtext : 1,
		link_to : "javascript:;",
		prev_text : "",
		next_text : ""
	});
	// } else {
	// fn(0);
	// pagination.empty();
	// }
}

// 初始化分页组件
function initPagination() {
	var num_entries = Number($("#totalItemsSize").val());
	var eachPageSize = Number($("#eachPageSize").val());
	var eachPageShow = Number($("#eachPageShow").val());
	var prePage = $("#page").val();
	if (prePage == null) {
		prePage = "1";
	}
	var current_page_val = 0;
	if (prePage > 1) {
		current_page_val = Number(prePage) - 1;
	}
	var totalPageVal = parseInt(num_entries / eachPageSize);
	if ((num_entries % eachPageSize) > 0) {
		totalPageVal = totalPageVal + 1;
	}
	if (current_page_val >= totalPageVal) {
		current_page_val = totalPageVal - 1;
	}
	if (num_entries > 0 && num_entries > eachPageSize) {
		$(".pagination").pagination(num_entries, {
			num_edge_entries : 1,
			num_display_entries : 6,
			callback : pageselectCallback,
			items_per_page : eachPageSize,
			current_page : current_page_val,
			showtext : eachPageShow,
			prev_text : "上一页",
			next_text : "下一页"
		});
	} else {
		pageselectCallback(0, "Pagination");
	}
}

// 分页回调函数
function pageselectCallback(page_index, jq) {
	loadEachPageItemInfo(page_index + 1);
	return false;
}
