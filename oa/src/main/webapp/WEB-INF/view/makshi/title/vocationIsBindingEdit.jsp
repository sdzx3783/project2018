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
		});
		
		function showResponse(responseText) {
			var obj = new com.hotent.form.ResultMessage(responseText);
			if (obj.isSuccess()) {
				$.ligerDialog.confirm(obj.getMessage()+",是否继续操作","提示信息", function(rtn) {
					if(rtn){
						window.location.href = window.location.href;
					}else{
						window.location.href = "${ctx}/makshi/title/vocationQualification/list.ht";
					}
				});
			} else {
				$.ligerDialog.error(obj.getMessage(),"提示信息");
			}
		}
	</script>
</head>
<body>

<div class="panel" style="height:100%;overflow:auto;">
    <div class="panel-top">
        <div class="tbar-title">
            <c:choose>
                <c:when test="${not empty occupationalRequirements.id}">
                    <span class="tbar-label"><span></span>编辑个人执业资格</span>
                </c:when>
                <c:otherwise>
                    <span class="tbar-label"><span></span>添加个人执业资格</span>
                </c:otherwise>  
            </c:choose>
        </div>
        <div class="panel-toolbar">
            <div class="toolBar">
                <div class="group"><a class="link save" id="dataFormSave" href="javascript:;"><span></span>保存</a></div>
                <div class="l-bar-separator"></div>
                <div class="group"><a class="link back" href="list.ht"><span></span>返回</a></div>
            </div>
        </div>
    </div>
<form id="occupationalRequirementsForm" method="post" action="save.ht">
        <div type="custform">
            <div class="panel-detail">
