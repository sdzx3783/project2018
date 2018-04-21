<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>个人执业资格管理</title>
<%@include file="/commons/include/list_get.jsp"%>
</head>
 <script type="text/javascript" src="${ctx}/js/hotent/ajaxgrid.js"></script>
 <script type="text/javascript" src="${ctx}/js/lg/util/DialogUtil.js" ></script>
<body>
	<!-- 顶部按钮区域 -->
	<div id="oa_search_table_button_wrap" class="oa-head-wrap">
		<a href="edit.ht" class="oa-condi-nav oa-button oa-button--primary oa-button--blue" target="_blank">添加</a>
	</div>
<!-- <a class="link import" action="/platform/form/bpmDataTemplate/importData_personal_qualification.ht?" onclick="openLinkDialog({scope:this,width:450,height:200,title:'导入'})" href="javascript:;"><span></span>导入资格证</a>
<a class="link import" action="/platform/form/bpmDataTemplate/importData_occupational_requirements.ht?" onclick="openLinkDialog({scope:this,width:450,height:200,title:'导入'})" href="javascript:;"><span></span>导入从业证</a> -->
	<!-- 在这里配置每个页面的简单查询模块 -->
	<div id="oa_simple_search" class="oa-simple-search-wrap oa-clear">
		<ul>
				 <li class="oa-search-item oa-fl oa-mgb10">
                    <div class="oa-label">持证人</div>
                    <div class="oa-input-wrap oa-mgl100">
                        <input data-type="7"  date_name="condition" type="text" name="s.userName" class="oa-input" value="${param['userName']}"/>
                    </div>
                </li>
                <li class="oa-search-item oa-fl oa-mgb10">
                    <div class="oa-label">工号</div>
                    <div class="oa-input-wrap oa-mgl100">
                        <input data-type="7"  date_name="condition" type="text" name="s.account" class="oa-input" value="${param['account']}"/>
                    </div>
                </li>
                <li class="oa-search-item oa-fl oa-mgb10">
                    <div class="oa-label">身份证号码</div>
                    <div class="oa-input-wrap oa-mgl100">
                        <input data-type="7"  date_name="condition" type="text" name="s.idNumber" class="oa-input" value="${param['idNumber']}"/>
                    </div>
                </li>
                <li class="oa-search-item oa-fl oa-mgb10">
                    <div class="oa-label">资格证书类型</div>
                    <div class="oa-input-wrap oa-mgl100">
                       <select class="oa-select" name="s.vocationName" date_name="condition" value="${param['s.vocationName']}">
						<option value="">选择</option>
						<option value="建设部监理工程师" <c:if test="${param['s.vocationName']=='建设部监理工程师'}">selected</c:if>>建设部监理工程师</option>
						<option value="建设部造价工程师" <c:if test="${param['s.vocationName']=='建设部造价工程师'}">selected</c:if>>建设部造价工程师</option>
						<option value="一级建造师" <c:if test="${param['s.vocationName']=='一级建造师'}">selected</c:if>>一级建造师</option>
						<option value="二级建造师" <c:if test="${param['s.vocationName']=='二级建造师'}">selected</c:if>>二级建造师</option>
						<option value="水利部总监" <c:if test="${param['s.vocationName']=='水利部总监'}">selected</c:if>>水利部总监</option>
						<option value="水利部监理工程师" <c:if test="${param['s.vocationName']=='水利部监理工程师'}">selected</c:if>>水利部监理工程师</option>
						<option value="水利部造价工程师" <c:if test="${param['s.vocationName']=='水利部造价工程师'}">selected</c:if>>水利部造价工程师</option>
						<option value="水利部监理员" <c:if test="${param['s.vocationName']=='水利部监理员'}">selected</c:if>>水利部监理员</option>
						<option value="一级结构工程师" <c:if test="${param['s.vocationName']=='一级结构工程师'}">selected</c:if>>一级结构工程师</option>
						<option value="二级结构工程师" <c:if test="${param['s.vocationName']=='二级结构工程师'}">selected</c:if>>二级结构工程师</option>
						<option value="招标师" <c:if test="${param['s.vocationName']=='招标师'}">selected</c:if>>招标师</option>
						<option value="注册设备监理工程师" <c:if test="${param['s.vocationName']=='注册设备监理工程师'}">selected</c:if>>注册设备监理工程师</option>
						<option value="注册公用设备工程师" <c:if test="${param['s.vocationName']=='注册公用设备工程师'}">selected</c:if>>注册公用设备工程师</option>
						<option value="注册安全工程师" <c:if test="${param['s.vocationName']=='注册安全工程师'}">selected</c:if>>注册安全工程师</option>
						<option value="咨询工程师(投资)" <c:if test="${param['s.vocationName']=='咨询工程师(投资)'}">selected</c:if>>咨询工程师(投资)</option>
						<option value="投资项目管理师" <c:if test="${param['s.vocationName']=='投资项目管理师'}">selected</c:if>>投资项目管理师</option>
						<option value="测绘师" <c:if test="${param['s.vocationName']=='测绘师'}">selected</c:if>>测绘师</option>
						<option value="信息监理工程师" <c:if test="${param['s.vocationName']=='信息监理工程师'}">selected</c:if>>信息监理工程师</option>
						<option value="系统集成项目管理工程师" <c:if test="${param['s.vocationName']=='系统集成项目管理工程师'}">selected</c:if>>系统集成项目管理工程师</option>
					</select>
                    </div>
                </li>
                 <li class="oa-search-item oa-fl oa-mgb10">
                    <div class="oa-label">注册证书编号</div>
                    <div class="oa-input-wrap oa-mgl100">
                        <input data-type="7"  date_name="condition" type="text" name="s.vocationNum" class="oa-input" value="${param['vocationNum']}"/>
                    </div>
                </li>

                 <li class="oa-search-item oa-fl oa-mgb10">
                    <div class="oa-label">从业证书类型</div>
                    <div class="oa-input-wrap oa-mgl100">
                       <select class="oa-select" name="s.occupationalType" date_name="condition" value="${param['s.occupationalType']}">
						<option value="">选择</option>
						<option value="1" <c:if test="${param['s.occupationalType']==1}">selected</c:if>>三类人员安全生产考核合格证</option>
						<option value="2" <c:if test="${param['s.occupationalType']==2}">selected</c:if>>深圳市监理员</option>
						<option value="3" <c:if test="${param['s.occupationalType']==3}">selected</c:if>>深圳市监理工程师</option>
						<option value="4" <c:if test="${param['s.occupationalType']==4}">selected</c:if>>水利部监理工程师信用手册</option>
						<option value="5" <c:if test="${param['s.occupationalType']==5}">selected</c:if>>建设部监理工程师信用手册</option>
						<option value="6" <c:if test="${param['s.occupationalType']==6}">selected</c:if>>深圳市档案员</option>
					</select>
                    </div>
                </li>
                <li class="oa-search-item oa-fl oa-mgb10">
                    <div class="oa-label">毕业专业</div>
                    <div class="oa-input-wrap oa-mgl100">
                    	<input data-type="7"  date_name="condition" type="text" name="s.learnMajor" class="oa-input" value="${param['learnMajor']}"/>
                	</div>
                </li>
                <li class="oa-search-item oa-fl oa-mgb10">
                    <div class="oa-label">初级职称专业</div>
                    <div class="oa-input-wrap oa-mgl100">
                    	<input data-type="7"  date_name="condition" type="text" name="s.learnMajor" class="oa-input" value="${param['learnMajor']}"/>
                	</div>
                </li>
                <li class="oa-search-item oa-fl oa-mgb10">
                    <div class="oa-label">注册证有效日期</div>
                    <div class="oa-input-wrap oa-mgl100 oa-input-wrap--ib">
                        <input date_name="condition" data-type="2" type="text"  name="s.lastEffecticeDate"  class="oa-input date" validate="{date:true}" value="${param['beginlastEffecticeDate']}"/>
                    </div>
                    <span>至</span>
                    <div class="oa-input-wrap oa-input-wrap--ib">
                        <input date_name="condition" data-type="4" type="text"  name="s.lastEffecticeDate"  class="oa-input date" validate="{date:true}" value="${param['endlastEffecticeDate']}"/>
                    </div>
                </li>
                <li class="oa-search-item oa-fl oa-mgb10">
                    <div class="oa-label">状态</div>
                    <div class="oa-input-wrap oa-mgl100">
                       <select class="oa-select" name="s.vocationSwitchs" date_name="condition" value="${param['s.vocationSwitchs']}">
						<option value="">选择</option>
						<option value="" <c:if test="${param['s.occupationalType']==''}">selected</c:if>>初始注册</option>
						<option value="1" <c:if test="${param['s.occupationalType']==1}">selected</c:if>>已转入</option>
						<option value="0" <c:if test="${param['s.occupationalType']==0}">selected</c:if>>已转出</option>
					</select>
                </li>
                <li class="oa-search-item oa-fl oa-mgb10">
                    <div class="oa-label">中级职称专业</div>
                    <div class="oa-input-wrap oa-mgl100">
                        <input data-type="7"  date_name="condition" type="text" name="s.positionalMajorTwo" class="oa-input" value="${param['positionalMajorTwo']}"/>
                    </div>
                </li>
                <li class="oa-search-item oa-fl oa-mgb10">
                    <div class="oa-label">高级职称专业</div>
                    <div class="oa-input-wrap oa-mgl100">
                        <input data-type="7"  date_name="condition" type="text" name="s.positionalMajorThree" class="oa-input" value="${param['positionalMajorThree']}"/>
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
				 formatter : 'addNum'
			}, {
				field : 's.userName',//查询的字段
				title : '持证人',//显示的title
				formatter : 'getDetail',
				sortable : true
			}, {
				field : 's.account',//查询的字段
				title : '员工编号',//显示的title
				formatter : 'getDetail',
				sortable : true
			}, {
				field : 's.idNumber',//查询的字段
				title : '身份证号码',//显示的title
				sortable : true
			}, {
				field : 's.learnMajor',//查询的字段
				title : '毕业专业',//显示的title
				sortable : true
			}, {
				field : 's.vocationNum',//查询的字段
				title : '资格证书编号或资格证书管理编号',//显示的title
				sortable : true
			//是否支持排序
			},{
				field : 's.vocationName',//查询的字段
				title : '资格证书类型',//显示的title
				formatter : 'getDetail',
				sortable : true
			//是否支持排序
			},{
				field : 's.vocationSwitchs',//查询的字段
				title : '状态',//显示的title
				sortable : true,
				selectType:"select",
				formatter : 'setVocationSwitchs'//显示的时候执行edit函数
			//是否支持排序
			},{
				field : 's.vocationBorrower',//查询的字段
				title : '当前借用人(资格证)',//显示的title
				sortable : true
			//是否支持排序
			},{
				field : 's.registCertificateRegistId',//查询的字段
				title : '注册证书编号',//显示的title
				sortable : true
			//是否支持排序
			},{
				field : 's.registMajor',//查询的字段
				title : '第一注册专业',//显示的title
				sortable : true
			},{
				field : 's.secondMajor',//查询的字段
				title : '第二注册专业',//显示的title
				sortable : true
			},{
				field : 's.thirdMajor',//查询的字段
				title : '第三注册专业',//显示的title
				sortable : true
			},{
				field : 's.lastEffecticeDate',//查询的字段
				title : '注册证书有效日期',//显示的title
				sortable : true,
				dateFlag : true,//是否是时间，这个会弹出时间控件选择器
				dateFormat : "yyyy-MM-dd",//时间的显示格式 
			},{
				field : 's.keepEduStatus',//查询的字段
				title : '继续教育完成情况',//显示的title
				sortable : true
			},{
				field : 's.regPeriod',//查询的字段
				title : '总学时',//显示的title
				sortable : true
			},{
				field : 's.regCompulsory',//查询的字段
				title : '必修课学时',//显示的title
				sortable : true
			},{
				field : 's.regElective',//查询的字段
				title : '选修课学时',//显示的title
				sortable : true
			},{
				field : 's.registBorrower',//查询的字段
				title : '当前借用人(注册证)',//显示的title
				sortable : true
			},{
				field : 's.occupationalCertificateId',//查询的字段
				title : '从业证书编号',//显示的title
				sortable : true
			//是否支持排序
			},{
				field : 's.occupationalType',//查询的字段
				title : '从业证书类型',//显示的title
				sortable : true,
				selectType:"select",
				formatter : 'setOccupationalType'//显示的时候执行edit函数
			},{
				field : 's.occPeriodOfValidity',//查询的字段
				title : '从业资格有效日期',//显示的title
				sortable : true,
				dateFlag : true,//是否是时间，这个会弹出时间控件选择器
				dateFormat : "yyyy-MM-dd",//时间的显示格式 
			},{
				field : 's.occupationalBorrower',//查询的字段
				title : '当前借用人(从业证)',//显示的title
				sortable : true
			},{
				field : '管理',//即使不去查询这个字段也必须。用来记录改变列的顺序用。如果不去查询必须带nosearch字段
				title : '管理',
				nosearch : true,//不去数据库查询
				formatter : 'edit'//显示的时候执行edit函数
			}, ],
			uniqueId : "s.linkId",//唯一主键字段
			type : "borrowViewList",//类型，用户存储在数据库中，每个项目的不同页面保证唯一
			tableName : 'borrowView s', //表明
			userId : '${sessionScope.LOGIN_USER_ID }', //用户ID
		//sortName : 's.seller_name',//初始化排序
		//sortOrder : 'desc'//初始化排序
		}
		initCommonTable(tableParam);
	});

	/* *
	修改显示列
	value: the field value. 
	row: the row record data.
	index: the row index
	 **/
	function addNum(value, row, index) {  
            var page = $('#tb_common_show').bootstrapTable("getPage"); 
            return page.pageSize * (page.pageNumber - 1) + index + 1;  
    } 
	
	function edit(value, row, index) {
		var html = "";
				html += '<a href="edit.ht?id=' + row["s.linkId"] + '" target="_blank" >编辑</a>' +
					    '<a class="tabledel" href="del.ht?&id=' + row["s.linkId"] + '">&nbsp删除</a>';
		return html;
	}
	
	function getDetail(value, row, index){
		var id = row["s.linkId"];
		var html = '--';
		if(value && value.length>0){
			html =  '<a href=get.ht?id='+id+' target="_blank">'+value+'</a>';
		}
			return html;
	}
	function setVocationSwitchs(value, row, index) {
		if (value == 0) {
			return "已转出";
		} else if (value == 1) {
			return "已转入";
		}
		return "初始注册";
	}
	allOptions.push(new singleOption("setVocationSwitchs","已转出",0,"已转入",1,"初始注册","",null,null,null,null,null,null));
	
	function setOccupationalType(value, row, index) {
		var id = row["s.linkId"];
		var html = '--';
		if (value == 1) {
			html =  '<a href=get.ht?id='+id+' target="_blank">三类人员安全生产考核合格证</a>';
		} else if (value == 2) {
			html =  '<a href=get.ht?id='+id+' target="_blank">深圳市监理员</a>';
		}else if (value == 3) {
			html =  '<a href=get.ht?id='+id+' target="_blank">深圳市监理工程师</a>';
		}else if (value == 4) {
			html =  '<a href=get.ht?id='+id+' target="_blank">水利部监理工程师信用手册</a>';
		}else if (value == 5) {
			html =  '<a href=get.ht?id='+id+' target="_blank">建设部监理工程师信用手册</a>';
		}else if (value == 6) {
			html =  '<a href=get.ht?id='+id+' target="_blank">深圳市档案员</a>';
		}
		return html;
	}
	allOptions.push(new singleOption("setOccupationalType",
									 "三类人员安全生产考核合格证",1,
									 "深圳市监理员",2,
									 "深圳市监理工程师",3,
									 "水利部监理工程师信用手册",4,
									 "建设部监理工程师信用手册",5,
									 "深圳市档案员",6
	));
	
	function setIsBinding(value, row, index) {
		if (value == 0) {
			return "否";
		} 
		if (value == 1) {
			return "是";
		}
		return "否";
	}
	
 	$(function(){
		handlerDelOne();
	});
	function handlerDelOne()
	{
		$("body").on("click",".tabledel",function(){
			var href_ = $(this).attr("href");
			if($(this).hasClass('disabled')) return false;
			var ele = this;
			$.ligerDialog.confirm('确认删除吗？','提示信息',function(rtn) {
				if(rtn) {
						window.location.href = href_;
					} else {
						window.location.reload();
					}
			});
			return false;
		});
	} 
/* 	function showResponse(responseText){
	    var obj=new com.hotent.form.ResultMessage(responseText);
	    console.log(obj);return false;
	    if(obj.isSuccess()){
	        var msg="启动流程成功!";
	        $.ligerDialog.success(msg,'提示信息',function(){
	            if(window.opener){
	                window.opener.location.href = window.opener.location.href;
	                window.close();
	            }else{
	                window.close();
	            }
	        });

	    }
	} */
/* 	$(function(){
		$.ligerDialog.fail('<p><font color="green">删除执业证书失败!</font></p>');
	}); */
</script>
</body>
</html>


