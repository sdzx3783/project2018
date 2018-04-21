<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
    <title>编辑分类库</title>
    <%@include file="/codegen/include/customForm.jsp" %>
    <%@include file="/commons/include/ueditor.jsp" %>
    <script src="${ctx}/js/hotent/formdata.js"></script>
    <script src="${ctx}/js/hotent/CustomValid.js"></script>
    <script src="${ctx}/js/hotent/subform.js"></script>
    <script>
        $(function() {
            var options={};
            if(showResponse){
                options.success=showResponse;
            }
            var frm=$('#classifylibForm').form();
            $("#dataFormSave").click(function() {
                frm.ajaxForm(options);
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
                            $('#classifylibForm').submit();
                        });
                    }else{
                        frm.handleFieldName();
                        $('#classifylibForm').submit();
                    }
                }
            });
        });
        
        function showResponse(responseText) {
            var obj = new com.hotent.form.ResultMessage(responseText);
            if (obj.isSuccess()) {
                window.parent.location.reload(true);
                window.close();
            } else {
                $.ligerDialog.error(obj.getMessage(),"提示信息");
            }
            
        }
        
        $(function(){
            var _charger='${classifyLib.charger}';
            var _chargerID='${classifyLib.chargerID}';
            if(_charger!=null && _charger.length>0  && _chargerID!=null && _chargerID.length>0){
                $("input[name='chargerID']").eq(0).val(_chargerID);
            }
            var _org='${classifyLib.org}';
            var _orgID='${classifyLib.orgID}';
            if(_org!=null && _org.length>0  && _orgID!=null && _orgID.length>0){
                $("input[name='orgID']").eq(0).val(_orgID);
            }
            
        })
        
    </script>
    <style>
    	.oa-table td{
    		padding: 10px 20px;
    	}
		.oa-input-wrap,
		.oa-select-wrap{
			display: inline-block;
		}
    </style>
</head>
<body>

    <div class="oa-top-wrap">
        <button class="oa-button oa-button--primary oa-button--blue" id="dataFormSave">保存</button>
    </div>

    <div class="oa-main-wrap">
        <form id="classifylibForm" method="post" action="save.ht">
            <table class="oa-table">
                <tr>
                    <th>分类名称：</th>
                    <td>
                        <div class="oa-input-wrap">
                            <input class="oa-input" type="text" name="name" value="${classifyLib.name }" validate="{'required':true,'maxlength':150}">
                        </div>
                    </td>
                </tr>
                <tr>
                    <th>负责人：</th>
                    <td>
                        <div class="oa-input-wrap">
                            <input class="users oa-input oa-hidden-trigger" name="charger"  type="text" ctltype="selector" value="${classifyLib.charger }" validate="{'required':true}" readonly="readonly" />
                        </div>
                        <button class="oa-button-label oa-trigger-hidden-button" type="button">选择用户</button>
                    </td>
                </tr>
                <tr>
                    <th>所属部门：</th>
                    <td>
                        <div class="oa-input-wrap">
                            <input class="org oa-input oa-hidden-trigger" name="org" type="text" ctltype="selector" value="${classifyLib.org }" validate="{'required':true}" readonly="readonly" />
                        </div>
                        <button class="oa-button-label oa-trigger-hidden-button" type="button">选择部门</button>
                    </td>
                </tr>
            </table>

            <input type="hidden" name="id" value="${classifyLib.id }">
            <input type="hidden" name="savetype" value="1">
        </form>
    </div>

<script>
    $(function(){
        $(".oa-trigger-hidden-button").on("click", function(){
            $(this).parent("td").find("a.oa-hidden-trigger").click();
        });
    });
</script>
</body>
</html>
