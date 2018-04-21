<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 租房登记表</title>
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
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#rentHouseRegistrationForm').form();
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
							$('#rentHouseRegistrationForm').submit();
						});
					}else{
						frm.handleFieldName();
						$('#rentHouseRegistrationForm').submit();
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
						window.location.href = "${ctx}/makshi/renthouseregistration/rentHouseRegistration/list.ht";
					}
				});
			} else {
				$.ligerDialog.error(obj.getMessage(),"提示信息");
			}
		}
	</script>
</head>
<body class="oa-mw-page">
    <div class="oa-pd20">
        <a class="oa-button oa-button--primary oa-button--blue save" id="dataFormSave">保存</a>
        <a class="oa-button oa-button--primary oa-button--blue" href="list.ht">返回</a>
    </div>
	<div class="oa-mg20">
		<form id="rentHouseRegistrationForm" method="post" action="save.ht">
			 <table class="oa-table--default" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
               	<c:choose>
			    <c:when test="${not empty rentHouseRegistrationItem.id}">
			         <caption>编辑租房登记表</caption>
			    </c:when>
			    <c:otherwise>
			         <caption>添加租房登记表</caption>
			    </c:otherwise>	
		   		</c:choose>				
			  <tr> 
			   <th>租房编号</th> 
			   <td>
			   		<c:if test="${rentHouseRegistration.id==null}">系统自动生成</c:if>
			   		<c:if test="${rentHouseRegistration.id!=null}">
			   			<input type="hidden" name="m:rent_house_registration:house_id" lablename="租房编号" value="${rentHouseRegistration.house_id}" oa-new-input isflag="tableflag" /> 
			   			${rentHouseRegistration.house_id}
					</c:if>
			   </td> 
			   <th>房屋面积(平米)</th> 
			   <td>
			    <input type="text" name="m:rent_house_registration:size" lablename="房屋面积" class="oa-new-input" value="${rentHouseRegistration.size}" oa-new-input isflag="tableflag" /></td> 
			  </tr> 
			  <tr> 
			   <th>部门名称</th> 
			   <td>
			    <input name="m:rent_house_registration:department" type="text" ctltype="selector" class="oa-new-input oa-middle org" lablename="部门名称" value="${rentHouseRegistration.department}" validate="{empty:false}" /> </td> 
			   <th>房租金额(元/月)</th> 
			   <td> <input type="text" name="m:rent_house_registration:money" lablename="房租金额" class="oa-new-input" value="${rentHouseRegistration.money}" oa-new-input isflag="tableflag" /></td> 
			  </tr> 
			   <tr> 
			   <th>经办人</th> 
			   <td>
			   		<input type="text" name="m:rent_house_registration:handle_person" lablename="经办人" class="oa-new-input" value="${rentHouseRegistration.handle_person}" oa-new-input isflag="tableflag" /></td>
			   <th>房屋押金</th> 
			   <td> 
			   		<input type="text" name="m:rent_house_registration:deposit" lablename="房屋押金" class="oa-new-input" value="${rentHouseRegistration.deposit}" oa-new-input isflag="tableflag" /></td> 
			  </tr> 
			  <tr> 
			   <th>入住员工姓名</th> 
			   <td>
			   		<input type="text" name="m:rent_house_registration:landlord" lablename="房东姓名" class="oa-new-input" value="${rentHouseRegistration.landlord}" oa-new-input isflag="tableflag" /></td> 
			   <th>房屋结构</th> 
			   <td>
			   		<select class="oa-new-select" value="${rentHouseRegistration.house_type}" name="m:rent_house_registration:house_type" >
			   			 <option value='' >请选择</option>
					   	 <option value='1' <c:if test="${rentHouseRegistration.house_type=='1'}">selected</c:if> >1房</option>
					   	 <option value='2' <c:if test="${rentHouseRegistration.house_type=='2'}">selected</c:if> >2房</option>
					   	 <option value='3' <c:if test="${rentHouseRegistration.house_type=='3'}">selected</c:if> >3房</option>
					   	 <option value='4' <c:if test="${rentHouseRegistration.house_type=='4'}">selected</c:if> >4房</option>
					   	 <option value='5' <c:if test="${rentHouseRegistration.house_type=='5'}">selected</c:if> >5房</option>
					   	 <option value='6' <c:if test="${rentHouseRegistration.house_type=='6'}">selected</c:if> >6房</option>
					   	 <option value='7' <c:if test="${rentHouseRegistration.house_type=='7'}">selected</c:if> >7房</option>
					   	 <option value='8' <c:if test="${rentHouseRegistration.house_type=='8'}">selected</c:if> >8房</option>
					   	 <option value='9' <c:if test="${rentHouseRegistration.house_type=='9'}">selected</c:if> >9房</option>
			   	    </select>
	   		  </td>
			  </tr> 
			  <tr> 
			   <th>承租人</th> 
			   <td>
			   		 <input type="text" name="m:rent_house_registration:rent_person" lablename="承租人" class="oa-new-input" value="${rentHouseRegistration.rent_person}" oa-new-input isflag="tableflag" /></td>
			   <th>租房性质</th> 
			   <td>
				   	 <select class="oa-new-select" value="${rentHouseRegistration.rent_type}" name="m:rent_house_registration:rent_type" >
					   	 <option value=1 <c:if test="${rentHouseRegistration.rent_type==1}">selected</c:if> >办公</option>
					   	 <option value=2 <c:if test="${rentHouseRegistration.rent_type==2}">selected</c:if> >宿舍</option>
					   	 <option value=3 <c:if test="${rentHouseRegistration.rent_type==3}">selected</c:if> >办公兼宿舍</option>
				   	 </select>
			   </td> 
			  </tr> 
			  <tr> 
			   <th>房屋地址</th> 
			   <td> 
			   		<input type="text" name="m:rent_house_registration:address" lablename="房屋地址" class="oa-new-input" value="${rentHouseRegistration.address}" oa-new-input isflag="tableflag" /></td> 
			   <th>住宿人数</th> 
			   <td>
			  		 <input type="text" name="m:rent_house_registration:number_people" lablename="住宿人数" class="oa-new-input" value="${rentHouseRegistration.number_people}" oa-new-input isflag="tableflag" /></td>
			  </tr> 
			  <tr> 
			   <th>租房开始日期</th> 
			   <td>
			   	<input  id="startDate" data-class="oa-new-input" parser="datetable" name="m:rent_house_registration:start_date" lablename="租房开始日期" class="oa-new-input Wdate" value="<fmt:formatDate value="${rentHouseRegistration.start_date}" pattern='yyyy-MM-dd'/>" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\');}'})" validate="{empty:false}" type="text"/>
			   </td> 
			   <th>宿舍负责人</th> 
				<td><input type="text" name="m:rent_house_registration:responsible_person" lablename="宿舍负责人" class="oa-new-input" value="${rentHouseRegistration.responsible_person}" oa-new-input isflag="tableflag" /></td>
			  </tr> 
			  <tr> 
			   <th>租房停止日期</th> 
			   <td>
			   	<input id="endDate" data-class="oa-new-input" parser="datetable" name="m:rent_house_registration:end_date" lablename="租房停止日期" class="oa-new-input Wdate" value="<fmt:formatDate value="${rentHouseRegistration.end_date}"  pattern="yyyy-MM-dd" />" onclick="WdatePicker({minDate:'#F{$dp.$D(\'startDate\');}'})" validate="{empty:false}" type="text"/>
			   </td> 
			   <th>附件</th> 
			   <td>
			   	<input ctltype="attachment" name="m:rent_house_registration:attachment" isdirectupload="0" right="w" value='${rentHouseRegistration.attachment}' validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}" /></td>
			  </tr> 
			  <tr> 
			   <th>备注</th> 
			   <td rowspan="1" colspan="3"> <textarea name="m:rent_house_registration:remarks" lablename="备注" class="oa-new-textarea" rows="5" cols="30" validate="{maxlength:1000}" isflag="tableflag">${rentHouseRegistration.remarks}</textarea></td> 
			  </tr> 
			 </tbody> 
			</table>
			<input type="hidden" name="id" value="${rentHouseRegistration.id}"/>
			<input type="hidden" id="saveData" name="saveData"/>
			<input type="hidden" name="executeType"  value="start" />
		</form>
	</div>
</body>
</html>
