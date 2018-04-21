<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
    <title>编辑 党员档案</title>
    <%@include file="/codegen/include/customForm.jsp" %>
    <%@include file="/commons/include/ueditor.jsp" %>
    <script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
    <script type="text/javascript">
        $(function() {
            var options={};
            if(showResponse){
                options.success=showResponse;
            }
            var frm=$('#partyFilesForm').form();
            $("a.save").click(function() {
                frm.ajaxForm(options);
                $("#saveData").val(1);
                if(frm.valid()){
                    //如果有编辑器的情况下
                    $("textarea[name^='m:'].myeditor").each(function(num) {
                        var name=$(this).attr("name");
                        var data=getEditorData(editor[num]);
                        $("textarea[name^='"+name+"']").val(data);
                    });
                    
                    if(WebSignPlugin.hasWebSignField){
                        WebSignPlugin.submit();
                    }
                    if(OfficePlugin.officeObjs.length>0){
                        OfficePlugin.submit(function(){
                            frm.handleFieldName();
                            $('#partyFilesForm').submit();
                        });
                    }else{
                        frm.handleFieldName();
                        $('#partyFilesForm').submit();
                    }
                }
            });
        });
        
        function showResponse(responseText) {
            var obj = new com.hotent.form.ResultMessage(responseText);
            if (obj.isSuccess()) {
                $.ligerDialog.confirm(obj.getMessage()+",是否继续操作","提示信息", function(rtn) {
                    if(rtn){
                        window.location.href = window.location.href;
                    }else{
                        window.location.href = "${ctx}/makshi/party/partyFiles/list.ht";
                    }
                });
            } else {
                $.ligerDialog.error(obj.getMessage(),"提示信息");
            }
        }
    </script>
<style>
  #partyFilesForm{
    margin: 0 11px;
  }
