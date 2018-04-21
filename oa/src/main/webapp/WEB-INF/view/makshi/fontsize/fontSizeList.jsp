<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>公文字号管理</title>
<%@include file="/commons/include/get.jsp" %>
<style>
    #fontSizeItem{
        margin-top: 0;
    }
</style>
</head>
<body>
    <div class="panel">
        <div class="panel-top">
            <div class="tbar-title">
                <span class="tbar-label">公文字号管理列表</span>
            </div>
            <!-- <div class="panel-toolbar">
                <div class="toolBar">
                    <div class="group"><a class="link search" id="btnSearch"><span></span>查询</a></div>
                    <div class="l-bar-separator"></div>
                    <div class="group"><a class="link add" href="edit.ht"><span></span>添加</a></div>
                    <div class="l-bar-separator"></div>
                    
                    
                    <div class="group"><a class="link update" id="btnUpd" action="edit.ht"><span></span>修改</a></div>
                    <div class="l-bar-separator"></div>
                    <div class="group"><a class="link del"  action="del.ht"><span></span>删除</a></div>
                </div>  
            </div>
            <div class="panel-search">
                <form id="searchForm" method="post" action="list.ht">
                    <div class="row">
                    </div>
                </form>
            </div> -->
        </div>
        <div class="panel-body">
            <c:set var="checkAll">
                <input type="checkbox" id="chkall"/>
            </c:set>
            <display:table name="fontSizeList" id="fontSizeItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="table-grid">
                <display:column title="${checkAll}" media="html" style="width:30px;">
                    <input type="checkbox" class="pk" name="id" value="${fontSizeItem.id}">
                </display:column>
                <display:column title="公文类型">
                    ${fontSizeItem.typeName}
                </display:column>
                <display:column title="发文年份">
                    ${fontSizeItem.dispatch_year}
                </display:column>
                <display:column title="公文字号">
                    ${fontSizeItem.font_size}
                </display:column>
                <display:column title="管理" media="html" style="width:220px">
                <%--    <a href="del.ht?id=${fontSizeItem.id}" class=""><span></span>删除</a> --%>
                    <a href="edit.ht?id=${fontSizeItem.id}" class=""><span></span>编辑</a>
                    <%-- <a href="get.ht?id=${fontSizeItem.id}" class="link detail"><span></span>明细</a> --%>
                </display:column>
            </display:table>
            <%-- <hotent:paging tableId="fontSizeItem"/> --%>
        </div><!-- end of panel-body -->                
    </div> <!-- end of panel -->
</body>
</html>


