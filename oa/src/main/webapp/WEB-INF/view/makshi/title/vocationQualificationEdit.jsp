<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 从业资格信息管理</title>
	<%@include file="/codegen/include/customForm.jsp" %>
	<%@include file="/commons/include/ueditor.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/AttachMent.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/form/TablePermission.js"></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#occupationalRequirementsForm').form();
			$("a.save").click(function() {
				frm.ajaxForm(options);
				$("#saveData").val(1);
				if(frm.valid()){
					//如果有编辑器的情况下
					$("textarea[name^='m:'].myeditor").each(function(num) {
						var name=$(this).attr("name");
						var data=getEditorData(editor[num]);
						$("textarea[name^='"+name+"']").val(data);
					});
					
					if(WebSignPlugin.hasWebSignField){
						WebSignPlugin.submit();
					}
					if(OfficePlugin.officeObjs.length>0){
						OfficePlugin.submit(function(){
							frm.handleFieldName();
							$('#occupationalRequirementsForm').submit();
						});
					}else{
						frm.handleFieldName();
						$('#occupationalRequirementsForm').submit();
					}
				}
			});
			
			$("#certificateUserId").on("change",function(){
				var userAccount = $(this).val();
				if(userAccount==null || userAccount.length==0){return false};
				$.post("/platform/bpm/bpmFormQuery/doQuery.ht",
						{alias:"getinfoByAccount",querydata:"{\"account\":\""+userAccount+"\"}"},
						function(date){
							var info = date.list;
							if(info!=null&&info.length>0){
								$("#certificateUserName").val(info[0].fullname);
								setCertificateUserSex(info[0].sex);
								
								$("#certificateUserNation").val(info[0].nation);
								$("#certificateNumberId").val(info[0].identification_id);
								$("#certificateNumberEducation").val(info[0].education);
								$("#certificateUserGraduationSchool").val(info[0].graduate_school);
								$("#certificateUserLearnMajor").val(info[0].f_major);
							}
						});
			});
		});
		
		function setCertificateUserSex(sex){
			if(sex==0){
				$("#certificateUserSex").val('女');
			}else{
				$("#certificateUserSex").val('男');	
			}
			
		}
		
		function showResponse(responseText) {
			var obj = new com.hotent.form.ResultMessage(responseText);
			if (obj.isSuccess()) {
				$.ligerDialog.confirm(obj.getMessage()+"成功,是否继续"+obj.getMessage(),"提示信息", function(rtn) {
					if(rtn){
						window.location.href = window.location.href;
					}else{
						window.location.href = window.close();
					}
				});
			} else {
				$.ligerDialog.error(obj.getMessage(),"提示信息");
			}
		}
	</script>
<style>
    .exmine{
    margin-left: 7px;
    padding-left: 8px;
    padding-bottom: 2px;
    font-weight: bold;
    color: #EA5200;
    display:none;
    }
    .oa-new-select,
    .oa-new-input{
      width: 100%;
    }
</style>
</head>
<body class="oa-mw-page">
    <div class="oa-mg20">
        <a class="oa-button oa-button--primary oa-button--blue save" id="dataFormSave">保存</a>
        <!-- <a class="oa-button oa-button--primary oa-button--blue" href="list.ht">返回</a> -->
    </div>
  <div class="oa-mg20">
