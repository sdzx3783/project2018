<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>邮件</title>
<%@include file="/commons/include/get.jsp" %>
<script src="${ctx}/js/lg/plugins/ligerLayout.js"></script>
<script>
	$(function(){
		$("div.group > a.link.del").unbind("click");
		delSelect();
	});
	function emailsync(type){
		var outMailSetId=$('#outMailSetId').val();
		
		$.ligerDialog.waitting("正在同步,请耐心等候...");
		$.post("sync.ht",{id:outMailSetId,types:type},function(data){
			$.ligerDialog.closeWaitting();
			var obj=new com.hotent.form.ResultMessage(data);
			if(obj.isSuccess()){
				$.ligerDialog.success(obj.getMessage(),'成功',function(){
					window.location.href="list.ht?id="+outMailSetId+"&types=1";
				});
			}else{
				
				$.ligerDialog.err('出错信息',"同步邮件出错!",obj.getMessage());
			}
			
		});
	}
	
	function delSelect(){
		$("#deleteBtn").click(function(){
			var outMailSetId=$("#outMailSetId").val();
			var type=$("#types").val();
			var tip="";
			var action=$(this).attr("action");
			var $aryId = $("input[type='checkbox'][disabled!='disabled'][class='pk']:checked");
			if($aryId.length==0){
				$.ligerDialog.warn("请选择记录！");
				return false;
			}
			var delId="";
			var len=$aryId.length;
			$aryId.each(function(i){
				var obj=$(this);
				if(i<len-1){
					delId+=obj.val() +",";
				}else{
					delId+=obj.val();
				}
			});
			var url=action +"?mailId=" +delId +"&outMailSetId="+outMailSetId+"&types="+type;
			if(type==1){
				tip="确认要将邮件移至垃圾箱？";
			}else{
				tip='确认要彻底删除邮件吗？';
			}
			$.ligerDialog.confirm(tip,'提示信息',function(rtn) {
				if(rtn) {
					var form=new com.hotent.form.Form();
					form.creatForm("form", action);
					form.addFormEl("mailId", delId);
					form.addFormEl("outMailSetId",outMailSetId);
					form.addFormEl("types",type);
					form.submit();
				}
			});
			return false;
		});
	}
		
	function emailedit(type){
		var defaultMail='${outMailUserSet.userName}';
		if(defaultMail==null||defaultMail.trim()==""){
			$.ligerDialog.confirm("请先设置默认邮箱<br/>或者在'邮箱配置管理里设置'!","提示信息",function(rtn){
				if(rtn){
					window.location.href="../../mail/outMailUserSeting/edit.ht";
				}
			});
		}else{
			window.location.href="edit.ht";
		}
	}
