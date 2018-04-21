<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>生成文档</title>
<%@include file="/commons/include/get.jsp"%>
<%@include file="/codegen/include/customForm.jsp" %>
	<%@include file="/commons/include/ueditor.jsp" %>
	<script type="text/javascript" src="/js/hotent/platform/form/OfficeControl.js"></script>
	<script type="text/javascript" src="/js/hotent/platform/form/OfficePlugin.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
<style>
.icon-location-arrow{margin-top:3px;font-size:16px;}
.link.search{display:inline-block;padding:4px 15px;font-size:14px;}
a.link.send{padding:5px 10px;padding-left:30px;border-radius:3px;background:#478de4 url(/images/email_icon.png) 5px center no-repeat;color:#fff;}
a.link.send+a{margin-left:5px;background:#36bb5f url(/images/message_icon.png) 5px center no-repeat;}
.panel-table{overflow-y:auto!important;}
.table{margin:0 16px;table-layout:fixed;}
.table th{padding:0 15px;min-width:50px;text-align:right;}
.table td{padding:8px;}
.table td div:first-child{margin-bottom:16px;}
.panel-head{padding:10px 0;}
.panel-head h2{font-size:14px;}
.panel-wrap{margin:0 16px;}
.btn{display:inline-block;padding:0 15px;border:0;border-radius:3px 3px 3px 3px;background:#478de4;-webkit-box-shadow:0 1px 1px rgba(0,0,0,.15);-moz-box-shadow:0 1px 1px rgba(0,0,0,.15);box-shadow:0 1px 1px rgba(0,0,0,.15);color:#fff;text-align:left;text-decoration:none;line-height:26px;cursor:pointer;}
.bookmark{margin-bottom:15px;width:100%;}
.bookmark .content input[type=text]{box-sizing:border-box;padding:0 10px;width:100%;height:36px;line-height:36px;}
.panel-main{padding:10px 0;}
.bookmark td,.bookmark th{padding:0 8px;height:50px;border:1px solid #dadfed;}
.select{width:200px;height:26px;border:1px solid #dadfed;line-height:26px;}
.btns-wrap{margin-bottom:15px;}
.office-div{width:100%;height:500px;}
</style>
<%@ taglib prefix="hotent" uri="http://www.jee-soft.cn/paging" %>
</head>
<script type="text/javascript">
$(function() {
	var options={};
	if(showResponse){
		options.success=showResponse;
	}
	var frm=$('#ruleForm').form();
	$("a.save").click(function() {
		frm.ajaxForm(options);
		$("#saveData").val(1);
		if(frm.valid()){
			if(!$("input.bookmarkname") || $("input.bookmarkname").length<=0){
				$.ligerDialog.warn("请先添加书签！");
				return ;
			}
			 $.ligerDialog.prompt('请输入新规则名称', '', true, function (yes, value){
                if (yes){
                	if(value.length==0){
                		alert("请输入新规则名称！");
                		return ;
                	}
                	if(value.length>150){
                		alert("规则名称长度不能大于150！");
                		return ;
                	}
                	$("#rulename").val(value);
                	$('#ruleForm').submit();
                }
           });
			/* //如果有编辑器的情况下
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
					$('#ruleForm').submit();
				});
			}else{
				frm.handleFieldName();
				frm.sortList();
				$('#ruleForm').submit();
			} */
		}
	});
});

function showResponse(responseText) {
	var obj = new com.hotent.form.ResultMessage(responseText);
	if (obj.isSuccess()) {
		 $.ligerDialog.success('保存成功！');
	} else {
		$.ligerDialog.error(obj.getMessage(),"提示信息");
	}
}
	
	
	function addRow(){
		var index=$("#bookmark").find("tr.content").length;
		
		var html='<tr class="content">'+
			'<td><input type="text" class="inputText bookmarkname" name="ruleBookmarkList['+index+'].mark_name" value="${bookmark.mark_name }" validate="{required:true,maxlength:50}"></td>'+
			'<td><input type="text" red-element="" class="inputText bookmarkvalue" name="ruleBookmarkList['+index+'].mark_value" value="${bookmark.mark_value }" ></td>'+
		'</tr>';
		$("#bookmark").append(html);
	}
	function delLastRow(){
		$("#bookmark .content:last").remove();
		
	}
	
	$(function(){
		$("#rule").change(function(){
			
			if($(this).val()==''){
				return ;
			}else{
				window.location.href='${ctx }/makshi/doc/template/detail.ht?id=${docTemplate.id}'+'&ruleId='+$(this).val();
			}
		})
		
		$("body").delegate("input.bookmarkname","blur",function(){
			
			var bookmarkvalueNode=$(this).parent().next().children("input:first-child");
			bookmarkvalueNode.attr("red-element",$(this).val());
		});
	})
</script>

<body>
	<div class="panel">
        <div class="panel-head">
            <div class="panel-wrap">
                <h2>文档生成</h2>
            </div>
        </div>
        
        <div class="panel-main">
            <div class="panel-wrap">
                <span>选择规则</span>
                <select class="select" name="rule" id="rule"> 
                	<option value="">-请选择规则-</option>
                    <c:forEach items="${ruleList }" var="rule">
                        <option value="${rule.id }" <c:if test="${rule.id==ruleId }">selected=selected</c:if>>${rule.name }</option>
                    </c:forEach>
                </select>
                <a href="javascript:;" class="save btn" onclick="saveRule()">保存规则</a>
            </div>
        </div>
		<div class="panel-main">
            <div class="panel-wrap">
        	    <form id="ruleForm" method="post" action="${ctx }/makshi/doc/rule/save.ht">
            		<table id="bookmark" class="bookmark">
        	    		<tr class="title">
        	    			<th>书签名</th>
        	    			<th>书签值</th>
        	    		</tr>
        	    		<c:forEach items="${bookmarks }" var="bookmark" varStatus="vs">
        	    			<tr class="content">
        	    				<td><input  name="ruleBookmarkList[${vs.index}].mark_name" type="text"  class="inputText bookmarkname" value="${bookmark.mark_name }" validate="{required:true,maxlength:50}"></td>
        	    				<td><input red-element="${bookmark.mark_name }" name="ruleBookmarkList[${vs.index}].mark_value" type="text" class="inputText bookmarkvalue" value="" ></td>
        	    			</tr>
        	    		</c:forEach>
            		</table>	
                    <div class="btns-wrap">
                        <span onclick="addRow()" class="btn">+添加</span>
                        <span onclick="delLastRow()" class="btn">删除</span>
                    </div>
            		<input type="hidden" id="rulename" name="name" value="">
            		<input type="hidden" id="doctemplatesign" value="doctemplatesign_${docTemplate.id }">
        			<input type="hidden" style="width: 700px; height: 400px;" right="b" controltype="office" name="m:overtime_application:dispatch_content" value="" />
        	    </form>
            </div>
        </div>
    </div>

<script type="text/javascript"> 
var permission = 
{"field":{"position":"b","dispatch_content":"b",
"reason":"w","department":"b","application_date":"b",
"type":"b","endTime":"b","statTime":"b","content":"w",
"overtimes":"","user_num":"b","legal_overtimes":"",
"applicant":"b","account":"b"},
"menuRight":{"dispatch_content":"{\"wjRight\":\"n\",\"lhRight\":\"n\",\"blhRight\":\"n\",\"qchjRight\":\"n\",\"mbthRight\":\"y\",\"xzmbRight\":\"n\",\"sxqmRight\":\"n\",\"gzRight\":\"n\",\"qpRight\":\"y\",\"zcpdfRight\":\"n\",\"ekeygzRight\":\"n\",\"pdfgzRight\":\"n\"}"},"subFieldJson":{},"opinion":{},"table":{},"subTableShow":{}}
</script>
</body>
</html>


