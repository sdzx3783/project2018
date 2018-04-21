$(function() {

	$(".peoplechart").on("click", function() {
		$(".peoplechart").removeClass("active");
		$(this).addClass("active");
	});
	
	$(".contractchart").on("click", function() {
		$(".contractchart").removeClass("active");
		$(this).addClass("active");
	});

	$(".financechart").on("click", function() {
		$(".financechart").removeClass("active");
		$(this).addClass("active");
	});

	$(".statics_title").on("click", function() {
		$(".statics_title").removeClass("active");
		$(this).addClass("active");
		$(".my-second-nav").addClass("hidden");
		var id = $(this).data("id");
		$(".statics_content_" + id).removeClass("hidden");
		if (id == 'organizational') {
			if ($.trim($("#organizationalList").text()) != '')
				return;
			organizational();
		}else if (id == 'people') {
			if ($.trim($("#containerPeopleList").text()) != '')
				return;
			people();
		} else if (id == 'contract') {
			if ($.trim($("#containerContractList").text()) != '')
				return;
			containerContract();
		} else if (id == 'asset') {
			if ($.trim($("#containerAssetsList").text()) != '')
				return;
			containerAsset();
		} else if (id == 'Qualifications') {
			certificate();
		} else if (id == 'finance') {
			$(".finance:first").trigger('click');
		}
	});

//	organizational();

	// people();

	// containerContract();
	//
	// containerAsset();
	//
	// certificate();

	$(".finance").click(function() {
		var fileurl = $(this).data("fileurl");
		$.getJSON("/makshi/gates/financereport/analysisExcel.ht", {
			fileurl : fileurl
		}, function(data) {
			$("#containerfinance").html(data.table);
		});
	});
});

function organizational() {
	var arry = [ '10000011000055', '10000011000073', '10000007780656', '10000011000072', '10000007780857', '10000011000075', '10000011000078', '10000011000053' ];
	var logos = ['办公室','工程部','河道管养部','环境事业部','监理部','南水北调','水保部','运维部','造价部','招标代理部','咨询部','综合部'];
	$.getJSON("/makshi/gates/executive/organizational.ht", {}, function(data) {
		if (data.result && data.items) {
			$("#organizationalList").empty();
			$.each(data.items, function(i, item) {
				var onclick = 'class="godepartment"',canclick = '',clickClass='class="godepartment"'
				if ($.inArray(item.orgId, arry) >= 0) {
					clickClass='class="godepartment hand"'
					onclick = ' onclick="changeOrgFuncByUrl(\'/makshi/portal/orgportal/index.ht?orgId=' + item.orgId + '\')"';
					canclick = 'canclick';
				}
//				var logoname = 'default';
//				if($.inArray(item.orgName, logos) >= 0){
//					logoname = item.orgName;
//				}
//				<img src="./images/gates/' + logoname + '.png"  />
				$("#organizationalList").append(
						'<div class="department-item"><div class="v-line"></div><div ' + clickClass + onclick + ' ><span class="'+canclick+'">' + item.orgName
								+ '</span></div></div>');
			});
		}
	});
}
function people() {
	$.getJSON("/makshi/gates/executive/people.ht", {}, function(data) {
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
				text : '公司部门人数分布情况'
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
						format : '{point.name}:<b>{point.y}</b>人,<br>占{point.percentage:.1f} %',
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
				name : '公司部门人数占比',
				data : pageviews
			} ]
		});
	});
}

function peopleAges() {
	$.getJSON("/makshi/gates/executive/peopleAges.ht", {}, function(data) {
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
function peopleBetAges() {
	var agesMin = $.trim($("#agesMin").val());
	var agesMax = $.trim($("#agesMax").val());
	if ((agesMin == '' && agesMax == '')) {
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
	$.getJSON("/makshi/gates/executive/peopleBetAges.ht", {
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
	$.getJSON("/makshi/gates/executive/peopleEducation.ht", {}, function(data) {
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
				text : '学历分布'
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
				name : '学历分布占比',
				data : pageviews
			} ]
		});
	});
}
function peopleProfessional() {
	$.getJSON("/makshi/gates/executive/peopleProfessional.ht", {}, function(data) {
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
				text : '职称分布'
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
				name : '职称分布占比',
				data : pageviews
			} ]
		});
	});
}

