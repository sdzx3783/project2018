
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>工时填报申请明细</title>
<%@include file="/commons/include/customForm.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
<script type="text/javascript">
	//放置脚本
</script>
<style>
    .oa-input-wrap.select{
        position: relative;
        margin-right: 50px;
    }
    .link.org{
        position: absolute;
        right: -50px;
        top: 7px;
    }
    .file-prefer label {
    	color: #657386;
    }
	.showFileName {
		margin-left: 20px;
	}
	.my-file input {
	    position: absolute;
	    display: block;
	    width: 100%;
	    height: 100%;
	    right: 0;
	    top: 0;
	    opacity: 0;
	    font-size: 0;
	}
	.vertical{ margin:0 auto;width:10px;line-height:24px;border:1px solid #333} 
	td,th{
		text-align:center;
		width: 10px;
	}
	.oa-table-scroll-wrap {
		overflow-x: visible;
	}
	.oa-pdb20{
		margin:20;
		padding:10;
	}
	.notscroll{
 		word-break: break-all;
	}
	.oa-table--nowrap th, .oa-table--nowrap td{
		white-space: nowrap;
	}
	div.remarkdiv{
		max-width:200px;word-break:break-all;
	}
	td.title{
		 font-size:20px
	}
	.oa-table-scroll-wrap{
		border:0
	}
	.oa-table--default{
		    border-width: 1px;
    	border-style: solid;
    	border-color: rgb(236, 239, 248);
    	border-image: initial;
	}
	.oa-table--default th, .oa-table--default td{
		padding: 1px 2px;
	}
</style>
</head>
<body>
	<div id="oa_list_title" class="oa-project-title">
	</div>
	<div id="oa_list_search" class="oa-pd20 my-condition">
        <form id="searchForm" method="post" action="orgStatsCount.ht" class="oa-clear">
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">月份</div>
                <div class="oa-input-wrap oa-mgl100">
                   <input id="queryMonth" name="queryMonth" type="text" datefmt="yyyy-MM" value="${queryMonth }" class="oa-input Wdate" />
                </div>
            </div>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">部门</div>
                <div class="oa-input-wrap select oa-mgl100">
                    <input name="org"  type="text" required="required" ctltype="selector" class="org oa-input"  value="${org}" initValue="${orgID}" validate="{empty:false}" readonly="readonly" />
                </div>
            </div>
            <div class="oa-fl oa-mgb10 oa-mgh20 my-options">
                <button type="button" onclick="queryFunc()" class="oa-button oa-button--primary oa-button--blue">查询</button>
            	<button type="button" id="exportBtn" class="oa-button oa-button--primary oa-button--blue">导出</button>
            </div>
        </form>
    </div>
    <c:if test="${not empty errorMsg}">
    	<div class="oa-pdb20 oa-mgh20" style="text-align:center;color:red;font-weight:900;">${errorMsg }</div>
    </c:if>
    <c:if test="${not empty workStatsCount}">
	    <div class="oa-pdb20 oa-mgh20">
			<div class="oa-table-scroll-wrap">
				<table  class="oa-table--default oa-table--nowrap table-list">
					<thead><tr><td colspan="14" class="title"> 深水咨询公司</td> </tr></thead>
					<tr>
					<th>部门</th>
					<th>项目部</th>
					<th>序号</th>
					<th>编号</th>
					<th>姓名</th>
					<th>出勤（天）</th>
					<th>年休（天）</th>
					<th>事假（天）</th>
					<th>婚假、产假、丧假等（天）</th>
					<th>旷工（天）</th>
					<th>培训出差（天）</th>
					<th>病假（天）</th>
					<th>法定假日加班（小时）</th>
					<th><div class="remarkdiv">备注</div></th>
					</tr>
					<c:set var="idx" value="0"></c:set>
					<c:if test="${isareaLevelOrg}">
						<c:forEach items="${workStatsCount }" varStatus="vs" var="workDto">
							<c:forEach items="${workDto.workcounts }" varStatus="vs1" var="workcount">
								<c:set var="idx" value="${idx+1 }"></c:set>
								<tr>
								<c:if test="${vs1.index==0 }">
									<td rowspan="${workDto.rowspan }" class="notscroll">${workDto.orgname }</td>
								</c:if>
								<c:if test="${vs1.index==0 }">
									<td rowspan="${workDto.rowspan }" class="notscroll">${workDto.workcounts.get(0).orgname }</td>
								</c:if>
									<td>${idx }</td>
									<td>${workcount.account }</td>
									<td>${workcount.username }</td>
									<td><fmt:formatNumber value="${workcount.workcount }" pattern="##.#"/></td>
									<td><fmt:formatNumber value="${workcount.yearleave }" pattern="##.#"/></td>
									<td><fmt:formatNumber value="${workcount.personalleave }" pattern="##.#"/></td>
									<td><fmt:formatNumber value="${workcount.otherleave }" pattern="##.#"/></td>
									<td><fmt:formatNumber value="${workcount.absence }" pattern="##.#"/></td>
									<td></td>
									<td><fmt:formatNumber value="${workcount.sickleave}" pattern="##.#"/></td>
									<td>${workcount.overhours }</td>
									<td class="notscroll"><div class="remarkdiv">${workcount.remark }</div></td>
								</tr>
							</c:forEach>
							
						</c:forEach>
					</c:if>
					<c:if test="${!isareaLevelOrg}">
						<c:forEach items="${workStatsCount }" varStatus="vs" var="workDto">
						<c:forEach items="${workDto.workcounts }" varStatus="vs1" var="workcount">
							<c:set var="idx" value="${idx+1 }"></c:set>
							<tr>
							<c:if test="${vs1.index==0 }">
								<td rowspan="${workDto.rowspan }" class="notscroll" colspan="${workDto.colspan }">${workDto.orgname }</td>
							</c:if>
							<c:if test="${vs1.index==0 }">
								<c:if test="${workDto.colspan!=2 }"><td rowspan="${workDto.workcounts.size() }"></td></c:if>
							</c:if>
								<td>${idx }</td>
								<td>${workcount.account }</td>
								<td>${workcount.username }</td>
								<td><fmt:formatNumber value="${workcount.workcount }" pattern="##.#"/></td>
								<td><fmt:formatNumber value="${workcount.yearleave }" pattern="##.#"/></td>
								<td><fmt:formatNumber value="${workcount.personalleave }" pattern="##.#"/></td>
								<td><fmt:formatNumber value="${workcount.otherleave }" pattern="##.#"/></td>
								<td><fmt:formatNumber value="${workcount.absence }" pattern="##.#"/></td>
								<td></td>
								<td><fmt:formatNumber value="${workcount.sickleave}" pattern="##.#"/></td>
								<td>${workcount.overhours }</td>
								<td class="notscroll"><div class="remarkdiv">${workcount.remark }</div></td>
							</tr>
						</c:forEach>
						<c:forEach items="${workDto.subworkorg}" var="subDto">
							<c:forEach items="${subDto.workcounts }" varStatus="subvs" var="subworkcount">
								<c:set var="idx" value="${idx+1 }"></c:set>
							<tr>
								<c:if test="${subvs.index==0 }"><td rowspan="${subDto.workcounts.size() }" class="notscroll">${subDto.orgname}</td></c:if>
								<td>${idx}</td>
								<td>${subworkcount.account }</td>
								<td>${subworkcount.username }</td>
								<td><fmt:formatNumber value="${subworkcount.workcount }" pattern="##.#"/></td>
								<td><fmt:formatNumber value="${subworkcount.yearleave }" pattern="##.#"/></td>
								<td><fmt:formatNumber value="${subworkcount.personalleave }" pattern="##.#"/></td>
								<td><fmt:formatNumber value="${subworkcount.otherleave }" pattern="##.#"/></td>
								<td><fmt:formatNumber value="${subworkcount.absence }" pattern="##.#"/></td>
								<td></td>
								<td><fmt:formatNumber value="${subworkcount.sickleave}" pattern="##.#"/></td>
								<td>${subworkcount.overhours }</td>
								<td  class="notscroll"><div class="remarkdiv">${subworkcount.remark }</div></td>
							</tr>
							</c:forEach>
						</c:forEach>
					</c:forEach>
					</c:if>
					
				</table>
			</div>
		</div>
	</c:if>
	
    
     <script type="text/javascript">  
	     	function queryFunc(){
				var orgID=$("input[name='orgID']").val();
				if(orgID==undefined || orgID.length<=0){
					alert("请选择部门");
					return ;
				}
				$("#searchForm").submit();
			}
             
             $(function(){
            	 $("#exportBtn").click(function exportExcel(){
            		var orgID=$("input[name='orgID']").val();
     				if(orgID==undefined || orgID.length<=0){
     					alert("请选择部门");
     					return ;
     				}
            		 /* $("#exportBtn").off("click"); */
            		 var url="exportOrgStatsCountExcel.ht"
            		 window.location.href=url+"?queryMonth="+$("#queryMonth").val()+"&orgID="+orgID;
            	 });
             })
    </script>
</body>
</html>

