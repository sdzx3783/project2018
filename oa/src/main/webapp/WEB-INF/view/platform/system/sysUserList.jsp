<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="com.hotent.platform.model.system.SysUser"%>
<%@include file="/commons/include/html_doctype.html" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
    <title>用户表管理</title>
   	<%@include file="/commons/include/list_get.jsp"%>
	<f:link href="Aqua/css/ligerui-all.css" ></f:link>
    <f:link href="tree/zTreeStyle.css" ></f:link>
    
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
    <script type="text/javascript" src="${ctx }/js/select2/select2.min.js"></script>
    <link href="${ctx}/js/select2/select2.css" rel="stylesheet" />
	<link href="${ctx}/js/select2/select2-metronic.css" rel="stylesheet" />
    <script type="text/javascript">
    
    function openUserUnder(userid,obj){
        if($(obj).hasClass('disabled')) return false;
        var conf={};                
        var url=__ctx + "/platform/system/userUnder/list.ht?userId="+userid;
        conf.url=url;
        var dialogWidth=800;
        var dialogHeight=600;
        conf=$.extend({},{dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:0,scroll:0,center:1},conf);
        DialogUtil.open({
            height:conf.dialogHeight,
            width: conf.dialogWidth,
            title : '下属管理',
            url: url, 
            isResize: true,
        });
    }
    function syncUserToWx(){
        var successMessage ;
        var aryId = $("input[type='checkbox'][disabled!='disabled'][class='pk']:checked");
        var len=aryId.length;
        if(len==0){
            successMessage ="同步所有用户、组织至微信通讯录成功！";
            $.ligerDialog.waitting('正在同步所有用户组织至微信通讯录，请等待...');
        }else successMessage ="同步"+len+"名用户至微信通讯录操作成功！";
        
        var ids="";
        aryId.each(function(i){
            ids+=$(aryId[i]).val() +",";
        })
        var url=__ctx + "/platform/system/sysUser/syncUserToWx.ht?userId="+ids;
        $.post(url,function(data){
            $.ligerDialog.closeWaitting();
            var obj=new com.hotent.form.ResultMessage(data);
            if(obj.isSuccess()){
                $.ligerDialog.success(successMessage+"（不存在组织或者尚未完善微信号信息将跳过）","提示信息");
            }
            else{
                $.ligerDialog.err("提示信息","同步用户失败",obj.data.cause);
            }
        })
    }
    
    function syncToLdap(obj){
        var v=$(obj);
        if(v.hasClass("disabled")){
            $.ligerDialog.error('没有权限!');
            return;
        }
        var confirmContent="<font color='red'>与AD服务器同步会将AD的用户同步到系统数据库，您确定要进行同步吗？</font>";
        $.ligerDialog.confirm(confirmContent,function(data){
            if(data){
                sync();
            }else{
                return false;
            }
        });
        sync=function(conf){
            var url=__ctx + "/platform/system/sysUser/syncUser.ht";
            $.ligerDialog.waitting('正在同步AD用户，请等待...');
            $.post(url,function(data){
                $.ligerDialog.closeWaitting();
                var obj=new com.hotent.form.ResultMessage(data);
                if(obj.isSuccess()){
                    $.ligerDialog.success("同步用户成功!","提示信息",function(){
                        location.href=location.href.getNewUrl();
                    });
                }
                else{
                    $.ligerDialog.err("提示信息","同步用户失败!",obj.getMessage());
                }
            })
        };
    }
    
    </script>
    <style>
    	#tb_common_show td, #tb_common_show th {
    		white-space: nowrap;
    	}
    	#tb_common_show  td[data-field='管理'], #tb_common_show  th[data-field='管理'] {
    		min-width: 200px;
    		width: 200px;
    	}
    	.oa-long-select{
    		padding: 0px;
		    border: 0px solid #e5e5e5;
		    background-image: none;
		    background-color: #fff;
		    filter: none;
		    -webkit-box-shadow: none !important;
		    box-shadow: none !important;
		    width: auto !important;
    	}
    .select2-container-multi .select2-choices .select2-search-field input{padding:0px !important;}
	div.select2-container-multi{
		min-width:180px;
		max-width:400px !important;
    	width: auto !important;
    	border: 1px solid #8c9fd6;
    	height: auto !important;
    }
    </style>