</script>
</head>
<body class="oa-mw-page">

	<input name="outMailSetId" id="outMailSetId" type="hidden" value="${outMailUserSet.id}"/>
	<input name="types" id="types" type="hidden" value="${types}"/>

    <div id="oa_list_title" class="oa-mgb20 oa-project-title">
	    <h2>
	    	${outMailUserSet.userName}(${outMailUserSet.mailAddress})
			<c:choose>
				<c:when test="${types==1}">收件箱</c:when>
				<c:when test="${types==2}">发件箱</c:when>
				<c:when test="${types==3}">草稿箱</c:when>
				<c:otherwise>垃圾箱</c:otherwise>
			</c:choose>
	    </h2>
	</div>

    <div id="oa_list_operation" class="oa-pdb20 oa-mgh20 oa-mgl40">
		<c:if test="${types==1}">
			<a class="oa-button oa-button--primary oa-button--blue" onclick="emailedit(${types})">写邮件</a>
			<a class="oa-button oa-button--primary oa-button--blue" onclick="emailsync()"" id="emailsync">收邮件</a>
		</c:if>
		<a class="oa-button oa-button--primary oa-button--blue"  id="deleteBtn" action="del.ht">删除</a>
	</div>

    <div id="oa_list_search" class="oa-pdb10 oa-mgh20">
        <form method="post" action="list.ht" class="oa-clear">
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">主题</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input type="text"  width="500" name="Q_title_SL"  class="oa-input"  value="${param['Q_title_SL']}"/>
                </div>
            </div>
            <c:if test="${types==1}">
	            <div class="oa-fl oa-mgb10">
	                <div class="oa-label">是否已读</div>
	                <div class="oa-input-wrap oa-mgl100">
	                    <select class="oa-select" name="Q_isRead_S" value="${param['Q_isRead_S']}">
                            <option value="">全部</option>
                            <option value="1" <c:if test="${param['Q_isRead_S'] == 1}">selected</c:if>>已读</option>
                            <option value="0" <c:if test="${param['Q_isRead_S'] == 0}">selected</c:if>>未读</option>
                        </select>
	                </div>
	            </div>
            </c:if>     
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">日期 从</div>
                <div class="oa-input-wrap oa-mgl100 oa-input-wrap--ib">
                    <input  name="Q_beginmailDate_DL"  class="oa-input date" value="${param['Q_beginmailDate_DL']}"/>
                </div>
                <span>至</span>
                <div class="oa-input-wrap oa-input-wrap--ib">
                    <input  name="Q_endmailDate_DG" class="oa-input date" value="${param['Q_endmailDate_DG']}"/>
                </div>
            </div>
            <div class="oa-fl oa-mgb10 oa-mgh20">
                <button class="oa-button oa-button--primary oa-button--blue">查询</button>
            </div>

            <input type="hidden" name="id" value="${outMailSetId}"/>
			<input type="hidden" name="types" value="${types}"/>
        </form>
    </div>

    <div class="oa-pdb20 oa-mgh20">
	    <div id="oa_list_content" class="oa-table-scroll-wrap">

			<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>

		    <display:table name="outMailList" id="outMailItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" export="false"  class="oa-table--default oa-table--nowrap">
				<display:column title="${checkAll}" media="html">
					<input type="checkbox" class="pk" name="mailId" value="${outMailItem.mailId}">
				</display:column>
				<display:column title="主题" sortable="true" sortName="title" href="get.ht" paramId="mailId" paramProperty="mailId">
					<a class="tirggerCollapse"  href="get.ht?mailId=${outMailItem.mailId}&outMailSetId=${outMailSetId}">${outMailItem.title}</a>
				</display:column>
				<display:column property="senderAddresses" title="发件人" sortable="true" sortName="senderAddresses"></display:column>
				<display:column  title="日期" sortable="true" sortName="mailDate">
					<fmt:formatDate value="${outMailItem.mailDate}" pattern="yyyy-MM-dd"/>
				</display:column>
				<c:if test="${types==1}">
					<display:column  title="是否已读" sortable="true" sortName="isRead">
				      <c:choose>
					   <c:when test="${outMailItem.isRead==0 }">
						<span class="red">未读</span>
					   </c:when>
					   <c:otherwise>
					   	<span class="green">已读</span>
					   </c:otherwise>
				       </c:choose>
			         </display:column>
		         </c:if>
				<display:column title="管理" media="html">
					<c:choose>
					 	<c:when test="${outMailItem.types==4 }">
							<a href="del.ht?mailId=${outMailItem.mailId}&outMailSetId=${outMailSetId}&types=${outMailItem.types}" id="delEnd" class="oa-button-label del">彻底删除</a>
						</c:when>
						<c:when test="${outMailItem.types==3 }">
							<a href="edit.ht?mailId=${outMailItem.mailId}&outMailSetId=${outMailSetId}&types=${outMailItem.types}" id="delEnd" class="oa-button-label edit">编辑</a>
							<a href="del.ht?mailId=${outMailItem.mailId}&outMailSetId=${outMailSetId}&types=${outMailItem.types}" class="oa-button-label del">删除</a>
						</c:when>
						<c:otherwise>
							<a href="del.ht?mailId=${outMailItem.mailId}&outMailSetId=${outMailSetId}&types=${outMailItem.types}" class="oa-button-label del">删除</a>
						</c:otherwise>
					</c:choose>
					<a href="get.ht?mailId=${outMailItem.mailId}&outMailSetId=${outMailSetId}" class="oa-button-label tirggerCollapse">查看</a>
				</display:column>
			</display:table>
	    </div>

		<hotent:paging tableId="outMailItem"/>
			
	</div>

<script>
$(function(){
	$('.tirggerCollapse').on('click', function(){
		window.parent.$(window).trigger('collapseClose');
	});
});
</script>
</body>
</html>


