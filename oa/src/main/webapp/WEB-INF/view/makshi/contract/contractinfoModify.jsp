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
   <td class="formHead" colspan="4" style="word-break: break-all;">合同详情</td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">合同类型 <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:contractinfo:contracttype" lablename="类型" class="inputText" value="${contractinfo.contracttype}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">合同金额（万元）</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">合同编号</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">结算金额（万元）</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">合同名称</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">工程标底价</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">合同状态</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">工程中标价</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">甲方</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right"><span style="font-size: 12px; background-color: rgb(255, 255, 255);">费率</span></td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:contractinfo:rate" lablename="费率" class="inputText" value="${contractinfo.rate}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">乙方</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">项目内容</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">付费类型</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">项目规模</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">签约时间</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">合同是否备案</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"><br /> <span> <label><input val="${contractinfo.isrecord}" type="radio" name="m:contractinfo:isrecord" value="0" lablename="合同是否备案" validate="{}" />否</label> <label><input val="${contractinfo.isrecord}" type="radio" name="m:contractinfo:isrecord" value="1" lablename="合同是否备案" validate="{}" />是</label> </span> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">项目总监</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">竣工子资料是否存档</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"> <span> <label><input val="${contractinfo.issave}" type="radio" name="m:contractinfo:issave" value="0" lablename="竣工子资料是否存档" validate="{}" />否</label> <label><input val="${contractinfo.issave}" type="radio" name="m:contractinfo:issave" value="1" lablename="竣工子资料是否存档" validate="{}" />是</label> </span> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">项目状态<br /></td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">合同是否回收</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"><br /> <span> <label><input val="${contractinfo.isrecovery}" type="radio" name="m:contractinfo:isrecovery" value="0" lablename="合同是否回收" validate="{}" />否</label> <label><input val="${contractinfo.isrecovery}" type="radio" name="m:contractinfo:isrecovery" value="1" lablename="合同是否回收" validate="{}" />是</label> </span> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">合同归属部门</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"><br /></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">系统内外</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"><br /> <span> <label><input val="${contractinfo.inout}" type="radio" name="m:contractinfo:inout" value="0" lablename="系统内外" validate="{}" />内</label> <label><input val="${contractinfo.inout}" type="radio" name="m:contractinfo:inout" value="1" lablename="系统内外" validate="{}" />外</label> </span> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">工程地址</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">开工时间</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">业主名称</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">完工时间</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">项目由来</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">项目负责人</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">招标平台</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">合同经手人</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">招标方式</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">合同审核人</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">合同批签人</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">备注</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:contractinfo:remark" lablename="备注" class="inputText" value="${contractinfo.remark}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">修改人</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:contractinfo:updater" lablename="修改人" class="inputText" value="${contractinfo.updater}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">创建人</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:contractinfo:creater" lablename="创建人" class="inputText" value="${contractinfo.creater}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">修改时间</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">创建时间</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">保管人</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:contractinfo:custodian" lablename="保管人" class="inputText" value="${contractinfo.custodian}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">投资总额（万元）</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">进度</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:contractinfo:progress" lablename="进度" class="inputText" value="${contractinfo.progress}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">开票金额</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">开票时间</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"><br /></td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">到账金额</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">到账时间</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"></td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">附件</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"><br /></td> 
  </tr> 
 </tbody> 
