<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人简历</title>
<link href="${ctx}/styles/default/css/form.css" rel="stylesheet" />
<%@include file="/commons/include/form.jsp" %>
<%@include file="/codegen/include/customForm.jsp" %>
</head>
<style>
.oa-table--default th, .oa-table--default td {
	border: solid 1px #888;
}
.oa-25 th, .oa-25 td {
	width: 25%;
}
h3 {
	margin: 12px 0 8px;
}
.photo {
	max-width: 150px;
	max-height: 220px;
}
@media print {
	.noprint {
		display: none;
	}
}
</style>
<body class="oa-mw-page">
		<div class="oa-pd20 noprint">
	            <a class="oa-button oa-button--primary oa-button--blue" href="javascript:;" onclick="window.print();">打印</a>
   		</div>
   		<div class="oa-mg20">
   	    	<table class="oa-table--default oa-25" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
				 <tbody>
				 	<tr>
				 		<th colspan="4" style="text-align: center;">个人简历</th>
				 	</tr>
					  <tr>
					   <th>工号</th>
					   <td>${sysUser.account}</td>
					    <td  rowspan="7" colspan="2">
					     <div class="profile" style="text-align: center;">
                    		<img class="photo" 
			                    <c:choose>
				                    <c:when test="${sysUser.sex==1}">
				                        onerror="this.src='/commons/image/default_image_male.jpg'"
				                    </c:when>
				                    <c:otherwise>
				                        onerror="this.src='/commons/image/default_image_female.jpg'" 
				                    </c:otherwise>
			                    </c:choose>  src="${pictureLoad}" alt="个人相片"/>
                    	</div>
					   </td>
					  </tr>
					  <tr>
					   <th>姓名</th>
					    <td>${sysUser.fullname}</td>
					  </tr>
					  <tr>
					  	<th>入职日期</th>
					    <td><fmt:formatDate value='${sysUser.entryDate}' pattern='yyyy-MM-dd'/></td>	
					  </tr>
					  <tr>
					  	<th>入所在部门</th>
					    <td>${sysUser.fullname}</td>	
					  </tr>
					  <tr>
					    <th>性别</th>
					    <td>
					    	<c:choose>
	                            <c:when test="${sysUser.sex==1}">男</c:when>
	                            <c:otherwise>女</c:otherwise>
                       	    </c:choose>
					    </td>
					  </tr>
					  <tr>
					  	<th>出生日期</th>
					    <td><fmt:formatDate value='${userInfomation.birthday}' pattern='yyyy-MM-dd'/></td>	
					  </tr>
					  <tr>
					    <th>婚姻状况</th>
					    <td>${userInfomation.marriage_state}</td>
					  </tr>
					 <tr>
					    <th>民族</th>
					    <td>${userInfomation.nation}</td>
					    <th>曾用名</th>
					    <td>${userInfomation.used_name}</td>
					  </tr> 
					  <tr>
					  	<th>籍贯</th>
					    <td>${userInfomation.address}</td>
					    <th>职称专业</th>
					    <td>${userInfomation.positional_major}</td>
					  </tr>
					  <tr>
					  	<th>文化程度</th>
					    <td>${userInfomation.education}</td>
					    <th>参加工作时间</th>
					    <td><fmt:formatDate value='${userInfomation.start_work_time}' pattern='yyyy-MM-dd'/></td>
					  </tr>
					  <tr>
					  	<th>毕业院校</th>
					    <td>${userInfomation.graduate_school}</td>
					    <th>政治面貌</th>
					    <td>${userInfomation.political_status}</td>
					  </tr>
					  <tr>
					  	<th>毕业专业</th>
					    <td>${userInfomation.major}</td>
					    <th>身份证号码</th>
					    <td>${userInfomation.identification_id}</td>
					  </tr>
					  <tr>
					  	<th>职称</th>
					    <td>${userInfomation.positional}</td>
					    <th>户籍</th>
					    <td>${userInfomation.address_type}</td>
					  </tr>
					  <tr>
					  	<th>基准工资</th>
					    <td></td>
					    <th>岗位工资</th>
					    <td></td>
					  </tr>
					</table>
					<h3>健康状态</h3>
					<table class="oa-table--default oa-25" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
					  <tr>
					  	<th>是否有传染病史</th>
					    <td>
						    <c:choose>
	                            <c:when test="${userInfomation.infection_history==1}">是</c:when>
	                            <c:otherwise>否</c:otherwise>
	                        </c:choose>
                        </td>
					    <th>是否有遗传病史</th>
					    <td>
					    	<c:choose>
                            <c:when test="${userInfomation.disorders_history==1}">是</c:when>
                            <c:otherwise>否</c:otherwise>
                        </c:choose>
					    </td>
					  </tr>
					</table>
					<h3>社保信息</h3>
					<table class="oa-table--default oa-25" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
					  <tr>
					  	<th>社会保险电脑号</th>
					    <td>${userInfomation.social_security_computer_id}</td>
					    <th>利手</th>
					    <td>${userInfomation.handedness}</td>
					  </tr>
					  </table>
					  <h3>家庭及其他个人信息</h3>
					  <table class="oa-table--default oa-25" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
					  <tr>
	                    <th>特长爱好</th>
	                    <td >${userInfomation.hobby}</td>
	                    <th>户籍所在地</th>
	                    <td >${userInfomation.home_address}</td>
               		  </tr>
                 	  <tr>
	                    <th>配偶姓名</th>
	                    <td >${userInfomation.spouse_name}</td>
	                    <th>父母居住地</th>
	                    <td >${userInfomation.parents}</td>
                	  </tr>
                	  <tr>
                	  	<th>配偶身份证号码</th>
                    	<td >${userInfomation.spouse_identification_id}</td>
                    	<th>配偶居住地</th>
                    	<td >${userInfomation.spouse_address}</td>
               		  </tr>
           		      <tr>
                    	<th>通讯地址</th>
                    	<td >${userInfomation.link_address}</td>
                    	<th>手机短号</th>
                    	<td >${userInfomation.sjdh}</td>
                	 </tr>
                	 <tr>
                	 	 <th>紧急联系人</th>
                    	 <td >${userInfomation.emergency_link_person}</td>
                   		 <th>工资卡号</th>
                 		 <td >${userInfomation.BOC_id}</td>
                	 </tr>
                	 <tr>
                	 	 <th>紧急联系人电话</th>
                    	 <td >${userInfomation.emergency_link_person_phone}</td>
                   		 <th>个人邮箱</th>
                 		 <td >${userInfomation.BOC_id}</td>
                	 </tr>
                	 <tr>
                	 	 <th>QQ号码</th>
                    	 <td >${userInfomation.QQ}</td>
                   		 <th>微信</th>
                 		 <td >${userInfomation.wechart}</td>
                	 </tr>
				 </tbody>
			</table>
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
                    </tr>
                </c:forEach>
                <c:if test="${empty userInfomation.entryFamilyList || fn:length(userInfomation.entryFamilyList)<=0}">
                    <tr>
                        <td colspan="7">当前没有记录。</td>
                    </tr>
                </c:if>
            </table>
			<h3 class="oa-h3">学习经历</h3>
            <table class="oa-table--default"> 
                <thead>
                    <tr> 
                        <th>开始日期</th> 
                        <th>结束日期</th> 
                        <th>就读学校或机构</th> 
                        <th>专业</th> 
                        <th>所获证书、学位、奖励</th> 
                    </tr> 
                </thead>
                <c:forEach items="${userInfomation.entryEducationHistoryList}" var="entryEducationHistory" varStatus="status">
                    <tr> 
                        <td>${f:shortDate(entryEducationHistory.startDate)}</td> 
                        <td>${f:shortDate(entryEducationHistory.endDate)}</td> 
                        <td>${entryEducationHistory.education_school}</td> 
                        <td>${entryEducationHistory.major}</td> 
                        <td>${entryEducationHistory.achieve_certificate}</td> 
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
                    </tr> 
                </thead>
                <c:forEach items="${userInfomation.entryWorkHistoryList}" var="entryWorkHistory" varStatus="status">
                    <tr> 
                        <td>${f:shortDate(entryWorkHistory.startDate)}</td> 
                        <td>${f:shortDate(entryWorkHistory.endDate)}</td>
                        <td>${entryWorkHistory.workplace}</td> 
                        <td>${entryWorkHistory.department_post}</td> 
                        <td>${entryWorkHistory.positions}</td> 
                    </tr>
                </c:forEach>
                <c:if test="${empty userInfomation.entryWorkHistoryList || fn:length(userInfomation.entryWorkHistoryList)<=0}">
                    <tr>
                        <td colspan="5">当前没有记录。</td>
                    </tr>
                </c:if>
            </table>
            <h3 class="oa-h3">专业职称</h3>
            <table class="oa-table--default"> 
                <thead>
                    <tr> 
                        <th>职称编号</th> 
                        <th>职称名称</th> 
                        <th>发证机构</th> 
                        <th>职称专业</th> 
                        <th>取得职称时间</th> 
                    </tr> 
                </thead>
                <c:forEach items="${userInfomation.entryProfessionalList}" var="entryProfessional" varStatus="status">
                    <tr> 
                        <td>${entryProfessional.num}</td> 
                        <td>${entryProfessional.name}</td> 
                        <td>${entryProfessional.organization}</td> 
                        <td>${entryProfessional.major}</td> 
                        <td><fmt:formatDate value="${entryProfessional.achieve_time}" pattern='yyyy-MM-dd'/></td> 
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
                                <c:when test="${entryVocationQualification.switchs==1}">是</c:when>
                                <c:otherwise>否</c:otherwise>
                            </c:choose>
                        </td>
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
                                <c:when test="${registrationQualification.isregist==1}">是</c:when>
                                <c:otherwise>否</c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty userInfomation.registrationQualificationList || fn:length(userInfomation.registrationQualificationList)<=0}">
                    <tr>
                        <td colspan="7">当前没有记录。</td>
                    </tr>
                </c:if>
            </table> 
    </form>
   </div>
  </div>
</body>
</html>
