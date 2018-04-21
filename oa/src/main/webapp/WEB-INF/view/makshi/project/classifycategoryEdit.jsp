<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 项目分类类别</title>
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
			var frm=$('#classifycategoryForm').form();
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
							$('#classifycategoryForm').submit();
						});
					}else{
						frm.handleFieldName();
						$('#classifycategoryForm').submit();
					}
				}
			});
		});
		
		function showResponse(responseText) {
			var json=eval("("+responseText+")");
			if(json.result==1){
				var name=$("#name").val();
				//var isFolder=$("#isFolder").val();
				//var icon=$("#icon").val();
				if(json.operate=='add'){
					parent.addResource(json.id,name);
					$.ligerDialog.success('保存成功','提示信息');
				}
				else{
					parent.editResource(name);
					$.ligerDialog.success('编辑节点成功!','提示信息');
				}
			}
			else{
				$.ligerDialog.err('出错信息',"编辑节点失败",json.message);
			}
		}
	</script>
</head>
<body>
<div class="panel" style="height:100%;overflow:auto;">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${not empty classifycategoryItem.id}">
			        <span class="tbar-label"><span></span>编辑项目分类类别</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label"><span></span>添加项目分类类别</span>
			    </c:otherwise>	
		    </c:choose>
		</div>
		<div class="panel-toolbar">
			<div class="toolBar">
				<div class="group"><a class="link save" id="dataFormSave" href="javascript:;"><span></span>保存</a></div>
				<!-- <div class="l-bar-separator"></div>
				<div class="group"><a class="link back" href="list.ht"><span></span>返回</a></div> -->
			</div>
		</div>
	</div>
	<form id="classifycategoryForm" method="post" action="save.ht">
		<div type="custform">
			<div class="panel-detail">
				<table class="formTable" border="1" cellspacing="0" cellpadding="2" parser="addpermission"> 
 <tbody> 
  <tr class="firstRow"> 
   <td class="formHead" style="-ms-word-break: break-all;" colspan="6">项目分类类别<br /></td> 
  </tr> 
  <tr> 
   <td align="right" class="formTitle" style="width: 13%; -ms-word-break: break-all;">名称:</td> 
   <td class="formInput" style="width: 20%; -ms-word-break: break-all;"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" id="name" name="m:classifycategory:name" lablename="名称" class="inputText" value="${classifycategory.name}" validate="{maxlength:150,required:true}" isflag="tableflag" /> </span> </td> 
   <td align="right" class="formTitle" style="width: 14%; -ms-word-break: break-all;">父类类别id:</td> 
   <td class="formInput" style="width: 20%;"> <input name="m:classifycategory:subid" type="text" value="${classifycategory.subid}" showtype="{"decimalValue":"0"}" validate="{number:true,maxIntLen:20,maxDecimalLen:0}" /> </td> 
   <td align="right" class="formTitle" style="width: 13%; -ms-word-break: break-all;">路径:</td> 
   <td class="formInput" style="width: 20%;"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:classifycategory:path" lablename="路径" class="inputText" value="${classifycategory.path}" validate="{maxlength:2000}" isflag="tableflag" /> </span> </td> 
  </tr> 
  <tr> 
   <td align="right" class="formTitle" style="width: 13%; -ms-word-break: break-all;">路径名称:</td> 
   <td class="formInput" style="width: 20%;"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:classifycategory:pathname" lablename="路径名称" class="inputText" value="${classifycategory.pathname}" validate="{maxlength:2000}" isflag="tableflag" /> </span> </td> 
   <td align="right" class="formTitle" style="width: 14%; -ms-word-break: break-all;">部门:</td> 
   <td class="formInput" style="width: 20%; -ms-word-break: break-all;"> <input name="m:classifycategory:org" type="text" ctltype="selector" class="org" lablename="部门" value="${classifycategory.org}" initValue="${classifycategory.orgID}" validate="{empty:false}" readonly="readonly" showcurorg="0" /> </td> 
   <td align="right" class="formTitle" style="width: 13%; -ms-word-break: break-all;">创建人:</td> 
   <td class="formInput" style="width: 20%; -ms-word-break: break-all;"> <input name="m:classifycategory:creator" type="text" ctltype="selector" class="user" lablename="创建人" value="${classifycategory.creator}" initValue="${classifycategory.creatorID}" validate="{empty:false}" readonly="readonly" showcuruser="0" /> </td> 
  </tr> 
  <tr> 
   <td class="formInput" style="-ms-word-break: break-all;" rowspan="1" colspan="1">创建时间</td> 
   <td class="formInput" style="-ms-word-break: break-all;" rowspan="1" colspan="1"> <fmt:formatDate value='${classifycategory.ctime}' pattern='yyyy-MM-dd HH:mm:ss'/></td> 
   <td class="formInput" style="-ms-word-break: break-all;" rowspan="1" colspan="1">修改时间</td> 
   <td class="formInput" rowspan="1" colspan="1"> <fmt:formatDate value='${classifycategory.utime}' pattern='yyyy-MM-dd HH:mm:ss'/></td> 
   <td class="formInput" rowspan="1" colspan="1"></td> 
   <td class="formInput" rowspan="1" colspan="1"></td> 
  </tr> 
  <tr> 
   <td align="right" class="formTitle" style="width: 13%; -ms-word-break: break-all;">排序:</td> 
   <td class="formInput" style="width: 20%;"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:classifycategory:order" lablename="排序" class="inputText" value="${classifycategory.order}" validate="{maxlength:2000}" isflag="tableflag" /> </span> </td> 
   <td align="right" class="formTitle" style="width: 13%; -ms-word-break: break-all;">负责人:</td> 
   <td class="formInput" style="width: 20%; -ms-word-break: break-all;"> <input name="m:classifycategory:charger" type="text" ctltype="selector" class="users" lablename="负责人" value="${classifycategory.charger}" validate="{empty:false}" readonly="readonly" initValue="${classifycategory.chargerID}"  showcuruser="0" /> </td> 
  </tr> 
 </tbody> 
</table>
			</div>
		</div>
		<input type="hidden" name="id" value="${classifycategory.id}"/>
		<input type="hidden" id="saveData" name="saveData"/>
		<input type="hidden" name="executeType"  value="start" />
	</form>
</body>
</html>
