<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>阶段库</title>
<%@include file="/commons/include/get.jsp"%>
<%@ taglib prefix="hotent" uri="http://www.jee-soft.cn/paging" %>
<script type="text/javascript">
    function addStage(){
        $.ligerDialog.open({ url: 'edit.ht',title:'新建阶段',width:500,height: 400, isResize:true});
        return false;
    }
    
    function editStage(stageno){
        $.ligerDialog.open({ url: 'edit.ht?stageno='+stageno,title:'编辑阶段',width:500,height: 400, isResize:true});
        return false;
    }
    
</script>
<style>
	.my-jdk .l-dialog-content {
		overflow: hidden;
	}
</style>
</head>
<body class="oa-mw-page my-jdk">

    <div id="oa_list_title" class="oa-mgb20 oa-project-title">
       <!--  <h2>阶段库列表</h2> -->
    </div>

    <div id="oa_list_operation" class="oa-pdb20 oa-mgh20 oa-mgl40">
        <button class="oa-button oa-button--primary oa-button--blue" onclick="return addStage()">新建阶段</button>
    </div>

    <div id="oa_list_search" class="oa-pdb10 oa-mgh20">
        <form id="searchForm" method="post" action="list.ht" class="oa-clear">
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">所属部门</div>
                <div class="oa-input-wrap oa-mgl100">
                    <select class="oa-select" name="createorgid">
                        <option value="">请选择部门</option>
                        <c:forEach items="${orgs}" var="org" >
                            <option value="${org.orgId }" <c:if test="${param['createorgid']==org.orgId}">selected="selected"</c:if>>${org.orgName}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">阶段名称</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input class="oa-input" type="text" name="Q_stagename_SL" value="${param['Q_stagename_SL'] }">
                </div>
            </div>

            <div class="oa-fl oa-mgb10 oa-mgh20">
                <button class="oa-button oa-button--primary oa-button--blue">查询</button>
            </div>
        </form>

    </div>

    <div class="oa-pdb20 oa-mgh20">

        <c:set var="checkAll">
            <input type="checkbox" id="chkall"/>
        </c:set>
        <div id="oa_list_content" class="oa-table-scroll-wrap">
            <display:table name="stagelibList" id="stagelib" requestURI="list.ht" sort="external" cellpadding="0" cellspacing="0" class="oa-table--default oa-table--nowrap">
                <display:column property="stagename" title="阶段名称" sortable="false" ></display:column>
                <display:column property="stageno" title="阶段编号"  sortable="false" ></display:column>
                <display:column property="createorg" title="所属部门" sortable="false" ></display:column>
                <display:column property="tasknum" title="包含任务数" sortable="false" ></display:column> 
                <display:column property="remark" title="备注" sortable="false" ></display:column> 
                <display:column title="操作" media="html">
                    <a class="oa-button-label" href="toset.ht?stageno=${stagelib.stageno }">配置</a>
                    <a class="oa-button-label" href="javascript:;" onclick="editStage(${stagelib.stageno })">编辑</a>
                    <a class="oa-button-label oa-button-label-remove" href="delstage.ht?stageno=${stagelib.stageno }">删除</a>
                </display:column>
            </display:table>
        </div>
        <hotent:paging tableId="stagelib"/> 
    </div>

<script>
    $(function(){
        //处理删除一行
        $(".oa-button-label-remove").click(function(){
            if($(this).hasClass('disabled')) return false;
            
            var ele = this;
            $.ligerDialog.confirm('确认删除吗？','提示信息',function(rtn) {
                if(rtn) {
                    if(ele.click) {
                        $(ele).unbind('click');
                        ele.click();
                    } else {
                        location.href=ele.href;
                    }
                }
            });
            
            return false;
        });
    });
</script>
</body>
</html>


