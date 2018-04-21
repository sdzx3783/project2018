<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>合同基本信息管理</title>
<%@include file="/commons/include/list_get_contract.jsp"%>
	<f:link href="Aqua/css/ligerui-all.css" ></f:link>
    <f:link href="tree/zTreeStyle.css" ></f:link>
    <link href="${ctx}/js/select2/select2.css" rel="stylesheet" />
	<link href="${ctx}/js/select2/select2-metronic.css" rel="stylesheet" />
    <script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
    
     <script type="text/javascript" src="${ctx}/js/lg/newligerui.all.js" ></script>
     <script type="text/javascript" src="${ctx}/js/tree/jquery.ztree.js"></script>
    <script type="text/javascript" src="${ctx}/js/lg/plugins/ligerComboBox.js"></script>
    <script type="text/javascript" src="${ctx}/js/lg/plugins/htDicCombo.js"></script> 
    
    <script type="text/javascript" src="${ctx}/js/jquery/jquery.form.js"></script>
    <script type="text/javascript" src="${ctx}/js/lg/plugins/ligerDrag.js" ></script>
    <script type="text/javascript" src="${ctx}/js/lg/plugins/newligerDialog.js" ></script>
    <script type="text/javascript" src="${ctx}/js/lg/plugins/ligerResizable.js" ></script>
    <%@include file="/commons/include/ueditor.jsp" %>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/form/SelectorInit.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/ajaxgrid.js"></script>
    <script type="text/javascript" src="${ctx}/js/lg/util/DialogUtil.js" ></script>
    <script type="text/javascript" src="${ctx }/js/select2/select2.min.js"></script>
    <style>
    </style>
<script type="text/javascript">
$(function(){
	$(".oa-mw-page").on("click","a.oa-del",function(){
		  if($(this).hasClass('disabled')) return false;
		  var ele = this;
		  $.ligerDialog.confirm('确认删除吗？','提示信息',function(rtn) {
		  	 if(rtn) {
		    	if(ele.click) {
		    		window.location.href=ele.href;
		   		 } else {
		    		 location.href=ele.href;
		 	     }
		   	 }
		  });
		  return false;
		 });
		
		$("#contractType2").hide();
		$.ajax({  
            type: "get",  
            url: "${ctx}/makshi/contract/contractinfo/queryContractType.ht?",  
            dataType: "json",  
            contentType: "application/json",  
            success: function (data) {  
            	render(data);
            } 
        });  
		var deepData = [];
		function render(data){
			var html = '<option value="">请选择</option >';
			data.forEach(function(item, index){
				var flag = 0;
				if(item.contractNumSecondList.length){
					flag = 1;
					item.contractNumSecondList.forEach(function(item, index){
						deepData.push({
							id: item.id,
							refId: item.refId,
							num: item.num,
							type: item.type
						});
						
					});
				}
				html += '<option value="' + item.type +'" data-num="'+ item.contract_num +'" <c:if test="${param['s.F_contracttype']=='+ item.type +'}">selected</c:if>>' + item.type +'</option>';
			});
			$("select[name='s.F_contracttype']").html(html);
		}
		$("select[name='s.F_contracttype']").on('change', function(){
			var _$select = $(this).find("option:selected");
			var _num = _$select.attr('data-num');
			var html = '';
			for(var i = 0 ; i<deepData.length ; i++){
				var typeItem = deepData[i];
				if(typeItem.num==_num){
					html += '<option value="' + typeItem.type +'"  <c:if test="${param['s.F_type']=='+ typeItem.type +'}">selected</c:if>>' + typeItem.type +'</option>';
				}
			}
			if(html.length>0){
				$("#contractType2").show();
			}else{
				$("#contractType2").hide();
			}
			html = '<option value="">请选择</option>'+html;
			$("select[name='s.F_type']").html(html);
		});
		
});
</script>
<style>
	body {
		overflow: hidden;
	}
	#tb_common_show td, #tb_common_show th {
		text-decoration: none;
	    white-space: nowrap;
	}
	.oa-long-select{
    		min-width: 300px;
    		width:auto !important;
    		padding: 0px;
		    border: 0px solid #e5e5e5;
		    background-image: none;
		    background-color: #fff;
		    filter: none;
		    -webkit-box-shadow: none !important;
		    box-shadow: none !important;
    	}
    .select2-container-multi .select2-choices .select2-search-field input{padding:0px !important;}
	div.select2-container-multi{
		min-width:180px;
		max-width:700px !important;
    	width: auto !important;
    	border: 1px solid #8c9fd6;
    	height: auto !important;
    }
