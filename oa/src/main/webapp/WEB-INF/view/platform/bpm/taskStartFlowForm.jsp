<%@ page pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<%
response.setHeader("Pragma","No-cache");
response.setHeader("Cache-Control","no-cache");
response.setDateHeader("Expires", -10);
%>
<html>
    <head>
       <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>流程启动--${bpmDefinition.subject} --版本:${bpmDefinition.versionNo}</title>
        <%@include file="/commons/include/customForm.jsp" %>
        <link  rel="stylesheet" type="text/css" href="${ctx}/styles/default/css/hotent/task.css"></link>
        <style>
            .link.reset{
                display: none;
            }
            .link.add{
                text-align: left;
                border: 0;
                color: #fff;
                padding: 3px 15px 3px 15px;
                line-height: 20px;
                background: #478de4 !important;
                -moz-box-shadow: 0 1px 1px rgba(0,0,0,0.15);
                -webkit-box-shadow: 0 1px 1px rgba(0,0,0,0.15);
                box-shadow: 0 1px 1px rgba(0,0,0,0.15);
                border-radius: 3px;
            }

            .browser-info{
                padding: 20px;
                font-size: 18px;
                color: #ff9000;
            }
            .browser-info div{
                line-height: 1.7;
            }
            .browser-img{
                float: left;
                margin-top: 30px;
            }
            .browser-text{
                margin-left: 100px;
                border-left: 1px solid #dadfed;
                padding-left: 20px;
            }
            .subtable{
                box-sizing: border-box;
            }
            .panel-body.printForm{
                overflow: visible !important;
            }

            #div_m_political_in_pic0_images img{
                width: 100% !important;
                height: auto !important;
            }
            #div_m_political_in_pic{
                height: auto !important;
            }
            .l-text.l-text-combobox,
            .l-text-wrapper{
                width: 255px !important;
            }
            .l-box-select.l-box-select-absolute{
                width: 255px !important;
            }
            .subtable.validError{
                border: 0 !important;
            }
            #td-sealImg {
                text-align: center;
            }
        </style>
        <script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/BpmImageDialog.js"></script>
        <script type="text/javascript" src="${ctx}/js/makshi/layer/layer.js"></script>
        <script type="text/javascript">
            var isExtForm=${isExtForm};
            var isFormEmpty=${isFormEmpty};
            var isNeedSubmitConfirm=${bpmDefinition.submitConfirm==1};
            var bpmGangedSets=[];
            var hasLoadComplete=false;
            var actDefId="${bpmDefinition.actDefId}";
            var defId="${param.defId}";
            var form;
            function contractIsExists(){
            	//校验合同盖章流程-start
                var contract_num=$("input[name='m:contract_seal_application:contract_num']").val();
                var contracttype=$("select[name='m:contract_seal_application:contracttype']").val();
                var runid="${param['runid']}";
              //校验格式
                var reg = /^([a-zA-Z]{2,4}[1,2][0-9]{3}[-]\d+(|([\（][\u4e00-\u9fa5_a-zA-Z0-9]+[\）])|([-]\d+|[-]\d)+(|([\（][\u4e00-\u9fa5_a-zA-Z0-9]+[\）]))))$/;
                if(!reg.test(contract_num)){
                	$.ligerDialog.warn('请按照JL2010-1或JL2010-1（补充协议）或JL2010-1-1或JL2010-1-1（补充协议）格式填写合同编号，括号为中文',"提示信息");
                    return false;
                }
                if(contract_num && contracttype){
                     var date=new Date();
                     var t=date.getTime();
                     var isExist=false;
                             $.ajax({
                                 type:"get",
                                 async: false,
                                 url:"/makshi/contract/contractinfo/contractIsExist.ht?contractnum="+contract_num+"&contracttype="+contracttype+"&runid="+runid+"&t="+t,
                                 success:function(data){
                                     var dataObj=eval("("+data+")");
                                     var result=dataObj.result;
                                     var status=dataObj.message;
                                     if(result==1){
                                   	  if(status=='1'){
                                   		  isExist=true;
                                   	  }
                                     }else{
                                   	  isExist=true;
                                   	  $.ligerDialog.warn(status,"提示信息");
                                     }
                                 }
                             });
                      if(isExist){
                     	 $.ligerDialog.warn("该合同编号已被使用！","提示信息");
                      	return false;
                      }
                }else{
              	  $.ligerDialog.warn("请选择合同类型","提示信息")
                  	return false;
                }
                //校验合同盖章流程合同编号-end
            	return true;
            }

            //印章注销，校验 0校验过，其他校验不过
            var cancelSealValidated = function () {
                var canSealType = $('#cancelsealtype select').val();
                var contrNum = $.trim($('#contrNum input').val());

                //是否选择副经理
                var ifChSecMng = $('#ifchsecmng').val();
                var secMng = $.trim($('#chosemngtoshow div input:nth-child(2)').val());

                if((canSealType == '1' && contrNum == '')) {
                    return '1';
                } else if(ifChSecMng == '1' && secMng == '') {
                    return '2'
                } else {
                    return '0';
                }
            }

            //校验1.印章是否合法 2.用户是否有权限注销
            var validSealImgAndTaker = function () {
                $.ajax({
                    type : "post",
                    url : __ctx+"/makshi/makechapter/makeChapter/isvalid.ht",
                    async: false,
                    data : {
                        theSealId : $('#td_sealid input').val()
                    },
                    success : function(result) {
                        var obj = eval('(' + result + ')');
                        if (obj.code != 1) {
                            $.ligerDialog.warn(obj.msg);
                            return false;
                        } else {
                            return true;
                        }

                    }});


            }

            //网站投稿，校验 0校验过，其他校验不过
            var webArtConValidated = function () {
                //是否选择副经理
                var ifChSecMng = $('#ifchsecmng').val();
                var secMng = $.trim($('#chosemngtoshow div input:nth-child(2)').val());

                if(ifChSecMng == '1' && secMng == '') {
                    return '2'
                } else {
                    return '0';
                }
            }

            $(function(){
                //设置表单。
                initForm();
                //启动流程事件绑定。
                $("a.run").click(function(){
                    var flowNodes = $("input[name='flowNode']");
                    if(flowNodes && flowNodes.length>1){
                        var flowNode = $("input[type='radio']:checked");
                        if(flowNode && flowNode.length==1){
                            startWorkFlow();
                        }
                        else{
                            $.ligerDialog.warn("请选择一个跳转节点!", '提示');
                            return;
                        }
                    }else{
                        startWorkFlow();
                    }
                });
                //保存表单
                $("a.save").click(function(){
                    saveForm(this);
                }); 
                //重置表单
                $("a.reset").click(function(){
                    var fieldName=$(this).attr("name");
                    if(fieldName!=undefined&&fieldName!=null&&fieldName!=""){
                        return;
                    }
                    $("#frmWorkFlow").resetForm();
                    var parentObj = $(this).parent();
                    $("input",parentObj).each(function(){
                        $(this).val('');
                    })
                });
                
                $("#flowNodeList").delegate("input", "click", function() {
                    $("#startNode").val($(this).val());
                });
                
                //选择第一步任务的执行人
                chooseJumpType();
                //初始化联动设置               
                <c:if test="${!empty bpmGangedSets}">
                    bpmGangedSets = ${bpmGangedSets};
                    FormUtil.InitGangedSet(bpmGangedSets);
                </c:if>
                //启动流程时隐藏意见控件
                $(".taskopinion").hide();
                
            });
            
            //设置表单。
            function initForm(){
                //初始化百度编辑器
                if(isFormEmpty) return;
                //表单不为空的情况。
                if(isExtForm){
                    form=$('#frmWorkFlow').form({excludes:"[type=append]"});
                    var formUrl=$('#divExternalForm').attr("formUrl");
                    $('#divExternalForm').load(formUrl, function() {
                        hasLoadComplete=true;
                        //动态执行第三方表单指定执行的js
                        try{
                            afterOnload();
                        }catch(e){}

                        initSubForm();
                        OfficePlugin.init();
                    });
                }
            };
            
            function selExeUsers(btnElement,nodeId,scope){
                var spanObj=$(btnElement).prev();
                var aryCheckBox=$(":checked",spanObj);
                var selectUsers = [];
                if(aryCheckBox.length>0){
                    for(var i=0;i<aryCheckBox.length;i++){
                        var obj = $(aryCheckBox[i]).val().split("^");
                        var selectUser={
                                type:obj[0],
                                id:obj[1],
                                name:obj[2]
                        }
                        selectUsers.push(selectUser);
                    }
                }
                
                if(!scope){
                    scope={};
                    scope.type='system',
                    scope.value='all';
                    scope=JSON2.stringify(scope);
                }else{
                    scope=scope.replaceAll("#@","\"");
                }

                
                FlowUserDialog({selectUsers:selectUsers,scope:scope,callback:function(aryTypes,aryIds,aryNames){
                    if(aryIds==null) return;
                    var aryTmp=[];
                    var aryUserName=[];
                    for(var i=0;i<aryIds.length;i++){
                        var val=aryTypes[i] +"^" + aryIds[i] +"^" +aryNames[i];
                        var tmp="<input type='checkbox'  include='1' name='"+nodeId+"_userId' checked='checked' value='"+val+"'/>"+aryNames[i];
                        aryTmp.push(tmp);
                    }
                    spanObj.html(aryTmp.join(''));
                }});
            }
            
            //是否点击了开始按钮。
            var isStartFlow=true;
            
            function saveForm(obj){     
                isStartFlow=false;
                var  action="";
                if($(obj).hasClass('isDraft')){
                    action="${ctx}/platform/bpm/task/saveForm.ht";
                }else{
                    action="${ctx}/platform/bpm/task/saveData.ht";
                }
                submitForm(action,"a.save");
            }
            
            function startWorkFlow(){
                isStartFlow=true;
                var  action="${ctx}/platform/bpm/task/startFlow.ht";
                if(isNeedSubmitConfirm){
                    $.ligerDialog.confirm("确认启动流程吗?","提示",function(rtn){
                        if(rtn){
                            submitForm(action,"a.run");
                        }
                    });
                }
                else{
                    submitForm(action,"a.run"); 
                }
            }
            function qxjlcbdValidate(){
                var formKey=$("input[name='formKey']").val();
                var leave_days=$("#leave_days").val();
                
                if(leave_days<=0){
                	$.ligerDialog.warn('请假天数必须大于0!',"提示信息");
                	return ;
                }
                //请假流程，查询年假
                if(formKey!=null && formKey=='qxjlcbd'){
                	if(isNaN(leave_days)){
                    	$.ligerDialog.warn('请假天数数字格式错误!',"提示信息");
                    	return false;
                    }
                    var leave_start=$("#leave_start").val();
                    var start=new Date(leave_start.replace("-", "/").replace("-", "/"));  
                    var leave_end=$("#leave_end").val();
                    var end =new Date(leave_end.replace("-", "/").replace("-", "/")); 
                    if(end<start){
                         $.ligerDialog.warn('请假开始时间必须小于请假结束时间!',"提示信息");
                        return false;
                    }
                    if(leave_start==leave_end){
                        var levea_start_select=$("#levea_start_select").val();
                        var levea_end_select=$("#levea_end_select").val();
                        if(levea_end_select<levea_start_select){
                             $.ligerDialog.warn('请假开始时间必须小于请假结束时间!',"提示信息");
                            return false;
                        }
                    }
                    
                }
                    return true;
                
            }
           
            
            //表单数据提交。
            //action:表单提交到的URL
            //button：点击按钮的样式。
            function submitForm(action,button){
                 //made by sammy
                var _stampType=$('input:radio[name="m:document_check_waterprotectpark:stampType"]:checked').val();
                if(_stampType && _stampType==3){
                    $.ligerDialog.warn("公章类型,请走公司流程！","提示信息")
                    return ;
                }
                var _leave_type=$('select[name="m:vaction:leave_type"] option:selected').val();
                var leave_days=Number($("input[name='m:vaction:leave_days']").val());
                if(leave_days<=0){
                	$.ligerDialog.warn("请假天数为0！","提示信息")
                    return ;
                }
                //请假流程，查询年假
                if(_leave_type && _leave_type=='年假'){
                	var date=new Date();
                    var t=date.getTime();
                        $.ajax({
                            type:"get",
                            async: false,
                            url:"/makshi/worksheet/annualVacation/annual_vacation_days.ht?t="+t,
                            success:function(data){
                                var dataObj=eval("("+data+")");
                                var vacationDay=dataObj.message;
                                $("#remain_annual_leave").val(vacationDay);
                                $("#remain_annual_leave").attr("class","none-border-text");
                                $("#remain_annual_leave").attr("readonly",true);
                            }
                        });
                        
                    
                    var remain_annual_leave=Number($("input[name='m:vaction:remain_annual_leave']").val());
                  
                    
                    if(leave_days>=0.0){
                    	if(remain_annual_leave<0.0 || (leave_days-remain_annual_leave)>0.0){
                    		$.ligerDialog.warn("请的年假天数不能超过剩余年假天数！","提示信息")
                            return ;
                    	}
                    }else{
                    	$.ligerDialog.warn("表单数据有误！","提示信息")
                        return ;
                    }
                }
                if(action.indexOf("/platform/bpm/task/saveData.ht")<0 && action.indexOf("/platform/bpm/task/saveForm.ht")<0){
	                //合同盖章流程 验证合同编号是否已存在
	                if($("input[name='m:contract_seal_application:contract_num']").length>0){
	                	
	                	if(!contractIsExists()){
	                		return ;
	                	}
	                }
                }
                //end
                //made by levi

                //seal cancel valid start
                //saveForm.ht是草稿，saveData是什么？
                if(action.indexOf("/platform/bpm/task/saveData.ht")<0 && action.indexOf("/platform/bpm/task/saveForm.ht")<0){

                    //判断是不是注销流程的
                    if($("input[name='m:seal_cancel:contractnum']").length>0){
                        if(cancelSealValidated() == '1'){
                            $.ligerDialog.warn("印章类型为合同类，则合同编号不能为空。","提示信息");
                            return ;
                        } else if(cancelSealValidated() == '2'){
                            $.ligerDialog.warn("选择需要副经理审核，则副经理选择项不能为空。","提示信息");
                            return ;
                        }

                        //印章校验不通过
                        if(validSealImgAndTaker() == false) {

                            return;
                        }
                    }
                }
                //seal cancel valid end

                //web art con begin
                if(action.indexOf("/platform/bpm/task/saveData.ht")<0 && action.indexOf("/platform/bpm/task/saveForm.ht")<0){

                    //判断是不是注销流程的
                    if($("#dirSubDisOp").length>0 && 'webartcontribute' == $("#dirSubDisOp").val()){
                        if(webArtConValidated() != '0'){
                            $.ligerDialog.warn("选择需要副经理审核，则副经理选择项不能为空。","提示信息");
                            return ;
                        }
                    }
                }
                //web art con end



                //start
                //河道 合同金额
                var unpaid_moneys=$('input[name="m:contract_payment_application:unpaid_money"]').val();
                var total_money=$('input[name="m:contract_payment_application:total_money"]').val();
                var paid_money=$('input[name="m:contract_payment_application:paid_money"]').val();
                if(parseFloat(total_money)<parseFloat(paid_money)){
                    $.ligerDialog.warn("已付款金额必须小于或等于合同总金额","提示信息");
                     return;
                }
                if(unpaid_moneys){
                    var unpaid_moneys=$('input[name="m:contract_payment_application:unpaid_money"]').val();
                    var this_paid_moneys=$('input[name="m:contract_payment_application:this_paid_money"]').val();
                    
                    if(parseFloat(unpaid_moneys)<parseFloat(this_paid_moneys)){
                        $.ligerDialog.warn("本次付款金额必须小于或等于未付款金额","提示信息");
                         return;
                    }
                    
                    var invoice_moneys=$('input[name="s:invoice_detail:invoice_money"]').val();
                    if(parseFloat(invoice_moneys)>parseFloat(this_paid_moneys)){
                        $.ligerDialog.warn("发票金额必须小于或等于本次付款金额","提示信息");
                         return;
                    }
                }
                //end
                //百度编辑器数据处理
                var ignoreRequired=false;
                if(button=="a.save"){
                    ignoreRequired=true;
                }
                var operatorType=(isStartFlow)?1:6;
                //前置事件处理
                var rtn=beforeClick(operatorType);
                if( rtn==false){
                    return;
                }
                if($(button).hasClass("disabled")){
                     $.ligerDialog.warn('流程已被启动，请到我的申请查看!',"提示信息");
                    return;
                }
                if(isFormEmpty){
                    $.ligerDialog.warn('流程表单为空，请先设置流程表单!',"提示信息");
                    return;
                }
                if(!qxjlcbdValidate()){
                    return;
                }
                if(!judeVacationExists()){
                	return ;
                }
                    
                
                var frmWorkFlow=$('#frmWorkFlow');
                frmWorkFlow.attr("action",action);
                        
                if(isExtForm){
                    //提交第三方表单时检查该表单的参数
                    var rtn = true;
                    if(button!="a.save"){
                        rtn=form.valid()
                    }
                    if(rtn){
                        
                        if(frmWorkFlow.handleFieldName){//url表单清除命名
                            frmWorkFlow.handleFieldName();
                        }
                        if(frmWorkFlow.setData){
                            frmWorkFlow.setData();
                        }
                        $(button).addClass("disabled");
                        frmWorkFlow.submit();
                    }
                }else{
                    var rtn=CustomForm.validate({ignoreRequired:ignoreRequired,returnErrorMsg:true});
                    if(!rtn.success){
                        $.ligerDialog.warn("表单验证不成功,请检查表单是否正确填写:"+rtn.errorMsg,"提示信息");
                        return;
                    }
                    // 验证子表是否为必填
                    rtn = CustomForm.isSubTableRequest();
                    if(!rtn.success){
                        $.ligerDialog.warn("表单验证不成功：<br><b>子表：<span style='color: red;'>"+rtn.errorMsg+"</span>至少需要有一行数据</b>", "提示信息");
                        return;
                    }
                    //获取自定义表单的数据
                    var data=CustomForm.getData();
                    
                    //WebSign控件提交。 有控件时才提交   xcx
                    if(WebSignPlugin.hasWebSignField){
                        WebSignPlugin.submit();
                    }   
                    
                    $(button).addClass("disabled");
                    
                    var uaName=navigator.userAgent.toLowerCase();               
                    if(uaName.indexOf("firefox")>=0||uaName.indexOf("chrome")>=0){  // 火狐和谷歌 的文档提交
                        //Office控件提交。 有可以提交的文档
                        if(OfficePlugin.submitNum>0){
                            OfficePlugin.submit();       
                            //火狐和谷歌 的文档提交包括了  业务提交代码部分（完成  OfficePlugin.submit()后面的回调 函数 有 业务提交代码），所以 后面就不用加上业务提交代码
                        }else{   //没有可提交的文档时 直接做 业务提交代码
                            data=CustomForm.getData();
                            //设置表单数据
                            $("#formData").val(data);
                            FormSubmitUtil.submitFormAjax(frmWorkFlow,showResponse);
                        }   
                    }else{        //IE内核的等 
                        //Office控件提交。 有可以提交的文档
                        if(OfficePlugin.submitNum>0){
                            OfficePlugin.submit();
                            //当提交问题 等于 提交数量的变量 时 表示所有文档 都提交了  然后做 业务相关的提交
                            if(OfficePlugin.submitNum == OfficePlugin.submitNewNum){    
                                //获取自定义表单的数据
                                data=CustomForm.getData();
                                //设置表单数据
                                $("#formData").val(data);
                                FormSubmitUtil.submitFormAjax(frmWorkFlow,showResponse);
                                OfficePlugin.submitNewNum = 0; //重置  提交数量的变量
                            }else{
                                $.ligerDialog.warn($lang_bpm.ntkOffice.resetOfficeKj,$lang.tip.warn);
                            }
                        }else{
                            //获取自定义表单的数据
                            data=CustomForm.getData();
                            //设置表单数据
                            $("#formData").val(data);
                            FormSubmitUtil.submitFormAjax(frmWorkFlow,showResponse);
                        }
                    }
                }
                loadingIndex = layer.load(1, {
                    shade: [0.1,'#858585'] //0.1透明度的白色背景
                   });
            }
            
            function showBpmImageDlg(){
                BpmImageDialog({actDefId:"${bpmDefinition.actDefId}"});
            }
            
            function initSubForm(){
                $('#frmWorkFlow').ajaxForm({success:showResponse }); 
            }
            
            function showResponse(responseText){
                var button=(isStartFlow)? "a.run":"a.save";
                var operatorType=(isStartFlow)?1:6;
                
                var obj=new com.hotent.form.ResultMessage(responseText);
                layer.close(loadingIndex);
                if(obj.isSuccess()){
                    //判断是不是发文
                    if(defId=="10000003390004"){
                        setFontSize();
                    }
                    var msg=(isStartFlow)?"启动流程成功!":"保存表单数据成功!";
                    if(isStartFlow){
	                    $.ligerDialog.confirm(msg+"是否需要打印?","提示",function(rtn){
	                        if(rtn){
	                        	var runId = obj.data.cause;
	                        	//去向打印界面
	                        	 window.open("/platform/bpm/processRun/info.ht?runId="+runId+"&prePage=myFinishedTask");
	                        }
	                        if(window.opener){
	                            window.opener.location.href = window.opener.location.href;
	                            window.close();
	                        }else{
	                            window.close();
	                        }
	                    });
                    }else{
                    	 $.ligerDialog.warn('保存草稿成功!',"提示信息");
                    };
                 /*    
                    $.ligerDialog.success(msg,'提示信息',function(){
                        //添加后置事件处理                      
                        var rtn=afterClick(operatorType);
                        if( rtn==false){
                            return;
                        }
                        if(window.opener){
                            window.opener.location.href = window.opener.location.href;
                            window.close();
                        }else{
                            window.close();
                        }
                    }); */
                }
                else{
                    var msg=(isStartFlow)?"启动流程失败!":"保存表单数据失败!";
                    $.ligerDialog.err('提示信息',msg,obj.getMessage());
                    $(button).removeClass("disabled"); 
                }
            }
            
            function chooseJumpType(){
                var obj=$('#jumpDiv');
                
                var url="${ctx}/platform/bpm/task/tranTaskUserMap.ht?selectPath=1&isStart=1&actDefId="+actDefId;
                url=url.getNewUrl();
                obj.html(obj.attr("tipInfo")).show().load(url);
            }
            
            function openHelpDoc(fileId){
                var h=screen.availHeight-35;
                var w=screen.availWidth-5;
                var vars="top=0,left=0,height="+h+",width="+w+",status=no,toolbar=no,menubar=no,location=no,resizable=1,scrollbars=1";
                var showUrl = __ctx+"/platform/form/office/get.ht?fileId=" + fileId;
                window.open(showUrl,"myWindow",vars);   
            }
            
            //增加Web签章
            function addWebSigns(){
                AddSecSignFromServiceX();          //WebSignPlugin JS类     
            }
            
            //增加手写签章
            function addHangSigns(){
                AddSecHandSignNoPromptX();          //WebSignPlugin JS类     
            }
            
            //跟新发文字号
            function setFontSize(){
                var type = $("#mdispatchtype_id").val();
                $.post("/makshi/fontsize/fontSize/update.ht",{type:type},function(data){});
            }
            
            
        </script>
        <script type="text/javascript">
            function judeVacationExists(){
                var formKey=$("input[name='formKey']").val();
                var flag=true;
                var msg="校验不通过";
                //请假流程，查询年假
                if(formKey!=null && formKey=='qxjlcbd'){
                    var leave_start=$("#leave_start").val();
                    var leave_end=$("#leave_end").val();
                    var levea_start_select=$("#levea_start_select").val();
                    var levea_end_select=$("#levea_end_select").val();
                    var levea_days=$("#leave_days").val();
                    var levea_type=$("select[name='m:vaction:leave_type']").val();
                    var date=new Date();
                    var t=date.getTime();
                    $.ajax({
                        type:"get",
                        data:{leave_start:leave_start,leave_end:leave_end,levea_start_select:levea_start_select,levea_end_select:levea_end_select,levea_days:levea_days,levea_type:levea_type},
                        url:"/makshi/worksheet/annualVacation/validLeave.ht?t="+t,
                        async:false,
                        success:function(responseText){
                        var obj=new com.hotent.form.ResultMessage(responseText);
                        if(obj.isSuccess()){
                            flag=true;  
                         }else{
                            flag=false;
                            msg=obj.getMessage();
                         }
                        }
                    });
                    if(!flag){
                    	$.ligerDialog.warn(msg);
                    }
                }
                return flag;
            }
            function getVacationDay(){
                var leave_start=$("#leave_start").val();
                var leave_end=$("#leave_end").val();
                var levea_start_select=$("#levea_start_select").val();
                var levea_end_select=$("#levea_end_select").val();
                var leava_type=$("select[name='m:vaction:leave_type'] option:selected").val();
                var date=new Date();
                var t=date.getTime();
                $.ajax({
                    type:"get",
                    data:{leave_start:leave_start,leave_end:leave_end,levea_start_select:levea_start_select,levea_end_select:levea_end_select,leava_type:leava_type},
                    url:"/makshi/worksheet/annualVacation/leave_vacation_days.ht?t="+t,
                    success:function(data){
                        var dataObj=eval("("+data+")");
                        var vacationDay=dataObj.message;
                        $("#leave_days").val(vacationDay);
                        //$("#leave_days").attr("class","none-border-text");
                        //$("#leave_days").attr("readonly",true);
                    }
                });
            }

            $(function(){
                //个人信息
                autoSetUserInfo();
                //执业资格
                var defId = $("input[name='defId']").val();
                if(defId=="10000003950846" || defId=="10000001130191" || defId == "10000001130259"){
                changeQualificationInfo();
                }
                //借阅
                if(defId=="10000003950821"){
                changeUserId();changeTye();/* getSealIdByName(); */
                }
                //党员档案
                changePolitical();getAppliPoliticalInfo();
                //资产
                $("body").on("change","#assetId",function(){
                    autoLoadingAssetyList($("#assetId").val());
                });
                //获取项目相关信息（项目名称，项目阶段，项目任务）
                var taskId="${param.projectTaskId}";
                if(taskId && taskId.length>0 && /^\d{1,20}$/.test(taskId)){
                    getProjectinfo(taskId);
                }
                //绑定周计划更新按钮
                /* if($("#tech_review_week_plan_updateButton").length>0){
                    $("#tech_review_week_plan_updateButton").onclick(function(){
                        alert("周计划!");
                    });
                } */


                $('body').on('close.iframe', function(e){
                    $('.subtable').find('tr:last input:first').focus();
                });
            });
            

        </script>
        <style type="text/css">
           input.none-border-text{
                border: 0 !important;
                outline: none;
                width:200px;
            }

            /*上传的图像宽高被js限制了，这里用css加固*/