</head>
<body class="oa-mw-page">
<%--     <c:set var="SysUser_EXPIRED" value="<%=SysUser.EXPIRED %>" />
    <c:set var="SysUser_UN_EXPIRED" value="<%=SysUser.UN_EXPIRED %>"  />
    <c:set var="SysUser_LOCKED" value="<%=SysUser.LOCKED %>"/>
    <c:set var="SysUser_UN_LOCKED" value="<%=SysUser.UN_LOCKED %>"/> --%>

  <!--   <div id="oa_list_title" class="oa-mgb20 oa-project-title">
        <h2>用户管理列表</h2>
    </div>
 -->
    <c:if test="${isEditor or superAdmin}">
        <div id="oa_search_table_button_wrap" class="oa-head-wrap">
            <a class="oa-button oa-button--primary oa-button--blue" href="edit.ht">添加</a>
        </div>
    </c:if>

   <div id="oa_simple_search" class="oa-simple-search-wrap oa-clear">
		<ul>
	 		<li class="oa-search-item oa-fl oa-mgb10">
                <div class="oa-label">姓名</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input data-type="7"  date_name="condition" type="text" name="s.fullname" class="oa-input" value="${param['fullname']}"/>
               </div>
           </li>
           <li class="oa-search-item oa-fl oa-mgb10">
               <div class="oa-label">员工编号</div>
               <div class="oa-input-wrap oa-mgl100">
                   <input data-type="7"  date_name="condition" type="text" name="s.account" class="oa-input" value="${param['account']}"/>
               </div>
           </li>
           
           <li class="oa-search-item oa-fl oa-mgb10">
               <div class="oa-label">手机</div>
               <div class="oa-input-wrap oa-mgl100">
                   <input data-type="7"  date_name="condition" type="text" name="s.MOBILE" class="oa-input" value="${param['MOBILE']}"/>
               </div>
           </li> 
           <li class="oa-search-item oa-fl oa-mgb10">
				<div class="oa-label">性别</div>
				<div class="oa-select-wrap oa-mgl100">
					<select class="oa-select" name="s.SEX" date_name="condition" value="${param['s.SEX']}">
						<option value="">选择</option>
						<option value="男" <c:if test="${param['s.SEX']=='男'}">selected</c:if>>男</option>
						<option value="女" <c:if test="${param['s.SEX']=='女'}">selected</c:if>>女</option>
					</select> 
				</div>
			</li>
            <li id="userLeaved"  class="oa-search-item oa-fl oa-mgb10" >
               <div class="oa-label">在职状态</div>
               <div class="oa-input-wrap oa-mgl100">
                   <input data-type="6"  date_name="condition" type="text" name="s.userLeaved" class="oa-input" value="离职"/>
               </div>
           </li> 
