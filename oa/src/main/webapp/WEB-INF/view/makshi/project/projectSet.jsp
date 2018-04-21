<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
    <title>配置项目</title>
    <%@include file="/codegen/include/customForm.jsp" %>
    <%@include file="/commons/include/ueditor.jsp" %>
    <script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
    <script type="text/javascript" src="${ctx}/js/lg/plugins/ligerTab.js" ></script>
    <script type="text/javascript">
        $(function() {
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
                    var from='${param['from']}';
                	if(rtn){
                    	if(window.location.href.indexOf("from")<0 && from && from=='myprolist'){
                    		window.location.href +="&from=myprolist";
                    	}else{
                    		 window.location.href = window.location.href;
                    	}
                    }else{
                    	if(from && from=='myprolist'){
                    		window.location.href="myProList.ht";
                    	}else{
	                        var classifyLibraryId = $("#classifylibraryid").val();
	                        window.location.href = "${ctx}/makshi/project/project/list.ht?classifylibraryid="+classifyLibraryId;
                    	}
                    }
                });
            } else {
                $.ligerDialog.error(obj.getMessage(),"提示信息");
            }
            
        }
    </script>
    <style>
        .oa-tab-nav{
            cursor: pointer;
            color: #106bdc;
            line-height: 32px;
            padding: 0 30px;
            background: #e5f4fe;
            float: left;
            margin-right: 5px;
            -webkit-transition: all .3s;
            -o-transition: all .3s;
            transition: all .3s;
        }
        .oa-tab-nav.active{
            background: #0f88eb;
            color: #fff;
        }
        .oa-tab-item{
            visibility: hidden;
        }
        .oa-tab-item.active{
            display: block;
        }
        .oa-pdv10{
            padding: 10px 0;
        }

        .oa-input-wrap{
            display: inline-block;
            width: 250px;
        }
        a.oa-hidden-trigger{
        	display: none;
        }
        dl{
            line-height: 25px;
            padding-bottom: 10px;
            border-bottom: 1px solid #eee;
        }
        dl dt{
            font-weight: bold;
            float: left;
        }
        dl dd{
            margin-left: 10px;
            color: #fff;
            padding: 0 10px;
            background: #ffa800;
            display: inline-block;
        }
        .oa-table{
            font-size: 14px;
        }
        .oa-table td{
            padding: 10px 20px;
        }
   </style>
