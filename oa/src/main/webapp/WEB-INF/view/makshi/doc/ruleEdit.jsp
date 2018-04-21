<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 规则管理</title>
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
			var frm=$('#ruleManagerForm').form();
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
							frm.sortList();
							$('#ruleManagerForm').submit();
						});
					}else{
						frm.handleFieldName();
						frm.sortList();
						$('#ruleManagerForm').submit();
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
						window.location.href = "${ctx}/makshi/doc/rule/list.ht";
					}
				});
			} else {
				$.ligerDialog.error(obj.getMessage(),"提示信息");
			}
		}
	</script>
    <style>
    .panel-head{padding:10px 0;}
    .panel-head h2{font-size:14px;}
    .panel-wrap{margin:0 16px;}
    .panel-main{margin:0 16px;padding:10px 0;}
    .default-table{width:100%;}
    .default-table td,.default-table th{padding:0 8px;height:50px;border:1px solid #dadfed;}
    .default-table input[type=text]{box-sizing:border-box;padding:0 10px;width:100%;height:36px;line-height:36px;}
    span[name=editable-input]{display:block!important;}
    .btn{display:inline-block;padding:0 15px;border:0;border-radius:3px 3px 3px 3px;background:#478de4;-webkit-box-shadow:0 1px 1px rgba(0,0,0,.15);-moz-box-shadow:0 1px 1px rgba(0,0,0,.15);box-shadow:0 1px 1px rgba(0,0,0,.15);color:#fff;text-align:left;text-decoration:none;line-height:26px;cursor:pointer;}
    .btns-wrap{margin-bottom:15px;}
    .sm-input{box-sizing:border-box;width:200px;height:26px;border:1px solid #dadfed;line-height:26px;}
    </style>
</head>
<body>
<div class="panel" style="height:100%;overflow:auto;">
        <div class="panel-head">
            <div class="panel-wrap">
                <h2>规则详情</h2>
            </div>
        </div>

	<form id="ruleManagerForm" method="post" action="save.ht">
		<div type="custform">
			<div class="">
                <div class="panel-main">
                    <span>规则</span>
                    <input type="text" name="m:rule_manager:name" lablename="规则" class="inputText sm-input" value="${ruleManager.name}" validate="{maxlength:50,required:true}" isflag="tableflag" />
                             <a class="btn save" id="dataFormSave" href="javascript:;">保存</a>
                <a class="btn " href="list.ht">返回</a>
                </div>

                <div type="subtable" class="panel-main" tablename="ruleBookmark" tabledesc="规则管理" show="true" parser="rowmodeedit" id="ruleBookmark" formtype="edit"> 
                    <div class="btns-wrap">
                        <a class="add btn" href="javascript:;">添加</a>
                        
                    </div>
                            
                     <table class="default-table" border="0" cellpadding="2" cellspacing="0"> 
                      <tbody> 

                       <tr class=""> 
                        <th style="word-break: break-all;">书签名</th> 
                        <th style="word-break: break-all;">书签值</th> 
                       </tr> 
                       <c:forEach items="${ruleBookmarkList}" var="ruleBookmark" varStatus="status">
                        <tr class="" type="subdata"> 
                         <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:ruleBookmark:mark_name" lablename="书签名" class="inputText" value="${ruleBookmark.mark_name}" validate="{required:true,maxlength:50}" isflag="tableflag" /> </span> </td> 
                         <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:ruleBookmark:mark_value" lablename="书签值" class="inputText" value="" readonly="readonly" isflag="tableflag" /> </span> </td> 
                        </tr>
                       </c:forEach> 
                       <tr class="" type="append" style="display:none;"> 
                          <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text"  name="s:ruleBookmark:mark_name" lablename="书签名" class="inputText" value="${ruleBookmark.mark_name}" validate="{required:true,maxlength:50}" isflag="tableflag" /> </span> </td> 
                          <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:ruleBookmark:mark_value" lablename="书签值" class="inputText" value="" readonly="readonly" isflag="tableflag" /> </span> </td> 
                       </tr>
                      </tbody> 
                     </table> 
                     <input name="s:rule_bookmark:id" type="hidden" pk="true" value="" />
                </div>
			</div>
		</div>
		<input type="hidden" name="id" value="${ruleManager.id}"/>
		<input type="hidden" id="saveData" name="saveData"/>
		<input type="hidden" name="executeType"  value="start" />
	</form>
</body>
</html>
