<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 公司年假福利</title>
	<%@include file="/codegen/include/customForm.jsp" %>
	<%@include file="/commons/include/ueditor.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	<script type="text/javascript" src="${ctx}/js/makshi/layer/layer.js"></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#CompanyYearVactionForm').form();
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
							$('#CompanyYearVactionForm').submit();
						});
					}else{
						frm.handleFieldName();
						$('#CompanyYearVactionForm').submit();
					}
					loadingIndex = layer.load(1, {
	                    shade: [0.1,'#858585'] //0.1透明度的白色背景
	                   });
				}
			});
		});
		
		function showResponse(responseText) {
			layer.close(loadingIndex);
			var obj = new com.hotent.form.ResultMessage(responseText);
			if (obj.isSuccess()) {
				$.ligerDialog.confirm(obj.getMessage()+",是否继续操作","提示信息", function(rtn) {
					if(rtn){
						window.location.href = window.location.href;
					}else{
						window.location.href = "${ctx}/makshi/vacation/CompanyYearVaction/list.ht";
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
			    <c:when test="${not empty CompanyYearVactionItem.id}">
			        <span class="tbar-label"><span></span>编辑公司年假福利</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label"><span></span>添加公司年假福利</span>
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
	<form id="CompanyYearVactionForm" method="post" action="save.ht">
		<div type="custform">
			<div class="panel-detail">
				<table class="formTable" border="1" cellspacing="0" cellpadding="2" parser="addpermission"> 
 <tbody> 
  <tr class="firstRow"> 
   <td class="formHead" colspan="4" style="word-break: break-all;">年假设置</td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">年份:</td> 
   <td style="width: 35%" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" readonly="readonly" name="m:w_company_year_vaction:year" lablename="年份" class="inputText" value="${CompanyYearVaction.year}" validate="{maxlength:10}" isflag="tableflag" /> </span> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">年假发放天数:</td> 
   <td style="width: 35%" class="formInput"> <input name="m:w_company_year_vaction:days" type="text" value='<fmt:formatNumber type="number" value="${CompanyYearVaction.days}" pattern="0" maxFractionDigits="0"/>' showtype="{'minValue':0,'maxValue':5}" validate="{required:true,digits:true,range:[0,5]}" /> </td> 
  </tr> 
  
 </tbody> 
</table>
			</div>
		</div>
		<input type="hidden" name="id" value="${CompanyYearVaction.id}"/>
		<input type="hidden" id="saveData" name="saveData"/>
		<input type="hidden" name="executeType"  value="start" />
	</form>
</body>
</html>
