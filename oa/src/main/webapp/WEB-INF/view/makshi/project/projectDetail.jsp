<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>项目详情</title>
<%@include file="/codegen/include/customForm.jsp"%>
<%@include file="/commons/include/ueditor.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerTab.js"></script>
<script type="text/javascript"
    src="${ctx}/js/hotent/platform/form/AttachMent.js"></script>
<script type="text/javascript"
    src="${ctx}/js/hotent/platform/form/TablePermission.js"></script>
<script type="text/javascript">
        $(function() {
            var options={};
            if(showResponse){
                options.success=showResponse;
            }
            var frm=$('#projectForm').form();
            $("a.save").click(function() {
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
                        var classifyLibraryId = $("#classifylibraryid").val();
                        window.location.href = "${ctx}/makshi/project/project/list.ht?classifylibraryid="+classifyLibraryId;
                    }
                });
            } else {
                $.ligerDialog.error(obj.getMessage(),"提示信息");
            }
            
        }
         
         function end(taskId) {
                if(taskId=='' || taskId ==null){
                    return ;
                }

                $.post("/makshi/project/project/endTask.ht", {
                    'id' : taskId,

                }, function(data) {
                    showResponse(data);
                });

            }

         
         function addField(taskId){
            if(taskId=='' || taskId==null) return;
            window.open('toTask.ht?taskId='+taskId, 'addaddField','height=500, width=900, top=200, left=300, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=n o, status=no');
        }
         
         
         function addFile(taskId){
            if(taskId=='' || taskId==null) return;
            var files = $("[name='files']").val();
            $.post("${ctx}/makshi/project/project/fileSave.ht",{
                    id:taskId,
                    files:files
                    },function(responseText){
                    var obj = new com.hotent.form.ResultMessage(responseText);
                    if (obj.isSuccess()) {
                        //$.ligerDialog.success("上传成功！");
                        location.reload();
                            
                    } else {
                        $.ligerDialog.error(obj.getMessage(),"提示信息");
                    }
            });
         }
         
         function collection(projectId){
                var url='collection.ht';
                $.post(url,{'id':projectId},function(data){
                    if(data=='ok'){
                        //收藏成功
                        $("#collect").attr("class","collec");
                    }else if(data=="cancel"){
                        //取消收藏
                        $("#collect").attr("class","no-collec");
                    }else{
                        $.ligerDialog.alert(data,"提示");
                    }
                });
            }
    </script>
    
    <style type="text/css">
        /**
         *  关注样式
         */
        .collec .collect {
            display: none;
        }
        
        .no-collec .uncollect {
            display: none;
        }

        /**
         * tab样式
         */
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
            display: none;
        }
        .oa-tab-item.active{
            display: block;
        }
        .oa-pdv10{
            padding: 10px 0;
        }
		.oa-tab dl{
            line-height: 25px;
            padding-bottom: 10px;
            border-bottom: 1px solid #eee;
        }
        .oa-tab dl dt{
            font-weight: bold;
            float: left;
        }
        .oa-tab dl dd{
        	font-size: 12px;
            margin-left: 10px;
            color: #fff;
            padding: 0 10px;
            background: #ffa800;
            display: inline-block;
        }

    
        .oa-table td{
            padding: 10px 20px;
        }
        .oa-table th{
            padding: 10px 0;
        }
        .oa-list-wrap dt{
            float: left;
            padding: 10px;
        }
        .oa-list-wrap dd{
            padding: 10px;
            margin-left: 120px;
        }
        .oa-list-wrap .oa-list-line{
            float: left;
            width: 50%;
        }
    </style>
