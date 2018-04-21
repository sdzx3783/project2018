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
                        window.location.href = "${ctx}/makshi/assetregistration/assetRegistration/list.ht";
                    }
                });
            } else {
                $.ligerDialog.error(obj.getMessage(),"提示信息");
            }
        }
      $(function(){
            var get_date = $("#get_date").text().trim();
            $("#get_date_value").val(get_date);
            var use_date = $("#use_date").text().trim();
            $("#use_date_value").val(use_date); 
            var abandonment_date = $("#abandonment_date").text().trim();
            $("#abandonment_date_value").val(abandonment_date);
        }); 
    </script>
<!-- <style type="text/css">
.inputText{
border:0px !important;
}
</style> -->
<style>
    #assetRegistrationForm{
        margin: 0 10px;
    }
</style>
</head>
<body>
<div class="panel" style="height:100%;overflow:auto;">
    <div class="panel-top">
        <div class="tbar-title">
            <c:choose>
                <c:when test="${not empty assetRegistrationItem.id}">
                    <span class="tbar-label"><span></span>编辑资产登记表</span>
                </c:when>
                <c:otherwise>
                    <span class="tbar-label"><span></span>添加资产登记表</span>
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
    <form id="assetRegistrationForm" method="post" action="save.ht">
        <div type="custform">
            <div class="panel-detail">
                <table class="formTable" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
 <tbody>
  <tr class="firstRow">
   <td class="formHead" colspan="4" style="word-break: break-all;">资产报废表</td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">资产编号:</td>
   <td style="width: 35%" class="formInput">
    <span name="editable-input" style="display:inline-block;" isflag="tableflag"> 
        ${assetRegistration.asset_id}
        <input type="hidden" readonly="readonly" name="m:asset_registration:asset_id" lablename="资产编号" class="inputText" value="${assetRegistration.asset_id}" validate="{maxlength:50}" isflag="tableflag" readonly="readonly"/>
    </span>
   </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">取得日期:</td>
   <td style="width: 35%" class="formInput">
    <span  id="get_date" style="">
    <fmt:formatDate   value="${assetRegistration.get_date}" pattern="yyyy-MM-dd" type="date" dateStyle="long" />
    </span>
    <span name="editable-input" style="display:none;" isflag="tableflag"> 
        <input id="get_date_value" parser="datetable" name="m:asset_registration:get_date" lablename="取得日期" class="Wdate" displaydate="0" datefmt="yyyy-MM-dd" value="" validate="{empty:false}" type="text"/>
    </span> 
   </td>
  </tr>
  <tr>
    <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">条码编号:</td>
   	<td style="width: 35%" class="formInput">
   		${assetRegistration.card_number}
         <input type="hidden"  readonly="readonly" name="m:asset_registration:card_number" lablename="条码编号" class="inputText" value="${assetRegistration.card_number}" validate="{&quot;maxlength&quot;:50,&quot;required&quot;:&quot;true&quot;}" isflag="tableflag" />
   </td>
     <td style="width: 15%; word-break: break-all;" class="formTitle" align="right"></td>
  	 <td style="width: 35%" class="formInput"></td>
  </tr>
  <tr>
    
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">保管人:</td>
   <td style="width: 35%" class="formInput">
    <span name="editable-input" style="display:inline-block;" isflag="tableflag"> 
        ${assetRegistration.care_person}
        <input type="hidden" readonly="readonly" name="m:asset_registration:care_person" lablename="保管人" class="inputText" value="${assetRegistration.care_person}" validate="{maxlength:50}" isflag="tableflag" />
    </span>
   </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">使用部门:</td>
   <td style="width: 35%" class="formInput">
    <span name="editable-input" style="display:inline-block;" isflag="tableflag"> 
        ${assetRegistration.use_department}
        <input readonly="readonly" type="hidden" name="m:asset_registration:use_department" lablename="使用部门" class="inputText" value="${assetRegistration.use_department}" validate="{maxlength:50}" isflag="tableflag" />
    </span> 
   
   </td>
  </tr>
  <tr>
    <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">资产分类代码:</td>
   <td style="width: 35%" class="formInput">
   <span name="editable-input" style="display:inline-block;" isflag="tableflag"> 
        ${assetRegistration.asset_code}
    </span>
     <input type="hidden" readonly="readonly" name="m:asset_registration:asset_type" lablename="资产类别" class="inputText" value="${assetRegistration.asset_type}" validate="{maxlength:50}" isflag="tableflag" />
   </td>
   </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">报废时间:</td>
   <td class="formInput" colspan="3" rowspan="1" style="width: 35%">
   <span  id="abandonment_date" style="display:none">
    <fmt:formatDate   value="${assetRegistration.abandonment_date}" pattern="yyyy-MM-dd" type="date" dateStyle="long" />
    </span>
    <input id="abandonment_date_value" parser="datetable" name="m:asset_registration:abandonment_date" lablename="报废时间" class="Wdate" value="${assetRegistration.abandonment_date}" datefmt="yyyy-MM-dd"  validate="{empty:false}" type="text"/>
    
  </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">资产名称:</td>
   <td style="width: 35%" class="formInput">
    <span name="editable-input" style="display:inline-block;" isflag="tableflag"> 
        ${assetRegistration.asset_name}
        <input readonly="readonly" type="hidden" name="m:asset_registration:asset_name" lablename="资产名称" class="inputText" value="${assetRegistration.asset_name}" validate="{maxlength:50}" isflag="tableflag" />
    </span>
   </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">使用日期:</td>
   <td style="width: 35%" class="formInput">
    <%-- <span name="editable-input" style="display:inline-block;" isflag="tableflag"> 
        <input type="text" name="m:asset_registration:use_date" lablename="使用日期" class="inputText" value="${assetRegistration.use_date}" validate="{maxlength:50}" isflag="tableflag" />
    </span> --%>
    <span  id="use_date" style="">
    <fmt:formatDate   value="${assetRegistration.use_date}" pattern="yyyy-MM-dd" type="date" dateStyle="long" />
    </span>
    <span style="display:none">
    <input id="use_date_value" parser="datetable" name="m:asset_registration:use_date" lablename="使用日期" class="Wdate" displaydate="0" datefmt="yyyy-MM-dd" value="" validate="{empty:false}" type="text"/>
    </span>
   </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">规格型号:</td>
   <td style="width: 35%" class="formInput"> 
   <span name="editable-input" style="display:inline-block;" isflag="tableflag"> 
   ${assetRegistration.specification}
   <input readonly="readonly" type="hidden" name="m:asset_registration:specification" lablename="规格型号" class="inputText" value="${assetRegistration.specification}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">数量:</td>
   <td style="width: 35%" class="formInput"> 
   <span name="editable-input" style="display:inline-block;" isflag="tableflag"> 
   ${assetRegistration.number}
   <input readonly="readonly" type="hidden" name="m:asset_registration:number" lablename="数量" class="inputText" value="${assetRegistration.number}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">资产类别:</td>
   <td style="width: 35%" class="formInput">
   <span name="editable-input" style="display:inline-block;" isflag="tableflag"> 
   ${assetRegistration.version}
    <input readonly="readonly" type="hidden" name="m:asset_registration:version" lablename="数量" class="inputText" value="${assetRegistration.version}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">使用状况:</td>
   <td style="width: 35%" class="formInput">
   
    <span name="editable-input" style="display:inline-block;padding:2px;" class="selectinput"> 
    <select name="m:asset_registration:state" lablename="使用状况" controltype="select" validate="{empty:false}" val="${assetRegistration.state}"> 
    <option value="0">在用</option> <option value="1">报废</option> </select> </span> </td>
  </tr>
  <tr>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">取得方式:</td>
   <td style="width: 35%" class="formInput">
    <span name="editable-input" style="display:inline-block;" isflag="tableflag"> 
        <c:if test="${assetRegistration.get_type==0}">委托办公室购买</c:if>
        <c:if test="${assetRegistration.get_type==1}">项目部自行购买</c:if>
        <input readonly="readonly" type="hidden" name="m:asset_registration:get_type" lablename="取得方式" class="inputText" value="${assetRegistration.get_type}" validate="{maxlength:50}" isflag="tableflag" />
    </span>
   </td>
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">上传贴条照片:</td>
   <td style="width: 35%" class="formInput">
    <input ctltype="attachment" name="m:asset_registration:attachment" isdirectupload="0" right="" value='${assetRegistration.attachment}' validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}" /></td> 
  </tr>
 </tbody>
</table>
            </div>
        </div>
        <input type="hidden" name="id" value="${assetRegistration.id}"/>
        <input type="hidden" id="saveData" name="saveData"/>
        <input type="hidden" name="executeType"  value="start" />
    </form>
</body>
</html>
