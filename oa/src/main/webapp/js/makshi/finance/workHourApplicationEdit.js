var frm;
var hasSubmit=false;//防止二次提交
var hasSubmitSuccess=false;//已提交成功 防止再次操作保存按钮和提交按钮
$(function() {
	calWorkhourRate();
	if ($("#projectTaskHourListSize").val() == '0') {
		changeTime();
	}
	$.getJSON("/makshi/doc/docinfo/isExistByNum.ht", {
		filenum : '50000000000533'
	}, function(data) {
		if (data.result == 1) {
			$("#filehelp").css({
				"display" : ""
			});
		}
	});

	$("a.save").click(function() {
		$("#optype").val(0);
		saveOrUpdate();
	});
	$("a.submit").click(function() {
		// var id=$("input[name='id']").val();
		$("#optype").val(1);
		if(hasSubmitSuccess){
			$.ligerDialog.error("该考勤填报已提交，不能再次修改和提交！", "温馨提示");
			return ;
		}
		if(hasSubmit){
			return ;
		}
		saveOrUpdate();
	});
	
	$(document).on("click",".l-dialog-close",function(){
		frm.handleFieldNameReduction("m:work_hour_application");
		frm.sortListReduction();
	});
	$(document).on("click",".del",function(){
		$(this).parents("tr").remove();
	})
});

function saveOrUpdate() {
	if(hasSubmitSuccess){
		$.ligerDialog.error("该考勤填报已提交，不能再次修改和提交！", "温馨提示");
		return ;
	}
	frm = $('#workHourApplicationForm').form();
	var options = {};
	if (showResponse) {
		options.success = showResponse;
	}
	frm.ajaxForm(options);
	var wh = $("input[name='m:work_hour_application:work_hour_rate']").val();
	if ($.trim(wh) == '' || $.trim(wh) == 0 || $.trim(wh) == undefined) {
		$.ligerDialog.error("请添加工作时间比例！", "温馨提示");
		return;
	}
	$("#saveData").val(1);
	if (frm.valid()) {
		// 校验累计正常工时
		
		calWorkhour();
		calOvertimehour();
		var d = $("#applicantTime").val();
		if (d.length <= 0) {
			$.ligerDialog.error("请输入日期！", "温馨提示");
			return;
		}
		var date = new Date(Date.parse(d.replace(/-/g, '/')));
		var day = date.getDay();
		var hour_total = $("input[name='m:work_hour_application:work_hour_total']").val();
		/*
		 * if(day>=1 &&day<=5){ if(hour_total>7){
		 * $.ligerDialog.error("周一至周五正常工时不能大于7个小时！","温馨提示"); return ; } }
		 * if(day==6){ if(hour_total>3){
		 * $.ligerDialog.error("周六正常工时不能大于3个小时！","温馨提示"); return ; } }
		 */
		// 如果有编辑器的情况下
		$("textarea[name^='m:'].myeditor").each(function(num) {
			var name = $(this).attr("name");
			var data = getEditorData(editor[num]);
			$("textarea[name^='" + name + "']").val(data);
		});

		if (WebSignPlugin.hasWebSignField) {
			WebSignPlugin.submit();
		}
		if (OfficePlugin.officeObjs.length > 0) {
			OfficePlugin.submit(function() {
				frm.handleFieldName();
				frm.sortList();
				$('#workHourApplicationForm').submit();
			});
		} else {
			frm.handleFieldName();
			frm.sortList();
			$('#workHourApplicationForm').submit();
		}
	}
}
function submitWorkHour(id) {
	var url = "/makshi/finance/workHourApplication/runStart.ht";
	$.post(url, {
		"id" : id
	}, function(responseText) {
		var obj = new com.hotent.form.ResultMessage(responseText);
		if (obj.isSuccess()) {
			hasSubmitSuccess=true;
			$.ligerDialog.success('<p><font color="green">' + obj.getMessage() + '</font></p>', "提示信息", function() {
				window.location.href = "/makshi/finance/workHourApplication/list.ht";
			});
		} else {
			$.ligerDialog.error(obj.getMessage(), "提示信息", function() {
				// window.location.reload();
				frm.handleFieldNameReduction("m:work_hour_application");
				frm.sortListReduction();
				hasSubmit=false;
			});
		}
	});
}
function showResponse(responseText) {
	var obj = new com.hotent.form.ResultMessage(responseText);
	if (obj.isSuccess()) {
		var optype = $("#optype").val();
		if (optype == 1) {// 提交操作
			if(hasSubmit){
				return ;
			}
			hasSubmit=true;
			submitWorkHour(obj.getMessage());
		} else {
			$.ligerDialog.success("保存成功", "提示", function() {
				window.location.href = "/makshi/finance/workHourApplication/edit.ht?id=" + obj.getMessage();
			});
		}
	} else {
		$.ligerDialog.error(obj.getMessage(), "提示信息", function() {
			// window.location.reload();
			frm.handleFieldNameReduction("m:work_hour_application");
			frm.sortListReduction();
		});
	}
}
function changeTime() {
	var aptime = $("#applicantTime").val();
	var url = "/makshi/project/project/getProListData.ht?select_time=" + aptime + "&t=" + new Date().getTime();
	var temp = $("select[name='m:work_hour_application:basehour'] option:selected").val();
	var isSat = isSaturday();
	if (temp == '') {
		if (isSat) {
			temp = 3;
		} else {
			temp = 7;
		}
	} else {
		if (isSat) {
			temp = 3;
		}
	}
	if ($("#isHjsyb").val() == 'true') {
		if (isSat) {
			// 周六 环境事业部有4小时制选项
			$("select[name='m:work_hour_application:basehour']").empty();
			$("select[name='m:work_hour_application:basehour']")
					.append(
							"<option value='3'>3小时制</option><option value='7'>7小时制</option><option value='8'>8小时倒班制（环境事业部）</option><option value='12'>12小时倒班制（环境事业部）</option>");
		} else {
			// 不是周六 环境事业部不能有3小时制选项
			$("select[name='m:work_hour_application:basehour']").empty();
			$("select[name='m:work_hour_application:basehour']").append(
					"<option value='7'>7小时制</option><option value='8'>8小时倒班制（环境事业部）</option><option value='12'>12小时倒班制（环境事业部）</option>");
		}
	} else {
		if (isSat) {
			// 周六 非环境事业部默认3
			$("select[name='m:work_hour_application:basehour']").empty();
			$("select[name='m:work_hour_application:basehour']").append("<option value='3'>3小时制</option>");
		} else {
			// 不是周六 非环境事业部默认7
			$("select[name='m:work_hour_application:basehour']").empty();
			$("select[name='m:work_hour_application:basehour']").append("<option value='7'>7小时制</option>");
		}
	}
	$("select[name='m:work_hour_application:basehour'] option[value='" + temp + "']").attr("selected", "selected");
	$.get(url, function(data) {
		$("#projectTaskHour").find("tr.listRow[type='subdata']").remove();
		$("input[name='m:work_hour_application:work_hour_total']").val(0);
		if (data && data.length > 0) {
			for ( var obj in data) {
				$("#addtaskbtn").trigger("click");
				var $tr = $("#projectTaskHour").find("tr.listRow[type='subdata']:last");
				filldata(data[obj], $tr);
			}
		}
	});

}
function isSaturday() {
	var f = false;
	var d = $("#applicantTime").val();
	if (d.length <= 0) {
		return false;
	}
	var date = new Date(Date.parse(d.replace(/-/g, '/')));
	var day = date.getDay();
	if (day == 6) {
		f = true;
	}
	return f;
}
function filldata(obj, tr) {
	$(".projectTaskHourProjectNameField", tr).html(obj.name);
	$("input[name='s:projectTaskHour:projectName']", tr).val(obj.name);
	$("input[name='s:projectTaskHour:proid']", tr).val(obj.id);
	/*
	 * $("input[name='s:projectTaskHour:taskName']",tr).val(obj.taskname);
	 * $("input[name='s:projectTaskHour:taskid']",tr).val(obj.id);
	 */
}
function calWorkhour() {
	var $protrs = $("#projectTaskHour").find("tr.listRow[type='subdata'] input[name='s:projectTaskHour:work_hour']");
	var $custrs = $("#customTaskHour").find("tr.listRow[type='subdata'] input[name='s:customTaskHour:work_hour']");
	var workhourtotal = parseFloat(0);
	if ($protrs && $protrs.length > 0) {
		$protrs.each(function(index, item) {
			var d = parseFloat($(item).val());
			if (d) {
				workhourtotal = workhourtotal + d;
			}
		});
	}
	if ($custrs && $custrs.length > 0) {
		$custrs.each(function(index, item) {
			var d = parseFloat($(item).val());
			if (d) {
				workhourtotal = workhourtotal + d;
			}
		});
	}
	$("input[name='m:work_hour_application:work_hour_total']").val(workhourtotal);
}

