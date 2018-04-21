<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
    <head>
        <title>个人荣誉管理</title>
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
    	$(".oa-project-content").on("click","a.link del",function(){
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

        <div id="oa_list_title" class="oa-mgb10 oa-project-title">
<!--             <h2>个人荣誉列表</h2> -->
        </div>
        <div id="oa_list_operation" class="oa-mgh20 oa-mgl20 ${addFlag?'':'hidden' }">
            <button type="button" class="oa-button oa-button--primary oa-button--blue" onclick="window.location = 'edit.ht'">新增</button>
        </div>                           
        <div id="oa_list_search" class="oa-pdb10 oa-mgh20">
			<div class="oa-accordion">
            	<div class="oa-accordion__title">查询条件</div>
            	<div class="oa-accordion__content">           		
		            <form id="searchForm" method="post" action="list.ht" class="oa-clear">
		                <div class="oa-fl oa-mgb10">
		                    <div class="oa-label">姓名</div>
		                    <div class="oa-input-wrap oa-mgl100">
		                        <input class="oa-input" name="name" value="${param['name']}"/>
		                    </div>
		                </div>
		                 
		                <div class="oa-fl oa-mgb10">
		                    <div class="oa-label">工号</div>
		                    <div class="oa-input-wrap oa-mgl100">
		                        <input class="oa-input"  name="user_num" value="${param['user_num']}"/>
		                    </div>
		                </div>
		                <div class="oa-fl oa-mgb10">
		                    <div class="oa-label">员工荣誉编号</div>
		                    <div class="oa-input-wrap oa-mgl100">
		                        <input class="oa-input" name="honor_num" value="${param['honor_num']}"/>
		                    </div>
		                </div>
		               
		                <div class="oa-fl oa-mgb10">
		                    <div class="oa-label">奖项名称</div>
		                    <div class="oa-input-wrap oa-mgl100">
		                        <input class="oa-input" name="honor_name" value="${param['honor_name']}"/>
		                    </div>
		                </div>
		               
		
		                <div class="oa-fl oa-mgb10 oa-mgh20">
		                    <button class="oa-button oa-button--primary oa-button--blue">查询</button>
		                </div>
		            </form>
		        </div>
			</div>
        </div>


        <div class="oa-pdb20 oa-mgh20">
            <div id="oa_list_content" class="oa-table-scroll-wrap">
                <display:table name="personalHonorList" id="personalHonorItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
                    <display:column title="员工荣誉编号">${personalHonorItem.honor_num}</display:column>
                    <display:column title="姓名">${personalHonorItem.name}</display:column>
                    <display:column title="工号">${personalHonorItem.user_num}</display:column>
                    <display:column title="奖项名称">${personalHonorItem.honor_name}</display:column>
                    <display:column title="奖项级别">${personalHonorItem.honor_level}</display:column>
                    <display:column title="颁发机构">${personalHonorItem.issuing_authority}</display:column>
                    <display:column title="颁发日期"><fmt:formatDate value='${personalHonorItem.issue_date}' pattern='yyyy-MM-dd'/></display:column>
                    <display:column title="备注">${personalHonorItem.remark}</display:column>
                    <display:column title="查询网站">${personalHonorItem.query_url}</display:column>
                    
                        <display:column title="管理" media="html">
                        <c:if test="${personalHonorItem.isEditers }">
                            <a href="del.ht?id=${personalHonorItem.id }" class="oa-button-label oa-button-label-remove" >删除</a>
                            <a href="edit.ht?id=${personalHonorItem.id}" class="oa-button-label">编辑</a>
                        </c:if>
                        </display:column>
                    
                </display:table>
            </div>

            <hotent:paging tableId="personalHonorItem"/>
        </div>
<script>
    $(function(){
    	$('.oa-accordion').oaAccordion({collapse: true});
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