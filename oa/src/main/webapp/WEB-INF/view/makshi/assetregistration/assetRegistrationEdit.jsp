<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
    <title>编辑 资产登记表</title>
    <%@include file="/codegen/include/customForm.jsp" %>
    <%@include file="/commons/include/ueditor.jsp" %>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/form/AttachMent.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
    <script type="text/javascript" src="${ctx}/js/jquery/plugins/jquery.attach.js" ></script>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/system/FlexUploadDialog.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/system/HtmlUploadDialog.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/form/TablePermission.js"></script>
    <script type="text/javascript">
        $(function() {
            AttachMent.init();
            var options={};
            if(showResponse){
                options.success=showResponse;
            }
            var frm=$('#assetRegistrationForm').form();
            $("a.save").click(function() {
              /*   var assetType = $('#masset_registrationasset_type_id').val();
                $("#selectAssetType").val(assetType);  */
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
                            $('#assetRegistrationForm').submit();
                        });
                    }else{
                        frm.handleFieldName();
                        $('#assetRegistrationForm').submit();
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
                    	window.close(); 
                    }
                });
            } else {
                $.ligerDialog.error(obj.getMessage(),"提示信息");
            }
        }      
        $(function(){
        	var assetType = '${assetRegistration.asset_code}';
            $("#selectAssetType").val(assetType);
        });
        
        
    </script>
    <style>
        .l-box-select-inner{background: #e1e5f0;}
        #assetRegistrationForm{
            margin: 0 10px;
        }
        .l-box-select.l-box-select-absolute{
          height: 150px !important;
        }

    </style>
