<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 jqueryUI用户表</title>
	<%@include file="/commons/include/customForm.jsp" %>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#uiuserForm').form();
			$("a.save").click(function() {
				frm.ajaxForm(options);
				if(frm.valid()){
					OfficePlugin.submit();
					if(WebSignPlugin.hasWebSignField){
						WebSignPlugin.submit();
					}
					$('#uiuserForm').submit();
				}
			});
		});
		
		function showResponse(responseText) {
			var obj = new com.hotent.form.ResultMessage(responseText);
			if (obj.isSuccess()) {
				$.ligerDialog.confirm(obj.getMessage()+",是否继续操作","提示信息", function(rtn) {
					if(rtn){
						this.close();
					}else{
						window.location.href = "${ctx}/demo/LigerUI/uiuser/list.ht";
					}
				});
			} else {
				$.ligerDialog.error(obj.getMessage(),"提示信息");
			}
		}
	</script>
</head>
<body>
<div class="panel">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${not empty uiuserItem.id}">
			        <span class="tbar-label"><span></span>编辑jqueryUI用户表</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label"><span></span>添加jqueryUI用户表</span>
			    </c:otherwise>	
		    </c:choose>
		</div>
		<div class="panel-toolbar">
			<div class="toolBar">
				<div class="group"><a class="link save" id="dataFormSave" href="javascript:;"><span></span>保存</a></div>
				<div class="l-bar-separator"></div>
				<div class="group"><a class="link back" href="list.ht"><span></span>返回</a></div>
			</div>
		</div>
	</div>
	<form id="uiuserForm" method="post" action="save.ht">
		<div type="custform">
			<div class="panel-detail">
				<table cellpadding="2" cellspacing="0" border="1" class="formTable">
 <tbody>
  <tr>
   <td colspan="8" class="formHead">jqueryUI用户表</td>
  </tr>
  <tr>
   <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">用户名:</td>
   <td style="width:15%;" class="formInput"><span name="editable-input" style="display:inline-block;" isflag="tableflag"><input type="text" name="UIname" lablename="用户名" class="inputText" validate="{maxlength:50}" isflag="tableflag" value="${uiuser.UIname}" /></span></td>
   <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">密码:</td>
   <td style="width:15%;" class="formInput"><span name="editable-input" style="display:inline-block;" isflag="tableflag"><input type="text" name="password" lablename="密码" class="inputText" validate="{maxlength:2000}" isflag="tableflag" value="${uiuser.password}" /></span></td>
   <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">年龄:</td>
   <td style="width:15%;" class="formInput"><span name="editable-input" style="display:inline-block;" isflag="tableflag"><input type="text" name="age" lablename="年龄" class="inputText" validate="{maxlength:2000}" isflag="tableflag" value="${uiuser.age}" /></span></td>
   <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">生日:</td>
   <td style="width:15%;" class="formInput"><input name="birthday" type="text" class="Wdate" displaydate="0" validate="{empty:false}" value="<fmt:formatDate value='${uiuser.birthday}' pattern='yyyy-MM-dd'/>" /></td>
  </tr>
  <tr>
   <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">年薪:</td>
   <td style="width:15%;" class="formInput"><input name="salary" type="text" showtype="{'coinValue':'','isShowComdify':false,'decimalValue':0}" validate="{number:true,maxIntLen:13,maxDecimalLen:0}" value="${uiuser.salary}" /></td>
   <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">是否党员:</td>
   <td style="width:15%;" class="formInput"><span name="editable-input" style="display:inline-block;padding:2px;" class="selectinput"><select name="isInPart" lablename="是否党员" controltype="select" validate="{empty:false}"><option value="1" <c:if test='${uiuser.isInPart==1}'>selected='selected'</c:if>>是</option><option value="0" <c:if test='${uiuser.isInPart==0}'>selected='selected'</c:if>>否</option></select></span></td>
   <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">备注:</td>
   <td style="width:15%;" class="formInput"><textarea name="comment" validate="{empty:false}">${uiuser.comment}</textarea></td>
   <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">家庭住址:</td>
   <td style="width:15%;" class="formInput"><span name="editable-input" style="display:inline-block;" isflag="tableflag"><input type="text" name="homeAddress" lablename="家庭住址" class="inputText" validate="{maxlength:50}" isflag="tableflag" value="${uiuser.homeAddress}" /></span></td>
  </tr>
  <tr>
   <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">所属部门:</td>
   <td style="width:15%;" class="formInput"><span name="editable-input" style="display:inline-block;" isflag="tableflag"><input type="text" name="department" lablename="所属部门" class="inputText" validate="{maxlength:2000}" isflag="tableflag" value="${uiuser.department}" /></span></td>
   <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">入职日期:</td>
   <td style="width:15%;" class="formInput"><input name="joinDate" type="text" class="Wdate" displaydate="0" validate="{empty:false}" value="<fmt:formatDate value='${uiuser.joinDate}' pattern='yyyy-MM-dd'/>" /></td>
   <td style="width:10%;" class="formTitle"></td>
   <td style="width:15%;" class="formInput"></td>
   <td style="width:10%;" class="formTitle"></td>
   <td style="width:15%;" class="formInput"></td>
  </tr>
 </tbody>
</table>
			</div>
		</div>
		<input type="hidden" name="id" value="${uiuser.id}"/>
	</form>
</body>
</html>