</head>
<body class="oa-mw-page">
    <div class="oa-project-title">
        <h2>项目详情</h2>
    </div>
    <div class="oa-top-wrap">
        <%-- <a class="oa-button oa-button--primary oa-button--blue" href="list.ht?classifylibraryid=${project.classifylibraryid}">返回项目列表</a> --%>
        <a class="oa-button oa-button--primary oa-button--blue" href="javascript:;" onclick="history.go(-1)">返回项目列表</a>
        <button class="oa-button oa-button--primary oa-button--blue"  onclick="collection(${project.id})" >
            <a id="collect"<c:if test="${!collect}">class="no-collec"</c:if> <c:if test="${collect}">class="collec"</c:if> >
                <p class="uncollect">取消关注</p>
                <p class="collect">关注</p>
            </a>
        </button>
    </div>
    <div class="oa-main-wrap">
        <div class="oa-list-wrap oa-clear">
        	<div class="oa-list-line">
                <dl>
                    <dt>项目ID：</dt>
                    <dd>${project.id}</dd>
                </dl>
            </div>
            <div class="oa-list-line">
                <dl>
                    <dt>项目名称：</dt>
                    <dd>${project.name}</dd>
                </dl>
            </div>
            <div class="oa-list-line">
                <dl>
                    <dt>项目负责人：</dt>
                    <dd>${project.applicant}</dd>
                </dl>
            </div>
            <div class="oa-list-line">
                <dl>
                    <dt>项目组成员：</dt>
                    <dd>${project.member}</dd>
                </dl>
            </div>
            <div class="oa-list-line">
                <dl>
                    <dt>项目开工时间：</dt>
                    <dd><fmt:formatDate value='${project.expectstarttime}' pattern='yyyy-MM-dd'/></dd>
                </dl>
            </div>
            <div class="oa-list-line">
                <dl>
                    <dt>项目截止时间：</dt>
                    <dd><fmt:formatDate value='${project.expectendtime}' pattern='yyyy-MM-dd'/></dd>
                </dl>
            </div>
            <div class="oa-list-line">
                <dl>
                    <dt>项目状态：</dt>
                    <dd>
                        <c:if test="${project.status==0}">未开工</c:if>
                        <c:if test="${project.status==1}">在建</c:if>
                        <c:if test="${project.status==2}">停工</c:if>
                        <c:if test="${project.status==3}">已完工</c:if>
                    </dd>
                </dl>
            </div>
            <div class="oa-list-line">
                <dl>
                    <dt>项目责任人：</dt>
                    <dd>${project.charger}</dd>
                </dl>
            </div>
            <div class="oa-list-line">
                <dl>
                    <dt>合同编码：</dt>
                    <dd>
                    <c:choose>
                    	<c:when test="${not empty project.contractId}"><a href="/makshi/contract/contractinfo/get.ht?id=${project.contractId}&projectId=${project.id}">${project.contractnum}</a></c:when>
                    	<c:otherwise>${project.contractnum}</c:otherwise>
                    </c:choose>
                    </dd>
                </dl>
            </div>
            <div class="oa-list-line">
                <dl>
                    <dt>合同名称：</dt>
                    <dd>${project.contractname}</dd>
                </dl>
            </div>
            <div class="oa-list-line">
                <dl>
                    <dt>合同金额<span style="color: red;">(万元)</span>：</dt>
                    <dd>${project.contractmoney}</dd>
                </dl>
            </div>
            <div class="oa-list-line">
                <dl>
                    <dt>法定代表人：</dt>
                    <dd>${project.legalperson}</dd>
                </dl>
            </div>
            <div class="oa-list-line">
                <dl>
                    <dt>联系电话：</dt>
                    <dd>${project.tel}</dd>
                </dl>
            </div>
            <div class="oa-list-line">
                <dl>
                    <dt>审批文号：</dt>
                    <dd>${project.approvalnumber}</dd>
                </dl>
            </div>
            <div class="oa-list-line">
                <dl>
                    <dt>审批时间：</dt>
                    <dd><fmt:formatDate value='${project.approvaltime}' pattern='yyyy-MM-dd'/></dd>
                </dl>
            </div>
            <div class="oa-list-line">
                <dl>
                    <dt>建设单位：</dt>
                    <dd>${project.procompany}</dd>
                </dl>
            </div>
            <div class="oa-list-line">
                <dl>
                    <dt>备注：</dt>
                    <dd>${project.remark}</dd>
                </dl>
            </div>
            <div class="oa-list-line">
                <dl>
                    <dt>建设单位联系人：</dt>
                    <dd>${project.procompanyLinker}</dd>
                </dl>
            </div>
            <div class="oa-list-line">
                <dl>
                    <dt>建设单位联系人电话：</dt>
                    <dd>${project.procompanyLinkerPhone}</dd>
                </dl>
            </div>
            <div class="oa-list-line">
                <dl>
                    <dt>项目年份：</dt>
                    <dd>${project.proYear}</dd>
                </dl>
            </div>
            <c:forEach items="${project.fieldArr}" var="js">
                <div class="oa-list-line">
                    <dl>
                        <dt>${js.fieldname}:</dt>
                        <dd>${js.fieldvalue}</dd>
                    </dl>
                </div>
            </c:forEach>
        </div>
    </div>
    
	<div class="oa-project-content">
		<div class="oa-tab">
			<div class="oa-tab-head oa-clear">
				<c:forEach items="${project.stages}" var="stage" varStatus="status">
					<div class="oa-tab-nav">
						${stage.stagename}
					</div>
				</c:forEach>
			</div>
			<div class="oa-tab-main">
				<c:forEach items="${project.stages}" var="stage" varStatus="status">
					<div class="oa-tab-item">
						<c:forEach items="${stage.tasks }" var="task" varStatus="taskNum">
							<div class="oa-pdv10">
				                <input type="hidden" name="stages[${ status.index }].tasks[${ taskNum.index}].id" value="${task.id }" />
								<dl>
									<dt>任务${ taskNum.index + 1}</dt>
									<dd>${task.taskname }</dd>
								</dl>

								<table class="oa-table">
									<tr>
										<th>负责人:</th>
										<td>${task.charge }</td>
									</tr>
									<tr>
										<th>截止时间:</th>
										<td>
											<fmt:formatDate value='${task.endtime }' pattern='yyyy-MM-dd' />
										</td>
									</tr>
									<tr>
										<th>任务类型：</th>
										<td>
											<c:if test="${task.tasktype==1 }">表单填写类</c:if>
				                            <c:if test="${task.tasktype==2 }">资料上传类</c:if>
				                            <c:if test="${task.tasktype==3 }">流程审批类</c:if>
										</td>
									</tr>
									<tr>
										<th>计划开始时间：</th>
										<td>
											<fmt:formatDate value='${task.starttime }' pattern='yyyy-MM-dd' />
										</td>
									</tr>
									<tr>
										<th>计划完成时间：</th>
										<td>
											<fmt:formatDate value='${task.endtime }' pattern='yyyy-MM-dd' />
										</td>
									</tr>
									<tr>
										<th>完成数量：</th>
										<td>
											${task.endcount}
										</td>
									</tr>
									<tr>
										<th>需要审批：</th>
										<td>
											<c:if test="${task.isexamine}">是</c:if>
				                            <c:if test="${!task.isexamine}">否</c:if>
										</td>
									</tr>
									<tr>
										<th>操作：</th>
										<td>
											<a class="oa-button-label" href="taskDetail.ht?id=${task.id}">详情</a>
										</td>
									</tr>
								</table>
							</div>
			            </c:forEach>
					</div>
				</c:forEach>
			</div>
		</div>
    </div>

<script>
	$(function(){

	    var tabSwitch = (function(){
	        var lastIndex = 0;

	        return function(){
	            $('.oa-tab').on("click", ".oa-tab-nav", function(e){
	                var index = $(this).index();
	                $(".oa-tab").trigger("tab.switch", index);
	            });

	            $(".oa-tab").on("tab.switch", function(e, index){
	                $(".oa-tab-nav").eq(lastIndex).removeClass('active');
	                $(".oa-tab-nav").eq(index).addClass('active');
	                $(".oa-tab-item").eq(lastIndex).removeClass('active');
	                $(".oa-tab-item").eq(index).addClass('active');
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
