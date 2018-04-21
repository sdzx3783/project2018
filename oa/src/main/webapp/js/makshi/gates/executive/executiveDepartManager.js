$(function() {
	var tab = $.trim($("#executivetab").val());

	if (tab != '') {
		var tabs = tab.split('_');
		$(".statics_title").removeClass("active");
		$("[data-id='" + tabs[0] + "']").addClass("active");
		$(".peoplechart").removeClass("active");
		$(".contractchart").removeClass("active");
		$("[data-id='" + tabs[1] + "']").addClass("active");

		$(".my-second-nav").addClass("hidden");
		$(".statics_content_" + tabs[0]).removeClass("hidden");

		showData(tabs[1]);
	} else {
		people();
	}

	$(".peoplechart").on("click", function() {
		$(".peoplechart").removeClass("active");
		$(this).addClass("active");
	});

	$(".contractchart").on("click", function() {
		$(".contractchart").removeClass("active");
		$(this).addClass("active");
	});

	$(".statics_title").on("click", function() {
		$(".statics_title").removeClass("active");
		$(this).addClass("active");
		$(".my-second-nav").addClass("hidden");
		var id = $(this).data("id");
		$(".statics_content_" + id).removeClass("hidden");
		if (id == 'people') {
			if ($.trim($("#containerPeopleList").text()) != '')
				return;
			people();
		} else if (id == 'contract') {
			if ($.trim($("#containerContractList").text()) != '')
				return;
			$(".contractchart").removeClass("active");
			$(".contractchart").first().addClass("active");
			containerContract();
		} else if (id == 'asset') {
			if ($.trim($("#containerAssetsList").text()) != '')
				return;
			containerAsset();
		} else if (id == 'Qualifications') {
			certificate();
		}
	});

	// containerContract();

	// containerAsset();

	// certificate();

});

function showData(id) {
	if (id == 'people') {
		people();
	} else if (id == 'age') {
		peopleAges();
	} else if (id == 'education') {
		peopleEducation();
	} else if (id == 'professional') {
		peopleProfessional();
	} else if (id == 'contract') {
		containerContract();
	} else if (id == 'asset') {
		containerAsset();
	} else if (id == 'Qualifications') {
		certificate();
	} else if (id == 'billing') {
		containerContractBilling();
	}
}