<form id="occupationalRequirementsForm" method="post" action="save.ht">
 <table class="oa-table--default" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
                <caption>个人执业资格</caption>
   <tr class="firstRow"> 
   	 <td colspan="4" rowspan="1"><p style="margin-top: 20px; margin-bottom: 5px;"><span style="font-size: 18px;">执业资格信息</span></p></td>
   </tr>
  <tr>
   <th>员工编号</th>
   <td> 
  	 <input id="certificateUserId" type="text" name="m:personal_qualification_regist:account" lablename="员工编号" class="oa-new-input" value="${personalQualification.account}" validate="{maxlength:50}" isflag="tableflag" /></td>
   <th>姓名</th>
   <td> 
   	<input id="certificateUserName" type="text" name="m:personal_qualification_regist:name" lablename="姓名" class="oa-new-input" value="${personalQualification.name}" validate="{maxlength:50}" isflag="tableflag" /></td>
  </tr>
  <tr>
   <th>性别</th>
  	  <td>
	   		<select id="certificateUserSex" class="oa-new-select" name="m:personal_qualification_regist:sex" lablename="性别" controltype="select" validate="{empty:false}" val="${personalQualification.sex}">
	    		<option value='男'>男</option> 
	    		<option value='女'>女</option> 
	        </select> 
     </td>
   <th>民族</th>
    <td> 
   <input id="certificateUserNation" type="text" name="m:personal_qualification_regist:nation" lablename="民族" class="oa-new-input" value="${personalQualification.nation}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
  </tr>
  <tr>
   <th>身份证号码</th>
   <td> 
   	<input id="certificateNumberId" type="text" name="m:personal_qualification_regist:ID_number" lablename="身份证号码" class="oa-new-input" value="${personalQualification.ID_number}"   isflag="tableflag" /> 
   	<label id="idClass" class="exmine">请输入18位身份证号码</label></td>
   <th>学历</th>
   <td> 
   <input id="certificateNumberEducation" type="text" name="m:personal_qualification_regist:xl" lablename="学历" class="oa-new-input" value="${personalQualification.xl}" validate="{maxlength:50}" isflag="tableflag" /> </td>
  </tr>
  
  <tr>
   <th>毕业院校</th>
   <td>
   <input id="certificateUserGraduationSchool" type="text" name="m:personal_qualification_regist:graduation_school" lablename="学历" class="oa-new-input" value="${personalQualification.graduation_school}" validate="{maxlength:50}" isflag="tableflag" /></td>
   <th>毕业时间</th>
   <td>
   <input id="certificateUserGraduationDate" data-class="oa-new-input" type="text" name="m:personal_qualification_regist:graduation_date" lablename="毕业时间" value="<fmt:formatDate value="${personalQualification.graduation_date}" pattern='yyyy-MM-dd'/>" class="oa-new-input date"  isflag="tableflag" />
   </th>
  </tr>
    <tr>
   <th>所学专业</td>
   <td> 
   	<input id="certificateUserLearnMajor" type="text" name="m:personal_qualification_regist:learnMajor" lablename="所学专业" class="oa-new-input" value="${personalQualification.learnMajor}" validate="{maxlength:50}" isflag="tableflag" /></td>
   <th>初级职称专业</th>
   <td><input type="text" name="m:personal_qualification_regist:positional_major_one" lablename="职称专业" class="oa-new-input" value="${personalQualification.positional_major_one}" validate="{maxlength:50}" isflag="tableflag" /></td>
  </tr>
  <tr>
   <th>中级职称专业</th>
   <td><input type="text" name="m:personal_qualification_regist:positional_major_two" lablename="职称专业" class="oa-new-input" value="${personalQualification.positional_major_two}" validate="{maxlength:50}" isflag="tableflag" /></td> 
   <th>高级职称专业</th>
   <td><input type="text" name="m:personal_qualification_regist:positional_major_three" lablename="职称专业" class="oa-new-input" value="${personalQualification.positional_major_three}" validate="{maxlength:50}" isflag="tableflag" /></td>
  </tr>
  <tr>
   <th>资格证书类型</th>
   <td>
		   <select name="m:personal_qualification_regist:certificate_type" lablename="资格证书类型" controltype="select" validate="{empty:false}" val="${personalQualification.certificate_type}"  class="oa-new-select"> 
		  	   <option value="">请选择</option>
			   <option value="建设部监理工程师">建设部监理工程师</option>
			   <option value="建设部造价工程师">建设部造价工程师</option>
			   <option value="一级建造师">一级建造师</option>
			   <option value="二级建造师">二级建造师</option>
			   <option value="水利部总监">水利部总监</option>
			   <option value="水利部监理工程师">水利部监理工程师</option>
			   <option value="水利部造价工程师">水利部造价工程师</option>
			   <option value="水利部监理员">水利部监理员</option>
			   <option value="一级结构工程师">一级结构工程师</option>
			   <option value="二级结构工程师">二级结构工程师</option>
			   <option value="招标师">招标师</option>
			   <option value="注册设备监理工程师">注册设备监理工程师</option>
			   <option value="注册公用设备工程师">注册公用设备工程师</option>
			   <option value="注册安全工程师">注册安全工程师</option>
			   <option value="咨询工程师(投资)">咨询工程师(投资)</option>
			   <option value="投资项目管理师">测投资项目管理师</option>
			   <option value="测绘师">测绘师</option>
			   <option value="信息监理工程师">信息监理工程师</option>
			   <option value="系统集成项目管理工程师">系统集成项目管理工程师</option>
		   </select> 
   </td>
   <th>资格证书编号或资格证书管理编号</th>
   <td>
   <input type="text" name="m:personal_qualification_regist:certificate_id" lablename="资格证书编号" class="oa-new-input" value="${personalQualification.certificate_id}"  isflag="tableflag" /> </td>
  </tr>
  <tr>
   <th>资格证书发证日期或批准日期</th>
   <td>
   <input data-class="oa-new-input" type="text" name="m:personal_qualification_regist:certificate_date" lablename="毕业时间" value="<fmt:formatDate value="${personalQualification.certificate_date}" pattern='yyyy-MM-dd'/>" class="oa-new-input date"  isflag="tableflag" />
   </td>
   <th>执业编号</th>
   <td>
   	<input type="text" name="m:personal_qualification_regist:certified_id" lablename="资格证书编号" class="oa-new-input" value="${personalQualification.certified_id}" validate="{maxlength:50}" isflag="tableflag" />
   </td>
  </tr>
  <tr>
   <th>证书专业</th>
   <td>
   <input type="text" name="m:personal_qualification_regist:certificate_major" lablename="资格证书编号" class="oa-new-input" value="${personalQualification.certificate_major}" validate="{maxlength:50}" isflag="tableflag" />
   </td>
   <th>资格证书签发单位</th>
   <td>
   <input type="text" name="m:personal_qualification_regist:send_unit" lablename="资格证书编号" class="oa-new-input" value="${personalQualification.send_unit}"  isflag="tableflag" />
   </td>
   </tr>
  <tr>
   <th>备注</th>
   <td rowspan="1" colspan="3"> 
   <textarea name="m:personal_qualification_regist:remark" lablename="备注" class="oa-new-textarea" rows="5" cols="40" validate="{maxlength:1000}" isflag="tableflag">${personalQualification.remark}</textarea>
    </td>
  </tr>
  <tr>
   <th>附件</th>
   <td rowspan="1" colspan="3"> 
   	<input  ctltype="attachment" right="w"  name="m:personal_qualification_regist:attachment" isdirectupload="0"  value='${personalQualification.attachment}' validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}"/>
   </td>
  </tr>
  <tr>
   <th rowspan="1" colspan="4" ></th>
  </tr>
  <td colspan="4" rowspan="1"><p style="margin-top: 20px; margin-bottom: 5px;"><span style="font-size: 18px;">注册资格信息</span></p></td>
  <tr>
   <th>是否已注册</th>
  <td>
	  <label><input name="m:personal_qualification_regist:isregist" value="1" lablename="是否已注册" validate="{}" type="radio">是</label>
	  <label><input name="m:personal_qualification_regist:isregist" value="0" lablename="是否已注册" validate="{}" type="radio">否</label>
	  <label><input name="m:personal_qualification_regist:isregist" value="2" lablename="是否已注册" validate="{}" type="radio">正在办理</label>
   </td> 
  </tr>
  <tr>
   <th>注册证书编号</th>
   <td>
    <input type="text" name="m:personal_qualification_regist:certificate_regist_id" lablename="注册证书编号" class="oa-new-input" value="${personalQualification.certificate_regist_id}" validate="{maxlength:50}" isflag="tableflag" />
   </td>
   <th>注册号</th>
   <td>
    <input type="text" name="m:personal_qualification_regist:regist_id" lablename="注册号" class="oa-new-input" value="${personalQualification.regist_id}" validate="{maxlength:50}" isflag="tableflag" />
   </td>
  </tr>
  <tr>
   <th>注册证书发证日期</th>
   <td>
   <input data-class="oa-new-input" type="text" name="m:personal_qualification_regist:get_date" lablename="注册证书发证日期" value="<fmt:formatDate value="${personalQualification.get_date}" pattern='yyyy-MM-dd'/>" class="oa-new-input date" validate="{date:true}" isflag="tableflag" />
   </td>
   <th>注册证书有效日期</th>
   <td>
   <input data-class="oa-new-input" type="text" name="m:personal_qualification_regist:last_effectice_date" lablename="注册证书有效日期" value="<fmt:formatDate value="${personalQualification.last_effectice_date}" pattern='yyyy-MM-dd'/>" class="oa-new-input date" validate="{date:true}" isflag="tableflag" />
   </td>
  </tr>
  <tr>
   <th>第一注册专业</th>
   <td>
   	<input type="text" name="m:personal_qualification_regist:regist_major" lablename="注册专业" class="oa-new-input" value="${personalQualification.regist_major}" validate="{maxlength:50}" isflag="tableflag" />
   </td>
   <th>第二注册专业</th>
   <td>
   	<input type="text" name="m:personal_qualification_regist:regist_secondMajor" lablename="第二注册专业" class="oa-new-input"  value="${personalQualification.regist_secondMajor}"  isflag="tableflag" />
   </td>
  </tr>
  <tr>
   <th>第三注册专业</td>
   <td>
   	<input type="text" name="m:personal_qualification_regist:regist_thirdMajor" lablename="第三注册专业" class="oa-new-input" value="${personalQualification.regist_thirdMajor}" validate="{maxlength:50}" isflag="tableflag" />
   </td>
   <th>执业印章有效日期</th>
   <td>
   	<input data-class="oa-new-input" type="text" name="m:personal_qualification_regist:effictiveDate" lablename="执业印章有效日期" value="<fmt:formatDate value="${personalQualification.effictiveDate}" pattern='yyyy-MM-dd'/>" class="oa-new-input date" validate="{date:true}" isflag="tableflag" />
   </td>
  </tr>
  
  <tr>
   <th>执业印章号</th>
   <td>
   	<input type="text" name="m:personal_qualification_regist:seal_id" lablename="执业印章号" class="oa-new-input" value="${personalQualification.seal_id}" validate="{maxlength:50}" isflag="tableflag" />
   </td>
   <th>初始日期</th>
   <td>
   <input data-class="oa-new-input" type="text" name="m:personal_qualification_regist:regist_date" lablename="初始日期" value="<fmt:formatDate value="${personalQualification.regist_date}" pattern='yyyy-MM-dd'/>" class="oa-new-input date" validate="{date:true}" isflag="tableflag" />
   </td>
  </tr>
  
    <tr>
   <th>转入日期</th>
   <td>
   <input data-class="oa-new-input" type="text" name="m:personal_qualification_regist:in_date" lablename="转入日期" value="<fmt:formatDate value="${personalQualification.in_date}" pattern='yyyy-MM-dd'/>" class="oa-new-input date" validate="{date:true}" isflag="tableflag" />
   </td>
   <th>转出日期</th>
   <td>
   <input data-class="oa-new-input" type="text" name="m:personal_qualification_regist:regist_out_date" lablename="转出日期" value="<fmt:formatDate value="${personalQualification.regist_out_date}" pattern='yyyy-MM-dd'/>" class="oa-new-input date" validate="{date:true}" isflag="tableflag" />
   </td>
  </tr>
  
  <tr>
   <th>注册证书发证单位</th>
   <td rowspan="1" colspan="3">
   <input type="text" name="m:personal_qualification_regist:regist_send_unit" lablename="注册证书发证单位" class="oa-new-input" value="${personalQualification.regist_send_unit}" validate="{maxlength:50}" isflag="tableflag" />
   </td>
  </tr>
  <tr>
   <th>最新注册类别</th>
   <td>
	    <select class="oa-new-select" name="m:personal_qualification_regist:lasted_regist_type" lablename="最新注册类别" controltype="select" validate="{empty:false}" val="${personalQualification.lasted_regist_type}">
		     <option value="无">无</option>
		     <option value="初始注册">初始注册</option> 
		     <option value="延期注册">延期注册</option> 
		    <option value="变更注册">变更注册</option> <option value="增项注册">增项注册</option> 
	        <option value="注销注册">注销注册</option> <option value="重新注册">重新注册</option> 
	    </select> 
   </td>
   <th>最新注册日期</th>
   <td>
   	<input data-class="oa-new-input" type="text" name="m:personal_qualification_regist:lasted_regist_date" lablename="转出日期" value="<fmt:formatDate value="${personalQualification.lasted_regist_date}" pattern='yyyy-MM-dd'/>" class="oa-new-input date" validate="{date:true}" isflag="tableflag" />
	</td>
  </tr>
  <tr>
   <th>继续教育完成情况</th>
   <td>
   <input type="text" name="m:personal_qualification_regist:keep_edu_status" lablename="继续教育完成情况" class="oa-new-input" value="${personalQualification.keep_edu_status}" validate="{maxlength:50}" isflag="tableflag" />
   </td>
   <th>总学时</th> 
   <td>
   	<input type="text" name="m:personal_qualification_regist:regist_period" lablename="总学时" class="oa-new-input" value="${personalQualification.regist_period}" validate="{maxlength:50}" isflag="tableflag" /> 
   </td> 
  </tr> 
  <tr>
   <th>必修课学时</th> 
   <td>
   	<input type="text" name="m:personal_qualification_regist:regist_compulsory" lablename="必修课学时" class="oa-new-input" value="${personalQualification.regist_compulsory}" validate="{maxlength:50}" isflag="tableflag" /> 
   </td> 
   <th>选修课学时</th> 
   <td>
   	<input type="text" name="m:personal_qualification_regist:regist_elective" lablename="选修课学时" class="oa-new-input" value="${personalQualification.regist_elective}" validate="{maxlength:50}" isflag="tableflag" /> 
   </td> 
  </tr>
  <tr>
   <th>备注</td>
   <td rowspan="1" colspan="3" >
   <textarea name="m:personal_qualification_regist:regist_remark" lablename="备注" class="oa-new-textarea" rows="5" cols="40" validate="{maxlength:1000}" isflag="tableflag">${personalQualification.regist_remark}</textarea>
   </td>
  </tr>
  <tr>
   <th>附件</th>
   <td rowspan="1" colspan="3">
 	 <input  ctltype="attachment" right="w"  name="m:personal_qualification_regist:regist_attachment" isdirectupload="0"  value='${personalQualification.regist_attachment}' validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}"/> 
   </td>
  </tr>
  
  <tr>
    <td  rowspan="1" colspan="4"><p style="margin-top: 20px; margin-bottom: 5px;"><span style="font-size: 18px;">
    		 从业资格信息</span></p></td>
   </tr>
   <tr> 
   <th>证书类型</th> 
   <td> 
   <select class="oa-new-select" name="m:personal_qualification_regists:occ_type" lablename="证书类型" controltype="select" validate="{empty:false}" val="${personalQualification.occ_type}">
  	 <option  value="" >请选择</option>
     <option value="1">三类人员安全生产考核合格证</option>
     <option value="2">深圳市监理员</option> 
     <option value="3">深圳市监理工程师</option> 
     <option value="4">水利部监理工程师信用手册</option> 
     <option value="5">建设部监理工程师信用手册</option> 
     <option value="6">深圳市档案员</option> 
    </select> 
    </td> 
   <th>证书编号</th> 
   <td>
   <input type="text" name="m:personal_qualification_regist:occ_certificate_id" lablename="证书编号" class="oa-new-input" value="${personalQualification.occ_certificate_id}" validate="{maxlength:50}" isflag="tableflag" />
   </td>
  </tr> 
  <tr> 
   <th>发证日期</th> 
   <td>
   	<input data-class="oa-new-input" type="text" name="m:personal_qualification_regist:occ_get_date" lablename="发证日期" value="<fmt:formatDate value="${personalQualification.occ_get_date}" pattern='yyyy-MM-dd'/>" class="oa-new-input date" validate="{date:true}" isflag="tableflag" />  
   </td> 
   <th>有效日期</th> 
   <td>
   	<input data-class="oa-new-input" type="text" name="m:personal_qualification_regist:occ_period_of_validity" lablename="有效日期" value="<fmt:formatDate value="${personalQualification.occ_period_of_validity}" pattern='yyyy-MM-dd'/>" class="oa-new-input date" validate="{date:true}" isflag="tableflag" />
   </td> 
  </tr> 
  <tr> 
   <th>第一专业</th> 
   <td> 
   <input type="text" name="m:personal_qualification_regist:occ_major" lablename="专业" class="oa-new-input" value="${personalQualification.occ_major}" validate="{maxlength:50}" isflag="tableflag" /> </th> 
   <th>第二专业</th> 
   <td> 
   <input type="text" name="m:personal_qualification_regist:occ_secondMajor" lablename="第二从业专业" class="oa-new-input" value="${personalQualification.occ_secondMajor}" validate="{maxlength:50}" isflag="tableflag" /> </td> 
  </tr> 
  <tr> 
   <th>转入日期</th> 
   <td>
   	<input  data-class="oa-new-input" type="text" name="m:personal_qualification_regist:occ_in_date" lablename="有效日期" value="<fmt:formatDate value="${personalQualification.occ_in_date}" pattern='yyyy-MM-dd'/>" class="oa-new-input date" validate="{date:true}" isflag="tableflag" />
   </td>  
   <th>转出日期</th> 
   <td>
   	<input  data-class="oa-new-input" type="text" name="m:personal_qualification_regist:occ_out_date" lablename="有效日期" value="<fmt:formatDate value="${personalQualification.occ_out_date}" pattern='yyyy-MM-dd'/>" class="oa-new-input date" validate="{date:true}" isflag="tableflag" />
   </td> 
  </tr>
  <tr> 
   <th>发证单位</th> 
   <td>
   <input type="text" name="m:personal_qualification_regist:occ_send_unit" lablename="发证单位" class="oa-new-input" value="${personalQualification.occ_send_unit}" validate="{maxlength:50}" isflag="tableflag" /> </td>
   </td> 
   <th></th> 
   <td>
   </td> 
  </tr> 
  <tr> 
   <th>工种</th> 
   <td>
   	<input type="text" name="m:personal_qualification_regist:occ_type_work" lablename="工种" class="oa-new-input" value="${personalQualification.occ_type_work}" validate="{maxlength:50}" isflag="tableflag" /> </td>
   </td> 
   <th>工种等级</th> 
   <td>
   	<input type="text" name="m:personal_qualification_regist:occ_degree_work" lablename="工种等级" class="oa-new-input" value="${personalQualification.occ_degree_work}" validate="{maxlength:50}" isflag="tableflag" /> </td>
   </td> 
  </tr> 
  
   <tr> 
    <th>继续教育完成情况</th> 
   <td>
   	<input type="text" name="m:personal_qualification_regist:occ_contine_edu_comple" lablename="继续教育完成情况" class="oa-new-input" value="${personalQualification.occ_contine_edu_comple}" validate="{maxlength:50}" isflag="tableflag" />  </td>
   <th>总学时</th> 
   <td>
   	<input type="text" name="m:personal_qualification_regist:occ_period" lablename="总学时" class="oa-new-input" value="${personalQualification.occ_period}" validate="{maxlength:50}" isflag="tableflag" /> 
   </td> 
  </tr> 
  <tr>
   <th>必修课学时</th> 
   <td>
   	<input type="text" name="m:personal_qualification_regist:occ_compulsory" lablename="必修课学时" class="oa-new-input" value="${personalQualification.occ_compulsory}" validate="{maxlength:50}" isflag="tableflag" /> 
   </td> 
   <th>选修课学时</th> 
   <td>
   	<input type="text" name="m:personal_qualification_regist:occ_elective" lablename="选修课学时" class="oa-new-input" value="${personalQualification.occ_elective}" validate="{maxlength:50}" isflag="tableflag" /> 
   </td> 
  </tr>
  
  <tr> 
   <th>备注</th> 
   <td rowspan="1" colspan="3"> 
   <textarea name="m:personal_qualification_regist:occ_remark" lablename="备注" class="oa-new-textarea" rows="5" cols="40" validate="{maxlength:1000}" isflag="tableflag">${personalQualification.occ_remark}</textarea></td> 
  </tr> 
  <tr> 
   <th>附件</th> 
   <td rowspan="1" colspan="3">
	<input  ctltype="attachment" right="w"  name="m:personal_qualification_regist:occ_attachment" isdirectupload="0"  value='${personalQualification.occ_attachment}' validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}"/> 
   </td>
   </td> 
  </tr> 
 </tbody> 
