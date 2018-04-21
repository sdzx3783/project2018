<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="com.hotent.platform.model.system.SysUser"%>
<%@include file="/commons/include/html_doctype.html" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
    <title>部门管理门户</title>
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
		#containerPeopleList table th, #containerPeopleList table td {
			width: 25%;
		}
		#containerPeopleList table th, #containerPeopleList table td {
			width: 16.666667%;
		}
		.my-order2 th, .my-order2 td {
			width: 16.666667%;
		}
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
	<div style="padding: 0 25px 0px 25px;">
	
		<div class="row">
       		<div class="caption_second_title">
				<span class="statics_title active" data-id='people' >人力资源管理</span>
				<span class="statics_title" data-id='contract'>合同管理</span>
				<span class="statics_title" data-id='asset' >资产管理</span>
				<!-- <span class="statics_title" data-id='Qualifications' >资质管理</span> -->
			</div>
       </div>
       
       
       <div class="my-second-nav statics_content_people">
       		 <ul class="nav nav-pills">
				<li role="presentation" class="peoplechart active nav_menu" data-id='people'><a href="javascript:people();">人员统计</a></li>
				<li role="presentation" class="peoplechart nav_menu" data-id='age'><a href="javascript:peopleAges();">年龄分布</a></li>
				<li role="presentation" class="peoplechart nav_menu" data-id='education'><a href="javascript:peopleEducation();">学历分布</a></li>
				<li role="presentation" class="peoplechart nav_menu" data-id='professional'><a href="javascript:peopleProfessional();">职称分布</a></li>
				<!-- <li role="presentation" class="peoplechart nav_menu"><a href="javascript:;">各类报表</a></li> -->
			 </ul>
	       <div class="row">
	       	   <div class="col-md-6">
				   <div id="container" style="min-width:500px;height:500px"></div>
	       	   </div>
	       	   <div class="col-md-5"  id="containerPeopleList">
	       	   </div>
	       </div>
       </div>
       
       
        
       <div class="my-second-nav statics_content_contract hidden">
       		<ul class="nav nav-pills">
		       <li role="presentation" class="contractchart active nav_menu" data-id="contract"><a href="javascript:containerContract();">合同签订统计</a></li>
		       <li role="presentation" class="contractchart nav_menu" data-id="billing"><a href="javascript:containerContractBilling();">合同收款明细统计</a></li>
		   </ul>
	       <div class="row">
	       	   <div class="col-md-6">
				   <div id="containerContract" style="min-width:400px;height:400px"></div>
	       	   </div>
	       	   <div class="col-md-5" id="containerContractList">
	       	   </div>
	       </div>
       </div> 
       
       <div class="my-second-nav statics_content_asset hidden">
	       <ul class="nav nav-pills">
		       <li role="presentation" class="active nav_menu"><a href="javascript:containerAsset();">资产统计</a></li>
			   <!-- <li role="presentation" class="nav_menu"><a href="javascript:;">租房统计</a></li>
			   <li role="presentation" class="nav_menu"><a href="javascript:;">其他报表</a></li> -->
		   </ul>
	       <div class="row">
	       	   <div class="col-md-6">
				   <div id="containerAssets" style="min-width:500px;height:500px"></div>
	       	   </div>
	       	   <div class="col-md-5" id="containerAssetsList">
	       	   </div>
	       </div>
       </div>
       
       
       <div class="my-second-nav statics_content_Qualifications hidden">
	       <ul class="nav nav-pills">
		       <li role="presentation" class="active nav_menu"><a href="javascript:certificate();">公司资质</a></li>
			   <li role="presentation" class="nav_menu"><a href="javascript:;">注册人员汇总表</a></li>
		   </ul>
	       <div class="row">
	       	   <div class="col-md-5">
				   <div id="containerQualifications" style="min-width:400px;height:400px"></div>
	       	   </div>
	       	   <div class="col-md-7" id="containerQualificationsList">
	       	   </div>
	       </div> 
       </div>
       
    </div>
    <input type="hidden" value="${param['tab']}" id="executivetab">
    <input type="hidden" value="${executiveOrgId}" id="executiveOrgId">
    <script type="text/javascript" src="${ctx}/js/makshi/gates/executive/executiveDepartManager.js?t=<%=Math.random()%>"></script>
</body>
</html>


