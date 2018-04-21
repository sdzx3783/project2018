<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>流程历史</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body class="oa-mw-page">

    <div class="oa-pd20">
        <a id="back" class="oa-button oa-button--primary oa-button--blue" href="/makshi/waterprotectpark/techReviewWeekPlanSbb/list.ht">返回</a>
    </div>

    <div class="oa-pd20">
        <div class="oa-table-scroll-wrap">
            <table class="oa-table--default">
                <thead>
                    <tr>
                        <th>序号</th>
                        <th>流程名称</th>
                        <th>申请人</th>
                        <th>申请时间</th>
                        <th>审批状态</th>
                        <th>审批历史</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${runs }" var="run" varStatus="runno">
                        <tr>
                            <td>${runno.index+1 }</td>
                            <td>${run.processName }</td>
                            <td>${run.creator }</td>
                            <td><fmt:formatDate value='${run.createtime }' pattern='yyyy-MM-dd HH:mm:ss' /></td>
                            <td>${run.taskName }</td>
                            <td><a href="/platform/bpm/processRun/get.ht?tab=1&runId=${run.runId }&rand=1496751292163" class="oa-button-label" target="_blank">查看</a></td>
                        </tr>
                    </c:forEach>    
                </tbody>                                        
            </table>
        </div>
    </div>

</body>
</html>


