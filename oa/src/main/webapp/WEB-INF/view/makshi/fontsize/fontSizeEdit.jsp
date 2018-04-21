<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
    <title>编辑 公文字号</title>
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
            var frm=$('#fontSizeForm').form();
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
                            $('#fontSizeForm').submit();
                        });
                    }else{
                        frm.handleFieldName();
                        $('#fontSizeForm').submit();
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
                        window.location.href = "${ctx}/makshi/fontsize/fontSize/list.ht";
                    }
                });
            } else {
                $.ligerDialog.error(obj.getMessage(),"提示信息");
            }
        }
    </script>
    <style type="text/css">
        #selectType{
        width:auto !important;
        min-width:auto;
        border:0px !important;
        height:auto !important;
        }
        #fontSizeForm{
            margin: 0 10px;
        }
    </style>
</head>
<body>
<div class="panel" style="height:100%;overflow:auto;">
    <div class="panel-top">
        <div class="tbar-title">
            <c:choose>
                <c:when test="${not empty fontSizeItem.id}">
                    <span class="tbar-label"><span></span>编辑公文字号</span>
                </c:when>
                <c:otherwise>
                    <span class="tbar-label"><span></span>添加公文字号</span>
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
    <form id="fontSizeForm" method="post" action="save.ht">
        <div type="custform">
            <div class="panel-detail">
                <table class="formTable" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled"> 
 <tbody> 
  <tr class="firstRow"> 
   <td class="formHead" colspan="4" style="word-break: break-all;">公文字号表</td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">公文类型:</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"> 
   
   <div class="l-text-wrapper" style="width: 150px; height: 23px;">
      <span name="editable-input" style="display:inline-block;" isflag="tableflag"> 
           <input id="selectType"  class="dicComboTree l-text-field" nodekey="gwlx" value="${fontSize.type}" name="m:font_size:type" validate="{empty:false}" height="100" width="250" />
         </span>
   </div>
   
   
<%--    
   <input  type="text" value="公文类型" style="width: 150px; height: 18px;" validate="{maxlength:50}" isflag="tableflag" /> </span> <br />
   <input type="text" name="m:document_type:type" lablename="公文类型" class="inputText" value="${documentType.type}" validate="{maxlength:50}" isflag="tableflag" />  --%>
   </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">发文字号:</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3">
      <span name="editable-input" style="display:inline-block;" isflag="tableflag">
     <input type="text" name="m:font_size:font_size" lablename="公文类型" class="inputText" value="${fontSize.font_size}" validate="{maxlength:50}" isflag="tableflag" /> </span> <br />
   </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">发文年份:</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3">
      <span name="editable-input" style="display:inline-block;" isflag="tableflag">
     <input type="text" name="m:font_size:dispatch_year" lablename="公文类型" class="inputText" value="${fontSize.dispatch_year}" validate="{maxlength:50}" isflag="tableflag" /> </span> <br />
   </td> 
  </tr>
 </tbody> 
</table>
            </div>
        </div>
        <input type="hidden" name="id" value="${fontSize.id}"/>
        <input type="hidden" id="saveData" name="saveData"/>
        <input type="hidden" name="executeType"  value="start" />
    </form>
</body>
</html>
