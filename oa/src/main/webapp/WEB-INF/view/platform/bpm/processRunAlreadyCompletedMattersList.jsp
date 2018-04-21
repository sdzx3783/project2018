<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/commons/include/get.jsp" %>
<title>已办办结事宜</title>
<script type="text/javascript" src="${ctx}/js/hotent/platform/tabOperator.js" ></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/FlowRightDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/FlowUtil.js" ></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/SelectUtil.js" ></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/UserInfo.js"></script>
<script type="text/javascript">
    function showDetail(obj){
        var url = $(obj).attr("action");
        jQuery.openFullWindow(url);
    };
    
</script>
</head>
<body>

    <div id="oa_list_search" class="oa-pdb10 oa-mgh20">
        <div class="oa-accordion">
            <div class="oa-accordion__title">查询条件</div>
            <div class="oa-accordion__content">
                 
                <form id="searchForm" method="post" action="alreadyCompletedMattersList.ht" class="oa-clear">
                    <%-- <div class="oa-fl oa-mgb10">
                        <div class="oa-label">标识</div>
                        <div class="oa-select-wrap oa-mgl100">
                            <select class="oa-select" name="Q_flag_SN">
                                <option value="">所有</option>
                                <option value="1" <c:if test="${param['Q_flag_SN'] == '1'}">selected</c:if>>已办事宜</option>
                                <option value="2" <c:if test="${param['Q_flag_SN'] == '2'}">selected</c:if>>办结事宜</option>
                            </select>
                        </div>
                    </div> --%>
                   <%--  <div class="oa-fl oa-mgb10">
                        <div class="oa-label">请求标题</div>
                        <div class="oa-input-wrap oa-mgl100">
                            <input  type="text" name="Q_subject_SUPL"  class="oa-input" value="${param['Q_subject_SUPL']}" />
                        </div>
                    </div> --%>
                    <div class="oa-fl oa-mgb10">
                        <div class="oa-label">申 请 人</div>
                        <div class="oa-input-wrap oa-mgl100">
                            <div class="oa-search-wrap">
                                <input type="text" class="oa-input" id="JS_oa_search" value="${param['Q_creator_SL']}" autocomplete="off">
                            </div>
                            <input type="hidden" id="creatorId" name="Q_creator_SL"  value="${param['Q_creator_SL']}" />
                            <ul class="oa-search-list" id="JS_oa_search_list"></ul>
                        </div>
                    </div>
                    <div class="oa-fl oa-mgb10">
                        <div class="oa-label">流程名称</div>
                        <div class="oa-input-wrap oa-mgl100">
                            <input type="text" name="Q_processName_SUPL"  class="oa-input" value="${param['Q_processName_SUPL']}" />
                        </div>
                    </div>
                    

                    <!-- <div class="oa-fl oa-mgb10">
                        <div class="oa-label">创 建 人</div>
                        <div class="oa-input-wrap oa-mgl100">
                            <input type="hidden" id="creatorId" name="Q_creatorId_L"  value="${param['Q_creatorId_L']}" />
                            <input type="text"   id="creator" name="Q_creator_SL" class="oa-input"  style="width: 110px;" value="${param['Q_creator_SL']}" onclick="selectUser('creatorId','creator');" readonly="readonly"  />
                            <button type="button" class="oa-button-label" onclick="selectUser('creatorId','creator');">选择</button>
                        </div>
                    </div> -->
                    <div class="oa-fl oa-mgb10">
                        <div class="oa-label">状态</div>
                        <div class="oa-select-wrap oa-mgl100">
                            <select class="oa-select" name="Q_status_SN">
                                <option value="">所有</option>
                                <option value="1" <c:if test="${param['Q_status_SN']==1}">selected</c:if>>审批中</option>
                                <option value="2" <c:if test="${param['Q_status_SN']==2}">selected</c:if>>已归档</option>
                                <option value="3" <c:if test="${param['Q_status_SN']==3}">selected</c:if>>已终止</option>
                                <option value="5" <c:if test="${param['Q_status_SN']==5}">selected</c:if>>已撤销</option>
                                <option value="6" <c:if test="${param['Q_status_SN']==6}">selected</c:if>>已驳回</option>
                                <option value="7" <c:if test="${param['Q_status_SN']==6}">selected</c:if>>已追回</option>
                                <option value="11" <c:if test="${param['Q_status_SN']==11}">selected</c:if>>已废除</option>
                            </select>
                        </div>
                    </div>                   
                    <div class="oa-fl oa-mgb10">
                        <div class="oa-label">创建时间从</div>
                        <div class="oa-input-wrap oa-mgl100 oa-input-wrap--ib">
                            <input  name="Q_begincreatetime_DL"  class="oa-input datePicker" datetype="1"  value="${param['Q_begincreatetime_DL']}" />
                        </div>
                        <span>至</span>
                        <div class="oa-input-wrap oa-input-wrap--ib">
                            <input name="Q_endcreatetime_DG" class="oa-input datePicker" datetype="2" value="${param['Q_endcreatetime_DG']}"/>
                        </div>
                    </div>
                    <c:if test="${hasGlobalFlowNo }">
                        <div class="oa-fl oa-mgb10">
                            <div class="oa-label">工单号</div>
                            <div class="oa-input-wrap oa-mgl100">
                                <input type="text" name="Q_globalFlowNo_SL" class="oa-input" value="${param['Q_globalFlowNo_SL']}"/>
                            </div>
                        </div>
                    </c:if>
            		<input type="hidden" name="isexpand" value="${param['isexpand'] }">
                    <div class="oa-fl oa-mgb10 oa-mgh20">
                        <button class="oa-button oa-button--primary oa-button--blue" type="button" onclick="tosubmit(1)" >查询</button>
                        <button type="button" class="oa-button oa-button--primary oa-button--blue" onclick="$.clearQueryForm()">重置</button>
                        <button type="button" class="oa-button oa-button--primary oa-button--blue" onclick="tosubmit(2)">导出</button>
                    </div>

                    <input type="hidden" name="nodePath" value="${param['nodePath']}" title="流程分类节点路径" />
                </form>
            </div>
        </div>
    </div>

    <div class="oa-pdb20 oa-mgh20">
        <div id="oa_list_content" class="oa-table-scroll-wrap">
            <c:set var="checkAll">
                <input type="checkbox" id="chkall"/>
            </c:set>
            <display:table name="processRunList" id="processRunItem" requestURI="alreadyCompletedMattersList.ht" sort="external" class="oa-table--default oa-table--nowrap">
                <display:column titleKey="序号" media="html">
                    ${processRunItem_rowNum}
                </display:column>
                <display:column titleKey="申请人" sortable="true" sortName="creator">
                    ${processRunItem.creator}
                </display:column>
                <display:column property="subject" titleKey="流程名称" sortable="true" sortName="subject"></display:column>
                <display:column property="processName" title="摘要"  sortable="true" sortName="processName"></display:column>
                
                <display:column titleKey="状态" sortable="true" sortName="status">
                    <f:processStatus status="${processRunItem.status}"></f:processStatus>
                </display:column>
                <display:column  titleKey="创建时间" sortable="true" sortName="createtime">
                    <fmt:formatDate value="${processRunItem.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                </display:column>
                <display:column titleKey="持续时间">                    
                    <c:choose>
                        <c:when test="${processRunItem.status==10 or processRunItem.status ==2 or processRunItem.status ==3}">
                            ${f:getTime(processRunItem.duration)}
                        </c:when>
                        <c:otherwise>
                            ${f:getDurationTime(processRunItem.createtime)} 
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <c:if test="${hasGlobalFlowNo }">
                    <%-- <display:column property="globalFlowNo" class="notscroll" title="工单号" sortable="true"  sortName="globalFlowNo"></display:column> --%>
	                 <display:column  titleKey="工单号" sortable="true" sortName="globalFlowNo">
	                    <a name="globalFlowNo" onclick="showDetail(this)" href="#${processRunItem.runId}"  action="${ctx}/platform/bpm/processRun/info.ht?runId=${processRunItem.runId}&prePage=myFinishedTask" title='${processRunItem.globalFlowNo}' >${processRunItem.globalFlowNo}</a>
	                </display:column>
                </c:if>
              <%--   <display:column  titleKey="请求标题" sortable="true" sortName="subject">
                    <a name="processDetail" onclick="showDetail(this)" href="#${processRunItem.runId}"  action="info.ht?runId=${processRunItem.runId}&prePage=myFinishedTask" title='${processRunItem.subject}' >${processRunItem.subject}</a>
                </display:column> --%>                                                                                 
            </display:table>
        </div>
        <hotent:paging tableId="processRunItem" />
    </div>

<script>
	function tosubmit(v){
		$("input[name='isexpand']").val(1);
		if(v==1)
		{
			$("#searchForm").attr("action","alreadyCompletedMattersList.ht")
			$("#searchForm").submit();
		}else{
			$("#searchForm").attr("action","alreadyCompletedMattersExport.ht")
			$("#searchForm").submit();
		}
	}
	
    $(function(){
        $('.oa-accordion').oaAccordion({});
        
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
        })
        //处理查询条件刷新后的收缩问题
        function searchToggleState() {
        	var ft = $(".oa-accordion__title").hasClass("active");
        	$(".oa-accordion__title").on("click",function() {
        		ft = $(".oa-accordion__title").hasClass("active")
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


