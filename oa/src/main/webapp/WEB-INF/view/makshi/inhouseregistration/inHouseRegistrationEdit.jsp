<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
    <title>编辑 租房入住人员登记表</title>
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
            var frm=$('#inHouseRegistrationForm').form();
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
                            frm.sortList();
                            $('#inHouseRegistrationForm').submit();
                        });
                    }else{
                        frm.handleFieldName();
                        frm.sortList();
                        $('#inHouseRegistrationForm').submit();
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
                        window.location.href = "${ctx}/makshi/inhouseregistration/inHouseRegistration/list.ht";
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
	<form id="inHouseRegistrationForm" method="post" action="save.ht">
		 <table class="oa-table--default" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
           	<c:choose>
		    <c:when test="${not empty inHouseRegistrationItem.id}">
		         <caption>编辑租房入住人员登记表</caption>
		    </c:when>
		    <c:otherwise>
		         <caption>添加租房入住人员登记表</caption>
		    </c:otherwise>	
	   		</c:choose>	
  			<tr class="firstRow"> 
		   		<th>租房编号:</th> 
		    	<td>
		      		<input type="text"  class="oa-new-input" name="m:in_house_registration:house_id" value="${inHouseRegistration.house_id}" validate="{&quot;maxlength&quot;:1000,&quot;required&quot;:&quot;true&quot;}" > 
	      		</td>
		   		<th>房屋地址:</th> 
			    <td>
			   		<input type="text"  class="oa-new-input" name="m:in_house_registration:address" lablename="房屋地址"  value="${inHouseRegistration.address}" validate="{maxlength:50}" isflag="tableflag" readonly="readonly"/>
		   		</td> 
		   </tr> 
		   <tr> 
			   <td colspan="4" rowspan="1">
	                <div type="subtable" tablename="userInfo" tabledesc="员工入住信息" show="true" parser="rowmodeedit" id="userInfo" formtype="edit"> 
       					<div class="oa-pdb10">
       						<a id="addList" class="oa-button-label" href="javascript:;">添加</a>
       					</div>
			        					
			     		<table class="oa-table--default" border="0" cellpadding="2" cellspacing="0"> 
			      			<thead> 
								<tr> 
									<th>入住人员姓名</th> 
									<th>是否为负责人</th> 
									<th>入住家属</th> 
									<th>入住时间</th> 
									<th>离开时间</th> 
								</tr> 
							</thead> 
			      			<tbody>
								<c:forEach items="${userInfoList}" var="userInfo" varStatus="status">
									<tr type="subdata"> 
										<td> 
											<span name="editable-input" style="display:inline-block;" isflag="tableflag"><input type="text" name="s:userInfo:user_name" lablename="入住人员姓名" class="oa-new-input" value="${userInfo.user_name}" validate="{maxlength:50}" isflag="tableflag" /> </span> 
										</td>
										<td><label><input val="${userInfo.isresponsible}" type="radio" name="s:userInfo:isresponsible" value="0" lablename="是否负责人" validate="{}" />是</label> 
										    <label><input val="${userInfo.isresponsible}" type="radio" name="s:userInfo:isresponsible" value="1" lablename="是否负责人" validate="{}" />否</label>
										</td> 
										<td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:userInfo:family" lablename="是否" class="oa-new-input" value="${userInfo.family}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
										<td> <input data-class="oa-new-input" type="text" id="in_date" name="s:userInfo:in_date" value="<fmt:formatDate value='${userInfo.in_date}' pattern='yyyy-MM-dd'/>" class="Wdate"  validate="{date:true}"/></td> 
										<td> <input data-class="oa-new-input" type="text" id="left_date" name="s:userInfo:left_date" value="<fmt:formatDate value='${userInfo.left_date}' pattern='yyyy-MM-dd'/>" class="Wdate" validate="{date:true}" /></td>
									</tr>
								</c:forEach> 
								<tr type="append" style="display:none;"> 
									<td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:userInfo:user_name" lablename="入住人员姓名" class="oa-new-input" value="${userInfo.user_name}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
									<td> <span> <label><input val="${userInfo.isresponsible}" type="radio" name="s:userInfo:isresponsible" value="0" lablename="是否负责人" validate="{}" />是</label> <label><input val="${userInfo.isresponsible}" type="radio" name="s:userInfo:isresponsible" value="1" lablename="是否负责人" validate="{}" />否</label> </span> </td> 
									<td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:userInfo:family" lablename="是否" class="oa-new-input" value="${userInfo.family}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
									<td> <input  data-class="oa-new-input" type="text" id="in_date" name="s:userInfo:in_date" value="<fmt:formatDate value='${userInfo.in_date}' pattern='yyyy-MM-dd'/>" class="Wdate"  validate="{date:true}" /></td> 
									<td> <input  data-class="oa-new-input" type="text" id="left_date" name="s:userInfo:left_date" value="<fmt:formatDate value='${userInfo.left_date}' pattern='yyyy-MM-dd'/>" class="Wdate" validate="{date:true}" /></td>
								</tr>
	   						</tbody> 
	   					</table> 
	   					<input name="s:userInfo:id" type="hidden" pk="true" value="" />
	  				</div>
	  			</td> 
			</tr> 
 		</tbody> 
	</table>
    <input type="hidden" name="id" value="${inHouseRegistration.id}"/>
    <input type="hidden" id="saveData" name="saveData"/>
    <input type="hidden" name="executeType"  value="start" />
    </form>
  </div>
</body>
<script type="text/javascript">
 //通过输入租房编号获取地址、租房开始时间、结束时间
 var ststartDate;
 var endDate;
 $("input[name='m:in_house_registration:house_id']").change(
		 function(){
				var houseId = $("input[name='m:in_house_registration:house_id']").val();
				$.post("/makshi/inhouseregistration/inHouseRegistration/houseIdList.ht",{houseId:houseId},function(data){
					if(data=="no"){
						 alert("该编号以被登记，请重新输入编号");
						 $("input[name='m:in_house_registration:house_id']").val("");
						 return false;
					}
				var alias = "house_rent_info";
				$.ajax({
			        type : "POST", 
			        url:"/platform/bpm/bpmFormQuery/doQuery.ht",
			        data:{alias:alias,querydata:"{F_house_id:\""+houseId+"\"}",page:1,pagesize:10},
			        dataType: "json",
			        success:function(data){ 
			            if(data!=null && data.list!=null && data.list.length>0){
			          	  var rowData=data.list[0];
			          	  $("input[name='m:in_house_registration:address']").val(rowData.f_address);
			          	  startDate = rowData.f_start_date;
			          	  endDate = rowData.f_end_date;
			            }else{
			          	  alert("租房编号不正确，请重新输入!");
			          	 $("input[name='m:in_house_registration:house_id']").val("");
			          	 return false;
			            }
			        },
			     error:function(){
			  	   alert("查询错误！");
			     }
			    });
				});
  }); 
  
  $(function(){
	var isEmpty = $(".listTable").find("tr").length;
	if(isEmpty>3){ 
		var houseId = $("input[name='m:in_house_registration:house_id']").val();
		$.post("/platform/bpm/bpmFormQuery/doQuery.ht",{alias:"house_rent_info",querydata:"{F_house_id:\""+houseId+"\"}",page:1,pagesize:10},function(data){
			 var rowData=data.list[0];
         	  startDate = rowData.f_start_date;
         	  endDate = rowData.f_end_date;
         	  setDate();
		});
		};
	$(document).on("addRow",function(){
		setDate();
	});
});
  
$("#addList").on("click",function(){
	var houseId = $("input[name='m:in_house_registration:house_id']").val(); 
	if(!houseId){return false;}
	$(".link.add").click();
});

function setDate(){
/* 	var sD = startDate.substring(0,10);
	var eD = endDate.substring(0,10); */
	$(".Wdate").attr("onfocus","WdatePicker({minDate:\'"+startDate+"\',maxDate:\'"+endDate+"\'})");
} 


</script>
</html>