function containerContract() {
	$.getJSON("/makshi/gates/executive/contract.ht", {}, function(data) {
		if (!data.result)
			return;
		var xaxis = [ data.time5 + "年", data.time4 + "年", data.time3 + "年", data.time2 + "年", data.time1 + "年" ];
		var pageviews = [];
		pageviews.push([ data.time5 + "年", data.timemoney5 ]);
		pageviews.push([ data.time4 + "年", data.timemoney4 ]);
		pageviews.push([ data.time3 + "年", data.timemoney3 ]);
		pageviews.push([ data.time2 + "年", data.timemoney2 ]);
		pageviews.push([ data.time1 + "年", data.timemoney1 ]);
		$('#containerContract').highcharts({
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
				text : '合同签订统计（万元）'
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
				pointFormat : '<span style="color:red;padding:0"> {point.y:.2f}万元</span>',
				footerFormat : '',
				shared : true,
				useHTML : true
			},
			plotOptions : {
				column : {
					pointPadding : 0.2,
					borderWidth : 0
				}
			},
			series : [ {
				name : '合同签订统计（万元）',
				data : pageviews
			} ]
		});
		var tables = '<table class="table table-bordered table-hover table-striped executive">' + '<tr><th>部门</th><th>' + data.time5 + "年" + '</th><th>' + data.time4 + "年" + '</th><th>'
				+ data.time3 + "年" + '</th><th>' + data.time2 + "年" + '</th><th>' + data.time1 + "年" + '</th></tr>';
		$.each(data.items, function(i, item) {
			tables += '<tr><td><a href="javascript:changeToTab(\'' + item.orgid + '\',\'contract_contract\',\'' + item.orgname + '\');">' + item.orgname + '</a></td>'
			/*
			 * '<tr><td><a
			 * href="/makshi/gates/executive/depart/manager.ht?orgId=' +
			 * item.orgid + '" target="_blank">' + item.orgname + '</a></td>'
			 */
			   + '<td><a href="javascript:contractDetail(\'' + item.orgid + '\',\'' + data.time5 + '-01-01\',\'' + data.time5 + '-12-31\');">' + item.money5 + '</a></td>'
			   + '<td><a href="javascript:contractDetail(\'' + item.orgid + '\',\'' + data.time4 + '-01-01\',\'' + data.time4 + '-12-31\');">' + item.money4 + '</a></td>'
			   + '<td><a href="javascript:contractDetail(\'' + item.orgid + '\',\'' + data.time3 + '-01-01\',\'' + data.time3 + '-12-31\');">' + item.money3 + '</a></td>'
			   + '<td><a href="javascript:contractDetail(\'' + item.orgid + '\',\'' + data.time2 + '-01-01\',\'' + data.time2 + '-12-31\');">' + item.money2 + '</a></td>'
			   + '<td><a href="javascript:contractDetail(\'' + item.orgid + '\',\'' + data.time1 + '-01-01\',\'' + data.time1 + '-12-31\');">' + item.money1 + '</a></td>'
			   + '</tr>';
		});
		tables += '<tr><td>合计</td><td>' + data.totalmoney5 + '</td><td>' + data.totalmoney4 + '</td><td>' + data.totalmoney3 + '</td><td>' + data.totalmoney2 + '</td><td>'
				+ data.totalmoney1 + '</td></tr>';
		tables += '</table>';
		$("#containerContractList").html(tables);
	});
}

function contractDetail(orgId,singingStart,singingEnd){
	addToTab('/makshi/gates/executive/detail/contract.ht?orgId=' + orgId + '&singingStart=' + singingStart + '&singingEnd=' + singingEnd,'合同签订明细表','htqdmxb');
}

