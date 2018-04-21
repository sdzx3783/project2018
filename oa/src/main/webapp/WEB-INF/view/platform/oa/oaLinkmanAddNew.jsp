<%--
	time:2015-07-14 09:13:58
	desc:edit the OA_LINKMAN
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑联系人</title>
	<%@include file="/commons/include/form.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript"  src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
   <script type="text/javascript" src="${ctx}/js/hotent/platform/system/FlexUploadDialog.js"></script>
   	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/HtmlUploadDialog.js" ></script>
	<script type="text/javascript">
		$(function() {
			$("a.save").click(function() {
				$("#oaLinkmanForm").attr("action","save.ht");
				$("#saveData").val(1);
				submitForm();
			});
		});
		//提交表单
		function submitForm(){
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#oaLinkmanForm').form();
			frm.ajaxForm(options);
			if(frm.valid()){
				frm.submit();
			}
		}
		
		function showResponse(responseText) {
			var obj = new com.hotent.form.ResultMessage(responseText);
			if (obj.isSuccess()) {
				$.ligerDialog.confirm("保存成功，是否继续操作",function(rtn){
		    		if(!rtn){
		    			frameElement.dialog.close();	
					}
				});
			} else {
				$.ligerDialog.err("提示信息","联系人保存失败!",obj.getMessage());
			}
		}
	</script>
</head>
<body>
<div class="panel">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${oaLinkman.id !=null}">
			        <span class="tbar-label"><span></span>编辑联系人</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label"><span></span>添加联系人</span>
			    </c:otherwise>			   
		    </c:choose>
		</div>
		<div class="panel-toolbar">
			<div class="toolBar">
				<div class="group"><a class="link save" id="dataFormSave" href="#"><span></span>保存</a></div>
				<div class="l-bar-separator"></div>
				<div class="group"><a class="link del" onclick="javasrcipt:frameElement.dialog.close()"><span></span>关闭</a></div>
			</div>
		</div>
	</div>
	<div class="panel-body">
		<form id="oaLinkmanForm" method="post" >
			<div  id="tabMyInfo" class="panel-nav" style="overflow:hidden; position:relative;">  
	           <div title="基本信息" tabid="linkmandetail" icon="${ctx}/styles/default/images/resicon/user.gif">
			           		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
								<tr>
									<th width="18%">姓名: <span class="required red">*</span></th>
									<td ><input type="text" id="name" name="name" value="${oaLinkman.name}" style="width:240px !important" class="inputText"/></td>
								</tr>						
								<tr>
								    <th>性别: <span class="required red">*</span></th>
									<td >
									<select name="sex" class="select" style="width:245px !important">
										<option value="男" <c:if test="${oaLinkman.sex=='男'}">selected</c:if> >男</option>
										<option value="女" <c:if test="${oaLinkman.sex=='女'}">selected</c:if> >女</option>
									</select>
									</td>
								</tr>
								<tr>
								    <th>电   话: </th>
									<td ><input type="text" id="phone" name="phone" value="${oaLinkman.phone}" validate="{phone:true}" style="width:240px !important" class="inputText"/></td>
								</tr>
								<tr>
								   <th>邮箱地址: </th>
								   <td ><input type="text" id="email" name="email" value="${oaLinkman.email}" validate="{email:true}" style="width:240px !important" class="inputText"/></td>
								</tr>
								<tr>
								   <th>公司: </th>
								   <td ><input type="text" id="company" name="company" value="${oaLinkman.company}" style="width:240px !important" class="inputText"/></td>
								</tr>
								<tr>
								   <th>工作: </th>
								   <td ><input type="text" id="job" name="job" value="${oaLinkman.job}" style="width:240px !important" class="inputText"/></td>
								</tr>
								<tr>
								   <th>地址: </th>
								   <td ><input type="text" id="address" name="address" value="${oaLinkman.address}" style="width:240px !important" class="inputText"/></td>
								</tr>
								<tr>
									<th>创建时间: </th>
									<td>
										<input type="text" id="createtime" name="createtime" value="<fmt:formatDate value='${oaLinkman.createtime}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" />
									</td>
								</tr>	
								<tr>
								   <th>当前状态: </th>
									<td>
										<select name="status"  class="select" style="width:245px !important" >
											<option value="1" <c:if test="${oaLinkman.status==1}">selected</c:if> >启用</option>
											<option value="0" <c:if test="${oaLinkman.status==0}">selected</c:if> >禁用</option>
										</select>
									</td>								
								</tr>	
														
							</table>
							
	           </div>
	        <input type="hidden" name="id" value="${oaLinkman.id}" />
		    <input type="hidden" name="saveData" id="saveData" />
		    <input type="hidden" name="executeType"  value="start" />
		</form>
		
	</div>
</div>
</body>
</html>
