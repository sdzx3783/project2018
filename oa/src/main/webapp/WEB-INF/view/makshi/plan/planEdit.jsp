<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
    <title>添加计划</title>
    <%@include file="/codegen/include/customForm.jsp" %>
    <%@include file="/commons/include/ueditor.jsp" %>
    <script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/form/AttachMent.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/form/TablePermission.js"></script>
</head>
<body>

        <div class="oa-mg20">
            <button class="oa-button oa-button--primary oa-button--blue" onclick="window.location.href='/makshi/plan/plan/list.ht'">返回</button>
            <button class="oa-button oa-button--primary oa-button--blue" id="dataFormSave">保存</button>
            <button class="oa-button oa-button--primary oa-button--blue" id="dataFormPub">发布</button>
        </div>
    
        <div class="oa-mg20">
            

            <form id="projectForm" method="post" action="save.ht">
				<h3 class="oa-h3">工作计划</h3>
                <table class="oa-table--default">
                    <input type="hidden"  name="id" value="${plan.id}"/>
                    <input type="hidden" id="ispub"  name="ispub" value="${plan.ispub}"/>
                    <tr>
                        <th style="width: 200px">计划周期：</th>
                        <td>
                            <label><input type="radio" name="cycle" value="1" onchange="calc()" <c:if test="${plan.cycle==1}">checked='checked'</c:if> />日计划</label>
                            <label><input type="radio" name="cycle" value="2" onchange="calc()" <c:if test="${plan.cycle==2}">checked='checked'</c:if> />周计划</label>
                            <label><input type="radio" name="cycle" value="3" onchange="calc()" <c:if test="${plan.cycle==3}">checked='checked'</c:if>/>月计划</label>
                            <label><input type="radio" name="cycle" value="4" onchange="calc()" <c:if test="${plan.cycle==4}">checked='checked'</c:if>/>季度计划</label>
                            <label><input type="radio" name="cycle" value="5" onchange="calc()" <c:if test="${plan.cycle==5}">checked='checked'</c:if>/>半年计划</label>
                            <label><input type="radio" name="cycle" value="6" onchange="calc()" <c:if test="${plan.cycle==6}">checked='checked'</c:if>/>年计划</label>
                        </td>
                    </tr>
                    <tr>
                        <th style="width: 200px">日期：</th>
                        <td>
                            <div class="oa-input-wrap oa-input-wrap--ib">
                                <input type="text" id="date" name="date" class="oa-input Wdate" value="<fmt:formatDate value='${plan.date}' pattern='yyyy-MM-dd'/>" validate="{date:true,required:true}" onClick="WdatePicker({onpicked:calc})" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th style="width: 200px">日期范围：</th>
                        <td>
                            <div id="range">${plan.dateRange}</div>
                            <input type="hidden" id="dateRange" name="dateRange" value="${plan.dateRange}"/>
                        </td>
                    </tr>
                    <tr>
                        <th style="width: 200px">附件：</th>
                        <td>
                            <input ctltype="attachment" name="file" isdirectupload="0" right="w" value='${plan.file}' validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}" />
                        </td>
                    </tr>
                </table>
                
                
                <div class="oa-pdv20" type="subtable" tablename="planDetail" tabledesc="具体计划" show="true" parser="rowmodeedit" id="planDetail" formtype="edit">
                    <table class="oa-table--default" border="0" cellpadding="2" cellspacing="0">
                            <caption style="text-align: left;">
                                <button type="button" class="oa-button-label add">添加</button>
                            </caption>

                            <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>相关事项</th>
                                    <th>计划完成时间</th>
                                    <th>是否完成</th>
                                    <th>总结/未完成原因</th>
                                </tr>
                            </thead>
                            
                            <c:forEach items="${plan.planDetailList}" var="planDetail" varStatus="status">
                                <tr type="subdata">
                                    <input type="hidden" name="s:planDetail:planId" value="${plan.id}" isflag="tableflag" />
                                    <input type="hidden" name="s:planDetail:id" value="${planDetail.id}" isflag="tableflag" />
                                    <td class="tdNo">${status.index+1}</td>
                                    <td><input type="text" name="s:planDetail:title" value="${planDetail.title}" class="inputText" validate="{required:true}" isflag="tableflag" /></td>
                                    <td><input type="text" name="s:planDetail:endDate" value="<fmt:formatDate value='${planDetail.endDate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" isflag="tableflag" /></td>
                                    <td>
                                        <select name="s:planDetail:isend" isflag="tableflag">
                                            <option <c:if test="${planDetail.isend==0}">selected</c:if> value="0">否</option>
                                            <option <c:if test="${planDetail.isend==1}">selected</c:if> value="1">是</option>
                                        </select>
                                    </td>
                                    <td><input type="text" name="s:planDetail:remark" value="${planDetail.remark}" class="inputText" isflag="tableflag"  /></td>
                                </tr>
                            </c:forEach>

                            <tr type="append" >
                                <input type="hidden" name="s:planDetail:planId" value="${plan.id}" isflag="tableflag"  />
                                <input type="hidden" name="s:planDetail:id" value="${planDetail.id}" isflag="tableflag" />
                                <td class="tdNo"><br /></td>
                                <td><input type="text" name="s:planDetail:title" value="${planDetail.title}" class="inputText" validate="{required:true}" isflag="tableflag" /></td>
                                <td><input type="text" name="s:planDetail:endDate" value="<fmt:formatDate value='${planDetail.endDate}' pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}"  isflag="tableflag"/></td>
                                <td>
                                    <select name="s:planDetail:isend" isflag="tableflag">
                                        <option <c:if test="${planDetail.isend==0}">selected</c:if> value="0">否</option>
                                        <option <c:if test="${planDetail.isend==1}">selected</c:if> value="1">是</option>
                                    </select>
                                </td>
                                <td><input type="text" name="s:planDetail:remark" value="${planDetail.remark}" class="inputText" isflag="tableflag"  /></td>
                            </tr>
                    </table>
                </div>
                
				<h3 class="oa-h3">工作总结</h3>
				<table class="oa-table--default">
					<tr>
                        <th style="width: 200px">总结：</th>
                        <td>
                            <div class="oa-textarea-wrap">
                                <textarea class="oa-textarea" id="summary" name="summary">${plan.summary}</textarea>
                            </div>
                        </td>
                    </tr>
				</table>
                

                
                
                <h3 class="oa-h3">上级评论</h3>
                <table class="oa-table--default">
                <thead>
                    <tr>
                        <th>序号</th>
                        <th>评价人</th>
                        <th>评价内容</th>
                        <th>评价时间</th>
                    </tr>
                </thead>
                <c:forEach items="${plan.replyList }" var="reply" varStatus="a">
                    <tr>
                        <td>${a.index+1}</td>
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
                        var cycle=$("input[name='cycle']:checked").val();
                        if(cycle==null){
                            $.ligerDialog.error("请选择计划周期","提示信息");
                            return;
                        }
                        var ispub =$("#ispub").val();
                        if(ispub==null || ispub ==''){
                            $("#ispub").val(0);
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
                        frm.sortList();
                        $('#projectForm').submit();
                    });
                }else{
                    frm.handleFieldName();
                    frm.sortList();
                    $('#projectForm').submit();
                }
    }
});


                $("#dataFormPub").click(function() {

                    frm.ajaxForm(options);
                    if(frm.valid()){
                        var cycle=$("input[name='cycle']:checked").val();
                        if(cycle==null){
                            $.ligerDialog.error("请选择计划周期","提示信息");
                            return;
                        }

                        $("#ispub").val(1);
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
                frm.sortList();
                $('#projectForm').submit();
            });
        }else{
            frm.handleFieldName();
            frm.sortList();
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

        //计算范围
        function calc(){
            var cycle=$("input[name='cycle']:checked").val();
            var dateStr = $("#date").val();
            if(dateStr==null || dateStr =="" || cycle==null ||cycle ==""){
                $("#rang").html("");
                $("#dateRange").val("");
                return;
            }
            var date = new Date(dateStr);
            switch(cycle){
                    case "1"://日
                    date.setDate(date.getDate());
                    break;
                    case "2"://周
                    date.setDate(date.getDate()+6);
                    break;
                    case "3"://月
                    date.setMonth(date.getMonth()+1);
                    date.setDate(date.getDate()-1);
                    break;
                    case "4"://季度
                    date.setMonth(date.getMonth()+3);
                    date.setDate(date.getDate()-1);
                    break;
                    case "5"://半年
                    date.setMonth(date.getMonth()+6);
                    date.setDate(date.getDate()-1);
                    break;
                    case "6"://一年
                    date.setMonth(date.getMonth()+12);
                    date.setDate(date.getDate()-1);
                    break;
                }
                var dateRange = dateStr+"__"+date.format("yyyy-MM-dd");
                $("#range").html(dateRange);
                $("#dateRange").val(dateRange);
                
                
            }
        
        </script>
</body>
</html>