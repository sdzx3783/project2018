<%@page language="java" pageEncoding="UTF-8"%>
<html>
<head>
    <title>添加项目</title>
    <%@include file="/codegen/include/customForm.jsp" %>
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
        <h2>新建项目</h2>
    </div>
    <div class="oa-project-search">
        <button class="oa-button oa-button--primary oa-button--blue" onclick="window.location.href='${returnUrl}'">返回列表</button>
        <button class="oa-button oa-button--primary oa-button--blue" id="dataFormSave">保存</button>
    </div>
    <div class="oa-project-content">
        <form id="projectForm" method="post" action="save.ht">
            <table class="oa-table">
                <tr>
                    <th>项目分类：</th>
                    <td>${classifyLibrary.name}</td>
                </tr>
                <tr>
                    <th>项目名称：</th>
                    <td>
                    	<div class="oa-input-wrap"><input class="oa-input" type="text" name="name" validate="{required:true}" /></div>
                    </td>
                </tr>
                <tr>
                    <th>项目负责人：</th>
                    <td>
                    	<div class="oa-input-wrap">
                    		<input name="applicant" type="text" ctltype="selector" class="users oa-input oa-hidden-trigger" validate="{empty:false}" readonly="readonly" />
                    	</div>
                    	<button class="oa-button-label" onclick="chooseUser(this,'chargeDiv','applicantID','applicant','no');" type="button">选择用户</button>
                    </td>
                </tr>
                <tr>
                    <th>项目组成员：</th>
                    <td>
                    	<div class="oa-input-wrap">
                    		<input name="member" type="text" ctltype="selector" class="users oa-input oa-hidden-trigger" validate="{empty:false}" readonly="readonly" />
                    	</div>
                    	<button class="oa-button-label oa-trigger-hidden-button" type="button">选择用户</button>
                    </td>
                </tr>
                <tr>
                    <th>项目预计完成时间：</th>
                    <td>
                    	<div class="oa-input-wrap">
                    		<input type="text" id="expectendtime" name="expectendtime" class="date oa-input" validate="{date:true}" />
                    	</div>
                    </td>
                </tr>
            </table>
            <input type="hidden" id="classifylibraryid" name="classifylibraryid" value="${classifyLibraryId }" />
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
    	var from='${param["from"]}';
        var obj = new com.hotent.form.ResultMessage(responseText);
        if (obj.isSuccess()) {
        	if("treeView"==from){
        		 $.ligerDialog.success("保存成功",function(){
        			 $(".oa-input").val('');
        		 });
        	}else{
        		 $.ligerDialog.success("保存成功",function(){
        			 $(".oa-input").val('');
        		 });
	            /* $.ligerDialog.confirm(obj.getMessage()+",是否继续操作","提示信息", function(rtn) {
	                if(rtn){
	                    window.location.href = window.location.href;
	                }else{
	                    var classifyLibraryId = $("#classifylibraryid").val();
	                    window.location.href = "${ctx}/makshi/project/project/list.ht?classifylibraryid="+classifyLibraryId;
	                }
	            }); */
        	}
        } else {
            $.ligerDialog.error(obj.getMessage(),"提示信息");
        }
        
    }
    
    $(function(){
        $("input[name='applicantID']").on("change",function(){
            var userLenth = $(this).val();
            var len = userLenth.split(",").length;
            if(len>1){
                $.ligerDialog.warn("只能选择一个用户！");
              $(this).val(""); $("input[name='charger']").val("");
              return false;
            };
          });
    })
</script>
</body>
</html>
