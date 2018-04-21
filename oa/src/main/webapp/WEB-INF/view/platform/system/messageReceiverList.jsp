
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
    <title>消息接收者管理</title>
    <%@include file="/commons/include/get.jsp" %>
    <script type="text/javascript">
    function mark(t){
        var ids = "";
        if(t){
            var tr = $(t).parents("tr"),
                pk = $("input.pk",tr).val();

            if(!pk)return;
            ids = pk;
        }
        else{
            var idArr = [];

            $("input.pk").each(function(){
                var me = $(this),
                    state = me.attr("checked");
                    
                if(state&&me.closest("tr").find('td').eq(5).text().indexOf('未读消息')>=0)
                    idArr.push(me.val());
            });
            if(idArr.length==0){
                $.ligerDialog.warn('请选择至少一条未读记录!','提示');
                return;
            }
            ids = idArr.join(',');
        }
        var url = __ctx + '/platform/system/messageReceiver/mark.ht';
        var params={ids:ids};
        $.post(url,params,function(d){
            if(t){
                return;
            }
            var json = eval("("+d+")");
            if(json.result){
                $.ligerDialog.success(json.message,'提示',function(){
                    location.href=location.href.getNewUrl();    
                });
            }
            else{
                $.ligerDialog.err("提示信息","标记已读失败!",json.message);
            }
        });
    };
