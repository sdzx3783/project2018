
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>收文汇总表明细</title>
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
				<span class="tbar-label">收文汇总表详细信息</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<div class="group"><a class="link detail"  onclick="FlowDetailWindow({runId:${runId}})" href="javascript:;" ><span></span>流程明细</a></div>
						<div class="l-bar-separator"></div>
						<c:if test="${type==1 }"><a class="link back" href="sendList.ht"><span></span>返回</a></c:if>
						<c:if test="${type!=1 }"><a class="link back" href="list.ht"><span></span>返回</a></c:if>
					
					</div>
				</div>
			</div>
		</div>
	</div>
	<table class="formTable" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
 <tbody>
  <tr class="firstRow">
   <td class="formHead" colspan="4" style="word-break: break-all;">收文汇总表</td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">收文编号:</td>
   <td style="width: 35%" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${receiptAll.receipt_id} </span> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">收文日期:</td>
   <td style="width: 35%" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${receiptAll.time} </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">来文标题:</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${receiptAll.title} </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">来文单位:</td>
   <td style="width: 35%" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${receiptAll.dispatch_unit} </span> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">紧急程度:</td>
   <td style="width: 35%" class="formInput">
   	<c:if test="${receiptAll.urgency_degree==1}">普通</c:if>
   	<c:if test="${receiptAll.urgency_degree==2}">紧急</c:if>
   	<c:if test="${receiptAll.urgency_degree==3}">非常紧急</c:if>
   </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">来文字号:</td>
   <td style="width: 35%" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${receiptAll.type} </span> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">办理时限:</td>
   <td style="width: 35%" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> 
   <fmt:formatDate   value="${receiptAll.time_limit}" pattern="yyyy-MM-dd" type="date" dateStyle="long" />
 </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">相关内容:</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"><br /> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${receiptAll.content} </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">初审人员:</td>
    <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${receiptAll.first_check_person} </span> </td>
  </tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">办文人员:</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${receiptAll.handle_person} </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">附件:</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"><br /> 
    <div name="div_attachment_container" right="r"> 
     <div class="attachement"></div> 
     <textarea style="display:none" controltype="attachment" name="attachment" lablename="附件" readonly="readonly">${receiptAll.attachment}</textarea> 
    </div> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">办理内容:</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"><br /> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${receiptAll.remarks} </span> </td>
  </tr>
  <tr style="display:none">
   <td class="formInput" rowspan="1" colspan="4"> ${receiptAll.state} </td>
  </tr>
 </tbody>
</table>
</body>
</html>

