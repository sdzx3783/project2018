<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<%@include file="/codegen/include/customForm.jsp" %>
<%@include file="/commons/include/get.jsp" %>
<title>待办事宜</title>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/BpmTaskExeAssignDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/SelectUtil.js" ></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/UserInfo.js"></script>
<script type="text/javascript">
$(function(){
    $("[name='subject']").click(function(){
        $("#treeFresh",window.parent.document).trigger("click");
    });
});

function executeTask(taskId){
	var param=encodeURIComponent(decodeURIComponent($("#matterform").not($("input[name='query']")).serialize(),true));
    var url="${ctx}/platform/bpm/task/toStart.ht?taskId="+taskId+"&query="+param;
    var rtn = jQuery.openFullWindow(url);
    
}

//重启任务
function restartTask(taskId){
	var param=encodeURIComponent(decodeURIComponent($("#matterform").not($("input[name='query']")).serialize(),true));
    var url="${ctx}/platform/bpm/task/restartTask.ht?taskId="+taskId+"&query="+param;
    var rtn = jQuery.openFullWindow(url);
}


function reload(){
    location.href=location.href.getNewUrl();
    parent.globalType.loadGlobalTree();
}



function batOperator(operator){
    if(operator=="approve"){
        if ($("#btnApprove").attr('class').indexOf('disabled')>0){return false;}    
    }
    else{
        if ($("#btnBatDelegate").attr('class').indexOf('disabled')>0){return false;}
    }
    
    var aryId = $("input[type='checkbox'][disabled!='disabled'][class='pk']:checked");
    var len=aryId.length;
    if(len==0){
        $.ligerDialog.warn('请选择记录!','提示');
        return;
    }
    
    var taskIds=new Array();
    $("input[name='id']:checked").each(function(){
        taskIds.push($(this).val());
    });
    var ids=taskIds.join(",");
    
    
    var url=__ctx + "/platform/bpm/task/pendingMattersListBatchApprovalCfm.ht?taskIds="+ids;
    if(operator=="delegate"){
        url=__ctx + "/platform/bpm/task/delegateDialog.ht?taskIds="+ids;
    }
    
    var winArgs="dialogWidth=500px;dialogHeight=250px;help=0;status=0;scroll=0;center=1";
    url=url.getNewUrl();
    /* var rtn=window.showModalDialog(url,"",winArgs);
    if (rtn=='ok'){
        location.href=location.href.getNewUrl();
    } */
    
    /*KILLDIALOG*/
    DialogUtil.open({
        height:350,
        width: 450,
        title : '批量审批',
        url: url, 
        isResize: true,
        //自定义参数
        sucCall:function(rtn){
            if (rtn=='ok'){
                location.href=location.href.getNewUrl();
            }
        }
    });
}

/**
 * 跳转到批量审批界面
 */
function summaryBatch(){
    parent.location.href= "${ctx}/platform/bpm/bpmBatchApproval/manage.ht";
}