</style>
</head>
<body class="oa-mw-page contractinfo-page">
	<!-- 顶部按钮区域 -->
	<c:if test="${isEditor}">
	<div id="oa_search_table_button_wrap" class="oa-head-wrap">
		<a class="oa-button oa-button--primary oa-button--blue" href="edit.ht" target="_blank">添加</a>
	 	<a class="oa-del oa-button oa-button--primary oa-button--blue" id="del" action="del.ht">删除</a>
	     <!-- <a class="link import" action="/platform/form/bpmDataTemplate/importData_contractinfo.ht?" onclick="openLinkDialog({scope:this,width:450,height:200,title:'导入'})" href="javascript:;"><span></span>导入</a> --> 
	</div>
	</c:if>	
	<!-- 在这里配置每个页面的简单查询模块 -->
	<div id="oa_simple_search" class="oa-simple-search-wrap oa-clear">
		<ul>
			<li class="oa-search-item oa-fl oa-mgb10">
				<div class="oa-label">合同类型:</div>
				<div class="oa-input-wrap oa-mgl100">
					<select class="oa-select" name="s.F_contracttype" date_name="condition" value="${param['s.s.F_contracttype']}"></select>
				</div>
				<div class="oa-input-wrap oa-mgl100" id="contractType2">
					<select class="oa-select" name="s.F_type" date_name="condition" value="${param['s.F_type']}"></select>
				</div> 
			</li>
			<li class="oa-search-item oa-fl oa-mgb10"><div class="oa-label">合同编号:</div><div class="oa-input-wrap oa-mgl100"><input type="text" data-type="7" date_name="condition" name="s.F_contract_num"  class="oa-input"  value="${param['s.F_contract_num']}"/></div></li>
			<li class="oa-search-item oa-fl oa-mgb10"><div class="oa-label">合同名称:</div><div class="oa-input-wrap oa-mgl100"><input type="text" data-type="7" date_name="condition" name="s.F_contract_name"  class="oa-input"  value="${param['s.F_contract_name']}"/></div></li>
			<li class="oa-search-item oa-fl oa-mgb10">
				<div class="oa-label">合同状态:</div>
				<div class="oa-select-wrap oa-mgl100">
					<select class="oa-select" name="s.F_contract_status" date_name="condition" value="${param['s.F_contract_status']}">
						<option value="">选择</option>
						<option value="作废" <c:if test="${param['s.F_contract_status']=='1'}">selected</c:if>>作废</option>
						<option value="正式合同" <c:if test="${param['s.F_contract_status']=='0'}">selected</c:if>>正式合同</option>
					</select> 
				</div>
			</li>
			<li class="oa-search-item oa-fl oa-mgb10">
				<div class="oa-label">合同是否备案:</div>
				<div class="oa-select-wrap oa-mgl100">
					<select class="oa-select" name="s.F_isrecord" value="${param['s.F_isrecord']}">
						<option value="">选择</option>
						<option value="是" <c:if test="${param['s.F_isrecord']=='1'}">selected</c:if>>是</option>
						<option value="否" <c:if test="${param['s.F_isrecord']=='0'}">selected</c:if>>否</option>
					</select> 
				</div>
			</li>
			<li class="oa-search-item oa-fl oa-mgb10"><div class="oa-label">甲方:</div><div class="oa-input-wrap oa-mgl100"><input type="text" name="s.F_first_party" data-type="7"  class="oa-input"  value="${param['s.F_first_party']}"/></div></li>
			<li class="oa-search-item oa-fl oa-mgb10"><div class="oa-label">乙方:</div><div class="oa-input-wrap oa-mgl100"><input type="text" name="s.F_second_party" data-type="7" class="oa-input"  value="${param['s.F_second_party']}"/></div></li>
			
			
			<li class="oa-search-item oa-fl oa-mgb10">
				<div class="oa-label">系统内外:</div>
				<div class="oa-select-wrap oa-mgl100">
					<select class="oa-select" name="F_inOut" value="${param['F_inOut']}">
						<option value="">选择</option>
						<option value="外" <c:if test="${param['F_inOut']=='1'}">selected</c:if>>外</option>
						<option value="内" <c:if test="${param['F_inOut']=='0'}">selected</c:if>>内</option>
					</select>
				</div> 
			</li>
			<li class="oa-search-item oa-fl oa-mgb10">
				<div class="oa-label">资料是否存档:</div>
				<div class="oa-select-wrap oa-mgl100">
					<select class="oa-select" name="s.F_issave" value="${param['s.F_issave']}">
						<option value="">选择</option>
						<option value="是" <c:if test="${param['s.F_issave']=='1'}">selected</c:if>>是</option>
						<option value="否" <c:if test="${param['s.F_issave']=='0'}">selected</c:if>>否</option>
					</select> 
				</div> 
			</li>
			<li class="oa-search-item oa-fl oa-mgb10"><div class="oa-label">项目总监:</div><div class="oa-input-wrap oa-mgl100"><input type="text" data-type="7" name="s.F_project_director"  class="oa-input"  value="${param['s.F_project_director']}"/></div></li>
			<li class="oa-search-item oa-fl oa-mgb10">
				<div class="oa-label">项目状态:</div>
				<div class="oa-select-wrap oa-mgl100">
					<select class="oa-select" name="s.F_project_status" value="${param['F_project_status']}">
						<option value="">选择</option>
						<option value="在建" <c:if test="${param['s.F_project_status']=='在建'}">selected</c:if>>在建</option>
						<option value="完工" <c:if test="${param['s.F_project_status']=='完工'}">selected</c:if>>完工</option>
						<option value="停工" <c:if test="${param['s.F_project_status']=='停工'}">selected</c:if>>停工</option>
					</select> 
				</div> 
			<li class="oa-search-item oa-fl oa-mgb10">
				<div class="oa-label">合同是否归档:</div>
				<div class="oa-select-wrap oa-mgl100">
					<select class="oa-select" name="s.F_isrecovery" value="${param['F_isrecovery']}">
						<option value="">选择</option>
						<option value="是" <c:if test="${param['F_isrecovery']=='1'}">selected</c:if>>是</option>
						<option value="否" <c:if test="${param['F_isrecovery']=='0'}">selected</c:if>>否</option>
					</select> 
				</div> 
			</li>
			<li class="oa-search-item oa-fl oa-mgb10" >
				<div class="oa-label">签约时间从:</div>
				<div class="oa-input-wrap oa-mgl100  oa-input-wrap--ib">
					<input type="text"  data-type="2" name="s.F_singing_time"  class="oa-input date" validate="{date:true}" value="${param['s.F_singing_time']}"/>
				</div>
				<span>至</span>
				<div class="oa-input-wrap oa-input-wrap--ib">
					<input type="text" data-type="4" name="s.F_singing_time"  class="oa-input date" validate="{date:true}" value="${param['s.F_singing_time']}"/>
				</div>
			</li>
			<li class="oa-search-item oa-fl oa-mgb10" >
				<div class="oa-label">合同金额(万元):</div>
				<div class="oa-input-wrap oa-mgl100  oa-input-wrap--ib">
					<input type="text"  data-type="2" name="s.F_contract_money"  class="oa-input"  value="${param['s.F_contract_money']}"/>
				</div>
				<span>至</span>
				<div class="oa-input-wrap oa-input-wrap--ib">
					<input type="text" data-type="4" name="s.F_contract_money"  class="oa-input"   value="${param['s.F_contract_money']}"/>
				</div>
			</li>
			<li id="selectDepartment" class="oa-search-item oa-fl oa-mgb10">
				<div class="oa-label">归属部门:</div>
				<div class="oa-input-wrap oa-mgl100 oa-long-select">
					<select id="departmentIds" class="oa-select" data-type="8" name="s.F_contract_departmentID" date_name="condition" multiple="multiple" style="width: 150px !important;">
							<option value="10000007780617">财务部</option>
							<option value="10000007780616">办公室</option>
							<option value="10000007780656">水保部</option>
							<option value="10000007780857">监理部</option>
							<option value="10000011000053">招标代理部</option>
							<option value="10000011000055">咨询部</option>
							<option value="10000011000072">综合部</option>
							<option value="10000011000073">运维部</option>
							<option value="10000011000075">河道管养部</option>
							<option value="10000011000077">工程部</option>
							<option value="10000011000078">环境事业部</option>
						</select>
				</div>
			</li>
		</ul>
	</div>
		

	<!-- 高级查询模块 -->
	<jsp:include page="/commons/include/list_common.jsp"></jsp:include>

