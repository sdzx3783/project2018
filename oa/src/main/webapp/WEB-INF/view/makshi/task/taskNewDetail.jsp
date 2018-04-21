<%@page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>任务详情</title>
    <%@include file="/codegen/include/customForm.jsp" %>
    <%@include file="/commons/include/ueditor.jsp" %>
    <script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/form/AttachMent.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/form/TablePermission.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/system/HtmlUploadDialog.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/system/FlexUploadDialog.js"></script>
    <style>
        .oa-table td,
        .oa-table th {
            padding: 10px 5px;
        }

        .oa-table--form dt {
            float: left;
            line-height: 32px;
        }

        .oa-table--form dd {
            margin-left: 120px;
            line-height: 32px;
        }
        .onlyread div div ul li .cancel {
            display: none;
        }
    </style>
</head>
<body class="oa-mw-page">
<div class="oa-project-title">
    <h2>任务详情</h2>
</div>

<div class="oa-mg20">

    <c:choose>
        <c:when test="${type == 1}">
            <button class="oa-button oa-button--primary oa-button--blue"
                    onclick="window.location.href='/makshi/task/task/newlist.ht?type=${type}&stage=${stage}'">返回
            </button>
        </c:when>
        <c:otherwise>
            <button class="oa-button oa-button--primary oa-button--blue"
                    onclick="window.location.href='/makshi/task/task/newlist.ht?type=${type}&logstage=${logstage}'">返回
            </button>
        </c:otherwise>
    </c:choose>
    <c:if test="${commited != 1 && type == 2}">
        <button class="oa-button oa-button--primary oa-button--blue"
                onclick="taskDetailToSubmit()">提交
        </button>
        <button class="oa-button oa-button--primary oa-button--blue"
                onclick="taskDetailToSave()">保存
        </button>
    </c:if>
</div>
<div class="oa-mg20">
    <form id="projectForm" method="post" action="newsave.ht">
        <table class="oa-table oa-table--form">
            <input type="hidden" name="id" value="${task.id}"/>
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
                        <dt>任务分配人：</dt>
                        <dd>${task.cuser }</dd>
                    </dl>
                </td>
                <td>
                    <dl>
                        <dt>办理人：</dt>
                        <dd>${task.member }</dd>
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
                        <dt>时间：</dt>
                        <dd><fmt:formatDate value='${task.startDate }' pattern='yyyy-MM-dd'/>__<fmt:formatDate
                                value='${task.endDate }' pattern='yyyy-MM-dd'/></dd>
                    </dl>
                </td>
            </tr>

            <tr>
                <td colspan="2">
                    <dl>
                        <dt>附件：</dt>
                        <dd class="onlyread">
                            <input ctltype="attachment" name="file" isdirectupload="0" right="r" value='${task.file}'
                                   validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}"/>
                        </dd>
                    </dl>
                </td>
            </tr>
        </table>
    </form>
</div>

<div class="oa-mg20">
    <div class="oa-table-scroll-wrap">
        <form id="newSubmitTaskLog" method="post" action="newsavetasklog.ht?type=${type}">
            <table class="oa-table--default oa-table--nowrap">
                <tr>
                    <th style="width: 15px;">序号</th>
                    <th style="width: 300px !important;">意见</th>
                    <th>办理人</th>
                    <th style="width: 118px;">时间</th>
                    <th>附件</th>
                </tr>
                <c:forEach items="${logList}" var="logItem" varStatus="a">
                    <tr>
                        <td>${a.index+1}</td>
                        <td>${logItem.opinion }</td>
                        <td>${logItem.cuser }</td>
                        <td><fmt:formatDate value='${logItem.ctime }' pattern='yyyy-MM-dd HH:mm:ss'/></td>
                        <td <c:if test="${!logItem.cuserid.equals(cuserid) || commited == 1}">class="onlyread"</c:if>>
                            <c:choose>
                                <c:when test="${commited == 0 && logItem.cuserid.equals(cuserid)}">
                                    <div name="div_attachment_container">
                                        <div class="attachement"></div>
                                        <textarea style="display: none" controltype="attachment" name="attach"
                                                  lablename="附件"
                                                  validate="{}">${logItem.attach}</textarea>
                                        <a href="javascript:;" field="attachment" class="link selectFile" atype="select"
                                           onclick="AttachMent.addFile(this);">选择</a>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <input ctltype="attachment" name="attach" isdirectupload="0" right="r"
                                           value='${logItem.attach }'
                                           validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}"/>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
                <%--如果当前人没有提交过意见，且没有保存过，则可以添加一条意见--%>
                <c:if test="${commited != 0 && commited != 1 && canAddLog == 1}">
                    <tr>
                        <td>${logList.size()+1}</td>
                        <td><textarea style="resize:none;" maxlength="70" rows="2" cols="50" name="opinion"></textarea>
                        </td>
                        <td>${cuser }</td>
                        <td>${crtime}</td>
                        <td>
                            <div name="div_attachment_container">
                                <div class="attachement"></div>
                                <textarea style="display: none" controltype="attachment" name="attach" lablename="附件"
                                          validate="{}"></textarea>
                                <a href="javascript:;" field="attachment" class="link selectFile" atype="select"
                                   onclick="AttachMent.addFile(this);">选择</a>
                            </div>
                        </td>
                    </tr>
                </c:if>
                <input type="hidden" name="id" value="${cruserlogid}">
                <input type="hidden" id="iscommited" name="commited" value="">
                <input type="hidden" name="taskId" value="${task.id}">
                <c:choose>
                    <c:when test="${type == 1}">
                        <input type="hidden" id="returnUri" value="/makshi/task/task/newlist.ht?type=${type}&stage=${stage}">
                    </c:when>
                    <c:when test="${type == 2}">
                        <input type="hidden" id="returnUri" value="/makshi/task/task/newlist.ht?type=${type}&logstage=${logstage}">
                    </c:when>
                </c:choose>
            </table>
        </form>
    </div>
    <hotent:paging tableId="logItem"/>
</div>
<script type="text/javascript">
    $(function () {
        AttachMent.init("w");
    });
    var frm = $('#newSubmitTaskLog').form();
    var options = {};
    if (showResponse) {
        options.success = showResponse;
    }
    function showResponse(data) {
        var resp = eval("(" + data + ")");

        if (typeof(resp.code) != 'undefined' && resp.code == 1) {
            $.ligerDialog.confirm(resp.msg + ",是否继续操作", "提示信息", function (rtn) {
                if (rtn) {
                    window.location.href = window.location.href;
                } else {
                    window.location.href = $('#returnUri').val();
                }
            });
        } else {
            $.ligerDialog.error(resp.msg, "提示信息");
        }

    }


    //保存用户TASKLOG
    var taskDetailToSave = function () {
        $('#iscommited').val("0");

        frm.ajaxForm(options);

        $('#newSubmitTaskLog').submit();
    }

    //提交用户TASKLOG
    var taskDetailToSubmit = function () {
        $.ligerDialog.confirm("确认提交？", "提示信息", function (rtn) {
            if (rtn) {
                $('#iscommited').val("1");

                frm.ajaxForm(options);

                $('#newSubmitTaskLog').submit();
            }
        });

    }
</script>
</body>
</html>
