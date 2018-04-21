<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="com.hotent.platform.model.system.SysUser"%>
<%@include file="/commons/include/html_doctype.html" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <title>公司管理门户</title>
    <base href="<%=basePath%>">
   	<%@include file="/commons/include/list_get.jsp"%>
	<f:link href="Aqua/css/ligerui-all.css" ></f:link>
    <f:link href="tree/zTreeStyle.css" ></f:link>
    
    <style type="text/css">
    	.portlet-title {
    		margin: -10px -25px 0;
    		padding: 0 25px;
    	    height: 40px;
    		white-space: nowrap;   		
    		overflow: hidden;
    		background: #4a81d6;
    	}
    	.portlet-title a {
    		text-align: center;
		    color: #dadfed;
		    cursor: pointer;
		    display: inline-block;
		    float: left;
		    font-size: 14px;
		    height: 40px;
		    line-height: 40px;
		    margin-right: 20px;
		    transition: all .5s;
		    text-decoration: none;
    	}
    	.portlet-title a:hover, .portlet-title a.active {
    		color: #fff;
    	}
    	.caption_title{    
	    	font-size: 25px;
	    	margin-bottom: 20px;
	    	margin-top: 30px;
    	}
    	.my-second-nav .nav {
    		margin-bottom: 20px;
    	}
    	table {
    		min-width: 480px;
    	}
    	table tr td a {
    		font-weight: bold;
    	}
    	table tr:first-child th {
    		font-size: 14px;
    		color: #000;
    	}
    	.caption_menu{
    		font-size: 15px;
	    	margin-bottom: 10px;
	    	padding-bottom: 5px;
	    	border-bottom: 1px solid;
	    	padding-left: 20px;
    	}
    	table.executive tr th{font-weight: 600;}
		.statics_title{
			cursor: pointer;
		    margin: 0px 15px 0px 14px;
		}
		.caption_second_title {
			border-bottom: 2px solid #e1e5f0;
			margin: 20px 0;
		}
		.caption_second_title .statics_title {
			display: inline-block;
			height: 40px;
			line-height: 28px;
			font-size: 16px;
			margin-bottom: -2px;
		}
		.caption_second_title .statics_title.active {
			border-bottom: solid 2px #478de4;
		}
		.financetable{font-size: 14px;}
		
	    .structure-wrapper a {
			display: block;
			text-decoration: none;
			color: #c10000;
			/* cursor: default; */
		}
		.structure-wrapper {
			box-sizing: border-box;
			font-weight: bold;
		}
		.t-header-wrapper {
			position: relative;
			text-align: center;
			width: 200px;
			margin: 0 auto;
		}
		.t-header-wrapper .t-role-wrapper {
			position: absolute;
			top: 197px;
    		left: 158px;
			width: 200px;
		}
		.v-line {
			position: relative;
		    width: 2px;
		    height: 60px;
		    margin: 0 auto 0;
		    background: #a9a9a9;
		}
		.v-line:before {
		    content: '';
		    position: absolute;
		    left: -9px;
		    top: 21px;
		    display: block;
		    width: 20px;
		    height: 20px;
		    border: solid 2px #a9a9a9;
		    border-radius: 50%;
		    background: #fff;
		    outline: solid #fff 3px;
		}
		.v-line.directior-line {
			background: #478de4;
		}
		.v-line.directior-line:before {
			border-color: #478de4;
		}
		.v-line.leadership-line {
			height: 100px;
			background: #478de4;
		}
		.v-line.leadership-line:before {
			top: 42px;
			border-color: #478de4;
		}
		.department-wrapper .department-item .v-line {
			height: 45px;
			margin-top: 15px;
		}
		.department-wrapper .department-item .v-line:before {
			top: -15px;
		}
		.h-line {
			position: absolute;
		    left: 113px;
		    bottom: 46px;
		    width: 45px;
		    height: 2px;
		    background: #478de4;
		}
		.department-wrapper {
			display: table;
			margin: 0 auto;
			text-align: center;
			overflow: hidden;
			min-width: 1105px;
		}
		.department-wrapper .department-item:first-child:before {
			display: none;
		}
		.department-wrapper .department-item:last-child:after {
			display: none;
		}
		.department-wrapper .department-item:before {
			content: '';
			position: absolute;
			top: 9px;
			left: -10px;
			width: 29px;
			height: 2px;
			background: #a9a9a9;
		}
		.department-wrapper .department-item:after {
			content: '';
		    position: absolute;
		    top: 9px;
		    right: -10px;
		    width: 29px;
		    height: 2px;
		    background: #a9a9a9;
		}
		.db-count.department-wrapper .department-item:before {
			left: -11px;
			width: 40px;
		}
		.db-count.department-wrapper .department-item:after {
			right: -11px;
		}
		.department-bar {
			position: relative;
			margin-top: 3px;
		}
		.godepartment {
			display: table;
			border-radius: 12px;
			border: 1px solid #a9a9a9;
			width: 100%; 
			min-height: 120px;
			height: 200px;
    		font-size: 18px;
    		font-weight: normal;
    		color: blue;
    		cursor: default;
    	}
		.godepartment.hand {
			cursor: pointer !important;
			color: blue;
		}
		.godepartment.hand:hover {
			background: #efefef;
		}
		.godepartment .v-middle {
			display: table-cell;
			vertical-align: middle;	
		}
		.department-wrapper .department-item {
			position: relative;
			float: left;
			width: 65px;
			margin: 0 10px;
		}
		.department-wrapper .department-item span {
			display: block;
			width: 1em;
			line-height: 1.2em;
			margin: 0 auto;
		}
		.department-wrapper .department-item img {
			width: 65px;
		}
		.department-item .canclick {
			color: blue;
		}
		.structure-wrapper a.departHigth {
			width: 200px;
			height: 56px;
			line-height: 44px;
			border: 6px solid #4a81d6;
			border-radius: 12px;
			color: #fff;
			background: #478de4;
		    font-size: 21px;	    
		}
		.structure-wrapper a.departHigth.director {
			border-color: #289bd2;
			background: #2ea7e0;
		}
		.structure-wrapper a.departHigth.leadership {
			border-color: #81c288;
			background: #23a82b;
		}
		.kh {
			margin: -7px 0 -7px 1px !important;
			text-align: center;
			transform: rotate(90deg);
		}
		.space-between {
			margin: 0 !important;
		}
		
		.my-order1 th, .my-order1 td {
			width: 25%;
		}
		.my-order2 th, .my-order2 td {
			width: 16.666667%;
		}
		.popover.right{width:550px !important;} 
    </style>
    
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
    <script type="text/javascript" src="${ctx}/js/lg/util/DialogUtil.js" ></script>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/form/SelectorInit.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/ajaxgrid.js"></script>
    <script type="text/javascript" src="${ctx}/js/lg/util/DialogUtil.js" ></script>
    
    <!-- Highcharts -->
    <script src="${ctx}/js/highcharts/highcharts.js"></script>
    <!-- Highcharts -->
