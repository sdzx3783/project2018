<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>发文总表明细</title>
<%@include file="/commons/include/customForm.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/FlowDetailWindow.js"></script>
<style type="text/css">
		#office_div_m_dispatch_dispatch_content{
		height:750px !important;
	}
	
</style>
<body class="oa-mw-page">
<div class="oa-pd20">
	<div class="oa-mg20">
		<form id="dispatchForm" method="post" action="save.ht">
			<table class="oa-table--default" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
				<caption>发文总表明细</caption>
 		    	<tbody>
 					<tr>
 						<th>发文类型</th>
 						<td>${dispatch.type}</td>
 						<th>发文时间</th>
 						<td><fmt:formatDate value="${dispatch.time}" pattern='yyyy-MM-dd'/></td>
 					</tr>
 					<tr>
 						<th>发文标题</th>
 						<td colspan="3">${dispatch.title}</td>
 					</tr>
 					<tr>
 						<th>发文字号</th>
 						<td colspan="3">${dispatch.dispatch_id}</td>
 					</tr>
 					<tr>
 						<th>主题词</th>
 						<td colspan="3">${dispatch.keyword}</td>
 					</tr>
 					<tr>
 						<th rowspan="2">主送单位</th>
 						<td>${dispatch.send_unit_department}</td>
 						<th rowspan="2">抄送单位</th>
 						<td>${dispatch.cc_unit_department}</td>
 					</tr>
 					<tr>
 						<td>${dispatch.send_unit_person}</td>
 						<td>${dispatch.cc_unit_person}</td>
 					</tr>
 					<tr>
 						<th>正文</th>
 						<td colspan="3">
 							<input parser="officetable" type="hidden" class="hidden" name="m:dispatch:dispatch_content" lablename="正文"  right="r" controltype="office" value="${dispatch.dispatch_content}"/>
 						</td>
 					</tr>
  					<tr>
  						<th>拟稿人</th>
   							<td  colspan="3">${dispatch.draft_person}</td>
   					</tr>
   					<tr>
   						<th>附件</th>
  						<td colspan="3">
   	 						<input ctltype="attachment" name="m:dispatch:attachment" isdirectupload="0" right="r" value='${dispatch.attachment}' validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}" /></td>
   						</td>
  					</tr>
				</tbody> 
			</table>
		</form>
	</div>
</div>
</body>
</html>
