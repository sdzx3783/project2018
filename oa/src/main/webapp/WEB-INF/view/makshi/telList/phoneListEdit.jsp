<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 手机号码列表</title>
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
			var frm=$('#phoneListForm').form();
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
							$('#phoneListForm').submit();
						});
					}else{
						frm.handleFieldName();
						$('#phoneListForm').submit();
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
						window.location.href = "${ctx}/makshi/telList/phoneList/list.ht";
					}
				});
			} else {
				$.ligerDialog.error(obj.getMessage(),"提示信息");
			}
		}
		
		
	</script>
    <style type="text/css">
         input.none-border-text{
             border: 0 !important;
             outline: none;
         }
    </style>
</head>
<body>
<div class="panel" style="height:100%;overflow:auto;">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${not empty phoneListItem.id}">
			        <span class="tbar-label"><span></span>编辑手机号码列表</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label"><span></span>添加手机号码列表</span>
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
	<form id="phoneListForm" method="post" action="save.ht">
		<div type="custform">
			<div class="panel-detail">
				<table class="formTable" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
 <tbody>
  <tr class="firstRow">
   <td class="formHead" colspan="4" style="word-break: break-all;">手机信息</td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">员工编号:</td>
   <td style="width: 35%" class="formInput">
   	   <span name="editable-input" style="display:inline-block;" isflag="tableflag"> 
	  	 <input id="userId" type="text" name="m:phone_list:user_id" lablename="员工编号" class="inputText" value="${phoneList.user_id}" validate="{maxlength:50}" isflag="tableflag" /> 
	   </span> 
   </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">状态:</td>
   <td style="width: 35%" class="formInput"><br />
    <span name="editable-input" style="display:inline-block;padding:2px;" class="selectinput"> <select name="m:phone_list:state" lablename="状态" controltype="select" validate="{empty:false}" val="${phoneList.state}"><option value="-1">--请选择--</option> <option value="0">在职</option> <option value="1">离职</option> </select> </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">员工姓名:</td>
   <td style="width: 35%" class="formInput">
   	 <span name="editable-input" style="display:inline-block;" isflag="tableflag"> 
	  	 <input type="text" id="fullname" name="m:phone_list:user_name" lablename="员工姓名" class="inputText" value="${phoneList.user_name}" validate="{maxlength:50}" isflag="tableflag" /> 
   	 </span>
   </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">套餐:</td>
   <td style="width: 35%" class="formInput"> 
	   <span name="editable-input" style="display:inline-block;" isflag="tableflag"> 
	  	 <input type="text" name="m:phone_list:packages" lablename="套餐" class="inputText" value="${phoneList.packages}" validate="{maxlength:50}" isflag="tableflag" /> 
	   </span> 
   </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">部门:</td>
   <td style="width: 35%" class="formInput"><br /> 
    <input name="m:phone_list:departmentID" type="hidden" lablename="部门" class="hidden" value="${phoneList.departmentID}">
   	<input name="m:phone_list:department"  id="orgName" type="text" ctltype="selector" class="orgs" lablename="部门" value="${phoneList.department}" validate="{empty:false}" readonly="readonly" /> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">定额:</td>
   <td style="width: 35%" class="formInput"> 
   <span name="editable-input" style="display:inline-block;" isflag="tableflag"> 
   <input type="text" name="m:phone_list:limit" lablename="定额" class="inputText" value="${phoneList.limit}" validate="{maxlength:50}" isflag="tableflag" /> 
   </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">手机号码:</td>
   <td style="width: 35%" class="formInput">
   <span name="editable-input" style="display:inline-block;" isflag="tableflag"> 
   <input type="text" name="m:phone_list:phone_number" lablename="手机号码" class="inputText" value="${phoneList.phone_number}" validate="{maxlength:50,required:true}" isflag="tableflag" /> 
   </span>
   </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">限额:</td>
   <td style="width: 35%" class="formInput">
   <span name="editable-input" style="display:inline-block;" isflag="tableflag"> 
   <input type="text" name="m:phone_list:max_position" lablename="限额" class="inputText" value="${phoneList.max_position}" validate="{maxlength:50}" isflag="tableflag" /> 
   </span>
   </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">短号:</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3">
   <span name="editable-input" style="display:inline-block;" isflag="tableflag"> 
   <input type="text" name="m:phone_list:short_munber" lablename="短号" class="inputText" value="${phoneList.short_munber}" validate="{maxlength:50}" isflag="tableflag" /> 
   </span>
   </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">备注:</td>
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"><br /> 
   <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <textarea name="m:phone_list:remarks" lablename="备注" class="l-textarea" rows="5" cols="40" validate="{maxlength:1000}" isflag="tableflag">${phoneList.remarks}</textarea> </span> </td>
  </tr>
 </tbody>
</table>
			</div>
		</div>
		<input type="hidden" name="id" value="${phoneList.id}"/>
		<input type="hidden" id="saveData" name="saveData"/>
		<input type="hidden" name="executeType"  value="start" />
	</form>
	
<script type="text/javascript">
$(function(){$("#userId").on("change",function(){
    var userId =  $("#userId").val();
    var alias="common_user_info_search";
    $.ajax({
        type : "POST", 
        url:"/platform/bpm/bpmFormQuery/doQuery.ht",
        data:{alias:alias,querydata:"{userId:"+userId+"}"},
        dataType: "json",
        success:function(data){ 
        	
            if(data!=null && data.list!=null && data.list.length>0){
                var rowData=data.list[0];
                setAutoPropeties("fullname",rowData.fullname,false);
                setAutoPropeties("orgName",rowData.orgname,true,rowData.orgid);
            }
	        }
	    });
	});

	function setAutoPropeties(id,val,isSelect,idValue){
	    if($("#"+id).length>0 && val!=null &&  val!=""){
	        $("#"+id).val(val);
	        $("#"+id).attr("class","none-border-text");
	        $("#"+id).attr("readonly",true);
	        if(isSelect){//是否是选择器类型的插件
	            if($("#"+id).next().length>0){
	                $("#"+id).next().hide();
	            }
	            if($("#"+id).prev().length>0){
	                $("#"+id).prev().val(idValue);
	            }
	            
	        }
	    }
	}

});

</script>	
</body>
</html>