<%-- 			<li class="oa-search-item oa-fl oa-mgb10">
				<div class="oa-label">生日月份</div>
				<div class="oa-select-wrap oa-mgl100">
					<select class="oa-select" name="s.USERSTATUS" date_name="condition" value="${param['s.USERSTATUS']}">
						<option value="">选择</option>
						<option value="正式员工" <c:if test="${param['s.USERSTATUS']=='正式员工'}">selected</c:if>>正式员工</option>
						<option value="试用员工" <c:if test="${param['s.USERSTATUS']=='试用员工'}">selected</c:if>>试用员工</option>
						<option value="返聘" <c:if test="${param['s.USERSTATUS']=='返聘'}">selected</c:if>>返聘</option>
						<option value="外聘" <c:if test="${param['s.USERSTATUS']=='外聘'}">selected</c:if>>外聘</option>
						<option value="实习" <c:if test="${param['s.USERSTATUS']=='实习'}">selected</c:if>>实习</option>
						<option value="离职" <c:if test="${param['s.USERSTATUS']=='离职'}">selected</c:if>>离职</option>
					</select> 
				</div>
			</li> --%>
			<li class="oa-search-item oa-fl oa-mgb10" >
				<div class="oa-label">入职时间从</div>
				<div class="oa-input-wrap oa-mgl100  oa-input-wrap--ib">
					<input type="text"  data-type="2" name="s.entrydate"  class="oa-input date" validate="{date:true}" value="${param['s.entrydate']}"/>
				</div>
				<span>至</span>
				<div class="oa-input-wrap oa-input-wrap--ib">
					<input type="text" data-type="4" name="s.entrydate"  class="oa-input date" validate="{date:true}" value="${param['s.entrydate']}"/>
				</div>
			</li>
			<li class="oa-search-item oa-fl oa-mgb10" >
				<div class="oa-label">转正时间从</div>
				<div class="oa-input-wrap oa-mgl100  oa-input-wrap--ib">
					<input type="text"  data-type="2" name="s.FORMALDATE"  class="oa-input date" validate="{date:true}" value="${param['s.FORMALDATE']}"/>
				</div>
				<span>至</span>
				<div class="oa-input-wrap oa-input-wrap--ib">
					<input type="text" data-type="4" name="s.FORMALDATE"  class="oa-input date" validate="{date:true}" value="${param['s.FORMALDATE']}"/>
				</div>
			</li>
			<li class="oa-search-item oa-fl oa-mgb10">
				<div class="oa-label">在职状态</div>
				<div class="oa-select-wrap oa-mgl100 oa-long-select">
					<select id="userstatuses" class="oa-select" data-type="8" name="s.USERSTATUS" date_name="condition" multiple="multiple" style="width: 150px !important;"  >
						<!-- <option value="">选择</option> -->
						<option value="正式员工">正式员工</option>
						<option value="试用员工">试用员工</option>
						<option value="返聘">返聘</option>
						<option value="外聘">外聘</option>
						<option value="实习">实习</option>
						<option value="离职">离职</option>
					</select> 
					
				</div> 
			</li>
			<li class="oa-search-item oa-fl oa-mgb10" >
				<div class="oa-label">离职时间从</div>
				<div class="oa-input-wrap oa-mgl100  oa-input-wrap--ib">
					<input type="text"  data-type="2" name="s.RESIGNATIONDATE"  class="oa-input date" validate="{date:true}" value="${param['s.RESIGNATIONDATE']}"/>
				</div>
				<span>至</span>
				<div class="oa-input-wrap oa-input-wrap--ib">
					<input type="text" data-type="4" name="s.RESIGNATIONDATE"  class="oa-input date" validate="{date:true}" value="${param['s.RESIGNATIONDATE']}"/>
				</div>
				<button type="button" id="exportBirthBtn" class="oa-button oa-button--primary oa-button--blue">员工生日</button>
			</li>
			<li id="selectDepartment" class="oa-search-item oa-fl oa-mgb10">
               <div class="oa-label">所属部门</div>
               <div class="oa-input-wrap oa-mgl100 oa-long-select">
                   <%-- <input id="selectOrgName" date_name="condition" data-type="7"  ctltype="selector" class="org oa-input oa-trigger-hidden"   type="text" name="s.orgName" value="${param['orgName']}"/> --%>
               		<select id="departmentIds" class="oa-select" data-type="8" name="s.orgNameID" date_name="condition" multiple="multiple" style="width: 150px !important;">
							<c:forEach items="${sysOrgs }" var="sysOrg">
								<option value="${sysOrg.orgId }">${sysOrg.orgName }</option>
							</c:forEach>
					</select>
               </div>
               <!-- <button id="nowDepartment" class="oa-button-label oa-trigger-hidden-button" type="button">选择部门</button> -->
           </li>
			
       </ul>
    </div>
 	<!-- 高级查询模块 -->
	<jsp:include page="/commons/include/list_common.jsp"></jsp:include>
    <script type="text/javascript">

	var tempUser = 'userlist';
	$(function() {
		var tableParam = {
			columns : [ {
				title : '序号',//显示的title
				formatter :'addNum'
			}, {
				field : 's.account',//查询的字段
				title : '员工编号',//显示的title
				sortable : true,
				formatter : 'getAccountDetail'
			},{
				field : 's.fullname',//查询的字段
				title : '姓名',//显示的title
				sortable : true,
				formatter : 'getNameDetail'
			},{
				field : 's.SEX',//查询的字段
				title : '性别',//显示的title		
				
			},{
				field : 's.education',//查询的字段
				title : '文化程度',//显示的title
				sortable : true
			},{
				field : 's.positional',//查询的字段
				title : '职称名称',//显示的title
				sortable : true
			},{
				field : 's.orgName',//查询的字段
				title : '所属部门',//显示的title
				sortable : true
				
			},{
				field : 's.posName',//查询的字段
				title : '岗位',//显示的title
				sortable : true
			},{
				field : 's.MOBILE',//查询的字段
				title : '手机'//显示的title
			},{
				field : 's.USERSTATUS',//查询的字段
				title : '在职状态',//显示的title
				sortable : true 
			},
			{
				field : 's.CREATETIME',//查询的字段
				title : '创建时间',//显示的title
				sortable : true,
				dateFlag : true,//是否是时间，这个会弹出时间控件选择
				dateFormat : "yyyy-MM-dd"//时间的显示格式 
			
			},{
				field : 's.entrydate',//查询的字段
				title : '入职时间',//显示的title
				sortable : true,
				dateFlag : true,//是否是时间，这个会弹出时间控件选择器
				dateFormat : "yyyy-MM-dd"//时间的显示格式 
			 },{
				field : 's.FORMALDATE',//查询的字段
				title : '转正时间',//显示的title
				sortable : true,
				dateFlag : true,//是否是时间，这个会弹出时间控件选择器
				dateFormat : "yyyy-MM-dd"//时间的显示格式 
			},{
				field : 's.RESIGNATIONDATE',//查询的字段
				title : '离职时间',//显示的title
				sortable : true,
				dateFlag : true,//是否是时间，这个会弹出时间控件选择器
				dateFormat : "yyyy-MM-dd"//时间的显示格式 
			},{
				field : 's.EMAIL',//查询的字段
				title : '邮箱地址',//显示的title
				sortable : true 
			 },{
				field : 's.wechart',//查询的字段
				title : '微 信',//显示的title
				sortable : true 
			},{
		        field : 's.positional_major',//查询的字段
				title : '职称专业',//显示的title
				sortable : true  
			},{
				field : 's.birthday',//查询的字段
				title : '出生日期',//显示的title
				sortable : true,
				dateFlag : true,//是否是时间，这个会弹出时间控件选择器
				dateFormat : "yyyy-MM-dd"//时间的显示格式 
			},{
				field : 's.marriage_state',//查询的字段
				title : '婚姻状况',//显示的title
				sortable : true
			},{
				field : 's.used_name',//查询的字段
				title : '曾用名',//显示的title
				sortable : true  
			},{
				field : 's.nation',//查询的字段
				title : '民族',//显示的title
				sortable : true
			},{
				field : 's.address',//查询的字段
				title : '籍贯',//显示的title
				sortable : true
			},{
				field : 's.startWorkTime',//查询的字段
				title : '参加工作时间',//显示的title
				sortable : true,
				dateFlag : true,//是否是时间，这个会弹出时间控件选择器
				dateFormat : "yyyy-MM-dd"//时间的显示格式 
				
			},{
				field : 's.graduate_school',//查询的字段
				title : '毕业院校',//显示的title
				sortable : true
		 	},{
				field : 's.f_major',//查询的字段
				title : '专业',//显示的title
				sortable : true 
		 	},{
				field : 's.graduate_time',//查询的字段
				title : '毕业时间',//显示的title
				sortable : true,
				dateFormat : "yyyy-MM-dd"//的显示格式 
			},{
				field : 's.identification_id',//查询的字段
				title : '身份证号码',//显示的title
				sortable : true
		 	},{
				field : 's.political_status',//查询的字段
				title : '政治面貌',//显示的title
				sortable : true 
			},{
				field : 's.address_type',//查询的字段
				title : '户籍',//显示的title
				sortable : true
			 },{
				field : 's.year_vacation',//查询的字段
				title : '剩余年假',//显示的title
				sortable : true  
			}, {
				field : 's.infection_history',//查询的字段
				title : '是否有传染病史',//显示的title		
				 
		     },{
				field : 's.disorders_history',//查询的字段
				title : '是否有遗传病史',//显示的title		
				 
		     },{
	    	 	field : 's.social_security_computer_id',//查询的字段
				title : '社会保险电脑号',//显示的title	
				sortable : true 
		     },{
	    	 	field : 's.social_security_num',//查询的字段
				title : '社保登记编号',//显示的title	
				sortable : true 
		     },{
	    	 	field : 's.monthly_wages',//查询的字段
				title : '月工资总额',//显示的title	
				sortable : true 
		     },{
	    	 	field : 's.handedness',//查询的字段
				title : '利手',//显示的title	
				sortable : true 
		     },{ 
	    	 	field : 's.medical_insurance_first',//查询的字段
				title : '医疗险一档',//显示的title		
				 
		     },{ 
	    	 	field : 's.medical_insurance_second',//查询的字段
				title : '医疗险二档',//显示的title		
				 
		     },{ 
	    	 	field : 's.injury_insurance',//查询的字段
				title : '工伤险',//显示的title		
				 
		     },{ 
	    	 	field : 's.unemployment_insurance',//查询的字段
				title : '失业险',//显示的title		
				 
		     },{ 
	    	 	field : 's.endowment_insurance',//查询的字段
				title : '养老险',//显示的title		
				 
		     },{
	    	 	field : 's.hobby',//查询的字段
				title : '特长爱好',//显示的title	
				sortable : true 
		     },{
	    	 	field : 's.home_address',//查询的字段
				title : '户籍所在地',//显示的title	
				sortable : true 
		     },{
		    	 field : 's.spouse_name',//查询的字段
				title : '配偶姓名',//显示的title	
				sortable : true 
		     },{
		    	 field : 's.parents',//查询的字段
				title : '父母居住地',//显示的title	
				sortable : true 
		     },{
		    	 field : 's.spouse_identification_id',//查询的字段
				title : '配偶身份证号码',//显示的title	
				sortable : true 
		     },{
		    	 field : 's.spouse_address',//查询的字段
				title : '配偶居住地',//显示的title	
				sortable : true 
		     },{
		    	 field : 's.link_address',//查询的字段
				title : '通讯地址',//显示的title	
				sortable : true 
		     },{
		    	 field : 's.sjdh',//查询的字段
				title : '手机短号',//显示的title	
				sortable : true 
		     },{
		    	field : 's.emergency_link_person',//查询的字段
				title : '紧急联系人',//显示的title	
				sortable : true 
		     },{
		    	field : 's.emergency_link_person_phone',//查询的字段
				title : '紧急联系人电话',//显示的title	
				sortable : true 
		     },{
		    	 field : 's.BOC_id',//查询的字段
				title : '交行卡号',//显示的title	
				sortable : true 
		     },{
		    	field : 's.ICBC_id',//查询的字段
				title : '工行卡号',//显示的title	
				sortable : true 
		     },{
		    	field : 's.QQ',//查询的字段
				title : 'QQ号码',//显示的title	
				sortable : true 
		     },{
		    	field : 's.age',//查询的字段
				title : '年龄',//显示的title	
				sortable : true 
		     },{
				field : '管理',//即使不去查询这个字段也必须。用来记录改变列的顺序用。如果不去查询必须带nosearch字段
				title : '管理',
				nosearch : true,//不去数据库查询
				formatter : 'edit'//显示的时候执行edit函数
			}],
			uniqueId : "s.userId",//唯一主键字段
			type : "userInfomationsList",//类型，用户存储在数据库中，每个项目的不同页面保证唯一
			tableName :'user_infomations s', //表明
			userId : '${sessionScope.LOGIN_USER_ID }', //用户ID
			sortName : 's.account',//初始化排序
		    sortOrder : 'asc'//初始化排序
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
		
		initCommonTable(tableParam,function(){
			var orgId = '<%=session.getAttribute("chooseOrgId") %>';
			var orgName = '<%=session.getAttribute("orgName") %>';
			if((orgId!=='null')&&(-1!=orgId)){
				departmentIds.val(orgId).trigger("change");
			    totalSearch();
			    $("select[name='s.orgNameID']").attr("disabled",true);
			    $("#selectDepartment").hide();
		    }else{
		    	totalSearch();
		    }
			$("#userLeaved").attr("style","display:none");
		});
		
		
	});
	
	function addNum(value, row, index) {  
		var page = $('#tb_common_show').bootstrapTable("getPage"); 
        return page.pageSize * (page.pageNumber - 1) + index + 1;   
    } 
	
	function getAccountDetail(value, row, index) { 
		var id = row["s.userId"];
		//value = getAccount(value);
		var html =  '<a href=get.ht?userId='+id+'>'+value+'</a>';
		return html;
	}
	
	function getNameDetail(value, row, index) { 
		var id = row["s.userId"];
		var html =  '<a target="_blank" href=get.ht?userId='+id+'>'+value+'</a>';
		return html;
	}
	
	function edit(value, row, index) {
		var isorg = '${isOrg}';
		var isEditor = '${isEditor}';
		var superAdmin = '${superAdmin}';
		var html = "";
		if(isorg=='true'){
		 html = '<a css="link edit" href="editDepartment.ht?userId=' + row["s.userId"]+ '" >&nbsp编辑&nbsp</a>';
		}
		if(isEditor=='true'){
			html = '<a css="link edit" href="edit.ht?userId=' + row["s.userId"]+ '" >&nbsp编辑&nbsp</a>'+
			'<a css="link primary" href="javascript:;" onclick="openUserUnder(' + row["s.userId"]+ ',this)">下属管理</a>'+
            '<a class="oa-del" href="del.ht?userId=' + row["s.userId"]+ '">&nbsp删除 </a>';
		}
		if(superAdmin=='true'){
			var accountstr = row["s.account"];
			//accountstr = getAccount(accountstr);
			html+=
				'<a css="link edit" href="edit.ht?userId=' + row["s.userId"]+ '" >&nbsp编辑&nbsp</a>'+
				'<a css="link primary" href="javascript:;" onclick="openUserUnder(' + row["s.userId"]+ ',this)">下属管理</a>'+
	            '<a class="oa-del" href="del.ht?userId=' + row["s.userId"]+ '">&nbsp删除 </a>'+
	            '<a alias="switch" css="link switchuser" target="_top" href="${ctx}/j_spring_security_switch_user?j_username=' + accountstr+ '" >&nbsp切换用户</a>'+
	            '<a href="resetPwdView.ht?userId=' + row["s.userId"]+ '">&nbsp重置密码</a>';
		}
		if(html==""){
			html="-";
		}
		return html;
	}	
	//根据 accountNum 拼接account added by yizehua 2017/11/21 18点42分
	function getAccount(value){
		if(value/100<1){
			if(value%100<10){
				value="0"+value;
			}
			value="0"+value;
		}
		value="A"+value;
		return value;
	}
 	function setSelect(value, row, index) {
		if (value == 0) {
			return "女";
		} 
		if (value == 1) {
			return "男";
		}
		
	} 
 	
 	function setStatus(value, row, index){
 		 if (!value) {
			return "否";
		} 
 		if (value == "0") {
			return "否";
		} 
		if (value == "1") {
			return "是";
		} 
 	}
 	
 	/* $("#selectOrgName").on("change",function(){
 		var selectOrgNameID = $("input[name='s.orgNameID']").val();
 		$.post("/platform/system/sysUser/getSysOrgByOrgId.ht",{orgId:selectOrgNameID},function(result){
			$("input[name='s.orgNameID']").attr("class","oa-input");
 			if(result!=null && result.orgType==4){
 				var html='<input name="s.orgId" type="hidden"  class="oa-input" value="'+result.orgId+'">'
 				 $("input[name='s.orgNameID']").val("");
 				 $("input[name='s.orgNameID']").parent().append(html);
 			}else{
 				$($("input[name='s.orgNameID']").parent().find("input").eq(2)).remove();
 			}
 			
 		});
 	}); */
 	
 	$("select[name = 's.USERSTATUS']").on("change",function(){
 		var type = $(this).val();
 		if(!type){
 			$("input[name='s.userLeaved']").val("离职");
 		}else{
 			$("input[name='s.userLeaved']").val("");
 		}
 	});
</script>
<script>
    var isEditor = '${isEditor}';
    var superAdmin = '${superAdmin}';
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
    });
</script>
<script type="text/javascript">
	$(function(){
		$("#exportBirthBtn").click(function(){
			$.ligerDialog.open({ url: '/platform/system/sysUser/exportBirthDialog.ht',title:'导出员工生日',width:500,height: 350, isResize:true,
				 buttons: [{ text: '确定', onclick: function (item, dialog) {//通过dialog.frame去获取弹出层页面的数据
					 			dialog.frame.$("#birthForm").submit();
				 			}
		      	  },
		          { text: '取消', onclick: function (item, dialog) { dialog.close(); } }]
			 });
		});
		
		
		$("#userstatuses").select2({
			placeholder : "请选择",
			allowClear : true,
			formatNoMatches: function(term) {
				return "";
			}
		});
	});
</script>
</body>
</html>


