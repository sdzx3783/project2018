<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>

<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${ctx}/js/hotent/scriptMgr.js"></script>
<script type="text/javascript">
	function afterOnload(){
		var afterLoadJs=[
		     			'${ctx}/js/hotent/formdata.js',
		     			'${ctx}/js/hotent/subform.js'
		 ];
		ScriptMgr.load({
			scripts : afterLoadJs
		});
	}
</script>
<table class="formTable" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled"> 
 <tbody> 
  <tr class="firstRow"> 
   <td class="formHead" colspan="4" style="word-break: break-all;">租房入住人员登记</td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">租房编号:</td> 
   <td style="width: 35%" class="formInput"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">房屋地址:</td> 
   <td style="width: 35%" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:in_house_registration:address" lablename="房屋地址" class="inputText" value="${inHouseRegistration.address}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
  </tr> 
  <tr> 
   <td class="formInput" colspan="4" rowspan="1"><br /> 
    <div type="subtable" tablename="user_info" tabledesc="员工入住信息" show="true" parser="rowmodeedit" id="user_info" formtype="edit"> 
     <table class="listTable" border="0" cellpadding="2" cellspacing="0"> 
      <tbody> 
       <tr class="toolBar firstRow"> 
        <td colspan="5"><a class="link add" href="javascript:;">添加</a></td> 
       </tr> 
       <tr class="headRow"> 
        <th style="word-break: break-all;">入住人员姓名</th> 
        <th style="word-break: break-all;">是为负责人</th> 
        <th style="word-break: break-all;">入住家属</th> 
        <th style="word-break: break-all;">入住时间</th> 
        <th style="word-break: break-all;">离开时间</th> 
       </tr> 
       <c:forEach items="${user_infoList}" var="user_info" varStatus="status">
        <tr class="listRow" type="subdata"> 
         <td></td> 
         <td> <span> <label><input val="${userInfo.isresponsible}" type="radio" name="s:user_info:isresponsible" value="0" lablename="是否负责人" validate="{}" />是</label> <label><input val="${userInfo.isresponsible}" type="radio" name="s:user_info:isresponsible" value="1" lablename="是否负责人" validate="{}" />否</label> </span> </td> 
         <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:user_info:family" lablename="是否" class="inputText" value="${userInfo.family}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
         <td></td> 
         <td></td> 
        </tr>
       </c:forEach> 
       <tr class="listRow" type="append" style="display:none;"> 
        <td></td> 
        <td> <span> <label><input val="${userInfo.isresponsible}" type="radio" name="s:user_info:isresponsible" value="0" lablename="是否负责人" validate="{}" />是</label> <label><input val="${userInfo.isresponsible}" type="radio" name="s:user_info:isresponsible" value="1" lablename="是否负责人" validate="{}" />否</label> </span> </td> 
        <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:user_info:family" lablename="是否" class="inputText" value="${userInfo.family}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
        <td></td> 
        <td></td> 
       </tr>
      </tbody> 
     </table> 
     <input name="s:user_info:id" type="hidden" pk="true" value="" />
    </div></td> 
  </tr> 
 </tbody> 
</table>