function people() {
	$.getJSON("/makshi/gates/executive/depart/people.ht", {
		orgId : $("#executiveOrgId").val()
	}, function(data) {
		if (!data.result)
			return;
		var pageviews = [];
		var title = data.title;
		$.each(data.items, function(i, item) {
			pageviews.push([ item.name, parseFloat(item.count) ]);
		});
		$("#containerPeopleList").html(data.table);
		$('#container').highcharts({
			chart : {
				plotBackgroundColor : null,
				plotBorderWidth : null,
				plotShadow : false
			},
			legend : {
				layout : 'vertical',
				align : 'right',
				verticalAlign : 'middle',
			},
			credits : {
				enabled : false
			},
			title : {
				text : title + '人数分布情况'
			},
			tooltip : {
				headerFormat : '{series.name}<br>',
				pointFormat : '{point.name}: <b>{point.percentage:.1f}%</b>'
			},
			plotOptions : {
				pie : {
					allowPointSelect : false,
					cursor : 'pointer',
					dataLabels : {
						enabled : true,
						format : '<b>{point.y}</b>人,<br>占{point.percentage:.1f} %',
						style : {
							color : (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
						}
					},
					showInLegend : true,
					point : {
						events : {
							legendItemClick : function() {
								return false;
							}
						}
					},
				}
			},
			series : [ {
				type : 'pie',
				name : title + '人数占比',
				data : pageviews
			} ]
		});
	});
}

function peopleAges() {
	$.getJSON("/makshi/gates/executive/depart/peopleAges.ht", {
		orgId : $("#executiveOrgId").val()
	}, function(data) {
		if (!data.result)
			return;
		var pageviews = [];
		var title = data.title;
		$.each(data.items, function(i, item) {
			pageviews.push([ item.name, parseFloat(item.count) ]);
		});
		$("#containerPeopleList").html(data.table);
		$('#container').highcharts({
			chart : {
				plotBackgroundColor : null,
				plotBorderWidth : null,
				plotShadow : false
			},
			legend : {
				layout : 'vertical',
				align : 'right',
				verticalAlign : 'middle',
			},
			credits : {
				enabled : false
			},
			title : {
				text : title + '年龄分布'
			},
			tooltip : {
				headerFormat : '{series.name}<br>',
				pointFormat : '{point.name}: <b>{point.percentage:.1f}%</b>'
			},
			plotOptions : {
				pie : {
					allowPointSelect : false,
					cursor : 'pointer',
					dataLabels : {
						enabled : true,
						format : '{point.name}：<b>{point.y}</b>人,<br>占{point.percentage:.1f} %',
						style : {
							color : (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
						}
					},
					showInLegend : true,
					point : {
						events : {
							legendItemClick : function() {
								return false;
							}
						}
					},
				}
			},
			series : [ {
				type : 'pie',
				name : title + '年龄分布占比',
				data : pageviews
			} ]
		});
	});
}

function peopleBetAges() {
	var agesMin = $.trim($("#agesMin").val());
	var agesMax = $.trim($("#agesMax").val());
	if (agesMin == '' && agesMax == '') {
		peopleAges();
		return;
	}
	if ((agesMin != '' && (parseInt(agesMin) + '') == 'NaN') || (agesMax != '' && (parseInt(agesMax) + '') == 'NaN')) {
		$("#peopleBetAgesQuery").html("请输入需要查询的年龄范围");
		return;
	}
	if (parseInt(agesMin) > parseInt(agesMax)) {
		$("#peopleBetAgesQuery").html("请输入正确的年龄范围");
		return;
	}
	$("#peopleBetAgesQuery").html("");
	$.getJSON("/makshi/gates/executive/depart/peopleBetAges.ht", {
		orgId : $("#executiveOrgId").val(),
		agesMin : agesMin,
		agesMax : agesMax
	}, function(data) {
		if (!data.result)
			return;
		var pageviews = [];
		$.each(data.items, function(i, item) {
			pageviews.push([ item.name, parseFloat(item.count) ]);
		});
		$("#containerPeopleList").html(data.table);
		$('#container').highcharts({
			chart : {
				plotBackgroundColor : null,
				plotBorderWidth : null,
				plotShadow : false
			},
			legend : {
				layout : 'vertical',
				align : 'right',
				verticalAlign : 'middle',
			},
			credits : {
				enabled : false
			},
			title : {
				text : '年龄分布'
			},
			tooltip : {
				headerFormat : '{series.name}<br>',
				pointFormat : '{point.name}: <b>{point.percentage:.1f}%</b>'
			},
			plotOptions : {
				pie : {
					allowPointSelect : false,
					cursor : 'pointer',
					dataLabels : {
						enabled : true,
						format : '{point.name}：<b>{point.y}</b>人,<br>占{point.percentage:.1f} %',
						style : {
							color : (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
						}
					},
					showInLegend : true,
					point : {
						events : {
							legendItemClick : function() {
								return false;
							}
						}
					},
				}
			},
			series : [ {
				type : 'pie',
				name : '年龄分布占比',
				data : pageviews
			} ]
		});
	});
}

function peopleEducation() {
	$.getJSON("/makshi/gates/executive/depart/peopleEducation.ht", {
		orgId : $("#executiveOrgId").val()
	}, function(data) {
		if (!data.result)
			return;
		var pageviews = [];
		var title = data.title;
		$.each(data.items, function(i, item) {
			pageviews.push([ item.name, parseFloat(item.count) ]);
		});
		$("#containerPeopleList").html(data.table);
		$('#container').highcharts({
			chart : {
				plotBackgroundColor : null,
				plotBorderWidth : null,
				plotShadow : false
			},
			legend : {
				layout : 'vertical',
				align : 'right',
				verticalAlign : 'middle',
			},
			credits : {
				enabled : false
			},
			title : {
				text : title + '学历分布'
			},
			tooltip : {
				headerFormat : '{series.name}<br>',
				pointFormat : '{point.name}: <b>{point.percentage:.1f}%</b>'
			},
			plotOptions : {
				pie : {
					allowPointSelect : false,
					cursor : 'pointer',
					dataLabels : {
						enabled : true,
						format : '{point.name}：<b>{point.y}</b>人,<br>占 <b>{point.percentage:.1f}%</b>',
						style : {
							color : (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
						}
					},
					showInLegend : true,
					point : {
						events : {
							legendItemClick : function() {
								return false;
							}
						}
					},
				}
			},
			series : [ {
				type : 'pie',
				name : title + '学历分布占比',
				data : pageviews
			} ]
		});
	});
}
function peopleProfessional() {
	$.getJSON("/makshi/gates/executive/depart/peopleProfessional.ht", {
		orgId : $("#executiveOrgId").val()
	}, function(data) {
		if (!data.result)
			return;
		var pageviews = [];
		var title = data.title;
		$.each(data.items, function(i, item) {
			pageviews.push([ item.name, parseFloat(item.count) ]);
		});
		$("#containerPeopleList").html(data.table);
		$('#container').highcharts({
			chart : {
				plotBackgroundColor : null,
				plotBorderWidth : null,
				plotShadow : false
			},
			legend : {
				layout : 'vertical',
				align : 'right',
				verticalAlign : 'middle',
			},
			credits : {
				enabled : false
			},
			title : {
				text : title + '职称分布'
			},
			tooltip : {
				headerFormat : '{series.name}<br>',
				pointFormat : '{point.name}: <b>{point.percentage:.1f}%</b>'
			},
			plotOptions : {
				pie : {
					allowPointSelect : false,
					cursor : 'pointer',
					dataLabels : {
						enabled : true,
						format : '{point.name}：<b>{point.y}</b>人,<br>占 <b>{point.percentage:.1f} %</b>',
						style : {
							color : (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
						}
					},
					showInLegend : true,
					point : {
						events : {
							legendItemClick : function() {
								return false;
							}
						}
					},
				}
			},
			series : [ {
				type : 'pie',
				name : title + '职称分布占比',
				data : pageviews
			} ]
		});
	});
}

function containerContract() {
	$.getJSON("/makshi/gates/executive/depart/contract.ht", {
		orgId : $("#executiveOrgId").val()
	}, function(data) {
		if (!data.result)
			return;
		var title = data.title;
		var xaxis = [ data.time5 + "年", data.time4 + "年", data.time3 + "年", data.time2 + "年", data.time1 + "年" ];
		var pageviews = [];
		pageviews.push([ data.time5 + "年", data.timemoney5 ]);
		pageviews.push([ data.time4 + "年", data.timemoney4 ]);
		pageviews.push([ data.time3 + "年", data.timemoney3 ]);
		pageviews.push([ data.time2 + "年", data.timemoney2 ]);
		pageviews.push([ data.time1 + "年", data.timemoney1 ]);
		$('#containerContract').highcharts(
				{
					chart : {
						type : 'column'
					},
					legend : {
						layout : 'vertical',
						align : 'right',
						verticalAlign : 'middle',
					},
					credits : {
						enabled : false
					},
					title : {
						text : title + '合同签订统计（万元）'
					},
					xAxis : {
						categories : xaxis,
						crosshair : true
					},
					yAxis : {
						min : 0,
						title : {
							text : ''
						}
					},
					tooltip : {
						headerFormat : '',
						formatter : function() {
							var year = this.x.substring(0, 4);
							return '<a href="javascript:contractDetail(\'' + $("#executiveOrgId").val() + '\',\'' + year + '-01-01\',\'' + year + '-12-31\');" <span style="color:red;padding:0">'
							+ this.y + '万元</span></a>';
//							/makshi/gates/executive/detail/contract.ht?orgId=' + $("#executiveOrgId").val() + '&singingStart='
//									+ year + '-01-01&singingEnd=' + year + '-12-31" target="_blank"><span style="color:red;padding:0"> ' + this.y
//									+ '万元</span></a>';
						},
						footerFormat : '',
						shared : true,
						useHTML : true
					},
					plotOptions : {
						column : {
							pointPadding : 0.2,
							borderWidth : 0,
							cursor : 'pointer',
							events : {
								click : function(e) {
									// location.href =
									// e.point.url;
									// 上面是当前页跳转，如果是要跳出新页面，那就用
									var year = e.point.name.substring(0, 4);
									contractDetail($("#executiveOrgId").val(),year + '-01-01',year + '-12-31');
//									var url = "/makshi/gates/executive/detail/contract.ht?orgId=" + $("#executiveOrgId").val() + "&singingStart="
//											+ year + "-01-01&singingEnd=" + year + "-12-31";
//									window.open(url);
								}
							}
						}
					},
					series : [ {
						name : title + '合同签订',
						data : pageviews
					} ]
				});

		// var tables = '<table class="table table-bordered
		// table-hover
		// table-striped
		// executive">' + '<tr><th>部门</th><th>' + data.time5 +
		// "年" + '</th><th>'
		// +
		// data.time4 + "年" + '</th><th>'
		// + data.time3 + "年" + '</th><th>' + data.time2 + "年" +
		// '</th><th>' +
		// data.time1 + "年" + '</th></tr>';
		// $.each(data.items, function(i, item) {
		// tables += '<tr><td>' + item.orgname + '</td><td>' +
		// item.money5 +
		// '</td><td>'
		// + item.money4 + '</td><td>' + item.money3 +
		// '</td><td>' + item.money2
		// +
		// '</td><td>'
		// + item.money1 + '</td></tr>';
		// });
		// tables += '<tr><td>合计</td><td>' +
		// data.timemoney5.toFixed(4) +
		// '</td><td>' +
		// data.timemoney4.toFixed(4) + '</td><td>' +
		// data.timemoney3.toFixed(4)
		// +
		// '</td><td>'
		// + data.timemoney2.toFixed(4) + '</td><td>' +
		// data.timemoney1.toFixed(4) +
		// '</td></tr>';
		// tables += '</table>';
		// $("#containerContractList").html(tables);
	});
}

function contractDetail(orgId,singingStart,singingEnd){
	addToTab('/makshi/gates/executive/detail/contract.ht?orgId=' + orgId + '&singingStart=' + singingStart + '&singingEnd=' + singingEnd,'合同签订明细表','htqdmxb');
}

function containerContractBilling() {
	$.getJSON("/makshi/gates/executive/depart/contractBilling.ht", {
		orgId : $("#executiveOrgId").val()
	}, function(data) {
		if (!data.result)
			return;
		var title = data.title;
		var xaxis = [ data.time5 + "年", data.time4 + "年", data.time3 + "年", data.time2 + "年", data.time1 + "年" ];
		var pageviews = [];
		pageviews.push([ data.time5 + "年", data.timemoney5 ]);
		pageviews.push([ data.time4 + "年", data.timemoney4 ]);
		pageviews.push([ data.time3 + "年", data.timemoney3 ]);
		pageviews.push([ data.time2 + "年", data.timemoney2 ]);
		pageviews.push([ data.time1 + "年", data.timemoney1 ]);
		$('#containerContract').highcharts(
				{
					chart : {
						type : 'column'
					},
					legend : {
						layout : 'vertical',
						align : 'right',
						verticalAlign : 'middle',
					},
					credits : {
						enabled : false
					},
					title : {
						text : title + '合同收款统计（万元）'
					},
					xAxis : {
						categories : xaxis,
						crosshair : true
					},
					yAxis : {
						min : 0,
						title : {
							text : ''
						}
					},
					tooltip : {
						headerFormat : '',
						formatter : function() {
							var year = this.x.substring(0, 4);
							return '<a href="javascript:contractBillingDetail(\'' + $("#executiveOrgId").val() + '\',\'' + year + '-01-01\',\'' + year + '-12-31\');" <span style="color:red;padding:0">'
							+ this.y + '万元</span></a>';
//							return '<a href="/makshi/gates/executive/detail/contractBilling.ht?orgId=' + $("#executiveOrgId").val()
//									+ '&singingStart=' + year + '-01-01&singingEnd=' + year
//									+ '-12-31" target="_blank"><span style="color:red;padding:0"> ' + this.y + '万元</span></a>';
						},
						footerFormat : '',
						shared : true,
						useHTML : true
					},
					plotOptions : {
						column : {
							pointPadding : 0.2,
							borderWidth : 0,
							cursor : 'pointer',
							events : {
								click : function(e) {
									// location.href = e.point.url;
									// 上面是当前页跳转，如果是要跳出新页面，那就用
									var year = e.point.name.substring(0, 4);
									contractBillingDetail($("#executiveOrgId").val(),year + '-01-01',year + '-12-31');
//									var url = "/makshi/gates/executive/detail/contractBilling.ht?orgId=" + $("#executiveOrgId").val()
//											+ "&singingStart=" + year + "-01-01&singingEnd=" + year + "-12-31";
//									window.open(url);
								}
							}
						}
					},
					series : [ {
						name : '合同收款统计（万元）',
						data : pageviews
					} ]
				});
		// var tables = '<table class="table table-bordered table-hover
		// table-striped executive">' + '<tr><th>部门</th><th>' + data.time5 + "年"
		// + '</th><th>' + data.time4 + "年" + '</th><th>'
		// + data.time3 + "年" + '</th><th>' + data.time2 + "年" + '</th><th>' +
		// data.time1 + "年" + '</th></tr>';
		// $.each(data.items, function(i, item) {
		// tables += '<tr><td>' + item.orgname + '</td>'
		// /*
		// * '<tr><td><a
		// * href="/makshi/gates/executive/depart/manager.ht?orgId=' +
		// * item.orgid + '" target="_blank">' + item.orgname + '</a></td>'
		// */+ '<td><a
		// href="/makshi/gates/executive/detail/contractBilling.ht?orgId=' +
		// item.orgid + '&singingStart=' + data.time5 + '-01-01&singingEnd=' +
		// data.time5
		// + '-12-31" target="_blank">' + item.money5 + '</a></td>' + '<td><a
		// href="/makshi/gates/executive/detail/contractBilling.ht?orgId=' +
		// item.orgid + '&singingStart='
		// + data.time4 + '-01-01&singingEnd=' + data.time4 + '-12-31"
		// target="_blank">' + item.money4 + '</a></td>'
		// + '<td><a
		// href="/makshi/gates/executive/detail/contractBilling.ht?orgId=' +
		// item.orgid + '&singingStart=' + data.time3 + '-01-01&singingEnd=' +
		// data.time3
		// + '-12-31" target="_blank">' + item.money3 + '</a></td>' + '<td><a
		// href="/makshi/gates/executive/detail/contractBilling.ht?orgId=' +
		// item.orgid + '&singingStart='
		// + data.time2 + '-01-01&singingEnd=' + data.time2 + '-12-31"
		// target="_blank">' + item.money2 + '</a></td>'
		// + '<td><a
		// href="/makshi/gates/executive/detail/contractBilling.ht?orgId=' +
		// item.orgid + '&singingStart=' + data.time1 + '-01-01&singingEnd=' +
		// data.time1
		// + '-12-31" target="_blank">' + item.money1 + '</a></td>' + '</tr>';
		// });
		// tables += '<tr><td>合计</td><td>' + data.totalmoney5 + '</td><td>' +
		// data.totalmoney4 + '</td><td>' + data.totalmoney3 + '</td><td>' +
		// data.totalmoney2 + '</td><td>'
		// + data.totalmoney1 + '</td></tr>';
		// tables += '</table>';
		// $("#containerContractList").html(tables);
	});

}

function contractBillingDetail(orgId,singingStart,singingEnd){
	addToTab('/makshi/gates/executive/detail/contractBilling.ht?orgId=' + orgId + '&singingStart=' + singingStart + '&singingEnd=' + singingEnd,'合同收款明细表','htskmxb');
}

function containerAsset() {
	$.getJSON("/makshi/gates/executive/depart/asset.ht", {
		orgId : $("#executiveOrgId").val()
	}, function(data) {
		if (!data.result)
			return;
		var title = data.title;
		var pageviews = [];
		$.each(data.pies, function(i, item) {
			pageviews.push([ item.asset_code, parseFloat(item.asset_total) ]);
		});

		// $("#containerAssetsList").empty().html(data.table);
		$('#containerAssets').highcharts({
			chart : {
				plotBackgroundColor : null,
				plotBorderWidth : null,
				plotShadow : false,
				renderTo : 'chart'
			},
			legend : {
				layout : 'vertical',
				align : 'right',
				verticalAlign : 'middle',
			},
			credits : {
				enabled : false
			},
			title : {
				text : title + '资产分布情况'
			},
			tooltip : {
				headerFormat : '{series.name}<br>',
				pointFormat : '{point.name}: <b>{point.y}</b>'
			},
			plotOptions : {
				pie : {
					allowPointSelect : false,
					cursor : 'pointer',
					dataLabels : {
						enabled : true,
						format : '<b>{point.name}</b>: {point.y}',
						style : {
							color : (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
						}
					},
					showInLegend : true,
					point : {
						events : {
							legendItemClick : function() {
								return false;
							}
						}
					},
					events : {
						click : function(e) {
							var name = e.point.name;
							if (name == '车辆' || name == '租房')
								return;
							// location.href = e.point.url;
							// 上面是当前页跳转，如果是要跳出新页面，那就用
							changeToPeopleAssetTab($("#executiveOrgId").val(),name,'资产分布明细表');
//							var url = "/makshi/gates/executive/detail/asset.ht?orgId=" + $("#executiveOrgId").val() + "&assetCode=" + name;
//							window.open(url);
						}
					}
				}
			},
			series : [ {
				type : 'pie',
				name : title + '资产分布',
				data : pageviews
			} ]
		});
	});
}

function certificate() {
	$.getJSON("/makshi/gates/executive/depart/certificate.ht", {
		orgId : $("#executiveOrgId").val()
	}, function(data) {
		if (!data.result)
			return;
		var pageviews = [];
		$.each(data.items, function(i, item) {
			pageviews.push([ item.type, parseFloat(item.total) ]);
		});

		$('#containerQualifications').highcharts({
			chart : {
				plotBackgroundColor : null,
				plotBorderWidth : null,
				plotShadow : false,
				renderTo : 'chart'
			},
			legend : {
				layout : 'vertical',
				align : 'right',
				verticalAlign : 'middle',
			},
			credits : {
				enabled : false
			},
			title : {
				text : '公司资质统计（个）'
			},
			tooltip : {
				headerFormat : '{series.name}<br>',
				pointFormat : '{point.name}: <b>{point.y}个</b>'
			},
			plotOptions : {
				pie : {
					allowPointSelect : false,
					cursor : 'pointer',
					dataLabels : {
						enabled : true,
						format : '<b>{point.name}</b>: {point.y}个',
						style : {
							color : (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
						}
					},
					showInLegend : true,
					point : {
						events : {
							legendItemClick : function() {
								return false;
							}
						}
					},
				}
			},
			series : [ {
				type : 'pie',
				name : '公司资质统计（个）',
				data : pageviews
			} ]
		});
	});

}

function changeToPeopleAssetTab(orgId,asset_code,name){
	addToTab("/makshi/gates/executive/detail/asset.ht?orgId="+orgId+"&assetCode="+asset_code,name,"zc-detail");
}
function changeToTab(orgId,tab,name){
	addToTab("/makshi/gates/executive/depart/manager.ht?tab="+tab+"&orgId=" + orgId,name,"bm-"+orgId);
}
function changeToPeopleDetailTab(orgId,querygo,querytype,name,sex){
	addToTab("/makshi/gates/executive/detail/people.ht?querytype="+querytype+"&querygo=" + querygo+"&orgId="+orgId+"&sex="+sex,name,"bm-"+querytype);
}
function changeToPeopleAgeTab(orgId,querygo,querytype,name,agesMin,agesMax){
	addToTab("/makshi/gates/executive/detail/people.ht?querytype="+querytype+"&querygo=" + querygo+"&orgId="+orgId+"&agesMin="+agesMin+"&agesMax="+agesMax,name,"bm-"+querytype);
}
function changeToPeopleEducationTab(orgId,querygo,querytype,name,education){
	addToTab("/makshi/gates/executive/detail/people.ht?querytype="+querytype+"&querygo=" + querygo+"&orgId="+orgId+"&education="+education,name,"bm-"+querytype);
}
function changeToPeopleProfessionalTab(orgId,querygo,querytype,name,positional){
	addToTab("/makshi/gates/executive/detail/people.ht?querytype="+querytype+"&querygo=" + querygo+"&orgId="+orgId+"&positional="+positional,name,"bm-"+querytype);
}
function changeToPeopleAssetTab(orgId,asset_code,name){
	addToTab("/makshi/gates/executive/detail/asset.ht?orgId="+orgId+"&assetCode="+asset_code,name,"zc-detail");
}
function addToTab(url,name,uniqueId){
	 //扩展了tab方法。
	 //var url='/makshi/gates/executive/depart/manager.ht?tab=people_education&orgId=10000007780616';
	 window.top.removeTab(uniqueId);
	 window.top.addToTab(url,name,uniqueId,"");
}