<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="f" uri="http://www.jee-soft.cn/functions" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="hotent" uri="http://www.jee-soft.cn/paging" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>部门门户</title>
    <!-- 水保部 -->
    <link rel="stylesheet" href="${ctx}/styles/custom/reset.css">
    <link rel="stylesheet" href="${ctx}/styles/custom/icon.css">
    <link rel="stylesheet" href="${ctx}/styles/custom/index.css">
    <link rel="stylesheet" href="${ctx}/styles/custom/idangerous.swiper.css">
</head>
<body>

    <div class="__container fixFloat">
        <div class="__container__wrap">

            <div class="__main fl">
                <div class="__main__wrap">
            
                    <div class="__section">
                        <div class="__section__head">
                            <h2 class="__section__title">项目进展 <span>Project Progress</span></h2>
                            <c:if test="${not empty taskLogslist && fn:length(taskLogslist)>=5}">
                                <div class="__section__more">
                                    <a class="__section__link" href="/makshi/portal/orgportal/moreProjectProgress.ht?&orgId=${orgId}">更多...</a>
                                </div>
                            </c:if>
                        </div>
                        <div class="__section__main __section__main--second">
                            <table class="__section__table">
                                <thead class="__section__table-head">
                                    <tr>
                                        <th style="width: 35%;">项目名称</th>
                                        <th style="width: 25%;">任务名称</th>
                                        <th style="width: 15%;">提交人</th>
                                        <th style="width: 15%;">提交时间</th>
                                        <th style="width: 10%;">状态</th>
                                    </tr>
                                </thead>
                                <tbody class="__section__table-tbody">
                                    <c:forEach begin="0" end="3" items="${taskLogslist}" var="taskLog">
                                        <tr>
                                            <td>${taskLog.projectName}</td>
                                            <td><a href="/makshi/project/project/taskDetail.ht?id=${taskLog.taskid}&type=2&portalOrgId=${orgId }">${taskLog.taskName}</a></td>
                                            <td>${taskLog.submittor}</td>
                                            <td><fmt:formatDate value='${taskLog.ctime}' pattern='yyyy/MM/dd' /></td>
                                            <td>${taskLog.status}</td>
                                        </tr>
                                    </c:forEach>
                                    <c:if test="${empty taskLogslist || fn:length(taskLogslist)<=0}">
                                        <tr>
                                            <td colspan="5">当前没有记录...</td>
                                        </tr>
                                    </c:if>
                                </tbody>
                            </table>

                        </div>
                    </div>

                    <div class="__section">
                        <div class="__section__head">
                            <h2 class="__section__title">部门通知 <span>Department Notice</span></h2>
                            <c:if test="${not empty tzgg_list && fn:length(tzgg_list)>=5}">
                                <div class="__section__more">
                                    <a class="__section__link" href="/makshi/doc/docinfo/filelist.ht?docId=${tzggId}&portalOrgId=${orgId}&from=orgportalindex">更多...</a>
                                </div>
                            </c:if>
                        </div>
                        <div class="__section__main __section__main--normol">
                            <ul class="__section__list">
                                <c:forEach items="${tzgg_list}" var="docFile">
                                    <li class="__section__item">
                                        <h3 class="__section__item-title">
                                            <a href="/makshi/doc/docinfo/filedetail.ht?id=${docFile.dfid}" class="__section__link">${docFile.title}</a>
                                        </h3>
                                        <span class="__section__item-date"><fmt:formatDate value='${docFile.createtime}' pattern='yyyy/MM/dd' /></span>
                                    </li>
                                </c:forEach>
                                <c:if test="${empty tzgg_list || fn:length(tzgg_list)<=0}">
                                    <li class="__section__item">
                                        <h3 class="__section__item-title">
                                            当前没有记录...
                                        </h3>
                                    </li>                                
                                </c:if>
                            </ul>
                        </div>
                    </div> 

                    <div class="__section">
                        <div class="__section__head">
                            <h2 class="__section__title">部门制度 <span>Department Policy</span></h2>
                            <c:if test="${not empty tzgg_list && fn:length(tzgg_list)>=5}">
                                <div class="__section__more">
                                    <a class="__section__link" href="/makshi/doc/docinfo/filelist.ht?docId=${gszdId}&portalOrgId=${orgId}&from=orgportalindex">更多...</a>
                                </div>
                            </c:if>
                        </div>
                        <div class="__section__main __section__main--normol">
                            <ul class="__section__list">
                                <c:forEach items="${gszd_list}" var="docFile">
                                    <li class="__section__item">
                                        <h3 class="__section__item-title">
                                            <a href="/makshi/doc/docinfo/filedetail.ht?id=${docFile.dfid}" class="__section__link">${docFile.title}</a>
                                        </h3>
                                        <span class="__section__item-date"><fmt:formatDate value='${docFile.createtime}' pattern='yyyy/MM/dd' /></span>
                                    </li>
                                </c:forEach>
                                <c:if test="${empty gszd_list || fn:length(gszd_list)<=0}">
                                    <li class="__section__item">
                                        <h3 class="__section__item-title">
                                            当前没有记录...
                                        </h3>
                                    </li>                                
                                </c:if>
                            </ul>
                        </div>
                    </div>

                </div>
            </div>


            <div class="__aside fr">
                <div class="__aside__wrap">

                    <div class="__section">
                        <div class="__section__head">
                            <h2 class="__section__title">工作动态 <span>Work News</span></h2>
                            <c:if test="${not empty gzdt_list && fn:length(gzdt_list)>=5}">
                                <div class="__section__more">
                                    <a class="__section__link" href="/makshi/doc/docinfo/filelist.ht?docId=${gzdtId}&portalOrgId=${orgId }&from=orgportalindex">更多...</a>
                                </div>
                            </c:if>
                        </div>
                        <div class="__section__main __section__main--normol">
                            <ul class="__section__list">
                                <c:forEach items="${gzdt_list}" var="docFile">
                                    <li class="__section__item">
                                        <h3 class="__section__item-title">
                                            <a href="/makshi/doc/docinfo/filedetail.ht?id=${docFile.dfid}" class="__section__link">${docFile.title}</a>
                                        </h3>
                                        <span class="__section__item-date"><fmt:formatDate value='${docFile.createtime}' pattern='yyyy/MM/dd' /></span>
                                    </li>
                                </c:forEach>
                                <c:if test="${empty gzdt_list || fn:length(gzdt_list)<=0}">
                                    <li class="__section__item">
                                        <h3 class="__section__item-title">
                                            当前没有记录...
                                        </h3>
                                    </li>
                                </c:if>
                            </ul>
                        </div>
                    </div>
           
                    <div class="__section">
                        <div class="__section__head">
                            <h2 class="__section__title">会议纪要 <span>Meeting Summary</span></h2>
                            <c:if test="${not empty hyjy_list && fn:length(hyjy_list)>=5}">
                            <div class="__section__more">
                                <a class="__section__link" href="/makshi/doc/docinfo/filelist.ht?docId=${hyjyId}&portalOrgId=${orgId }&from=orgportalindex">更多...</a>
                            </div>
                            </c:if>
                        </div>
                        <div class="__section__main __section__main--normol __section__main--row2">
                            <ul class="__section__list">
                                <c:forEach items="${hyjy_list}" var="docFile">
                                    <li class="__section__item">
                                        <h3 class="__section__item-title">
                                            <a href="/makshi/doc/docinfo/filedetail.ht?id=${docFile.dfid}" class="__section__link">${docFile.title}</a>
                                        </h3>
                                        <span class="__section__item-date"><fmt:formatDate value='${docFile.createtime}' pattern='yyyy/MM/dd' /></span>
                                    </li>
                                </c:forEach>
                                <c:if test="${empty hyjy_list || fn:length(hyjy_list)<=0}">
                                    <li class="__section__item">
                                        <h3 class="__section__item-title">
                                            当前没有记录...
                                        </h3>
                                    </li>
                                </c:if>
                            </ul>
                        </div>
                    </div>

                </div>
            </div>

        </div>
    </div>
 
<script src="/js/custom/jquery-1.8.3.min.js"></script>
</body>
</html>