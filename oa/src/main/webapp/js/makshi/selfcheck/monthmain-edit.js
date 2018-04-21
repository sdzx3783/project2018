$(function() {
	$(document).on("click", ".monthdel", function() {
		$(this).parent().parent().remove();
	});
	$(document).on("click", ".monthadd", function() {
		$("#tb_common_show").append(addHtml());
	});

	$("#dataFormSave").click(
			function() {
				if ($("#submitAt").val() == '') {
					$("#submitAt").addClass("montherror");
				} else {
					$("#submitAt").removeClass("montherror");
				}

				$(".monthcontent").each(function() {
					if ($(this).val() == '') {
						$(this).addClass("montherror");
					} else {
						$(this).removeClass("montherror");
					}
				});
				$(".monthrequire").each(function() {
					if ($(this).val() == '') {
						$(this).addClass("montherror");
					} else {
						$(this).removeClass("montherror");
					}
				});
				$(".monthchecks").each(function() {
					if ($(this).val() == '') {
						$(this).addClass("montherror");
					} else {
						$(this).removeClass("montherror");
					}
				});
				if ($(".montherror").length > 0) {
					$.ligerDialog.error("请输入必填项", "提示信息");
					return;
				}

				var lines = '';
				$(".monthtr").each(
						function() {
							var line = "{\"content\":\"" + $(this).find(".monthcontent").val() + "\",\"require\":\"" + $(this).find(".monthrequire").val() + "\",\"checks\":\""
									+ $(this).find(".monthchecks").val() + "\"}"
							lines += "," + JSON.stringify(line);
						});
				if (lines != '') {
					lines = "[" + lines.substring(1) + "]";
				}
				$.post("/makshi/selfcheck/monthmain/save.ht", {
					id : $("#mainid").val(),
					submitAt : $("#submitAt").val(),
					items : lines
				}, function(data) {
					if (data.result) {
						$.ligerDialog.confirm("操作成功,是否继续操作","提示信息", function(rtn) {
							if(rtn){
								window.location.href = window.location.href;
							}else{
								window.location.href = "/makshi/selfcheck/monthmain/main.ht";
							}
						});
					} else {
						$.ligerDialog.error(data.msg, "提示信息");
					}
				}, "json");
			});
});

function addHtml() {
	return '<tr class="monthtr">' + '<td><a class="oa-button oa-button--primary oa-button--blue td-button monthdel" href="javascript:;">删除</a></td>'
			+ '<td><textarea style="width: 98%;"  class="monthcontent" rows="3"></textarea></td>'
			+ '<td><textarea style="width: 98%;"  class="monthrequire" rows="3"></textarea></td>'
			+ '<td><textarea style="width: 98%;"  class="monthchecks" rows="3"></textarea></td>' + '</tr>';
}