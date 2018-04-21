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
    <!-- 咨询部 -->
    <link rel="stylesheet" href="${ctx}/styles/custom/reset.css">
    <link rel="stylesheet" href="${ctx}/styles/custom/icon.css">
    <link rel="stylesheet" href="${ctx}/styles/custom/index.css">
    <link rel="stylesheet" href="${ctx}/styles/custom/idangerous.swiper.css">
    <style>
        button {
            padding: 0;
            font: inherit;
            color: inherit;
            cursor: pointer;
            background: none;
            border: none;
            outline: none;
            -webkit-appearance: none;
            -moz-appearance: none;
            appearance: none;
        }
    </style>
</head>
<body>

    <div class="__container fixFloat">
        <div class="__container__wrap">

            <div class="__main fl">
                <div class="__main__wrap">
                    
                    <!-- 区块 -->
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
                    <!-- 区块 -->
                    
                    <!-- 区块 -->
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
                    <!-- 区块 -->
                    
                    <!-- 区块 -->
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
                    <!-- 区块 -->

                </div>
            </div>

            <div class="__aside fr">
                <div class="__aside__wrap">

                    <!-- 区块 -->
                    <div class="__section">
                        <div class="__section__head">
                            <h2 class="__section__title">新闻简报、技术质量 <span>news Briefing</span>&nbsp&nbsp&nbsp&nbsp<a href="/makshi/doc/docinfo/filelist.ht?docId=10000004170113">添加</a></h2>
                            <c:if test="${not empty gzdt_list && fn:length(gzdt_list)>=5}">
                                <div class="__section__more">
                                    <a class="__section__link" href="/makshi/doc/docinfo/filelist.ht?docId=${gzdtId}&portalOrgId=${orgId}&from=orgportalindex">更多...</a>
                                </div>
                            </c:if>
                        </div>
                        <div class="__section__main __section__main--normol">
                            <ul class="__section__list">
                                <c:forEach items="${xwjbjszl_list}" var="docFile">
                                    <li class="__section__item">
                                        <h3 class="__section__item-title">
                                            <a href="/makshi/doc/docinfo/filedetail.ht?id=${docFile.dfid}" class="__section__link">${docFile.title}</a>
                                        </h3>
                                        <span class="__section__item-date"><fmt:formatDate value='${docFile.createtime}' pattern='yyyy/MM/dd' /></span>
                                    </li>
                                </c:forEach>
                                <c:if test="${empty xwjbjszl_list || fn:length(xwjbjszl_list)<=0}">
                                    <li class="__section__item">
                                        <h3 class="__section__item-title">
                                            当前没有记录...
                                        </h3>
                                    </li>
                                </c:if>
                            </ul>
                        </div>
                    </div>
                    <!-- 区块 -->

                    <!-- 区块 -->
                    <div class="__section">
                        <div class="__section__head">
                            <h2 class="__section__title">时政 - 法规 <span>Politics Laws</span>&nbsp&nbsp&nbsp&nbsp<a href="/makshi/doc/docinfo/filelist.ht?docId=30000000550016">添加</a></h2>

                            <div class="test-wrap">
                                <!-- tab0 -->
                                <c:if test="${not empty fg_list && fn:length(fg_list)>=5}">
                                    <div class="__section__more">
                                        <a class="__section__link" href="/makshi/doc/docinfo/filelist.ht?docId=${fgId}&portalOrgId=${orgId }&from=orgportalindex">更多...</a>
                                    </div>
                                </c:if>
                                <!-- tab0 -->

                                <!-- tab1 -->
                                <c:if test="${not empty sz_list && fn:length(sz_list)>=5}">
                                    <div class="__section__more">
                                        <a class="__section__link" href="/makshi/doc/docinfo/filelist.ht?docId=${szId}&portalOrgId=${orgId }&from=orgportalindex">更多...</a>
                                    </div>
                                </c:if>
                                <!-- tab1 -->
                            </div>
                        </div>
                        <div class="__section__main __section__main--normol __section__main--row2">

                            <div class="oa-tab">
                                <div class="oa-tab-head">
                                    <button class="oa-tab-nav active">时政</button>
                                    <button class="oa-tab-nav">法规</button>
                                </div>
                                <div class="oa-tab-main">
                                    <!-- tab0 -->
                                    <div class="oa-tab-item active">
                                        <ul class="__section__list">
                                            <c:forEach  items="${sz_list}" begin="0" end="3" var="docFile">
                                                <li class="__section__item">
                                                    <h3 class="__section__item-title">
                                                        <a href="/makshi/doc/docinfo/filedetail.ht?id=${docFile.dfid}" class="__section__link">${docFile.title}</a>
                                                    </h3>
                                                    <span class="__section__item-date"><fmt:formatDate value='${docFile.createtime}' pattern='yyyy/MM/dd' /></span>
                                                </li>
                                            </c:forEach>
                                            <c:if test="${empty sz_list || fn:length(sz_list)<=0}">
                                                <li class="__section__item">
                                                    <h3 class="__section__item-title">
                                                        当前没有记录...
                                                    </h3>
                                                </li>
                                            </c:if>
                                        </ul>    
                                    </div>
                                    <!-- tab0 -->
                                    
                                    <!-- tab1 -->
                                    <div class="oa-tab-item">
                                        <ul class="__section__list">
                                            <c:forEach  items="${fg_list}" begin="0" end="3" var="docFile">
                                                <li class="__section__item">
                                                    <h3 class="__section__item-title">
                                                        <a href="/makshi/doc/docinfo/filedetail.ht?id=${docFile.dfid}" class="__section__link">${docFile.title}</a>
                                                    </h3>
                                                    <span class="__section__item-date"><fmt:formatDate value='${docFile.createtime}' pattern='yyyy/MM/dd' /></span>
                                                </li>
                                            </c:forEach>
                                            <c:if test="${empty fg_list || fn:length(fg_list)<=0}">
                                                <li class="__section__item">
                                                    <h3 class="__section__item-title">
                                                        当前没有记录......
                                                    </h3>
                                                </li>
                                            </c:if>
                                        </ul>     
                                    </div>
                                    <!-- tab1 -->                                
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- 区块 -->

                </div>
            </div>

        </div>
    </div>

<script src="/js/custom/jquery-1.8.3.min.js"></script>
<script src="/js/custom/common.js"></script>
<script>
    $(function(){
        $('.oa-tab').oaTab(function(index){
            var $items = $('.test-wrap').find('.__section__more');

            $items.hide();
            $items.eq(index).show();
        });
    });
</script>
</body>
</html>