function containerContractBilling(){
	$.getJSON("/makshi/gates/executive/contractBilling.ht", {}, function(data) {
		if (!data.result)
			return;
		var xaxis = [ data.time5 + "年", data.time4 + "年", data.time3 + "年", data.time2 + "年", data.time1 + "年" ];
		var pageviews = [];
		pageviews.push([ data.time5 + "年", data.timemoney5 ]);
		pageviews.push([ data.time4 + "年", data.timemoney4 ]);
		pageviews.push([ data.time3 + "年", data.timemoney3 ]);
		pageviews.push([ data.time2 + "年", data.timemoney2 ]);
		pageviews.push([ data.time1 + "年", data.timemoney1 ]);
		$('#containerContract').highcharts({
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
				text : '合同收款统计（万元）'
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
				pointFormat : '<span style="color:red;padding:0"> {point.y:.2f}万元</span>',
				footerFormat : '',
				shared : true,
				useHTML : true
			},
			plotOptions : {
				column : {
					pointPadding : 0.2,
					borderWidth : 0
				}
			},
			series : [ {
				name : '合同收款统计（万元）',
				data : pageviews
			} ]
		});
		var tables = '<table class="table table-bordered table-hover table-striped executive">' + '<tr><th>部门</th><th>' + data.time5 + "年" + '</th><th>' + data.time4 + "年" + '</th><th>'
				+ data.time3 + "年" + '</th><th>' + data.time2 + "年" + '</th><th>' + data.time1 + "年" + '</th></tr>';
		$.each(data.items, function(i, item) {
			tables += '<tr><td><a href="javascript:changeToTab(\'' + item.orgid + '\',\'contract_billing\',\'' + item.orgname + '\');">' + item.orgname + '</a></td>'
			/*
			 * '<tr><td><a
			 * href="/makshi/gates/executive/depart/manager.ht?orgId=' +
			 * item.orgid + '" target="_blank">' + item.orgname + '</a></td>'
			 */     
			        + '<td><a href="javascript:contractBillingDetail(\'' + item.orgid + '\',\'' + data.time5 + '-01-01\',\'' + data.time5 + '-12-31\');">' + item.money5 + '</a></td>' 
			        + '<td><a href="javascript:contractBillingDetail(\'' + item.orgid + '\',\'' + data.time4 + '-01-01\',\'' + data.time4 + '-12-31\');">' + item.money4 + '</a></td>' 
			        + '<td><a href="javascript:contractBillingDetail(\'' + item.orgid + '\',\'' + data.time3 + '-01-01\',\'' + data.time3 + '-12-31\');">' + item.money3 + '</a></td>' 
			        + '<td><a href="javascript:contractBillingDetail(\'' + item.orgid + '\',\'' + data.time2 + '-01-01\',\'' + data.time2 + '-12-31\');">' + item.money2 + '</a></td>' 
			        + '<td><a href="javascript:contractBillingDetail(\'' + item.orgid + '\',\'' + data.time1 + '-01-01\',\'' + data.time1 + '-12-31\');">' + item.money1 + '</a></td>' 
			        + '</tr>';
		});
		tables += '<tr><td>合计</td><td>' + data.totalmoney5 + '</td><td>' + data.totalmoney4 + '</td><td>' + data.totalmoney3 + '</td><td>' + data.totalmoney2 + '</td><td>'
				+ data.totalmoney1 + '</td></tr>';
		tables += '</table>';
		$("#containerContractList").html(tables);
	});

}

function contractBillingDetail(orgId,singingStart,singingEnd){
	addToTab('/makshi/gates/executive/detail/contractBilling.ht?orgId=' + orgId + '&singingStart=' + singingStart + '&singingEnd=' + singingEnd,'合同收款明细表','htskmxb');
}

function containerAsset() {
	$.getJSON("/makshi/gates/executive/asset.ht", {}, function(data) {
		if (!data.result)
			return;
		var pageviews = [];
		$.each(data.pies, function(i, item) {
			pageviews.push([ item.asset_code, parseFloat(item.asset_total) ]);
		});

		$("#containerAssetsList").empty().html(data.table);
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
				text : '资产分布情况'
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
				}
			},
			series : [ {
				type : 'pie',
				name : '资产分布',
				data : pageviews
			} ]
		});
	});
}

function certificate() {
	$.getJSON("/makshi/gates/executive/certificate.ht", {}, function(data) {
		if (!data.result)
			return;
		var pageviews = [];
		var table = '<table class="table table-bordered table-hover table-striped executive">';
		table += '<tr><th>资质类型</th><th>资质数量</th></tr>';
		$.each(data.items, function(i, item) {
			pageviews.push([ item.typeDesc, parseFloat(item.total) ]);
			table += '<tr><td>' + item.typeDesc + '</td><td><a href="javascript:certificateDetail(\''+item.type+'\');">' + parseFloat(item.total) + '</a></td></tr>';
		});
		table += '</table>';
		$("#containerQualificationsList").html(table);
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
				text : '公司资质统计'
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
				name : '公司资质统计',
				data : pageviews
			} ]
		});
		
		
	});

}

function certificateDetail(type){
	addToTab('/makshi/gates/executive/detail/qualifications.ht?type='+type,'公司资质明细表','gszzmxb');
}
function changeOrg(id){
	var $company=window.top;
	$company.changeOrgFunc(id);
}
function changeOrgFuncByUrl(orgId,name){
	//var url = "/makshi/portal/orgportal/index.ht?orgId=" + orgId;
	//var $company=window.top;
	//$company.changeOrgFuncByUrl(url);
	//window.open("/makshi/gates/executive/depart/manager.ht?tab=people_people&orgId=" + orgId);
	changeToTab(orgId,"people_people",name);
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