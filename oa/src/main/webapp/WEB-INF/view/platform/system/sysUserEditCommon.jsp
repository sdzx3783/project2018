<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户表明细</title>
<%@include file="/commons/include/form.jsp"%>
<style>
	.profile {
	    text-align: center;
	    margin: 20px auto;
	    width: 100px;
	    height: 100px;
	    line-height: 100px;
	    border-radius: 50%;
	    background: 50%/cover;
	    background-color: #fff;
	}
	.profile img {
	    max-width: 100%;
	}
</style>
<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
<script type="text/javascript" src="${ctx }/js/lg/plugins/ligerTab.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/HtmlUploadDialog.js" ></script>
<script type="text/javascript">
	var dialog =null;
	try{
		dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)
	}catch(e){
	}
	$(function() {
		var h = $('body').height();
		$("#tabMyInfo").ligerTab({
		});
	});
	
	$(function() {
		$("#dataFormSave").click(function() {
			submitForm();
		});
	});
	//提交表单
	function submitForm() {
		var options = {};
		if (showResponse) {
			options.success = showResponse;
		}
		var frm = $('#sysUserForm').form();
		frm.ajaxForm(options);
		if (frm.valid()) {
			frm.submit();
		}
	}

	function showResponse(responseText) {
		var obj = new com.hotent.form.ResultMessage(responseText);
		if (obj.isSuccess()) {
			$.ligerDialog.confirm(obj.getMessage() + ",是否继续操作","提示信息",function(rtn) {
				if (rtn) {
					window.location.reload();
				} else {
					window.location.href = "${returnUrl}";
				}
			});
		} else {
			$.ligerDialog.err("提示信息","用户信息保存失败!",obj.getMessage());
		}
	}
	
	//添加个人照片
	function picCallBack(array){
		if(!array && array!="") return;
		var fileId=array[0].fileId,
			fileName=array[0].fileName,
			extName=array[0].extName;
		var path= __ctx + "/platform/system/sysFile/getFileById.ht?fileId=" + fileId;
		if(/(png|gif|jpg)$/gi.test(extName)){
			$("#picture").val("/platform/system/sysFile/getFileById.ht?fileId=" + fileId);
			$("#personPic").attr("src",path);
		}
			
		else
			$.ligerDialog.warn("请选择*png,*gif,*jpg类型图片!");
				
	};
	//上传照片
	function addPic(){
			HtmlUploadDialog({max:1,size:10,callback:picCallBack});
	};
	//删除照片
	function delPic(){
		$("#picture").val("");
		$("#personPic").attr("src","${ctx}/commons/image/default_image_male.jpg");
	};
</script>
</head>
<body>

	<div class="oa-pd20">
		<a class="oa-button oa-button--primary oa-button--blue" id="dataFormSave">保存</a>
	    <a class="oa-button oa-button--primary oa-button--blue" href="${returnUrl}">返回</a>
	</div>

	<div class="oa-pd20">
		<div class="profile">
			<img id="personPic" src="${pictureLoad}" alt="个人相片" />
		</div>
		<div class="oa-text-center">
			<button class="oa-button-label" onclick="addPic();">上传照片</button>
			<button class="oa-button-label" onclick="delPic();">删除照片</button>
		</div>
	</div>

	<div class="oa-pd20">
		<form id="sysUserForm" method="post" action="updateCommon.ht">
		
			<table class="oa-table--default oa-table--second">
				<tr>
					<th>帐 号:</th>
					<td>
						${sysUser.account}
					</td>
				</tr>						
				<tr>
				    <th>用户姓名:</th>
					<td>${sysUser.fullname}</td>
				</tr>
				<tr>
					<th>用户性别: </th>
					<td>
						<c:if test="${sysUser.sex==1}">男</c:if>
						<c:if test="${sysUser.sex==0}">女</c:if>
					</td>
				</tr>						
				<tr>
					<th>入职时间: </th>
					<td>
						<fmt:formatDate value='${sysUser.entryDate}' pattern='yyyy-MM-dd'/>
					</td>
				</tr>						
				<tr>
					<th>员工状态: </th>
					<td>
						${sysUser.userStatus }	
					</td>
				</tr>						
				<tr>
					<th>是否锁定: </th>
					<td>								
						<c:if test="${sysUser.isLock==0}">未锁定</c:if>
						<c:if test="${sysUser.isLock==1}">已锁定</c:if>
					</td>				  
				</tr>
				<tr>
				    <th>是否过期: </th>
					<td>
						<c:if test="${sysUser.isExpired==0}">未过期</c:if>
						<c:if test="${sysUser.isExpired==1}">已过期</c:if>
					</td>
				</tr>
				<tr>
				    <th>当前状态: </th>
					<td>
						<c:if test="${sysUser.status==1}">激活</c:if>
						<c:if test="${sysUser.status==0}">禁用</c:if>
						<c:if test="${sysUser.status==-1}">删除</c:if>
					</td>								
				</tr>						
				<tr>
					<th>邮箱地址: </th>
				    <td><input type="text" id="email" name="email" value="${sysUser.email}" class="oa-new-input"/></td>
				</tr>
				<tr>
			    	<th>微   信: </th>
					<td><input type="text" id="weixinid" name="weixinid" value="${sysUser.weixinid}" class="oa-new-input"/></td>
				</tr>
				<tr>
					<th>手   机: </th>
					<td><input type="text" id="mobile" name="mobile" value="${sysUser.mobile}" class="oa-new-input"/></td>						   
				</tr>
				<tr>
					<th>手机短号: </th>
					<td><input type="text" id="sjdh" name="sjdh" value="${sysUser.sjdh}" class="oa-new-input"/></td>						   
				</tr>
				<tr>
				    <th>电   话: </th>
					<td><input type="text" id="phone" name="phone" value="${sysUser.phone}" class="oa-new-input"/></td>
				</tr>
				<tr>
				    <th>紧急联系人: </th>
					<td><input type="text" id="phone" name="emergency_link_person" value="${infomation.emergency_link_person}" class="oa-new-input"/></td>
				</tr>
				<tr>
				    <th>紧急联系人电话: </th>
					<td><input type="text" id="phone" name="emergency_link_person_phone" value="${infomation.emergency_link_person_phone}" class="oa-new-input"/></td>
				</tr>
				
			</table>

			<input type="hidden" name="userId" value="${sysUser.userId}" />
			<input type="hidden" id="picture" name="picture" value="${sysUser.picture}" />
		</form>	
	</div>

</body>
</html>
