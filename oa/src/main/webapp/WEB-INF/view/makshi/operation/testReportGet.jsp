
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>运维部检测报告表明细</title>
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
				<span class="tbar-label">运维部检测报告表详细信息</span>
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
   <td class="formHead" colspan="4" style="word-break: break-all;">检测报告审批表</td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">报告名称</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${testReport.name} </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">项目名称</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${testReport.project} </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">编写人员</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"> ${testReport.editor} </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">开始日期</td>
   <td style="width: 35%; word-break: break-all;" class="formInput"> <fmt:formatDate value='${testReport.startDate}' pattern='yyyy-MM-dd'/> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">完成日期</td>
   <td style="width: 35%; word-break: break-all;" class="formInput"> <fmt:formatDate value='${testReport.endDate}' pattern='yyyy-MM-dd'/> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">提交人</td>
   <td style="width: 35%; word-break: break-all;" class="formInput"> ${testReport.applicant} </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">提交日期</td>
   <td style="width: 35%; word-break: break-all;" class="formInput"> <fmt:formatDate value='${testReport.appliDate}' pattern='yyyy-MM-dd'/> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">打印日期</td>
   <td style="width: 35%; word-break: break-all;" class="formInput"> <fmt:formatDate value='${testReport.printDate}' pattern='yyyy-MM-dd'/> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">盖章完成日期</td>
   <td style="width: 35%; word-break: break-all;" class="formInput"> <fmt:formatDate value='${testReport.sealDate}' pattern='yyyy-MM-dd'/> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">提交业主日期</td>
   <td style="width: 35%; word-break: break-all;" class="formInput"> <fmt:formatDate value='${testReport.transferDate}' pattern='yyyy-MM-dd'/> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right"><br /></td>
   <td style="width: 35%; word-break: break-all;" class="formInput"><br /></td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">备注</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${testReport.remark} </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">附件</td>
   <td style="width: 35%" class="formInput" rowspan="1" colspan="3"> 
    <div name="div_attachment_container" right="r"> 
     <div class="attachement"></div> 
     <textarea style="display:none" controltype="attachment" name="attachment" lablename="附件" readonly="readonly">${testReport.attachment}</textarea> 
    </div> </td>
  </tr>
 </tbody>
</table>
</body>
</html>

