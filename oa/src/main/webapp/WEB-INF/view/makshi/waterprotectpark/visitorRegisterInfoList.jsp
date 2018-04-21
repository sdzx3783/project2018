<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>参观登记信息-水保园管理</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body class="oa-mw-page">

    <div id="oa_list_title" class="oa-mgb20 oa-project-title">
        <h2>团体参观登记信息管理列表</h2>
    </div>
    
    <c:if test="${hasAccess }">
        <div id="oa_list_operation" class="oa-pdb20 oa-mgh20 oa-mgl40">
            <div class="oa-top-group">
                <a class="oa-button oa-button--primary oa-button--blue add" href="edit.ht">添加</a>
                <a class="oa-button oa-button--primary oa-button--blue update" id="btnUpd" action="edit.ht">修改</a>
                <a class="oa-button oa-button--primary oa-button--blue del"  action="del.ht">删除</a>
            </div>
        </div>
    </c:if>

    <div id="oa_list_search" class="oa-pdb10 oa-mgh20">
        <form id="searchForm" method="post" action="list.ht" class="oa-clear">
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">批次</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input type="text" name="Q_batch_S"  class="oa-input"  value="${param['Q_batch_S'] }"/>
                </div>
            </div>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">参观日期</div>
                <div class="oa-input-wrap oa-mgl100 oa-input-wrap--ib">
                    <input  name="Q_beginvisitTime_DL" id="Q_beginvisitTime_DL" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'Q_endvisitTime_DL\');}'})" class="oa-input Wdate" value="${param['Q_beginvisitTime_DL'] }" />
                </div>
                <span>至</span>
                <div class="oa-input-wrap oa-input-wrap--ib">
                    <input  name="Q_endvisitTime_DL" id="Q_endvisitTime_DL" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'Q_beginvisitTime_DL\');}'})" class="oa-input Wdate" value="${param['Q_endvisitTime_DL'] }" />
                </div>
            </div>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">登记时间</div>
                <div class="oa-input-wrap oa-mgl100 oa-input-wrap--ib">
                    <input  name="Q_beginregisterTime_DL" id="Q_beginregisterTime_DL" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'Q_endregisterTime_DL\');}'})"  class="oa-input Wdate" value="${param['Q_beginregisterTime_DL'] }"/>
                </div>
                <span>至</span>
                <div class="oa-input-wrap oa-input-wrap--ib">
                    <input  name="Q_endregisterTime_DL" id="Q_endregisterTime_DL" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'Q_beginregisterTime_DL\');}'})" class="oa-input Wdate" value="${param['Q_endregisterTime_DL'] }"/>
                </div>
            </div>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">团体名称</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input type="text" name="Q_teamName_S"  class="oa-input" value="${param['Q_teamName_S'] }"/>
                </div>
            </div>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">负责人</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input type="text" name="Q_charger_S"  class="oa-input" value="${param['Q_charger_S'] }"/>
                </div>
            </div>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">接待员</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input type="text" name="Q_greeter_S"  class="oa-input" value="${param['Q_greeter_S'] }"/>
                </div>
            </div>

            <div class="oa-fl oa-mgb10 oa-mgh20">
                <button class="oa-button oa-button--primary oa-button--blue">查询</button>
            </div>
        </form>
    </div>


    <div class="oa-pdb20 oa-mgh20">
        <div id="oa_list_content" class="oa-table-scroll-wrap">

            <c:set var="checkAll">
                <input type="checkbox" id="chkall"/>
            </c:set>
            <display:table name="visitorRegisterInfoList" id="visitorRegisterInfoItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
                <display:column title="${checkAll}" media="html">
                    <input type="checkbox" class="pk" name="id" value="${visitorRegisterInfoItem.id}">
                </display:column>
                <display:column title="批次">
                    ${visitorRegisterInfoItem.batch}
                </display:column>
                <display:column title="团体名称">
                    ${visitorRegisterInfoItem.teamName}
                </display:column>
                <display:column title="参观日期">
                    <fmt:formatDate value="${visitorRegisterInfoItem.visitTime}" pattern="yyyy-MM-dd" type="date" dateStyle="long" />
                </display:column>
                <display:column title="星期">
                    ${visitorRegisterInfoItem.xq}
                </display:column>
                <display:column title="登记时间">
                    <fmt:formatDate value="${visitorRegisterInfoItem.registerTime}" pattern="yyyy-MM-dd" type="date" dateStyle="long" />
                </display:column>
                <display:column title="参观人数">
                    ${visitorRegisterInfoItem.visitorNum}
                </display:column>
                <display:column title="负责人">
                    ${visitorRegisterInfoItem.charger}
                </display:column>
                <display:column title="联系电话">
                    ${visitorRegisterInfoItem.tel}
                </display:column>
                <display:column title="接待员">
                    ${visitorRegisterInfoItem.greeter}
                </display:column>
                <display:column title="是否来函">
                    <c:if test="${visitorRegisterInfoItem.isLetter==1}" >是</c:if>
                    <c:if test="${visitorRegisterInfoItem.isLetter==0}" >否</c:if>
                </display:column>
                <display:column title="所属类别">
                    <c:if test="${visitorRegisterInfoItem.type==1}" >中小学生团体</c:if>
                    <c:if test="${visitorRegisterInfoItem.type==2}" >政府部门团体</c:if>
                </display:column>
                <display:column title="管理" media="html">
                    <c:if test="${hasAccess }">
                        <a href="edit.ht?id=${visitorRegisterInfoItem.id}" class="oa-button-label edit">编辑</a>
                        <a href="del.ht?id=${visitorRegisterInfoItem.id}" class="oa-button-label oa-button-label-remove">删除</a>
                    </c:if>
                    <a href="get.ht?id=${visitorRegisterInfoItem.id}" class="oa-button-label detail">明细</a>
                </display:column>
            </display:table>

        </div>
        <hotent:paging tableId="visitorRegisterInfoItem"/>

    </div>


