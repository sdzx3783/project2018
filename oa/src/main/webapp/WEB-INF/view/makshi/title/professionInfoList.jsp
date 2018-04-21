<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
    <title>职素报表</title>
<%@include file="/commons/include/get.jsp" %>
</head>
<body>
	<div id="oa_list_search" style="padding: 10px 20px;">
		<div class="oa-accordion">
		<div class="oa-accordion__title">查询条件</div>
        <div class="oa-accordion__content">
        <form id="searchForm" method="post" action="list.ht" class="oa-clear">
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">姓名</div>
                <div class="oa-input-wrap oa-mgl100">
                   <input type="text" name="Q_name_SL"  class="oa-input"  value="${param['Q_name_SL']}"/>
                </div>
            </div>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">部门</div>
                <div class="oa-input-wrap oa-mgl100">
                   <input type="text" name="Q_orgName_SL"  class="oa-input"  value="${param['Q_orgName_SL']}"/>
                </div>
            </div>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">专业</div>
                <div class="oa-input-wrap oa-mgl100">
                  <input type="text" name="Q_profession_SL"  class="oa-input"  value="${param['Q_profession_SL']}"/>
                </div>
            </div>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">职称</div>
                <div class="oa-input-wrap oa-mgl100">
               	 <input type="text" name="Q_title_SL"  class="oa-input"  value="${param['Q_title_SL']}"/>
                </div>
            </div>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">性别</div>
                <div class="oa-input-wrap oa-mgl100">
               		<select name="sex" class="oa-select">
						<option value="" <c:if test="${param['sex']==''}">selected</c:if>>请选择</option>
						<option value = "1"<c:if test="${param['sex']==1 && param['sex']!=''}">selected</c:if>>男</option>
						<option value = "0"<c:if test="${param['sex']==0  && param['sex']!=''}">selected</c:if>>女</option>
					</select>
                </div>
            </div>
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">持证名称</div>
                <div class="oa-input-wrap oa-mgl100">
						<select class="oa-select" name="Q_professionName_S" >
							<option value="">-------请选择持证名称-------</option>
							<option value="buildSupervisionEngineer"<c:if test="${param['Q_professionName_S']=='buildSupervisionEngineer'}">selected</c:if>>建设部监理工程师</option>
							<option value="buildCostEngineer"<c:if test="${param['Q_professionName_S']=='buildCostEngineer'}">selected</c:if>>建设部造价工程师</option>
							<option value="waterSupervisionEngineer"<c:if test="${param['Q_professionName_S']=='waterSupervisionEngineer'}">selected</c:if>>水利部监理工程师</option>
							<option value="waterCostEngineer"<c:if test="${param['Q_professionName_S']=='waterCostEngineer'}">selected</c:if>>水利部造价工程师</option>
							<option value="waterDirector"<c:if test="${param['Q_professionName_S']=='waterDirector'}">selected</c:if>>水利部总监</option>
							<option value="firstBuilder"<c:if test="${param['Q_professionName_S']=='firstBuilder'}">selected</c:if>>一级建造师</option>
							<option value="secondBuilder"<c:if test="${param['Q_professionName_S']=='secondBuilder'}">selected</c:if>>二级建造师</option>
							<option value="consultingEngineer"<c:if test="${param['Q_professionName_S']=='consultingEngineer'}">selected</c:if>>咨询工程师(投资)</option>
							<option value="informationEngineer"<c:if test="${param['Q_professionName_S']=='informationEngineer'}">selected</c:if>>信息监理工程师</option>
							<option value="registeredEquipmentEngineer"<c:if test="${param['Q_professionName_S']=='registeredEquipmentEngineer'}">selected</c:if>>注册设备监理工程师</option>
							<option value="registeredSafetyEngineer"<c:if test="${param['Q_professionName_S']=='registeredSafetyEngineer'}">selected</c:if>>注册安全工程师</option>
							<option value="registeredUtilityEngineer"<c:if test="${param['Q_professionName_S']=='registeredUtilityEngineer'}">selected</c:if>>注册公用设备工程师</option>
							<option value="bidder"<c:if test="${param['Q_professionName_S']=='bidder'}">selected</c:if>>招标师</option>
							<option value="projectManagementEngineer"<c:if test="${param['Q_professionName_S']=='projectManagementEngineer'}">selected</c:if>>系统集成项目管理工程师</option>
							<option value="supervisor"<c:if test="${param['Q_professionName_S']=='supervisor'}">selected</c:if>>水利部监理员</option>
							<option value="firstStructuralEngineer"<c:if test="${param['Q_professionName_S']=='firstStructuralEngineer'}">selected</c:if>>一级结构工程师</option>
							<option value="secondStructuralEngineer"<c:if test="${param['Q_professionName_S']=='secondStructuralEngineer'}">selected</c:if>>二级结构工程师</option>
							<option value="investmentSubjectManage"<c:if test="${param['Q_professionName_S']=='investmentSubjectManage'}">selected</c:if>>投资项目管理师</option>
							<option value="cartographers"<c:if test="${param['Q_professionName_S']=='cartographers'}">selected</c:if>>测绘师</option>
						</select>
                </div>
            </div> 
            <input type="hidden" name="Q_export_SL"  class="oa-input"  value=""/>
            <div class="oa-fl oa-mgb10 oa-mgh20">
                <button  onclick="tosubmit('1')" class="oa-button oa-button--primary oa-button--blue">查询</button>
                <button  onclick="tosubmit('2')" class="oa-button oa-button--primary oa-button--blue">导出</button>
            </div>
        </form>
        </div>
        </div>
    </div>
		
	<div class="oa-pd20">
		<c:set var="startNum" value="${(pageBeanprofessionInfoItem.currentPage-1)*pageBeanprofessionInfoItem.pageSize+1}"></c:set>
	    <div id="oa_list_content" class="oa-table-scroll-wrap">
		    <display:table  name="professionInfoList" id="professionInfoItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1" class="oa-table--default oa-table--nowrap">
		    	<display:column title="序号"> 
				    	<c:out value="${startNum}"/>
						<c:set var="startNum" value="${startNum+1}"/>
			    </display:column>
			    <display:column title="员工编号" style="">
					${professionInfoItem.account}
				</display:column>
				<display:column title="姓名" style="">
					${professionInfoItem.name}
				</display:column>
				<display:column title="部门" style="">
					${professionInfoItem.orgName}
				</display:column>
				<display:column title="性别" style="">
					<c:if test="${professionInfoItem.sex==1}">男</c:if>
					<c:if test="${professionInfoItem.sex==0}">女</c:if>
				</display:column>
				<display:column title="入职日期" style="">
					<fmt:formatDate value="${professionInfoItem.intime}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column title="身份证号" style="">
					${professionInfoItem.idcard}
				</display:column>
				<display:column title="专业" style="">
					${professionInfoItem.profession}
				</display:column>
				<display:column title="职称" style="">
					${professionInfoItem.title}
				</display:column>
				<display:column title="毕业院校" style="">
					${professionInfoItem.school}
				</display:column>
				<display:column title="建设部监理工程师" style="">
					<c:if test="${professionInfoItem.buildSupervisionEngineer==1}">√</c:if>
				</display:column>
				<display:column title="建设部造价工程师" style="">
					<c:if test="${professionInfoItem.buildCostEngineer==1}">√</c:if>
				</display:column>
				<display:column title="一级建造师" style="">
					<c:if test="${professionInfoItem.firstBuilder==1}">√</c:if>
				</display:column>
				<display:column title="二级建造师" style="">
					<c:if test="${professionInfoItem.secondBuilder==1}">√</c:if>
				</display:column>
				<display:column title="水利部总监证" style="">
					<c:if test="${professionInfoItem.waterDirector==1}">√</c:if>
				</display:column>
				<display:column title="水利部监理工程师" style="">
					<c:if test="${professionInfoItem.waterSupervisionEngineer==1}">√</c:if>
				</display:column>
				<display:column title="水利部造价工程师" style="">
					<c:if test="${professionInfoItem.waterCostEngineer==1}">√</c:if>
				</display:column>
				<display:column title="水利部监理员" style="">
					<c:if test="${professionInfoItem.supervisor==1}">√</c:if>
				</display:column>
				<display:column title="一级结构工程师" style="">
					<c:if test="${professionInfoItem.firstStructuralEngineer==1}">√</c:if>
				</display:column>
				<display:column title="二级结构工程师" style="">
					<c:if test="${professionInfoItem.secondStructuralEngineer==1}">√</c:if>
				</display:column>
				<display:column title="招标师" style="">
					<c:if test="${professionInfoItem.bidder==1}">√</c:if>
				</display:column>
				<display:column title="注册设备监理工程师" style="">
					<c:if test="${professionInfoItem.registeredEquipmentEngineer==1}">√</c:if>
				</display:column>
				<display:column title="注册公用设备工程师" style="">
					<c:if test="${professionInfoItem.registeredUtilityEngineer==1}">√</c:if>
				</display:column>
				<display:column title="注册安全工程师" style="">
					<c:if test="${professionInfoItem.registeredSafetyEngineer==1}">√</c:if>
				</display:column>
				<display:column title="咨询工程师(投资)" style="">
					<c:if test="${professionInfoItem.consultingEngineer==1}">√</c:if>
				</display:column>
				<display:column title="投资项目管理师" style="">
					<c:if test="${professionInfoItem.investmentSubjectManage==1}">√</c:if>
				</display:column>
				<display:column title="测绘师" style="">
					<c:if test="${professionInfoItem.cartographers==1}">√</c:if>
				</display:column>
				<display:column title="信息监理工程师" style="">
					<c:if test="${professionInfoItem.informationEngineer==1}">√</c:if>
				</display:column>
				<display:column title="系统集成项目管理工程师" style="">
					<c:if test="${professionInfoItem.projectManagementEngineer==1}">√</c:if>
				</display:column>
			</display:table>
	    </div>
	    <hotent:paging tableId="professionInfoItem"/>
	</div>
</body>
<script type="text/javascript">
function tosubmit(str){
	if(1==str){
		$("#searchForm").attr("action","list.ht");
		$("#searchForm").submit();
	}else if(2==str){
		if(confirm("确定导出")){
			$("input[name='Q_export_SL']").val(1);
			$("#searchForm").attr("action","export.ht");
			$("#searchForm").submit();
		}
	}
}
$(function(){
	$('.oa-accordion').oaAccordion({collapse: true});
});
</script>
</html>