</script>
</head>
<body class="oa-mw-page">

    <div id="oa_list_title" class="oa-mgb20 oa-project-title">
        <!-- <h2>消息接收管理列表</h2> -->
    </div>

    <div id="oa_list_operation" class="oa-pdb20 oa-mgh20 oa-mgl40">
        <a class="oa-button oa-button--primary oa-button--blue del" action="del.ht">删除</a>
        <a class="oa-button oa-button--primary oa-button--blue done" onclick="mark()">标记为已读</a>
    </div>
    
        <div id="oa_list_search" class="oa-pdb10 oa-mgh20">
    <form id="searchForm" method="post" action="list.ht" class="oa-clear">

        <div class="oa-fl oa-mgb10">
            <div class="oa-label">标题</div>
            <div class="oa-input-wrap oa-mgl100">
                <input type="text" name="Q_subject_SL"  class="oa-input" value="${param['Q_subject_SL']}"/>
            </div>
        </div>

        <div class="oa-fl oa-mgb10">
            <div class="oa-label">消息类型</div>
            <div class="oa-input-wrap oa-mgl100">
                <select name="Q_messageType_S" class="oa-select" value="${param['Q_messageType_S']}">
                    <option value="">全部</option>
                    <option value="1" <c:if test="${param['Q_messageType_S'] == 1}">selected</c:if>>个人信息</option>
                    <option value="2" <c:if test="${param['Q_messageType_S'] == 2}">selected</c:if>>日程安排</option>
                    <option value="3" <c:if test="${param['Q_messageType_S'] == 3}">selected</c:if>>计划任务</option>
                    <option value="4" <c:if test="${param['Q_messageType_S'] == 4}">selected</c:if>>系统信息</option>
                    <option value="5" <c:if test="${param['Q_messageType_S'] == 5}">selected</c:if>>代办提醒 </option>
                    <option value="6" <c:if test="${param['Q_messageType_S'] == 6}">selected</c:if>>流程提醒 </option>
                </select>
            </div>
        </div>

        <div class="oa-fl oa-mgb10">
            <div class="oa-label">是否已读</div>
            <div class="oa-input-wrap oa-mgl100">
                <select name="Q_receiveTime_S" class="oa-select" value="${param['Q_receiveTime_S']}">
                    <option value="">全部</option>
                    <option value="1" <c:if test="${param['Q_receiveTime_S'] == 1}">selected</c:if>>未读</option>
                    <option value="2" <c:if test="${param['Q_receiveTime_S'] == 2}">selected</c:if>>已读</option>
                </select>
            </div>
        </div>

        <div class="oa-fl oa-mgb10">
            <div class="oa-label">发送时间</div>
            <div class="oa-input-wrap oa-mgl100  oa-input-wrap--ib">
                <input  name="Q_beginreceiveTime_DL"  class="oa-input datetime" value="${param['Q_beginreceiveTime_DL']}"/>
            </div>
            <span>至</span>
            <div class="oa-input-wrap  oa-input-wrap--ib">
                <input  name="Q_endreceiveTime_DG" class="oa-input datetime" value="${param['Q_endreceiveTime_DG']}"/>
            </div>
        </div>

        <div class="oa-fl oa-mgb10 oa-mgh20">
            <button class="oa-button oa-button--primary oa-button--blue">查询</button>
        </div>

    </form>
        </div>

   <div class="oa-pdb20 oa-mgh20">
    <div id="oa_list_content" class="oa-table-scroll-wrap">


    <c:set var="checkAll">
        <input type="checkbox" id="chkall"/>
    </c:set>
    <display:table name="messageReceiverList" id="messageReceiverItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1"   class="oa-table--default oa-table--nowrap">
    	<c:choose>
	    	<c:when test="${messageReceiverItem.receiveTime==null}">		        
		        <display:column title="${checkAll}" media="html" >
		                <input type="checkbox" class="pk" name="id" value="${messageReceiverItem.rid}">
		        </display:column>
		        <display:column style="font-weight:bold;" property="userName" title="发信人" sortable="true" sortName="userName"></display:column>
		        <display:column style="font-weight:bold;" property="subject" title="标题" sortable="true" sortName="subject"></display:column>
		        <display:column style="font-weight:bold;" title="消息类型" sortable="true" sortName="messageType">
		        <c:choose>
		            <c:when test="${messageReceiverItem.messageType==1}">
		                   个人信息
		            </c:when>
		            <c:when test="${messageReceiverItem.messageType==2}">
		                    日程安排
		            </c:when>
		            <c:when test="${messageReceiverItem.messageType==3}">
		                   计划任务
		            </c:when>
		            <c:when test="${messageReceiverItem.messageType==4}">
		                           系统信息
		            </c:when>
		            <c:when test="${messageReceiverItem.messageType==5}">
		                           代办提醒
		            </c:when>
		            <c:when test="${messageReceiverItem.messageType==6}">
		                          流程提醒
		            </c:when>
		            <c:otherwise>
		                           其他                 
		            </c:otherwise>
		        </c:choose>
		        </display:column>   
		        <display:column style="font-weight:bold;" title="发送时间" sortable="true" sortName="sendTime">
		            <fmt:formatDate value="${messageReceiverItem.sendTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
		        </display:column>                           
		        <display:column style="font-weight:bold;" title="收信时间" sortable="true" sortName="receiveTime">未读消息</display:column>
		        <display:column title="管理" media="html" >
		            <c:if test="${messageReceiverItem.rid!=null}">
		                <a href="del.ht?id=${messageReceiverItem.rid}" class="oa-button-label del">删除</a>
		            </c:if>
		            <a href="${ctx}/platform/system/messageRead/list.ht?messageId=${messageReceiverItem.id}" class="oa-button-label detail">详情</a>
		            <a href="${ctx}/platform/system/messageSend/getReply.ht?id=${messageReceiverItem.id}&rid=${messageReceiverItem.rid}" class="oa-button-label detail">快速回复</a>
		        </display:column>
		    </c:when>
		    <c:otherwise>
		    	 <display:column title="${checkAll}" media="html" >
		                <input type="checkbox" class="pk" name="id" value="${messageReceiverItem.rid}">
		        </display:column>
		        <display:column style="font-weight:normal;" property="userName" title="发信人" sortable="true" sortName="userName"></display:column>
		        <display:column style="font-weight:normal;" property="subject" title="标题" sortable="true" sortName="subject"></display:column>
		        <display:column style="font-weight:normal;" title="消息类型" sortable="true" sortName="messageType">
		        <c:choose>
		            <c:when test="${messageReceiverItem.messageType==1}">
		                   个人信息
		            </c:when>
		            <c:when test="${messageReceiverItem.messageType==2}">
		                    日程安排
		            </c:when>
		            <c:when test="${messageReceiverItem.messageType==3}">
		                   计划任务
		            </c:when>
		            <c:when test="${messageReceiverItem.messageType==4}">
		                           系统信息
		            </c:when>
		            <c:when test="${messageReceiverItem.messageType==5}">
		                           代办提醒
		            </c:when>
		            <c:when test="${messageReceiverItem.messageType==6}">
		                          流程提醒
		            </c:when>
		            <c:otherwise>
		                           其他                 
		            </c:otherwise>
		        </c:choose>
		        </display:column>   
		        <display:column style="font-weight:normal;" title="发送时间" sortable="true" sortName="sendTime">
		            <fmt:formatDate value="${messageReceiverItem.sendTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
		        </display:column>                           
		        <display:column style="font-weight:normal;" title="收信时间" sortable="true" sortName="receiveTime">
		            <fmt:formatDate value="${messageReceiverItem.receiveTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
		        </display:column>
		        <display:column title="管理" media="html" >
		            <c:if test="${messageReceiverItem.rid!=null}">
		                <a href="del.ht?id=${messageReceiverItem.rid}" class="oa-button-label del">删除</a>
		            </c:if>
		            <a href="${ctx}/platform/system/messageRead/list.ht?messageId=${messageReceiverItem.id}" class="oa-button-label detail">详情</a>
		            <a href="${ctx}/platform/system/messageSend/getReply.ht?id=${messageReceiverItem.id}&rid=${messageReceiverItem.rid}" class="oa-button-label detail">快速回复</a>
		        </display:column>
		    </c:otherwise>
		</c:choose>
    </display:table>
    </div>
    <hotent:paging tableId="messageReceiverItem"/>

</div>      

</body>
</html>


