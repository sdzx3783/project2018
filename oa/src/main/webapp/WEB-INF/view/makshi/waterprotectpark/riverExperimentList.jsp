<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>径流实验申请(水保示范园)管理</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body class="oa-mw-page">

    <div id="oa_list_title" class="oa-mgb20 oa-project-title">
	    <h2>径流实验信息管理列表</h2>
	</div>

    <c:if test="${hasAccess}">
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
                <div class="oa-label">实验名称</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input type="text" name="Q_name_S"  class="oa-input" value="${param['Q_name_S'] }" />
                </div>
            </div>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">实验时间</div>
                <div class="oa-input-wrap oa-mgl100 oa-input-wrap--ib">
                    <input name="Q_beginexperiTime_DL" id="Q_beginexperiTime_DL" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'Q_endexperiTime_DL\');}'})" value="${param['Q_beginexperiTime_DL'] }"  class="oa-input Wdate" />
                </div>
                <span>至</span>
                <div class="oa-input-wrap oa-input-wrap--ib">
                    <input  name="Q_endexperiTime_DL" id="Q_endexperiTime_DL" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'Q_beginexperiTime_DL\');}'})" value="${param['Q_endexperiTime_DL'] }" class="oa-input Wdate" />
                </div>
            </div>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">取样时间</div>
                <div class="oa-input-wrap oa-mgl100 oa-input-wrap--ib">
                    <input  name="Q_beginsamlpeTime_DL" id="Q_beginsamlpeTime_DL" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'Q_endsamlpeTime_DL\');}'})" value="${param['Q_beginsamlpeTime_DL'] }"  class="oa-input Wdate" />
                </div>
                <span>至</span>
                <div class="oa-input-wrap oa-input-wrap--ib">
                    <input  name="Q_endsamlpeTime_DL" id="Q_endsamlpeTime_DL" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'Q_beginsamlpeTime_DL\');}'})" value="${param['Q_endsamlpeTime_DL'] }" class="oa-input Wdate" />
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
		    <display:table name="riverExperimentList" id="riverExperimentItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
				<display:column title="${checkAll}" media="html">
			  		<input type="checkbox" class="pk" name="id" value="${riverExperimentItem.id}">
				</display:column>
				
				<display:column  title="实验名称">
					${riverExperimentItem.name}
				</display:column>
				<display:column title="实验人员">
					${riverExperimentItem.experimenter}
				</display:column>
				<display:column title="实验时间">
					<fmt:formatDate value="${riverExperimentItem.experiTime}" pattern="yyyy-MM-dd HH:mm:ss" type="date" dateStyle="long" />
				</display:column>
				<display:column title="取样时间">
					<fmt:formatDate value="${riverExperimentItem.samlpeTime}" pattern="yyyy-MM-dd HH:mm:ss" type="date" dateStyle="long" />
				</display:column>
				<display:column title="取样人员">
					${riverExperimentItem.sampleMan}
				</display:column>
				<display:column title="管理" media="html">
					<c:if test="${hasAccess }">
						<a href="edit.ht?id=${riverExperimentItem.id}" class="oa-button-label edit">编辑</a>
						<a href="del.ht?id=${riverExperimentItem.id}" class="oa-button-label oa-button-label-remove">删除</a>
					</c:if>
					<a href="get.ht?id=${riverExperimentItem.id}" class="oa-button-label detail">明细</a>
				</display:column>
			</display:table>
	    </div>

	    <hotent:paging tableId="riverExperimentItem"/>

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


