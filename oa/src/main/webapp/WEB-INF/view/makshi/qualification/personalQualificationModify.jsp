<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>

<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${ctx}/js/hotent/scriptMgr.js"></script>
<script type="text/javascript">
	function afterOnload(){
		var afterLoadJs=[
		     			'${ctx}/js/hotent/formdata.js',
		     			'${ctx}/js/hotent/subform.js'
		 ];
		ScriptMgr.load({
			scripts : afterLoadJs
		});
	}
</script>
<table class="formTable" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled"> 
 <tbody> 
  <tr class="firstRow"> 
   <td class="formHead" colspan="4" style="word-break: break-all;">个人执业、注册情况表</td> 
  </tr> 
  <tr> 
   <td class="formInput" colspan="4" rowspan="1" style="word-break: break-all;"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right" width="213">员工编号</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput" width="524"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:personal_qualification:account" lablename="员工编号" class="inputText" value="${personalQualification.account}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">姓名</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:personal_qualification:name" lablename="姓名" class="inputText" value="${personalQualification.name}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right" width="0">性别</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput" width="524"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:personal_qualification:sex" lablename="性别" class="inputText" value="${personalQualification.sex}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">名族</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:personal_qualification:nation" lablename="名族" class="inputText" value="${personalQualification.nation}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right" width="0">身份证号码</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput" width="524"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">学历</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"> <span name="editable-input" style="display:inline-block;padding:2px;" class="selectinput"> <select name="m:personal_qualification:xl" lablename="学历" controltype="select" validate="{empty:false}" val="${personalQualification.xl}"> <option value="1">中专</option> <option value="2">大专</option> <option value="3">本科</option> <option value="4">硕士</option> <option value="5">博士及以上</option> <option value="6">其他</option> </select> </span> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right" width="0">毕业院校</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput" width="524"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">毕业时间</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right" width="0">职称等级</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput" width="524"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">职称专业</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right" width="0">职称等级</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput" width="524"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">职称专业</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right" width="0">职称等级</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput" width="524"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">职称专业</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right" width="0">资格证书类型</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right" width="0">资格证书编号</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput" width="524"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">资格证书发证日期</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right" width="0">执业编号</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput" width="524"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">证书专业</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right" width="0">资格证书签发单位</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput" width="524"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">转出日期</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right" width="0">备注</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <textarea name="m:personal_qualification:remark" lablename="备注" class="l-textarea" rows="5" cols="40" validate="{maxlength:1000}" isflag="tableflag">${personalQualification.remark}</textarea> </span> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right" width="0">附件</td> 
   <td style="width: 35%" class="formInput" rowspan="1" colspan="3"></td> 
  </tr> 
  <tr> 
   <td class="formInput" rowspan="1" colspan="4" style="word-break: break-all;"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">是否已注册</td> 
   <td class="formInput" rowspan="1" colspan="3" style="word-break: break-all;"> <span> <label><input val="${personalQualification.isregist}" type="radio" name="m:personal_qualification:isregist" value="0" lablename="是否已注册" validate="{}" />是</label> <label><input val="${personalQualification.isregist}" type="radio" name="m:personal_qualification:isregist" value="1" lablename="是否已注册" validate="{}" />否</label> </span> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">注册证书编号</td> 
   <td class="formInput" rowspan="1" colspan="1" style="word-break: break-all;"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">注册号</td> 
   <td class="formInput" rowspan="1" colspan="1" style="word-break: break-all;"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">注册证书发证日期</td> 
   <td class="formInput" rowspan="1" colspan="1" style="word-break: break-all;"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">注册证书有效日期</td> 
   <td class="formInput" rowspan="1" colspan="1" style="word-break: break-all;"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">注册专业</td> 
   <td class="formInput" rowspan="1" colspan="3" style="word-break: break-all;"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">执业印章号</td> 
   <td class="formInput" rowspan="1" colspan="1" style="word-break: break-all;"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">转出日期</td> 
   <td class="formInput" rowspan="1" colspan="1" style="word-break: break-all;"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">注册证书发证单位</td> 
   <td class="formInput" rowspan="1" colspan="3" style="word-break: break-all;"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">最新注册类别</td> 
   <td class="formInput" rowspan="1" colspan="1" style="word-break: break-all;"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">最新注册日期</td> 
   <td class="formInput" rowspan="1" colspan="1" style="word-break: break-all;"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">继续教育完成情况<br /></td> 
   <td class="formInput" rowspan="1" colspan="3" style="word-break: break-all;"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">备注</td> 
   <td class="formInput" rowspan="1" colspan="3" style="word-break: break-all;"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">附件</td> 
   <td class="formInput" rowspan="1" colspan="3"></td> 
  </tr> 
 </tbody> 
</table>
