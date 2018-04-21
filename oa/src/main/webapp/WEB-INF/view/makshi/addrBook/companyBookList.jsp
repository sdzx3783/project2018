<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>公司通讯录</title>
<%@include file="/commons/include/get.jsp"%>
<style>
    .icon-location-arrow{
        margin-top: 3px;
        font-size: 16px;
    }
    .link.search{
        padding: 4px 15px;
        font-size: 14px;
        display: inline-block;
        line-height: 20px;
    }
    .table-grid td {
    	white-space: nowrap;
    }
    a.link.send{
        color: #fff;
        padding: 5px 10px;
        padding-left: 30px;
        border-radius: 3px;
        background: #478de4 url(/images/email_icon.png) 5px center no-repeat;
    }
    a.link.send + a{
        margin-left: 5px;
        background: #36bb5f url(/images/message_icon.png) 5px center no-repeat;
    }
    .panel-table{
            overflow-y: auto !important;
    }
    .link.output {
    	padding: 4px 15px;
	    font-size: 14px;
	    display: inline-block;
	    line-height: 20px;
    }
</style>
<%@ taglib prefix="hotent" uri="http://www.jee-soft.cn/paging" %>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">通讯录列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"></div>
					<div class="l-bar-separator"></div>
				</div>	
			</div>
            <a onclick="tosubmit('1')" class="link search button" id="btnSearch"><span>查询</span></a>
            <a onclick="tosubmit('2')" class="link output button"><span>导出</span></a>

			<div class="panel-search">
				<form id="searchForm" method="post" action="list.ht?orgId=${orgId}">
					<div class="row">
						<p>
						<span class="label">姓名:</span><input type="text" name="Q_fullname_SL" value="${param['Q_fullname_SL'] }"  class="inputText" />
						<span class="label">移动电话:</span><input type="text" name="Q_mobile_SL" value="${param['Q_mobile_SL'] }" class="inputText" />
						<span class="label" >办公电话:</span><select id="phone" name="phoneType" class="select" style="width:50px">
                                    <option value="0">包含</option>
                                    <option value="1" <c:if test="${param['phoneType']==1}">selected</c:if>>等于</option>
                                </select>
                                <input type="text" class="inputText" <c:choose><c:when test="${param['phoneType']==1}">name="phone"   value="${param['phone'] }"</c:when> 
                                			<c:otherwise>name="Q_phone_SL"  value="${param['Q_phone_SL'] }"</c:otherwise></c:choose>
                                style="width:150px"/>
                         <span class="label">短号:</span><input type="text" name="Q_sjdh_SL" value="${param['Q_sjdh_SL'] }" class="inputText" />
						</p>
						<p><span class="label">电子邮箱:</span><select id="email" name="emailType" class="select" style="width:50px">
                                    <option value="0">包含</option>
                                    <option value="1" <c:if test="${param['emailType']==1}">selected</c:if>>等于</option>
                                </select>
                        <input type="text"  class="inputText" <c:choose><c:when test="${param['emailType']==1}">name="email"   value="${param['email'] }"</c:when> 
                                			<c:otherwise>name="Q_email_SL"  value="${param['Q_email_SL'] }"</c:otherwise></c:choose>
                                style="width:150px"/>
						<span class="label">办公QQ:</span><select id="qq" name="qqType" class="select" style="width:50px">
                                    <option value="0">包含</option>
                                    <option value="1" <c:if test="${param['qqType']==1}">selected</c:if>>等于</option>
                                </select>
                        <input type="text"  class="inputText" <c:choose><c:when test="${param['qqType']==1}">name="qq"   value="${param['qq'] }"</c:when> 
                                			<c:otherwise>name="Q_qq_SL"  value="${param['Q_qq_SL'] }"</c:otherwise></c:choose> 
                                style="width:150px"/>
						<span class="label">工号:</span><input type="text" name="Q_account_SL" value="${param['Q_account_SL'] }" class="inputText" />
						</p>
					</div>
				</form>
			</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="companyBookList" id="companyBookItem" requestURI="list.ht?orgId=${orgId}" sort="external" cellpadding="1" cellspacing="1" class="table-grid">
				<%-- <display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${companyBookItem.id}">
				</display:column> --%>
				<display:column property="account" title="工号" sortable="false" sortName="userId"></display:column>
				<display:column property="userName" title="姓名"  sortable="false" sortName="userId"></display:column>
                        
				<display:column   title="性别" >
                            <c:choose>
                                <c:when test="${companyBookItem.sex==1}"><span class="">男</span></c:when>
                                <c:otherwise><span class="">女</span></c:otherwise>
                            </c:choose>
                 </display:column>
                 <display:column property="orgName" title="部门" sortable="false" sortName="orgId"></display:column>
                 <display:column property="department" title="项目部" sortable="false" sortName="orgId"></display:column>
                 <display:column property="posName" title="岗位" sortable="false" sortName="posId"></display:column> 
                 <display:column property="phone" title="办公电话" sortable="false" sortName="mobile"></display:column> 
                 <display:column property="sjdh" title="短号" sortable="false" sortName="sjdh"></display:column> 
                 <display:column property="mobile" title="手机" sortable="false" sortName="mobile"></display:column> 
				<display:column title="操作" media="html" style="width:220px">
					<a href="javascript:;" onclick="checkSendEmail('/platform/mail/outMail/edit.ht?receieveAddress=${companyBookItem.email}&receieveName=${companyBookItem.userName}&returnUrl=/makshi/addrBook/companyBook/list.ht?orgId=${orgId}');" class="link send"><span></span>发送邮件</a>
					<a href="javascript:;" class="link send" onclick="window.parent.$(window).trigger('collapseClose');window.location.href='/platform/system/messageSend/edit.ht?receiveId=${companyBookItem.userId}&returnUrl=/makshi/addrBook/companyBook/list.ht?orgId=${orgId}'"><span></span>发送消息</a>
				</display:column>
			</display:table>
			<hotent:paging tableId="companyBookItem"/>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
	<script type="text/javascript">
		$("#phone").change(function(){
			var se=$(this).val();
			if(se=='1'){
				$(this).next().attr("name","phone");
			}else{
				$(this).next().attr("name","Q_phone_SL");
			}
		});
		$("#email").change(function(){
			var se=$(this).val();
			if(se=='1'){
				$(this).next().attr("name","email");
			}else{
				$(this).next().attr("name","Q_email_SL");
			}
		});
		$("#qq").change(function(){
			var se=$(this).val();
			if(se=='1'){
				$(this).next().attr("name","qq");
			}else{
				$(this).next().attr("name","Q_qq_SL");
			}
		});
		
		function checkSendEmail(url){

            window.parent.$(window).trigger('collapseClose');
     
			$(this).removeAttr("onclick");
			$.get('getMailUserSetingCount.ht',function(data){
				var result=JSON.parse(data);
				if(result && result.message){
					var count=result.message;
					if(count && count>0){
						window.location.href=url;
					}else{
						$.ligerDialog.warn('没有添加邮箱，请先在【邮箱配置管理】里【添加邮箱】');
					}
				}else{
					$.ligerDialog.warn('服务忙，请稍后再试!');
				}
				$(this).attr("onclick","checkSendEmail(url)");
			});
		}
	    function tosubmit(str){
	    	var orgId = '${orgId}';
			if(1==str){
				$("#searchForm").attr("action","list.ht?orgId="+orgId);
				$("#searchForm").submit();
			}else if(2==str){
				if(confirm("确定导出")){
					$("#searchForm").attr("action","upload.ht?orgId="+orgId);
					$("#searchForm").submit();
				}
			}
		}
	</script>
</body>
</html>


