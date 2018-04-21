<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户表明细</title>
<link href="${ctx}/styles/default/css/form.css" rel="stylesheet" />
<%@include file="/commons/include/form.jsp" %>
<%@include file="/codegen/include/customForm.jsp" %>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/AttachMent.js"></script>
<%@include file="/commons/include/ueditor.jsp" %>
<script type="text/javascript" src="${ctx}/servlet/ValidJs?form=sysUser"></script>
<f:link href="tree/zTreeStyle.css"></f:link>
<script type="text/javascript" src="${ctx}/js/tree/jquery.ztree.js"></script>
<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerTab.js" ></script>
<script type="text/javascript" src="${ctx}/js/hotent/displaytag.js" ></script>
<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerWindow.js" ></script>
<script type="text/javascript"  src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/FlexUploadDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/handlebars/handlebars.min.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/HtmlUploadDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/OfficePlugin.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/rule.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery/plugins/jquery.attach.js" ></script>
<script type="text/javascript" src="${ctx}/js/ntkoWebSign/WebSignPlugin.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/FormUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/ReadOnlyQuery.js"></script>
<script type="text/javascript" src="${ctx}/js/pictureShow/PictureShowPlugin.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/FormMath.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/Cascadequery.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/TablePermission.js"></script>
<script type="text/javascript">

    var dialog =null;
    try{
        dialog = frameElement.dialog;
    }catch(e){
        
    }
    $(function() {
        var h = $('body').height();
        $("#tabMyInfo").ligerTab({});
    });
</script>
<style>	
    #tabMyInfo .l-tab-links {
        border: 1px solid #eceff8;
        background: #eceff8;
    }
    .l-tab-links li {
        cursor: pointer;
        float: left;
        font-size: 14px;
        height: 42px;
        line-height: 42px;
        margin: 0;
        overflow: hidden;
        position: relative;
    }
    #tabMyInfo .l-tab-links li.l-selected a {
        display: block;
        padding: 0 10px;
        border-radius: 0;
    }
    #tabMyInfo .l-tab-links li a {
        color: #657386;
        padding: 0 6px;
        font-size: 13px;
    }

    .oa-table--second td,
    .oa-table--second th{
        padding: 10px 20px;
        border: 1px solid #eceff8;
    }
    .oa-table--second th{
        font-weight: bold;
        width: 100px;
        background: #f6f7fb;
    }
    .profile{
        text-align: center;
        margin: 20px auto;
        width: 100px;
        height: 100px;
        line-height: 100px;
        border-radius: 50%;
        background: 50%/cover;
        background-color: #fff;
    }
    .profile img{
        max-width: 100%;
        max-height: 100%;
    }
    .my-data-info {
    	text-align: center;
    	font-size: 16px !important;
    }
