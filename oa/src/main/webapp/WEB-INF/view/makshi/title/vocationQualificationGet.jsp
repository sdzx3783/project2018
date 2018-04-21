<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
    <title>个人执业资格详情</title>
    <%@include file="/codegen/include/customForm.jsp" %>
    <%@include file="/commons/include/ueditor.jsp" %>
    <script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
   	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/AttachMent.js"></script>
    <script type="text/javascript" src="${ctx}/js/lg/plugins/ligerComboBox.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/form/TablePermission.js"></script>
</head>
<body class="oa-mw-page">
<div class="oa-mg20">
	<form id="occupationalRequirementsForm" method="post" action="save.ht">
		<table class="oa-table--default" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled"> 
			<caption>个人执业资格</caption>
 			<tbody> 
			<tr>
		    <td colspan="4" rowspan="1"><p style="margin-top: 20px; margin-bottom: 5px;"><span style="font-size: 18px;">执业资格信息</span></p></td>
		    </tr>
		    <tr>
			    <th>员工编号</th>
			    <td>${personalQualification.account}</td>
			    <th>姓名</th>
	   			<td>${personalQualification.name}</td>
		    </tr>
			<tr>
		    	<th>性别</th>
		    	<td>${personalQualification.sex}</td>
		    	<th>民族</th>
   				<td>${personalQualification.nation}</td>
		    </tr>
		    <tr>
		    	<th>身份证号码</th>
		    	<td>${personalQualification.ID_number}</td>
		    	<th>学历</th>
   				<td>${personalQualification.xl}</td>
		    </tr>
		    <tr>
		    	<th>毕业院校</th>
		    	<td>${personalQualification.graduation_school}</td>
		    	<th>毕业时间</th>
   				<td><fmt:formatDate value='${personalQualification.graduation_date}' pattern='yyyy-MM-dd'/></td>
		    </tr>
		    <tr>
		    	<th>所学专业</th>
		    	<td>${personalQualification.learnMajor}</td>
		    	<th>初级职称专业</th>
   				<td>${personalQualification.positional_major_one}</td>
		    </tr>
		    <tr>
		    	<th>中级职称专业</th>
		    	<td>${personalQualification.positional_major_two}</td>
		    	<th>高级职称专业</th>
   				<td>${personalQualification.positional_major_three}</td>
		    </tr>
		    <tr>
		    	<th>资格证书类型</th>
		    	<td>${personalQualification.certificate_type}</td>
		    	<th>资格证书编号或资格证书管理编号</th>
		    	<td>${personalQualification.certificate_id}</td>
		    </tr>
		    <tr>
		    	<th>资格证书发证日期或批准日期</th>
   				<td><fmt:formatDate value='${personalQualification.certificate_date}' pattern='yyyy-MM-dd'/></td>
		    	<th>执业编号</th>
		    	<td>${personalQualification.certified_id}</td>
		    </tr>
		    <tr>
		    	<th>证书专业</th>
   				<td>${personalQualification.certificate_major}</td>
		    	<th>资格证书签发单位</th>
		    	<td>${personalQualification.send_unit}</td>
		    </tr>
		    <tr>
		    	<th>备注</th>
		    	<td colspan="3">${personalQualification.remark}</td>
		    </tr>
		    <tr>
		    	<th>附件</th>
		    	<td colspan="3">
		    		<input  ctltype="attachment" right="r"  name="m:personal_qualification_regist:attachment" isdirectupload="0"  value='${personalQualification.attachment}' validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}"/>
		    	</td>
		    </tr>
		<tr>
		    <th rowspan="1" colspan="4" ></th>
	    </tr>
		    <td colspan="4" rowspan="1"><p style="margin-top: 20px; margin-bottom: 5px;"><span style="font-size: 18px;">注册资格信息</span></p></td>
		<tr>
		    <th>是否已注册</th>
		    <td>
		   	  <c:if test="${personalQualification.isregist==0}">否</c:if>
		   	  <c:if test="${personalQualification.isregist==1}">是</c:if>
		   	  <c:if test="${personalQualification.isregist==2}">正在办理</c:if>
		   	</td>
		   	<th>注册号</th>
	    	<td>${personalQualification.regist_id}</td>
		</tr>
		<tr>
	    	<th>注册证书发证日期</th>
	    	<td><fmt:formatDate value='${personalQualification.get_date}' pattern='yyyy-MM-dd'/></td>
	    	<th>注册证书有效日期</th>
			<td><fmt:formatDate value='${personalQualification.last_effectice_date}' pattern='yyyy-MM-dd'/></td>
		</tr>
		<tr>
	    	<th>第一注册专业</th>
	    	<td>${personalQualification.regist_major}</td>
	    	<th>第二注册专业</th>
	    	<td>${personalQualification.regist_secondMajor}</td>
		</tr>
		<tr>
	    	<th>第三注册专业</th>
	    	<td>${personalQualification.regist_thirdMajor}</td>
	    	<th>执业印章有效日期</th>
			<td><fmt:formatDate value='${personalQualification.effictiveDate}' pattern='yyyy-MM-dd'/></td>
		</tr>
		<tr>
	    	<th>执业印章号</th>
	    	<td>${personalQualification.seal_id}</td>
	    	<th>初始日期</th>
			<td><fmt:formatDate value='${personalQualification.regist_date}' pattern='yyyy-MM-dd'/></td>
		</tr>
		<tr>
	    	<th>转入日期</th>
			<td><fmt:formatDate value='${personalQualification.in_date}' pattern='yyyy-MM-dd'/></td>
	    	<th>转出日期</th>
			<td><fmt:formatDate value='${personalQualification.regist_out_date}' pattern='yyyy-MM-dd'/></td>
		</tr>
		<tr>
	    	<th>注册证书发证单位</th>
	    	<td colspan="3">${personalQualification.regist_send_unit}</td>
		</tr>
  		<tr>
	    	<th>最新注册类别</th>
			<td>${personalQualification.lasted_regist_type}</td>
	    	<th>最新注册日期</th>
	    	<td><fmt:formatDate value='${personalQualification.lasted_regist_date}' pattern='yyyy-MM-dd'/></td>
		</tr>
		<tr>
	    	<th>继续教育完成情况</th>
			<td>${personalQualification.keep_edu_status}</td>
	    	<th>总学时</th>
			<td>${personalQualification.regist_period}</td>
		</tr>
		<tr>
	    	<th>必修课学时</th>
			<td>${personalQualification.regist_compulsory}</td>
	    	<th>选修课学时</th>
			<td>${personalQualification.regist_elective}</td>
		</tr>
		<tr>
	    	<th>备注</th>
			<td colspan="3">${personalQualification.regist_remark}</td>
		</tr>
		<tr>
	    	<th>附件</th>
			<td colspan="3">
				<input  ctltype="attachment" right="r"  name="m:personal_qualification_regist:regist_attachment" isdirectupload="0"  value='${personalQualification.regist_attachment}' validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}"/>
			</td>
		</tr>
   		<tr>
    		<td  rowspan="1" colspan="4"><p style="margin-top: 20px; margin-bottom: 5px;"><span style="font-size: 18px;">从业资格信息</span></p></td>
   		</tr>
   		<tr> 
   			<th>证书类型</th>
   			<td> 
			     <c:if test="${personalQualification.occ_type==''}">无</c:if>
				 <c:if test="${personalQualification.occ_type==1}">三类人员安全生产考核合格证</c:if>
				 <c:if test="${personalQualification.occ_type==2}">深圳市监理员</c:if>
				 <c:if test="${personalQualification.occ_type==3}">深圳市监理工程师</c:if>
				 <c:if test="${personalQualification.occ_type==4}">水利部监理工程师信用手册</c:if>
				 <c:if test="${personalQualification.occ_type==5}">建设部监理工程师信用手册</c:if>
				 <c:if test="${personalQualification.occ_type==6}">深圳市档案员</c:if>
		     </td> 
		     <th>证书编号</th>
			 <td>${personalQualification.occ_certificate_id}</td>
	    <tr>
	    	 <th>发证日期</th>
			 <td><fmt:formatDate value="${personalQualification.occ_get_date}" pattern='yyyy-MM-dd'/></td>
	    	 <th>有效日期</th>
			 <td><fmt:formatDate value="${personalQualification.occ_period_of_validity}" pattern='yyyy-MM-dd'/></td>
		</tr>
		<tr>
	    	 <th>第一专业</th>
			 <td>${personalQualification.occ_major}</td>
	    	 <th>第二专业</th>
	    	 <td>${personalQualification.occ_secondMajor}</td>
		</tr>
		<tr>
	    	 <th>转入日期</th>
			 <td><fmt:formatDate value="${personalQualification.occ_in_date}" pattern='yyyy-MM-dd'/></td>
	    	 <th>转出日期</th>
			 <td><fmt:formatDate value="${personalQualification.occ_out_date}" pattern='yyyy-MM-dd'/></td>
		</tr>
		<tr>
	    	 <th>发证单位</th>
			 <td colspan="3">${personalQualification.occ_send_unit}</td>
		</tr>
		<tr>
	    	 <th>工种</th>
			 <td>${personalQualification.occ_type_work}</td>
	    	 <th>工种等级</th>
	    	 <td>${personalQualification.occ_degree_work}</td>
		</tr>
		<tr>
	    	 <th>继续教育完成情况</th>
			 <td>${personalQualification.occ_contine_edu_comple}</td>
	    	 <th>总学时</th>
	    	 <td>${personalQualification.occ_period}</td>
		</tr>
		<tr>
	    	 <th>必修课学时</th>
			 <td>${personalQualification.occ_compulsory}</td>
	    	 <th>选修课学时</th>
	    	 <td>${personalQualification.occ_elective}</td>
		</tr>
		<tr>
	    	 <th>备注</th>
			 <td colspan="3">${personalQualification.occ_remark}</td>
		</tr>
		<tr>
	    	 <th>附件</th>
			 <td colspan="3">
			 	<input  ctltype="attachment" right="r"  name="m:personal_qualification_regist:occ_attachment" isdirectupload="0"  value='${personalQualification.occ_attachment}' validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}"/>
			 </td>
		</tr>
	    </table>
    </form>
</div>   
</body>
</html>