<table class="formTable" border="1" cellspacing="0" cellpadding="2" parser="addpermission"> 
 <tbody> 
  <tr class="firstRow"> 
   <td class="formHead" colspan="4" style="word-break: break-all;">个人执业资格</td> 
  </tr>
	<tr>
    <td class="formInput" colspan="4" rowspan="1" style="word-break: break-all;"><p style="margin-top: 20px; margin-bottom: 5px;"><span style="font-size: 18px;">执业资格信息</span></p></td>
   </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" width="213" align="right">员工编号</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" width="524"> 
   <span name="editable-input" style="display:inline-block;" isflag="tableflag"> 
  	 <input type="text" name="m:personal_qualification_regist:account" lablename="员工编号" class="inputText" value="${personalQualification.account}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">姓名</td>
   <td style="width: 35%; word-break: break-all;" class="formInput"> 
   	<input type="text" name="m:personal_qualification_regist:name" lablename="姓名" class="inputText" value="${personalQualification.name}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" width="0" align="right">性别</td>
  	  <td style="width: 35%; word-break: break-all;" class="formInput" width="524">
       <span name="editable-input" style="display:inline-block;padding:2px;" class="selectinput"> 
	   		<select name="m:personal_qualification_regist:sex" lablename="性别" controltype="select" validate="{empty:false}" val="${personalQualification.sex}">
	    		<option value="男">男</option> 
	    		<option value="女">女</option> 
	        </select> 
     </span> 
     </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">民族</td>
    <td style="width: 35%; word-break: break-all;" class="formInput"> 
   <input type="text" name="m:personal_qualification_regist:nation" lablename="民族" class="inputText" value="${personalQualification.nation}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" width="0" align="right">身份证号码</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" width="524"> 
   	<input type="text" name="m:personal_qualification_regist:ID_number" lablename="身份证号码" class="inputText" value="${personalQualification.ID_number}" validate="{&quot;maxlength&quot;:18kj,&quot;required&quot;:&quot;true&quot;}" isflag="tableflag" /> </span> </td>
   </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">学历</td>
   <td style="width: 35%; word-break: break-all;" class="formInput"> 
   <input type="text" name="m:personal_qualification_regist:xl" lablename="学历" class="inputText" value="${personalQualification.xl}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
  </tr>
  
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" width="0" align="right">毕业院校</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" width="524">
   <input type="text" name="m:personal_qualification_regist:graduation_school" lablename="学历" class="inputText" value="${personalQualification.graduation_school}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">毕业时间</td>
   <td style="width: 35%; word-break: break-all;" class="formInput">
   <input type="text" name="m:personal_qualification_regist:graduation_date" lablename="毕业时间" value="<fmt:formatDate value="${personalQualification.graduation_date}" pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" isflag="tableflag" />
   </td>
  </tr>
    <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" width="0" align="right">所学专业</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" width="524"> 
   	<input type="text" name="m:personal_qualification_regist:learnMajor" lablename="所学专业" class="inputText" value="${personalQualification.learnMajor}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right"></td>
   <td style="width: 35%; word-break: break-all;" class="formInput"> </td>
  </tr>
  <tr id="degree">
   <td style="width: 15%; word-break: break-all;" class="formTitle" width="0" align="right">职称等级</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" width="524">
      <span name="editable-input" style="display:inline-block;padding:2px;" class="selectinput"> 
   <select name="m:personal_qualification_regist:positional_degree_one" lablename="职称专业" controltype="select" validate="{empty:false}" val="${personalQualification.positional_degree_one}">
    <option value="0">请选择</option> <option value="1">初级</option> 
    <option value="2">中级</option> <option value="3">高级</option> </select> </span> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">职称专业</td>
   <td style="width: 35%; word-break: break-all;" class="formInput">
   <input type="text" name="m:personal_qualification_regist:positional_major_one" lablename="职称专业" class="inputText" value="${personalQualification.positional_major_one}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
  </tr>
   </tr>
  <tr id="degree">
   <td style="width: 15%; word-break: break-all;" class="formTitle" width="0" align="right">职称等级</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" width="524">
      <span name="editable-input" style="display:inline-block;padding:2px;" class="selectinput"> 
   <select name="m:personal_qualification_regist:positional_degree_two" lablename="职称专业" controltype="select" validate="{empty:false}" val="${personalQualification.positional_degree_two}">
    <option value="0">请选择</option> <option value="1">初级</option> 
    <option value="2">中级</option> <option value="3">高级</option> </select> </span> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">职称专业</td>
   <td style="width: 35%; word-break: break-all;" class="formInput">
   <input type="text" name="m:personal_qualification_regist:positional_major_two" lablename="职称专业" class="inputText" value="${personalQualification.positional_major_two}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
  </tr>
   <tr id="degree">
   <td style="width: 15%; word-break: break-all;" class="formTitle" width="0" align="right">职称等级</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" width="524">
	    <span name="editable-input" style="display:inline-block;padding:2px;" class="selectinput"> 
		    <select name="m:personal_qualification_regist:positional_degree_three" lablename="职称专业" controltype="select" validate="{empty:false}" val="${personalQualification.positional_degree_three}">
		    <option value="0">请选择</option> <option value="1">初级</option> 
		    <option value="2">中级</option> <option value="3">高级</option> 
		    </select> 
        </span> 
    </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">职称专业</td>
   <td style="width: 35%; word-break: break-all;" class="formInput">
   <input type="text" name="m:personal_qualification_regist:positional_major_three" lablename="职称专业" class="inputText" value="${personalQualification.positional_major_three}" validate="{maxlength:50}" isflag="tableflag" /> </td>
  </tr>
  
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" width="0" align="right">资格证书类型</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3">
   <span name="editable-input" style="display:inline-block;padding:2px;" class="selectinput"> 
		   <select name="m:personal_qualification_regist:certificate_type" lablename="资格证书类型" controltype="select" validate="{empty:false}" val="${personalSeal.seal_name}"> 
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
			   <option value="注册公用设备工程师（给水排水）">注册公用设备工程师</option>
			   <option value="注册安全工程师">注册安全工程师</option>
			   <option value="咨询工程师（投资）">咨询工程师（投资）</option>
			   <option value="投资项目管理师">测投资项目管理师</option>
			   <option value="测绘师">测绘师</option>
			   <option value="信息监理工程师">信息监理工程师</option>
			   <option value="系统集成项目管理工程师">系统集成项目管理工程师</option>
		   </select> 
	   </span> 
   <%-- <span name="editable-input" style="display:inline-block;" isflag="tableflag"> 
   <input   class="dicComboTree l-text-field" nodekey="vocationQualification" value="${personalQualification.certificate_type}" name="m:personal_qualification_regist:certificate_type" validate="{empty:false}"  style="height:23px; width:250px"/>
   </span> --%>
   </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" width="0" align="right">资格证书编号</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" width="524">
   <input type="text" name="m:personal_qualification_regist:certificate_id" lablename="资格证书编号" class="inputText" value="${personalQualification.certificate_id}" validate="{maxlength:50}" isflag="tableflag" /> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">资格证书发证日期</td>
   <td style="width: 35%; word-break: break-all;" class="formInput">
   <input type="text" name="m:personal_qualification_regist:certificate_date" lablename="毕业时间" value="<fmt:formatDate value="${personalQualification.certificate_date}" pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" isflag="tableflag" />
   </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" width="0" align="right">执业编号</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" width="524">
   	<input type="text" name="m:personal_qualification_regist:certified_id" lablename="资格证书编号" class="inputText" value="${personalQualification.certified_id}" validate="{maxlength:50}" isflag="tableflag" />
   </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">证书专业</td>
   <td style="width: 35%; word-break: break-all;" class="formInput">
   <input type="text" name="m:personal_qualification_regist:certificate_major" lablename="资格证书编号" class="inputText" value="${personalQualification.certificate_major}" validate="{maxlength:50}" isflag="tableflag" />
   </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" width="0" align="right">资格证书签发单位</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" width="524">
   <input type="text" name="m:personal_qualification_regist:send_unit" lablename="资格证书编号" class="inputText" value="${personalQualification.send_unit}" validate="{maxlength:50}" isflag="tableflag" />
   </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">转出日期</td>
   <td style="width: 35%; word-break: break-all;" class="formInput">
   	<input type="text" name="m:personal_qualification_regist:out_date" lablename="转出日期" value="<fmt:formatDate value="${personalQualification.out_date}" pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" isflag="tableflag" />
   </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" width="0" align="right">备注</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"> 
   <textarea name="m:personal_qualification_regist:remark" lablename="备注" class="l-textarea" rows="5" cols="40" validate="{maxlength:1000}" isflag="tableflag">${personalQualification.remark}</textarea>
    </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" width="0" align="right">附件</td>
   <td style="width: 35%" class="formInput" rowspan="1" colspan="3"> 
   	<input  ctltype="attachment" right="w"  name="m:personal_qualification_regist:attachment" isdirectupload="0"  value='${personalQualification.attachment}' validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}"/>
   </td>
  </tr>
  <tr>
   <td class="formInput" rowspan="1" colspan="4" style="word-break: break-all;"></td>
  </tr>
  <tr>
    <td class="formInput" rowspan="1" colspan="4" style="word-break: break-all;"><p style="margin-top: 20px; margin-bottom: 5px;"><span style="text-align: right; background-color: rgb(240, 241, 241); font-size: 18px;">注册资格信息</span></p></td>
   </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">是否已注册</td>
  <td style="width:35%;" class="formInput">
	  <label><input name="m:personal_qualification_regist:isregist" value="1" lablename="是否已注册" validate="{}" type="radio">是</label>
	  <label><input name="m:personal_qualification_regist:isregist" value="0" lablename="是否已注册" validate="{}" type="radio">否</label>
   </td> 
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">注册证书编号</td>
   <td class="formInput" rowspan="1" colspan="1" style="word-break: break-all;">
    <input type="text" name="m:personal_qualification_regist:certificate_regist_id" lablename="注册证书编号" class="inputText" value="${personalQualification.certificate_regist_id}" validate="{maxlength:50}" isflag="tableflag" />
   </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">注册号</td>
   <td class="formInput" rowspan="1" colspan="1" style="word-break: break-all;">
    <input type="text" name="m:personal_qualification_regist:regist_id" lablename="注册号" class="inputText" value="${personalQualification.regist_id}" validate="{maxlength:50}" isflag="tableflag" />
   </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">注册证书发证日期</td>
   <td class="formInput" rowspan="1" colspan="1" style="word-break: break-all;">
   <input type="text" name="m:personal_qualification_regist:get_date" lablename="注册证书发证日期" value="<fmt:formatDate value="${personalQualification.get_date}" pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" isflag="tableflag" />
   </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">注册证书有效日期</td>
   <td class="formInput" rowspan="1" colspan="1" style="word-break: break-all;">
   <input type="text" name="m:personal_qualification_regist:last_effectice_date" lablename="注册证书有效日期" value="<fmt:formatDate value="${personalQualification.last_effectice_date}" pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" isflag="tableflag" />
   </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">注册专业</td>
   <td class="formInput" rowspan="1" colspan="1" style="word-break: break-all;">
   	<input type="text" name="m:personal_qualification_regist:regist_major" lablename="注册专业" class="inputText" value="${personalQualification.regist_major}" validate="{maxlength:50}" isflag="tableflag" />
   </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">执业印章有效日期</td>
   <td class="formInput" rowspan="1" colspan="1" style="word-break: break-all;">
   	<input type="text" name="m:personal_qualification_regist:effictiveDate" lablename="执业印章有效日期" value="<fmt:formatDate value="${personalQualification.effictiveDate}" pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" isflag="tableflag" />
   </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">执业印章号</td>
   <td class="formInput" rowspan="1" colspan="1" style="word-break: break-all;">
   	<input type="text" name="m:personal_qualification_regist:seal_id" lablename="执业印章号" class="inputText" value="${personalQualification.seal_id}" validate="{maxlength:50}" isflag="tableflag" />
   </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">转出日期</td>
   <td class="formInput" rowspan="1" colspan="1" style="word-break: break-all;">
   <input type="text" name="m:personal_qualification_regist:regist_out_date" lablename="转出日期" value="<fmt:formatDate value="${personalQualification.regist_out_date}" pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" isflag="tableflag" />
   </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">注册证书发证单位</td>
   <td class="formInput" rowspan="1" colspan="3" style="word-break: break-all;">
   <input type="text" name="m:personal_qualification_regist:regist_send_unit" lablename="注册证书发证单位" class="inputText" value="${personalQualification.regist_send_unit}" validate="{maxlength:50}" isflag="tableflag" />
   </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">最新注册类别</td>
   <td class="formInput" rowspan="1" colspan="1" style="word-break: break-all;">
   		 <span name="editable-input" style="display:inline-block;padding:2px;" class="selectinput"> 
		    <select name="m:personal_qualification_regist:lasted_regist_type" lablename="最新注册类别" controltype="select" validate="{empty:false}" val="${personalQualification.lasted_regist_type}">
			    <option value="初始注册">初始注册</option> <option value="延期注册">延期注册</option> 
			    <option value="变更注册">变更注册</option> <option value="增项注册">增项注册</option> 
		        <option value="注销注册">注销注册</option> <option value="重新注册">重新注册</option> 
		    </select> 
        </span>
   </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">最新注册日期</td>
   <td class="formInput" rowspan="1" colspan="1" style="word-break: break-all;">
   	<input type="text" name="m:personal_qualification_regist:lasted_regist_date" lablename="转出日期" value="<fmt:formatDate value="${personalQualification.lasted_regist_date}" pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" isflag="tableflag" />
	</td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">继续教育完成情况</td>
   <td class="formInput" rowspan="1" colspan="3" style="word-break: break-all;">
   <input type="text" name="m:personal_qualification_regist:keep_edu_status" lablename="继续教育完成情况" class="inputText" value="${personalQualification.keep_edu_status}" validate="{maxlength:50}" isflag="tableflag" />
   </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">备注</td>
   <td class="formInput" rowspan="1" colspan="3" style="word-break: break-all;">
   <textarea name="m:personal_qualification_regist:regist_remark" lablename="备注" class="l-textarea" rows="5" cols="40" validate="{maxlength:1000}" isflag="tableflag">
   ${personalQualification.regist_remark}</textarea>
   </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">附件</td>
   <td class="formInput" rowspan="1" colspan="3">
 	 <input  ctltype="attachment" right="w"  name="m:personal_qualification_regist:regist_attachment" isdirectupload="0"  value='${personalQualification.regist_attachment}' validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}"/> 
   </td>
  </tr>
  
  <tr>
    <td class="formInput" rowspan="1" colspan="4" style="word-break: break-all;">
    <p style="margin-top: 20px; margin-bottom: 5px;"><span style="text-align: right; background-color: rgb(240, 241, 241); font-size: 18px;">
    		 从业资格信息</span></p></td>
   </tr>
   <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">证书类型</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"> 
   <span name="editable-input" style="display:inline-block;padding:2px;" class="selectinput"> 
   <select name="m:personal_qualification_regists:occ_type" lablename="证书类型" controltype="select" validate="{empty:false}" val="${personalQualification.occ_type}">
  	 <option  value="" >请选择</option>
    <option value="1">三类人员安全生产考核合格证</option>
     <option value="2">深圳市监理员</option> 
    <option value="3">深圳市监理工程师</option> <option value="4">水利部监理工程师信用手册</option> <option value="5">建设部监理工程师信用手册</option> <option value="6">深圳市档案员</option> </select> </span> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">证书编号</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput">
   <span name="editable-input" style="display:inline-block;" isflag="tableflag"> 
   <input type="text" name="m:personal_qualification_regist:occ_certificate_id" lablename="证书编号" class="inputText" value="${personalQualification.occ_certificate_id}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
