<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
    <title>编辑 合同基本信息</title>
    <%@include file="/codegen/include/customForm.jsp" %>
    <%@include file="/commons/include/ueditor.jsp" %>
    <script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/form/AttachMent.js"></script>
     <script type="text/javascript" src="${ctx}/js/hotent/platform/form/TablePermission.js"></script>
      <style>
      	.oa-table--default th.notopborder {
      		border-top: solid 1px #fff;
    		padding-top: 10px;
      	}
      	.oa-table--default th.sectitle {
		    font-size: 15px;
		    border-left: solid 1px #fff;
		    border-right: solid 1px #fff;
		    padding-top: 25px;
		  }
      </style>
      <script>
    $(function() {
        var options={};
        if(showResponse){
            options.success=showResponse;
        }
        var frm=$('#contractinfoForm').form();
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
                        if(testContractNum()){
                        $('#contractinfoForm').submit();
                        }else{
                       	   $.ligerDialog.warn('请按照JL2010-1或JL2010-1-1或JL2010-1-1(内容)格式填写合同编号',"提示信息");
                        return;
                        }
                    });
                }else{
                    frm.handleFieldName();
                    frm.sortList();
                    if(testContractNum()){
                        $('#contractinfoForm').submit();
                    }else{
                    $.ligerDialog.warn('请按照JL2010-1或JL2010-1-1或JL2010-1-1(内容)格式填写合同编号',"提示信息");
                    return;
                    }
                }
            }else{
                $.ligerDialog.warn("表单验证不成功,请检查表单是否正确填写","提示信息");
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
                    window.location.href = window.close();
                }
            });
        } else {
            $.ligerDialog.error(obj.getMessage(),"提示信息");
        }
    }
		
	 $(function(){
	       $("input:radio[name='m:contract_seal_application:isrecovery'][value='0']").attr("checked",true); 
	       $("input:radio[name='m:contract_seal_application:isrecord'][value='0']").attr("checked",true); 
		   $("input:radio[name='m:contract_seal_application:isrecovery']").change( function(){
		       var val=$(this).val();
               if(val==1){
                   var temp="<span class=\"red\">*</span>";
                   $("textarea[name='m:contract_seal_application:fj_second']").attr("validate","{'required':true,'maxlength':2000}");
                   $("textarea[name='m:contract_seal_application:fj_second']").parent().parent().prev().append(temp);
                 //$("textarea[name='m:contract_seal_application:fj_second']").parent().parent().show();
               }else{
                   $("textarea[name='m:contract_seal_application:fj_second']").val("");
                   $("textarea[name='m:contract_seal_application:fj_second']").attr("validate","{'maxlength':2000}");
                   $("textarea[name='m:contract_seal_application:fj_second']").parent().parent().prev().find(".red").remove();
                 //$("textarea[name='m:contract_seal_application:fj_second']").parent().parent().hide();
               }
       	   });  
	       $("input:radio[name='m:contract_seal_application:isrecord']").change(function(){
               var val=$(this).val();
               var temp="<span class=\"red\">*</span>";
               if(val==1){
                   $("textarea[name='m:contract_seal_application:fj_third']").attr("validate","{'required':true,'maxlength':2000}");
                   $("textarea[name='m:contract_seal_application:fj_third']").parent().parent().prev().append(temp);
               }else{
                   $("textarea[name='m:contract_seal_application:fj_third']").val("");
                   $("textarea[name='m:contract_seal_application:fj_third']").attr("validate","{'maxlength':2000}");
                   $("textarea[name='m:contract_seal_application:fj_third']").parent().parent().prev().find(".red").remove();
               }
           });
       });
	 
    var contractArray = new Array(); 
    $(function() {
    	$("#contractTypeText").on("change",function(){init($(this).val());});
	    function init(contractType){
	   	    var year = $("#selectYear").val();
		    var selectId = 2;
		    $("#contractnum").val('');
		    $("#contractTypeText"+(selectId+1)).html('');
		    $("#contractTypeText"+(selectId+1)).attr("style","display:none");
			getTypeDetails(contractType,year,selectId);
	    }
		$("#contractTypeText2").on("change",function(){
			$("#contractnum").val('');
	        var secondContractType = $(this).val();
	        var contractNum = $("#contractTypeText2 option:selected").attr('data-num');
	        var  haveChildrenSelect = 0;
	        //判断是否存在于上级select
	        $("#contractTypeText option").each(function(){
	        	if($(this).val()==secondContractType){
	        		haveChildrenSelect = 1;
					var selectId = 3;
					var year = $("#selectYear").val();
					setSelect(contractArray,selectId,secondContractType,year);
	        	}
	        });
	        if(haveChildrenSelect==0){
	        	 $("#contractnum").val(contractNum);
	        }
		});
		
		$("#contractTypeText3").on("change",function(){
	        var contractNum = $("#contractTypeText3 option:selected").attr('data-num');
       	    $("#contractnum").val(contractNum);
		});
		function getTypeDetails(contractType,year,selectId,isChange) {
        	$.ajax({  
                type: "get",  
                url: "/makshi/contract/contractinfo/queryContractType.ht?t=" + Math.random(),  
                dataType: "json",  
                data: {"year": year},
                contentType: "application/json",  
                success: function (data) { 
                	setSelect(data,selectId,contractType,year);
                } 
            });
        }  	  
		function setSelect(data,selectId,contractType,year){
			var selected = false;
			for(var i = 0 ; i < data.length ; i++ ){
        		if(data[i].type==contractType){
        			var selected = true;
        			//拥有子目录
       				var html = '<option>请选择</option>';
        			var num = data[i].contract_num + year +"-"+ data[i].flowNo;
       				/* if(data[i].contract_num=="CG"){
   	                    $("input[name='m:contractinfo:first_party']").val("深圳市深水水务咨询有限公司");
   	                    $("input[name='m:contractinfo:second_party']").val("");
                    }else{
  	                    $("input[name='m:contractinfo:second_party']").val("深圳市深水水务咨询有限公司");
   	                    $("input[name='m:contractinfo:first_party']").val("");
        			} */
        			if(data[i].contractNumSecondList.length>0){
						var secondList = data[i].contractNumSecondList				                				
        				for(var j = 0 ; j < secondList.length ; j++ ){
        					html = html + '<option data-num = '+num+'>'+secondList[j].type+'</option>';
        				}
        				$("#contractTypeText"+selectId).html(html);
        				$("#contractTypeText"+selectId).attr("style","display:inline");
        			}else{
        				$("#contractTypeText"+selectId).html('');
        				$("#contractTypeText"+selectId).attr("style","display:none");
        				$("#contractnum").val(num);
        			}
        		}
        		contractArray[i]=data[i];
        	}
			if(!selected){
				$("#contractTypeText"+selectId).html('');
				$("#contractTypeText"+selectId).attr("style","display:none");
			}
		}
    });
    //合同编号校验
    function testContractNum(){
        var contractNum = $("#contractnum").val();
        //校验格式
        var reg = /^([a-zA-Z]{2,4}[1,2][0-9]{3}[-]\d+(|([\（][\u4e00-\u9fa5_a-zA-Z0-9]+[\）])|([-]\d+|[-]\d)+(|([\（][\u4e00-\u9fa5_a-zA-Z0-9]+[\）]))))$/;
        if(!reg.test(contractNum)){
            return false;
        }
       return true;
    } 
