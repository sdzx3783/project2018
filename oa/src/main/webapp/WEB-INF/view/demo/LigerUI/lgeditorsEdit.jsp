<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 lgEditor多样化</title>
	<%@include file="/commons/include/customForm.jsp" %>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#lgeditorsForm').form();
			$("a.save").click(function() {
				frm.ajaxForm(options);
				if(frm.valid()){
					OfficePlugin.submit();
					if(WebSignPlugin.hasWebSignField){
						WebSignPlugin.submit();
					}
					$('#lgeditorsForm').submit();
				}
			});
		});
		
		function showResponse(responseText) {
			var obj = new com.hotent.form.ResultMessage(responseText);
			if (obj.isSuccess()) {
				$.ligerDialog.confirm(obj.getMessage()+",是否继续操作","提示信息", function(rtn) {
					if(rtn){
						this.close();
					}else{
						window.location.href = "${ctx}/demo/LigerUI/lgeditors/list.ht";
					}
				});
			} else {
				$.ligerDialog.error(obj.getMessage(),"提示信息");
			}
		}
	</script>
</head>
<body>
<div class="panel">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${not empty lgeditorsItem.id}">
			        <span class="tbar-label"><span></span>编辑lgEditor多样化</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label"><span></span>添加lgEditor多样化</span>
			    </c:otherwise>	
		    </c:choose>
		</div>
		<div class="panel-toolbar">
			<div class="toolBar">
				<div class="group"><a class="link save" id="dataFormSave" href="javascript:;"><span></span>保存</a></div>
				<div class="l-bar-separator"></div>
				<div class="group"><a class="link back" href="list.ht"><span></span>返回</a></div>
			</div>
		</div>
	</div>
	<form id="lgeditorsForm" method="post" action="save.ht">
		<div type="custform">
			<div class="panel-detail">
				<table cellpadding="2" cellspacing="0" border="1" class="formTable">
 <tbody>
  <tr>
   <td colspan="8" class="formHead">lgEditor多样化</td>
  </tr>
  <tr>
   <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">姓名:</td>
   <td style="width:15%;" class="formInput"><span name="editable-input" style="display:inline-block;" isflag="tableflag"><input type="text" name="name" lablename="姓名" class="inputText" validate="{maxlength:50}" isflag="tableflag" value="${lgeditors.name}" /></span></td>
   <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">性别:</td>
   <td style="width:15%;" class="formInput"><span name="editable-input" style="display:inline-block;padding:2px;" class="selectinput"><select name="sex" lablename="性别" controltype="select" validate="{empty:false}"><option value="1" <c:if test='${lgeditors.sex==1}'>selected='selected'</c:if>>男</option><option value="0" <c:if test='${lgeditors.sex==0}'>selected='selected'</c:if>>女</option></select></span></td>
   <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">生日:</td>
   <td style="width:15%;" class="formInput"><input name="birthday" type="text" class="Wdate" displaydate="0" validate="{empty:false}" value="<fmt:formatDate value='${lgeditors.birthday}' pattern='yyyy-MM-dd'/>" /></td>
   <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">年薪:</td>
   <td style="width:15%;" class="formInput"><input name="money" type="text" showtype="{'coinValue':'','isShowComdify':false,'decimalValue':0}" validate="{number:true,maxIntLen:13,maxDecimalLen:0}" value="${lgeditors.money}" /></td>
  </tr>
  <tr>
   <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">toWife:</td>
   <td style="width:15%;" class="formInput"><input name="toWife" type="text" showtype="{'coinValue':'','isShowComdify':false,'decimalValue':2}" validate="{number:true,maxIntLen:13,maxDecimalLen:2}" value="${lgeditors.toWife}" /></td>
   <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">入职日期:</td>
   <td style="width:15%;" class="formInput"><input name="joinDate" type="text" class="Wdate" displaydate="0" validate="{empty:false}" value="<fmt:formatDate value='${lgeditors.joinDate}' pattern='yyyy-MM-dd HH:mm:00'/>" /></td>
   <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">结婚日期:</td>
   <td style="width:15%;" class="formInput"><input name="jiehunDay" type="text" class="Wdate" displaydate="0" validate="{empty:false}" value="<fmt:formatDate value='${lgeditors.jiehunDay}' pattern='yyyy-MM-dd'/>" /></td>
   <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">工作性质:</td>
   <td style="width:15%;" class="formInput"><span name="editable-input" style="display:inline-block;padding:2px;" class="selectinput"><select name="jobType" lablename="工作性质" controltype="select" validate="{empty:false}"><option value="零售业" <c:if test='${lgeditors.jobType==零售业}'>selected='selected'</c:if>>零售业</option><option value="销售业" <c:if test='${lgeditors.jobType==销售业}'>selected='selected'</c:if>>销售业</option><option value="物流业" <c:if test='${lgeditors.jobType==物流业}'>selected='selected'</c:if>>物流业</option><option value="打杂" <c:if test='${lgeditors.jobType==打杂}'>selected='selected'</c:if>>打杂</option></select></span></td>
  </tr>
  <tr>
   <td align="right" style="width:10%;" class="formTitle" nowrap="nowarp">备注:</td>
   <td style="width:15%;" class="formInput"><textarea name="comment" validate="{empty:false}">${lgeditors.comment}</textarea></td>
   <td style="width:10%;" class="formTitle"></td>
   <td style="width:15%;" class="formInput"></td>
   <td style="width:10%;" class="formTitle"></td>
   <td style="width:15%;" class="formInput"></td>
   <td style="width:10%;" class="formTitle"></td>
   <td style="width:15%;" class="formInput"></td>
  </tr>
 </tbody>
</table>
			</div>
		</div>
		<input type="hidden" name="id" value="${lgeditors.id}"/>
	</form>
</body>
</html>
