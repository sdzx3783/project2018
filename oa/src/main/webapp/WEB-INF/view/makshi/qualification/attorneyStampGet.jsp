
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>法定委托书盖章申请明细</title>
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
				<span class="tbar-label">法定委托书盖章申请详细信息</span>
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
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">申请人:</td>
   <td style="width: 35%; word-break: break-all;" class="formInput"> ${attorneyStamp.applicant} </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">申请部门:</td>
   <td style="width: 35%; word-break: break-all;" class="formInput"> ${attorneyStamp.org} </td>
  </tr>
  <tr class="firstRow">
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">申请时间:</td>
   <td style="width: 35%; word-break: break-all;" class="formInput"> <fmt:formatDate value='${attorneyStamp.submittime}' pattern='yyyy-MM-dd'/> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">证书字号:</td>
   <td style="width: 35%; word-break: break-all;" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${attorneyStamp.cerno} </span> </td>
  </tr>
  <tr>
   <td style="" class="formTitle" align="left" rowspan="1" colspan="1" width="371">法定委托书基本信息</td>
   <td rowspan="1" valign="null" align="left" width="371"><br /></td>
   <td rowspan="1" valign="null" align="left" width="371"><br /></td>
   <td rowspan="1" valign="null" align="left" width="371"><br /></td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">姓名:</td>
   <td style="width: 35%" class="formInput" colspan="3"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${attorneyStamp.name} </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">权限:</td>
   <td style="width: 35%" class="formInput" colspan="3"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${attorneyStamp.privilege} </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">有效期限:</td>
   <td style="width: 35%; word-break: break-all;" class="formInput"> <fmt:formatDate value='${attorneyStamp.limittime}' pattern='yyyy-MM-dd'/> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">签发日期:</td>
   <td style="width: 35%" class="formInput"> <fmt:formatDate value='${attorneyStamp.signtime}' pattern='yyyy-MM-dd'/> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">代表人性别:</td>
   <td style="width: 35%" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${attorneyStamp.representor} </span> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">年龄:</td>
   <td style="width: 35%" class="formInput"> ${attorneyStamp.age} </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">工作证号码:</td>
   <td style="width: 35%" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${attorneyStamp.workno} </span> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">职务:</td>
   <td style="width: 35%" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${attorneyStamp.job} </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">营业执照号码:</td>
   <td style="width: 35%" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${attorneyStamp.businessnum} </span> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">经济性质:</td>
   <td style="width: 35%" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${attorneyStamp.economicnature} </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right"><span style="font-size: 12px; text-align: right; background-color: rgb(240, 241, 241);">主营(产):</span></td>
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"><span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${attorneyStamp.major_pro} </span></td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">兼营(产):</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"><span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${attorneyStamp.sideline_pro} </span></td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right"><span style="font-size: 12px; text-align: right; background-color: rgb(240, 241, 241);">进口物品经营许可证号码:</span></td>
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${attorneyStamp.licenseno} </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">主营:</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${attorneyStamp.major} </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">兼营</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${attorneyStamp.sideline} </span> </td>
  </tr>
 </tbody>
</table>
</body>
</html>

