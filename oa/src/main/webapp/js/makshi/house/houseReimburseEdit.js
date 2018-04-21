$(function() {
	var options = {};
	if (showResponse) {
		options.success = showResponse;
	}
	var frm = $('#houseReimburseForm').form();
	$("a.save").click(function() {
		
		$(".moneys").each(function() {
			if ($(this).val() == '') {
				$(this).addClass("montherror");
			} else {
				$(this).removeClass("montherror");
			}
		});
		$(".reimburse_at").each(function() {
			if ($(this).val() == '') {
				$(this).addClass("montherror");
			} else {
				$(this).removeClass("montherror");
			}
		});
		
		$(".person").each(function() {
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
		
		changeReimburseAt();
		
		assembleRecords();
		frm.ajaxForm(options);
		$("#saveData").val(1);
		if (frm.valid()) {
			if (WebSignPlugin.hasWebSignField) {
				WebSignPlugin.submit();
			}
			if (OfficePlugin.officeObjs.length > 0) {
				OfficePlugin.submit(function() {
					frm.handleFieldName();
					$('#houseReimburseForm').submit();
				});
			} else {
				frm.handleFieldName();
				$('#houseReimburseForm').submit();
			}
		}
	});

	$(document).on("click", ".recorddel", function() {
		$(this).parent().parent().remove();
		changeReimburseAt();
		initTotalMoney();
	});

	$(document).on("input", ".moneys", function() {
		var money = $(this).val();
		var r = /^\d+(\.\d{1,2})?$/;
		if (!r.test(money)) {
			$(this).addClass("montherror");
			if ($(this).next("span").length == 0)
				$(this).after("<span style='color:red;'>请输入正确的报销金额</span>");
			else {

			}
			return;
		} else {
			$(this).next("span").remove();
			$(this).removeClass("montherror");
		}

		initTotalMoney();
	});
});

function initTotalMoney(){
	var totalMoney = 0;
	$(".moneys").each(function() {
		totalMoney += parseFloat($(this).val());
	});
	$("#reimburse_money_show").html(totalMoney.toFixed(2));
	$("input[name='m:house_reimburse:reimburse_money']").val(totalMoney.toFixed(2));
}

function changeReimburseAt() {
	var arr = [];
	$(".reimburse_at").each(function() {
		if ($(this).val() == '') {
			$(this).addClass("montherror");
		} else {
			$(this).removeClass("montherror");
			arr.push($(this).val());
			
			$(this).attr("id",$(this).val());
		}
	});

	if (arr.length > 0) {
		arr.sort();
		var minDateStr = arr[0];
		var regEx = new RegExp("\\-","gi"); 
		minDateStr = minDateStr.replace(regEx,"/"); 
		var minDate = new Date(minDateStr);
		minDate.setMonth(minDate.getMonth()-1);
		minDateStr = minDate.Format("yyyy-MM-dd");
		 
		$("#feePerdio").empty().html(minDateStr + " -- " + arr[arr.length - 1]);
		$("#start_date").val(minDateStr);
		$("#end_date").val(arr[arr.length - 1]);
		//取最大的报销日期为主报销日期
		$("#reimburse_date").val(arr[arr.length - 1]);
		//取最大的报销人为主报销人
		$("#reimburse_person").val($("#"+ arr[arr.length - 1]).parent().parent().find(".person").val());
	}
}

function showResponse(responseText) {
	var obj = new com.hotent.form.ResultMessage(responseText);
	if (obj.isSuccess()) {
		$.ligerDialog.confirm(obj.getMessage() + ",是否继续操作", "提示信息", function(rtn) {
			if (rtn) {
				window.location.href = window.location.href;
			} else {
				window.location.href = "/makshi/housereimburse/houseReimburse/list.ht";
			}
		});
	} else {
		$.ligerDialog.error(obj.getMessage(), "提示信息");
	}
}

function assembleRecords() {
	var lines = '';
	$(".reimburseRecord").each(
			function() {
				var id = $.trim($(this).find(".id").val());
				var line = "{\"id\":\"" + id + "\",\"reimburse_at\":\"" + $(this).find(".reimburse_at").val() + "\",\"moneys\":\""
						+ $(this).find(".moneys").val() + "\",\"person\":\"" + $(this).find(".person").val() + "\"}"
				lines += "," + JSON.stringify(line);
			});
	if (lines != '') {
		lines = "[" + lines.substring(1) + "]";
	}
	$("#items").val(lines);
	return lines;
}

function addRecord() {
	$("#recordNo").remove();
	$("#recordFields").after(record());
}

function record() {
	return '<tr class="reimburseRecord">'
			+ '<td><a class="oa-button oa-button--primary oa-button--blue recorddel" href="javascript:;">删除</a></td>'
			+ '<td><input name="reimburse_at" class="reimburse_at oa-new-input Wdate" onclick="WdatePicker({dateFmt:\'yyyy-MM-dd\',onpicked:function(){changeReimburseAt();}})"  value=""></td>'
			+ '<td><input name="moneys" class="moneys oa-new-input" value=""></td>'
			+ '<td><input name="person" class="person oa-new-input" value=""></td>' + '</tr>';
}
