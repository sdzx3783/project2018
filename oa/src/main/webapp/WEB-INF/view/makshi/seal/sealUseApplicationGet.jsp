
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>公章使用申请表明细</title>
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
				<span class="tbar-label">公章使用申请表详细信息</span>
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
   <td class="formHead" colspan="4" style="word-break: break-all;">公章使用申请表</td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">工号:</td> 
   <td class="formInput" colspan="1" rowspan="1" style="word-break: break-all;"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${sealUseApplication.account} </span> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">使用时间:</td> 
   <td class="formInput" colspan="1" rowspan="1"> <fmt:formatDate value='${sealUseApplication.appiDate}' pattern='yyyy-MM-dd'/> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">申请人:</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput">${sealUseApplication.application_person}</td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">申请部门:</td> 
   <td style="width: 35%" class="formInput"> ${sealUseApplication.department} </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">材料内容:</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${sealUseApplication.contents} </span> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">类型:</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput">
   	<c:if test="${sealUseApplication.type==1}">工程监理业务类</c:if>
   	<c:if test="${sealUseApplication.type==2}">行政文书类</c:if>
   	<c:if test="${sealUseApplication.type==3}">其他业务</c:if>
   </td> 
  </tr> 
  <tr class="toggle-view"> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">关联合同编号:</td> 
   <td style="width: 35%" class="formInput">${sealUseApplication.contract_id}</td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">关联合同名称:</td> 
   <td style="width: 35%" class="formInput">${sealUseApplication.contract_name}</td> 
  </tr> 
  <tr class="toggle-view"> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">工程进度:</td> 
   <td style="width: 35%" class="formInput" rowspan="1" colspan="3">${sealUseApplication.project_point}</td> 
  </tr> 
<!--   <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">是否直接盖章</td> 
   <td class="formInput" rowspan="1" colspan="3" style="word-break: break-all;"></td> 
  </tr>  -->
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">份数:</td> 
   <td style="width: 35%" class="formInput"> ${sealUseApplication.number} </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">附件:</td> 
   <td style="width: 35%" class="formInput"> 
    <div name="div_attachment_container" right="r"> 
     <div class="attachement"></div> 
     <textarea style="display:none" controltype="attachment" name="attachment" lablename="附件" readonly="readonly">${sealUseApplication.attachment}</textarea> 
    </div> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">备注:</td> 
   <td class="formInput" colspan="3" rowspan="1"><br /> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${sealUseApplication.remark} </span> </td> 
  </tr> 
 </tbody> 
</table>
</body>
</html>

