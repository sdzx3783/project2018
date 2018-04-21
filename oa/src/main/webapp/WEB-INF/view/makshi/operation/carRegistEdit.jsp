<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
    <head>
        <title>编辑 车辆登记</title>
        <%@include file="/codegen/include/customForm.jsp" %>
        <%@include file="/commons/include/ueditor.jsp" %>
        <script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
        <script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
        <script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
        <script type="text/javascript" src="${ctx}/js/hotent/platform/form/AttachMent.js"></script>
        <script type="text/javascript" src="${ctx}/js/hotent/platform/form/TablePermission.js"></script>
        <script type="text/javascript">
            $(function() {
                var options={};
                if(showResponse){
                    options.success=showResponse;
                }
                var frm=$('#carRegistForm').form();
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
                                $('#carRegistForm').submit();
                            });
                        }else{
                            frm.handleFieldName();
                            $('#carRegistForm').submit();
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
                            window.location.href = "${ctx}/makshi/operation/carRegist/list.ht";
                        }
                    });
                } else {
                    $.ligerDialog.error(obj.getMessage(),"提示信息");
                }
            }
        </script>
</head>
<body class="oa-mw-page">

    <div class="oa-project-title">
       
    </div>

    <div class="oa-mg20">
        <a class="oa-button oa-button--primary oa-button--blue save" id="dataFormSave" href="javascript:;">保存</a>
        <a class="oa-button oa-button--primary oa-button--blue" href="list.ht">返回</a>
    </div>
    
    <div class="oa-mg20">

    <form id="carRegistForm" method="post" action="save.ht">
                <table class="oa-table--default" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
                    <caption>车辆信息登记</caption>
                    <tr>
                        <td>车辆型号</td>
                        <td>
                            <div class="oa-input-wrap  oa-input-wrap--ib">
                                <input type="text" name="m:car_regist:version" lablename="车辆型号" class="oa-input" value="${carRegist.version}" validate="{required:true,maxlength:50}" isflag="tableflag" />
                            </div>
                        </td>
                        <td>车牌号</td>
                        <td>
                            <div class="oa-input-wrap  oa-input-wrap--ib">
                                <input type="text" name="m:car_regist:carId" lablename="车牌号" class="oa-input" value="${carRegist.carId}" validate="{required:true,maxlength:50}" isflag="tableflag" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>项目部</td>
                        <td>
                            <div class="oa-input-wrap oa-input-wrap--ib">
                                <input type="text" ctltype="selector" name="m:car_regist:department" lablename="项目部" class="org oa-input oa-hidden-trigger" value="${carRegist.department}" validate="{maxlength:50}" isflag="tableflag" />
                            </div>
                            <button class="oa-button-label oa-trigger-hidden-button" type="button">选择部门</button>
                        </td>
                        <td>保管人</td>
                        <td>
                            <div class="oa-input-wrap oa-input-wrap--ib">
                                <input type="text" name="m:car_regist:kepper" ctltype="selector" class="user oa-input oa-hidden-trigger" lablename="保管人"  value="${carRegist.kepper}" validate="{maxlength:50}" isflag="tableflag" />
                            </div>
                            <button class="oa-button-label oa-trigger-hidden-button" type="button">选择用户</button>
                        </td>
                    </tr>
                    <tr>
                        <td>加油卡</td>
                        <td>
                            <div class="oa-input-wrap  oa-input-wrap--ib">
                                <input type="text" name="m:car_regist:oil_card" lablename="加油卡" class="oa-input" value="${carRegist.oil_card}" validate="{maxlength:50}" isflag="tableflag" />
                            </div>
                        </td>
                        <td>粤通卡</td>
                        <td>
                            <div class="oa-input-wrap  oa-input-wrap--ib">
                                <input type="text" name="m:car_regist:pass_card" lablename="粤通卡" class="oa-input" value="${carRegist.pass_card}" validate="{maxlength:50}" isflag="tableflag" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>附件</td>
                        <td rowspan="1" colspan="3">
                            <input ctltype="attachment" right="w" name="m:monthly_manage:attachment" isdirectupload="0" value='${carRegist.attachment}' validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}" />
                        </td>
                    </tr>
                </table>
            <input type="hidden" name="id" value="${carRegist.id}"/>
            <input type="hidden" id="saveData" name="saveData"/>
            <input type="hidden" name="executeType"  value="start" />
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