</style>
</head>
<body>
    
    <div class="oa-pd20">
        <c:if test="${canReturn==0 && openType!='detail'}">
            <a class="oa-button oa-button--primary oa-button--blue" href="${returnUrl}">返回</a>
            <a class="oa-button oa-button--primary oa-button--blue" target="_blank" href="${ctx}/platform/system/sysUser/detail.ht?userId=${sysUser.userId}">打印简历</a>
        </c:if>
        <c:if test="${openType=='detail'}">
            <a class="oa-button oa-button--primary oa-button--blue" onclick="dialog.close();">关闭</a>
        </c:if>
        <c:if test="${canReturn==1}">
            <a class="oa-button oa-button--primary oa-button--blue" href="${ctx}/platform/system/sysUser/editCommon.ht">修改信息</a>
        </c:if>
    </div>
    
    <div id="tabMyInfo">
		<div title="账号信息" tabid="accountdetail" icon="${ctx}/styles/default/images/resicon/user.gif">
			<div class="profile">
                <img 
                    <c:choose>
	                    <c:when test="${sysUser.sex==1}">
	                        onerror="this.src='/commons/image/default_image_male.jpg'"
	                    </c:when>
	                    <c:otherwise>
	                        onerror="this.src='/commons/image/default_image_female.jpg'" 
	                    </c:otherwise>
                    </c:choose>  src="${pictureLoad}" alt="个人相片"/>
            </div>
			<table class="oa-table--default oa-table--second">
				<tr>
                    <th>帐 号:</th>
                    <td>${sysUser.account}</td>
                    <th>电 话:</th>
                    <td>${sysUser.phone}</td>
                </tr>
                <tr>
                    <th>用户姓名:</th>
                    <td>${sysUser.fullname}</td>
    
                    <th>用户性别:</th>
                    <td>
                        <c:choose>
                            <c:when test="${sysUser.sex==1}">男</c:when>
                            <c:otherwise>女</c:otherwise>
                        </c:choose>
                    </td>
                </tr>
                <tr>
                    <th>是否锁定:</th>
                    <td>
                        <c:choose>
                            <c:when test="${sysUser.isLock==1}">已锁定</c:when>
                            <c:otherwise>未锁定</c:otherwise>
                        </c:choose>
                    </td>
   
                    <th>是否过期:</th>
                    <td>
                        <c:choose>
                            <c:when test="${sysUser.isExpired==1}">已过期</c:when>
                            <c:otherwise>未过期</c:otherwise>
                        </c:choose>
                    </td>
                </tr>
                <tr>
                    <th>当前状态:</th>
                    <td>
                        <c:choose>
                            <c:when test="${sysUser.status==1}">激活</c:when>
                            <c:when test="${sysUser.status==0}">禁用</c:when>
                            <c:otherwise>删除</c:otherwise>
                        </c:choose>
                    </td>
  
                    <th>创建时间:</th>
                    <td>${f:shortDate(sysUser.createtime)}</td>
                </tr>
			</table>
		</div>
		
        <div title="基本信息" tabid="userdetail" icon="${ctx}/styles/default/images/resicon/user.gif">           
            <h3 class="oa-h3">资料信息</h3>
            <table class="oa-table--default oa-table--second">               
               <tr>
                    <th>劳动合同开始时间:</th>
                    <td>${f:shortDate(sysUser.laborcontstarttime)}</td>
  
                    <th>劳动合同结束时间:</th>
                    <td>${f:shortDate(sysUser.laborcontendtime)}</td>
                </tr>
               <tr>
                    <th>入职时间:</th>
                    <td>${f:shortDate(sysUser.entryDate)}</td>
  
                    <th>转正时间:</th>
                    <td>${f:shortDate(sysUser.formalDate)}</td>
                </tr>
                <tr>
                    <th>离职时间:</th>
                    <td>${f:shortDate(sysUser.resignationDate)}</td>
  
                    <th>邮箱地址:</th>
                    <td>${sysUser.email}</td>
                </tr>
                <tr>
                    <th>微 信: </th>
                    <td>${sysUser.weixinid}</td>

                    <th>手 机:</th>
                    <td>${sysUser.mobile}</td>
                </tr>
                <tr>
                    <th>专业:</th>
                    <td >${userInfomation.major}</td>
                    <th>职称专业:</th>
                    <td >${userInfomation.positional_major}</td>
                </tr>
                <tr>
                    <th>职称名称:</th>
                    <td id="positional">${userInfomation.positional}</td>

                    <th>出生日期:</th>
                    <td >${f:shortDate(userInfomation.birthday)}</td>
                </tr>
                <tr>
                    <th>婚姻状况:</th>
                    <td >${userInfomation.marriage_state}</td>
 
                    <th>曾用名:</th>
                    <td >${userInfomation.used_name}</td>
                </tr>
                <tr>
                    <th>民族:</th>
                    <td >${userInfomation.nation}</td>

                    <th>籍贯:</th>
                    <td >${userInfomation.address}</td>
                </tr>
                <tr>
                    <th>文化程度:</th>
                    <td >${userInfomation.education}</td>

                    <th>参加工作时间:</th>
                    <td >${f:shortDate(userInfomation.start_work_time)}</td>
                </tr>
                <tr>
                    <th>毕业院校:</th>
                    <td >${userInfomation.graduate_school}</td>
 					
 					<th>毕业时间:</th>
                    <td >${f:shortDate(userInfomation.graduate_time)}</td>
                </tr>
                <tr>
                    <th>身份证号码:</th>
                    <td >${userInfomation.identification_id}</td>

					<th>政治面貌:</th>
                    <td >${userInfomation.political_status}</td>
                    
                </tr><tr>
                    <th>户籍:</th>
                    <td >${userInfomation.address_type}</td>

					<th>剩余年假:</th>
                    <td >${userInfomation.yearVacation}</td>
                    
                </tr>
                <tr>
                    <th>是否有传染病史:</th>
                    <td>
                        <c:choose>
                            <c:when test="${userInfomation.infection_history==1}">是</c:when>
                            <c:otherwise>否</c:otherwise>
                        </c:choose>
                    </td>
  
                    <th>是否有遗传病史:</th>
                    <td>
                        <c:choose>
                            <c:when test="${userInfomation.disorders_history==1}">是</c:when>
                            <c:otherwise>否</c:otherwise>
                        </c:choose>
                    </td>
                </tr>
                <tr>
                    <th>社会保险电脑号:</th>
                    <td >${userInfomation.social_security_computer_id}</td>
   
                    <th>社保登记编号:</th>
                    <td >${userInfomation.social_security_num}</td>
                </tr>
                <tr>
                    <th>月工资总额:</th>
                    <td >${userInfomation.monthly_wages}</td>
  
                    <th>利手:</th>
                    <td >${userInfomation.handedness}</td>
                </tr>
                <tr>
                    <th>医疗险一档:</th>
                    <td>
                        <c:choose>
                            <c:when test="${userInfomation.medical_insurance_first==1}">是</c:when>
                            <c:otherwise>否</c:otherwise>
                        </c:choose>
                    </td>
    
                    <th>医疗险二档:</th>
                    <td>
                        <c:choose>
                            <c:when test="${userInfomation.medical_insurance_second==1}">是</c:when>
                            <c:otherwise>否</c:otherwise>
                        </c:choose>
                    </td>
                </tr>
                <tr>
                    <th>工伤险:</th>
                    <td>
                        <c:choose>
                            <c:when test="${userInfomation.injury_insurance==1}">是</c:when>
                            <c:otherwise>否</c:otherwise>
                        </c:choose>
                    </td>
         
                    <th>失业险:</th>
                    <td>
                        <c:choose>
                            <c:when test="${userInfomation.unemployment_insurance==1}">是</c:when>
                            <c:otherwise>否</c:otherwise>
                        </c:choose>
                    </td>
                </tr>
                <tr>
                    <th>养老险:</th>
                    <td>
                        <c:choose>
                            <c:when test="${userInfomation.endowment_insurance==1}">是</c:when>
                            <c:otherwise>否</c:otherwise>
                        </c:choose>
                    </td>
        
                    <th>特长爱好:</th>
                    <td >${userInfomation.hobby}</td>
                </tr>
                <tr>
                    <th>户籍所在地:</th>
                    <td >${userInfomation.home_address}</td>
 
                    <th>配偶姓名:</th>
                    <td >${userInfomation.spouse_name}</td>
                </tr>
                <tr>
                    <th>父母居住地:</th>
                    <td >${userInfomation.parents}</td>
     
                    <th>配偶身份证号码:</th>
                    <td >${userInfomation.spouse_identification_id}</td>
                </tr>
                <tr>
                    <th>配偶居住地:</th>
                    <td >${userInfomation.spouse_address}</td>
      
                    <th>通讯地址:</th>
                    <td >${userInfomation.link_address}</td>
                </tr>
                <tr>
                    <th>手机短号:</th>
                    <td >${userInfomation.sjdh}</td>
         
                    <th>紧急联系人:</th>
                    <td >${userInfomation.emergency_link_person}</td>
                </tr>
                <tr>
                    <th>交行卡号:</th>
                    <td >${userInfomation.BOC_id}</td>
         
                    <th>工资卡号:</th>
                    <td >${userInfomation.ICBC_id}</td>
                </tr>
                <tr>
                    <th>紧急联系人电话:</th>
                    <td >${userInfomation.emergency_link_person_phone}</td>
           
                    <th>QQ号码:</th>
                    <td >${userInfomation.QQ}</td>
                </tr>
            </table>  
            <c:if test="${isOtherLink==0}">
            	<h3 class="oa-h3">所在部门</h3>
                <table class="oa-table--default">
                    <thead>
                        <th>组织名称</th>
                        <th>是否主组织</th>
                        <th>主要负责人</th>
                    </thead>
                    <c:forEach items="${orgList}" var="orgItem" varStatus="status">
                        <tr class="${status.index%2==0?'odd':'even'}">
                            <td>
                            	<c:choose>
                            		<c:when test="${fn:indexOf(orgItem.orgPathName,'/深水咨询/')>-1}">
                            			${fn:replace(orgItem.orgPathName,"/深水咨询/","")}
                            		</c:when>
                            		<c:otherwise>
                            			<c:if test="${fn:indexOf(orgItem.orgPathName,'/')==0}">
                            				${fn:replace(orgItem.orgPathName,"/","")}
                            			</c:if>
                            		</c:otherwise>
                            	</c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${orgItem.isPrimary==1}">是</c:when>
                                    <c:otherwise>否</c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                               <c:choose>
                                    <c:when test="${orgItem.isCharge==1}">是</c:when>
                                    <c:otherwise>否</c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty orgList || fn:length(orgList)<=0}">
                        <tr>
                            <td colspan="3">当前没有记录。</td>
                        </tr>
                    </c:if>
                </table>
                <h3 class="oa-h3">岗位</h3>
                <table class="oa-table--default">
                    <thead>
                        <th>岗位名称</th>
                        <th>是否主岗位</th>
                    </thead>
                    <c:forEach items="${posList}" var="posItem" varStatus="status">
                        <tr class="${status.index%2==0?'odd':'even'}">
                            <td>${posItem.posName}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${posItem.isPrimary==1}">是</c:when>
                                    <c:otherwise>否</c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty posList || fn:length(posList)<=0}">
                        <tr>
                            <td colspan="2">当前没有记录。</td>
                        </tr>
                    </c:if>
                </table>
	            <%-- <div title="所属角色" tabid="roldetail" icon="${ctx}/styles/default/images/resicon/customer.png">
	                <table class="oa-table--default">
	                    <thead>
	                        <th>角色名称</th>
	                        <th>子系统名称</th>
	                    </thead>
	                    <c:forEach items="${roleList}" var="rolItem" varStatus="status">
	                        <tr class="${status.index%2==0?'odd':'even'}">
	                            <td>${rolItem.roleName}</td>
	                            <td>${rolItem.systemName}</td>
	                        </tr>
	                    </c:forEach>
	                    <c:if test="${empty roleList || fn:length(roleList)<=0}">
	                        <tr>
	                            <td colspan="2">当前没有记录。</td>
	                        </tr>
	                    </c:if>
	                </table>
	            </div> 
	            <div title="所属组织角色" tabid="orgRoldetail" icon="${ctx}/styles/default/images/resicon/customer.png">
	
	                <table class="oa-table--default">
	                    <thead>
	                        <th>组织</th>
	                        <th>角色</th>
	                    </thead>
	                    <c:forEach items="${sysOrgRoles}" var="sysOrgRole">
	                        <tr>
	                            <td>${sysOrgRole.key.orgName}</td>
	                            <td>
	                                <c:forEach items="${sysOrgRole.value}" var="sysRole">
	                                    ${sysRole.roleName} 
	                                </c:forEach>
	                            </td>
	                        </tr>
	                    </c:forEach>
	                    <c:if test="${empty sysOrgRoles || fn:length(sysOrgRoles)<=0}">
	                        <tr>
	                            <td colspan="2">当前没有记录。</td>
	                        </tr>
	                    </c:if>
	                </table>
	
	            </div>--%>
        	</c:if>                                                                         
        </div>

        <div title="职称及执业资格 " tabid="otherInfo" icon="${ctx}/styles/default/images/resicon/user.gif">
            <h3 class="oa-h3">职称专业</h3>
            <table class="oa-table--default"> 
                <thead>
                    <tr> 
                        <th>职称编号</th> 
                        <th>职称名称</th> 
                        <th>发证机构</th> 
                        <th>职称专业</th> 
                        <th>取得职称时间</th> 
                        <th>附件</th> 
                    </tr> 
                </thead>
                <c:forEach items="${userInfomation.entryProfessionalList}" var="entryProfessional" varStatus="status">
                    <tr> 
                        <td>${entryProfessional.num}</td> 
                        <td>${entryProfessional.name}</td> 
                        <td>${entryProfessional.organization}</td> 
                        <td>${entryProfessional.major}</td> 
                        <td><fmt:formatDate value="${entryProfessional.achieve_time}" pattern='yyyy-MM-dd'/></td> 
                        <td><input ctltype="attachment" name="s:entryProfessional:attachment" isdirectupload="0"  value='${entryProfessional.attachment}' validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}" /></td> 
                    </tr>
                </c:forEach>
                <c:if test="${empty userInfomation.entryProfessionalList || fn:length(userInfomation.entryProfessionalList)<=0}">
                    <tr>
                        <td colspan="6">当前没有记录。</td>
                    </tr>
                </c:if>
            </table>
            <h3 class="oa-h3">执业资格</h3>
            <table class="oa-table--default"> 
                <thead>
                    <tr> 
                        <th>执业资格编号</th> 
                        <th>执业资格名称</th> 
                        <th>发证机构</th> 
                        <th>执业资格证专业</th> 
                        <th>取得证书时间</th> 
                        <th>是否转入本公司</th> 
                        <th>附件</th> 
                    </tr>                    
                </thead>
                <c:forEach items="${userInfomation.entryVocationQualificationList}" var="entryVocationQualification" varStatus="status">
                    <tr>
                        <td>${entryVocationQualification.num}</td>
                        <td>${entryVocationQualification.name}</td>
                        <td>${entryVocationQualification.organization}</td>
                        <td>${entryVocationQualification.major}</td>
                        <td><fmt:formatDate value="${entryVocationQualification.achieve_time}" pattern='yyyy-MM-dd'/></td>
                        <td>
                            <c:choose>
                                <c:when test="${entryVocationQualification.switchs==0}">否</c:when>
                                <c:otherwise>是</c:otherwise>
                            </c:choose>
                        </td>
                        <td><input ctltype="attachment" name="s:entryVocationQualification:attachment" isdirectupload="0"  value='${entryVocationQualification.attachment}' validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}" /></td>
                    </tr>
                </c:forEach>
                <c:if test="${empty userInfomation.entryVocationQualificationList || fn:length(userInfomation.entryVocationQualificationList)<=0}">
                    <tr>
                        <td colspan="7">当前没有记录。</td>
                    </tr>
                </c:if>
            </table> 
            <h3 class="oa-h3">注册资格</h3>
            <table class="oa-table--default"> 
                <thead>
                    <tr> 
                        <th>注册证书编号</th> 
                        <th>注册专业</th> 
                        <th>发证机构</th> 
                        <th>发证日期</th> 
                        <th>证书有效期</th> 
                        <th>是否注册</th> 
                        <th>附件</th> 
                    </tr>     
                </thead>
                <c:forEach items="${userInfomation.registrationQualificationList}" var="registrationQualification" varStatus="status">
                    <tr> 
                        <td>${registrationQualification.certificate_regist_id}</td> 
                        <td>${registrationQualification.regist_major}</td> 
                        <td>${registrationQualification.regist_send_unit}</td> 
                        <td><fmt:formatDate value="${registrationQualification.get_date}" pattern='yyyy-MM-dd'/></td>
                        <td><fmt:formatDate value="${registrationQualification.last_effectice_date}" pattern='yyyy-MM-dd'/></td> 
                        <td>
                            <c:choose>
                                <c:when test="${registrationQualification.isregist==0}">否</c:when>
                                <c:otherwise>是</c:otherwise>
                            </c:choose>
                        </td>
                        <td><input ctltype="attachment" name="s:registrationQualification:regist_attachment" isdirectupload="0"  value='${registrationQualification.regist_attachment}' validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}" /></td> 
                    </tr>
                </c:forEach>
                <c:if test="${empty userInfomation.registrationQualificationList || fn:length(userInfomation.registrationQualificationList)<=0}">
                    <tr>
                        <td colspan="7">当前没有记录。</td>
                    </tr>
                </c:if>
            </table> 
			 <h3 class="oa-h3">从业资格信息</h3>
            <table class="oa-table--default"> 
                <thead>
                    <tr> 
                    	<th>证书类型</th> 
                        <th>证书编号</th> 
                        <th>发证日期</th> 
                        <th>有效日期</th> 
                        <th>第一专业</th>
                        <th>第二专业</th> 
                        <th>附件</th> 
                    </tr>     
                </thead>
                <c:forEach items="${userInfomation.occupationalRequirementList}" var="occupationalRequirement" varStatus="status">
                    <tr> 
                        <td>
                        	     
                        	 <c:choose>
                                <c:when test="${occupationalRequirement.occ_type=='1'}">三类人员安全生产考核合格证</c:when>
								<c:when test="${occupationalRequirement.occ_type=='2'}">深圳市监理员</c:when> 
								<c:when test="${occupationalRequirement.occ_type=='3'}">深圳市监理工程师</c:when> 
								<c:when test="${occupationalRequirement.occ_type=='4'}">水利部监理工程师信用手册</c:when> 
								<c:when test="${occupationalRequirement.occ_type=='5'}">建设部监理工程师信用手册</c:when> 
								<c:when test="${occupationalRequirement.occ_type=='6'}">深圳市档案员</c:when> 
								<c:otherwise></c:otherwise>
                            </c:choose>
                        </td> 
                        <td>${occupationalRequirement.occ_certificate_id}</td> 
                        <td><fmt:formatDate value="${occupationalRequirement.occ_get_date}" pattern='yyyy-MM-dd'/></td> 
                        <td><fmt:formatDate value="${occupationalRequirement.occ_period_of_validity}" pattern='yyyy-MM-dd'/></td>
                        <td>${occupationalRequirement.occ_secondMajor}</td> 
						<td>${occupationalRequirement.occ_major}</td>    
                        <td><input ctltype="attachment" name="s:occupationalRequirement:occ_attachment" isdirectupload="0"  value='${occupationalRequirement.occ_attachment}' validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}" /></td> 
                    </tr>
                </c:forEach>
                <c:if test="${empty userInfomation.occupationalRequirementList || fn:length(userInfomation.occupationalRequirementList)<=0}">
                    <tr>
                        <td colspan="7">当前没有记录。</td>
                    </tr>
                </c:if>
            </table> 
       </div>
        
        <div title="学习及工作经历 " tabid="studyAndworkInfo" icon="${ctx}/styles/default/images/resicon/user.gif">
        	<h3 class="oa-h3">学习经历（包括所接受的专业培训，大学起填）</h3>
            <table class="oa-table--default"> 
                <thead>
                    <tr> 
                        <th>开始日期</th> 
                        <th>结束日期</th> 
                        <th>就读学校或机构</th> 
                        <th>专业</th> 
                        <th>所获证书、学位、奖励</th> 
                        <th>附件</th> 
                    </tr> 
                </thead>
                <c:forEach items="${userInfomation.entryEducationHistoryList}" var="entryEducationHistory" varStatus="status">
                    <tr> 
                        <td>${f:shortDate(entryEducationHistory.startDate)}</td> 
                        <td>${f:shortDate(entryEducationHistory.endDate)}</td> 
                        <td>${entryEducationHistory.education_school}</td> 
                        <td>${entryEducationHistory.major}</td> 
                        <td>${entryEducationHistory.achieve_certificate}</td> 
                        <td><input ctltype="attachment" name="s:entryEducationHistory:attachment" isdirectupload="0"  value='${entryEducationHistory.attachment}' validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}" /></td> 
                    </tr>
                </c:forEach>
                <c:if test="${empty userInfomation.entryEducationHistoryList || fn:length(userInfomation.entryEducationHistoryList)<=0}">
                    <tr>
                        <td colspan="5">当前没有记录。</td>
                    </tr>
                </c:if>
            </table> 
            <h3 class="oa-h3">主要工作经历</h3>
            <table class="oa-table--default"> 
                <thead>
                    <tr> 
                        <th>开始日期</th> 
                        <th>结束日期</th>  
                        <th>工作单位</th> 
                        <th>部门岗位</th> 
                        <th>技术职务</th> 
                        <th>附件</th> 
                    </tr> 
                </thead>
                <c:forEach items="${userInfomation.entryWorkHistoryList}" var="entryWorkHistory" varStatus="status">
                    <tr> 
                        <td>${f:shortDate(entryWorkHistory.startDate)}</td> 
                        <td>${f:shortDate(entryWorkHistory.endDate)}</td>
                        <td>${entryWorkHistory.workplace}</td> 
                        <td>${entryWorkHistory.department_post}</td> 
                        <td>${entryWorkHistory.positions}</td> 
                        <td><input ctltype="attachment" name="s:entryWorkHistory:attachment" isdirectupload="0"  value='${entryWorkHistory.attachment}' validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}" /></td> 
                    </tr>
                </c:forEach>
                <c:if test="${empty userInfomation.entryWorkHistoryList || fn:length(userInfomation.entryWorkHistoryList)<=0}">
                    <tr>
                        <td colspan="5">当前没有记录。</td>
                    </tr>
                </c:if>
            </table>
        </div>
        
        <div title="家庭成员主要关系 " tabid="familyInfo" icon="${ctx}/styles/default/images/resicon/user.gif">
        	<h3 class="oa-h3">家庭成员及主要社会关系</h3>
            <table class="oa-table--default"> 
                <thead>
                    <tr> 
                        <th>关系</th> 
                        <th>姓名</th> 
                        <th>性别</th> 
                        <th>出生年</th> 
                        <th>工作单位</th> 
                        <th>联系电话</th> 
                        <th>附件</th> 
                    </tr> 
                </thead>
                <c:forEach items="${userInfomation.entryFamilyList}" var="entryFamily" varStatus="status">
                    <tr> 
                        <td>${entryFamily.relationship}</td> 
                        <td>${entryFamily.person_name}</td>
                        <td>
                            <c:choose>
                                <c:when test="${entryFamily.person_sex==1}">男</c:when>
                                <c:otherwise>女</c:otherwise>
                            </c:choose>
                        </td> 
                        <td>${entryFamily.person_birthday_year}</td> 
                        <td>${entryFamily.person_workspace}</td> 
                        <td>${entryFamily.person_phone}</td> 
                        <td><input ctltype="attachment" name="s:entryVocationQualification:attachment" isdirectupload="0"  value='${entryFamily.person_attachment}' validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}" /></td> 
                    </tr>
                </c:forEach>
                <c:if test="${empty userInfomation.entryFamilyList || fn:length(userInfomation.entryFamilyList)<=0}">
                    <tr>
                        <td colspan="7">当前没有记录。</td>
                    </tr>
                </c:if>
            </table>
            <h3 class="oa-h3">子女信息</h3>
            <table class="oa-table--default"> 
                <thead>
                    <tr> 
                        <th>姓名</th> 
                        <th>性别</th> 
                        <th>出生日期</th> 
                    </tr> 
                </thead>
                <c:forEach items="${userInfomation.entryChildrenList}" var="entryChildren" varStatus="status">
                    <tr> 
                        <td>${entryChildren.name}</td> 
                        <td>${entryChildren.sex}</td> 
                        <td><fmt:formatDate value="${entryChildren.birthday}" pattern='yyyy-MM-dd'/></td> 
                    </tr>
                </c:forEach>
                <c:if test="${empty userInfomation.entryChildrenList || fn:length(userInfomation.entryChildrenList)<=0}">
                    <tr>
                        <td colspan="3">当前没有记录。</td>
                    </tr>
                </c:if>
            </table>
        </div>        
       
        <div title="保管资产" tabid="asset" icon="${ctx}/styles/default/images/resicon/article.gif">
            <display:table  name="assetRegistrationList" id="assetRegistrationItem" requestURI="list.ht" sort="external" class="oa-table--default">
                <display:column title="资产编号">${assetRegistrationItem.asset_id }</display:column>
                <display:column title="资产名称">${assetRegistrationItem.asset_name }</display:column>
                <display:column title="规格型号">${assetRegistrationItem.specification }</display:column>
                <display:column title="资产分类代码">${assetRegistrationItem.asset_code }</display:column>
                <display:column title="资产类别">${assetRegistrationItem.version }</display:column>
                <display:column title="取得方式">
					<c:if test="${assetRegistrationItem.get_type==0 }">领用旧设备</c:if>
					<c:if test="${assetRegistrationItem.get_type==1 }">委托办公室购买</c:if>
					<c:if test="${assetRegistrationItem.get_type==2 }">项目部自行购买</c:if>
				</display:column>
                <display:column title="取得日期">
                    <fmt:formatDate value="${assetRegistrationItem.get_date }"   pattern="yyyy-MM-dd" type="date" dateStyle="long" />
                </display:column>
                <display:column title="使用部门">${assetRegistrationItem.use_department }</display:column>
                <display:column title="保管人">${assetRegistrationItem.care_person }</display:column>
                <display:column title="使用日期">
                    <fmt:formatDate value="${assetRegistrationItem.use_date }"   pattern="yyyy-MM-dd" type="date" dateStyle="long" />
                </display:column>
                <display:column title="数量">${assetRegistrationItem.number }</display:column>
                <display:column title="使用状况">
                    <c:if test="${assetRegistrationItem.state ==0}">在用</c:if>
                    <c:if test="${assetRegistrationItem.state ==1}">报废</c:if>
                </display:column>
            </display:table>
        </div>
        
        <div title="借阅合同" tabid="borrow" icon="${ctx}/styles/default/images/resicon/article.gif">
            <display:table  name="borrowConList" id="borrowItem" requestURI="list.ht" sort="external" class="oa-table--default">
                <display:column title="合同编号">${borrowItem.contract_num }</display:column>
                <display:column title="合同名称">${borrowItem.contract_name }</display:column>
                <display:column title="借阅日期">
                    <fmt:formatDate value="${borrowItem.borrow_date }"   pattern="yyyy-MM-dd" type="date" dateStyle="long" />
                </display:column>
                <display:column title="预计归还日期">
                    <fmt:formatDate value="${borrowItem.expected_return_date }"   pattern="yyyy-MM-dd" type="date" dateStyle="long" />
                </display:column>
                <display:column title="备注">${borrowItem.remark }</display:column>
            </display:table>
        </div>
        
        <div title="个人荣誉" tabid="honor" icon="${ctx}/styles/default/images/resicon/article.gif">
            <display:table  name="honorList" id="honorItem" requestURI="list.ht" sort="external" class="oa-table--default">
                <display:column title="荣誉编号">${honorItem.honor_num }</display:column>
                <display:column title="奖项名称">${honorItem.honor_name }</display:column>
                <display:column title="奖项级别">${honorItem.honor_level }</display:column>
                <display:column title="颁发机构">${honorItem.issuing_authority }</display:column>
                <display:column title="备注">${honorItem.remark }</display:column>
                <display:column title="查询网址">${honorItem.query_url }</display:column>
                <display:column title="颁发日期">
                    <fmt:formatDate value="${honorItem.issue_date }"   pattern="yyyy-MM-dd" type="date" dateStyle="long" />
                </display:column>
            </display:table>
        </div>
        
        <div title="个人借阅" tabid="qualification" icon="${ctx}/styles/default/images/resicon/article.gif">
            <display:table  name="certificateBorrowList" id="certificateBorrowItem" requestURI="list.ht" sort="external" class="oa-table--default">
                <display:column title="证书名称">${certificateBorrowItem.steal_name}</display:column>
                <display:column title="证书类型">
                	<c:if test="${certificateBorrowItem.type==1}">资格证</c:if>
					<c:if test="${certificateBorrowItem.type==2}">注册证</c:if>
					<c:if test="${certificateBorrowItem.type==3}">执业印章</c:if>
					<c:if test="${certificateBorrowItem.type==4}">其他</c:if>
                </display:column>
                <display:column title="证书（印章）编号">${certificateBorrowItem.borrow_data_num}</display:column>
                <display:column title="印章有效日期">
                	<fmt:formatDate value="${certificateBorrowItem.effictiveDate }"   pattern="yyyy-MM-dd" type="date" dateStyle="long" />
                </display:column>
                <display:column title="持证人">${certificateBorrowItem.fullname}</display:column>
                <display:column title="归还日期">
                	<fmt:formatDate value="${certificateBorrowItem.return_date }"   pattern="yyyy-MM-dd" type="date" dateStyle="long" />
                </display:column>
                <display:column title="借阅日期">
                	<fmt:formatDate value="${certificateBorrowItem.ap_date }"   pattern="yyyy-MM-dd" type="date" dateStyle="long" />
                </display:column>
                <display:column title="借阅人">${certificateBorrowItem.applicant}</display:column>
            </display:table>
        </div>
        
        <div title="我的流程" tabid="process" icon="${ctx}/styles/default/images/resicon/article.gif">
            <display:table  name="processList" id="processItem" requestURI="list.ht" sort="external" class="oa-table--default">
                <display:column title="名称">${processItem.processName }</display:column>
                <display:column title="发起时间">
                    <fmt:formatDate value="${processItem.createtime }"   pattern="yyyy-MM-dd HH:mm:ss" type="date" dateStyle="long" />
                </display:column>
                <display:column title="状态" sortName="status">
                    <c:choose>
                        <c:when test="${processItem.status==1}">
                            <span class='green'>正在运行</span>
                        </c:when>
                        <c:when test="${processItem.status==2}">
                            <span class="red">结束</span>
                        </c:when>
                        <c:when test="${processItem.status==3}">
                            <span class="brown">人工结束</span>
                        </c:when>
                        <c:when test="${processItem.status==4}">
                            <span class="green">试运行</span>
                        </c:when>
                    </c:choose>
                </display:column>
            </display:table>
        </div>
        
        <div title="借用印章" tabid="contract" icon="${ctx}/styles/default/images/resicon/article.gif">
            <display:table  name="sealList" id="sealItem" requestURI="list.ht" sort="external" class="oa-table--default">
                <display:column title="印章编号">${sealItem.seal_num }</display:column>
                <display:column title="印章名称">${sealItem.seal_name }</display:column>
                <display:column title="所有人">${sealItem.holder }</display:column>
                <display:column title="状态">
                    <c:choose>
                        <c:when test="${sealItem.status==0 }">
                            <span class='green'>未借出</span>
                        </c:when>
                        <c:when test="${sealItem.status==1 }">
                            <span class='red'>已借出</span>
                        </c:when>
                    </c:choose>
                </display:column>
                <display:column title="备注">${sealItem.remark }</display:column>
            </display:table>
        </div>
        
        <div title="经手合同" tabid="seal" icon="${ctx}/styles/default/images/resicon/article.gif">
            <display:table  name="contractList" id="sealItem" requestURI="list.ht" sort="external" class="oa-table--default">
                <display:column title="合同编号"><a href="/makshi/contract/contractinfo/get.ht?id=${sealItem.id }" target="_blank">${sealItem.contract_num }</a></display:column>
                <display:column title="名称">${sealItem.contract_name }</display:column>
                <display:column title="经手人">${sealItem.contract_handler }</display:column>
                <display:column title="是否归档">
                    <c:choose>
                        <c:when test="${sealItem.isrecovery==0 || sealItem.isrecovery==null}">
                            <span class='green'>未归档</span>
                        </c:when>
                        <c:when test="${sealItem.isrecovery==1}">
                            <span class='red'>已归档</span>
                        </c:when>
                    </c:choose>
                </display:column>
            </display:table>
        </div>
        <div title="历史变更" tabid="infoChange" icon="${ctx}/styles/default/images/resicon/article.gif">
            <display:table  name="changeList" id="infoChangeItem" requestURI="list.ht" sort="external" class="oa-table--default">
            	<display:column title="变更字段">${infoChangeItem.field}</display:column>
                <display:column title="变更前">${infoChangeItem.before}</display:column>
                <display:column title="变更后">${infoChangeItem.after}</display:column>
            </display:table>
        </div>
    </div>
    <script type="text/javascript">
		$(function(){
			var names = {};
			<c:forEach items="${userInfomation.entryProfessionalList}" var="entryProfessional" varStatus="status">
				names['${entryProfessional.name}'] = '${entryProfessional.name}';
        	</c:forEach>
			if('高级工程师' in names ){
				$("#positional").html('高级工程师');
			}else if('中级工程师' in names ){
				$("#positional").html('中级工程师');
			}else if('初级工程师' in names ){
				$("#positional").html('初级工程师');
			}
		});
	</script>
</body>
</html>
