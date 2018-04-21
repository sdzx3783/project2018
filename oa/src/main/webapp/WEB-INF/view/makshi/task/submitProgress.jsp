<%@page language="java" pageEncoding="UTF-8"%>
<html>
<head>
    <title>提交进度</title>
    <%@include file="/commons/include/customForm.jsp" %>
    <%@include file="/commons/include/ueditor.jsp" %>
    <script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
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
        <h2>提交进度</h2>
    </div>
    <div class="oa-project-search">
        <button class="oa-button oa-button--primary oa-button--blue" id="dataFormSave">提交</button>
    </div>
    <div class="oa-project-content">
        <form id="projectForm" method="post" action="submitProgress.ht">
            <table class="oa-table">
            	<input type="hidden"  name="taskId" value="${id}"/>
            	<input type="hidden" id="pgs" name="pgs" value="${progress}"/>
                <tr>
                    <th>任务进度：</th>
                    <td>
                    	<div class="oa-input-wrap"><input class="oa-input" type="text" id="progress" name="progress"    validate="{required:true,number:true}" /></div>%
                    </td>
                </tr>
                <tr>
                    <th>任务反馈：</th>
                    <td class="editor-wrap">
						<textarea style="height:100px;width:300px;" id="feedback" name="feedback"></textarea>
					</td>
                </tr>
                 <tr>
                    <th>附件：</th>
                    <td>
                    	<input  ctltype="attachment" name="file" isdirectupload="0" right="w"  validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}" />
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
            	var pgs = $("#pgs").val();
            	var progress = $("#progress").val();
            	if(pgs==null || pgs=="" ||pgs==null || pgs==""){
            		pgs = 0;
            		progress=0;
            	}
            	console.log(pgs);
            	console.log(progress);
            	if(parseInt(pgs)>parseInt(progress)){
            		 $.ligerDialog.error("任务进度应大于当前进度","提示信息");
            		return false;
            	}
            	if(parseInt(progress)>100 || 0>parseInt(progress)){
           		 $.ligerDialog.error("任务进度为0-100","提示信息");
           		return false;
           	}
            	
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
    
    function checkProgress(){
    	
    }
    
    
</script>
</body>
</html>
