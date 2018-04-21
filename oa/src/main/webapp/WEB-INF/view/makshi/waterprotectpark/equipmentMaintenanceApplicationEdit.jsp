<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 设施设备维修申请(水保园流程)</title>
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
			var frm=$('#equipmentMaintenanceApplicationForm').form();
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
							$('#equipmentMaintenanceApplicationForm').submit();
						});
					}else{
						frm.handleFieldName();
						$('#equipmentMaintenanceApplicationForm').submit();
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
						window.location.href = "${ctx}/makshi/waterprotectpark/equipmentMaintenanceApplication/list.ht";
					}
				});
			} else {
				$.ligerDialog.error(obj.getMessage(),"提示信息");
			}
		}
	</script>
    <style>
        .no-border{
            border: 0 !important;
        }
    </style>
</head>
<body class="oa-mw-page">

    <div class="oa-pd20">
        <a class="oa-button oa-button--primary oa-button--blue  save" id="dataFormSave">保存</a>
        <a class="oa-button oa-button--primary oa-button--blue  back" href="list.ht">返回</a>  
    </div>
    
    <div class="oa-mg20">
        <form id="equipmentMaintenanceApplicationForm" method="post" action="save.ht">
            <div type="custform">
                <div class="panel-detail">
                    <table class="oa-table--default" border="1" cellspacing="0" cellpadding="2" parser="addpermission"> 
                     <tbody> 
                        <caption>设施设备维修</caption>
                      <tr> 
                       <td>申请人</td> 
                       <td> 
                        <div class="oa-input-wrap">
                            <input name="m:equipment_maintenance_application:applicant" class="oa-input" type="text"  lablename="申请人" value="${equipmentMaintenanceApplication.applicant}"  validate="{empty:false}" readonly="readonly"/>
                        </div>
                        <input type="hidden" name="m:equipment_maintenance_application:applicantID" value="${equipmentMaintenanceApplication.applicantID}">
                        </td> 
                       <td>申请时间</td> 
                       <td> 
                            <div class="oa-input-wrap">
                                <input class="oa-input" name="m:equipment_maintenance_application:applicationTime" readonly="readonly"  type="text" value="<fmt:formatDate value='${equipmentMaintenanceApplication.applicationTime}' pattern='yyyy-MM-dd'/>" validate="{empty:false}" />
                            </div>
                        </td> 
                      </tr> 
                      <tr> 
                       <td>职能组</td> 
                       <td> 
                            <div class="oa-input-wrap">
                                <input name="m:equipment_maintenance_application:org" type="text"  class="org oa-input" lablename="职能组" value="${equipmentMaintenanceApplication.org}" validate="{empty:false}" readonly="readonly" /> 
                            </div>
                            <input name="m:equipment_maintenance_application:orgID" type="hidden" lablename="职能组" value="${equipmentMaintenanceApplication.orgID}"  /> 
                        </td> 
                       </td>
                        
                       <td>故障发生时间</td> 
                       <td> 
                            <div class="oa-input-wrap">
                                <input name="m:equipment_maintenance_application:faultTime" type="text" class="Wdate oa-input no-border" displaydate="0" datefmt="yyyy-MM-dd" value="<fmt:formatDate value='${equipmentMaintenanceApplication.faultTime}' pattern='yyyy-MM-dd'/>" validate="{empty:false}" /> 
                            </div>
                        </td> 
                      </tr> 
                      <tr> 
                       <td>设备名称</td> 
                       <td colspan="1" rowspan="1" > <span name="editable-input" isflag="tableflag"> 
                            <div class="oa-input-wrap">
                                <input type="text" name="m:equipment_maintenance_application:equipmentName" lablename="设备名称" class="oa-input" value="${equipmentMaintenanceApplication.equipmentName}" validate="{maxlength:50}" isflag="tableflag" /> </span> 
                            </div>
                        </td> 
                       <td>存在问题及故障部位</td> 
                       <td colspan="1" rowspan="1" > <span name="editable-input" isflag="tableflag">
                            <div class="oa-input-wrap">
                                 <input type="text" name="m:equipment_maintenance_application:problemDesc" lablename="存在问题及故障部位" class="oa-input" value="${equipmentMaintenanceApplication.problemDesc}" validate="{maxlength:50}" isflag="tableflag" /> </span> 
                            </div>
                        </td> 
                      </tr> 
                      <tr> 
                       <td>维修方案</td> 
                       <td colspan="1" rowspan="1" > <span name="editable-input" isflag="tableflag"> 
                            <div class="oa-input-wrap">
                                <input type="text" name="m:equipment_maintenance_application:maintenancePlan" lablename="维修方案" class="oa-input" value="${equipmentMaintenanceApplication.maintenancePlan}" validate="{maxlength:50}" isflag="tableflag" /> </span> 
                            </div>
                        </td> 
                       <td>维修费用</td> 
                       <td colspan="1" rowspan="1" > 
                            <div class="oa-input-wrap">
                                <input class="oa-input" name="m:equipment_maintenance_application:maintenanceCost" type="text" value="${equipmentMaintenanceApplication.maintenanceCost}" showtype="{"isShowComdify":1,"coinValue":"￥","decimalValue":"2"}" validate="{number:true,maxIntLen:14,maxDecimalLen:2}" /> 
                            </div>
                        </td> 
                      </tr> 
                      <tr> 
                       <td>备注</td> 
                       <td colspan="3" rowspan="1" > 
                            <span name="editable-input" isflag="tableflag"> 
                                <div class="oa-textarea-wrap">
                                    <textarea name="m:equipment_maintenance_application:remark" lablename="备注" class="oa-textarea no-border" rows="5" cols="40" validate="{maxlength:1000}" isflag="tableflag">${equipmentMaintenanceApplication.remark}</textarea>
                                </div>
                            </span> 

                        </td> 
                      </tr> 
                      <tr> 
                       <td>附件</td> 
                       <td colspan="1" rowspan="1" >
                           <input ctltype="attachment" name="m:equipment_maintenance_application:attachment" isdirectupload="0" right="w"  value='${equipmentMaintenanceApplication.attachment}' validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}" />
                       </td> 
                       <td colspan="1" rowspan="1"><br /></td> 
                       <td colspan="1" rowspan="1"><br /></td> 
                      </tr> 
                     </tbody> 
                    </table>
                </div>
            </div>
            <input type="hidden" name="id" value="${equipmentMaintenanceApplication.id}"/>
            <input type="hidden" id="saveData" name="saveData"/>
            <input type="hidden" name="executeType"  value="start" />
        </form>
            
    </div>

</body>
</html>
