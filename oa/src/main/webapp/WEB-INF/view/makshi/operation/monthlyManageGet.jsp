
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>月报管理明细</title>
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
				<span class="tbar-label">月报管理详细信息</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<a class="link back" href="list.ht">返回</a>
					</div>
				</div>
			</div>
		</div>
	</div>
	<table class="formTable" parser="addpermission" data-sort="sortDisabled" cellspacing="0" cellpadding="2" border="1"> 
 <tbody> 
  <tr class="firstRow"> 
   <td class="formHead" colspan="4" style="word-break: break-all;">月报管理</td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">月报名称:</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"> 
   <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${monthlyManage.name} </span> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">项目名称:</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"> 
   <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${monthlyManage.item} </span> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">片区:</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"> 
   <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${monthlyManage.grop} </span> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">月份:</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"> ${monthlyManage.month}
 <%--   <fmt:formatDate value='${monthlyManage.month}' pattern='yyyy-MM-dd'/> --%>
    </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" width="0" align="right"></td> 
   <td style="width: 35%; word-break: break-all;" class="formInput" width="529"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">提交人</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"> ${monthlyManage.editer} </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" width="0" align="right">提交日期:</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput" width="529"><fmt:formatDate value='${monthlyManage.edit_date}' pattern='yyyy-MM-dd'/></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">备注:</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"> 
   <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${monthlyManage.remarks} </span> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">附件:</td> 
   <td style="width: 35%" class="formInput" rowspan="1" colspan="3"> 
    <div name="div_attachment_container" right="r"> 
     <div class="attachement"></div> 
     <textarea style="display:none" controltype="attachment" name="attachment" lablename="附件" readonly="readonly">${monthlyManage.attachment}</textarea> 
    </div> </td> 
  </tr> 
 </tbody> 
</table>
</body>
</html>

