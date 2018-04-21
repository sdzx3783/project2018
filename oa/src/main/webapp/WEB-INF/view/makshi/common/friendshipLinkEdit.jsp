<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 友情链接</title>
	<%@include file="/codegen/include/customForm.jsp" %>
	<%@include file="/commons/include/ueditor.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#friendshipLinkForm').form();
			$("a.save").click(function() {
				frm.ajaxForm(options);
				$("#saveData").val(1);
				if(frm.valid()){
					//如果有编辑器的情况下
					$("textarea[name^='m:'].myeditor").each(function(num) {
						var name=$(this).attr("name");
						var data=getEditorData(editor[num]);
						$("textarea[name^='"+name+"']").val(data);
					});
					
					if(WebSignPlugin.hasWebSignField){
						WebSignPlugin.submit();
					}
					if(OfficePlugin.officeObjs.length>0){
						OfficePlugin.submit(function(){
							frm.handleFieldName();
							$('#friendshipLinkForm').submit();
						});
					}else{
						frm.handleFieldName();
						$('#friendshipLinkForm').submit();
					}
				}
			});
		});
		
		function showResponse(responseText) {
			var obj = new com.hotent.form.ResultMessage(responseText);
			if (obj.isSuccess()) {
				$.ligerDialog.confirm(obj.getMessage()+",是否继续操作","提示信息", function(rtn) {
					if(rtn){
						window.location.href = window.location.href;
					}else{
						window.location.href = "${ctx}/makshi/common/friendshipLink/list.ht";
					}
				});
			} else {
				$.ligerDialog.error(obj.getMessage(),"提示信息");
			}
		}
	</script>
</head>
<body>
<div class="panel" style="height:100%;overflow:auto;">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${not empty friendshipLinkItem.id}">
			        <span class="tbar-label"><span></span>编辑友情链接</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label"><span></span>添加友情链接</span>
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
	<form id="friendshipLinkForm" method="post" action="save.ht">
		<div type="custform">
			<div class="panel-detail">
				<table cellpadding="2" cellspacing="0" border="1" class="formTable" parser="addpermission">
 <tbody>
  <tr class="firstRow">
   <td colspan="6" class="formHead">友情链接</td>
  </tr>
  <tr>
   <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">链接名称:</td>
   <td style="width:15%;" class="formInput"><input parser="inputtable" type="text" name="m:wFriendshipLink:name" lablename="链接名称" class="inputText" value="${friendshipLink.name}" validate="{maxlength:100,required:true}" isflag="tableflag" /></td>
   <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">链接地址:</td>
   <td style="width:15%;" class="formInput"><input parser="inputtable" type="text" name="m:wFriendshipLink:url" lablename="链接地址" class="inputText" value="${friendshipLink.url}" validate="{maxlength:1000,required:true}" isflag="tableflag" /></td>
   <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">链接优先级:</td>
   <td style="width:15%;" class="formInput"><input parser="inputtable" name="m:wFriendshipLink:priority" validate="{required:true}" type="text" value="${friendshipLink.priority}" showtype="{"coinValue":"","isShowComdify":0,"decimalValue":0}" validate="{number:true,maxIntLen:6,maxDecimalLen:0}" /></td>
  </tr>
 </tbody>
</table>
			</div>
		</div>
		<input type="hidden" name="id" value="${friendshipLink.id}"/>
		<input type="hidden" id="saveData" name="saveData"/>
		<input type="hidden" name="executeType"  value="start" />
	</form>
</body>
</html>