</script>
</head>
<body>

    <div id="oa_list_search" class="oa-pdb10 oa-mgh20">

        <div class="oa-accordion">
            <div class="oa-accordion__title active">查询条件</div>
            <div class="oa-accordion__content active">
                <form class="oa-clear" method="post" id="matterform" action="pendingMattersList.ht?porIndex=${porIndex}&tabIndex=${tabIndex}">
                    <div class="oa-fl oa-mgb10">
                        <div class="oa-label">申 请 人</div>
                        <div class="oa-input-wrap oa-mgl100">
                            <div class="oa-search-wrap">
                                <input type="text" class="oa-input" id="JS_oa_search"  value="${param['Q_creator_SL']}"  autocomplete="off">
                            </div>
                            <input type="hidden" id="creatorId"  class="oa-input"  name="Q_creator_SL"  value="${param['Q_creator_SL']}" />
                            <ul class="oa-search-list" id="JS_oa_search_list"></ul>
                        </div>
                    </div>
                    <div class="oa-fl oa-mgb10">
                        <div class="oa-label">申请部门</div>
                        <div class="oa-input-wrap oa-mgl100">
                            <input name="org"  type="text" ctltype="selector" class="oa-input org"  value="${param['org']}" initValue="${param['orgID']}" validate="{empty:false}" readonly="readonly" />
                        </div>
                    </div>
                    <div class="oa-fl oa-mgb10">
                        <div class="oa-label">流程名称</div>
                        <div class="oa-input-wrap oa-mgl100">
                            <input type="text" name="Q_subject_SUPL"  class="oa-input" value="${param['Q_subject_SUPL']}"/>
                        </div>
                    </div>
                    <div class="oa-fl oa-mgb10">
                        <div class="oa-label">任务名称</div>
                        <div class="oa-input-wrap oa-mgl100">
                            <input type="text" name="Q_name_SUPL"  class="oa-input"  value="${param['Q_name_SUPL']}"/>
                        </div>
                    </div>                                        
                    <div class="oa-fl oa-mgb10">
                        <div class="oa-label">阅读状态</div>
                        <div class="oa-input-wrap oa-mgl100">
                            <select name="Q_hasRead_S" class="oa-select">
                                <option value="">所有</option>
                                <option value="0" <c:if test="${param['Q_hasRead_S'] == '0'}">selected</c:if>>未读</option>
                                <option value="1" <c:if test="${param['Q_hasRead_S'] == '1'}">selected</c:if>>已读</option>
                            </select>
                        </div>
                    </div>
                    <div class="oa-fl oa-mgb10">
                        <div class="oa-label">待办类型</div>
                        <div class="oa-select-wrap oa-mgl100">
                            <select name="Q_type_S" class="oa-select">
                                <option value="">所有</option>
                                <option value="-1" <c:if test="${param['Q_type_S'] == '-1'}">selected</c:if>>待办</option>
                                <option value="21" <c:if test="${param['Q_type_S'] == '21'}">selected</c:if>>转办</option>
                                <option value="15" <c:if test="${param['Q_type_S'] == '15'}">selected</c:if>>沟通意见</option>
                                <option value="26" <c:if test="${param['Q_type_S'] == '26'}">selected</c:if>>代理</option>
                                <option value="38" <c:if test="${param['Q_type_S'] == '38'}">selected</c:if>>流转意见</option>
                            </select>
                        </div>
                    </div>
                    <div class="oa-fl oa-mgb10">
                        <div class="oa-label">状态</div>
                        <div class="oa-select-wrap oa-mgl100">
                            <select name="Q_status_N"   class="oa-select">
                                <option value="">所有</option>
                                <option value="1" <c:if test="${param['Q_status_N'] == '1'}">selected</c:if>>审批中</option>
                                <option value="5" <c:if test="${param['Q_status_N'] == '5'}">selected</c:if>>已撤销</option>
                                <option value="6" <c:if test="${param['Q_status_N'] == '6'}">selected</c:if>>已驳回</option>
                                <option value="7" <c:if test="${param['Q_status_N'] == '7'}">selected</c:if>>已追回</option>
                            </select>
                        </div>
                    </div>
                  <%--   <div class="oa-fl oa-mgb10">
                        <div class="oa-label">创建时间从</div>
                        <div class="oa-input-wrap oa-mgl100 oa-input-wrap--ib">
                            <input type="text" name="Q_beginCreateTime_DL"  class="datePicker oa-input"  datetype="1" value="${param['Q_beginCreateTime_DL']}"/>
                        </div>
                        <span>至</span>
                        <div class="oa-input-wrap oa-input-wrap--ib">
                            <input type="text" name="Q_endCreateTime_DG"  class="datePicker oa-input"  value="${param['Q_endCreateTime_DG']}" datetype="2"/>
                        </div>
                    </div> --%>
                    <c:if test="${hasGlobalFlowNo }">
                        <div class="oa-fl oa-mgb10">
                            <div class="oa-label">工单号</div>
                            <div class="oa-input-wrap oa-mgl100">
                                <input type="text" name="Q_globalFlowNo_SL"  class="oa-input"  value="${param['Q_globalFlowNo_SL']}"/>
                            </div>
                        </div>
                    </c:if>
					<input type="hidden" name="isexpand" value="${param['isexpand'] }">
                    <div class="oa-fl oa-mgb10 oa-mgh20">
                        <button type="button" onclick="tosubmit(1)"  class="oa-button oa-button--primary oa-button--blue">查询</button>
                        <button type="button" onclick="tosubmit(2)"  class="oa-button oa-button--primary oa-button--blue">导出</button>
                    </div>
                    <input type="hidden" name="nodePath" value="${param['nodePath']}" title="流程分类节点路径" />
                </form>
					<input type="hidden" name="query" >
            </div>
        </div>

    </div>

    <div class="oa-pdb20 oa-mgh20"> 
        <div id="oa_list_content" class="oa-table-scroll-wrap">
            <c:set var="checkAll">
                <input type="checkbox" id="chkall"/>
            </c:set>
            <display:table name="taskList" id="taskItem" requestURI="pendingMattersList.ht" sort="external" class="oa-table--default oa-table--nowrap">
                <display:column title="${checkAll}" media="html">
                        <c:choose>
                        <c:when test="${taskItem.description eq '15' || taskItem.description eq '38'}">
                            <input type="checkbox" class="pk" name="id"  disabled="disabled" value="${taskItem.id}">
                        </c:when>
                        <c:otherwise >
                            <input type="checkbox" class="pk" name="id"  value="${taskItem.id}">
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column  title="申请人" sortable="true" sortName="creator">
                    ${taskItem.creator}
                </display:column>
                <display:column title="流程名称"  sortable="true" sortName="subject">
                    <c:choose> 
                            <c:when test="${taskItem.description eq '36'}">
                                <a name="subject"  href="#${taskItem.id}" onclick="javascript:restdartTask(${taskItem.id})" style="color:${warningSet[taskItem.priority].color}" title='${taskItem.subject}'  <c:if test="${taskItem.hasRead == 0}"> class='message close-message'</c:if><c:if test="${taskItem.hasRead != 0}"> class='message open-message'</c:if> >${f:subString(taskItem.subject)}</a>
                            </c:when>
                        <c:otherwise>
                                <a name="subject"  href="#${taskItem.id}" onclick="javascript:executeTask(${taskItem.id})" style="color:${warningSet[taskItem.priority].color}" title='${taskItem.subject}' <c:if test="${taskItem.hasRead == 0}"> class='message close-message'</c:if><c:if test="${taskItem.hasRead != 0}"> class='message open-message'</c:if> >${f:subString(taskItem.subject)}</a>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column  title="申请部门">
                    ${taskItem.orgName}
                </display:column>
                <display:column property="processName" title="摘要"  sortable="true" sortName="processName"></display:column>
                <display:column title="任务名称" property="name"></display:column>                                                                                             
                <%--<display:column title="创建时间" sortable="true" sortName="create_time_">
                    <fmt:formatDate value="${taskItem.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                </display:column> --%>
                <display:column title="状态">
                    <c:choose>
                        <c:when test="${taskItem.taskStatus==1}">审批中</c:when>
                        <c:when test="${taskItem.taskStatus==5}">已撤销</c:when>
                        <c:when test="${taskItem.taskStatus==6}">已驳回</c:when>
                        <c:when test="${taskItem.taskStatus==7}">已追回</c:when>
                    </c:choose>
                </display:column>
                <display:column title="待办类型">
                    <c:choose>
                        <c:when test="${taskItem.description=='-1'}">
                            <span class="green">待办</span>
                        </c:when>
                        <c:when test="${taskItem.description eq '21' }" >
                            <span class="brown">转办</span>
                        </c:when>
                        <c:when test="${taskItem.description eq '15' }" >
                            <span class="orange">沟通意见</span>
                        </c:when>
                        <c:when test="${taskItem.description eq '26' }" >
                            <span class="brown">代理</span>
                        </c:when>
                        <c:when test="${taskItem.description eq '38' }" >
                            <span class="red">流转意见</span>
                        </c:when>
                    </c:choose>
                </display:column>            	
                <c:if test="${hasGlobalFlowNo }">
                    <display:column property="globalFlowNo" title="工单号" class="notscroll" sortable="true"  sortName="globalFlowNo"></display:column>
                </c:if> 
            </display:table>
        </div>
        <hotent:paging tableId="taskItem" />
    </div>