</style>
</head>
<body>
<div class="panel" style="height:100%;overflow:auto;">
    <div class="panel-top">
        <div class="tbar-title">
            <c:choose>
                <c:when test="${not empty partyFilesItem.id}">
                    <span class="tbar-label"><span></span>编辑党员档案</span>
                </c:when>
                <c:otherwise>
                    <span class="tbar-label"><span></span>添加党员档案</span>
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
    <form id="partyFilesForm" method="post" action="save.ht">
        <div type="custform">
            <div class="panel-detail">
                <table class="formTable" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled"> 
 <tbody> 
  <tr class="firstRow"> 
   <td class="formHead" colspan="4" style="word-break: break-all;">党员资料</td> 
   <input type="hidden" name="m:party_files:user_num" lablename="员工编号" class="inputText" value="${partyFiles.user_num}" validate="{maxlength:50}" isflag="tableflag" />
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">党员基本情况编号</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"><span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:party_files:party_num" lablename="党员基本情况编号" class="inputText" value="${partyFiles.party_num}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">与党组织联系情况</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"><span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:party_files:contact_situation" lablename="与党组织联系情况" class="inputText" value="${partyFiles.contact_situation}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">工号</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"><span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:party_files:account" lablename="员工编号" class="inputText" value="${partyFiles.account}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">流出支部联系人</td> 
   <td style="width: 35%" class="formInput"><span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:party_files:out_contact" lablename="流出支部联系人" class="inputText" value="${partyFiles.out_contact}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">入党时间</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"><span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:party_files:join_party_date" lablename="入党时间" value="<fmt:formatDate value="${partyFiles.join_party_date}" pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" isflag="tableflag" /> </span> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">流出支部联系人联系方式</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"><span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:party_files:out_tel" lablename="流出支部联系人联系方式" class="inputText" value="${partyFiles.out_tel}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">转正时间</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"><span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:party_files:regular_date" lablename="转正时间"  value="<fmt:formatDate value="${partyFiles.regular_date}" pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" isflag="tableflag" /> </span> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">流入支部联系人</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"><span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:party_files:in_contact" lablename="流入支部联系人" class="inputText" value="${partyFiles.in_contact}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">入党时所在支部</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"><span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:party_files:join_party_where" lablename="入党时所在支部" class="inputText" value="${partyFiles.join_party_where}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">流入支部联系人联系方式</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"><span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:party_files:in_tel" lablename="流入支部联系人联系方式" class="inputText" value="${partyFiles.in_tel}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">转正时所在支部</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"><span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:party_files:regular_where" lablename="转正时所在支部" class="inputText" value="${partyFiles.regular_where}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">流入地（单位）</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"><span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:party_files:place_of_influx" lablename="流入地（单位）" class="inputText" value="${partyFiles.place_of_influx}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">入党介绍人</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:party_files:introducer" lablename="入党介绍人" class="inputText" value="${partyFiles.introducer}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">失去联系日期</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"><span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:party_files:lost_date" lablename="失去联系日期" value="<fmt:formatDate value="${partyFiles.lost_date}" pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" isflag="tableflag" /> </span> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">所在支部</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:party_files:branch" lablename="所在支部" class="inputText" value="${partyFiles.branch}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">出国日期</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"><span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:party_files:abroad_date" lablename="出国日期" value="<fmt:formatDate value="${partyFiles.abroad_date}" pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" isflag="tableflag" /> </span> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">进入党支部日期</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"><span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:party_files:join_branch_date" lablename="进入党支部日期" value="<fmt:formatDate value="${partyFiles.join_branch_date}" pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" isflag="tableflag" /> </span> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">出国原因</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"><span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:party_files:abroad_reason" lablename="出国原因" class="inputText" value="${partyFiles.abroad_reason}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">现任党内职务</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"><span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:party_files:party_post" lablename="现任党内职务" class="inputText" value="${partyFiles.party_post}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">出国后与组织联系情况</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"><span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:party_files:abroad_situation" lablename="出国后与组织联系情况" class="inputText" value="${partyFiles.abroad_situation}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">组织关系所在单位</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"><span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:party_files:org_relationship" lablename="组织关系所在单位" class="inputText" value="${partyFiles.org_relationship}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">党籍处理方式</td> 
   <td style="width: 35%" class="formInput"><span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:party_files:party_handling" lablename="党籍处理方式" class="inputText" value="${partyFiles.party_handling}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">流出日期</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"><span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:party_files:out_date" lablename="流出日期" value="<fmt:formatDate value="${partyFiles.out_date}" pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" isflag="tableflag" /> </span> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">回国日期</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"><span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:party_files:return_date" lablename="回国日期" value="<fmt:formatDate value="${partyFiles.return_date}" pattern='yyyy-MM-dd'/>" class="inputText date" validate="{date:true}" isflag="tableflag" /> </span> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">外出流向</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"><span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:party_files:outgoing_flow" lablename="外出流向" class="inputText" value="${partyFiles.outgoing_flow}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">恢复组织生活情况</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"><span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:party_files:restore_org_life" lablename="恢复组织生活情况" class="inputText" value="${partyFiles.restore_org_life}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">外出类型</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"><span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:party_files:out_type" lablename="外出类型" class="inputText" value="${partyFiles.out_type}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">审核人</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:party_files:reviewer" lablename="审核人" class="inputText" value="${partyFiles.reviewer}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">外出原因</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"><span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:party_files:out_reason" lablename="外出原因" class="inputText" value="${partyFiles.out_reason}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">支部书记审核</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput"><span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:party_files:branch_review" lablename="支部书记审核" class="inputText" value="${partyFiles.branch_review}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
  </tr> 
  <tr> 
   <td style="width: 15%; word-break: break-all;" class="formTitle" align="right">活动证号码</td> 
   <td style="width: 35%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"><span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="m:party_files:activity_num" lablename="活动证号码" class="inputText" value="${partyFiles.activity_num}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
  </tr> 
 </tbody> 
</table>
            </div>
        </div>
        <input type="hidden" name="id" value="${partyFiles.id}"/>
        <input type="hidden" id="saveData" name="saveData"/>
        <input type="hidden" name="executeType"  value="start" />
    </form>
</body>
</html>
