
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>lgEditor多样化明细</title>
<%@include file="/commons/include/customForm.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">lgEditor多样化详细信息</span>
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
	<table cellpadding="2" cellspacing="0" border="1" class="formTable">
 <tbody>
  <tr>
   <td colspan="8" class="formHead">lgEditor多样化</td>
  </tr>
  <tr>
   <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">姓名:</td>
   <td style="width:15%;" class="formInput"><span name="editable-input" style="display:inline-block;" isflag="tableflag">${lgeditors.name}</span></td>
   <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">性别:</td>
   <td style="width:15%;" class="formInput"><span name="editable-input" style="display:inline-block;padding:2px;" class="selectinput"><c:if test='${lgeditors.sex==1}'>男</c:if><c:if test='${lgeditors.sex==0}'>女</c:if></span></td>
   <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">生日:</td>
   <td style="width:15%;" class="formInput"><fmt:formatDate value='${lgeditors.birthday}' pattern='yyyy-MM-dd'/></td>
   <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">年薪:</td>
   <td style="width:15%;" class="formInput">${lgeditors.money}</td>
  </tr>
  <tr>
   <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">toWife:</td>
   <td style="width:15%;" class="formInput">${lgeditors.toWife}</td>
   <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">入职日期:</td>
   <td style="width:15%;" class="formInput"><fmt:formatDate value='${lgeditors.joinDate}' pattern='yyyy-MM-dd HH:mm:00'/></td>
   <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">结婚日期:</td>
   <td style="width:15%;" class="formInput"><fmt:formatDate value='${lgeditors.jiehunDay}' pattern='yyyy-MM-dd'/></td>
   <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">工作性质:</td>
   <td style="width:15%;" class="formInput"><span name="editable-input" style="display:inline-block;padding:2px;" class="selectinput"><c:if test='${lgeditors.jobType==零售业}'>零售业</c:if><c:if test='${lgeditors.jobType==销售业}'>销售业</c:if><c:if test='${lgeditors.jobType==物流业}'>物流业</c:if><c:if test='${lgeditors.jobType==打杂}'>打杂</c:if></span></td>
  </tr>
  <tr>
   <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">备注:</td>
   <td style="width:15%;" class="formInput"><textarea name="comment" validate="{empty:false}" readonly="readonly">${lgeditors.comment}</textarea></td>
   <td style="width:10%;" class="formTitle"></td>
   <td style="width:15%;" class="formInput"></td>
   <td style="width:10%;" class="formTitle"></td>
   <td style="width:15%;" class="formInput"></td>
   <td style="width:10%;" class="formTitle"></td>
   <td style="width:15%;" class="formInput"></td>
  </tr>
 </tbody>
</table>
</body>
</html>