<script>
//更新
function handlerUpd(){
    $(".oa-top-group a.update").click(function() {
        if($(this).hasClass('disabled')) return false;
        
        var action=$(this).attr("action");
        var aryId = $("input[type='checkbox'][disabled!='disabled'][class='pk']:checked");
        var len=aryId.length;
        if(len==0){
            $.ligerDialog.warn("还没有选择,请选择一项进行编辑!",'提示信息');
            return;
        }
        else if(len>1){
            $.ligerDialog.warn("已经选择了多项,请选择一项进行编辑!",'提示信息');
            return;
        }
        var name=aryId.attr("name");
        var value=aryId.val();
        var form=new com.hotent.form.Form();
        form.creatForm("form", action);
        form.addFormEl(name, value);
        form.submit();
        
    });
}


function handlerDelSelect()
{
    //单击删除超链接的事件处理
    $(".oa-top-group a.del").click(function()
    {   
        if($(this).hasClass('disabled')) return false;
        
        var action=$(this).attr("action");
        var $aryId = $("input[type='checkbox'][disabled!='disabled'][class='pk']:checked");
        
        if($aryId.length == 0){
            $.ligerDialog.warn("请选择记录！");
            return false;
        }
        
        //提交到后台服务器进行日志删除批处理的日志编号字符串
        var delId="";
        var keyName="";
        var len=$aryId.length;
        $aryId.each(function(i){
            var obj=$(this);
            if(i<len-1){
                delId+=obj.val() +",";
            }
            else{
                keyName=obj.attr("name");
                delId+=obj.val();
            }
        });
        var url=action +"?" +keyName +"=" +delId ;
        
        $.ligerDialog.confirm('确认删除吗？','提示信息',function(rtn) {
            if(rtn) {
                var form=new com.hotent.form.Form();
                form.creatForm("form", action);
                form.addFormEl(keyName, delId);
                form.submit();
            }
        });
        return false;
    
        
    });
}
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


