
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
    <title>消息回复管理</title>
    <%@include file="/commons/include/get.jsp" %>
    <%@include file="/codegen/include/customForm.jsp" %>
    <%@include file="/commons/include/ueditor.jsp" %>
    <script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
        <script type="text/javascript">
        $(function() {
            $("textarea").on('keypress',  //所有input标签回车无效，当然，可以根据需求自定义
                function(e){
                    var key = window.event ? e.keyCode : e.which;
                    if(key.toString() == "13"){
                        $("a.save").trigger("click");
                        return false;
                    }
                }
            );
            var options={};
            if(showResponse){
                options.success=showResponse;
            }
            var frm=$('#replyForm').form();
            $("a.save").click(function() {
                frm.ajaxForm(options);
                $("#saveData").val(1);
                if(frm.valid()){
                    //var action="/platform/system/messageReply/reply.ht";
                    //frm.attr("action", action);
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
                            $('#replyForm').submit();
                        });
                    }else{
                        frm.handleFieldName();
                        $('#replyForm').submit();
                    }
                }
            });
        });
        
        function showResponse(responseText) {
            var obj = new com.hotent.form.ResultMessage(responseText);
            if (obj.isSuccess()) {
                window.location.href = window.location.href;
            } else {
                $.ligerDialog.error(obj.getMessage(),"提示信息");
            }
        }
    </script>
    <style>
    body,.panel{background:#f7f7f7}
    .panel .panel-top{margin:0!important}
    .hide-panel{box-sizing:border-box;border-bottom:1px solid #dadfed}
    .panel-toolbar{padding:27px 8px;height:auto;background:#f1f1f1!important}
    .toolBar{text-align:center}
    .toolBar a{float:left}
    .icon-arrow-left{color:#333;font-size:36px}
    .icon-location-arrow-outline{display:inline-block;margin-top:-3px;vertical-align:middle;font-size:22px}
    .text{vertical-align:middle}
    .panel-toolbar{margin-top:0}
    .message-wrap{margin:0 16px;padding-top:16px}
    .message-item{margin-bottom:30px}
    .message-title{margin-bottom:5px;color:#9da6bb;font-size:14px}
    .message-line{position:relative}
    .message-profile{position:absolute;top:0;overflow:hidden;width:50px;height:50px;border-radius:35px}
    .message-profile img{max-width:100%}
    .message-text{position:relative;display:inline-block;padding:12px;min-width:40px;min-height:19px;border-radius:3px;text-align:left;font-size:14px; margin-bottom: 5px;}
    .message-text::before{position:absolute;top:10px;width:0;height:0;border-top:12px solid transparent;border-bottom:12px solid transparent;content:''}
    .message-item--left{text-align:left}
    .message-item--left .message-title{margin-left:70px;}
    .message-item--left .message-text{margin-left:70px;background:#e7f1f6}
    .message-item--left .message-time{margin-left:90px;color: #9da6bb;}
    .message-item--left .message-text::before{left:-12px;border-right:15px solid #e7f1f6}
    .message-item--left .message-profile{left:0}
    .message-item--right{text-align:right}
    .message-item--right .message-title{margin-right:70px;}
    .message-item--right .message-text{margin-right:70px;background:#fff}
    .message-item--right .message-time{margin-right:90px;color: #9da6bb;}
    .message-item--right .message-text::before{right:-12px;border-left:15px solid #fff}
    .message-item--right .message-profile{right:0}
    .fixed-bottom{position:fixed;right:0;bottom:0;left:0;height:90px;background:#ddd}
    .fixed-sibling{height:90px}
    .input-wrap{position:relative;padding-top:25px}
    .input-text{box-sizing:border-box;padding-right:170px;padding-left:10px;width:100%;height:40px;outline:0;border:0;background:#ddd;font-size:10px;line-height:1.2}
    a.send-btn{position:absolute;top:20px;right:20px;padding:0 20px;width:100px;height:50px;background:#55d48b!important;color:#fff;text-align:center;font-size:16px;line-height:50px}
    .username{display:inline-block;height:36px;vertical-align:middle;font-size:18px;line-height:36px}
    .fixed-top{position:fixed;top:0;right:0;left:0;z-index:1;height:90px;background:#ddd}
    .message-text{
        word-break: break-all;
    }
    </style>
</head>
<body>
<div class="panel">
    <div class="fixed-wrap">
        <div class="fixed-top">
            <div class="hide-panel">
                <div class="panel-top">
                    <div class="tbar-title">
                        <span class="tbar-label">消息回复详细信息</span>
                    </div>
                    <div class="panel-toolbar">
                        <div class="toolBar">
                            <%-- <a href="mylist.ht?userId=${userId}"><span class="icon icon-arrow-left"></span></a> --%>
                            <a href="#" onclick="JavaScript:history.back(-1);"><span class="icon icon-arrow-left"></span></a>
                            <span class="username">${userName}</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="fixed-sibling"></div>
    </div>
    
    <div class="message-wrap">

        <c:forEach items="${list}" var="message">

            <c:if test="${message.userId==userId}">
                <div class="message-item message-item--left">
                    <div class="message-line">
                        <div class="message-profile">
                            <img src="/images/default.png" alt="">
                        </div>
                        <div class="message-title">${message.userName}</div>
                        <div class="message-text">${message.content}</div>
                        <div class="message-time"><fmt:formatDate value="${message.sendTime }" pattern="yyyy-MM-dd HH:mm:ss"/></div>
                    </div>
                </div>
            </c:if>
            <c:if test="${message.userId!=userId}">
                <div class="message-item message-item--right">
                    <div class="message-line">
                        <div class="message-profile">
                            <img src="/images/default.png" alt="">
                        </div>
                        <div class="message-title">${message.userName}</div>
                        <div class="message-text">${message.content}</div>
                        <div class="message-time"><fmt:formatDate value="${message.sendTime }" pattern="yyyy-MM-dd HH:mm:ss"/></div>
                    </div>
                </div>
            </c:if>
        </c:forEach>
    </div>

    <div class="fixed-wrap">
        <div class="fixed-bottom">
            <form id="replyForm" method="post" action="/platform/system/messageReply/reply.ht">
                <div class="input-wrap">
                    <!-- <input type="text" class="input-text" placeholder="请在这里输入..." name="content"/> -->
                    <textarea  class="input-text" placeholder="请在这里输入..." name="content"></textarea>
                    <a class="link save send-btn    " id="dataFormSave" href="javascript:;"><span class="text">确认发送</span></a>
                </div>

                <input type="hidden" name="messageId" value="${msgId }"/>
                <input type="hidden" name="replyId" value="${userId }"/>
                <input type="hidden" name="reply" value="${userName }"/>
            </form>
        </div>
        <div class="fixed-sibling"></div>
    </div>

<!--        <div class="panel-detail">
            <table class="table-detail" cellpadding="0" cellspacing="0" border="0">
                <tr>
                    <th width="20%">标题:</th>
                    <td>${list.get(0).subject}</td>
                </tr>
            </table>                    
        </div>
        <div class="panel-data">
            <table id="dicTable" class="table-grid table-list" id="0" cellpadding="1" cellspacing="1">
                        <thead>
                            <th style="width:18%">回复人</th>
                            <th>回复内容</th>
                            <th style="width:18%">回复时间</th>                     
                        </thead>                            
                        <tbody>
                        <c:forEach items="${list}" var="message">
                            <tr <c:if test="${message.userId==userId }">class="odd"</c:if>> 
                                <td>${message.userName }</td> 
                                <td>${message.content }</td>
                                <td><fmt:formatDate value="${message.sendTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>                                                                       
                            </tr>
                        </c:forEach>
                        <tr>
                            <form id="replyForm" method="post" action="/platform/system/messageReply/reply.ht">
                                <td ><input type="text" name="content"/></td>
                                <td ><a class="link save" id="dataFormSave" href="javascript:;"><span></span>发送</a></td>
                                <input type="hidden" name="messageId" value="${msgId }"/>
                                <input type="hidden" name="replyId" value="${userId }"/>
                                <input type="hidden" name="reply" value="${userName }"/>
                            </form>
                        </tr>                                                                               
                    </tbody>                            
             </table>                               
        </div>   -->    
</div>

<script>
    $(function(){
        $('.input-text').focus();

        $('html,body').animate({
            scrollTop: $(document).height()
        }, 0);
    });
</script>
</body>
</html>