</head>
<body>
    <div class="oa-project-title">
        <h2>配置项目</h2>
    </div>
    <div class="oa-project-search">
    	<c:choose>
    		<c:when test="${param['from']=='myprolist' }">
    			<a class="oa-button oa-button--primary oa-button--blue" href="myProList.ht">返回我的项目</a>
    		</c:when>
			<c:otherwise>
		        <a class="oa-button oa-button--primary oa-button--blue" href="list.ht?classifylibraryid=${project.classifylibraryid}">返回项目列表</a>
			</c:otherwise>
    	</c:choose>
        <button class="oa-button oa-button--primary oa-button--blue" id="dataFormSave">保存</button>
    </div>
    <div class="oa-project-content">
        <table class="oa-table">
            <tr>
                <th>项目名称：</th>
                <td>${project.name}</td>
            </tr>
            <tr>
                <th>项目负责人：</th>
                <td>${project.applicant}</td>
            </tr>
             <tr>
                <th>项目组成员：</th>
                <td>${project.member}</td>
            </tr>
            <tr>
                <th>项目ID：</th>
                <td>${project.id}</td>
            </tr>
        </table>
    </div>
    
    <div class="oa-project-content">
        <form id="projectForm" method="post" action="setTask.ht">
            <div class="oa-tab">
                <div class="oa-tab-head oa-clear">
                    <c:forEach items="${project.stages }" var="stage" varStatus="status">
                        <div class="oa-tab-nav">${stage.stagename}</div>
                    </c:forEach>
                </div>
                <div class="oa-tab-main">
                    <c:forEach items="${project.stages }" var="stage" varStatus="status">
                        <div class="oa-tab-item">
                            <c:forEach items="${stage.tasks }" var="task" varStatus="taskNum">
                                <div class="oa-pdv10">
                                    <input type="hidden" name="stages[${ status.index }].tasks[${ taskNum.index}].id" value="${task.id }" />
                                    
                                    <dl>
                                        <dt>任务${taskNum.index + 1}</dt>
                                        <dd>${task.taskname}</dd>
                                        <input type="hidden" name="stages[${ status.index }].tasks[${ taskNum.index}].taskname" value="${task.taskname}"/>
                                    </dl>

                                    <table class="oa-table">
                                        <tr>
                                            <th>经办人</th>
                                            <td>
                                   				<div class="oa-input-wrap">
                                              	  <input type="text" class="oa-input" name="stages[${ status.index }].tasks[${ taskNum.index}].charge" value="${task.charge }" readonly="readonly" />
                                                   <a href="javascript:;" class="link users oa-hidden-trigger"  name="stages[${ status.index }].tasks[${ taskNum.index}].charge" >选择</a>
                                                   <input type="hidden" name="stages[${ status.index }].tasks[${ taskNum.index}].chargeID" value="${task.chargeID }"/>
                                             	</div>
                                               	<button type="button" class="oa-trigger-hidden-button oa-button-label">选择用户</button>	
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>计划开始时间</th>
                                            <td>
                                                <div class="oa-input-wrap">
                                                    <input type="text" id="stages[${ status.index }].tasks[${ taskNum.index}].starttime" name="stages[${ status.index }].tasks[${ taskNum.index}].starttime" 
                                                    onClick="WdatePicker({maxDate:'#F{$dp.$D(\'stages[${ status.index }].tasks[${ taskNum.index}].endtime\');}'})" value="<fmt:formatDate value='${task.starttime }' pattern='yyyy-MM-dd'/>" class="oa-input Wdate" validate="{date:true}" />
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>计划完成时间</th>
                                            <td>
                                                <div class="oa-input-wrap">
                                                    <input type="text" id="stages[${ status.index }].tasks[${ taskNum.index}].endtime" name="stages[${ status.index }].tasks[${ taskNum.index}].endtime" 
                                                    onClick="WdatePicker({minDate:'#F{$dp.$D(\'stages[${ status.index }].tasks[${ taskNum.index}].starttime\');}'})" value="<fmt:formatDate value='${task.endtime }' pattern='yyyy-MM-dd'/>" class="oa-input Wdate" validate="{date:true}" />
                                                </div>
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                            </c:forEach>
                        </div>
                    </c:forEach>
                </div>
            </div>
            <input type="hidden" id="classifylibraryid" name="classifylibraryid" value="${project.classifylibraryid }" />
        </form>

    </div>

<script>


$(function(){

    $(".oa-trigger-hidden-button").on("click", function(){
        $(this).parent("td").find("a.oa-hidden-trigger").click();
    });


    var tabSwitch = (function(){
        var lastIndex = 0;

        //  初始化隐藏所有tab项目，这里受限于宏天自定义组件的限制，无奈用js
        //  来控制切换，后续剔除宏天自定义组件后，更换为css控制
        $(".oa-tab-item").css("visibility", "visible").hide();

        return function(){
            $('.oa-tab').on("click", ".oa-tab-nav", function(e){
                var index = $(this).index();
                $(".oa-tab").trigger("tab.switch", index);
            });

            $(".oa-tab").on("tab.switch", function(e, index){
                $(".oa-tab-nav").eq(lastIndex).removeClass('active');
                $(".oa-tab-nav").eq(index).addClass('active');
                $(".oa-tab-item").eq(lastIndex).hide();
                $(".oa-tab-item").eq(index).show();
                lastIndex = index;
            });

            $(".oa-tab").trigger("tab.switch", 0);
        };
    })();

    tabSwitch();
});



</script>
</body>
</html>
