
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>个人执业资格明细</title>
<%@include file="/commons/include/customForm.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">个人执业资格详细信息</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<a class="link back" href="list.ht"><span></span>返回</a>
					</div>
				</div>
			</div>
		</div>
	</div>
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
   <td style="width: 35%; word-break: break-all;" class="formInput" width="524"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${personalQualification.account} </span> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">姓名</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${personalQualification.name} </span> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right" width="0">性别</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput" width="524"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${personalQualification.sex} </span> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">名族</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${personalQualification.nation} </span> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right" width="0">身份证号码</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput" width="524"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">学历</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"> ${personalQualification.xl} </td> 
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
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${personalQualification.remark} </span> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right" width="0">附件</td> 
   <td style="width: 35%" class="formInput" rowspan="1" colspan="3"> 
    <div name="div_attachment_container" right="r"> 
     <div class="attachement"></div> 
     <textarea style="display:none" controltype="attachment" name="attachment" lablename="附件" readonly="readonly">${personalQualification.attachment}</textarea> 
    </div> </td> 
  </tr> 
  <tr> 
   <td class="formInput" rowspan="1" colspan="4" style="word-break: break-all;"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">是否已注册</td> 
   <td class="formInput" rowspan="1" colspan="3" style="word-break: break-all;"> <span> <label><input data="${personalQualification.isregist}" type="radio" name="isregist" value="0" lablename="是否已注册" validate="{}" disabled="disabled" />是</label> <label><input data="${personalQualification.isregist}" type="radio" name="isregist" value="1" lablename="是否已注册" validate="{}" disabled="disabled" />否</label> </span> </td> 
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
</body>
</html>

