<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 大车使用申请</title>
	<%@include file="/codegen/include/customForm.jsp" %>
	<%@include file="/commons/include/ueditor.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/FlowDetailWindow.js"></script>
	<script type="text/javascript" src="${ctx}/js/util/util.js"></script>
	<script type="text/javascript" src="${ctx}/js/lg/ligerui.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/displaytag.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery/plugins/jquery.htselect.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery/plugins/jquery.rowOps.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/foldBox.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/absoulteInTop.js"></script>
	<script type="text/javascript" src="${ctx}/js/util/ObjectUtil.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/displaytag.js"></script>
	<script type="text/javascript" src="${ctx}/js/lg/ligerui.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/FormUtil.js"></script>
	<script type="text/javascript">
	
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#carUseForm').form();
			
			$("a.save").click(function() {
				alert(4);
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
							$('#carUseForm').submit();
						});
					}else{
						frm.handleFieldName();
						$('#carUseForm').submit();
					}
				}
			});
			$("a.run").click(function() {
				alert(1);return false;alert(3);
				frm.ajaxForm(options);
				$("#saveData").val(0);
				$('#carUseForm').attr("action","run.ht");
				if(frm.valid()){
					if(WebSignPlugin.hasWebSignField){
						WebSignPlugin.submit();
					}
					data=CustomForm.getData();
					//设置表单数据
					$("#formData").val(data);
					frm.handleFieldName();
					OfficePlugin.submit();
					$('#carUseForm').submit();
				}
			});
		});
		
		function showResponse(responseText) {
			var obj = new com.hotent.form.ResultMessage(responseText);
			if (obj.isSuccess()) {
				alert(2);
				$.ligerDialog.confirm(obj.getMessage()+",是否继续操作","提示信息", function(rtn) {
					if(rtn){
						window.location.href = window.location.href;
					}else{
						window.location.href = "${ctx}/makshi/opreation/carUse/list.ht";
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
	<form id="carUseForm" method="post" action="save.ht">
		<div type="custform">
			<div class="panel-detail">
				<table class="formTable" parser="addpermission" data-sort="sortDisabled" cellspacing="0" cellpadding="2" border="1">
 <tbody>
  <tr class="firstRow">
   <td class="formHead" colspan="4" style="word-break: break-all;">大车使用申请</td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">申请车辆</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:car_use:applicar" lablename="申请车辆" class="inputText" value="${carUse.applicar}" validate="{maxlength:50}" isflag="tableflag" /> </span> 
   <a href="javaScript:void(0)" class="extend message" name="applicar" eventbtn="[{'name':'carsInfo','fields':[{'src':'F_carId','target':['car_use-applicar']}],'query':[],'tabName':'自定义对话框','isAddRow':true,'type':'dialog'}]">选择</a>
   </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">申请人</td>
   <td style="width: 35%; word-break: break-all;" class="formInput"> <input name="m:car_use:applicant" type="text" ctltype="selector" class="user" lablename="申请人" value="${carUse.applicant}" validate="{empty:false}" readonly="readonly" showcuruser="0" /> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">申请时间</td>
   <td style="width: 35%; word-break: break-all;" class="formInput"> <input name="m:car_use:appliDate" type="text" class="Wdate" displaydate="0" datefmt="yyyy-MM-dd" value="<fmt:formatDate value='${carUse.appliDate}' pattern='yyyy-MM-dd'/>" validate="{empty:false}" /> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">使用地点</td>
   <td style="width: 35%; word-break: break-all;" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:car_use:address" lablename="使用地点" class="inputText" value="${carUse.address}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">是否需要下井</td>
   <td style="width: 35%; word-break: break-all;" class="formInput"> <span name="editable-input" style="display:inline-block;padding:2px;" class="selectinput"> <select name="m:car_use:down" lablename="是否需要下井" controltype="select" validate="{empty:false}" val="${carUse.down}"> <option value="否">否</option> <option value="是">是</option> </select> </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">使用开始时间</td>
   <td style="width: 35%; word-break: break-all;" class="formInput"> <input name="m:car_use:startTime" type="text" class="Wdate" displaydate="0" datefmt="yyyy-MM-dd" value="<fmt:formatDate value='${carUse.startTime}' pattern='yyyy-MM-dd'/>" validate="{empty:false}" /> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">使用结束时间</td>
   <td style="width: 35%; word-break: break-all;" class="formInput"> <input name="m:car_use:endTime" type="text" class="Wdate" displaydate="0" datefmt="yyyy-MM-dd" value="<fmt:formatDate value='${carUse.endTime}' pattern='yyyy-MM-dd'/>" validate="{empty:false}" /> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">用车原因</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <textarea name="m:car_use:reason" lablename="用车原因" class="l-textarea" rows="5" cols="40" validate="{maxlength:1000}" isflag="tableflag">${carUse.reason}</textarea> </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">安排车辆</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" id="unUseCar"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:car_use:carName" lablename="安排车辆" class="inputText" value="${carUse.carName}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">安排司机</td>
   <td style="width: 35%; word-break: break-all;" class="formInput"> <input name="m:car_use:driver" type="text" ctltype="selector" class="user" lablename="安排司机" value="${carUse.driver}" validate="{empty:false}" readonly="readonly" showcuruser="0" /> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">附件</td>
   <td style="width: 35%" class="formInput" rowspan="1" colspan="3"></td>
  </tr>
 </tbody>
</table>
			</div>
		</div>
		<input type="hidden" name="id" value="${carUse.id}"/>
		<input type="hidden" id="saveData" name="saveData"/>
		<input type="hidden" name="executeType"  value="start" />
	</form>
</body>
</html>
