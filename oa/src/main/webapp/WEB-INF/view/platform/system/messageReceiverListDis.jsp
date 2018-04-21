
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
	<title>消息接收者管理</title>
	<%@include file="/commons/include/get.jsp" %>
	<style>
		a.link.del {
			text-decoration: none;
		    display: inline-block;
		    padding: 0 16px;
		    font-size: 14px;
		    height: 32px;
		    line-height: 32px;
		    text-align: center;
		    cursor: pointer;
		    color: #fff;
	    	background: #0f88eb;
		    border: 1px solid #0f88eb;
		    -webkit-box-sizing: border-box;
		    -moz-box-sizing: border-box;
		    box-sizing: border-box;
		    -webkit-border-radius: 3px;
		    border-radius: 3px;
		    -webkit-box-shadow: 0 1px 1px rgba(0,0,0,0.15);
		    box-shadow: 0 1px 1px rgba(0,0,0,0.15);
		}
		div.panel-top {
			margin-left: 0 !important;
			overflow: hidden;
		}
		.panel-top .group {
			float: none;
		}
	</style>
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

				if(state&&me.closest("tr").find('td').eq(5).text().indexOf('未读消息')>0)
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
<body>
	<div id="oa_list_title">
		<div class="panel-top">
			<div class="oa-mgt10 oa-mgh20 group">		    	
		        <a class="oa-button oa-button--primary oa-button--blue link del"  action="del.ht">删除</a>
		        <a class="oa-button oa-button--primary oa-button--blue" onclick="mark()">标记为已读</a>
		    </div>
	    </div>
	</div>
	<div id="oa_list_search" class="oa-pdb10 oa-mgh20">
        <div class="oa-accordion">
            <div class="oa-accordion__title">查询条件</div>
            <div class="oa-accordion__content">               
                <form id="searchForm" method="post" action="list.ht?getType=1" class="oa-clear">
                    <div class="oa-fl oa-mgb10">
                        <div class="oa-label">发文类型</div>
                        <div class="oa-select-wrap oa-mgl100">
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
                        <div class="oa-select-wrap oa-mgl100">
                            <select name="Q_receiveTime_S" class="oa-select" value="${param['Q_receiveTime_S']}">
							    <option value="">全部</option>
								<option value="1" <c:if test="${param['Q_receiveTime_S'] == 1}">selected</c:if>>未读</option>
								<option value="2" <c:if test="${param['Q_receiveTime_S'] == 2}">selected</c:if>>已读</option>
						    </select>
                        </div>
                    </div>                    
                    <div class="oa-fl oa-mgb10">
                        <div class="oa-label">收信时间</div>
                        <div class="oa-input-wrap oa-mgl100 oa-input-wrap--ib">
                            <input  name="Q_beginreceiveTime_DL"  class="oa-input datePicker" datetype="1"  value="${param['Q_beginreceiveTime_DL']}" />
                        </div>
                        <span>至</span>
                        <div class="oa-input-wrap oa-input-wrap--ib">
                            <input name="Q_endreceiveTime_DG" class="oa-input datePicker" datetype="2" value="${param['Q_endreceiveTime_DG']}"/>
                        </div>
                    </div>
                    <div class="oa-fl oa-mgb10 oa-mgh20">
                    	<button class="oa-button oa-button--primary oa-button--blue" type="button" onclick="tosubmit()" >查询</button>
                    </div> 
                </form>
            </div>
        </div>
    </div>
    <div class="oa-pdb20 oa-mgh20">
        <div id="oa_list_content" class="oa-table-scroll-wrap">
            <c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
            <display:table name="messageReceiverList" id="messageReceiverItem" requestURI="list.ht" sort="external" class="oa-table--default oa-table--nowrap">
                <display:column title="${checkAll}" media="html" style="width:30px;">
					<input type="checkbox"  class="pk" name="id" value="${messageReceiverItem.rid}">
				</display:column>
				<display:column property="userName" title="发文" sortable="true" sortName="userName"></display:column>
				<display:column title="标题" ><span class="thisContent">${messageReceiverItem.content}</span></display:column>
				<display:column title="发文类型" sortable="true" sortName="messageType" style="text-align:center;">
				<display:column title="收信人" >${messageReceiverItem.receiverName}</display:column>
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
				<display:column title="收信时间" sortable="true" sortName="sendTime" style="text-align:center;">
					<fmt:formatDate value="${messageReceiverItem.sendTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</display:column>							
				<display:column  title="阅读时间" sortable="true" sortName="receiveTime" style="text-align:center;">
					<c:choose>
						<c:when test="${messageReceiverItem.receiveTime!=null}">
							<fmt:formatDate value="${messageReceiverItem.receiveTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</c:when>
						<c:otherwise>
							未读消息
						</c:otherwise>
					</c:choose>
				</display:column>
				<display:column title="管理" media="html" style="width:180px;text-align:center">
					<c:if test="${messageReceiverItem.rid!=null}">
						<a href="del.ht?id=${messageReceiverItem.rid}" class="link del">删除</a>
					</c:if>
					<c:if test="${messageReceiverItem.canReply==1}">
					<a href="${ctx}/platform/system/messageReply/edit.ht?messageId=${messageReceiverItem.id}" class="link edit">回复</a>
					</c:if>
					<%-- <a href="${ctx}/platform/system/messageRead/list.ht?messageId=${messageReceiverItem.id}" class="link detail">明细</a> --%>
				</display:column>
				<input id="reName" value="{messageReceiverItem.receiverName}" />                                                                            
            </display:table>
        </div>
        <hotent:paging tableId="messageReceiverItem" />
    </div>
	<script type="text/javascript">
		$(function(){
			$('.oa-accordion').oaAccordion({});
		});
		function tosubmit(){
			$("input[name='isexpand']").val(1);
			$("#searchForm").submit();
		}
		$(".thisContent").on("click","a",function(){
			var oldUrl = $(this).attr("href");
			if(!(oldUrl.indexOf("rid")>0)){
			var id  = $(this).parents("tr").find(".pk").val();
		 	var newUrl = oldUrl + "&" + "rid=" + id;
		 	$(this).attr("href",newUrl);
			}
			//判断是否已读
			var isRead = $(this).parents("tr").find("td").eq(6).text().trim().length;
			if(isRead>4){
				//已读直接访问
				var url_ = $(this).attr("href"); 
			}else{
				//未读标记为已读
				var url = __ctx + '/platform/system/messageReceiver/mark.ht?';
				var params={ids:id};
				$.post(url,params,function(d){});
			    var url_ = $(this).attr("href");
			} 
		});
	</script>
</body>
</html>