</head>
<body class="oa-mw-page">
		<div class="oa-pd20">
        	<a class="oa-button oa-button--primary oa-button--blue save" id="dataFormSave">保存</a>
	        <!-- <a class="oa-button oa-button--primary oa-button--blue" href="list.ht">返回</a> -->
        </div>
	    <div class="oa-mg20">
    	    <form id="assetRegistrationForm" method="post" action="save.ht">
    	    	<table class="oa-table--default" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
		             <c:choose>
		                <c:when test="${not empty assetRegistrationItem.id}">
		                   <caption>编辑资产登记表</caption>
		                </c:when>
		                <c:otherwise><caption>添加资产登记表</caption></c:otherwise> 
		             </c:choose>
					 <tbody>
						  <tr class="firstRow">
						   <th>资产编号<span class="red">*</span></th>
						   <td>
						        <input type="text" name="m:asset_registration:asset_id" lablename="资产编号" class="oa-new-input" value="${assetRegistration.asset_id}" validate="{&quot;maxlength&quot;:10,&quot;required&quot;:&quot;true&quot;}" isflag="tableflag" />
						   </td>
						   <th>取得日期<span class="red">*</span></th>
						   <td>
						    <input type="text" name="m:asset_registration:get_date" value="<fmt:formatDate value='${assetRegistration.get_date}' pattern='yyyy-MM-dd'/>" class="date" data-class="oa-new-input" validate="{required:true}" />
						   </td>
						  </tr>
						  <tr>
						    <th>条码编号<span class="red">*</span></th>
						    <td><input type="text" name="m:asset_registration:card_number" lablename="条码编号" class="oa-new-input" value="${assetRegistration.card_number}" validate="{&quot;maxlength&quot;:50,&quot;required&quot;:&quot;true&quot;}" isflag="tableflag" /></td>
						    <td></td>
						    <td></td>
						  </tr>
						  <tr>
						    <th>保管人<span class="red">*</span></th>
						   <td>
						   <input id="userId" name="m:asset_registration:care_person" type="text" ctltype="selector" class="user-search oa-new-input oa-middle user" lablename="保管人" value="${assetRegistration.care_person}" validate="{required:true}" /></td>
						   <th>使用部门<span class="red">*</span></th>
						   <td><input name="m:asset_registration:use_department" type="text" ctltype="selector" class="oa-new-input oa-middle org" lablename="使用部门" value="${assetRegistration.use_department }"  validate="{required:true}" /></td>
						  </tr>
						  <tr>
						   <th>资产分类代码</th>
						   <td><input id="selectAssetType"  class="dicComboTree l-text-field" nodekey="assetType" value="${assetRegistration.asset_type}" name="m:asset_registration:asset_type" validate="{empty:false}"  style="height:23px; width:250px"/></td>
						   <th>报废时间</th>
						   <td>
						     <input type="text" name="m:asset_registration:abandonment_date" value="<fmt:formatDate value='${assetRegistration.abandonment_date}' pattern='yyyy-MM-dd'/>" class="date" data-class="oa-new-input" />
						  </td>
						  </tr>
						  <tr>
						   <th>资产名称<span class="red">*</span></th>
						   <td><input type="text" name="m:asset_registration:asset_name" lablename="资产名称" class="oa-new-input" value="${assetRegistration.asset_name}" validate="{&quot;maxlength&quot;:50,&quot;required&quot;:&quot;true&quot;}" isflag="tableflag" /></td>
						   <th>使用日期<span class="red">*</span></th>
						   <td>
						    <input type="text" name="m:asset_registration:use_date" value="<fmt:formatDate value='${assetRegistration.use_date}' pattern='yyyy-MM-dd'/>" class="date" data-class="oa-new-input" validate="{required:true}" />
						   </td>
						  </tr>
						  <tr>
						   <th>规格型号<span class="red">*</span></th>
						   <td><input type="text" name="m:asset_registration:specification" lablename="规格型号" class="oa-new-input" value="${assetRegistration.specification}" validate="{&quot;maxlength&quot;:50,&quot;required&quot;:&quot;true&quot;}" isflag="tableflag" /></td>
						   <th>数量<span class="red">*</span></th>
						   <td><input type="text" name="m:asset_registration:number" lablename="数量" class="oa-new-input" value="${assetRegistration.number}" validate="{&quot;maxlength&quot;:50,&quot;required&quot;:&quot;true&quot;}" isflag="tableflag" /></td>
						  </tr>
						  <tr>
						    <th>资产类别</th>
						   <td><input id="assetMainType" name="m:asset_registration:version" type="text" lablename="资产分类代码" class="oa-new-input" value="${assetRegistration.version}" validate="{maxlength:50,required:true}" isflag="tableflag" readonly="readonly" /></td>
						   <th>使用状况</th>
						   <td>
						    <select class='oa-new-select' name="m:asset_registration:state" lablename="使用状况" val="${assetRegistration.state}"> 
						   	 <option value="0" <c:if test="${assetRegistration.state == 0}">selected</c:if> >在用</option> 
						   	 <option value="1" <c:if test="${assetRegistration.state == 1}">selected</c:if> >报废</option> 
						    </select></td>
						  </tr>
						  <tr>
						   <th>取得方式</th>
						   <td>
						        <select  class='oa-new-select' value="${assetRegistration.get_type}" name="m:asset_registration:get_type" lablename="取得方式" >
						       	    <option  value=0 <c:if test="${assetRegistration.get_type == 0}">selected</c:if> >领用旧设备</option>
						            <option  value=1 <c:if test="${assetRegistration.get_type == 1}">selected</c:if> >委托办公室购买</option>
						            <option  value=2 <c:if test="${assetRegistration.get_type == 2}">selected</c:if> >项目部自行购买</option>
						        </select>
						   </td>
						   <th>上传贴条照片</th>
						   <td>
						    <input ctltype="attachment" name="m:asset_registration:attachment" isdirectupload="0" right="w" value='${assetRegistration.attachment}' validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}" /></td> 
						  </tr>
					 </tbody>
				</table>
        <input type="hidden" name="id" value="${assetRegistration.id}"/>
        <input type="hidden" id="saveData" name="saveData"/>
        <input type="hidden" name="executeType"  value="start" />
    </form>
   </div>
  </div>
    <script type="text/javascript">
     $('body').on('change','#selectAssetType',function(){
          var assetType = $('#masset_registrationasset_type_id').val();
          var len = assetType.length;
          if(len<4){
        	  $.ligerDialog.warn("请选择具体类别","提示信息");
        	  $("#masset_registrationasset_type_id").val("");
        	  $("#selectAssetType").val("");
            return false;
          }
          var itemValue = assetType.substring(0,2);
          var alias = "selectItemName";
          $.post("/platform/bpm/bpmFormQuery/doQuery.ht",{alias:alias,querydata:"{ITEMVALUE:\""+itemValue+"\"}",page:1,pagesize:10},function(data){
              var itemName = data.list[0].itemname;
              var startNum = itemName.indexOf("]")+1;
              var endNum = itemName.length;
              var assetTypeName = itemName.substring(startNum,endNum);
              $("#assetMainType").val(assetTypeName);
          });
     });
     
     $("#userId").on("change",function(){
    	 var userName = $(this).val();
         var alias="common_user_info_search";
         $("input[name='m:asset_registration:care_personID']").val("");
         if(!userName){
        	return false;
         }
         $.ajax({
             type : "POST", 
             url:"/platform/bpm/bpmFormQuery/doQuery.ht",
             data:{alias:alias,querydata:"{\"fullname\":\""+userName+"\"}",page:1,pagesize:10},
             dataType: "json",
             success:function(data){ 
                 if(data!=null && data.list!=null && data.list.length>0){
                     var rowData=data.list[0];
                     $("input[name='m:asset_registration:care_personID']").val(rowData.userid);
                     $("input[name='m:asset_registration:use_department']").val(rowData.orgname);
                 }
             }
         });
         
     });
     
     $("#bankName").on("change",function(){
    	    var bankName = $(this).val();
    	    $("#bankAccount").val("");
    	    $.post("/platform/bpm/bpmFormQuery/doQuery.ht",{alias:"bankSeach",querydata:"{F_bankName:"+bankName+"}"},function(data){
    	      if(data.list!=null&&data.list.length>0){
    	        $("#bankAccount").val(data.list[0].f_bankaccount)
    	      }
    	    });
    	  })
     
     $("input[name='m:asset_registration:asset_id']").on("change",function(){
    	 var asset_id = $(this).val();
    	 $.post("/platform/bpm/bpmFormQuery/doQuery.ht?",
                {alias:"selectAssetById",querydata:"{F_asset_id:\""+asset_id+"\"}",page:1,pagesize:10},
                function(data){
               	  var size=data.list.length;
               	  if(size!=0){
               		  alert("该编号已被占用，请重新输入！");
               		 $("input[name='m:asset_registration:asset_id']").val("");
               		  return false;
               	  }
                });
    	 
     });
     $(function(){
         //设置保管人id和使用部门Id
         var care_personId = "${assetRegistration.care_personID}";
         var use_departmentID = "${assetRegistration.use_departmentID}";
         if(care_personId!=null&&care_personId!=""){
             $("input[name='m:asset_registration:care_personID']").val(care_personId);
         };
         if(use_departmentID!=null&&use_departmentID!=""){
             $("input[name='m:asset_registration:use_departmentID']").val(use_departmentID);
         };
        /*  var _assetType = $('#masset_registrationasset_type_id').val();
         var start = _assetType.indexOf("[");
         var end = _assetType.indexOf("]");
         var assetType = _assetType.substring(start+1,end);
         $('#masset_registrationasset_type_id').val(assetType); */
       /*   var assetType = $("#selectAssetType").val();
         var typeName = "${assetRegistration.asset_code}";
         $(".l-text.l-text-combobox.l-text-focus").children().last().after(typeName); */
     }); 
    </script>
</body>

</html>
