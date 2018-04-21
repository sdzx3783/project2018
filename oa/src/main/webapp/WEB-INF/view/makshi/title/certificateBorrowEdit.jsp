<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 个人证书借阅申请</title>
	<%@include file="/codegen/include/customForm.jsp" %>
	<%@include file="/commons/include/ueditor.jsp" %>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
	<script type="text/javascript" src="${ctx}/js/makshi/qualification.js"></script>
	<script type="text/javascript" src="${ctx}/js/makshi/layer/layer.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/AttachMent.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/form/TablePermission.js"></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#certificateBorrowForm').form();
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
							$('#certificateBorrowForm').submit();
						});
					}else{
						frm.handleFieldName();
						$('#certificateBorrowForm').submit();
						
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
				$.ligerDialog.confirm(obj.getMessage()+"成功,是否继续"+obj.getMessage(),"提示信息", function(rtn) {
					if(rtn){
						window.location.href = window.location.href;
					}else{
						window.location.href = window.close();
					}
				});
			} else {
				$.ligerDialog.error(obj.getMessage(),"提示信息");
			}
		}
		
		$(function(){
	      //  页面加载将用户id赋值js
	        $(function(){
	       	 var borrowerID = "${certificateBorrow.applicantID}";
	       	 if(borrowerID!=null&&borrowerID!=""){
	       		 $("input[name='m:certificate_borrow:applicantID']").val(borrowerID);
	       	 };
	       	 var holderID = "${certificateBorrow.fullnameID}";
	       	 if(holderID!=null&&holderID!=""){
	       		 $("input[name='m:certificate_borrow:fullnameID']").val(holderID);
	       	 };
	        });
	        
		});
	</script>
	<style>
		input[data-class='oa-new-input'],
		input.oa-new-input,
		select.oa-new-select{
			width: 250px;
		}
	</style>
