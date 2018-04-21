<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>公司资质证书管理</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body>

	<div id="oa_list_title" class="oa-mgb20 oa-project-title">
	    <!-- <h2>公司资质证书管理列表</h2> -->
	</div>

	<div id="oa_list_operation" class="oa-pdb20 oa-mgh20 oa-mgl20">
		<a class="oa-button oa-button--primary oa-button--blue add" href="edit.ht">添加</a>
		<a class="oa-button oa-button--primary oa-button--blue del"  action="del.ht">删除</a>
	</div>

    <div id="oa_list_search" class="oa-pdb10 oa-mgh20">
        <form id="searchForm" method="post" action="list.ht" class="oa-clear">
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">证书编号</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input type="text" name="Q_cno_S" class="oa-input" value='${Q_cno_S }'/>
                </div>
            </div>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">证书类型</div>
                <div class="oa-select-wrap oa-mgl100">
                	<select name="ctype" class="oa-select" class="inputText" >
						<option value="">--所有--</option>
						<option value="1" <c:if test='${ctype==1 }'>selected</c:if>>企业资质</option>
						<option value="2" <c:if test='${ctype==2 }'>selected</c:if>>施工监理</option>
						<option value="3" <c:if test='${ctype==3 }'>selected</c:if>>工程咨询</option>
						<option value="4" <c:if test='${ctype==4 }'>selected</c:if>>招标代理</option>
						<option value="5" <c:if test='${ctype==5 }'>selected</c:if>>造价咨询</option>
						<option value="6" <c:if test='${ctype==6 }'>selected</c:if>>水土保持</option>
						<option value="7" <c:if test='${ctype==7 }'>selected</c:if>>污水运营、环境</option>
						<option value="8" <c:if test='${ctype==8 }'>selected</c:if>>信息</option>
						<option value="9" <c:if test='${ctype==9 }'>selected</c:if>>测绘</option>
						<option value="10" <c:if test='${ctype==10 }'>selected</c:if>>施工</option>
					</select>
                </div>
            </div>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">证书名称</div>
                <div class="oa-input-wrap oa-mgl100">
                    <input type="text" name="Q_cname_S" class="oa-input" value='${Q_cname_S }' />
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
		    <display:table name="companyQualificationCertificateList" id="companyQualificationCertificateItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
				<display:column title="${checkAll}" media="html">
			  		<input type="checkbox" class="pk" name="id" value="${companyQualificationCertificateItem.id}">
				</display:column>
				<display:column property="cno" title="证书编号"></display:column>
				<display:column property="cname" title="证书名称" ></display:column>
				<display:column title="证书类型">
					<c:choose>
						<c:when test="${companyQualificationCertificateItem.ctype=='1' }">企业资质</c:when>
						<c:when test="${companyQualificationCertificateItem.ctype=='2' }">施工监理</c:when>
						<c:when test="${companyQualificationCertificateItem.ctype=='3' }">工程咨询</c:when>
						<c:when test="${companyQualificationCertificateItem.ctype=='4' }">招标代理</c:when>
						<c:when test="${companyQualificationCertificateItem.ctype=='5' }">造价咨询</c:when>
						<c:when test="${companyQualificationCertificateItem.ctype=='6' }">水土保持</c:when>
						<c:when test="${companyQualificationCertificateItem.ctype=='7' }">污水运营、环境</c:when>
						<c:when test="${companyQualificationCertificateItem.ctype=='8' }">信息</c:when>
						<c:when test="${companyQualificationCertificateItem.ctype=='9' }">测绘</c:when>
						<c:when test="${companyQualificationCertificateItem.ctype=='10' }">施工</c:when>
						<c:otherwise></c:otherwise>
					</c:choose>
				</display:column>
				<display:column property="institution" title="发证机构"></display:column>
				<display:column  title="发证时间" sortable="true" sortName="F_CERTIFICATIONTIME">
					<fmt:formatDate value="${companyQualificationCertificateItem.certificationtime}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column  title="有效期限" sortable="true" sortName="F_CERTIFICATIONLIMIT">
					<fmt:formatDate value="${companyQualificationCertificateItem.certificationlimit}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column  title="状态" sortable="true" sortName="F_CERTIFICATIONLIMIT">
						${companyQualificationCertificateItem.statusDesc }
				</display:column>
				<display:column  title="借用状态" sortable="true" sortName="F_CERTIFICATIONLIMIT">
						<c:choose>
							<c:when test="${companyQualificationCertificateItem.isborrowed==1 }">已借出</c:when>
							<c:otherwise>未借出</c:otherwise>
						</c:choose>
				</display:column>
				<display:column title="管理" media="html">
					<a href="del.ht?id=${companyQualificationCertificateItem.id}" class="oa-button-label del">删除</a>
					<a href="edit.ht?id=${companyQualificationCertificateItem.id}" class="oa-button-label edit">编辑</a>
				</display:column>
			</display:table>
	    </div>

		<hotent:paging tableId="companyQualificationCertificateItem"/>

	</div>
</body>
</html>