</table>   
<div type="subtable" tablename="contract_billing_record" tabledesc="合同开票记录" show="true" parser="rowmodeedit" id="contract_billing_record" formtype="edit"> 
 <table class="listTable" border="0" cellpadding="2" cellspacing="0"> 
  <tbody> 
   <tr class="toolBar firstRow"> 
    <td colspan="8" style="word-break: break-all;"><a class="link add" href="javascript:;">添加</a></td> 
   </tr> 
   <tr class="headRow"> 
    <th style="word-break: break-all;">序号</th> 
    <th style="word-break: break-all;">开票时间</th> 
    <th style="word-break: break-all;">发票金额</th> 
    <th style="word-break: break-all;">经办人</th> 
    <th style="word-break: break-all;">审批状态</th> 
    <th style="word-break: break-all;">领票人</th> 
    <th style="word-break: break-all;">到账时间</th> 
    <th style="word-break: break-all;">到账金额</th> 
   </tr> 
   <c:forEach items="${contract_billing_recordList}" var="contract_billing_record" varStatus="status">
    <tr class="listRow" type="subdata"> 
     <td class="tdNo" style="word-break: break-all;">${status.index+1}</td> 
     <td style="word-break: break-all;"></td> 
     <td style="word-break: break-all;"></td> 
     <td> <input name="s:contract_billing_record:operator" type="text" ctltype="selector" class="users" lablename="经办人" value="${contractBillingRecord.operator}" validate="{empty:false}" readonly="readonly" /> </td> 
     <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:contract_billing_record:status" lablename="审批状态" class="inputText" value="${contractBillingRecord.status}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
     <td> <input name="s:contract_billing_record:bearer" type="text" ctltype="selector" class="users" lablename="领票人" value="${contractBillingRecord.bearer}" validate="{empty:false}" readonly="readonly" /> </td> 
     <td></td> 
     <td style="word-break: break-all;"></td> 
    </tr>
   </c:forEach> 
   <tr class="listRow" type="append" style="display:none;"> 
    <td class="tdNo" style="word-break: break-all;"><br /></td> 
    <td style="word-break: break-all;"></td> 
    <td style="word-break: break-all;"></td> 
    <td> <input name="s:contract_billing_record:operator" type="text" ctltype="selector" class="users" lablename="经办人" value="${contractBillingRecord.operator}" validate="{empty:false}" readonly="readonly" /> </td> 
    <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:contract_billing_record:status" lablename="审批状态" class="inputText" value="${contractBillingRecord.status}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
    <td> <input name="s:contract_billing_record:bearer" type="text" ctltype="selector" class="users" lablename="领票人" value="${contractBillingRecord.bearer}" validate="{empty:false}" readonly="readonly" /> </td> 
    <td></td> 
    <td style="word-break: break-all;"></td> 
   </tr>
  </tbody> 
 </table> 
 <input name="s:contract_billing_record:id" type="hidden" pk="true" value="" />
</div>    
<div style="text-align: center;"> 
 <span style="font-size: 12px;"> 
  <div type="subtable" tablename="contract_payment_record" tabledesc="合同付款记录" show="true" parser="rowmodeedit" id="contract_payment_record" formtype="edit"> 
   <table class="listTable" border="0" cellpadding="2" cellspacing="0"> 
    <tbody> 
     <tr class="toolBar firstRow"> 
      <td colspan="7"><a class="link add" href="javascript:;">添加</a></td> 
     </tr> 
     <tr class="headRow"> 
      <th>序号</th> 
      <th style="word-break: break-all;">申请付款时间</th> 
      <th style="word-break: break-all;">申请付款金额</th> 
      <th style="word-break: break-all;">经办人</th> 
      <th style="word-break: break-all;">审批状态</th> 
      <th style="word-break: break-all;">付款时间</th> 
      <th style="word-break: break-all;">实际支付金额</th> 
     </tr> 
     <c:forEach items="${contract_payment_recordList}" var="contract_payment_record" varStatus="status">
      <tr class="listRow" type="subdata"> 
       <td class="tdNo">${status.index+1}</td> 
       <td><br /></td> 
       <td></td> 
       <td> <input name="s:contract_payment_record:operator" type="text" ctltype="selector" class="users" lablename="经办人" value="${contractPaymentRecord.operator}" validate="{empty:false}" readonly="readonly" /> </td> 
       <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:contract_payment_record:status" lablename="审批状态" class="inputText" value="${contractPaymentRecord.status}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
       <td></td> 
       <td></td> 
      </tr>
     </c:forEach> 
     <tr class="listRow" type="append" style="display:none;"> 
      <td class="tdNo"><br /></td> 
      <td><br /></td> 
      <td></td> 
      <td> <input name="s:contract_payment_record:operator" type="text" ctltype="selector" class="users" lablename="经办人" value="${contractPaymentRecord.operator}" validate="{empty:false}" readonly="readonly" /> </td> 
      <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:contract_payment_record:status" lablename="审批状态" class="inputText" value="${contractPaymentRecord.status}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
      <td></td> 
      <td></td> 
     </tr>
    </tbody> 
   </table> 
   <input name="s:contract_payment_record:id" type="hidden" pk="true" value="" />
  </div></span> 
</div>