function calWorkhourRate() {
	var $protrs = $("#projectTaskHour").find("tr.listRow[type='subdata'] input[name='s:projectTaskHour:project_work_rate']");
	var $custrs = $("#customTaskHour").find("tr.listRow[type='subdata'] input[name='s:customTaskHour:task_work_rate']");
	var workhourtotal = parseInt(0);
	if ($protrs && $protrs.length > 0) {
		$protrs.each(function(index, item) {
			var d = parseInt($(item).val()) || 0;
			if (d) {
				workhourtotal = workhourtotal + d;
			}
		});
	}
	if ($custrs && $custrs.length > 0) {
		$custrs.each(function(index, item) {
			var d = parseInt($(item).val()) || 0;
			if (d) {
				workhourtotal = workhourtotal + d;
			}
		});
	}
	$("input[name='m:work_hour_application:work_hour_rate']").val(workhourtotal);
}

function calOvertimehour() {
	var $protrs = $("#projectTaskHour").find("tr.listRow[type='subdata'] input[name='s:projectTaskHour:overtime_hour']");
	var $custrs = $("#customTaskHour").find("tr.listRow[type='subdata'] input[name='s:customTaskHour:overtime_hour']");
	var overtimehourtotal = parseFloat(0);
	if ($protrs && $protrs.length > 0) {
		$protrs.each(function(index, item) {
			var d = parseFloat($(item).val());
			if (d) {
				overtimehourtotal = overtimehourtotal + d;
			}
		});
	}
	if ($custrs && $custrs.length > 0) {
		$custrs.each(function(index, item) {
			var d = parseFloat($(item).val());
			if (d) {
				overtimehourtotal = overtimehourtotal + d;
			}
		});
	}
	$("input[name='m:work_hour_application:overtime_hour_total']").val(overtimehourtotal);
}
