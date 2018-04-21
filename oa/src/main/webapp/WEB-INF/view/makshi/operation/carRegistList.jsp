<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
    <head>
        <title>车辆登记管理</title>
        <%@include file="/commons/include/get.jsp" %>
        <f:link href="Aqua/css/ligerui-all.css" ></f:link>
        <f:link href="tree/zTreeStyle.css" ></f:link>
        <script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
        <script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
        <script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
        
        <script type="text/javascript" src="${ctx}/js/lg/newligerui.all.js" ></script>
        <script type="text/javascript" src="${ctx}/js/tree/jquery.ztree.js"></script>
        <script type="text/javascript" src="${ctx}/js/lg/plugins/ligerComboBox.js"></script>
        <script type="text/javascript" src="${ctx}/js/lg/plugins/htDicCombo.js"></script>
        
        <script type="text/javascript" src="${ctx}/js/jquery/jquery.form.js"></script>
        <script type="text/javascript" src="${ctx}/js/lg/plugins/ligerDrag.js" ></script>
        <script type="text/javascript" src="${ctx}/js/lg/plugins/newligerDialog.js" ></script>
        <script type="text/javascript" src="${ctx}/js/lg/plugins/ligerResizable.js" ></script>
        <%@include file="/commons/include/ueditor.jsp" %>
        <script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
        <script type="text/javascript" src="${ctx}/js/lg/util/DialogUtil.js" ></script>
        <script type="text/javascript" src="${ctx}/js/hotent/platform/form/SelectorInit.js"></script>
        <script type="text/javascript" src="${ctx}/js/hotent/ajaxgrid.js"></script>
        <script type="text/javascript" src="${ctx}/js/lg/util/DialogUtil.js" ></script>
    </head>
    <script type="text/javascript">
    $(function(){
        $(".oa-project-content").on("click","a.oa-del",function(){
            if($(this).hasClass('disabled')) return false;
            var ele = this;
            $.ligerDialog.confirm('确认删除吗？','提示信息',function(rtn) {
                if(rtn) {
                if(ele.click) {
                    window.location.href=ele.href;
                    } else {
                    location.href=ele.href;
                }
                }
            });
            return false;
            });
    });
    </script>
    <body class="oa-mw-page">

        <div id="oa_list_title" class="oa-mgb20 oa-project-title">
            <h2>车辆信息列表</h2>
        </div>
        
        <c:if test="${isEditer}">
            <div id="oa_list_operation" class="oa-pdb20 oa-mgh20 oa-mgl40">
                <button type="button" class="oa-button oa-button--primary oa-button--blue" onclick="window.location = 'edit.ht'">新增</button>
            </div>
        </c:if>
        
        <div id="oa_list_search" class="oa-pdb10 oa-mgh20">

            <form id="searchForm" method="post" action="list.ht" class="oa-clear">
                <div class="oa-fl oa-mgb10">
                    <div class="oa-label">车牌号</div>
                    <div class="oa-input-wrap oa-mgl100">
                        <input class="oa-input" name="carId" value="${param['carId']}"/>
                    </div>
                </div>
                <div class="oa-fl oa-mgb10">
                    <div class="oa-label">车辆型号</div>
                    <div class="oa-input-wrap oa-mgl100">
                        <input class="oa-input"  name="version" value="${param['version']}"/>
                    </div>
                </div>
                <div class="oa-fl oa-mgb10">
                    <div class="oa-label">项目部</div>
                    <div class="oa-input-wrap oa-mgl100">
                        <input class="oa-input" name="department" value="${param['department']}"/>
                    </div>
                </div>
                <div class="oa-fl oa-mgb10">
                    <div class="oa-label">保管人</div>
                    <div class="oa-input-wrap oa-mgl100">
                        <input class="oa-input" name="kepper" value="${param['kepper']}"/>
                    </div>
                </div>

                <div class="oa-fl oa-mgb10 oa-mgh20">
                    <button class="oa-button oa-button--primary oa-button--blue">查询</button>
                </div>
            </form>

        </div>


        <div class="oa-pdb20 oa-mgh20">

            <div id="oa_list_content" class="oa-table-scroll-wrap">
                <display:table name="carRegistList" id="carRegistItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
                    <display:column title="车牌号">${carRegistItem.carId}</display:column>
                    <display:column title="车辆型号">${carRegistItem.version}</display:column>
                    <display:column title="项目部">${carRegistItem.department}</display:column>
                    <display:column title="保管人">${carRegistItem.kepper}</display:column>
                    <display:column title="加油卡">${carRegistItem.oil_card}</display:column>
                    <display:column title="粤通卡">${carRegistItem.pass_card}</display:column>
                    <c:if test="${isEditer }">
                        <display:column title="管理" media="html">
                            <a href="del.ht?id=${carRegistItem.id }" class="oa-del oa-button-label" >删除</a>
                            <a href="edit.ht?id=${carRegistItem.id}" class="oa-button-label">编辑</a>
                        </display:column>
                    </c:if>
                </display:table>
            </div>

            <hotent:paging tableId="carRegistItem"/>
        </div>
</body>
</html>