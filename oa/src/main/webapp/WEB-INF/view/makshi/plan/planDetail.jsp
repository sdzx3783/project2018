<%@page language="java" pageEncoding="UTF-8"%>
<html>
<head>
    <title>计划详情</title>
    <%@include file="/codegen/include/customForm.jsp" %>
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
        <h2>计划详情</h2>
    </div>
    <div class="oa-project-search">
        <button class="oa-button oa-button--primary oa-button--blue" onclick="window.location.href='/makshi/plan/plan/list.ht'">返回</button>
        <button class="oa-button oa-button--primary oa-button--blue" id="dataFormSave">保存</button>
    </div>
    <div class="oa-project-content">
        <form id="projectForm" method="post" action="save.ht">
            <table class="oa-table">
            	<input type="hidden"  name="id" value="${plan.id}"/>
                <tr>
                    <th>计划名称：</th>
                    <td>
                    	<fmt:formatDate value='${plan.ctime }' pattern='yyyy-MM-dd HH:mm:ss'/>
						<c:if test="${plan.cycle==1}">日计划</c:if>
						<c:if test="${plan.cycle==2}">周计划</c:if>
						<c:if test="${plan.cycle==3}">月计划</c:if>
						<c:if test="${plan.cycle==4}">季度计划</c:if>
						<c:if test="${plan.cycle==5}">半年计划</c:if>
						<c:if test="${plan.cycle==6}">年计划</c:if>
                    </td>
                </tr>
                <tr>
                    <th>日期范围：</th>
                    <td>
                    	<div id="range">${plan.dateRange}</div>
                    </td>
                </tr>
                <tr>
                    <th>工作计划与总结：</th>
                    <td class="editor-wrap">
                    <table>
                    <tr> 
					    <th>序号</th> 
					    <th>相关事项</th> 
					    <th>计划完成时间</th> 
					    <th>是否完成</th> 
					    <th>总结/未完成原因</th> 
					 </tr> 
					 <c:forEach items="${plan.planDetailList}" var="planDetail" varStatus="status">
					    <tr> 
					     <td class="tdNo">${status.index+1}</td> 
					     <td>${planDetail.title}</td> 
					     <td><fmt:formatDate value='${planDetail.endDate}' pattern='yyyy-MM-dd'/></td> 
					     <td>
					     	<c:if test="${planDetail.isend==0}">否</c:if>
					     	<c:if test="${planDetail.isend==1}">是</c:if>
					     </td>
					     <td>${planDetail.remark}</td> 
					    </tr>
					   </c:forEach> 
					   </table>
					</td>
                </tr>
                <tr>
                    <th>总结：</th>
                    <td>
                    	<textarea style="height:300px;width:400px;" id="summary" name="summary">${plan.summary}</textarea>
                    </td>
                </tr>
            </table>
            ----------------------------------------------
            <table class="oa-table">
            	<c:forEach items="${plan.replyList }" var="reply">
            		<tr>
            			<td>${reply.cuser }</td>
            			<td>${reply.reply }</td>
            			<td><fmt:formatDate value='${reply.ctime }' pattern='yyyy-MM-dd HH:mm:ss'/></td>
            		</tr>
            	</c:forEach>
            </table>
            
        </form>
    </div>


<script type="text/javascript">
    $(function() {
    	var messageEditor = new baidu.editor.ui.Editor({minFrameHeight:1300,initialFrameWidth: '80%',lang:'zh_cn'});
		messageEditor.render("content");
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
            $.ligerDialog.confirm(obj.getMessage()+",是否继续操作","提示信息", function(rtn) {
                if(rtn){
                    window.location.href = window.location.href;
                }else{
                    window.location.href = "/makshi/plan/plan/list.ht";
                }
            });
        } else {
            $.ligerDialog.error(obj.getMessage(),"提示信息");
        }
        
    }
    
</script>
</body>
</html>
