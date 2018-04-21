
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>jqueryUI用户表明细</title>
<%@include file="/commons/include/customForm.jsp"%>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">jqueryUI用户表详细信息</span>
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
   <td colspan="8" class="formHead">jqueryUI用户表</td>
  </tr>
  <tr>
   <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">用户名:</td>
   <td style="width:15%;" class="formInput"><span name="editable-input" style="display:inline-block;" isflag="tableflag">${uiuser.UIname}</span></td>
   <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">密码:</td>
   <td style="width:15%;" class="formInput"><span name="editable-input" style="display:inline-block;" isflag="tableflag">${uiuser.password}</span></td>
   <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">年龄:</td>
   <td style="width:15%;" class="formInput"><span name="editable-input" style="display:inline-block;" isflag="tableflag">${uiuser.age}</span></td>
   <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">生日:</td>
   <td style="width:15%;" class="formInput"><fmt:formatDate value='${uiuser.birthday}' pattern='yyyy-MM-dd'/></td>
  </tr>
  <tr>
   <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">年薪:</td>
   <td style="width:15%;" class="formInput">${uiuser.salary}</td>
   <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">是否党员:</td>
   <td style="width:15%;" class="formInput"><span name="editable-input" style="display:inline-block;padding:2px;" class="selectinput"><c:if test='${uiuser.isInPart==1}'>是</c:if><c:if test='${uiuser.isInPart==0}'>否</c:if></span></td>
   <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">备注:</td>
   <td style="width:15%;" class="formInput"><textarea name="comment" validate="{empty:false}" readonly="readonly">${uiuser.comment}</textarea></td>
   <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">家庭住址:</td>
   <td style="width:15%;" class="formInput"><span name="editable-input" style="display:inline-block;" isflag="tableflag">${uiuser.homeAddress}</span></td>
  </tr>
  <tr>
   <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">所属部门:</td>
   <td style="width:15%;" class="formInput"><span name="editable-input" style="display:inline-block;" isflag="tableflag">${uiuser.department}</span></td>
   <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">入职日期:</td>
   <td style="width:15%;" class="formInput"><fmt:formatDate value='${uiuser.joinDate}' pattern='yyyy-MM-dd'/></td>
   <td style="width:10%;" class="formTitle"></td>
   <td style="width:15%;" class="formInput"></td>
   <td style="width:10%;" class="formTitle"></td>
   <td style="width:15%;" class="formInput"></td>
  </tr>
 </tbody>
</table>
</body>
</html>

