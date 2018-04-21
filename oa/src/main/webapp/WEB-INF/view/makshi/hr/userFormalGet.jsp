
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>员工转正表明细</title>
<%@include file="/commons/include/customForm.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/FlowDetailWindow.js"></script>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">员工转正表详细信息</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<div class="group"><a class="link detail"  onclick="FlowDetailWindow({runId:${runId}})" href="javascript:;" ><span></span>流程明细</a></div>
						<div class="l-bar-separator"></div>
						<a class="link back" href="list.ht"><span></span>返回</a>
					</div>
				</div>
			</div>
		</div>
	</div>
	<table class="formTable" parser="addpermission" data-sort="sortDisabled" cellspacing="0" cellpadding="2" border="1">
 <tbody>
  <tr class="firstRow">
   <td class="formHead" colspan="4" style="word-break: break-all;">员工转正表</td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" width="0" align="right">申请人:</td>
   <td style="width: 35%" class="formInput" id="fullname" width="467"><br /></td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">劳动合同约定转正时间:</td>
   <td style="width: 35%" class="formInput"></td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" width="0" align="right">员工编号:</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" id="userId_procedure" width="467"></td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">职称:</td>
   <td style="width: 35%" class="formInput" id="positional"><br /></td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" width="0" align="right">入职日期:</td>
   <td class="formInput" colspan="1" rowspan="1" id="entrydate" width="467"><br /></td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">专业:</td>
   <td class="formInput" colspan="1" rowspan="1" id="major"><br /></td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" width="0" align="right">试用期所在部门及项目部:</td>
   <td class="formInput" colspan="1" rowspan="1" id="orgpathname" width="467"><br /></td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">学历:</td>
   <td class="formInput" colspan="1" rowspan="1" id="education"><br /></td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" width="0" align="right">岗位工资</td>
   <td class="formInput" colspan="3" rowspan="1"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${userFormal.salary} </span> </td>
  </tr>
 </tbody>
</table>
<table class="formTable" parser="addpermission" cellspacing="0" cellpadding="2" border="1">
 <tbody id="qualification">
  <tr class="firstRow"></tr>
 </tbody>
</table>
</body>
</html>

