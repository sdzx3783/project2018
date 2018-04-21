
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
    <title>接收状态管理</title>
    <%@include file="/commons/include/get.jsp" %>
    <f:link href="form.css" ></f:link>
    <script type="text/javascript"
        src="${ctx}/js/hotent/platform/form/AttachMent.js"></script>
    <script type="text/javascript"
        src="${ctx}/js/hotent/platform/system/HtmlUploadDialog.js"></script>
    <script type="text/javascript"
        src="${ctx}/js/hotent/platform/system/FlexUploadDialog.js"></script>
    
    <script type="text/javascript">
        $().ready(function (){
            AttachMent.init("r");
        });
    </script>
    </head>
<body class="oa-mw-page">


    <div class="oa-pd20 oa-pdb0">
        <c:choose>
            <c:when test="${param.type!='index' }">
                <a class="oa-button oa-button--primary oa-button--blue" onclick="javascript:history.go(-1);">返回</a>
            </c:when>
            <c:otherwise>
                <a class="oa-button oa-button--primary oa-button--blue" onclick="window.close();">关闭</a>
            </c:otherwise>
        </c:choose>        
    </div>


    <div class="oa-pd20">
        <table class="oa-table--default" cellpadding="0" cellspacing="0" border="0">
            <tr>
                <th>标题:</th>
                <td>${messageSend.subject}</td>
            </tr>
            <tr>
                <th>发信人:</th>
                <td>${messageSend.userName}</td>
            </tr>
            <tr>
                <th>发信时间:</th>
                <td><fmt:formatDate value="${messageSend.sendTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            </tr>
            <tr>
                <th>附件:</th>
                <td colspan="3">
                    <div name="div_attachment_container">
                        <div class="attachement"></div>
                        <textarea style="display: none" controltype="attachment"
                            name="attachment" lablename="附件" validate="{}">${messageSend.attachment}</textarea>
                    </div>
                </td>
            </tr>
            <tr>
                <th>内容:</th>
                <td>${messageSend.content}</td>
            </tr>
        </table>
    </div>
    
            
            <div class="oa-pd20">
                    <div class="oa-table-scroll-wrap">
                        <table id="dicTable" class="oa-table--default oa-table--nowrap" id="0" cellpadding="1" cellspacing="1">
                            <thead>
                                <th>回复人</th>
                                <th>回复内容</th>
                                <th>回复时间</th> 
                            </thead>                            
                            <tbody>
                                <c:forEach items="${replyList}" var="replyItem">
                                    <tr style="<c:if test="${replyItem.isPrivate==1&&replyItem.replyId!=userId}">display:none</c:if>;height:50px" class="${status.index%2==0?'odd':'even'}">                                                                                                                                                                                    
                                        <td>${replyItem.reply }</td> 
                                        <td>${replyItem.content }</td>
                                        <td><fmt:formatDate value="${replyItem.replyTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td> 
                                    </tr>
                                </c:forEach>
                                <tr>
                                    <td>
                                        <c:if test="${empty replyList || fn:length(replyList)<=0}">暂无数据...</c:if>
                                    </td>
                                </tr>
                            </tbody>                            
                         </table>
                     </div>
                </div>
                
</body>
</html>