<script>
	function tosubmit(v){
		$("input[name='isexpand']").val(1);
		if(v==1)
		{
			$("#matterform").attr("action","pendingMattersList.ht")
			$("#matterform").submit();
		}else{
			$("#matterform").attr("action","pendingMattersExport.ht")
			$("#matterform").submit();
		}
	}

    $(function(){

    
        $('.oa-accordion').oaAccordion({collapse: true});       
        $(".oa-accordion__title").trigger('click');
        function isArray(obj){
            return Object.prototype.toString.call(obj) === '[object Array]';
        }

        function getPeopleList(name, callback){
            var url = '/platform/system/sysUser/getUserByName.ht?Q_fullname_SL=' + name + '&page=1&pageSize=15';

            $.get(url, function(data){
                if(data){
                    callback && callback(data);
                }else if(!isArray(data)){
                    alert('服务器返回人员列表数据出错(要求为数组类型)，请联系管理员');
                }
            });

        }

        function renderPeopleList(data){
            var html = '';
            for(var i = 0; i < data.length; i++){
                html += '<li data-id="'+ data[i].userid +'"><label>'+ data[i].username +'</label><span>'+ data[i].orgName +'</span></li>';
            }

            return html;
        }

        function viewPeopleList(html){
            var left = $('#JS_oa_search').offset().left;
            var top = $('#JS_oa_search').offset().top;
            var $list = $(html);

            $('#JS_oa_search_list').empty();
            $('#JS_oa_search_list').append($list);
        }

        $('body').on('input', '#JS_oa_search', function(e){
            var $this = $(this);
            var value = "";

            value = $.trim($this.val());

            $('#creatorId').val(value);
            if(!value || !value.length){
                $('#JS_oa_search_list').empty();
                $('#JS_oa_search_list').trigger('search.input');
                return;
            }

            getPeopleList(value, function(data){
                if(data){
                    var html = renderPeopleList(data);

                    viewPeopleList(html);

                    $('#JS_oa_search_list').trigger('search.input');

                }
            });

        });


        $('#JS_oa_search_list').on('search.input', function(e){
            if(!$(this).find('li').length){
                $(this).hide();
            }else{
                $(this).show();
            }
        });

        $('#JS_oa_search_list').on('click', 'li', function(e){
            var name = $(this).find('label').text();
            var id = $(this).attr('data-id');

            $('#JS_oa_search_list').empty();
            $('#JS_oa_search_list').trigger('search.input');

            $('#creatorId').val(name);
            $('#JS_oa_search').val(name);
        });

        $(document).on('click', function(e){
            $('#JS_oa_search_list').hide();
        });
        //处理查询条件刷新后的收缩问题
        function searchToggleState() {
        	var ft = $(".oa-accordion__title").hasClass("active");
        	$(".oa-accordion__title").on("click",function() {
        		ft = $(".oa-accordion__title").hasClass("active");
        		if(!ft) {
        			sessionStorage.setItem("st", true);
        			ft = !ft;
        		}else {
        			sessionStorage.removeItem("st");
        			ft = !ft;
        		}
        	});
        	
        	var st = sessionStorage.getItem("st");
        	if(st && !ft) $(".oa-accordion__title").trigger('click');      	
        };
        searchToggleState();
    });
</script>
</body>
</html>


