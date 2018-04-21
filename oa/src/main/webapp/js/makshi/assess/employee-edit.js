$(function() {
	rolCount();
	var colTypes = [ "self", "depart", "leader" ];
	var rolTypes = [ 'zy', 'td', 'nl', 'xg' ];
	$.each(colTypes, function(i, m) {
		$.each(rolTypes, function(j, n) {
			for ( var x = 1; x <= 4; x++) {
				$(document).on("input", "input[name='m:employee_assess:self_" + n + "_" + m + x + "']", function() {
					rolCount();
				});
			}
		});
	});

	var options = {};
	if (showResponse) {
		options.success = showResponse;
	}
	var frm = $('#employeeForm').form();
	$("a.save").click(function() {
		frm.ajaxForm(options);
		$("#saveData").val(1);
		if (frm.valid()) {
			if (WebSignPlugin.hasWebSignField) {
				WebSignPlugin.submit();
			}
			if (OfficePlugin.officeObjs.length > 0) {
				OfficePlugin.submit(function() {
					frm.handleFieldName();
					$('#employeeForm').submit();
				});
			} else {
				frm.handleFieldName();
				$('#employeeForm').submit();
			}
		}
	});
});
function rolCount() {
	var colTypes = [ "self", "depart", "leader" ];
	var rolTypes = [ 'zy', 'td', 'nl', 'xg' ];
	$.each(colTypes, function(i, m) {
		var roltotal = 0;
		$.each(rolTypes, function(j, n) {
			for ( var x = 1; x <= 4; x++) {
				var temp = $("input[name='m:employee_assess:self_" + n + "_" + m + x + "']").val();
				if ($.trim(temp) != '')
					roltotal += parseInt(temp);
			}
		});
		if (m == 'self' || m == 'depart')
			$("#" + m + "total").val((parseInt(roltotal) * 0.4).toFixed(2));
		else if (m == 'leader') {
			$("#" + m + "total").val((parseInt(roltotal) * 0.2).toFixed(2));
		}
	});
}

function showResponse(responseText) {
	var obj = new com.hotent.form.ResultMessage(responseText);
	if (obj.isSuccess()) {
		$.ligerDialog.confirm(obj.getMessage() + ",是否继续操作", "提示信息", function(rtn) {
			if (rtn) {
				window.location.href = window.location.href;
			} else {
				window.location.href = "/makshi/assess/employee/main.ht";
			}
		});
	} else {
		$.ligerDialog.error(obj.getMessage(), "提示信息");
	}
}