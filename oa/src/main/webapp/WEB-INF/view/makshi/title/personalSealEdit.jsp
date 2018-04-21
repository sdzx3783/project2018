<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
    <title>编辑 个人执业印章</title>
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
            var frm=$('#personalSealForm').form();
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
                            $('#personalSealForm').submit();
                        });
                    }else{
                        frm.handleFieldName();
                        $('#personalSealForm').submit();
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
                        window.location.href = window.close();
                    }
                });
            } else {
                $.ligerDialog.error(obj.getMessage(),"提示信息");
            }
        }
        
        
      //  页面加载将用户id赋值js
        $(function(){
       	 var borrowerID = "${personalSeal.borrowerID}";
       	 if(borrowerID!=null&&borrowerID!=""){
       		 $("input[name='m:personal_seal:borrowerID']").val(borrowerID);
       	 };
       	 var holderID = "${personalSeal.holderID}";
       	 if(holderID!=null&&holderID!=""){
       		 $("input[name='m:personal_seal:holderID']").val(holderID);
       	 };
        });
        
    </script>
<style>
    #personalSealForm{
        margin: 0 11px;
    }
</style>
</head>
<body class="oa-mw-page">
    <div class="oa-pd20">
        <a class="oa-button oa-button--primary oa-button--blue save" id="dataFormSave">保存</a>
        <!-- <a class="oa-button oa-button--primary oa-button--blue" href="list.ht">返回</a> -->
    </div>
    <div class="oa-mg20">
    	<form id="personalSealForm" method="post" action="save.ht">
    		<table class="oa-table--default" border="1" cellspacing="0" cellpadding="2" parser="addpermission" data-sort="sortDisabled">
                    <caption>个人执业印章</caption>
  <tr> 
   <th>印章编号<span class="red">*</span></th> 
   <td>
   		<input type="text" name="m:personal_seal:seal_num" lablename="印章编号" class="oa-new-input" value="${personalSeal.seal_num}" validate="{maxlength:50,required:true}" isflag="tableflag" />
   </td> 
   <th>印章名称</th> 
   <td>
		   <select name="m:personal_seal:seal_name" lablename="印章名称" controltype="select" validate="{empty:false}" val="${personalSeal.seal_name}" class="oa-new-select"> 
			   <option value="建设部监理工程师">建设部监理工程师</option>
			   <option value="建设部造价工程师">建设部造价工程师</option>
			   <option value="一级建造师">一级建造师</option>
			   <option value="二级建造师">二级建造师</option>
			   <option value="水利部总监">水利部总监</option>
			   <option value="水利部监理工程师">水利部监理工程师</option>
			   <option value="水利部造价工程师">水利部造价工程师</option>
			   <option value="水利部监理员">水利部监理员</option>
			   <option value="一级结构工程师">一级结构工程师</option>
			   <option value="二级结构工程师">二级结构工程师</option>
			   <option value="招标师">招标师</option>
			   <option value="注册设备监理工程师">注册设备监理工程师</option>
			   <option value="注册公用设备工程师">注册公用设备工程师</option>
			   <option value="注册安全工程师">注册安全工程师</option>
			   <option value="咨询工程师（投资）">咨询工程师（投资）</option>
			   <option value="投资项目管理师">测投资项目管理师</option>
			   <option value="测绘师">测绘师</option>
			   <option value="信息监理工程师">信息监理工程师</option>
			   <option value="系统集成项目管理工程师">系统集成项目管理工程师</option>
		   </select> 
  </tr> 
  <tr> 
   <th>持章人<span class="red">*</span></th> 
   <td> 
   <input parser="selectortable" name="m:personal_seal:holder" type="text" ctltype="selector" class="link oa-new-input oa-middle user" lablename="持章人"  value="${personalSeal.holder}" validate="{empty:false,required:true}"  scope="{&#39;value&#39;:&#39;all&#39;,&#39;type&#39;:&#39;system&#39;}"/>
   </td> 
  <th>状态</th> 
   <td>
		   <select id="personalSealStatus"  class="oa-new-select"  name="m:personal_seal:status" lablename="状态" controltype="select" validate="{empty:false}" val="${personalSeal.status}"> 
			   <option value="0">未借出</option> 
			   <option value="1">已借出</option>
		   </select> 
   </td>
  </tr> 
  <tr> 
    <th>当前借用人</th> 
    <td>
   		<input parser="selectortable" name="m:personal_seal:borrower" type="text" ctltype="selector" class="link oa-new-input oa-middle user" lablename="当前借用人" value="${personalSeal.borrower}" validate="{empty:false}" readonly="readonly" scope="{&#39;value&#39;:&#39;all&#39;,&#39;type&#39;:&#39;system&#39;}"/>
    </td> 
	   
   <th>印章有效期</th> 
   <td>
   	  <input type="text" name="m:personalSeal:effictiveDate" lablename="有效日期" value="<fmt:formatDate value="${personalSeal.effictiveDate}" pattern='yyyy-MM-dd'/>" data-class="oa-new-input" class="Wdate" validate="{date:true}" isflag="tableflag" />
   </td>
  </tr> 
  <tr> 
   <th>备注</th> 
   <td> 
    <input type="text" name="m:personal_seal:remark" lablename="备注" class="oa-new-input" value="${personalSeal.remark}" validate="{maxlength:50}" isflag="tableflag" />
     </td> 
     <th>借阅日期</th> 
   <td>
   	  <input type="text" name="m:personalSeal:borrowDate" lablename="借阅日期" value="<fmt:formatDate value="${personalSeal.borrowDate}" pattern='yyyy-MM-dd'/>" data-class="oa-new-input" class="Wdate" validate="{date:true}" isflag="tableflag" />
   </td>
  </tr> 
 </tbody> 
</table>
</div>
        </div>
        <input type="hidden" name="id" value="${personalSeal.id}"/>
        <input type="hidden" name="switchs" value="${personalSeal.switchs}"/>
        <input type="hidden" name="linkId" value="${personalSeal.linkId}"/>
        <input type="hidden" id="saveData" name="saveData"/>
        <input type="hidden" name="executeType"  value="start" />
    </form>
</body>

<script type="text/javascript">
$(function(){
	changeStatus();	
	changeBorrower();
});
function changeBorrower(){
	$("input[name='m:personal_seal:borrower']").on("change",function(){
		if($(this).val()){
			$("#personalSealStatus").val("1");
		}else{
			$("#personalSealStatus").val("0")
		}
	});
}

function changeStatus(){
	$("#personalSealStatus").on("change",function(){
		var isBorrowed = $(this).val();
		if(0==isBorrowed){
			$("input[name='m:personal_seal:borrowerID']").val("");
			$("input[name='m:personal_seal:borrower']").val("");
			$("input[name='m:personal_seal:borrower']").attr("validate","{\"required\":false}");
		}else{
			var existName = $("input[name='m:personal_seal:borrower']").val();
			if(!existName){
				$("input[name='m:personal_seal:borrower']").attr("validate","{\"required\":\"true\"}");
			}
		}
	});
}

</script>
</html>
