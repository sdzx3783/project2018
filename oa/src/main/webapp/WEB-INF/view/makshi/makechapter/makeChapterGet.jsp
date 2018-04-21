
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>印章表明细</title>
<%@include file="/commons/include/customForm.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/FlowDetailWindow.js"></script>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body class="oa-mw-page">
	<div class="oa-mg20">
		<a class="oa-button oa-button--primary oa-button--blue" href="list.ht">返回</a>
		<a class="oa-button oa-button--primary oa-button--blue"  onclick="FlowDetailWindow({runId:${runId}})" href="javascript:;" >流程明细</a>
	</div>
<table class="oa-table--default" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
<caption>印章申请详细信息</caption>
 <tbody>
  <tr class="firstRow">
   <th>合同编号:</th>
   <td>${makeChapter.contract_id}</td>
   <th>总投资额(万):</th>
   <td>${makeChapter.total_investment}</td>
  </tr>
  <tr>
   <th>申请人:</th>
   <td>${makeChapter.application_person}</td>
   <th>申请印章名称:</th>
   <td>${makeChapter.chapter_name}</td>
  </tr>
  <tr>
   <th>申请刻章事由:</th>
   <td>${makeChapter.reason}</td>
   <th>要求到位时间:</th>
   <td>
   <fmt:formatDate value="${makeChapter.limit_date}"  type="both" pattern="yyyy-MM-dd"/></td>
  </tr>
  <tr>
   <th>项目名称:</th>
   <td>${makeChapter.project_name}</td>
   <th>刻章类型:</th>
   <td>
	<c:if test="${makeChapter.chapter_type==1}">合同类</c:if>
	<c:if test="${makeChapter.chapter_type==0}">其他类</c:if>
	</td>
  </tr>
  <tr>
   <th>保管人:</th>
   <td colspan="3">${empty makeChapter.take_person ? makeChapter.application_person : makeChapter.take_person}</td>
  </tr>
  <tr>
   <th>备注:</th>
   <td colspan="3" rowspan="1">${makeChapter.remarks}</td>
  </tr>
  <tr>
   <th>附件:</th>
   <td rowspan="1" colspan="3">
    <div name="div_attachment_container" right="r"> 
     <div class="attachement"></div> 
     <textarea style="display:none" controltype="attachment" name="attachment" lablename="附件" readonly="readonly">${makeChapter.attachment}</textarea> 
    </div></td>
  </tr>
  <tr>
   <th>印章图片:</th>
   <td colspan="3" rowspan="1" align="center">
       <img id="manualimg" style="height: 300px;width: auto;" onclick="outImg(this);" src="${makeChapter.sealImg}" />
   </td>
  </tr>
 </tbody>
</table>
<script type="text/javascript">
    var outImg = function (e) {
        if (typeof (e.src) == 'undefined' || e.src == '') {
            return;
        }
        $.fancybox({
            'type': 'image',
            'padding': 0,
            'href': e.src,
            'transitionIn': 'elastic',
            'transitionOut': 'elastic'
        })
    };
</script>
</body>
</html>

