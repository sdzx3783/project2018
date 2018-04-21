<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@include file="/commons/include/customForm.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/scriptMgr.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
<script type="text/javascript">
	function afterOnload() {
		var afterLoadJs = [ '${ctx}/js/hotent/formdata.js',
				'${ctx}/js/hotent/subform.js' ];
		ScriptMgr.load({
			scripts : afterLoadJs
		});
	}
</script>
<style type="text/css">
	@media print { 
	.noprint { display: none;color:green } 
	}
	.oa-table{
		table-layout: fixed;
		width: 100%;
	}
	.oa-table th{
		padding: 15px 15px;
		border: 1px solid #333;
	}
	.oa-table td{
		padding: 15px 15px;
		border: 1px solid #333;
	}
	.oa-no-border{
		border: 0 !important;
	}
	caption{
		text-align: center;
		font-size: 18px;
		padding-bottom: 20px;
	}
	.oa-print-title{
	text-align: center;
	}
	
</style>
	<tbody>
	    <div class="oa-top-wrap noprint noprint" >
		<a class="oa-button oa-button--primary oa-button--blue" href="get.ht?id=${hdDocumentReview.id}">返回</a>
		<a class="oa-button oa-button--primary oa-button--blue" onclick="window.print();">打印</a>	
	</div>	
		<div class="oa-main-wrap">
		<table class="oa-table">
			<caption>文件审查表</caption>
			<tr>
				<th class="oa-print-title" colspan="2">文件名称:</th>
				<td colspan="6">${hdDocumentReview.file_name}</td>
			    
			    <th class="oa-print-title"colspan="2">编号:</th>
				<td colspan="2">${hdDocumentReview.number}</td>
			</tr>
			
			<tr>
				<th class="oa-print-title" colspan="2">项目部:</th>
				<td colspan="2">${hdDocumentReview.department}</td>
			    <th class="oa-print-title"colspan="2">申请人:</th>
				<td colspan="2">${hdDocumentReview.applicant}</td>
				<th class="oa-print-title"colspan="2">填报日期:</th>
				<td colspan="2"><fmt:formatDate value='${hdDocumentReview.date}' pattern='yyyy-MM-dd'/>
				</td>
			</tr>
			<tr>
				<th class="oa-print-title"colspan="2">审批类型:</th>
				<td colspan="6">
				 <c:if test="${hdDocumentReview.type==0}">技术文件</c:if>
			  	 <c:if test="${hdDocumentReview.type==1}">通知</c:if>
			  	 <c:if test="${hdDocumentReview.type==2}">请示</c:if>
				</td>
			    
			    <th class="oa-print-title"colspan="2">紧急程度:</th>
				<td colspan="2">
				  <c:if test="${hdDocumentReview.level==0}">普通</c:if>
			      <c:if test="${hdDocumentReview.level==1}">紧急</c:if>
			      <c:if test="${hdDocumentReview.level==2}">加急</c:if>
				</td>
			</tr>
			<tr>
				<th class="oa-print-title"colspan="2">内容摘要:</th>
				<td colspan="10">${hdDocumentReview.content}</td>
			</tr>
			<tr>
				<th class="oa-print-title"colspan="2">附件:</th>
				<td colspan="10">
				<div name="div_attachment_container" right="r"> 
                    <div class="attachement"></div> 
                    <textarea style="display:none" controltype="attachment" name="attachment" 
                    lablename="附件" readonly="readonly">${hdDocumentReview.attachment}</textarea> 
                </div>
		        </td>
			</tr>
			<%-- <c:forEach var="item" items="${taskOpinionList}" varStatus="status" begin="0"  >
			   <c:forEach var="tem" items="${item.list}" varStatus="var">
				<tr>
				   <c:if test="${var.index==0}">
				      <th class="oa-print-title" colspan="2"<c:if test="${fn:length(item.list)>1}">rowspan="${fn:length(item.list)}"</c:if>>
			       	  			${item.taskName}:
			       	  </th>
			       </c:if> 			
					<td style="border-right: 0;" colspan="5">
						<table>
							<tr>
								<td class="oa-no-border" colspan="2">意见：${item.opinion}</td>
							</tr>
							<tr>
								<td class="oa-no-border"colspan="2">审核人：${item.exeFullname}</td>
							</tr>
						</table>
					</td>
					<td style="border-left: 0; text-align: right;"colspan="5">审核时间：<fmt:formatDate value="${item.endTime}" pattern='yyyy-MM-dd HH:mm:ss' /></td>
				</tr>
			  </c:forEach>  
			</c:forEach> --%>
			<c:forEach items="${rtnMap}" var="item">
				<tr>
				      <th class="oa-print-title" colspan="2">
			       	  			${item.key}:
			       	  </th>
					<td style="border-right: 0;" colspan="9">
						<table>
							
							<tr>
								<td class="oa-no-border"style="width: 270px !important;" colspan="3" >
								          <c:set var = "string1" value = "${item.value.opinion}" />
								          <c:set var = "string2" value = "${fn:split(string1,',')}" />                  
								            <c:forEach items="${string2 }" var="str">               
								                                             意见：${str }</br></br>
								            </c:forEach>                    
								</td>
							    <td class="oa-no-border" style="width: 230px !important;"colspan="3">
							                <c:set var = "string1" value = "${item.value.exeFullname}" />
								            <c:set var = "string2" value = "${fn:split(string1,',')}" />                  
								            <c:forEach items="${string2 }" var="str">               
								                                              审核人：${str }</br></br>
								            </c:forEach>                               
							    </td>
							    <td class="oa-no-border" style="width: 400px !important;"colspan="3">
							               <c:set var = "string1" value = "${item.value.taskToken}" />
								            <c:set var = "string2" value = "${fn:split(string1,',')}" />                  
								            <c:forEach items="${string2 }" var="str">               
								                                              审核时间：${str }</br></br>
								            </c:forEach>                                        
							    
							    </td>
							</tr>
						  
						</table>
					</td>
					 <td style="border-left: 0; text-align: right;"colspan="0"></td> 
				</tr>
			</c:forEach>
		</table>
		
	</tbody>
<%-- </table>--%>
