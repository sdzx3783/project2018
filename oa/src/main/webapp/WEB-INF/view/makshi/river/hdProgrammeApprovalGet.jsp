
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>方案审批表明细</title>
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
				<span class="tbar-label">方案审批表详细信息</span>
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
	<table class="formTable" border="1" cellspacing="0" cellpadding="2" parser="addpermission"> 
 <tbody> 
  <tr class="firstRow"> 
   <td class="formHead" colspan="4" style="word-break: break-all;">方案审批流程</td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">项目名称</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput">${hdProgrammeApproval.project_name}</td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">阶段名称<br /></td> 
   <td style="width: 35%" class="formInput">${hdProgrammeApproval.stage_name}</td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">任务名称</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput">${hdProgrammeApproval.task_name}</td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right"><span style="font-size: 12px; text-align: right; background-color: rgb(240, 241, 241);">拟稿人</span><br /></td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"> ${hdProgrammeApproval.name} </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right"><span style="font-size: 12px; text-align: right; background-color: rgb(240, 241, 241);">填报日期</span></td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"> <fmt:formatDate value='${hdProgrammeApproval.date}' pattern='yyyy-MM-dd'/> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right"><span style="font-size: 12px; text-align: right; background-color: rgb(240, 241, 241);">方案名称</span></td> 
   <td style="width: 35%" class="formInput">${hdProgrammeApproval.program_name}</td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right"><span style="font-size: 12px; text-align: right; background-color: rgb(240, 241, 241);">方案内容</span></td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${hdProgrammeApproval.content} </span> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right"><span style="font-size: 12px; text-align: right; background-color: rgb(240, 241, 241);">备注</span></td> 
   <td style="width: 35%" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${hdProgrammeApproval.note} </span> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">附件</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"> 
    <div name="div_attachment_container" right="r"> 
     <div class="attachement"></div> 
     <textarea style="display:none" controltype="attachment" name="attachment" lablename="附件" readonly="readonly">${hdProgrammeApproval.attachment}</textarea> 
    </div> </td> 
  </tr> 
  
  <tr style="display:none"> 
   <td class="formInput" colspan="1" rowspan="1"><br /></td> 
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all;"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${hdProgrammeApproval.projectTaskId} </span> </td> 
   <td class="formInput" colspan="1" rowspan="1"><br /></td> 
  </tr> 
 </tbody> 
</table>
</body>
</html>

