
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>个人执业印章明细</title>
<%@include file="/commons/include/customForm.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
<script type="text/javascript">
	//放置脚本
</script>
<style>
  .container{
    margin: 0 16px;
  }
  .panel-toolbar{
    margin-top: 0;
  }
</style>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">个人执业印章详细信息</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<!-- <a class="link back" href="list.ht"><span></span>返回</a> -->
					</div>
				</div>
			</div>
		</div>
	</div>
  <div class="container">
    
  
	<table class="formTable" border="1" cellspacing="0" cellpadding="2" parser="addpermission"> 
 <tbody> 
  <tr class="firstRow"> 
   <td class="formHead" colspan="4" style="word-break: break-all;">个人执业印章</td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">印章编号</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput">${personalSeal.seal_num}</td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">印章名称</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput">${personalSeal.seal_name}</td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">持章人</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${personalSeal.holder} </span> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">状态</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"><c:if test="${personalSeal.status==1}">已借出</c:if>
					<c:if test="${personalSeal.status==0}">未借出</c:if> </td>
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">当前借用人</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${personalSeal.borrower} </span> </td> 
   
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">印章有效期</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"> <fmt:formatDate value='${personalSeal.effictiveDate}' pattern='yyyy-MM-dd'/></td>
  </tr> 
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">备注</td>
   <td class="formInput" rowspan="1" colspan="3" style="word-break: break-all;">${personalSeal.remark}</td>
  </tr>
 </tbody> 
</table>
</div>
</body>
</html>

