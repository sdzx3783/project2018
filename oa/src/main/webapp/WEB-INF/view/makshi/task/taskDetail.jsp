<%@page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>任务详情</title>
    <%@include file="/commons/include/customForm.jsp" %>
    <%@include file="/commons/include/ueditor.jsp" %>
    <script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
    <script type="text/javascript">
    function submitProgress(type,id){
		window.open('toSubmit.ht?type='+type+'&id='+id, 'toSubmit','height=500, width=900, top=200, left=300, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
	}
    
    </script>
    <style>
        .oa-table td,
        .oa-table th{
            padding: 10px 5px;
        }

        .oa-table--form dt{
            float: left;
            line-height: 32px;
        }
        .oa-table--form dd{
            margin-left: 120px;
            line-height: 32px;
        }
    </style>
</head>
<body class="oa-mw-page">
    <div class="oa-project-title">
        <h2>任务详情</h2>
    </div>

    <div class="oa-mg20">
        <button class="oa-button oa-button--primary oa-button--blue" onclick="window.location.href='/makshi/task/task/list.ht?type=${type}'">返回</button>
        <c:if test="${isCharge }">
            <button class="oa-button oa-button--primary oa-button--blue" onClick="submitProgress(1,${task.id})">提交进度</button>
            <button class="oa-button oa-button--primary oa-button--blue" onClick="submitProgress(2,${task.id})">任务延期</button>
            <button class="oa-button oa-button--primary oa-button--blue" onClick="submitProgress(3,${task.id})">转派责任人</button>
            <button class="oa-button oa-button--primary oa-button--blue" onClick="submitProgress(4,${task.id})">任务取消</button>
        </c:if> 
       
    </div>
    <div class="oa-mg20">
        <form id="projectForm" method="post" action="save.ht">
            <table class="oa-table oa-table--form">
            	<input type="hidden"  name="id" value="${task.id}"/>
                <tr>
                    <td>
                        <dl>
                            <dt>任务标题：</dt>
                            <dd>${task.name}</dd>
                        </dl>
                    </td>
                    <td>
                        <dl>
                            <dt>重要：</dt>
                            <dd>
                                <c:if test="${task.imports==1}">普通</c:if>
                                <c:if test="${task.imports==2}">重要</c:if>
                            </dd>
                        </dl>
                    </td>
                </tr>
        
                <tr>
                    <td>
                        <dl>
                            <dt>负责人：</dt>
                            <dd>${task.charger }</dd>
                        </dl>
                    </td>
                    <td>
                        <dl>
                            <dt>参与人：</dt>
                            <dd>${task.member }</dd>
                        </dl>
                    </td>
                </tr>

                <tr>
                    <td>
                        <dl>
                            <dt>任务分配人：</dt>
                            <dd>${task.cuser }</dd>
                        </dl>
                    </td>
                    <td>
                        <dl>
                            <dt>时间：</dt>
                            <dd><fmt:formatDate value='${task.startDate }' pattern='yyyy-MM-dd'/>__<fmt:formatDate value='${task.endDate }' pattern='yyyy-MM-dd'/></dd>
                        </dl>
                    </td>
                </tr>
                
                <tr>
                    <td>
                        <dl>
                            <dt>详情：</dt>
                            <dd>${task.content}</dd>
                        </dl>
                    </td>
                    <td>
                        <dl>
                            <dt>附件：</dt>
                            <dd>
                                <input  ctltype="attachment" name="file" isdirectupload="0" right="r" value='${task.file}' validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}" />
                            </dd>
                        </dl>
                    </td>
                </tr>
            </table>
        </form>
    </div>

    <div class="oa-mg20">
        <div class="oa-table-scroll-wrap">
            <table class="oa-table--default oa-table--nowrap">
                <tr>
                    <th>序号</th>
                    <th>时间</th>
                    <th>类型</th>
                    <th>进度</th>
                    <th>备注</th>
                </tr>
                <c:forEach items="${logList}" var="logItem" varStatus="a">
                <tr>
                    <td>${a.index+1}</td>
                    <td><fmt:formatDate value='${logItem.ctime }' pattern='yyyy-MM-dd'/></td>
                    <td>
                        <c:if test="${logItem.type==1}">提交进度</c:if>
                        <c:if test="${logItem.type==2}">任务取消</c:if>
                        <c:if test="${logItem.type==3}">任务延期</c:if>
                        <c:if test="${logItem.type==4}">转派责任人</c:if>
                    </td>
                    <td>${logItem.progress }%</td>
                    <td>${logItem.remark }</td>
                </tr>
                </c:forEach>
            </table>
        </div>
        <hotent:paging tableId="logItem"/>
    </div>

</body>
</html>