/*            .pictureShow-div{
                width: 420px; 
                height: 487.115px;
            }
            .thumbnail-a.elevatezoom-gallery.gallery_active{
                width: 58px; height: 49px;
            }*/
/*            .thumbnail-a.elevatezoom-gallery.gallery_active img{
                height: 45px !important;
                width: 56px !important;
            }*/
/*            #div_m_Entry_zp0_images img{
                width: 100% !important;
                height: 100% !important;
            }*/
            .table_pictureShow{
                display: none;
            }
            input[type='text'].dicComboTree.l-text-field{
                width: 230px !important;
                min-width: 230px !important;
                height: 15px !important;
            }
            .dicComboTree.l-text-field{
                width: 230px !important;
                min-width: 230px !important;
                height: 19px !important;
            }
            .l-box-select.l-box-select-absolute{
                height: 200px !important;
            }

            .panel-toolbar{
                margin-top: 0;
            }
           	.none {
           		display: none !important;
           	}
        </style>
    </head>
    <body class="oa-mw-page">
        <input type="hidden" id="selSealName"  value="" />
        <form id="frmWorkFlow" method="post" >

            <input type="hidden" id="finSealInfo" value="" />
            <input type="hidden" id="tempSealInfo" value=""/>

            <input type="hidden" include="1" name="curUserId" value="${curUserId}" />
            <input type="hidden" include="1" name="curUserName" value="${curUserName}"/>
            <input type="hidden" include="1" name="actDefId" value="${bpmDefinition.actDefId}"/>
            <input type="hidden" include="1" name="defId" value="${bpmDefinition.defId}"/>
            <input type="hidden" include="1" id="businessKey" name="businessKey" value="${businessKey}" />
            <c:if test="${empty  runId}">
                <input type="hidden" include="1" name="runId" value="${runId}" />
            </c:if>
            <input type="hidden" include="1" id="startNode" name="startNode" />
            <c:if test="${not empty  paraMap}">
                <c:forEach items="${paraMap}" var="item">
                    <input  include="1" type="hidden" name="${item.key}" value="${item.value}" />
                </c:forEach>
            </c:if>
            <div class="panel">
            
                    <!-- 顶部工具区块 -->
                    <%@include file="incToolBarStart.jsp" %>      
                    <!-- 顶部工具区块 -->

                    <div style="padding:6px 8px 3px 12px;" class="none noprint">
                        <b>流程简述：</b>${bpmDefinition.descp}
                    </div>
                    
                    <div class="panel-body printForm" style="overflow: auto;">
                            <c:choose>
                                <c:when test="${isMultipleFirstNode}">
                                    <div id="flowNodeList">
                                        <table class="table-grid">
                                            <thead>
                                            <tr>
                                                <th height="28" width="20%">选择起始路径</th>
                                                <td>
                                                    <c:forEach items="${flowNodeList}" var="flowNode">
                                                        <lable>${flowNode.nodeName}<input type="radio" name="flowNode" value="${flowNode.nodeId}" /></lable>
                                                    </c:forEach>
                                                </td>
                                            </tr>
                                            </thead>
                                        </table>
                                    </div>
                                </c:when>
                                
                            </c:choose>
                            
                            <c:if test="${bpmDefinition.showFirstAssignee==1}">
                                <div id="jumpDiv" class="noprint" style="display:none;" tipInfo="正在加载表单请稍候..."></div>
                            </c:if>
                            
                            <c:if test="${not empty param.relRunId}">
                                <div class="noprint">
                                    <jsp:include page="incTaskOpinion.jsp"></jsp:include>
                                </div>
                            </c:if>
                            
                            <c:choose>
                                <c:when test="${isFormEmpty==true}">
                                    <div class="noForm">没有设置流程表单。</div>
                                </c:when>
                                <c:otherwise>
                                    <c:choose>
                                        <c:when test="${isExtForm}">
                                            <div id="divExternalForm" formUrl="${form}"></div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="panel-detail" type="custform" id="custformDiv">${form}</div>
                                            <input type="hidden" include="1" name="formKey" value="${formKey}"/>
                                            <input type="hidden" include="1" name="formData" id="formData" />
                                        </c:otherwise>
                                    </c:choose>
                                </c:otherwise>
                            </c:choose>
                    </div>
            </div>
        </form>

        <input type="hidden" id="curCtx" value="${ctx}" />
 <script type="text/javascript">
    $(function(){
        var formKey=$("input[name='formKey']").val();
        //请假流程，查询年假
        if(formKey!=null && formKey=='qxjlcbd'){
            getAnnualVacationDay();
        }
        function getAnnualVacationDay(){
            var date=new Date();
            var t=date.getTime();
                $.ajax({
                    type:"get",
                    url:"/makshi/worksheet/annualVacation/annual_vacation_days.ht?t="+t,
                    success:function(data){
                        var dataObj=eval("("+data+")");
                        var vacationDay=dataObj.message;
                        $("#remain_annual_leave").val(vacationDay);
                        $("#remain_annual_leave").attr("class","none-border-text");
                        $("#remain_annual_leave").attr("readonly",true);
                    }
                });
                $("#leave_days").val(1);
                //$("#leave_days").attr("class","none-border-text");
                //$("#leave_days").attr("readonly",true);
        }
    });


    //设置印章信息
    var cfgSealInfo = function () {
        //只有印章注销才执行
        if($("#dirSubDisOp").length > 0 && $("#dirSubDisOp").val() == 'seal_cancel') {
            if ($('#tempSealInfo').val() != '') {
                $('#finSealInfo').val($('#tempSealInfo').val());

                var json = eval('(' + $('#tempSealInfo').val() + ')');

//                console.log(json);

                if(typeof(json.ID) != 'undefined' && typeof(json.seal_img) != 'undefined') {
                    $('input[name="m:seal_cancel:sealId"]').val(json.ID);
                    $('input[name="m:seal_cancel:sealimguri"]').val(json.seal_img);
                    setSealImg(json.seal_img);
                }

            }
        }
    }

    //设置中间变量，印章信息
    var setTempSealInfo = function (data) {
        //只有印章注销才执行
        if($("#dirSubDisOp").length > 0 && $("#dirSubDisOp").val() == 'seal_cancel') {
            $('#tempSealInfo').val(data);

        }
    }

    //设置印章图片
    var setSealImg = function (sufUrl) {

        if(sufUrl == 'undefined' || typeof (sufUrl) == 'undefined') {
            return;
        }

        var fUrl = $('#curCtx').val() + sufUrl;
        var newSealImg = '<img id="newSealImg" style="height: 300px;width: auto;" alt="" src="' + fUrl + '"/>';

        if($('#td-sealImg').length > 0) {
            if ($('#td-sealImg img').length > 0 ) {
                $('#td-sealImg img').remove();
            }
            $('#td-sealImg').append(newSealImg);
        }
    }

    //初始化的时候要设置图片
     $(function () {
         var sufUri = $('input[name="m:seal_cancel:sealimguri"]').val();
         if(sufUri != '' && $("#dirSubDisOp").length > 0 && $("#dirSubDisOp").val() == 'seal_cancel') {
             setSealImg(sufUri);
         }
     });
 </script>
    </body>
    
</html>