<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 数字运算</title>
	<%@include file="/codegen/include/customForm.jsp" %>
	<%@include file="/commons/include/ueditor.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/FlowDetailWindow.js"></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#szysForm').form();
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
							$('#szysForm').submit();
						});
					}else{
						frm.handleFieldName();
						$('#szysForm').submit();
					}
				}
			});
			$("a.run").click(function() {
				frm.ajaxForm(options);
				$("#saveData").val(0);
				$('#szysForm').attr("action","run.ht");
				if(frm.valid()){
					if(WebSignPlugin.hasWebSignField){
						WebSignPlugin.submit();
					}
					data=CustomForm.getData();
					//设置表单数据
					$("#formData").val(data);
					frm.handleFieldName();
					OfficePlugin.submit();
					$('#szysForm').submit();
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
						window.location.href = "${ctx}/demo/test/szys/list.ht";
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
			    <c:when test="${not empty szysItem.id}">
			        <span class="tbar-label"><span></span>编辑数字运算</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label"><span></span>添加数字运算</span>
			    </c:otherwise>	
		    </c:choose>
		</div>
		<div class="panel-toolbar">
			<div class="toolBar">
				<div class="group"><a class="link save" id="dataFormSave" href="javascript:;"><span></span>保存</a></div>
				<div class="l-bar-separator"></div>
				<c:if test="${runId!=0}">
					<div class="group"><a class="link detail"  onclick="FlowDetailWindow({runId:${runId}})" href="javascript:;" ><span></span>流程明细</a></div>
					<div class="l-bar-separator"></div>
				</c:if>
				<div class="group"><a class="link back" href="list.ht"><span></span>返回</a></div>
			</div>
		</div>
	</div>
	<form id="szysForm" method="post" action="save.ht">
		<div type="custform">
			<div class="panel-detail">
				<table class="formTable" border="1" cellspacing="0" cellpadding="2" parser="addpermission">
 <tbody>
  <tr class="firstRow">
   <td class="formHead" colspan="4">主 &nbsp; &nbsp; &nbsp;表</td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">数字一:</td>
   <td style="width: 35%" class="formInput"> <input name="m:szys:szy" type="text" value="${szys.szy}" showtype="{"isShowComdify":1,"coinValue":"","decimalValue":"0","minValue":1,"maxValue":9999}" validate="{number:true,maxIntLen:14,maxDecimalLen:0}" /> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">数字二:</td>
   <td style="width: 35%; word-break: break-all;" class="formInput"> <input name="m:szys:sze" type="text" value="${szys.sze}" showtype="{"isShowComdify":0,"coinValue":"","decimalValue":"0"}" validate="{number:true,maxIntLen:14,maxDecimalLen:0}" /> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">合计:</td>
   <td style="width: 35%" class="formInput"> <input name="m:szys:hj" type="text" value="${szys.hj}" showtype="{"decimalValue":"2"}" validate="{number:true,maxIntLen:14,maxDecimalLen:2}" funcexp="{数字一(m:szys:szy)}+{数字二(m:szys:sze)}" /> </td>
   <td style="width: 15%" class="formTitle" align="right">主表字段四:</td>
   <td style="width: 35%" class="formInput"><br /></td>
  </tr>
 </tbody>
</table>
			</div>
		</div>
		<input type="hidden" name="id" value="${szys.id}"/>
		<input type="hidden" id="saveData" name="saveData"/>
		<input type="hidden" name="executeType"  value="start" />
	</form>
</body>
</html>