</head>
<body class="oa-mw-page">
    <div class="oa-pd20">
        <a class="oa-button oa-button--primary oa-button--blue save" id="dataFormSave">保存</a>
      <!--   <a class="oa-button oa-button--primary oa-button--blue" href="list.ht">返回</a> -->
    </div>
    <div class="oa-mg20">
    	<form id="certificateBorrowForm" method="post" action="save.ht">
    		<table class="oa-table--default" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
                <c:choose>    
				    <c:when test="${not empty certificateBorrowItem.id}">
				        <span class="tbar-label"><caption>编辑个人证书(印章)借阅申请</caption>
				    </c:when>
				    <c:otherwise>
				        <span class="tbar-label"><caption>添加个人(印章)证书借阅申请</caption>
				    </c:otherwise>	
		   		</c:choose>
			    <tr>
				   <th>申请人</th>
				   <td>
				   	   <input parser="selectortable" name="m:certificate_borrow:applicant" type="text" ctltype="selector" class="link oa-new-input oa-middle user" lablename="申请人" value="${certificateBorrow.applicant}" validate="{empty:false}" readonly="readonly" scope="{&#39;value&#39;:&#39;all&#39;,&#39;type&#39;:&#39;system&#39;}"/>
			       </td>
				   <th>申请时间</th>
				   <td>
				       <input type="text" name="m:certificate_borrow:ap_date" lablename="申请时间" value="<fmt:formatDate value="${certificateBorrow.ap_date}" pattern='yyyy-MM-dd'/>" data-class="oa-new-input" class="Wdate" validate="{date:true}" isflag="tableflag" />
				   </td>
			    </tr>
			    <tr>
				   <th>持证人<span class="red">*</span></th>
				   <td> 
				   	   <input parser="selectortable" name="m:certificate_borrow:fullname" type="text" ctltype="selector" class="link oa-new-input oa-middle user" lablename="持证人"  value="${certificateBorrow.fullname}" validate="{empty:false,required:true}"  scope="{&#39;value&#39;:&#39;all&#39;,&#39;type&#39;:&#39;system&#39;}"/>
			       </td>
				   <th>借阅证书名称<span class="red">*</span></th>
				   <td id="steal_name">
				   	   <input type="text" name="m:certificate_borrow:steal_name" lablename="持证人" class="oa-new-input" value="${certificateBorrow.steal_name}" validate="{empty:false,required:true}" isflag="tableflag" />
				   </td>
				  </tr>
				  <tr>
				   <th>借阅证书类型</th>
				   <td>
				       <select name="m:certificate_borrow:type" class="oa-new-select" lablename="借阅证书类型" controltype="select" validate="{empty:false}" val="${certificateBorrow.type}"> 
				       	   <option value="">请选择</option> 
				           <option value="1">资格证</option> 
				           <option value="2">注册证</option> 
				           <option value="3">印章</option> 
				           <option value="4">其他</option> 
			           </select>
		           </td>
				   <th>借阅证书(印章)编号<span class="red">*</span></th>
				   <td>
				   	   <input id="borrowId" type="text" name="m:certificate_borrow:borrow_data_num" lablename="借阅证书(印章)编号" class="oa-new-input" value="${certificateBorrow.borrow_data_num}" validate="{empty:false,required:true}" isflag="tableflag" />
				   </td>
				  </tr>
				  <tr id="selectedSeal">
				    <th>执业印章有效期</th>
				    <td><input id="effictiveDate" data-class="oa-new-input" type="text" value="" name="m:certificate_borrow:effictiveDate" validate="{'date':true}" class="Wdate" datefmt="yyyy-MM-dd"></td>
				    <td></td>
				    <td></td>
			      </tr>
				  <tr>
				   <th>预计归还日期</th>
				   <td>
				   	   <input type="text" name="m:certificate_borrow:return_date_expect"  value="<fmt:formatDate value="${certificateBorrow.return_date_expect}" pattern='yyyy-MM-dd'/>" data-class="oa-new-input" class="Wdate" validate="{date:true}" isflag="tableflag" />
				   </td>
				   <th>归还日期</th>
				   <td>
				       <input type="text" name="m:certificate_borrow:return_date"  value="<fmt:formatDate value="${certificateBorrow.return_date}" pattern='yyyy-MM-dd'/>" data-class="oa-new-input" class="Wdate" validate="{date:true}" isflag="tableflag" />
				   </td>
				  </tr>
				  <tr>
				   <th>备注</th>
				   <td rowspan="1" colspan="3">
				   	   <textarea name="m:certificate_borrow:remark" lablename="备注" class="oa-new-textarea" rows="5" cols="40" validate="{maxlength:1000}" isflag="tableflag">${certificateBorrow.remark}</textarea></td>
				  </tr>
				  <tr>
				  	<th>附件</th> 
					   <td rowspan="1" colspan="3">
					    <input  ctltype="attachment" name="m:certificate_borrow:attachment" isdirectupload="0" right="w" value='${certificateBorrow.attachment}' validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}" />
					   </td>
				  </tr>
				 </tbody>
			</table>
		</div>
		</div>
		<input type="hidden" name="id" value="${certificateBorrow.id}"/>
		<input type="hidden" name="m:certificate_borrow:finish" value="${certificateBorrow.finish}"/>
		<input type="hidden" id="saveData" name="saveData"/>
		<input type="hidden" name="executeType"  value="start" />
	</form>
</body>
<script type="text/javascript">
	var selectedType;
	$(function(){
		checkCertificateType();
	});
		function checkCertificateType(){
			$("select[name='m:certificate_borrow:type'] option").each(function(){
				var type = $(this).val();
				var select = $(this).attr("selected");
				if(!select){
					return;
				}
				selectedType = type;
				if(type==3 ){
					$("#selectedSeal").show();
				}else{
					$("#selectedSeal").hide();
					$("#effictiveDate").val('');
				}
	                             
			});
		}
		
		$("select[name='m:certificate_borrow:type']").on("change",function(){
			checkCertificateType();
			getCertificateInfo();
		});
		function getCertificateInfo(){
				//获取持证人
				var userName = $("input[name='m:certificate_borrow:fullname']").val();
				var userId = $("input[name='m:certificate_borrow:fullnameID']").val(); 
			    var pointName = "m:certificate_borrow:steal_name";
				//因为持证人为可编辑，若修改持证人后hoderId并未改变，需要将hoderId进行修改
				$.post("/platform/system/sysUser/checkUserNameAndUserId.ht",{userName:userName,userId:userId},function(data){
						//id和姓名不匹配
						if(data.Ok==0){
						    $.ligerDialog.warn(data.msg,"提示信息");
						}
						//匹配
						if(data.Ok==1){
							getQualificationInfo(userId,userName,selectedType,pointName);						
						}
						//无userId人员
						if(data.Ok==2){
							userId = 0;
							getQualificationInfo(userId,userName,selectedType,pointName);	
						} 
				});
		}
</script>

</html>
