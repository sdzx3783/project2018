function alertErrorMsg(msg) {
	alertMsg("错误", msg);
}

function alertTipMsg(msg) {
	alertMsg("提示", msg);
}

function alertTipMsg2(title, msg) {
	alertMsg(title, msg);
}

function showLoading() {
	closeAll();
	var d = dialog({
		cancel : false,
		content : '<img src="js/artDialog/loading.gif" />',
		fixed : true
	});
	d.title('正在拼命处理中...');
	d.showModal();
}

function closeLoading() {
	var list = dialog.list;
	for ( var i in list)
		list[i].close().remove();
}

function closeAll() {
	var list = dialog.list;
	for ( var i in list)
		list[i].close().remove();
}
