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
        	var json=eval("("+responseText+")");
			if(json.result==1){
				var name=$("#name").val();
				//var isFolder=$("#isFolder").val();
				//var icon=$("#icon").val();
				if(json.operate=='add'){
					parent.addResource(json.id,name);
					$.ligerDialog.success('保存成功','提示信息');
				}
				else{
					parent.editResource(name);
					$.ligerDialog.success('编辑节点成功!','提示信息');
				}
			}
			else{
				$.ligerDialog.err('出错信息',"编辑节点失败",json.message);
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
  
  		<c:if test="${not empty classifyLib.id and classifyLib.isused==1 }">
              <button class="oa-button oa-button--primary oa-button--blue" type="button" onClick="return check(${classifyLib.id });">创建项目</button>
         </c:if>
         <c:if test="${not empty classifyLib.id}">
         	<button class="oa-button oa-button--primary oa-button--blue" type="button" onClick="set(${classifyLib.id })" class="oa-button-label">配置</button>
   		</c:if>
    </div>

    <div class="oa-main-wrap">
        <form id="classifylibForm" method="post" action="save.ht">
            <table class="oa-table" style="width: 100%;">
                 <tr>
                    <th>所属部门：</th>
                    <td>${classifyLib.org }
                        <div style="display:none" class="oa-input-wrap">
                            <input class="org oa-input oa-hidden-trigger" name="org" type="text" ctltype="selector" value="${classifyLib.org }" initValue="${classifyLib.orgID}" validate="{'required':true}" readonly="readonly" />
                        </div>
                        <button style="display:none" class="oa-button-label oa-trigger-hidden-button" type="button">选择部门</button>
                    </td>
                </tr>
                <tr>
                    <th>分类名称：</th>
                    <td>
                        <div class="oa-input-wrap">
                            <input class="oa-input" type="text" id="name" name="name" value="${classifyLib.name }" validate="{'required':true,'maxlength':150}">
                        </div>
                    </td>
                    <th>负责人：</th>
                    <td>
                        <div class="oa-input-wrap">
                            <input class="users oa-input oa-hidden-trigger" name="charger"  type="text" ctltype="selector" value="${classifyLib.charger }" initValue="${classifyLib.chargerID}" validate="{'required':true}" readonly="readonly" />
                        </div>
                        <button class="oa-button-label oa-trigger-hidden-button" type="button">选择用户</button>
                    </td>
                </tr>
                <tr>
                    <th>排序</th>
	                <td><div class="oa-input-wrap"><input class="oa-input"  type="text"  name="order" value="${classifyLib.order }" validate="{'required':true,'digits':true}"></div></td>
                    <th><input class="oa-input" type="hidden" name="supid" value="${classifyLib.supid }"></th>
                    <td></td>
                </tr>
                <c:if test="${not empty classifyLib.id }">
	                <tr>
	                    <th>项目数</th>
	                    <td>${classifyLib.pronum }</td>
	                    <th>阶段数</th>
	                    <td>${classifyLib.stagenum }</td>
	                </tr>
	                <tr>
	                    <th>任务数</th>
	                    <td>${classifyLib.tasknum }</td>
	                    <th></th>
	                    <td></td>
	                </tr>
                </c:if>
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
    function check(id){
        $.post("${ctx}/makshi/project/classifylib/check.ht",{
            'id':id
            },function(responseText){
            var obj = new com.hotent.form.ResultMessage(responseText);
            if (obj.isSuccess()) {
                 window.location.href="/makshi/project/project/toAdd.ht?id="+id+"&from=treeView";
                    
            } else {
                $.ligerDialog.error(obj.getMessage(),"提示信息");
                return false;
            }
   	 });
   }
    function set(id){
    	window.location.href="edit.ht?id="+id;
    }
</script>
</body>
</html>