</head>
<body class="oa-mw-page">
	<div style="padding: 10px 25px 0px 25px;">
       	<!-- <div class="portlet-title">
			<a href="javascript:;" onclick="changeOrg(10000010330001)" class=' nav_menu'>咨询部</a>
			<a href="javascript:;" onclick="changeOrg(10000008480017)" class=' nav_menu'>运维部</a>
			<a href="javascript:;" onclick="changeOrg(10000011390000)" class=' nav_menu'>水保部</a>
			<a href="javascript:;" onclick="changeOrg(10000000430001)" class=' nav_menu'>综合部</a>
			<a href="javascript:;" onclick="changeOrg(10000000430002)" class=' nav_menu'>监理部</a>
			<a href="javascript:;" onclick="changeOrg(10000014580000)" class=' nav_menu'>河道管养部</a>
			<a href="javascript:;" onclick="changeOrg(10000010880097)" class=' nav_menu'>环境事业部</a>
			<a href="javascript:;" onclick="changeOrg(10000008060007)" class=' nav_menu'>招标代理部</a>
	   </div> -->
	    <div>
	       <div class="row">
	       		<div class="caption_second_title">
					<span class="statics_title active" data-id='organizational' >组织架构</span>
					<span class="statics_title" data-id='people' >人力资源管理</span>
					<span class="statics_title" data-id='contract' >合同管理</span>
					<span class="statics_title" data-id='asset' >资产管理</span>
					<span class="statics_title" data-id='Qualifications' >资质管理</span>
					<span class="statics_title" data-id='finance' >财务管理</span>
				</div>
	       </div>
	       <div class="my-second-nav statics_content_organizational ">
		       <div class="row">
		       	    <div class="structure-wrapper">
						<!-- 顶部领导 -->
						<div class="top-leader">
							<div class="t-header-wrapper">
								<!-- <img src="./images/gates/default.png" /> -->
								<a href="javascript:void(0);" class="departHigth popovers" 
								 data-placement="right" data-trigger="hover"
								data-content="黄琼、陆子锋、罗振、 邓学让、武海军、李财金、张攀、王春华、张伟" data-original-title="董事会名单">董事会</a>
								<div class="v-line directior-line"></div>
								<div class="h-line"></div>
								<!-- <img width="75" src="./images/gates/default.png" /> -->
								<a href="javascript:void(0);" class="departHigth popovers" 
								 data-placement="right" data-trigger="hover"
								data-content="黄琼、陆子锋、罗振、 邓学让、武海军、李财金、张攀、王春华、张伟" data-original-title="领导层名单">领导层</a>
								<div class="v-line leadership-line"></div>
								<div class="t-role-wrapper">
									<!-- <a href="javascript:void(0);">董事会</a>
									<a href="javascript:void(0);">领导层</a> -->
									<a class="departHigth  popovers" 
								 data-placement="right" data-trigger="hover"
								data-content="邓学让" data-original-title="技术委员会名单"
								 href="javascript:void(0);">技术委员会</a>
								</div>
							</div>				
						</div>
						<!-- 部门（可动态加载） -->
						<div class="department-bar">
							<div class="department-wrapper" id='organizationalList11'>
								<div class="department-item">
									<div class="v-line"></div>
									<div class="godepartment hand" onclick="changeOrgFuncByUrl('10000007780616','办公室')">
										<div class="v-middle"><span>办<span class="space-between"></span>公<span class="space-between"></span>室</span></div>
									</div>
								</div> 
								<div class="department-item">
									<div class="v-line"></div>
									<div class="godepartment hand" onclick="changeOrgFuncByUrl('10000007780617','财务部')">
										<div class="v-middle"><span>财<span class="space-between"></span>务<span class="space-between"></span>部</span></div>
									</div>
								</div>
								<div class="department-item">
									<div class="v-line"></div>
									<div class="godepartment hand" onclick="changeOrgFuncByUrl('10000011000055','咨询部')">
										<div class="v-middle"><span>咨<span class="space-between"></span>询<span class="space-between"></span>部</span></div>
									</div>
								</div> 
								<div class="department-item">
									<div class="v-line"></div>
									<div class="godepartment hand" onclick="changeOrgFuncByUrl('10000011000053','招标代理部')">
										<div class="v-middle"><span>招标代理部<span class="kh">（</span>投标组<span class="kh">）</span></span></div>
									</div>
								</div>
								<div class="department-item">
									<div class="v-line"></div>
									<div class="godepartment hand" onclick="changeOrgFuncByUrl('10000007780857','监理部')">
										<div class="v-middle"><span>监<span class="space-between"></span>理<span class="space-between"></span>部</span></div>
									</div>
								</div>
								<div class="department-item">
									<div class="v-line"></div>
									<div class="godepartment hand" onclick="changeOrgFuncByUrl('10000011000078','环境事业部')">
										<div class="v-middle"><span>环境事业部</span></div>
									</div>
								</div>
								<div class="department-item">
									<div class="v-line"></div>
									<div class="godepartment hand" onclick="changeOrgFuncByUrl('10000011000073','运维部')">
										<div class="v-middle"><span>运<span class="space-between"></span>维<span class="space-between"></span>部</span></div>
									</div>
								</div>
								<div class="department-item">
									<div class="v-line"></div>
									<div class="godepartment hand" onclick="changeOrgFuncByUrl('10000011000075','河道管养部')">
										<div class="v-middle"><span>河道管养部</span></div>
									</div>
								</div>
								<div class="department-item">
									<div class="v-line"></div>
									<div class="godepartment hand" onclick="changeOrgFuncByUrl('10000011000072','综合部')">
										<div class="v-middle"><span>综<span class="space-between"></span>合<span class="space-between"></span>部</span></div>
									</div>
								</div>
								<div class="department-item">
									<div class="v-line"></div>
									<div class="godepartment hand" onclick="changeOrgFuncByUrl('10000007780656','水保部')">
										<div class="v-middle"><span>水<span class="space-between"></span>保<span class="space-between"></span>部</span></div>
									</div>
								</div>
								<div class="department-item">
									<div class="v-line"></div>
									<div class="godepartment hand" onclick="changeOrgFuncByUrl('10000011000077','工程部')">
										<div class="v-middle"><span>工<span class="space-between"></span>程<span class="space-between"></span>部</span></div>
									</div>
								</div>
								<div class="department-item">
									<div class="v-line"></div>
									<div class="godepartment hand" onclick="">
										<div class="v-middle"><span>松岗项目部</span></div>
									</div>
								</div>
								<div class="department-item">
									<div class="v-line"></div>
									<div class="godepartment hand" onclick="changeOrgFuncByUrl('20000000890016','江西项目部')">
										<div class="v-middle"><span>江西项目部</span></div>
									</div>
								</div>
							</div>
						</div>
					</div>
		       </div>
	       </div>
	       <div class="my-second-nav statics_content_people hidden">
				 <ul class="nav nav-pills">
					<li role="presentation" class="peoplechart active nav_menu"><a href="javascript:people();">人员统计</a></li>
					<li role="presentation" class="peoplechart nav_menu"><a href="javascript:peopleAges();">年龄分布</a></li>
					<li role="presentation" class="peoplechart nav_menu"><a href="javascript:peopleEducation();">学历分布</a></li>
					<li role="presentation" class="peoplechart nav_menu"><a href="javascript:peopleProfessional();">职称分布</a></li>
					<li role="presentation" class="nav_menu"><a href="javascript:addToTab('/makshi/gates/executive/peopleMonth.ht','员工月度报表','peopleMonth');">员工月度报表</a></li>
					<li role="presentation" class="nav_menu"><a href="javascript:addToTab('/makshi/gates/executive/peopleYear.ht','员工年度报表','peopleYear');">员工年度报表</a></li>
				 </ul>
		       <div class="row">
		       	   <div class="col-md-6">
					   <div id="container" style="min-width:400px;height:500px"></div>
		       	   </div>
		       	   <div class="col-md-5"  id="containerPeopleList">
		       	   </div>
		       </div>
	       </div>
	       
	       <div class="my-second-nav statics_content_contract hidden">
		       <ul class="nav nav-pills">
			       <li role="presentation" class="contractchart active nav_menu"><a href="javascript:containerContract();">合同签订统计</a></li>
			       <li role="presentation" class="contractchart nav_menu"><a href="javascript:containerContractBilling();">合同收款明细统计</a></li>
				   <!-- <li role="presentation" class="nav_menu"><a href="javascript:;">收支统计</a></li>
				   <li role="presentation" class="nav_menu"><a href="javascript:;">各类报表</a></li> -->
			   </ul>
		       <div class="row">
		       	   <div class="col-md-5">
					   <div id="containerContract" style="min-width:400px;height:400px"></div>
		       	   </div>
		       	   <div class="col-md-7" id="containerContractList">
		       	   </div>
		       </div>
	       </div>
	       
	       <div class="my-second-nav statics_content_asset hidden">
		       <ul class="nav nav-pills">
			       <li role="presentation" class="active nav_menu"><a href="javascript:containerAsset();">资产统计</a></li>
				   <li role="presentation" class="nav_menu"><a href="javascript:addToTab('/makshi/gates/executive/getRentHouseStastics.ht','租房统计','zgtj');" >租房统计</a></li>
			   </ul>
		       <div class="row">
		       	   <div class="col-md-5">
					   <div id="containerAssets" style="min-width:400px;height:400px"></div>
		       	   </div>
		       	   <div class="col-md-7" id="containerAssetsList">
		       	   </div>
		       </div>
	       </div>
	       
	       
	       <div class="my-second-nav statics_content_Qualifications hidden">
		       <ul class="nav nav-pills">
			       <li role="presentation" class="active nav_menu"><a href="javascript:certificate();">公司资质</a></li>
				   <li role="presentation" class="nav_menu"><a href="javascript:addToTab('/makshi/qualification/personalQualification/certificatePerson.ht','注册人员汇总表','zcryhzb');" >注册人员汇总表</a></li>
			   </ul>
		       <div class="row">
		       	   <div class="col-md-6">
					   <div id="containerQualifications" style="min-width:400px;height:400px"></div>
		       	   </div>
		       	   <div class="col-md-4" id="containerQualificationsList">
		       	   </div>
		       	   <div class="col-md-2">
		       	   </div>
		       </div>
	       </div>
	       
	       <div class="my-second-nav statics_content_finance hidden">
		       <ul class="nav nav-pills">
			       <li role="presentation" class="financechart active nav_menu"><a href="javascript:void(0);" 
			       data-fileurl="lirun.xls" class='finance'>资产负债表</a></li>
			       <li role="presentation" class="financechart nav_menu"><a href="javascript:void(0);" 
			       data-fileurl="xianjinliuliang.xls" class='finance'>现金流量表</a></li>
			       <li role="presentation" class="financechart nav_menu"><a href="javascript:void(0);"
			       data-fileurl="lirun.xls" class='finance'>利润表</a></li>
			   </ul>
		       <div class="row">
		       	   <!-- <div class="col-md-6">
					   <div id="containerfinance" style="min-width:400px;height:400px"></div>
		       	   </div> -->
		       	   <div class="col-md-12" id="containerfinance">
		       	   </div>
		       </div>
	       </div>
	    </div>
    </div>
    <script type="text/javascript" src="${ctx}/js/makshi/gates/executive/executiveManager.js?t=<%=Math.random()%>"></script>
    <script type="text/javascript">
    $(function(){
    	jQuery('.popovers').popover();
    })
    </script>
</body>
</html>


