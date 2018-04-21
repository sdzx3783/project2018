<%@page language="java" pageEncoding="UTF-8"%>
<html>
<head>
    <title>转派责任人</title>
    <%@include file="/commons/include/customForm.jsp" %>
    <%@include file="/commons/include/ueditor.jsp" %>
    <script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysPlanScript.js"></script>
    <style>
    	.oa-table{
    		font-size: 14px;
    	}
    	.oa-table td{
    		padding: 10px 20px;
    	}
    	.oa-input-wrap{
    		display: inline-block;
    	}
		a.oa-hidden-trigger{
			display: none;
		}
    </style>
</head>
<body>
    <div class="oa-project-title">
        <h2>转派责任人</h2>
    </div>
    <div class="oa-project-search">
        <button class="oa-button oa-button--primary oa-button--blue" id="dataFormSave">提交</button>
    </div>
    <div class="oa-project-content">
        <form id="projectForm" method="post" action="transfer.ht">
            <table class="oa-table">
            	<input type="hidden"  name="taskId" value="${id}"/>
                <tr>
                    <th>任务进度：</th>
                    <td>
                    	${progress }%<input type="hidden" name="progress" value="${progress }" />
                    </td>
                </tr>
                <tr>
                    <th>任务负责人：</th>
                    <td>
                    	<div class="oa-input-wrap">
                    	 <input type="text" class="oa-input" name="charger" value="${task.charger }" readonly="readonly" validate="{required:true}"/>
                         <a href="javascript:;" class="link users oa-hidden-trigger"  name="charger" >选择</a>
                         <input type="hidden" name="chargerID" value="${task.chargerID }"/>
                   	    </div>
                     	<button type="button" class="oa-button-label" onclick="chooseUser(this,'chargeDiv','chargerID','charger','yes');">选择用户</button>
                    </td>
                </tr>
                 <tr>
                    <th>变更原因：</th>
                    <td class="editor-wrap">
						<textarea style="height:100px;width:300px;" id="reason" name="reason"></textarea>
					</td>
                </tr>
            </table>
        </form>
    </div>


<script type="text/javascript">
    $(function() {
    	$(".oa-trigger-hidden-button").on("click", function(){
    		$(this).parent("td").find("a.oa-hidden-trigger").click();
    	});

        var options={};
        if(showResponse){
            options.success=showResponse;
        }
        var frm=$('#projectForm').form();
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
                        $('#projectForm').submit();
                    });
                }else{
                    frm.handleFieldName();
                    $('#projectForm').submit();
                }
            }
        });
    });
    
    function showResponse(responseText) {
        var obj = new com.hotent.form.ResultMessage(responseText);
        if (obj.isSuccess()) {
        	window.opener.location.reload(true);
			window.close();
        } else {
            $.ligerDialog.error(obj.getMessage(),"提示信息");
        }
        
    }
    
    
</script>
</body>
</html>