<%--    <input type="hidden" name="m:occupational_requirements:refid" value="${personalQualification.certificate_id}"/> --%>
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">发证日期</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput">
   	<input type="text" name="m:personal_qualification_regist:occ_get_date" lablename="发证日期" value="<fmt:formatDate value="${personalQualification.occ_get_date}" pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" isflag="tableflag" />  
   </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">有效日期</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput">
   	<input type="text" name="m:personal_qualification_regist:occ_period_of_validity" lablename="有效日期" value="<fmt:formatDate value="${personalQualification.occ_period_of_validity}" pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" isflag="tableflag" />
   </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">专业</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"> 
   <span name="editable-input" style="display:inline-block;" isflag="tableflag"> 
   <input type="text" name="m:personal_qualification_regist:occ_major" lablename="专业" class="inputText" value="${personalQualification.occ_major}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">转出日期</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput">
   	<input type="text" name="m:personal_qualification_regist:occ_out_date" lablename="有效日期" value="<fmt:formatDate value="${personalQualification.occ_out_date}" pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" isflag="tableflag" />
   </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">发证单位</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3">
    <span name="editable-input" style="display:inline-block;" isflag="tableflag"> 
   <input type="text" name="m:personal_qualification_regist:occ_send_unit" lablename="发证单位" class="inputText" value="${personalQualification.occ_send_unit}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
   </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">工种</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput">
   <span name="editable-input" style="display:inline-block;" isflag="tableflag">
   	<input type="text" name="m:personal_qualification_regist:occ_type_work" lablename="工种" class="inputText" value="${personalQualification.occ_type_work}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
   </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">工种等级</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput">
   <span name="editable-input" style="display:inline-block;" isflag="tableflag">
   	<input type="text" name="m:personal_qualification_regist:occ_degree_work" lablename="工种等级" class="inputText" value="${personalQualification.occ_degree_work}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
   </td> 
  </tr> 
  
   <tr> 
    <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">继续教育完成情况</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput">
   <span name="editable-input" style="display:inline-block;" isflag="tableflag">
   	<input type="text" name="m:personal_qualification_regist:occ_contine_edu_comple" lablename="继续教育完成情况" class="inputText" value="${personalQualification.occ_contine_edu_comple}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
   </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">总学时</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput">
   	<input type="text" name="m:personal_qualification_regist:occ_degree_work" lablename="总学时" class="inputText" value="${personalQualification.occ_degree_work}" validate="{maxlength:50}" isflag="tableflag" /> 
   </td> 
  </tr> 
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">必修课学时</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput">
   	<input type="text" name="m:personal_qualification_regist:occ_degree_work" lablename="必修课学时" class="inputText" value="${personalQualification.occ_degree_work}" validate="{maxlength:50}" isflag="tableflag" /> 
   </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">选修课学时</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput">
   	<input type="text" name="m:personal_qualification_regist:occ_degree_work" lablename="选修课学时" class="inputText" value="${personalQualification.occ_degree_work}" validate="{maxlength:50}" isflag="tableflag" /> 
   </td> 
  </tr>
  
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">备注</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"> 
   <span name="editable-input" style="display:inline-block;" isflag="tableflag"> 
   <textarea name="m:personal_qualification_regist:occ_remark" lablename="备注" class="l-textarea" rows="5" cols="40" validate="{maxlength:1000}" isflag="tableflag">${personalQualification.occ_remark}</textarea> </span> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">附件</td> 
   <td class="formInput" rowspan="1" colspan="3">
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
    </form>
  <div class="l-window-mask" style="display: none; height: 1010px;"></div>
</body>
<script type="text/javascript">
	$(function(){
		var isRegist = "${personalQualification.isregist}";
		$("input[type='radio']").each(function(){
			if($(this).val()==isRegist){
				$(this).attr("checked",true);
			}
			
		});
	})
</script>
</html>
