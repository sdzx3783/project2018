<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 劳务人员信息采集(水保示范园流程)</title>
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
			var frm=$('#contractWorkersInfoCollectionForm').form();
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
							$('#contractWorkersInfoCollectionForm').submit();
						});
					}else{
						frm.handleFieldName();
						$('#contractWorkersInfoCollectionForm').submit();
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
						window.location.href = "${ctx}/makshi/waterprotectpark/contractWorkersInfoCollection/list.ht";
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
        <form id="contractWorkersInfoCollectionForm" method="post" action="save.ht">
        	<table class="oa-table--default" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled"> 
             <tbody> 
              <caption>劳务人员信息采集</caption>
              <tr> 
               <td>姓名</td> 
               <td>
                <span name="editable-input" isflag="tableflag"> 
                    <div class="oa-input-wrap oa-input-wrap--ib">
                        <input type="text" name="m:contract_workers_info_collection:name" lablename="名称" class="oa-input" value="${contractWorkersInfoCollection.name}" validate="{required:true,maxlength:50}" isflag="tableflag" /> 
                    </div>
                </span> 
                </td> 
               <td>入职时间</td> 
               <td>
                    <div class="oa-input-wrap oa-input-wrap--ib">
               	        <input id="entry_date" parser="datetable" name="m:contract_workers_info_collection:entry_date" lablename="入职日期" class="Wdate oa-input" value="<fmt:formatDate   value="${contractWorkersInfoCollection.entry_date}" pattern="yyyy-MM-dd" type="date" dateStyle="long" />" datefmt="yyyy-MM-dd"  validate="{required:true,empty:false}" type="text"/>
                    </div>
               </td>
              </tr> 
              <tr> 
               <td>身份证号</td> 
               <td>

               		<span name="editable-input" isflag="tableflag"> 
                        <div class="oa-input-wrap oa-input-wrap--ib">
                            <input type="text" name="m:contract_workers_info_collection:id_card" lablename="身份证" class="oa-input" value="${contractWorkersInfoCollection.id_card}" validate="{required:true,maxlength:25,验证身份证:true}" isflag="tableflag" />
                        </div>
                     </span>
               </td> 
               <td>登记人</td> 
               <td> 
                    <div class="oa-input-wrap oa-input-wrap--ib">
                        <input name="m:contract_workers_info_collection:registerer" class="oa-input" type="text" lablename="登记人" value="${contractWorkersInfoCollection.registerer}" validate="{required:true,empty:false}" readonly="readonly" /><input name="m:contract_workers_info_collection:registererID" type="hidden" lablename="登记人" value="${contractWorkersInfoCollection.registererID}" validate="{required:true,empty:false}"/> 
                    </div>
                </td> 
              </tr> 
              <tr> 
               <td>户籍</td> 
               <td colspan="1" rowspan="1"> 
                    <span name="editable-input" isflag="tableflag"> 
                        <div class="oa-input-wrap oa-input-wrap--ib">
                            <input type="text" name="m:contract_workers_info_collection:address" lablename="户籍" class="oa-input" value="${contractWorkersInfoCollection.address}" validate="{maxlength:100}" isflag="tableflag" /> 
                        </div>
                    </span>
                </td> 
               <td>紧急联系人</td> 
               <td colspan="1" rowspan="1"> 
                    <span name="editable-input" isflag="tableflag"> 
                        <div class="oa-input-wrap oa-input-wrap--ib">
                            <input type="text" name="m:contract_workers_info_collection:emergencyer" lablename="紧急联系人" class="oa-input" value="${contractWorkersInfoCollection.emergencyer}" validate="{maxlength:50}" isflag="tableflag" /> 
                        </div>
                    </span> 
                </td> 
              </tr> 
              <tr> 
               <td>紧急联系人电话</td> 
               <td colspan="1" rowspan="1">
               		<span name="editable-input" isflag="tableflag"> 
                        <div class="oa-input-wrap oa-input-wrap--ib">
                            <input type="text" name="m:contract_workers_info_collection:emergency_phone" lablename="紧急联系人电话" class="oa-input" value="${contractWorkersInfoCollection.emergency_phone}" validate="{maxlength:11,手机号码:true}" isflag="tableflag" /> 
                        </div>
                    </span> 
               </td> 
               <td>劳务工种</td> 
               <td colspan="1" rowspan="1">
               		<span name="editable-input" isflag="tableflag">
                        <div class="oa-select-wrap oa-select-wrap--ib">
                            <select validate="{required:true}" class="oa-select" name="m:contract_workers_info_collection:work_type">
                                <option <c:if test="${contractWorkersInfoCollection.work_type==2 }">selected=selected</c:if> value="2">其他</option>
                                <option <c:if test="${contractWorkersInfoCollection.work_type==1 }">selected=selected</c:if> value="1">保安</option>
                                <option <c:if test="${contractWorkersInfoCollection.work_type==0 }">selected=selected</c:if> value="0">保洁</option>
                            </select>
                        </div>
               		</span>
               </td> 
              </tr> 
              <tr> 
               <td>是否住宿</td> 
               <td colspan="1" rowspan="1">
               		<span> <label><input val="${contractWorkersInfoCollection.is_stay}" validate="{required:true}"  type="radio" name="m:contract_workers_info_collection:is_stay" value="1" lablename="是否住宿" validate="{}" />是</label> <label><input val="${contractWorkersInfoCollection.is_stay}" type="radio" validate="{required:true}"  name="m:contract_workers_info_collection:is_stay" value="0" lablename="是否住宿" validate="{}" />否</label> </span>
               </td> 
               <td>性别</td> 
               <td colspan="1" rowspan="1"> <span> <label><input val="${contractWorkersInfoCollection.gender}" validate="{required:true}"  type="radio" name="m:contract_workers_info_collection:gender" value="1" lablename="性别" validate="{}" />男</label> <label><input val="${contractWorkersInfoCollection.gender}" type="radio" validate="{required:true}"  name="m:contract_workers_info_collection:gender" value="0" lablename="性别" validate="{}" />女</label> </span> </td> 
              </tr> 
              <tr> 
               <td>年龄</td> 
               <td colspan="1" rowspan="1" width="378"> 
                    <div class="oa-input-wrap oa-input-wrap--ib">
                        <input name="m:contract_workers_info_collection:age" class="oa-input" type="text" value="${contractWorkersInfoCollection.age}" showtype="{"isShowComdify":0,"coinValue":"","decimalValue":"0","minValue":1,"maxValue":200}" validate="{required:true,number:true,maxIntLen:3,maxDecimalLen:0}" /> 
                    </div>
                </td> 
               <td>在职状态</td> 
              	<td colspan="1" rowspan="1">
               		<span name="editable-input" isflag="tableflag">
                        <div class="oa-select-wrap oa-select-wrap--ib">
                            <select validate="{required:true}" class="oa-select" name="m:contract_workers_info_collection:status">
                                <option <c:if test="${contractWorkersInfoCollection.status==1 }">selected=selected</c:if> value="1">在职</option>
                                <option <c:if test="${contractWorkersInfoCollection.status==0 }">selected=selected</c:if> value="0">离职</option>
                            </select>
                        </div>
               		</span>
               </td> 
              </tr> 
              <tr> 
               <td>备注</td> 
               <td colspan="3" rowspan="1">
                    <span name="editable-input" isflag="tableflag"> 
                        <div class="oa-textarea-wrap">
                            <textarea name="m:contract_workers_info_collection:remark" lablename="备注" class="oa-textarea" rows="5" cols="40" validate="{maxlength:1000}" isflag="tableflag">${contractWorkersInfoCollection.remark}</textarea> 
                        </div>
                    </span>
                </td> 
              </tr> 
              <tr> 
               <td>相片</td> 
               <td colspan="3" rowspan="1">
               		   	<input ctltype="attachment" name="m:contract_workers_info_collection:attachment" isdirectupload="0" right="w"  value='${contractWorkersInfoCollection.attachment}' validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}" />
               </td> 
              </tr> 
             </tbody> 
            </table>

    		<input type="hidden" name="id" value="${contractWorkersInfoCollection.id}"/>
    		<input type="hidden" id="saveData" name="saveData"/>
    		<input type="hidden" name="executeType"  value="start" />
    	</form>
    </div>
</body>
</html>