</script>
</head>
<body class="oa-mw-page" style="min-width: 1400px;">
	<div class="oa-mg20">
        <a class="oa-button oa-button--primary oa-button--blue save" id="dataFormSave">保存</a>
        <!-- <a class="oa-button oa-button--primary oa-button--blue" href="list.ht">返回</a> -->
	</div>
	<div class="oa-mg20">
		<form id="contractinfoForm" method="post" action="save.ht">
			<table class="oa-table--default" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
	        	<c:if test="${empty contractinfo.id}"><caption>添加合同信息</caption></c:if>
	        	<c:if test="${not empty contractinfo.id}"><caption>编辑合同信息</caption></c:if>
  				<tr>
			    	<th class="sectitle notopborder" colspan="6">基本资料</th>
			    </tr>
  				<tr>  
	   				<th>合同类型<span class="red">*</span></th> 
	   				<td>
				     	<select id="contractTypeText" name='m:contractinfo:contracttype' class='oa-new-select' validate="{required:true}">
				     		 <option value="">请选择</option>
		               		 <c:forEach items="${contractTypes }" var="e">
		               		 	<option value="${e }" <c:if test="${ contractinfo.contracttype==e}">selected</c:if>>${e }</option>
		               		 </c:forEach>
						</select> 
						<select id="contractTypeText2"  <c:if test="${ empty contractinfo.type}"> style='display: none;'</c:if> type="hidden" name="m:contractinfo:type" value="${contractinfo.type}" class='oa-new-select'>
						 	 <option value="">请选择</option>
		               		 <c:forEach items="${secondList }" var="e">
		               		 	<option value="${e.type }" data-num = '${contractinfo.contract_num }'<c:if test="${ contractinfo.type==e.type}">selected</c:if>>${e.type }</option>
		               		 </c:forEach>
						</select>
						<select id="contractTypeText3" type="hidden"  <c:if test="${ empty contractinfo.thirdcontracttype}"> style='display: none;'</c:if> value="${contractinfo.thirdcontracttype}" name="m:contractinfo:thirdcontracttype" class='oa-new-select'>
							<option value="">请选择</option>
		               		 <c:forEach items="${thirdList }" var="e">
		               		 	<option value="${e.type }" data-num = '${contractinfo.contract_num }'<c:if test="${ contractinfo.thirdcontracttype==e.type}">selected</c:if>>${e.type }</option>
		               		 </c:forEach>
						</select>
		   			</td> 
		   			<th>合同年份</th> 
			   		<td>
			   			<select name="docname" id="selectYear" class="oa-new-select" value="${contractinfo.yearSort}">
							<c:forEach begin="2016" end="${cutYear }" var="e" >
				               		<option <c:if test="${fn:contains(years,(cutYear-e+2016))}">selected</c:if> >${cutYear-e+2016 }</option>
		               	    </c:forEach>
						</select>
			   		</td>
					<th >合同状态<span class="red">*</span></th> 
				    <td>
				      <select name="m:contractinfo:contract_status" class="oa-new-select" validate="{required:true}" >
				          <option value="" <c:if test="${contractinfo.contract_status==''}">selected</c:if> >请选择</option>
				          <option value="1" <c:if test="${contractinfo.contract_status==1}">selected</c:if> >作废</option>
				          <option value="0" <c:if test="${contractinfo.contract_status==0}">selected</c:if> >正式合同</option>
				      </select> 
			    	</td>
			    </tr> 
	        	<tr>
		        	<th>合同编号<span class="red">*</span></th> 
		        	<td><input id="contractnum" type="text" name="m:contractinfo:contract_num" lablename="合同编号" class="oa-new-input" value="${contractinfo.contract_num}" title="${contractinfo.contract_num}"  validate="{required:true}" isflag="tableflag" /></td> 
		      		<th>合同名称<span class="red">*</span></th> 
		       		<td><input type="text" name="m:contractinfo:contract_name" lablename="合同名称" class="oa-new-input" value="${contractinfo.contract_name}" title="${contractinfo.contract_name}"  isflag="tableflag" validate="{required:true}" /></td> 
		       		<th>项目状态<span class="red">*</span></th> 
		       		<td>
		       		  <%-- <input type="text" name="m:contractinfo:project_status" lablename="项目状态" class="oa-new-input" value="${contractinfo.project_status}"  isflag="tableflag" /></td> --%> 
			         <select name="m:contractinfo:project_status" class="oa-new-select" lablename="项目状态"  validate="{required:true}" isflag="tableflag" >
			         	  <option value="" <c:if test="${contractinfo.project_status==''}">selected</c:if>>请选择</option>
			              <option value="在建" <c:if test="${contractinfo.project_status=='在建'}">selected</c:if>>在建</option>
						  <option value="完工" <c:if test="${contractinfo.project_status=='完工'}">selected</c:if>>完工</option>
			              <option value="停工" <c:if test="${contractinfo.project_status=='停工'}">selected</c:if>>停工</option>
			         </select>
				</tr> 
		  		<tr>  
				   <th>甲方<span class="red">*</span></th> 
				   <td><input type="text" name="m:contractinfo:first_party" lablename="甲方" class="oa-new-input" value="${contractinfo.first_party}" title="${contractinfo.first_party}"  isflag="tableflag" validate="{required:true}" /></td> 
				   <th>乙方<span class="red">*</span></th> 
				   <td><input type="text" name="m:contractinfo:second_party" lablename="乙方" class="oa-new-input" value="${contractinfo.second_party}" title="${contractinfo.second_party}"  isflag="tableflag" validate="{required:true}" /></td>
				   <th>签约时间<span class="red">*</span></th> 
				   <td>
				     <input type="text" name="m:contractinfo:singing_time" value="<fmt:formatDate value='${contractinfo.singing_time}' pattern='yyyy-MM-dd'/>" class="inputText date" data-class="oa-new-input"  validate="{required:true}"/>
				   </td>
		  		</tr> 
		  		<tr> 
		   			<th>委托方式<span class="red">*</span></td> 
					<td> 
				     <select name="m:contractinfo:bidding_method" class="oa-new-select"  style="width:245px !important" validate="{required:true}">
				          <option value="" <c:if test="${empty contractinfo.bidding_method}">selected</c:if> >请选择</option>
				          <option value="0" <c:if test="${contractinfo.bidding_method==0}">selected</c:if> >公开招标</option>
				          <option value="1" <c:if test="${contractinfo.bidding_method==1}">selected</c:if> >邀请招标</option>
				          <option value="2" <c:if test="${contractinfo.bidding_method==2}">selected</c:if> >直接委托</option>
				          <option value="3" <c:if test="${contractinfo.bidding_method==3}">selected</c:if> >抽签</option>
				     </select>
				    </td>
				   <th>招标平台</th> 
				   <td> 
				       <select name="m:contractinfo:bidding_platform" class="oa-new-select" >
				              <option value="" <c:if test="${empty contractinfo.bidding_platform}">selected</c:if> >请选择</option>
				              <option value="0" <c:if test="${contractinfo.bidding_platform==0}">selected</c:if> >深圳市建设工程交易服务中心</option>
				              <option value="1" <c:if test="${contractinfo.bidding_platform==1}">selected</c:if> >政府采购中心</option>
				              <option value="2" <c:if test="${contractinfo.bidding_platform==2}">selected</c:if> >无</option>
				              <option value="3" <c:if test="${contractinfo.bidding_platform==3}">selected</c:if> >其他</option>
				       </select>
				   </td>
				   <th>开工时间<span class="red">*</span></td> 
			       <td><input type="text" name="m:contractinfo:start_time" value="<fmt:formatDate value='${contractinfo.start_time}' pattern='yyyy-MM-dd'/>" class="inputText date" data-class="oa-new-input"  validate="{required:true}"/>
			       </td>
		  		</tr> 
		  		<tr>  
			      <th>总投资(万元)<span class="red">*</span></th> 
			      <td><input type="text" name="m:contractinfo:total_investment" lablename="投资总额（万元）" class="oa-new-input"  value="${contractinfo.total_investment}"  isflag="tableflag" validate="{required:true}"/></td>
			      <th>合同金额(万元)<span class="red">*</span></th> 
			      <td><input type="text" name="m:contractinfo:contract_money" lablename="合同金额（万元）" class="oa-new-input"  value="${contractinfo.contract_money}"  isflag="tableflag" validate="{required:true}"/></td> 
			      <th>完工时间<span class="red">*</span></th> 
				  <td><input type="text" name="m:contractinfo:end_time" value="<fmt:formatDate value='${contractinfo.end_time}' pattern='yyyy-MM-dd'/>" class="inputText date" data-class="oa-new-input" validate="{required:true}" /></td>
		  		</tr>    
				<tr>
					<th>工程标底价(万元)</th> 
					<td><input type="text" name="m:contractinfo:project_bid_floorprice" lablename="工程标底价" class="oa-new-input" value="${contractinfo.project_bid_floorprice}" validate="{number:true}" isflag="tableflag" /></td> 
					<th>工程中标价(万元)</th> 
					<td><input type="text" name="m:contractinfo:project_bid_price" lablename="工程中标价" class="oa-new-input" value="${contractinfo.project_bid_price}" validate="{number:true}" isflag="tableflag" /></td> 
					<th>费率(%)</th> 
					<td><input type="text" name="m:contractinfo:rate" lablename="费率" class="oa-new-input" value="${contractinfo.rate}" validate="{maxlength:50}" isflag="tableflag" /></td>
				</tr>
				<tr>  
					<th>结算金额（万元）</th> 
					<td><input type="text" name="m:contractinfo:settlement_money" lablename="结算金额（万元）" class="oa-new-input" value="${contractinfo.settlement_money}" validate="{number:true}" isflag="tableflag" /></td> 
					<th>系统内外</th> 
					<td>
						<label><input <c:if test="${contractinfo.inout==0 || empty contractinfo.inout}">checked</c:if> val="${contractinfo.inout}" type="radio" name="m:contractinfo:inout" value="0" lablename="系统内外" validate="{}" />内</label> 
						<label><input <c:if test="${contractinfo.inout==1}">checked</c:if> val="${contractinfo.inout}" type="radio" name="m:contractinfo:inout" value="1" lablename="系统内外" validate="{}" />外</label> </span> 
					</td> 
					<th>项目负责人<span class="red">*</span></th> 
					<td><input type="text" name="m:contractinfo:project_leader" lablename="项目负责人"  parser="selectortable" ctltype="selector"  class="oa-new-input oa-middle users" value="${contractinfo.project_leader}"  validate="{required:true}" isflag="tableflag" /></td>  
				</tr> 
				<tr>
					<th>项目总监/项目经理<span class="red">*</span></td> 
					<td><input type="text" parser="selectortable" name="m:contractinfo:project_director" lablename="项目总监" ctltype="selector"  class="oa-new-input oa-middle users" value="${contractinfo.project_director}"  isflag="tableflag" validate="{required:true}"/></td> 
					<th></th>
					<td></td>
					<th>合同审核人<span class="red">*</span></th> 
					<td><input type="text" name="m:contractinfo:contract_reviewer" lablename="合同审核人" parser="selectortable" ctltype="selector"  class="oa-new-input oa-middle users" value="${contractinfo.contract_reviewer}"  validate="{required:true}" isflag="tableflag" /></td>
				</tr>
				<tr> 
					<th>项目归属部门<span class="red">*</span></th> 
					<td>
						<select name="m:contractinfo:contract_departmentID" validate="{required:true}" id="contract_departmentID" onchange="changeOrg();" class="oa-new-select"  style="width:245px !important" validate="{required:true}">
							<option value="">请选择</option>
							<c:forEach items="${orgs }" var="e">
		               		 	<option value="${e.orgId }" data-name="${e.orgName }" <c:if test="${contractinfo.contract_departmentID==e.orgId || (empty contractinfo.contract_departmentID && contractinfo.contract_department==e.orgName) }">selected</c:if>>${e.orgName }</option>
		               		 </c:forEach>
	               		</select>
						<input type="hidden" name="m:contractinfo:contract_department" id="contract_department" value="${contractinfo.contract_department }">
					</td>
					<th>合同经手人<span class="red">*</span></th> 
					<td><input parser="selectortable" name="m:contractinfo:contract_handler"  type="text" ctltype="selector" class="oa-new-input oa-middle users" lablename="合同经手人" class="oa-new-input" value="${contractinfo.contract_handler}"  validate="{required:true}" isflag="tableflag" /></td> 
					<th>合同批签人</th> 
					<td><input type="text" name="m:contractinfo:contract_authorized_person" lablename="合同批签人" parser="selectortable" ctltype="selector"  class="oa-new-input oa-middle users" value="${contractinfo.contract_authorized_person}"  isflag="tableflag" /></td>
				</tr> 
				<tr>  
					<th>项目内容<span class="red">*</span></th> 
					<td rowspan="1" colspan="5"><textarea name="m:contractinfo:project_content" lablename="项目内容" class="oa-new-textarea" rows="5" cols="40"  value="${contractinfo.project_content}" isflag="tableflag" validate="{required:true}">${contractinfo.project_content}</textarea> </span> </td>					
				</tr> 
				<tr>   
					<th>项目规模<span class="red">*</span></th> 
					<td rowspan="1" colspan="5"><textarea name="m:contractinfo:project_scale" lablename="项目规模" class="oa-new-textarea" rows="5" cols="40"    value="${contractinfo.project_scale}" isflag="tableflag" validate="{required:true}">${contractinfo.project_scale}</textarea> </span> </td>					 
				</tr> 
				<tr>  
					<th>工程地址<span class="red">*</span></th> 
					<td rowspan="1" colspan="5"><textarea name="m:contractinfo:project_place" lablename="工程地址" class="oa-new-textarea" rows="5" cols="40"   value="${contractinfo.project_place}" isflag="tableflag" validate="{required:true}" >${contractinfo.project_place}</textarea> 
					</td> 					 
				</tr>
				<tr>  
					<th>合同是否归档</th> 
					<td colspan="2">
						<label><input <c:if test="${contractinfo.isrecovery==0 || empty contractinfo.isrecovery}">checked</c:if> val="${contractinfo.isrecovery}" type="radio" name="m:contractinfo:isrecovery" value="0" lablename="合同是否收回" validate="{}" />否</label>
						<label><input <c:if test="${contractinfo.isrecovery==1}">checked</c:if> val="${contractinfo.isrecovery}" type="radio" name="m:contractinfo:isrecovery" value="1" lablename="合同是否收回" validate="{}" />是</label> 
					</td> 
					<th>合同是否备案</th> 
					<td colspan="2"> 
						<label><input  <c:if test="${contractinfo.isrecord==0 || empty contractinfo.isrecord}">checked</c:if> val="${contractinfo.isrecord}" type="radio" name="m:contractinfo:isrecord" value="0" lablename="项目是否备案" validate="{}" checked=true/>否</label> 
						<label><input  <c:if test="${contractinfo.isrecord==1}">checked</c:if> val="${contractinfo.isrecord}" type="radio" name="m:contractinfo:isrecord" value="1" lablename="项目是否备案" validate="{}" />是</label>
					</td> 					
				</tr> 
				<tr>
					<th>竣工子资料是否存档</th> 
					<td colspan="2"> 
						<label><input  <c:if test="${contractinfo.issave==0 || empty contractinfo.issave}">checked</c:if> val="${contractinfo.issave}" type="radio" name="m:contractinfo:issave" value="0" lablename="竣工子资料是否存档" validate="{}" />否</label> 
						<label><input <c:if test="${contractinfo.issave==1}">checked</c:if> val="${contractinfo.issave}" type="radio" name="m:contractinfo:issave" value="1" lablename="竣工子资料是否存档" validate="{}" />是</label>
					</td>
					<th>申请时间<span class="red">*</span></th>
					<td colspan="2"><input type="text" name="m:contractinfo:create_time" value="<fmt:formatDate value='${contractinfo.create_time}' pattern='yyyy-MM-dd'/>" class="inputText date" data-class="oa-new-input" validate="{required:true}" /></td>
				</tr>
				<tr>
				    <th class="sectitle" colspan="6">详细资料及附件</th>
				</tr>
				<tr>
					<th>进度</th> 
					<td><input type="text" name="m:contractinfo:progress" lablename="进度" class="oa-new-input" value="${contractinfo.progress}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
					<th>归档时间</th>
					<td><input type="text" name="m:contractinfo:instock_date" value="<fmt:formatDate value='${contractinfo.instock_date}' pattern='yyyy-MM-dd'/>" class="inputText date" data-class="oa-new-input" validate="{date:true}" /></td>
					<th>修改时间</th>
					<td><input type="text" name="m:contractinfo:update_time" value="<fmt:formatDate value='${contractinfo.update_time}' pattern='yyyy-MM-dd'/>" class="inputText date" data-class="oa-new-input" validate="{date:true}" /></td>
				</tr> 
				<tr>   
					
					<th>修改人</th>
					<td><input type="text" parser="selectortable" name="m:contractinfo:updater" lablename="修改人" ctltype="selector"  class="oa-new-input oa-middle users" value="${contractinfo.updater}"  isflag="tableflag" /></td>
					<th>项目部</th>
					<td><input type="text" name="m:contractinfo:project_department" lablename="项目部" class="oa-new-input" value="${contractinfo.project_department}" validate="{maxlength:50}" isflag="tableflag" /></td>
				</tr> 
				<tr> 
					<th>开票金额（万元）</th> 
					<td>
					<span id="invoice_amount_show">0</span>
					<input type="hidden" name="m:contractinfo:invoice_amount" lablename="开票金额（万元）" class="oa-new-input" value="${contractinfo.invoice_amount}" validate="{number:true}" readonly="readonly" isflag="tableflag" />
					</td>
					<th>开票时间</th> 
					<td><input type="text" name="m:contractinfo:billing_time" value="<fmt:formatDate value='${contractinfo.billing_time}' pattern='yyyy-MM-dd'/>" class="inputText date" data-class="oa-new-input" validate="{date:true}" /></td>
					<th>到账金额（万元）</th> 
					<td><input type="text" name="m:contractinfo:arrival_amount" lablename="到账金额（万元）" class="oa-new-input" value="${contractinfo.arrival_amount}" validate="{number:true}" isflag="tableflag" /></td> 
				</tr> 
				<tr> 
					<th>到账时间</th> 
					<td><input type="text" name="m:contractinfo:arrival_time" value="<fmt:formatDate value='${contractinfo.arrival_time}' pattern='yyyy-MM-dd'/>" class="inputText date" data-class="oa-new-input" validate="{date:true}" /></td>
					<th>备注</th> 
					<td><input type="text" name="m:contractinfo:remark" lablename="备注" class="oa-new-input" value="${contractinfo.remark}" validate="{maxlength:50}" isflag="tableflag" /></td> 
					<th></th> 
					<td></td> 
				</tr> 
				<tr> 
					<th>附件1:合同电子档</th> 
					<td rowspan="1" colspan="5"><input  ctltype="attachment" name="m:contractinfo:file" isdirectupload="0" right="w" value='${contractinfo.file}'  /></td>
				</tr>  
				<tr>
					<th>附件2:合同扫描件</th> 
					<td rowspan="1" colspan="5"><input  ctltype="attachment" name="m:contractinfo:file_second" isdirectupload="0" right="w" value='${contractinfo.file_second}'   /></td> 
				</tr>  
				<tr> 
					<th>附件3:备案回执、中标通知书等其他资料</th> 
					<td rowspan="1" colspan="5"><input ctltype="attachment" name="m:contractinfo:file_third" isdirectupload="0" right="w" value='${contractinfo.file_third}'  /></td> 
				</tr> 
				
			</table> 
		  
		<p style="margin-top: 20px; margin-bottom: 5px;"><span style="font-size: 18px;">合同开票记录</span></p>
		<div type="subtable" tablename="contractBillingRecord" tabledesc="合同开票记录" show="true" parser="rowmodeedit" id="contractBillingRecord" formtype="edit"> 
			<table class="listTable" border="0" cellpadding="2" cellspacing="0"> 
				<tbody> 
					<tr class="toolBar firstRow"> 
						<td colspan="8" style="word-break: break-all;"><a class="link add" href="javascript:;">添加</a></td> 
					</tr> 
					<tr class="headRow"> 
						<th style="word-break: break-all;">序号</th> 
						<th style="word-break: break-all;">开票时间</th> 
						<th style="word-break: break-all;">发票金额（元）</th> 
						<th style="word-break: break-all;">申请人</th> 
						<th style="word-break: break-all;">审批状态</th> 
						<th style="word-break: break-all;">领票人</th> 
						<th style="word-break: break-all;">到账时间</th> 
						<th style="word-break: break-all;">到账金额（元）</th> 
					</tr> 
					<c:forEach items="${contractinfo.contractBillingRecordList}" var="contractBillingRecord" varStatus="status">
						<tr class="listRow" type="subdata"> 
							<td class="tdNo" style="word-break: break-all;">${status.index+1}</td> 
							<td><input type="text" name="s:contractBillingRecord:billing_date" value="<fmt:formatDate value='${contractBillingRecord.billing_date}' pattern='yyyy-MM-dd'/>" class="inputText date" data-class="oa-new-input" validate="{date:true}" /></td> 
							<td><input type="text" name="s:contractBillingRecord:invoice_money" lablename="发票金额" class="oa-new-input" value="${contractBillingRecord.invoice_money}" validate="{number:true}" isflag="tableflag" /></td> 
							<td><input name="s:contractBillingRecord:operator" type="text" ctltype="selector"  class="oa-new-input oa-middle users" lablename="经办人" value="${contractBillingRecord.operator}" validate="{empty:false}" readonly="readonly" /> </td> 
							<td><input type="text" name="s:contractBillingRecord:status" lablename="审批状态" class="oa-new-input" value="${contractBillingRecord.status}" validate="{maxlength:50}" isflag="tableflag" />  </td> 
							<td><input name="s:contractBillingRecord:bearer" type="text" ctltype="selector"  class="oa-new-input oa-middle users" lablename="领票人" value="${contractBillingRecord.bearer}" validate="{empty:false}" readonly="readonly" /> </td> 
							<td><input type="text" name="s:contractBillingRecord:payment_date" value="<fmt:formatDate value='${contractBillingRecord.payment_date}' pattern='yyyy-MM-dd'/>" class="inputText date" data-class="oa-new-input" validate="{date:true}" /></td> 
							<td style="word-break: break-all;"><input type="text" name="s:contractBillingRecord:arrival_money" lablename="到账金额" class="oa-new-input" value="${contractBillingRecord.arrival_money}" validate="{number:true}" isflag="tableflag" /></td> 
						</tr>
					</c:forEach> 
					<tr class="listRow" type="append" style="display:none;"> 
						<td class="tdNo" style="word-break: break-all;"></td> 
						<td><input type="text" name="s:contractBillingRecord:billing_date" value="<fmt:formatDate value='${contractBillingRecord.billing_date}' pattern='yyyy-MM-dd'/>" class="inputText date" data-class="oa-new-input" validate="{date:true}" /></td> 
						<td><input type="text" name="s:contractBillingRecord:invoice_money" lablename="发票金额" class="oa-new-input" value="${contractBillingRecord.invoice_money}" validate="{number:true}" isflag="tableflag" /></td> 
						<td><input name="s:contractBillingRecord:operator" type="text" ctltype="selector"  class="oa-new-input oa-middle users" lablename="经办人" value="${contractBillingRecord.operator}" validate="{empty:false}" readonly="readonly" /> </td> 
						<td><input type="text" name="s:contractBillingRecord:status" lablename="审批状态" class="oa-new-input" value="${contractBillingRecord.status}" validate="{maxlength:50}" isflag="tableflag" /></td> 
						<td><input name="s:contractBillingRecord:bearer" type="text" ctltype="selector" class="oa-new-input oa-middle users" lablename="领票人" value="${contractBillingRecord.bearer}" validate="{empty:false}" readonly="readonly" /> </td> 
						<td><input type="text" name="s:contractBillingRecord:payment_date" value="<fmt:formatDate value='${contractBillingRecord.payment_date}' pattern='yyyy-MM-dd'/>" class="inputText date" data-class="oa-new-input" validate="{date:true}" /></td> 
						<td><input type="text" name="s:contractBillingRecord:arrival_money" lablename="到账金额" class="oa-new-input" value="${contractBillingRecord.arrival_money}" validate="{maxlength:50}" isflag="tableflag" /></td> 
					</tr>
				</tbody> 
			</table> 
			<input name="s:contractBillingRecord:id" type="hidden" pk="true" value="" />
		</div> 
 
			<p sty	le="margin-top: 20px; margin-bottom: 5px;"><span style="font-size: 18px;">合同付款记录</span></p>
			<div type="subtable" tablename="contractPaymentRecord" tabledesc="合同付款记录" show="true" parser="rowmodeedit" id="contractPaymentRecord" formtype="edit"> 
				<table class="listTable" border="0" cellpadding="2" cellspacing="0"> 
					<tbody> 
						<tr class="toolBar firstRow"> 
							<td colspan="7"><a class="link add" href="javascript:;">添加</a></td> 
						</tr> 
						<tr class="headRow"> 
							<th>序号</th> 
							<th style="word-break: break-all;">申请付款时间</th> 
							<th style="word-break: break-all;">申请付款金额（万元）</th> 
							<th style="word-break: break-all;">申请人</th> 
							<th style="word-break: break-all;">审批状态</th> 
							<th style="word-break: break-all;">付款时间</th> 
							<th style="word-break: break-all;">实际支付金额（万元）</th> 
						</tr> 
						<c:forEach items="${contractinfo.contractPaymentRecordList}" var="contractPaymentRecord" varStatus="status">
							<tr class="listRow" type="subdata"> 
								<td class="tdNo">${status.index+1}</td> 
								<td><input type="text" name="s:contractPaymentRecord:apply_payment_date" value="<fmt:formatDate value='${contractPaymentRecord.apply_payment_date}' pattern='yyyy-MM-dd'/>" class="inputText date" data-class="oa-new-input" validate="{date:true}" /></td> 
								<td><input type="text" name="s:contractPaymentRecord:apply_payment_money" lablename="申请付款金额（万元）" class="oa-new-input" value="${contractPaymentRecord.apply_payment_money}" validate="{number:true}" isflag="tableflag" /></td> 
								<td><input name="s:contractPaymentRecord:operator" type="text" ctltype="selector"  class="oa-new-input oa-middle users" lablename="经办人" value="${contractPaymentRecord.operator}" validate="{empty:false}" readonly="readonly" /> </td> 
								<td><span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:contractPaymentRecord:status" lablename="审批状态" class="oa-new-input" value="${contractPaymentRecord.status}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
								<td><input type="text" name="s:contractPaymentRecord:payment_time" value="<fmt:formatDate value='${contractPaymentRecord.payment_time}' pattern='yyyy-MM-dd'/>" class="inputText date" data-class="oa-new-input" validate="{date:true}" /></td> 
								<td><input type="text" name="s:contractPaymentRecord:actual_amount_paid" lablename="实际支付金额（万元）" class="oa-new-input" value="${contractPaymentRecord.actual_amount_paid}" validate="{number:true}" isflag="tableflag" /></td> 
							</tr>
						</c:forEach> 
						<tr class="listRow" type="append" style="display:none;"> 
							<td class="tdNo"><br /></td> 
							<td><input type="text" name="s:contractPaymentRecord:apply_payment_date" value="<fmt:formatDate value='${contractPaymentRecord.apply_payment_date}' pattern='yyyy-MM-dd'/>" class="inputText date" data-class="oa-new-input" validate="{date:true}" /></td> 
							<td><input type="text" name="s:contractPaymentRecord:apply_payment_money" lablename="申请付款金额（万元）" class="oa-new-input" value="${contractPaymentRecord.apply_payment_money}" validate="{number:true}" isflag="tableflag" /></td> 
							<td><input name="s:contractPaymentRecord:operator" type="text" ctltype="selector"  class="oa-new-input oa-middle users" lablename="经办人" value="${contractPaymentRecord.operator}" validate="{empty:false}" readonly="readonly" /> </td> 
							<td><input type="text" name="s:contractPaymentRecord:status" lablename="审批状态" class="oa-new-input" value="${contractPaymentRecord.status}" validate="{maxlength:50}" isflag="tableflag" /> </td> 
							<td><input type="text" name="s:contractPaymentRecord:payment_time" value="<fmt:formatDate value='${contractPaymentRecord.payment_time}' pattern='yyyy-MM-dd'/>" class="inputText date" data-class="oa-new-input" validate="{date:true}" /></td> 
							<td><input type="text" name="s:contractPaymentRecord:actual_amount_paid" lablename="实际支付金额（万元）" class="oa-new-input" value="${contractPaymentRecord.actual_amount_paid}" validate="{number:true}" isflag="tableflag" /></td> 
						</tr>
					</tbody> 
				</table> 
				<input name="s:contractPaymentRecord:id" type="hidden" pk="true" value="" />
			</div>
	        <input type="hidden" name="id" value="${contractinfo.id}"/>
	        <input type="hidden" id="saveData" name="saveData"/>
	        <input type="hidden" name="executeType"  value="start" />
	        <input type="hidden" name="m:contractinfo:instock_sign" value="${contractinfo.instock_sign}"/>
            <input type="hidden" name="m:contractinfo:sure_sign" value="${contractinfo.sure_sign}"/>
	        <input type="hidden" name="m:contractinfo:filecopies" value="${contractinfo.filecopies}"/>
	        <input type="hidden" name="m:contractinfo:fj_sencond_copies" value="${contractinfo.fj_sencond_copies}"/>
	        <input type="hidden" name="m:contractinfo:project_chapter" value="${contractinfo.project_chapter}"/>
    	</form>
	</div>
	<script type="text/javascript">
		$(document).on("input","input[name='s:contractBillingRecord:invoice_money']",function(){  
			var invoice_amount_total = 0;
			$("input[name='s:contractBillingRecord:invoice_money']").each( function(i,item){
				if($.trim($(this).val())=='')
					return true;
				invoice_amount_total += parseFloat($(this).val()) ;
			});
			$("#invoice_amount_show").html((parseFloat(invoice_amount_total) * 0.0001).toFixed(4));
			$("input[name='m:contractinfo:invoice_amount']").val((parseFloat(invoice_amount_total) * 0.0001).toFixed(4));
	    });
		changeOrg();
		//部门变化的时候，将部门名称填入对应的值
		function changeOrg(){
			var name = $("#contract_departmentID option:selected").data("name");
			$("#contract_department").val(name);
		}
	</script>
</body>
</html>

