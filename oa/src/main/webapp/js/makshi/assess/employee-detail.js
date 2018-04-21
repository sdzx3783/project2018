$(function() {
	colCountShow();
	totalCountShow();
});

function colCountShow() {
	var rolTypes = [ 'zy', 'td', 'nl', 'xg' ];
	for (var x = 1; x <= 4; x++) {
		$.each(rolTypes, function(j, n) {
			var roltotal = 0;
			$("." + n + x).each(
					function(iq, wq) {
						var temp = $(this).text();
						if ($.trim(temp) != '') {
							if (iq == 0 || iq == 1)
								roltotal += parseFloat((parseFloat(temp) * 0.4)
										.toFixed(1));
							else if (iq == 2)
								roltotal += parseFloat((parseFloat(temp) * 0.2)
										.toFixed(1));
						}
					});
			$("#" + n + "total" + x).html(parseFloat(roltotal).toFixed(1));
		});
	}
}

function totalCountShow() {
	var total = 0;
	var selftotal = $(".selftotal").text();
	var departtotal = $(".departtotal").text();
	var leadertotal = $(".leadertotal").text();
	if ($.trim(selftotal) != '')
		total += parseFloat(selftotal);
	if ($.trim(departtotal) != '')
		total += parseFloat(departtotal);
	if ($.trim(leadertotal) != '')
		total += parseFloat(leadertotal);
	$("#alltotal").html(parseFloat(total).toFixed(1));
}