</table>
        </div>
        </div>
        <input type="hidden" name="id" value="${personalQualification.id}"/>
        <input type="hidden" id="saveData" name="saveData"/>
        <input type="hidden" name="executeType"  value="start" />
        <input type="hidden" name="m:personal_qualification_regist:switchs"  value="${personalQualification.switchs}" />
        <input type="hidden" name="m:personal_qualification_regist:userId"  value="${personalQualification.userId}" />
    </form>
</body>
<script type="text/javascript">
	$("input[name='hoder']").on("change",function(){
		var userId = $("input[name='hoderID']").val();
		$("input[name='m:occupational_requirements:userId']").val(userId);
	})
	$(function(){
		var isregist = "${personalQualification.isregist}";
		$("input[name='m:personal_qualification_regist:isregist']").each(function(){
			if($(this).val()==isregist){
				$(this).attr("checked",true);
			}
		});
		
	});
	
	$("input[name='m:personal_qualification_regist:ID_number']").focus(function(){
		var idNumber = $(this).val();
		testIdNumber(idNumber);
	});
	$("input[name='m:personal_qualification_regist:ID_number']").blur(function(){
		var idNumber = $(this).val();
		testIdNumber(idNumber);
	});
	$("input[name='m:personal_qualification_regist:ID_number']").on("change",function(){
		var idNumber = $(this).val();
		testIdNumber(idNumber);
	});
	function testIdNumber(obj){
		var reg = /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/;
		if(reg.test(obj)){
		$("#idClass").css("display","none");
			return;
		}
		$("#idClass").css("display","inline");
		return false;
	}
	
</script>
</html>
