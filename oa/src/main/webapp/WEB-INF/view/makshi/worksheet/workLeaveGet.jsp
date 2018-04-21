
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>缺勤表明细</title>
<%@include file="/commons/include/customForm.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
<style>
  .panel-toolbar{
    margin: 0;
  }
  .container{
    margin: 0 16px !important;
  }
</style>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">缺勤表详细信息</span>
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

  <div class="container">
    
 
	<table class="formTable" border="1" cellspacing="0" cellpadding="2" parser="addpermission">
 <tbody>
  <tr class="firstRow">
   <td class="formHead" colspan="2">主表</td>
  </tr>
  <tr>
   <td style="width: 20%; word-break: break-all;" class="formTitle" align="right">姓名:</td>
   <td style="width: 80%" class="formInput"> ${workLevel.fullname} </td>
  </tr>
  <tr>
   <td style="width: 20%; word-break: break-all;" class="formTitle" align="right">部门:</td>
   <td style="width: 80%" class="formInput"> ${workLevel.org} </td>
  </tr>
  <tr>
   <td style="width: 20%; word-break: break-all;" class="formTitle" align="right">类型:</td>
   <td style="width: 80%" class="formInput"> ${workLevel.type} </td>
  </tr>
  <tr>
   <td style="width: 20%; word-break: break-all;" class="formTitle" align="right">缺勤时间:</td>
   <td style="width: 80%; word-break: break-all;" class="formInput">
   		<fmt:formatDate value="${workLevel.leave_time}" pattern="yyyy-MM-dd"/>
   </td>
  </tr>
   <tr>
   <td style="width: 20%; word-break: break-all;" class="formTitle" align="right">缺勤范围:</td>
   <td style="width: 80%; word-break: break-all;" class="formInput">
   			<c:choose>
   						<c:when test="${workLevel.leave_range==0}">全天</c:when>
						<c:when test="${workLevel.leave_range==1}">上午</c:when>
						<c:otherwise>
							下午
						</c:otherwise>
			</c:choose>
   </td>
  </tr>
  <tr>
   <td style="width: 20%; word-break: break-all;" class="formTitle" align="right">登记时间:</td>
   <td style="width: 80%" class="formInput">
   		<fmt:formatDate value="${workLevel.create_time}" pattern="yyyy-MM-dd HH:mm:ss"/>
   </td>
  </tr>
  <tr>
   <td style="width: 20%; word-break: break-all;" class="formTitle" align="right">备注:</td>
   <td style="width: 80%" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${workLevel.remark} </span> </td>
  </tr>
 </tbody>
</table>

 </div>
</body>
</html>