<script type="text/javascript">	
	$(function() {
		var tableParam = {
			columns : [ {
				title : '序号',//显示的title
				formatter :'addNum',
				sortable : true,
				footerFormatter:'合计'
			}, {
				field : 's.F_contract_num',//查询的字段
				title : '合同编号',//显示的title
				sortable : true,
				formatter : 'getDetail',
				footerFormatter:'-'
			//是否支持排序
			}, {
				field : 's.F_contract_name',
				title : '合同名称',
				sortable : true,
				formatter : 'getDetail',
				footerFormatter:'-'
			//是否支持排序
			}, {
				field : 's.F_contracttype',
				title : '合同类型',
				sortable : true,
				footerFormatter:'-'
			}, {
				field : 's.F_contract_status',
				title : '合同状态',
				sortable : true,
				selectType:"select",
				footerFormatter:'-'
			//是否支持排序
   			}, {
				field : 's.F_payment_type',
				title : '付费类型',
				sortable : true,
				selectType:"select"
			//是否支持排序 
			}, {
				field : 's.F_project_status',
				title : '项目状态',
				sortable : true,
				footerFormatter:'-'
			//是否支持排序	
			}, {
				field : 's.F_first_party',
				title : '甲方',
				sortable : true,
				footerFormatter:'-'
			//是否支持排序
			}, {
				field : 's.F_second_party',
				title : '乙方',
				sortable : true,
				footerFormatter:'-'
			//是否支持排序
			}, {
				field : 's.F_bidding_method',
				title : '发包方式',
				sortable : true,
				selectType:"select",
				footerFormatter:'-'
			//是否支持排序
			}, {
				field : 's.F_bidding_platform',
				title : '招标平台',
				sortable : true,
				selectType:"select",
				footerFormatter:'-'
			//是否支持排序		
			}, {
				field : 's.F_total_investment',
				title : '总投资（万元）',
				sortable : true,
				footerFormatter:"totalInvestment"
			//是否支持排序
			}, {
				field : 's.F_contract_money',
				title : '合同金额（万元）',
				sortable : true,
			    footerFormatter:"totalContractMoney"
			//是否支持排序
			}, {
				field : 's.F_project_bid_floorprice',
				title : '工程标底价（万元）',
				sortable : true,
				footerFormatter:'totalProjectBidFloorprice'
			//是否支持排序
			}, {
				field : 's.F_project_bid_price',
				title : '工程中标价（万元）',
				sortable : true,
				footerFormatter:'totalProjectBidPrice'
			//是否支持排序
			}, {
				field : 's.F_settlement_money',
				title : '结算金额（万元）',
				sortable : true,
				footerFormatter:'totalSettlementMoney'
			//是否支持排序
			}, {
				field : 's.F_inout',
				title : '系统内外',
				sortable : true,
				footerFormatter:'-'
			//是否支持排序
			}, {
				field : 's.F_contract_department',
				title : '合同归属部门',
				sortable : false,
				footerFormatter:'-'
			//是否支持排序
			}, {
				field : 's.F_project_department',
				title : '项目部',
				sortable : true,
				footerFormatter:'-'
			//是否支持排序
			}, {
				field : 's.F_contract_handler',
				title : '合同经手人',
				sortable : true,
				footerFormatter:'-'
			//是否支持排序
			}, {
				field : 's.F_project_director',
				title : '项目总监',
				sortable : true,
				footerFormatter:'-'
			//是否支持排序
			}, {
				field : 's.F_project_leader',
				title : '项目负责人',
				sortable : true,
				footerFormatter:'-'
			//是否支持排序
			}, {
				field : 's.F_contract_reviewer',
				title : '合同审核人',
				sortable : true,
				footerFormatter:'-'
			//是否支持排序
			}, {
				field : 's.F_contract_authorized_person',
				title : '合同批签人',
				sortable : true,
				footerFormatter:'-'
			//是否支持排序
			}, {
				field : 's.F_isrecovery',
				title : '合同是否归档',
				sortable : true,
				footerFormatter:'-'
			//是否支持排序
			}, {
				field : 's.F_isrecord',
				title : '合同是否备案',
				sortable : true,
				footerFormatter:'-'
			//是否支持排序
			}, {
				field : 's.F_issave',
				title : '竣工子资料是否存档',
				sortable : true,
				footerFormatter:'-'
			//是否支持排序
			}, {
				field : 's.F_updater',
				title : '修改人',
				sortable : true,
				footerFormatter:'-'
			//是否支持排序
			},
			/* {
				field : 's.F_invoice_amount',
				title : '开票金额',
				sortable : true,
				footerFormatter:'totalInvoiceAmount'
			//是否支持排序
			}, */
			{
				field : 's.F_singing_time',//查询的字段
				title : '签约时间',//显示的title
				sortable : true,
				dateFlag : true,//是否是时间，这个会弹出时间控件选择器
				dateFormat : "yyyy-MM-dd",//时间的显示格式
				footerFormatter:'-'
			//是否支持排序
			},{
				field : 's.F_start_time',//查询的字段
				title : '开工时间',//显示的title
				sortable : true,
				dateFlag : true,//是否是时间，这个会弹出时间控件选择器
				dateFormat : "yyyy-MM-dd",//时间的显示格式
				footerFormatter:'-'
			//是否支持排序
			},{
				field : 's.F_end_time',//查询的字段
				title : '完工时间',//显示的title
				sortable : true,
				dateFlag : true,//是否是时间，这个会弹出时间控件选择器
				dateFormat : "yyyy-MM-dd",//时间的显示格式
				footerFormatter:'-'
			},{
				field : 's.F_create_time',//查询的字段
				title : '创建时间',//显示的title
				sortable : true,
				dateFlag : true,//是否是时间，这个会弹出时间控件选择器
				dateFormat : "yyyy-MM-dd",//时间的显示格式
				footerFormatter:'-'
			},{
				field : 's.F_update_time',//查询的字段
				title : '修改时间',//显示的title
				sortable : true,
				dateFlag : true,//是否是时间，这个会弹出时间控件选择器
				dateFormat : "yyyy-MM-dd",//时间的显示格式
				footerFormatter:'-'
			}, {
				field : '管理',//即使不去查询这个字段也必须。用来记录改变列的顺序用。如果不去查询必须带nosearch字段
				title : '管理',
				nosearch : true,//不去数据库查询
				footerFormatter:'-',
				formatter : 'edit'//显示的时候执行edit函数
			//	class : 'notscroll'
			}, ],
			uniqueId : "s.ID",//唯一主键字段
			type : "contractinfoviewList",//类型，用户存储在数据库中，每个项目的不同页面保证唯一
			tableName : 'contractinfoview s', //表明
			userId : '${sessionScope.LOGIN_USER_ID }', //用户ID
		   	sortName : 'F_contract_num',//初始化排序
		    sortOrder : 'desc',//初始化排序    
	    	showFooter:true,
	    	resizeMode:'overflow',
	    	sumFooter : '/common/component/maishicommon?servlettype=brootstraptable&configType=search&needFootSum=true' //自定义求和参数
		}
		//list_common.jsp加载慢时 会出现问题  所有将代码放在此处初始化
		if($("#oa_simple_search_container").find("#oa_simple_search").length<=0){
        	$("#oa_simple_search").parent(".oa-head-wrap").hide();
            $("#oa_simple_search_container").prepend($("#oa_simple_search").show());
        }
		
		var departmentIds=$("#departmentIds").select2({
			placeholder : "请选择",
			allowClear : true,
			formatNoMatches: function(term) {
				return "";
			}
		});
		
		initCommonTable(tableParam,function() {
			var orgId = '<%=session.getAttribute("chooseOrgId") %>';
			var orgName = '<%=session.getAttribute("orgName") %>';
			 $("select[name='s.F_contract_status']").val("正式合同");
			if((orgId!=='null')&&(-1!=orgId)){
				departmentIds.val(orgId).trigger("change");
			    totalSearch();
			    $("select[name='s.F_contract_departmentID']").attr("disabled",true);
			    $("#selectDepartment").hide();
		    }else{
		    	totalSearch();
		    }
		});	
		handlerDelSelect();
		
		
	});

	function addNum(value, row, index) {  
		var page = $('#tb_common_show').bootstrapTable("getPage"); 
        return page.pageSize * (page.pageNumber - 1) + index + 1;   
    }    
	function totalContractMoney(value) {
		if(sumData) return parseFloat(sumData["s.F_contract_money"].toFixed(4));
        var count = 0;
        var length = value.length;
        for (var i=0;i<length;i++) {
        	var obj = value[i]["s.F_contract_money"];
        	if(!obj){
        	var obj = 0.0;
        	}
           count +=obj;
        }
        if(count>0){
            count = count.toFixed(4);
            count = parseFloat(count);
            }else{
            	return "0";
            }
        return count;
    }
 	function totalInvestment(value) {
 		if(sumData) return parseFloat(sumData["s.F_total_investment"].toFixed(4));
        var count = 0;
        var length = value.length;
        for (var i=0;i<length;i++) {
        	var obj = value[i]["s.F_total_investment"];
        	if(!obj){
        	var obj = 0.0;
        	}
           count +=obj;
        }
        if(count>0){
            count = count.toFixed(4);
            count = parseFloat(count);
            }else{
            	return "0";
            }
        return count;
    } 
 	function totalProjectBidFloorprice(value){
 		if(sumData) return parseFloat(sumData["s.F_project_bid_floorprice"].toFixed(4));
		var count = 0;
        var length = value.length;
        for (var i=0;i<length;i++) {
        	var obj = value[i]["s.F_project_bid_floorprice"];
        	if(!obj){
        	var obj = 0.0;
        	}
           count +=obj;
        }
        if(count>0){
            count = count.toFixed(4);
            count = parseFloat(count);
            }else{
            	return "0";
            }
        return count;
    } 
 	function totalProjectBidPrice(value){
 		if(sumData) return parseFloat(sumData["s.F_project_bid_price"].toFixed(4));
		var count = 0;
        var length = value.length;
        for (var i=0;i<length;i++) {
        	var obj = value[i]["s.F_project_bid_price"];
        	if(!obj){
        	var obj = 0.0;
        	}
           count +=obj;
        }
        if(count>0){
            count = count.toFixed(4);
            count = parseFloat(count);
            }else{
            	return "0";
            }
        return count;
    }
	function totalSettlementMoney(value){
		if(sumData) return parseFloat(sumData["s.F_settlement_money"].toFixed(4));
		var count = 0;
        var length = value.length;
        for (var i=0;i<length;i++) {
        	var obj = value[i]["s.F_settlement_money"];
        	if(!obj){
        	var obj = 0.0;
        	}
           count +=obj;
        }
        if(count>0){
        count = count.toFixed(4);
        count = parseFloat(count);
        }else{
        	return "0";
        }
        return count;
	}
	function totalInvoiceAmount(value){
		if(sumData) return parseFloat(sumData["s.F_invoice_amount"].toFixed(4));
		var count = 0;
        var length = value.length;
        for (var i=0;i<length;i++) {
        	var obj = value[i]["s.F_invoice_amount"];
        	if(!obj){
        	var obj = 0.0;
        	}
           count +=obj;
        }
        if(count>0){
        count = count.toFixed(4);
        count = parseFloat(count);
        }else{
        	return "0";
        }
        return count;
	}
	
	function getDetail(value, row, index) { 
		var id = row["s.ID"];
		var html =  '<a href=get.ht?id='+id+' target="_blank">'+value+'</a>';
		return html;
	}
	function edit(value, row, index) {
		var isEditor = '${isEditor}';
		var readonly = '${readonly}';
		var html = '<a href="get.ht?id=' + row["s.ID"]+ '" target="_blank"><span></span>明细</a>'
				 + '<a href="toprint.ht?id=' + row["s.ID"]+ '" class="" target="_blank">&nbsp打印</a>';					
		if(readonly=='true'){
			html =  '<a href="get.ht?id=' + row["s.ID"]+ '" target="_blank">&nbsp明细</a>';
		}
		if(isEditor=='true'){
			html = '<a  href="del.ht?id=' + row["s.ID"]+ '" class="oa-del">&nbsp删除</a>'
					+ '<a href="edit.ht?id=' + row["s.ID"]+ '" target="_blank">&nbsp编辑</a>'
					+ '<a href="get.ht?id=' + row["s.ID"]+ '" target="_blank">&nbsp明细</a>'
					+ '<a href="toprint.ht?id=' + row["s.ID"]+ '" class="" target="_blank">&nbsp打印</a>';
		}
		return html;
	}

	/* function addOption(isSelect,name,value){
var singleOption = {"name:"+name+",value:"+value};
var holeOption = {"name:"+isSelect+",value:"+singleOption};
allOptions.push(holeOption); */

	/**
	 * 获取选择的数据
	 */
	function getSelect() {
		return $('#tb_common_show').bootstrapTable('getSelections');
	}
	function handlerDelSelect() {
		//单击删除超链接的事件处理
		$("#del").click(function() {
			if ($(this).hasClass('disabled'))
				return false;
			var action = $(this).attr("action");
			var $aryId = getSelect();

			if (typeof ($aryId) == "undefined" || $aryId.length == 0) {
				alert("请选择记录！");
				return false;
			}

			//提交到后台服务器进行日志删除批处理的日志编号字符串
			var delId = "";
			var keyName = "";
			var len = $aryId.length;
			$.each($aryId, function(key, value) {
				delId += value['s.ID'] + ",";
			})

			var url = action + "?id=" + delId;
			$.post(url, function(data) {
				alert(data.message);
				location.reload();
			})
			return false;

		});
	}
	function del(id) {
		var url = "del.ht?id=" + id;
		$.post(url, function(data) {
			alert(data.message);
			location.reload();
		})
	}
</script>
</body>
</html>


