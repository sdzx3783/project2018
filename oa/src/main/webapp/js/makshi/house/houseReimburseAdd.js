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

		assembleRecords();
		
		changeReimburseAt();
		
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

	$("input[name='m:house_reimburse:house_id']").change(function() {
		var houseId = $("input[name='m:house_reimburse:house_id']").val();
		if (houseId == "") {
			return false;
		}
		var alias = "house_rent_info";
		$.ajax({
			type : "POST",
			url : "/platform/bpm/bpmFormQuery/doQuery.ht",
			data : {
				alias : alias,
				querydata : "{F_house_id:\"" + houseId + "\"}",
				page : 1,
				pagesize : 10
			},
			dataType : "json",
			success : function(data) {
				if (data != null && data.list != null && data.list.length > 0) {
					var rowData = data.list[0];
					$("input[name='m:house_reimburse:department']").val(rowData.f_department);
					$("#department_field").html(rowData.f_department);

					$("input[name='m:house_reimburse:rent_person']").val(rowData.f_rent_person);
					$("#rent_person_field").html(rowData.f_rent_person);

					$("input[name='m:house_reimburse:address']").val(rowData.f_address);
					$("#address_field").html(rowData.f_address);

					$("input[name='m:house_reimburse:start_date']").val(getdate(isNull(rowData.f_start_date)));
					if (rowData.f_start_date != '')
						$("#start_date_field").html(rowData.f_start_date.substring(0, 10));

					$("input[name='m:house_reimburse:end_date']").val(getdate(isNull(rowData.f_end_date)));
					if (rowData.f_end_date != '')
						$("#end_date_field").html(rowData.f_end_date.substring(0, 10));

					$("input[name='m:house_reimburse:rent_money']").val(rowData.f_money);
					$("#rent_money_field").html(rowData.f_money);

					$("input[name='m:house_reimburse:reimburse_date']").val(getNow());

					$("select[name='m:house_reimburse:house_type']").val(rowData.f_house_type);
					$("#house_type_field").html(rowData.f_house_type + "房");
					$("#inpersons").val(rowData.f_number_people);
					getReimburseInfo(houseId);
				} else {
					alert("租房编号不正确，请重新输入!");
					$("input[name='m:house_reimburse:house_id']").val("");
				}
			},
			error : function() {
				alert("查询错误！");
			}
		});
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
	$("#recordFields").append(record());
}

function record(obj) {
	var moneys = '', reimburse_at = '', person = '';
	if (obj) {
		person = obj.person;
		moneys = obj.moneys;
		reimburse_at = obj.reimburse_at;
	}
	return '<tr class="reimburseRecord">'
			+ '<td><a class="oa-button oa-button--primary oa-button--blue recorddel" href="javascript:;">删除</a></td>'
			+ '<td><input name="reimburse_at" class="reimburse_at oa-new-input Wdate" onclick="WdatePicker({dateFmt:\'yyyy-MM-dd\',onpicked:function(){changeReimburseAt();}})"  value="'
			+ reimburse_at + '"></td>' + '<td><input name="moneys" class="moneys oa-new-input" value="' + moneys + '"></td>'
			+ '<td><input name="person" class="person oa-new-input" value="' + person + '"></td>' + '</tr>';
}

function getReimburseInfo(houseId) {
	$.post("/makshi/housereimburse/houseReimburse/getInfo.ht", {
		houseId : houseId
	}, function(result) {
		if (null != result && result.state == 1) {
			var rowData = result.houseReimburse;
			var start = getdate(isNull(rowData.pay_start_date));
			var end = getdate(isNull(rowData.pay_end_date));
			$("input[name='m:house_reimburse:reimburse_person']").val(rowData.reimburse_person);
			$("input[name='m:house_reimburse:pay_start_date']").val(getdate(isNull(rowData.pay_start_date)));
			$("input[name='m:house_reimburse:pay_end_date']").val(getdate(isNull(rowData.pay_end_date)));
			$("input[name='m:house_reimburse:reimburse_money']").val(rowData.reimburse_money);
			$("input[name='id']").val(rowData.id);
			
			$("#reimburse_money_show").html(rowData.reimburse_money);
			
			$("#feePerdio").empty().html(start.substring(0, 10) + " -- " + end.substring(0, 10));

			$.getJSON('/makshi/housereimburse/houseReimburse/records.ht', {
				reimburseId : rowData.id
			}, function(data) {
				$("#recordFields").empty().append('<tr id=""><th></th><th>报销日期</th><th>报销金额(元)</th><th>报销人</th></tr>');
				if (data.result) {
					$.each(data.items, function(i, item) {
						$("#recordFields").append(record(item));
					});
				}
			});
		}
	});
}

function isNull(obj) {
	var dateType = typeof (obj);
	if ("number" == dateType) {
		obj = new Date(obj);
		if (obj) {
			return obj;
		}
	}
	if (!obj || obj.length == 0) {
		return '';
	}
	return new Date(obj.replace(/-/g, "/"));
};
function getdate(date) {
	if (!date || date == null || date.length == 0) {
		return '';
	}
	var y = date.getFullYear();
	var m = date.getMonth() + 1;
	m = m < 10 ? '0' + m : m;
	var d = date.getDate();
	d = d < 10 ? ('0' + d) : d;
	return y + '-' + m + '-' + d;
}

function getNow() {
	var date = new Date();
	return getdate(date);
}