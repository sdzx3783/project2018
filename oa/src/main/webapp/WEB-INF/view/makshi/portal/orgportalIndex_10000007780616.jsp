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
    <!-- 办公室 -->
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
                            <h2 class="__section__title">自查月报 <span>Project Progress</span></h2>
                            <div class="__section__more" id="monthmainfieldMore" style="display: none;">
                                <a class="__section__link" href="/makshi/selfcheck/monthmain/main.ht" target="_blank">更多...</a>
                            </div>
                        </div>
                        <div class="__section__main __section__main--second">
                            <table class="__section__table">
                                <thead class="__section__table-head">
                                    <tr>
                                        <th style="width: 35%;">姓名</th>
                                        <th style="width: 25%;">时间</th>
                                        <th style="width: 15%;">状态</th>
                                        <!-- <th style="width: 15%;">管理</th> -->
                                    </tr>
                                </thead>
                                <tbody class="__section__table-tbody" id="monthmainfield">
                                    
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
                            <h2 class="__section__title">重点公务活动 <span>Official Business</span></h2>
                            <c:if test="${not empty zdgwhd_list && fn:length(zdgwhd_list)>=5}">
                                <div class="__section__more">
                                    <a class="__section__link" href="/makshi/doc/docinfo/filelist.ht?docId=${zdgwhdId}&portalOrgId=${orgId}&from=orgportalindex">更多...</a>
                                </div>
                            </c:if>
                        </div>
                        <div class="__section__main __section__main--normol" style="min-height: 548px;">
                            <ul class="__section__list">
                                <c:forEach items="${zdgwhd_list}" var="docFile">
                                    <li class="__section__item">
                                        <h3 class="__section__item-title">
                                            <a href="/makshi/doc/docinfo/filedetail.ht?id=${docFile.dfid}" class="__section__link">${docFile.title}</a>
                                        </h3>
                                        <span class="__section__item-date"><fmt:formatDate value='${docFile.createtime}' pattern='yyyy/MM/dd' /></span>
                                    </li>
                                </c:forEach>
                                <c:if test="${empty zdgwhd_list || fn:length(zdgwhd_list)<=0}">
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
        
        $.getJSON("/makshi/selfcheck/monthmain/json.ht",{},function(data){
        	if(data.result){
        		$.each(data.items,function(i,item){
        			var line = '<tr><td><a href="/makshi/selfcheck/monthmain/detail.ht?id='+item.id+'" target="_blank">' + item.username  + '</a></td><td><a href="/makshi/selfcheck/monthmain/detail.ht?id='+item.id+'" target="_blank">'+item.submitAt+'</a></td><td>'+(item.status==1?'未提交':'已提交')+'</td>';
        			/* if(item.status == 1){
        				line += '<td>'
							 +'	<a href="/makshi/selfcheck/monthmain/edit.ht?id=${e.id }" class="oa-button-label">编辑</a>'
							 +'	<a href="javascript:del(\'${e.id }\');" class="oa-button-label">删除</a>'
							 +'	<a href="javascript:submit(\'${e.id }\');" class="oa-button-label">提交</a>'
							 +'	</td>';
        			}else{
        				line += '<td></td>';
        			} */
        			line +='</tr>';
        			$("#monthmainfield").append(line);
        		});
        		if(data.items.length >= 1){
        			$("#monthmainfieldMore").css({"display":""});
        		}
        	}else{
        		$("#monthmainfield").append('<tr><td colspan="3" style="text-align: left;">当前没有记录...</td></tr>');
        	}
        });
    });
</script>
</body>
</html>